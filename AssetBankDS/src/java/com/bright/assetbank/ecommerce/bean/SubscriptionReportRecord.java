/*    */ package com.bright.assetbank.ecommerce.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.BrightMoney;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class SubscriptionReportRecord
/*    */ {
/* 31 */   private String m_sSubscriptionName = null;
/* 32 */   private long m_lSubscriptionId = 0L;
/* 33 */   private long m_lNumSubscribers = 0L;
/* 34 */   private BrightMoney m_totalCost = null;
/* 35 */   private Vector m_vecUserSubscriptionReports = null;
/*    */ 
/*    */   public String getSubscriptionName()
/*    */   {
/* 39 */     return this.m_sSubscriptionName;
/*    */   }
/*    */ 
/*    */   public void setSubscriptionName(String a_sSubscriptionName) {
/* 43 */     this.m_sSubscriptionName = a_sSubscriptionName;
/*    */   }
/*    */ 
/*    */   public long getSubscriptionId()
/*    */   {
/* 48 */     return this.m_lSubscriptionId;
/*    */   }
/*    */ 
/*    */   public void setSubscriptionId(long a_lSubscriptionId) {
/* 52 */     this.m_lSubscriptionId = a_lSubscriptionId;
/*    */   }
/*    */ 
/*    */   public long getNumSubscribers()
/*    */   {
/* 57 */     return this.m_lNumSubscribers;
/*    */   }
/*    */ 
/*    */   public void setNumSubscribers(long a_lNumSubscribers) {
/* 61 */     this.m_lNumSubscribers = a_lNumSubscribers;
/*    */   }
/*    */ 
/*    */   public BrightMoney getTotalCost()
/*    */   {
/* 66 */     return this.m_totalCost;
/*    */   }
/*    */ 
/*    */   public void setTotalCost(BrightMoney a_lTotalCost) {
/* 70 */     this.m_totalCost = a_lTotalCost;
/*    */   }
/*    */ 
/*    */   public Vector getUserSubscriptionReports()
/*    */   {
/* 75 */     return this.m_vecUserSubscriptionReports;
/*    */   }
/*    */ 
/*    */   public void setUserSubscriptionReports(Vector a_vecUserSubscriptionReports) {
/* 79 */     this.m_vecUserSubscriptionReports = a_vecUserSubscriptionReports;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.bean.SubscriptionReportRecord
 * JD-Core Version:    0.6.0
 */