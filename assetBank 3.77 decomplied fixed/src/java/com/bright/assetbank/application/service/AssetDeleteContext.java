/*    */ package com.bright.assetbank.application.service;
/*    */ 
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ 
/*    */ public class AssetDeleteContext extends AssetChangeContext
/*    */ {
/*    */   private Asset m_originalAsset;
/*    */ 
/*    */   public AssetDeleteContext(Asset a_originalAsset)
/*    */   {
/* 33 */     this.m_originalAsset = a_originalAsset;
/*    */   }
/*    */ 
/*    */   public Asset getOriginalAsset()
/*    */   {
/* 40 */     return this.m_originalAsset;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.AssetDeleteContext
 * JD-Core Version:    0.6.0
 */