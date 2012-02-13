/*    */ package com.bright.assetbank.synchronise.constant;
/*    */ 
/*    */ import com.bright.framework.common.constant.Settings;
/*    */ 
/*    */ public class ExternalDataSynchronisationSettings extends Settings
/*    */ {
/*    */   public ExternalDataSynchronisationSettings(String a_sSettingsFilename)
/*    */   {
/* 33 */     super(a_sSettingsFilename);
/*    */   }
/*    */ 
/*    */   public int getSynchHourOfDay()
/*    */   {
/* 38 */     return getIntSetting("synch-hour");
/*    */   }
/*    */ 
/*    */   public boolean getSynchEnabled()
/*    */   {
/* 43 */     return getBooleanSetting("synch-enabled");
/*    */   }
/*    */ 
/*    */   public String getFeedEncoding()
/*    */   {
/* 48 */     return getStringSetting("feed-encoding");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.constant.ExternalDataSynchronisationSettings
 * JD-Core Version:    0.6.0
 */