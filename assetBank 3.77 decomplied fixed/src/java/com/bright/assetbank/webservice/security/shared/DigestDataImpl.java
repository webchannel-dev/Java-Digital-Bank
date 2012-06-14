/*     */ package com.bright.assetbank.webservice.security.shared;
/*     */ 
/*     */ import com.bright.assetbank.webservice.synchronise.util.ErrorUtils;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ 
/*     */ public class DigestDataImpl
/*     */   implements DigestData
/*     */ {
/*     */   private String username;
/*     */   private String realm;
/*     */   private String uri;
/*     */   private String nonce;
/*     */   private String cnonce;
/*     */   private String opaque;
/*     */   private String qualityOfProtection;
/*     */   private Long requestCounter;
/*     */   private String algorithm;
/*     */   private String storedDigestHash;
/*     */   private boolean stale;
/*     */ 
/*     */   public DigestDataImpl()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DigestDataImpl(DigestDataImpl digest)
/*     */   {
/* 116 */     this.username = digest.getUsername();
/*     */ 
/* 118 */     this.realm = digest.getRealm();
/*     */ 
/* 120 */     this.uri = digest.getUri();
/*     */ 
/* 122 */     this.nonce = digest.getNonce();
/*     */ 
/* 124 */     this.cnonce = digest.getCnonce();
/*     */ 
/* 126 */     this.opaque = digest.getOpaque();
/*     */ 
/* 128 */     this.qualityOfProtection = digest.getQualityOfProtection();
/*     */ 
/* 130 */     this.requestCounter = digest.getRequestCounter();
/*     */ 
/* 132 */     this.algorithm = digest.getAlgorithm();
/*     */ 
/* 134 */     this.storedDigestHash = digest.getStoredDigestHash();
/*     */ 
/* 136 */     this.stale = digest.isStale();
/*     */   }
/*     */ 
/*     */   public void storeNewDigestHash(String password)
/*     */   {
/* 154 */     setStoredDigestHash(getGeneratedDigestHash(password));
/*     */   }
/*     */ 
/*     */   public String getGeneratedDigestHash(String password)
/*     */   {
/* 178 */     StringBuffer hashBuff = new StringBuffer(getUsername());
/*     */ 
/* 180 */     hashBuff.append(password);
/*     */ 
/* 182 */     hashBuff.append(getNonce());
/*     */ 
/* 184 */     hashBuff.append(getRequestCounter());
/*     */ 
/* 186 */     byte[] hashValue = hashBuff.toString().getBytes();
/*     */     try
/*     */     {
/* 194 */       MessageDigest md = MessageDigest.getInstance(getAlgorithm());
/*     */ 
/* 196 */       return SecurityUtils.getHexString(md.digest(hashValue));
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/*     */     
/*     */ 
/* 204 */     throw ErrorUtils.getRuntimeException(this, e, "Cannot create MessageDigest for algorithm " + getAlgorithm());
/*     */  } }
/*     */ 
/*     */   public String getCnonce()
/*     */   {
/* 216 */     return this.cnonce;
/*     */   }
/*     */ 
/*     */   public void setCnonce(String cnonce)
/*     */   {
/* 224 */     this.cnonce = cnonce;
/*     */   }
/*     */ 
/*     */   public String getNonce()
/*     */   {
/* 232 */     return this.nonce;
/*     */   }
/*     */ 
/*     */   public void setNonce(String nonce)
/*     */   {
/* 240 */     this.nonce = nonce;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/* 248 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String username)
/*     */   {
/* 256 */     this.username = username;
/*     */   }
/*     */ 
/*     */   public String getStoredDigestHash()
/*     */   {
/* 264 */     return this.storedDigestHash;
/*     */   }
/*     */ 
/*     */   public void setStoredDigestHash(String digestHash)
/*     */   {
/* 272 */     this.storedDigestHash = digestHash;
/*     */   }
/*     */ 
/*     */   public Long getRequestCounter()
/*     */   {
/* 280 */     return this.requestCounter;
/*     */   }
/*     */ 
/*     */   public void setRequestCounter(Long requestCounter)
/*     */   {
/* 288 */     this.requestCounter = requestCounter;
/*     */   }
/*     */ 
/*     */   public String getAlgorithm()
/*     */   {
/* 298 */     return this.algorithm;
/*     */   }
/*     */ 
/*     */   public void setAlgorithm(String algorithm)
/*     */   {
/* 308 */     this.algorithm = algorithm;
/*     */   }
/*     */ 
/*     */   public String getOpaque()
/*     */   {
/* 318 */     return this.opaque;
/*     */   }
/*     */ 
/*     */   public void setOpaque(String opaque)
/*     */   {
/* 328 */     this.opaque = opaque;
/*     */   }
/*     */ 
/*     */   public String getQualityOfProtection()
/*     */   {
/* 338 */     return this.qualityOfProtection;
/*     */   }
/*     */ 
/*     */   public void setQualityOfProtection(String qualityOfProtection)
/*     */   {
/* 348 */     this.qualityOfProtection = qualityOfProtection;
/*     */   }
/*     */ 
/*     */   public String getRealm()
/*     */   {
/* 358 */     return this.realm;
/*     */   }
/*     */ 
/*     */   public void setRealm(String realm)
/*     */   {
/* 368 */     this.realm = realm;
/*     */   }
/*     */ 
/*     */   public String getUri()
/*     */   {
/* 378 */     return this.uri;
/*     */   }
/*     */ 
/*     */   public void setUri(String uri)
/*     */   {
/* 388 */     this.uri = uri;
/*     */   }
/*     */ 
/*     */   public boolean isStale()
/*     */   {
/* 398 */     return this.stale;
/*     */   }
/*     */ 
/*     */   public void setStale(boolean stale)
/*     */   {
/* 408 */     this.stale = stale;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.webservice.security.shared.DigestDataImpl
 * JD-Core Version:    0.6.0
 */