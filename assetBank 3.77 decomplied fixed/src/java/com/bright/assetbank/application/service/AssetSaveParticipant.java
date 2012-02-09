package com.bright.assetbank.application.service;

import com.bn2web.common.exception.Bn2Exception;

public abstract interface AssetSaveParticipant
{
  public abstract void initAssetSave(AssetSaveContext paramAssetSaveContext)
    throws Bn2Exception;

  public abstract void save(AssetSaveContext paramAssetSaveContext)
    throws Bn2Exception;

  public abstract void assetWasSaved(AssetSaveContext paramAssetSaveContext)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.AssetSaveParticipant
 * JD-Core Version:    0.6.0
 */