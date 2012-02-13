/*    */ package com.bright.framework.category.bean;
/*    */ 
/*    */ import com.bright.framework.category.util.CategoryUtil;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public final class FlatCategoryList
/*    */   implements Cloneable
/*    */ {
/* 31 */   int m_iDepth = 0;
/* 32 */   Vector<Category> m_vecCategories = null;
/*    */ 
/*    */   public int getDepth()
/*    */   {
/* 36 */     return this.m_iDepth;
/*    */   }
/*    */ 
/*    */   public void setDepth(int a_iDepth)
/*    */   {
/* 41 */     this.m_iDepth = a_iDepth;
/*    */   }
/*    */ 
/*    */   public Vector<Category> getCategories()
/*    */   {
/* 47 */     if (this.m_vecCategories == null)
/*    */     {
/* 49 */       this.m_vecCategories = new Vector();
/*    */     }
/* 51 */     return this.m_vecCategories;
/*    */   }
/*    */ 
/*    */   public void setCategories(Vector<Category> a_vecCategories)
/*    */   {
/* 56 */     this.m_vecCategories = a_vecCategories;
/*    */   }
/*    */ 
/*    */   public FlatCategoryList clone()
/*    */   {
/*    */     FlatCategoryList ret;
/*    */     try {
/* 64 */       ret = (FlatCategoryList)super.clone();
/*    */     }
/*    */     catch (CloneNotSupportedException e)
/*    */     {
/* 69 */       throw new RuntimeException(e);
/*    */     }
/*    */ 
/* 73 */     if (this.m_vecCategories != null)
/*    */     {
/* 75 */       ret.setCategories((Vector)this.m_vecCategories.clone());
/*    */     }
/*    */ 
/* 78 */     return ret;
/*    */   }
/*    */ 
/*    */   public Category getCategoryById(long a_lId)
/*    */   {
/* 86 */     return CategoryUtil.getCategoryById(this.m_vecCategories, a_lId);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.bean.FlatCategoryList
 * JD-Core Version:    0.6.0
 */