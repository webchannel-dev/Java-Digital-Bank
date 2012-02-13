package com.bright.assetbank.converter;

import com.bright.assetbank.converter.exception.AssetConversionException;

public abstract interface AssetConverter
{
  public abstract String convert(String paramString1, String paramString2, String paramString3, String paramString4)
    throws AssetConversionException;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.AssetConverter
 * JD-Core Version:    0.6.0
 */