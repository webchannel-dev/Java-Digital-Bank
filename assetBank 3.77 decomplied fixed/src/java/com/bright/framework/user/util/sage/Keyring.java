/*     */ package com.bright.framework.user.util.sage;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.RSAPublicKeySpec;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ 
/*     */ public class Keyring
/*     */ {
/*     */   private static KeyFactory RSA_KEY_FACTORY;
/*     */   public static Keyring instance;
/*  59 */   private Map publicKeys = new HashMap();
/*  60 */   private Map privateKeys = new HashMap();
/*     */ 
/*     */   private Keyring()
/*     */   {
/*  56 */     init();
/*     */   }
/*     */ 
/*     */   private void init()
/*     */   {
/*  65 */     String sageEncKey = "BgIAAAAkAABSU0ExAAQAAAEAAQBD3JyCuNuSh3Z04yxV5cW/WxCzYR6ifOutpyFfVpaOs7mV+5c9dH99wKqGMlj7/QdHlTYesEjTjVY53SrHIudwceGxvkjYdLF88CdXNLK9nQpYcBUOvM+2OaX4a9vyVunTRis5E62CBeVqKK7Xc6GJu0NLjjR8RS/F3U+h5exi6Q==";
/*  66 */     addSagePublicKey("signin.sage.com", sageEncKey);
/*     */ 
/*  69 */     String sageTestEncKey = "BgIAAAAkAABSU0ExAAQAAAEAAQAJulL0UW7ZP0f3ArkqtW0sKEnFaaA+qwKj9gqZoAoWhl+krobAYJFzpXaw+qtJ73pzVsmkWRV5ucvyDD9Iy+Mxnm8hpOCdLZF4iAcfngcKCwbh/CU4W3j7wRKkTUTsDdY9R5FA+0MpVpD5KxQE/X43lcX0DPpuyJZhfmGnxgX20g==";
/*  70 */     addSagePublicKey("signin.qa.sage.com", sageTestEncKey);
/*     */   }
/*     */ 
/*     */   public SageKey getPublicKeyForSite(String site)
/*     */   {
/*  75 */     return (SageKey)this.publicKeys.get(site);
/*     */   }
/*     */ 
/*     */   public SageKey getPrivateKeyForSite(String site) {
/*  79 */     return (SageKey)this.privateKeys.get(site);
/*     */   }
/*     */ 
/*     */   private void addSagePublicKey(String site, String encKey)
/*     */   {
/*  86 */     byte[] decKeyLEB = decodeBase64(encKey);
/*  87 */     byte[] exponentLEB = slice(decKeyLEB, 16, 4);
/*  88 */     byte[] exponentBEB = reverseArray(exponentLEB);
/*     */ 
/*  90 */     byte[] modulusLEB = slice(decKeyLEB, 20, 128);
/*  91 */     byte[] modulusBEB = reverseArray(modulusLEB);
/*     */     try
/*     */     {
/*  94 */       RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(bigIntegerFromUnsignedBEB(modulusBEB), bigIntegerFromUnsignedBEB(exponentBEB));
/*  95 */       PublicKey pubKey = RSA_KEY_FACTORY.generatePublic(pubKeySpec);
/*  96 */       SageKey sageKey = new SageKey(pubKey);
/*  97 */       this.publicKeys.put(site, sageKey);
/*     */     }
/*     */     catch (InvalidKeySpecException e) {
/* 100 */       String sMsg = "Keyring.addSagePublicKey: InvalidKeySpecException: " + site + ", " + e.getMessage();
/* 101 */       System.out.println(sMsg);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static BigInteger bigIntegerFromUnsignedBEB(byte[] ubeb)
/*     */   {
/* 108 */     byte[] sbeb = new byte[ubeb.length + 1];
/* 109 */     System.arraycopy(ubeb, 0, sbeb, 1, ubeb.length);
/*     */ 
/* 111 */     return new BigInteger(sbeb);
/*     */   }
/*     */   private static byte[] decodeBase64(String encString) {
/*     */     byte[] encStringB;
/*     */     try {
/* 117 */       encStringB = encString.getBytes("us-ascii");
/*     */     } catch (UnsupportedEncodingException e) {
/* 119 */       throw new RuntimeException(e);
/*     */     }
/* 121 */     byte[] decB = Base64.decodeBase64(encStringB);
/* 122 */     return decB;
/*     */   }
/*     */ 
/*     */   private static byte[] slice(byte[] in, int start, int length) {
/* 126 */     byte[] out = new byte[length];
/* 127 */     System.arraycopy(in, start, out, 0, length);
/* 128 */     return out;
/*     */   }
/*     */ 
/*     */   private static byte[] reverseArray(byte[] in) {
/* 132 */     byte[] out = new byte[in.length];
/* 133 */     int l = in.length;
/* 134 */     for (int n = 0; n < l; n++) {
/* 135 */       out[n] = in[(l - n - 1)];
/*     */     }
/* 137 */     return out;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  45 */       RSA_KEY_FACTORY = KeyFactory.getInstance("RSA");
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/*  49 */       String sMsg = "RSA security provider could not be loaded: NoSuchAlgorithmException: " + e.getMessage();
/*  50 */       System.out.println(sMsg);
/*     */     }
/*     */ 
/*  54 */     instance = new Keyring();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.util.sage.Keyring
 * JD-Core Version:    0.6.0
 */