/*     */ package com.bright.assetbank.autocomplete.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.search.service.AttributeSearchManager;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.collections.Factory;
/*     */ import org.apache.commons.collections.MapUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ class AffectedKeywords
/*     */   implements Serializable
/*     */ {
/*     */   FieldValueTokeniser m_tokeniser;
/*     */   private Map<String, Map<AutoCompleteUpdateManager.ACField, Set<String>>> m_keywordSetsByFieldByLanguage;
/*     */ 
/*     */   public AffectedKeywords(AttributeSearchManager a_attributeSearchManager)
/*     */   {
/*  55 */     this.m_tokeniser = new FieldValueTokeniser(a_attributeSearchManager);
/*     */ 
/*  57 */     this.m_keywordSetsByFieldByLanguage = MapUtils.lazyMap(new HashMap(), new Factory()
/*     */     {
/*     */       public Object create()
/*     */       {
/*  62 */         return MapUtils.lazyMap(new HashMap(), new Factory()
/*     */         {
/*     */           public Object create()
/*     */           {
/*  66 */             return new HashSet();
/*     */           }
/*     */         });
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   void addAttributeValue(String a_sLanguageCode, AutoCompleteUpdateManager.AttributeField a_field, String a_sAttributeValue)
/*     */     throws Bn2Exception
/*     */   {
/*  82 */     if (StringUtils.isEmpty(a_sAttributeValue))
/*     */     {
/*  84 */       return;
/*     */     }
/*     */ 
/*     */     Set tokens;
/*     */     try
/*     */     {
/*  91 */       tokens = this.m_tokeniser.tokeniseAttributeValue(a_sLanguageCode, a_field.getAttributeId(), a_sAttributeValue);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  96 */       throw new Bn2Exception("IOException tokenising from attribute value " + a_sAttributeValue, e);
/*     */     }
/*     */ 
/* 100 */     addTokenisedLowerCasedFieldValue(a_sLanguageCode, a_field, tokens);
/*     */   }
/*     */ 
/*     */   void addFieldValue(String a_sLanguageCode, AutoCompleteUpdateManager.ACField a_field, String a_sText)
/*     */     throws Bn2Exception
/*     */   {
/* 110 */     if (StringUtils.isEmpty(a_sText))
/*     */     {
/* 112 */       return;
/*     */     }
/*     */ 
/*     */     Set tokens;
/*     */     try
/*     */     {
/* 119 */       tokens = this.m_tokeniser.tokeniseKeywords(a_sLanguageCode, a_sText);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 123 */       throw new Bn2Exception("IOException tokenising from attribute value " + a_sText, e);
/*     */     }
/*     */ 
/* 127 */     addTokenisedLowerCasedFieldValue(a_sLanguageCode, a_field, tokens);
/*     */   }
/*     */ 
/*     */   void addTokenisedFieldValue(String a_sLanguageCode, AutoCompleteUpdateManager.ACField a_field, Collection<String> a_tokens)
/*     */   {
/* 133 */     List lowerCasedTokens = new ArrayList(a_tokens.size());
/* 134 */     for (String token : a_tokens)
/*     */     {
/* 136 */       if (!StringUtils.isEmpty(token))
/*     */       {
/* 138 */         lowerCasedTokens.add(token.toLowerCase());
/*     */       }
/*     */     }
/* 141 */     addTokenisedLowerCasedFieldValue(a_sLanguageCode, a_field, lowerCasedTokens);
/*     */   }
/*     */ 
/*     */   private void addTokenisedLowerCasedFieldValue(String a_sLanguageCode, AutoCompleteUpdateManager.ACField a_field, Collection<String> a_tokens)
/*     */   {
/* 151 */     Map keywordSetsByAttribute = (Map)this.m_keywordSetsByFieldByLanguage.get(a_sLanguageCode);
/*     */ 
/* 154 */     Set keywords = (Set)keywordSetsByAttribute.get(a_field);
/*     */ 
/* 157 */     for (String token : a_tokens)
/*     */     {
/* 159 */       if (token.length() >= 3)
/*     */       {
/* 161 */         keywords.add(token);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Iterator<Map.Entry<String, Map<AutoCompleteUpdateManager.ACField, Set<String>>>> keywordSetsByFieldByLanguageIterator()
/*     */   {
/* 168 */     return this.m_keywordSetsByFieldByLanguage.entrySet().iterator();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.autocomplete.service.AffectedKeywords
 * JD-Core Version:    0.6.0
 */