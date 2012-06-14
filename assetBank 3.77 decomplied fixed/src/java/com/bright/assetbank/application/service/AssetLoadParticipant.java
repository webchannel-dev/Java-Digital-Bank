package com.bright.assetbank.application.service;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.assetbank.application.bean.Asset;
import java.util.List;

public abstract interface AssetLoadParticipant
{
  public abstract void assetWasLoaded(Asset paramAsset)
    throws Bn2Exception;

  public abstract void assetsWereLoaded(List<Asset> paramList)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.AssetLoadParticipant
 * JD-Core Version:    0.6.0
 */