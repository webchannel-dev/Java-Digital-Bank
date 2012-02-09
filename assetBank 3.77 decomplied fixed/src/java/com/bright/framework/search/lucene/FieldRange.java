/*    */ package com.bright.framework.search.lucene;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public abstract class FieldRange
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 3430225172531647396L;
/* 31 */   private String m_sFieldName = null;
/*    */ 
/*    */   public String getFieldName()
/*    */   {
/* 35 */     return this.m_sFieldName;
/*    */   }
/*    */ 
/*    */   public void setFieldName(String a_sFieldName) {
/* 39 */     this.m_sFieldName = a_sFieldName;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.FieldRange
 * JD-Core Version:    0.6.0
 */