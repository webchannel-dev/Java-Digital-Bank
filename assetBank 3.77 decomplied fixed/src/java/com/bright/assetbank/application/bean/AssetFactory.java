/*    */ package com.bright.assetbank.application.bean;
/*    */ 
/*    */ public class AssetFactory
/*    */ {
/*    */   public static Asset createAsset(int a_iAssetTypeId)
/*    */   {
/* 35 */     Asset asset = null;
/*    */ 
/* 37 */     if (a_iAssetTypeId == 2L)
/*    */     {
/* 39 */       asset = new ImageAsset();
/*    */     }
/* 41 */     else if (a_iAssetTypeId == 3L)
/*    */     {
/* 43 */       asset = new VideoAsset();
/*    */     }
/* 45 */     else if (a_iAssetTypeId == 4L)
/*    */     {
/* 47 */       asset = new AudioAsset();
/*    */     }
/*    */     else
/*    */     {
/* 51 */       asset = new Asset();
/*    */     }
/* 53 */     return asset;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.AssetFactory
 * JD-Core Version:    0.6.0
 */