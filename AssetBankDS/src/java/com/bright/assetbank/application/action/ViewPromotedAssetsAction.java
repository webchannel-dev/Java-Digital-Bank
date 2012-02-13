/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.category.form.BrowseItemsForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewPromotedAssetsAction extends BTransactionAction
/*     */ {
/*  49 */   private IAssetManager m_assetManager = null;
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager) {
/*  52 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  79 */     ABUserProfile userprofile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */     try
/*     */     {
/*  83 */       BrowseItemsForm form = (BrowseItemsForm)a_form;
/*     */ 
/*  86 */       int iPageIndex = getIntParameter(a_request, "page");
/*     */ 
/*  89 */       if (iPageIndex < 0)
/*     */       {
/*  91 */         iPageIndex = 0;
/*     */       }
/*     */ 
/*  95 */       int iPageSize = getIntParameter(a_request, "pageSize");
/*     */ 
/*  98 */       if (iPageSize <= 0)
/*     */       {
/* 100 */         iPageSize = AssetBankSettings.getDefaultNumImagesPerBrowsePage();
/*     */       }
/*     */ 
/* 103 */       SearchResults searchResults = this.m_assetManager.getPromotedAssets(a_dbTransaction, iPageIndex, iPageSize, userprofile.getSelectedFilters(), userprofile);
/*     */ 
/* 109 */       userprofile.setInAssetBoxFlagOnAll(searchResults.getSearchResults());
/*     */ 
/* 111 */       form.setSearchResults(searchResults);
/*     */     }
/*     */     catch (Bn2Exception bn2)
/*     */     {
/* 115 */       this.m_logger.error("Exception in ViewPromotedAssetsAction: " + bn2.getMessage());
/* 116 */       throw bn2;
/*     */     }
/*     */ 
/* 119 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewPromotedAssetsAction
 * JD-Core Version:    0.6.0
 */