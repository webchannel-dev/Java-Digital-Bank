/*    */ package com.bright.assetbank.usage.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.usage.bean.AssetUseLogEntry;
/*    */ import java.util.List;
/*    */ 
/*    */ public class AssetUseForm extends Bn2Form
/*    */ {
/* 33 */   private List<AssetUseLogEntry> m_assetUsageLog = null;
/* 34 */   private String m_sAssetTitle = null;
/*    */ 
/*    */   public List<AssetUseLogEntry> getAssetUsageLog()
/*    */   {
/* 39 */     return this.m_assetUsageLog;
/*    */   }
/*    */ 
/*    */   public void setAssetUsageLog(List<AssetUseLogEntry> a_sAssetUsageLog)
/*    */   {
/* 45 */     this.m_assetUsageLog = a_sAssetUsageLog;
/*    */   }
/*    */ 
/*    */   public String getAssetTitle()
/*    */   {
/* 51 */     return this.m_sAssetTitle;
/*    */   }
/*    */ 
/*    */   public void setAssetTitle(String a_sAssetTitle)
/*    */   {
/* 57 */     this.m_sAssetTitle = a_sAssetTitle;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.form.AssetUseForm
 * JD-Core Version:    0.6.0
 */