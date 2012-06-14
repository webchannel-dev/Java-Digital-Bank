/*     */ package com.bright.assetbank.marketing.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.marketing.bean.MarketingEmail;
/*     */ import com.bright.assetbank.user.bean.UserSearchCriteria;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.mail.bean.EmailContent;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class SendMarketingEmailForm extends Bn2Form
/*     */ {
/*  37 */   private List m_users = null;
/*  38 */   private List m_groups = null;
/*  39 */   private List m_marketingGroups = null;
/*  40 */   private List m_countries = null;
/*  41 */   private UserSearchCriteria m_searchCriteria = null;
/*  42 */   private Vector m_emailTemplates = null;
/*  43 */   private String m_sEmailTemplateId = null;
/*  44 */   private EmailContent m_emailContent = null;
/*  45 */   private Vector m_emailTranslations = null;
/*  46 */   private Vector m_previewEmails = null;
/*  47 */   private List m_languages = null;
/*  48 */   private long m_lAssetBoxId = 0L;
/*  49 */   private String m_sAssetLink = null;
/*  50 */   private String m_sFromAddress = null;
/*  51 */   private String m_sToAddress = null;
/*  52 */   private String m_sCcAddress = null;
/*  53 */   private String m_sBccAddress = null;
/*  54 */   private MarketingEmail m_marketingEmail = null;
/*     */ 
/*     */   public List getUsers()
/*     */   {
/*  58 */     return this.m_users;
/*     */   }
/*     */ 
/*     */   public void setUsers(List a_users)
/*     */   {
/*  63 */     this.m_users = a_users;
/*     */   }
/*     */ 
/*     */   public int getNumUsers()
/*     */   {
/*  68 */     return this.m_users == null ? 0 : this.m_users.size();
/*     */   }
/*     */ 
/*     */   public UserSearchCriteria getSearchCriteria()
/*     */   {
/*  73 */     if (this.m_searchCriteria == null)
/*     */     {
/*  75 */       this.m_searchCriteria = new UserSearchCriteria();
/*     */     }
/*  77 */     return this.m_searchCriteria;
/*     */   }
/*     */ 
/*     */   public void setSearchCriteria(UserSearchCriteria a_searchCriteria)
/*     */   {
/*  82 */     this.m_searchCriteria = a_searchCriteria;
/*     */   }
/*     */ 
/*     */   public List getGroups()
/*     */   {
/*  87 */     return this.m_groups;
/*     */   }
/*     */ 
/*     */   public void setGroups(List groups)
/*     */   {
/*  92 */     this.m_groups = groups;
/*     */   }
/*     */ 
/*     */   public List getMarketingGroups()
/*     */   {
/*  97 */     return this.m_marketingGroups;
/*     */   }
/*     */ 
/*     */   public void setMarketingGroups(List a_marketingGroups)
/*     */   {
/* 102 */     this.m_marketingGroups = a_marketingGroups;
/*     */   }
/*     */ 
/*     */   public List getCountries()
/*     */   {
/* 107 */     return this.m_countries;
/*     */   }
/*     */ 
/*     */   public void setCountries(List countries)
/*     */   {
/* 112 */     this.m_countries = countries;
/*     */   }
/*     */ 
/*     */   public Vector getEmailTemplates()
/*     */   {
/* 117 */     return this.m_emailTemplates;
/*     */   }
/*     */ 
/*     */   public void setEmailTemplates(Vector a_emails)
/*     */   {
/* 122 */     this.m_emailTemplates = a_emails;
/*     */   }
/*     */ 
/*     */   public String getEmailTemplateId()
/*     */   {
/* 127 */     return this.m_sEmailTemplateId;
/*     */   }
/*     */ 
/*     */   public void setEmailTemplateId(String emailTemplateCode)
/*     */   {
/* 132 */     this.m_sEmailTemplateId = emailTemplateCode;
/*     */   }
/*     */ 
/*     */   public Vector getEmailTranslations()
/*     */   {
/* 137 */     return this.m_emailTranslations;
/*     */   }
/*     */ 
/*     */   public void setEmailTranslations(Vector emailTranslations)
/*     */   {
/* 142 */     this.m_emailTranslations = emailTranslations;
/*     */   }
/*     */ 
/*     */   public EmailContent getEmailContent()
/*     */   {
/* 147 */     if (this.m_emailContent == null)
/*     */     {
/* 149 */       this.m_emailContent = new EmailContent();
/*     */ 
/* 152 */       if (FrameworkSettings.getSupportMultiLanguage())
/*     */       {
/* 154 */         LanguageUtils.createEmptyTranslations(this.m_emailContent, 20);
/*     */       }
/*     */     }
/* 157 */     return this.m_emailContent;
/*     */   }
/*     */ 
/*     */   public void setEmailContent(EmailContent emailContent)
/*     */   {
/* 162 */     this.m_emailContent = emailContent;
/*     */   }
/*     */ 
/*     */   public Vector getPreviewEmails()
/*     */   {
/* 167 */     return this.m_previewEmails;
/*     */   }
/*     */ 
/*     */   public void setPreviewEmails(Vector previewEmails)
/*     */   {
/* 172 */     this.m_previewEmails = previewEmails;
/*     */   }
/*     */ 
/*     */   public List getLanguages()
/*     */   {
/* 177 */     return this.m_languages;
/*     */   }
/*     */ 
/*     */   public void setLanguages(List languages)
/*     */   {
/* 182 */     this.m_languages = languages;
/*     */   }
/*     */ 
/*     */   public long getAssetBoxId()
/*     */   {
/* 187 */     return this.m_lAssetBoxId;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxId(long assetBoxId)
/*     */   {
/* 192 */     this.m_lAssetBoxId = assetBoxId;
/*     */   }
/*     */ 
/*     */   public String getAssetLink()
/*     */   {
/* 197 */     return this.m_sAssetLink;
/*     */   }
/*     */ 
/*     */   public void setAssetLink(String assetLink)
/*     */   {
/* 202 */     this.m_sAssetLink = assetLink;
/*     */   }
/*     */ 
/*     */   public String getFromAddress()
/*     */   {
/* 207 */     return this.m_sFromAddress;
/*     */   }
/*     */ 
/*     */   public void setFromAddress(String fromAddress)
/*     */   {
/* 212 */     this.m_sFromAddress = fromAddress;
/*     */   }
/*     */ 
/*     */   public MarketingEmail getMarketingEmail()
/*     */   {
/* 217 */     if (this.m_marketingEmail == null)
/*     */     {
/* 219 */       this.m_marketingEmail = new MarketingEmail();
/*     */ 
/* 222 */       if (FrameworkSettings.getSupportMultiLanguage())
/*     */       {
/* 224 */         LanguageUtils.createEmptyTranslations(this.m_marketingEmail, 20);
/*     */       }
/*     */     }
/* 227 */     return this.m_marketingEmail;
/*     */   }
/*     */ 
/*     */   public void setMarketingEmail(MarketingEmail marketingEmail)
/*     */   {
/* 232 */     this.m_marketingEmail = marketingEmail;
/*     */   }
/*     */ 
/*     */   public String getBccAddress()
/*     */   {
/* 237 */     return this.m_sBccAddress;
/*     */   }
/*     */ 
/*     */   public void setBccAddress(String bccAddress)
/*     */   {
/* 242 */     this.m_sBccAddress = bccAddress;
/*     */   }
/*     */ 
/*     */   public String getCcAddress()
/*     */   {
/* 247 */     return this.m_sCcAddress;
/*     */   }
/*     */ 
/*     */   public void setCcAddress(String ccAddress)
/*     */   {
/* 252 */     this.m_sCcAddress = ccAddress;
/*     */   }
/*     */ 
/*     */   public String getToAddress()
/*     */   {
/* 257 */     return this.m_sToAddress;
/*     */   }
/*     */ 
/*     */   public void setToAddress(String toAddress)
/*     */   {
/* 262 */     this.m_sToAddress = toAddress;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.form.SendMarketingEmailForm
 * JD-Core Version:    0.6.0
 */