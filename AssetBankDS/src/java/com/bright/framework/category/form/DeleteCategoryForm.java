/*    */ package com.bright.framework.category.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.category.bean.Category;
/*    */ import com.bright.framework.category.bean.CategoryImpl;
/*    */ import com.bright.framework.category.bean.FlatCategoryList;
/*    */ import com.bright.framework.category.constant.CategoryConstants;
/*    */ 
/*    */ public class DeleteCategoryForm extends Bn2Form
/*    */   implements CategoryConstants
/*    */ {
/* 31 */   private long m_lCategoryId = 0L;
/* 32 */   private long m_lCategoryIdToDelete = 0L;
/* 33 */   private long m_lCategoryIdToMoveTo = 0L;
/* 34 */   private Category m_category = null;
/* 35 */   private FlatCategoryList m_flatCategoryList = null;
/*    */ 
/*    */   public Category getCategory()
/*    */   {
/* 39 */     if (this.m_category == null)
/*    */     {
/* 41 */       this.m_category = new CategoryImpl();
/*    */     }
/* 43 */     return this.m_category;
/*    */   }
/*    */ 
/*    */   public void setCategory(Category a_vecCategory)
/*    */   {
/* 48 */     this.m_category = a_vecCategory;
/*    */   }
/*    */ 
/*    */   public FlatCategoryList getFlatCategoryList() {
/* 52 */     return this.m_flatCategoryList;
/*    */   }
/*    */ 
/*    */   public void setFlatCategoryList(FlatCategoryList a_flatCategoryList) {
/* 56 */     this.m_flatCategoryList = a_flatCategoryList;
/*    */   }
/*    */ 
/*    */   public long getCategoryId() {
/* 60 */     return this.m_lCategoryId;
/*    */   }
/*    */ 
/*    */   public void setCategoryId(long a_lCategoryId) {
/* 64 */     this.m_lCategoryId = a_lCategoryId;
/*    */   }
/*    */ 
/*    */   public long getCategoryIdToDelete() {
/* 68 */     return this.m_lCategoryIdToDelete;
/*    */   }
/*    */ 
/*    */   public void setCategoryIdToDelete(long a_lCategoryIdToDelete) {
/* 72 */     this.m_lCategoryIdToDelete = a_lCategoryIdToDelete;
/*    */   }
/*    */ 
/*    */   public long getCategoryIdToMoveTo() {
/* 76 */     return this.m_lCategoryIdToMoveTo;
/*    */   }
/*    */ 
/*    */   public void setCategoryIdToMoveTo(long a_lCategoryIdToMoveTo) {
/* 80 */     this.m_lCategoryIdToMoveTo = a_lCategoryIdToMoveTo;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.form.DeleteCategoryForm
 * JD-Core Version:    0.6.0
 */