/*     */ package org.apache.commons.httpclient.crypto;
/*     */ 
/*     */ public class DESEngine
/*     */ {
/*     */   protected static final int BLOCK_SIZE = 8;
/*  20 */   private int[] workingKey = null;
/*     */ 
/* 112 */   static short[] Df_Key = { 1, 35, 69, 103, 137, 171, 205, 239, 254, 220, 186, 152, 118, 84, 50, 16, 137, 171, 205, 239, 1, 35, 69, 103 };
/*     */ 
/* 119 */   static short[] bytebit = { 128, 64, 32, 16, 8, 4, 2, 1 };
/*     */ 
/* 124 */   static int[] bigbyte = { 8388608, 4194304, 2097152, 1048576, 524288, 262144, 131072, 65536, 32768, 16384, 8192, 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1 };
/*     */ 
/* 138 */   static byte[] pc1 = { 56, 48, 40, 32, 24, 16, 8, 0, 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 60, 52, 44, 36, 28, 20, 12, 4, 27, 19, 11, 3 };
/*     */ 
/* 146 */   static byte[] totrot = { 1, 2, 4, 6, 8, 10, 12, 14, 15, 17, 19, 21, 23, 25, 27, 28 };
/*     */ 
/* 152 */   static byte[] pc2 = { 13, 16, 10, 23, 0, 4, 2, 27, 14, 5, 20, 9, 22, 18, 11, 3, 25, 7, 15, 6, 26, 19, 12, 1, 40, 51, 30, 36, 46, 54, 29, 39, 50, 44, 32, 47, 43, 48, 38, 55, 33, 52, 45, 41, 49, 35, 28, 31 };
/*     */ 
/* 160 */   static int[] SP1 = { 16843776, 0, 65536, 16843780, 16842756, 66564, 4, 65536, 1024, 16843776, 16843780, 1024, 16778244, 16842756, 16777216, 4, 1028, 16778240, 16778240, 66560, 66560, 16842752, 16842752, 16778244, 65540, 16777220, 16777220, 65540, 0, 1028, 66564, 16777216, 65536, 16843780, 4, 16842752, 16843776, 16777216, 16777216, 1024, 16842756, 65536, 66560, 16777220, 1024, 4, 16778244, 66564, 16843780, 65540, 16842752, 16778244, 16777220, 1028, 66564, 16843776, 1028, 16778240, 16778240, 0, 65540, 66560, 0, 16842756 };
/*     */ 
/* 179 */   static int[] SP2 = { -2146402272, -2147450880, 32768, 1081376, 1048576, 32, -2146435040, -2147450848, -2147483616, -2146402272, -2146402304, -2147483648, -2147450880, 1048576, 32, -2146435040, 1081344, 1048608, -2147450848, 0, -2147483648, 32768, 1081376, -2146435072, 1048608, -2147483616, 0, 1081344, 32800, -2146402304, -2146435072, 32800, 0, 1081376, -2146435040, 1048576, -2147450848, -2146435072, -2146402304, 32768, -2146435072, -2147450880, 32, -2146402272, 1081376, 32, 32768, -2147483648, 32800, -2146402304, 1048576, -2147483616, 1048608, -2147450848, -2147483616, 1048608, 1081344, 0, -2147450880, 32800, -2147483648, -2146435040, -2146402272, 1081344 };
/*     */ 
/* 198 */   static int[] SP3 = { 520, 134349312, 0, 134348808, 134218240, 0, 131592, 134218240, 131080, 134217736, 134217736, 131072, 134349320, 131080, 134348800, 520, 134217728, 8, 134349312, 512, 131584, 134348800, 134348808, 131592, 134218248, 131584, 131072, 134218248, 8, 134349320, 512, 134217728, 134349312, 134217728, 131080, 520, 131072, 134349312, 134218240, 0, 512, 131080, 134349320, 134218240, 134217736, 512, 0, 134348808, 134218248, 131072, 134217728, 134349320, 8, 131592, 131584, 134217736, 134348800, 134218248, 520, 134348800, 131592, 8, 134348808, 131584 };
/*     */ 
/* 217 */   static int[] SP4 = { 8396801, 8321, 8321, 128, 8396928, 8388737, 8388609, 8193, 0, 8396800, 8396800, 8396929, 129, 0, 8388736, 8388609, 1, 8192, 8388608, 8396801, 128, 8388608, 8193, 8320, 8388737, 1, 8320, 8388736, 8192, 8396928, 8396929, 129, 8388736, 8388609, 8396800, 8396929, 129, 0, 0, 8396800, 8320, 8388736, 8388737, 1, 8396801, 8321, 8321, 128, 8396929, 129, 1, 8192, 8388609, 8193, 8396928, 8388737, 8193, 8320, 8388608, 8396801, 128, 8388608, 8192, 8396928 };
/*     */ 
/* 236 */   static int[] SP5 = { 256, 34078976, 34078720, 1107296512, 524288, 256, 1073741824, 34078720, 1074266368, 524288, 33554688, 1074266368, 1107296512, 1107820544, 524544, 1073741824, 33554432, 1074266112, 1074266112, 0, 1073742080, 1107820800, 1107820800, 33554688, 1107820544, 1073742080, 0, 1107296256, 34078976, 33554432, 1107296256, 524544, 524288, 1107296512, 256, 33554432, 1073741824, 34078720, 1107296512, 1074266368, 33554688, 1073741824, 1107820544, 34078976, 1074266368, 256, 33554432, 1107820544, 1107820800, 524544, 1107296256, 1107820800, 34078720, 0, 1074266112, 1107296256, 524544, 33554688, 1073742080, 524288, 0, 1074266112, 34078976, 1073742080 };
/*     */ 
/* 255 */   static int[] SP6 = { 536870928, 541065216, 16384, 541081616, 541065216, 16, 541081616, 4194304, 536887296, 4210704, 4194304, 536870928, 4194320, 536887296, 536870912, 16400, 0, 4194320, 536887312, 16384, 4210688, 536887312, 16, 541065232, 541065232, 0, 4210704, 541081600, 16400, 4210688, 541081600, 536870912, 536887296, 16, 541065232, 4210688, 541081616, 4194304, 16400, 536870928, 4194304, 536887296, 536870912, 16400, 536870928, 541081616, 4210688, 541065216, 4210704, 541081600, 0, 541065232, 16, 16384, 541065216, 4210704, 16384, 4194320, 536887312, 0, 541081600, 536870912, 4194320, 536887312 };
/*     */ 
/* 274 */   static int[] SP7 = { 2097152, 69206018, 67110914, 0, 2048, 67110914, 2099202, 69208064, 69208066, 2097152, 0, 67108866, 2, 67108864, 69206018, 2050, 67110912, 2099202, 2097154, 67110912, 67108866, 69206016, 69208064, 2097154, 69206016, 2048, 2050, 69208066, 2099200, 2, 67108864, 2099200, 67108864, 2099200, 2097152, 67110914, 67110914, 69206018, 69206018, 2, 2097154, 67108864, 67110912, 2097152, 69208064, 2050, 2099202, 69208064, 2050, 67108866, 69208066, 69206016, 2099200, 0, 2, 69208066, 0, 2099202, 69206016, 2048, 67108866, 67110912, 2048, 2097154 };
/*     */ 
/* 293 */   static int[] SP8 = { 268439616, 4096, 262144, 268701760, 268435456, 268439616, 64, 268435456, 262208, 268697600, 268701760, 266240, 268701696, 266304, 4096, 64, 268697600, 268435520, 268439552, 4160, 266240, 262208, 268697664, 268701696, 4160, 0, 0, 268697664, 268435520, 268439552, 266304, 262144, 266304, 262144, 268701696, 4096, 64, 268697664, 4096, 266304, 268439552, 64, 268435520, 268697600, 268697664, 268435456, 262144, 268439616, 0, 268701760, 262208, 268435520, 268697600, 268439552, 268439616, 0, 268701760, 266240, 266240, 4160, 4160, 262208, 268435456, 268701696 };
/*     */ 
/*     */   public DESEngine()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DESEngine(boolean encrypting, byte[] key)
/*     */   {
/*  34 */     init(encrypting, key);
/*     */   }
/*     */ 
/*     */   public void init(boolean encrypting, byte[] key)
/*     */   {
/*  49 */     this.workingKey = generateWorkingKey(encrypting, key);
/*     */   }
/*     */ 
/*     */   public String getAlgorithmName()
/*     */   {
/*  66 */     return "DES";
/*     */   }
/*     */ 
/*     */   public int getBlockSize()
/*     */   {
/*  71 */     return 8;
/*     */   }
/*     */ 
/*     */   public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
/*     */   {
/*  80 */     if (this.workingKey == null)
/*     */     {
/*  82 */       throw new IllegalStateException("DES engine not initialised");
/*     */     }
/*     */ 
/*  85 */     if (inOff + 8 > in.length)
/*     */     {
/*  88 */       throw new IllegalArgumentException("input buffer too short");
/*     */     }
/*     */ 
/*  91 */     if (outOff + 8 > out.length)
/*     */     {
/*  94 */       throw new IllegalArgumentException("output buffer too short");
/*     */     }
/*     */ 
/*  97 */     desFunc(this.workingKey, in, inOff, out, outOff);
/*     */ 
/*  99 */     return 8;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected int[] generateWorkingKey(boolean encrypting, byte[] key)
/*     */   {
/* 323 */     int[] newKey = new int[32];
/* 324 */     boolean[] pc1m = new boolean[56];
/* 325 */     boolean[] pcr = new boolean[56];
/*     */ 
/* 327 */     for (int j = 0; j < 56; j++)
/*     */     {
/* 329 */       int l = pc1[j];
/*     */ 
/* 331 *///       pc1m[j] = ((key[(l >>> 3)] & bytebit[(l & 0x7)]) != 0 ? 1 : false);
 pc1m[j] = ((key[(l >>> 3)] & bytebit[(l & 0x7)]) != 0 );
 
/*     */     }
/*     */ 
/* 334 */     for (int i = 0; i < 16; i++)
/*     */     {
/*     */       int m;

/* 338 */       if (encrypting)
/*     */       {
/* 340 */         m = i << 1;
/*     */       }
/*     */       else
/*     */       {
/* 344 */         m = 15 - i << 1;
/*     */       }
/*     */ 
/* 347 */       int n = m + 1;
/*     */       int tmp115_114 = 0; newKey[n] = tmp115_114; newKey[m] = tmp115_114;
/*     */ 
/* 350 */       for (int j = 0; j < 28; j++)
/*     */       {
/* 352 */         int l = j + totrot[i];
/* 353 */         if (l < 28)
/*     */         {
/* 355 */           pcr[j] = pc1m[l];
/*     */         }
/*     */         else
/*     */         {
/* 359 */           pcr[j] = pc1m[(l - 28)];
/*     */         }
/*     */       }
/*     */ 
/* 363 */       for (int j = 28; j < 56; j++)
/*     */       {
/* 365 */         int l = j + totrot[i];
/* 366 */         if (l < 56)
/*     */         {
/* 368 */           pcr[j] = pc1m[l];
/*     */         }
/*     */         else
/*     */         {
/* 372 */           pcr[j] = pc1m[(l - 28)];
/*     */         }
/*     */       }
/*     */ 
/* 376 */       for (int j = 0; j < 24; j++)
/*     */       {
/* 378 */         //if (pcr[pc2[j]] != 0)
    if (pcr[pc2[j]] )
/*     */         {
/* 380 */           newKey[m] |= bigbyte[j];
/*     */         }
/*     */ 
/* 383 */         //if (pcr[pc2[(j + 24)]] == 0)
    if (pcr[pc2[(j + 24)]])
/*     */           continue;
/* 385 */         newKey[n] |= bigbyte[j];
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 393 */     for (int i = 0; i != 32; i += 2)
/*     */     {
/* 397 */       int i1 = newKey[i];
/* 398 */       int i2 = newKey[(i + 1)];
/*     */ 
/* 400 */       newKey[i] = ((i1 & 0xFC0000) << 6 | (i1 & 0xFC0) << 10 | (i2 & 0xFC0000) >>> 10 | (i2 & 0xFC0) >>> 6);
/*     */ 
/* 403 */       newKey[(i + 1)] = ((i1 & 0x3F000) << 12 | (i1 & 0x3F) << 16 | (i2 & 0x3F000) >>> 4 | i2 & 0x3F);
/*     */     }
/*     */ 
/* 407 */     return newKey;
/*     */   }
/*     */ 
/*     */   protected void desFunc(int[] wKey, byte[] in, int inOff, byte[] out, int outOff)
/*     */   {
/* 422 */     int left = (in[(inOff + 0)] & 0xFF) << 24;
/* 423 */     left |= (in[(inOff + 1)] & 0xFF) << 16;
/* 424 */     left |= (in[(inOff + 2)] & 0xFF) << 8;
/* 425 */     left |= in[(inOff + 3)] & 0xFF;
/*     */ 
/* 427 */     int right = (in[(inOff + 4)] & 0xFF) << 24;
/* 428 */     right |= (in[(inOff + 5)] & 0xFF) << 16;
/* 429 */     right |= (in[(inOff + 6)] & 0xFF) << 8;
/* 430 */     right |= in[(inOff + 7)] & 0xFF;
/*     */ 
/* 432 */     int work = (left >>> 4 ^ right) & 0xF0F0F0F;
/* 433 */     right ^= work;
/* 434 */     left ^= work << 4;
/* 435 */     work = (left >>> 16 ^ right) & 0xFFFF;
/* 436 */     right ^= work;
/* 437 */     left ^= work << 16;
/* 438 */     work = (right >>> 2 ^ left) & 0x33333333;
/* 439 */     left ^= work;
/* 440 */     right ^= work << 2;
/* 441 */     work = (right >>> 8 ^ left) & 0xFF00FF;
/* 442 */     left ^= work;
/* 443 */     right ^= work << 8;
/* 444 */     right = (right << 1 | right >>> 31 & 0x1) & 0xFFFFFFFF;
/* 445 */     work = (left ^ right) & 0xAAAAAAAA;
/* 446 */     left ^= work;
/* 447 */     right ^= work;
/* 448 */     left = (left << 1 | left >>> 31 & 0x1) & 0xFFFFFFFF;
/*     */ 
/* 450 */     for (int round = 0; round < 8; round++)
/*     */     {
/* 454 */       work = right << 28 | right >>> 4;
/* 455 */       work ^= wKey[(round * 4 + 0)];
/* 456 */       int fval = SP7[(work & 0x3F)];
/* 457 */       fval |= SP5[(work >>> 8 & 0x3F)];
/* 458 */       fval |= SP3[(work >>> 16 & 0x3F)];
/* 459 */       fval |= SP1[(work >>> 24 & 0x3F)];
/* 460 */       work = right ^ wKey[(round * 4 + 1)];
/* 461 */       fval |= SP8[(work & 0x3F)];
/* 462 */       fval |= SP6[(work >>> 8 & 0x3F)];
/* 463 */       fval |= SP4[(work >>> 16 & 0x3F)];
/* 464 */       fval |= SP2[(work >>> 24 & 0x3F)];
/* 465 */       left ^= fval;
/* 466 */       work = left << 28 | left >>> 4;
/* 467 */       work ^= wKey[(round * 4 + 2)];
/* 468 */       fval = SP7[(work & 0x3F)];
/* 469 */       fval |= SP5[(work >>> 8 & 0x3F)];
/* 470 */       fval |= SP3[(work >>> 16 & 0x3F)];
/* 471 */       fval |= SP1[(work >>> 24 & 0x3F)];
/* 472 */       work = left ^ wKey[(round * 4 + 3)];
/* 473 */       fval |= SP8[(work & 0x3F)];
/* 474 */       fval |= SP6[(work >>> 8 & 0x3F)];
/* 475 */       fval |= SP4[(work >>> 16 & 0x3F)];
/* 476 */       fval |= SP2[(work >>> 24 & 0x3F)];
/* 477 */       right ^= fval;
/*     */     }
/*     */ 
/* 480 */     right = right << 31 | right >>> 1;
/* 481 */     work = (left ^ right) & 0xAAAAAAAA;
/* 482 */     left ^= work;
/* 483 */     right ^= work;
/* 484 */     left = left << 31 | left >>> 1;
/* 485 */     work = (left >>> 8 ^ right) & 0xFF00FF;
/* 486 */     right ^= work;
/* 487 */     left ^= work << 8;
/* 488 */     work = (left >>> 2 ^ right) & 0x33333333;
/* 489 */     right ^= work;
/* 490 */     left ^= work << 2;
/* 491 */     work = (right >>> 16 ^ left) & 0xFFFF;
/* 492 */     left ^= work;
/* 493 */     right ^= work << 16;
/* 494 */     work = (right >>> 4 ^ left) & 0xF0F0F0F;
/* 495 */     left ^= work;
/* 496 */     right ^= work << 4;
/*     */ 
/* 498 */     out[(outOff + 0)] = (byte)(right >>> 24 & 0xFF);
/* 499 */     out[(outOff + 1)] = (byte)(right >>> 16 & 0xFF);
/* 500 */     out[(outOff + 2)] = (byte)(right >>> 8 & 0xFF);
/* 501 */     out[(outOff + 3)] = (byte)(right & 0xFF);
/* 502 */     out[(outOff + 4)] = (byte)(left >>> 24 & 0xFF);
/* 503 */     out[(outOff + 5)] = (byte)(left >>> 16 & 0xFF);
/* 504 */     out[(outOff + 6)] = (byte)(left >>> 8 & 0xFF);
/* 505 */     out[(outOff + 7)] = (byte)(left & 0xFF);
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.commons.httpclient.crypto.DESEngine
 * JD-Core Version:    0.6.0
 */