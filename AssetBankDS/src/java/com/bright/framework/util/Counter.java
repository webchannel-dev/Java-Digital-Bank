/*    */ package com.bright.framework.util;
/*    */ 
/*    */ public class Counter
/*    */ {
/*    */   private int m_iValue;
/*    */ 
/*    */   public Counter(int a_iValue)
/*    */   {
/* 29 */     this.m_iValue = a_iValue;
/*    */   }
/*    */ 
/*    */   public Counter()
/*    */   {
/* 34 */     this(0);
/*    */   }
/*    */ 
/*    */   public int getValue()
/*    */   {
/* 39 */     return this.m_iValue;
/*    */   }
/*    */ 
/*    */   public void increment()
/*    */   {
/* 44 */     this.m_iValue += 1;
/*    */   }
/*    */ 
/*    */   public void skip(int a_i)
/*    */   {
/* 49 */     this.m_iValue += a_i;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.Counter
 * JD-Core Version:    0.6.0
 */