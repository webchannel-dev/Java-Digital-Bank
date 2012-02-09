/*     */ package com.bright.framework.category.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AlphabetiseSubCategoriesAction extends BTransactionAction
/*     */   implements CategoryConstants
/*     */ {
/*  43 */   private CategoryManager m_categoryManager = null;
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_categoryManager) {
/*  46 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  72 */     ActionForward forward = null;
/*     */ 
/*  75 */     long lParentCatId = getLongParameter(a_request, "id");
/*  76 */     long lTypeId = getLongParameter(a_request, "categoryTypeId");
/*  77 */     int iRoot = getIntParameter(a_request, "root");
/*  78 */     int iOrgUnitId = getIntParameter(a_request, "ouid");
/*     */ 
/*  80 */     boolean bRoot = false;
/*     */ 
/*  82 */     if (iRoot > 0)
/*     */     {
/*  84 */       bRoot = true;
/*     */     }
/*     */ 
/*  87 */     this.m_categoryManager.alphabetiseSubCategories(a_dbTransaction, lTypeId, lParentCatId, bRoot);
/*     */ 
/*  90 */     a_request.setAttribute("categoryId", new Long(lParentCatId));
/*     */ 
/*  94 */     if (lTypeId == 2L)
/*     */     {
/*  96 */       forward = createRedirectingForward("ouid=" + iOrgUnitId, a_mapping, "AccessLevel");
/*     */     }
/*     */     else
/*     */     {
/* 100 */       forward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 103 */     return forward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.action.AlphabetiseSubCategoriesAction
 * JD-Core Version:    0.6.0
 */