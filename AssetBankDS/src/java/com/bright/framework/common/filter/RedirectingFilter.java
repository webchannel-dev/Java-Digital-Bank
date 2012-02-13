package com.bright.framework.common.filter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

public abstract interface RedirectingFilter extends Filter
{
  public abstract String getOriginalUrl(HttpServletRequest paramHttpServletRequest);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.filter.RedirectingFilter
 * JD-Core Version:    0.6.0
 */