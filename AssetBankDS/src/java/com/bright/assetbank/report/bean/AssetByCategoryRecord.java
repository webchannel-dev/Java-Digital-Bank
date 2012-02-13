/*    */ package com.bright.assetbank.report.bean;
/*    */ 
/*    */ import com.bright.framework.category.bean.Category;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class AssetByCategoryRecord
/*    */ {
/* 32 */   private Category m_category = null;
/*    */ 
/* 35 */   private Vector<AssetRecord> m_assets = null;
/*    */ 
/* 38 */   private long m_lNumDirectInCategory = 0L;
/*    */ 
/* 41 */   private long m_lNumUnderCategory = 0L;
/*    */ 
/*    */   public Vector<AssetRecord> getAssets()
/*    */   {
/* 46 */     return this.m_assets;
/*    */   }
/*    */ 
/*    */   public void setAssets(Vector<AssetRecord> a_sAssets) {
/* 50 */     this.m_assets = a_sAssets;
/*    */   }
/*    */ 
/*    */   public Category getCategory() {
/* 54 */     return this.m_category;
/*    */   }
/*    */ 
/*    */   public void setCategory(Category a_sCategory) {
/* 58 */     this.m_category = a_sCategory;
/*    */   }
/*    */ 
/*    */   public long getNumDirectInCategory() {
/* 62 */     return this.m_lNumDirectInCategory;
/*    */   }
/*    */ 
/*    */   public void setNumDirectInCategory(long a_sNumDirectInCategory) {
/* 66 */     this.m_lNumDirectInCategory = a_sNumDirectInCategory;
/*    */   }
/*    */ 
/*    */   public long getNumUnderCategory() {
/* 70 */     return this.m_lNumUnderCategory;
/*    */   }
/*    */ 
/*    */   public void setNumUnderCategory(long a_sNumUnderCategory) {
/* 74 */     this.m_lNumUnderCategory = a_sNumUnderCategory;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.report.bean.AssetByCategoryRecord
 * JD-Core Version:    0.6.0
 */