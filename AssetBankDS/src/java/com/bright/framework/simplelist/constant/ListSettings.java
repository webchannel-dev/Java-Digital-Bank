/*    */ package com.bright.framework.simplelist.constant;
/*    */ 
/*    */ import com.bn2web.common.constant.GlobalSettings;
/*    */ 
/*    */ public class ListSettings extends GlobalSettings
/*    */ {
/*    */   public static int getNoOfExtraContentPages()
/*    */   {
/* 31 */     return getInstance().getIntSetting("no-of-extra-content-pages");
/*    */   }
/*    */ 
/*    */   public static String getExtraContentPageName(int a_iIndex)
/*    */   {
/* 36 */     return getInstance().getStringSetting("extra-content-page-name" + a_iIndex);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.simplelist.constant.ListSettings
 * JD-Core Version:    0.6.0
 */