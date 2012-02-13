/*    */ package com.bright.assetbank.taxonomy.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.category.action.ViewCategoryAdminAction;
/*    */ import com.bright.framework.category.form.CategoryAdminForm;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewKeywordsByHierarchy extends ViewCategoryAdminAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 48 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 50 */     long lCatId = getLongParameter(a_request, "categoryId");
/* 51 */     long lTreeId = getLongParameter(a_request, "categoryTypeId");
/*    */ 
/* 53 */     getCategories((CategoryAdminForm)a_form, a_dbTransaction, lTreeId, lCatId, userProfile.getCurrentLanguage());
/*    */ 
/* 55 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.action.ViewKeywordsByHierarchy
 * JD-Core Version:    0.6.0
 */