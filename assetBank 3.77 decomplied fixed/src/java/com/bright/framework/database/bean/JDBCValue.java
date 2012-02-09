/*    */ package com.bright.framework.database.bean;
/*    */ 
/*    */ public class JDBCValue
/*    */ {
/*    */   private int m_iJDBCType;
/*    */   private Object m_value;
/*    */ 
/*    */   public JDBCValue()
/*    */   {
/*    */   }
/*    */ 
/*    */   public JDBCValue(int a_iJDBCType, Object a_value)
/*    */   {
/* 36 */     this.m_iJDBCType = a_iJDBCType;
/* 37 */     this.m_value = a_value;
/*    */   }
/*    */ 
/*    */   public int getJDBCType()
/*    */   {
/* 42 */     return this.m_iJDBCType;
/*    */   }
/*    */ 
/*    */   public void setJDBCType(int a_jdbcType)
/*    */   {
/* 47 */     this.m_iJDBCType = a_jdbcType;
/*    */   }
/*    */ 
/*    */   public Object getValue()
/*    */   {
/* 52 */     return this.m_value;
/*    */   }
/*    */ 
/*    */   public void setValue(Object a_value)
/*    */   {
/* 57 */     this.m_value = a_value;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.bean.JDBCValue
 * JD-Core Version:    0.6.0
 */