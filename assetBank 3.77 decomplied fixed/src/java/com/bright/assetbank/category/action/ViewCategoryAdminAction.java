/*     */ package com.bright.assetbank.category.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.category.util.CategoryUtil;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.form.CategoryAdminForm;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewCategoryAdminAction extends com.bright.framework.category.action.ViewCategoryAdminAction
/*     */   implements AssetBankConstants
/*     */ {
/*  49 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     ActionForward afForward = null;
/*  72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  73 */     CategoryAdminForm form = (CategoryAdminForm)a_form;
/*     */ 
/*  76 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  78 */       this.m_logger.error("ViewCategoryAdminAction.execute : User does not have admin permission : " + userProfile);
/*  79 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  83 */     long lCatId = getLongParameter(a_request, "categoryId");
/*     */ 
/*  86 */     if (lCatId <= 0L)
/*     */     {
/*  88 */       if (a_request.getAttribute("categoryId") != null)
/*     */       {
/*  90 */         lCatId = ((Long)a_request.getAttribute("categoryId")).longValue();
/*     */       }
/*     */     }
/*     */ 
/*  94 */     CategoryUtil.checkForCategoryExtensionError(a_request, form);
/*  95 */     super.getCategories(form, a_dbTransaction, 1L, lCatId, null);
/*     */ 
/*  97 */     if ((!form.getHasErrors()) && (AssetBankSettings.getSupportMultiLanguage()))
/*     */     {
/* 100 */       this.m_languageManager.createEmptyTranslations(a_dbTransaction, form.getNewCategory());
/*     */     }
/*     */ 
/* 103 */     afForward = a_mapping.findForward("Success");
/* 104 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 109 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.action.ViewCategoryAdminAction
 * JD-Core Version:    0.6.0
 */