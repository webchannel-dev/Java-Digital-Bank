/*    */ package com.bright.framework.search.lucene;
/*    */ 
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import java.io.IOException;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.lucene.index.IndexReader;
/*    */ import org.apache.lucene.index.Term;
/*    */ import org.apache.lucene.search.DocIdSet;
/*    */ import org.apache.lucene.search.TermsFilter;
/*    */ 
/*    */ public class LoggingTermsFilter extends TermsFilter
/*    */ {
/* 34 */   private Log m_logger = GlobalApplication.getInstance().getLogger();
/*    */ 
/* 36 */   private int m_iNumTerms = 0;
/*    */ 
/*    */   public DocIdSet getDocIdSet(IndexReader reader)
/*    */     throws IOException
/*    */   {
/* 41 */     long lTime = System.currentTimeMillis();
/*    */ 
/* 43 */     DocIdSet docSet = super.getDocIdSet(reader);
/*    */ 
/* 45 */     this.m_logger.trace(getClass().getSimpleName() + " : Time in TermsFilter (" + this.m_iNumTerms + " terms) for segment = " + (System.currentTimeMillis() - lTime) + "ms");
/*    */ 
/* 47 */     return docSet;
/*    */   }
/*    */ 
/*    */   public void addTerm(Term term)
/*    */   {
/* 53 */     super.addTerm(term);
/*    */ 
/* 55 */     this.m_iNumTerms += 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.LoggingTermsFilter
 * JD-Core Version:    0.6.0
 */