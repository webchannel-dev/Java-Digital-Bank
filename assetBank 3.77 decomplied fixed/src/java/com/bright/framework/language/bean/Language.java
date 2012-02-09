/*     */ package com.bright.framework.language.bean;
/*     */ 
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Language extends DataBean
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6196129606156496459L;
/*  33 */   private String m_sName = null;
/*  34 */   private String m_sNativeName = null;
/*  35 */   private String m_sCode = null;
/*  36 */   private boolean m_bSuspended = false;
/*  37 */   private boolean m_bDefault = false;
/*  38 */   private boolean m_bRightToLeft = false;
/*  39 */   private String m_sIconFilename = null;
/*  40 */   private String m_sIconFilePath = null;
/*  41 */   private boolean m_bUsesLatinAlphabet = false;
/*     */ 
/*     */   public Language()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Language(long a_lId)
/*     */   {
/*  49 */     this.m_lId = a_lId;
/*     */   }
/*     */ 
/*     */   public Language(String a_sCode)
/*     */   {
/*  54 */     this.m_sCode = a_sCode;
/*  55 */     this.m_bDefault = "en".equalsIgnoreCase(a_sCode);
/*     */   }
/*     */ 
/*     */   public Language(long a_lId, String a_sName, String a_sCode)
/*     */   {
/*  60 */     this.m_lId = a_lId;
/*  61 */     this.m_sName = a_sName;
/*  62 */     this.m_sCode = a_sCode;
/*  63 */     this.m_bDefault = LanguageConstants.k_defaultLanguage.equals(this);
/*     */   }
/*     */ 
/*     */   public Language(long a_lId, String a_sName, String a_sCode, boolean a_bDefault)
/*     */   {
/*  68 */     this.m_lId = a_lId;
/*  69 */     this.m_sName = a_sName;
/*  70 */     this.m_sCode = a_sCode;
/*  71 */     this.m_bDefault = a_bDefault;
/*     */   }
/*     */ 
/*     */   public Language(long a_lId, String a_sName, String a_sCode, boolean a_bDefault, boolean a_bUsesLatinAlphabet)
/*     */   {
/*  76 */     this.m_lId = a_lId;
/*  77 */     this.m_sName = a_sName;
/*  78 */     this.m_sCode = a_sCode;
/*  79 */     this.m_bDefault = a_bDefault;
/*  80 */     this.m_bUsesLatinAlphabet = a_bUsesLatinAlphabet;
/*     */   }
/*     */ 
/*     */   public String getCode()
/*     */   {
/*  85 */     return this.m_sCode;
/*     */   }
/*     */ 
/*     */   public void setCode(String a_sCode)
/*     */   {
/*  90 */     this.m_sCode = a_sCode;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  95 */     return this.m_sName;
/*     */   }
/*     */ 
/*     */   public void setName(String a_sName)
/*     */   {
/* 100 */     this.m_sName = a_sName;
/*     */   }
/*     */ 
/*     */   public boolean isSuspended()
/*     */   {
/* 105 */     return this.m_bSuspended;
/*     */   }
/*     */ 
/*     */   public void setSuspended(boolean a_bSuspended)
/*     */   {
/* 110 */     this.m_bSuspended = a_bSuspended;
/*     */   }
/*     */ 
/*     */   public boolean isDefault()
/*     */   {
/* 115 */     return this.m_bDefault;
/*     */   }
/*     */ 
/*     */   public void setDefault(boolean a_bDefault)
/*     */   {
/* 120 */     this.m_bDefault = a_bDefault;
/*     */   }
/*     */ 
/*     */   public boolean isRightToLeft()
/*     */   {
/* 125 */     return this.m_bRightToLeft;
/*     */   }
/*     */ 
/*     */   public void setRightToLeft(boolean rightToLeft)
/*     */   {
/* 130 */     this.m_bRightToLeft = rightToLeft;
/*     */   }
/*     */ 
/*     */   public String getIconFilename()
/*     */   {
/* 135 */     return this.m_sIconFilename;
/*     */   }
/*     */ 
/*     */   public void setIconFilename(String iconFilename)
/*     */   {
/* 140 */     this.m_sIconFilename = iconFilename;
/*     */   }
/*     */ 
/*     */   public String getNativeName()
/*     */   {
/* 145 */     return this.m_sNativeName;
/*     */   }
/*     */ 
/*     */   public void setNativeName(String nativeName)
/*     */   {
/* 150 */     this.m_sNativeName = nativeName;
/*     */   }
/*     */ 
/*     */   public String getIconFilePath()
/*     */   {
/* 155 */     return this.m_sIconFilePath;
/*     */   }
/*     */ 
/*     */   public void setIconFilePath(String iconFilePath)
/*     */   {
/* 160 */     this.m_sIconFilePath = iconFilePath;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 165 */     if (!(obj instanceof Language))
/*     */     {
/* 167 */       return false;
/*     */     }
/*     */ 
/* 170 */     Language toCompare = (Language)obj;
/*     */ 
/* 172 */     if ((getId() > 0L) && (toCompare.getId() > 0L))
/*     */     {
/* 174 */       return getId() == toCompare.getId();
/*     */     }
/*     */ 
/* 177 */     return ((getCode() == null) && (toCompare.getCode() == null)) || ((toCompare.getCode() != null) && (toCompare.getCode().equalsIgnoreCase(getCode())));
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 182 */     if (getId() > 0L)
/*     */     {
/* 184 */       return (int)getId();
/*     */     }
/* 186 */     if (getCode() == null)
/*     */     {
/* 188 */       return 1;
/*     */     }
/* 190 */     return getCode().hashCode();
/*     */   }
/*     */ 
/*     */   public boolean getUsesLatinAlphabet()
/*     */   {
/* 195 */     return this.m_bUsesLatinAlphabet;
/*     */   }
/*     */ 
/*     */   public void setUsesLatinAlphabet(boolean a_bUsesLatinAlphabet)
/*     */   {
/* 200 */     this.m_bUsesLatinAlphabet = a_bUsesLatinAlphabet;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.language.bean.Language
 * JD-Core Version:    0.6.0
 */