/*     */ package com.bright.framework.category.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.CategoryImpl;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class CategoryAdminForm extends Bn2Form
/*     */   implements CategoryConstants
/*     */ {
/*  36 */   private Vector m_vecCategoryTreeList = null;
/*  37 */   private Vector<Category> m_vecSubCategoryList = null;
/*  38 */   private Vector m_vecAncestorCategoryList = null;
/*  39 */   private long m_lCategoryId = -1L;
/*  40 */   private long m_lCategoryIdToDelete = 0L;
/*  41 */   private long m_lCategoryTreeId = 0L;
/*  42 */   private Category m_newCategory = null;
/*  43 */   private String m_sCategoryName = null;
/*  44 */   private String m_sCategoryTreeName = null;
/*  45 */   private String m_sNodeURL = null;
/*  46 */   private boolean m_bTopLevel = false;
/*     */ 
/*     */   public Category getNewCategory()
/*     */   {
/*  50 */     if (this.m_newCategory == null)
/*     */     {
/*  52 */       this.m_newCategory = new CategoryImpl();
/*     */ 
/*  55 */       Vector translastions = this.m_newCategory.getTranslations();
/*  56 */       for (int i = 0; i < 20; i++)
/*     */       {
/*  58 */         translastions.add(this.m_newCategory.createTranslation(new Language()));
/*     */       }
/*     */     }
/*  61 */     return this.m_newCategory;
/*     */   }
/*     */ 
/*     */   public void setNewCategory(Category a_newCategory)
/*     */   {
/*  66 */     this.m_newCategory = a_newCategory;
/*     */   }
/*     */ 
/*     */   public void setNodeURL(String a_sNodeURL)
/*     */   {
/*  71 */     this.m_sNodeURL = a_sNodeURL;
/*     */   }
/*     */ 
/*     */   public String getNodeURL()
/*     */   {
/*  76 */     return this.m_sNodeURL;
/*     */   }
/*     */ 
/*     */   public boolean getRoot()
/*     */   {
/*  81 */     return this.m_lCategoryId == -1L;
/*     */   }
/*     */ 
/*     */   public boolean getSubCategoryListIsEmpty()
/*     */   {
/*  86 */     if (this.m_vecSubCategoryList != null)
/*     */     {
/*  88 */       return this.m_vecSubCategoryList.isEmpty();
/*     */     }
/*     */ 
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean getSubCategoriesContainsLeaves()
/*     */   {
/*  96 */     if (this.m_vecSubCategoryList != null)
/*     */     {
/*  98 */       for (int i = 0; i < this.m_vecSubCategoryList.size(); i++)
/*     */       {
/* 100 */         if (((Category)this.m_vecSubCategoryList.get(i)).getNumChildCategories() == 0)
/*     */         {
/* 102 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getCategoryTreeListIsEmpty()
/*     */   {
/* 111 */     if (this.m_vecCategoryTreeList != null)
/*     */     {
/* 113 */       return this.m_vecCategoryTreeList.isEmpty();
/*     */     }
/*     */ 
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */   public void setCategoryTreeName(String a_sCategoryTreeName)
/*     */   {
/* 121 */     this.m_sCategoryTreeName = a_sCategoryTreeName;
/*     */   }
/*     */ 
/*     */   public String getCategoryTreeName()
/*     */   {
/* 126 */     return this.m_sCategoryTreeName;
/*     */   }
/*     */ 
/*     */   public void setCategoryName(String a_sCategoryName)
/*     */   {
/* 131 */     this.m_sCategoryName = a_sCategoryName;
/*     */   }
/*     */ 
/*     */   public String getCategoryName()
/*     */   {
/* 136 */     return this.m_sCategoryName;
/*     */   }
/*     */ 
/*     */   public void setSubCategoryList(Vector<Category> a_vecSubCategoryList)
/*     */   {
/* 141 */     this.m_vecSubCategoryList = a_vecSubCategoryList;
/*     */   }
/*     */ 
/*     */   public Vector<Category> getSubCategoryList()
/*     */   {
/* 146 */     return this.m_vecSubCategoryList;
/*     */   }
/*     */ 
/*     */   public void setAncestorCategoryList(Vector a_vecAncestorCategoryList)
/*     */   {
/* 151 */     this.m_vecAncestorCategoryList = a_vecAncestorCategoryList;
/*     */   }
/*     */ 
/*     */   public Vector getAncestorCategoryList()
/*     */   {
/* 156 */     return this.m_vecAncestorCategoryList;
/*     */   }
/*     */ 
/*     */   public void setCategoryId(long a_lCategoryId)
/*     */   {
/* 161 */     this.m_lCategoryId = a_lCategoryId;
/*     */   }
/*     */ 
/*     */   public long getCategoryId()
/*     */   {
/* 166 */     return this.m_lCategoryId;
/*     */   }
/*     */ 
/*     */   public void setCategoryTreeId(long a_lCategoryTreeId)
/*     */   {
/* 171 */     this.m_lCategoryTreeId = a_lCategoryTreeId;
/*     */   }
/*     */ 
/*     */   public long getCategoryTreeId()
/*     */   {
/* 176 */     return this.m_lCategoryTreeId;
/*     */   }
/*     */ 
/*     */   public void setCategoryIdToDelete(long a_lCategoryIdToDelete)
/*     */   {
/* 181 */     this.m_lCategoryIdToDelete = a_lCategoryIdToDelete;
/*     */   }
/*     */ 
/*     */   public long getCategoryIdToDelete()
/*     */   {
/* 186 */     return this.m_lCategoryIdToDelete;
/*     */   }
/*     */ 
/*     */   public void setCategoryTreeList(Vector a_vecCategoryTreeList)
/*     */   {
/* 191 */     this.m_vecCategoryTreeList = a_vecCategoryTreeList;
/*     */   }
/*     */ 
/*     */   public Vector getCategoryTreeList()
/*     */   {
/* 196 */     return this.m_vecCategoryTreeList;
/*     */   }
/*     */ 
/*     */   public boolean isTopLevel()
/*     */   {
/* 201 */     return this.m_bTopLevel;
/*     */   }
/*     */ 
/*     */   public void setTopLevel(boolean a_bTopLevel)
/*     */   {
/* 206 */     this.m_bTopLevel = a_bTopLevel;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.form.CategoryAdminForm
 * JD-Core Version:    0.6.0
 */