/*    */ package com.bright.framework.message.constant;
/*    */ 
/*    */ import com.bn2web.common.constant.GlobalSettings;
/*    */ 
/*    */ public class MessageSettings extends GlobalSettings
/*    */   implements MessageConstants
/*    */ {
/*    */   public static String getMessagePropertiesPath()
/*    */   {
/* 30 */     return getInstance().getStringSetting("messagePropertiesPath");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.message.constant.MessageSettings
 * JD-Core Version:    0.6.0
 */