/*     */ package com.bright.framework.workflow.bean;
/*     */ 
/*     */ public class Transition
/*     */ {
/*  29 */   private String m_sName = null;
/*     */ 
/*  34 */   private String m_sDescription = null;
/*     */ 
/*  39 */   private String m_sHelpText = null;
/*     */ 
/*  44 */   private String m_sNextStateName = null;
/*     */ 
/*  49 */   private int m_iTransitionNumber = 0;
/*     */ 
/*  54 */   private String m_sConfirmationText = null;
/*     */   private boolean m_hasMessage;
/*     */   private boolean m_messageMandatory;
/*     */ 
/*     */   public String getName()
/*     */   {
/*  64 */     return this.m_sName;
/*     */   }
/*     */ 
/*     */   public void setName(String a_sName)
/*     */   {
/*  72 */     this.m_sName = a_sName;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  80 */     return this.m_sDescription;
/*     */   }
/*     */ 
/*     */   public void setDescription(String a_sDescription)
/*     */   {
/*  88 */     this.m_sDescription = a_sDescription;
/*     */   }
/*     */ 
/*     */   public String getHelpText()
/*     */   {
/*  96 */     return this.m_sHelpText;
/*     */   }
/*     */ 
/*     */   public void setHelpText(String a_sHelpText)
/*     */   {
/* 104 */     this.m_sHelpText = a_sHelpText;
/*     */   }
/*     */ 
/*     */   public String getNextStateName()
/*     */   {
/* 112 */     return this.m_sNextStateName;
/*     */   }
/*     */ 
/*     */   public void setNextStateName(String a_sNextStateName)
/*     */   {
/* 120 */     this.m_sNextStateName = a_sNextStateName;
/*     */   }
/*     */ 
/*     */   public int getTransitionNumber()
/*     */   {
/* 128 */     return this.m_iTransitionNumber;
/*     */   }
/*     */ 
/*     */   public void setTransitionNumber(int a_iTransitionNumber)
/*     */   {
/* 136 */     this.m_iTransitionNumber = a_iTransitionNumber;
/*     */   }
/*     */ 
/*     */   public void setConfirmationText(String a_sConfirmationText)
/*     */   {
/* 144 */     this.m_sConfirmationText = a_sConfirmationText;
/*     */   }
/*     */ 
/*     */   public String getConfirmationText()
/*     */   {
/* 152 */     return this.m_sConfirmationText;
/*     */   }
/*     */ 
/*     */   public boolean getHasMessage()
/*     */   {
/* 170 */     return this.m_hasMessage;
/*     */   }
/*     */ 
/*     */   public void setHasMessage(boolean a_sHasMessage)
/*     */   {
/* 180 */     this.m_hasMessage = a_sHasMessage;
/*     */   }
/*     */ 
/*     */   public boolean getMessageMandatory()
/*     */   {
/* 198 */     return this.m_messageMandatory;
/*     */   }
/*     */ 
/*     */   public void setMessageMandatory(boolean a_sMessageMandatory)
/*     */   {
/* 208 */     this.m_messageMandatory = a_sMessageMandatory;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.workflow.bean.Transition
 * JD-Core Version:    0.6.0
 */