package com.bright.assetbank.custom.indesign.custom;

import com.bn2web.common.exception.Bn2Exception;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract interface InDesignAttributeProvider
{
  public abstract Collection<Long> getAvailableAttributeIds(long paramLong)
    throws Bn2Exception;

  public abstract Map<Long, String> getAttributeValues(long paramLong, Collection<Long> paramCollection)
    throws Bn2Exception;

  public abstract Collection<Long> getAttributeDependentAssetIds(long paramLong, Set<Long> paramSet);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.custom.InDesignAttributeProvider
 * JD-Core Version:    0.6.0
 */