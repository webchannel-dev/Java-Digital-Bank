/*    */ package com.bright.assetbank.tax.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.BrightDecimal;
/*    */ import com.bright.framework.common.bean.TranslatableStringDataBean;
/*    */ 
/*    */ public class TaxValue
/*    */ {
/* 31 */   TranslatableStringDataBean m_taxType = null;
/* 32 */   TranslatableStringDataBean m_taxRegion = null;
/* 33 */   BrightDecimal m_taxPercent = null;
/* 34 */   boolean m_zeroIfTaxNumberGiven = false;
/*    */ 
/*    */   public TaxValue()
/*    */   {
/* 38 */     this.m_taxType = new TranslatableStringDataBean();
/* 39 */     this.m_taxRegion = new TranslatableStringDataBean();
/* 40 */     this.m_taxPercent = new BrightDecimal();
/*    */   }
/*    */ 
/*    */   public TranslatableStringDataBean getTaxRegion()
/*    */   {
/* 45 */     return this.m_taxRegion;
/*    */   }
/*    */ 
/*    */   public void setTaxRegion(TranslatableStringDataBean a_sTaxRegion) {
/* 49 */     this.m_taxRegion = a_sTaxRegion;
/*    */   }
/*    */ 
/*    */   public TranslatableStringDataBean getTaxType() {
/* 53 */     return this.m_taxType;
/*    */   }
/*    */ 
/*    */   public void setTaxType(TranslatableStringDataBean a_sTaxType) {
/* 57 */     this.m_taxType = a_sTaxType;
/*    */   }
/*    */ 
/*    */   public boolean getZeroIfTaxNumberGiven() {
/* 61 */     return this.m_zeroIfTaxNumberGiven;
/*    */   }
/*    */ 
/*    */   public void setZeroIfTaxNumberGiven(boolean a_sZeroIfTaxNumberGiven) {
/* 65 */     this.m_zeroIfTaxNumberGiven = a_sZeroIfTaxNumberGiven;
/*    */   }
/*    */ 
/*    */   public BrightDecimal getTaxPercent() {
/* 69 */     return this.m_taxPercent;
/*    */   }
/*    */ 
/*    */   public void setTaxPercent(BrightDecimal a_sTaxPercent) {
/* 73 */     this.m_taxPercent = a_sTaxPercent;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.tax.bean.TaxValue
 * JD-Core Version:    0.6.0
 */