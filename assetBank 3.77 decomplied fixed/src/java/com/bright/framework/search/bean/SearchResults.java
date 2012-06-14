/*     */ package com.bright.framework.search.bean;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.builder.ReflectionToStringBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringStyle;
/*     */ 
/*     */ public class SearchResults<T>
/*     */ {
/*  34 */   private Vector<T> m_searchResults = null;
/*  35 */   private int m_iTotalHits = 0;
/*  36 */   private int m_iNumResults = 0;
/*  37 */   private int m_iPageIndex = 0;
/*  38 */   private int m_iPageSize = 0;
/*  39 */   private boolean m_bMaxResultsExceeded = false;
/*     */ 
/*     */   public int getPageIndex()
/*     */   {
/*  44 */     return this.m_iPageIndex;
/*     */   }
/*     */ 
/*     */   public void setPageIndex(int a_sPageIndex)
/*     */   {
/*  50 */     this.m_iPageIndex = a_sPageIndex;
/*     */   }
/*     */ 
/*     */   public int getPageSize()
/*     */   {
/*  56 */     return this.m_iPageSize;
/*     */   }
/*     */ 
/*     */   public void setPageSize(int a_sResultsPerPage)
/*     */   {
/*  62 */     this.m_iPageSize = a_sResultsPerPage;
/*     */   }
/*     */ 
/*     */   public int getNumResults()
/*     */   {
/*  72 */     return this.m_iNumResults;
/*     */   }
/*     */ 
/*     */   public void setNumResults(int a_iNumResults)
/*     */   {
/*  82 */     this.m_iNumResults = a_iNumResults;
/*     */   }
/*     */ 
/*     */   public Vector<T> getSearchResults()
/*     */   {
/*  88 */     if (this.m_searchResults == null)
/*     */     {
/*  90 */       this.m_searchResults = new Vector();
/*     */     }
/*     */ 
/*  93 */     return this.m_searchResults;
/*     */   }
/*     */ 
/*     */   public void setSearchResults(Vector<T> a_sSearchResults)
/*     */   {
/*  99 */     this.m_searchResults = a_sSearchResults;
/*     */   }
/*     */ 
/*     */   public int getNumResultsPopulated()
/*     */   {
/* 105 */     if (this.m_searchResults == null)
/*     */     {
/* 107 */       return 0;
/*     */     }
/*     */ 
/* 110 */     return this.m_searchResults.size();
/*     */   }
/*     */ 
/*     */   public int getNumPages()
/*     */   {
/* 116 */     return (int)Math.ceil(getNumResults() / getPageSize());
/*     */   }
/*     */ 
/*     */   public int getResultOffset()
/*     */   {
/* 122 */     return getPageIndex() * getPageSize();
/*     */   }
/*     */ 
/*     */   public void setMaxResultsExceeded(boolean a_bMaxResultsExceeded)
/*     */   {
/* 127 */     this.m_bMaxResultsExceeded = a_bMaxResultsExceeded;
/*     */   }
/*     */ 
/*     */   public boolean getMaxResultsExceeded()
/*     */   {
/* 132 */     return this.m_bMaxResultsExceeded;
/*     */   }
/*     */ 
/*     */   public int getTotalHits()
/*     */   {
/* 142 */     return this.m_iTotalHits;
/*     */   }
/*     */ 
/*     */   public void setTotalHits(int a_iTotalHits)
/*     */   {
/* 152 */     this.m_iTotalHits = a_iTotalHits;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 158 */     String sResult = null;
/*     */ 
/* 160 */     if ((this.m_searchResults == null) || (this.m_searchResults.size() <= 3))
/*     */     {
/* 162 */       sResult = ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
/*     */     }
/* 164 */     else if ((this.m_searchResults != null) && (this.m_searchResults.size() > 3))
/*     */     {
/* 166 */       sResult = ReflectionToStringBuilder.toStringExclude(this, new String[] { "m_searchResults" });
/*     */ 
/* 168 */       StringBuilder sb = new StringBuilder(ReflectionToStringBuilder.toStringExclude(this, new String[] { "m_searchResults" }));
/*     */ 
/* 170 */       sb.append("\n<< Results (3 of " + this.m_searchResults.size() + " shown) >>\n");
/*     */ 
/* 172 */       for (int i = 0; i < 3; i++)
/*     */       {
/* 174 */         sb.append(ReflectionToStringBuilder.toString(this.m_searchResults.get(i), ToStringStyle.MULTI_LINE_STYLE));
/*     */       }
/*     */ 
/* 177 */       sResult = sResult + sb.toString();
/*     */     }
/*     */ 
/* 180 */     return sResult;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.bean.SearchResults
 * JD-Core Version:    0.6.0
 */