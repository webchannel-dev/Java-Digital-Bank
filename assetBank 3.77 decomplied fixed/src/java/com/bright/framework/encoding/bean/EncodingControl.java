package com.bright.framework.encoding.bean;

public abstract interface EncodingControl
{
  public abstract byte[] encode(byte[] paramArrayOfByte);

  public abstract byte[] decode(byte[] paramArrayOfByte);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.encoding.bean.EncodingControl
 * JD-Core Version:    0.6.0
 */