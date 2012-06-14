/*    */ package com.bright.assetbank.approval.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.approval.bean.AssetApproval;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class ApprovalNotesForm extends Bn2Form
/*    */ {
/*    */   private AssetApproval m_assetApproval;
/*    */   private Vector m_vecOrderList;
/*    */ 
/*    */   public AssetApproval getAssetApproval()
/*    */   {
/* 54 */     return this.m_assetApproval;
/*    */   }
/*    */ 
/*    */   public void setAssetApproval(AssetApproval a_sAssetApproval)
/*    */   {
/* 63 */     this.m_assetApproval = a_sAssetApproval;
/*    */   }
/*    */ 
/*    */   public Vector getOrderList()
/*    */   {
/* 71 */     return this.m_vecOrderList;
/*    */   }
/*    */ 
/*    */   public void setOrderList(Vector a_sOrderList)
/*    */   {
/* 79 */     this.m_vecOrderList = a_sOrderList;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.form.ApprovalNotesForm
 * JD-Core Version:    0.6.0
 */