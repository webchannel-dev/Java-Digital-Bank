/*    */ package com.bright.assetbank.presentation.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.approval.bean.AssetInList;
/*    */ 
/*    */ public class MergeAssetForm extends Bn2Form
/*    */ {
/*    */   private AssetInList[] m_assetList;
/*    */ 
/*    */   public AssetInList[] getAssetList()
/*    */   {
/* 33 */     return this.m_assetList;
/*    */   }
/*    */ 
/*    */   public void setAssetList(AssetInList[] a_assetList) {
/* 37 */     this.m_assetList = a_assetList;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.presentation.form.MergeAssetForm
 * JD-Core Version:    0.6.0
 */