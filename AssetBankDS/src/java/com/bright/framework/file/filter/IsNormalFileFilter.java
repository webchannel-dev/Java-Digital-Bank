/*    */ package com.bright.framework.file.filter;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ 
/*    */ public class IsNormalFileFilter
/*    */   implements FileFilter
/*    */ {
/*    */   public boolean accept(File a_file)
/*    */   {
/* 47 */     return a_file.isFile();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.file.filter.IsNormalFileFilter
 * JD-Core Version:    0.6.0
 */