package com.bright.assetbank.application.service;

import com.bright.assetbank.entity.bean.AssetEntity;

public abstract interface UploadAssetExtension
{
  public abstract boolean allowFileUploadOnAdd(AssetEntity paramAssetEntity);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.UploadAssetExtension
 * JD-Core Version:    0.6.0
 */