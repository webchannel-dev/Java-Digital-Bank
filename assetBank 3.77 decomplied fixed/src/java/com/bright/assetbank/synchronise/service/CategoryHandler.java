package com.bright.assetbank.synchronise.service;

import com.bn2web.common.exception.Bn2Exception;
import java.util.List;
import java.util.Set;

public abstract interface CategoryHandler
{
  public abstract Set<Long> getCategoryIds(List<? extends Object> paramList, long paramLong)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.service.CategoryHandler
 * JD-Core Version:    0.6.0
 */