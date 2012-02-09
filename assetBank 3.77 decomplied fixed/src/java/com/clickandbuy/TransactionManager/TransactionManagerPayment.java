package com.clickandbuy.TransactionManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface TransactionManagerPayment extends Remote
{
  public abstract TransactionManagerPaymentPaymentResponse getEasyCollectSingle(TransactionManagerPaymentSingleRequest paramTransactionManagerPaymentSingleRequest)
    throws RemoteException, TransactionManagerPaymentPaymentException;

  public abstract TransactionManagerPaymentPaymentResponse540 getEasyCollectSingle540(TransactionManagerPaymentSingleRequest paramTransactionManagerPaymentSingleRequest)
    throws RemoteException, TransactionManagerPaymentPaymentException;

  public abstract boolean commitEasyCollectBDR(long paramLong1, String paramString, long paramLong2, long paramLong3)
    throws RemoteException, TransactionManagerPaymentPaymentException;

  public abstract long partialEasyCollectBegin(long paramLong1, String paramString, long paramLong2)
    throws RemoteException, TransactionManagerPaymentPaymentException;

  public abstract long partialEasyCollectAddSingleItem(long paramLong1, String paramString, long paramLong2, TransactionManagerPaymentPaymentRequest paramTransactionManagerPaymentPaymentRequest)
    throws RemoteException, TransactionManagerPaymentPaymentException;

  public abstract long partialEasyCollectAddItems(long paramLong1, String paramString, long paramLong2, TransactionManagerPaymentPaymentRequest[] paramArrayOfTransactionManagerPaymentPaymentRequest)
    throws RemoteException, TransactionManagerPaymentPaymentException;

  public abstract long partialEasyCollectFinish(long paramLong1, String paramString, long paramLong2)
    throws RemoteException, TransactionManagerPaymentPaymentException;

  public abstract long placeEasyCollectMulti(TransactionManagerPaymentMultiRequest paramTransactionManagerPaymentMultiRequest)
    throws RemoteException, TransactionManagerPaymentPaymentException;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerPayment
 * JD-Core Version:    0.6.0
 */