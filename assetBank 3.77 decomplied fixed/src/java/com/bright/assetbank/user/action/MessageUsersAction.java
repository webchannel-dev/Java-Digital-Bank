/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.MessageUsersForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class MessageUsersAction extends UserAction
/*     */ {
/*  55 */   private EmailManager m_emailManager = null;
/*     */ 
/*     */   public void setEmailManager(EmailManager a_emailManager)
/*     */   {
/*  59 */     this.m_emailManager = a_emailManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  86 */     MessageUsersForm form = (MessageUsersForm)a_form;
/*  87 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  90 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  92 */       this.m_logger.error("AddUserAction.execute : User does not have admin permission : " + userProfile);
/*  93 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  97 */     Vector vecGroupIds = getGroupIds(a_request);
/*  98 */     form.setGroupIds(vecGroupIds);
/*  99 */     Vector vecGroups = new Vector();
/* 100 */     for (int i = 0; i < vecGroupIds.size(); i++)
/*     */     {
/* 102 */       StringDataBean idBean = new StringDataBean();
/* 103 */       idBean.setId(((Long)vecGroupIds.get(i)).longValue());
/* 104 */       vecGroups.add(idBean);
/*     */     }
/*     */ 
/* 107 */     validateMandatoryFields(form, a_request);
/*     */ 
/* 109 */     if (form.getHasErrors())
/*     */     {
/* 111 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 114 */     Collection addresses = getUserManager().getUserEmailsForGroups(vecGroups).values();
/* 115 */     List vecAddresses = new Vector();
/* 116 */     Iterator it = addresses.iterator();
/* 117 */     for (int i = 0; i < addresses.size(); i++)
/*     */     {
/* 119 */       vecAddresses.add(it.next());
/*     */     }
/*     */ 
/* 123 */     String sUsers = form.getToUser();
/*     */ 
/* 125 */     if (StringUtil.stringIsPopulated(sUsers))
/*     */     {
/* 129 */       String[] saUsers = sUsers.split(",");
/*     */ 
/* 132 */       for (int i = 0; i < saUsers.length; i++)
/*     */       {
/* 134 */         long lUserId = getUserManager().getUserIdForLocalUsername(a_dbTransaction, saUsers[i]);
/*     */ 
/* 136 */         if (lUserId <= 0L)
/*     */           continue;
/* 138 */         ABUser managedUser = (ABUser)getUserManager().getUser(a_dbTransaction, lUserId);
/*     */ 
/* 140 */         if (managedUser == null)
/*     */         {
/*     */           continue;
/*     */         }
/* 144 */         String sUserEmailAddresss = managedUser.getEmailAddress();
/*     */ 
/* 147 */         vecAddresses.add(sUserEmailAddresss);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 154 */     if (vecAddresses.size() <= 0)
/*     */     {
/* 156 */       form.addError("No valid email addresses were found to send email to");
/* 157 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 161 */     Map hmParams = new HashMap();
/* 162 */     hmParams.put("template", "user_message");
/* 163 */     hmParams.put("subject", form.getMessageSubject());
/* 164 */     hmParams.put("body", form.getMessageBody());
/*     */ 
/* 167 */     for (int i = 0; i < vecAddresses.size(); i++)
/*     */     {
/* 169 */       String sAddress = (String)vecAddresses.get(i);
/* 170 */       hmParams.put("email", sAddress);
/*     */ 
/* 175 */       this.m_emailManager.sendTemplatedEmail(hmParams, null, true, userProfile.getCurrentLanguage());
/*     */     }
/*     */ 
/* 179 */     this.m_emailManager.processQueueAsynchronously();
/*     */ 
/* 182 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.MessageUsersAction
 * JD-Core Version:    0.6.0
 */