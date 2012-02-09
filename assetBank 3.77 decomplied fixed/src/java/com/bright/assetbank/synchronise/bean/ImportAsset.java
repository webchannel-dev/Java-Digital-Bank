package com.bright.assetbank.synchronise.bean;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.assetbank.application.bean.Asset;
import com.bright.assetbank.attribute.bean.AttributeValue;

public abstract interface ImportAsset
{
  public abstract Asset getAsset();

  public abstract void setAsset(Asset paramAsset);

  public abstract void setId(String paramString);

  public abstract void setCode(String paramString);

  public abstract void setFilename(String paramString);

  public abstract void setAuthor(String paramString);

  public abstract void setAddedByUser(String paramString);

  public abstract void setLastModifiedByUser(String paramString);

  public abstract void setDateAdded(String paramString);

  public abstract void setDateModified(String paramString);

  public abstract void setExpiryDate(String paramString);

  public abstract void setApproved(String paramString);

  public abstract void setPrice(String paramString);

  public abstract void setAccessLevels(String paramString)
    throws Bn2Exception;

  public abstract void setDescriptiveCategories(String paramString)
    throws Bn2Exception;

  public abstract void setAttributeValue(AttributeValue paramAttributeValue);

  public abstract void setRelatedAssets(String paramString);

  public abstract void setChildAssets(String paramString);

  public abstract void setParentAssets(String paramString);

  public abstract void setEntityId(String paramString);

  public abstract void setIsSensitive(String paramString);

  public abstract void setOriginalFilename(String paramString);

  public abstract void setAgreementType(String paramString);

  public abstract void setAgreements(String paramString);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.ImportAsset
 * JD-Core Version:    0.6.0
 */