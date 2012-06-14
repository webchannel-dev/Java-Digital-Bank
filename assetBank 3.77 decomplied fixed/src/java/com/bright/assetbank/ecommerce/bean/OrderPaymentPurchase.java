/*    */ package com.bright.assetbank.ecommerce.bean;
/*    */ 
/*    */ import com.bright.assetbank.ecommerce.constant.EcommerceSettings;
/*    */ 
/*    */ public class OrderPaymentPurchase extends Purchase
/*    */ {
/* 30 */   private Order m_order = null;
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 48 */     String sDescription = EcommerceSettings.getPspDescStem() + " ";
/*    */ 
/* 50 */     sDescription = sDescription + AssetPurchase.convertAssetsToString(this.m_order.getAssets());
/*    */ 
/* 52 */     return sDescription;
/*    */   }
/*    */ 
/*    */   public Order getOrder()
/*    */   {
/* 58 */     return this.m_order;
/*    */   }
/*    */ 
/*    */   public void setOrder(Order a_sOrder)
/*    */   {
/* 64 */     this.m_order = a_sOrder;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.bean.OrderPaymentPurchase
 * JD-Core Version:    0.6.0
 */