/*     */ package com.bright.assetbank.synchronise.bean;
/*     */ 
/*     */ import com.bright.framework.queue.bean.QueuedItem;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class ExportInfo extends QueuedItem
/*     */ {
/*  32 */   private User m_user = null;
/*  33 */   private Vector m_vecAssetIds = null;
/*  34 */   private String m_sName = null;
/*  35 */   private String m_sDescription = null;
/*  36 */   private boolean[] m_aExportFiles = null;
/*  37 */   private boolean m_bDataOnly = true;
/*  38 */   private boolean m_bExportFilesInZips = true;
/*  39 */   private boolean m_bAllLanguages = false;
/*     */ 
/*     */   public User getUser()
/*     */   {
/*  43 */     return this.m_user;
/*     */   }
/*     */ 
/*     */   public void setUser(User a_user) {
/*  47 */     this.m_user = a_user;
/*     */   }
/*     */ 
/*     */   public Vector getAssetIds()
/*     */   {
/*  52 */     return this.m_vecAssetIds;
/*     */   }
/*     */ 
/*     */   public void setAssetIds(Vector a_vecAssetIds)
/*     */   {
/*  57 */     this.m_vecAssetIds = a_vecAssetIds;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  62 */     return this.m_sName;
/*     */   }
/*     */ 
/*     */   public void setName(String a_sName)
/*     */   {
/*  67 */     this.m_sName = a_sName;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  72 */     return this.m_sDescription;
/*     */   }
/*     */ 
/*     */   public void setDescription(String a_sDescription)
/*     */   {
/*  77 */     this.m_sDescription = a_sDescription;
/*     */   }
/*     */ 
/*     */   public boolean[] getExportFiles()
/*     */   {
/*  82 */     return this.m_aExportFiles;
/*     */   }
/*     */ 
/*     */   public void setExportFiles(boolean[] a_aExportFiles)
/*     */   {
/*  87 */     this.m_aExportFiles = a_aExportFiles;
/*     */   }
/*     */ 
/*     */   public boolean getDataOnly()
/*     */   {
/*  92 */     return this.m_bDataOnly;
/*     */   }
/*     */ 
/*     */   public void setDataOnly(boolean a_bDataOnly)
/*     */   {
/*  97 */     this.m_bDataOnly = a_bDataOnly;
/*     */   }
/*     */ 
/*     */   public boolean isAllLanguages()
/*     */   {
/* 102 */     return this.m_bAllLanguages;
/*     */   }
/*     */ 
/*     */   public void setAllLanguages(boolean allLanguages)
/*     */   {
/* 107 */     this.m_bAllLanguages = allLanguages;
/*     */   }
/*     */ 
/*     */   public boolean getExportFilesInZips()
/*     */   {
/* 112 */     return this.m_bExportFilesInZips;
/*     */   }
/*     */ 
/*     */   public void setExportFilesInZips(boolean a_bExportFilesInZips)
/*     */   {
/* 117 */     this.m_bExportFilesInZips = a_bExportFilesInZips;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.ExportInfo
 * JD-Core Version:    0.6.0
 */