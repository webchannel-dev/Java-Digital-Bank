package com.bright.assetbank.brandtemplate.service;

import com.lowagie.text.pdf.PdfReader;
import java.io.IOException;

abstract interface PdfReaderSource
{
  public abstract PdfReader createPdfReader()
    throws IOException;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.brandtemplate.service.PdfReaderSource
 * JD-Core Version:    0.6.0
 */