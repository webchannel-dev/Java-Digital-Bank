/*     */ package com.bright.assetbank.taxonomy.form;
/*     */ 
/*     */ import com.bright.assetbank.category.form.BrowseItemsForm;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.common.bean.KeyValueBean;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class KeywordChooserForm extends BrowseItemsForm
/*     */ {
/*  33 */   private Vector m_keywordList = null;
/*  34 */   private String m_sFilter = null;
/*  35 */   private long m_lCategoryTreeId = 0L;
/*  36 */   private String m_sBrowserName = null;
/*     */ 
/*     */   public Vector getKeywordList()
/*     */   {
/*  40 */     return this.m_keywordList;
/*     */   }
/*     */ 
/*     */   public void setKeywordList(Vector a_sKeywordList) {
/*  44 */     this.m_keywordList = a_sKeywordList;
/*     */   }
/*     */ 
/*     */   public long getCategoryTreeId()
/*     */   {
/*  51 */     return this.m_lCategoryTreeId;
/*     */   }
/*     */ 
/*     */   public void setCategoryTreeId(long a_lCategoryTreeId)
/*     */   {
/*  57 */     this.m_lCategoryTreeId = a_lCategoryTreeId;
/*     */   }
/*     */ 
/*     */   public String getFilter()
/*     */   {
/*  63 */     return this.m_sFilter;
/*     */   }
/*     */ 
/*     */   public void setFilter(String a_sFilter)
/*     */   {
/*  69 */     this.m_sFilter = a_sFilter;
/*     */   }
/*     */ 
/*     */   public void setBrowserName(String a_sBrowserName)
/*     */   {
/*  74 */     this.m_sBrowserName = a_sBrowserName;
/*     */   }
/*     */ 
/*     */   public String getBrowserName()
/*     */   {
/*  79 */     return this.m_sBrowserName;
/*     */   }
/*     */ 
/*     */   public int getNoOfBrowseableKeywords()
/*     */   {
/*  84 */     if (getKeywordList() != null)
/*     */     {
/*  86 */       return getKeywordList().size();
/*     */     }
/*  88 */     return 0;
/*     */   }
/*     */ 
/*     */   public double getColLength()
/*     */   {
/*  93 */     double dColLength = Math.ceil(getNoOfBrowseableKeywords() / 3.0D);
/*  94 */     return dColLength;
/*     */   }
/*     */ 
/*     */   public Vector getBreadcrumbTrail()
/*     */   {
/* 111 */     Vector vecTrail = new Vector();
/* 112 */     String sLink = "?filter=" + getFilter() + "&categoryTypeId=" + getCategoryTreeId();
/*     */ 
/* 114 */     String sLabel = getRootCategoryName();
/*     */ 
/* 116 */     KeyValueBean entry = new KeyValueBean(sLabel, "keywordBrowser" + sLink);
/* 117 */     vecTrail.add(entry);
/*     */ 
/* 120 */     if (getCategory() != null)
/*     */     {
/* 122 */       String sCatLink = "browseByKeyword" + sLink + "&categoryId=" + getCategory().getId();
/* 123 */       KeyValueBean entry2 = new KeyValueBean(getCategory().getName(), sCatLink);
/* 124 */       vecTrail.add(entry2);
/*     */     }
/*     */ 
/* 129 */     return vecTrail;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.form.KeywordChooserForm
 * JD-Core Version:    0.6.0
 */