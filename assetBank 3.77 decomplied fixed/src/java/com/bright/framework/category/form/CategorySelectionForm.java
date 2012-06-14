/*     */ package com.bright.framework.category.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class CategorySelectionForm extends Bn2Form
/*     */ {
/*  41 */   private Vector m_vTopLevelCategories = null;
/*  42 */   private Vector m_FlatCategoryList = null;
/*  43 */   private int m_iMaxNumSubCats = 0;
/*  44 */   private Vector m_vecSubCategoryIterator = new Vector();
/*  45 */   private String m_sCategoryIds = null;
/*  46 */   private String[] m_asSelectedCategories = null;
/*  47 */   private String m_sNonListboxCategoryIds = null;
/*  48 */   private boolean m_bCatIdsSetFromSelectedCats = false;
/*  49 */   private List<CategorySelectionListener> m_categorySelectionListeners = new ArrayList();
/*     */ 
/*     */   public void addCategorySelectionListener(CategorySelectionListener a_listener)
/*     */   {
/*  53 */     this.m_categorySelectionListeners.add(a_listener);
/*     */   }
/*     */ 
/*     */   private void processCategoriesSelected(String[] a_categoryIds)
/*     */   {
/*     */     Set categoryIds;
/*  61 */     if (!this.m_categorySelectionListeners.isEmpty())
/*     */     {
/*  63 */       categoryIds = new HashSet();
/*  64 */       if (a_categoryIds != null)
/*     */       {
/*  66 */         for (int i = 0; i < a_categoryIds.length; i++)
/*     */         {
/*  68 */           String sCategoryId = a_categoryIds[i];
/*  69 */           if (!StringUtil.stringIsPopulated(sCategoryId))
/*     */             continue;
/*  71 */           categoryIds.add(Long.valueOf(Long.parseLong(sCategoryId)));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  76 */       for (CategorySelectionListener listener : this.m_categorySelectionListeners)
/*     */       {
/*  78 */         listener.categoriesSelected(this, categoryIds);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vector getFlatCategoryList()
/*     */   {
/*  86 */     return this.m_FlatCategoryList;
/*     */   }
/*     */ 
/*     */   public void setFlatCategoryList(Vector a_vFlatCategoryList)
/*     */   {
/*  92 */     this.m_FlatCategoryList = a_vFlatCategoryList;
/*     */   }
/*     */ 
/*     */   public int getMaxNumOfSubCats()
/*     */   {
/*  98 */     return this.m_iMaxNumSubCats;
/*     */   }
/*     */ 
/*     */   public void setMaxNumOfSubCats(int a_iMaxNumSubCats)
/*     */   {
/* 104 */     this.m_iMaxNumSubCats = a_iMaxNumSubCats;
/*     */ 
/* 106 */     String sDummy = "dummy";
/*     */ 
/* 109 */     for (int i = 1; i < this.m_iMaxNumSubCats; i++)
/*     */     {
/* 111 */       this.m_vecSubCategoryIterator.add(sDummy);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vector getTopLevelCategories()
/*     */   {
/* 118 */     return this.m_vTopLevelCategories;
/*     */   }
/*     */ 
/*     */   public void setTopLevelCategories(Vector a_vTopLevelCategories)
/*     */   {
/* 124 */     this.m_vTopLevelCategories = a_vTopLevelCategories;
/*     */   }
/*     */ 
/*     */   public Vector getSubCategoryIterator()
/*     */   {
/* 129 */     return this.m_vecSubCategoryIterator;
/*     */   }
/*     */ 
/*     */   public String[] getSelectedCategories()
/*     */   {
/* 154 */     if (((this.m_asSelectedCategories == null) || (this.m_asSelectedCategories.length == 0)) && (StringUtil.stringIsPopulated(this.m_sCategoryIds)))
/*     */     {
/* 158 */       this.m_asSelectedCategories = StringUtil.convertToArray(this.m_sCategoryIds, ",");
/*     */     }
/* 160 */     return this.m_asSelectedCategories;
/*     */   }
/*     */ 
/*     */   public void setSelectedCategories(String[] a_asSelectedServiceCategories)
/*     */   {
/* 175 */     this.m_asSelectedCategories = a_asSelectedServiceCategories;
/* 176 */     this.m_sCategoryIds = StringUtil.convertStringArrayToString(a_asSelectedServiceCategories, ",");
/*     */ 
/* 179 */     this.m_bCatIdsSetFromSelectedCats = true;
/*     */ 
/* 181 */     processCategoriesSelected(a_asSelectedServiceCategories);
/*     */   }
/*     */ 
/*     */   public void setSelectedNonListboxCategories(String[] a_asSelectedServiceCategories)
/*     */   {
/* 205 */     this.m_sNonListboxCategoryIds = StringUtil.convertStringArrayToString(a_asSelectedServiceCategories, ",");
/*     */   }
/*     */ 
/*     */   public void setCategoryIds(String a_sCategoryIds)
/*     */   {
/* 210 */     if (!this.m_bCatIdsSetFromSelectedCats)
/*     */     {
/* 212 */       this.m_sCategoryIds = a_sCategoryIds;
/* 213 */       this.m_asSelectedCategories = StringUtil.convertToArray(this.m_sCategoryIds, ",");
/* 214 */       processCategoriesSelected(this.m_asSelectedCategories);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getCategoryIds()
/*     */   {
/* 220 */     return this.m_sCategoryIds;
/*     */   }
/*     */ 
/*     */   public String getNonListboxCategoryIds()
/*     */   {
/* 225 */     return this.m_sNonListboxCategoryIds;
/*     */   }
/*     */ 
/*     */   public boolean getHasSelectedCategory(String a_sCatId)
/*     */   {
/* 230 */     String[] aCats = getSelectedCategories();
/* 231 */     if (aCats != null)
/*     */     {
/* 233 */       for (int i = 0; i < aCats.length; i++)
/*     */       {
/* 235 */         if (aCats[i].equals(a_sCatId))
/*     */         {
/* 237 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 241 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.form.CategorySelectionForm
 * JD-Core Version:    0.6.0
 */