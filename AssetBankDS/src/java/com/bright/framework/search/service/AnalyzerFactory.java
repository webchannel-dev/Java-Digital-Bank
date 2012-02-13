package com.bright.framework.search.service;

import com.bn2web.common.exception.Bn2Exception;
import org.apache.lucene.analysis.Analyzer;

public abstract interface AnalyzerFactory
{
  public abstract Analyzer getAnalyzer()
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.service.AnalyzerFactory
 * JD-Core Version:    0.6.0
 */