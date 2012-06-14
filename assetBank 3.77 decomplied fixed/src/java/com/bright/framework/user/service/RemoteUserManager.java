package com.bright.framework.user.service;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.framework.database.bean.DBTransaction;
import com.bright.framework.database.service.DBTransactionManager;
import com.bright.framework.user.bean.User;
import com.bright.framework.user.exception.AuthenticationErrorException;
import com.bright.framework.user.exception.AuthenticationException;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface RemoteUserManager
{
  public abstract void authenticateUser(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString1, String paramString2, int paramInt)
    throws AuthenticationException, AuthenticationErrorException;

  public abstract boolean populateUser(HttpServletRequest paramHttpServletRequest, User paramUser)
    throws Bn2Exception;

  public abstract void updateUserInGroups(DBTransaction paramDBTransaction, LocalUserManager paramLocalUserManager, Vector paramVector, User paramUser, String[] paramArrayOfString)
    throws Bn2Exception;

  public abstract void synchronise(LocalUserManager paramLocalUserManager, DBTransactionManager paramDBTransactionManager)
    throws Bn2Exception;

  public abstract void syncRemoteUserGroups(DBTransaction paramDBTransaction, LocalUserManager paramLocalUserManager, User paramUser)
    throws Bn2Exception;

  public abstract void synchroniseUser(LocalUserManager paramLocalUserManager, DBTransaction paramDBTransaction, String paramString)
    throws Bn2Exception;

  public abstract String getRemoteUserIdentifier(HttpServletRequest paramHttpServletRequest)
    throws Bn2Exception;

  public abstract boolean isUserAuthenticated(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Bn2Exception;

  public abstract void logOffUser(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.service.RemoteUserManager
 * JD-Core Version:    0.6.0
 */