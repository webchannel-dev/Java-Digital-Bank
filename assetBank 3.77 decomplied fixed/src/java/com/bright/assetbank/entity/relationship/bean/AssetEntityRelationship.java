/*     */ package com.bright.assetbank.entity.relationship.bean;
/*     */ 
/*     */ public class AssetEntityRelationship
/*     */ {
/*  28 */   private long m_lRelatesToAssetEntityId = 0L;
/*  29 */   private long m_lRelatesFromAssetEntityId = 0L;
/*  30 */   private long m_lDefaultRelationshipCategoryId = -1L;
/*  31 */   private String m_sRelationshipDescriptionLabel = null;
/*  32 */   private Boolean m_bRelatesToCategoryExtensionType = null;
/*     */ 
/*     */   public long getRelatesToAssetEntityId()
/*     */   {
/*  36 */     return this.m_lRelatesToAssetEntityId;
/*     */   }
/*     */ 
/*     */   public void setRelatesToAssetEntityId(long a_lRelatesToAssetEntityId)
/*     */   {
/*  41 */     this.m_lRelatesToAssetEntityId = a_lRelatesToAssetEntityId;
/*     */   }
/*     */ 
/*     */   public long getRelatesFromAssetEntityId()
/*     */   {
/*  47 */     return this.m_lRelatesFromAssetEntityId;
/*     */   }
/*     */ 
/*     */   public void setRelatesFromAssetEntityId(long a_sRelatesFromAssetEntityId)
/*     */   {
/*  52 */     this.m_lRelatesFromAssetEntityId = a_sRelatesFromAssetEntityId;
/*     */   }
/*     */ 
/*     */   public boolean getCanRelateToAssetEntity(long a_lRelatesToAssetEntityId)
/*     */   {
/*  57 */     return (this.m_lRelatesToAssetEntityId <= 0L) || (this.m_lRelatesToAssetEntityId == a_lRelatesToAssetEntityId);
/*     */   }
/*     */ 
/*     */   public void setDefaultRelationshipCategoryId(long a_lDefaultRelationshipCategoryId)
/*     */   {
/*  62 */     this.m_lDefaultRelationshipCategoryId = a_lDefaultRelationshipCategoryId;
/*     */   }
/*     */ 
/*     */   public long getDefaultRelationshipCategoryId()
/*     */   {
/*  75 */     return this.m_lDefaultRelationshipCategoryId;
/*     */   }
/*     */ 
/*     */   public void setRelatesToCategoryExtensionType(Boolean a_bRelatesToCategoryExtensionType)
/*     */   {
/*  80 */     this.m_bRelatesToCategoryExtensionType = a_bRelatesToCategoryExtensionType;
/*     */   }
/*     */ 
/*     */   public Boolean getRelatesToCategoryExtensionType()
/*     */   {
/*  85 */     return this.m_bRelatesToCategoryExtensionType;
/*     */   }
/*     */ 
/*     */   public void setRelationshipDescriptionLabel(String a_sRelationshipDescriptionLabel)
/*     */   {
/*  90 */     this.m_sRelationshipDescriptionLabel = a_sRelationshipDescriptionLabel;
/*     */   }
/*     */ 
/*     */   public String getRelationshipDescriptionLabel()
/*     */   {
/* 102 */     return this.m_sRelationshipDescriptionLabel;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.bean.AssetEntityRelationship
 * JD-Core Version:    0.6.0
 */