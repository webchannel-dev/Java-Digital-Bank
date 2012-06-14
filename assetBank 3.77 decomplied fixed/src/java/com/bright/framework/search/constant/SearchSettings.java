/*    */ package com.bright.framework.search.constant;
/*    */ 
/*    */ import com.bn2web.common.constant.GlobalSettings;
/*    */ 
/*    */ public class SearchSettings extends GlobalSettings
/*    */ {
/*    */   public static boolean getUseStemming()
/*    */   {
/* 29 */     return getInstance().getBooleanSetting("use-stemming");
/*    */   }
/*    */ 
/*    */   public static int getSearchMaxBooleanClauses()
/*    */   {
/* 34 */     return getInstance().getIntSetting("search-max-boolean-clauses");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.constant.SearchSettings
 * JD-Core Version:    0.6.0
 */