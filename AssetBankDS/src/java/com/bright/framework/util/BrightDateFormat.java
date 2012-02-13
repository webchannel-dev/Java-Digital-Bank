/*     */ package com.bright.framework.util;
/*     */ 
/*     */ import java.text.DateFormatSymbols;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class BrightDateFormat extends SimpleDateFormat
/*     */ {
/*  38 */   boolean m_bValidate4YearDate = false;
/*  39 */   SimpleDateFormat m_comparisonFormat = null;
/*     */ 
/*     */   public BrightDateFormat()
/*     */   {
/*  47 */     this.m_comparisonFormat = new SimpleDateFormat("dd/MM/yyyy");
/*     */   }
/*     */ 
/*     */   public BrightDateFormat(String a_sPattern)
/*     */   {
/*  55 */     super(a_sPattern);
/*  56 */     this.m_comparisonFormat = new SimpleDateFormat("dd/MM/yyyy");
/*     */   }
/*     */ 
/*     */   public BrightDateFormat(String a_sPattern, DateFormatSymbols a_sFormatSymbols)
/*     */   {
/*  65 */     super(a_sPattern, a_sFormatSymbols);
/*  66 */     this.m_comparisonFormat = new SimpleDateFormat("dd/MM/yyyy");
/*     */   }
/*     */ 
/*     */   public BrightDateFormat(String a_sPattern, Locale a_sLocale)
/*     */   {
/*  75 */     super(a_sPattern, a_sLocale);
/*  76 */     this.m_comparisonFormat = new SimpleDateFormat("dd/MM/yyyy");
/*     */   }
/*     */ 
/*     */   public Date parse(String a_sSource)
/*     */     throws ParseException
/*     */   {
/*  98 */     Date dtResult = super.parse(a_sSource);
/*  99 */     String sPattern = toPattern();
/*     */ 
/* 101 */     if ((sPattern != null) && (sPattern.indexOf("yyyy") >= 0) && (this.m_bValidate4YearDate))
/*     */     {
/* 103 */       if ((dtResult != null) && ((dtResult.before(this.m_comparisonFormat.parse("01/01/1000"))) || (dtResult.after(this.m_comparisonFormat.parse("31/12/9999")))))
/*     */       {
/* 106 */         throw new ParseException("The date being parsed does not have a 4 digit year.", 0);
/*     */       }
/*     */     }
/*     */ 
/* 110 */     return dtResult;
/*     */   }
/*     */ 
/*     */   public boolean getValidate4YearDate()
/*     */   {
/* 116 */     return this.m_bValidate4YearDate;
/*     */   }
/*     */ 
/*     */   public void setValidate4YearDate(boolean a_sValidate4YearDate)
/*     */   {
/* 122 */     this.m_bValidate4YearDate = a_sValidate4YearDate;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.BrightDateFormat
 * JD-Core Version:    0.6.0
 */