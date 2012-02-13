/*    */ package com.bright.framework.category.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.category.constant.CategoryConstants;
/*    */ import com.bright.framework.category.form.CategoriesForm;
/*    */ import com.bright.framework.category.service.CategoryManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewAlphabeticCategoryListAction extends BTransactionAction
/*    */   implements CategoryConstants
/*    */ {
/* 42 */   protected CategoryManager m_categoryManager = null;
/*    */ 
/*    */   public void setCategoryManager(CategoryManager a_categoryManager) {
/* 45 */     this.m_categoryManager = a_categoryManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 70 */     CategoriesForm form = (CategoriesForm)a_form;
/*    */ 
/* 73 */     form.setCategories(this.m_categoryManager.getAlphabeticFlatCategoryList(a_dbTransaction, getCategoryTreeId()));
/*    */ 
/* 75 */     ActionForward afForward = a_mapping.findForward("Success");
/* 76 */     return afForward;
/*    */   }
/*    */ 
/*    */   public long getCategoryTreeId()
/*    */   {
/* 82 */     return -1L;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.action.ViewAlphabeticCategoryListAction
 * JD-Core Version:    0.6.0
 */