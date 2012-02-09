/*     */ package com.bright.assetbank.workflow.bean;
/*     */ 
/*     */ import com.bright.assetbank.workflow.util.SubmitOptionForApprovalPredicate;
/*     */ import com.bright.assetbank.workflow.util.SubmitOptionLeaveInCurrentStatePredicate;
/*     */ import com.bright.assetbank.workflow.util.SubmitOptionPredicate;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class WorkflowUpdate
/*     */ {
/*  38 */   private int m_iUpdateType = -1;
/*  39 */   private HashMap<String, Integer> m_hmWorkflowApprovalUpdates = null;
/*  40 */   private boolean m_bSetSubmitted = false;
/*  41 */   private boolean m_bSetUnsubmitted = false;
/*  42 */   private Vector<Category> m_vecMissingCategories = null;
/*     */ 
/*  44 */   private long m_lUserId = -1L;
/*  45 */   private long m_lSessionId = -1L;
/*     */ 
/*     */   public WorkflowUpdate(long a_lUserId, long a_lSessionId)
/*     */   {
/*  34 */     this.m_lUserId = a_lUserId;
/*  35 */     this.m_lSessionId = a_lSessionId;
/*     */   }
/*     */ 
/*     */   public int getUpdateType()
/*     */   {
/*  49 */     return this.m_iUpdateType;
/*     */   }
/*     */ 
/*     */   public void setUpdateType(int a_iUpdateType)
/*     */   {
/*  54 */     this.m_iUpdateType = a_iUpdateType;
/*     */   }
/*     */ 
/*     */   public HashMap<String, Integer> getWorkflowApprovalUpdates()
/*     */   {
/*  59 */     return this.m_hmWorkflowApprovalUpdates;
/*     */   }
/*     */ 
/*     */   public void setWorkflowApprovalUpdates(HashMap<String, Integer> a_hmWorkflowApprovalUpdates)
/*     */   {
/*  64 */     this.m_hmWorkflowApprovalUpdates = a_hmWorkflowApprovalUpdates;
/*     */   }
/*     */ 
/*     */   public void setSetSubmitted(boolean a_bSetSubmitted)
/*     */   {
/*  69 */     this.m_bSetSubmitted = a_bSetSubmitted;
/*     */   }
/*     */ 
/*     */   public boolean getSetSubmitted()
/*     */   {
/*  74 */     return this.m_bSetSubmitted;
/*     */   }
/*     */ 
/*     */   public void setSetUnsubmitted(boolean a_bSetUnsubmitted)
/*     */   {
/*  79 */     this.m_bSetUnsubmitted = a_bSetUnsubmitted;
/*     */   }
/*     */ 
/*     */   public boolean getSetUnsubmitted()
/*     */   {
/*  84 */     return this.m_bSetUnsubmitted;
/*     */   }
/*     */ 
/*     */   public long getUserId()
/*     */   {
/*  89 */     return this.m_lUserId;
/*     */   }
/*     */ 
/*     */   public long getSessionId()
/*     */   {
/*  94 */     return this.m_lSessionId;
/*     */   }
/*     */ 
/*     */   public void setMissingCategories(Vector<Category> a_vecMissingCategories)
/*     */   {
/*  99 */     this.m_vecMissingCategories = a_vecMissingCategories;
/*     */   }
/*     */ 
/*     */   public Vector<Category> getMissingCategories()
/*     */   {
/* 104 */     return this.m_vecMissingCategories;
/*     */   }
/*     */ 
/*     */   public boolean getLeavingAllWorkflowsInCurrentState(Collection<String> a_workflows)
/*     */   {
/* 109 */     SubmitOptionLeaveInCurrentStatePredicate pred = new SubmitOptionLeaveInCurrentStatePredicate();
/* 110 */     return getWorkflowsMatchSubmitOption(a_workflows, pred);
/*     */   }
/*     */ 
/*     */   public boolean getUnnapprovingAllWorkflows(Collection<String> a_workflows)
/*     */   {
/* 115 */     SubmitOptionForApprovalPredicate pred = new SubmitOptionForApprovalPredicate();
/* 116 */     return getWorkflowsMatchSubmitOption(a_workflows, pred);
/*     */   }
/*     */ 
/*     */   private boolean getWorkflowsMatchSubmitOption(Collection<String> a_workflows, SubmitOptionPredicate a_predicate)
/*     */   {
/* 121 */     if ((this.m_hmWorkflowApprovalUpdates != null) && (a_workflows != null))
/*     */     {
/* 123 */       Set keys = this.m_hmWorkflowApprovalUpdates.keySet();
/* 124 */       Iterator it = keys.iterator();
/* 125 */       while (it.hasNext())
/*     */       {
/* 127 */         String sKey = (String)it.next();
/* 128 */         int iOption = ((Integer)this.m_hmWorkflowApprovalUpdates.get(sKey)).intValue();
/* 129 */         if ((a_workflows.contains(sKey)) && (!a_predicate.submitOptionMatches(iOption)))
/*     */         {
/* 132 */           return false;
/*     */         }
/*     */       }
/* 135 */       return true;
/*     */     }
/* 137 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.bean.WorkflowUpdate
 * JD-Core Version:    0.6.0
 */