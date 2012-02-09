/*    */ package com.bright.framework.user.util;
/*    */ 
/*    */ import org.apache.commons.lang.RandomStringUtils;
/*    */ 
/*    */ public class PasswordUtil
/*    */ {
/*    */   public static String generateRandomPassword()
/*    */   {
/* 31 */     return RandomStringUtils.randomAlphanumeric(8);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.util.PasswordUtil
 * JD-Core Version:    0.6.0
 */