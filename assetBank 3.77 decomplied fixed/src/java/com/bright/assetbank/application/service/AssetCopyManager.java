/*     */ package com.bright.assetbank.application.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.AssetFileSource;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.util.AssetUtil;
/*     */ import com.bright.assetbank.category.bean.ExtendedCategoryInfo;
/*     */ import com.bright.assetbank.category.util.CategoryUtil;
/*     */ import com.bright.assetbank.entity.constant.AssetEntityConstants;
/*     */ import com.bright.assetbank.entity.relationship.util.RelationshipUtil;
/*     */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*     */ import com.bright.assetbank.workflow.util.WorkflowUtil;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.CategoryImpl;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class AssetCopyManager extends Bn2Manager
/*     */   implements AssetBankConstants, AssetEntityConstants
/*     */ {
/*  46 */   private AssetManager m_assetManager = null;
/*     */ 
/*  52 */   private AssetCategoryManager m_assetCategoryManager = null;
/*     */ 
/*     */   public void setAssetManager(AssetManager a_sAssetManager)
/*     */   {
/*  49 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_assetCategoryManager)
/*     */   {
/*  55 */     this.m_assetCategoryManager = a_assetCategoryManager;
/*     */   }
/*     */ 
/*     */   public Asset createCopy(DBTransaction a_transaction, Asset a_asset, long a_lUserId, LinkedHashMap<Long, Integer> a_lhmCopyChildRelationshipMap, boolean a_bCopyParents)
/*     */     throws Bn2Exception
/*     */   {
/*  78 */     Asset copy = AssetUtil.copyAsset(a_asset);
/*     */ 
/*  81 */     copy.setId(0L);
/*  82 */     copy.setDateAdded(new Date());
/*     */ 
/*  85 */     if (a_asset.getSurrogateAssetId() > 0L)
/*     */     {
/*  87 */       copy.setTypeId(0L);
/*  88 */       copy.setFormat(null);
/*     */ 
/*  90 */       copy.setThumbnailImageFile(null);
/*  91 */       copy.setHomogenizedImageFile(null);
/*  92 */       copy.setPreviewImageFile(null);
/*     */     }
/*     */ 
/*  95 */     copy.setVersionNumber(1);
/*  96 */     copy.setCurrentVersionId(0L);
/*     */ 
/*  99 */     copy.setChildAssetIdsAsString("");
/* 100 */     copy.setPeerAssetIdsAsString(null);
/* 101 */     if (!a_bCopyParents)
/*     */     {
/* 103 */       copy.setParentAssetIdsAsString(null);
/*     */     }
/*     */ 
/* 108 */     if ((AssetBankSettings.getCategoryExtensionAssetsEnabled()) && (copy.getExtendsCategory().getId() > 0L))
/*     */     {
/* 110 */       Category oldCat = this.m_assetCategoryManager.getCategory(a_transaction, copy.getExtendsCategory().getCategoryTypeId(), copy.getExtendsCategory().getId());
/* 111 */       Category newCat = new CategoryImpl();
/* 112 */       newCat.setName("");
/* 113 */       CategoryUtil.checkCategoryTypeAndSave(a_transaction, this.m_assetCategoryManager, newCat, copy.getExtendsCategory().getCategoryTypeId(), copy.getExtendsCategory().getParentId(), true);
/* 114 */       copy.getExtendsCategory().setId(newCat.getId());
/*     */ 
/* 117 */       if (copy.getExtendsCategory().getCategoryTypeId() == 2L)
/*     */       {
/* 119 */         copy.getPermissionCategories().remove(oldCat);
/* 120 */         copy.getPermissionCategories().add(newCat);
/*     */       }
/*     */       else
/*     */       {
/* 124 */         copy.getDescriptiveCategories().remove(oldCat);
/* 125 */         copy.getDescriptiveCategories().add(newCat);
/*     */       }
/*     */     }
/*     */     boolean bFirst;
/*     */     Iterator i$;
/* 131 */     if (a_lhmCopyChildRelationshipMap != null)
/*     */     {
/* 133 */       bFirst = true;
/* 134 */       for (i$ = a_lhmCopyChildRelationshipMap.keySet().iterator(); i$.hasNext(); ) { long lAssetId = ((Long)i$.next()).longValue();
/*     */ 
/* 136 */         int iRetainType = ((Integer)a_lhmCopyChildRelationshipMap.get(Long.valueOf(lAssetId))).intValue();
/* 137 */         if (iRetainType == 2)
/*     */         {
/* 141 */           Asset sourceAsset = this.m_assetManager.getAsset(a_transaction, lAssetId, null, true, true);
/* 142 */           WorkflowUpdate update = WorkflowUtil.getUpdateThatMirrorsAsset(sourceAsset, a_lUserId, -1L);
/* 143 */           LinkedHashMap lhmRelatedAssetRelMap = RelationshipUtil.getCopyChildRelationshipActions(sourceAsset, 1);
/*     */ 
/* 146 */           Asset copiedAsset = createCopy(a_transaction, sourceAsset, a_lUserId, lhmRelatedAssetRelMap, false);
/* 147 */           copiedAsset = this.m_assetManager.saveAsset(a_transaction, copiedAsset, new AssetFileSource(), a_lUserId, AssetUtil.getNewConversionInfoForAsset(copiedAsset), null, false, 0, sourceAsset.getFormat().getId(), update, false, false);
/*     */ 
/* 150 */           lAssetId = copiedAsset.getId();
/*     */ 
/* 154 */           if ((bFirst) && (a_asset.getSurrogateAssetId() > 0L))
/*     */           {
/* 156 */             copy.setSurrogateAssetId(lAssetId);
/*     */           }
/*     */         }
/*     */ 
/* 160 */         if (iRetainType != 3)
/*     */         {
/* 163 */           copy.setChildAssetIdsAsString(copy.getChildAssetIdsAsString() + "," + lAssetId);
/*     */         }
/* 165 */         bFirst = false;
/*     */       }
/*     */     }
/*     */ 
/* 169 */     return copy;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.AssetCopyManager
 * JD-Core Version:    0.6.0
 */