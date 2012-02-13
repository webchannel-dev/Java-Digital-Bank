/*    */ package com.bright.assetbank.ecommerce.form;
/*    */ 
/*    */ import com.bright.framework.common.bean.BrightMoney;
/*    */ 
/*    */ public class ReportForm extends com.bright.assetbank.usage.form.ReportForm
/*    */ {
/* 29 */   private BrightMoney m_total = null;
/* 30 */   private boolean m_bShowActiveSubscriptions = false;
/* 31 */   private boolean m_bShowActiveSubscriptionModels = false;
/*    */ 
/*    */   public void setTotal(BrightMoney a_total)
/*    */   {
/* 35 */     this.m_total = a_total;
/*    */   }
/*    */ 
/*    */   public BrightMoney getTotal()
/*    */   {
/* 40 */     return this.m_total;
/*    */   }
/*    */ 
/*    */   public void setShowActiveSubscriptions(boolean a_bShowActiveSubscriptions)
/*    */   {
/* 45 */     this.m_bShowActiveSubscriptions = a_bShowActiveSubscriptions;
/*    */   }
/*    */ 
/*    */   public boolean getShowActiveSubscriptions() {
/* 49 */     return this.m_bShowActiveSubscriptions;
/*    */   }
/*    */ 
/*    */   public void setShowActiveSubscriptionModels(boolean a_bShowActiveSubscriptionModels)
/*    */   {
/* 54 */     this.m_bShowActiveSubscriptionModels = a_bShowActiveSubscriptionModels;
/*    */   }
/*    */ 
/*    */   public boolean getShowActiveSubscriptionModels() {
/* 58 */     return this.m_bShowActiveSubscriptionModels;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.form.ReportForm
 * JD-Core Version:    0.6.0
 */