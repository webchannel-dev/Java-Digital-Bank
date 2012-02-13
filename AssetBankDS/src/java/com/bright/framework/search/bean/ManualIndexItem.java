/*    */ package com.bright.framework.search.bean;
/*    */ 
/*    */ import com.bright.framework.queue.bean.QueuedItem;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class ManualIndexItem extends QueuedItem
/*    */ {
/* 33 */   private boolean m_bQuickIndex = false;
/* 34 */   private long m_lUserId = -1L;
/* 35 */   private Vector<Long> m_vecIdsToReindex = null;
/*    */ 
/*    */   public void setIsQuickIndex(boolean a_bQuickIndex)
/*    */   {
/* 39 */     this.m_bQuickIndex = a_bQuickIndex;
/*    */   }
/*    */ 
/*    */   public boolean getIsQuickIndex()
/*    */   {
/* 44 */     return this.m_bQuickIndex;
/*    */   }
/*    */ 
/*    */   public void setUserId(long a_lUserId)
/*    */   {
/* 49 */     this.m_lUserId = a_lUserId;
/*    */   }
/*    */ 
/*    */   public long getUserId()
/*    */   {
/* 54 */     return this.m_lUserId;
/*    */   }
/*    */ 
/*    */   public void setIdsToReindex(Vector<Long> a_vecIdsToReindex)
/*    */   {
/* 59 */     this.m_vecIdsToReindex = a_vecIdsToReindex;
/*    */   }
/*    */ 
/*    */   public Vector<Long> getIdsToReindex()
/*    */   {
/* 64 */     return this.m_vecIdsToReindex;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.bean.ManualIndexItem
 * JD-Core Version:    0.6.0
 */