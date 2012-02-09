/*     */ package com.bright.assetbank.externalfilter.util;
/*     */ 
/*     */ import com.bright.framework.util.ClassUtil;
/*     */ import com.bright.framework.util.LogUtil;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class ExternalFilterUtil
/*     */ {
/*     */   private static final String c_ksClassName = "ExternalFilterUtil";
/*  31 */   private static final Log c_logger = LogUtil.getLog(ClassUtil.currentClassName());
/*     */ 
/*     */   public static Double readDoubleParam(Map<String, String> a_externalFilterCriteria, String a_paramName, String a_sNiceParamName, List<String> a_errors)
/*     */   {
/*  39 */     String sParamValue = (String)a_externalFilterCriteria.get(a_paramName);
/*  40 */     if (StringUtils.isEmpty(sParamValue))
/*     */     {
/*  42 */       return null;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  47 */       return new Double(sParamValue);
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/*  51 */       c_logger.warn("ExternalFilterUtil: value \"" + sParamValue + "\" for parameter \"" + a_paramName + "\" could not be parsed as a Double");
/*  52 */       String sMessage = "Parameter " + a_sNiceParamName + " must be a decimal number.";
/*  53 */       a_errors.add(sMessage);
/*  54 */     }return null;
/*     */   }
/*     */ 
/*     */   public static Integer readIntegerParam(Map<String, String> a_externalFilterCriteria, String a_paramName, String a_sNiceParamName, List<String> a_errors)
/*     */   {
/*  63 */     String sParamValue = (String)a_externalFilterCriteria.get(a_paramName);
/*  64 */     if (StringUtils.isEmpty(sParamValue))
/*     */     {
/*  66 */       return null;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  71 */       return new Integer(sParamValue);
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/*  75 */       c_logger.warn("ExternalFilterUtil: value \"" + sParamValue + "\" for parameter \"" + a_paramName + "\" could not be parsed as a Integer");
/*  76 */       String sMessage = "Parameter " + a_sNiceParamName + " must be an integer number.";
/*  77 */       a_errors.add(sMessage);
/*  78 */     }return null;
/*     */   }
/*     */ 
/*     */   public static Boolean readBooleanParam(Map<String, String> a_externalFilterCriteria, String a_paramName, String a_sNiceParamName, List<String> a_errors)
/*     */   {
/*  87 */     String sParamValue = (String)a_externalFilterCriteria.get(a_paramName);
/*  88 */     if (StringUtils.isEmpty(sParamValue))
/*     */     {
/*  90 */       return null;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  95 */       return Boolean.valueOf(sParamValue);
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/*  99 */       c_logger.warn("ExternalFilterUtil: value \"" + sParamValue + "\" for parameter \"" + a_paramName + "\" could not be parsed as a Boolean");
/* 100 */       String sMessage = "Parameter " + a_sNiceParamName + " must be a boolean (\"true\" or \"false\") or empty.";
/* 101 */       a_errors.add(sMessage);
/* 102 */     }return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.externalfilter.util.ExternalFilterUtil
 * JD-Core Version:    0.6.0
 */