package com.bright.framework.database.service;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.framework.database.bean.DBTransaction;
import java.sql.SQLException;

public abstract interface DBTransactionCallback<T>
{
  public abstract T doInTransaction(DBTransaction paramDBTransaction)
    throws SQLException, Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.service.DBTransactionCallback
 * JD-Core Version:    0.6.0
 */