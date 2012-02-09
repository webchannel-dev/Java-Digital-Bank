package com.bright.assetbank.application.service;

import com.bn2web.common.exception.Bn2Exception;

public abstract interface AssetDeleteParticipant
{
  public abstract void assetWillBeDeleted(AssetDeleteContext paramAssetDeleteContext)
    throws Bn2Exception;

  public abstract void assetWasDeleted(AssetDeleteContext paramAssetDeleteContext)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.AssetDeleteParticipant
 * JD-Core Version:    0.6.0
 */