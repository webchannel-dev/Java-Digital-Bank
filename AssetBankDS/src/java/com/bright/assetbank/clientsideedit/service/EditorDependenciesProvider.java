package com.bright.assetbank.clientsideedit.service;

import com.bright.assetbank.restapi.representations.EditorDependenciesRepr;
import java.net.URL;

public abstract interface EditorDependenciesProvider
{
  public abstract EditorDependenciesRepr getEditorDependenciesForAsset(URL paramURL, long paramLong);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.clientsideedit.service.EditorDependenciesProvider
 * JD-Core Version:    0.6.0
 */