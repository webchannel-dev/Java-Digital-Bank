/*     */ package com.bright.assetbank.application.bean;
/*     */ 
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public class AssetFileSource
/*     */ {
/*  42 */   private String m_sFilename = null;
/*  43 */   private InputStream m_inputStream = null;
/*  44 */   private String m_sStoredFileLocation = null;
/*  45 */   private boolean m_bRemove = false;
/*  46 */   private boolean m_bRegenerate = false;
/*  47 */   private long m_lFileSize = 0L;
/*  48 */   private boolean m_bIsNewWithFixedId = false;
/*  49 */   private String m_sOriginalFilename = null;
/*  50 */   private File m_file = null;
/*     */ 
/*     */   public AssetFileSource()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AssetFileSource(File a_file)
/*     */     throws FileNotFoundException
/*     */   {
/*  61 */     if (a_file != null)
/*     */     {
/*  63 */       this.m_file = a_file;
/*  64 */       this.m_inputStream = new FileInputStream(a_file);
/*  65 */       this.m_sFilename = a_file.getName();
/*  66 */       this.m_lFileSize = a_file.length();
/*     */     }
/*     */   }
/*     */ 
/*     */   public AssetFileSource(FormFile a_formFile)
/*     */     throws FileNotFoundException, IOException
/*     */   {
/*  74 */     if (a_formFile != null)
/*     */     {
/*  76 */       this.m_inputStream = a_formFile.getInputStream();
/*  77 */       this.m_sFilename = a_formFile.getFileName();
/*  78 */       this.m_lFileSize = a_formFile.getFileSize();
/*     */     }
/*     */   }
/*     */ 
/*     */   public InputStream getInputStream()
/*     */   {
/*  85 */     return this.m_inputStream;
/*     */   }
/*     */ 
/*     */   public void setInputStream(InputStream a_sInputStream)
/*     */   {
/*  91 */     this.m_inputStream = a_sInputStream;
/*     */     try
/*     */     {
/*  95 */       this.m_lFileSize = this.m_inputStream.available();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  99 */       this.m_lFileSize = 0L;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getFilename()
/*     */   {
/* 107 */     if (this.m_sFilename != null)
/*     */     {
/* 109 */       return this.m_sFilename;
/*     */     }
/*     */ 
/* 113 */     if (this.m_sStoredFileLocation != null)
/*     */     {
/* 115 */       return FileUtil.getFilename(this.m_sStoredFileLocation);
/*     */     }
/*     */ 
/* 118 */     return null;
/*     */   }
/*     */ 
/*     */   public void setFilename(String a_sFilename)
/*     */   {
/* 124 */     this.m_sFilename = a_sFilename;
/*     */   }
/*     */ 
/*     */   public boolean isValid()
/*     */   {
/* 129 */     return (this.m_sStoredFileLocation != null) || ((this.m_inputStream != null) && (this.m_sFilename != null) && (this.m_sFilename.trim().length() > 0));
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 144 */     if (this.m_inputStream != null)
/*     */     {
/* 146 */       this.m_inputStream.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getStoredFileLocation()
/*     */   {
/* 152 */     return this.m_sStoredFileLocation;
/*     */   }
/*     */ 
/*     */   public void setStoredFileLocation(String a_sStoredFileLocation)
/*     */   {
/* 157 */     this.m_sStoredFileLocation = a_sStoredFileLocation;
/*     */   }
/*     */ 
/*     */   public boolean getRemove() {
/* 161 */     return this.m_bRemove;
/*     */   }
/*     */ 
/*     */   public void setRemove(boolean a_bRemove) {
/* 165 */     this.m_bRemove = a_bRemove;
/*     */   }
/*     */ 
/*     */   public boolean getRegenerate() {
/* 169 */     return this.m_bRegenerate;
/*     */   }
/*     */ 
/*     */   public void setRegenerate(boolean a_sRegenerate) {
/* 173 */     this.m_bRegenerate = a_sRegenerate;
/*     */   }
/*     */ 
/*     */   public long getFileSize()
/*     */   {
/* 179 */     return this.m_lFileSize;
/*     */   }
/*     */ 
/*     */   public void setFileSize(long a_lSize)
/*     */   {
/* 184 */     this.m_lFileSize = a_lSize;
/*     */   }
/*     */ 
/*     */   public void setIsNewWithFixedId(boolean a_bIsNewWithFixedId)
/*     */   {
/* 189 */     this.m_bIsNewWithFixedId = a_bIsNewWithFixedId;
/*     */   }
/*     */ 
/*     */   public boolean getIsNewWithFixedId()
/*     */   {
/* 194 */     return this.m_bIsNewWithFixedId;
/*     */   }
/*     */ 
/*     */   public void setOriginalFilename(String a_sOriginalFilename)
/*     */   {
/* 199 */     this.m_sOriginalFilename = a_sOriginalFilename;
/*     */   }
/*     */ 
/*     */   public String getOriginalFilename()
/*     */   {
/* 204 */     return this.m_sOriginalFilename;
/*     */   }
/*     */ 
/*     */   public File getFile()
/*     */   {
/* 209 */     return this.m_file;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.AssetFileSource
 * JD-Core Version:    0.6.0
 */