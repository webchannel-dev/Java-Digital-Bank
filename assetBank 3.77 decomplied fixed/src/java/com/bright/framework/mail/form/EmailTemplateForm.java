/*     */ package com.bright.framework.mail.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.mail.bean.EmailTemplate;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class EmailTemplateForm extends Bn2Form
/*     */ {
/*  40 */   private EmailTemplate m_emailTemplate = new EmailTemplate();
/*  41 */   private ListItem m_listItem = null;
/*  42 */   private Vector m_emailTemplates = null;
/*  43 */   boolean m_bAddingTemplate = false;
/*  44 */   boolean m_bHidden = false;
/*     */ 
/*     */   public void validate(HttpServletRequest a_request)
/*     */   {
/*  48 */     String sAddrTo = getEmailTemplate().getAddrTo();
/*  49 */     if ((!StringUtil.stringIsPopulated(sAddrTo)) || (!StringUtil.isValidEmailAddressList(sAddrTo)))
/*     */     {
/*  51 */       addError("Please provide a valid to email address");
/*     */     }
/*     */ 
/*  54 */     String sAddrFrom = getEmailTemplate().getAddrFrom();
/*  55 */     if ((!StringUtil.stringIsPopulated(sAddrFrom)) || (!StringUtil.isValidEmailAddressList(sAddrFrom)))
/*     */     {
/*  57 */       addError("Please provide a valid from email address");
/*     */     }
/*     */ 
/*  60 */     String sAddrCc = getEmailTemplate().getAddrCc();
/*  61 */     if ((StringUtil.stringIsPopulated(sAddrCc)) && (!StringUtil.isValidEmailAddressList(sAddrCc)))
/*     */     {
/*  63 */       addError("Please provide a valid CC email address");
/*     */     }
/*     */ 
/*  66 */     String sAddrBcc = getEmailTemplate().getAddrBcc();
/*  67 */     if ((StringUtil.stringIsPopulated(sAddrBcc)) && (!StringUtil.isValidEmailAddressList(sAddrBcc)))
/*     */     {
/*  69 */       addError("Please provide a valid BCC email address");
/*     */     }
/*     */   }
/*     */ 
/*     */   public EmailTemplate getEmailTemplate()
/*     */   {
/*  76 */     return this.m_emailTemplate;
/*     */   }
/*     */ 
/*     */   public void setEmailTemplate(EmailTemplate a_emailTemplate)
/*     */   {
/*  81 */     this.m_emailTemplate = a_emailTemplate;
/*     */   }
/*     */ 
/*     */   public Vector getEmailTemplates()
/*     */   {
/*  86 */     if (this.m_emailTemplates == null)
/*     */     {
/*  88 */       this.m_emailTemplates = new Vector(20);
/*  89 */       for (int i = 0; i < 20; i++)
/*     */       {
/*  91 */         EmailTemplate template = new EmailTemplate();
/*  92 */         template.setLanguage(new Language());
/*  93 */         this.m_emailTemplates.add(template);
/*     */       }
/*     */     }
/*  96 */     return this.m_emailTemplates;
/*     */   }
/*     */ 
/*     */   public void setEmailTemplates(Vector a_vecList)
/*     */   {
/* 101 */     this.m_emailTemplates = a_vecList;
/*     */   }
/*     */ 
/*     */   public ListItem getListItem()
/*     */   {
/* 106 */     if (this.m_listItem == null)
/*     */     {
/* 108 */       this.m_listItem = new ListItem();
/*     */     }
/*     */ 
/* 111 */     return this.m_listItem;
/*     */   }
/*     */ 
/*     */   public void setListItem(ListItem a_listItem)
/*     */   {
/* 116 */     this.m_listItem = a_listItem;
/*     */   }
/*     */ 
/*     */   public boolean isAddingTemplate()
/*     */   {
/* 121 */     return this.m_bAddingTemplate;
/*     */   }
/*     */ 
/*     */   public void setAddingTemplate(boolean addingTemplate)
/*     */   {
/* 126 */     this.m_bAddingTemplate = addingTemplate;
/*     */   }
/*     */ 
/*     */   public void setHidden(boolean a_bHidden)
/*     */   {
/* 131 */     this.m_bHidden = a_bHidden;
/*     */   }
/*     */ 
/*     */   public boolean getHidden()
/*     */   {
/* 136 */     return this.m_bHidden;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.form.EmailTemplateForm
 * JD-Core Version:    0.6.0
 */