/*    */ package com.bright.assetbank.taxonomy;
/*    */ 
/*    */ import com.bright.assetbank.taxonomy.bean.Keyword;
/*    */ import com.bright.framework.category.bean.Category;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class TaxonomyUtil
/*    */ {
/*    */   public static Vector<Keyword> convertToKeywords(Vector<Category> a_vecCategories)
/*    */   {
/* 17 */     Vector vecKeywords = null;
/* 18 */     if (a_vecCategories != null)
/*    */     {
/* 20 */       vecKeywords = new Vector(a_vecCategories.size());
/* 21 */       for (int i = 0; i < a_vecCategories.size(); i++)
/*    */       {
/* 23 */         Category category = (Category)a_vecCategories.elementAt(i);
/*    */ 
/* 25 */         Keyword keyword = new Keyword(category);
/*    */ 
/* 29 */         if (keyword.getAncestors() != null)
/*    */         {
/* 31 */           keyword.setAncestors(convertToKeywords(keyword.getAncestors()));
/*    */         }
/* 33 */         keyword.setInMasterList(true);
/* 34 */         vecKeywords.add(keyword);
/*    */       }
/*    */     }
/* 37 */     return vecKeywords;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.TaxonomyUtil
 * JD-Core Version:    0.6.0
 */