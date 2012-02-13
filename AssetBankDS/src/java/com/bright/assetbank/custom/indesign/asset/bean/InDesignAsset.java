/*    */ package com.bright.assetbank.custom.indesign.asset.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class InDesignAsset
/*    */   implements Serializable
/*    */ {
/*    */   private boolean m_isTemplate;
/*    */   private long m_templateAssetId;
/*    */   private long m_inDesignDocumentId;
/*    */   private long m_pdfStatusId;
/*    */ 
/*    */   public boolean getIsTemplate()
/*    */   {
/* 34 */     return this.m_isTemplate;
/*    */   }
/*    */ 
/*    */   public void setIsTemplate(boolean a_template)
/*    */   {
/* 39 */     this.m_isTemplate = a_template;
/*    */   }
/*    */ 
/*    */   public long getTemplateAssetId()
/*    */   {
/* 44 */     return this.m_templateAssetId;
/*    */   }
/*    */ 
/*    */   public void setTemplateAssetId(long a_templateAssetId)
/*    */   {
/* 49 */     this.m_templateAssetId = a_templateAssetId;
/*    */   }
/*    */ 
/*    */   public long getInDesignDocumentId()
/*    */   {
/* 54 */     return this.m_inDesignDocumentId;
/*    */   }
/*    */ 
/*    */   public void setInDesignDocumentId(long a_inDesignDocumentId)
/*    */   {
/* 59 */     this.m_inDesignDocumentId = a_inDesignDocumentId;
/*    */   }
/*    */ 
/*    */   public long getPDFStatusId()
/*    */   {
/* 64 */     return this.m_pdfStatusId;
/*    */   }
/*    */ 
/*    */   public void setPDFStatusId(long a_pdfStatusId)
/*    */   {
/* 69 */     this.m_pdfStatusId = a_pdfStatusId;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.asset.bean.InDesignAsset
 * JD-Core Version:    0.6.0
 */