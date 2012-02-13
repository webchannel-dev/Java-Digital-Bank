package com.bright.framework.customfield.bean;

public abstract interface CustomFieldValueHolder
{
  public abstract void setCustomFieldValues(CustomFieldSelectedValueSet paramCustomFieldSelectedValueSet);

  public abstract CustomFieldSelectedValueSet getCustomFieldValues();

  public abstract long getItemId();
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.customfield.bean.CustomFieldValueHolder
 * JD-Core Version:    0.6.0
 */