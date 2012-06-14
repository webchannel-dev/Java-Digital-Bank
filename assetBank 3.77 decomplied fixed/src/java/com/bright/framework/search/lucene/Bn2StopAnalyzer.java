/*    */ package com.bright.framework.search.lucene;
/*    */ 
/*    */ import java.io.Reader;
/*    */ import java.util.Set;
/*    */ import org.apache.lucene.analysis.Analyzer;
/*    */ import org.apache.lucene.analysis.StopFilter;
/*    */ import org.apache.lucene.analysis.TokenStream;
/*    */ 
/*    */ public class Bn2StopAnalyzer extends Analyzer
/*    */ {
/* 34 */   private Set m_stopSet = null;
/*    */ 
/* 36 */   public static final String[] ENGLISH_STOP_WORDS = { 
/* 37 */     "a", "an", "and", "are", "as", "at", "be", "but", "by", 
/* 38 */     "for", "if", "in", "into", "is", "it", 
/* 39 */     "not", "of", "on", "or", "s", "such", 
/* 40 */     "t", "that", "the", "their", "then", "there", "these", 
/* 41 */     "they", "this", "to", "was", "will", "with" };
/*    */ 
/*    */   public Bn2StopAnalyzer()
/*    */   {
/* 55 */     this.m_stopSet = StopFilter.makeStopSet(ENGLISH_STOP_WORDS);
/*    */   }
/*    */ 
/*    */   public TokenStream tokenStream(String a_sFieldName, Reader a_reader)
/*    */   {
/* 71 */     return new StopFilter(true,new Bn2LowerCaseTokenizer(a_reader), this.m_stopSet);
/*    */   }
/*    */ 
/*    */   public void setStopWords(String[] a_asStopWords)
/*    */   {
/* 76 */     this.m_stopSet = StopFilter.makeStopSet(a_asStopWords);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.Bn2StopAnalyzer
 * JD-Core Version:    0.6.0
 */