/*    */ package com.bright.assetbank.custom.indesign.entity.util;
/*    */ 
/*    */ import com.bright.assetbank.custom.indesign.entity.abplugin.InDesignAssetEntityExtension;
/*    */ import com.bright.assetbank.custom.indesign.entity.bean.InDesignAssetEntity;
/*    */ import com.bright.assetbank.entity.bean.AssetEntity;
/*    */ 
/*    */ public class InDesignAssetEntityUtil
/*    */ {
/*    */   public static void setInDesignAssetEntityTypeId(AssetEntity a_entity, int a_inDAssetEntityTypeId)
/*    */   {
/* 24 */     InDesignAssetEntityExtension.getInDesignAssetEntity(a_entity, true).setInDesignAssetEntityTypeId(a_inDAssetEntityTypeId);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.entity.util.InDesignAssetEntityUtil
 * JD-Core Version:    0.6.0
 */