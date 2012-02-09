/*    */ package com.bright.assetbank.presentation.service;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bn2web.common.service.Bn2Manager;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.approval.bean.AssetInList;
/*    */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*    */ import com.bright.framework.service.FileStoreManager;
/*    */ import com.bright.framework.storage.constant.StoredFileType;
/*    */ 
/*    */ public class PresentationManager extends Bn2Manager
/*    */   implements AssetBankConstants, AssetBoxConstants
/*    */ {
/* 35 */   private FileStoreManager m_fileStoreManager = null;
/*    */ 
/*    */   public void setFileStoreManager(FileStoreManager a_fileStoreManager) {
/* 38 */     this.m_fileStoreManager = a_fileStoreManager;
/*    */   }
/*    */ 
/*    */   public String mergeAssets(AssetInList[] a_aAssets)
/*    */     throws Bn2Exception
/*    */   {
/* 56 */     String sResultStem = "merged.ppt";
/* 57 */     String sMergedFilePath = this.m_fileStoreManager.getUniqueFilepath(sResultStem, StoredFileType.ASSET);
/*    */ 
/* 59 */     PresentationMergingConverter merger = new PresentationMergingConverter();
/*    */ 
/* 61 */     merger.merge(this.m_fileStoreManager, this.m_fileStoreManager.getAbsolutePath(sMergedFilePath), a_aAssets);
/*    */ 
/* 63 */     return sMergedFilePath;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.presentation.service.PresentationManager
 * JD-Core Version:    0.6.0
 */