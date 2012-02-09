package com.bright.assetbank.autocomplete.service;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.framework.queue.bean.QueuedItem;

public abstract class ACIndexJob extends QueuedItem
{
  public abstract void perform(AutoCompleteUpdateManager paramAutoCompleteUpdateManager)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.autocomplete.service.ACIndexJob
 * JD-Core Version:    0.6.0
 */