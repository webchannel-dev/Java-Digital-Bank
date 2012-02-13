/*     */ package com.bright.framework.user.util.sage;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.Key;
/*     */ import javax.crypto.Cipher;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ 
/*     */ public class SageKey
/*     */ {
/*     */   private Key key;
/*     */ 
/*     */   public SageKey(Key key)
/*     */   {
/*  39 */     this.key = key;
/*     */   }
/*     */   private static byte[] decodeBase64(String encString) {
/*     */     byte[] encStringB;
/*     */     try {
/*  45 */       encStringB = encString.getBytes("us-ascii");
/*     */     } catch (UnsupportedEncodingException e) {
/*  47 */       throw new RuntimeException(e);
/*     */     }
/*  49 */     byte[] decB = Base64.decodeBase64(encStringB);
/*  50 */     return decB;
/*     */   }
/*  54 */   private static String encodeBase64(byte[] decBytes) { byte[] encBytes = Base64.encodeBase64(decBytes);
/*     */     String encString;
/*     */     try {
/*  57 */       encString = new String(encBytes, "us-ascii");
/*     */     } catch (UnsupportedEncodingException e) {
/*  59 */       throw new RuntimeException(e);
/*     */     }
/*  61 */     return encString; }
/*     */ 
/*     */   private static byte[] slice(byte[] in, int start, int length)
/*     */   {
/*  65 */     byte[] out = new byte[length];
/*  66 */     System.arraycopy(in, start, out, 0, length);
/*  67 */     return out;
/*     */   }
/*     */ 
/*     */   public byte[] decryptHash(String sigdataS)
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     byte[] sigdata = decodeBase64(sigdataS);
/*  74 */     return decryptHash(sigdata);
/*     */   }
/*     */ 
/*     */   public byte[] decryptHash(byte[] sigdata) throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/*  81 */       Cipher RSA = Cipher.getInstance("RSA");
/*     */ 
/*  83 */       RSA.init(2, this.key);
/*     */ 
/*  85 */       byte[] sigdataRev = reverseArray(sigdata);
/*     */ 
/*  87 */       byte[] decDataRev = RSA.doFinal(sigdataRev);
/*     */ 
/*  89 */       byte[] decData = reverseArray(decDataRev);
/*  90 */       byte[] md5BEB = slice(decData, 0, 16);
/*  91 */       byte[] md5LEB = reverseArray(md5BEB);
/*     */ 
/*  93 */       return md5LEB;
/*     */     } catch (Exception e) {
/*     */     
/*  96 */     throw new Bn2Exception("SageKey.decryptHash : Exception:" + e.getMessage(), e);}
/*     */   }
/*     */ 
/*     */   private static byte[] reverseArray(byte[] in)
/*     */   {
/* 108 */     byte[] out = new byte[in.length];
/* 109 */     int l = in.length;
/* 110 */     for (int n = 0; n < l; n++) {
/* 111 */       out[n] = in[(l - n - 1)];
/*     */     }
/* 113 */     return out;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.util.sage.SageKey
 * JD-Core Version:    0.6.0
 */