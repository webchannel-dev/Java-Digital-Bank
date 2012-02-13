/*    */ package com.bright.assetbank.custom.indesign.abplugin;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.plugin.iface.ABPlugin;
/*    */ import com.bright.assetbank.plugin.service.PluginSPI;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.annotation.AnnotationConfigApplicationContext;
/*    */ 
/*    */ public class InDesignPlugin
/*    */   implements ABPlugin
/*    */ {
/*    */   public void startup(PluginSPI a_spi)
/*    */     throws Bn2Exception
/*    */   {
/* 38 */     if (!AssetBankSettings.getAssetEntitiesEnabled())
/*    */     {
/* 40 */       throw new Bn2Exception("The InDesign plugin can only be used if asset entities are enabled.");
/*    */     }
/*    */ 
/* 43 */     ApplicationContext applicationContext = createContext();
/*    */ 
/* 45 */     InDesignPluginExtensions extensions = (InDesignPluginExtensions)applicationContext.getBean(InDesignPluginExtensions.class);
/*    */ 
/* 48 */     a_spi.addExtension("asset", extensions.getAssetExtension());
/*    */ 
/* 51 */     a_spi.addExtension("assetEntity", extensions.getAssetEntityExtension());
/*    */ 
/* 54 */     a_spi.addCategoryDiskUsageExtension(extensions.getCategoryDiskUsageExtension());
/*    */ 
/* 56 */     a_spi.addDependencyProvider(extensions.getDependencyProvider());
/*    */ 
/* 58 */     a_spi.addEditorDependencyProvider(extensions.getEditorDependenciesProvider());
/*    */ 
/* 60 */     a_spi.addUploadAssetExtension(extensions.getUploadAssetExtension());
/*    */   }
/*    */ 
/*    */   public static ApplicationContext createContext()
/*    */   {
/* 65 */     AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
/*    */ 
/* 67 */     context.scan(new String[] { "com.bright.assetbank.custom.indesign" });
/* 68 */     context.refresh();
/* 69 */     return context;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.abplugin.InDesignPlugin
 * JD-Core Version:    0.6.0
 */