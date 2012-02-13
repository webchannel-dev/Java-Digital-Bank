/*    */ package com.bright.framework.common.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class StringBean
/*    */   implements Serializable
/*    */ {
/*    */   private String m_sValue;
/*    */ 
/*    */   public StringBean()
/*    */   {
/* 39 */     this.m_sValue = "";
/*    */   }
/*    */ 
/*    */   public StringBean(String a_sValue)
/*    */   {
/* 44 */     this.m_sValue = a_sValue;
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 53 */     return this.m_sValue;
/*    */   }
/*    */ 
/*    */   public void setValue(String a_sValue)
/*    */   {
/* 62 */     this.m_sValue = a_sValue;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.StringBean
 * JD-Core Version:    0.6.0
 */