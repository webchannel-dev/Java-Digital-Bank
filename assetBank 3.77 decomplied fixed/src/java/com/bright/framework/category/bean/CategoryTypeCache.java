/*    */ package com.bright.framework.category.bean;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class CategoryTypeCache
/*    */ {
/* 30 */   private HashMap hmCategoryTypes = new HashMap(10);
/*    */ 
/*    */   public CategoryCache getCache(long a_lTypeId)
/*    */   {
/* 46 */     Long lKey = new Long(a_lTypeId);
/* 47 */     CategoryCache catCache = (CategoryCache)this.hmCategoryTypes.get(lKey);
/* 48 */     return catCache;
/*    */   }
/*    */ 
/*    */   public void setCache(long a_lTypeId, CategoryCache a_cache)
/*    */   {
/* 65 */     Long lKey = new Long(a_lTypeId);
/*    */ 
/* 68 */     this.hmCategoryTypes.put(lKey, a_cache);
/*    */   }
/*    */ 
/*    */   public Set getCategoryTypeIds()
/*    */   {
/* 76 */     return this.hmCategoryTypes.keySet();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.bean.CategoryTypeCache
 * JD-Core Version:    0.6.0
 */