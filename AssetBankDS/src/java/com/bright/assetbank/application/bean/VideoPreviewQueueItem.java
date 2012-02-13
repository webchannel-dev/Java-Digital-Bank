/*    */ package com.bright.assetbank.application.bean;
/*    */ 
/*    */ import com.bright.framework.queue.bean.QueuedItem;
/*    */ 
/*    */ public class VideoPreviewQueueItem extends QueuedItem
/*    */ {
/*    */   private VideoAsset m_videoAsset;
/*    */   private VideoInfo m_videoInfo;
/*    */   private String m_sourcePath;
/*    */   private int m_startPreviewAtFrame;
/*    */ 
/*    */   public VideoPreviewQueueItem(VideoAsset a_videoAsset, VideoInfo a_videoInfo, String a_sourcePath, int a_startPreviewAtFrame)
/*    */   {
/* 13 */     this.m_videoAsset = a_videoAsset;
/* 14 */     this.m_videoInfo = a_videoInfo;
/* 15 */     this.m_sourcePath = a_sourcePath;
/* 16 */     this.m_startPreviewAtFrame = a_startPreviewAtFrame;
/*    */   }
/*    */ 
/*    */   public VideoAsset getVideoAsset() {
/* 20 */     return this.m_videoAsset;
/*    */   }
/*    */ 
/*    */   public VideoInfo getVideoInfo() {
/* 24 */     return this.m_videoInfo;
/*    */   }
/*    */ 
/*    */   public String getSourcePath() {
/* 28 */     return this.m_sourcePath;
/*    */   }
/*    */ 
/*    */   public int getStartPreviewAtFrame() {
/* 32 */     return this.m_startPreviewAtFrame;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.VideoPreviewQueueItem
 * JD-Core Version:    0.6.0
 */