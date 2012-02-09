package com.bright.assetbank.ecommerce.psp;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.assetbank.ecommerce.bean.PspPaymentReturn;
import com.bright.assetbank.ecommerce.bean.Purchase;
import com.bright.framework.database.bean.DBTransaction;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public abstract interface PSPPlugin
{
  public abstract String createSignatureOut(Purchase paramPurchase, String paramString);

  public abstract String createSignatureCallback(Purchase paramPurchase, String paramString, HttpServletRequest paramHttpServletRequest);

  public abstract HashMap<String, String> createPurchaseForm(Purchase paramPurchase, String paramString);

  public abstract String getPSPUrl(Purchase paramPurchase, String paramString);

  public abstract String getKeyFromReturnRequest(HttpServletRequest paramHttpServletRequest);

  public abstract PspPaymentReturn processCallback(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, DBTransaction paramDBTransaction)
    throws Bn2Exception;

  public abstract boolean requiresRedirectFromCallback();

  public abstract String generateSuccessRedirect(PspPaymentReturn paramPspPaymentReturn);

  public abstract String generateFailureRedirect(PspPaymentReturn paramPspPaymentReturn);

  public abstract boolean registerSuccessOnCallback();

  public abstract boolean registerSuccessOnReturn();

  public abstract boolean processReturn(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, DBTransaction paramDBTransaction);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.psp.PSPPlugin
 * JD-Core Version:    0.6.0
 */