package com.bright.framework.user.service;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.assetbank.user.bean.Group;
import com.bright.framework.database.bean.DBTransaction;
import com.bright.framework.user.bean.User;
import java.util.Vector;

public abstract interface LocalUserManager
{
  public abstract Vector getAllGroups()
    throws Bn2Exception;

  public abstract Vector getMappedGroups()
    throws Bn2Exception;

  public abstract Vector getUserIdsOfRemoteUsers(DBTransaction paramDBTransaction, int paramInt, String paramString)
    throws Bn2Exception;

  public abstract User getUser(DBTransaction paramDBTransaction, long paramLong)
    throws Bn2Exception;

  public abstract User createUser();

  public abstract User saveUser(DBTransaction paramDBTransaction, User paramUser)
    throws Bn2Exception;

  public abstract void addUserToGroups(DBTransaction paramDBTransaction, long paramLong, Vector<Long> paramVector)
    throws Bn2Exception;

  public abstract long getUserIdForRemoteUsername(DBTransaction paramDBTransaction, String paramString)
    throws Bn2Exception;

  public abstract long getUserIdForLocalUsername(DBTransaction paramDBTransaction, String paramString)
    throws Bn2Exception;

  public abstract void addUserToLoggedInUsersGroup(DBTransaction paramDBTransaction, long paramLong)
    throws Bn2Exception;

  public abstract Vector<Group> getGroups(Vector<Long> paramVector)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.service.LocalUserManager
 * JD-Core Version:    0.6.0
 */