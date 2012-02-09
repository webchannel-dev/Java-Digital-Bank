/*     */ package com.bright.framework.storage.bean;
/*     */ 
/*     */ public class FileInStorageDevice
/*     */ {
/*  13 */   private String m_sPath = null;
/*  14 */   private String m_sDriveOrServer = null;
/*  15 */   private String m_sPathWithoutDrive = null;
/*  16 */   private boolean m_bUserIsOnWindows = false;
/*     */ 
/*     */   public FileInStorageDevice(String a_sPath, boolean a_bUserIsOnWindows)
/*     */   {
/*  20 */     setPath(a_sPath);
/*  21 */     setUserIsOnWindows(a_bUserIsOnWindows);
/*     */   }
/*     */ 
/*     */   public String getDriveOrServer()
/*     */   {
/*  26 */     return this.m_sDriveOrServer;
/*     */   }
/*     */ 
/*     */   public String getPathWithoutDrive()
/*     */   {
/*  31 */     return this.m_sPathWithoutDrive;
/*     */   }
/*     */ 
/*     */   public String getPathWithoutDriveFormatted()
/*     */   {
/*  36 */     if (this.m_sPathWithoutDrive == null)
/*     */     {
/*  38 */       return null;
/*     */     }
/*     */ 
/*  41 */     String sFormattedPath = null;
/*     */ 
/*  43 */     if (this.m_bUserIsOnWindows)
/*     */     {
/*  46 */       sFormattedPath = this.m_sPathWithoutDrive.replace('/', '\\');
/*     */     }
/*     */     else
/*     */     {
/*  51 */       sFormattedPath = this.m_sPathWithoutDrive.replace('\\', '/');
/*     */     }
/*     */ 
/*  54 */     return sFormattedPath;
/*     */   }
/*     */ 
/*     */   public String getPathFormatted()
/*     */   {
/*  59 */     if (this.m_sPath == null)
/*     */     {
/*  61 */       return null;
/*     */     }
/*     */ 
/*  64 */     String sFormattedPath = null;
/*     */ 
/*  66 */     if (this.m_bUserIsOnWindows)
/*     */     {
/*  69 */       sFormattedPath = this.m_sPath.replace('/', '\\');
/*     */     }
/*     */     else
/*     */     {
/*  74 */       sFormattedPath = this.m_sPath.replace('\\', '/');
/*     */     }
/*     */ 
/*  77 */     return sFormattedPath;
/*     */   }
/*     */ 
/*     */   public String getPath()
/*     */   {
/*  82 */     return this.m_sPath;
/*     */   }
/*     */ 
/*     */   public void setPath(String a_sPath)
/*     */   {
/*  87 */     this.m_sPath = a_sPath;
/*     */ 
/*  89 */     parsePath();
/*     */   }
/*     */ 
/*     */   private void parsePath()
/*     */   {
/* 100 */     if ((this.m_sPath == null) || (this.m_sPath.length() == 0))
/*     */     {
/* 102 */       return;
/*     */     }
/*     */ 
/* 105 */     int iStartOfPathPos = 0;
/*     */ 
/* 108 */     if ((this.m_sPath.length() >= 2) && (this.m_sPath.substring(1, 2).equals(":")))
/*     */     {
/* 111 */       this.m_sDriveOrServer = ("/" + this.m_sPath.substring(0, 2));
/* 112 */       iStartOfPathPos = 2;
/*     */     }
/* 114 */     else if ((this.m_sPath.length() >= 3) && (this.m_sPath.substring(0, 2).equals("\\\\")))
/*     */     {
/* 117 */       int iThirdSlashPos = this.m_sPath.indexOf('\\', 2);
/*     */ 
/* 119 */       if (iThirdSlashPos < 0)
/*     */       {
/* 122 */         iThirdSlashPos = this.m_sPath.indexOf('/', 2);
/*     */       }
/*     */ 
/* 125 */       if (iThirdSlashPos > 0)
/*     */       {
/* 127 */         this.m_sDriveOrServer = this.m_sPath.substring(2, iThirdSlashPos);
/* 128 */         iStartOfPathPos = iThirdSlashPos;
/*     */       }
/*     */     }
/*     */ 
/* 132 */     this.m_sPathWithoutDrive = this.m_sPath.substring(iStartOfPathPos);
/*     */   }
/*     */ 
/*     */   public boolean getUserIsOnWindows()
/*     */   {
/* 138 */     return this.m_bUserIsOnWindows;
/*     */   }
/*     */ 
/*     */   public void setUserIsOnWindows(boolean a_bUserIsOnWindows)
/*     */   {
/* 143 */     this.m_bUserIsOnWindows = a_bUserIsOnWindows;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.bean.FileInStorageDevice
 * JD-Core Version:    0.6.0
 */