/*     */ package com.bright.framework.category.bean;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class CategoryCache
/*     */ {
/*  29 */   private boolean m_bNeedsBuilding = true;
/*  30 */   private FlatCategoryList m_flatCategoryList = null;
/*  31 */   private HashMap hmCategories = new HashMap();
/*     */ 
/*     */   public synchronized void invalidateCache()
/*     */   {
/*  43 */     this.m_bNeedsBuilding = true;
/*     */   }
/*     */ 
/*     */   public Vector getChildCategories(long a_lCategoryId)
/*     */   {
/*  64 */     Long lKey = new Long(a_lCategoryId);
/*  65 */     Category cat = (Category)this.hmCategories.get(lKey);
/*  66 */     Vector vecCats = cat.getChildCategories();
/*  67 */     return vecCats;
/*     */   }
/*     */ 
/*     */   public Category getCategory(long a_lCategoryId)
/*     */   {
/*  84 */     Long lKey = new Long(a_lCategoryId);
/*  85 */     Category cat = (Category)this.hmCategories.get(lKey);
/*  86 */     return cat;
/*     */   }
/*     */ 
/*     */   public FlatCategoryList getFlatCategoryList()
/*     */   {
/* 101 */     return this.m_flatCategoryList;
/*     */   }
/*     */ 
/*     */   public void populateCache(FlatCategoryList a_flatCategoryList)
/*     */   {
/* 118 */     this.m_flatCategoryList = a_flatCategoryList;
/*     */ 
/* 120 */     Vector vecAllCats = a_flatCategoryList.getCategories();
/*     */ 
/* 123 */     for (int i = 0; i < vecAllCats.size(); i++)
/*     */     {
/* 125 */       Category cat = (Category)vecAllCats.get(i);
/*     */ 
/* 128 */       this.hmCategories.put(new Long(cat.getId()), cat);
/*     */     }
/*     */ 
/* 131 */     this.m_bNeedsBuilding = false;
/*     */   }
/*     */ 
/*     */   public synchronized boolean needsBuilding()
/*     */   {
/* 146 */     return this.m_bNeedsBuilding;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.bean.CategoryCache
 * JD-Core Version:    0.6.0
 */