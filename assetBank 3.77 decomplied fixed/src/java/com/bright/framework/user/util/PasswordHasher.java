/*     */ package com.bright.framework.user.util;
/*     */ 
/*     */ import com.bright.framework.user.exception.InvalidSaltedHashException;
/*     */ import com.bright.framework.util.EncodingUtil;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Arrays;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class PasswordHasher
/*     */ {
/*     */   private static final int HASH_BYTES = 8;
/*     */   public static final int ITERATIONS = 1000;
/*     */   private SecureRandom m_rand;
/*     */   private static final String PASSWORD_DB_FILE = "testdata/passwords.properties";
/*     */ 
/*     */   public PasswordHasher()
/*     */   {
/*     */     try
/*     */     {
/*  62 */       this.m_rand = SecureRandom.getInstance("SHA1PRNG");
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/*  68 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String saltAndHash(String a_sPassword)
/*     */   {
/*  83 */     if (a_sPassword == null) return null;
/*     */ 
/*  86 */     byte[] salt = new byte[8];
/*  87 */     this.m_rand.nextBytes(salt);
/*     */ 
/*  91 */     byte[] hash = hash(salt, a_sPassword);
/*     */ 
/*  95 */     byte[] saltedHash = new byte[salt.length + hash.length];
/*  96 */     System.arraycopy(salt, 0, saltedHash, 0, salt.length);
/*  97 */     System.arraycopy(hash, 0, saltedHash, salt.length, hash.length);
/*     */ 
/* 101 */     return EncodingUtil.base64Encode(saltedHash);
/*     */   }
/*     */ 
/*     */   public boolean checkPassword(String a_sSaltedHash, String a_sCandidatePassword)
/*     */     throws InvalidSaltedHashException
/*     */   {
/* 115 */     byte[] saltedHash = EncodingUtil.base64Decode(a_sSaltedHash);
/*     */ 
/* 118 */     byte[] salt = new byte[8];
/* 119 */     int hashLength = saltedHash.length - 8;
/* 120 */     if (hashLength <= 0)
/*     */     {
/* 122 */       throw new InvalidSaltedHashException(a_sSaltedHash, "Salted hash was too short after base64 decoding");
/*     */     }
/*     */ 
/* 125 */     byte[] hash = new byte[hashLength];
/* 126 */     System.arraycopy(saltedHash, 0, salt, 0, salt.length);
/* 127 */     System.arraycopy(saltedHash, salt.length, hash, 0, hash.length);
/*     */ 
/* 132 */     byte[] candidateHash = hash(salt, a_sCandidatePassword);
/*     */ 
/* 136 */     return Arrays.equals(candidateHash, hash);
/*     */   }
/*     */ 
/*     */   private byte[] hash(byte[] a_salt, String a_sPassword)
/*     */   {
/* 151 */     MessageDigest md = getSHA256MessageDigestInstance();
/*     */ 
/* 154 */     md.update(a_salt);
/* 155 */     md.update(utf8BytesFromString(a_sPassword));
/*     */ 
/* 157 */     byte[] hash = md.digest();
/*     */ 
/* 160 */     for (int i = 1; i < 1000; i++)
/*     */     {
/* 162 */       md.reset();
/* 163 */       hash = md.digest(hash);
/*     */     }
/* 165 */     return hash;
/*     */   }
/*     */ 
/*     */   private static MessageDigest getSHA256MessageDigestInstance()
/*     */   {
/*     */     MessageDigest md;
/*     */     try
/*     */     {
/* 176 */       md = MessageDigest.getInstance("SHA-256");
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/* 182 */       throw new RuntimeException(e);
/*     */     }
/* 184 */     return md;
/*     */   }
/*     */ 
/*     */   private byte[] utf8BytesFromString(String a_sPassword)
/*     */   {
/*     */     byte[] passwordBytes;
/*     */     try {
/* 192 */       passwordBytes = a_sPassword.getBytes("UTF-8");
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/* 198 */       throw new RuntimeException(e);
/*     */     }
/* 200 */     return passwordBytes;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws IOException, InvalidSaltedHashException
/*     */   {
/* 217 */     String operation = args[0];
/* 218 */     String username = args[1];
/* 219 */     String password = args[2];
/*     */ 
/* 221 */     PasswordHasher sh = new PasswordHasher();
/*     */ 
/* 223 */     InputStream is = new FileInputStream("testdata/passwords.properties");
/* 224 */     Properties pwdb = new Properties();
/* 225 */     pwdb.load(is);
/* 226 */     is.close();
/*     */ 
/* 228 */     if (operation.equals("set"))
/*     */     {
/* 230 */       String saltedHash = sh.saltAndHash(password);
/* 231 */       pwdb.setProperty(username, saltedHash);
/*     */ 
/* 233 */       OutputStream os = new FileOutputStream("testdata/passwords.properties");
/* 234 */       pwdb.store(os, "Passwords");
/* 235 */       os.close();
/*     */     }
/* 237 */     else if (operation.equals("check"))
/*     */     {
/* 239 */       String sSaltedHash = pwdb.getProperty(username);
/* 240 */       boolean matches = sh.checkPassword(sSaltedHash, password);
/* 241 */       System.out.println(matches);
/*     */     }
/*     */     else
/*     */     {
/* 245 */       System.err.println("Unknown operation: " + operation);
/* 246 */       System.exit(1);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.util.PasswordHasher
 * JD-Core Version:    0.6.0
 */