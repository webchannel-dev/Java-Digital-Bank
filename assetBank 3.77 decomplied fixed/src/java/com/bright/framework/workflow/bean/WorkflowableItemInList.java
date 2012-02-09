/*     */ package com.bright.framework.workflow.bean;
/*     */ 
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ 
/*     */ public class WorkflowableItemInList extends DataBean
/*     */ {
/*  33 */   private long m_lId = 0L;
/*     */ 
/*  37 */   private long m_lWorkflowableItemId = 0L;
/*     */ 
/*  41 */   private String m_sOwnerForename = null;
/*     */ 
/*  45 */   private String m_sOwnerSurname = null;
/*     */ 
/*  49 */   private String m_sStateName = null;
/*     */ 
/*  53 */   private String m_sStateDescription = null;
/*     */ 
/*  57 */   private String m_sCounterpartStateDescription = null;
/*     */ 
/*  61 */   private String m_sActionUrl = null;
/*     */ 
/*  65 */   private String m_sCounterpartActionUrl = null;
/*     */ 
/*  69 */   private String m_sViewActionUrl = null;
/*     */ 
/*  73 */   private String m_sCounterpartViewActionUrl = null;
/*     */ 
/*     */   public void setId(long a_lId)
/*     */   {
/*  82 */     this.m_lId = a_lId;
/*     */   }
/*     */ 
/*     */   public long getId()
/*     */   {
/*  92 */     return this.m_lId;
/*     */   }
/*     */ 
/*     */   public void setWorkflowableItemId(long a_lWorkflowableItemId)
/*     */   {
/* 102 */     this.m_lWorkflowableItemId = a_lWorkflowableItemId;
/*     */   }
/*     */ 
/*     */   public long getWorkflowableItemId()
/*     */   {
/* 112 */     return this.m_lWorkflowableItemId;
/*     */   }
/*     */ 
/*     */   public void setOwnerForename(String a_sOwnerForename)
/*     */   {
/* 122 */     this.m_sOwnerForename = a_sOwnerForename;
/*     */   }
/*     */ 
/*     */   public String getOwnerForename()
/*     */   {
/* 132 */     return this.m_sOwnerForename;
/*     */   }
/*     */ 
/*     */   public void setOwnerSurname(String a_sOwnerSurname)
/*     */   {
/* 142 */     this.m_sOwnerSurname = a_sOwnerSurname;
/*     */   }
/*     */ 
/*     */   public String getOwnerSurname()
/*     */   {
/* 152 */     return this.m_sOwnerSurname;
/*     */   }
/*     */ 
/*     */   public String getStateName()
/*     */   {
/* 162 */     return this.m_sStateName;
/*     */   }
/*     */ 
/*     */   public void setStateName(String a_sStateName)
/*     */   {
/* 172 */     this.m_sStateName = a_sStateName;
/*     */   }
/*     */ 
/*     */   public String getStateDescription()
/*     */   {
/* 182 */     return this.m_sStateDescription;
/*     */   }
/*     */ 
/*     */   public void setStateDescription(String a_sStateDescription)
/*     */   {
/* 192 */     this.m_sStateDescription = a_sStateDescription;
/*     */   }
/*     */ 
/*     */   public String getCounterpartStateDescription()
/*     */   {
/* 202 */     return this.m_sCounterpartStateDescription;
/*     */   }
/*     */ 
/*     */   public void setCounterpartStateDescription(String a_sCounterpartStateDescription)
/*     */   {
/* 212 */     this.m_sCounterpartStateDescription = a_sCounterpartStateDescription;
/*     */   }
/*     */ 
/*     */   public String getActionUrl()
/*     */   {
/* 222 */     return this.m_sActionUrl;
/*     */   }
/*     */ 
/*     */   public void setActionUrl(String a_sActionUrl)
/*     */   {
/* 232 */     this.m_sActionUrl = a_sActionUrl;
/*     */   }
/*     */ 
/*     */   public String getViewActionUrl()
/*     */   {
/* 243 */     return this.m_sViewActionUrl;
/*     */   }
/*     */ 
/*     */   public void setViewActionUrl(String a_sViewActionUrl)
/*     */   {
/* 253 */     this.m_sViewActionUrl = a_sViewActionUrl;
/*     */   }
/*     */ 
/*     */   public String getCounterpartActionUrl()
/*     */   {
/* 263 */     return this.m_sCounterpartActionUrl;
/*     */   }
/*     */ 
/*     */   public void setCounterpartActionUrl(String a_sCounterpartActionUrl)
/*     */   {
/* 273 */     this.m_sCounterpartActionUrl = a_sCounterpartActionUrl;
/*     */   }
/*     */ 
/*     */   public String getCounterpartViewActionUrl()
/*     */   {
/* 284 */     return this.m_sCounterpartViewActionUrl;
/*     */   }
/*     */ 
/*     */   public void setCounterpartViewActionUrl(String a_sCounterpartViewActionUrl)
/*     */   {
/* 294 */     this.m_sCounterpartViewActionUrl = a_sCounterpartViewActionUrl;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.workflow.bean.WorkflowableItemInList
 * JD-Core Version:    0.6.0
 */