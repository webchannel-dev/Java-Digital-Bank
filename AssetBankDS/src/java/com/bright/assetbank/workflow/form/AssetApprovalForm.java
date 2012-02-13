/*     */ package com.bright.assetbank.workflow.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.workflow.bean.TransitionableAssets;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.workflow.bean.Workflow;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class AssetApprovalForm extends Bn2Form
/*     */ {
/*  31 */   private long m_lDownloadedAssetsForApproval = 0L;
/*  32 */   private Workflow m_selectedWorkflow = null;
/*  33 */   private Vector m_vecApprovableWorkflows = null;
/*     */   private TransitionableAssets m_approvalList;
/*  63 */   private long m_selectedUserId = 0L;
/*     */   private Vector<StringDataBean> m_listUsers;
/*     */ 
/*     */   public TransitionableAssets getApprovalList()
/*     */   {
/*  47 */     return this.m_approvalList;
/*     */   }
/*     */ 
/*     */   public void setApprovalList(TransitionableAssets a_sApprovalList)
/*     */   {
/*  57 */     this.m_approvalList = a_sApprovalList;
/*     */   }
/*     */ 
/*     */   public long getSelectedUserId()
/*     */   {
/*  72 */     return this.m_selectedUserId;
/*     */   }
/*     */ 
/*     */   public void setSelectedUserId(long a_sSelectedUserId)
/*     */   {
/*  82 */     this.m_selectedUserId = a_sSelectedUserId;
/*     */   }
/*     */ 
/*     */   public Vector<StringDataBean> getListUsers()
/*     */   {
/*  97 */     return this.m_listUsers;
/*     */   }
/*     */ 
/*     */   public void setListUsers(Vector<StringDataBean> a_sListUsers)
/*     */   {
/* 107 */     this.m_listUsers = a_sListUsers;
/*     */   }
/*     */ 
/*     */   public long getDownloadedAssetsForApproval()
/*     */   {
/* 113 */     return this.m_lDownloadedAssetsForApproval;
/*     */   }
/*     */ 
/*     */   public void setDownloadedAssetsForApproval(long a_lDownloadedAssetsForApproval)
/*     */   {
/* 118 */     this.m_lDownloadedAssetsForApproval = a_lDownloadedAssetsForApproval;
/*     */   }
/*     */ 
/*     */   public void setSelectedWorkflow(Workflow a_selectedWorkflow)
/*     */   {
/* 123 */     this.m_selectedWorkflow = a_selectedWorkflow;
/*     */   }
/*     */ 
/*     */   public Workflow getSelectedWorkflow()
/*     */   {
/* 128 */     return this.m_selectedWorkflow;
/*     */   }
/*     */ 
/*     */   public void setWorkflows(Vector a_vecApprovableWorkflows)
/*     */   {
/* 133 */     this.m_vecApprovableWorkflows = a_vecApprovableWorkflows;
/*     */   }
/*     */ 
/*     */   public Vector getWorkflows()
/*     */   {
/* 138 */     return this.m_vecApprovableWorkflows;
/*     */   }
/*     */ 
/*     */   public int getNoOfWorkflows()
/*     */   {
/* 143 */     if (getWorkflows() != null)
/*     */     {
/* 145 */       return getWorkflows().size();
/*     */     }
/* 147 */     return 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.form.AssetApprovalForm
 * JD-Core Version:    0.6.0
 */