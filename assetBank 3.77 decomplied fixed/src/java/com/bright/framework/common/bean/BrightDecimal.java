/*     */ package com.bright.framework.common.bean;
/*     */ 
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ 
/*     */ public class BrightDecimal
/*     */ {
/*     */   private String m_sFormNumber;
/*     */   private float m_fNumber;
/*     */   private int m_numDecimals;
/*     */   private boolean m_bPositiveOnly;
/*     */ 
/*     */   public BrightDecimal()
/*     */   {
/*  37 */     this.m_fNumber = 0.0F;
/*  38 */     this.m_sFormNumber = null;
/*  39 */     this.m_numDecimals = 0;
/*  40 */     this.m_bPositiveOnly = true;
/*     */   }
/*     */ 
/*     */   public boolean processFormData()
/*     */   {
/*  56 */     boolean bIsValid = true;
/*  57 */     this.m_fNumber = 0.0F;
/*     */ 
/*  60 */     NumberFormat nf = NumberFormat.getNumberInstance();
/*  61 */     nf.setMaximumFractionDigits(getNumDecimals());
/*     */ 
/*  63 */     if ((this.m_sFormNumber != null) && (this.m_sFormNumber.length() > 0))
/*     */     {
/*     */       try
/*     */       {
/*  68 */         float f = nf.parse(this.m_sFormNumber).floatValue();
/*     */ 
/*  71 */         if ((getPositiveOnly()) && (f < 0.0F))
/*     */         {
/*  73 */           bIsValid = false;
/*     */         }
/*     */         else
/*     */         {
/*  78 */           this.m_fNumber = f;
/*     */         }
/*     */       }
/*     */       catch (ParseException e)
/*     */       {
/*  83 */         bIsValid = false;
/*     */       }
/*     */     }
/*     */ 
/*  87 */     return bIsValid;
/*     */   }
/*     */ 
/*     */   public boolean getIsFormNumberEntered()
/*     */   {
/* 102 */     return (this.m_sFormNumber != null) && (this.m_sFormNumber.trim().length() > 0);
/*     */   }
/*     */ 
/*     */   protected void generateFormData()
/*     */   {
/* 119 */     this.m_sFormNumber = new Float(this.m_fNumber).toString();
/*     */   }
/*     */ 
/*     */   public String getDisplayNumber()
/*     */   {
/* 137 */     String sDisplayFormat = new Float(this.m_fNumber).toString();
/* 138 */     return sDisplayFormat;
/*     */   }
/*     */ 
/*     */   public String getFormNumber()
/*     */   {
/* 156 */     return this.m_sFormNumber;
/*     */   }
/*     */ 
/*     */   public void setFormNumber(String a_sFormNumber)
/*     */   {
/* 165 */     this.m_sFormNumber = a_sFormNumber;
/*     */   }
/*     */ 
/*     */   public float getNumber()
/*     */   {
/* 181 */     return this.m_fNumber;
/*     */   }
/*     */ 
/*     */   public void setNumber(float a_sNumber)
/*     */   {
/* 190 */     this.m_fNumber = a_sNumber;
/* 191 */     generateFormData();
/*     */   }
/*     */ 
/*     */   public int getNumDecimals()
/*     */   {
/* 207 */     return this.m_numDecimals;
/*     */   }
/*     */ 
/*     */   public void setNumDecimals(int a_sNumDecimals)
/*     */   {
/* 216 */     this.m_numDecimals = a_sNumDecimals;
/*     */   }
/*     */ 
/*     */   public boolean getPositiveOnly()
/*     */   {
/* 232 */     return this.m_bPositiveOnly;
/*     */   }
/*     */ 
/*     */   public void setPositiveOnly(boolean a_sPositiveOnly)
/*     */   {
/* 241 */     this.m_bPositiveOnly = a_sPositiveOnly;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.BrightDecimal
 * JD-Core Version:    0.6.0
 */