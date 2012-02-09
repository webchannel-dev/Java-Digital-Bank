/*     */ package com.bright.framework.common.bean;
/*     */ 
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ 
/*     */ public class BrightNaturalNumber
/*     */ {
/*     */   private long m_lNumber;
/*     */   private String m_sFormNumber;
/*     */ 
/*     */   public BrightNaturalNumber()
/*     */   {
/*  40 */     this.m_lNumber = 0L;
/*  41 */     this.m_sFormNumber = null;
/*     */   }
/*     */ 
/*     */   public boolean processFormData()
/*     */   {
/*  58 */     boolean bIsValid = true;
/*  59 */     this.m_lNumber = 0L;
/*     */ 
/*  62 */     NumberFormat nf = NumberFormat.getNumberInstance();
/*  63 */     nf.setParseIntegerOnly(true);
/*     */ 
/*  65 */     if ((this.m_sFormNumber != null) && (this.m_sFormNumber.length() > 0))
/*     */     {
/*     */       try
/*     */       {
/*  70 */         long l = nf.parse(this.m_sFormNumber).longValue();
/*     */ 
/*  73 */         if (l < 0L)
/*     */         {
/*  75 */           bIsValid = false;
/*     */         }
/*     */         else
/*     */         {
/*  80 */           this.m_lNumber = l;
/*     */         }
/*     */       }
/*     */       catch (ParseException e)
/*     */       {
/*  85 */         bIsValid = false;
/*     */       }
/*     */     }
/*     */ 
/*  89 */     return bIsValid;
/*     */   }
/*     */ 
/*     */   public boolean getIsFormNumberEntered()
/*     */   {
/* 104 */     return (this.m_sFormNumber != null) && (this.m_sFormNumber.trim().length() > 0);
/*     */   }
/*     */ 
/*     */   protected void generateFormData()
/*     */   {
/* 121 */     if (this.m_lNumber > 0L)
/*     */     {
/* 123 */       this.m_sFormNumber = new Long(this.m_lNumber).toString();
/*     */     }
/*     */     else
/*     */     {
/* 127 */       this.m_sFormNumber = "";
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getDisplayNumber()
/*     */   {
/*     */     String sDisplayFormat;
/*     */     //String sDisplayFormat;
/* 146 */     if (this.m_lNumber > 0L)
/*     */     {
/* 148 */       sDisplayFormat = new Long(this.m_lNumber).toString();
/*     */     }
/*     */     else
/*     */     {
/* 152 */       sDisplayFormat = "";
/*     */     }
/* 154 */     return sDisplayFormat;
/*     */   }
/*     */ 
/*     */   public long getNumber()
/*     */   {
/* 174 */     return this.m_lNumber;
/*     */   }
/*     */ 
/*     */   public void setNumber(long a_sNumber)
/*     */   {
/* 184 */     this.m_lNumber = a_sNumber;
/* 185 */     generateFormData();
/*     */   }
/*     */ 
/*     */   public String getFormNumber()
/*     */   {
/* 202 */     return this.m_sFormNumber;
/*     */   }
/*     */ 
/*     */   public void setFormNumber(String a_sFormNumber)
/*     */   {
/* 211 */     this.m_sFormNumber = a_sFormNumber;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.BrightNaturalNumber
 * JD-Core Version:    0.6.0
 */