/*     */ package org.apache.commons.httpclient.auth;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.httpclient.crypto.DESEngine;
/*     */ import org.apache.commons.httpclient.crypto.MD4Digest;
/*     */ import org.apache.commons.httpclient.crypto.MD5Digest;
/*     */ 
/*     */ public class NTLMAuth
/*     */ {
/*     */   public static byte[] getNTLMChallenge(String password, byte[] nonce)
/*     */     throws UnsupportedEncodingException
/*     */   {
/*  40 */     return encryptNonce(ntHash(password), nonce);
/*     */   }
/*     */ 
/*     */   public static byte[] getLMChallenge(String pwd, byte[] nonce) throws UnsupportedEncodingException {
/*  44 */     byte[] password = convertPassword(pwd);
/*     */ 
/*  46 */     DESEngine d1 = new DESEngine(true, makeDESkey(password, 0));
/*  47 */     DESEngine d2 = new DESEngine(true, makeDESkey(password, 7));
/*  48 */     byte[] encrypted = new byte[21];
/*  49 */     Arrays.fill(encrypted, (byte)0);
/*     */ 
/*  51 */     d1.processBlock(nonce, 0, encrypted, 0);
/*  52 */     d2.processBlock(nonce, 0, encrypted, 8);
/*     */ 
/*  54 */     return encryptNonce(encrypted, nonce);
/*     */   }
/*     */ 
/*     */   public static byte[] getNTLMv2Challenge(String domain, String user, String password, byte[] nonce, byte[] targetInfo, byte[] clientNonce)
/*     */     throws UnsupportedEncodingException
/*     */   {
/*  63 */     return getNTLMv2Challenge(domain, user, password, nonce, targetInfo, clientNonce, System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */   public static byte[] getNTLMv2Challenge(String domain, String user, String password, byte[] nonce, byte[] targetInfo, byte[] clientNonce, byte[] timestamp)
/*     */     throws UnsupportedEncodingException
/*     */   {
/*  70 */     byte[] hash = ntv2Hash(domain, user, password);
/*  71 */     byte[] blob = createBlob(targetInfo, clientNonce, timestamp);
/*  72 */     return lmv2Response(hash, blob, nonce);
/*     */   }
/*     */ 
/*     */   public static byte[] getNTLMv2Challenge(String domain, String user, String password, byte[] nonce, byte[] targetInfo, byte[] clientNonce, long now)
/*     */     throws UnsupportedEncodingException
/*     */   {
/*  78 */     return getNTLMv2Challenge(domain, user, password, nonce, targetInfo, clientNonce, createTimestamp(now));
/*     */   }
/*     */ 
/*     */   public static byte[] getLMv2Challenge(String domain, String user, String password, byte[] nonce, byte[] clientNonce)
/*     */     throws UnsupportedEncodingException
/*     */   {
/*  85 */     byte[] hash = ntv2Hash(domain, user, password);
/*  86 */     return lmv2Response(hash, clientNonce, nonce);
/*     */   }
/*     */ 
/*     */   private static byte[] ntv2Hash(String domain, String user, String password)
/*     */     throws UnsupportedEncodingException
/*     */   {
/*  95 */     byte[] hash = ntHash(password);
/*  96 */     String identity = user.toUpperCase() + domain.toUpperCase();
/*  97 */     byte[] identityBytes = identity.getBytes("UnicodeLittleUnmarked");
/*     */ 
/*  99 */     return hmacMD5(identityBytes, hash);
/*     */   }
/*     */ 
/*     */   private static byte[] lmv2Response(byte[] hash, byte[] clientData, byte[] challenge)
/*     */   {
/* 116 */     byte[] data = new byte[challenge.length + clientData.length];
/* 117 */     System.arraycopy(challenge, 0, data, 0, challenge.length);
/* 118 */     System.arraycopy(clientData, 0, data, challenge.length, clientData.length);
/*     */ 
/* 120 */     byte[] mac = hmacMD5(data, hash);
/* 121 */     byte[] lmv2Response = new byte[mac.length + clientData.length];
/* 122 */     System.arraycopy(mac, 0, lmv2Response, 0, mac.length);
/* 123 */     System.arraycopy(clientData, 0, lmv2Response, mac.length, clientData.length);
/* 124 */     return lmv2Response;
/*     */   }
/*     */ 
/*     */   private static byte[] hmacMD5(byte[] data, byte[] key)
/*     */   {
/* 138 */     byte[] ipad = new byte[64];
/* 139 */     byte[] opad = new byte[64];
/* 140 */     for (int i = 0; i < 64; i++) {
/* 141 */       ipad[i] = 54;
/* 142 */       opad[i] = 92;
/*     */     }
/* 144 */     for (int i = key.length - 1; i >= 0; i--)
/*     */     {
/*     */       int tmp52_50 = i;
/*     */       byte[] tmp52_49 = ipad; tmp52_49[tmp52_50] = (byte)(tmp52_49[tmp52_50] ^ key[i]);
/*     */       int tmp64_62 = i;
/*     */       byte[] tmp64_61 = opad; tmp64_61[tmp64_62] = (byte)(tmp64_61[tmp64_62] ^ key[i]);
/*     */     }
/* 148 */     byte[] content = new byte[data.length + 64];
/* 149 */     System.arraycopy(ipad, 0, content, 0, 64);
/* 150 */     System.arraycopy(data, 0, content, 64, data.length);
/* 151 */     data = md5(content);
/* 152 */     content = new byte[data.length + 64];
/* 153 */     System.arraycopy(opad, 0, content, 0, 64);
/* 154 */     System.arraycopy(data, 0, content, 64, data.length);
/* 155 */     return md5(content);
/*     */   }
/*     */ 
/*     */   private static byte[] md5(byte[] data) {
/* 159 */     MD5Digest md5 = new MD5Digest();
/* 160 */     md5.update(data, 0, data.length);
/* 161 */     byte[] hash = new byte[16];
/* 162 */     md5.doFinal(hash, 0);
/* 163 */     return hash;
/*     */   }
/*     */ 
/*     */   public static byte[] createTimestamp(long time)
/*     */   {
/* 189 */     time += 11644473600000L;
/* 190 */     time *= 10000L;
/*     */ 
/* 193 */     byte[] timestamp = new byte[8];
/* 194 */     for (int i = 0; i < 8; i++) {
/* 195 */       timestamp[i] = (byte)(int)time;
/* 196 */       time >>>= 8;
/*     */     }
/*     */ 
/* 199 */     return timestamp;
/*     */   }
/*     */ 
/*     */   private static byte[] createBlob(byte[] targetInformation, byte[] clientChallenge, byte[] timestamp)
/*     */   {
/* 213 */     byte[] blobSignature = { 1, 1, 0, 0 };
/*     */ 
/* 216 */     byte[] reserved = { 0, 0, 0, 0 };
/*     */ 
/* 219 */     byte[] unknown1 = { 0, 0, 0, 0 };
/*     */ 
/* 222 */     byte[] unknown2 = { 0, 0, 0, 0 };
/*     */ 
/* 226 */     byte[] blob = new byte[blobSignature.length + reserved.length + timestamp.length + clientChallenge.length + unknown1.length + targetInformation.length + unknown2.length];
/*     */ 
/* 230 */     int offset = 0;
/* 231 */     System.arraycopy(blobSignature, 0, blob, offset, blobSignature.length);
/* 232 */     offset += blobSignature.length;
/* 233 */     System.arraycopy(reserved, 0, blob, offset, reserved.length);
/* 234 */     offset += reserved.length;
/* 235 */     System.arraycopy(timestamp, 0, blob, offset, timestamp.length);
/* 236 */     offset += timestamp.length;
/* 237 */     System.arraycopy(clientChallenge, 0, blob, offset, clientChallenge.length);
/*     */ 
/* 239 */     offset += clientChallenge.length;
/* 240 */     System.arraycopy(unknown1, 0, blob, offset, unknown1.length);
/* 241 */     offset += unknown1.length;
/* 242 */     System.arraycopy(targetInformation, 0, blob, offset, targetInformation.length);
/*     */ 
/* 244 */     offset += targetInformation.length;
/* 245 */     System.arraycopy(unknown2, 0, blob, offset, unknown2.length);
/* 246 */     return blob;
/*     */   }
/*     */ 
/*     */   private static byte[] encryptNonce(byte[] key, byte[] nonce)
/*     */   {
/* 254 */     byte[] out = new byte[24];
/*     */ 
/* 256 */     DESEngine d1 = new DESEngine(true, makeDESkey(key, 0));
/* 257 */     DESEngine d2 = new DESEngine(true, makeDESkey(key, 7));
/* 258 */     DESEngine d3 = new DESEngine(true, makeDESkey(key, 14));
/*     */ 
/* 260 */     d1.processBlock(nonce, 0, out, 0);
/* 261 */     d2.processBlock(nonce, 0, out, 8);
/* 262 */     d3.processBlock(nonce, 0, out, 16);
/*     */ 
/* 264 */     return out;
/*     */   }
/*     */ 
/*     */   private static byte[] ntHash(String password)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 272 */     byte[] key = new byte[21];
/* 273 */      Arrays.fill(key, (byte)0);
/* 274 */     byte[] pwd = password.getBytes("UnicodeLittleUnmarked");
/*     */ 
/* 277 */     MD4Digest md4 = new MD4Digest();
/* 278 */     md4.update(pwd, 0, pwd.length);
/* 279 */     md4.doFinal(key, 0);
/* 280 */     return key;
/*     */   }
/*     */ 
/*     */   private static byte[] convertPassword(String password)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 288 */     byte[] pwd = password.toUpperCase().getBytes("UTF8");
/*     */ 
/* 290 */     byte[] rtn = new byte[14];
/* 291 */    Arrays.fill(rtn, (byte) 0);
/* 292 */     System.arraycopy(pwd, 0, rtn, 0, pwd.length > 14 ? 14 : pwd.length);
/*     */ 
/* 297 */     return rtn;
/*     */   }
/*     */ 
/*     */   private static byte[] makeDESkey(byte[] buf, int off)
/*     */   {
/* 305 */     byte[] ret = new byte[8];
/*     */ 
/* 307 */     ret[0] = (byte)(buf[(off + 0)] >> 1 & 0xFF);
/* 308 */     ret[1] = (byte)(((buf[(off + 0)] & 0x1) << 6 | (buf[(off + 1)] & 0xFF) >> 2 & 0xFF) & 0xFF);
/* 309 */     ret[2] = (byte)(((buf[(off + 1)] & 0x3) << 5 | (buf[(off + 2)] & 0xFF) >> 3 & 0xFF) & 0xFF);
/* 310 */     ret[3] = (byte)(((buf[(off + 2)] & 0x7) << 4 | (buf[(off + 3)] & 0xFF) >> 4 & 0xFF) & 0xFF);
/* 311 */     ret[4] = (byte)(((buf[(off + 3)] & 0xF) << 3 | (buf[(off + 4)] & 0xFF) >> 5 & 0xFF) & 0xFF);
/* 312 */     ret[5] = (byte)(((buf[(off + 4)] & 0x1F) << 2 | (buf[(off + 5)] & 0xFF) >> 6 & 0xFF) & 0xFF);
/* 313 */     ret[6] = (byte)(((buf[(off + 5)] & 0x3F) << 1 | (buf[(off + 6)] & 0xFF) >> 7 & 0xFF) & 0xFF);
/* 314 */     ret[7] = (byte)(buf[(off + 6)] & 0x7F);
/*     */ 
/* 316 */     for (int i = 0; i < 8; i++) {
/* 317 */       ret[i] = (byte)(ret[i] << 1);
/*     */     }
/*     */ 
/* 320 */     return ret;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.commons.httpclient.auth.NTLMAuth
 * JD-Core Version:    0.6.0
 */