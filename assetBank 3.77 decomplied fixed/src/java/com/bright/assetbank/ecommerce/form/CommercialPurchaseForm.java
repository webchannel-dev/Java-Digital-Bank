/*    */ package com.bright.assetbank.ecommerce.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.ecommerce.bean.AssetPurchase;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class CommercialPurchaseForm extends Bn2Form
/*    */ {
/* 34 */   AssetPurchase m_purchase = null;
/*    */ 
/* 36 */   Vector m_vecCommercialOptionsList = null;
/*    */ 
/* 38 */   Vector m_vecAssets = null;
/*    */ 
/*    */   public Vector getCommercialOptionsList()
/*    */   {
/* 45 */     return this.m_vecCommercialOptionsList;
/*    */   }
/*    */ 
/*    */   public void setCommercialOptionsList(Vector a_vecCommercialOptionsList)
/*    */   {
/* 53 */     this.m_vecCommercialOptionsList = a_vecCommercialOptionsList;
/*    */   }
/*    */ 
/*    */   public AssetPurchase getPurchase()
/*    */   {
/* 61 */     if (this.m_purchase == null)
/*    */     {
/* 63 */       this.m_purchase = new AssetPurchase();
/*    */     }
/* 65 */     return this.m_purchase;
/*    */   }
/*    */ 
/*    */   public void setPurchase(AssetPurchase a_sPurchase)
/*    */   {
/* 73 */     this.m_purchase = a_sPurchase;
/*    */   }
/*    */ 
/*    */   public Vector getAssets()
/*    */   {
/* 81 */     return this.m_vecAssets;
/*    */   }
/*    */ 
/*    */   public void setAssets(Vector a_sAssets)
/*    */   {
/* 89 */     this.m_vecAssets = a_sAssets;
/*    */   }
/*    */ 
/*    */   public Asset getAssetsIndexed(int a_iIndex)
/*    */   {
/* 94 */     return (Asset)getAssets().get(a_iIndex);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.form.CommercialPurchaseForm
 * JD-Core Version:    0.6.0
 */