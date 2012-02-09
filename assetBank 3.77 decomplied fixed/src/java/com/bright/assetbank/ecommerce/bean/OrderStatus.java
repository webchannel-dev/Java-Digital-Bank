/*    */ package com.bright.assetbank.ecommerce.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.StringDataBean;
/*    */ import com.bright.framework.common.bean.TranslatableStringDataBean;
/*    */ 
/*    */ public class OrderStatus extends TranslatableStringDataBean
/*    */ {
/* 28 */   private StringDataBean m_orderWorkflow = null;
/*    */ 
/* 30 */   private boolean m_bManualSelect = false;
/*    */ 
/*    */   public boolean isManualSelect()
/*    */   {
/* 37 */     return this.m_bManualSelect;
/*    */   }
/*    */ 
/*    */   public void setManualSelect(boolean a_sManualSelect)
/*    */   {
/* 45 */     this.m_bManualSelect = a_sManualSelect;
/*    */   }
/*    */ 
/*    */   public StringDataBean getOrderWorkflow()
/*    */   {
/* 53 */     if (this.m_orderWorkflow == null)
/*    */     {
/* 55 */       this.m_orderWorkflow = new StringDataBean();
/*    */     }
/* 57 */     return this.m_orderWorkflow;
/*    */   }
/*    */ 
/*    */   public void setOrderWorkflow(StringDataBean a_sOrderWorkflow)
/*    */   {
/* 65 */     this.m_orderWorkflow = a_sOrderWorkflow;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.bean.OrderStatus
 * JD-Core Version:    0.6.0
 */