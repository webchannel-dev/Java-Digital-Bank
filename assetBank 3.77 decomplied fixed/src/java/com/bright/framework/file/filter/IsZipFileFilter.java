/*    */ package com.bright.framework.file.filter;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ 
/*    */ public class IsZipFileFilter
/*    */   implements FileFilter
/*    */ {
/*    */   public boolean accept(File a_file)
/*    */   {
/* 51 */     return (a_file.isFile()) && (a_file.getName().toLowerCase().endsWith(".zip"));
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.file.filter.IsZipFileFilter
 * JD-Core Version:    0.6.0
 */