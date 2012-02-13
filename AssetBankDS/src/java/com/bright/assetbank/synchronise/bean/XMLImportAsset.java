/*    */ package com.bright.assetbank.synchronise.bean;
/*    */ 
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ 
/*    */ public class XMLImportAsset extends Asset
/*    */ {
/* 30 */   private String[] m_aFlatCategories = null;
/* 31 */   private String[] m_aFlatAccessLevels = null;
/* 32 */   private long m_lDefaultAccessLevelId = 0L;
/* 33 */   private long m_lDefaultCategoryId = 0L;
/*    */ 
/*    */   public void setFlatCategories(String[] a_aFlatCategories)
/*    */   {
/* 37 */     this.m_aFlatCategories = a_aFlatCategories;
/*    */   }
/*    */ 
/*    */   public String[] getFlatCategories()
/*    */   {
/* 42 */     return this.m_aFlatCategories;
/*    */   }
/*    */ 
/*    */   public void setFlatAccessLevels(String[] a_aFlatAccessLevels)
/*    */   {
/* 47 */     this.m_aFlatAccessLevels = a_aFlatAccessLevels;
/*    */   }
/*    */ 
/*    */   public String[] getFlatAccessLevels()
/*    */   {
/* 52 */     return this.m_aFlatAccessLevels;
/*    */   }
/*    */ 
/*    */   public long getDefaultAccessLevelId()
/*    */   {
/* 58 */     return this.m_lDefaultAccessLevelId;
/*    */   }
/*    */ 
/*    */   public void setDefaultAccessLevelId(long a_lDefaultAccessLevelId)
/*    */   {
/* 64 */     this.m_lDefaultAccessLevelId = a_lDefaultAccessLevelId;
/*    */   }
/*    */ 
/*    */   public long getDefaultCategoryId()
/*    */   {
/* 70 */     return this.m_lDefaultCategoryId;
/*    */   }
/*    */ 
/*    */   public void setDefaultCategoryId(long a_lDefaultCategoryId)
/*    */   {
/* 76 */     this.m_lDefaultCategoryId = a_lDefaultCategoryId;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.XMLImportAsset
 * JD-Core Version:    0.6.0
 */