/*    */ package com.bright.assetbank.category.form;
/*    */ 
/*    */ import com.bright.framework.category.bean.Category;
/*    */ 
/*    */ public class CategoryAdminForm extends com.bright.framework.category.form.CategoryAdminForm
/*    */   implements CategoryValidationForm
/*    */ {
/* 23 */   private boolean m_bExtendedCategory = false;
/*    */ 
/*    */   public void setExtendedCategory(boolean a_bExtendedCategory)
/*    */   {
/* 27 */     this.m_bExtendedCategory = a_bExtendedCategory;
/*    */   }
/*    */ 
/*    */   public boolean getExtendedCategory()
/*    */   {
/* 32 */     return this.m_bExtendedCategory;
/*    */   }
/*    */ 
/*    */   public int getNoOfExtendedCategories()
/*    */   {
/* 37 */     int iCount = 0;
/* 38 */     if (getSubCategoryList() != null)
/*    */     {
/* 40 */       for (Category cat : getSubCategoryList())
/*    */       {
/* 42 */         if (cat.getExtensionAssetId() > 0L)
/*    */         {
/* 44 */           iCount++;
/*    */         }
/*    */       }
/*    */     }
/* 48 */     return iCount;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.form.CategoryAdminForm
 * JD-Core Version:    0.6.0
 */