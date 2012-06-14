package com.bright.framework.search.lucene;

import org.apache.lucene.search.Filter;

public abstract interface SearchFilter
{
  public abstract Filter getAsLuceneFilter();
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.lucene.SearchFilter
 * JD-Core Version:    0.6.0
 */