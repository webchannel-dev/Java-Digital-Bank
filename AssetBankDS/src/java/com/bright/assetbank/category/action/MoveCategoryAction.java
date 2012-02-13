/*     */ package com.bright.assetbank.category.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.category.service.CategoryCountCacheManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class MoveCategoryAction extends BTransactionAction
/*     */   implements AssetBankConstants, CategoryConstants
/*     */ {
/*  51 */   private CategoryManager m_categoryManager = null;
/*     */ 
/*  57 */   private CategoryCountCacheManager m_categoryCountCacheManager = null;
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_categoryManager)
/*     */   {
/*  54 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryCountCacheManager(CategoryCountCacheManager a_manager)
/*     */   {
/*  60 */     this.m_categoryCountCacheManager = a_manager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  82 */     ActionForward afForward = null;
/*  83 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  84 */     long lParentCatId = getLongParameter(a_request, "categoryId");
/*     */ 
/*  86 */     List ancestors = null;
/*     */ 
/*  89 */     if (lParentCatId != -1L)
/*     */     {
/*  91 */       ancestors = this.m_categoryManager.getAncestors(a_dbTransaction, 1L, lParentCatId);
/*     */     }
/*     */ 
/*  95 */     if ((!userProfile.getIsLoggedIn()) || (!userProfile.getCanEditSubcategories((int)lParentCatId, ancestors)))
/*     */     {
/*  97 */       this.m_logger.error("MoveCategoryAction.execute : User does not have admin permission : " + userProfile);
/*  98 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 101 */     long lCategoryId = getIntParameter(a_request, "catIdToMove");
/* 102 */     String sDirection = a_request.getParameter("direction");
/*     */ 
/* 105 */     this.m_categoryManager.moveCategoryInSequence(a_dbTransaction, 1L, lCategoryId, !"down".equalsIgnoreCase(sDirection));
/*     */ 
/* 110 */     this.m_categoryCountCacheManager.invalidateCache();
/*     */ 
/* 112 */     String sQueryString = "";
/* 113 */     if (lParentCatId > 0L)
/*     */     {
/* 115 */       sQueryString = sQueryString + "categoryId=" + lParentCatId;
/*     */     }
/* 117 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */ 
/* 119 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.action.MoveCategoryAction
 * JD-Core Version:    0.6.0
 */