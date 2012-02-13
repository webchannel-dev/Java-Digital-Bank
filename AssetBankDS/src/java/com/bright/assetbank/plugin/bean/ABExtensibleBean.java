package com.bright.assetbank.plugin.bean;

import java.io.Serializable;

public abstract interface ABExtensibleBean
{
  public abstract void setExtensionData(String paramString, Serializable paramSerializable);

  public abstract Serializable getExtensionData(String paramString);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.bean.ABExtensibleBean
 * JD-Core Version:    0.6.0
 */