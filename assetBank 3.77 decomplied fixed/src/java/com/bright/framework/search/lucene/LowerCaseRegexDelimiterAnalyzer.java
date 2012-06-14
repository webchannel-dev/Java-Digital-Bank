/*    */ package com.bright.framework.search.lucene;
/*    */ 
/*    */ import java.io.Reader;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.lucene.analysis.Analyzer;
/*    */ import org.apache.lucene.analysis.LowerCaseFilter;
/*    */ import org.apache.lucene.analysis.PorterStemFilter;
/*    */ import org.apache.lucene.analysis.TokenStream;
/*    */ 
/*    */ public class LowerCaseRegexDelimiterAnalyzer extends Analyzer
/*    */ {
/*    */   private Pattern m_delimiterPattern;
/*    */   private boolean m_bStemming;
/*    */ 
/*    */   public LowerCaseRegexDelimiterAnalyzer(String a_sDelimiterRegex)
/*    */   {
/* 42 */     this(a_sDelimiterRegex, false);
/*    */   }
/*    */ 
/*    */   public LowerCaseRegexDelimiterAnalyzer(String a_sDelimiterRegex, boolean a_bStemming)
/*    */   {
/* 55 */     this.m_delimiterPattern = Pattern.compile(a_sDelimiterRegex);
/* 56 */     this.m_bStemming = a_bStemming;
/*    */   }
/*    */ 
/*    */   public TokenStream tokenStream(String a_sFieldName, Reader a_reader)
/*    */   {
/* 61 */     TokenStream tokenStream = new LowerCaseFilter(new RegexTokenizer(a_reader, this.m_delimiterPattern));
/* 62 */     if (this.m_bStemming)
/*    */     {
/* 64 */       tokenStream = new PorterStemFilter(tokenStream);
/*    */     }
/* 66 */     return tokenStream;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.LowerCaseRegexDelimiterAnalyzer
 * JD-Core Version:    0.6.0
 */