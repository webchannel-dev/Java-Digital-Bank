/*     */ package com.bright.assetbank.webservice.shared.security;
/*     */ 
/*     */ import org.apache.axis.message.SOAPHeaderElement;
/*     */ 
/*     */ public class DigestDataSoapHeader
/*     */   implements DigestData
/*     */ {
/*     */   private DigestData digestData;
/*     */ 
/*     */   public DigestDataSoapHeader()
/*     */   {
/*  73 */     this.digestData = new DigestDataImpl();
/*     */   }
/*     */ 
/*     */   public DigestDataSoapHeader(DigestData digestData)
/*     */   {
/*  91 */     this();
/*     */ 
/*  93 */     this.digestData = digestData;
/*     */   }
/*     */ 
/*     */   public DigestDataSoapHeader(SOAPHeaderElement header)
/*     */   {
/* 111 */     this();
/*     */ 
/* 113 */     populateFromHeader(header);
/*     */   }
/*     */ 
/*     */   public DigestData getDigestData()
/*     */   {
/* 129 */     return this.digestData;
/*     */   }
/*     */ 
/*     */   public void populateFromHeader(SOAPHeaderElement header)
/*     */   {
/* 147 */     if (header != null)
/*     */     {
/* 151 */       this.digestData.setUsername(header.getAttribute("username"));
/*     */ 
/* 153 */       this.digestData.setRealm(header.getAttribute("realm"));
/*     */ 
/* 155 */       this.digestData.setUri(header.getAttribute("uri"));
/*     */ 
/* 157 */       this.digestData.setNonce(header.getAttribute("nonce"));
/*     */ 
/* 159 */       this.digestData.setCnonce(header.getAttribute("cnonce"));
/*     */ 
/* 161 */       this.digestData.setOpaque(header.getAttribute("opaque"));
/*     */ 
/* 163 */       this.digestData.setRequestCounter(getLongValue(header.getAttribute("nc")));
/*     */ 
/* 165 */       this.digestData.setQualityOfProtection(header.getAttribute("qop"));
/*     */ 
/* 167 */       this.digestData.setAlgorithm(header.getAttribute("algorithm"));
/*     */ 
/* 169 */       this.digestData.setStoredDigestHash(header.getAttribute("response"));
/*     */ 
/* 171 */       this.digestData.setStale(Boolean.getBoolean(header.getAttribute("stale")));
/*     */     }
/*     */   }
/*     */ 
/*     */   public SOAPHeaderElement getHeader()
/*     */   {
/* 191 */     SOAPHeaderElement header = new SOAPHeaderElement("http://test", "AuthenticationHeader");
/*     */ 
/* 193 */     populateSOAPHeaderElement(header);
/*     */ 
/* 195 */     return header;
/*     */   }
/*     */ 
/*     */   private SOAPHeaderElement populateSOAPHeaderElement(SOAPHeaderElement header)
/*     */   {
/* 205 */     header.setMustUnderstand(false);
/*     */ 
/* 207 */     header.setPrefix("auth");
/*     */ 
/* 209 */     header.setAttribute("username", this.digestData.getUsername());
/*     */ 
/* 211 */     header.setAttribute("realm", this.digestData.getRealm());
/*     */ 
/* 213 */     header.setAttribute("uri", this.digestData.getUri());
/*     */ 
/* 215 */     header.setAttribute("nonce", this.digestData.getNonce());
/*     */ 
/* 217 */     header.setAttribute("cnonce", this.digestData.getCnonce());
/*     */ 
/* 219 */     header.setAttribute("opaque", this.digestData.getOpaque());
/*     */ 
/* 221 */     header.setAttribute("nc", this.digestData.getRequestCounter() != null ? this.digestData.getRequestCounter().toString() : "-1");
/*     */ 
/* 223 */     header.setAttribute("qop", this.digestData.getQualityOfProtection());
/*     */ 
/* 225 */     header.setAttribute("algorithm", this.digestData.getAlgorithm());
/*     */ 
/* 227 */     if (this.digestData.getStoredDigestHash() != null)
/*     */     {
/* 231 */       header.setAttribute("response", new String(this.digestData.getStoredDigestHash()));
/*     */     }
/*     */ 
/* 235 */     header.setAttribute("stale", String.valueOf(this.digestData.isStale()));
/*     */ 
/* 237 */     return header;
/*     */   }
/*     */ 
/*     */   private static Long getLongValue(String longValue)
/*     */   {
/*     */     try
/*     */     {
/* 251 */       return Long.valueOf(longValue);
/*     */     }
/*     */     catch (NumberFormatException nfe)
/*     */     {
/*     */     }
/*     */ 
/* 259 */     return new Long(-1L);
/*     */   }
/*     */ 
/*     */   public String getAlgorithm()
/*     */   {
/* 271 */     return this.digestData.getAlgorithm();
/*     */   }
/*     */ 
/*     */   public String getCnonce()
/*     */   {
/* 281 */     return this.digestData.getCnonce();
/*     */   }
/*     */ 
/*     */   public String getGeneratedDigestHash(String password)
/*     */   {
/* 291 */     return this.digestData.getGeneratedDigestHash(password);
/*     */   }
/*     */ 
/*     */   public String getNonce()
/*     */   {
/* 301 */     return this.digestData.getNonce();
/*     */   }
/*     */ 
/*     */   public String getOpaque()
/*     */   {
/* 311 */     return this.digestData.getOpaque();
/*     */   }
/*     */ 
/*     */   public String getQualityOfProtection()
/*     */   {
/* 321 */     return this.digestData.getQualityOfProtection();
/*     */   }
/*     */ 
/*     */   public String getRealm()
/*     */   {
/* 331 */     return this.digestData.getRealm();
/*     */   }
/*     */ 
/*     */   public Long getRequestCounter()
/*     */   {
/* 341 */     return this.digestData.getRequestCounter();
/*     */   }
/*     */ 
/*     */   public String getStoredDigestHash()
/*     */   {
/* 351 */     return this.digestData.getStoredDigestHash();
/*     */   }
/*     */ 
/*     */   public String getUri()
/*     */   {
/* 361 */     return this.digestData.getUri();
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/* 371 */     return this.digestData.getUsername();
/*     */   }
/*     */ 
/*     */   public boolean isStale()
/*     */   {
/* 381 */     return this.digestData.isStale();
/*     */   }
/*     */ 
/*     */   public void setAlgorithm(String algorithm)
/*     */   {
/* 391 */     this.digestData.setAlgorithm(algorithm);
/*     */   }
/*     */ 
/*     */   public void setCnonce(String cnonce)
/*     */   {
/* 401 */     this.digestData.setCnonce(cnonce);
/*     */   }
/*     */ 
/*     */   public void setNonce(String nonce)
/*     */   {
/* 411 */     this.digestData.setNonce(nonce);
/*     */   }
/*     */ 
/*     */   public void setOpaque(String opaque)
/*     */   {
/* 421 */     this.digestData.setOpaque(opaque);
/*     */   }
/*     */ 
/*     */   public void setQualityOfProtection(String qualityOfProtection)
/*     */   {
/* 431 */     this.digestData.setQualityOfProtection(qualityOfProtection);
/*     */   }
/*     */ 
/*     */   public void setRealm(String realm)
/*     */   {
/* 441 */     this.digestData.setRealm(realm);
/*     */   }
/*     */ 
/*     */   public void setRequestCounter(Long requestCounter)
/*     */   {
/* 451 */     this.digestData.setRequestCounter(requestCounter);
/*     */   }
/*     */ 
/*     */   public void setStale(boolean stale)
/*     */   {
/* 461 */     this.digestData.setStale(stale);
/*     */   }
/*     */ 
/*     */   public void setStoredDigestHash(String digestHash)
/*     */   {
/* 471 */     this.digestData.setStoredDigestHash(digestHash);
/*     */   }
/*     */ 
/*     */   public void setUri(String uri)
/*     */   {
/* 481 */     this.digestData.setUri(uri);
/*     */   }
/*     */ 
/*     */   public void setUsername(String username)
/*     */   {
/* 491 */     this.digestData.setUsername(username);
/*     */   }
/*     */ 
/*     */   public void storeNewDigestHash(String password)
/*     */   {
/* 501 */     this.digestData.storeNewDigestHash(password);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.webservice.shared.security.DigestDataSoapHeader
 * JD-Core Version:    0.6.0
 */