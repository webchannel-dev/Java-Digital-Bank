/*    */ package com.bright.assetbank.usage.bean;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class TotalsReport
/*    */ {
/* 29 */   private Vector m_assetLines = null;
/* 30 */   private long m_totalTimes = 0L;
/* 31 */   private long m_totalAssets = 0L;
/*    */ 
/*    */   public Vector getAssetLines()
/*    */   {
/* 36 */     return this.m_assetLines;
/*    */   }
/*    */ 
/*    */   public void setAssetLines(Vector a_sAssetLines) {
/* 40 */     this.m_assetLines = a_sAssetLines;
/*    */   }
/*    */ 
/*    */   public long getTotalAssets() {
/* 44 */     return this.m_totalAssets;
/*    */   }
/*    */ 
/*    */   public void setTotalAssets(long a_sTotalAssetsViewed) {
/* 48 */     this.m_totalAssets = a_sTotalAssetsViewed;
/*    */   }
/*    */ 
/*    */   public long getTotalTimes() {
/* 52 */     return this.m_totalTimes;
/*    */   }
/*    */ 
/*    */   public void setTotalTimes(long a_sTotalViews) {
/* 56 */     this.m_totalTimes = a_sTotalViews;
/*    */   }
/*    */ 
/*    */   public Vector getAssetIds()
/*    */   {
/* 65 */     Vector ids = null;
/*    */ 
/* 67 */     if (this.m_assetLines != null)
/*    */     {
/* 69 */       ids = new Vector(this.m_assetLines.size());
/*    */ 
/* 71 */       for (int i = 0; i < this.m_assetLines.size(); i++)
/*    */       {
/* 73 */         ids.add(Long.valueOf(((ReportEntity)this.m_assetLines.get(i)).getIdForAsset()));
/*    */       }
/*    */     }
/*    */ 
/* 77 */     return ids;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.TotalsReport
 * JD-Core Version:    0.6.0
 */