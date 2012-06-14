/*     */ package com.bright.assetbank.batch.upload.bean;
/*     */ 
/*     */ import com.bright.assetbank.batch.update.bean.BatchAssetInfo;
/*     */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*     */ 
/*     */ public class BatchBulkUploadInfo extends BatchAssetInfo
/*     */ {
/*  40 */   private String m_sDirectoryOrFileName = null;
/*  41 */   private boolean m_bFromZip = false;
/*  42 */   private boolean m_bLinkAssets = false;
/*  43 */   private boolean m_bPopulateNameFromFilename = false;
/*  44 */   private boolean m_bRemoveIdFromFilename = false;
/*  45 */   private boolean m_bProcessZipsAsAssets = false;
/*     */ 
/*  48 */   private long m_lChosenFileFormat = 0L;
/*     */ 
/*  52 */   private boolean m_bImportFilesToExistingAssets = false;
/*  53 */   private boolean m_bImportChildAssets = false;
/*  54 */   private boolean m_bAddPlaceholders = false;
/*     */ 
/*  56 */   private long m_lAssetEntityId = 0L;
/*  57 */   private long m_lSourcePeerId = -1L;
/*     */ 
/*  60 */   private int m_iNumAssetsToAdd = 0;
/*     */ 
/*  62 */   private WorkflowUpdate m_workflowUpdate = null;
/*     */ 
/*     */   public String getDirectoryOrFileName()
/*     */   {
/*  68 */     return this.m_sDirectoryOrFileName;
/*     */   }
/*     */ 
/*     */   public void setDirectoryOrFileName(String a_sDirectoryOrFileName)
/*     */   {
/*  73 */     this.m_sDirectoryOrFileName = a_sDirectoryOrFileName;
/*     */   }
/*     */ 
/*     */   public boolean getFromZip()
/*     */   {
/*  78 */     return this.m_bFromZip;
/*     */   }
/*     */ 
/*     */   public void setFromZip(boolean a_bFromZip)
/*     */   {
/*  83 */     this.m_bFromZip = a_bFromZip;
/*     */   }
/*     */ 
/*     */   public void setLinkAssets(boolean a_bLinkAssets)
/*     */   {
/*  88 */     this.m_bLinkAssets = a_bLinkAssets;
/*     */   }
/*     */ 
/*     */   public boolean getLinkAssets()
/*     */   {
/*  93 */     return this.m_bLinkAssets;
/*     */   }
/*     */ 
/*     */   public boolean getPopulateNameFromFilename() {
/*  97 */     return this.m_bPopulateNameFromFilename;
/*     */   }
/*     */ 
/*     */   public void setPopulateNameFromFilename(boolean a_sPopulateNameFromFilename) {
/* 101 */     this.m_bPopulateNameFromFilename = a_sPopulateNameFromFilename;
/*     */   }
/*     */ 
/*     */   public void setProcessZipsAsAssets(boolean a_bProcessZipsAsAssets)
/*     */   {
/* 106 */     this.m_bProcessZipsAsAssets = a_bProcessZipsAsAssets;
/*     */   }
/*     */ 
/*     */   public boolean getProcessZipsAsAssets()
/*     */   {
/* 111 */     return this.m_bProcessZipsAsAssets;
/*     */   }
/*     */ 
/*     */   public long getChosenFileFormat()
/*     */   {
/* 116 */     return this.m_lChosenFileFormat;
/*     */   }
/*     */ 
/*     */   public void setChosenFileFormat(long a_lChosenFileFormat) {
/* 120 */     this.m_lChosenFileFormat = a_lChosenFileFormat;
/*     */   }
/*     */ 
/*     */   public void setImportFilesToExistingAssets(boolean a_bImportFilesToExistingAssets)
/*     */   {
/* 125 */     this.m_bImportFilesToExistingAssets = a_bImportFilesToExistingAssets;
/*     */   }
/*     */ 
/*     */   public boolean getImportFilesToExistingAssets()
/*     */   {
/* 130 */     return this.m_bImportFilesToExistingAssets;
/*     */   }
/*     */ 
/*     */   public void setImportChildAssets(boolean a_bImportChildAssets)
/*     */   {
/* 135 */     this.m_bImportChildAssets = a_bImportChildAssets;
/*     */   }
/*     */ 
/*     */   public boolean getImportChildAssets()
/*     */   {
/* 140 */     return this.m_bImportChildAssets;
/*     */   }
/*     */ 
/*     */   public long getAssetEntityId()
/*     */   {
/* 145 */     return this.m_lAssetEntityId;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityId(long a_lAssetEntityId) {
/* 149 */     this.m_lAssetEntityId = a_lAssetEntityId;
/*     */   }
/*     */ 
/*     */   public int getNumAssetsToAdd()
/*     */   {
/* 154 */     return this.m_iNumAssetsToAdd;
/*     */   }
/*     */ 
/*     */   public void setNumAssetsToAdd(int a_sNumAssetsToAdd)
/*     */   {
/* 159 */     this.m_iNumAssetsToAdd = a_sNumAssetsToAdd;
/*     */   }
/*     */ 
/*     */   public boolean getAddPlaceholders()
/*     */   {
/* 164 */     return this.m_bAddPlaceholders;
/*     */   }
/*     */ 
/*     */   public void setAddPlaceholders(boolean a_sAddPlaceholders)
/*     */   {
/* 169 */     this.m_bAddPlaceholders = a_sAddPlaceholders;
/*     */   }
/*     */ 
/*     */   public void setWorkflowUpdate(WorkflowUpdate a_workflowUpdate)
/*     */   {
/* 174 */     this.m_workflowUpdate = a_workflowUpdate;
/*     */   }
/*     */ 
/*     */   public WorkflowUpdate getWorkflowUpdate()
/*     */   {
/* 179 */     return this.m_workflowUpdate;
/*     */   }
/*     */ 
/*     */   public boolean isRemoveIdFromFilename()
/*     */   {
/* 184 */     return this.m_bRemoveIdFromFilename;
/*     */   }
/*     */ 
/*     */   public void setRemoveIdFromFilename(boolean a_bRemoveIdFromFilename)
/*     */   {
/* 189 */     this.m_bRemoveIdFromFilename = a_bRemoveIdFromFilename;
/*     */   }
/*     */ 
/*     */   public void setSourcePeerId(long a_lSourcePeerId)
/*     */   {
/* 194 */     this.m_lSourcePeerId = a_lSourcePeerId;
/*     */   }
/*     */ 
/*     */   public long getSourcePeerId()
/*     */   {
/* 199 */     return this.m_lSourcePeerId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.upload.bean.BatchBulkUploadInfo
 * JD-Core Version:    0.6.0
 */