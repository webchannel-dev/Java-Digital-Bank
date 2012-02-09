package com.bright.assetbank.marketing.form;

import java.util.List;

public abstract interface MarketingGroupSubscriptionForm
{
  public abstract List getMarketingGroups();

  public abstract void setMarketingGroups(List paramList);

  public abstract void setMarketingGroupIds(String[] paramArrayOfString);

  public abstract String[] getMarketingGroupIds();

  public abstract int getNumberOfGroupSubscriptions();

  public abstract boolean getMarketingGroupsHaveDescriptions();

  public abstract void setMarketingGroupsHaveDescriptions(boolean paramBoolean);

  public abstract void setPopulatedViaReload(boolean paramBoolean);

  public abstract boolean isPopulatedviaReload();
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.form.MarketingGroupSubscriptionForm
 * JD-Core Version:    0.6.0
 */