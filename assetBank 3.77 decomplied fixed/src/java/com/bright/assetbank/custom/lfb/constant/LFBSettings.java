/*    */ package com.bright.assetbank.custom.lfb.constant;
/*    */ 
/*    */ import com.bright.framework.common.constant.Settings;
/*    */ 
/*    */ public class LFBSettings extends Settings
/*    */ {
/* 23 */   private static LFBSettings c_instance = null;
/*    */ 
/*    */   public LFBSettings()
/*    */   {
/* 27 */     super("LFBSettings");
/*    */   }
/*    */ 
/*    */   public static String getIncidentLookupWebserviceUrl()
/*    */   {
/* 32 */     return getInstance().getStringSetting("incident-lookup-webservice-url");
/*    */   }
/*    */ 
/*    */   public static String getIncidentLookupAttributeMappings()
/*    */   {
/* 37 */     return getInstance().getStringSetting("incident-lookup-attribute-mappings");
/*    */   }
/*    */ 
/*    */   private static synchronized LFBSettings getInstance()
/*    */   {
/* 42 */     if (c_instance == null)
/*    */     {
/* 44 */       c_instance = new LFBSettings();
/*    */     }
/* 46 */     return c_instance;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.lfb.constant.LFBSettings
 * JD-Core Version:    0.6.0
 */