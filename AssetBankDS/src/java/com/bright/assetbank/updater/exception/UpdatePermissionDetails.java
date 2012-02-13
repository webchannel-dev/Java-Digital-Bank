/*     */ package com.bright.assetbank.updater.exception;
/*     */ 
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class UpdatePermissionDetails
/*     */ {
/*  23 */   private boolean m_bInsufficientDatabasePermissions = false;
/*  24 */   private boolean m_bInsufficientFileSystemPermissions = false;
/*     */ 
/*  26 */   private String m_sDatabasePermissionError = null;
/*  27 */   private Vector<String> m_vecInsufficientPermissionFiles = new Vector();
/*     */ 
/*     */   public boolean getInsufficientDatabasePermissions()
/*     */   {
/*  34 */     return this.m_bInsufficientDatabasePermissions;
/*     */   }
/*     */ 
/*     */   public void setInsufficientDatabasePermissions(boolean a_bInsufficientDatabasePermissions)
/*     */   {
/*  43 */     this.m_bInsufficientDatabasePermissions = a_bInsufficientDatabasePermissions;
/*     */   }
/*     */ 
/*     */   public boolean getInsufficientFileSystemPermissions()
/*     */   {
/*  52 */     return this.m_bInsufficientFileSystemPermissions;
/*     */   }
/*     */ 
/*     */   public void setInsufficientFileSystemPermissions(boolean a_bInsufficientFileSystemPermissions)
/*     */   {
/*  60 */     this.m_bInsufficientFileSystemPermissions = a_bInsufficientFileSystemPermissions;
/*     */   }
/*     */ 
/*     */   public String getDatabasePermissionError()
/*     */   {
/*  71 */     return this.m_sDatabasePermissionError;
/*     */   }
/*     */ 
/*     */   public void setDatabasePermissionError(String a_sDatabasePermissionError)
/*     */   {
/*  79 */     this.m_sDatabasePermissionError = a_sDatabasePermissionError;
/*     */   }
/*     */ 
/*     */   public Vector<String> getInsufficientPermissionFiles()
/*     */   {
/*  89 */     return this.m_vecInsufficientPermissionFiles;
/*     */   }
/*     */ 
/*     */   public void setInsufficientPermissionFiles(Vector<String> a_vecInsufficientPermissionFiles)
/*     */   {
/*  97 */     this.m_vecInsufficientPermissionFiles = a_vecInsufficientPermissionFiles;
/*     */   }
/*     */ 
/*     */   public boolean getInsufficientPermissions()
/*     */   {
/* 105 */     return (this.m_bInsufficientDatabasePermissions) || (this.m_bInsufficientFileSystemPermissions);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.exception.UpdatePermissionDetails
 * JD-Core Version:    0.6.0
 */