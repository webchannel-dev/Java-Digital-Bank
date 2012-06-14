/*    */ package com.bright.assetbank.ecommerce.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.ecommerce.constant.EcommerceConstants;
/*    */ import com.bright.assetbank.user.action.ViewRegisterAction;
/*    */ import com.bright.assetbank.user.bean.ABUser;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.user.form.RegisterForm;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewOfflineRegisterAction extends ViewRegisterAction
/*    */   implements EcommerceConstants
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 47 */     ActionForward afForward = null;
/* 48 */     RegisterForm form = (RegisterForm)a_form;
/* 49 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 52 */     form.getUser().setEmailAddress(userProfile.getRegisterEmailAddress());
/*    */ 
/* 54 */     afForward = super.execute(a_mapping, form, a_request, a_response, a_dbTransaction);
/*    */ 
/* 56 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.ViewOfflineRegisterAction
 * JD-Core Version:    0.6.0
 */