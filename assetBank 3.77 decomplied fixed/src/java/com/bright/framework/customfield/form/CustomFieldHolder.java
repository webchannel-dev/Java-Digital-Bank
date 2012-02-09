package com.bright.framework.customfield.form;

import com.bright.framework.customfield.bean.CustomField;
import java.util.Vector;

public abstract interface CustomFieldHolder
{
  public abstract void setCustomFields(Vector<? extends CustomField> paramVector);

  public abstract Vector<? extends CustomField> getCustomFields();
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.customfield.form.CustomFieldHolder
 * JD-Core Version:    0.6.0
 */