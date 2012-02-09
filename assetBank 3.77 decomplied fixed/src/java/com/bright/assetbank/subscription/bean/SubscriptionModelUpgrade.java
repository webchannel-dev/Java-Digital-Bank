/*    */ package com.bright.assetbank.subscription.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.BrightMoney;
/*    */ 
/*    */ public class SubscriptionModelUpgrade
/*    */ {
/*    */   private long m_lFromModelId;
/*    */   private long m_lToModelId;
/* 31 */   private BrightMoney m_price = null;
/*    */ 
/*    */   public SubscriptionModelUpgrade()
/*    */   {
/* 36 */     this.m_price = new BrightMoney();
/*    */   }
/*    */ 
/*    */   public BrightMoney getPrice()
/*    */   {
/* 41 */     return this.m_price;
/*    */   }
/*    */ 
/*    */   public void setPrice(BrightMoney a_sPrice) {
/* 45 */     this.m_price = a_sPrice;
/*    */   }
/*    */ 
/*    */   public long getFromModelId()
/*    */   {
/* 50 */     return this.m_lFromModelId;
/*    */   }
/*    */ 
/*    */   public void setFromModelId(long a_sFromModel) {
/* 54 */     this.m_lFromModelId = a_sFromModel;
/*    */   }
/*    */ 
/*    */   public long getToModelId() {
/* 58 */     return this.m_lToModelId;
/*    */   }
/*    */ 
/*    */   public void setToModelId(long a_sToModel) {
/* 62 */     this.m_lToModelId = a_sToModel;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.bean.SubscriptionModelUpgrade
 * JD-Core Version:    0.6.0
 */