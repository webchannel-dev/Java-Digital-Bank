package com.bright.framework.search.bean;

import org.apache.lucene.document.Document;

public abstract interface SearchResult
{
  public abstract void populateFromLuceneDocument(Document paramDocument);

  public abstract void setScore(float paramFloat);

  public abstract void setPosition(int paramInt);

  public abstract long getId();
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.bean.SearchResult
 * JD-Core Version:    0.6.0
 */