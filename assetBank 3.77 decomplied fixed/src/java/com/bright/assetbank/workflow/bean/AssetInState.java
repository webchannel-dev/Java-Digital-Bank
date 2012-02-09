/*     */ package com.bright.assetbank.workflow.bean;
/*     */ 
/*     */ import com.bright.framework.image.bean.ImageFile;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class AssetInState
/*     */ {
/*  33 */   private String m_stateName = "";
/*     */ 
/*  38 */   private String m_workflowName = "";
/*     */   private long m_assetId;
/*  48 */   private String m_matchedVariationName = "";
/*     */   private long m_assetTypeId;
/*     */   private ImageFile m_thumbnailImageFile;
/*  63 */   private String m_userName = "";
/*     */ 
/*  68 */   private String m_userEmail = "";
/*     */ 
/*  70 */   private AssetWorkflowAuditEntry m_auditEntry = null;
/*     */ 
/*  72 */   private Set<Long> m_permissionCategoryIds = new HashSet();
/*     */ 
/*  74 */   private long m_lWorkflowInfoId = -1L;
/*     */   private Date m_dateAdded;
/*     */ 
/*     */   public String getWorkflowName()
/*     */   {
/*  83 */     return this.m_workflowName;
/*     */   }
/*     */ 
/*     */   public void setWorkflowName(String a_sWorkflowName)
/*     */   {
/*  93 */     this.m_workflowName = a_sWorkflowName;
/*     */   }
/*     */ 
/*     */   public String getStateName()
/*     */   {
/* 103 */     return this.m_stateName;
/*     */   }
/*     */ 
/*     */   public void setStateName(String a_sStateName)
/*     */   {
/* 113 */     this.m_stateName = a_sStateName;
/*     */   }
/*     */ 
/*     */   public long getAssetId()
/*     */   {
/* 123 */     return this.m_assetId;
/*     */   }
/*     */ 
/*     */   public void setAssetId(long a_sAssetId)
/*     */   {
/* 133 */     this.m_assetId = a_sAssetId;
/*     */   }
/*     */ 
/*     */   public String getMatchedVariationName()
/*     */   {
/* 144 */     return this.m_matchedVariationName;
/*     */   }
/*     */ 
/*     */   public void setMatchedVariationName(String a_sMatchedVariationName)
/*     */   {
/* 154 */     this.m_matchedVariationName = a_sMatchedVariationName;
/*     */   }
/*     */ 
/*     */   public long getAssetTypeId()
/*     */   {
/* 165 */     return this.m_assetTypeId;
/*     */   }
/*     */ 
/*     */   public void setAssetTypeId(long a_sAssetTypeId)
/*     */   {
/* 175 */     this.m_assetTypeId = a_sAssetTypeId;
/*     */   }
/*     */ 
/*     */   public ImageFile getThumbnailImageFile()
/*     */   {
/* 186 */     return this.m_thumbnailImageFile;
/*     */   }
/*     */ 
/*     */   public void setThumbnailImageFile(ImageFile a_sThumbnailImageFile)
/*     */   {
/* 196 */     this.m_thumbnailImageFile = a_sThumbnailImageFile;
/*     */   }
/*     */ 
/*     */   public String getUserName()
/*     */   {
/* 207 */     return this.m_userName;
/*     */   }
/*     */ 
/*     */   public void setUserName(String a_sUserName)
/*     */   {
/* 217 */     this.m_userName = a_sUserName;
/*     */   }
/*     */ 
/*     */   public String getUserEmail()
/*     */   {
/* 228 */     return this.m_userEmail;
/*     */   }
/*     */ 
/*     */   public void setUserEmail(String a_sUserEmail)
/*     */   {
/* 238 */     this.m_userEmail = a_sUserEmail;
/*     */   }
/*     */ 
/*     */   public AssetWorkflowAuditEntry getAuditEntry()
/*     */   {
/* 245 */     if (this.m_auditEntry == null)
/*     */     {
/* 247 */       this.m_auditEntry = new AssetWorkflowAuditEntry();
/*     */     }
/*     */ 
/* 250 */     return this.m_auditEntry;
/*     */   }
/*     */ 
/*     */   public void setAuditEntry(AssetWorkflowAuditEntry a_auditEntry) {
/* 254 */     this.m_auditEntry = a_auditEntry;
/*     */   }
/*     */ 
/*     */   public Set<Long> getPermissionCategoryIds()
/*     */   {
/* 259 */     return this.m_permissionCategoryIds;
/*     */   }
/*     */ 
/*     */   public void setPermissionCategoryIds(Set<Long> a_permissionCategoryIds)
/*     */   {
/* 264 */     this.m_permissionCategoryIds = a_permissionCategoryIds;
/*     */   }
/*     */ 
/*     */   public Date getDateAdded()
/*     */   {
/* 279 */     return this.m_dateAdded;
/*     */   }
/*     */ 
/*     */   public void setDateAdded(Date a_sDateAdded)
/*     */   {
/* 289 */     this.m_dateAdded = a_sDateAdded;
/*     */   }
/*     */ 
/*     */   public void setWorkflowInfoId(long a_lWorkflowInfoId)
/*     */   {
/* 294 */     this.m_lWorkflowInfoId = a_lWorkflowInfoId;
/*     */   }
/*     */ 
/*     */   public long getWorkflowInfoId()
/*     */   {
/* 299 */     return this.m_lWorkflowInfoId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.bean.AssetInState
 * JD-Core Version:    0.6.0
 */