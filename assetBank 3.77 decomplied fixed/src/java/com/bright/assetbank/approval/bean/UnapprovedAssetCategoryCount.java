/*    */ package com.bright.assetbank.approval.bean;
/*    */ 
/*    */ import com.bright.framework.category.bean.Category;
/*    */ 
/*    */ public class UnapprovedAssetCategoryCount
/*    */ {
/* 29 */   private Category m_category = null;
/* 30 */   private int m_iCount = 0;
/*    */ 
/*    */   public int getCount()
/*    */   {
/* 35 */     return this.m_iCount;
/*    */   }
/*    */ 
/*    */   public void setCount(int a_iCount)
/*    */   {
/* 40 */     this.m_iCount = a_iCount;
/*    */   }
/*    */ 
/*    */   public Category getCategory()
/*    */   {
/* 45 */     return this.m_category;
/*    */   }
/*    */ 
/*    */   public void setCategory(Category a_sCategory)
/*    */   {
/* 50 */     this.m_category = a_sCategory;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.bean.UnapprovedAssetCategoryCount
 * JD-Core Version:    0.6.0
 */