package com.bright.framework.storage.bean;

public abstract interface RemoteStorageDevice extends StorageDevice
{
  public abstract void setAuthenticationId(String paramString);

  public abstract String getAuthenticationId();

  public abstract void setAuthenticationKey(String paramString);

  public abstract String getAuthenticationKey();
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.bean.RemoteStorageDevice
 * JD-Core Version:    0.6.0
 */