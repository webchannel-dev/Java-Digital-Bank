/*    */ package com.bright.framework.search.lucene;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.lucene.document.Document;
/*    */ import org.apache.lucene.index.IndexReader;
/*    */ import org.apache.lucene.search.IndexSearcher;
/*    */ import org.apache.lucene.search.ScoreDoc;
/*    */ import org.apache.lucene.search.TopDocs;
/*    */ 
/*    */ public class BTopDocs
/*    */ {
/* 33 */   private IndexSearcher m_searcher = null;
/* 34 */   private TopDocs m_hits = null;
/*    */ 
/*    */   public BTopDocs(IndexSearcher a_searcher, TopDocs a_hits)
/*    */   {
/* 42 */     this.m_searcher = a_searcher;
/* 43 */     this.m_hits = a_hits;
/*    */ 
/* 45 */     this.m_searcher.getIndexReader().incRef();
/*    */   }
/*    */ 
/*    */   public void close() throws IOException
/*    */   {
/* 50 */     this.m_searcher.getIndexReader().decRef();
/*    */   }
/*    */ 
/*    */   public TopDocs getHits()
/*    */   {
/* 56 */     return this.m_hits;
/*    */   }
/*    */ 
/*    */   public Document getDocumentByHitIndex(int a_iHitIndex)
/*    */     throws IOException
/*    */   {
/* 67 */     return getDocumentByDocumentIndex(this.m_hits.scoreDocs[a_iHitIndex].doc);
/*    */   }
/*    */ 
/*    */   public Document getDocumentByDocumentIndex(int a_iDocIndex)
/*    */     throws IOException
/*    */   {
/* 78 */     return this.m_searcher.doc(a_iDocIndex);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.BTopDocs
 * JD-Core Version:    0.6.0
 */