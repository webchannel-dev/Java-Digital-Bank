/*     */ package com.bright.assetbank.usage.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.usage.bean.UsageType;
/*     */ import com.bright.assetbank.usage.bean.UsageTypeFormat;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class UsageFormatAdminForm extends Bn2Form
/*     */ {
/*  42 */   private Vector m_vecUsageTypeFormats = null;
/*  43 */   private UsageTypeFormat m_usageTypeFormat = null;
/*  44 */   private UsageType m_usageType = null;
/*  45 */   private String m_sWidthString = null;
/*  46 */   private String m_sHeightString = null;
/*  47 */   private String[] m_preserveFormatList = null;
/*  48 */   private Vector m_vecColorSpaces = null;
/*     */ 
/*     */   public void reset(ActionMapping mapping, HttpServletRequest request)
/*     */   {
/*  61 */     this.m_preserveFormatList = new String[0];
/*  62 */     setWidthString(null);
/*  63 */     setHeightString(null);
/*     */ 
/*  65 */     if (!getHasErrors())
/*     */     {
/*  67 */       this.m_usageTypeFormat = new UsageTypeFormat();
/*     */ 
/*  70 */       if (FrameworkSettings.getSupportMultiLanguage())
/*     */       {
/*  72 */         LanguageUtils.createEmptyTranslations(this.m_usageTypeFormat, 20);
/*     */       }
/*     */     }
/*     */ 
/*  76 */     super.reset(mapping, request);
/*     */   }
/*     */ 
/*     */   public void setUsageTypeFormats(Vector a_vecUsageTypeFormats)
/*     */   {
/*  82 */     this.m_vecUsageTypeFormats = a_vecUsageTypeFormats;
/*     */   }
/*     */ 
/*     */   public Vector getUsageTypeFormats()
/*     */   {
/*  87 */     return this.m_vecUsageTypeFormats;
/*     */   }
/*     */ 
/*     */   public void setUsageTypeFormat(UsageTypeFormat a_usageTypeFormat)
/*     */   {
/*  92 */     this.m_usageTypeFormat = a_usageTypeFormat;
/*     */   }
/*     */ 
/*     */   public UsageTypeFormat getUsageTypeFormat()
/*     */   {
/*  97 */     return this.m_usageTypeFormat;
/*     */   }
/*     */ 
/*     */   public void setUsageType(UsageType a_usageType)
/*     */   {
/* 102 */     this.m_usageType = a_usageType;
/*     */   }
/*     */ 
/*     */   public UsageType getUsageType()
/*     */   {
/* 107 */     return this.m_usageType;
/*     */   }
/*     */ 
/*     */   public void setWidthString(String a_sWidthString)
/*     */   {
/* 112 */     this.m_sWidthString = a_sWidthString;
/*     */   }
/*     */ 
/*     */   public String getWidthString()
/*     */   {
/* 117 */     return this.m_sWidthString;
/*     */   }
/*     */ 
/*     */   public String getHeightString() {
/* 121 */     return this.m_sHeightString;
/*     */   }
/*     */ 
/*     */   public void setHeightString(String a_sHeightString) {
/* 125 */     this.m_sHeightString = a_sHeightString;
/*     */   }
/*     */ 
/*     */   public String[] getPreserveFormatList()
/*     */   {
/* 130 */     return this.m_preserveFormatList;
/*     */   }
/*     */ 
/*     */   public void setPreserveFormatList(String[] a_sPreserveFormatList)
/*     */   {
/* 135 */     this.m_preserveFormatList = a_sPreserveFormatList;
/*     */   }
/*     */ 
/*     */   public void setColorSpaces(Vector a_vecColorSpaces)
/*     */   {
/* 140 */     this.m_vecColorSpaces = a_vecColorSpaces;
/*     */   }
/*     */ 
/*     */   public Vector getColorSpaces()
/*     */   {
/* 145 */     return this.m_vecColorSpaces;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.form.UsageFormatAdminForm
 * JD-Core Version:    0.6.0
 */