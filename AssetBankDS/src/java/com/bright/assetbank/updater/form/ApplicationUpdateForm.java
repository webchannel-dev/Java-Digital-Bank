/*     */ package com.bright.assetbank.updater.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.updater.exception.UpdatePermissionDetails;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ApplicationUpdateForm extends Bn2Form
/*     */ {
/*  37 */   private Collection m_versionDetails = null;
/*     */ 
/*  40 */   private List m_updateProgressMessages = null;
/*     */ 
/*  43 */   private List m_updateErrorMessages = null;
/*     */   private int m_iUpdateStaus;
/*  49 */   private String m_sFailureError = null;
/*     */ 
/*  52 */   private boolean m_bUpToDate = false;
/*     */ 
/*  55 */   private boolean m_bUpdateDatabaseOnly = false;
/*     */ 
/*  58 */   private String m_sTomcatReloadUrl = null;
/*     */ 
/*  62 */   private String m_sContextPath = null;
/*     */ 
/*  64 */   private UpdatePermissionDetails m_updatePermissionDetails = null;
/*     */ 
/*     */   public void setVersionDetails(Collection a_VersionDetails)
/*     */   {
/*  71 */     this.m_versionDetails = a_VersionDetails;
/*     */   }
/*     */ 
/*     */   public Collection getVersionDetails()
/*     */   {
/*  80 */     return this.m_versionDetails;
/*     */   }
/*     */ 
/*     */   public List getUpdateProgressMessages()
/*     */   {
/*  88 */     return this.m_updateProgressMessages;
/*     */   }
/*     */ 
/*     */   public void setUpdateProgressMessages(List a_updateProgressMessages)
/*     */   {
/*  97 */     this.m_updateProgressMessages = a_updateProgressMessages;
/*     */   }
/*     */ 
/*     */   public String getFailureError()
/*     */   {
/* 106 */     return this.m_sFailureError;
/*     */   }
/*     */ 
/*     */   public void setFailureError(String a_sFailureError)
/*     */   {
/* 115 */     this.m_sFailureError = a_sFailureError;
/*     */   }
/*     */ 
/*     */   public void setUpdateStaus(int a_dtUpdateStaus)
/*     */   {
/* 124 */     this.m_iUpdateStaus = a_dtUpdateStaus;
/*     */   }
/*     */ 
/*     */   public boolean getUpdateInProgress()
/*     */   {
/* 133 */     return this.m_iUpdateStaus == 2;
/*     */   }
/*     */ 
/*     */   public boolean getUpdateFailed()
/*     */   {
/* 142 */     return this.m_iUpdateStaus == 3;
/*     */   }
/*     */ 
/*     */   public boolean getUpdateSucceeded()
/*     */   {
/* 151 */     return this.m_iUpdateStaus == 4;
/*     */   }
/*     */ 
/*     */   public boolean isUpToDate()
/*     */   {
/* 160 */     return this.m_bUpToDate;
/*     */   }
/*     */ 
/*     */   public void setUpToDate(boolean a_dtUpToDate)
/*     */   {
/* 169 */     this.m_bUpToDate = a_dtUpToDate;
/*     */   }
/*     */ 
/*     */   public boolean isUpdateDatabaseOnly()
/*     */   {
/* 178 */     return this.m_bUpdateDatabaseOnly;
/*     */   }
/*     */ 
/*     */   public void setUpdateDatabaseOnly(boolean a_bUpdateDatabaseOnly)
/*     */   {
/* 187 */     this.m_bUpdateDatabaseOnly = a_bUpdateDatabaseOnly;
/*     */   }
/*     */ 
/*     */   public String getContextPath()
/*     */   {
/* 196 */     return this.m_sContextPath;
/*     */   }
/*     */ 
/*     */   public void setContextPath(String a_sContextPath)
/*     */   {
/* 205 */     this.m_sContextPath = a_sContextPath;
/*     */   }
/*     */ 
/*     */   public String getTomcatReloadUrl()
/*     */   {
/* 214 */     return this.m_sTomcatReloadUrl;
/*     */   }
/*     */ 
/*     */   public void setTomcatReloadUrl(String a_sTomcatReloadUrl)
/*     */   {
/* 223 */     this.m_sTomcatReloadUrl = a_sTomcatReloadUrl;
/*     */   }
/*     */ 
/*     */   public UpdatePermissionDetails getUpdatePermissionDetails()
/*     */   {
/* 232 */     return this.m_updatePermissionDetails;
/*     */   }
/*     */ 
/*     */   public void setUpdatePermissionDetails(UpdatePermissionDetails a_updatePermissionDetails)
/*     */   {
/* 241 */     this.m_updatePermissionDetails = a_updatePermissionDetails;
/*     */   }
/*     */ 
/*     */   public List getUpdateErrorMessages()
/*     */   {
/* 250 */     return this.m_updateErrorMessages;
/*     */   }
/*     */ 
/*     */   public void setUpdateErrorMessages(List a_updateErrorMessages)
/*     */   {
/* 259 */     this.m_updateErrorMessages = a_updateErrorMessages;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.form.ApplicationUpdateForm
 * JD-Core Version:    0.6.0
 */