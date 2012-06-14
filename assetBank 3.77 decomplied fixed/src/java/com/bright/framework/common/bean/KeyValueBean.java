/*    */ package com.bright.framework.common.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class KeyValueBean extends StringBean
/*    */   implements Serializable
/*    */ {
/* 31 */   private String m_sKey = null;
/*    */ 
/*    */   public KeyValueBean()
/*    */   {
/* 36 */     this.m_sKey = "";
/*    */   }
/*    */ 
/*    */   private KeyValueBean(String a_sKey)
/*    */   {
/* 42 */     super(a_sKey);
/*    */   }
/*    */ 
/*    */   public KeyValueBean(String a_sKey, String a_sValue)
/*    */   {
/* 48 */     this(a_sValue);
/* 49 */     setKey(a_sKey);
/*    */   }
/*    */ 
/*    */   public String getKey()
/*    */   {
/* 55 */     return this.m_sKey;
/*    */   }
/*    */ 
/*    */   public void setKey(String a_sKey)
/*    */   {
/* 60 */     this.m_sKey = a_sKey;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.KeyValueBean
 * JD-Core Version:    0.6.0
 */