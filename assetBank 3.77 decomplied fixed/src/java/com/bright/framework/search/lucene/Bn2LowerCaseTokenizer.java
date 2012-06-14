/*    */ package com.bright.framework.search.lucene;
/*    */ 
/*    */ import java.io.Reader;
/*    */ import org.apache.lucene.analysis.LetterTokenizer;
/*    */ 
/*    */ public class Bn2LowerCaseTokenizer extends LetterTokenizer
/*    */ {
/*    */   public Bn2LowerCaseTokenizer(Reader a_reader)
/*    */   {
/* 42 */     super(a_reader);
/*    */   }
/*    */ 
/*    */   protected boolean isTokenChar(char c)
/*    */   {
/* 57 */     return Character.isLetterOrDigit(c);
/*    */   }
/*    */ 
/*    */   protected char normalize(char c)
/*    */   {
/* 72 */     return Character.toLowerCase(c);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.Bn2LowerCaseTokenizer
 * JD-Core Version:    0.6.0
 */