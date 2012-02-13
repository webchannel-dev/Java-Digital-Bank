/*     */ package com.bright.framework.service;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ 
/*     */ final class FileStoreManager$2
/*     */   implements FileFilter
/*     */ {
/*     */   public boolean accept(File pathname)
/*     */   {
/* 302 */     return pathname.lastModified() < this.val$a_lOlderThan;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.service.FileStoreManager.2
 * JD-Core Version:    0.6.0
 */