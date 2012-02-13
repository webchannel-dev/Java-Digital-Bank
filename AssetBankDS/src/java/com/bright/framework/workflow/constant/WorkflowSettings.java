/*    */ package com.bright.framework.workflow.constant;
/*    */ 
/*    */ import com.bn2web.common.constant.GlobalSettings;
/*    */ 
/*    */ public class WorkflowSettings extends GlobalSettings
/*    */ {
/*    */   public static String getXmlFilePath()
/*    */   {
/* 31 */     return getInstance().getStringSetting("workflow-xmlFilePath");
/*    */   }
/*    */ 
/*    */   public static int getMaxAssetsOnList()
/*    */   {
/* 36 */     return getInstance().getIntSetting("workflow-max-assets-on-list");
/*    */   }
/*    */ 
/*    */   public static String getValidationMessagesXmlFilePath()
/*    */   {
/* 41 */     return getInstance().getStringSetting("validation-messages-xmlFilePath");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.workflow.constant.WorkflowSettings
 * JD-Core Version:    0.6.0
 */