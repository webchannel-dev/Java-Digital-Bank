/*     */ package com.bright.assetbank.category.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.category.form.CategoryAdminForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.service.CategoryManager;
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
/*     */ public class AddCategoryAction extends AddExtendedCategoryAction
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  67 */     ActionForward afForward = null;
/*  68 */     CategoryAdminForm form = (CategoryAdminForm)a_form;
/*     */ 
/*  70 */     long lParentCatId = getLongParameter(a_request, "categoryId");
/*  71 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  73 */     List ancestors = null;
/*     */ 
/*  76 */     if (lParentCatId != -1L)
/*     */     {
/*  78 */       ancestors = getCategoryManager().getAncestors(a_dbTransaction, 1L, lParentCatId);
/*     */     }
/*     */ 
/*  82 */     if ((!userProfile.getIsLoggedIn()) || (!userProfile.getCanEditSubcategories((int)lParentCatId, ancestors)))
/*     */     {
/*  84 */       this.m_logger.error("AddCategoryAction.execute : User does not have admin permission : " + userProfile);
/*  85 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  89 */     Category newCategory = form.getNewCategory();
/*  90 */     boolean bValid = saveCategory(a_dbTransaction, a_request, newCategory, form, userProfile, lParentCatId, 1L);
/*     */ 
/*  94 */     if (!bValid)
/*     */     {
/*  96 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/* 100 */       String sQueryString = "";
/*     */ 
/* 103 */       if (!form.getExtendedCategory())
/*     */       {
/* 105 */         if (lParentCatId > 0L)
/*     */         {
/* 107 */           sQueryString = sQueryString + "categoryId=" + lParentCatId;
/*     */         }
/* 109 */         afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */       }
/*     */       else
/*     */       {
/* 113 */         afForward = getExtendedCategoryForward(a_dbTransaction, a_request, a_mapping, newCategory, sQueryString, lParentCatId);
/*     */       }
/*     */     }
/*     */ 
/* 117 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.action.AddCategoryAction
 * JD-Core Version:    0.6.0
 */