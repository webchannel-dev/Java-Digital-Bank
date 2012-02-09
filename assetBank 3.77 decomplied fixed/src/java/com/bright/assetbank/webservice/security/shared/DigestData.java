package com.bright.assetbank.webservice.security.shared;

public abstract interface DigestData
{
  public abstract void storeNewDigestHash(String paramString);

  public abstract String getGeneratedDigestHash(String paramString);

  public abstract String getCnonce();

  public abstract void setCnonce(String paramString);

  public abstract String getNonce();

  public abstract void setNonce(String paramString);

  public abstract String getUsername();

  public abstract void setUsername(String paramString);

  public abstract String getStoredDigestHash();

  public abstract void setStoredDigestHash(String paramString);

  public abstract Long getRequestCounter();

  public abstract void setRequestCounter(Long paramLong);

  public abstract String getAlgorithm();

  public abstract void setAlgorithm(String paramString);

  public abstract String getOpaque();

  public abstract void setOpaque(String paramString);

  public abstract String getQualityOfProtection();

  public abstract void setQualityOfProtection(String paramString);

  public abstract String getRealm();

  public abstract void setRealm(String paramString);

  public abstract String getUri();

  public abstract void setUri(String paramString);

  public abstract boolean isStale();

  public abstract void setStale(boolean paramBoolean);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.webservice.security.shared.DigestData
 * JD-Core Version:    0.6.0
 */