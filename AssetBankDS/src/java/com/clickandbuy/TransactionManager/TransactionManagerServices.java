package com.clickandbuy.TransactionManager;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public abstract interface TransactionManagerServices extends Service
{
  public abstract String getTransactionManagerStatusServicesPortAddress();

  public abstract TransactionManagerStatus getTransactionManagerStatusServicesPort()
    throws ServiceException;

  public abstract TransactionManagerStatus getTransactionManagerStatusServicesPort(URL paramURL)
    throws ServiceException;

  public abstract String getTransactionManagerReservationServicesPortAddress();

  public abstract TransactionManagerReservation getTransactionManagerReservationServicesPort()
    throws ServiceException;

  public abstract TransactionManagerReservation getTransactionManagerReservationServicesPort(URL paramURL)
    throws ServiceException;

  public abstract String getTransactionManagerPaymentServicesPortAddress();

  public abstract TransactionManagerPayment getTransactionManagerPaymentServicesPort()
    throws ServiceException;

  public abstract TransactionManagerPayment getTransactionManagerPaymentServicesPort(URL paramURL)
    throws ServiceException;

  public abstract String getTransactionManagerECommerceServicesPortAddress();

  public abstract TransactionManagerECommerce getTransactionManagerECommerceServicesPort()
    throws ServiceException;

  public abstract TransactionManagerECommerce getTransactionManagerECommerceServicesPort(URL paramURL)
    throws ServiceException;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerServices
 * JD-Core Version:    0.6.0
 */