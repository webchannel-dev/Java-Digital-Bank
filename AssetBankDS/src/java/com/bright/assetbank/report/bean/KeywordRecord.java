/*    */ package com.bright.assetbank.report.bean;
/*    */ 
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.framework.common.bean.StringDataBean;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class KeywordRecord extends StringDataBean
/*    */ {
/* 32 */   private Vector<Asset> m_assets = null;
/*    */ 
/*    */   public KeywordRecord()
/*    */   {
/* 36 */     this.m_assets = new Vector();
/*    */   }
/*    */ 
/*    */   public Vector<Asset> getAssets()
/*    */   {
/* 41 */     return this.m_assets;
/*    */   }
/*    */ 
/*    */   public void setAssets(Vector<Asset> a_sAssets) {
/* 45 */     this.m_assets = a_sAssets;
/*    */   }
/*    */ 
/*    */   public long getNumberOfAssets()
/*    */   {
/* 50 */     return this.m_assets.size();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.report.bean.KeywordRecord
 * JD-Core Version:    0.6.0
 */