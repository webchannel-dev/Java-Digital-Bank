package com.bright.framework.user.sso;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.framework.database.bean.DBTransaction;
import com.bright.framework.user.bean.User;
import com.bright.framework.user.form.LoginForm;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public abstract interface SSOPlugin
{
  public static final String k_sSSOMode_Identify = "Identify";
  public static final String k_sSSOMode_RemoteLoginForm = "RemoteLoginForm";
  public static final String k_sSSOMode_RemoteSessionCheck = "RemoteSessionCheck";
  public static final String k_sSSOMode_HTTPRequestToken = "HTTPRequestToken";

  public abstract String getSSOMode();

  public abstract String getLoginJsp();

  public abstract void processRequest(HttpServletRequest paramHttpServletRequest, LoginForm paramLoginForm);

  public abstract String getLoginBaseUrl();

  public abstract HashMap getLoginFormForPost(HttpServletRequest paramHttpServletRequest);

  public abstract User initialSessionCheck(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Bn2Exception;

  public abstract String getSessionCheckUrl(DBTransaction paramDBTransaction, HttpServletRequest paramHttpServletRequest, LoginForm paramLoginForm)
    throws Bn2Exception;

  public abstract String getLoginUrlForRedirect(DBTransaction paramDBTransaction, HttpServletRequest paramHttpServletRequest, LoginForm paramLoginForm)
    throws Bn2Exception;

  public abstract boolean validateForm(LoginForm paramLoginForm);

  public abstract String getForwardUrl(HttpServletRequest paramHttpServletRequest);

  public abstract User getRemoteUser(HttpServletRequest paramHttpServletRequest)
    throws Bn2Exception;

  public abstract String getLogoutUrl(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);

  public abstract void doDestroyAction(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, DBTransaction paramDBTransaction);

  public abstract void doPreLogoutActions(HttpServletResponse paramHttpServletResponse);

  public abstract void resetAfterLogin(HttpServletResponse paramHttpServletResponse);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.sso.SSOPlugin
 * JD-Core Version:    0.6.0
 */