package com.bright.assetbank.attribute.plugin;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.framework.common.bean.IdValueBean;
import java.util.Vector;

public abstract interface DataLookupPlugin
{
  public abstract Vector<IdValueBean> getAttributeValueMap(String paramString)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.plugin.DataLookupPlugin
 * JD-Core Version:    0.6.0
 */