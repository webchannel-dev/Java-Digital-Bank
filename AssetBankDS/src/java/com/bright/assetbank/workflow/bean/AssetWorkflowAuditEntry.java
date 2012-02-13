/*     */ package com.bright.assetbank.workflow.bean;
/*     */ 
/*     */ import java.util.Date;
/*     */ 
/*     */ public class AssetWorkflowAuditEntry
/*     */ {
/*  26 */   private long m_lId = 0L;
/*  27 */   private long m_lUserId = 0L;
/*  28 */   private long m_lAssetId = 0L;
/*     */ 
/*  30 */   private String m_sName = "";
/*  31 */   private String m_sUsername = "";
/*     */ 
/*  33 */   private String m_sMessage = "";
/*  34 */   private Date m_dtDateAdded = null;
/*  35 */   private String m_sTransition = "";
/*  36 */   private String m_sWorkflowName = null;
/*     */ 
/*     */   public long getId()
/*     */   {
/*  41 */     return this.m_lId;
/*     */   }
/*     */ 
/*     */   public void setId(long a_lId) {
/*  45 */     this.m_lId = a_lId;
/*     */   }
/*     */ 
/*     */   public long getUserId()
/*     */   {
/*  50 */     return this.m_lUserId;
/*     */   }
/*     */ 
/*     */   public void setUserId(long a_lUserId) {
/*  54 */     this.m_lUserId = a_lUserId;
/*     */   }
/*     */ 
/*     */   public long getAssetId()
/*     */   {
/*  59 */     return this.m_lAssetId;
/*     */   }
/*     */ 
/*     */   public void setAssetId(long a_lAssetId) {
/*  63 */     this.m_lAssetId = a_lAssetId;
/*     */   }
/*     */ 
/*     */   public String getMessage()
/*     */   {
/*  68 */     return this.m_sMessage;
/*     */   }
/*     */ 
/*     */   public void setMessage(String a_sMessage) {
/*  72 */     this.m_sMessage = a_sMessage;
/*     */   }
/*     */ 
/*     */   public Date getDateAdded()
/*     */   {
/*  77 */     return this.m_dtDateAdded;
/*     */   }
/*     */ 
/*     */   public void setDateAdded(Date a_dtDateAdded) {
/*  81 */     this.m_dtDateAdded = a_dtDateAdded;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  86 */     return this.m_sName;
/*     */   }
/*     */ 
/*     */   public void setName(String a_sName) {
/*  90 */     this.m_sName = a_sName;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/*  95 */     return this.m_sUsername;
/*     */   }
/*     */ 
/*     */   public void setUsername(String a_sUsername) {
/*  99 */     this.m_sUsername = a_sUsername;
/*     */   }
/*     */ 
/*     */   public String getTransition() {
/* 103 */     return this.m_sTransition;
/*     */   }
/*     */ 
/*     */   public void setTransition(String a_sTransition) {
/* 107 */     this.m_sTransition = a_sTransition;
/*     */   }
/*     */ 
/*     */   public void setWorkflowName(String a_sWorkflowName)
/*     */   {
/* 112 */     this.m_sWorkflowName = a_sWorkflowName;
/*     */   }
/*     */ 
/*     */   public String getWorkflowName()
/*     */   {
/* 117 */     return this.m_sWorkflowName;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.bean.AssetWorkflowAuditEntry
 * JD-Core Version:    0.6.0
 */