/*    */ package com.bright.assetbank.api.constant;
/*    */ 
/*    */ import com.bn2web.common.constant.GlobalSettings;
/*    */ 
/*    */ public class ApiSettings extends GlobalSettings
/*    */ {
/*    */   public static boolean getRestrictByIpAddress()
/*    */   {
/* 29 */     return getInstance().getBooleanSetting("api-restrict-by-ip");
/*    */   }
/*    */ 
/*    */   public static String[] getAllowedIpAddresses()
/*    */   {
/* 34 */     String sAllIps = getInstance().getStringSetting("api-allowed-ip-addresses");
/*    */ 
/* 37 */     return sAllIps.split(",");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.api.constant.ApiSettings
 * JD-Core Version:    0.6.0
 */