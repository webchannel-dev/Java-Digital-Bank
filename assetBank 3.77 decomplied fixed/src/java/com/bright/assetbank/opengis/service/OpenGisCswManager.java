package com.bright.assetbank.opengis.service;

import com.bright.assetbank.application.service.AssetManager;
import com.bright.assetbank.attribute.service.AttributeManager;
import com.bright.assetbank.opengis.exception.OpenGisServiceException;
import com.bright.assetbank.search.service.MultiLanguageSearchManager;
import com.bright.assetbank.user.service.ABUserManager;
import org.w3c.dom.Element;

public abstract interface OpenGisCswManager
{
  public abstract Element getCapabilities(Element paramElement, boolean paramBoolean)
    throws OpenGisServiceException;

  public abstract Element describeRecord(Element paramElement, boolean paramBoolean)
    throws OpenGisServiceException;

  public abstract Element getRecordById(Element paramElement, boolean paramBoolean)
    throws OpenGisServiceException;

  public abstract Element getRecords(Element paramElement, boolean paramBoolean)
    throws OpenGisServiceException;

  public abstract void setSearchManager(MultiLanguageSearchManager paramMultiLanguageSearchManager);

  public abstract void setAttributeManager(AttributeManager paramAttributeManager);

  public abstract void setAssetManager(AssetManager paramAssetManager);

  public abstract void setUserManager(ABUserManager paramABUserManager);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.opengis.service.OpenGisCswManager
 * JD-Core Version:    0.6.0
 */