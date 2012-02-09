package com.bright.framework.language.bean;

import java.util.Vector;

public abstract interface Translatable
{
  public abstract Translation createTranslation(Language paramLanguage);

  public abstract void setTranslations(Vector paramVector);

  public abstract Vector getTranslations();
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.language.bean.Translatable
 * JD-Core Version:    0.6.0
 */