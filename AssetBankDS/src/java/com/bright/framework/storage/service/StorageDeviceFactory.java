package com.bright.framework.storage.service;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.framework.database.bean.DBTransaction;
import com.bright.framework.storage.bean.StorageDevice;
import com.bright.framework.storage.constant.StorageDeviceType;
import java.util.List;

public abstract interface StorageDeviceFactory<T extends StorageDevice>
{
  public abstract String getDisplayName();

  public abstract String getFactoryClassName();

  public abstract List<StorageDeviceType> getSupportedDeviceTypes();

  public abstract T createNewStorageDevice();

  public abstract T saveStorageDevice(DBTransaction paramDBTransaction, T paramT)
    throws Bn2Exception;

  public abstract List<String> validateStorageDevice(T paramT);

  public abstract T loadStorageDevice(DBTransaction paramDBTransaction, long paramLong, boolean paramBoolean)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.service.StorageDeviceFactory
 * JD-Core Version:    0.6.0
 */