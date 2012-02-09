package com.bright.assetbank.synchronise.bean;

import com.bn2web.common.exception.Bn2Exception;

public abstract class FileListReader
{
  public abstract String[] getFiles()
    throws Bn2Exception;

  public abstract String[] getFilesToUpdate()
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.FileListReader
 * JD-Core Version:    0.6.0
 */