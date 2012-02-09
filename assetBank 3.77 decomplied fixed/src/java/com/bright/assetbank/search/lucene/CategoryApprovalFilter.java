/*     */ package com.bright.assetbank.search.lucene;
/*     */ 
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.search.lucene.FieldValueIsSubsetFilter;
/*     */ import com.bright.framework.search.lucene.LoggingTermsFilter;
/*     */ import com.bright.framework.search.lucene.SearchFilter;
/*     */ import java.io.Serializable;
/*     */ import java.util.Set;
/*     */ import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
/*     */ //import org.apache.lucene.search.BooleanClause.Occur;
/*     */ import org.apache.lucene.search.BooleanFilter;
/*     */ import org.apache.lucene.search.Filter;
/*     */ import org.apache.lucene.search.FilterClause;
/*     */ import org.apache.lucene.search.TermsFilter;
/*     */ 
/*     */ public class CategoryApprovalFilter
/*     */   implements SearchFilter, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4553204753267210424L;
/*  48 */   private Set<Long> m_searchCategories = null;
/*  49 */   private Set<Long> m_permissionCategories = null;
/*  50 */   private boolean m_bAdvancedViewing = false;
/*     */ 
/*     */   public void setSearchCategories(Set<Long> a_categories)
/*     */   {
/*  54 */     this.m_searchCategories = a_categories;
/*     */   }
/*     */ 
/*     */   public Set<Long> getSearchCategories()
/*     */   {
/*  59 */     return this.m_searchCategories;
/*     */   }
/*     */ 
/*     */   public void setPermissionCategories(Set<Long> a_categories)
/*     */   {
/*  64 */     this.m_permissionCategories = a_categories;
/*     */   }
/*     */ 
/*     */   public Set<Long> getPermissionCategories()
/*     */   {
/*  69 */     return this.m_permissionCategories;
/*     */   }
/*     */ 
/*     */   public void setAdvancedViewing(boolean a_bAdvancedViewing)
/*     */   {
/*  74 */     this.m_bAdvancedViewing = a_bAdvancedViewing;
/*     */   }
/*     */ 
/*     */   public boolean getAdvancedViewing()
/*     */   {
/*  79 */     return this.m_bAdvancedViewing;
/*     */   }
/*     */ 
/*     */   public Filter getAsLuceneFilter()
/*     */   {
/*  84 */     if ((getSearchCategories() == null) && (getPermissionCategories() == null))
/*     */     {
/*  86 */       return null;
/*     */     }
/*     */ 
/*  89 */     BooleanFilter topLevelFilter = new BooleanFilter();
/*     */ 
/*  92 */     TermsFilter fullApprovalFilter = new LoggingTermsFilter();
/*  93 */     fullApprovalFilter.addTerm(new Term("f_approvalStatus", String.valueOf(3)));
/*  94 */     topLevelFilter.add(new FilterClause(fullApprovalFilter, BooleanClause.Occur.SHOULD));
/*     */ 
/*  97 */     BooleanFilter approvedCatIdFilter = new BooleanFilter();
/*  98 */     topLevelFilter.add(new FilterClause(approvedCatIdFilter, BooleanClause.Occur.SHOULD));
/*     */ 
/* 101 */     if (this.m_bAdvancedViewing)
/*     */     {
/* 103 */       TermsFilter advancedViewingFilter = new LoggingTermsFilter();
/* 104 */       advancedViewingFilter.addTerm(new Term("f_advancedViewing", String.valueOf("1")));
/* 105 */       topLevelFilter.add(new FilterClause(advancedViewingFilter, BooleanClause.Occur.SHOULD));
/*     */     }
/*     */ 
/* 109 */     if (this.m_searchCategories != null)
/*     */     {
/* 111 */       TermsFilter searchFilter = new LoggingTermsFilter();
/*     */ 
/* 114 */       for (Long lCatId : this.m_searchCategories)
/*     */       {
/* 116 */         searchFilter.addTerm(new Term("f_approvedCats", String.valueOf(lCatId)));
/*     */       }
/*     */ 
/* 120 */       approvedCatIdFilter.add(new FilterClause(searchFilter, BooleanClause.Occur.MUST));
/*     */     }
/*     */ 
/* 124 */     if (this.m_permissionCategories != null)
/*     */     {
/* 127 */       if (AssetBankSettings.getUserRequiresAllAccessLevelsToViewAsset())
/*     */       {
/* 129 */         FieldValueIsSubsetFilter permCatFilter = new FieldValueIsSubsetFilter("f_approvedCats", ' ', this.m_permissionCategories);
/*     */ 
/* 132 */         approvedCatIdFilter.add(new FilterClause(permCatFilter, BooleanClause.Occur.MUST));
/*     */       }
/*     */       else
/*     */       {
/* 136 */         TermsFilter searchFilter = new LoggingTermsFilter();
/*     */ 
/* 139 */         for (Long lCatId : this.m_permissionCategories)
/*     */         {
/* 141 */           searchFilter.addTerm(new Term("f_approvedCats", String.valueOf(lCatId)));
/*     */         }
/*     */ 
/* 144 */         approvedCatIdFilter.add(new FilterClause(searchFilter, BooleanClause.Occur.MUST));
/*     */       }
/*     */     }
/*     */ 
/* 148 */     return topLevelFilter;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.lucene.CategoryApprovalFilter
 * JD-Core Version:    0.6.0
 */