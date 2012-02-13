/*    */ package com.bright.assetbank.usage.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class AssetAuditForm extends Bn2Form
/*    */ {
/* 31 */   private Vector m_vAssetAuditLog = null;
/* 32 */   private String m_sAssetTitle = null;
/*    */ 
/*    */   public Vector getAssetAuditLog()
/*    */   {
/* 36 */     return this.m_vAssetAuditLog;
/*    */   }
/*    */ 
/*    */   public void setAssetAuditLog(Vector a_sAssetAuditLog) {
/* 40 */     this.m_vAssetAuditLog = a_sAssetAuditLog;
/*    */   }
/*    */ 
/*    */   public String getAssetTitle()
/*    */   {
/* 45 */     return this.m_sAssetTitle;
/*    */   }
/*    */ 
/*    */   public void setAssetTitle(String a_sAssetTitle) {
/* 49 */     this.m_sAssetTitle = a_sAssetTitle;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.form.AssetAuditForm
 * JD-Core Version:    0.6.0
 */