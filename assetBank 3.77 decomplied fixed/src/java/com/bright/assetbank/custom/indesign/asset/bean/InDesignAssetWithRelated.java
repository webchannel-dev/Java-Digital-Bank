/*     */ package com.bright.assetbank.custom.indesign.asset.bean;
/*     */ 
/*     */ import com.bright.assetbank.custom.indesign.entity.bean.InDesignAssetEntity;
/*     */ 
/*     */ public class InDesignAssetWithRelated extends InDesignAsset
/*     */ {
/*     */   private InDesignAssetEntity m_entity;
/*     */   private InDesignDocument m_document;
/*     */   private String m_pdfStatus;
/*     */ 
/*     */   public InDesignAssetWithRelated(InDesignAssetEntity a_entity, InDesignDocument a_document, String a_pdfStatus)
/*     */   {
/*  43 */     this.m_entity = a_entity;
/*  44 */     this.m_document = a_document;
/*  45 */     this.m_pdfStatus = a_pdfStatus;
/*     */   }
/*     */ 
/*     */   public boolean isDocument()
/*     */   {
/*  60 */     return getEntity().isDocument();
/*     */   }
/*     */ 
/*     */   public boolean isSymbol()
/*     */   {
/*  65 */     return getEntity().isSymbol();
/*     */   }
/*     */ 
/*     */   public boolean isImage()
/*     */   {
/*  70 */     return getEntity().isImage();
/*     */   }
/*     */ 
/*     */   public InDesignAssetEntity getEntity()
/*     */   {
/*  78 */     return this.m_entity;
/*     */   }
/*     */ 
/*     */   public void setEntity(InDesignAssetEntity a_entity)
/*     */   {
/*  83 */     this.m_entity = a_entity;
/*     */   }
/*     */ 
/*     */   public InDesignDocument getDocument()
/*     */   {
/*  88 */     return this.m_document;
/*     */   }
/*     */ 
/*     */   public void setDocument(InDesignDocument a_document)
/*     */   {
/*  93 */     this.m_document = a_document;
/*     */   }
/*     */ 
/*     */   public String getPdfStatus()
/*     */   {
/*  98 */     return this.m_pdfStatus;
/*     */   }
/*     */ 
/*     */   public void setPdfStatus(String a_pdfStatus)
/*     */   {
/* 103 */     this.m_pdfStatus = a_pdfStatus;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.asset.bean.InDesignAssetWithRelated
 * JD-Core Version:    0.6.0
 */