/*    */ package com.bright.assetbank.approval.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.BrightDate;
/*    */ import com.bright.framework.common.bean.StringDataBean;
/*    */ 
/*    */ public class UsersWithApprovalListRecord extends StringDataBean
/*    */ {
/*    */   private BrightDate m_dateOfOldestApprovalRequest;
/*    */ 
/*    */   public UsersWithApprovalListRecord()
/*    */   {
/* 37 */     this.m_dateOfOldestApprovalRequest = new BrightDate();
/*    */   }
/*    */ 
/*    */   public BrightDate getDateOfOldestApprovalRequest()
/*    */   {
/* 54 */     return this.m_dateOfOldestApprovalRequest;
/*    */   }
/*    */ 
/*    */   public void setDateOfOldestApprovalRequest(BrightDate a_sDateOfOldestApprovalRequest)
/*    */   {
/* 63 */     this.m_dateOfOldestApprovalRequest = a_sDateOfOldestApprovalRequest;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.bean.UsersWithApprovalListRecord
 * JD-Core Version:    0.6.0
 */