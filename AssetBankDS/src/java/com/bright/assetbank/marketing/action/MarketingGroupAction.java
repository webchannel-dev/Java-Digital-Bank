/*    */ package com.bright.assetbank.marketing.action;
/*    */ 
/*    */ import com.bright.assetbank.marketing.service.MarketingGroupManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ 
/*    */ public abstract class MarketingGroupAction extends BTransactionAction
/*    */ {
/* 22 */   private MarketingGroupManager m_marketingGroupManager = null;
/*    */ 
/*    */   public MarketingGroupManager getMarketingGroupManager()
/*    */   {
/* 26 */     return this.m_marketingGroupManager;
/*    */   }
/*    */ 
/*    */   public void setMarketingGroupManager(MarketingGroupManager marketingGroupManager)
/*    */   {
/* 31 */     this.m_marketingGroupManager = marketingGroupManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.action.MarketingGroupAction
 * JD-Core Version:    0.6.0
 */