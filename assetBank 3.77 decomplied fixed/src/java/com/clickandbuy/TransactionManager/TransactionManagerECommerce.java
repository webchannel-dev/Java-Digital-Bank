package com.clickandbuy.TransactionManager;

import java.rmi.Remote;
import java.rmi.RemoteException;
import org.apache.axis.types.UnsignedInt;

public abstract interface TransactionManagerECommerce extends Remote
{
  public abstract TransactionManagerECommerceReservation rcCreateReservation(TransactionManagerPaymentSingleRequest paramTransactionManagerPaymentSingleRequest)
    throws RemoteException, TransactionManagerPaymentPaymentException;

  public abstract boolean rcCaptureReservation(long paramLong1, String paramString1, long paramLong2, long paramLong3, UnsignedInt paramUnsignedInt, String paramString2, String paramString3)
    throws RemoteException, TransactionManagerPaymentPaymentException;

  public abstract boolean rcCancelReservation(long paramLong1, String paramString1, long paramLong2, long paramLong3, String paramString2)
    throws RemoteException, TransactionManagerPaymentPaymentException;

  public abstract TransactionManagerECommerceReservationInfo rcStatusReservation(long paramLong1, String paramString, long paramLong2, long paramLong3)
    throws RemoteException, TransactionManagerPaymentPaymentException;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerECommerce
 * JD-Core Version:    0.6.0
 */