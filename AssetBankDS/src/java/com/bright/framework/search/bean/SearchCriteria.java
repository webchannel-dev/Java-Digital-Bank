/*     */ package com.bright.framework.search.bean;
/*     */ 
/*     */ import com.bright.framework.search.lucene.FieldDateRange;
/*     */ import com.bright.framework.search.lucene.FieldNumericRange;
/*     */ import org.apache.lucene.search.SortField;
/*     */ 
/*     */ public abstract class SearchCriteria
/*     */ {
/*     */   public static final String k_sDefaultOperatorAnd = "AND";
/*     */   public static final String k_sDefaultOperatorOr = "OR";
/*  45 */   private String m_sKeywords = null;
/*  46 */   private SortField[] m_aSortFields = null;
/*  47 */   private int m_iMaxNoOfResults = 0;
/*  48 */   private String m_sDefaultOperator = null;
/*     */ 
/*     */   public SearchCriteria()
/*     */   {
/*  52 */     this.m_sDefaultOperator = "OR";
/*     */   }
/*     */ 
/*     */   public abstract String getLuceneQuery();
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  74 */     boolean bEmpty = true;
/*     */ 
/*  76 */     if ((this.m_sKeywords != null) && (this.m_sKeywords.length() > 0))
/*     */     {
/*  78 */       bEmpty = false;
/*     */     }
/*     */ 
/*  81 */     return bEmpty;
/*     */   }
/*     */ 
/*     */   public void setKeywords(String a_sKeywords)
/*     */   {
/*  89 */     this.m_sKeywords = a_sKeywords;
/*     */   }
/*     */ 
/*     */   public String getKeywords()
/*     */   {
/*  97 */     return this.m_sKeywords;
/*     */   }
/*     */ 
/*     */   public static String prepareUserQuery(String a_sSearchQuery)
/*     */   {
/* 112 */     a_sSearchQuery = a_sSearchQuery.toLowerCase();
/*     */ 
/* 117 */     if (!a_sSearchQuery.matches(".*\".+ and .+\".*"))
/*     */     {
/* 119 */       a_sSearchQuery = a_sSearchQuery.replaceAll(" and ", " AND ");
/* 120 */       a_sSearchQuery = a_sSearchQuery.replaceAll("\\)and ", ") AND ");
/* 121 */       a_sSearchQuery = a_sSearchQuery.replaceAll(" and\\(", " AND (");
/* 122 */       a_sSearchQuery = a_sSearchQuery.replaceAll("\\)and\\(", ") AND (");
/*     */     }
/*     */ 
/* 128 */     if (!a_sSearchQuery.matches(".*\".+ not .+\".*"))
/*     */     {
/* 130 */       a_sSearchQuery = a_sSearchQuery.replaceAll(" not ", " NOT ");
/* 131 */       a_sSearchQuery = a_sSearchQuery.replaceAll("\\)not ", ") NOT ");
/* 132 */       a_sSearchQuery = a_sSearchQuery.replaceAll(" not\\(", " NOT (");
/* 133 */       a_sSearchQuery = a_sSearchQuery.replaceAll("\\)not\\(", ") NOT (");
/*     */     }
/*     */ 
/* 136 */     return a_sSearchQuery;
/*     */   }
/*     */ 
/*     */   public void setSortFields(SortField[] a_sSortFields)
/*     */   {
/* 142 */     this.m_aSortFields = a_sSortFields;
/*     */   }
/*     */ 
/*     */   public SortField[] getSortFields()
/*     */   {
/* 157 */     return this.m_aSortFields;
/*     */   }
/*     */ 
/*     */   public void setMaxNoOfResults(int a_iMaxNoOfResults)
/*     */   {
/* 163 */     this.m_iMaxNoOfResults = a_iMaxNoOfResults;
/*     */   }
/*     */ 
/*     */   public int getMaxNoOfResults()
/*     */   {
/* 168 */     return this.m_iMaxNoOfResults;
/*     */   }
/*     */ 
/*     */   public String getDefaultOperator() {
/* 172 */     return this.m_sDefaultOperator;
/*     */   }
/*     */ 
/*     */   public void setDefaultOperator(String defaultOperator) {
/* 176 */     this.m_sDefaultOperator = defaultOperator;
/*     */   }
/*     */ 
/*     */   public FieldDateRange[] getDateFilter()
/*     */   {
/* 181 */     return null;
/*     */   }
/*     */ 
/*     */   public FieldNumericRange[] getNumericFilter()
/*     */   {
/* 186 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.bean.SearchCriteria
 * JD-Core Version:    0.6.0
 */