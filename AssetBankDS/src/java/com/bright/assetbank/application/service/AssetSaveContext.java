/*     */ package com.bright.assetbank.application.service;
/*     */ 
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*     */ 
/*     */ public class AssetSaveContext extends AssetChangeContext
/*     */ {
/*     */   private boolean m_bNew;
/*  38 */   private boolean m_bNeedOriginalAsset = false;
/*     */ 
/*  40 */   private boolean m_bHaveOriginalAsset = false;
/*     */   private Asset m_originalAsset;
/*     */   private Asset m_asset;
/*     */   private WorkflowUpdate m_update;
/*     */ 
/*     */   public AssetSaveContext(Asset a_asset, boolean a_bNew, WorkflowUpdate a_update)
/*     */   {
/*  51 */     this.m_asset = a_asset;
/*  52 */     this.m_bNew = a_bNew;
/*  53 */     this.m_update = a_update;
/*     */   }
/*     */ 
/*     */   public void needOriginalAsset()
/*     */   {
/*  60 */     this.m_bNeedOriginalAsset = true;
/*     */   }
/*     */ 
/*     */   public Asset getOriginalAsset()
/*     */   {
/*  67 */     if (!this.m_bHaveOriginalAsset)
/*     */     {
/*  69 */       throw new IllegalStateException("Original Asset not populated.  Did your initAssetSave() method call needOriginalAsset()?");
/*     */     }
/*     */ 
/*  72 */     return this.m_originalAsset;
/*     */   }
/*     */ 
/*     */   void setOriginalAsset(Asset a_originalAsset)
/*     */   {
/*  77 */     if ((!this.m_bNew) && (a_originalAsset == null)) {
/*  78 */       throw new NullPointerException();
/*     */     }
/*  80 */     this.m_originalAsset = a_originalAsset;
/*  81 */     this.m_bHaveOriginalAsset = true;
/*     */   }
/*     */ 
/*     */   public Asset getAsset()
/*     */   {
/*  88 */     return this.m_asset;
/*     */   }
/*     */ 
/*     */   void setAsset(Asset a_asset)
/*     */   {
/*  93 */     this.m_asset = a_asset;
/*     */   }
/*     */ 
/*     */   public boolean isNew()
/*     */   {
/*  98 */     return this.m_bNew;
/*     */   }
/*     */ 
/*     */   public WorkflowUpdate getWorkflowUpdate()
/*     */   {
/* 103 */     return this.m_update;
/*     */   }
/*     */ 
/*     */   boolean getNeedOriginalAsset()
/*     */   {
/* 110 */     return this.m_bNeedOriginalAsset;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.AssetSaveContext
 * JD-Core Version:    0.6.0
 */