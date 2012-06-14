/*    */ package com.bright.framework.category.bean;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class AlphabeticCategoryComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object a_cat1, Object a_cat2)
/*    */   {
/* 33 */     String sName1 = ((Category)a_cat1).getName();
/* 34 */     String sName2 = ((Category)a_cat2).getName();
/*    */ 
/* 38 */     return sName1.compareToIgnoreCase(sName2);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.bean.AlphabeticCategoryComparator
 * JD-Core Version:    0.6.0
 */