/*    */ package com.bright.framework.util;
/*    */ 
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import org.apache.commons.codec.binary.Base64;
/*    */ 
/*    */ public class EncodingUtil
/*    */ {
/*    */   public static String base64Encode(byte[] a_bytes)
/*    */   {
/* 35 */     byte[] base64Bytes = Base64.encodeBase64(a_bytes);
/*    */     try
/*    */     {
/* 40 */       return new String(base64Bytes, "US-ASCII");
/*    */     }
/*    */     catch (UnsupportedEncodingException e)
/*    */     {
                throw new RuntimeException(e);
/*    */     }
/*    */ 
/* 46 */    // throw new RuntimeException(e);
/*    */   }
/*    */ 
/*    */   public static byte[] base64Decode(String a_sBase64)
/*    */   {
/*    */     byte[] base64Bytes;
/*    */     try
/*    */     {
/* 62 */       base64Bytes = a_sBase64.getBytes("US-ASCII");
/*    */     }
/*    */     catch (UnsupportedEncodingException e)
/*    */     {
/* 68 */       throw new RuntimeException(e);
/*    */     }
/*    */ 
/* 72 */     return Base64.decodeBase64(base64Bytes);
/*    */   }
/*    */ 
/*    */   public static String padBase64String(String value)
/*    */   {
/* 81 */     int mod = value.length() % 4;
/* 82 */     if (mod <= 0)
/*    */     {
/* 84 */       return value;
/*    */     }
/*    */ 
/* 87 */     int numEqs = 4 - mod;
/*    */ 
/* 89 */     for (int i = 0; i < numEqs; i++)
/*    */     {
/* 91 */       value = value + "=";
/*    */     }
/*    */ 
/* 94 */     return value;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.EncodingUtil
 * JD-Core Version:    0.6.0
 */