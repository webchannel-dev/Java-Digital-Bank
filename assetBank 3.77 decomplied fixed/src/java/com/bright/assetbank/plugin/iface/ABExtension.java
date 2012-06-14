package com.bright.assetbank.plugin.iface;

public abstract interface ABExtension
{
  public abstract String getUniqueKey();

  public abstract ABViewMod getViewMod();

  public abstract ABEditMod getAddMod();

  public abstract ABEditMod getEditExistingMod();

  public abstract ABModelMod getModelMod();
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.iface.ABExtension
 * JD-Core Version:    0.6.0
 */