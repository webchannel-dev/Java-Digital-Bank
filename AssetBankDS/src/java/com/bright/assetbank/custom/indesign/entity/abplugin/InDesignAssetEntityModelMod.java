/*    */ package com.bright.assetbank.custom.indesign.entity.abplugin;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.custom.indesign.entity.bean.InDesignAssetEntity;
/*    */ import com.bright.assetbank.entity.bean.AssetEntity;
/*    */ import com.bright.assetbank.plugin.iface.DefaultABModelMod;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import java.io.Serializable;
/*    */ import javax.annotation.Resource;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class InDesignAssetEntityModelMod extends DefaultABModelMod
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private InDesignAssetEntityService m_service;
/*    */ 
/*    */   public Serializable get(DBTransaction a_transaction, Object a_coreObject)
/*    */     throws Bn2Exception
/*    */   {
/* 40 */     AssetEntity assetEntity = (AssetEntity)a_coreObject;
/* 41 */     return getService().get(a_transaction, assetEntity.getId());
/*    */   }
/*    */ 
/*    */   public void add(DBTransaction a_transaction, Object a_coreObject, Object a_extensionData) throws Bn2Exception
/*    */   {
/* 46 */     AssetEntity assetEntity = (AssetEntity)a_coreObject;
/* 47 */     InDesignAssetEntity inDesignAssetEntity = (InDesignAssetEntity)a_extensionData;
/* 48 */     getService().addOrUpdate(a_transaction, assetEntity.getId(), inDesignAssetEntity);
/*    */   }
/*    */ 
/*    */   public void editExisting(DBTransaction a_transaction, Object a_coreObject, Object a_extensionData) throws Bn2Exception
/*    */   {
/* 53 */     AssetEntity assetEntity = (AssetEntity)a_coreObject;
/* 54 */     InDesignAssetEntity inDesignAssetEntity = (InDesignAssetEntity)a_extensionData;
/* 55 */     getService().addOrUpdate(a_transaction, assetEntity.getId(), inDesignAssetEntity);
/*    */   }
/*    */ 
/*    */   public void delete(DBTransaction a_transaction, long a_lId) throws Bn2Exception
/*    */   {
/* 60 */     getService().delete(a_transaction, a_lId);
/*    */   }
/*    */ 
/*    */   private InDesignAssetEntityService getService()
/*    */   {
/* 65 */     return this.m_service;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.entity.abplugin.InDesignAssetEntityModelMod
 * JD-Core Version:    0.6.0
 */