/*    */ package com.bright.assetbank.custom.indesign.entity.abplugin;
/*    */ 
/*    */ import com.bright.assetbank.custom.indesign.entity.bean.InDesignAssetEntity;
/*    */ import com.bright.assetbank.entity.bean.AssetEntity;
/*    */ import com.bright.assetbank.plugin.iface.ABEditMod;
/*    */ import com.bright.assetbank.plugin.iface.ABExtension;
/*    */ import com.bright.assetbank.plugin.iface.ABModelMod;
/*    */ import com.bright.assetbank.plugin.iface.ABViewMod;
/*    */ import javax.annotation.Resource;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class InDesignAssetEntityExtension
/*    */   implements ABExtension
/*    */ {
/*    */ 
/*    */   @Resource(name="inDesignAssetEntityEditMod")
/*    */   private ABEditMod m_editMod;
/*    */ 
/*    */   @Resource(name="inDesignAssetEntityModelMod")
/*    */   private ABModelMod m_modelMod;
/*    */   private static final String c_ksExtensionKey = "indesign.assetentity";
/*    */ 
/*    */   public static InDesignAssetEntity getInDesignAssetEntity(AssetEntity a_entity)
/*    */   {
/* 43 */     return getInDesignAssetEntity(a_entity, false);
/*    */   }
/*    */ 
/*    */   public static InDesignAssetEntity getInDesignAssetEntity(AssetEntity a_entity, boolean a_createIfNonexistent)
/*    */   {
/* 49 */     InDesignAssetEntity inDEntity = (InDesignAssetEntity)a_entity.getExtensionData("indesign.assetentity");
/* 50 */     if ((inDEntity == null) && (a_createIfNonexistent))
/*    */     {
/* 52 */       inDEntity = new InDesignAssetEntity();
/* 53 */       a_entity.setExtensionData("indesign.assetentity", inDEntity);
/*    */     }
/* 55 */     return inDEntity;
/*    */   }
/*    */ 
/*    */   public String getUniqueKey()
/*    */   {
/* 60 */     return "indesign.assetentity";
/*    */   }
/*    */ 
/*    */   public ABViewMod getViewMod()
/*    */   {
/* 65 */     return null;
/*    */   }
/*    */ 
/*    */   public ABEditMod getAddMod()
/*    */   {
/* 70 */     return this.m_editMod;
/*    */   }
/*    */ 
/*    */   public ABEditMod getEditExistingMod()
/*    */   {
/* 75 */     return this.m_editMod;
/*    */   }
/*    */ 
/*    */   public ABModelMod getModelMod()
/*    */   {
/* 80 */     return this.m_modelMod;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.entity.abplugin.InDesignAssetEntityExtension
 * JD-Core Version:    0.6.0
 */