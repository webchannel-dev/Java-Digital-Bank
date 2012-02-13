package com.bright.assetbank.plugin.iface;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.framework.database.bean.DBTransaction;
import javax.servlet.http.HttpServletRequest;

public abstract interface ABViewMod
{
  public abstract String getInclude(String paramString);

  public abstract String getReplacementInclude(String paramString);

  public abstract void populateViewRequest(DBTransaction paramDBTransaction, HttpServletRequest paramHttpServletRequest, Object paramObject1, Object paramObject2)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.iface.ABViewMod
 * JD-Core Version:    0.6.0
 */