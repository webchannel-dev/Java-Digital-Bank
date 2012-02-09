/*    */ package com.bright.framework.user.util;
/*    */ 
/*    */ public class SimplePasswordEncrypter
/*    */ {
/*    */   public static final String k_sEncryptionPassword = "534836598276491234623965329837659823";
/*    */ 
/*    */   public String decrypt(String a_sEncrypted)
/*    */   {
/* 39 */     char[] ct = a_sEncrypted.toCharArray();
/*    */ 
/* 41 */     for (int i = 0; i < ct.length; i++)
/*    */     {
/*    */       int tmp15_14 = i;
/*    */       char[] tmp15_13 = ct; tmp15_13[tmp15_14] = (char)(tmp15_13[tmp15_14] - "534836598276491234623965329837659823".charAt(i % "534836598276491234623965329837659823".length()));
/*    */     }
/*    */ 
/* 46 */     return new String(ct);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.util.SimplePasswordEncrypter
 * JD-Core Version:    0.6.0
 */