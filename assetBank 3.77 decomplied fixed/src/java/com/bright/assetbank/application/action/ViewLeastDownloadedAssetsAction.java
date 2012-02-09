/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.category.form.BrowseItemsForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewLeastDownloadedAssetsAction extends Bn2Action
/*     */ {
/*  46 */   protected IAssetManager m_assetManager = null;
/*     */ 
/*     */   public final ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  64 */     this.m_logger.debug("In ViewLeastDownloadedAssetsAction.execute");
/*  65 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  66 */     BrowseItemsForm form = (BrowseItemsForm)a_form;
/*     */ 
/*  69 */     int iPageIndex = getIntParameter(a_request, "page");
/*     */ 
/*  72 */     if (iPageIndex < 0)
/*     */     {
/*  74 */       iPageIndex = 0;
/*     */     }
/*     */ 
/*  78 */     int iPageSize = getIntParameter(a_request, "pageSize");
/*     */ 
/*  81 */     if (iPageSize <= 0)
/*     */     {
/*  83 */       iPageSize = AssetBankSettings.getDefaultNumImagesPerBrowsePage();
/*     */     }
/*     */ 
/*  87 */     SearchResults srAssets = this.m_assetManager.getAssetsByDownloads(AssetBankSettings.getNumberLeastPopularAssets(), userProfile, iPageIndex, iPageSize, userProfile.getCurrentLanguage().getCode(), false);
/*     */ 
/*  91 */     form.setSearchResults(srAssets);
/*     */ 
/*  94 */     userProfile.setInAssetBoxFlagOnAll(srAssets.getSearchResults());
/*     */ 
/*  96 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 102 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewLeastDownloadedAssetsAction
 * JD-Core Version:    0.6.0
 */