/*     */ package org.apache.commons.httpclient.crypto;
/*     */ 
/*     */ public class MD4Digest extends GeneralDigest
/*     */ {
/*     */   private static final int DIGEST_LENGTH = 16;
/*     */   private static final int S11 = 3;
/*     */   private static final int S12 = 7;
/*     */   private static final int S13 = 11;
/*     */   private static final int S14 = 19;
/*     */   private static final int S21 = 3;
/*     */   private static final int S22 = 5;
/*     */   private static final int S23 = 9;
/*     */   private static final int S24 = 13;
/*     */   private static final int S31 = 3;
/*     */   private static final int S32 = 9;
/*     */   private static final int S33 = 11;
/*     */   private static final int S34 = 15;
/*     */   private int H1;
/*     */   private int H2;
/*     */   private int H3;
/*     */   private int H4;
/*  60 */   private int[] X = new int[16];
/*     */   private int xOff;
/*     */ 
/*     */   public MD4Digest()
/*     */   {
/*  67 */     reset();
/*     */   }
/*     */ 
/*     */   public MD4Digest(MD4Digest t)
/*     */   {
/*  75 */     super(t);
/*     */ 
/*  77 */     this.H1 = t.H1;
/*  78 */     this.H2 = t.H2;
/*  79 */     this.H3 = t.H3;
/*  80 */     this.H4 = t.H4;
/*     */ 
/*  82 */     System.arraycopy(t.X, 0, this.X, 0, t.X.length);
/*  83 */     this.xOff = t.xOff;
/*     */   }
/*     */ 
/*     */   public String getAlgorithmName() {
/*  87 */     return "MD4";
/*     */   }
/*     */ 
/*     */   public int getDigestSize() {
/*  91 */     return 16;
/*     */   }
/*     */ 
/*     */   protected void processWord(byte[] in, int inOff) {
/*  95 */     this.X[(this.xOff++)] = (in[inOff] & 0xFF | (in[(inOff + 1)] & 0xFF) << 8 | (in[(inOff + 2)] & 0xFF) << 16 | (in[(inOff + 3)] & 0xFF) << 24);
/*     */ 
/*  98 */     if (this.xOff == 16)
/*  99 */       processBlock();
/*     */   }
/*     */ 
/*     */   protected void processLength(long bitLength)
/*     */   {
/* 104 */     if (this.xOff > 14) {
/* 105 */       processBlock();
/*     */     }
/*     */ 
/* 108 */     this.X[14] = (int)(bitLength & 0xFFFFFFFF);
/* 109 */     this.X[15] = (int)(bitLength >>> 32);
/*     */   }
/*     */ 
/*     */   private void unpackWord(int word, byte[] out, int outOff) {
/* 113 */     out[outOff] = (byte)word;
/* 114 */     out[(outOff + 1)] = (byte)(word >>> 8);
/* 115 */     out[(outOff + 2)] = (byte)(word >>> 16);
/* 116 */     out[(outOff + 3)] = (byte)(word >>> 24);
/*     */   }
/*     */ 
/*     */   public int doFinal(byte[] out, int outOff) {
/* 120 */     finish();
/*     */ 
/* 122 */     unpackWord(this.H1, out, outOff);
/* 123 */     unpackWord(this.H2, out, outOff + 4);
/* 124 */     unpackWord(this.H3, out, outOff + 8);
/* 125 */     unpackWord(this.H4, out, outOff + 12);
/*     */ 
/* 127 */     reset();
/*     */ 
/* 129 */     return 16;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 136 */     super.reset();
/*     */ 
/* 138 */     this.H1 = 1732584193;
/* 139 */     this.H2 = -271733879;
/* 140 */     this.H3 = -1732584194;
/* 141 */     this.H4 = 271733878;
/*     */ 
/* 143 */     this.xOff = 0;
/*     */ 
/* 145 */     for (int i = 0; i != this.X.length; i++)
/* 146 */       this.X[i] = 0;
/*     */   }
/*     */ 
/*     */   private int rotateLeft(int x, int n)
/*     */   {
/* 154 */     return x << n | x >>> 32 - n;
/*     */   }
/*     */ 
/*     */   private int F(int u, int v, int w)
/*     */   {
/* 161 */     return u & v | (u ^ 0xFFFFFFFF) & w;
/*     */   }
/*     */ 
/*     */   private int G(int u, int v, int w) {
/* 165 */     return u & v | u & w | v & w;
/*     */   }
/*     */ 
/*     */   private int H(int u, int v, int w) {
/* 169 */     return u ^ v ^ w;
/*     */   }
/*     */ 
/*     */   protected void processBlock() {
/* 173 */     int a = this.H1;
/* 174 */     int b = this.H2;
/* 175 */     int c = this.H3;
/* 176 */     int d = this.H4;
/*     */ 
/* 181 */     a = rotateLeft(a + F(b, c, d) + this.X[0], 3);
/* 182 */     d = rotateLeft(d + F(a, b, c) + this.X[1], 7);
/* 183 */     c = rotateLeft(c + F(d, a, b) + this.X[2], 11);
/* 184 */     b = rotateLeft(b + F(c, d, a) + this.X[3], 19);
/* 185 */     a = rotateLeft(a + F(b, c, d) + this.X[4], 3);
/* 186 */     d = rotateLeft(d + F(a, b, c) + this.X[5], 7);
/* 187 */     c = rotateLeft(c + F(d, a, b) + this.X[6], 11);
/* 188 */     b = rotateLeft(b + F(c, d, a) + this.X[7], 19);
/* 189 */     a = rotateLeft(a + F(b, c, d) + this.X[8], 3);
/* 190 */     d = rotateLeft(d + F(a, b, c) + this.X[9], 7);
/* 191 */     c = rotateLeft(c + F(d, a, b) + this.X[10], 11);
/* 192 */     b = rotateLeft(b + F(c, d, a) + this.X[11], 19);
/* 193 */     a = rotateLeft(a + F(b, c, d) + this.X[12], 3);
/* 194 */     d = rotateLeft(d + F(a, b, c) + this.X[13], 7);
/* 195 */     c = rotateLeft(c + F(d, a, b) + this.X[14], 11);
/* 196 */     b = rotateLeft(b + F(c, d, a) + this.X[15], 19);
/*     */ 
/* 201 */     a = rotateLeft(a + G(b, c, d) + this.X[0] + 1518500249, 3);
/* 202 */     d = rotateLeft(d + G(a, b, c) + this.X[4] + 1518500249, 5);
/* 203 */     c = rotateLeft(c + G(d, a, b) + this.X[8] + 1518500249, 9);
/* 204 */     b = rotateLeft(b + G(c, d, a) + this.X[12] + 1518500249, 13);
/* 205 */     a = rotateLeft(a + G(b, c, d) + this.X[1] + 1518500249, 3);
/* 206 */     d = rotateLeft(d + G(a, b, c) + this.X[5] + 1518500249, 5);
/* 207 */     c = rotateLeft(c + G(d, a, b) + this.X[9] + 1518500249, 9);
/* 208 */     b = rotateLeft(b + G(c, d, a) + this.X[13] + 1518500249, 13);
/* 209 */     a = rotateLeft(a + G(b, c, d) + this.X[2] + 1518500249, 3);
/* 210 */     d = rotateLeft(d + G(a, b, c) + this.X[6] + 1518500249, 5);
/* 211 */     c = rotateLeft(c + G(d, a, b) + this.X[10] + 1518500249, 9);
/* 212 */     b = rotateLeft(b + G(c, d, a) + this.X[14] + 1518500249, 13);
/* 213 */     a = rotateLeft(a + G(b, c, d) + this.X[3] + 1518500249, 3);
/* 214 */     d = rotateLeft(d + G(a, b, c) + this.X[7] + 1518500249, 5);
/* 215 */     c = rotateLeft(c + G(d, a, b) + this.X[11] + 1518500249, 9);
/* 216 */     b = rotateLeft(b + G(c, d, a) + this.X[15] + 1518500249, 13);
/*     */ 
/* 221 */     a = rotateLeft(a + H(b, c, d) + this.X[0] + 1859775393, 3);
/* 222 */     d = rotateLeft(d + H(a, b, c) + this.X[8] + 1859775393, 9);
/* 223 */     c = rotateLeft(c + H(d, a, b) + this.X[4] + 1859775393, 11);
/* 224 */     b = rotateLeft(b + H(c, d, a) + this.X[12] + 1859775393, 15);
/* 225 */     a = rotateLeft(a + H(b, c, d) + this.X[2] + 1859775393, 3);
/* 226 */     d = rotateLeft(d + H(a, b, c) + this.X[10] + 1859775393, 9);
/* 227 */     c = rotateLeft(c + H(d, a, b) + this.X[6] + 1859775393, 11);
/* 228 */     b = rotateLeft(b + H(c, d, a) + this.X[14] + 1859775393, 15);
/* 229 */     a = rotateLeft(a + H(b, c, d) + this.X[1] + 1859775393, 3);
/* 230 */     d = rotateLeft(d + H(a, b, c) + this.X[9] + 1859775393, 9);
/* 231 */     c = rotateLeft(c + H(d, a, b) + this.X[5] + 1859775393, 11);
/* 232 */     b = rotateLeft(b + H(c, d, a) + this.X[13] + 1859775393, 15);
/* 233 */     a = rotateLeft(a + H(b, c, d) + this.X[3] + 1859775393, 3);
/* 234 */     d = rotateLeft(d + H(a, b, c) + this.X[11] + 1859775393, 9);
/* 235 */     c = rotateLeft(c + H(d, a, b) + this.X[7] + 1859775393, 11);
/* 236 */     b = rotateLeft(b + H(c, d, a) + this.X[15] + 1859775393, 15);
/*     */ 
/* 238 */     this.H1 += a;
/* 239 */     this.H2 += b;
/* 240 */     this.H3 += c;
/* 241 */     this.H4 += d;
/*     */ 
/* 246 */     this.xOff = 0;
/*     */ 
/* 248 */     for (int i = 0; i != this.X.length; i++)
/* 249 */       this.X[i] = 0;
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.commons.httpclient.crypto.MD4Digest
 * JD-Core Version:    0.6.0
 */