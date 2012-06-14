/*     */ package com.bright.assetbank.custom.indesign.abplugin;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.application.service.FileUploadManager;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.custom.indesign.constant.InDesignSettings;
/*     */ import com.bright.assetbank.custom.indesign.custom.InDesignAttributeProvider;
/*     */ import com.bright.assetbank.custom.indesign.custom.NullInDesignAttributeProvider;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.service.StorageDeviceManager;
/*     */ import com.bright.framework.util.ClassUtil;
/*     */ import org.apache.avalon.framework.component.Component;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ 
/*     */ @Configuration
/*     */ public class InDesignPluginConfig
/*     */ {
/*     */   @Bean
/*     */   public InDesignAttributeProvider inDesignAttributeProvider()
/*     */     throws Bn2Exception
/*     */   {
/*  54 */     String className = InDesignSettings.getAttributeProviderClass();
/*     */     InDesignAttributeProvider attributeProvider;
/*     */     //ignAttributeProvider attributeProvider;
/*  55 */     if (StringUtils.isBlank(className))
/*     */     {
/*  57 */       attributeProvider = new NullInDesignAttributeProvider();
/*     */     }
/*     */     else
/*     */     {
/*  61 */       attributeProvider = (InDesignAttributeProvider)ClassUtil.newInstance(className);
/*     */     }
/*     */ 
/*  64 */     return attributeProvider;
/*     */   }
/*     */ 
/*     */   @Bean
/*     */   public AssetManager assetManager()
/*     */   {
/*  74 */     return (AssetManager)lookup("AssetManager");
/*     */   }
/*     */ 
/*     */   @Bean
/*     */   public AttributeManager attributeManager() {
/*  80 */     return (AttributeManager)lookup("AttributeManager");
/*     */   }
/*     */ 
/*     */   @Bean
/*     */   public FileStoreManager fileStoreManager() {
/*  86 */     return (FileStoreManager)lookup("FileStoreManager");
/*     */   }
/*     */ 
/*     */   @Bean
/*     */   public FileUploadManager fileUploadManager() {
/*  92 */     return (FileUploadManager)lookup("FileUploadManager");
/*     */   }
/*     */ 
/*     */   @Bean
/*     */   public MultiLanguageSearchManager multiLanguageSearchManager() {
/*  98 */     return (MultiLanguageSearchManager)lookup("SearchManager");
/*     */   }
/*     */ 
/*     */   @Bean
/*     */   public StorageDeviceManager storageDeviceManager() {
/* 104 */     return (StorageDeviceManager)lookup("StorageDeviceManager");
/*     */   }
/*     */ 
/*     */   @Bean
/*     */   public DBTransactionManager transactionManager() {
/* 110 */     return (DBTransactionManager)lookup("DBTransactionManager");
/*     */   }
/*     */ 
/*     */   @Bean
/*     */   public ABUserManager userManager() {
/* 116 */     return (ABUserManager)lookup("UserManager");
/*     */   }
/*     */ 
/*     */   private Component lookup(String a_componentName)
/*     */   {
/*     */     try
/*     */     {
/* 125 */       return getComponentManager().lookup(a_componentName);
/*     */     }
/*     */     catch (ComponentException e) {
/*     */     
/* 129 */     throw new RuntimeException(e);}
/*     */   }
/*     */ 
/*     */   private ComponentManager getComponentManager()
/*     */   {
/* 139 */     return GlobalApplication.getInstance().getComponentManager();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.abplugin.InDesignPluginConfig
 * JD-Core Version:    0.6.0
 */