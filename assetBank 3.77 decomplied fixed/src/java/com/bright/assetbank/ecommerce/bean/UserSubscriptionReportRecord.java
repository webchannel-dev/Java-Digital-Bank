/*    */ package com.bright.assetbank.ecommerce.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.BrightMoney;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class UserSubscriptionReportRecord
/*    */ {
/* 31 */   private Date m_startDate = null;
/* 32 */   private String m_sEmail = null;
/* 33 */   private String m_sSubscriptionModel = null;
/* 34 */   private boolean m_bActive = false;
/* 35 */   private BrightMoney m_pricePaid = null;
/* 36 */   private BrightMoney m_totalPaid = null;
/*    */ 
/*    */   public Date getStartDate()
/*    */   {
/* 40 */     return this.m_startDate;
/*    */   }
/*    */ 
/*    */   public void setStartDate(Date a_startDate) {
/* 44 */     this.m_startDate = a_startDate;
/*    */   }
/*    */ 
/*    */   public String getEmail()
/*    */   {
/* 49 */     return this.m_sEmail;
/*    */   }
/*    */ 
/*    */   public void setEmail(String a_sEmail) {
/* 53 */     this.m_sEmail = a_sEmail;
/*    */   }
/*    */ 
/*    */   public boolean isActive()
/*    */   {
/* 58 */     return this.m_bActive;
/*    */   }
/*    */ 
/*    */   public void setActive(boolean a_bActive) {
/* 62 */     this.m_bActive = a_bActive;
/*    */   }
/*    */ 
/*    */   public BrightMoney getPricePaid()
/*    */   {
/* 67 */     return this.m_pricePaid;
/*    */   }
/*    */ 
/*    */   public void setPricePaid(BrightMoney a_pricePaid) {
/* 71 */     this.m_pricePaid = a_pricePaid;
/*    */   }
/*    */ 
/*    */   public String getSubscriptionModel()
/*    */   {
/* 76 */     return this.m_sSubscriptionModel;
/*    */   }
/*    */ 
/*    */   public void setSubscriptionModel(String a_sSubscriptionModel) {
/* 80 */     this.m_sSubscriptionModel = a_sSubscriptionModel;
/*    */   }
/*    */ 
/*    */   public BrightMoney getTotalPaid()
/*    */   {
/* 85 */     if (this.m_totalPaid == null)
/*    */     {
/* 87 */       return new BrightMoney();
/*    */     }
/*    */ 
/* 90 */     return this.m_totalPaid;
/*    */   }
/*    */ 
/*    */   public void setTotalPaid(BrightMoney a_totalPaid)
/*    */   {
/* 95 */     this.m_totalPaid = a_totalPaid;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.bean.UserSubscriptionReportRecord
 * JD-Core Version:    0.6.0
 */