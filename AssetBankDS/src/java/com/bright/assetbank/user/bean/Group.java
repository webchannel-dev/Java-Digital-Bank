/*     */ package com.bright.assetbank.user.bean;
/*     */ 
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnitMetadata;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.user.bean.RemoteUserGroup;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class Group extends RemoteUserGroup
/*     */   implements UserConstants
/*     */ {
/*  53 */   private String m_sName = "";
/*  54 */   private String m_sDescription = "";
/*     */ 
/*  56 */   private long m_lMaxDownloads = 0L;
/*     */ 
/*  59 */   private boolean m_bIsDefaultGroup = false;
/*     */ 
/*  61 */   private Vector<GroupCategoryPermission> m_vecCategoryPermissions = null;
/*  62 */   private OrgUnitMetadata m_orgUnitReference = null;
/*  63 */   private boolean m_bCanDelete = true;
/*  64 */   private StringDataBean m_brandReference = null;
/*     */ 
/*  67 */   private boolean m_bOrgUnitAdminGroup = false;
/*     */ 
/*  70 */   private String m_sIpMapping = null;
/*  71 */   private String[] m_asIPMappingValues = null;
/*  72 */   private String m_sUrlMapping = null;
/*  73 */   private String[] m_asURLMappingValues = null;
/*     */ 
/*  75 */   private Vector<Role> m_vecRoles = null;
/*     */ 
/*  77 */   private int m_iDiscountPercentage = 0;
/*     */ 
/*  79 */   private boolean m_bUserCanOnlyEditOwnFiles = false;
/*  80 */   private boolean m_bUserCanEmailAssets = false;
/*  81 */   private boolean m_bUserCanRepurposeAssets = false;
/*  82 */   private boolean m_bUsersCanSeeSourcePath = false;
/*  83 */   private boolean m_bUsersCanInviteUsers = false;
/*  84 */   private boolean m_bUsersCanAddEmptyAssets = false;
/*  85 */   private boolean m_bUsersCanViewLargerSize = false;
/*     */ 
/*  87 */   private boolean m_bAutomaticBrandRegister = false;
/*     */ 
/*  89 */   private int m_iMaxDownloadHeight = -1;
/*  90 */   private int m_iMaxDownloadWidth = -1;
/*     */ 
/*  92 */   private boolean m_bAdvancedViewing = false;
/*     */ 
/*     */   public Group()
/*     */   {
/*  99 */     this.m_orgUnitReference = new OrgUnitMetadata();
/* 100 */     this.m_brandReference = new StringDataBean();
/*     */   }
/*     */ 
/*     */   public OrgUnitMetadata getOrgUnitReference()
/*     */   {
/* 105 */     return this.m_orgUnitReference;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitReference(OrgUnitMetadata a_orgUnitReference) {
/* 109 */     this.m_orgUnitReference = a_orgUnitReference;
/*     */   }
/*     */ 
/*     */   public StringDataBean getBrandReference()
/*     */   {
/* 115 */     return this.m_brandReference;
/*     */   }
/*     */ 
/*     */   public void setBrandReference(StringDataBean a_sBrandReference)
/*     */   {
/* 120 */     this.m_brandReference = a_sBrandReference;
/*     */   }
/*     */ 
/*     */   public void setName(String a_sName)
/*     */   {
/* 125 */     this.m_sName = a_sName;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 130 */     return this.m_sName;
/*     */   }
/*     */ 
/*     */   public void setDescription(String a_sDescription)
/*     */   {
/* 135 */     this.m_sDescription = a_sDescription;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 140 */     return this.m_sDescription;
/*     */   }
/*     */ 
/*     */   public void setMaxDownloads(long a_lMaxDownloads)
/*     */   {
/* 145 */     this.m_lMaxDownloads = a_lMaxDownloads;
/*     */   }
/*     */ 
/*     */   public long getMaxDownloads()
/*     */   {
/* 150 */     return this.m_lMaxDownloads;
/*     */   }
/*     */ 
/*     */   public void setCategoryPermissions(Vector<GroupCategoryPermission> a_vecCategoryPermissions)
/*     */   {
/* 155 */     this.m_vecCategoryPermissions = a_vecCategoryPermissions;
/*     */   }
/*     */ 
/*     */   public Vector<GroupCategoryPermission> getCategoryPermissions()
/*     */   {
/* 160 */     return this.m_vecCategoryPermissions;
/*     */   }
/*     */ 
/*     */   public boolean getIsDefaultGroup()
/*     */   {
/* 166 */     return this.m_bIsDefaultGroup;
/*     */   }
/*     */ 
/*     */   public void setIsDefaultGroup(boolean a_sIsDefaultGroup)
/*     */   {
/* 172 */     this.m_bIsDefaultGroup = a_sIsDefaultGroup;
/*     */   }
/*     */ 
/*     */   public void setCanDelete(boolean a_bCanDelete)
/*     */   {
/* 177 */     this.m_bCanDelete = a_bCanDelete;
/*     */   }
/*     */ 
/*     */   public boolean getCanDelete()
/*     */   {
/* 182 */     return this.m_bCanDelete;
/*     */   }
/*     */ 
/*     */   public String getNameWithOrgUnit()
/*     */   {
/* 187 */     String sNameWithOrgUnit = "";
/* 188 */     if (StringUtil.stringIsPopulated(this.m_orgUnitReference.getName()))
/*     */     {
/* 190 */       sNameWithOrgUnit = this.m_orgUnitReference.getName();
/*     */     }
/*     */ 
/* 193 */     if (StringUtil.stringIsPopulated(this.m_sName))
/*     */     {
/* 195 */       if (StringUtil.stringIsPopulated(sNameWithOrgUnit))
/*     */       {
/* 197 */         sNameWithOrgUnit = sNameWithOrgUnit + " " + AssetBankSettings.getOrgUnitGroupSeparator() + " ";
/*     */       }
/* 199 */       sNameWithOrgUnit = sNameWithOrgUnit + this.m_sName;
/*     */     }
/*     */ 
/* 202 */     return sNameWithOrgUnit;
/*     */   }
/*     */ 
/*     */   public String getIpMapping()
/*     */   {
/* 208 */     return this.m_sIpMapping;
/*     */   }
/*     */ 
/*     */   public void setIpMapping(String a_sIPMapping) {
/* 212 */     this.m_sIpMapping = a_sIPMapping;
/*     */   }
/*     */ 
/*     */   public boolean getHasIpMapping()
/*     */   {
/* 217 */     boolean bHasMapping = (this.m_sIpMapping != null) && (this.m_sIpMapping.length() > 0);
/* 218 */     return bHasMapping;
/*     */   }
/*     */ 
/*     */   public String[] getIpMappingValues(String a_sDelimiter)
/*     */   {
/* 230 */     if (this.m_asIPMappingValues == null)
/*     */     {
/* 232 */       if (this.m_sIpMapping == null)
/*     */       {
/* 234 */         return null;
/*     */       }
/* 236 */       if ((a_sDelimiter == null) || (a_sDelimiter.length() == 0))
/*     */       {
/* 238 */         this.m_asIPMappingValues = new String[1];
/* 239 */         this.m_asIPMappingValues[0] = this.m_sIpMapping;
/*     */       }
/*     */       else
/*     */       {
/* 243 */         this.m_asIPMappingValues = this.m_sIpMapping.split(a_sDelimiter);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 248 */     for (int i = 0; i < this.m_asIPMappingValues.length; i++)
/*     */     {
/* 250 */       this.m_asIPMappingValues[i] = this.m_asIPMappingValues[i].replaceAll("\\r", "");
/*     */     }
/*     */ 
/* 254 */     return this.m_asIPMappingValues;
/*     */   }
/*     */ 
/*     */   public boolean isIpIncluded(String a_sIPAddress)
/*     */   {
/* 266 */     boolean bIsIncluded = false;
/*     */ 
/* 268 */     String[] ranges = getIpMappingValues("\\n");
/* 269 */     if (ranges != null)
/*     */     {
/* 271 */       for (int i = 0; i < ranges.length; i++)
/*     */       {
/* 273 */         if ((ranges[i] == null) || (ranges[i].length() <= 0))
/*     */           continue;
/* 275 */         String[] range = ranges[i].split("-");
/* 276 */         int iLowComp = StringUtil.compareDotNotationNumbers(a_sIPAddress, range[0]);
/*     */ 
/* 278 */         if (((range.length != 1) || (iLowComp != 0)) && ((range.length != 2) || (iLowComp < 0) || (StringUtil.compareDotNotationNumbers(a_sIPAddress, range[1]) > 0))) {
/*     */           continue;
/*     */         }
/* 281 */         bIsIncluded = true;
/* 282 */         break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 288 */     return bIsIncluded;
/*     */   }
/*     */ 
/*     */   public String getUrlMapping()
/*     */   {
/* 296 */     return this.m_sUrlMapping;
/*     */   }
/*     */ 
/*     */   public void setUrlMapping(String a_sURLMapping) {
/* 300 */     this.m_sUrlMapping = a_sURLMapping;
/*     */   }
/*     */ 
/*     */   public void setDiscountPercentage(int a_iDiscountPercentage)
/*     */   {
/* 305 */     this.m_iDiscountPercentage = a_iDiscountPercentage;
/*     */   }
/*     */ 
/*     */   public int getDiscountPercentage()
/*     */   {
/* 310 */     return this.m_iDiscountPercentage;
/*     */   }
/*     */ 
/*     */   public String[] getUrlMappingValues(String a_sDelimiter)
/*     */   {
/* 322 */     if (this.m_asURLMappingValues == null)
/*     */     {
/* 324 */       if ((a_sDelimiter == null) || (a_sDelimiter.length() == 0))
/*     */       {
/* 326 */         this.m_asURLMappingValues = new String[1];
/* 327 */         this.m_asURLMappingValues[0] = this.m_sUrlMapping;
/*     */       }
/*     */       else
/*     */       {
/* 331 */         this.m_asURLMappingValues = this.m_sUrlMapping.split(a_sDelimiter);
/*     */       }
/*     */     }
/*     */ 
/* 335 */     return this.m_asURLMappingValues;
/*     */   }
/*     */ 
/*     */   public void setRoles(Vector<Role> a_vecRoles)
/*     */   {
/* 341 */     this.m_vecRoles = a_vecRoles;
/*     */   }
/*     */ 
/*     */   public Vector<Role> getRoles()
/*     */   {
/* 346 */     return this.m_vecRoles;
/*     */   }
/*     */ 
/*     */   public boolean getHasRole(int a_iRoleId)
/*     */   {
/* 351 */     if ((getRoles() != null) && (getRoles().size() > 0))
/*     */     {
/* 353 */       for (int i = 0; i < getRoles().size(); i++)
/*     */       {
/* 355 */         Role role = (Role)getRoles().elementAt(i);
/* 356 */         if (role.getId() == a_iRoleId)
/*     */         {
/* 358 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 362 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getUserCanOnlyEditOwnFiles()
/*     */   {
/* 367 */     return this.m_bUserCanOnlyEditOwnFiles;
/*     */   }
/*     */ 
/*     */   public void setUserCanOnlyEditOwnFiles(boolean a_sUserCanOnlyEditOwnFiles)
/*     */   {
/* 372 */     this.m_bUserCanOnlyEditOwnFiles = a_sUserCanOnlyEditOwnFiles;
/*     */   }
/*     */ 
/*     */   public boolean getUserCanEmailAssets()
/*     */   {
/* 377 */     return this.m_bUserCanEmailAssets;
/*     */   }
/*     */ 
/*     */   public void setUserCanEmailAssets(boolean a_bUserCanEmailAssets)
/*     */   {
/* 382 */     this.m_bUserCanEmailAssets = a_bUserCanEmailAssets;
/*     */   }
/*     */ 
/*     */   public boolean getOrgUnitAdminGroup()
/*     */   {
/* 387 */     return this.m_bOrgUnitAdminGroup;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitAdminGroup(boolean a_sOrgUnitAdminGroup)
/*     */   {
/* 392 */     this.m_bOrgUnitAdminGroup = a_sOrgUnitAdminGroup;
/*     */   }
/*     */ 
/*     */   public boolean getUserCanRepurposeAssets()
/*     */   {
/* 397 */     return this.m_bUserCanRepurposeAssets;
/*     */   }
/*     */ 
/*     */   public void setUserCanRepurposeAssets(boolean a_bUserCanRepurposeAssets)
/*     */   {
/* 402 */     this.m_bUserCanRepurposeAssets = a_bUserCanRepurposeAssets;
/*     */   }
/*     */ 
/*     */   public boolean getUsersCanSeeSourcePath()
/*     */   {
/* 407 */     return this.m_bUsersCanSeeSourcePath;
/*     */   }
/*     */ 
/*     */   public void setUsersCanSeeSourcePath(boolean a_bUsersCanSeeSourcePath)
/*     */   {
/* 412 */     this.m_bUsersCanSeeSourcePath = a_bUsersCanSeeSourcePath;
/*     */   }
/*     */ 
/*     */   public boolean getUsersCanInviteUsers()
/*     */   {
/* 417 */     return this.m_bUsersCanInviteUsers;
/*     */   }
/*     */ 
/*     */   public void setUsersCanInviteUsers(boolean a_bUsersCanInviteUsers)
/*     */   {
/* 422 */     this.m_bUsersCanInviteUsers = a_bUsersCanInviteUsers;
/*     */   }
/*     */ 
/*     */   public void setMaxDownloadHeight(int a_iMaxDownloadHeight)
/*     */   {
/* 427 */     this.m_iMaxDownloadHeight = a_iMaxDownloadHeight;
/*     */   }
/*     */ 
/*     */   public int getMaxDownloadHeight()
/*     */   {
/* 432 */     return this.m_iMaxDownloadHeight;
/*     */   }
/*     */ 
/*     */   public void setMaxDownloadWidth(int a_iMaxDownloadWidth)
/*     */   {
/* 437 */     this.m_iMaxDownloadWidth = a_iMaxDownloadWidth;
/*     */   }
/*     */ 
/*     */   public int getMaxDownloadWidth()
/*     */   {
/* 442 */     return this.m_iMaxDownloadWidth;
/*     */   }
/*     */ 
/*     */   public boolean getUsersCanAddEmptyAssets()
/*     */   {
/* 447 */     return this.m_bUsersCanAddEmptyAssets;
/*     */   }
/*     */ 
/*     */   public void setUsersCanAddEmptyAssets(boolean a_sUsersCanAddEmptyAssets)
/*     */   {
/* 452 */     this.m_bUsersCanAddEmptyAssets = a_sUsersCanAddEmptyAssets;
/*     */   }
/*     */ 
/*     */   public boolean getUsersCanViewLargerSize()
/*     */   {
/* 457 */     return this.m_bUsersCanViewLargerSize;
/*     */   }
/*     */ 
/*     */   public void setUsersCanViewLargerSize(boolean a_bUsersCanViewLargerSize)
/*     */   {
/* 462 */     this.m_bUsersCanViewLargerSize = a_bUsersCanViewLargerSize;
/*     */   }
/*     */ 
/*     */   public void setAutomaticBrandRegister(boolean a_bAutomaticBrandRegister)
/*     */   {
/* 467 */     this.m_bAutomaticBrandRegister = a_bAutomaticBrandRegister;
/*     */   }
/*     */ 
/*     */   public boolean getAutomaticBrandRegister()
/*     */   {
/* 472 */     return this.m_bAutomaticBrandRegister;
/*     */   }
/*     */ 
/*     */   public void setAdvancedViewing(boolean a_bAdvancedViewing)
/*     */   {
/* 477 */     this.m_bAdvancedViewing = a_bAdvancedViewing;
/*     */   }
/*     */ 
/*     */   public boolean getAdvancedViewing()
/*     */   {
/* 489 */     return this.m_bAdvancedViewing;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.bean.Group
 * JD-Core Version:    0.6.0
 */