/*     */ package com.bright.framework.cache;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class FileCache
/*     */ {
/*  38 */   private SizeLimitedObjectCache m_files = null;
/*     */   private String m_sDir;
/*     */   private Log m_logger;
/*  41 */   private int m_iTempFileCount = 1;
/*     */ 
/*     */   public FileCache(String a_sPath, int a_iMaxSize)
/*     */   {
/*  55 */     this.m_files = new SizeLimitedObjectCache(a_iMaxSize)
/*     */     {
/*     */       public synchronized boolean removeEldestEntry(Map.Entry a_entry)
/*     */       {
/*  59 */         boolean bResult = super.removeEldestEntry(a_entry);
/*     */ 
/*  61 */         if (bResult)
/*     */         {
/*     */           try
/*     */           {
/*  65 */             FileUtils.forceDelete(new File((String)a_entry.getValue()));
/*     */           }
/*     */           catch (IOException e)
/*     */           {
/*  69 */             FileCache.this.m_logger.error("FileCache: Cannot delete file during remove operation due to IOException: " + e.getMessage(), e);
/*     */           }
/*     */         }
/*  72 */         return bResult;
/*     */       }
/*     */     };
/*  76 */     this.m_logger = GlobalApplication.getInstance().getLogger();
/*     */ 
/*  78 */     this.m_sDir = a_sPath;
/*  79 */     File fDir = new File(this.m_sDir);
/*     */ 
/*  81 */     if (fDir.exists())
/*     */     {
/*     */       try
/*     */       {
/*  85 */         FileUtils.deleteDirectory(fDir);
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/*  89 */         this.m_logger.debug("FileCache constructor: Cannot delete existing cache directory due to IOException: " + e.getMessage(), e);
/*     */       }
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  95 */       FileUtils.forceMkdir(fDir);
/*  96 */       FileUtils.forceDeleteOnExit(fDir);
/*  97 */       this.m_logger.debug("FileCache constructor: created cache directory: " + this.m_sDir);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 101 */       this.m_logger.debug("FileCache constructor: Cannot create cache directory due to IOException: " + e.getMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void cacheFile(Object a_key, String a_sFilePath)
/*     */   {
/* 113 */     String sFilename = this.m_sDir + "/" + this.m_iTempFileCount++ + "_" + FileUtil.getFilename(a_sFilePath);
/*     */     try
/*     */     {
/* 116 */       FileUtils.copyFile(new File(a_sFilePath), new File(sFilename));
/* 117 */       this.m_files.put(a_key, sFilename);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 121 */       this.m_logger.error("FileCache.cacheFile() : Cannot cache file due to IOException: " + e.getMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized String getFile(Object a_key)
/*     */     throws FileNotFoundException
/*     */   {
/* 133 */     if (containsFile(a_key))
/*     */     {
/* 135 */       return (String)this.m_files.get(a_key);
/*     */     }
/* 137 */     return null;
/*     */   }
/*     */ 
/*     */   public synchronized boolean containsFile(Object a_key)
/*     */   {
/* 147 */     boolean bFound = false;
/*     */ 
/* 150 */     if (this.m_files.containsKey(a_key))
/*     */     {
/* 152 */       String sFile = (String)this.m_files.get(a_key);
/* 153 */       if (new File(sFile).exists())
/*     */       {
/* 155 */         bFound = true;
/*     */       }
/*     */       else
/*     */       {
/* 159 */         this.m_files.remove(a_key);
/*     */       }
/*     */     }
/* 162 */     return bFound;
/*     */   }
/*     */ 
/*     */   public synchronized void clear()
/*     */   {
/* 167 */     File fDir = new File(this.m_sDir);
/*     */ 
/* 169 */     if (fDir.exists())
/*     */     {
/*     */       try
/*     */       {
/* 173 */         FileUtils.cleanDirectory(fDir);
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 177 */         this.m_logger.debug("FileCache constructor: Cannot clean existing cache directory due to IOException: " + e.getMessage(), e);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.cache.FileCache
 * JD-Core Version:    0.6.0
 */