/*    */ package com.bright.framework.search.lucene;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
/*    */ import java.util.LinkedList;
/*    */ import org.apache.lucene.analysis.Tokenizer;
/*    */ import org.apache.lucene.analysis.tokenattributes.TermAttribute;
/*    */ 
/*    */ public abstract class OneShotTermAttributeTokenizer extends Tokenizer
/*    */ {
/* 37 */   private LinkedList<String> m_tokens = null;
/*    */   private TermAttribute m_termAtt;
/*    */ 
/*    */   public OneShotTermAttributeTokenizer(Reader a_input)
/*    */   {
/* 42 */     super(a_input);
/*    */ 
/* 44 */     this.m_termAtt = ((TermAttribute)addAttribute(TermAttribute.class));
/*    */   }
/*    */ 
/*    */   public final boolean incrementToken()
/*    */     throws IOException
/*    */   {
/* 55 */     clearAttributes();
/*    */ 
/* 57 */     String sToken = next();
/*    */ 
/* 59 */     if (sToken != null)
/*    */     {
/* 61 */       this.m_termAtt.setTermBuffer(sToken);
/* 62 */       return true;
/*    */     }
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */   final String next()
/*    */     throws IOException
/*    */   {
/* 74 */     if (this.m_tokens == null)
/*    */     {
/* 76 */       this.m_tokens = readTokens();
/*    */     }
/*    */ 
/* 79 */     if (this.m_tokens.isEmpty())
/*    */     {
/* 81 */       return null;
/*    */     }
/*    */ 
/* 84 */     return (String)this.m_tokens.removeFirst();
/*    */   }
/*    */ 
/*    */   protected abstract LinkedList<String> readTokens()
/*    */     throws IOException;
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.OneShotTermAttributeTokenizer
 * JD-Core Version:    0.6.0
 */