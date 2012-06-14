/*     */ package com.bright.assetbank.custom.bluebooks.indesign;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.custom.bluebooks.constant.BluebooksSettings;
/*     */ import com.bright.assetbank.custom.indesign.custom.InDesignAttributeProvider;
/*     */ import com.bright.assetbank.custom.indesign.custom.InconsistentAttributeValuesException;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionCallback;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.annotation.Resource;
/*     */ import org.apache.commons.lang.ObjectUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class BluebooksInDesignAttributeProvider
/*     */   implements InDesignAttributeProvider
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private DBTransactionManager m_transactionManager;
/*     */ 
/*     */   @Resource
/*     */   private AssetManager m_assetManager;
/*     */ 
/*     */   public Collection<Long> getAvailableAttributeIds(long a_inDesignDocumentAssetId)
/*     */     throws Bn2Exception
/*     */   {
/*  59 */     return null;
/*     */   }
/*     */ 
/*     */   public Map<Long, String> getAttributeValues(long a_inDesignDocumentAssetId, Collection<Long> a_attributeIds)
/*     */     throws Bn2Exception
/*     */   {
/*  68 */     if (a_attributeIds.isEmpty())
/*     */     {
/*  70 */       return Collections.emptyMap();
/*     */     }
/*     */ 
/*  73 */     return (Map)getTransactionManager().execute(new AttributeValueGetter(a_inDesignDocumentAssetId, a_attributeIds));
/*     */   }
/*     */ 
/*     */   private static String getNonEmptyAttributeValueAsString(Asset asset, long a_attributeId)
/*     */   {
/* 183 */     AttributeValue skuAttVal = asset.getAttributeValue(a_attributeId);
/*     */     String sSKUAttVal;
/*     */     //String sSKUAttVal;
/* 185 */     if (skuAttVal == null)
/*     */     {
/* 187 */       sSKUAttVal = null;
/*     */     }
/*     */     else
/*     */     {
/* 191 */       sSKUAttVal = skuAttVal.getValue();
/*     */ 
/* 193 */       if (StringUtils.isEmpty(sSKUAttVal))
/*     */       {
/* 195 */         sSKUAttVal = null;
/*     */       }
/*     */     }
/* 198 */     return sSKUAttVal;
/*     */   }
/*     */ 
/*     */   public static String getAttributeValueAndCheckConsistent(long a_attributeId, Collection<Asset> a_skuAssets)
/*     */     throws InconsistentAttributeValuesException
/*     */   {
/* 209 */     String sFirstSKUAttVal = null;
/* 210 */     boolean firstSku = true;
/* 211 */     for (Asset asset : a_skuAssets)
/*     */     {
/* 213 */       if (firstSku)
/*     */       {
/* 215 */         sFirstSKUAttVal = getNonEmptyAttributeValueAsString(asset, a_attributeId);
/*     */ 
/* 217 */         firstSku = false;
/*     */       }
/*     */       else
/*     */       {
/* 221 */         String sOtherSKUAttVal = getNonEmptyAttributeValueAsString(asset, a_attributeId);
/* 222 */         if (!ObjectUtils.equals(sFirstSKUAttVal, sOtherSKUAttVal))
/*     */         {
/* 225 */           throw new InconsistentAttributeValuesException();
/*     */         }
/*     */       }
/*     */     }
/* 229 */     return sFirstSKUAttVal;
/*     */   }
/*     */ 
/*     */   private Set<Long> findSKUAssetIds(DBTransaction a_transaction, long a_inDesignDocumentAssetId)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 239 */     return findCategoryExtensionAssetsDirectlyRelatedToAsset(a_transaction, a_inDesignDocumentAssetId, BluebooksSettings.getSKUEntityId());
/*     */   }
/*     */ 
/*     */   private Set<Long> findModelAssetIds(DBTransaction a_transaction, long a_inDesignDocumentAssetId)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 250 */     Set modelAssetIds = new HashSet();
/* 251 */     modelAssetIds.addAll(findCategoryExtensionAssetsDirectlyRelatedToAsset(a_transaction, a_inDesignDocumentAssetId, BluebooksSettings.getModelEntityId()));
/*     */ 
/* 253 */     modelAssetIds.addAll(findCategoryExtensionAssetsParentsOfThoseDirectlyRelatedToAsset(a_transaction, a_inDesignDocumentAssetId, BluebooksSettings.getModelEntityId()));
/*     */ 
/* 255 */     return modelAssetIds;
/*     */   }
/*     */ 
/*     */   private Set<Long> findCategoryExtensionAssetsDirectlyRelatedToAsset(DBTransaction a_transaction, long a_mainAssetId, long a_cxaEntityId)
/*     */     throws Bn2Exception, SQLException
/*     */   {
/* 267 */     String sSQL = "SELECT xa.Id FROM Asset xa JOIN CM_Category c ON c.ExtensionAssetId = xa.Id JOIN CM_ItemInCategory iic ON c.Id = iic.CategoryId WHERE iic.ItemId = ? AND xa.AssetEntityId = ?";
/*     */ 
/* 275 */     Connection con = a_transaction.getConnection();
/* 276 */     PreparedStatement psql = con.prepareStatement(sSQL);
/* 277 */     psql.setLong(1, a_mainAssetId);
/* 278 */     psql.setLong(2, a_cxaEntityId);
/*     */ 
/* 280 */     ResultSet rs = psql.executeQuery();
/*     */ 
/* 282 */     Set cxaIds = new HashSet();
/* 283 */     while (rs.next())
/*     */     {
/* 285 */       cxaIds.add(Long.valueOf(rs.getLong("Id")));
/*     */     }
/*     */ 
/* 288 */     psql.close();
/*     */ 
/* 290 */     return cxaIds;
/*     */   }
/*     */ 
/*     */   private Set<Long> findCategoryExtensionAssetsParentsOfThoseDirectlyRelatedToAsset(DBTransaction a_transaction, long a_mainAssetId, long a_cxaEntityId)
/*     */     throws Bn2Exception, SQLException
/*     */   {
/* 304 */     String sSQL = "SELECT xa.Id FROM Asset xa JOIN CM_Category cp ON cp.ExtensionAssetId = xa.Id JOIN CM_Category c ON c.ParentId = cp.Id JOIN CM_ItemInCategory iic ON c.Id = iic.CategoryId WHERE iic.ItemId = ? AND xa.AssetEntityId = ?";
/*     */ 
/* 313 */     Connection con = a_transaction.getConnection();
/* 314 */     PreparedStatement psql = con.prepareStatement(sSQL);
/* 315 */     psql.setLong(1, a_mainAssetId);
/* 316 */     psql.setLong(2, a_cxaEntityId);
/*     */ 
/* 318 */     ResultSet rs = psql.executeQuery();
/*     */ 
/* 320 */     Set cxaIds = new HashSet();
/* 321 */     while (rs.next())
/*     */     {
/* 323 */       cxaIds.add(Long.valueOf(rs.getLong("Id")));
/*     */     }
/*     */ 
/* 326 */     psql.close();
/*     */ 
/* 328 */     return cxaIds;
/*     */   }
/*     */ 
/*     */   public Collection<Long> getAttributeDependentAssetIds(long a_assetId, Set<Long> a_attributeIds)
/*     */   {
/* 334 */     return null;
/*     */   }
/*     */ 
/*     */   private AssetManager getAssetManager()
/*     */   {
/* 339 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   private DBTransactionManager getTransactionManager()
/*     */   {
/* 344 */     return this.m_transactionManager;
/*     */   }
/*     */ 
/*     */   class AttributeValueGetter
/*     */     implements DBTransactionCallback<Map<Long, String>>
/*     */   {
/*     */     private final long m_inDesignDocumentAssetId;
/*     */     private final Collection<Long> m_attributeIds;
/*     */     private Collection<Asset> m_skuAssets;
/*  86 */     private boolean m_skuAssetsSet = false;
/*     */     private Collection<Asset> m_modelAssets;
/*  88 */     private boolean m_modelAssetsSet = false;
/*     */ 
/*     */     //public AttributeValueGetter(Collection<Long> arg3)
               public AttributeValueGetter(Long a_inDesignDocumentAssetId ,Collection<Long> a_attributeIds ) 
/*     */     {
/*  92 */       this.m_inDesignDocumentAssetId = a_inDesignDocumentAssetId;
/*  93 */       this.m_attributeIds = a_attributeIds;
/*  94 */       this.m_skuAssets = null;
/*     */     }
/*     */ 
/*     */     public Map<Long, String> doInTransaction(DBTransaction a_transaction) throws SQLException, Bn2Exception
/*     */     {
/*  99 */       Map attributeValues = new HashMap();
/*     */ 
/* 101 */       Asset mainAsset = BluebooksInDesignAttributeProvider.this.getAssetManager().getAsset(a_transaction, this.m_inDesignDocumentAssetId);
/*     */ 
/* 103 */       for (Long lAttributeId : this.m_attributeIds)
/*     */       {
/* 105 */         long attributeId = lAttributeId.longValue();
/*     */ 
/* 112 */         //String attValToUse = BluebooksInDesignAttributeProvider.access$100(mainAsset, attributeId);
/*     */           String attValToUse = BluebooksInDesignAttributeProvider.getNonEmptyAttributeValueAsString(mainAsset, attributeId);
/* 116 */         if (attValToUse == null)
/*     */         {
/* 118 */           Collection skuAssets = getSKUAssets(a_transaction);
/* 119 */           String sAllSKUsAttVal = BluebooksInDesignAttributeProvider.getAttributeValueAndCheckConsistent(attributeId, skuAssets);
/*     */ 
/* 121 */           if (sAllSKUsAttVal != null)
/*     */           {
/* 123 */             attValToUse = sAllSKUsAttVal;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 129 */         if (attValToUse == null)
/*     */         {
/* 131 */           Collection modelAssets = getModelAssets(a_transaction);
/* 132 */           String sAllModelsAttVal = BluebooksInDesignAttributeProvider.getAttributeValueAndCheckConsistent(attributeId, modelAssets);
/*     */ 
/* 134 */           if (sAllModelsAttVal != null)
/*     */           {
/* 136 */             attValToUse = sAllModelsAttVal;
/*     */           }
/*     */         }
/*     */ 
/* 140 */         if (attValToUse != null)
/*     */         {
/* 142 */           attributeValues.put(lAttributeId, attValToUse);
/*     */         }
/*     */       }
/*     */ 
/* 146 */       return attributeValues;
/*     */     }
/*     */ 
/*     */     Collection<Asset> getSKUAssets(DBTransaction a_transaction) throws SQLException, Bn2Exception
/*     */     {
/* 151 */       if (!this.m_skuAssetsSet)
/*     */       {
/* 153 */         Vector skuAssetIds = new Vector();
/* 154 */         skuAssetIds.addAll(BluebooksInDesignAttributeProvider.this.findSKUAssetIds(a_transaction, this.m_inDesignDocumentAssetId));
/* 155 */         this.m_skuAssets = BluebooksInDesignAttributeProvider.this.getAssetManager().getAssets(a_transaction, skuAssetIds, false, false);
/*     */ 
/* 159 */         this.m_skuAssetsSet = true;
/*     */       }
/* 161 */       return this.m_skuAssets;
/*     */     }
/*     */ 
/*     */     public Collection<Asset> getModelAssets(DBTransaction a_transaction) throws Bn2Exception, SQLException
/*     */     {
/* 166 */       if (!this.m_modelAssetsSet)
/*     */       {
/* 168 */         Vector modelAssetIds = new Vector();
/* 169 */         modelAssetIds.addAll(BluebooksInDesignAttributeProvider.this.findModelAssetIds(a_transaction, this.m_inDesignDocumentAssetId));
/* 170 */         this.m_modelAssets = BluebooksInDesignAttributeProvider.this.getAssetManager().getAssets(a_transaction, modelAssetIds, false, false);
/*     */ 
/* 174 */         this.m_modelAssetsSet = true;
/*     */       }
/* 176 */       return this.m_modelAssets;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.bluebooks.indesign.BluebooksInDesignAttributeProvider
 * JD-Core Version:    0.6.0
 */