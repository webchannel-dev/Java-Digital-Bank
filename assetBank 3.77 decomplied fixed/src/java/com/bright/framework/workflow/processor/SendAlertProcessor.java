package com.bright.framework.workflow.processor;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.framework.database.bean.DBTransaction;
import com.bright.framework.workflow.bean.AlertInfo;

public abstract interface SendAlertProcessor
{
  public abstract void sendAlert(DBTransaction paramDBTransaction, AlertInfo paramAlertInfo)
    throws Bn2Exception;

  public abstract void startBatch(long paramLong)
    throws Bn2Exception;

  public abstract void endBatch(long paramLong);

  public abstract void addToBatch(AlertInfo paramAlertInfo, long paramLong)
    throws Bn2Exception;

  public abstract void sendBatch(DBTransaction paramDBTransaction, long paramLong)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.workflow.processor.SendAlertProcessor
 * JD-Core Version:    0.6.0
 */