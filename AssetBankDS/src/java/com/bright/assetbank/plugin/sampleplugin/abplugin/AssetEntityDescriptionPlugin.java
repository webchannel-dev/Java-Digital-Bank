/*    */ package com.bright.assetbank.plugin.sampleplugin.abplugin;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.plugin.iface.ABPlugin;
/*    */ import com.bright.assetbank.plugin.service.PluginSPI;
/*    */ 
/*    */ public class AssetEntityDescriptionPlugin
/*    */   implements ABPlugin
/*    */ {
/*    */   public void startup(PluginSPI a_spi)
/*    */     throws Bn2Exception
/*    */   {
/* 28 */     a_spi.addExtension("assetEntity", new AssetEntityDescriptionExtension());
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.sampleplugin.abplugin.AssetEntityDescriptionPlugin
 * JD-Core Version:    0.6.0
 */