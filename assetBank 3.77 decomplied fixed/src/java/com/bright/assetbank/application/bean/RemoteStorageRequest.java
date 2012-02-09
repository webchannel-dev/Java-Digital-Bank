/*    */ package com.bright.assetbank.application.bean;
/*    */ 
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.queue.bean.QueuedItem;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.atomic.AtomicLong;
/*    */ 
/*    */ public class RemoteStorageRequest extends QueuedItem
/*    */ {
/* 12 */   static AtomicLong m_requestIdCounter = new AtomicLong(0L);
/*    */   Long m_requestId;
/*    */   String m_filepath;
/*    */   ABUserProfile m_userProfile;
/*    */   Map<String, String> m_emailParams;
/*    */ 
/*    */   public RemoteStorageRequest(String filepath, ABUserProfile userProfile, Map<String, String> emailParams)
/*    */   {
/* 21 */     this.m_requestId = Long.valueOf(m_requestIdCounter.getAndIncrement());
/* 22 */     this.m_filepath = filepath;
/* 23 */     this.m_userProfile = userProfile;
/* 24 */     this.m_emailParams = emailParams;
/*    */   }
/*    */ 
/*    */   public String getFilepath() {
/* 28 */     return this.m_filepath;
/*    */   }
/*    */ 
/*    */   public Long getRequestId() {
/* 32 */     return this.m_requestId;
/*    */   }
/*    */ 
/*    */   public ABUserProfile getUserProfile() {
/* 36 */     return this.m_userProfile;
/*    */   }
/*    */ 
/*    */   public Map<String, String> getEmailParams() {
/* 40 */     return this.m_emailParams;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.RemoteStorageRequest
 * JD-Core Version:    0.6.0
 */