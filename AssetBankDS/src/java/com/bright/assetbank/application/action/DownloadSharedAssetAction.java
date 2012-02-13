/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.form.DownloadSharedAssetForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.service.UserManager;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.RC4CipherUtil;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class DownloadSharedAssetAction extends Bn2Action
/*     */   implements AssetBankConstants, FrameworkConstants
/*     */ {
/*     */   private EmailManager m_emailManager;
/*     */   private UserManager m_userManager;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  66 */     String sConditionsAccepted = a_request.getParameter("conditionsAccepted");
/*     */ 
/*  68 */     if ((sConditionsAccepted == null) || (sConditionsAccepted.length() == 0)) {
/*  69 */       DownloadSharedAssetForm form = (DownloadSharedAssetForm)a_form;
/*  70 */       form.setConditionsAccepted(false);
/*  71 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  74 */     ActionForward afForward = a_mapping.findForward("Success");
/*     */ 
/*  76 */     a_request.setAttribute("downloadFile", a_request.getParameter("downloadFile"));
/*     */ 
/*  80 */     String sEncryptedNotify = a_request.getParameter("notify");
/*  81 */     if (StringUtils.isNotEmpty(sEncryptedNotify))
/*     */     {
/*  83 */       ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  85 */       HashMap params = new HashMap();
/*     */ 
/*  88 */       if (userProfile.getIsLoggedIn())
/*     */       {
/*  90 */         params.put("template", "file_transfer_notification_loggedin");
/*  91 */         params.put("username", userProfile.getUser().getUsername());
/*     */       }
/*     */       else
/*     */       {
/*  95 */         params.put("template", "file_transfer_notification_loggedout");
/*     */       }
/*     */ 
/*  99 */       String sFileLocation = (String)a_request.getAttribute("downloadFile");
/* 100 */       sFileLocation = FileUtil.decryptFilepath(sFileLocation);
/* 101 */       String sFilename = FileUtil.getFilename(sFileLocation);
/*     */ 
/* 104 */       String sNotify = RC4CipherUtil.decryptFromHex(sEncryptedNotify);
/* 105 */       String[] userIdToNotifyAndOriginalRecipients = sNotify.split(":", 2);
/* 106 */       String sUserIdToNotify = userIdToNotifyAndOriginalRecipients[0];
/* 107 */       String sOriginalRecipients = userIdToNotifyAndOriginalRecipients[1];
/*     */ 
/* 109 */       long lUserIdToNotify = Long.parseLong(sUserIdToNotify);
/* 110 */       User userToNotify = this.m_userManager.getUser(null, lUserIdToNotify);
/*     */ 
/* 113 */       params.put("recipients", userToNotify.getEmailAddress());
/* 114 */       params.put("filename", sFilename);
/* 115 */       params.put("original_recipients", sOriginalRecipients);
/*     */ 
/* 118 */       this.m_emailManager.sendTemplatedEmail(params, false, userProfile.getCurrentLanguage());
/*     */     }
/*     */ 
/* 121 */     return afForward;
/*     */   }
/*     */ 
/*     */   public boolean getAvailableNotLoggedIn()
/*     */   {
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */   public EmailManager getEmailManager()
/*     */   {
/* 131 */     return this.m_emailManager;
/*     */   }
/*     */ 
/*     */   public void setEmailManager(EmailManager a_emailManager)
/*     */   {
/* 136 */     this.m_emailManager = a_emailManager;
/*     */   }
/*     */ 
/*     */   public UserManager getUserManager()
/*     */   {
/* 141 */     return this.m_userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(UserManager a_userManager)
/*     */   {
/* 146 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.DownloadSharedAssetAction
 * JD-Core Version:    0.6.0
 */