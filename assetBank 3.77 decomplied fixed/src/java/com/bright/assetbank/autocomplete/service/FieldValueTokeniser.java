/*     */ package com.bright.assetbank.autocomplete.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.search.service.AttributeSearchManager;
/*     */ import com.bright.framework.search.service.AnalyzerFactory;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.analysis.TokenStream;
/*     */ import org.apache.lucene.analysis.tokenattributes.TermAttribute;
/*     */ 
/*     */ class FieldValueTokeniser
/*     */ {
/*     */   private AttributeSearchManager m_attributeSearchManager;
/*  49 */   private Map<String, Analyzer> m_analyzers = new HashMap();
/*     */ 
/*     */   public FieldValueTokeniser(AttributeSearchManager a_attributeSearchManager)
/*     */   {
/*  55 */     this.m_attributeSearchManager = a_attributeSearchManager;
/*     */   }
/*     */ 
/*     */   public Set<String> tokeniseKeywords(String a_sLanguageCode, String a_sAttributeValue)
/*     */     throws IOException, Bn2Exception
/*     */   {
/*  68 */     return tokenise(a_sLanguageCode, null, a_sAttributeValue);
/*     */   }
/*     */ 
/*     */   public Set<String> tokeniseAttributeValue(String a_sLanguageCode, long a_lAttributeId, String a_sAttributeValue)
/*     */     throws IOException, Bn2Exception
/*     */   {
/*  78 */     String sFieldName = "f_att_" + a_lAttributeId;
/*  79 */     return tokenise(a_sLanguageCode, sFieldName, a_sAttributeValue);
/*     */   }
/*     */ 
/*     */   private Set<String> tokenise(String a_sLanguageCode, String a_sFieldName, String a_sAttributeValue)
/*     */     throws Bn2Exception, IOException
/*     */   {
/*  85 */     Analyzer analyzer = analyzerForLanguage(a_sLanguageCode);
/*     */ 
/*  88 */     TokenStream tokenStream = analyzer.tokenStream(a_sFieldName, new StringReader(a_sAttributeValue));
/*     */ 
/*  91 */     TermAttribute termAtt = (TermAttribute)tokenStream.addAttribute(TermAttribute.class);
/*     */ 
/*  94 */     tokenStream.reset();
/*     */ 
/*  96 */     Set tokens = new HashSet();
/*     */ 
/*  98 */     while (tokenStream.incrementToken())
/*     */     {
/* 100 */       tokens.add(termAtt.term());
/*     */     }
/*     */ 
/* 103 */     return tokens;
/*     */   }
/*     */ 
/*     */   private Analyzer analyzerForLanguage(String a_sLanguageCode)
/*     */     throws Bn2Exception
/*     */   {
/* 111 */     Analyzer a = (Analyzer)this.m_analyzers.get(a_sLanguageCode);
/* 112 */     if (a == null)
/*     */     {
/* 117 */       a = this.m_attributeSearchManager.analyzerFactoryForLanguage(a_sLanguageCode, false).getAnalyzer();
/* 118 */       this.m_analyzers.put(a_sLanguageCode, a);
/*     */     }
/* 120 */     return a;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.autocomplete.service.FieldValueTokeniser
 * JD-Core Version:    0.6.0
 */