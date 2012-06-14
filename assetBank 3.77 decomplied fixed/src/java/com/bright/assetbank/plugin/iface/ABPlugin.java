package com.bright.assetbank.plugin.iface;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.assetbank.plugin.service.PluginSPI;

public abstract interface ABPlugin
{
  public abstract void startup(PluginSPI paramPluginSPI)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.iface.ABPlugin
 * JD-Core Version:    0.6.0
 */