/*    */ package com.bright.assetbank.entity.relationship.bean;
/*    */ 
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.bean.NameValueBean;
/*    */ import com.bright.framework.queue.bean.QueuedItem;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class EmptyRelatedAssetBatch extends QueuedItem
/*    */ {
/* 34 */   private Asset m_asset = null;
/* 35 */   private HashMap<Long, NameValueBean> m_hmRelatedAssets = null;
/* 36 */   private int m_iRelType = -1;
/* 37 */   private ABUserProfile m_userProfile = null;
/*    */ 
/*    */   public Asset getAsset()
/*    */   {
/* 41 */     return this.m_asset;
/*    */   }
/*    */ 
/*    */   public void setAsset(Asset a_asset)
/*    */   {
/* 46 */     this.m_asset = a_asset;
/*    */   }
/*    */ 
/*    */   public HashMap<Long, NameValueBean> getRelatedAssets()
/*    */   {
/* 51 */     return this.m_hmRelatedAssets;
/*    */   }
/*    */ 
/*    */   public void setRelatedAssets(HashMap<Long, NameValueBean> a_hmRelatedAssets)
/*    */   {
/* 56 */     this.m_hmRelatedAssets = a_hmRelatedAssets;
/*    */   }
/*    */ 
/*    */   public int getRelType()
/*    */   {
/* 61 */     return this.m_iRelType;
/*    */   }
/*    */ 
/*    */   public void setRelType(int a_iRelType)
/*    */   {
/* 66 */     this.m_iRelType = a_iRelType;
/*    */   }
/*    */ 
/*    */   public ABUserProfile getUserProfile()
/*    */   {
/* 71 */     return this.m_userProfile;
/*    */   }
/*    */ 
/*    */   public void setUserProfile(ABUserProfile a_userProfile)
/*    */   {
/* 76 */     this.m_userProfile = a_userProfile;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.bean.EmptyRelatedAssetBatch
 * JD-Core Version:    0.6.0
 */