/*    */ package com.bright.assetbank.assetbox.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class RequestAssetBoxForm extends Bn2Form
/*    */ {
/* 31 */   private Vector m_divisionList = null;
/* 32 */   private long m_lAssetId = 0L;
/* 33 */   private long m_lAssetEntityId = 0L;
/*    */ 
/*    */   public Vector getDivisionList()
/*    */   {
/* 37 */     return this.m_divisionList;
/*    */   }
/*    */ 
/*    */   public void setDivisionList(Vector a_sDivisionList) {
/* 41 */     this.m_divisionList = a_sDivisionList;
/*    */   }
/*    */ 
/*    */   public long getAssetId()
/*    */   {
/* 46 */     return this.m_lAssetId;
/*    */   }
/*    */ 
/*    */   public void setAssetId(long a_lAssetId)
/*    */   {
/* 51 */     this.m_lAssetId = a_lAssetId;
/*    */   }
/*    */ 
/*    */   public long getAssetEntityId()
/*    */   {
/* 56 */     return this.m_lAssetEntityId;
/*    */   }
/*    */ 
/*    */   public void setAssetEntityId(long a_lAssetEntityId)
/*    */   {
/* 61 */     this.m_lAssetEntityId = a_lAssetEntityId;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.form.RequestAssetBoxForm
 * JD-Core Version:    0.6.0
 */