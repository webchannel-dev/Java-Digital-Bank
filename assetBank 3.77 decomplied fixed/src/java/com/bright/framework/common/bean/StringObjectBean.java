/*    */ package com.bright.framework.common.bean;
/*    */ 
/*    */ public class StringObjectBean extends StringBean
/*    */ {
/* 22 */   private Object m_object = null;
/*    */ 
/*    */   public StringObjectBean(String a_sValue, Object a_object)
/*    */   {
/* 26 */     super(a_sValue);
/* 27 */     this.m_object = a_object;
/*    */   }
/*    */ 
/*    */   public Object getObject()
/*    */   {
/* 32 */     return this.m_object;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.StringObjectBean
 * JD-Core Version:    0.6.0
 */