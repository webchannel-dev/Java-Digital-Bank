/*    */ package com.bright.assetbank.autocomplete.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.autocomplete.form.ACReindexStatusForm;
/*    */ import com.bright.assetbank.autocomplete.service.AutoCompleteUpdateManager;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewACReindexStatusAction extends Bn2Action
/*    */ {
/*    */   public static final String c_ksClassName = "ViewACReindexStatusAction";
/*    */   private AutoCompleteUpdateManager m_autoCompleteUpdateManager;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 49 */     ACReindexStatusForm statusForm = (ACReindexStatusForm)a_form;
/* 50 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 53 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 55 */       this.m_logger.error("ViewACReindexStatusAction : User does not have admin permission : " + userProfile);
/* 56 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 60 */     if (this.m_autoCompleteUpdateManager.getMessages(userProfile.getUser().getId()) != null)
/*    */     {
/* 62 */       statusForm.setMessages(this.m_autoCompleteUpdateManager.getMessages(userProfile.getUser().getId()));
/*    */     }
/*    */     else
/*    */     {
/* 66 */       statusForm.setMessages(null);
/*    */     }
/*    */ 
/* 70 */     if (this.m_autoCompleteUpdateManager.isBatchInProgress(userProfile.getUser().getId()))
/*    */     {
/* 72 */       statusForm.setReindexInProgress(true);
/*    */     }
/*    */     else
/*    */     {
/* 76 */       statusForm.setReindexInProgress(false);
/*    */     }
/*    */ 
/* 81 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAutoCompleteUpdateManager(AutoCompleteUpdateManager a_autoCompleteUpdateManager)
/*    */   {
/* 86 */     this.m_autoCompleteUpdateManager = a_autoCompleteUpdateManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.autocomplete.action.ViewACReindexStatusAction
 * JD-Core Version:    0.6.0
 */