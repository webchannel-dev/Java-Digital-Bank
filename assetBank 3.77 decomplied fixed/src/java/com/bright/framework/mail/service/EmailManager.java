/*     */ package com.bright.framework.mail.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.common.service.ScheduleManager;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.mail.bean.DuplicateEmail;
/*     */ import com.bright.framework.mail.bean.DuplicateEmailKey;
/*     */ import com.bright.framework.mail.bean.EmailTemplate;
/*     */ import com.bright.framework.mail.constant.MailConstants;
/*     */ import com.bright.framework.mail.util.MailUtils;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TimerTask;
/*     */ import java.util.Vector;
/*     */ import javax.mail.AuthenticationFailedException;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.mail.NoSuchProviderException;
/*     */ import javax.mail.SendFailedException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.mail.EmailAttachment;
/*     */ import org.apache.commons.mail.HtmlEmail;
/*     */ 
/*     */ public class EmailManager extends Bn2Manager
/*     */   implements FrameworkConstants, MailConstants
/*     */ {
/*  95 */   private List<HtmlEmail> m_vecMailQueue = new Vector();
/*  96 */   private DBTransactionManager m_transactionManager = null;
/*  97 */   private EmailTemplateManager m_emailTemplateManager = null;
/*  98 */   private ScheduleManager m_scheduleManager = null;
/*  99 */   private ABUserManager m_userManager = null;
/* 100 */   private Map<DuplicateEmailKey, DuplicateEmail> m_hmDupeEmails = new HashMap();
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/* 106 */     super.startup();
/* 107 */     int iSyncPeriod = FrameworkSettings.getPotentiallyDuplicateEmailPeriod();
/*     */ 
/* 109 */     if (iSyncPeriod > 0)
/*     */     {
/* 112 */       TimerTask task = new TimerTask()
/*     */       {
/*     */         public void run()
/*     */         {
/*     */           try
/*     */           {
/* 118 */             EmailManager.this.processPotentiallyDuplicateEmails();
/*     */           }
/*     */           catch (Throwable e)
/*     */           {
/* 122 */             EmailManager.this.m_logger.error("processPotentiallyDuplicateEmails exception: " + e);
/*     */           }
/*     */         }
/*     */       };
/* 127 */       this.m_scheduleManager.schedule(task, 60000L, iSyncPeriod * 60L * 1000L, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void sendTemplatedEmail(Map<String, String> a_hmParams, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 148 */     sendTemplatedEmail(a_hmParams, null, false, a_language);
/*     */   }
/*     */ 
/*     */   public void sendTemplatedEmail(Map<String, String> a_hmParams, long a_lTemplateTypeId, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 167 */     sendTemplatedEmail(a_hmParams, null, a_lTemplateTypeId, false, a_language);
/*     */   }
/*     */ 
/*     */   public void sendTemplatedEmail(Map<String, String> a_hmParams, boolean a_bQueueOnly, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 187 */     sendTemplatedEmail(a_hmParams, null, a_bQueueOnly, a_language);
/*     */   }
/*     */ 
/*     */   public void sendTemplatedEmailFromRequest(HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/* 204 */     Map hmParams = new HashMap();
/*     */ 
/* 206 */     Enumeration e = a_request.getParameterNames();
/*     */ 
/* 208 */     while (e.hasMoreElements())
/*     */     {
/* 210 */       String sName = (String)e.nextElement();
/* 211 */       String sValue = a_request.getParameter(sName);
/* 212 */       hmParams.put(sName, sValue);
/*     */     }
/*     */ 
/* 215 */     sendTemplatedEmail(hmParams, false, (Language)a_request.getSession().getAttribute("currentLanguage"));
/*     */   }
/*     */ 
/*     */   public void sendEmailsToAdminAndGroups(Map<String, String> a_hmParams, List<StringDataBean> a_vecGroups, EmailAttachment[] a_eaEmailAttachments, boolean a_bQueueOnly)
/*     */     throws Bn2Exception
/*     */   {
/* 236 */     Map hmParams = a_hmParams;
/* 237 */     Map hmUserEmails = this.m_userManager.getUserEmailsForGroups(a_vecGroups);
/* 238 */     String sEmails = StringUtil.getEmailAddressesFromHashMap(hmUserEmails);
/*     */ 
/* 241 */     String sAdminEmails = this.m_userManager.getAdminEmailAddresses();
/* 242 */     this.m_logger.debug("sendEmailsToAdminAndGroups + : Admin Emails: " + sAdminEmails);
/*     */ 
/* 245 */     if (StringUtil.stringIsPopulated(sAdminEmails))
/*     */     {
/* 247 */       if (StringUtil.stringIsPopulated(sEmails))
/*     */       {
/* 249 */         sEmails = sEmails + ";";
/*     */       }
/* 251 */       sEmails = sEmails + sAdminEmails;
/*     */     }
/*     */ 
/* 254 */     hmParams.put("recipients", sEmails);
/*     */ 
/* 257 */     sendTemplatedEmail(hmParams, a_eaEmailAttachments, a_bQueueOnly, LanguageConstants.k_defaultLanguage);
/*     */   }
/*     */ 
/*     */   public void sendTemplatedEmail(Map<String, String> a_hmParams, EmailAttachment[] a_attachments, boolean a_bQueueOnly, Language a_langauge)
/*     */     throws Bn2Exception
/*     */   {
/* 275 */     sendTemplatedEmail(a_hmParams, a_attachments, 1L, a_bQueueOnly, a_langauge);
/*     */   }
/*     */ 
/*     */   public void sendPotentiallyDuplicateEmail(Map<String, String> a_hmParams, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 293 */       HtmlEmail message = buildHtmlEmail(a_hmParams, null, 1L, a_language);
/* 294 */       if (message != null)
/*     */       {
/* 296 */         String sTemplateName = (String)a_hmParams.get("template");
/* 297 */         ArrayList alToAddresses = message.getToList();
/* 298 */         DuplicateEmailKey key = new DuplicateEmailKey();
/* 299 */         key.setTemplateName(sTemplateName);
/* 300 */         key.setToList(alToAddresses);
/*     */ 
/* 303 */         synchronized (this.m_hmDupeEmails)
/*     */         {
/* 305 */           Set keySet = this.m_hmDupeEmails.keySet();
/* 306 */           Iterator it = keySet.iterator();
/* 307 */           boolean bFound = false;
/* 308 */           while (it.hasNext())
/*     */           {
/* 310 */             DuplicateEmailKey temp = (DuplicateEmailKey)it.next();
/* 311 */             if (temp.equals(key))
/*     */             {
/* 313 */               bFound = true;
/* 314 */               break;
/*     */             }
/*     */           }
/*     */ 
/* 318 */           if (!bFound)
/*     */           {
/* 321 */             DuplicateEmail email = new DuplicateEmail();
/* 322 */             email.setMessage(message);
/* 323 */             email.setTimeSent(new GregorianCalendar());
/* 324 */             this.m_hmDupeEmails.put(key, email);
/* 325 */             this.m_logger.debug("EmailManager.sendPotentiallyDuplicateEmail: Added email to queue: " + sTemplateName);
/*     */           }
/*     */           else
/*     */           {
/* 329 */             this.m_logger.debug("EmailManager.sendPotentiallyDuplicateEmail: Duplicate email ignored: " + sTemplateName);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 336 */       this.m_logger.error("EMAIL ERROR: " + e.getMessage());
/* 337 */       throw new Bn2Exception("EMAIL ERROR: " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void processPotentiallyDuplicateEmails()
/*     */   {
/* 350 */     GregorianCalendar now = new GregorianCalendar();
/*     */ 
/* 352 */     boolean bQueuedMessage = false;
/*     */ 
/* 354 */     synchronized (this.m_hmDupeEmails)
/*     */     {
/* 356 */       Set keys = this.m_hmDupeEmails.keySet();
/* 357 */       Iterator it = keys.iterator();
/*     */ 
/* 359 */       List vecToRemove = new Vector();
/*     */ 
/* 362 */       while (it.hasNext())
/*     */       {
/* 364 */         DuplicateEmailKey key = (DuplicateEmailKey)it.next();
/* 365 */         DuplicateEmail email = (DuplicateEmail)this.m_hmDupeEmails.get(key);
/* 366 */         GregorianCalendar timeToSend = new GregorianCalendar();
/* 367 */         timeToSend.setTimeInMillis(email.getTimeSent().getTimeInMillis());
/* 368 */         timeToSend.add(12, FrameworkSettings.getPotentiallyDuplicateEmailPeriod());
/* 369 */         if (now.getTimeInMillis() >= timeToSend.getTimeInMillis())
/*     */         {
/* 372 */           this.m_logger.debug("EmailManager.processPotentiallyDuplicateEmails: sending email " + key.getTemplateName());
/* 373 */           addMessageToQueue(email.getMessage());
/* 374 */           bQueuedMessage = true;
/* 375 */           vecToRemove.add(key);
/*     */         }
/*     */       }
/*     */ 
/* 379 */       Iterator itToRemove = vecToRemove.iterator();
/* 380 */       while (itToRemove.hasNext())
/*     */       {
/* 382 */         this.m_hmDupeEmails.remove(itToRemove.next());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 387 */     if (bQueuedMessage)
/*     */     {
/* 389 */       processQueue();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void sendTemplatedEmail(Map<String, String> a_hmParams, EmailAttachment[] a_attachments, long a_lTemplateTypeId, boolean a_bQueueOnly, Language a_langauge)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 446 */       HtmlEmail message = buildHtmlEmail(a_hmParams, a_attachments, a_lTemplateTypeId, a_langauge);
/*     */ 
/* 449 */       if (message != null)
/*     */       {
/* 451 */         addMessageToQueue(message);
/*     */       }
/*     */     }
/*     */     catch (NoSuchProviderException e)
/*     */     {
/* 456 */       this.m_logger.error("FEEDBACK EMAIL ERROR : NO SUCH PROVIDER EXCEPTION : " + e);
/* 457 */       throw new Bn2Exception("FEEDBACK EMAIL ERROR : NO SUCH PROVIDER EXCEPTION : " + e, e);
/*     */     }
/*     */     catch (AuthenticationFailedException e)
/*     */     {
/* 461 */       this.m_logger.error("FEEDBACK EMAIL ERROR : AUTHENTICATION FAILED EXCEPTION : " + e);
/* 462 */       throw new Bn2Exception("FEEDBACK EMAIL ERROR : AUTHENTICATION FAILED EXCEPTION : " + e, e);
/*     */     }
/*     */     catch (SendFailedException e)
/*     */     {
/* 466 */       this.m_logger.error("FEEDBACK EMAIL ERROR : SEND FAILED EXCEPTION : " + e, e);
/*     */ 
/* 468 */       if (e.getNextException() != null)
/*     */       {
/* 470 */         throw new Bn2Exception(e.getNextException().getMessage());
/*     */       }
/*     */ 
/* 473 */       throw new Bn2Exception(e.getMessage());
/*     */     }
/*     */     catch (MessagingException e)
/*     */     {
/* 477 */       this.m_logger.error("FEEDBACK EMAIL ERROR : MESSAGING EXCEPTION : " + e);
/* 478 */       throw new Bn2Exception("FEEDBACK EMAIL ERROR : MESSAGING EXCEPTION : " + e, e);
/*     */     }
/*     */ 
/* 482 */     if (!a_bQueueOnly)
/*     */     {
/* 484 */       processQueueAsynchronously();
/*     */     }
/*     */   }
/*     */ 
/*     */   public HtmlEmail buildHtmlEmail(Map<String, String> a_hmParams, EmailAttachment[] a_attachments, long a_lTemplateTypeId, Language a_language)
/*     */     throws MessagingException, Bn2Exception
/*     */   {
/* 501 */     EmailTemplate emailTemplate = null;
/*     */ 
/* 504 */     String sTemplateName = (String)a_hmParams.get("template");
/*     */ 
/* 506 */     DBTransaction transaction = this.m_transactionManager.getNewTransaction();
/*     */     try
/*     */     {
/* 511 */       emailTemplate = this.m_emailTemplateManager.getEmailItem(transaction, sTemplateName, a_lTemplateTypeId, a_language);
/*     */ 
/* 514 */       if (!emailTemplate.getEnabled())
/*     */       {
/* 516 */         Object localObject1 = null;
/*     */        // return localObject1;
                 return null;
/*     */       }
/* 520 */       String sFromAddress = emailTemplate.getAddrFrom();
/* 521 */       String sSubject = emailTemplate.getAddrSubject();
/* 522 */       String sBody = emailTemplate.getAddrBody();
/* 523 */       String sToAddress = emailTemplate.getAddrTo();
/* 524 */       String sCCAddress = emailTemplate.getAddrCc();
/* 525 */       String sBCCAddress = emailTemplate.getAddrBcc();
/*     */ 
/* 533 */       if (a_hmParams.containsKey("replacement_subject"))
/*     */       {
/* 535 */         sSubject = (String)a_hmParams.get("replacement_subject");
/*     */       }
/*     */ 
/* 539 */       if (a_hmParams.containsKey("replacement_body"))
/*     */       {
/* 541 */         sBody = (String)a_hmParams.get("replacement_body");
/*     */       }
/*     */ 
/* 545 */       Iterator itParams = a_hmParams.keySet().iterator();
/*     */ 
/* 550 */       while (itParams.hasNext())
/*     */       {
/* 552 */         String sParam = (String)itParams.next();
/* 553 */         String sParamValue = (String)a_hmParams.get(sParam);
/*     */ 
/* 556 */         sParam = "#" + sParam + "#";
/*     */ 
/* 558 */         sSubject = StringUtil.replaceString(sSubject, sParam, sParamValue);
/* 559 */         sBody = StringUtil.replaceString(sBody, sParam, sParamValue);
/* 560 */         sToAddress = StringUtil.replaceString(sToAddress, sParam, sParamValue);
/* 561 */         sCCAddress = StringUtil.replaceString(sCCAddress, sParam, sParamValue);
/* 562 */         sBCCAddress = StringUtil.replaceString(sBCCAddress, sParam, sParamValue);
/* 563 */         sFromAddress = StringUtil.replaceString(sFromAddress, sParam, sParamValue);
/*     */       }
/*     */ 
/* 566 */       this.m_logger.debug("Generated email");
/* 567 */       this.m_logger.debug("To: " + sToAddress);
/* 568 */       this.m_logger.debug("Cc: " + sCCAddress);
/* 569 */       this.m_logger.debug("Bcc: " + sBCCAddress);
/* 570 */       this.m_logger.debug("From: " + sFromAddress);
/* 571 */       this.m_logger.debug("Subject: " + sSubject);
/* 572 */       this.m_logger.debug("Body: " + sBody);
/*     */ 
/* 575 */       HtmlEmail localHtmlEmail = MailUtils.populateHtmlMessage(sSubject, sBody, sFromAddress, sToAddress, sCCAddress, sBCCAddress, a_attachments);
/*     */       return localHtmlEmail;
/*     */     }
/*     */     finally
/*     */     {
/* 580 */       if (transaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 584 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 588 */           this.m_logger.error("EmailManager.buildHtmlEmail() : SQL Exception whilst trying to close connection " + sqle.getMessage(), sqle);
                    throw new Bn2Exception(sqle);
/*     */         }
/*     */       }
                }
/* 589 */     //throw new Bn2Exception();
/*     */   }
/*     */ 
/*     */   private void addMessageToQueue(HtmlEmail a_message)
/*     */   {
/* 678 */     synchronized (this.m_vecMailQueue)
/*     */     {
/* 680 */       this.m_vecMailQueue.add(a_message);
/*     */     }
/*     */   }
/*     */ 
/*     */   private int processQueue()
/*     */   {
/* 698 */     List vecToSend = new Vector();
/* 699 */     int iSuccessCount = 0;
/*     */ 
/* 702 */     synchronized (this.m_vecMailQueue)
/*     */     {
/* 704 */       for (int i = 0; i < this.m_vecMailQueue.size(); i++)
/*     */       {
/* 706 */         vecToSend.add(this.m_vecMailQueue.get(i));
/*     */       }
/*     */ 
/* 710 */       this.m_vecMailQueue.clear();
/*     */     }
/*     */ 
/* 714 */     this.m_logger.debug("EmailManager.processQueue: About to send " + vecToSend.size() + " emails");
/* 715 */     for (int i = 0; i < vecToSend.size(); i++)
/*     */     {
/* 717 */       HtmlEmail email = (HtmlEmail)vecToSend.get(i);
/*     */       try
/*     */       {
/* 722 */         email.send();
/* 723 */         iSuccessCount++;
/*     */ 
/* 725 */         Thread.sleep(AssetBankSettings.getSecondsToSleepBetweenEmails() * 1000);
/*     */       }
/*     */       catch (MessagingException e)
/*     */       {
/* 729 */         this.m_logger.error("FEEDBACK EMAIL ERROR : MESSAGING EXCEPTION : ", e);
/*     */       }
/*     */       catch (InterruptedException ie)
/*     */       {
/* 733 */         this.m_logger.error("ERROR WHEN SENDING EMAIL (SLEEP) : ", ie);
/*     */       }
/*     */     }
/*     */ 
/* 737 */     return iSuccessCount;
/*     */   }
/*     */ 
/*     */   public void processQueueAsynchronously()
/*     */   {
/* 751 */     Thread thread = new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/* 756 */         EmailManager.this.processQueue();
/*     */       }
/*     */     };
/* 760 */     thread.start();
/*     */   }
/*     */ 
/*     */   public int sendEmail(HtmlEmail a_email)
/*     */   {
/* 771 */     synchronized (this.m_vecMailQueue)
/*     */     {
/* 773 */       addMessageToQueue(a_email);
/* 774 */       return processQueue();
/*     */     }
/*     */   }
/*     */ 
/*     */   public int sendEmails(List<HtmlEmail> a_emails)
/*     */   {
/* 786 */     return sendEmails(a_emails, false);
/*     */   }
/*     */ 
/*     */   public void sendEmailsAsynchronously(List<HtmlEmail> a_emails)
/*     */   {
/* 796 */     sendEmails(a_emails, true);
/*     */   }
/*     */ 
/*     */   public int sendEmails(List<HtmlEmail> a_emails, boolean a_bSendAsynchronously)
/*     */   {
/* 807 */     int iSuccessCount = 0;
/* 808 */     if ((a_emails != null) && (a_emails.size() > 0))
/*     */     {
/* 810 */       synchronized (this.m_vecMailQueue)
/*     */       {
/* 812 */         Iterator itEmails = a_emails.iterator();
/* 813 */         while (itEmails.hasNext())
/*     */         {
/* 815 */           addMessageToQueue((HtmlEmail)itEmails.next());
/*     */         }
/* 817 */         if (a_bSendAsynchronously)
/*     */         {
/* 819 */           processQueueAsynchronously();
/*     */         }
/*     */         else
/*     */         {
/* 823 */           iSuccessCount = processQueue();
/*     */         }
/*     */       }
/*     */     }
/* 827 */     return iSuccessCount;
/*     */   }
/*     */ 
/*     */   public DBTransactionManager getTransactionManager()
/*     */   {
/* 871 */     return this.m_transactionManager;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*     */   {
/* 877 */     this.m_transactionManager = a_sTransactionManager;
/*     */   }
/*     */ 
/*     */   public EmailTemplateManager getEmailTemplateManager()
/*     */   {
/* 883 */     return this.m_emailTemplateManager;
/*     */   }
/*     */ 
/*     */   public void setEmailTemplateManager(EmailTemplateManager a_emailTemplateManager)
/*     */   {
/* 888 */     this.m_emailTemplateManager = a_emailTemplateManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 894 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setScheduleManager(ScheduleManager a_scheduleManager)
/*     */   {
/* 899 */     this.m_scheduleManager = a_scheduleManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.service.EmailManager
 * JD-Core Version:    0.6.0
 */