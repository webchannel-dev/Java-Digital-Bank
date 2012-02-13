/*     */ package com.bright.assetbank.workflow.bean;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class TransitionableAssets
/*     */ {
/*     */   private Vector m_workflowList;
/*     */   private long m_totalSize;
/*     */   private long m_returnSize;
/*     */ 
/*     */   public int getNumberStates()
/*     */   {
/*  48 */     int iNum = 0;
/*  49 */     if (this.m_workflowList != null)
/*     */     {
/*  51 */       Iterator it = this.m_workflowList.iterator();
/*  52 */       while (it.hasNext())
/*     */       {
/*  54 */         VariationsInWorkflow var = (VariationsInWorkflow)it.next();
/*  55 */         iNum += var.getNumberStates();
/*     */       }
/*     */     }
/*     */ 
/*  59 */     return iNum;
/*     */   }
/*     */ 
/*     */   public Vector getWorkflowList()
/*     */   {
/*  70 */     return this.m_workflowList;
/*     */   }
/*     */ 
/*     */   public void setWorkflowList(Vector a_sWorkflowList)
/*     */   {
/*  80 */     this.m_workflowList = a_sWorkflowList;
/*     */   }
/*     */ 
/*     */   public long getTotalSize()
/*     */   {
/*  90 */     return this.m_totalSize;
/*     */   }
/*     */ 
/*     */   public void setTotalSize(long a_sTotalSize)
/*     */   {
/* 100 */     this.m_totalSize = a_sTotalSize;
/*     */   }
/*     */ 
/*     */   public long getReturnSize()
/*     */   {
/* 110 */     return this.m_returnSize;
/*     */   }
/*     */ 
/*     */   public void setReturnSize(long a_sReturnSize)
/*     */   {
/* 120 */     this.m_returnSize = a_sReturnSize;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.bean.TransitionableAssets
 * JD-Core Version:    0.6.0
 */