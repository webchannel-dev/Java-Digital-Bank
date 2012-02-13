/*    */ package com.bright.framework.search.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.search.bean.SearchQuery;
/*    */ import com.bright.framework.search.bean.SearchResults;
/*    */ 
/*    */ public class SearchForm extends Bn2Form
/*    */ {
/* 33 */   private SearchResults m_results = null;
/*    */ 
/* 36 */   private SearchQuery m_query = null;
/*    */ 
/*    */   public SearchResults getResults()
/*    */   {
/* 44 */     return this.m_results;
/*    */   }
/*    */ 
/*    */   public void setResults(SearchResults a_results)
/*    */   {
/* 53 */     this.m_results = a_results;
/*    */   }
/*    */ 
/*    */   public SearchQuery getQuery()
/*    */   {
/* 62 */     return this.m_query;
/*    */   }
/*    */ 
/*    */   public void setCriteria(SearchQuery a_query)
/*    */   {
/* 71 */     this.m_query = a_query;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.form.SearchForm
 * JD-Core Version:    0.6.0
 */