/*     */ package com.bright.framework.service;
/*     */ 
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ 
/*     */ class FileStoreManager$1StorageFileFilter
/*     */   implements FileFilter
/*     */ {
/* 363 */   private int m_iNumFiles = 0;
/* 364 */   private int m_iMaxSizeToKeep = FrameworkSettings.getDeleteStoredFilesLargerThanBytes();
/*     */ 
/*     */   public boolean accept(File pathname)
/*     */   {
/* 370 */     if ((pathname.lastModified() < this.val$a_lOlderThan) && (pathname.length() > this.m_iMaxSizeToKeep))
/*     */     {
/* 372 */       this.m_iNumFiles += 1;
/* 373 */       return true;
/*     */     }
/* 375 */     return false;
/*     */   }
/*     */ 
/*     */   public int getNumMatches()
/*     */   {
/* 380 */     return this.m_iNumFiles;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.service.FileStoreManager.1StorageFileFilter
 * JD-Core Version:    0.6.0
 */