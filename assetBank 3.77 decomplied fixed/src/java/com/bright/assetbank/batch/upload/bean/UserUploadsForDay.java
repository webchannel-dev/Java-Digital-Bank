/*    */ package com.bright.assetbank.batch.upload.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.BrightDate;
/*    */ 
/*    */ public class UserUploadsForDay
/*    */ {
/*    */   private long m_lUserId;
/*    */   private BrightDate m_bdtDate;
/*    */   private int m_iCount;
/*    */ 
/*    */   public long getUserId()
/*    */   {
/* 18 */     return this.m_lUserId;
/*    */   }
/*    */   public void setUserId(long userId) {
/* 21 */     this.m_lUserId = userId;
/*    */   }
/*    */   public BrightDate getDate() {
/* 24 */     return this.m_bdtDate;
/*    */   }
/*    */   public void setDate(BrightDate a_bdtDate) {
/* 27 */     this.m_bdtDate = a_bdtDate;
/*    */   }
/*    */ 
/*    */   public int getCount() {
/* 31 */     return this.m_iCount;
/*    */   }
/*    */   public void setCount(int count) {
/* 34 */     this.m_iCount = count;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.upload.bean.UserUploadsForDay
 * JD-Core Version:    0.6.0
 */