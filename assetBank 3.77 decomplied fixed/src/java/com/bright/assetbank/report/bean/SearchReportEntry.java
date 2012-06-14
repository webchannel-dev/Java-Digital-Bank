/*    */ package com.bright.assetbank.report.bean;
/*    */ 
/*    */ import com.bright.framework.queue.bean.QueuedItem;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class SearchReportEntry extends QueuedItem
/*    */ {
/* 31 */   private String m_sSearchTerm = null;
/* 32 */   private String m_sLuceneQuery = null;
/* 33 */   private int m_iResultCount = 0;
/* 34 */   private boolean m_bSuccessful = false;
/* 35 */   private Date m_dtDate = null;
/*    */ 
/*    */   public void setSearchTerm(String a_sSearchTerm)
/*    */   {
/* 39 */     this.m_sSearchTerm = a_sSearchTerm;
/*    */   }
/*    */ 
/*    */   public String getSearchTerm()
/*    */   {
/* 44 */     return this.m_sSearchTerm;
/*    */   }
/*    */ 
/*    */   public void setLuceneQuery(String a_sLuceneQuery)
/*    */   {
/* 49 */     this.m_sLuceneQuery = a_sLuceneQuery;
/*    */   }
/*    */ 
/*    */   public String getLuceneQuery()
/*    */   {
/* 54 */     return this.m_sLuceneQuery;
/*    */   }
/*    */ 
/*    */   public void setSuccessful(boolean a_bSuccessful)
/*    */   {
/* 59 */     this.m_bSuccessful = a_bSuccessful;
/*    */   }
/*    */ 
/*    */   public boolean getSuccessful()
/*    */   {
/* 64 */     return this.m_bSuccessful;
/*    */   }
/*    */ 
/*    */   public void setDate(Date a_dtDate)
/*    */   {
/* 69 */     this.m_dtDate = a_dtDate;
/*    */   }
/*    */ 
/*    */   public Date getDate()
/*    */   {
/* 74 */     return this.m_dtDate;
/*    */   }
/*    */ 
/*    */   public void setResultCount(int a_iResultCount)
/*    */   {
/* 79 */     this.m_iResultCount = a_iResultCount;
/*    */   }
/*    */ 
/*    */   public int getResultCount()
/*    */   {
/* 84 */     return this.m_iResultCount;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.report.bean.SearchReportEntry
 * JD-Core Version:    0.6.0
 */