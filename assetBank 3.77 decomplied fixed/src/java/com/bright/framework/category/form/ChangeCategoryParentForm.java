/*    */ package com.bright.framework.category.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.category.bean.Category;
/*    */ import com.bright.framework.category.bean.CategoryImpl;
/*    */ import com.bright.framework.category.bean.FlatCategoryList;
/*    */ import com.bright.framework.category.constant.CategoryConstants;
/*    */ 
/*    */ public class ChangeCategoryParentForm extends Bn2Form
/*    */   implements CategoryConstants
/*    */ {
/* 32 */   private Category m_category = null;
/* 33 */   private long m_lCategoryTypeId = 0L;
/* 34 */   private long m_lParentId = 0L;
/* 35 */   private long m_lCategoryIdToMove = 0L;
/* 36 */   private long m_lNewParentId = 0L;
/* 37 */   private FlatCategoryList m_flatCategoryList = null;
/*    */ 
/*    */   public ChangeCategoryParentForm()
/*    */   {
/* 41 */     this.m_category = new CategoryImpl();
/*    */   }
/*    */ 
/*    */   public FlatCategoryList getFlatCategoryList() {
/* 45 */     return this.m_flatCategoryList;
/*    */   }
/*    */ 
/*    */   public void setFlatCategoryList(FlatCategoryList a_sFlatCategoryList) {
/* 49 */     this.m_flatCategoryList = a_sFlatCategoryList;
/*    */   }
/*    */ 
/*    */   public long getCategoryIdToMove() {
/* 53 */     return this.m_lCategoryIdToMove;
/*    */   }
/*    */ 
/*    */   public void setCategoryIdToMove(long a_sCategoryIdToMove) {
/* 57 */     this.m_lCategoryIdToMove = a_sCategoryIdToMove;
/*    */   }
/*    */ 
/*    */   public long getNewParentId() {
/* 61 */     return this.m_lNewParentId;
/*    */   }
/*    */ 
/*    */   public void setNewParentId(long a_sNewParentId) {
/* 65 */     this.m_lNewParentId = a_sNewParentId;
/*    */   }
/*    */ 
/*    */   public long getParentId() {
/* 69 */     return this.m_lParentId;
/*    */   }
/*    */ 
/*    */   public void setParentId(long a_sParentId) {
/* 73 */     this.m_lParentId = a_sParentId;
/*    */   }
/*    */ 
/*    */   public Category getCategory() {
/* 77 */     return this.m_category;
/*    */   }
/*    */ 
/*    */   public void setCategory(Category a_sCategory) {
/* 81 */     this.m_category = a_sCategory;
/*    */   }
/*    */ 
/*    */   public long getCategoryTypeId()
/*    */   {
/* 86 */     return this.m_lCategoryTypeId;
/*    */   }
/*    */ 
/*    */   public void setCategoryTypeId(long a_lCategoryTypeId) {
/* 90 */     this.m_lCategoryTypeId = a_lCategoryTypeId;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.form.ChangeCategoryParentForm
 * JD-Core Version:    0.6.0
 */