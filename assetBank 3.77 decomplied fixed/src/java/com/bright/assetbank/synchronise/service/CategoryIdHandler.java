/*    */ package com.bright.assetbank.synchronise.service;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class CategoryIdHandler
/*    */   implements CategoryHandler
/*    */ {
/*    */   public Set<Long> getCategoryIds(List<? extends Object> a_categoryNamesOrIds, long a_lCatTreeId)
/*    */   {
/* 36 */     Set categoryIds = new HashSet();
/* 37 */     for (Iterator i$ = a_categoryNamesOrIds.iterator(); i$.hasNext(); ) { Object obj = i$.next();
/*    */ 
/* 39 */       if (((obj instanceof String)) && (StringUtils.isNotEmpty((String)obj)))
/*    */       {
/* 41 */         categoryIds.add(Long.valueOf(Long.parseLong((String)obj)));
/*    */       }
/* 43 */       else if (((obj instanceof Long)) && (((Long)obj).longValue() > 0L))
/*    */       {
/* 45 */         categoryIds.add((Long)obj);
/*    */       }
/*    */     }
/*    */ 
/* 49 */     return categoryIds;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.service.CategoryIdHandler
 * JD-Core Version:    0.6.0
 */