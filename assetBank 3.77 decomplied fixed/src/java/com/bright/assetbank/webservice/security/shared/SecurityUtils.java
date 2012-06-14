/*    */ package com.bright.assetbank.webservice.security.shared;
/*    */ 
/*    */ public class SecurityUtils
/*    */ {
/*    */   public static String getHexString(byte[] input)
/*    */   {
/* 69 */     StringBuilder hexString = new StringBuilder();
/*    */ 
/* 71 */     for (int i = 0; i < input.length; i++)
/*    */     {
/* 75 */       hexString.append(Integer.toHexString(input[i] >>> 4 & 0xF));
/*    */ 
/* 77 */       hexString.append(Integer.toHexString(0xF & input[i]));
/*    */     }
/*    */ 
/* 81 */     return hexString.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.webservice.security.shared.SecurityUtils
 * JD-Core Version:    0.6.0
 */