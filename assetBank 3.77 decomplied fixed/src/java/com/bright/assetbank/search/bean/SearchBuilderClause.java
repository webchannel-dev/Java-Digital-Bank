/*     */ package com.bright.assetbank.search.bean;
/*     */ 
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.util.DateUtil;
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class SearchBuilderClause
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7879385691253347138L;
/*  37 */   private static final DateFormat k_format = AssetBankSettings.getStandardDateFormat();
/*  38 */   private static final DateFormat k_formatYearOnly = DateUtil.getYearOnlyDateFormat();
/*     */ 
/*  40 */   private long m_lAttributeId = 0L;
/*  41 */   private boolean m_bIsOrClause = false;
/*  42 */   private long m_lOperatorId = 0L;
/*  43 */   private String m_sValue = null;
/*  44 */   private boolean m_bIsVisible = false;
/*  45 */   private boolean m_bIsNewClause = false;
/*  46 */   private boolean m_bIsDate = false;
/*  47 */   private boolean m_bIsNumeric = false;
/*  48 */   private boolean m_bNumericIsWholeNumber = false;
/*  49 */   private boolean m_bIsListAttribute = false;
/*  50 */   private boolean m_bDelimiterIsSpace = true;
/*     */   private String m_sTokenDelimiterRegex;
/*  52 */   private boolean m_bIsHidden = false;
/*     */ 
/*     */   public long getAttributeId()
/*     */   {
/*  56 */     return this.m_lAttributeId;
/*     */   }
/*     */ 
/*     */   public void setAttributeId(long a_iAttributeId) {
/*  60 */     this.m_lAttributeId = a_iAttributeId;
/*     */   }
/*     */ 
/*     */   public boolean isOrClause() {
/*  64 */     return this.m_bIsOrClause;
/*     */   }
/*     */ 
/*     */   public void setOrClause(boolean a_bValue) {
/*  68 */     this.m_bIsOrClause = a_bValue;
/*     */   }
/*     */ 
/*     */   public long getOperatorId() {
/*  72 */     return this.m_lOperatorId;
/*     */   }
/*     */ 
/*     */   public void setOperatorId(long a_iOperatorId) {
/*  76 */     this.m_lOperatorId = a_iOperatorId;
/*     */   }
/*     */ 
/*     */   public String getValue() {
/*  80 */     return this.m_sValue;
/*     */   }
/*     */ 
/*     */   public void setValue(String a_iValue) {
/*  84 */     this.m_sValue = a_iValue;
/*     */   }
/*     */ 
/*     */   public boolean isVisible() {
/*  88 */     return this.m_bIsVisible;
/*     */   }
/*     */ 
/*     */   public void setVisible(boolean a_iIsVisible) {
/*  92 */     this.m_bIsVisible = a_iIsVisible;
/*     */   }
/*     */ 
/*     */   public boolean isNewClause() {
/*  96 */     return this.m_bIsNewClause;
/*     */   }
/*     */ 
/*     */   public void setNewClause(boolean a_iIsNewClause) {
/* 100 */     this.m_bIsNewClause = a_iIsNewClause;
/*     */   }
/*     */ 
/*     */   public boolean isDate() {
/* 104 */     return this.m_bIsDate;
/*     */   }
/*     */ 
/*     */   public void setDate(boolean isDate) {
/* 108 */     this.m_bIsDate = isDate;
/*     */   }
/*     */ 
/*     */   public Date getDateValueAsStart()
/*     */   {
/* 113 */     Date value = null;
/*     */ 
/* 115 */     if ((this.m_bIsDate) && (StringUtils.isNotEmpty(this.m_sValue)))
/*     */     {
/*     */       try
/*     */       {
/* 119 */         value = k_format.parse(this.m_sValue);
/*     */       }
/*     */       catch (ParseException pe)
/*     */       {
/*     */         try
/*     */         {
/* 125 */           value = k_formatYearOnly.parse(this.m_sValue);
/*     */         }
/*     */         catch (ParseException peYearOnly)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 134 */     return value;
/*     */   }
/*     */ 
/*     */   public Date getDateValueAsEnd()
/*     */   {
/* 139 */     Date value = null;
/*     */ 
/* 141 */     if ((this.m_bIsDate) && (StringUtils.isNotEmpty(this.m_sValue)))
/*     */     {
/*     */       try
/*     */       {
/* 145 */         value = DateUtil.getEndOfDay(k_format.parse(this.m_sValue));
/*     */       }
/*     */       catch (ParseException pe)
/*     */       {
/*     */         try
/*     */         {
/* 151 */           value = DateUtil.getEndOfYear(k_formatYearOnly.parse(this.m_sValue));
/*     */         }
/*     */         catch (ParseException peYearOnly)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 160 */     return value;
/*     */   }
/*     */ 
/*     */   public Double getNumericValue()
/*     */   {
/* 165 */     Double d = null;
/*     */     try
/*     */     {
/* 168 */       d = Double.valueOf(this.m_sValue);
/*     */     }
/*     */     catch (NumberFormatException e) {
/*     */     }
/* 172 */     return d;
/*     */   }
/*     */ 
/*     */   public boolean isNumeric()
/*     */   {
/* 177 */     return this.m_bIsNumeric;
/*     */   }
/*     */ 
/*     */   public void setNumeric(boolean isNumeric) {
/* 181 */     this.m_bIsNumeric = isNumeric;
/*     */   }
/*     */ 
/*     */   public boolean isListAttribute() {
/* 185 */     return this.m_bIsListAttribute;
/*     */   }
/*     */ 
/*     */   public void setListAttribute(boolean isListAttribute) {
/* 189 */     this.m_bIsListAttribute = isListAttribute;
/*     */   }
/*     */ 
/*     */   public boolean getDelimiterIsSpace()
/*     */   {
/* 194 */     return this.m_bDelimiterIsSpace;
/*     */   }
/*     */ 
/*     */   public void setDelimiterIsSpace(boolean a_bDelimiterIsSpace)
/*     */   {
/* 199 */     this.m_bDelimiterIsSpace = a_bDelimiterIsSpace;
/*     */   }
/*     */ 
/*     */   public String getTokenDelimiterRegex()
/*     */   {
/* 204 */     return this.m_sTokenDelimiterRegex;
/*     */   }
/*     */ 
/*     */   public void setTokenDelimiterRegex(String a_sTokenDelimiterRegex)
/*     */   {
/* 209 */     this.m_sTokenDelimiterRegex = a_sTokenDelimiterRegex;
/*     */   }
/*     */ 
/*     */   public void setIsHidden(boolean a_bIsHidden)
/*     */   {
/* 214 */     this.m_bIsHidden = a_bIsHidden;
/*     */   }
/*     */ 
/*     */   public boolean getIsHidden()
/*     */   {
/* 219 */     return this.m_bIsHidden;
/*     */   }
/*     */ 
/*     */   public boolean getNumericIsWholeNumber()
/*     */   {
/* 224 */     return this.m_bNumericIsWholeNumber;
/*     */   }
/*     */ 
/*     */   public void setNumericIsWholeNumber(boolean a_bNumericIsWholeNumber)
/*     */   {
/* 229 */     this.m_bNumericIsWholeNumber = a_bNumericIsWholeNumber;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.bean.SearchBuilderClause
 * JD-Core Version:    0.6.0
 */