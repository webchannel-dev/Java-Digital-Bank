package com.bright.framework.file;

public abstract interface FileFormat
{
  public abstract String getFieldDelimiter();

  public abstract String getRecordDelimiter();

  public abstract String getFilenameExtension();

  public abstract String getLiteralDelimiter();

  public abstract String getLiteralDelimiterEnd();

  public abstract String getLiteralDelimiterEscapedReplacement();

  public abstract String getLiteralDelimiterEndEscapedReplacement();
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.file.FileFormat
 * JD-Core Version:    0.6.0
 */