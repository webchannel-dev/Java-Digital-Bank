/*    */ package com.bright.framework.common.constant;
/*    */ 
/*    */ import com.bn2web.common.constant.GlobalSettings;
/*    */ 
/*    */ public class CommonSettings extends GlobalSettings
/*    */ {
/*    */   public static String getFormDateFormat()
/*    */   {
/* 31 */     return getInstance().getStringSetting("form-date-format");
/*    */   }
/*    */ 
/*    */   public static String getDisplayDateFormat()
/*    */   {
/* 36 */     return getInstance().getStringSetting("display-date-format");
/*    */   }
/*    */ 
/*    */   public static String getSqlDateFormat()
/*    */   {
/* 41 */     return getInstance().getStringSetting("sql-date-format");
/*    */   }
/*    */ 
/*    */   public static String getFormTimeFormat()
/*    */   {
/* 46 */     return getInstance().getStringSetting("form-time-format");
/*    */   }
/*    */ 
/*    */   public static String getDisplayDateTimeFormat()
/*    */   {
/* 51 */     return getInstance().getStringSetting("display-datetime-format");
/*    */   }
/*    */ 
/*    */   public static String getSqlTimeFormat()
/*    */   {
/* 56 */     return getInstance().getStringSetting("sql-time-format");
/*    */   }
/*    */ 
/*    */   public static String getCountry()
/*    */   {
/* 61 */     return getInstance().getStringSetting("locale-country");
/*    */   }
/*    */ 
/*    */   public static String getLanguage()
/*    */   {
/* 66 */     return getInstance().getStringSetting("locale-language");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.constant.CommonSettings
 * JD-Core Version:    0.6.0
 */