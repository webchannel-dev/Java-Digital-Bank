/*     */ package com.bright.assetbank.search.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.search.bean.SearchBuilderClause;
/*     */ import com.bright.framework.category.form.CategorySelectionForm;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class SearchBuilderForm extends BaseSearchForm
/*     */ {
/*  37 */   private Vector<SearchBuilderClause> m_vClauses = null;
/*  38 */   private Vector m_vOperators = null;
/*  39 */   private Vector m_vSearchAttributes = null;
/*     */ 
/*     */   public SearchBuilderForm() throws Exception
/*     */   {
/*  43 */     this.m_vClauses = new Vector(10);
/*     */ 
/*  45 */     for (int i = 0; i < 10; i++)
/*     */     {
/*  47 */       SearchBuilderClause clause = new SearchBuilderClause();
/*  48 */       this.m_vClauses.add(clause);
/*     */ 
/*  50 */       if (i != 0)
/*     */         continue;
/*  52 */       clause.setVisible(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public long[] getCategoryIdArray()
/*     */   {
/*  59 */     String sCats = getDescriptiveCategoryForm().getCategoryIds();
/*  60 */     return getIdArray(sCats);
/*     */   }
/*     */ 
/*     */   public long[] getPermissionCategoryIdArray()
/*     */   {
/*  65 */     String sCats = getPermissionCategoryForm().getCategoryIds();
/*  66 */     return getIdArray(sCats);
/*     */   }
/*     */ 
/*     */   private long[] getIdArray(String a_sCats)
/*     */   {
/*  71 */     if (StringUtils.isEmpty(a_sCats))
/*     */     {
/*  73 */       return new long[0];
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  78 */       return StringUtil.getIdsArray(a_sCats);
/*     */     }
/*     */     catch (Bn2Exception e) {
/*     */     }
/*  82 */     return new long[0];
/*     */   }
/*     */ 
/*     */   public Vector<SearchBuilderClause> getClauses()
/*     */   {
/*  88 */     return this.m_vClauses;
/*     */   }
/*     */ 
/*     */   public void setClauses(Vector<SearchBuilderClause> a_iClauses)
/*     */   {
/*  93 */     this.m_vClauses = a_iClauses;
/*     */   }
/*     */ 
/*     */   public Vector getOperators()
/*     */   {
/*  98 */     return this.m_vOperators;
/*     */   }
/*     */ 
/*     */   public void setOperators(Vector a_iOperators)
/*     */   {
/* 103 */     this.m_vOperators = a_iOperators;
/*     */   }
/*     */ 
/*     */   public Vector getSearchAttributes()
/*     */   {
/* 108 */     return this.m_vSearchAttributes;
/*     */   }
/*     */ 
/*     */   public void setSearchAttributes(Vector a_vSearchAttributes)
/*     */   {
/* 113 */     this.m_vSearchAttributes = a_vSearchAttributes;
/*     */   }
/*     */ 
/*     */   public boolean clauseIsHidden(SearchBuilderClause a_clause)
/*     */   {
/* 118 */     if (getSearchAttributes() != null)
/*     */     {
/* 120 */       for (int i = 0; i < getSearchAttributes().size(); i++)
/*     */       {
/* 122 */         Attribute att = (Attribute)getSearchAttributes().elementAt(i);
/* 123 */         if (att.getId() == a_clause.getAttributeId())
/*     */         {
/* 125 */           return false;
/*     */         }
/*     */       }
/*     */     }
/* 129 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.form.SearchBuilderForm
 * JD-Core Version:    0.6.0
 */