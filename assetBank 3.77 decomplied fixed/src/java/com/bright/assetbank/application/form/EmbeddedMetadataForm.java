/*    */ package com.bright.assetbank.application.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class EmbeddedMetadataForm extends Bn2Form
/*    */ {
/* 31 */   private String m_sAssetUrl = null;
/* 32 */   private long m_lAssetId = 0L;
/* 33 */   private Vector m_vecMetadata = null;
/*    */ 
/*    */   public void setAssetUrl(String a_sAssetUrl)
/*    */   {
/* 37 */     this.m_sAssetUrl = a_sAssetUrl;
/*    */   }
/*    */ 
/*    */   public String getAssetUrl()
/*    */   {
/* 42 */     return this.m_sAssetUrl;
/*    */   }
/*    */ 
/*    */   public void setAssetId(long a_lAssetId)
/*    */   {
/* 47 */     this.m_lAssetId = a_lAssetId;
/*    */   }
/*    */ 
/*    */   public long getAssetId()
/*    */   {
/* 52 */     return this.m_lAssetId;
/*    */   }
/*    */ 
/*    */   public void setMetadata(Vector a_vecMetadata)
/*    */   {
/* 57 */     this.m_vecMetadata = a_vecMetadata;
/*    */   }
/*    */ 
/*    */   public Vector getMetadata()
/*    */   {
/* 62 */     return this.m_vecMetadata;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.form.EmbeddedMetadataForm
 * JD-Core Version:    0.6.0
 */