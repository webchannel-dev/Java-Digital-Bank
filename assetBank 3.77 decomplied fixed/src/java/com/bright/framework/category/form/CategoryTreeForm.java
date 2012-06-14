/*    */ package com.bright.framework.category.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.category.constant.CategoryConstants;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class CategoryTreeForm extends Bn2Form
/*    */   implements CategoryConstants
/*    */ {
/* 30 */   private Vector m_vecCategoryTypeList = null;
/* 31 */   private String m_sCategoryTypeName = null;
/* 32 */   private long m_lCategoryId = 0L;
/* 33 */   private long m_lCategoryTypeId = 0L;
/*    */ 
/*    */   public boolean getCategoryTypeListIsEmpty()
/*    */   {
/* 37 */     if (this.m_vecCategoryTypeList != null)
/*    */     {
/* 39 */       return this.m_vecCategoryTypeList.isEmpty();
/*    */     }
/*    */ 
/* 42 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean getRoot()
/*    */   {
/* 47 */     return getCategoryId() == -1L;
/*    */   }
/*    */ 
/*    */   public void setCategoryId(long a_lCategoryId)
/*    */   {
/* 52 */     this.m_lCategoryId = a_lCategoryId;
/*    */   }
/*    */ 
/*    */   public long getCategoryId()
/*    */   {
/* 57 */     return this.m_lCategoryId;
/*    */   }
/*    */ 
/*    */   public void setCategoryTypeId(long a_lCategoryTreeId)
/*    */   {
/* 62 */     this.m_lCategoryTypeId = a_lCategoryTreeId;
/*    */   }
/*    */ 
/*    */   public long getCategoryTypeId()
/*    */   {
/* 67 */     return this.m_lCategoryTypeId;
/*    */   }
/*    */ 
/*    */   public void setCategoryTypeName(String a_sCategoryTreeName)
/*    */   {
/* 72 */     this.m_sCategoryTypeName = a_sCategoryTreeName;
/*    */   }
/*    */ 
/*    */   public String getCategoryTypeName()
/*    */   {
/* 77 */     return this.m_sCategoryTypeName;
/*    */   }
/*    */ 
/*    */   public void setCategoryTypeList(Vector a_vecCategoryTreeList)
/*    */   {
/* 82 */     this.m_vecCategoryTypeList = a_vecCategoryTreeList;
/*    */   }
/*    */ 
/*    */   public Vector getCategoryTypeList()
/*    */   {
/* 87 */     return this.m_vecCategoryTypeList;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.form.CategoryTreeForm
 * JD-Core Version:    0.6.0
 */