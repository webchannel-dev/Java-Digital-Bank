package com.bright.assetbank.database;

import com.bright.assetbank.attribute.bean.AttributeType;
import com.bright.framework.database.sql.ApplicationSql;

public abstract interface AssetBankSql extends ApplicationSql
{
  public abstract String dbTypeNameForAttributeType(AttributeType paramAttributeType, int paramInt);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.database.AssetBankSql
 * JD-Core Version:    0.6.0
 */