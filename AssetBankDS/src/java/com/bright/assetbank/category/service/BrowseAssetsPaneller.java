package com.bright.assetbank.category.service;

import com.bright.assetbank.application.bean.LightweightAsset;
import com.bright.assetbank.category.bean.Panel;
import com.bright.framework.category.bean.Category;
import com.bright.framework.user.bean.UserProfile;
import java.util.List;

public abstract interface BrowseAssetsPaneller
{
  public abstract List<Panel> getPanels(UserProfile paramUserProfile, List<LightweightAsset> paramList, List<Panel> paramList1, Category paramCategory, boolean paramBoolean);

  public abstract boolean requiresPaging();
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.service.BrowseAssetsPaneller
 * JD-Core Version:    0.6.0
 */