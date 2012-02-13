/*    */ package com.bright.assetbank.ecommerce.psp;
/*    */ 
/*    */ import com.bright.framework.encryption.bean.AESCBCPKCS5Padding;
/*    */ import java.io.PrintStream;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.spec.IvParameterSpec;
/*    */ import javax.crypto.spec.SecretKeySpec;
/*    */ 
/*    */ public class TestSage
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/*    */     try
/*    */     {
/* 16 */       SecretKeySpec skeySpec = new SecretKeySpec("8qkNwQzJSzlQxRU7".getBytes(), "AES");
/* 17 */       IvParameterSpec ips = new IvParameterSpec("8qkNwQzJSzlQxRU7".getBytes());
/*    */ 
/* 19 */       Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
/*    */ 
/* 21 */       cipher.init(1, skeySpec, ips);
/* 22 */       byte[] encrypted = cipher.doFinal(new String("test test test test test test test test test test test test test test test test test test").getBytes());
/*    */ 
/* 24 */       String sValue = new String(encrypted);
/* 25 */       System.out.println("ENCRYPTED 1:" + sValue);
/*    */ 
/* 27 */       cipher.init(2, skeySpec, ips);
/*    */ 
/* 29 */       byte[] decrypted = cipher.doFinal(encrypted);
/*    */ 
/* 31 */       System.out.println("DECRYPTED 1:" + new String(decrypted));
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 36 */       System.out.println(e);
/*    */     }
/*    */ 
/*    */     try
/*    */     {
/* 44 */       String sToEncrypt = "test test test test test test test test test test test test test test test test test test";
/* 45 */       AESCBCPKCS5Padding encrypter = new AESCBCPKCS5Padding("8qkNwQzJSzlQxRU7", "8qkNwQzJSzlQxRU7", true);
/* 46 */       String sValue = encrypter.doCipher(sToEncrypt);
/*    */ 
/* 48 */       System.out.println("ENCRYPTED 2:" + sValue);
/*    */ 
/* 50 */       encrypter.setDecryption();
/* 51 */       sValue = encrypter.doCipher(sValue);
/*    */ 
/* 53 */       System.out.println("DECRYPTED 2:" + sValue);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 57 */       System.out.println(e);
/* 58 */       for (StackTraceElement el : e.getStackTrace())
/*    */       {
/* 60 */         System.out.println(el.toString());
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.psp.TestSage
 * JD-Core Version:    0.6.0
 */