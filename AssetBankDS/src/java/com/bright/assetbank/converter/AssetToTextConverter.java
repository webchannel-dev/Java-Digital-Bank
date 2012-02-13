package com.bright.assetbank.converter;

import com.bn2web.common.exception.Bn2Exception;

public abstract interface AssetToTextConverter
{
  public abstract String getText(String paramString)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.AssetToTextConverter
 * JD-Core Version:    0.6.0
 */