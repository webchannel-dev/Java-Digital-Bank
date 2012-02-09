/*    */ package com.bright.framework.search.lucene;
/*    */ 
/*    */ import java.io.Reader;
/*    */ import org.apache.lucene.analysis.CharTokenizer;
/*    */ 
/*    */ public class FilenameTokeniser extends CharTokenizer
/*    */ {
/*    */   public FilenameTokeniser(Reader input)
/*    */   {
/* 25 */     super(input);
/*    */   }
/*    */ 
/*    */   protected boolean isTokenChar(char a_char)
/*    */   {
/* 31 */     return a_char != '.';
/*    */   }
/*    */ 
/*    */   protected char normalize(char c)
/*    */   {
/* 41 */     return Character.toLowerCase(c);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.FilenameTokeniser
 * JD-Core Version:    0.6.0
 */