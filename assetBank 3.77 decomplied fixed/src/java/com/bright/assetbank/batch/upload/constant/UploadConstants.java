package com.bright.assetbank.batch.upload.constant;

import com.bn2web.common.constant.CommonConstants;

public abstract interface UploadConstants extends CommonConstants
{
  public static final String k_sYearOnlyDateFormat = "yyyy";
  public static final String k_sParam_QueueFailure = "queuefailure";
  public static final String k_sParam_BatchId = "batchid";
  public static final String k_sParam_ImportFromTopLevel = "#toplevel#";
  public static final String k_sParam_BulkUploadFirst = "bulkUploadFirst";
  public static final String k_sParam_WhatToUpload = "whatToUpload";
  public static final String k_sParam_ImportFilesToExistingAssets = "importFilesToExistingAssets";
  public static final String k_sParam_ImportChildAssets = "importChildAssets";
  public static final String k_sParam_AddPlaceholders = "addPlaceholders";
  public static final String k_sParam_InCompleteOnly = "incomplete";
  public static final String k_sDirectory_ImportFilesToExistingAssets = "files_for_existing_assets";
  public static final String k_sUploadType_Ftp = "ftp";
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.upload.constant.UploadConstants
 * JD-Core Version:    0.6.0
 */