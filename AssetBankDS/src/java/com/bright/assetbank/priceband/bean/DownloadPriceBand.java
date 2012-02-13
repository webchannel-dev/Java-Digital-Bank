/*    */ package com.bright.assetbank.priceband.bean;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class DownloadPriceBand extends PriceBand
/*    */ {
/* 30 */   private Vector m_usageTypes = null;
/* 31 */   private boolean m_bCanDownloadOriginal = false;
/* 32 */   private boolean m_bIsCommercial = false;
/*    */ 
/*    */   public boolean getCanDownloadOriginal()
/*    */   {
/* 44 */     return this.m_bCanDownloadOriginal;
/*    */   }
/*    */ 
/*    */   public void setCanDownloadOriginal(boolean a_sCanDownloadOriginal) {
/* 48 */     this.m_bCanDownloadOriginal = a_sCanDownloadOriginal;
/*    */   }
/*    */ 
/*    */   public Vector getUsageTypes() {
/* 52 */     return this.m_usageTypes;
/*    */   }
/*    */ 
/*    */   public void setUsageTypes(Vector a_sUsageTypes) {
/* 56 */     this.m_usageTypes = a_sUsageTypes;
/*    */   }
/*    */ 
/*    */   public boolean getIsCommercial()
/*    */   {
/* 61 */     return this.m_bIsCommercial;
/*    */   }
/*    */ 
/*    */   public void setIsCommercial(boolean a_sIsCommercial)
/*    */   {
/* 66 */     this.m_bIsCommercial = a_sIsCommercial;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.priceband.bean.DownloadPriceBand
 * JD-Core Version:    0.6.0
 */