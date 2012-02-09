package com.bright.framework.storage.bean;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.framework.storage.constant.StorageDeviceType;
import com.bright.framework.storage.constant.StoredFileType;
import com.bright.framework.storage.exception.UnsupportedStorageDeviceTypeException;
import com.bright.framework.storage.exception.UnsupportedStoredFileTypeException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public abstract interface StorageDevice
{
  public abstract void setId(long paramLong);

  public abstract long getId();

  public abstract StorageDeviceType getType();

  public abstract void setType(StorageDeviceType paramStorageDeviceType)
    throws UnsupportedStorageDeviceTypeException;

  public abstract String getName();

  public abstract void setName(String paramString);

  public abstract void setFactoryClassName(String paramString);

  public abstract String getFactoryClassName();

  public abstract String getLocalBasePath();

  public abstract String getFullLocalBasePath();

  public abstract void setLocalBasePath(String paramString);

  public abstract String getFullLocalBasePath(StoredFileType paramStoredFileType);

  public abstract String getSubPath();

  public abstract void setSubPath(String paramString);

  public abstract boolean isLocked();

  public abstract void setLocked(boolean paramBoolean);

  public abstract long getMaxSpaceInMb();

  public abstract void setMaxSpaceInMb(long paramLong);

  public abstract void initialiseStorage()
    throws Bn2Exception;

  public abstract void runUsageScan()
    throws Bn2Exception;

  public abstract boolean getHasFreeSpace();

  public abstract long getFreeSpaceInBytes();

  public abstract long getUsedSpaceInBytes();

  public abstract void setUsedSpaceInBytes(long paramLong);

  public abstract long getUsedLocalSpaceInBytes();

  public abstract void setUsedLocalSpaceInBytes(long paramLong);

  public abstract Date getLastUsageScan();

  public abstract void setLastUsageScan(Date paramDate);

  public abstract boolean isStorageFor(StorageDeviceType paramStorageDeviceType);

  public abstract boolean isAvailableForStorage(StorageDeviceType paramStorageDeviceType);

  public abstract int getFreeSpaceSafetyMargin();

  public abstract String getHttpBaseUrl();

  public abstract void setHttpBaseUrl(String paramString);

  public abstract boolean isHttpUrlRelative();

  public abstract String getHttpUrl(String paramString)
    throws Bn2Exception, FileNotFoundException, IOException;

  public abstract String getHttpUrl(String paramString, boolean paramBoolean)
    throws FileNotFoundException, IOException, Bn2Exception;

  public abstract boolean getVerifyHttpUrls();

  public abstract void setVerifyHttpUrls(boolean paramBoolean);

  public abstract String getFullLocalPath(String paramString)
    throws Bn2Exception;

  public abstract boolean isLocalBasePathRelative();

  public abstract boolean isLocalFileStorage();

  public abstract String getRelativePathForNewFile(String paramString, StoredFileType paramStoredFileType)
    throws UnsupportedStoredFileTypeException, Bn2Exception;

  public abstract String getRelativePathForNewFile(String paramString1, String paramString2, StoredFileType paramStoredFileType)
    throws UnsupportedStoredFileTypeException, Bn2Exception;

  public abstract void storeExistingFile(String paramString)
    throws FileNotFoundException, IOException, Bn2Exception;

  public abstract String storeNewFile(String paramString, InputStream paramInputStream, StoredFileType paramStoredFileType)
    throws UnsupportedStoredFileTypeException, Bn2Exception;

  public abstract String storeNewFile(String paramString1, InputStream paramInputStream, StoredFileType paramStoredFileType, String paramString2)
    throws UnsupportedStoredFileTypeException, Bn2Exception;

  public abstract Boolean removeFile(String paramString);

  public abstract int getNumStoredAssets();

  public abstract void setNumStoredAssets(int paramInt);

  public abstract void cleanFileStore()
    throws Bn2Exception;

  public abstract int getNextStorageSubdirectory();

  public abstract void setNextStorageSubdirectory(int paramInt);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.bean.StorageDevice
 * JD-Core Version:    0.6.0
 */