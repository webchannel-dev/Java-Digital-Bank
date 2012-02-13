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
/*     */ public class ViewRecentAssetsAction extends BTransactionAction
/*     */ {
/*  49 */   protected IAssetManager m_assetManager = null;
/*     */ 
/*     */   public final ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  68 */     this.m_logger.debug("In ViewRecentAssetsAction.execute");
/*  69 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  70 */     BrowseItemsForm form = (BrowseItemsForm)a_form;
/*     */ 
/*  73 */     int iPageIndex = getIntParameter(a_request, "page");
/*     */ 
/*  76 */     if (iPageIndex < 0)
/*     */     {
/*  78 */       iPageIndex = 0;
/*     */     }
/*     */ 
/*  82 */     int iPageSize = getIntParameter(a_request, "pageSize");
/*     */ 
/*  85 */     if (iPageSize <= 0)
/*     */     {
/*  87 */       iPageSize = AssetBankSettings.getDefaultNumImagesPerBrowsePage();
/*     */     }
/*     */ 
/*  91 */     SearchResults srRecentAssets = this.m_assetManager.getRecentAssets(a_dbTransaction, AssetBankSettings.getNumberMoreRecentAssets(), userProfile, iPageIndex, iPageSize, false);
/*     */ 
/* 100 */     userProfile.setInAssetBoxFlagOnAll(srRecentAssets.getSearchResults());
/*     */ 
/* 102 */     form.setSearchResults(srRecentAssets);
/*     */ 
/* 104 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 110 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewRecentAssetsAction
 * JD-Core Version:    0.6.0
 */