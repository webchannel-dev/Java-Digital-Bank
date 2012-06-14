/*     */ package com.bright.framework.mail.util;
/*     */ 
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.mail.bean.EmailContent;
/*     */ import com.bright.framework.mail.bean.EmailContent.Translation;
/*     */ import com.bright.framework.mail.bean.EmailTemplate;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.mail.MessagingException;
/*     */ import org.apache.commons.mail.EmailAttachment;
/*     */ import org.apache.commons.mail.HtmlEmail;
/*     */ 
/*     */ public class MailUtils
/*     */ {
/*     */   public static void populateEmailFromTemplates(EmailContent a_email, Vector a_templates)
/*     */   {
/*  51 */     Iterator itTemplates = a_templates.iterator();
/*  52 */     while (itTemplates.hasNext())
/*     */     {
/*  54 */       EmailTemplate template = (EmailTemplate)itTemplates.next();
/*  55 */       if (LanguageConstants.k_defaultLanguage.equals(template.getLanguage()))
/*     */       {
/*  57 */         a_email.setSubject(template.getAddrSubject());
/*  58 */         a_email.setBody(template.getAddrBody());
/*     */       }
/*     */       else
/*     */       {
/*  62 */         EmailContent.Translation translation = (EmailContent.Translation)LanguageUtils.getTranslation(template.getLanguage(), a_email.getTranslations());
/*  63 */         translation.setSubject(template.getAddrSubject());
/*  64 */         translation.setBody(template.getAddrBody());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static HtmlEmail populateHtmlMessage(String a_sSubject, String a_sBody, String a_sFromAddress, String a_sToAddress, String a_sCCAddress, String a_sBCCAddress, EmailAttachment[] a_attachments)
/*     */     throws MessagingException
/*     */   {
/*  89 */     HtmlEmail message = new HtmlEmail();
/*     */ 
/*  91 */     message.setCharset("UTF-8");
/*  92 */     message.setHostName(FrameworkSettings.getEmailSMTP());
/*     */ 
/*  95 */     String sPort = FrameworkSettings.getEmailSMTPPort();
/*  96 */     if (StringUtil.stringIsInteger(sPort))
/*     */     {
/*  98 */       message.setPort(Integer.parseInt(sPort));
/*     */     }
/*     */ 
/* 102 */     String sUsername = FrameworkSettings.getEmailSMTPUsername();
/* 103 */     String sPassword = FrameworkSettings.getEmailSMTPPassword();
/* 104 */     if (StringUtil.stringIsPopulated(sUsername))
/*     */     {
/* 106 */       message.setAuthentication(sUsername, sPassword);
/*     */     }
/*     */ 
/* 110 */     message.setSentDate(new Date());
/*     */ 
/* 112 */     message.setSubject(a_sSubject);
/*     */ 
/* 114 */     message.setFrom(a_sFromAddress);
/*     */ 
/* 116 */     String[] sToAddresses = a_sToAddress.split(";");
/* 117 */     for (int x = 0; x < sToAddresses.length; x++)
/*     */     {
/* 119 */       message.addTo(sToAddresses[x]);
/*     */     }
/*     */ 
/* 123 */     if ((a_sCCAddress != null) && (a_sCCAddress.length() > 0))
/*     */     {
/* 125 */       String[] sCCAddresses = a_sCCAddress.split(";");
/* 126 */       for (int x = 0; x < sCCAddresses.length; x++)
/*     */       {
/* 128 */         message.addCc(sCCAddresses[x]);
/*     */       }
/*     */     }
/*     */ 
/* 132 */     if ((a_sBCCAddress != null) && (a_sBCCAddress.length() > 0))
/*     */     {
/* 134 */       String[] sBCCAddresses = a_sBCCAddress.split(";");
/* 135 */       for (int x = 0; x < sBCCAddresses.length; x++)
/*     */       {
/* 137 */         message.addBcc(sBCCAddresses[x]);
/*     */       }
/*     */     }
/*     */ 
/* 141 */     message.setHtmlMsg(a_sBody);
/*     */ 
/* 143 */     if (FrameworkSettings.getAddPlainTextToHtmlEmail())
/*     */     {
/* 145 */       message.setTextMsg(StringUtil.stripHTML(a_sBody));
/*     */     }
/*     */ 
/* 148 */     if (a_attachments != null)
/*     */     {
/* 150 */       for (int i = 0; i < a_attachments.length; i++)
/*     */       {
/* 152 */         message.attach(a_attachments[i]);
/*     */       }
/*     */     }
/*     */ 
/* 156 */     return message;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.util.MailUtils
 * JD-Core Version:    0.6.0
 */