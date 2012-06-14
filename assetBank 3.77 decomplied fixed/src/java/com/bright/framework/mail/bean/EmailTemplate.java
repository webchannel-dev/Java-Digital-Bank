/*     */ package com.bright.framework.mail.bean;
/*     */ 
/*     */ import com.bright.framework.language.bean.Language;
/*     */ 
/*     */ public class EmailTemplate
/*     */ {
/*  30 */   private Language m_language = null;
/*  31 */   private String m_sTextId = null;
/*  32 */   private String m_sCode = null;
/*  33 */   private String m_sAddrFrom = null;
/*  34 */   private String m_sAddrTo = null;
/*  35 */   private String m_sAddrCc = null;
/*  36 */   private String m_sAddrBcc = null;
/*  37 */   private String m_sAddrSubject = null;
/*  38 */   private String m_sAddrBody = null;
/*  39 */   private boolean m_bHidden = false;
/*  40 */   private boolean m_bEnabled = false;
/*     */ 
/*     */   public String getTextId()
/*     */   {
/*  44 */     return this.m_sTextId;
/*     */   }
/*     */ 
/*     */   public void setTextId(String a_sTextId)
/*     */   {
/*  49 */     this.m_sTextId = a_sTextId;
/*     */   }
/*     */ 
/*     */   public String getCode()
/*     */   {
/*  54 */     return this.m_sCode;
/*     */   }
/*     */ 
/*     */   public void setCode(String a_sCode)
/*     */   {
/*  59 */     this.m_sCode = a_sCode;
/*     */   }
/*     */ 
/*     */   public String getAddrFrom()
/*     */   {
/*  64 */     return this.m_sAddrFrom;
/*     */   }
/*     */ 
/*     */   public void setAddrFrom(String a_sAddrFrom)
/*     */   {
/*  69 */     this.m_sAddrFrom = a_sAddrFrom;
/*     */   }
/*     */ 
/*     */   public String getAddrTo()
/*     */   {
/*  74 */     return this.m_sAddrTo;
/*     */   }
/*     */ 
/*     */   public void setAddrTo(String a_sAddrTo)
/*     */   {
/*  79 */     this.m_sAddrTo = a_sAddrTo;
/*     */   }
/*     */ 
/*     */   public String getAddrCc()
/*     */   {
/*  84 */     return this.m_sAddrCc;
/*     */   }
/*     */ 
/*     */   public void setAddrCc(String a_sAddrCc)
/*     */   {
/*  89 */     this.m_sAddrCc = a_sAddrCc;
/*     */   }
/*     */ 
/*     */   public String getAddrBcc()
/*     */   {
/*  94 */     return this.m_sAddrBcc;
/*     */   }
/*     */ 
/*     */   public void setAddrBcc(String a_sAddrBcc)
/*     */   {
/*  99 */     this.m_sAddrBcc = a_sAddrBcc;
/*     */   }
/*     */ 
/*     */   public String getAddrSubject()
/*     */   {
/* 104 */     return this.m_sAddrSubject;
/*     */   }
/*     */ 
/*     */   public void setAddrSubject(String a_sAddrSubject)
/*     */   {
/* 109 */     this.m_sAddrSubject = a_sAddrSubject;
/*     */   }
/*     */ 
/*     */   public String getAddrBody()
/*     */   {
/* 114 */     return this.m_sAddrBody;
/*     */   }
/*     */ 
/*     */   public void setAddrBody(String a_sAddrBody)
/*     */   {
/* 119 */     this.m_sAddrBody = a_sAddrBody;
/*     */   }
/*     */ 
/*     */   public Language getLanguage()
/*     */   {
/* 124 */     if (this.m_language == null)
/*     */     {
/* 126 */       this.m_language = new Language();
/*     */     }
/* 128 */     return this.m_language;
/*     */   }
/*     */ 
/*     */   public void setLanguage(Language language)
/*     */   {
/* 133 */     this.m_language = language;
/*     */   }
/*     */ 
/*     */   public void setHidden(boolean a_bHidden)
/*     */   {
/* 138 */     this.m_bHidden = a_bHidden;
/*     */   }
/*     */ 
/*     */   public boolean getHidden()
/*     */   {
/* 143 */     return this.m_bHidden;
/*     */   }
/*     */ 
/*     */   public void setEnabled(boolean a_bEnabled)
/*     */   {
/* 148 */     this.m_bEnabled = a_bEnabled;
/*     */   }
/*     */ 
/*     */   public boolean getEnabled()
/*     */   {
/* 153 */     return this.m_bEnabled;
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/* 158 */     EmailTemplate clone = new EmailTemplate();
/* 159 */     clone.m_sTextId = this.m_sTextId;
/* 160 */     clone.m_sCode = this.m_sCode;
/* 161 */     clone.m_sAddrFrom = this.m_sAddrFrom;
/* 162 */     clone.m_sAddrTo = this.m_sAddrTo;
/* 163 */     clone.m_sAddrCc = this.m_sAddrCc;
/* 164 */     clone.m_sAddrBcc = this.m_sAddrBcc;
/* 165 */     clone.m_sAddrSubject = this.m_sAddrSubject;
/* 166 */     clone.m_sAddrBody = this.m_sAddrBody;
/* 167 */     clone.m_bHidden = this.m_bHidden;
/* 168 */     return clone;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.bean.EmailTemplate
 * JD-Core Version:    0.6.0
 */