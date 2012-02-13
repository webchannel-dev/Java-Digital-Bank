/*    */ package com.bright.assetbank.ecommerce.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.Address;
/*    */ import com.bright.framework.common.bean.BrightMoney;
/*    */ 
/*    */ public class AssetWithShippingPurchase extends AssetPurchase
/*    */ {
/* 24 */   private Address m_shippingAddress = null;
/* 25 */   private String m_sRecipient = null;
/* 26 */   private boolean m_bHasPrints = false;
/* 27 */   private BrightMoney m_shippingCost = null;
/*    */ 
/*    */   public AssetWithShippingPurchase()
/*    */   {
/* 35 */     this.m_shippingCost = new BrightMoney();
/*    */   }
/*    */ 
/*    */   public Address getShippingAddress()
/*    */   {
/* 40 */     return this.m_shippingAddress;
/*    */   }
/*    */ 
/*    */   public void setShippingAddress(Address a_sShippingAddress)
/*    */   {
/* 45 */     this.m_shippingAddress = a_sShippingAddress;
/*    */   }
/*    */ 
/*    */   public String getRecipient()
/*    */   {
/* 50 */     return this.m_sRecipient;
/*    */   }
/*    */ 
/*    */   public void setRecipient(String a_sRecipient)
/*    */   {
/* 55 */     this.m_sRecipient = a_sRecipient;
/*    */   }
/*    */ 
/*    */   public boolean getHasPrints()
/*    */   {
/* 60 */     return this.m_bHasPrints;
/*    */   }
/*    */ 
/*    */   public void setHasPrints(boolean a_sHasPrints)
/*    */   {
/* 65 */     this.m_bHasPrints = a_sHasPrints;
/*    */   }
/*    */ 
/*    */   public BrightMoney getShippingCost()
/*    */   {
/* 73 */     return this.m_shippingCost;
/*    */   }
/*    */ 
/*    */   public void setShippingCost(BrightMoney a_dtShippingCost)
/*    */   {
/* 81 */     this.m_shippingCost = a_dtShippingCost;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.bean.AssetWithShippingPurchase
 * JD-Core Version:    0.6.0
 */