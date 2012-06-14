/*    */ package com.bright.framework.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.language.constant.LanguageConstants;
/*    */ import com.bright.framework.simplelist.service.ListManager;
/*    */ import com.bright.framework.user.form.LoginForm;
/*    */ import com.bright.framework.user.service.OpenIdManager;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.axis.utils.StringUtils;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class LoginWithOpenIdAction extends LoginAction
/*    */ {
/* 42 */   private ListManager m_listManager = null;
/* 43 */   private OpenIdManager m_openIdManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 52 */     ActionForward afForward = null;
/*    */ 
/* 54 */     LoginForm form = (LoginForm)a_form;
/*    */ 
/* 56 */     String sPrefix = this.m_listManager.getListItem(a_dbTransaction, "technical-open-id-prefix", LanguageConstants.k_defaultLanguage, null);
/*    */ 
/* 58 */     if (StringUtils.isEmpty(form.getUsername()))
/*    */     {
/* 60 */       form.setLoginFailed(true);
/* 61 */       afForward = a_mapping.findForward("Failure");
/*    */     }
/*    */     else
/*    */     {
/* 65 */       String sUrl = this.m_openIdManager.getAuthenticationUrl(sPrefix + form.getUsername(), a_request);
/*    */ 
/* 67 */       if (sUrl != null)
/*    */       {
/* 70 */         afForward = createRedirectingForward(sUrl);
/*    */       }
/*    */       else
/*    */       {
/* 74 */         form.setLoginFailed(true);
/* 75 */         afForward = a_mapping.findForward("Failure");
/*    */       }
/*    */     }
/*    */ 
/* 79 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setListManager(ListManager a_manager)
/*    */   {
/* 84 */     this.m_listManager = a_manager;
/*    */   }
/*    */ 
/*    */   public void setOpenIdManager(OpenIdManager a_openIdManager)
/*    */   {
/* 89 */     this.m_openIdManager = a_openIdManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.action.LoginWithOpenIdAction
 * JD-Core Version:    0.6.0
 */