/*     */ package com.bright.framework.encryption.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.encoding.bean.EncodingControl;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ 
/*     */ public abstract class EncryptionMethod
/*     */ {
/*  28 */   private int m_iEncryptionMode = -1;
/*  29 */   private boolean m_bIVRequired = true;
/*  30 */   private EncodingControl m_encoder = null;
/*  31 */   private String m_sKey = null;
/*  32 */   private String m_sIV = null;
/*     */ 
/*     */   public EncryptionMethod(String a_sKey, String a_sIV, EncodingControl a_encoder, boolean bEncrypting)
/*     */   {
/*  36 */     this.m_sKey = a_sKey;
/*  37 */     this.m_sIV = a_sIV;
/*  38 */     this.m_encoder = a_encoder;
/*  39 */     if (bEncrypting)
/*     */     {
/*  41 */       setEncryption();
/*     */     }
/*     */     else
/*     */     {
/*  45 */       setDecryption();
/*     */     }
/*     */   }
/*     */ 
/*     */   public abstract String getEncryptionName();
/*     */ 
/*     */   public abstract String getKeySpec();
/*     */ 
/*     */   public String doCipher(String a_sValue) throws Bn2Exception
/*     */   {
/*     */     try {
/*  58 */       byte[] byKey = getKey();
/*  59 */       byte[] byIV = null;
/*  60 */       if (isIVRequired())
/*     */       {
/*  62 */         byIV = getIV();
/*     */       }
/*     */ 
/*  66 */       SecretKeySpec skeySpec = new SecretKeySpec(byKey, getKeySpec());
/*  67 */       Cipher cipher = Cipher.getInstance(getEncryptionName());
/*     */ 
/*  69 */       if (isIVRequired())
/*     */       {
/*  71 */         IvParameterSpec ips = new IvParameterSpec(byIV);
/*  72 */         cipher.init(this.m_iEncryptionMode, skeySpec, ips);
/*     */       }
/*     */       else
/*     */       {
/*  76 */         cipher.init(this.m_iEncryptionMode, skeySpec);
/*     */       }
/*     */ 
/*  80 */       byte[] byVaue = a_sValue.getBytes();
/*  81 */       if (!getEncrypting())
/*     */       {
/*  83 */         byVaue = getEncodingControl().decode(byVaue);
/*     */       }
/*     */ 
/*  86 */       byte[] encrypted = cipher.doFinal(byVaue);
/*  87 */       String sEncryptedValuesString = null;
/*     */ 
/*  89 */       if (getEncrypting())
/*     */       {
/*  91 */         sEncryptedValuesString = new String(getEncodingControl().encode(encrypted));
/*     */       }
/*     */       else
/*     */       {
/*  95 */         sEncryptedValuesString = new String(encrypted);
/*     */       }
/*     */ 
/*  98 */       return sEncryptedValuesString;
/*     */     }
/*     */     catch (Exception e) {
/*     */     
/* 102 */     throw new Bn2Exception("CipherUtil.doCipher: Error performing cipher: " + e.getMessage(), e);
                }
/*     */   }
/*     */ 
/*     */   public boolean isIVRequired()
/*     */   {
/* 108 */     return this.m_bIVRequired;
/*     */   }
/*     */ 
/*     */   public EncodingControl getEncodingControl()
/*     */   {
/* 113 */     return this.m_encoder;
/*     */   }
/*     */ 
/*     */   public byte[] getKey()
/*     */   {
/* 118 */     return this.m_encoder.decode(this.m_sKey.getBytes());
/*     */   }
/*     */ 
/*     */   public byte[] getIV()
/*     */   {
/* 123 */     return this.m_encoder.decode(this.m_sIV.getBytes());
/*     */   }
/*     */ 
/*     */   public boolean getEncrypting()
/*     */   {
/* 128 */     return this.m_iEncryptionMode == 1;
/*     */   }
/*     */ 
/*     */   public void setEncryption()
/*     */   {
/* 133 */     this.m_iEncryptionMode = 1;
/*     */   }
/*     */ 
/*     */   public void setDecryption()
/*     */   {
/* 138 */     this.m_iEncryptionMode = 2;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.encryption.bean.EncryptionMethod
 * JD-Core Version:    0.6.0
 */