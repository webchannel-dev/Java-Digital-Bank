/*    */ package com.bright.assetbank.plugin.sampleplugin.abplugin;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.entity.bean.AssetEntity;
/*    */ import com.bright.assetbank.plugin.iface.DefaultABModelMod;
/*    */ import com.bright.assetbank.plugin.sampleplugin.bean.AssetEntityDescription;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class AssetEntityDescriptionModelMod extends DefaultABModelMod
/*    */ {
/*    */   private AssetEntityDescriptionService m_service;
/*    */ 
/*    */   public AssetEntityDescriptionModelMod(AssetEntityDescriptionService a_service)
/*    */   {
/* 34 */     this.m_service = a_service;
/*    */   }
/*    */ 
/*    */   public Serializable get(DBTransaction a_transaction, Object a_coreObject)
/*    */     throws Bn2Exception
/*    */   {
/* 40 */     AssetEntity assetEntity = (AssetEntity)a_coreObject;
/* 41 */     return getService().get(a_transaction, assetEntity.getId());
/*    */   }
/*    */ 
/*    */   public void add(DBTransaction a_transaction, Object a_coreObject, Object a_extensionData)
/*    */     throws Bn2Exception
/*    */   {
/* 48 */     AssetEntity assetEntity = (AssetEntity)a_coreObject;
/* 49 */     AssetEntityDescription inDesignAssetEntity = (AssetEntityDescription)a_extensionData;
/* 50 */     getService().add(a_transaction, assetEntity.getId(), inDesignAssetEntity);
/*    */   }
/*    */ 
/*    */   public void editExisting(DBTransaction a_transaction, Object a_coreObject, Object a_extensionData)
/*    */     throws Bn2Exception
/*    */   {
/* 57 */     AssetEntity assetEntity = (AssetEntity)a_coreObject;
/* 58 */     AssetEntityDescription inDesignAssetEntity = (AssetEntityDescription)a_extensionData;
/* 59 */     getService().addOrUpdate(a_transaction, assetEntity.getId(), inDesignAssetEntity);
/*    */   }
/*    */ 
/*    */   public void delete(DBTransaction a_transaction, long a_lId)
/*    */     throws Bn2Exception
/*    */   {
/* 65 */     getService().delete(a_transaction, a_lId);
/*    */   }
/*    */ 
/*    */   private AssetEntityDescriptionService getService()
/*    */   {
/* 70 */     return this.m_service;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.sampleplugin.abplugin.AssetEntityDescriptionModelMod
 * JD-Core Version:    0.6.0
 */