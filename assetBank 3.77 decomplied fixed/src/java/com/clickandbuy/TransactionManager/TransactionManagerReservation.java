package com.clickandbuy.TransactionManager;

import java.rmi.Remote;
import java.rmi.RemoteException;
import org.apache.axis.types.UnsignedInt;

public abstract interface TransactionManagerReservation extends Remote
{
  public abstract ClickAndBuyEasyCollectReservationResult createReservation(long paramLong1, String paramString1, long paramLong2, long paramLong3, long paramLong4, UnsignedInt paramUnsignedInt, String paramString2, String paramString3)
    throws RemoteException, TransactionManagerPaymentPaymentException;

  public abstract ClickAndBuyEasyCollectEasyCollectResult capture(long paramLong1, String paramString, long paramLong2, ClickAndBuyEasyCollectEasyCollectQuery paramClickAndBuyEasyCollectEasyCollectQuery)
    throws RemoteException, TransactionManagerPaymentPaymentException;

  public abstract boolean cancelReservation(long paramLong1, String paramString, long paramLong2, long paramLong3, long paramLong4)
    throws RemoteException, TransactionManagerPaymentPaymentException;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerReservation
 * JD-Core Version:    0.6.0
 */