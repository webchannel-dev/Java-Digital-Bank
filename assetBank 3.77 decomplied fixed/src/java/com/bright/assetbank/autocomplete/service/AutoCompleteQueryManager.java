/*     */ package com.bright.assetbank.autocomplete.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.autocomplete.bean.AutoCompleteType;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.FlatCategoryList;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.service.LanguageManager;
/*     */ import com.bright.framework.search.lucene.BTopDocs;
/*     */ import com.bright.framework.search.service.IndexManager;
/*     */ import com.bright.framework.search.util.LuceneUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.lucene.document.Document;
/*     */ import org.apache.lucene.document.Field;
/*     */ import org.apache.lucene.search.SortField;
/*     */ import org.apache.lucene.search.TopDocs;
/*     */ 
/*     */ public class AutoCompleteQueryManager extends Bn2Manager
/*     */ {
/*     */   public static final String c_ksClassName = "AutoCompletionQueryManager";
/*     */   private AttributeManager m_attributeManager;
/*     */   private AutoCompleteIndexManagers m_autoCompleteIndexManagers;
/*     */   private CategoryManager m_categoryManager;
/*     */   private LanguageManager m_languageManager;
/*     */ 
/*     */   public List<String> findKeywordsByPrefix(AutoCompleteType a_completeType, String a_sLanguageCode, String a_sPrefix, int a_iMaxResults)
/*     */     throws Bn2Exception
/*     */   {
/*  74 */     return findKeywordsByPrefix(a_completeType, -1L, a_sLanguageCode, a_sPrefix, a_iMaxResults);
/*     */   }
/*     */ 
/*     */   public List<String> findKeywordsByPrefix(AutoCompleteType a_completeType, long a_lAttributeId, String a_sLanguageCode, String a_sPrefix, int a_iMaxResults)
/*     */     throws Bn2Exception
/*     */   {
/*  82 */     boolean bAllAttributes = a_lAttributeId <= 0L;
/*     */ 
/*  87 */     if (!bAllAttributes)
/*     */     {
/*  90 */       Attribute attribute = this.m_attributeManager.getAttribute(null, a_lAttributeId);
/*  91 */       if (attribute.getIsKeywordPicker())
/*     */       {
/*  93 */         return findKeywordsForPickerAttribute(attribute, a_sLanguageCode, a_sPrefix, a_iMaxResults);
/*     */       }
/*     */     }
/*  96 */     return findKeywordsUsingACIndex(a_lAttributeId, a_sLanguageCode, a_sPrefix, a_iMaxResults);
/*     */   }
/*     */ 
/*     */   private List<String> findKeywordsForPickerAttribute(Attribute a_attribute, String a_sLanguageCode, String a_sPrefix, int a_iMaxResults)
/*     */     throws Bn2Exception
/*     */   {
/* 103 */     long lTreeId = a_attribute.getTreeId();
/* 104 */     Language language = this.m_languageManager.getLanguageByCode(null, a_sLanguageCode);
/* 105 */     FlatCategoryList categoryList = this.m_categoryManager.getFlatCategoryList(null, lTreeId, language);
/*     */ 
/* 108 */     String sLowerCasePrefix = a_sPrefix.toLowerCase();
/* 109 */     SortedSet foundCatNames = new TreeSet();
/* 110 */     for (Object oCat : categoryList.getCategories())
/*     */     {
/* 112 */       Category cat = (Category)oCat;
/* 113 */       if (cat.getFullName().toLowerCase().matches("(\\A" + sLowerCasePrefix + ".*)|(.*\\W)" + sLowerCasePrefix + ".*"))
/*     */       {
/* 115 */         foundCatNames.add(cat.getFullName());
/*     */       }
/*     */     }
/* 118 */     return new ArrayList(foundCatNames);
/*     */   }
/*     */ 
/*     */   private List<String> findKeywordsUsingACIndex(long a_lAttributeId, String a_sLanguageCode, String a_sPrefix, int a_iMaxResults)
/*     */     throws Bn2Exception
/*     */   {
/* 125 */     boolean bAllAttributes = a_lAttributeId <= 0L;
/* 126 */     IndexManager indexManager = this.m_autoCompleteIndexManagers.indexManagerForLanguage(a_sLanguageCode);
/* 127 */     if (!indexManager.indexExists())
/*     */     {
/* 129 */       this.m_logger.warn("AutoCompletionQueryManager: no index exists yet for language " + a_sLanguageCode);
/* 130 */       return Collections.emptyList();
/*     */     }
/*     */ 
/* 134 */     SortField sortByIsAC = new SortField("f_isAC", 4, true);
/* 135 */     SortField sortByHitCount = new SortField("f_hitCount", 4, true);
/*     */ 
/* 137 */     SortField[] sortFields = { sortByIsAC, sortByHitCount };
/*     */ 
/* 139 */     String sQuery = "";
/* 140 */     if (!bAllAttributes)
/*     */     {
/* 142 */       sQuery = sQuery + "f_fieldId:" + InternalACUtil.fieldIdFromAttributeId(a_lAttributeId) + " AND ";
/*     */     }
/*     */ 
/* 145 */     sQuery = sQuery + "f_keyword:" + LuceneUtil.escape(a_sPrefix) + "*";
/*     */ 
/* 151 */     Collection keywords = new LinkedHashSet();
/* 152 */     BTopDocs bhits = indexManager.getDocuments(sQuery, sortFields, "AND", null, a_iMaxResults);
/*     */     try
/*     */     {
/* 156 */       TopDocs hits = bhits.getHits();
/*     */    
                int i;
/* 158 */       for (i = 0; (i < hits.scoreDocs.length) && (i < a_iMaxResults); i++)
/*     */       {
/* 160 */         Document doc = bhits.getDocumentByHitIndex(i);
/* 161 */         String sKeyword = doc.getField("f_keyword").stringValue();
/* 162 */         keywords.add(sKeyword);
/*     */       }
/*     */ 
/* 165 */       //i = new ArrayList(keywords);
                return new ArrayList(keywords);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */       
/* 169 */       throw new Bn2Exception("IOException in AutoCompletionQueryManager.findKeywordsByPrefix", e);
/*     */     }
/*     */     finally
/*     */     {
/* 173 */       if (bhits != null)
/*     */       {
/*     */         try
/*     */         {
/* 177 */           bhits.close();
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/* 181 */           this.m_logger.error("Exception closing BHits", e);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 190 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setAutoCompleteIndexManagers(AutoCompleteIndexManagers a_autoCompleteIndexManagers)
/*     */   {
/* 195 */     this.m_autoCompleteIndexManagers = a_autoCompleteIndexManagers;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_categoryManager)
/*     */   {
/* 200 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/* 205 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.autocomplete.service.AutoCompleteQueryManager
 * JD-Core Version:    0.6.0
 */