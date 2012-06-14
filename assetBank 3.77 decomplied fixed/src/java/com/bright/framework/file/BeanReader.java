package com.bright.framework.file;

import com.bn2web.common.exception.Bn2Exception;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

public abstract interface BeanReader
{
  public abstract Vector readBeans()
    throws Bn2Exception, IOException, InstantiationException, IllegalAccessException, InvocationTargetException;

  public abstract Vector readBeans(int paramInt)
    throws Bn2Exception, IOException, InstantiationException, IllegalAccessException, InvocationTargetException;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.file.BeanReader
 * JD-Core Version:    0.6.0
 */