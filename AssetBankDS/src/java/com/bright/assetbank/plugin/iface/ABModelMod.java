package com.bright.assetbank.plugin.iface;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.framework.database.bean.DBTransaction;
import java.io.Serializable;
import java.util.List;

public abstract interface ABModelMod
{
  public abstract Serializable get(DBTransaction paramDBTransaction, Object paramObject)
    throws Bn2Exception;

  public abstract List<Serializable> get(DBTransaction paramDBTransaction, List<?> paramList)
    throws Bn2Exception;

  public abstract void add(DBTransaction paramDBTransaction, Object paramObject1, Object paramObject2)
    throws Bn2Exception;

  public abstract void editExisting(DBTransaction paramDBTransaction, Object paramObject1, Object paramObject2)
    throws Bn2Exception;

  public abstract void delete(DBTransaction paramDBTransaction, long paramLong)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.iface.ABModelMod
 * JD-Core Version:    0.6.0
 */