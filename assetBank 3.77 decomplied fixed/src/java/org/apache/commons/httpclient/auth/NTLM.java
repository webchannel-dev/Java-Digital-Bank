/*     */ package org.apache.commons.httpclient.auth;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ import org.apache.commons.httpclient.util.EncodingUtil;
/*     */ 
/*     */ final class NTLM
/*     */ {
/*     */   public static final String DEFAULT_CHARSET = "UnicodeLittleUnmarked";
/*     */   private byte[] currentResponse;
/*     */   private int currentPosition;
/*     */   private String credentialCharset;
/*     */ 
/*     */   NTLM()
/*     */   {
/*  69 */     this.currentPosition = 0;
/*     */ 
/*  72 */     this.credentialCharset = "UnicodeLittleUnmarked";
/*     */   }
/*     */ 
/*     */   public final String getResponseFor(String message, String username, String password, String host, String domain)
/*     */     throws AuthenticationException
/*     */   {
/*     */     String response;

/*  90 */     if ((message == null) || (message.trim().equals("")))
/*  91 */       response = getType1Message(host, domain);
/*     */     else {
/*  93 */       response = getType3Message(username, password, host, domain, extractType2Message(message));
/*     */     }
/*  95 */     return response;
/*     */   }
/*     */ 
/*     */   private void prepareResponse(int length)
/*     */   {
/* 103 */     this.currentResponse = new byte[length];
/* 104 */     this.currentPosition = 0;
/*     */   }
/*     */ 
/*     */   private void addByte(byte b)
/*     */   {
/* 112 */     this.currentResponse[this.currentPosition] = b;
/* 113 */     this.currentPosition += 1;
/*     */   }
/*     */ 
/*     */   private void addBytes(byte[] bytes)
/*     */   {
/* 121 */     for (int i = 0; i < bytes.length; i++) {
/* 122 */       this.currentResponse[this.currentPosition] = bytes[i];
/* 123 */       this.currentPosition += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean getUseNTLMv2()
/*     */   {
/* 131 */     return true;
/*     */   }
/*     */ 
/*     */   private String getResponse()
/*     */   {
/*     */     byte[] resp;

/* 141 */     if (this.currentResponse.length > this.currentPosition) {
/* 142 */       byte[] tmp = new byte[this.currentPosition];
/* 143 */       for (int i = 0; i < this.currentPosition; i++) {
/* 144 */         tmp[i] = this.currentResponse[i];
/*     */       }
/* 146 */       resp = tmp;
/*     */     } else {
/* 148 */       resp = this.currentResponse;
/*     */     }
/* 150 */     return EncodingUtil.getAsciiString(Base64.encodeBase64(resp));
/*     */   }
/*     */ 
/*     */   public String getType1Message(String host, String domain)
/*     */   {
/* 162 */     host = host.toUpperCase();
/* 163 */     domain = domain.toUpperCase();
/* 164 */     byte[] hostBytes = EncodingUtil.getBytes(host, "UnicodeLittleUnmarked");
/* 165 */     byte[] domainBytes = EncodingUtil.getBytes(domain, "UnicodeLittleUnmarked");
/*     */ 
/* 169 */     int finalLength = 32 + hostBytes.length + domainBytes.length;
/* 170 */     prepareResponse(finalLength);
/*     */ 
/* 173 */     byte[] protocol = EncodingUtil.getBytes("NTLMSSP", "ISO-8859-1");
/* 174 */     addBytes(protocol);
/* 175 */    /* addByte(0);
/*     */ 
/* 178 */    // addByte(1);
/* 179 */     //addByte(0);
/* 180 */     //addByte(0);
/* 181 */     //addByte(0);
/*     */ 
/* 192 */    // addByte(getUseNTLMv2() ? 5 : 1);
/* 193 */    // addByte(-78);
/* 194 */    // addByte(0);
/* 195 */     //addByte(0); 
addBytes(protocol);
        addByte((byte) 0);

        // Type
        addByte((byte) 1);
        addByte((byte) 0);
        addByte((byte) 0);
        addByte((byte) 0);

        // Flags
        addByte((byte) 6);
        addByte((byte) 82);
        addByte((byte) 0);
        addByte((byte) 0);
/*     */ 
/* 198 */     int iDomLen = domainBytes.length;
/* 199 */     byte[] domLen = convertShort(iDomLen);
/* 200 */     addByte(domLen[0]);
/* 201 */     addByte(domLen[1]);
/*     */ 
/* 204 */     addByte(domLen[0]);
/* 205 */     addByte(domLen[1]);
/*     */ 
/* 208 */     byte[] domOff = convertShort(hostBytes.length + 32);
/* 209 */     addByte(domOff[0]);
/* 210 */     addByte(domOff[1]);
/* 211 */    // addByte(0);
/* 212 */     //addByte(0);
addByte((byte) 0);
        addByte((byte) 0);
/*     */ 
/* 215 */     byte[] hostLen = convertShort(hostBytes.length);
/* 216 */     addByte(hostLen[0]);
/* 217 */     addByte(hostLen[1]);
/*     */ 
/* 220 */     addByte(hostLen[0]);
/* 221 */     addByte(hostLen[1]);
/*     */ 
/* 224 */     byte[] hostOff = convertShort(32);
/* 225 */     addByte(hostOff[0]);
/* 226 */     addByte(hostOff[1]);

/* 227 */     //addByte(0);
/* 228 */     //addByte(0);
/*     */     addByte((byte) 0);
        addByte((byte) 0);
/* 231 */     addBytes(hostBytes);
/*     */ 
/* 234 */     addBytes(domainBytes);
/*     */ 
/* 236 */     return getResponse();
/*     */   }
/*     */ 
/*     */   public MessageExtract extractType2Message(String message)
/*     */   {
/* 247 */     byte[] msg = Base64.decodeBase64(EncodingUtil.getBytes(message, "ISO-8859-1"));
/*     */ 
/* 249 */     byte[] challenge = new byte[8];
/* 250 */     for (int i = 0; i < 8; i++) {
/* 251 */       challenge[i] = msg[(i + 24)];
/*     */     }
/*     */ 
/* 254 */     byte[] targetInfo = new byte[8];
/* 255 */     for (int i = 0; i < 8; i++) {
/* 256 */       targetInfo[i] = msg[(i + 40)];
/*     */     }
/* 258 */     return new MessageExtract(challenge, targetInfo);
/*     */   }
/*     */ 
/*     */   public String getType3Message(String user, String password, String host, String domain, MessageExtract extract)
/*     */     throws AuthenticationException
/*     */   {
/* 277 */     byte[] lmResponse = null;
/* 278 */     byte[] ntResponse = null;
/* 279 */     byte[] clientChallenge = new byte[8];
/* 280 */     new Random().nextBytes(clientChallenge);
/*     */     try
/*     */     {
/* 283 */      
/*     */   lmResponse = getUseNTLMv2() ? NTLMAuth.getLMv2Challenge(domain, user, password, extract.getChallenge(), clientChallenge) : NTLMAuth.getLMChallenge(password, extract.getChallenge());
/* 286 */       ntResponse = getUseNTLMv2() ? NTLMAuth.getNTLMv2Challenge(domain, user, password, extract.getChallenge(), extract.getTargetInfo(), clientChallenge) : NTLMAuth.getNTLMChallenge(password, extract.getChallenge());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 291 */       throw new AuthenticationException(e.getMessage(), e);
/*     */     }
/*     */ 
/* 294 */     domain = domain.toUpperCase();
/* 295 */     host = host.toUpperCase();
/* 296 */     user = user.toUpperCase();
/* 297 */     byte[] domainBytes = EncodingUtil.getBytes(domain, "UnicodeLittleUnmarked");
/* 298 */     byte[] hostBytes = EncodingUtil.getBytes(host, "UnicodeLittleUnmarked");
/* 299 */     byte[] userBytes = EncodingUtil.getBytes(user, "UnicodeLittleUnmarked");
/*     */ 
/* 306 */     int finalLength = 64 + domainBytes.length + userBytes.length + hostBytes.length + lmResponse.length + ntResponse.length + 0;
/*     */ 
/* 310 */     prepareResponse(finalLength);
/*     */ 
/* 312 */     byte[] ntlmssp = EncodingUtil.getBytes("NTLMSSP", "ISO-8859-1");
/* 313 */     addBytes(ntlmssp);
addByte((byte) 0);
      addByte((byte) 3);
        addByte((byte) 0);
        addByte((byte) 0);
        addByte((byte) 0);
/*     */ 
/* 323 */     addBytes(convertShort(lmResponse.length));
/* 324 */     addBytes(convertShort(lmResponse.length));
/*     */ 
/* 327 */     addBytes(convertShort(64 + domainBytes.length + userBytes.length + hostBytes.length));
  addByte((byte) 0);
        addByte((byte) 0);
/*     */ 
/* 332 */     addBytes(convertShort(ntResponse.length));
/* 333 */     addBytes(convertShort(ntResponse.length));
/*     */ 
/* 336 */     addBytes(convertShort(64 + domainBytes.length + userBytes.length + hostBytes.length + lmResponse.length));
  addByte((byte) 0);
        addByte((byte) 0);;
/*     */ 
/* 341 */     addBytes(convertShort(domainBytes.length));
/* 342 */     addBytes(convertShort(domainBytes.length));
/*     */ 
/* 345 */     addBytes(convertShort(64));
  addByte((byte) 0);
        addByte((byte) 0);
/*     */ 
/* 350 */     addBytes(convertShort(userBytes.length));
/* 351 */     addBytes(convertShort(userBytes.length));
/*     */ 
/* 354 */     addBytes(convertShort(64 + domainBytes.length));
  addByte((byte) 0);
        addByte((byte) 0);
/*     */ 
/* 359 */     addBytes(convertShort(hostBytes.length));
/* 360 */     addBytes(convertShort(hostBytes.length));
/*     */ 
/* 363 */     addBytes(convertShort(64 + domainBytes.length + userBytes.length));
  addByte((byte) 0);
        addByte((byte) 0);
/*     */ 
/* 368 */     addBytes(convertShort(0));
/* 369 */     addBytes(convertShort(0));
/*     */ 
/* 372 */     addBytes(convertShort(finalLength));
  addByte((byte) 0);
        addByte((byte) 0);
/*     */ 
 addByte((byte) 6);
        addByte((byte) 82);
        addByte((byte) 0);
        addByte((byte) 0);
/*     */ 
/* 390 */     addBytes(domainBytes);
/* 391 */     addBytes(userBytes);
/* 392 */     addBytes(hostBytes);
/* 393 */     addBytes(lmResponse);
/* 394 */     addBytes(ntResponse);
/*     */ 
/* 396 */     return getResponse();
/*     */   }
/*     */ 
/*     */   private byte[] convertShort(int num)
/*     */   {
/* 406 */     byte[] val = new byte[2];
/* 407 */     String hex = Integer.toString(num, 16);
/* 408 */     while (hex.length() < 4) {
/* 409 */       hex = "0" + hex;
/*     */     }
/* 411 */     String low = hex.substring(2, 4);
/* 412 */     String high = hex.substring(0, 2);
/*     */ 
/* 414 */     val[0] = (byte)Integer.parseInt(low, 16);
/* 415 */     val[1] = (byte)Integer.parseInt(high, 16);
/* 416 */     return val;
/*     */   }
/*     */ 
/*     */   public String getCredentialCharset()
/*     */   {
/* 423 */     return this.credentialCharset;
/*     */   }
/*     */ 
/*     */   public void setCredentialCharset(String credentialCharset)
/*     */   {
/* 430 */     this.credentialCharset = credentialCharset;
/*     */   }
/*     */ 
/*     */   public class MessageExtract
/*     */   {
/*     */     private byte[] challenge;
/*     */     private byte[] targetInfo;
/*     */ 
/*     */     public MessageExtract(byte[] challenge, byte[] targetInfo) {
/* 441 */       this.challenge = challenge;
/* 442 */       this.targetInfo = targetInfo;
/*     */     }
/*     */ 
/*     */     public byte[] getChallenge() {
/* 446 */       return this.challenge;
/*     */     }
/*     */ 
/*     */     public byte[] getTargetInfo() {
/* 450 */       return this.targetInfo;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.commons.httpclient.auth.NTLM
 * JD-Core Version:    0.6.0
 */