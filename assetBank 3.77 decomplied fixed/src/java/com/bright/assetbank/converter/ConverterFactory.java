/*     */ package com.bright.assetbank.converter;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ 
/*     */ public class ConverterFactory
/*     */ {
/*     */   private static final String c_ksClassName = "ConverterFactory";
/*     */ 
/*     */   public static AssetConverter getConverterInstance(String a_sConverterClassName)
/*     */     throws Bn2Exception
/*     */   {
/*  46 */     String ksMethodName = "getConverterInstance";
/*     */ 
/*  48 */     AssetConverter converter = null;
/*     */     try
/*     */     {
/*  53 */       Class classConverter = Class.forName(a_sConverterClassName);
/*     */ 
/*  56 */       converter = (AssetConverter)classConverter.newInstance();
/*     */     }
/*     */     catch (ClassNotFoundException cnf)
/*     */     {
/*  60 */       throw new Bn2Exception("ConverterFactory.getConverterInstance: ClassNotFoundException occurred: " + cnf, cnf);
/*     */     }
/*     */     catch (IllegalAccessException iae)
/*     */     {
/*  64 */       throw new Bn2Exception("ConverterFactory.getConverterInstance: IllegalAccessException occurred: " + iae, iae);
/*     */     }
/*     */     catch (InstantiationException ie)
/*     */     {
/*  68 */       throw new Bn2Exception("ConverterFactory.getConverterInstance: InstantiationException occurred: " + ie, ie);
/*     */     }
/*     */ 
/*  71 */     return converter;
/*     */   }
/*     */ 
/*     */   public static AssetToTextConverter getFileAssetConverterInstance(String a_sConverterClassName)
/*     */     throws Bn2Exception
/*     */   {
/*  88 */     String ksMethodName = "getFileAssetConverterInstance";
/*     */ 
/*  90 */     AssetToTextConverter converter = null;
/*     */     try
/*     */     {
/*  95 */       Class classConverter = Class.forName(a_sConverterClassName);
/*     */ 
/*  98 */       converter = (AssetToTextConverter)classConverter.newInstance();
/*     */     }
/*     */     catch (ClassNotFoundException cnf)
/*     */     {
/* 102 */       throw new Bn2Exception("ConverterFactory.getFileAssetConverterInstance: ClassNotFoundException occurred: " + cnf, cnf);
/*     */     }
/*     */     catch (IllegalAccessException iae)
/*     */     {
/* 106 */       throw new Bn2Exception("ConverterFactory.getFileAssetConverterInstance: IllegalAccessException occurred: " + iae, iae);
/*     */     }
/*     */     catch (InstantiationException ie)
/*     */     {
/* 110 */       throw new Bn2Exception("ConverterFactory.getFileAssetConverterInstance: InstantiationException occurred: " + ie, ie);
/*     */     }
/*     */ 
/* 113 */     return converter;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.ConverterFactory
 * JD-Core Version:    0.6.0
 */