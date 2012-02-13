/*     */ package com.bright.framework.image.util;
/*     */ 
/*     */ import com.bright.framework.image.bean.Colour;
/*     */ import com.bright.framework.image.bean.ColourImpl;
/*     */ import com.bright.framework.image.bean.MutableColour;
/*     */ 
/*     */ public class ColourUtil
/*     */ {
/*     */   public static String hexStringFromColour(Colour a_c)
/*     */   {
/*  32 */     return twoDigitHexString(a_c.getRed()) + twoDigitHexString(a_c.getGreen()) + twoDigitHexString(a_c.getBlue());
/*     */   }
/*     */ 
/*     */   private static String twoDigitHexString(int a_i)
/*     */   {
/*  42 */     String sHex = Integer.toHexString(a_i);
/*  43 */     if ((a_i < 0) || (a_i > 255))
/*     */     {
/*  45 */       throw new IllegalArgumentException("The value " + a_i + " is not in the range 0-255");
/*     */     }
/*     */ 
/*  48 */     if (a_i < 16)
/*     */     {
/*  51 */       return "0" + sHex;
/*     */     }
/*     */ 
/*  56 */     return sHex;
/*     */   }
/*     */ 
/*     */   public static Colour colourFromHexString(String a_s)
/*     */   {
/*  68 */     return populateColourFromHexString(new ColourImpl(), a_s);
/*     */   }
/*     */ 
/*     */   public static MutableColour populateColourFromHexString(MutableColour a_colour, String a_s)
/*     */   {
/*  78 */     String sSixDigitHex = a_s.trim();
/*  79 */     if (sSixDigitHex.startsWith("#"))
/*     */     {
/*  81 */       sSixDigitHex = sSixDigitHex.substring(1);
/*     */     }
/*     */ 
/*  84 */     if (sSixDigitHex.length() != 6)
/*     */     {
/*  86 */       throw new IllegalArgumentException("\"" + a_s + "\" cannot be parsed as a hex colour");
/*     */     }
/*     */ 
/*  92 */     int r = Integer.parseInt(sSixDigitHex.substring(0, 2), 16);
/*  93 */     int g = Integer.parseInt(sSixDigitHex.substring(2, 4), 16);
/*  94 */     int b = Integer.parseInt(sSixDigitHex.substring(4, 6), 16);
/*     */ 
/*  96 */     a_colour.setRed(r);
/*  97 */     a_colour.setGreen(g);
/*  98 */     a_colour.setBlue(b);
/*     */ 
/* 100 */     return a_colour;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.image.util.ColourUtil
 * JD-Core Version:    0.6.0
 */