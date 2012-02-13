/*     */ package com.bright.assetbank.approval.form;
/*     */ 
/*     */ import com.bright.assetbank.usage.form.DateRangeForm;
/*     */ import java.util.Collection;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class AssetApprovalListForm extends DateRangeForm
/*     */ {
/*     */   private Collection m_approvalList;
/*     */   private boolean m_isAppeal;
/*     */   private boolean m_bIsHighResRequest;
/*     */   private Vector m_usageTypeList;
/*     */ 
/*     */   public Collection getApprovalList()
/*     */   {
/*  50 */     return this.m_approvalList;
/*     */   }
/*     */ 
/*     */   public void setApprovalList(Collection a_sApprovalList)
/*     */   {
/*  59 */     this.m_approvalList = a_sApprovalList;
/*     */   }
/*     */ 
/*     */   public boolean getIsAppeal()
/*     */   {
/*  78 */     return this.m_isAppeal;
/*     */   }
/*     */ 
/*     */   public void setIsAppeal(boolean a_sIsAppeal)
/*     */   {
/*  83 */     this.m_isAppeal = a_sIsAppeal;
/*     */   }
/*     */ 
/*     */   public boolean getIsHighResRequest()
/*     */   {
/*  89 */     return this.m_bIsHighResRequest;
/*     */   }
/*     */ 
/*     */   public void setIsHighResRequest(boolean a_bIsHighResRequest)
/*     */   {
/*  94 */     this.m_bIsHighResRequest = a_bIsHighResRequest;
/*     */   }
/*     */ 
/*     */   public Vector getUsageTypeList()
/*     */   {
/* 105 */     return this.m_usageTypeList;
/*     */   }
/*     */ 
/*     */   public void setUsageTypeList(Vector a_sUsageTypeList) {
/* 109 */     this.m_usageTypeList = a_sUsageTypeList;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.form.AssetApprovalListForm
 * JD-Core Version:    0.6.0
 */