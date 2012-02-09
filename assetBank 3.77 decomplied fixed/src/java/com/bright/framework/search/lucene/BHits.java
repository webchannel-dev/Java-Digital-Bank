/*    */ package com.bright.framework.search.lucene;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.lucene.search.Hits;
/*    */ import org.apache.lucene.search.IndexSearcher;
/*    */ 
/*    */ public class BHits
/*    */ {
/* 34 */   private Hits m_hits = null;
/* 35 */   private IndexSearcher m_searcher = null;
/*    */ 
/*    */   public BHits(IndexSearcher a_searcher, Hits a_hits)
/*    */   {
/* 44 */     this.m_searcher = a_searcher;
/* 45 */     this.m_hits = a_hits;
/*    */   }
/*    */ 
/*    */   public void close()
/*    */     throws IOException
/*    */   {
/* 60 */     if (this.m_searcher != null)
/*    */     {
/* 62 */       this.m_searcher.close();
/*    */     }
/*    */   }
/*    */ 
/*    */   public Hits getHits()
/*    */   {
/* 69 */     return this.m_hits;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.BHits
 * JD-Core Version:    0.6.0
 */