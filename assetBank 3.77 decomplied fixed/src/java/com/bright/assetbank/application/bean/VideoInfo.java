package com.bright.assetbank.application.bean;

public abstract interface VideoInfo
{
  public abstract int getHeight();

  public abstract int getWidth();

  public abstract long getDuration();

  public abstract float getPAR();

  public abstract int getDisplayHeight();

  public abstract void setHeight(int paramInt);

  public abstract void setWidth(int paramInt);

  public abstract void setDuration(long paramLong);

  public abstract void setPAR(float paramFloat);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.VideoInfo
 * JD-Core Version:    0.6.0
 */