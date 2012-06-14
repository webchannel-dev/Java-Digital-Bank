/*    */ package com.bright.framework.workflow.bean;
/*    */ 
/*    */ public class StateEvent
/*    */ {
/* 31 */   private String m_sName = "";
/*    */ 
/* 56 */   private String m_handlerClassName = "";
/*    */ 
/*    */   public String getName()
/*    */   {
/* 40 */     return this.m_sName;
/*    */   }
/*    */ 
/*    */   public void setName(String a_sName)
/*    */   {
/* 50 */     this.m_sName = a_sName;
/*    */   }
/*    */ 
/*    */   public String getHandlerClassName()
/*    */   {
/* 65 */     return this.m_handlerClassName;
/*    */   }
/*    */ 
/*    */   public void setHandlerClassName(String a_sHandlerClassName)
/*    */   {
/* 75 */     this.m_handlerClassName = a_sHandlerClassName;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.workflow.bean.StateEvent
 * JD-Core Version:    0.6.0
 */