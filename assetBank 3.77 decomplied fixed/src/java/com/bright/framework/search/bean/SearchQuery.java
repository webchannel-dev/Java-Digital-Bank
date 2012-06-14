package com.bright.framework.search.bean;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.framework.common.bean.BrightMoney;
import com.bright.framework.search.lucene.SearchFilter;
import java.util.Map;
import java.util.Vector;
import org.apache.lucene.search.SortField;

public abstract interface SearchQuery
{
  public abstract String getLuceneQuery();

  public abstract String getQueryDescription();

  public abstract String getLanguageCode();

  public abstract void setLanguageCode(String paramString);

  public abstract long getSortAttributeId();

  public abstract void setSortAttributeId(long paramLong);

  public abstract void setSortDescending(boolean paramBoolean);

  public abstract boolean isSortDescending();

  public abstract Vector<Long> getAssetEntityIdsToInclude();

  public abstract void setAssetEntityIdsToInclude(Vector<Long> paramVector);

  public abstract boolean isEmpty();

  public abstract BrightMoney getPriceLower();

  public abstract void setPriceLower(BrightMoney paramBrightMoney);

  public abstract BrightMoney getPriceUpper();

  public abstract void setPriceUpper(BrightMoney paramBrightMoney);

  public abstract Vector getDescriptiveCategoriesToRefine();

  public abstract void setDescriptiveCategoriesToRefine(Vector paramVector);

  public abstract Vector getPermissionCategoriesToRefine();

  public abstract void setPermissionCategoriesToRefine(Vector paramVector);

  public abstract void setCategoryIds(String paramString);

  public abstract String getCategoryIds();

  public abstract void setIncludeImplicitCategoryMembers(boolean paramBoolean);

  public abstract int getPageSize();

  public abstract void setPageSize(int paramInt);

  public abstract int getPageIndex();

  public abstract void setPageIndex(int paramInt);

  public abstract int getMaxNoOfResults();

  public abstract void setMaxNoOfResults(int paramInt);

  public abstract String getDefaultOperator();

  public abstract SortField[] getSortFields();

  public abstract void setSortFields(SortField[] paramArrayOfSortField);

  public abstract SearchFilter[] getSearchFilters()
    throws Bn2Exception;

  public abstract Map<String, String> getExternalFilterCriteria();

  public abstract void setExternalFilterCriteria(Map<String, String> paramMap);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.bean.SearchQuery
 * JD-Core Version:    0.6.0
 */