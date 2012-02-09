package com.bright.framework.file;

import com.bn2web.common.exception.Bn2Exception;
import java.util.Vector;

public abstract interface BeanWriter
{
  public abstract void writeBeansWithPropertyHeader(Vector paramVector, BeanWrapper paramBeanWrapper)
    throws Bn2Exception;

  public abstract void writeBeans(Vector paramVector, BeanWrapper paramBeanWrapper)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.file.BeanWriter
 * JD-Core Version:    0.6.0
 */