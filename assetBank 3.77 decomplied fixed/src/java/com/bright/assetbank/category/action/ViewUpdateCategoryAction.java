/*    */ package com.bright.assetbank.category.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.category.form.ABCategoryForm;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewUpdateCategoryAction extends com.bright.framework.category.action.ViewUpdateCategoryAction
/*    */   implements AssetBankConstants
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 67 */     ActionForward afForward = null;
/* 68 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 71 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 73 */       this.m_logger.error("ViewUpdateCategoryAction.execute : User does not have admin permission : " + userProfile);
/* 74 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 77 */     ABCategoryForm form = (ABCategoryForm)a_form;
/*    */ 
/* 79 */     if (!form.getHasErrors())
/*    */     {
/* 81 */       long lCatId = getLongParameter(a_request, "categoryId");
/* 82 */       super.getCategory(form, a_dbTransaction, 1L, lCatId);
/*    */     }
/*    */ 
/* 85 */     afForward = a_mapping.findForward("Success");
/* 86 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.action.ViewUpdateCategoryAction
 * JD-Core Version:    0.6.0
 */