/*     */ package com.bright.framework.mail.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import org.apache.commons.mail.HtmlEmail;
/*     */ 
/*     */ public class SendEmailForm extends BasicEmailForm
/*     */ {
/*  35 */   private HtmlEmail m_email = null;
/*  36 */   private String m_sToAddress = null;
/*  37 */   private String m_sSubject = null;
/*  38 */   private String m_sBody = null;
/*  39 */   private String m_sFromAddress = null;
/*  40 */   private String m_sRedirectUrl = null;
/*  41 */   private String m_sBackUrl = null;
/*     */ 
/*     */   public void setEmail(HtmlEmail a_email)
/*     */   {
/*  45 */     this.m_email = a_email;
/*     */   }
/*     */ 
/*     */   public HtmlEmail getEmail()
/*     */   {
/*  50 */     return this.m_email;
/*     */   }
/*     */ 
/*     */   public String getToAddress()
/*     */   {
/*  57 */     return this.m_sToAddress;
/*     */   }
/*     */ 
/*     */   public void setToAddress(String a_sToAddress)
/*     */   {
/*  63 */     this.m_sToAddress = a_sToAddress;
/*     */   }
/*     */ 
/*     */   public String getSubject()
/*     */   {
/*  69 */     return this.m_sSubject;
/*     */   }
/*     */ 
/*     */   public void setSubject(String a_sSubject)
/*     */   {
/*  75 */     this.m_sSubject = a_sSubject;
/*     */   }
/*     */ 
/*     */   public String getBody()
/*     */   {
/*  80 */     return this.m_sBody;
/*     */   }
/*     */ 
/*     */   public void setBody(String body)
/*     */   {
/*  86 */     this.m_sBody = body;
/*     */   }
/*     */ 
/*     */   public String getFromAddress()
/*     */   {
/*  92 */     return this.m_sFromAddress;
/*     */   }
/*     */ 
/*     */   public void setFromAddress(String fromAddress)
/*     */   {
/*  98 */     this.m_sFromAddress = fromAddress;
/*     */   }
/*     */ 
/*     */   public String getRedirectUrl()
/*     */   {
/* 104 */     return this.m_sRedirectUrl;
/*     */   }
/*     */ 
/*     */   public void setRedirectUrl(String redirectUrl)
/*     */   {
/* 110 */     this.m_sRedirectUrl = redirectUrl;
/*     */   }
/*     */ 
/*     */   public String getBackUrl()
/*     */   {
/* 116 */     return this.m_sBackUrl;
/*     */   }
/*     */ 
/*     */   public void setBackUrl(String backUrl)
/*     */   {
/* 122 */     this.m_sBackUrl = backUrl;
/*     */   }
/*     */ 
/*     */   public void validate(DBTransaction a_dbTransaction, ListManager a_listManager, UserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 129 */     if (!StringUtil.isValidEmailAddress(getToAddress()))
/*     */     {
/* 131 */       addError(a_listManager.getListItem(a_dbTransaction, "failedValidationEmailAddress", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.form.SendEmailForm
 * JD-Core Version:    0.6.0
 */