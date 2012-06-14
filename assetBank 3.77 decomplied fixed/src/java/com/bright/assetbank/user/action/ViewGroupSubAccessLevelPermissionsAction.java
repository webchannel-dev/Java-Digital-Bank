/*    */ package com.bright.assetbank.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.bean.Group;
/*    */ import com.bright.assetbank.user.form.GroupForm;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
/*    */ import com.bright.framework.category.bean.Category;
/*    */ import com.bright.framework.category.service.CategoryManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewGroupSubAccessLevelPermissionsAction extends BTransactionAction
/*    */ {
/* 46 */   private CategoryManager m_categoryManager = null;
/* 47 */   private ABUserManager m_userManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 57 */     GroupForm form = (GroupForm)a_form;
/*    */ 
/* 59 */     long lCategoryId = getLongParameter(a_request, "categoryId");
/* 60 */     long lGroupId = getLongParameter(a_request, "id");
/*    */ 
/* 63 */     form.setAccessLevelsExpandable(true);
/*    */ 
/* 65 */     if (lCategoryId > 0L)
/*    */     {
/* 67 */       Group group = this.m_userManager.getGroup(lGroupId);
/* 68 */       form.setGroup(group);
/*    */ 
/* 71 */       if (form.getSelectedPermissions() == null)
/*    */       {
/* 73 */         form.setSelectedPermissions(group.getCategoryPermissions());
/*    */       }
/*    */ 
/* 76 */       boolean bOpen = Boolean.parseBoolean(a_request.getParameter("open"));
/*    */ 
/* 79 */       if (bOpen)
/*    */       {
/* 81 */         Category cat = this.m_categoryManager.getCategory(a_transaction, 2L, lCategoryId);
/* 82 */         a_request.setAttribute("subAccessLevels", cat.getChildCategories());
/*    */       }
/*    */     }
/*    */ 
/* 86 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setCategoryManager(CategoryManager a_manager)
/*    */   {
/* 91 */     this.m_categoryManager = a_manager;
/*    */   }
/*    */ 
/*    */   public void setUserManager(ABUserManager a_manager)
/*    */   {
/* 96 */     this.m_userManager = a_manager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewGroupSubAccessLevelPermissionsAction
 * JD-Core Version:    0.6.0
 */