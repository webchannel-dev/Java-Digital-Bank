package com.bright.assetbank.application.service;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.assetbank.application.bean.Asset;
import com.bright.assetbank.application.bean.AssetConversionInfo;
import com.bright.assetbank.application.bean.AssetFileSource;
import com.bright.assetbank.application.bean.FileFormat;
import com.bright.assetbank.application.bean.UploadedFileInfo;
import com.bright.assetbank.application.exception.AssetFileReadException;
import com.bright.framework.database.bean.DBTransaction;
import com.bright.framework.language.bean.Language;
import com.bright.framework.storage.constant.StoredFileType;
import java.util.Vector;

public abstract interface IAssetManagerImpl
{
  public abstract long getAssetTypeId();

  public abstract Asset getAsset(DBTransaction paramDBTransaction, long paramLong, Asset paramAsset, Vector paramVector, boolean paramBoolean)
    throws Bn2Exception;

  public abstract Asset getAsset(DBTransaction paramDBTransaction, long paramLong, Asset paramAsset, Vector paramVector, Language paramLanguage, boolean paramBoolean)
    throws Bn2Exception;

  public abstract Asset saveAsset(DBTransaction paramDBTransaction, Asset paramAsset, AssetFileSource paramAssetFileSource1, long paramLong, AssetConversionInfo paramAssetConversionInfo, AssetFileSource paramAssetFileSource2, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
    throws Bn2Exception, AssetFileReadException;

  public abstract void deleteAsset(DBTransaction paramDBTransaction, long paramLong)
    throws Bn2Exception;

  public abstract void deleteAsset(DBTransaction paramDBTransaction, Asset paramAsset)
    throws Bn2Exception;

  public abstract String getDownloadableAssetPath(Asset paramAsset, String paramString, AssetConversionInfo paramAssetConversionInfo)
    throws Bn2Exception;

  public abstract boolean canConvertToFileFormat(Asset paramAsset, FileFormat paramFileFormat)
    throws Bn2Exception;

  public abstract boolean canStoreFileFormat(FileFormat paramFileFormat);

  public abstract UploadedFileInfo storeTempUploadedFile(FileFormat paramFileFormat, AssetFileSource paramAssetFileSource, StoredFileType paramStoredFileType)
    throws Bn2Exception;

  public abstract UploadedFileInfo getUploadedFileInfo(String paramString, boolean paramBoolean)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.IAssetManagerImpl
 * JD-Core Version:    0.6.0
 */