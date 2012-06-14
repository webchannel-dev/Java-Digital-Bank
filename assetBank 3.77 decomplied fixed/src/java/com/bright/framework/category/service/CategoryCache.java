/*     */ package com.bright.framework.category.service;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.FlatCategoryList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ class CategoryCache
/*     */ {
/*  38 */   private final Object m_lock = new Object();
/*  39 */   private FlatCategoryList m_flatCategoryList = new FlatCategoryList();
/*  40 */   private HashMap<Long, Category> m_hmCategories = new HashMap();
/*     */ 
/*     */   public Category getCategory(long a_lCategoryId)
/*     */   {
/*     */     Category cat;
/*  52 */     synchronized (this.m_lock)
/*     */     {
/*  54 */       cat = (Category)this.m_hmCategories.get(Long.valueOf(a_lCategoryId));
/*     */     }
/*     */ 
/*  57 */     return cat;
/*     */   }
/*     */ 
/*     */   public FlatCategoryList getFlatCategoryList()
/*     */   {
/*     */     FlatCategoryList ret;
/*  68 */     synchronized (this.m_lock)
/*     */     {
/*  70 */       ret = this.m_flatCategoryList;
/*     */     }
/*     */ 
/*  73 */     return ret;
/*     */   }
/*     */ 
/*     */   public void populateCache(FlatCategoryList a_flatCategoryList)
/*     */   {
/*  82 */     synchronized (this.m_lock)
/*     */     {
/*  85 */       this.m_flatCategoryList = a_flatCategoryList;
/*     */ 
/*  87 */       Vector<Category> vecAllCats = a_flatCategoryList.getCategories();
/*     */ 
/*  90 */       this.m_hmCategories = new HashMap();
/*     */ 
/*  93 */       for (Category cat : vecAllCats)
/*     */       {
/*  96 */         this.m_hmCategories.put(new Long(cat.getId()), cat);
/*     */       }
/*     */     }
/*     */ 
/* 100 */     GlobalApplication.getInstance().getLogger().info("populateCache completed...");
/*     */   }
/*     */ 
/*     */   public boolean needsBuilding()
/*     */   {
/*     */     boolean bNeedsBuilding;
/* 111 */     synchronized (this.m_lock)
/*     */     {
/* 113 */       bNeedsBuilding = this.m_flatCategoryList.getCategories().isEmpty();
/*     */     }
/*     */ 
/* 116 */     return bNeedsBuilding;
/*     */   }
/*     */ 
/*     */   public void clearCache()
/*     */   {
/* 125 */     synchronized (this.m_lock)
/*     */     {
/* 127 */       this.m_flatCategoryList = new FlatCategoryList();
/* 128 */       this.m_hmCategories = new HashMap();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.service.CategoryCache
 * JD-Core Version:    0.6.0
 */