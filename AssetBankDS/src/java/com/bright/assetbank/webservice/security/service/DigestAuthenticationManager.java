/*     */ package com.bright.assetbank.webservice.security.service;
/*     */ 
/*     */ import com.bright.assetbank.webservice.security.shared.DigestData;
/*     */ import com.bright.assetbank.webservice.security.shared.SecurityUtils;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class DigestAuthenticationManager
/*     */ {
/*  39 */   private static DigestAuthenticationManager instance = new DigestAuthenticationManager();
/*     */   private Map nonces;
/*     */   private Random random;
/*     */ 
/*     */   public DigestAuthenticationManager()
/*     */   {
/*  53 */     this.nonces = new HashMap();
/*  54 */     this.random = new Random();
/*     */   }
/*     */ 
/*     */   public static DigestAuthenticationManager getInstance()
/*     */   {
/*  48 */     return instance;
/*     */   }
/*     */ 
/*     */   public String createNewNonce()
/*     */   {
/*  62 */     String nonce = null;
/*  63 */     byte[] bytes = new byte[16];
/*     */ 
/*  65 */     synchronized (this.nonces)
/*     */     {
/*     */       do
/*     */       {
/*  69 */         this.random.nextBytes(bytes);
/*  70 */         nonce = SecurityUtils.getHexString(bytes);
/*     */       }
/*  72 */       while (this.nonces.containsKey(nonce));
/*     */ 
/*  74 */       this.nonces.put(nonce, new NonceData(new Long(0L), getNewNonceExpiryDate()));
/*     */     }
/*  76 */     return nonce;
/*     */   }
/*     */ 
/*     */   private Date getNewNonceExpiryDate()
/*     */   {
/*  85 */     return new Date(System.currentTimeMillis() + 120000L);
/*     */   }
/*     */ 
/*     */   public boolean authenticateDigest(DigestData digest)
/*     */   {
/*  99 */     if (isStale(digest))
/*     */     {
/* 101 */       this.nonces.remove(digest.getNonce());
/* 102 */       return false;
/*     */     }
/*     */ 
/* 105 */     synchronized (this.nonces)
/*     */     {
/* 107 */       String nonce = digest.getNonce();
/* 108 */       if (this.nonces.containsKey(nonce))
/*     */       {
/* 110 */         NonceData nonceData = (NonceData)this.nonces.get(nonce);
/* 111 */         Long lastRequestCounter = nonceData.getRequestCounter();
/*     */ 
/* 114 */         if (digest.getRequestCounter().compareTo(lastRequestCounter) >= 0)
/*     */         {
/* 117 */           if (digest.getGeneratedDigestHash("9repH3Pa").equals(digest.getStoredDigestHash()))
/*     */           {
/* 120 */             nonceData.setRequestCounter(new Long(lastRequestCounter.longValue() + 1L));
/* 121 */             nonceData.setExpires(getNewNonceExpiryDate());
/* 122 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isStale(DigestData digest)
/*     */   {
/* 138 */     synchronized (this.nonces)
/*     */     {
/* 140 */       String nonce = digest.getNonce();
/* 141 */       if (this.nonces.containsKey(nonce))
/*     */       {
/* 143 */         return ((NonceData)this.nonces.get(nonce)).getExpires().getTime() < System.currentTimeMillis();
/*     */       }
/*     */     }
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */   public void removeExpiredNonces()
/*     */   {
/* 156 */     long now = System.currentTimeMillis();
/* 157 */     synchronized (this.nonces)
/*     */     {
/* 159 */       Iterator itNonces = this.nonces.keySet().iterator();
/* 160 */       while (itNonces.hasNext())
/*     */       {
/* 162 */         String nonce = (String)itNonces.next();
/* 163 */         NonceData nonceData = (NonceData)this.nonces.get(nonce);
/* 164 */         if (nonceData.getExpires().getTime() < now)
/*     */         {
/* 166 */           this.nonces.remove(nonce);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private class NonceData
/*     */   {
/*     */     private Long requestCounter;
/*     */     private Date expires;
/*     */ 
/*     */     public NonceData(Long requestCounter, Date expires)
/*     */     {
/* 183 */       this.requestCounter = requestCounter;
/* 184 */       this.expires = expires;
/*     */     }
/*     */ 
/*     */     public Date getExpires() {
/* 188 */       return this.expires;
/*     */     }
/*     */ 
/*     */     public void setExpires(Date expires) {
/* 192 */       this.expires = expires;
/*     */     }
/*     */ 
/*     */     public Long getRequestCounter() {
/* 196 */       return this.requestCounter;
/*     */     }
/*     */ 
/*     */     public void setRequestCounter(Long requestCounter) {
/* 200 */       this.requestCounter = requestCounter;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.webservice.security.service.DigestAuthenticationManager
 * JD-Core Version:    0.6.0
 */