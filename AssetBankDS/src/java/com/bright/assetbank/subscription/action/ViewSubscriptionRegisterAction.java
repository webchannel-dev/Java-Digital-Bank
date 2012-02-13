/*    */ package com.bright.assetbank.subscription.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.language.service.LanguageManager;
/*    */ import com.bright.assetbank.subscription.constant.SubscriptionConstants;
/*    */ import com.bright.assetbank.subscription.form.SubscriptionForm;
/*    */ import com.bright.assetbank.user.action.UserAction;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewSubscriptionRegisterAction extends UserAction
/*    */   implements SubscriptionConstants
/*    */ {
/* 45 */   private LanguageManager m_languageManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 68 */     ActionForward afForward = null;
/* 69 */     SubscriptionForm form = (SubscriptionForm)a_form;
/*    */ 
/* 72 */     String sStart = a_request.getParameter("start");
/* 73 */     if (StringUtil.stringIsPopulated(sStart))
/*    */     {
/* 75 */       form.resetAll();
/*    */     }
/*    */ 
/* 79 */     Vector vecDivisions = getUserManager().getAllDivisions(a_dbTransaction);
/* 80 */     form.setDivisionList(vecDivisions);
/*    */ 
/* 83 */     form.setLanguages(this.m_languageManager.getLanguages(a_dbTransaction, true, false));
/*    */ 
/* 85 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 87 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setLanguageManager(LanguageManager languageManager)
/*    */   {
/* 92 */     this.m_languageManager = languageManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.action.ViewSubscriptionRegisterAction
 * JD-Core Version:    0.6.0
 */