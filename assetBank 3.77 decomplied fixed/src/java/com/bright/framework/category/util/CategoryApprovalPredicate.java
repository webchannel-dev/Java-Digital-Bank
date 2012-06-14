/*    */ package com.bright.framework.category.util;
/*    */ 
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.framework.category.bean.Category;
/*    */ 
/*    */ public class CategoryApprovalPredicate
/*    */   implements CategoryPredicate
/*    */ {
/*    */   private Asset m_asset;
/*    */   private boolean m_bMatchApproved;
/*    */ 
/*    */   public CategoryApprovalPredicate(Asset a_asset, boolean a_bMatchApproved)
/*    */   {
/* 28 */     this.m_asset = a_asset;
/* 29 */     this.m_bMatchApproved = a_bMatchApproved;
/*    */   }
/*    */ 
/*    */   public boolean catMatches(Category a_cat)
/*    */   {
/* 34 */     if (a_cat != null)
/*    */     {
/* 36 */       return this.m_asset.isApproved(a_cat) == this.m_bMatchApproved;
/*    */     }
/* 38 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.util.CategoryApprovalPredicate
 * JD-Core Version:    0.6.0
 */