/*    */ package com.bright.assetbank.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.bean.ABUser;
/*    */ import com.bright.assetbank.user.form.UserForm;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.mail.service.EmailManager;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import java.util.HashMap;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class UpdateUserRequestAction extends UpdateUserAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 51 */     ActionForward afForward = null;
/* 52 */     UserForm form = (UserForm)a_form;
/*    */ 
/* 55 */     afForward = super.execute(a_mapping, a_form, a_request, a_response, a_dbTransaction);
/*    */ 
/* 58 */     ABUser user = form.getUser();
/*    */ 
/* 61 */     HashMap hmParams = new HashMap();
/*    */ 
/* 63 */     String sName = user.getFullName();
/* 64 */     if (!StringUtil.stringIsPopulated(sName))
/*    */     {
/* 66 */       sName = user.getUsername();
/*    */     }
/*    */ 
/* 69 */     hmParams.put("email", user.getEmailAddress());
/* 70 */     hmParams.put("name", sName);
/* 71 */     hmParams.put("adminMessage", form.getMessage());
/* 72 */     hmParams.put("template", "user_updated");
/*    */ 
/* 75 */     if (!form.getHasErrors())
/*    */     {
/*    */       try
/*    */       {
/* 79 */         this.m_emailManager.sendTemplatedEmail(hmParams, user.getLanguage());
/*    */       }
/*    */       catch (Bn2Exception bn2e)
/*    */       {
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 88 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.UpdateUserRequestAction
 * JD-Core Version:    0.6.0
 */