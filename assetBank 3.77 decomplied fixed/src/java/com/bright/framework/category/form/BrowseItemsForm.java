/*    */ package com.bright.framework.category.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.category.bean.Category;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class BrowseItemsForm extends Bn2Form
/*    */ {
/* 32 */   private boolean m_bItemListNotNeeded = false;
/* 33 */   private Vector m_vecItemList = null;
/* 34 */   private Category m_category = null;
/* 35 */   private Vector m_vecSubCategoryList = null;
/*    */ 
/*    */   public Vector getSubCategoryList()
/*    */   {
/* 40 */     return this.m_vecSubCategoryList;
/*    */   }
/*    */ 
/*    */   public void setSubCategoryList(Vector a_vecSubCategoryList) {
/* 44 */     this.m_vecSubCategoryList = a_vecSubCategoryList;
/*    */   }
/*    */ 
/*    */   public boolean getSubCategoryListIsEmpty() {
/* 48 */     return (this.m_vecSubCategoryList == null) || (this.m_vecSubCategoryList.isEmpty());
/*    */   }
/*    */ 
/*    */   public void setItemListNotNeeded(boolean a_bItemListNotNeeded)
/*    */   {
/* 54 */     this.m_bItemListNotNeeded = a_bItemListNotNeeded;
/*    */   }
/*    */ 
/*    */   public boolean getItemListNotNeeded() {
/* 58 */     return this.m_bItemListNotNeeded;
/*    */   }
/*    */ 
/*    */   public void setCategory(Category a_category)
/*    */   {
/* 64 */     this.m_category = a_category;
/*    */   }
/*    */ 
/*    */   public Category getCategory() {
/* 68 */     return this.m_category;
/*    */   }
/*    */ 
/*    */   public void setItemList(Vector a_vecItemList)
/*    */   {
/* 74 */     this.m_vecItemList = a_vecItemList;
/*    */   }
/*    */ 
/*    */   public Vector getItemList() {
/* 78 */     return this.m_vecItemList;
/*    */   }
/*    */ 
/*    */   public boolean getItemListIsEmpty() {
/* 82 */     return (this.m_vecItemList == null) || (this.m_vecItemList.isEmpty());
/*    */   }
/*    */ 
/*    */   public long getTopLevelCategoryId()
/*    */   {
/* 87 */     return -1L;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.form.BrowseItemsForm
 * JD-Core Version:    0.6.0
 */