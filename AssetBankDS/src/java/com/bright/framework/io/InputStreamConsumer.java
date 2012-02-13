package com.bright.framework.io;

import java.io.IOException;
import java.io.InputStream;

public abstract interface InputStreamConsumer
{
  public abstract void consume(InputStream paramInputStream)
    throws IOException;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.io.InputStreamConsumer
 * JD-Core Version:    0.6.0
 */