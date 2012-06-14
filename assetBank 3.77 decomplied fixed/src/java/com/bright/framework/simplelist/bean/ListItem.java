/*     */ package com.bright.framework.simplelist.bean;
/*     */ 
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class ListItem
/*     */   implements Cloneable
/*     */ {
/*  34 */   private String m_sIdentifier = null;
/*  35 */   private String m_sTitle = null;
/*  36 */   private String m_sSummary = null;
/*  37 */   private String m_sBody = null;
/*  38 */   private Date m_dtDateAdded = null;
/*  39 */   private long m_lListId = 0L;
/*  40 */   private boolean m_bCannotBeDeleted = false;
/*  41 */   private long m_lListItemTextTypeId = 0L;
/*  42 */   private String m_sListIdentifier = null;
/*  43 */   private Language m_language = null;
/*     */ 
/*  46 */   private boolean m_bCreated = false;
/*     */ 
/*     */   public String getSummary()
/*     */   {
/*  50 */     return this.m_sSummary;
/*     */   }
/*     */ 
/*     */   public void setSummary(String a_sSummary) {
/*  54 */     this.m_sSummary = a_sSummary;
/*     */   }
/*     */ 
/*     */   public String getIdentifier() {
/*  58 */     return this.m_sIdentifier;
/*     */   }
/*     */ 
/*     */   public void setIdentifier(String a_sIdentifier) {
/*  62 */     this.m_sIdentifier = a_sIdentifier;
/*     */   }
/*     */ 
/*     */   public String getTitle() {
/*  66 */     return this.m_sTitle;
/*     */   }
/*     */ 
/*     */   public void setTitle(String a_sTitle) {
/*  70 */     this.m_sTitle = a_sTitle;
/*     */   }
/*     */ 
/*     */   public Date getDateAdded() {
/*  74 */     return this.m_dtDateAdded;
/*     */   }
/*     */ 
/*     */   public void setDateAdded(Date a_dtDateAdded) {
/*  78 */     this.m_dtDateAdded = a_dtDateAdded;
/*     */   }
/*     */ 
/*     */   public String getBody() {
/*  82 */     return this.m_sBody;
/*     */   }
/*     */ 
/*     */   public void setBody(String a_sBody) {
/*  86 */     this.m_sBody = a_sBody;
/*     */   }
/*     */ 
/*     */   public long getListId() {
/*  90 */     return this.m_lListId;
/*     */   }
/*     */ 
/*     */   public void setListId(long a_sListId) {
/*  94 */     this.m_lListId = a_sListId;
/*     */   }
/*     */ 
/*     */   public boolean getCannotBeDeleted() {
/*  98 */     return this.m_bCannotBeDeleted;
/*     */   }
/*     */ 
/*     */   public void setCannotBeDeleted(boolean a_bCannotBeDeleted) {
/* 102 */     this.m_bCannotBeDeleted = a_bCannotBeDeleted;
/*     */   }
/*     */ 
/*     */   public long getListItemTextTypeId()
/*     */   {
/* 110 */     return this.m_lListItemTextTypeId;
/*     */   }
/*     */ 
/*     */   public void setListItemTextTypeId(long a_lListItemTextTypeId)
/*     */   {
/* 118 */     this.m_lListItemTextTypeId = a_lListItemTextTypeId;
/*     */   }
/*     */ 
/*     */   public Language getLanguage()
/*     */   {
/* 123 */     if (this.m_language == null)
/*     */     {
/* 125 */       this.m_language = new Language();
/*     */     }
/* 127 */     return this.m_language;
/*     */   }
/*     */ 
/*     */   public void setLanguage(Language language)
/*     */   {
/* 132 */     this.m_language = language;
/*     */   }
/*     */ 
/*     */   public void setListIdentifier(String a_sListIdentifier)
/*     */   {
/* 137 */     this.m_sListIdentifier = a_sListIdentifier;
/*     */   }
/*     */ 
/*     */   public String getListIdentifier()
/*     */   {
/* 142 */     return this.m_sListIdentifier;
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     try
/*     */     {
/* 149 */       return super.clone();
/*     */     }
/*     */     catch (CloneNotSupportedException e) {
/*     */     
/* 153 */     throw new RuntimeException(e);}
/*     */   }
/*     */ 
/*     */   public boolean isCreated()
/*     */   {
/* 159 */     return this.m_bCreated;
/*     */   }
/*     */ 
/*     */   public void setCreated(boolean a_bCreated)
/*     */   {
/* 164 */     this.m_bCreated = a_bCreated;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.simplelist.bean.ListItem
 * JD-Core Version:    0.6.0
 */