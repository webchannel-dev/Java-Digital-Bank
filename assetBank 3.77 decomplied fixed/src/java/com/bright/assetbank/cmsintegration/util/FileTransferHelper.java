package com.bright.assetbank.cmsintegration.util;

import com.bn2web.common.exception.Bn2Exception;
import java.io.IOException;

public abstract interface FileTransferHelper
{
  public abstract boolean requiresConnection();

  public abstract void connect(String paramString1, int paramInt, String paramString2, String paramString3)
    throws Bn2Exception;

  public abstract void disconnect();

  public abstract String copyFile(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
    throws IOException;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.cmsintegration.util.FileTransferHelper
 * JD-Core Version:    0.6.0
 */