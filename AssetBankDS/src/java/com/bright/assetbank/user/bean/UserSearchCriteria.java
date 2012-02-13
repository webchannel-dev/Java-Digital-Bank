/*     */ package com.bright.assetbank.user.bean;
/*     */ 
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class UserSearchCriteria
/*     */ {
/*  35 */   private String m_sForename = null;
/*  36 */   private String m_sSurname = null;
/*  37 */   private String m_sUsername = null;
/*  38 */   private String m_sEmailAddress = null;
/*  39 */   private long m_lGroupId = 0L;
/*  40 */   private boolean m_bHidden = false;
/*  41 */   private Boolean m_boolNotApproved = null;
/*  42 */   private Boolean m_boolNotActiveDirectory = null;
/*  43 */   private Boolean m_boolIsAdmin = null;
/*  44 */   private Boolean m_boolRequiresUpdate = null;
/*  45 */   private long m_lCategoryId = 0L;
/*  46 */   private String m_sOrganisation = null;
/*  47 */   private long m_lAdminUserId = 0L;
/*  48 */   private long m_lDivisionId = 0L;
/*  49 */   private long m_lExcludedUserId = 0L;
/*  50 */   private Vector m_vGroupIds = null;
/*  51 */   private String[] m_asMarketingGroupIds = null;
/*  52 */   private long m_lCountryId = 0L;
/*  53 */   private boolean m_bExpired = false;
/*     */ 
/*  56 */   private int m_iPermissionInCategory = 0;
/*  57 */   private long m_lAssetId = 0L;
/*     */ 
/* 140 */   public boolean m_bIgnoreHidden = false;
/*     */ 
/*     */   public long getAssetId()
/*     */   {
/*  61 */     return this.m_lAssetId;
/*     */   }
/*     */ 
/*     */   public void setAssetId(long a_sAssetId) {
/*  65 */     this.m_lAssetId = a_sAssetId;
/*     */   }
/*     */ 
/*     */   public String getEmailAddress()
/*     */   {
/*  72 */     return this.m_sEmailAddress;
/*     */   }
/*     */ 
/*     */   public void setEmailAddress(String a_sEmailAddress)
/*     */   {
/*  78 */     this.m_sEmailAddress = a_sEmailAddress;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/*  84 */     return this.m_sUsername;
/*     */   }
/*     */ 
/*     */   public void setUsername(String a_sUsername)
/*     */   {
/*  90 */     this.m_sUsername = a_sUsername;
/*     */   }
/*     */ 
/*     */   public String getForename()
/*     */   {
/*  96 */     return this.m_sForename;
/*     */   }
/*     */ 
/*     */   public void setForename(String a_sForename)
/*     */   {
/* 102 */     this.m_sForename = a_sForename;
/*     */   }
/*     */ 
/*     */   public String getSurname()
/*     */   {
/* 108 */     return this.m_sSurname;
/*     */   }
/*     */ 
/*     */   public void setSurname(String a_sSurname)
/*     */   {
/* 114 */     this.m_sSurname = a_sSurname;
/*     */   }
/*     */ 
/*     */   public long getGroupId()
/*     */   {
/* 120 */     return this.m_lGroupId;
/*     */   }
/*     */ 
/*     */   public void setGroupId(long a_sGroupId)
/*     */   {
/* 126 */     this.m_lGroupId = a_sGroupId;
/*     */   }
/*     */ 
/*     */   public boolean getHidden()
/*     */   {
/* 132 */     return this.m_bHidden;
/*     */   }
/*     */ 
/*     */   public void setHidden(boolean a_bHidden)
/*     */   {
/* 137 */     this.m_bHidden = a_bHidden;
/*     */   }
/*     */ 
/*     */   public void setIgnoreHidden(boolean a_bIgnoreHidden)
/*     */   {
/* 143 */     this.m_bIgnoreHidden = a_bIgnoreHidden;
/*     */   }
/*     */ 
/*     */   public boolean getIgnoreHidden()
/*     */   {
/* 148 */     return this.m_bIgnoreHidden;
/*     */   }
/*     */ 
/*     */   public Boolean getNotActiveDirectory()
/*     */   {
/* 154 */     return this.m_boolNotActiveDirectory;
/*     */   }
/*     */ 
/*     */   public void setNotActiveDirectory(Boolean a_sBoolNotActiveDirectory)
/*     */   {
/* 159 */     this.m_boolNotActiveDirectory = a_sBoolNotActiveDirectory;
/*     */   }
/*     */ 
/*     */   public Boolean getNotApproved()
/*     */   {
/* 164 */     return this.m_boolNotApproved;
/*     */   }
/*     */ 
/*     */   public void setNotApproved(Boolean a_sBoolNotApproved)
/*     */   {
/* 169 */     this.m_boolNotApproved = a_sBoolNotApproved;
/*     */   }
/*     */ 
/*     */   public Boolean getRequiresUpdate()
/*     */   {
/* 175 */     return this.m_boolRequiresUpdate;
/*     */   }
/*     */ 
/*     */   public void setRequiresUpdate(Boolean a_sBoolRequiresUpdate) {
/* 179 */     this.m_boolRequiresUpdate = a_sBoolRequiresUpdate;
/*     */   }
/*     */ 
/*     */   public Boolean getIsAdmin()
/*     */   {
/* 184 */     return this.m_boolIsAdmin;
/*     */   }
/*     */ 
/*     */   public void setIsAdmin(Boolean a_boolIsAdmin)
/*     */   {
/* 189 */     this.m_boolIsAdmin = a_boolIsAdmin;
/*     */   }
/*     */ 
/*     */   public long getCategoryId()
/*     */   {
/* 194 */     return this.m_lCategoryId;
/*     */   }
/*     */ 
/*     */   public void setCategoryId(long a_lCategoryId)
/*     */   {
/* 199 */     this.m_lCategoryId = a_lCategoryId;
/*     */   }
/*     */ 
/*     */   public String getOrganisation()
/*     */   {
/* 204 */     return this.m_sOrganisation;
/*     */   }
/*     */ 
/*     */   public void setOrganisation(String a_sOrganisation)
/*     */   {
/* 209 */     this.m_sOrganisation = a_sOrganisation;
/*     */   }
/*     */ 
/*     */   public long getAdminUserId()
/*     */   {
/* 214 */     return this.m_lAdminUserId;
/*     */   }
/*     */ 
/*     */   public void setAdminUserId(long a_sAdminUserId) {
/* 218 */     this.m_lAdminUserId = a_sAdminUserId;
/*     */   }
/*     */ 
/*     */   public int getPermissionInCategory()
/*     */   {
/* 224 */     return this.m_iPermissionInCategory;
/*     */   }
/*     */ 
/*     */   public void setPermissionInCategory(int a_sPermissionInCategory) {
/* 228 */     this.m_iPermissionInCategory = a_sPermissionInCategory;
/*     */   }
/*     */ 
/*     */   public long getDivisionId()
/*     */   {
/* 233 */     return this.m_lDivisionId;
/*     */   }
/*     */ 
/*     */   public void setDivisionId(long a_sDivisionId) {
/* 237 */     this.m_lDivisionId = a_sDivisionId;
/*     */   }
/*     */ 
/*     */   public long getExcludedUserId() {
/* 241 */     return this.m_lExcludedUserId;
/*     */   }
/*     */ 
/*     */   public void setExcludedUserId(long excludedUserId) {
/* 245 */     this.m_lExcludedUserId = excludedUserId;
/*     */   }
/*     */ 
/*     */   public Vector getGroupIds()
/*     */   {
/* 250 */     return this.m_vGroupIds;
/*     */   }
/*     */ 
/*     */   public void setGroupIds(Vector a_groupIds) {
/* 254 */     this.m_vGroupIds = a_groupIds;
/*     */   }
/*     */ 
/*     */   public boolean getExpired()
/*     */   {
/* 259 */     return this.m_bExpired;
/*     */   }
/*     */ 
/*     */   public void setExpired(boolean a_bExpired) {
/* 263 */     this.m_bExpired = a_bExpired;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 278 */     return ((this.m_sForename == null) || (this.m_sForename.trim().length() == 0)) && ((this.m_sSurname == null) || (this.m_sSurname.trim().length() == 0)) && ((this.m_sUsername == null) || (this.m_sUsername.trim().length() == 0)) && ((this.m_sEmailAddress == null) || (this.m_sEmailAddress.trim().length() == 0)) && (this.m_lGroupId <= 0L) && ((this.m_asMarketingGroupIds == null) || (this.m_asMarketingGroupIds.length == 0)) && (this.m_lCountryId <= 0L);
/*     */   }
/*     */ 
/*     */   public String[] getMarketingGroupIds()
/*     */   {
/* 288 */     return this.m_asMarketingGroupIds;
/*     */   }
/*     */ 
/*     */   public void setMarketingGroupIds(String[] a_asMarketingGroupIds) {
/* 292 */     this.m_asMarketingGroupIds = a_asMarketingGroupIds;
/*     */   }
/*     */ 
/*     */   public long getCountryId() {
/* 296 */     return this.m_lCountryId;
/*     */   }
/*     */ 
/*     */   public void setCountryId(long countryId) {
/* 300 */     this.m_lCountryId = countryId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.bean.UserSearchCriteria
 * JD-Core Version:    0.6.0
 */