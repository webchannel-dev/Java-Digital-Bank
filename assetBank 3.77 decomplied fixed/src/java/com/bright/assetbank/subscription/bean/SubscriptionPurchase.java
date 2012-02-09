/*    */ package com.bright.assetbank.subscription.bean;
/*    */ 
/*    */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*    */ import com.bright.framework.common.bean.BrightDate;
/*    */ 
/*    */ public class SubscriptionPurchase extends Purchase
/*    */ {
/* 30 */   private Subscription m_subscription = null;
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 46 */     String sDescription = this.m_subscription.getModel().getDescription();
/* 47 */     sDescription = sDescription + ", " + this.m_subscription.getStartDate().getDisplayDate();
/* 48 */     return sDescription;
/*    */   }
/*    */ 
/*    */   public Subscription getSubscription()
/*    */   {
/* 54 */     return this.m_subscription;
/*    */   }
/*    */ 
/*    */   public void setSubscription(Subscription a_sSubscription) {
/* 58 */     this.m_subscription = a_sSubscription;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.bean.SubscriptionPurchase
 * JD-Core Version:    0.6.0
 */