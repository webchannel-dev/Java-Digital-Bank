package com.bright.framework.storage.service;

import com.bright.framework.database.service.DBTransactionManager;
import com.bright.framework.storage.bean.StorageDevice;

public abstract interface TransactionStorageDeviceFactory<T extends StorageDevice>
{
  public abstract void setTransactionManager(DBTransactionManager paramDBTransactionManager);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.service.TransactionStorageDeviceFactory
 * JD-Core Version:    0.6.0
 */