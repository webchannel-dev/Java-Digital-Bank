/*    */ package com.bright.assetbank.entity.relationship.bean;
/*    */ 
/*    */ public class RelationshipDescriptionEntry
/*    */ {
/* 28 */   private long m_lSourceAssetId = -1L;
/* 29 */   private long m_lTargetAssetId = -1L;
/* 30 */   private long m_lRelationshipTypeId = -1L;
/* 31 */   private String m_sDescription = null;
/*    */ 
/*    */   public long getSourceAssetId()
/*    */   {
/* 35 */     return this.m_lSourceAssetId;
/*    */   }
/*    */ 
/*    */   public void setSourceAssetId(long a_lSourceAssetId)
/*    */   {
/* 40 */     this.m_lSourceAssetId = a_lSourceAssetId;
/*    */   }
/*    */ 
/*    */   public long getTargetAssetId()
/*    */   {
/* 45 */     return this.m_lTargetAssetId;
/*    */   }
/*    */ 
/*    */   public void setTargetAssetId(long a_lTargetAssetId)
/*    */   {
/* 50 */     this.m_lTargetAssetId = a_lTargetAssetId;
/*    */   }
/*    */ 
/*    */   public long getRelationshipTypeId()
/*    */   {
/* 55 */     return this.m_lRelationshipTypeId;
/*    */   }
/*    */ 
/*    */   public void setRelationshipTypeId(long a_lRelationshipTypeId)
/*    */   {
/* 60 */     this.m_lRelationshipTypeId = a_lRelationshipTypeId;
/*    */   }
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 65 */     return this.m_sDescription;
/*    */   }
/*    */ 
/*    */   public void setDescription(String a_sDescription)
/*    */   {
/* 70 */     this.m_sDescription = a_sDescription;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.bean.RelationshipDescriptionEntry
 * JD-Core Version:    0.6.0
 */