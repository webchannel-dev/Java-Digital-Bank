package com.bright.assetbank.ecommerce.service;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.assetbank.ecommerce.bean.Purchase;
import com.bright.framework.database.bean.DBTransaction;
import com.bright.framework.language.bean.Language;

public abstract interface PaymentCallbackProcessor
{
  public abstract void registerPaymentSuccess(DBTransaction paramDBTransaction, Purchase paramPurchase, Language paramLanguage)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.service.PaymentCallbackProcessor
 * JD-Core Version:    0.6.0
 */