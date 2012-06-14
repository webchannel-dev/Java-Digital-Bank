/*    */ package com.bright.assetbank.application.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.search.bean.SearchResults;
/*    */ 
/*    */ public class RecentAssetsForm extends Bn2Form
/*    */ {
/* 29 */   private SearchResults m_srRecentAssets = null;
/*    */ 
/*    */   public SearchResults getRecentAssets()
/*    */   {
/* 33 */     return this.m_srRecentAssets;
/*    */   }
/*    */ 
/*    */   public void setRecentAssets(SearchResults a_srRecentAssets)
/*    */   {
/* 38 */     this.m_srRecentAssets = a_srRecentAssets;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.form.RecentAssetsForm
 * JD-Core Version:    0.6.0
 */