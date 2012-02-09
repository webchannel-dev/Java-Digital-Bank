/*    */ package com.bright.assetbank.taxonomy.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.language.service.LanguageManager;
/*    */ import com.bright.framework.category.constant.CategoryConstants;
/*    */ import com.bright.framework.category.form.CategoryAdminForm;
/*    */ import com.bright.framework.category.service.CategoryManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewAddKeywordAction extends BTransactionAction
/*    */   implements CategoryConstants
/*    */ {
/* 44 */   private LanguageManager m_languageManager = null;
/* 45 */   private CategoryManager m_categoryManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 64 */     CategoryAdminForm catForm = (CategoryAdminForm)a_form;
/*    */ 
/* 66 */     long lTreeId = 0L;
/*    */ 
/* 68 */     if (a_request.getAttribute("categoryTypeId") != null)
/*    */     {
/* 70 */       lTreeId = ((Long)a_request.getAttribute("categoryTypeId")).longValue();
/*    */     }
/* 72 */     else if (a_request.getParameter("categoryTypeId") != null)
/*    */     {
/* 74 */       lTreeId = getLongParameter(a_request, "categoryTypeId");
/*    */     }
/*    */ 
/* 77 */     if (lTreeId > 0L)
/*    */     {
/* 79 */       catForm.setCategoryTreeId(lTreeId);
/* 80 */       catForm.setSubCategoryList(this.m_categoryManager.getCategories(a_dbTransaction, catForm.getCategoryTreeId(), -1L));
/*    */     }
/*    */ 
/* 83 */     if (AssetBankSettings.getSupportMultiLanguage())
/*    */     {
/* 85 */       this.m_languageManager.createEmptyTranslations(a_dbTransaction, catForm.getNewCategory());
/*    */     }
/*    */ 
/* 88 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setLanguageManager(LanguageManager languageManager)
/*    */   {
/* 93 */     this.m_languageManager = languageManager;
/*    */   }
/*    */ 
/*    */   public void setCategoryManager(CategoryManager a_manager)
/*    */   {
/* 98 */     this.m_categoryManager = a_manager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.action.ViewAddKeywordAction
 * JD-Core Version:    0.6.0
 */