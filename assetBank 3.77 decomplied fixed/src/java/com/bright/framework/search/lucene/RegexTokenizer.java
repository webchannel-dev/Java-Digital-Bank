/*    */ package com.bright.framework.search.lucene;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
/*    */ import java.util.LinkedList;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ 
/*    */ public class RegexTokenizer extends OneShotTermAttributeTokenizer
/*    */ {
/*    */   private Pattern m_pattern;
/*    */ 
/*    */   public RegexTokenizer(Reader a_reader, Pattern a_pattern)
/*    */   {
/* 45 */     super(a_reader);
/* 46 */     this.m_pattern = a_pattern;
/*    */   }
/*    */ 
/*    */   protected LinkedList<String> readTokens()
/*    */     throws IOException
/*    */   {
/* 52 */     String sInput = IOUtils.toString(this.input);
/*    */ 
/* 54 */     LinkedList tokens = new LinkedList();
/*    */ 
/* 56 */     Matcher matcher = this.m_pattern.matcher(sInput);
/* 57 */     int start = 0;
/*    */     while (true)
/*    */     {
/* 60 */       if (!matcher.find())
/*    */       {
/* 62 */         int end = sInput.length();
/* 63 */         addToken(tokens, sInput, start, end);
/* 64 */         break;
/*    */       }
/*    */ 
/* 68 */       int end = matcher.start();
/* 69 */       addToken(tokens, sInput, start, end);
/*    */ 
/* 72 */       start = matcher.end();
/*    */     }
/*    */ 
/* 75 */     return tokens;
/*    */   }
/*    */ 
/*    */   private static void addToken(LinkedList<String> a_tokens, String a_sInput, int a_start, int a_end)
/*    */   {
/* 80 */     if (a_start < a_end)
/*    */     {
/* 82 */       String sText = a_sInput.substring(a_start, a_end);
/* 83 */       String sTrimmedText = sText.trim();
/* 84 */       a_tokens.add(sTrimmedText);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.RegexTokenizer
 * JD-Core Version:    0.6.0
 */