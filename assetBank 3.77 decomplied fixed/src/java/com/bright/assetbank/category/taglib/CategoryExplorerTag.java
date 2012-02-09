/*    */ package com.bright.assetbank.category.taglib;
/*    */ 
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ 
/*    */ public class CategoryExplorerTag extends com.bright.framework.category.taglib.CategoryExplorerTag
/*    */   implements AssetBankConstants
/*    */ {
/* 30 */   private long m_lCategoryTypeId = 1L;
/*    */ 
/*    */   public long getCategoryTypeId()
/*    */   {
/* 34 */     return this.m_lCategoryTypeId;
/*    */   }
/*    */ 
/*    */   public void setCategoryTypeId(long a_lCategoryTypeId)
/*    */   {
/* 39 */     this.m_lCategoryTypeId = a_lCategoryTypeId;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.taglib.CategoryExplorerTag
 * JD-Core Version:    0.6.0
 */