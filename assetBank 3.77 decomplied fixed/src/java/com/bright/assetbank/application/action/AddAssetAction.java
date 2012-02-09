/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Date;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public class AddAssetAction extends SaveAssetAction
/*     */   implements AssetBankConstants
/*     */ {
/*  72 */   private AssetRelationshipManager m_assetRelationshipManager = null;
/*     */ 
/*     */   public void setAssetRelationshipManager(AssetRelationshipManager a_assetRelationshipManager) {
/*  75 */     this.m_assetRelationshipManager = a_assetRelationshipManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  85 */     AssetForm form = (AssetForm)a_form;
/*  86 */     ActionForward forward = null;
/*  87 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  90 */     if (((form.getFile() == null) || (form.getFile().getFileSize() == 0)) && ((form.getTempFileLocation() == null) || (form.getTempFileLocation().length() == 0)) && (((form.getAsset().getEntity()).getId() <= 0L) || (form.getAsset().getEntity().getAllowAssetFiles())) && (!form.isEmptyAsset()))
/*     */     {
/*  94 */       form.addError(this.m_listManager.getListItem("failedValidationFile", userProfile.getCurrentLanguage()).getBody());
/*     */ 
/*  96 */       form.setNoUploadFileSpecified(true);
/*     */     }
/*     */ 
/* 100 */     form.getAsset().setDateAdded(new Date());
/*     */ 
/* 103 */     String sTempFileLocation = form.getTempFileLocation();
/*     */ 
/* 105 */     if (StringUtil.stringIsPopulated(sTempFileLocation))
/*     */     {
/* 107 */       sTempFileLocation = FileUtil.getFilename(sTempFileLocation);
/* 108 */       form.getAsset().setImportedAssetId(FileUtil.getFilepathWithoutSuffix(form.getOriginalFilename()));
/*     */     }
/*     */ 
/* 112 */     forward = super.execute(a_mapping, a_form, a_request, a_response);
/*     */ 
/* 115 */     AssetEntity entity = this.m_assetEntityManager.getEntity(null, form.getAsset().getEntity().getId());
/* 116 */     form.getAsset().setEntity(entity);
/*     */ 
/* 119 */     if ((forward.getName() == null) || (forward.getName().equals("Success")) || (forward.getName().equals("BrowseCategory")) || (forward.getName().equals("BrowseExplorer")) || (forward.getName().equals("AddExtensionAssetAL")) || (forward.getName().equals("AddExtensionAsset")))
/*     */     {
/* 126 */       if ((form.getPeerId() > 0L) || (form.getParentId() > 0L))
/*     */       {
/* 128 */         String sIds = null;
/* 129 */         String sOriginalId = null;
/* 130 */         long lRelationshipId = 0L;
/* 131 */         String sParam = null;
/*     */ 
/* 133 */         if (form.getPeerId() > 0L)
/*     */         {
/* 135 */           sIds = String.valueOf(form.getPeerId());
/* 136 */           sOriginalId = sIds;
/* 137 */           Asset peerAsset = this.m_assetManager.getAsset(null, form.getPeerId(), null, true, false);
/* 138 */           if (StringUtil.stringIsPopulated(peerAsset.getPeerAssetIdsAsString()))
/*     */           {
/* 140 */             sIds = sIds + "," + peerAsset.getPeerAssetIdsAsString();
/*     */           }
/* 142 */           lRelationshipId = 1L;
/* 143 */           sParam = "peerAssetIds";
/* 144 */           form.getAsset().setPeerAssetIdsAsString(sIds);
/*     */         }
/* 146 */         else if (form.getParentId() > 0L)
/*     */         {
/* 148 */           sIds = String.valueOf(form.getParentId());
/* 149 */           sOriginalId = sIds;
/* 150 */           lRelationshipId = 3L;
/* 151 */           sParam = "childAssetIds";
/* 152 */           form.getAsset().setParentAssetIdsAsString(sIds);
/*     */         }
/*     */ 
/* 156 */         this.m_assetRelationshipManager.saveRelatedAssetIds(null, form.getAsset(), sIds, lRelationshipId, true);
/* 157 */         Vector<Long> longIdsToReindex = StringUtil.convertToVectorOfLongs(sIds.replaceAll(" ", ""), ",");
/*     */ 
/* 159 */         for (Long longId : longIdsToReindex)
/*     */         {
/* 161 */           Asset asset = this.m_assetManager.getAsset(null, longId.longValue(), null, true, true);
/*     */ 
/* 164 */           if ((lRelationshipId == 1L) || (((AssetBankSettings.getSortByPresenceOfChildAssets()) || (AttributeUtil.getDataFromChildrenSet(asset.getAttributeValues()))) && (lRelationshipId == 3L)))
/*     */           {
/* 168 */             this.m_searchManager.indexDocument(asset, true);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 176 */         this.m_searchManager.indexDocument(this.m_assetManager.getAsset(null, form.getAsset().getId(), null, true, true), true);
/*     */ 
/* 179 */         if ((forward.getName() == null) || (forward.getName().equals("Success")))
/*     */         {
/* 181 */           forward = createRedirectingForward("id=" + sOriginalId + "&" + sParam + "=" + form.getAsset().getId(), a_mapping, "AddedRelatedAsset");
/*     */         }
/*     */ 
/*     */       }
/* 186 */       else if (((forward.getName() == null) || (forward.getName().equals("Success"))) && (entity != null) && (entity.getAllowChildren()))
/*     */       {
/* 189 */         forward = createRedirectingForward("id=" + form.getAsset().getId() + "&" + "isParent" + "=" + String.valueOf(true) + "&" + "typeName" + "=" + entity.getName() + "&" + "childName" + "=" + entity.getChildRelationshipToName() + "&" + "childrenName" + "=" + entity.getChildRelationshipToNamePlural() + "&" + "addingFromBrowseCatId" + "=" + form.getAddingFromBrowseCatId() + "&" + "addingFromBrowseTreeId" + "=" + form.getAddingFromBrowseTreeId(), a_mapping, "Success");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 202 */     if ((forward.getName() == null) || (!forward.getName().equals("Failure")))
/*     */     {
/* 205 */       if ((form.getAsset().getEntity().getHasDefaultRelationship()) && (form.getPeerId() <= 0L) && (form.getParentId() <= 0L))
/*     */       {
/* 208 */         a_request.setAttribute("forward", forward);
/* 209 */         a_request.setAttribute("id", new Long(form.getAsset().getId()));
/* 210 */         forward = a_mapping.findForward("DefaultRelationship");
/*     */       }
/*     */     }
/*     */ 
/* 214 */     return forward;
/*     */   }
/*     */ 
/*     */   public void addInvisibleCategoryIds(Vector<Category> a_vExistingCategoriesInAsset, Set<Long> a_visIds, Vector<Category> a_vecNewCategoriesInAsset, boolean a_bImplySameForAllDescendants)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.AddAssetAction
 * JD-Core Version:    0.6.0
 */