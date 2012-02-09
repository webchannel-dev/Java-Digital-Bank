/*    */ package com.bright.framework.search.lucene;
/*    */ 
/*    */ import java.io.Reader;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import org.apache.lucene.analysis.Analyzer;
/*    */ import org.apache.lucene.analysis.KeywordTokenizer;
/*    */ import org.apache.lucene.analysis.PorterStemFilter;
/*    */ import org.apache.lucene.analysis.TokenStream;
/*    */ import org.apache.lucene.analysis.standard.StandardFilter;
/*    */ 
/*    */ public class BrightAnalyzer extends Analyzer
/*    */ {
/* 39 */   private boolean m_bStem = false;
/*    */ 
/* 42 */   private Set<String> m_untokenizedFields = new HashSet();
/*    */ 
/*    */   public BrightAnalyzer(boolean a_bStem)
/*    */   {
/* 46 */     this.m_bStem = a_bStem;
/*    */   }
/*    */ 
/*    */   public BrightAnalyzer(String a_sStem)
/*    */   {
/* 54 */     this(Boolean.parseBoolean(a_sStem));
/*    */   }
/*    */ 
/*    */   public TokenStream tokenStream(String a_sFieldName, Reader a_reader)
/*    */   {
/* 66 */     if ((a_sFieldName != null) && (("f_id".equals(a_sFieldName)) || (a_sFieldName.startsWith("f_dbl_")) || (a_sFieldName.startsWith("f_long_")) || (this.m_untokenizedFields.contains(a_sFieldName))))
/*    */     {
/* 72 */       return new KeywordTokenizer(a_reader);
/*    */     }
/*    */ 
/* 76 */     if ("f_filename".equals(a_sFieldName))
/*    */     {
/* 78 */       return new StandardFilter(new FilenameTokeniser(a_reader));
/*    */     }
/*    */ 
/* 82 */     if (this.m_bStem)
/*    */     {
/* 84 */       return new PorterStemFilter(new Bn2LowerCaseTokenizer(a_reader));
/*    */     }
/*    */ 
/* 87 */     return new StandardFilter(new Bn2LowerCaseTokenizer(a_reader));
/*    */   }
/*    */ 
/*    */   public void fieldIsUnTokenized(String a_sFieldName)
/*    */   {
/* 96 */     this.m_untokenizedFields.add(a_sFieldName);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.BrightAnalyzer
 * JD-Core Version:    0.6.0
 */