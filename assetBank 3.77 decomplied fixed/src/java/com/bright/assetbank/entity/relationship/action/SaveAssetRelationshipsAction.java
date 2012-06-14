/*     */ package com.bright.assetbank.entity.relationship.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.exception.InvalidRelationshipException;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetEntityRelationshipManager;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveAssetRelationshipsAction extends AssetRelationshipsUpdateAction
/*     */ {
/*  82 */   private AssetEntityRelationshipManager m_assetEntityRelationshipManager = null;
/*  83 */   private ListManager m_listManager = null;
/*     */ 
/*     */   protected ActionForward performAction(Asset a_asset, ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  93 */     ActionForward afForward = null;
/*  94 */     AssetForm form = (AssetForm)a_form;
/*  95 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  98 */     if (form.getAsset().getCurrentVersionId() > 0L)
/*     */     {
/* 100 */       this.m_logger.debug("SaveAssetACtion.execute() : Cannot edit relationships for previous asset versions");
/* 101 */       return a_mapping.findForward("SystemFailure");
/*     */     }
/*     */ 
/* 105 */     validateAssetRelationships(a_dbTransaction, form, userProfile);
/*     */ 
/* 107 */     if (form.getHasErrors())
/*     */     {
/* 109 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/* 113 */       getAssetRelationshipManager().saveRelationships(a_asset, a_asset.getId(), false, form.getAsset().getChildAssetIdsAsString(), form.getAsset().getPeerAssetIdsAsString(), form.getAsset().getParentAssetIdsAsString());
/*     */ 
/* 120 */       a_asset.setChildAssetIdsAsString(getRelatedAssetIds(form.getAsset().getChildAssetIdsAsString(), form.getChildAssetIdArray()));
/* 121 */       a_asset.setPeerAssetIdsAsString(getRelatedAssetIds(form.getAsset().getPeerAssetIdsAsString(), form.getPeerAssetIdArray()));
/* 122 */       a_asset.setParentAssetIdsAsString(getRelatedAssetIds(form.getAsset().getParentAssetIdsAsString(), form.getParentAssetIdArray()));
/*     */ 
/* 124 */       afForward = getSuccessForward(a_dbTransaction, a_request, form, a_mapping);
/*     */     }
/*     */ 
/* 127 */     return afForward;
/*     */   }
/*     */ 
/*     */   private String getRelatedAssetIds(String a_sRelatedAssetIds, long[] a_aRelatedAssetIds)
/*     */   {
/* 141 */     if ((a_aRelatedAssetIds != null) && (a_aRelatedAssetIds.length > 0))
/*     */     {
/* 144 */       if (StringUtil.stringIsPopulated(a_sRelatedAssetIds))
/*     */       {
/* 146 */         return a_sRelatedAssetIds + "," + StringUtil.convertNumbersToString(a_aRelatedAssetIds, ",");
/*     */       }
/* 148 */       return StringUtil.convertNumbersToString(a_aRelatedAssetIds, ",");
/*     */     }
/*     */ 
/* 151 */     return a_sRelatedAssetIds;
/*     */   }
/*     */ 
/*     */   private void validateAssetRelationships(DBTransaction a_dbTransaction, AssetForm a_form, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 164 */     if ((a_form.getAsset().getEntity() != null) && (a_form.getAsset().getEntity().getId() > 0L))
/*     */     {
/* 166 */       String sChildIds = a_form.getAsset().getChildAssetIdsAsString();
/* 167 */       String sParentIds = a_form.getAsset().getParentAssetIdsAsString();
/* 168 */       String sPeerIds = a_form.getAsset().getPeerAssetIdsAsString();
/*     */ 
/* 170 */       if (((StringUtils.isNotEmpty(sChildIds)) && (!sChildIds.matches("( *[0-9]{1,10} *,)*( *[0-9]{1,10} *)"))) || ((StringUtils.isNotEmpty(sParentIds)) && (!sParentIds.matches("( *[0-9]{1,10} *,)*( *[0-9]{1,10} *)"))) || ((StringUtils.isNotEmpty(sPeerIds)) && (!sPeerIds.matches("( *[0-9]{1,10} *,)*( *[0-9]{1,10} *)"))))
/*     */       {
/* 174 */         a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationInvalidIdList", a_userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */       else
/*     */       {
/*     */         try
/*     */         {
/* 180 */           this.m_assetEntityRelationshipManager.validateAssetRelationships(null, a_form.getAsset().getEntity().getId(), sChildIds, 2L, a_userProfile.getCurrentLanguage());
/*     */ 
/* 183 */           this.m_assetEntityRelationshipManager.validateAssetRelationships(null, a_form.getAsset().getEntity().getId(), sParentIds, 3L, a_userProfile.getCurrentLanguage());
/*     */ 
/* 186 */           this.m_assetEntityRelationshipManager.validateAssetRelationships(null, a_form.getAsset().getEntity().getId(), sPeerIds, 1L, a_userProfile.getCurrentLanguage());
/*     */         }
/*     */         catch (InvalidRelationshipException e)
/*     */         {
/* 191 */           String sMessageId = e.isInvalidEfference() ? "failedValidationInvalidToRelationship" : "failedValidationInvalidFromRelationship";
/* 192 */           String[] asParams = { String.valueOf(e.getAfferentId()), e.getRelationshipName() };
/* 193 */           a_form.addError(this.m_listManager.getListItem(a_dbTransaction, sMessageId, a_userProfile.getCurrentLanguage(), asParams));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private ActionForward getSuccessForward(DBTransaction a_transaction, HttpServletRequest a_request, AssetForm a_form, ActionMapping a_mapping)
/*     */     throws Bn2Exception
/*     */   {
/* 213 */     String sAdding = a_request.getParameter("add");
/* 214 */     boolean bAdding = false;
/* 215 */     String sQueryString = "addingFromBrowseCatId=" + a_form.getAddingFromBrowseCatId() + "&" + "addingFromBrowseTreeId" + "=" + a_form.getAddingFromBrowseTreeId() + "&" + "id" + "=";
/*     */ 
/* 218 */     if (StringUtil.stringIsPopulated(sAdding))
/*     */     {
/* 220 */       bAdding = Boolean.parseBoolean(sAdding);
/*     */     }
/*     */ 
/* 223 */     if ((bAdding) && (a_form.getPeerId() <= 0L) && (a_form.getParentId() <= 0L))
/*     */     {
/* 226 */       Asset tempAsset = getAssetManager().getAsset(a_transaction, a_form.getAsset().getId(), null, false, false);
/*     */ 
/* 230 */       if ((!tempAsset.getIsUnsubmitted()) && (!tempAsset.getIsFullyApproved()))
/*     */       {
/* 232 */         return a_mapping.findForward("SubmittedForApproval");
/*     */       }
/*     */ 
/* 236 */       sQueryString = sQueryString + a_form.getAsset().getId();
/* 237 */       if (tempAsset.getIsUnsubmitted())
/*     */       {
/* 239 */         sQueryString = sQueryString + "&" + "unsubmitted" + "=true";
/*     */       }
/* 241 */       return createRedirectingForward(sQueryString, a_mapping, "AddAssetSuccess");
/*     */     }
/*     */ 
/* 247 */     long lForwardId = a_form.getAsset().getId();
/* 248 */     if (a_form.getPeerId() > 0L)
/*     */     {
/* 250 */       lForwardId = a_form.getPeerId();
/*     */     }
/* 252 */     else if (a_form.getParentId() > 0L)
/*     */     {
/* 254 */       lForwardId = a_form.getParentId();
/*     */     }
/* 256 */     return createRedirectingForward(sQueryString += lForwardId, a_mapping, "Success");
/*     */   }
/*     */ 
/*     */   public void setAssetEntityRelationshipManager(AssetEntityRelationshipManager assetEntityManager)
/*     */   {
/* 265 */     this.m_assetEntityRelationshipManager = assetEntityManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 270 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.SaveAssetRelationshipsAction
 * JD-Core Version:    0.6.0
 */