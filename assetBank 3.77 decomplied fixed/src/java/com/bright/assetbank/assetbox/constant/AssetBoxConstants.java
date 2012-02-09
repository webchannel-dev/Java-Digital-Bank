/*    */ package com.bright.assetbank.assetbox.constant;
/*    */ 
/*    */ public abstract interface AssetBoxConstants
/*    */ {
/*    */   public static final int k_iAssetStateCanDownload = 1;
/*    */   public static final int k_iAssetStateApprovalApproved = 2;
/*    */   public static final int k_iAssetStateApprovalRequired = 3;
/*    */   public static final int k_iAssetStateApprovalPending = 4;
/*    */   public static final int k_iAssetStateApprovalRejected = 5;
/*    */   public static final int k_iAssetStateViewOnly = 6;
/*    */   public static final int k_iAssetStateCanDownloadNow = 7;
/*    */   public static final int k_iAssetStateHighResApprovalRequired = 8;
/*    */   public static final int k_iUnlimitedDimension = 9999999;
/*    */   public static final String k_sAttributeName_AssetboxDownloadFiles = "assetboxDownloadFiles";
/*    */   public static final String k_sAttributeName_EmailAssetBox = "emailAssetBox";
/*    */   public static final String k_sAttributeName_ErrorMessage = "assetBoxErrorMessage";
/*    */   public static final String k_sAttributeName_DownloadingAssetBox = "downloadingAssetBox";
/*    */   public static final String k_sAttributeName_DownloadingParentId = "downloadingParentId";
/*    */   public static final String k_sParameterName_AssetBoxId = "assetBoxId";
/*    */   public static final String k_sParameterName_CurrentAssetBoxId = "currentAssetBoxId";
/*    */   public static final String k_sParameterName_AssetBoxName = "name";
/*    */   public static final String k_sParameterName_AssetBoxIsShared = "shared";
/*    */   public static final String k_sParameterName_SharePermissionCanEdit = "canEdit";
/*    */   public static final String k_sParameterName_SelectedUsers = "selectedUsers";
/*    */   public static final String k_sParameterName_OnlyRemoveSelected = "onlyRemoveSelected";
/*    */   public static final String k_sParameterName_MoveTo = "moveTo";
/*    */   public static final String k_sViewAssetBoxAction = "viewAssetBox";
/*    */   public static final String k_sShareAssetBoxEmailTemplate = "shared_assetbox";
/*    */   public static final int k_iPseudoSortingAttributeId_TimeAdded = -2;
/* 67 */   public static final long[] k_sValidSortingAttributeTypes = { 1L, 2L, 3L, 8L, 14L, 15L };
/*    */ 
/* 74 */   public static final String[] k_sValidSortingStaticAttributeNames = { "assetId", "dateAdded", "dateLastModified", "dateLastDownloaded", "size", "originalFilename" };
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.constant.AssetBoxConstants
 * JD-Core Version:    0.6.0
 */