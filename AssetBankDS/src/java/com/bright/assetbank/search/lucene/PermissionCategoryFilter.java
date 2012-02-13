/*    */ package com.bright.assetbank.search.lucene;
/*    */ 
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.framework.search.lucene.FieldValueIsSubsetFilter;
/*    */ import com.bright.framework.search.lucene.LoggingTermsFilter;
/*    */ import com.bright.framework.search.lucene.SearchFilter;
/*    */ import java.io.Serializable;
/*    */ import java.util.Set;
/*    */ import org.apache.lucene.index.Term;
/*    */ import org.apache.lucene.search.Filter;
/*    */ import org.apache.lucene.search.TermsFilter;
/*    */ 
/*    */ public class PermissionCategoryFilter
/*    */   implements SearchFilter, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4553204753267210424L;
/* 42 */   private Set<Long> m_permissionCategories = null;
/*    */ 
/*    */   public void setPermissionCategories(Set<Long> a_categories)
/*    */   {
/* 46 */     this.m_permissionCategories = a_categories;
/*    */   }
/*    */ 
/*    */   public Set<Long> getPermissionCategories()
/*    */   {
/* 51 */     return this.m_permissionCategories;
/*    */   }
/*    */ 
/*    */   public Filter getAsLuceneFilter()
/*    */   {
/* 56 */     if (this.m_permissionCategories == null)
/*    */     {
/* 58 */       return null;
/*    */     }
/*    */ 
/* 61 */     Filter filter = null;
/*    */ 
/* 63 */     if (AssetBankSettings.getUserRequiresAllAccessLevelsToViewAsset())
/*    */     {
/* 65 */       filter = new FieldValueIsSubsetFilter("f_restrictiveCats", ' ', this.m_permissionCategories);
/*    */     }
/*    */     else
/*    */     {
/* 69 */       filter = new LoggingTermsFilter();
/*    */ 
/* 71 */       for (Long lCatId : this.m_permissionCategories)
/*    */       {
/* 73 */         ((TermsFilter)filter).addTerm(new Term("f_restrictiveCats", String.valueOf(lCatId)));
/*    */       }
/*    */     }
/*    */ 
/* 77 */     return filter;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.lucene.PermissionCategoryFilter
 * JD-Core Version:    0.6.0
 */