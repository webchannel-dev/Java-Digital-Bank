/*    */ package com.bright.assetbank.marketing.util;
/*    */ 
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class MarketingUtils
/*    */ {
/*    */   public static String replaceEmailVariables(String a_sBody, String a_sUsername, String a_sTitle, String a_sForename, String a_sSurname)
/*    */   {
/* 32 */     a_sBody = a_sBody.replaceAll("(?i)#title#", StringUtils.isNotEmpty(a_sTitle) ? a_sTitle : "");
/* 33 */     a_sBody = a_sBody.replaceAll("(?i)#forename#", StringUtils.isNotEmpty(a_sForename) ? a_sForename : "");
/* 34 */     a_sBody = a_sBody.replaceAll("(?i)#surname#", StringUtils.isNotEmpty(a_sSurname) ? a_sSurname : "");
/* 35 */     a_sBody = a_sBody.replaceAll("(?i)#username#", StringUtils.isNotEmpty(a_sUsername) ? a_sUsername : "");
/*    */ 
/* 37 */     return a_sBody;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.util.MarketingUtils
 * JD-Core Version:    0.6.0
 */