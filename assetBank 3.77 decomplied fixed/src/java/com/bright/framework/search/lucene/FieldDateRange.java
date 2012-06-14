/*    */ package com.bright.framework.search.lucene;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class FieldDateRange extends FieldRange
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 3515072875865040156L;
/* 31 */   private long m_lLower = 0L;
/* 32 */   private long m_lUpper = 0L;
/*    */ 
/*    */   public long getLower()
/*    */   {
/* 36 */     return this.m_lLower;
/*    */   }
/*    */ 
/*    */   public void setLower(long a_lLower) {
/* 40 */     this.m_lLower = a_lLower;
/*    */   }
/*    */ 
/*    */   public long getUpper() {
/* 44 */     return this.m_lUpper;
/*    */   }
/*    */ 
/*    */   public void setUpper(long a_lUpper)
/*    */   {
/* 49 */     this.m_lUpper = a_lUpper;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.FieldDateRange
 * JD-Core Version:    0.6.0
 */