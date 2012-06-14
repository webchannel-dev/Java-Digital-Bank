/*    */ package com.bright.assetbank.usage.service;
/*    */ 
/*    */ import java.util.Set;
/*    */ 
/*    */ class LogAssetUseQueueItem extends LogAssetViewQueueItem
/*    */ {
/*    */   private long m_lUsageTypeId;
/*    */   private String m_sOtherDescription;
/*    */   private long m_lDownloadTypeId;
/* 32 */   private Set<Long> m_secondaryUsageTypeIds = null;
/*    */ 
/*    */   public LogAssetUseQueueItem(long a_assetId, long a_userId, long a_usageTypeId, String a_otherDescription, long a_downloadTypeId, long a_loginSessionId, Set<Long> a_secondaryUsageTypeIds)
/*    */   {
/* 42 */     super(a_assetId, a_userId, a_loginSessionId);
/* 43 */     this.m_lUsageTypeId = a_usageTypeId;
/* 44 */     this.m_sOtherDescription = a_otherDescription;
/* 45 */     this.m_lDownloadTypeId = a_downloadTypeId;
/* 46 */     this.m_secondaryUsageTypeIds = a_secondaryUsageTypeIds;
/*    */   }
/*    */ 
/*    */   public long getDownloadTypeId()
/*    */   {
/* 51 */     return this.m_lDownloadTypeId;
/*    */   }
/*    */ 
/*    */   public long getUsageTypeId()
/*    */   {
/* 56 */     return this.m_lUsageTypeId;
/*    */   }
/*    */ 
/*    */   public String getOtherDescription()
/*    */   {
/* 61 */     return this.m_sOtherDescription;
/*    */   }
/*    */ 
/*    */   public Set<Long> getSecondaryUsageTypeIds()
/*    */   {
/* 66 */     return this.m_secondaryUsageTypeIds;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.service.LogAssetUseQueueItem
 * JD-Core Version:    0.6.0
 */