/*     */ package com.bright.assetbank.tax.bean;
/*     */ 
/*     */ import com.bright.framework.common.bean.BrightDecimal;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ 
/*     */ public class TaxablePrice
/*     */ {
/*     */   private TaxValue m_tax;
/*  35 */   private BrightMoney m_subtotalAmount = null;
/*  36 */   private String m_sTaxNumber = null;
/*     */ 
/*     */   public TaxablePrice(TaxValue a_tax, long a_lSubtotalAmount)
/*     */   {
/*  47 */     this.m_tax = a_tax;
/*  48 */     this.m_subtotalAmount = new BrightMoney(a_lSubtotalAmount);
/*     */   }
/*     */ 
/*     */   public boolean getExcludesTax()
/*     */   {
/*  59 */     boolean bExcl = this.m_tax.m_taxPercent.getNumber() < 0.0F;
/*  60 */     return bExcl;
/*     */   }
/*     */ 
/*     */   public BrightMoney getTotalAmount()
/*     */   {
/*  76 */     long lTotal = this.m_subtotalAmount.getAmount() + getTaxAmount().getAmount();
/*     */ 
/*  78 */     BrightMoney total = new BrightMoney(lTotal);
/*  79 */     return total;
/*     */   }
/*     */ 
/*     */   public BrightMoney getTaxAmount()
/*     */   {
/*  96 */     long lTaxAmount = getTaxAmountWithoutNumber().getAmount();
/*     */ 
/*  99 */     if ((StringUtil.stringIsPopulated(this.m_sTaxNumber)) && (this.m_tax.getZeroIfTaxNumberGiven()))
/*     */     {
/* 101 */       lTaxAmount = 0L;
/*     */     }
/*     */ 
/* 104 */     BrightMoney taxAmount = new BrightMoney(lTaxAmount);
/* 105 */     return taxAmount;
/*     */   }
/*     */ 
/*     */   public BrightMoney getTaxAmountWithoutNumber()
/*     */   {
/* 123 */     double dTaxAmount = (float)this.m_subtotalAmount.getAmount() * this.m_tax.getTaxPercent().getNumber() / 100.0F;
/* 124 */     long lTaxAmount = Math.round(dTaxAmount);
/*     */ 
/* 127 */     if (lTaxAmount < 0L)
/*     */     {
/* 129 */       lTaxAmount = 0L;
/*     */     }
/*     */ 
/* 132 */     BrightMoney taxAmount = new BrightMoney(lTaxAmount);
/* 133 */     return taxAmount;
/*     */   }
/*     */ 
/*     */   public BrightMoney getSubtotalAmount()
/*     */   {
/* 140 */     return this.m_subtotalAmount;
/*     */   }
/*     */ 
/*     */   public void setSubtotalAmount(BrightMoney a_sSubtotalAmount) {
/* 144 */     this.m_subtotalAmount = a_sSubtotalAmount;
/*     */   }
/*     */ 
/*     */   public TaxValue getTax()
/*     */   {
/* 149 */     return this.m_tax;
/*     */   }
/*     */ 
/*     */   public void setTax(TaxValue a_sTax) {
/* 153 */     this.m_tax = a_sTax;
/*     */   }
/*     */ 
/*     */   public String getTaxNumber()
/*     */   {
/* 159 */     return this.m_sTaxNumber;
/*     */   }
/*     */ 
/*     */   public void setTaxNumber(String a_sTaxNumber) {
/* 163 */     this.m_sTaxNumber = a_sTaxNumber;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.tax.bean.TaxablePrice
 * JD-Core Version:    0.6.0
 */