/*     */ package com.bright.assetbank.custom.indesign.asset.abplugin;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.custom.indesign.asset.bean.InDesignAsset;
/*     */ import com.bright.assetbank.custom.indesign.asset.bean.InDesignAssetWithNewFile;
/*     */ import com.bright.assetbank.custom.indesign.entity.abplugin.InDesignAssetEntityService;
/*     */ import com.bright.assetbank.custom.indesign.entity.bean.InDesignAssetEntity;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.plugin.iface.DefaultABModelMod;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import java.io.Serializable;
/*     */ import javax.annotation.Resource;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class InDesignAssetModelMod extends DefaultABModelMod
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private InDesignAssetService m_service;
/*     */ 
/*     */   @Resource
/*     */   private InDesignAssetEntityService m_entityService;
/*     */ 
/*     */   public Serializable get(DBTransaction a_transaction, Object a_coreObject)
/*     */     throws Bn2Exception
/*     */   {
/*  43 */     Asset asset = (Asset)a_coreObject;
/*  44 */     return getService().getWithRelated(a_transaction, asset.getId());
/*     */   }
/*     */ 
/*     */   public void add(DBTransaction a_transaction, Object a_coreObject, Object a_extensionData)
/*     */     throws Bn2Exception
/*     */   {
/*  51 */     edit(a_transaction, a_coreObject, a_extensionData);
/*     */   }
/*     */ 
/*     */   public void editExisting(DBTransaction a_transaction, Object a_coreObject, Object a_extensionData)
/*     */     throws Bn2Exception
/*     */   {
/*  58 */     edit(a_transaction, a_coreObject, a_extensionData);
/*     */   }
/*     */ 
/*     */   private void edit(DBTransaction a_transaction, Object a_coreObject, Object a_extensionData)
/*     */     throws Bn2Exception
/*     */   {
/*  64 */     Asset asset = (Asset)a_coreObject;
/*     */ 
/*  66 */     InDesignAssetEntity inDAssetEntity = getEntityService().get(a_transaction, asset.getEntity().getId());
/*     */ 
/*  72 */     if ((inDAssetEntity != null) && (a_extensionData != null))
/*     */     {
/*  74 */       InDesignAsset inDAsset = (InDesignAsset)a_extensionData;
/*     */ 
/*  76 */       if ((inDAsset instanceof InDesignAssetWithNewFile))
/*     */       {
/*  78 */         InDesignAssetWithNewFile inDAssetWithNewFile = (InDesignAssetWithNewFile)inDAsset;
/*     */ 
/*  82 */         getService().addOrUpdateWithNewFile(a_transaction, asset.getId(), inDAssetWithNewFile);
/*     */       }
/*     */       else
/*     */       {
/*  86 */         getService().addOrUpdate(a_transaction, asset.getId(), inDAsset);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/*  94 */     getService().delete(a_transaction, a_lId);
/*     */   }
/*     */ 
/*     */   private InDesignAssetService getService()
/*     */   {
/*  99 */     return this.m_service;
/*     */   }
/*     */ 
/*     */   private InDesignAssetEntityService getEntityService()
/*     */   {
/* 104 */     return this.m_entityService;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.asset.abplugin.InDesignAssetModelMod
 * JD-Core Version:    0.6.0
 */