/*    */ package com.bright.framework.search.util;
/*    */ 
/*    */ import org.apache.lucene.queryParser.QueryParser;
/*    */ 
/*    */ public class LuceneUtil
/*    */ {
/*    */   public static String escape(String a_s)
/*    */   {
/* 32 */     return QueryParser.escape(a_s).replace(" ", "\\ ");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.util.LuceneUtil
 * JD-Core Version:    0.6.0
 */