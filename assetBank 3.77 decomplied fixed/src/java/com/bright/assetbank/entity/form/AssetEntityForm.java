/*     */ package com.bright.assetbank.entity.form;
/*     */ 
/*     */ import com.bright.assetbank.attribute.bean.AttributeInList;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.relationship.bean.AssetEntityRelationship;
/*     */ import com.bright.framework.common.bean.RefDataItem;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.struts.Bn2ExtensibleForm;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class AssetEntityForm extends Bn2ExtensibleForm
/*     */ {
/*  39 */   private AssetEntity m_entity = null;
/*  40 */   private Vector<AssetEntity> m_entities = null;
/*  41 */   private Vector<AttributeInList> m_attributes = null;
/*  42 */   private String[] m_allowableAttributeIds = null;
/*  43 */   private RefDataItem[] m_assetTypes = null;
/*  44 */   private String[] m_allowableAssetTypeIds = null;
/*     */   private long[] m_alSelectedChildEntities;
/*     */   private long[] m_alSelectedPeerEntities;
/*     */ 
/*     */   public long[] getSelectedChildEntities()
/*     */   {
/*  51 */     return this.m_alSelectedChildEntities;
/*     */   }
/*     */ 
/*     */   public void setSelectedChildEntities(long[] a_alSelectedChildEntities)
/*     */   {
/*  56 */     this.m_alSelectedChildEntities = a_alSelectedChildEntities;
/*     */   }
/*     */ 
/*     */   public long[] getSelectedPeerEntities()
/*     */   {
/*  61 */     return this.m_alSelectedPeerEntities;
/*     */   }
/*     */ 
/*     */   public void setSelectedPeerEntities(long[] a_alSelectedPeerEntities)
/*     */   {
/*  66 */     this.m_alSelectedPeerEntities = a_alSelectedPeerEntities;
/*     */   }
/*     */ 
/*     */   public AssetEntity getEntity()
/*     */   {
/*  71 */     if (this.m_entity == null)
/*     */     {
/*  73 */       this.m_entity = new AssetEntity();
/*     */ 
/*  76 */       if (FrameworkSettings.getSupportMultiLanguage())
/*     */       {
/*  78 */         LanguageUtils.createEmptyTranslations(this.m_entity, 20);
/*     */       }
/*     */     }
/*  81 */     return this.m_entity;
/*     */   }
/*     */ 
/*     */   public void setEntity(AssetEntity entity)
/*     */   {
/*  86 */     this.m_entity = entity;
/*     */   }
/*     */ 
/*     */   public Vector<AssetEntity> getEntities()
/*     */   {
/*  91 */     return this.m_entities;
/*     */   }
/*     */ 
/*     */   public void setEntities(Vector<AssetEntity> entities)
/*     */   {
/*  96 */     this.m_entities = entities;
/*     */   }
/*     */ 
/*     */   public Vector<AttributeInList> getAttributes()
/*     */   {
/* 101 */     return this.m_attributes;
/*     */   }
/*     */ 
/*     */   public void setAttributes(Vector<AttributeInList> attributes)
/*     */   {
/* 106 */     this.m_attributes = attributes;
/*     */   }
/*     */ 
/*     */   public String[] getAllowableAttributeIds()
/*     */   {
/* 111 */     return this.m_allowableAttributeIds;
/*     */   }
/*     */ 
/*     */   public void setAllowableAttributeIds(String[] allowableAttributeIds)
/*     */   {
/* 116 */     this.m_allowableAttributeIds = allowableAttributeIds;
/*     */   }
/*     */ 
/*     */   public RefDataItem[] getAssetTypes()
/*     */   {
/* 121 */     return this.m_assetTypes;
/*     */   }
/*     */ 
/*     */   public void setAssetTypes(RefDataItem[] assetTypes)
/*     */   {
/* 126 */     this.m_assetTypes = assetTypes;
/*     */   }
/*     */ 
/*     */   public String[] getAllowableAssetTypeIds()
/*     */   {
/* 131 */     return this.m_allowableAssetTypeIds;
/*     */   }
/*     */ 
/*     */   public void setAllowableAssetTypeIds(String[] allowableAssetTypes)
/*     */   {
/* 136 */     this.m_allowableAssetTypeIds = allowableAssetTypes;
/*     */   }
/*     */ 
/*     */   public boolean getEntityIsAdvanced()
/*     */   {
/* 147 */     if (this.m_entity != null)
/*     */     {
/* 149 */       ArrayList<ArrayList<AssetEntityRelationship>> alTemp = new ArrayList();
/* 150 */       alTemp.add(this.m_entity.getChildRelationships());
/* 151 */       alTemp.add(this.m_entity.getPeerRelationships());
/*     */ 
/* 153 */       for (ArrayList<AssetEntityRelationship> alRels : alTemp)
/*     */       {
/* 155 */         if (alRels != null)
/*     */         {
/* 157 */           for (AssetEntityRelationship aer : alRels)
/*     */           {
/* 159 */             if ((aer.getDefaultRelationshipCategoryId() > 0L) || ((!FrameworkSettings.getSupportMultiLanguage()) && (StringUtil.stringIsPopulated(aer.getRelationshipDescriptionLabel()))))
/*     */             {
/* 161 */               return true;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 167 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.form.AssetEntityForm
 * JD-Core Version:    0.6.0
 */