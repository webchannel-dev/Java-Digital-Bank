/*    */ package com.bright.assetbank.autocomplete.service;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ 
/*    */ class ACIndexAssetKeywordsJob extends ACIndexJob
/*    */ {
/*    */   private Asset m_originalAsset;
/*    */   private Asset m_asset;
/*    */ 
/*    */   public ACIndexAssetKeywordsJob(Asset a_originalAsset, Asset a_asset)
/*    */   {
/* 37 */     this.m_originalAsset = a_originalAsset;
/* 38 */     this.m_asset = a_asset;
/*    */   }
/*    */ 
/*    */   public Asset getAsset()
/*    */   {
/* 43 */     return this.m_asset;
/*    */   }
/*    */ 
/*    */   public void perform(AutoCompleteUpdateManager a_autoCompleteUpdateManager)
/*    */     throws Bn2Exception
/*    */   {
/* 49 */     a_autoCompleteUpdateManager.indexAssetKeywords(this.m_originalAsset, this.m_asset);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.autocomplete.service.ACIndexAssetKeywordsJob
 * JD-Core Version:    0.6.0
 */