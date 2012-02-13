package com.bright.assetbank.application.service;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.framework.database.bean.DBTransaction;

public abstract interface CategoryDiskUsageExtension
{
  public abstract long getAdditionalDiskUsageForCategoryAssets(DBTransaction paramDBTransaction, long[] paramArrayOfLong, long paramLong)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.CategoryDiskUsageExtension
 * JD-Core Version:    0.6.0
 */