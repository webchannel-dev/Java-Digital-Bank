/*     */ package com.bright.assetbank.batch.upload.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.action.ViewAddAssetAction;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.SubmitOptions;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.batch.upload.form.ImportForm;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.CollectionUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewAddPlaceholdersMetadataAction extends ViewAddAssetAction
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  53 */     ActionForward afForward = null;
/*     */ 
/*  55 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  57 */     if ((!userProfile.getIsLoggedIn()) || (!AssetBankSettings.getAssetEntitiesEnabled()) || (!userProfile.getUserCanAddEmptyAssets()))
/*     */     {
/*  59 */       this.m_logger.debug("This user does not have permission to view the admin page");
/*  60 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  63 */     ImportForm form = (ImportForm)a_form;
/*     */ 
/*  66 */     Vector vecEntities = this.m_assetEntityManager.getEntitiesWithMatchAttribute(a_dbTransaction);
/*     */ 
/*  69 */     if ((vecEntities == null) || (vecEntities.size() == 0))
/*     */     {
/*  71 */       throw new Bn2Exception("ViewAddPlaceholders: no entities with match attributes.");
/*     */     }
/*     */ 
/*  74 */     form.setEntities(vecEntities);
/*     */ 
/*  76 */     LanguageUtils.setLanguageOnAll(form.getEntities(), userProfile.getCurrentLanguage());
/*     */ 
/*  79 */     long lEntityId = getLongParameter(a_request, "entityId");
/*     */ 
/*  81 */     if (lEntityId > 0L)
/*     */     {
/*  83 */       form.setSelectedAssetEntityId(lEntityId);
/*  84 */       form.setEntityPreSelected(true);
/*     */     }
/*  89 */     else if (form.getEntities().size() == 1)
/*     */     {
/*  91 */       form.setSelectedAssetEntityId(((AssetEntity)form.getEntities().get(0)).getId());
/*  92 */       form.setEntityPreSelected(true);
/*     */     }
/*     */ 
/*  97 */     if (form.getSelectedAssetEntityId() > 0L)
/*     */     {
/*  99 */       AssetEntity entity = (AssetEntity)CollectionUtil.getDataBeanWithId(form.getEntities(), form.getSelectedAssetEntityId());
/* 100 */       form.getAsset().setEntity(entity);
/*     */     }
/*     */ 
/* 106 */     afForward = super.execute(a_mapping, a_form, a_request, a_response, a_dbTransaction);
/*     */ 
/* 110 */     if (!form.getHasErrors())
/*     */     {
/* 112 */       if (form.getSubmitOptions().getContains(2))
/*     */       {
/* 114 */         form.setSelectedSubmitOption(2);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 119 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected void populateMetadataDefaults(AssetForm a_assetForm)
/*     */     throws Bn2Exception
/*     */   {
/* 126 */     if (AssetBankSettings.getBulkUploadPopulateDefaults())
/*     */     {
/* 128 */       super.populateMetadataDefaults(a_assetForm);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.upload.action.ViewAddPlaceholdersMetadataAction
 * JD-Core Version:    0.6.0
 */