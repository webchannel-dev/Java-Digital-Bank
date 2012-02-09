/*     */ package org.apache.commons.httpclient.crypto;
/*     */ 
/*     */ public abstract class GeneralDigest
/*     */ {
/*     */   private byte[] xBuf;
/*     */   private int xBufOff;
/*     */   private long byteCount;
/*     */ 
/*     */   protected GeneralDigest()
/*     */   {
/*  40 */     this.xBuf = new byte[4];
/*  41 */     this.xBufOff = 0;
/*     */   }
/*     */ 
/*     */   protected GeneralDigest(GeneralDigest t)
/*     */   {
/*  51 */     this.xBuf = new byte[t.xBuf.length];
/*  52 */     System.arraycopy(t.xBuf, 0, this.xBuf, 0, t.xBuf.length);
/*     */ 
/*  54 */     this.xBufOff = t.xBufOff;
/*  55 */     this.byteCount = t.byteCount;
/*     */   }
/*     */ 
/*     */   public void update(byte in)
/*     */   {
/*  61 */     this.xBuf[(this.xBufOff++)] = in;
/*     */ 
/*  63 */     if (this.xBufOff == this.xBuf.length)
/*     */     {
/*  65 */       processWord(this.xBuf, 0);
/*  66 */       this.xBufOff = 0;
/*     */     }
/*     */ 
/*  69 */     this.byteCount += 1L;
/*     */   }
/*     */ 
/*     */   public void update(byte[] in, int inOff, int len)
/*     */   {
/*  80 */     while ((this.xBufOff != 0) && (len > 0))
/*     */     {
/*  82 */       update(in[inOff]);
/*     */ 
/*  84 */       inOff++;
/*  85 */       len--;
/*     */     }
/*     */ 
/*  91 */     while (len > this.xBuf.length)
/*     */     {
/*  93 */       processWord(in, inOff);
/*     */ 
/*  95 */       inOff += this.xBuf.length;
/*  96 */       len -= this.xBuf.length;
/*  97 */       this.byteCount += this.xBuf.length;
/*     */     }
/*     */ 
/* 103 */     while (len > 0)
/*     */     {
/* 105 */       update(in[inOff]);
/*     */ 
/* 107 */       inOff++;
/* 108 */       len--;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void finish()
/*     */   {
/* 114 */     long bitLength = this.byteCount << 3;
/*     */ 
/* 119 */     //update(-128);
update((byte)128);
/*     */ 
/* 121 */     while (this.xBufOff != 0)
/*     */     {
/* 123 */       //update(0);
     update((byte)0);
/*     */     }
/*     */ 
/* 126 */     processLength(bitLength);
/*     */ 
/* 128 */     processBlock();
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 133 */     this.byteCount = 0L;
/*     */ 
/* 135 */     this.xBufOff = 0;
/* 136 */     for (int i = 0; i < this.xBuf.length; i++)
/* 137 */       this.xBuf[i] = 0;
/*     */   }
/*     */ 
/*     */   protected abstract void processWord(byte[] paramArrayOfByte, int paramInt);
/*     */ 
/*     */   protected abstract void processLength(long paramLong);
/*     */ 
/*     */   protected abstract void processBlock();
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.commons.httpclient.crypto.GeneralDigest
 * JD-Core Version:    0.6.0
 */