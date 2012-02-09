/*    */ package com.bright.assetbank.priceband.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.StringDataBean;
/*    */ 
/*    */ public class QuantityRange extends StringDataBean
/*    */ {
/* 29 */   private long m_lLowerLimit = 0L;
/*    */ 
/*    */   public long getLowerLimit()
/*    */   {
/* 38 */     return this.m_lLowerLimit;
/*    */   }
/*    */ 
/*    */   public void setLowerLimit(long a_sLowerLimit) {
/* 42 */     this.m_lLowerLimit = a_sLowerLimit;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.priceband.bean.QuantityRange
 * JD-Core Version:    0.6.0
 */