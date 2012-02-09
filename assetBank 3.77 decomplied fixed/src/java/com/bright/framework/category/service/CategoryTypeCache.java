/*    */ package com.bright.framework.category.service;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Set;
/*    */ 
/*    */ class CategoryTypeCache
/*    */ {
/* 31 */   private HashMap hmCategoryTypes = new HashMap(10);
/*    */ 
/*    */   public CategoryCache getCache(long a_lTypeId)
/*    */   {
/* 48 */     CategoryCache catCache = (CategoryCache)this.hmCategoryTypes.get(Long.valueOf(a_lTypeId));
/*    */ 
/* 50 */     if (catCache == null)
/*    */     {
/* 52 */       catCache = new CategoryCache();
/* 53 */       this.hmCategoryTypes.put(Long.valueOf(a_lTypeId), catCache);
/*    */     }
/*    */ 
/* 56 */     return catCache;
/*    */   }
/*    */ 
/*    */   public void setCache(long a_lTypeId, CategoryCache a_cache)
/*    */   {
/* 73 */     Long lKey = new Long(a_lTypeId);
/*    */ 
/* 76 */     this.hmCategoryTypes.put(lKey, a_cache);
/*    */   }
/*    */ 
/*    */   public Set getCategoryTypeIds()
/*    */   {
/* 84 */     return this.hmCategoryTypes.keySet();
/*    */   }
/*    */ 
/*    */   public Collection<CategoryCache> getAllCaches()
/*    */   {
/* 93 */     return this.hmCategoryTypes.values();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.service.CategoryTypeCache
 * JD-Core Version:    0.6.0
 */