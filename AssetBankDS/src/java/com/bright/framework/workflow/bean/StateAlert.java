/*     */ package com.bright.framework.workflow.bean;
/*     */ 
/*     */ public class StateAlert
/*     */ {
/*     */   private String m_sName;
/*  30 */   private String m_handlerClassName = "";
/*     */   private String m_sendTo;
/* 101 */   private String m_sTemplate = "";
/*     */ 
/*     */   public String getHandlerClassName()
/*     */   {
/*  39 */     return this.m_handlerClassName;
/*     */   }
/*     */ 
/*     */   public void setHandlerClassName(String a_sHandlerClassName)
/*     */   {
/*  49 */     this.m_handlerClassName = a_sHandlerClassName;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  59 */     return this.m_sName;
/*     */   }
/*     */ 
/*     */   public void setName(String a_sName)
/*     */   {
/*  69 */     this.m_sName = a_sName;
/*     */   }
/*     */ 
/*     */   public String getSendTo()
/*     */   {
/*  85 */     return this.m_sendTo;
/*     */   }
/*     */ 
/*     */   public void setSendTo(String a_sSendTo)
/*     */   {
/*  95 */     this.m_sendTo = a_sSendTo;
/*     */   }
/*     */ 
/*     */   public String getTemplate()
/*     */   {
/* 110 */     return this.m_sTemplate;
/*     */   }
/*     */ 
/*     */   public void setTemplate(String a_sTemplate)
/*     */   {
/* 120 */     this.m_sTemplate = a_sTemplate;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.workflow.bean.StateAlert
 * JD-Core Version:    0.6.0
 */