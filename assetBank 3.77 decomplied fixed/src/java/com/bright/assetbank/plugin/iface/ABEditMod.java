package com.bright.assetbank.plugin.iface;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.assetbank.plugin.bean.ABExtensibleBean;
import com.bright.assetbank.user.bean.ABUserProfile;
import com.bright.framework.database.bean.DBTransaction;
import com.bright.framework.struts.Bn2ExtensibleForm;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;

public abstract interface ABEditMod
{
  public abstract void populateForm(DBTransaction paramDBTransaction, Object paramObject, Serializable paramSerializable, HttpServletRequest paramHttpServletRequest, Bn2ExtensibleForm paramBn2ExtensibleForm)
    throws Bn2Exception;

  public abstract void populateViewEditRequest(DBTransaction paramDBTransaction, HttpServletRequest paramHttpServletRequest, Bn2ExtensibleForm paramBn2ExtensibleForm)
    throws Bn2Exception;

  public abstract String getInclude(String paramString);

  public abstract void validate(DBTransaction paramDBTransaction, ABUserProfile paramABUserProfile, Object paramObject, Bn2ExtensibleForm paramBn2ExtensibleForm)
    throws Bn2Exception;

  public abstract Serializable extractDataFromForm(DBTransaction paramDBTransaction, ABExtensibleBean paramABExtensibleBean, Bn2ExtensibleForm paramBn2ExtensibleForm)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.iface.ABEditMod
 * JD-Core Version:    0.6.0
 */