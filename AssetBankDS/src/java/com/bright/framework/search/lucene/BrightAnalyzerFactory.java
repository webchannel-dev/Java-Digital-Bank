/*     */ package com.bright.framework.search.lucene;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.search.constant.SearchSettings;
/*     */ import com.bright.framework.search.service.AnalyzerFactory;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.analysis.PerFieldAnalyzerWrapper;
/*     */ 
/*     */ public class BrightAnalyzerFactory
/*     */   implements AnalyzerFactory
/*     */ {
/*     */   private AttributeManager m_attributeManager;
/*     */   private String m_sLanguageCode;
/*     */   private boolean m_bAllowStemming;
/*     */ 
/*     */   public BrightAnalyzerFactory(AttributeManager a_attributeManager, String a_sLanguageCode)
/*     */   {
/*  54 */     this(a_attributeManager, a_sLanguageCode, true);
/*     */   }
/*     */ 
/*     */   public BrightAnalyzerFactory(AttributeManager a_attributeManager, String a_sLanguageCode, boolean a_bAllowStemming)
/*     */   {
/*  63 */     this.m_attributeManager = a_attributeManager;
/*  64 */     this.m_sLanguageCode = a_sLanguageCode;
/*  65 */     this.m_bAllowStemming = a_bAllowStemming;
/*     */   }
/*     */ 
/*     */   public Analyzer getAnalyzer()
/*     */     throws Bn2Exception
/*     */   {
/*  74 */     boolean bStemming = (this.m_bAllowStemming) && (SearchSettings.getUseStemming()) && (LanguageUtils.shouldUseStemming(this.m_sLanguageCode));
/*     */ 
/*  80 */     BrightAnalyzer defaultAnalyzer = new BrightAnalyzer(bStemming);
/*     */ 
/*  84 */     Map customDelimiterRegexes = new HashMap();
/*  85 */     Collection<Attribute> attributes = this.m_attributeManager.getAttributes(null);
/*  86 */     for (Attribute attribute : attributes)
/*     */     {
/*  88 */       String sRegex = attribute.getTokenDelimiterRegex();
/*     */ 
/*  90 */       if (StringUtils.isNotEmpty(sRegex))
/*     */       {
/*  92 */         String sFieldName = "f_att_" + attribute.getId();
/*  93 */         customDelimiterRegexes.put(sFieldName, sRegex);
/*     */       }
/*     */ 
/*  97 */       if (attribute.getIsList())
/*     */       {
/*  99 */         defaultAnalyzer.fieldIsUnTokenized("f_att_" + attribute.getId());
/*     */       }
/*     */     }
/*     */ 
/* 103 */     if (customDelimiterRegexes.isEmpty())
/*     */     {
/* 105 */       return defaultAnalyzer;
/*     */     }
/*     */ 
/* 109 */     PerFieldAnalyzerWrapper analyzer = new PerFieldAnalyzerWrapper(defaultAnalyzer);
/*     */ 
/* 111 */     for (Map.Entry entry :(Set<Map.Entry>) customDelimiterRegexes.entrySet())
/*     */     {
/* 113 */       String sFieldName = (String)entry.getKey();
/* 114 */       String sRegex = (String)entry.getValue();
/* 115 */       analyzer.addAnalyzer(sFieldName, new LowerCaseRegexDelimiterAnalyzer(sRegex, bStemming));
/*     */     }
/* 117 */     return analyzer;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.BrightAnalyzerFactory
 * JD-Core Version:    0.6.0
 */