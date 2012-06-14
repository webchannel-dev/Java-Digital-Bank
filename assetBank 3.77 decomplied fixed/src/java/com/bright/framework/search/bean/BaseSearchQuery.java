/*     */ package com.bright.framework.search.bean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.lucene.search.SortField;
/*     */ 
/*     */ public abstract class BaseSearchQuery
/*     */   implements SearchQuery, Serializable
/*     */ {
/*     */   public static final String k_sDefaultOperatorAnd = "AND";
/*     */   public static final String k_sDefaultOperatorOr = "OR";
/*  45 */   private String m_sKeywords = null;
/*  46 */   private SortField[] m_aSortFields = null;
/*  47 */   private int m_iMaxNoOfResults = -1;
/*  48 */   private String m_sDefaultOperator = null;
/*     */ 
/*     */   public BaseSearchQuery()
/*     */   {
/*  52 */     this.m_sDefaultOperator = "OR";
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  67 */     boolean bEmpty = true;
/*     */ 
/*  69 */     if ((this.m_sKeywords != null) && (this.m_sKeywords.length() > 0))
/*     */     {
/*  71 */       bEmpty = false;
/*     */     }
/*     */ 
/*  74 */     return bEmpty;
/*     */   }
/*     */ 
/*     */   public void setKeywords(String a_sKeywords)
/*     */   {
/*  82 */     this.m_sKeywords = a_sKeywords;
/*     */   }
/*     */ 
/*     */   public String getKeywords()
/*     */   {
/*  90 */     return this.m_sKeywords;
/*     */   }
/*     */ 
/*     */   public String getQueryDescription()
/*     */   {
/*  95 */     return this.m_sKeywords;
/*     */   }
/*     */ 
/*     */   public static String prepareUserQuery(String a_sSearchQuery)
/*     */   {
/* 110 */     a_sSearchQuery = a_sSearchQuery.toLowerCase();
/*     */ 
/* 115 */     if (!a_sSearchQuery.matches(".*\".+ and .+\".*"))
/*     */     {
/* 117 */       a_sSearchQuery = a_sSearchQuery.replaceAll(" and ", " AND ");
/* 118 */       a_sSearchQuery = a_sSearchQuery.replaceAll("\\)and ", ") AND ");
/* 119 */       a_sSearchQuery = a_sSearchQuery.replaceAll(" and\\(", " AND (");
/* 120 */       a_sSearchQuery = a_sSearchQuery.replaceAll("\\)and\\(", ") AND (");
/*     */     }
/*     */ 
/* 126 */     if (!a_sSearchQuery.matches(".*\".+ not .+\".*"))
/*     */     {
/* 128 */       a_sSearchQuery = a_sSearchQuery.replaceAll(" not ", " NOT ");
/* 129 */       a_sSearchQuery = a_sSearchQuery.replaceAll("\\)not ", ") NOT ");
/* 130 */       a_sSearchQuery = a_sSearchQuery.replaceAll(" not\\(", " NOT (");
/* 131 */       a_sSearchQuery = a_sSearchQuery.replaceAll("\\)not\\(", ") NOT (");
/*     */     }
/*     */ 
/* 134 */     return a_sSearchQuery;
/*     */   }
/*     */ 
/*     */   public void setSortFields(SortField[] a_sSortFields)
/*     */   {
/* 140 */     this.m_aSortFields = a_sSortFields;
/*     */   }
/*     */ 
/*     */   public SortField[] getSortFields()
/*     */   {
/* 155 */     return this.m_aSortFields;
/*     */   }
/*     */ 
/*     */   public void setMaxNoOfResults(int a_iMaxNoOfResults)
/*     */   {
/* 161 */     this.m_iMaxNoOfResults = a_iMaxNoOfResults;
/*     */   }
/*     */ 
/*     */   public int getMaxNoOfResults()
/*     */   {
/* 166 */     return this.m_iMaxNoOfResults;
/*     */   }
/*     */ 
/*     */   public String getDefaultOperator() {
/* 170 */     return this.m_sDefaultOperator;
/*     */   }
/*     */ 
/*     */   public void setDefaultOperator(String defaultOperator) {
/* 174 */     this.m_sDefaultOperator = defaultOperator;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.bean.BaseSearchQuery
 * JD-Core Version:    0.6.0
 */