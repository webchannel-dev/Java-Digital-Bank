/*     */ package com.bright.framework.workflow.bean;
/*     */ 
/*     */ import java.util.Date;
/*     */ 
/*     */ public class WorkflowInfo
/*     */ {
/*     */   private long m_entityId;
/*  38 */   private String m_sWorkflowName = null;
/*     */ 
/*  43 */   private String m_stateName = "";
/*     */ 
/*  48 */   private State m_state = null;
/*     */ 
/*  53 */   private String m_variationName = "";
/*     */ 
/*  58 */   private long m_sId = 0L;
/*     */ 
/*  63 */   private Date m_lastStateChangeDate = null;
/*     */ 
/*     */   public WorkflowInfo()
/*     */   {
/*  72 */     this.m_state = new State();
/*  73 */     this.m_lastStateChangeDate = new Date();
/*     */   }
/*     */ 
/*     */   public void setWorkflowName(String a_sWorkflowName)
/*     */   {
/*  82 */     this.m_sWorkflowName = a_sWorkflowName;
/*     */   }
/*     */ 
/*     */   public String getWorkflowName()
/*     */   {
/*  90 */     return this.m_sWorkflowName;
/*     */   }
/*     */ 
/*     */   public void setState(State a_state)
/*     */   {
/*  98 */     this.m_state = a_state;
/*     */   }
/*     */ 
/*     */   public State getState()
/*     */   {
/* 106 */     return this.m_state;
/*     */   }
/*     */ 
/*     */   public String getVariationName()
/*     */   {
/* 116 */     return this.m_variationName;
/*     */   }
/*     */ 
/*     */   public void setVariationName(String a_sVariationName)
/*     */   {
/* 126 */     this.m_variationName = a_sVariationName;
/*     */   }
/*     */ 
/*     */   public long getId()
/*     */   {
/* 134 */     return this.m_sId;
/*     */   }
/*     */ 
/*     */   public void setId(long a_sId)
/*     */   {
/* 143 */     this.m_sId = a_sId;
/*     */   }
/*     */ 
/*     */   public String getStateName()
/*     */   {
/* 152 */     return this.m_stateName;
/*     */   }
/*     */ 
/*     */   public void setStateName(String a_sStateName)
/*     */   {
/* 161 */     this.m_stateName = a_sStateName;
/*     */   }
/*     */ 
/*     */   public long getEntityId()
/*     */   {
/* 170 */     return this.m_entityId;
/*     */   }
/*     */ 
/*     */   public void setEntityId(long a_sEntityId)
/*     */   {
/* 179 */     this.m_entityId = a_sEntityId;
/*     */   }
/*     */ 
/*     */   public Date getLastStateChangeDate()
/*     */   {
/* 190 */     return this.m_lastStateChangeDate;
/*     */   }
/*     */ 
/*     */   public void setLastStateChangeDate(Date a_sLastStateChangeDate)
/*     */   {
/* 201 */     this.m_lastStateChangeDate = a_sLastStateChangeDate;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.workflow.bean.WorkflowInfo
 * JD-Core Version:    0.6.0
 */