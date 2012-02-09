/*     */ package com.bright.framework.workflow.bean;
/*     */ 
/*     */ import java.util.Date;
/*     */ 
/*     */ public class CompletedTransition
/*     */ {
/*  30 */   private String m_sFromStateName = null;
/*     */ 
/*  34 */   private String m_sToStateName = null;
/*     */ 
/*  39 */   private Date m_transitionDate = null;
/*     */ 
/*  44 */   private long m_lUserId = 0L;
/*     */ 
/*  48 */   private long m_lWorkflowableItemId = 0L;
/*     */ 
/*     */   public String getFromStateName()
/*     */   {
/*  55 */     return this.m_sFromStateName;
/*     */   }
/*     */ 
/*     */   public void setFromStateName(String a_sFromStateName)
/*     */   {
/*  63 */     this.m_sFromStateName = a_sFromStateName;
/*     */   }
/*     */ 
/*     */   public String getToStateName()
/*     */   {
/*  71 */     return this.m_sToStateName;
/*     */   }
/*     */ 
/*     */   public void setToStateName(String a_sToStateName)
/*     */   {
/*  79 */     this.m_sToStateName = a_sToStateName;
/*     */   }
/*     */ 
/*     */   public Date getTransitionDate()
/*     */   {
/*  87 */     return this.m_transitionDate;
/*     */   }
/*     */ 
/*     */   public void setTransitionDate(Date a_dtTransitionDate)
/*     */   {
/*  95 */     this.m_transitionDate = a_dtTransitionDate;
/*     */   }
/*     */ 
/*     */   public long getUserId()
/*     */   {
/* 103 */     return this.m_lUserId;
/*     */   }
/*     */ 
/*     */   public void setUserId(long a_lUserId)
/*     */   {
/* 111 */     this.m_lUserId = a_lUserId;
/*     */   }
/*     */ 
/*     */   public long getWorkflowableItemId()
/*     */   {
/* 119 */     return this.m_lWorkflowableItemId;
/*     */   }
/*     */ 
/*     */   public void setWorkflowableItemId(long a_lWorkflowableItemId)
/*     */   {
/* 127 */     this.m_lWorkflowableItemId = a_lWorkflowableItemId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.workflow.bean.CompletedTransition
 * JD-Core Version:    0.6.0
 */