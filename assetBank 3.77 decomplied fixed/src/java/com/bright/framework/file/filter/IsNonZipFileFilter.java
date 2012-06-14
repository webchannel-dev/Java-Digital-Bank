/*    */ package com.bright.framework.file.filter;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public class IsNonZipFileFilter extends IsZipFileFilter
/*    */ {
/* 31 */   private boolean m_bAllowDirectories = false;
/*    */ 
/*    */   public IsNonZipFileFilter(boolean a_bAllowDirectories)
/*    */   {
/* 39 */     this.m_bAllowDirectories = a_bAllowDirectories;
/*    */   }
/*    */ 
/*    */   public boolean accept(File a_file)
/*    */   {
/* 58 */     if (super.accept(a_file))
/*    */     {
/* 61 */       return false;
/*    */     }
/*    */ 
/* 65 */     if (this.m_bAllowDirectories)
/*    */     {
/* 67 */       return true;
/*    */     }
/*    */ 
/* 71 */     return a_file.isFile();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.file.filter.IsNonZipFileFilter
 * JD-Core Version:    0.6.0
 */