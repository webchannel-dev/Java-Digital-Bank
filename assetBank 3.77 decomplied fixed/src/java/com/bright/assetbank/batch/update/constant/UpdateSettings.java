/*    */ package com.bright.assetbank.batch.update.constant;
/*    */ 
/*    */ import com.bn2web.common.constant.GlobalSettings;
/*    */ 
/*    */ public class UpdateSettings extends GlobalSettings
/*    */ {
/*    */   public static int getMaxBatchUpdateResults()
/*    */   {
/* 30 */     return getInstance().getIntSetting("max-batch-update-results");
/*    */   }
/*    */ 
/*    */   public static int getMaxBulkUpdateResults()
/*    */   {
/* 35 */     return getInstance().getIntSetting("max-bulk-update-results");
/*    */   }
/*    */ 
/*    */   public static int getBulkUploadsLimit()
/*    */   {
/* 40 */     return getInstance().getIntSetting("bulk-uploads-limit");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.constant.UpdateSettings
 * JD-Core Version:    0.6.0
 */