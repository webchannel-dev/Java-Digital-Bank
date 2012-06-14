/*    */ package com.bright.assetbank.application.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class AssetListForm extends Bn2Form
/*    */ {
/* 31 */   private Vector m_assets = null;
/*    */ 
/*    */   public Vector getAssets()
/*    */   {
/* 36 */     return this.m_assets;
/*    */   }
/*    */ 
/*    */   public void setAssets(Vector a_sAssets) {
/* 40 */     this.m_assets = a_sAssets;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.form.AssetListForm
 * JD-Core Version:    0.6.0
 */