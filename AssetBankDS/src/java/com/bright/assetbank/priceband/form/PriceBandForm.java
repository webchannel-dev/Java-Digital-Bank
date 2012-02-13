/*    */ package com.bright.assetbank.priceband.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.priceband.bean.PriceBand;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class PriceBandForm extends Bn2Form
/*    */ {
/* 33 */   private Vector m_priceBandList = null;
/* 34 */   protected PriceBand m_priceBand = null;
/*    */ 
/*    */   public PriceBand getPriceBand()
/*    */   {
/* 43 */     return this.m_priceBand;
/*    */   }
/*    */ 
/*    */   public void setPriceBand(PriceBand a_sPriceBand) {
/* 47 */     this.m_priceBand = a_sPriceBand;
/*    */   }
/*    */ 
/*    */   public Vector getPriceBandList() {
/* 51 */     return this.m_priceBandList;
/*    */   }
/*    */ 
/*    */   public void setPriceBandList(Vector a_sPriceBandList) {
/* 55 */     this.m_priceBandList = a_sPriceBandList;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.priceband.form.PriceBandForm
 * JD-Core Version:    0.6.0
 */