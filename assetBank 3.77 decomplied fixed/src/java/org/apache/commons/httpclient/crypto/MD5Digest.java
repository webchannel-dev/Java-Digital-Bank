/*     */ package org.apache.commons.httpclient.crypto;
/*     */ 
/*     */ public class MD5Digest extends GeneralDigest
/*     */ {
/*     */   private static final int DIGEST_LENGTH = 16;
/*     */   private int H1;
/*     */   private int H2;
/*     */   private int H3;
/*     */   private int H4;
/*  32 */   private int[] X = new int[16];
/*     */   private int xOff;
/*     */   private static final int S11 = 7;
/*     */   private static final int S12 = 12;
/*     */   private static final int S13 = 17;
/*     */   private static final int S14 = 22;
/*     */   private static final int S21 = 5;
/*     */   private static final int S22 = 9;
/*     */   private static final int S23 = 14;
/*     */   private static final int S24 = 20;
/*     */   private static final int S31 = 4;
/*     */   private static final int S32 = 11;
/*     */   private static final int S33 = 16;
/*     */   private static final int S34 = 23;
/*     */   private static final int S41 = 6;
/*     */   private static final int S42 = 10;
/*     */   private static final int S43 = 15;
/*     */   private static final int S44 = 21;
/*     */ 
/*     */   public MD5Digest()
/*     */   {
/*  40 */     reset();
/*     */   }
/*     */ 
/*     */   public MD5Digest(MD5Digest t)
/*     */   {
/*  49 */     super(t);
/*     */ 
/*  51 */     this.H1 = t.H1;
/*  52 */     this.H2 = t.H2;
/*  53 */     this.H3 = t.H3;
/*  54 */     this.H4 = t.H4;
/*     */ 
/*  56 */     System.arraycopy(t.X, 0, this.X, 0, t.X.length);
/*  57 */     this.xOff = t.xOff;
/*     */   }
/*     */ 
/*     */   public String getAlgorithmName()
/*     */   {
/*  62 */     return "MD5";
/*     */   }
/*     */ 
/*     */   public int getDigestSize()
/*     */   {
/*  67 */     return 16;
/*     */   }
/*     */ 
/*     */   protected void processWord(byte[] in, int inOff)
/*     */   {
/*  74 */     this.X[(this.xOff++)] = (in[inOff] & 0xFF | (in[(inOff + 1)] & 0xFF) << 8 | (in[(inOff + 2)] & 0xFF) << 16 | (in[(inOff + 3)] & 0xFF) << 24);
/*     */ 
/*  77 */     if (this.xOff == 16)
/*     */     {
/*  79 */       processBlock();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processLength(long bitLength)
/*     */   {
/*  86 */     if (this.xOff > 14)
/*     */     {
/*  88 */       processBlock();
/*     */     }
/*     */ 
/*  91 */     this.X[14] = (int)(bitLength & 0xFFFFFFFF);
/*  92 */     this.X[15] = (int)(bitLength >>> 32);
/*     */   }
/*     */ 
/*     */   private void unpackWord(int word, byte[] out, int outOff)
/*     */   {
/* 100 */     out[outOff] = (byte)word;
/* 101 */     out[(outOff + 1)] = (byte)(word >>> 8);
/* 102 */     out[(outOff + 2)] = (byte)(word >>> 16);
/* 103 */     out[(outOff + 3)] = (byte)(word >>> 24);
/*     */   }
/*     */ 
/*     */   public int doFinal(byte[] out, int outOff)
/*     */   {
/* 110 */     finish();
/*     */ 
/* 112 */     unpackWord(this.H1, out, outOff);
/* 113 */     unpackWord(this.H2, out, outOff + 4);
/* 114 */     unpackWord(this.H3, out, outOff + 8);
/* 115 */     unpackWord(this.H4, out, outOff + 12);
/*     */ 
/* 117 */     reset();
/*     */ 
/* 119 */     return 16;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 127 */     super.reset();
/*     */ 
/* 129 */     this.H1 = 1732584193;
/* 130 */     this.H2 = -271733879;
/* 131 */     this.H3 = -1732584194;
/* 132 */     this.H4 = 271733878;
/*     */ 
/* 134 */     this.xOff = 0;
/*     */ 
/* 136 */     for (int i = 0; i != this.X.length; i++)
/*     */     {
/* 138 */       this.X[i] = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   private int rotateLeft(int x, int n)
/*     */   {
/* 181 */     return x << n | x >>> 32 - n;
/*     */   }
/*     */ 
/*     */   private int F(int u, int v, int w)
/*     */   {
/* 192 */     return u & v | (u ^ 0xFFFFFFFF) & w;
/*     */   }
/*     */ 
/*     */   private int G(int u, int v, int w)
/*     */   {
/* 200 */     return u & w | v & (w ^ 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   private int H(int u, int v, int w)
/*     */   {
/* 208 */     return u ^ v ^ w;
/*     */   }
/*     */ 
/*     */   private int K(int u, int v, int w)
/*     */   {
/* 216 */     return v ^ (u | w ^ 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   protected void processBlock()
/*     */   {
/* 221 */     int a = this.H1;
/* 222 */     int b = this.H2;
/* 223 */     int c = this.H3;
/* 224 */     int d = this.H4;
/*     */ 
/* 229 */     a = rotateLeft(a + F(b, c, d) + this.X[0] + -680876936, 7) + b;
/* 230 */     d = rotateLeft(d + F(a, b, c) + this.X[1] + -389564586, 12) + a;
/* 231 */     c = rotateLeft(c + F(d, a, b) + this.X[2] + 606105819, 17) + d;
/* 232 */     b = rotateLeft(b + F(c, d, a) + this.X[3] + -1044525330, 22) + c;
/* 233 */     a = rotateLeft(a + F(b, c, d) + this.X[4] + -176418897, 7) + b;
/* 234 */     d = rotateLeft(d + F(a, b, c) + this.X[5] + 1200080426, 12) + a;
/* 235 */     c = rotateLeft(c + F(d, a, b) + this.X[6] + -1473231341, 17) + d;
/* 236 */     b = rotateLeft(b + F(c, d, a) + this.X[7] + -45705983, 22) + c;
/* 237 */     a = rotateLeft(a + F(b, c, d) + this.X[8] + 1770035416, 7) + b;
/* 238 */     d = rotateLeft(d + F(a, b, c) + this.X[9] + -1958414417, 12) + a;
/* 239 */     c = rotateLeft(c + F(d, a, b) + this.X[10] + -42063, 17) + d;
/* 240 */     b = rotateLeft(b + F(c, d, a) + this.X[11] + -1990404162, 22) + c;
/* 241 */     a = rotateLeft(a + F(b, c, d) + this.X[12] + 1804603682, 7) + b;
/* 242 */     d = rotateLeft(d + F(a, b, c) + this.X[13] + -40341101, 12) + a;
/* 243 */     c = rotateLeft(c + F(d, a, b) + this.X[14] + -1502002290, 17) + d;
/* 244 */     b = rotateLeft(b + F(c, d, a) + this.X[15] + 1236535329, 22) + c;
/*     */ 
/* 249 */     a = rotateLeft(a + G(b, c, d) + this.X[1] + -165796510, 5) + b;
/* 250 */     d = rotateLeft(d + G(a, b, c) + this.X[6] + -1069501632, 9) + a;
/* 251 */     c = rotateLeft(c + G(d, a, b) + this.X[11] + 643717713, 14) + d;
/* 252 */     b = rotateLeft(b + G(c, d, a) + this.X[0] + -373897302, 20) + c;
/* 253 */     a = rotateLeft(a + G(b, c, d) + this.X[5] + -701558691, 5) + b;
/* 254 */     d = rotateLeft(d + G(a, b, c) + this.X[10] + 38016083, 9) + a;
/* 255 */     c = rotateLeft(c + G(d, a, b) + this.X[15] + -660478335, 14) + d;
/* 256 */     b = rotateLeft(b + G(c, d, a) + this.X[4] + -405537848, 20) + c;
/* 257 */     a = rotateLeft(a + G(b, c, d) + this.X[9] + 568446438, 5) + b;
/* 258 */     d = rotateLeft(d + G(a, b, c) + this.X[14] + -1019803690, 9) + a;
/* 259 */     c = rotateLeft(c + G(d, a, b) + this.X[3] + -187363961, 14) + d;
/* 260 */     b = rotateLeft(b + G(c, d, a) + this.X[8] + 1163531501, 20) + c;
/* 261 */     a = rotateLeft(a + G(b, c, d) + this.X[13] + -1444681467, 5) + b;
/* 262 */     d = rotateLeft(d + G(a, b, c) + this.X[2] + -51403784, 9) + a;
/* 263 */     c = rotateLeft(c + G(d, a, b) + this.X[7] + 1735328473, 14) + d;
/* 264 */     b = rotateLeft(b + G(c, d, a) + this.X[12] + -1926607734, 20) + c;
/*     */ 
/* 269 */     a = rotateLeft(a + H(b, c, d) + this.X[5] + -378558, 4) + b;
/* 270 */     d = rotateLeft(d + H(a, b, c) + this.X[8] + -2022574463, 11) + a;
/* 271 */     c = rotateLeft(c + H(d, a, b) + this.X[11] + 1839030562, 16) + d;
/* 272 */     b = rotateLeft(b + H(c, d, a) + this.X[14] + -35309556, 23) + c;
/* 273 */     a = rotateLeft(a + H(b, c, d) + this.X[1] + -1530992060, 4) + b;
/* 274 */     d = rotateLeft(d + H(a, b, c) + this.X[4] + 1272893353, 11) + a;
/* 275 */     c = rotateLeft(c + H(d, a, b) + this.X[7] + -155497632, 16) + d;
/* 276 */     b = rotateLeft(b + H(c, d, a) + this.X[10] + -1094730640, 23) + c;
/* 277 */     a = rotateLeft(a + H(b, c, d) + this.X[13] + 681279174, 4) + b;
/* 278 */     d = rotateLeft(d + H(a, b, c) + this.X[0] + -358537222, 11) + a;
/* 279 */     c = rotateLeft(c + H(d, a, b) + this.X[3] + -722521979, 16) + d;
/* 280 */     b = rotateLeft(b + H(c, d, a) + this.X[6] + 76029189, 23) + c;
/* 281 */     a = rotateLeft(a + H(b, c, d) + this.X[9] + -640364487, 4) + b;
/* 282 */     d = rotateLeft(d + H(a, b, c) + this.X[12] + -421815835, 11) + a;
/* 283 */     c = rotateLeft(c + H(d, a, b) + this.X[15] + 530742520, 16) + d;
/* 284 */     b = rotateLeft(b + H(c, d, a) + this.X[2] + -995338651, 23) + c;
/*     */ 
/* 289 */     a = rotateLeft(a + K(b, c, d) + this.X[0] + -198630844, 6) + b;
/* 290 */     d = rotateLeft(d + K(a, b, c) + this.X[7] + 1126891415, 10) + a;
/* 291 */     c = rotateLeft(c + K(d, a, b) + this.X[14] + -1416354905, 15) + d;
/* 292 */     b = rotateLeft(b + K(c, d, a) + this.X[5] + -57434055, 21) + c;
/* 293 */     a = rotateLeft(a + K(b, c, d) + this.X[12] + 1700485571, 6) + b;
/* 294 */     d = rotateLeft(d + K(a, b, c) + this.X[3] + -1894986606, 10) + a;
/* 295 */     c = rotateLeft(c + K(d, a, b) + this.X[10] + -1051523, 15) + d;
/* 296 */     b = rotateLeft(b + K(c, d, a) + this.X[1] + -2054922799, 21) + c;
/* 297 */     a = rotateLeft(a + K(b, c, d) + this.X[8] + 1873313359, 6) + b;
/* 298 */     d = rotateLeft(d + K(a, b, c) + this.X[15] + -30611744, 10) + a;
/* 299 */     c = rotateLeft(c + K(d, a, b) + this.X[6] + -1560198380, 15) + d;
/* 300 */     b = rotateLeft(b + K(c, d, a) + this.X[13] + 1309151649, 21) + c;
/* 301 */     a = rotateLeft(a + K(b, c, d) + this.X[4] + -145523070, 6) + b;
/* 302 */     d = rotateLeft(d + K(a, b, c) + this.X[11] + -1120210379, 10) + a;
/* 303 */     c = rotateLeft(c + K(d, a, b) + this.X[2] + 718787259, 15) + d;
/* 304 */     b = rotateLeft(b + K(c, d, a) + this.X[9] + -343485551, 21) + c;
/*     */ 
/* 306 */     this.H1 += a;
/* 307 */     this.H2 += b;
/* 308 */     this.H3 += c;
/* 309 */     this.H4 += d;
/*     */ 
/* 314 */     this.xOff = 0;
/* 315 */     for (int i = 0; i != this.X.length; i++)
/*     */     {
/* 317 */       this.X[i] = 0;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.commons.httpclient.crypto.MD5Digest
 * JD-Core Version:    0.6.0
 */