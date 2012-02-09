/*     */ package com.bright.framework.file;
/*     */ 
/*     */ public class ExcelFormat
/*     */   implements FileFormat
/*     */ {
/*     */   public String getFieldDelimiter()
/*     */   {
/*  43 */     return "\t";
/*     */   }
/*     */ 
/*     */   public String getRecordDelimiter()
/*     */   {
/*  59 */     return "\n";
/*     */   }
/*     */ 
/*     */   public String getFilenameExtension()
/*     */   {
/*  74 */     return "xls";
/*     */   }
/*     */ 
/*     */   public String getLiteralDelimiter()
/*     */   {
/*  83 */     return "\"";
/*     */   }
/*     */ 
/*     */   public String getLiteralDelimiterEnd()
/*     */   {
/*  92 */     return getLiteralDelimiter();
/*     */   }
/*     */ 
/*     */   public String getLiteralDelimiterEscapedReplacement()
/*     */   {
/* 101 */     return "\"\"";
/*     */   }
/*     */ 
/*     */   public String getLiteralDelimiterEndEscapedReplacement()
/*     */   {
/* 110 */     return getLiteralDelimiterEscapedReplacement();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.file.ExcelFormat
 * JD-Core Version:    0.6.0
 */