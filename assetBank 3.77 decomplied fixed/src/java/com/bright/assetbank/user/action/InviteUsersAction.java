/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.InviteUsersForm;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.ServletUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class InviteUsersAction extends UserAction
/*     */ {
/*  50 */   private EmailManager m_emailManager = null;
/*     */ 
/*     */   public void setEmailManager(EmailManager a_emailManager)
/*     */   {
/*  54 */     this.m_emailManager = a_emailManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  79 */     InviteUsersForm form = (InviteUsersForm)a_form;
/*  80 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  83 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getUserCanInviteUsers()))
/*     */     {
/*  85 */       this.m_logger.error("InviteUsersAction.execute : User does not have permission : " + userProfile);
/*  86 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  89 */     validateMandatoryFields(form, a_request);
/*     */ 
/*  91 */     if (form.getHasErrors())
/*     */     {
/*  93 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  97 */     String sEmails = form.getTo();
/*     */ 
/*  99 */     Vector vecValidEmails = new Vector();
/*     */ 
/* 101 */     if (StringUtil.stringIsPopulated(sEmails))
/*     */     {
/* 105 */       String[] saEmails = sEmails.split(",");
/*     */ 
/* 108 */       for (int i = 0; i < saEmails.length; i++)
/*     */       {
/* 111 */         if (!StringUtil.isValidEmailAddress(saEmails[i]))
/*     */           continue;
/* 113 */         vecValidEmails.add(saEmails[i]);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 119 */     if (vecValidEmails.size() <= 0)
/*     */     {
/* 121 */       form.addError("No valid email addresses were found to send email to");
/* 122 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 126 */     String sUrl = ServletUtil.getApplicationUrl(a_request) + "/action/" + "viewRegisterUser" + "?" + "invitedBy" + "=" + userProfile.getUser().getId();
/*     */ 
/* 132 */     Map hmParams = new HashMap();
/* 133 */     hmParams.put("template", "invitation_to_register");
/* 134 */     hmParams.put("message", form.getMessageBody());
/* 135 */     hmParams.put("url", sUrl);
/*     */ 
/* 139 */     for (int i = 0; i < vecValidEmails.size(); i++)
/*     */     {
/* 141 */       String sAddress = (String)vecValidEmails.elementAt(i);
/* 142 */       hmParams.put("email", sAddress);
/*     */ 
/* 145 */       this.m_emailManager.sendTemplatedEmail(hmParams, null, true, userProfile.getCurrentLanguage());
/*     */     }
/*     */ 
/* 149 */     this.m_emailManager.processQueueAsynchronously();
/*     */ 
/* 151 */     form.setInvitesSent(true);
/* 152 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.InviteUsersAction
 * JD-Core Version:    0.6.0
 */