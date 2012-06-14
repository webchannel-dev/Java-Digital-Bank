/*    */ package com.bright.assetbank.synchronise.bean;
/*    */ 
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.synchronise.service.CategoryIdHandler;
/*    */ 
/*    */ public class ExternalSynchAssetImpl extends ExternalAssetImpl
/*    */ {
/*    */   public ExternalSynchAssetImpl()
/*    */   {
/* 34 */     super(new CategoryIdHandler());
/*    */   }
/*    */ 
/*    */   public String getOriginalFilename()
/*    */   {
/* 39 */     return valueOf(getAsset().getOriginalFilename());
/*    */   }
/*    */ 
/*    */   public String getId()
/*    */   {
/* 44 */     String sId = super.getId();
/* 45 */     return sId + "_synch";
/*    */   }
/*    */ 
/*    */   public String getDescriptiveCategories()
/*    */   {
/* 50 */     return getCategoryNameList(getAsset().getDescriptiveCategories(), true);
/*    */   }
/*    */ 
/*    */   public String getEntityId()
/*    */   {
/* 55 */     if (!AssetBankSettings.getSpecifyEntityIdInPublish())
/*    */     {
/* 57 */       return "";
/*    */     }
/* 59 */     return super.getEntityId();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.ExternalSynchAssetImpl
 * JD-Core Version:    0.6.0
 */