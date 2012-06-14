package com.bright.assetbank.externalfilter.service;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.assetbank.application.bean.Asset;
import com.bright.framework.search.bean.SearchQuery;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;

public abstract interface ExternalFilter
{
  public abstract void validateSearchCriteria(Map<String, String> paramMap, List<String> paramList);

  public abstract boolean emptySearchCriteria(SearchQuery paramSearchQuery);

  public abstract Collection<Long> externalSearch(SearchQuery paramSearchQuery)
    throws Bn2Exception;

  public abstract void setLogger(Log paramLog);

  public abstract void clearIndex()
    throws Bn2Exception;

  public abstract void indexAsset(Asset paramAsset, boolean paramBoolean)
    throws Bn2Exception;

  public abstract void indexAssets(List<Asset> paramList, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws Bn2Exception;

  public abstract void removeAsset(long paramLong)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.externalfilter.service.ExternalFilter
 * JD-Core Version:    0.6.0
 */