/*     */ package com.bright.framework.util;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import java.lang.reflect.Array;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class RC4CipherUtil
/*     */ {
/*  32 */   static int[] cipherBox = new int[256];
/*  33 */   static int[] cipherKeyArray = new int[256];
/*  34 */   static String rc4Key = "&h02 &h2d &h48 &h79 &ha3 &hc6 &hf0";
/*     */ 
/*     */   public static String encryptToHex(String a_sSource)
/*     */   {
/*  56 */     String sCipheredText = null;
/*  57 */     String sResult = "";
/*  58 */     char asciiChar = '\000';
/*     */ 
/*  61 */     sCipheredText = doEncryption(a_sSource);
/*     */ 
/*  64 */     for (int i = 0; i < sCipheredText.length(); i++)
/*     */     {
/*  66 */       asciiChar = sCipheredText.charAt(i);
/*  67 */       sResult = sResult + Integer.toString(((byte)asciiChar & 0xFF) + 256, 16).substring(1);
/*     */     }
/*     */ 
/*  70 */     return sResult;
/*     */   }
/*     */ 
/*     */   public static String decryptFromHex(String sSource)
/*     */     throws NumberFormatException
/*     */   {
/*  88 */     String sResult = "";
/*     */ 
/*  90 */     if (sSource == null)
/*     */     {
/*  92 */       return null;
/*     */     }
/*     */ 
/*  96 */     for (int i = 0; i < sSource.length() - 1; i += 2)
/*     */     {
/*  98 */       String sHexVal = sSource.substring(i, i + 2);
/*  99 */       sResult = sResult + (char)Integer.parseInt(sHexVal, 16);
/*     */     }
/*     */ 
/* 103 */     sResult = doEncryption(sResult);
/*     */ 
/* 105 */     return sResult;
/*     */   }
/*     */ 
/*     */   public static String doEncryption(String cipherString)
/*     */   {
/* 121 */     String rc4Data = cipherString;
/* 122 */     String rc4DataCiphered = "";
/* 123 */     String stringReturn = "";
/*     */     try
/*     */     {
/* 127 */       rc4DataCiphered = doCipher(rc4Data);
/* 128 */       stringReturn = rc4DataCiphered;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 132 */       GlobalApplication.getInstance().getLogger().error(e);
/*     */     }
/*     */ 
/* 135 */     return stringReturn;
/*     */   }
/*     */ 
/*     */   private static String doCipher(String unencodedText)
/*     */   {
/* 151 */     int z = 0;
/* 152 */     int t = 0;
/* 153 */     int i = 0;
/* 154 */     int cipherBy = 0;
/* 155 */     int tempInt = 0;
/* 156 */     String cipher = "";
/*     */ 
/* 159 */     int iCipherBoxLength = Array.getLength(cipherBox);
/* 160 */     int[] myCipherBox = new int[iCipherBoxLength];
/*     */ 
/* 162 */     System.arraycopy(cipherBox, 0, myCipherBox, 0, iCipherBoxLength);
/*     */ 
/* 164 */     for (int a = 0; a < unencodedText.length(); a++)
/*     */     {
/* 166 */       i = (i + 1) % 255;
/* 167 */       t = (t + myCipherBox[i]) % 255;
/* 168 */       tempInt = myCipherBox[i];
/* 169 */       myCipherBox[i] = myCipherBox[t];
/* 170 */       myCipherBox[t] = tempInt;
/*     */ 
/* 172 */       z = myCipherBox[((myCipherBox[i] + myCipherBox[t]) % 255)];
/*     */ 
/* 175 */       char cipherText = unencodedText.charAt(a);
/*     */ 
/* 178 */       cipherBy = cipherText ^ z;
/*     */ 
/* 181 */       cipher = cipher + (char)cipherBy;
/*     */     }
/*     */ 
/* 185 */     return cipher;
/*     */   }
/*     */ 
/*     */   private static void doRC4MatrixSeed(String thisKey)
/*     */   {
/* 200 */     int keyLength = 0;
/*     */ 
/* 203 */     int asciiVal = 0;
/*     */ 
/* 205 */     keyLength = thisKey.length();
/*     */ 
/* 207 */     for (int a = 0; a < 255; a++)
/*     */     {
/* 210 */       char asciiChar = thisKey.charAt(a % keyLength);
/* 211 */       asciiVal = asciiChar;
/* 212 */       cipherKeyArray[a] = asciiVal;
/* 213 */       cipherBox[a] = a;
/*     */     }
/*     */ 
/* 216 */     int b = 0;
/*     */ 
/* 218 */     for (int a = 0; a < 255; a++)
/*     */     {
/* 220 */       b = (b + cipherBox[a] + cipherKeyArray[a]) % 255;
/* 221 */       int dataSwap = cipherBox[a];
/* 222 */       cipherBox[a] = cipherBox[b];
/* 223 */       cipherBox[b] = dataSwap;
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  39 */     doRC4MatrixSeed(rc4Key);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.RC4CipherUtil
 * JD-Core Version:    0.6.0
 */