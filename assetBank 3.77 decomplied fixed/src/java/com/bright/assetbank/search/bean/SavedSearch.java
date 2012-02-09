/*     */ package com.bright.assetbank.search.bean;
/*     */ 
/*     */ import java.util.Date;
/*     */ 
/*     */ public class SavedSearch
/*     */ {
/*  31 */   private Date m_dtTimeLastRun = null;
/*  32 */   private String m_sName = null;
/*  33 */   private String m_sKeywords = null;
/*  34 */   private boolean m_bRssFeed = false;
/*  35 */   private boolean m_bBuilderSearch = false;
/*  36 */   private long m_lSortAttributeId = 0L;
/*  37 */   private boolean m_bDescending = false;
/*  38 */   private boolean m_bPromoted = false;
/*  39 */   private boolean m_bAvailableToAll = false;
/*  40 */   private String m_sImage = null;
/*  41 */   private boolean m_bAlert = false;
/*  42 */   private BaseSearchQuery m_criteria = null;
/*  43 */   private String m_sCriteriaFile = null;
/*  44 */   private long m_lUserId = -1L;
/*     */ 
/*     */   public String getKeywords()
/*     */   {
/*  48 */     return this.m_sKeywords;
/*     */   }
/*     */ 
/*     */   public void setKeywords(String keywords) {
/*  52 */     this.m_sKeywords = keywords;
/*     */   }
/*     */ 
/*     */   public Date getTimeLastRun() {
/*  56 */     return this.m_dtTimeLastRun;
/*     */   }
/*     */ 
/*     */   public void setTimeLastRun(Date a_dtTimeLastRun) {
/*  60 */     this.m_dtTimeLastRun = a_dtTimeLastRun;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  65 */     return this.m_sName;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  69 */     this.m_sName = name;
/*     */   }
/*     */ 
/*     */   public boolean isRssFeed()
/*     */   {
/*  74 */     return this.m_bRssFeed;
/*     */   }
/*     */ 
/*     */   public void setRssFeed(boolean rssFeed)
/*     */   {
/*  79 */     this.m_bRssFeed = rssFeed;
/*     */   }
/*     */ 
/*     */   public boolean isBuilderSearch()
/*     */   {
/*  84 */     return this.m_bBuilderSearch;
/*     */   }
/*     */ 
/*     */   public void setBuilderSearch(boolean a_iBuilderSearch) {
/*  88 */     this.m_bBuilderSearch = a_iBuilderSearch;
/*     */   }
/*     */ 
/*     */   public long getSortAttributeId()
/*     */   {
/*  93 */     return this.m_lSortAttributeId;
/*     */   }
/*     */ 
/*     */   public void setSortAttributeId(long a_iSortAttributeId) {
/*  97 */     this.m_lSortAttributeId = a_iSortAttributeId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 106 */     int prime = 31;
/* 107 */     int result = 1;
/* 108 */     result = 31 * result + (int)(this.m_lSortAttributeId ^ this.m_lSortAttributeId >>> 32);
/* 109 */     result = 31 * result + (this.m_criteria == null ? 0 : this.m_criteria.hashCode());
/* 110 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 119 */     if (this == obj)
/* 120 */       return true;
/* 121 */     if (obj == null)
/* 122 */       return false;
/* 123 */     if (getClass() != obj.getClass())
/* 124 */       return false;
/* 125 */     SavedSearch other = (SavedSearch)obj;
/* 126 */     if (this.m_lSortAttributeId != other.m_lSortAttributeId)
/* 127 */       return false;
/* 128 */     if (this.m_criteria == null)
/*     */     {
/* 130 */       if (other.m_criteria != null)
/* 131 */         return false;
/*     */     }
/* 133 */     else if (!this.m_criteria.equals(other.m_criteria))
/* 134 */       return false;
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isDescending() {
/* 139 */     return this.m_bDescending;
/*     */   }
/*     */ 
/*     */   public void setDescending(boolean a_iDescending) {
/* 143 */     this.m_bDescending = a_iDescending;
/*     */   }
/*     */ 
/*     */   public void setPromoted(boolean a_bPromoted)
/*     */   {
/* 148 */     this.m_bPromoted = a_bPromoted;
/*     */   }
/*     */ 
/*     */   public boolean getPromoted()
/*     */   {
/* 153 */     return this.m_bPromoted;
/*     */   }
/*     */ 
/*     */   public void setImage(String a_sImage)
/*     */   {
/* 158 */     this.m_sImage = a_sImage;
/*     */   }
/*     */ 
/*     */   public String getImage()
/*     */   {
/* 163 */     return this.m_sImage;
/*     */   }
/*     */ 
/*     */   public void setAvailableToAll(boolean a_bAvailableToAll)
/*     */   {
/* 168 */     this.m_bAvailableToAll = a_bAvailableToAll;
/*     */   }
/*     */ 
/*     */   public boolean getAvailableToAll()
/*     */   {
/* 173 */     return this.m_bAvailableToAll;
/*     */   }
/*     */ 
/*     */   public void setAlert(boolean a_bAlert)
/*     */   {
/* 178 */     this.m_bAlert = a_bAlert;
/*     */   }
/*     */ 
/*     */   public boolean getAlert()
/*     */   {
/* 183 */     return this.m_bAlert;
/*     */   }
/*     */ 
/*     */   public void setCriteria(BaseSearchQuery a_criteria)
/*     */   {
/* 188 */     this.m_criteria = a_criteria;
/*     */   }
/*     */ 
/*     */   public BaseSearchQuery getCriteria()
/*     */   {
/* 193 */     return this.m_criteria;
/*     */   }
/*     */ 
/*     */   public void setCriteriaFile(String a_sCriteriaFile)
/*     */   {
/* 198 */     this.m_sCriteriaFile = a_sCriteriaFile;
/*     */   }
/*     */ 
/*     */   public String getCriteriaFile()
/*     */   {
/* 203 */     return this.m_sCriteriaFile;
/*     */   }
/*     */ 
/*     */   public void setUserId(long a_lUserId)
/*     */   {
/* 208 */     this.m_lUserId = a_lUserId;
/*     */   }
/*     */ 
/*     */   public long getUserId()
/*     */   {
/* 213 */     return this.m_lUserId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.bean.SavedSearch
 * JD-Core Version:    0.6.0
 */