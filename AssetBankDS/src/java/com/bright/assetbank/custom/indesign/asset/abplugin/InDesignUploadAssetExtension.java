/*    */ package com.bright.assetbank.custom.indesign.asset.abplugin;
/*    */ 
/*    */ import com.bright.assetbank.application.service.UploadAssetExtension;
/*    */ import com.bright.assetbank.custom.indesign.entity.abplugin.InDesignAssetEntityExtension;
/*    */ import com.bright.assetbank.custom.indesign.entity.bean.InDesignAssetEntity;
/*    */ import com.bright.assetbank.entity.bean.AssetEntity;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class InDesignUploadAssetExtension
/*    */   implements UploadAssetExtension
/*    */ {
/*    */   public boolean allowFileUploadOnAdd(AssetEntity a_assetEntity)
/*    */   {
/* 28 */     InDesignAssetEntity inDAssetEntity = InDesignAssetEntityExtension.getInDesignAssetEntity(a_assetEntity);
/*    */ 
/* 40 */     return (inDAssetEntity == null) || (!inDAssetEntity.isDocument());
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.asset.abplugin.InDesignUploadAssetExtension
 * JD-Core Version:    0.6.0
 */