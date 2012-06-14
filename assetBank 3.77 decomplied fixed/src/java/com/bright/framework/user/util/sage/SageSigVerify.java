/*     */ package com.bright.framework.user.util.sage;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.TimeZone;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class SageSigVerify
/*     */ {
/*     */   private String authString;
/*     */   private String sigParamB64S;
/*     */   public byte[] authStringHash;
/*     */   public String site;
/*     */   private String notValidBeforeS;
/*     */   private String notValidAfterS;
/*     */   public String signature;
/*     */ 
/*     */   public SageSigVerify(NodeList nodeList)
/*     */   {
/*  42 */     List values = new ArrayList();
/*     */ 
/*  44 */     int i = 0; for (int count = nodeList.getLength(); i < count; i++)
/*     */     {
/*  48 */       if (!nodeList.item(i).getNodeName().equals("Signature")) {
/*  49 */         String value = nodeList.item(i).getTextContent().toLowerCase();
/*  50 */         this.signature = value;
/*  51 */         values.add(value);
/*     */       }
/*     */ 
/*  54 */       if (nodeList.item(i).getNodeName().equals("Signer")) {
/*  55 */         this.site = nodeList.item(i).getTextContent();
/*  56 */       } else if (nodeList.item(i).getNodeName().equals("NotValidAfter")) {
/*  57 */         this.notValidAfterS = nodeList.item(i).getTextContent();
/*  58 */       } else if (nodeList.item(i).getNodeName().equals("NotValidBefore")) {
/*  59 */         this.notValidBeforeS = nodeList.item(i).getTextContent(); } else {
/*  60 */         if (!nodeList.item(i).getNodeName().equals("Signature"))
/*     */           continue;
/*  62 */         this.sigParamB64S = nodeList.item(i).getTextContent();
/*     */       }
/*     */     }
/*     */ 
/*  66 */     Collections.sort(values);
/*     */ 
/*  69 */     StringBuffer sb = new StringBuffer();
/*  70 */     for (Iterator it = values.iterator(); it.hasNext(); ) {
/*  71 */       String s = (String)it.next();
/*  72 */       sb.append(s);
/*     */     }
/*     */ 
/*  75 */     String concatS = sb.toString();
/*  76 */     this.authString = concatS;
/*     */     byte[] authStringB;
/*     */     try
/*     */     {
/*  85 */       authStringB = concatS.getBytes("windows-1252");
/*     */     } catch (UnsupportedEncodingException e) {
/*  87 */       throw new RuntimeException(e);
/*     */     }
/*     */     try
/*     */     {
/*  91 */       this.authStringHash = MessageDigest.getInstance("MD5").digest(authStringB);
/*     */     } catch (NoSuchAlgorithmException e) {
/*  93 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void validate(Date now) throws Exception {
/*  98 */     validateSignatureOnly();
/*  99 */     validateDatesOnly(now);
/*     */   }
/*     */ 
/*     */   public void validateSignatureOnly()
/*     */     throws Exception
/*     */   {
/* 111 */     SageKey sageKey = Keyring.instance.getPublicKeyForSite(this.site);
/* 112 */     if (sageKey == null) {
/* 113 */       throw new Exception("Unknown signer: '" + this.site + "'");
/*     */     }
/*     */ 
/* 116 */     byte[] decHash = null;
/* 117 */     decHash = sageKey.decryptHash(this.sigParamB64S);
/* 118 */     boolean match = Arrays.equals(decHash, this.authStringHash);
/*     */ 
/* 120 */     if (!match)
/* 121 */       throw new Exception("Signature from '" + this.site + "' did not validate"); 
/*     */   }
/*     */ 
/*     */   public void validateDatesOnly(Date now) throws Exception {
/* 136 */     DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
/* 137 */     df.setTimeZone(TimeZone.getTimeZone("GMT"));
/*     */     Date notValidBeforeD;
/*     */     try {
/* 140 */       notValidBeforeD = df.parse(this.notValidBeforeS);
/*     */     } catch (ParseException e) {
/* 142 */       throw new RuntimeException("Could not parse NotValidBefore: " + this.notValidBeforeS);
/* 144 */     }long notValidBeforeL = notValidBeforeD.getTime();
/*     */     Date notValidAfterD;
/*     */     try { notValidAfterD = df.parse(this.notValidAfterS);
/*     */     } catch (ParseException e) {
/* 149 */       throw new RuntimeException("Could not parse NotValidAfter: " + this.notValidAfterS);
/*     */     }
/* 151 */     long notValidAfterL = notValidAfterD.getTime();
/*     */ 
/* 153 */     long nowL = now.getTime();
/*     */ 
/* 155 */     boolean tooEarly = nowL < notValidBeforeL;
/* 156 */     if (tooEarly) {
/* 157 */       throw new Exception("Signature not valid until: '" + this.notValidBeforeS + "', currently: '" + df.format(now) + "'");
/*     */     }
/* 159 */     boolean tooLate = nowL > notValidAfterL;
/* 160 */     if (tooLate)
/* 161 */       throw new Exception("Signature expired on: '" + this.notValidBeforeS + "', currently: '" + df.format(now) + "'");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.util.sage.SageSigVerify
 * JD-Core Version:    0.6.0
 */