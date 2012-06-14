/*    */ package com.bright.assetbank.category.form;
/*    */ 
/*    */ import com.bright.framework.category.form.CategoryForm;
/*    */ 
/*    */ public class ABCategoryForm extends CategoryForm
/*    */   implements CategoryValidationForm
/*    */ {
/* 29 */   private long m_lAdminUserId = 0L;
/* 30 */   private boolean m_bExtendedCategory = false;
/*    */ 
/*    */   public long getAdminUserId()
/*    */   {
/* 35 */     return this.m_lAdminUserId;
/*    */   }
/*    */ 
/*    */   public void setAdminUserId(long a_lAdminUserId)
/*    */   {
/* 40 */     this.m_lAdminUserId = a_lAdminUserId;
/*    */   }
/*    */ 
/*    */   public void setExtendedCategory(boolean a_bExtendedCategory)
/*    */   {
/* 45 */     this.m_bExtendedCategory = a_bExtendedCategory;
/*    */   }
/*    */ 
/*    */   public boolean getExtendedCategory()
/*    */   {
/* 50 */     return this.m_bExtendedCategory;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.form.ABCategoryForm
 * JD-Core Version:    0.6.0
 */