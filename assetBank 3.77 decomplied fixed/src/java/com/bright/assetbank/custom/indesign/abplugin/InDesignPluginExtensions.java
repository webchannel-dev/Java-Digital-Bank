/*    */ package com.bright.assetbank.custom.indesign.abplugin;
/*    */ 
/*    */ import com.bright.assetbank.custom.indesign.application.abplugin.InDesignCategoryDiskUsageExtension;
/*    */ import com.bright.assetbank.custom.indesign.asset.abplugin.InDesignAssetExtension;
/*    */ import com.bright.assetbank.custom.indesign.asset.abplugin.InDesignEditorDependenciesProvider;
/*    */ import com.bright.assetbank.custom.indesign.asset.abplugin.InDesignUploadAssetExtension;
/*    */ import com.bright.assetbank.custom.indesign.batchrel.InDesignDependencyProvider;
/*    */ import com.bright.assetbank.custom.indesign.entity.abplugin.InDesignAssetEntityExtension;
/*    */ import javax.annotation.Resource;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class InDesignPluginExtensions
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private InDesignAssetExtension m_assetExtension;
/*    */ 
/*    */   @Resource
/*    */   private InDesignAssetEntityExtension m_assetEntityExtension;
/*    */ 
/*    */   @Resource
/*    */   private InDesignCategoryDiskUsageExtension m_categoryDiskUsageExtension;
/*    */ 
/*    */   @Resource
/*    */   private InDesignDependencyProvider m_dependencyProvider;
/*    */ 
/*    */   @Resource
/*    */   private InDesignEditorDependenciesProvider m_editorDependenciesProvider;
/*    */ 
/*    */   @Resource
/*    */   private InDesignUploadAssetExtension m_uploadAssetExtension;
/*    */ 
/*    */   public InDesignAssetExtension getAssetExtension()
/*    */   {
/* 55 */     return this.m_assetExtension;
/*    */   }
/*    */ 
/*    */   public InDesignAssetEntityExtension getAssetEntityExtension()
/*    */   {
/* 60 */     return this.m_assetEntityExtension;
/*    */   }
/*    */ 
/*    */   public InDesignCategoryDiskUsageExtension getCategoryDiskUsageExtension()
/*    */   {
/* 65 */     return this.m_categoryDiskUsageExtension;
/*    */   }
/*    */ 
/*    */   public InDesignDependencyProvider getDependencyProvider()
/*    */   {
/* 70 */     return this.m_dependencyProvider;
/*    */   }
/*    */ 
/*    */   public InDesignEditorDependenciesProvider getEditorDependenciesProvider()
/*    */   {
/* 75 */     return this.m_editorDependenciesProvider;
/*    */   }
/*    */ 
/*    */   public InDesignUploadAssetExtension getUploadAssetExtension()
/*    */   {
/* 80 */     return this.m_uploadAssetExtension;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.abplugin.InDesignPluginExtensions
 * JD-Core Version:    0.6.0
 */