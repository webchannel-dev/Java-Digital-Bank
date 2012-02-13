/*    */ package com.bright.assetbank.priceband.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.BrightMoney;
/*    */ import com.bright.framework.common.bean.StringDataBean;
/*    */ 
/*    */ public class ShippingCost
/*    */ {
/* 30 */   private BrightMoney m_price = null;
/* 31 */   private StringDataBean m_taxRegion = null;
/* 32 */   private long m_lPriceBandId = 0L;
/* 33 */   private QuantityRange m_quantityRange = null;
/*    */ 
/*    */   public ShippingCost()
/*    */   {
/* 37 */     this.m_price = new BrightMoney();
/* 38 */     this.m_taxRegion = new StringDataBean();
/* 39 */     this.m_quantityRange = new QuantityRange();
/*    */   }
/*    */ 
/*    */   public long getPriceBandId()
/*    */   {
/* 45 */     return this.m_lPriceBandId;
/*    */   }
/*    */ 
/*    */   public void setPriceBandId(long a_sPriceBandId) {
/* 49 */     this.m_lPriceBandId = a_sPriceBandId;
/*    */   }
/*    */ 
/*    */   public BrightMoney getPrice() {
/* 53 */     return this.m_price;
/*    */   }
/*    */ 
/*    */   public void setPrice(BrightMoney a_sPrice) {
/* 57 */     this.m_price = a_sPrice;
/*    */   }
/*    */ 
/*    */   public QuantityRange getQuantityRange() {
/* 61 */     return this.m_quantityRange;
/*    */   }
/*    */ 
/*    */   public void setQuantityRange(QuantityRange a_sQuantityRange) {
/* 65 */     this.m_quantityRange = a_sQuantityRange;
/*    */   }
/*    */ 
/*    */   public StringDataBean getTaxRegion() {
/* 69 */     return this.m_taxRegion;
/*    */   }
/*    */ 
/*    */   public void setTaxRegion(StringDataBean a_sTaxRegion) {
/* 73 */     this.m_taxRegion = a_sTaxRegion;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.priceband.bean.ShippingCost
 * JD-Core Version:    0.6.0
 */