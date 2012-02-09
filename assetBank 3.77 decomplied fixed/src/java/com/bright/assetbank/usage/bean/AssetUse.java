/*    */ package com.bright.assetbank.usage.bean;
/*    */ 
/*    */ public class AssetUse
/*    */ {
/* 27 */   private long m_lUsageTypeId = 0L;
/* 28 */   private String m_sUsageOther = null;
/* 29 */   private boolean m_bEditable = true;
/*    */ 
/*    */   public String getUsageOther()
/*    */   {
/* 34 */     return this.m_sUsageOther;
/*    */   }
/*    */ 
/*    */   public void setUsageOther(String a_sUsageTypeOther)
/*    */   {
/* 40 */     this.m_sUsageOther = a_sUsageTypeOther;
/*    */   }
/*    */ 
/*    */   public long getUsageTypeId()
/*    */   {
/* 46 */     return this.m_lUsageTypeId;
/*    */   }
/*    */ 
/*    */   public void setUsageTypeId(long a_sAssetUseId)
/*    */   {
/* 52 */     this.m_lUsageTypeId = a_sAssetUseId;
/*    */   }
/*    */ 
/*    */   public boolean getEditable()
/*    */   {
/* 57 */     return this.m_bEditable;
/*    */   }
/*    */ 
/*    */   public void setEditable(boolean a_bEditable) {
/* 61 */     this.m_bEditable = a_bEditable;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.AssetUse
 * JD-Core Version:    0.6.0
 */