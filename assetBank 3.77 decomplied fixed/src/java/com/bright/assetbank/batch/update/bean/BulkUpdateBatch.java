/*    */ package com.bright.assetbank.batch.update.bean;
/*    */ 
/*    */ import com.bright.assetbank.application.bean.LightweightAsset;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class BulkUpdateBatch extends Batch
/*    */ {
/*    */   public static final String k_sTypeBulkUpdate = "BULKUPDATE";
/* 35 */   private HashMap m_hmAssetsToUpdate = null;
/* 36 */   private HashMap m_hmAssetsAlreadyUpdated = null;
/*    */ 
/*    */   public BulkUpdateBatch()
/*    */   {
/* 40 */     this.m_hmAssetsAlreadyUpdated = new HashMap();
/* 41 */     this.m_hmAssetsToUpdate = new HashMap();
/*    */   }
/*    */ 
/*    */   public HashMap getAssetsAlreadyUpdated()
/*    */   {
/* 47 */     return this.m_hmAssetsAlreadyUpdated;
/*    */   }
/*    */ 
/*    */   public HashMap getAssetsToUpdate() {
/* 51 */     return this.m_hmAssetsToUpdate;
/*    */   }
/*    */ 
/*    */   public void clearAssetsToUpdate()
/*    */   {
/* 57 */     this.m_hmAssetsToUpdate.clear();
/*    */   }
/*    */ 
/*    */   public void addAssetToUpdate(long a_lId)
/*    */   {
/* 62 */     Long olId = new Long(a_lId);
/* 63 */     this.m_hmAssetsToUpdate.put(olId, olId);
/*    */   }
/*    */ 
/*    */   public boolean getAssetIsToUpdate(long a_lId)
/*    */   {
/* 68 */     Long olId = new Long(a_lId);
/* 69 */     boolean bIsToUpdate = this.m_hmAssetsToUpdate.containsKey(olId);
/* 70 */     return bIsToUpdate;
/*    */   }
/*    */ 
/*    */   public boolean getAssetIsAlreadyUpdated(long a_lId)
/*    */   {
/* 75 */     Long olId = new Long(a_lId);
/* 76 */     boolean bIsUpdated = this.m_hmAssetsAlreadyUpdated.containsKey(olId);
/* 77 */     return bIsUpdated;
/*    */   }
/*    */ 
/*    */   public void addAssetUpdated(long a_lId)
/*    */   {
/* 82 */     Long olId = new Long(a_lId);
/* 83 */     this.m_hmAssetsAlreadyUpdated.put(olId, olId);
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 90 */     return "BULKUPDATE";
/*    */   }
/*    */ 
/*    */   public long getIdFromBatchObject(Object a_obj)
/*    */   {
/* 97 */     LightweightAsset asset = (LightweightAsset)a_obj;
/* 98 */     long l = asset.getId();
/* 99 */     return l;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.bean.BulkUpdateBatch
 * JD-Core Version:    0.6.0
 */