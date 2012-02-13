/*    */ package com.bright.assetbank.publishing.bean;
/*    */ 
/*    */ import com.bright.framework.queue.bean.QueuedItem;
/*    */ import java.util.concurrent.atomic.AtomicLong;
/*    */ 
/*    */ public class PublishingActionRequest extends QueuedItem
/*    */ {
/* 11 */   static AtomicLong m_requestIdCounter = new AtomicLong(0L);
/*    */   Long m_requestId;
/*    */   PublishingAction m_publishingAction;
/*    */ 
/*    */   public PublishingActionRequest(PublishingAction a_publishingAction)
/*    */   {
/* 18 */     this.m_requestId = Long.valueOf(m_requestIdCounter.getAndIncrement());
/* 19 */     this.m_publishingAction = a_publishingAction;
/*    */   }
/*    */ 
/*    */   public Long getRequestId() {
/* 23 */     return this.m_requestId;
/*    */   }
/*    */ 
/*    */   public PublishingAction getPublishingAction() {
/* 27 */     return this.m_publishingAction;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.publishing.bean.PublishingActionRequest
 * JD-Core Version:    0.6.0
 */