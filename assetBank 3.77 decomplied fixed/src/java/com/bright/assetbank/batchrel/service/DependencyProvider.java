package com.bright.assetbank.batchrel.service;

import com.bright.assetbank.application.bean.AssetLog;
import java.util.Collection;

public abstract interface DependencyProvider
{
  public abstract Collection<Long> getDependentAssetIds(long paramLong, Collection<AssetLog> paramCollection);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batchrel.service.DependencyProvider
 * JD-Core Version:    0.6.0
 */