/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bright.assetbank.application.constant;

import com.bn2web.common.constant.GlobalSettings;

/**
 *
 * @author mamatha
 */
public class HandysawDSConstants extends GlobalSettings{
    public static String getHandysawDSInstallPath()
  {
     return getInstance().getStringSetting("handysaw-install-path");
   }
}
