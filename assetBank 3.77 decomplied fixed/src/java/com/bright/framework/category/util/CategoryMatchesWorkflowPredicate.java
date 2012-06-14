/*    */ package com.bright.framework.category.util;
/*    */ 
/*    */ import com.bright.framework.category.bean.Category;
/*    */ 
/*    */ public class CategoryMatchesWorkflowPredicate
/*    */   implements CategoryPredicate
/*    */ {
/* 22 */   public String m_sWorkflowName = null;
/*    */ 
/*    */   public CategoryMatchesWorkflowPredicate(String a_sWorkflowName)
/*    */   {
/* 26 */     this.m_sWorkflowName = a_sWorkflowName;
/*    */   }
/*    */ 
/*    */   public boolean catMatches(Category a_cat)
/*    */   {
/* 31 */     if (a_cat != null)
/*    */     {
/* 33 */       return a_cat.getWorkflowName().equals(this.m_sWorkflowName);
/*    */     }
/* 35 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.util.CategoryMatchesWorkflowPredicate
 * JD-Core Version:    0.6.0
 */