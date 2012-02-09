/*    */ package com.bright.assetbank.plugin.sampleplugin.abplugin;
/*    */ 
/*    */ import com.bright.assetbank.plugin.iface.ABEditMod;
/*    */ import com.bright.assetbank.plugin.iface.ABExtension;
/*    */ import com.bright.assetbank.plugin.iface.ABModelMod;
/*    */ import com.bright.assetbank.plugin.iface.ABViewMod;
/*    */ 
/*    */ public class AssetEntityDescriptionExtension
/*    */   implements ABExtension
/*    */ {
/* 26 */   AssetEntityDescriptionService m_service = new AssetEntityDescriptionService();
/*    */ 
/* 29 */   private ABEditMod m_editMod = new AssetEntityDescriptionEditMod();
/*    */ 
/* 32 */   private ABModelMod m_modelMod = new AssetEntityDescriptionModelMod(this.m_service);
/*    */ 
/*    */   public String getUniqueKey()
/*    */   {
/* 37 */     return "description.assetentity";
/*    */   }
/*    */ 
/*    */   public ABViewMod getViewMod()
/*    */   {
/* 44 */     return null;
/*    */   }
/*    */ 
/*    */   public ABEditMod getAddMod()
/*    */   {
/* 49 */     return this.m_editMod;
/*    */   }
/*    */ 
/*    */   public ABEditMod getEditExistingMod()
/*    */   {
/* 54 */     return this.m_editMod;
/*    */   }
/*    */ 
/*    */   public ABModelMod getModelMod()
/*    */   {
/* 59 */     return this.m_modelMod;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.sampleplugin.abplugin.AssetEntityDescriptionExtension
 * JD-Core Version:    0.6.0
 */