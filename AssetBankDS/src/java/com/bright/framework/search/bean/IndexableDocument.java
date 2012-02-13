package com.bright.framework.search.bean;

import org.apache.lucene.document.Document;

public abstract interface IndexableDocument
{
  public abstract Document createLuceneDocument(Object paramObject);

  public abstract String getIndexableDocId();
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.bean.IndexableDocument
 * JD-Core Version:    0.6.0
 */