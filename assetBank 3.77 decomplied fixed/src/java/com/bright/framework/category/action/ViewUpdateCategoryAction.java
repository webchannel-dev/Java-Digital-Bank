/*    */ package com.bright.framework.category.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.category.bean.Category;
/*    */ import com.bright.framework.category.constant.CategoryConstants;
/*    */ import com.bright.framework.category.form.CategoryForm;
/*    */ import com.bright.framework.category.service.CategoryManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.simplelist.service.ListManager;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public abstract class ViewUpdateCategoryAction extends BTransactionAction
/*    */   implements CategoryConstants
/*    */ {
/* 46 */   private CategoryManager m_categoryManager = null;
/*    */ 
/* 52 */   protected ListManager m_listManager = null;
/*    */ 
/*    */   public void setCategoryManager(CategoryManager a_categoryManager)
/*    */   {
/* 49 */     this.m_categoryManager = a_categoryManager;
/*    */   }
/*    */ 
/*    */   public void setListManager(ListManager listManager)
/*    */   {
/* 55 */     this.m_listManager = listManager;
/*    */   }
/*    */ 
/*    */   public abstract ActionForward execute(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, DBTransaction paramDBTransaction)
/*    */     throws Bn2Exception;
/*    */ 
/*    */   public void getCategory(CategoryForm categoryForm, DBTransaction a_dbTransaction, long a_lCatTreeId, long a_lCatId)
/*    */     throws Bn2Exception
/*    */   {
/* 80 */     Category cat = this.m_categoryManager.getCategory(a_dbTransaction, a_lCatTreeId, a_lCatId);
/*    */ 
/* 83 */     cat = cat.clone();
/*    */ 
/* 86 */     categoryForm.setAncestorCategoryList(this.m_categoryManager.getAncestors(a_dbTransaction, a_lCatTreeId, a_lCatId));
/*    */ 
/* 88 */     categoryForm.setCategory(cat);
/* 89 */     categoryForm.setCategoryTypeName(this.m_categoryManager.getCategoryTypeName(a_lCatTreeId));
/* 90 */     categoryForm.setBrowsable(cat.getIsBrowsable());
/* 91 */     categoryForm.setIsRestrictive(cat.getIsRestrictive());
/* 92 */     categoryForm.setParentId(cat.getParentId());
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.action.ViewUpdateCategoryAction
 * JD-Core Version:    0.6.0
 */