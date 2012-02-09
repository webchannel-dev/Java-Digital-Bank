/*    */ package com.bright.framework.file.filter;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ 
/*    */ public class IsDirectoryFilter
/*    */   implements FileFilter
/*    */ {
/*    */   public boolean accept(File a_file)
/*    */   {
/* 47 */     return a_file.isDirectory();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.file.filter.IsDirectoryFilter
 * JD-Core Version:    0.6.0
 */