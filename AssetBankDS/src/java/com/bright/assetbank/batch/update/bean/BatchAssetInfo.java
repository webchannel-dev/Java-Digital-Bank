/*    */ package com.bright.assetbank.batch.update.bean;
/*    */ 
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.user.bean.ABUser;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.queue.bean.QueuedItem;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public abstract class BatchAssetInfo extends QueuedItem
/*    */ {
/* 36 */   private ABUser m_user = null;
/* 37 */   private ABUserProfile m_userProfile = null;
/* 38 */   private Asset m_assetMetadata = null;
/* 39 */   private Vector m_vecAssetAttributes = null;
/* 40 */   private boolean m_bDeferThumbnailGeneration = false;
/*    */ 
/*    */   public Asset getAssetMetadata()
/*    */   {
/* 44 */     return this.m_assetMetadata;
/*    */   }
/*    */ 
/*    */   public void setAssetMetadata(Asset a_assetMetadata) {
/* 48 */     this.m_assetMetadata = a_assetMetadata;
/*    */   }
/*    */ 
/*    */   public ABUser getUser()
/*    */   {
/* 53 */     return this.m_user;
/*    */   }
/*    */ 
/*    */   public void setUser(ABUser a_user) {
/* 57 */     this.m_user = a_user;
/*    */   }
/*    */ 
/*    */   public ABUserProfile getUserProfile()
/*    */   {
/* 62 */     return this.m_userProfile;
/*    */   }
/*    */ 
/*    */   public void setUserProfile(ABUserProfile a_userProfile) {
/* 66 */     this.m_userProfile = a_userProfile;
/*    */   }
/*    */ 
/*    */   public Vector getAssetAttributes()
/*    */   {
/* 71 */     return this.m_vecAssetAttributes;
/*    */   }
/*    */ 
/*    */   public void setAssetAttributes(Vector a_vecAssetAttributes) {
/* 75 */     this.m_vecAssetAttributes = a_vecAssetAttributes;
/*    */   }
/*    */ 
/*    */   public boolean getDeferThumbnailGeneration()
/*    */   {
/* 80 */     return this.m_bDeferThumbnailGeneration;
/*    */   }
/*    */ 
/*    */   public void setDeferThumbnailGeneration(boolean a_bDeferThumbnailGeneration)
/*    */   {
/* 85 */     this.m_bDeferThumbnailGeneration = a_bDeferThumbnailGeneration;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.bean.BatchAssetInfo
 * JD-Core Version:    0.6.0
 */