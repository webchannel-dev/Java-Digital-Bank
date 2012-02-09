/*    */ package com.bright.assetbank.synchronise.constant;
/*    */ 
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ 
/*    */ public abstract interface ExportConstants
/*    */ {
/* 29 */   public static final String k_sExportFileDateFormat = AssetBankSettings.getExportDateFormat();
/* 30 */   public static final String k_sImportFileDateFormat = AssetBankSettings.getImportDateFormat();
/*    */   public static final String k_sSessionAttribute_ExportAssetIds = "ExportAssetIds";
/*    */   public static final String k_sSessionAttribute_ExportResult = "ExportResult";
/*    */   public static final int k_iNumAssetsPerFileWrite = 1000;
/*    */   public static final String k_sDefaultExportFilename = "export.xls";
/*    */   public static final char k_sExportedAssetFilenameToken_Title = 't';
/*    */   public static final char k_sExportedAssetFilenameToken_Id = 'i';
/*    */   public static final char k_sExportedAssetFilenameToken_Filename = 'f';
/*    */   public static final String k_sExportedAssetFilenameToken_None = "none";
/*    */   public static final String k_sPrefix_Id = "id:";
/*    */   public static final String k_sImportServiceClassName = "SynchronisationService";
/*    */   public static final String k_sImportServiceMethodName = "synchroniseAssets";
/*    */   public static final String k_sLastSyncTimeSetting = "LastSyncTime";
/*    */   public static final int k_iMaxNumExportedAsets = 1000;
/*    */   public static final String k_sSynch_ImportSuffix = "_synch";
/*    */   public static final String k_sApprovalToken_FullApproval = "TRUE";
/*    */   public static final String k_sApprovalToken_Unapproved = "FALSE";
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.constant.ExportConstants
 * JD-Core Version:    0.6.0
 */