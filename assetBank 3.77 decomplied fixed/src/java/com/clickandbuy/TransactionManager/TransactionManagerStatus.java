package com.clickandbuy.TransactionManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface TransactionManagerStatus extends Remote
{
  public abstract boolean getInterfaceStatus(long paramLong, String paramString)
    throws RemoteException, TransactionManagerStatusStatusException;

  public abstract ClickAndBuyTransactionBDRStatus getBDRStatusByBDRID(long paramLong1, String paramString, long paramLong2, long paramLong3)
    throws RemoteException, TransactionManagerStatusStatusException;

  public abstract ClickAndBuyTransactionBDRStatus getBDRStatusByExternalBDRID(long paramLong1, String paramString1, long paramLong2, String paramString2)
    throws RemoteException, TransactionManagerStatusStatusException;

  public abstract ClickAndBuyTransactionCustomerStatus getCustomerStatusByCRN(long paramLong1, String paramString, long paramLong2, long paramLong3, long paramLong4)
    throws RemoteException, TransactionManagerStatusStatusException;

  public abstract ClickAndBuyTransactionCustomerStatus getCustomerStatusByMSISDN(long paramLong1, String paramString1, long paramLong2, String paramString2, long paramLong3)
    throws RemoteException, TransactionManagerStatusStatusException;

  public abstract ClickAndBuyTransactionBDRStatus[] getCustomerTransactionStatus(long paramLong1, String paramString1, long paramLong2, long paramLong3, String paramString2, String paramString3)
    throws RemoteException, TransactionManagerStatusStatusException;

  public abstract TransactionManagerStatusTransStatusResponse getTransactionStatusByJobID(long paramLong1, String paramString, long paramLong2, long paramLong3)
    throws RemoteException, TransactionManagerStatusStatusException;

  public abstract TransactionManagerStatusTransStatusResponse getTransactionStatusByExternalBDRID(long paramLong1, String paramString1, long paramLong2, String paramString2)
    throws RemoteException, TransactionManagerStatusStatusException;

  public abstract TransactionManagerStatusJobStatusResponse getJobStatusByJobID(long paramLong1, String paramString, long paramLong2)
    throws RemoteException, TransactionManagerStatusStatusException;

  public abstract TransactionManagerStatusJobStatusResponse getJobStatusByExtJobID(long paramLong1, String paramString, long paramLong2)
    throws RemoteException, TransactionManagerStatusStatusException;

  public abstract TransactionManagerStatusJobDetailResponse getJobItemsStatusByJobID(long paramLong1, String paramString, long paramLong2)
    throws RemoteException, TransactionManagerStatusStatusException;

  public abstract TransactionManagerStatusJobDetailResponse getJobItemsStatusByExtJobID(long paramLong1, String paramString, long paramLong2)
    throws RemoteException, TransactionManagerStatusStatusException;

  public abstract ClickAndBuyTransactionBDRProcessingState isBDRIDCommitted(long paramLong1, String paramString, long paramLong2, long paramLong3)
    throws RemoteException, TransactionManagerStatusStatusException;

  public abstract ClickAndBuyTransactionBDRProcessingState isExternalBDRIDCommitted(long paramLong1, String paramString1, long paramLong2, String paramString2)
    throws RemoteException, TransactionManagerStatusStatusException;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerStatus
 * JD-Core Version:    0.6.0
 */