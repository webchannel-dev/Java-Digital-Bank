/*    */ package com.bright.framework.category.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.category.bean.Category;
/*    */ 
/*    */ public class CategoriesForm extends Bn2Form
/*    */ {
/* 29 */   private Category[] m_categories = null;
/*    */ 
/*    */   public void setCategories(Category[] a_categories)
/*    */   {
/* 33 */     this.m_categories = a_categories;
/*    */   }
/*    */ 
/*    */   public Category[] getCategories()
/*    */   {
/* 38 */     return this.m_categories;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.form.CategoriesForm
 * JD-Core Version:    0.6.0
 */