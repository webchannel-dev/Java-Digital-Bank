package com.bright.assetbank.plugin.service;

import com.bright.assetbank.application.service.CategoryDiskUsageExtension;
import com.bright.assetbank.application.service.UploadAssetExtension;
import com.bright.assetbank.batchrel.service.DependencyProvider;
import com.bright.assetbank.category.service.BrowseAssetsPaneller;
import com.bright.assetbank.clientsideedit.service.EditorDependenciesProvider;
import com.bright.assetbank.plugin.iface.ABExtension;

public abstract interface PluginSPI
{
  public abstract void addExtension(String paramString, ABExtension paramABExtension);

  public abstract void addDependencyProvider(DependencyProvider paramDependencyProvider);

  public abstract void addEditorDependencyProvider(EditorDependenciesProvider paramEditorDependenciesProvider);

  public abstract void addCategoryDiskUsageExtension(CategoryDiskUsageExtension paramCategoryDiskUsageExtension);

  public abstract void addUploadAssetExtension(UploadAssetExtension paramUploadAssetExtension);

  public abstract void setBrowseAssetsPaneller(BrowseAssetsPaneller paramBrowseAssetsPaneller);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.service.PluginSPI
 * JD-Core Version:    0.6.0
 */