/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.AssetForm;
          import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.exception.InvalidRelationshipException;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveAssetRelationshipsAction extends BTransactionAction
/*     */ {
/*  95 */   private IAssetManager m_assetManager = null;
/*  96 */   private MultiLanguageSearchManager m_searchManager = null;
/*  97 */   private AssetEntityManager m_assetEntityManager = null;
/*  98 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 107 */     ActionForward afForward = null;
/* 108 */     AssetForm form = (AssetForm)a_form;
/*     */ 
/* 111 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 113 */     if ((!userProfile.getIsLoggedIn()) || ((!userProfile.getCanUpdateAssets()) && (!userProfile.getCanUploadWithApproval())))
/*     */     {
/* 115 */       this.m_logger.debug("This user does not have permission to view the admin page");
/* 116 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 119 */     Asset asset = form.getAsset();
/*     */ 
/* 122 */     if (form.getAsset().getCurrentVersionId() > 0L)
/*     */     {
/* 124 */       this.m_logger.debug("SaveAssetACtion.execute() : Cannot edit relationships for previous asset versions");
/* 125 */       return a_mapping.findForward("SystemFailure");
/*     */     }
/*     */ 
/* 129 */     validateAssetRelationships(a_dbTransaction, form, userProfile);
/*     */ 
/* 131 */     if (form.getHasErrors())
/*     */     {
/* 133 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/* 137 */       Set assetsToIndex = new HashSet();
/*     */ 
/* 140 */       Vector vChildren =((AssetManager)this.m_assetManager).getRelatedAssetIds(a_dbTransaction, asset.getId(), 2L);
/* 141 */       Vector vParents =((AssetManager) this.m_assetManager).getRelatedAssetIds(a_dbTransaction, asset.getId(), 3L);
/* 142 */       Vector vPeers =((AssetManager) this.m_assetManager).getRelatedAssetIds(a_dbTransaction, asset.getId(), 1L);
/*     */ 
/* 146 */       Collection colTemp = null;
/*     */                                   //DBTransaction a_dbTransaction, long a_lAssetId, String a_sIdList, Vector a_vExistingIds, long a_lRelationshipTypeId
/* 148 */       colTemp = saveRelatedAssets(a_dbTransaction, asset.getId(), asset.getChildAssetIdsAsString(), vChildren, 2L);
/* 149 */       assetsToIndex.addAll(colTemp);
/*     */ 
/* 151 */       colTemp = saveRelatedAssets(a_dbTransaction, asset.getId(), asset.getParentAssetIdsAsString(), vParents, 3L);
/* 152 */       assetsToIndex.addAll(colTemp);
/*     */ 
/* 154 */       colTemp = saveRelatedAssets(a_dbTransaction, asset.getId(), asset.getPeerAssetIdsAsString(), vPeers, 1L);
/* 155 */       assetsToIndex.addAll(colTemp);
/*     */ 
/* 159 */       assetsToIndex.add(Long.valueOf(asset.getId()));
/*     */ 
/* 161 */       Vector assets = new Vector();
/* 162 */       Iterator itIdsToIndex = assetsToIndex.iterator();
/*     */ 
/* 164 */       while (itIdsToIndex.hasNext())
/*     */       {
/* 166 */         assets.add(this.m_assetManager.getAsset(a_dbTransaction, ((Long)itIdsToIndex.next()).longValue(), null, true, true));
/*     */       }
/*     */ 
/* 170 */       if ((AssetBankSettings.getAllowPublishing()) && (AssetBankSettings.getIncludeParentMetadataForExport()))
/*     */       {
/* 172 */         this.m_assetManager.updateDateLastModifiedForAssets(a_dbTransaction, new GregorianCalendar().getTime(), new Vector(assetsToIndex));
/*     */       }
/*     */ 
/* 175 */       this.m_searchManager.indexDocuments(a_dbTransaction, assets, true, false);
/*     */ 
/* 177 */       afForward = createRedirectingForward("id=" + asset.getId(), a_mapping, "Success");
/*     */     }
/*     */ 
/* 180 */     return afForward;
/*     */   }
/*     */ 
/*     */   private Collection saveRelatedAssets(DBTransaction a_dbTransaction, long a_lAssetId, String a_sIdList, Vector a_vExistingIds, long a_lRelationshipTypeId)
/*     */     throws Bn2Exception
/*     */   {
/* 202 */     Collection result = Collections.EMPTY_LIST;
/* 203 */     if (a_sIdList != null)
/*     */     {
/* 205 */       //this.m_assetManager.saveRelatedAssetIds(a_dbTransaction, a_lAssetId, a_sIdList, a_lRelationshipTypeId);
/* 206 */       Vector vIds = StringUtil.convertToVectorOfLongs(a_sIdList.replaceAll(" ", ""), ",");
/* 207 */       if (a_vExistingIds != null)
/*     */       {
/* 209 */         result = CollectionUtils.disjunction(vIds, a_vExistingIds);
/*     */       }
/*     */       else
/*     */       {
/* 213 */         result = vIds;
/*     */       }
/*     */     }
/* 216 */     return result;
/*     */   }
/*     */ 
/*     */   private void validateAssetRelationships(DBTransaction a_dbTransaction, AssetForm a_form, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 228 */     if ((a_form.getAsset().getEntity() != null) && (a_form.getAsset().getEntity().getId() > 0L))
/*     */     {
/* 230 */       String sChildIds = a_form.getAsset().getChildAssetIdsAsString();
/* 231 */       String sParentIds = a_form.getAsset().getParentAssetIdsAsString();
/* 232 */       String sPeerIds = a_form.getAsset().getPeerAssetIdsAsString();
/*     */ 
/* 234 */       if (((StringUtils.isNotEmpty(sChildIds)) && (!sChildIds.matches("( *[0-9]{1,10} *,)*( *[0-9]{1,10} *)"))) || 
/* 235 */         ((StringUtils.isNotEmpty(sParentIds)) && (!sParentIds.matches("( *[0-9]{1,10} *,)*( *[0-9]{1,10} *)"))) || (
/* 236 */         (StringUtils.isNotEmpty(sPeerIds)) && (!sPeerIds.matches("( *[0-9]{1,10} *,)*( *[0-9]{1,10} *)"))))
/*     */       {
/* 238 */         a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationInvalidIdList", a_userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */       else
/*     */       {
/*     */         //try
/*     */         //{
/* 244 */         //  this.m_assetEntityManager.assertRelationships(
/* 245 */         //  null, a_form.getAsset().getEntity().getId(), sChildIds, 2L, a_userProfile.getCurrentLanguage());
/*     */ 
/* 247 */         //  this.m_assetEntityManager.assertRelationships(
/* 248 */         //  null, a_form.getAsset().getEntity().getId(), sParentIds, 3L, a_userProfile.getCurrentLanguage());
/*     */ 
/* 250 */         //  this.m_assetEntityManager.assertRelationships(
/* 251 */         //  null, a_form.getAsset().getEntity().getId(), sPeerIds, 1L, a_userProfile.getCurrentLanguage());
/*     */         //}
/*     */         //catch (InvalidRelationshipException e)
/*     */         //{
/* 255 */         //  String sMessageId = e.isInvalidEfference() ? "failedValidationInvalidToRelationship" : "failedValidationInvalidFromRelationship";
/* 256 */         // String[] asParams = { String.valueOf(e.getAfferentId()), e.getRelationshipName() };
/* 257 */         //  a_form.addError(this.m_listManager.getListItem(a_dbTransaction, sMessageId, a_userProfile.getCurrentLanguage(), asParams));
/*     */        // }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 268 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 274 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityManager(AssetEntityManager assetEntityManager)
/*     */   {
/* 279 */     this.m_assetEntityManager = assetEntityManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 284 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.SaveAssetRelationshipsAction
 * JD-Core Version:    0.6.0
 */