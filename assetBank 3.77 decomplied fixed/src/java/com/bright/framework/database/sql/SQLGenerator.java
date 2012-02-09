/*     */ package com.bright.framework.database.sql;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ 
/*     */ public class SQLGenerator
/*     */ {
/*     */   private static final String c_ksClassName = "SQLGenerator";
/*  33 */   private static ApplicationSql c_instance = null;
/*     */ 
/*     */   public static void initialise(String a_sClassName)
/*     */     throws Bn2Exception
/*     */   {
/*  64 */     String ksMethodName = "initialise";
/*     */ 
/*  67 */     if (c_instance == null)
/*     */     {
/*     */       try
/*     */       {
/*  72 */         Class classSqlGenerator = Class.forName(a_sClassName);
/*     */ 
/*  75 */         c_instance = (ApplicationSql)classSqlGenerator.newInstance();
/*     */       }
/*     */       catch (ClassNotFoundException cnf)
/*     */       {
/*  79 */         throw new Bn2Exception("SQLGenerator.initialise: ClassNotFoundException occurred: " + cnf, cnf);
/*     */       }
/*     */       catch (IllegalAccessException iae)
/*     */       {
/*  83 */         throw new Bn2Exception("SQLGenerator.initialise: IllegalAccessException occurred: " + iae, iae);
/*     */       }
/*     */       catch (InstantiationException ie)
/*     */       {
/*  87 */         throw new Bn2Exception("SQLGenerator.initialise: InstantiationException occurred: " + ie, ie);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ApplicationSql getInstance()
/*     */     throws Bn2Exception
/*     */   {
/* 106 */     if (c_instance == null)
/*     */     {
/* 108 */       initialise(FrameworkSettings.getSqlGeneratorClassName());
/*     */     }
/*     */ 
/* 111 */     return c_instance;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.sql.SQLGenerator
 * JD-Core Version:    0.6.0
 */