/*    */ package com.bright.framework.search.bean;
/*    */ 
/*    */ import com.bright.framework.constant.FrameworkSettings;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class FilterArrayIterator
/*    */ {
/* 33 */   private Collection<Long> m_filterArray = null;
/*    */ 
/* 36 */   Iterator<Long> m_iterator = null;
/*    */ 
/*    */   public FilterArrayIterator(Collection<Long> a_array)
/*    */   {
/* 40 */     this.m_filterArray = a_array;
/* 41 */     this.m_iterator = this.m_filterArray.iterator();
/*    */   }
/*    */ 
/*    */   public boolean getHasMoreBatches()
/*    */   {
/* 52 */     return this.m_iterator.hasNext();
/*    */   }
/*    */ 
/*    */   public String getNextBatchQuery()
/*    */   {
/* 62 */     int iBatchSize = FrameworkSettings.getSearchFilterBatchSize();
/*    */ 
/* 65 */     StringBuffer sbIds = new StringBuffer(iBatchSize * 2);
/*    */ 
/* 68 */     int iBatchCounter = 0;
/* 69 */     while ((iBatchCounter < iBatchSize) && (this.m_iterator.hasNext()))
/*    */     {
/* 71 */       if (iBatchCounter > 0)
/*    */       {
/* 73 */         sbIds = sbIds.append(" OR ");
/*    */       }
/*    */ 
/* 76 */       String sId = Long.toString(((Long)this.m_iterator.next()).longValue());
/* 77 */       sbIds = sbIds.append("f_id").append(":(").append(sId).append(")");
/*    */ 
/* 79 */       iBatchCounter++;
/*    */     }
/*    */ 
/* 82 */     return sbIds.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.bean.FilterArrayIterator
 * JD-Core Version:    0.6.0
 */