package com.bright.framework.util;

import com.bn2web.common.exception.Bn2Exception;

public abstract interface UnaryFunction<A, R>
{
  public abstract R execute(A paramA)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.UnaryFunction
 * JD-Core Version:    0.6.0
 */