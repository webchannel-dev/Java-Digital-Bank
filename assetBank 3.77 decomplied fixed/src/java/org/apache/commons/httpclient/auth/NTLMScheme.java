/*     */ package org.apache.commons.httpclient.auth;
/*     */ 
/*     */ import org.apache.commons.httpclient.Credentials;
/*     */ import org.apache.commons.httpclient.HttpMethod;
/*     */ import org.apache.commons.httpclient.NTCredentials;
/*     */ import org.apache.commons.httpclient.params.HttpMethodParams;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class NTLMScheme
/*     */   implements AuthScheme
/*     */ {
/*  55 */   private static final Log LOG = LogFactory.getLog(NTLMScheme.class);
/*     */ 
/*  58 */   private String ntlmchallenge = null;
/*     */   private static final int UNINITIATED = 0;
/*     */   private static final int INITIATED = 1;
/*     */   private static final int TYPE1_MSG_GENERATED = 2;
/*     */   private static final int TYPE2_MSG_RECEIVED = 3;
/*     */   private static final int TYPE3_MSG_GENERATED = 4;
/*     */   private static final int FAILED = 2147483647;
/*     */   private int state;
/*     */ 
/*     */   public NTLMScheme()
/*     */   {
/*  77 */     this.state = 0;
/*     */   }
/*     */ 
/*     */   public NTLMScheme(String challenge)
/*     */     throws MalformedChallengeException
/*     */   {
/*  90 */     processChallenge(challenge);
/*     */   }
/*     */ 
/*     */   public void processChallenge(String challenge)
/*     */     throws MalformedChallengeException
/*     */   {
/* 104 */     String s = AuthChallengeParser.extractScheme(challenge);
/* 105 */     if (!s.equalsIgnoreCase(getSchemeName())) {
/* 106 */       throw new MalformedChallengeException("Invalid NTLM challenge: " + challenge);
/*     */     }
/* 108 */     int i = challenge.indexOf(' ');
/* 109 */     if (i != -1) {
/* 110 */       s = challenge.substring(i, challenge.length());
/* 111 */       this.ntlmchallenge = s.trim();
/* 112 */       this.state = 3;
/*     */     } else {
/* 114 */       this.ntlmchallenge = "";
/* 115 */       if (this.state == 0)
/* 116 */         this.state = 1;
/*     */       else
/* 118 */         this.state = 2147483647;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isComplete()
/*     */   {
/* 132 */     return (this.state == 4) || (this.state == 2147483647);
/*     */   }
/*     */ 
/*     */   public String getSchemeName()
/*     */   {
/* 141 */     return "ntlm";
/*     */   }
/*     */ 
/*     */   public String getRealm()
/*     */   {
/* 151 */     return null;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public String getID()
/*     */   {
/* 173 */     return this.ntlmchallenge;
/*     */   }
/*     */ 
/*     */   public String getParameter(String name)
/*     */   {
/* 187 */     if (name == null) {
/* 188 */       throw new IllegalArgumentException("Parameter name may not be null");
/*     */     }
/* 190 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isConnectionBased()
/*     */   {
/* 201 */     return true;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static String authenticate(NTCredentials credentials, String challenge)
/*     */     throws AuthenticationException
/*     */   {
/* 220 */     LOG.trace("enter NTLMScheme.authenticate(NTCredentials, String)");
/*     */ 
/* 222 */     if (credentials == null) {
/* 223 */       throw new IllegalArgumentException("Credentials may not be null");
/*     */     }
/*     */ 
/* 226 */     NTLM ntlm = new NTLM();
/* 227 */     String s = ntlm.getResponseFor(challenge, credentials.getUserName(), credentials.getPassword(), credentials.getHost(), credentials.getDomain());
/*     */ 
/* 230 */     return "NTLM " + s;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static String authenticate(NTCredentials credentials, String challenge, String charset)
/*     */     throws AuthenticationException
/*     */   {
/* 254 */     LOG.trace("enter NTLMScheme.authenticate(NTCredentials, String)");
/*     */ 
/* 256 */     if (credentials == null) {
/* 257 */       throw new IllegalArgumentException("Credentials may not be null");
/*     */     }
/*     */ 
/* 260 */     NTLM ntlm = new NTLM();
/* 261 */     ntlm.setCredentialCharset(charset);
/* 262 */     String s = ntlm.getResponseFor(challenge, credentials.getUserName(), credentials.getPassword(), credentials.getHost(), credentials.getDomain());
/*     */ 
/* 268 */     return "NTLM " + s;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public String authenticate(Credentials credentials, String method, String uri)
/*     */     throws AuthenticationException
/*     */   {
/* 289 */     LOG.trace("enter NTLMScheme.authenticate(Credentials, String, String)");
/*     */ 
/* 291 */     NTCredentials ntcredentials = null;
/*     */     try {
/* 293 */       ntcredentials = (NTCredentials)credentials;
/*     */     } catch (ClassCastException e) {
/* 295 */       throw new InvalidCredentialsException("Credentials cannot be used for NTLM authentication: " + credentials.getClass().getName());
/*     */     }
/*     */ 
/* 299 */     return authenticate(ntcredentials, this.ntlmchallenge);
/*     */   }
/*     */ 
/*     */   public String authenticate(Credentials credentials, HttpMethod method)
/*     */     throws AuthenticationException
/*     */   {
/* 322 */     LOG.trace("enter NTLMScheme.authenticate(Credentials, HttpMethod)");
/*     */ 
/* 324 */     if (this.state == 0) {
/* 325 */       throw new IllegalStateException("NTLM authentication process has not been initiated");
/*     */     }
/*     */ 
/* 328 */     NTCredentials ntcredentials = null;
/*     */     try {
/* 330 */       ntcredentials = (NTCredentials)credentials;
/*     */     } catch (ClassCastException e) {
/* 332 */       throw new InvalidCredentialsException("Credentials cannot be used for NTLM authentication: " + credentials.getClass().getName());
/*     */     }
/*     */ 
/* 336 */     NTLM ntlm = new NTLM();
/* 337 */     ntlm.setCredentialCharset(method.getParams().getCredentialCharset());
/* 338 */     String response = null;
/* 339 */     if ((this.state == 1) || (this.state == 2147483647)) {
/* 340 */       response = ntlm.getType1Message(ntcredentials.getHost(), ntcredentials.getDomain());
/*     */ 
/* 343 */       this.state = 2;
/*     */     } else {
/* 345 */       response = ntlm.getType3Message(ntcredentials.getUserName(), ntcredentials.getPassword(), ntcredentials.getHost(), ntcredentials.getDomain(), ntlm.extractType2Message(this.ntlmchallenge));
/*     */ 
/* 351 */       this.state = 4;
/*     */     }
/* 353 */     return "NTLM " + response;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.commons.httpclient.auth.NTLMScheme
 * JD-Core Version:    0.6.0
 */