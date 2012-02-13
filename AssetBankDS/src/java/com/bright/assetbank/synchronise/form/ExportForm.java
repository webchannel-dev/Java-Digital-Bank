/*     */ package com.bright.assetbank.synchronise.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ 
/*     */ public class ExportForm extends Bn2Form
/*     */ {
/*  29 */   private int m_iNumAssets = 0;
/*  30 */   private String m_sName = null;
/*  31 */   private String m_sAppendToFilename = null;
/*  32 */   private String m_sDescription = null;
/*  33 */   private boolean m_bExportingAssetFiles = false;
/*  34 */   private boolean m_bExportFilesInZips = false;
/*  35 */   private boolean m_bExportAllLanguages = false;
/*  36 */   private int m_iNumLanguages = 1;
/*  37 */   private String m_sExportDirectory = null;
/*     */ 
/*     */   public int getNumAssets()
/*     */   {
/*  41 */     return this.m_iNumAssets;
/*     */   }
/*     */ 
/*     */   public void setNumAssets(int a_sNumAssets)
/*     */   {
/*  46 */     this.m_iNumAssets = a_sNumAssets;
/*     */   }
/*     */ 
/*     */   public boolean isExportingAssetFiles()
/*     */   {
/*  51 */     return this.m_bExportingAssetFiles;
/*     */   }
/*     */ 
/*     */   public void setExportingAssetFiles(boolean a_sExportingAssetFiles)
/*     */   {
/*  56 */     this.m_bExportingAssetFiles = a_sExportingAssetFiles;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  61 */     return this.m_sDescription;
/*     */   }
/*     */ 
/*     */   public void setDescription(String a_sDescription)
/*     */   {
/*  66 */     this.m_sDescription = a_sDescription;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  71 */     return this.m_sName;
/*     */   }
/*     */ 
/*     */   public void setName(String a_sName)
/*     */   {
/*  76 */     this.m_sName = a_sName;
/*     */   }
/*     */ 
/*     */   public void setAppendToFilename(String a_sAppendToFilename)
/*     */   {
/*  81 */     this.m_sAppendToFilename = a_sAppendToFilename;
/*     */   }
/*     */ 
/*     */   public String getAppendToFilename()
/*     */   {
/*  86 */     return this.m_sAppendToFilename;
/*     */   }
/*     */ 
/*     */   public boolean getExportAllLanguages()
/*     */   {
/*  91 */     return this.m_bExportAllLanguages;
/*     */   }
/*     */ 
/*     */   public void setExportAllLanguages(boolean exportAllLanguages)
/*     */   {
/*  96 */     this.m_bExportAllLanguages = exportAllLanguages;
/*     */   }
/*     */ 
/*     */   public int getNumLanguages()
/*     */   {
/* 101 */     return this.m_iNumLanguages;
/*     */   }
/*     */ 
/*     */   public void setNumLanguages(int numLanguages)
/*     */   {
/* 106 */     this.m_iNumLanguages = numLanguages;
/*     */   }
/*     */ 
/*     */   public boolean getExportFilesInZips()
/*     */   {
/* 111 */     return this.m_bExportFilesInZips;
/*     */   }
/*     */ 
/*     */   public void setExportFilesInZips(boolean a_bExportAsZipFiles)
/*     */   {
/* 116 */     this.m_bExportFilesInZips = a_bExportAsZipFiles;
/*     */   }
/*     */ 
/*     */   public String getExportDirectory()
/*     */   {
/* 121 */     return this.m_sExportDirectory;
/*     */   }
/*     */ 
/*     */   public void setExportDirectory(String a_sExportDirectory)
/*     */   {
/* 126 */     this.m_sExportDirectory = a_sExportDirectory;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.form.ExportForm
 * JD-Core Version:    0.6.0
 */