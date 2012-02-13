package com.bright.assetbank.synchronise.bean;

import com.bright.assetbank.application.bean.Asset;
import com.bright.framework.language.bean.Language;

public abstract interface ExportAsset
{
  public abstract Asset getAsset();

  public abstract void setAsset(Asset paramAsset);

  public abstract String getId();

  public abstract String getCode();

  public abstract String getFilename();

  public abstract String getAuthor();

  public abstract String getAddedByUser();

  public abstract String getDateAdded();

  public abstract String getDateModified();

  public abstract String getExpiryDate();

  public abstract String getLastModifiedByUser();

  public abstract String getApproved();

  public abstract String getPrice();

  public abstract String getAccessLevels();

  public abstract String getDescriptiveCategories();

  public abstract String getAttributeValue(long paramLong);

  public abstract String getAttributeValueTranslation(long paramLong, Language paramLanguage);

  public abstract boolean isAttributeTranslatable(long paramLong);

  public abstract String getIsSensitive();

  public abstract String getAgreementType();

  public abstract String getAgreements();
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.ExportAsset
 * JD-Core Version:    0.6.0
 */