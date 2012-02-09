/*    */ package com.bright.assetbank.assetbox.bean;
/*    */ 
/*    */ import com.bright.framework.database.bean.DataBean;
/*    */ 
/*    */ public class AssetBoxSummary extends DataBean
/*    */ {
/* 22 */   private String m_sName = null;
/* 23 */   private boolean m_bShared = false;
/* 24 */   private int m_iNumShares = 0;
/* 25 */   private boolean m_bEditable = false;
/* 26 */   private long m_lAssetBoxSize = 0L;
/*    */ 
/*    */   public boolean isShared()
/*    */   {
/* 30 */     return this.m_bShared;
/*    */   }
/*    */ 
/*    */   public void setShared(boolean shared)
/*    */   {
/* 35 */     this.m_bShared = shared;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 40 */     return this.m_sName;
/*    */   }
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 45 */     this.m_sName = name;
/*    */   }
/*    */ 
/*    */   public boolean isEditable()
/*    */   {
/* 50 */     return this.m_bEditable;
/*    */   }
/*    */ 
/*    */   public void setEditable(boolean editable)
/*    */   {
/* 55 */     this.m_bEditable = editable;
/*    */   }
/*    */ 
/*    */   public int getNumShares()
/*    */   {
/* 60 */     return this.m_iNumShares;
/*    */   }
/*    */ 
/*    */   public void setNumShares(int numShares)
/*    */   {
/* 65 */     this.m_iNumShares = numShares;
/*    */   }
/*    */ 
/*    */   public long getAssetBoxSize()
/*    */   {
/* 70 */     return this.m_lAssetBoxSize;
/*    */   }
/*    */ 
/*    */   public void setAssetBoxSize(long a_lAssetBoxSize) {
/* 74 */     this.m_lAssetBoxSize = a_lAssetBoxSize;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.bean.AssetBoxSummary
 * JD-Core Version:    0.6.0
 */