package com.bright.framework.search.service;

import com.bn2web.common.exception.Bn2Exception;

public abstract interface SortFieldSpecifier
{
  public abstract String[] getSortFieldsNames()
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.service.SortFieldSpecifier
 * JD-Core Version:    0.6.0
 */