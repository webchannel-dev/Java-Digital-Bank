/*    */ package com.bright.assetbank.custom.indesign.constant;
/*    */ 
/*    */ import com.bright.framework.common.constant.Settings;
/*    */ import com.bright.framework.constant.FrameworkSettings;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class InDesignSettings extends Settings
/*    */ {
/* 25 */   private static InDesignSettings c_instance = null;
/*    */ 
/*    */   protected InDesignSettings()
/*    */   {
/* 31 */     super("InDesignSettings", "InDesignSettings-standard");
/*    */   }
/*    */ 
/*    */   public static void setTestingInstance(InDesignSettings a_testingInstance)
/*    */   {
/* 38 */     c_instance = a_testingInstance;
/*    */   }
/*    */ 
/*    */   public static String getLocalWorkDirectory()
/*    */   {
/* 47 */     String maybeRelativeDirectory = getInstance().getStringSetting("work-directory");
/* 48 */     return FrameworkSettings.makeAbsolute(maybeRelativeDirectory);
/*    */   }
/*    */ 
/*    */   public static String getRemoteWorkDirectory()
/*    */   {
/* 57 */     String remoteDir = getInstance().getStringSetting("work-directory-remote");
/*    */ 
/* 61 */     if (StringUtils.isBlank(remoteDir))
/*    */     {
/* 63 */       remoteDir = getLocalWorkDirectory();
/*    */     }
/*    */ 
/* 66 */     return remoteDir;
/*    */   }
/*    */ 
/*    */   public static String getIORFilename()
/*    */   {
/* 75 */     return getInstance().getStringSetting("ior-filename");
/*    */   }
/*    */ 
/*    */   public static String getAttributeProviderClass()
/*    */   {
/* 85 */     return getInstance().getStringSetting("attribute-provider-class");
/*    */   }
/*    */ 
/*    */   private static synchronized InDesignSettings getInstance()
/*    */   {
/* 90 */     if (c_instance == null)
/*    */     {
/* 92 */       c_instance = new InDesignSettings();
/*    */     }
/* 94 */     return c_instance;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.constant.InDesignSettings
 * JD-Core Version:    0.6.0
 */