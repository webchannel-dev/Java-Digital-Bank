/*    */ package com.bright.assetbank.custom.indesign.asset.abplugin;
/*    */ 
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.custom.indesign.asset.bean.InDesignAssetWithRelated;
/*    */ import com.bright.assetbank.plugin.iface.ABEditMod;
/*    */ import com.bright.assetbank.plugin.iface.ABExtension;
/*    */ import com.bright.assetbank.plugin.iface.ABModelMod;
/*    */ import com.bright.assetbank.plugin.iface.ABViewMod;
/*    */ import javax.annotation.Resource;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class InDesignAssetExtension
/*    */   implements ABExtension
/*    */ {
/*    */   private static final String c_ksExtensionKey = "indesign.asset";
/*    */ 
/*    */   @Resource(name="inDesignAssetAddMod")
/*    */   private ABEditMod m_addMod;
/*    */ 
/*    */   @Resource(name="inDesignAssetEditExistingMod")
/*    */   private ABEditMod m_editExistingMod;
/*    */ 
/*    */   @Resource(name="inDesignAssetModelMod")
/*    */   private ABModelMod m_modelMod;
/*    */ 
/*    */   @Resource(name="inDesignAssetViewMod")
/*    */   private ABViewMod m_viewMod;
/*    */ 
/*    */   public static InDesignAssetWithRelated getInDesignAsset(Asset a_asset)
/*    */   {
/* 49 */     return (InDesignAssetWithRelated)a_asset.getExtensionData("indesign.asset");
/*    */   }
/*    */ 
/*    */   public String getUniqueKey()
/*    */   {
/* 54 */     return "indesign.asset";
/*    */   }
/*    */ 
/*    */   public ABViewMod getViewMod()
/*    */   {
/* 59 */     return this.m_viewMod;
/*    */   }
/*    */ 
/*    */   public ABEditMod getAddMod()
/*    */   {
/* 64 */     return this.m_addMod;
/*    */   }
/*    */ 
/*    */   public ABEditMod getEditExistingMod()
/*    */   {
/* 69 */     return this.m_editExistingMod;
/*    */   }
/*    */ 
/*    */   public ABModelMod getModelMod()
/*    */   {
/* 74 */     return this.m_modelMod;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.asset.abplugin.InDesignAssetExtension
 * JD-Core Version:    0.6.0
 */