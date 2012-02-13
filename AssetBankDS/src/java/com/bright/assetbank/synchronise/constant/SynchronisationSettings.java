/*    */ package com.bright.assetbank.synchronise.constant;
/*    */ 
/*    */ import com.bn2web.common.constant.GlobalSettings;
/*    */ 
/*    */ public class SynchronisationSettings extends GlobalSettings
/*    */ {
/*    */   public static String getSynchTasksPackage()
/*    */   {
/* 30 */     return getInstance().getStringSetting("synch-tasks-package");
/*    */   }
/*    */ 
/*    */   public static String getSynchTaskNames()
/*    */   {
/* 35 */     return getInstance().getStringSetting("synch-task-names");
/*    */   }
/*    */ 
/*    */   public static String getExportFileStem()
/*    */   {
/* 40 */     return getInstance().getStringSetting("export-file-stem");
/*    */   }
/*    */ 
/*    */   public static boolean getIncludeDateInExportFilename()
/*    */   {
/* 45 */     return getInstance().getBooleanSetting("export-file-name-dt");
/*    */   }
/*    */ 
/*    */   public static int getPublishingBatchSize()
/*    */   {
/* 50 */     return getInstance().getIntSetting("publishing-batch-size");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.constant.SynchronisationSettings
 * JD-Core Version:    0.6.0
 */