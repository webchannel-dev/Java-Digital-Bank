/*    */ package com.bright.assetbank.category.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.category.form.GenerateJSForm;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.category.bean.FlatCategoryList;
/*    */ import com.bright.framework.category.service.CategoryManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class GenerateJSAction extends BTransactionAction
/*    */ {
/* 48 */   private CategoryManager m_categoryManager = null;
/*    */ 
/*    */   public void setCategoryManager(CategoryManager a_categoryManager) {
/* 51 */     this.m_categoryManager = a_categoryManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 73 */     GenerateJSForm form = (GenerateJSForm)a_form;
/* 74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 76 */     Vector vDescCats = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, 1L, userProfile.getCurrentLanguage()).getCategories();
/* 77 */     Vector vPermCats = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, 2L, userProfile.getCurrentLanguage()).getCategories();
/*    */ 
/* 79 */     form.setDescriptiveCategories(vDescCats);
/* 80 */     form.setPermissionCategories(vPermCats);
/*    */ 
/* 82 */     a_response.setContentType("text/javascript");
/*    */ 
/* 85 */     long lOneYearsTime = System.currentTimeMillis() + 1384828928L;
/* 86 */     a_response.setHeader("Content-Type", "text/javascript");
/* 87 */     a_response.setDateHeader("Last-Modified", lOneYearsTime);
/* 88 */     a_response.setHeader("Cache-Control", "public");
/* 89 */     a_response.setDateHeader("Expires", lOneYearsTime);
/* 90 */     a_response.setHeader("Pragma", "");
/*    */ 
/* 92 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public boolean getAvailableNotLoggedIn()
/*    */   {
/* 97 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.action.GenerateJSAction
 * JD-Core Version:    0.6.0
 */