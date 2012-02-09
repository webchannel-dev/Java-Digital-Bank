/*    */ package com.bright.framework.search.lucene;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class FieldNumericRange extends FieldRange
/*    */   implements Serializable
/*    */ {
/* 29 */   private Number m_nLower = Integer.valueOf(-1);
/* 30 */   private Number m_nUpper = Integer.valueOf(-1);
/*    */ 
/*    */   public Number getLower()
/*    */   {
/* 34 */     return this.m_nLower;
/*    */   }
/*    */ 
/*    */   public void setLower(Number a_nLower) {
/* 38 */     this.m_nLower = a_nLower;
/*    */   }
/*    */ 
/*    */   public Number getUpper() {
/* 42 */     return this.m_nUpper;
/*    */   }
/*    */ 
/*    */   public void setUpper(Number a_nUpper)
/*    */   {
/* 47 */     this.m_nUpper = a_nUpper;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.FieldNumericRange
 * JD-Core Version:    0.6.0
 */