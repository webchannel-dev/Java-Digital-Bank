/*    */ package com.bright.assetbank.entity.bean;
/*    */ 
/*    */ import com.bright.assetbank.entity.constant.AssetEntityConstants;
/*    */ 
/*    */ public class AssetEntityRetreivalCriteria
/*    */   implements AssetEntityConstants
/*    */ {
/* 28 */   private boolean m_bFileEntitiesOnly = false;
/* 29 */   private boolean m_bSearchableOnly = false;
/* 30 */   private boolean m_bQuickSearch = false;
/* 31 */   private boolean m_bOnlyParentlessEntities = false;
/* 32 */   private boolean m_bMatchAttributeOnly = false;
/* 33 */   private long[] m_aRestrictions = null;
/* 34 */   private int m_iCategoryExtensionStatus = 3;
/*    */ 
/*    */   public boolean isFileEntitiesOnly()
/*    */   {
/* 38 */     return this.m_bFileEntitiesOnly;
/*    */   }
/*    */ 
/*    */   public void setFileEntitiesOnly(boolean a_bFileEntitiesOnly) {
/* 42 */     this.m_bFileEntitiesOnly = a_bFileEntitiesOnly;
/*    */   }
/*    */ 
/*    */   public boolean isSearchableOnly() {
/* 46 */     return this.m_bSearchableOnly;
/*    */   }
/*    */ 
/*    */   public void setSearchableOnly(boolean a_bSearchableOnly) {
/* 50 */     this.m_bSearchableOnly = a_bSearchableOnly;
/*    */   }
/*    */ 
/*    */   public boolean isQuickSearch() {
/* 54 */     return this.m_bQuickSearch;
/*    */   }
/*    */ 
/*    */   public void setQuickSearch(boolean a_bQuickSearch) {
/* 58 */     this.m_bQuickSearch = a_bQuickSearch;
/*    */   }
/*    */ 
/*    */   public boolean isOnlyParentlessEntities() {
/* 62 */     return this.m_bOnlyParentlessEntities;
/*    */   }
/*    */ 
/*    */   public void setOnlyParentlessEntities(boolean a_bOnlyParentlessEntities) {
/* 66 */     this.m_bOnlyParentlessEntities = a_bOnlyParentlessEntities;
/*    */   }
/*    */ 
/*    */   public boolean isMatchAttributeOnly() {
/* 70 */     return this.m_bMatchAttributeOnly;
/*    */   }
/*    */ 
/*    */   public void setMatchAttributeOnly(boolean a_bMatchAttributeOnly) {
/* 74 */     this.m_bMatchAttributeOnly = a_bMatchAttributeOnly;
/*    */   }
/*    */ 
/*    */   public long[] getRestrictions() {
/* 78 */     return this.m_aRestrictions;
/*    */   }
/*    */ 
/*    */   public void setRestrictions(long[] a_aRestrictions) {
/* 82 */     this.m_aRestrictions = a_aRestrictions;
/*    */   }
/*    */ 
/*    */   public int getCategoryExtensionStatus() {
/* 86 */     return this.m_iCategoryExtensionStatus;
/*    */   }
/*    */ 
/*    */   public void setCategoryExtensionStatus(int a_iCategoryExtensionStatus) {
/* 90 */     this.m_iCategoryExtensionStatus = a_iCategoryExtensionStatus;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.bean.AssetEntityRetreivalCriteria
 * JD-Core Version:    0.6.0
 */