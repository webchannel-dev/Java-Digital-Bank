/*     */ package com.bright.assetbank.batch.update.form;
/*     */ 
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.workflow.bean.AssetWorkflowAuditEntry;
/*     */ import com.bright.framework.workflow.bean.State;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class BatchAssetForm extends AssetForm
/*     */ {
/*  39 */   private int m_iNumberLeftInBatch = 0;
/*     */ 
/*  41 */   private String m_sAddedByUser = null;
/*  42 */   private String m_sAddedDate = null;
/*     */   private AssetWorkflowAuditEntry m_auditEntry;
/*  46 */   private long m_lSelectedTransition = -1L;
/*  47 */   private Vector m_vecTransitions = null;
/*  48 */   private State m_state = null;
/*     */ 
/*  50 */   private boolean m_bApprovalMode = false;
/*  51 */   private boolean m_bUnsubmittedMode = false;
/*  52 */   private boolean m_bOwnerMode = false;
/*     */ 
/*     */   public int getNumberLeftInBatch()
/*     */   {
/*  56 */     return this.m_iNumberLeftInBatch;
/*     */   }
/*     */ 
/*     */   public void setNumberLeftInBatch(int a_vecNumberLeftInBatch) {
/*  60 */     this.m_iNumberLeftInBatch = a_vecNumberLeftInBatch;
/*     */   }
/*     */ 
/*     */   public String getAddedByUser() {
/*  64 */     return this.m_sAddedByUser;
/*     */   }
/*     */ 
/*     */   public void setAddedByUser(String a_sAddedByUser) {
/*  68 */     this.m_sAddedByUser = a_sAddedByUser;
/*     */   }
/*     */ 
/*     */   public String getAddedDate() {
/*  72 */     return this.m_sAddedDate;
/*     */   }
/*     */ 
/*     */   public void setAddedDate(String a_sAddedDate) {
/*  76 */     this.m_sAddedDate = a_sAddedDate;
/*     */   }
/*     */ 
/*     */   public Vector getTransitions() {
/*  80 */     return this.m_vecTransitions;
/*     */   }
/*     */ 
/*     */   public void setTransitions(Vector a_vecTransitions) {
/*  84 */     this.m_vecTransitions = a_vecTransitions;
/*     */   }
/*     */ 
/*     */   public AssetWorkflowAuditEntry getAuditEntry()
/*     */   {
/*  89 */     return this.m_auditEntry;
/*     */   }
/*     */ 
/*     */   public void setAuditEntry(AssetWorkflowAuditEntry a_auditEntry) {
/*  93 */     this.m_auditEntry = a_auditEntry;
/*     */   }
/*     */ 
/*     */   public boolean getApprovalMode()
/*     */   {
/*  98 */     return this.m_bApprovalMode;
/*     */   }
/*     */ 
/*     */   public void setApprovalMode(boolean a_bApprovalMode) {
/* 102 */     this.m_bApprovalMode = a_bApprovalMode;
/*     */   }
/*     */ 
/*     */   public boolean getUnsubmittedMode()
/*     */   {
/* 107 */     return this.m_bUnsubmittedMode;
/*     */   }
/*     */ 
/*     */   public void setUnsubmittedMode(boolean a_sUnsubmittedMode) {
/* 111 */     this.m_bUnsubmittedMode = a_sUnsubmittedMode;
/*     */   }
/*     */ 
/*     */   public boolean getOwnerMode()
/*     */   {
/* 116 */     return this.m_bOwnerMode;
/*     */   }
/*     */ 
/*     */   public void setOwnerMode(boolean a_sOwnerMode) {
/* 120 */     this.m_bOwnerMode = a_sOwnerMode;
/*     */   }
/*     */ 
/*     */   public long getSelectedTransition() {
/* 124 */     return this.m_lSelectedTransition;
/*     */   }
/*     */ 
/*     */   public void setSelectedTransition(long a_lSelectedTransition) {
/* 128 */     this.m_lSelectedTransition = a_lSelectedTransition;
/*     */   }
/*     */ 
/*     */   public State getState() {
/* 132 */     return this.m_state;
/*     */   }
/*     */ 
/*     */   public void setState(State a_sState) {
/* 136 */     this.m_state = a_sState;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.form.BatchAssetForm
 * JD-Core Version:    0.6.0
 */