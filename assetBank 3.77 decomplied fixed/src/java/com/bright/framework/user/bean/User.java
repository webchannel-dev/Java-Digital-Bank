/*     */ package com.bright.framework.user.bean;
/*     */ 
/*     */ import com.bright.assetbank.user.bean.Role;
/*     */ import com.bright.framework.customfield.bean.CustomFieldSelectedValueSet;
/*     */ import com.bright.framework.customfield.bean.CustomFieldValueHolder;
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class User extends DataBean
/*     */   implements CustomFieldValueHolder
/*     */ {
/*  41 */   private String m_sDisplayName = null;
/*  42 */   private String m_sUsername = null;
/*  43 */   private String m_sPassword = null;
/*  44 */   private boolean m_bIsSuspended = false;
/*  45 */   private String m_sOrganisation = null;
/*     */ 
/*  47 */   private HashMap m_hmRoles = null;
/*  48 */   private HashMap m_hmPermissions = null;
/*     */ 
/*  50 */   private Language m_lLanguage = null;
/*     */ 
/*  52 */   private boolean m_bRemoteUser = false;
/*  53 */   private String m_sRemoteUsername = null;
/*  54 */   private int m_iRemoteServerIndex = 0;
/*  55 */   private Vector m_vGroups = null;
/*  56 */   private String m_sEmailAddress = null;
/*  57 */   private boolean m_bHidden = false;
/*  58 */   private String m_sForename = null;
/*  59 */   private String m_sSurname = null;
/*  60 */   private String m_sCommonName = null;
/*  61 */   private String m_sMailbox = null;
/*  62 */   private boolean m_bPasswordExpired = false;
/*  63 */   private int m_iSearchResultsPerPage = 0;
/*  64 */   private CustomFieldSelectedValueSet m_customFieldValues = null;
/*     */ 
/*     */   public User()
/*     */   {
/*     */   }
/*     */ 
/*     */   public User(User a_user)
/*     */   {
/*  73 */     this.m_sDisplayName = a_user.m_sDisplayName;
/*  74 */     this.m_sUsername = a_user.m_sUsername;
/*  75 */     this.m_sPassword = a_user.m_sPassword;
/*  76 */     this.m_bIsSuspended = a_user.m_bIsSuspended;
/*  77 */     this.m_hmRoles = a_user.m_hmRoles;
/*  78 */     this.m_hmPermissions = a_user.m_hmPermissions;
/*  79 */     this.m_lLanguage = a_user.m_lLanguage;
/*  80 */     this.m_bRemoteUser = a_user.m_bRemoteUser;
/*  81 */     this.m_sRemoteUsername = a_user.m_sRemoteUsername;
/*  82 */     this.m_iRemoteServerIndex = a_user.m_iRemoteServerIndex;
/*  83 */     this.m_vGroups = a_user.m_vGroups;
/*  84 */     this.m_sEmailAddress = a_user.m_sEmailAddress;
/*  85 */     this.m_bHidden = a_user.m_bHidden;
/*  86 */     this.m_sForename = a_user.m_sForename;
/*  87 */     this.m_sSurname = a_user.m_sSurname;
/*     */   }
/*     */ 
/*     */   public void addPermission(Permission a_permission)
/*     */   {
/* 102 */     if (this.m_hmPermissions == null)
/*     */     {
/* 104 */       this.m_hmPermissions = new HashMap(20);
/*     */     }
/*     */ 
/* 107 */     this.m_hmPermissions.put(new Long(a_permission.getId()), a_permission);
/*     */   }
/*     */ 
/*     */   public boolean hasPermission(long a_lPermissionId)
/*     */   {
/* 123 */     boolean bHasPermission = false;
/*     */ 
/* 125 */     if (this.m_hmPermissions != null)
/*     */     {
/* 127 */       if (this.m_hmPermissions.containsKey(new Long(a_lPermissionId)))
/*     */       {
/* 129 */         bHasPermission = true;
/*     */       }
/*     */     }
/*     */ 
/* 133 */     return bHasPermission;
/*     */   }
/*     */ 
/*     */   public Collection getPermissions()
/*     */   {
/* 149 */     if (this.m_hmPermissions == null)
/*     */     {
/* 151 */       return null;
/*     */     }
/*     */ 
/* 154 */     return this.m_hmPermissions.values();
/*     */   }
/*     */ 
/*     */   public Permission getFirstPermission()
/*     */   {
/* 170 */     if (this.m_hmPermissions == null)
/*     */     {
/* 172 */       return null;
/*     */     }
/*     */ 
/* 175 */     Iterator it = this.m_hmPermissions.values().iterator();
/* 176 */     return (Permission)it.next();
/*     */   }
/*     */ 
/*     */   public void addRole(Role a_role)
/*     */   {
/* 192 */     if (this.m_hmRoles == null)
/*     */     {
/* 194 */       this.m_hmRoles = new HashMap(20);
/*     */     }
/*     */ 
/* 197 */     this.m_hmRoles.put(new Long(a_role.getId()), a_role);
/*     */   }
/*     */ 
/*     */   public boolean hasRole(long a_lRoleId)
/*     */   {
/* 213 */     boolean bHasRole = false;
/*     */ 
/* 215 */     if (this.m_hmRoles != null)
/*     */     {
/* 217 */       if (this.m_hmRoles.containsKey(new Long(a_lRoleId)))
/*     */       {
/* 219 */         bHasRole = true;
/*     */       }
/*     */     }
/*     */ 
/* 223 */     return bHasRole;
/*     */   }
/*     */ 
/*     */   public Collection getRoles()
/*     */   {
/* 239 */     if (this.m_hmRoles == null)
/*     */     {
/* 241 */       return null;
/*     */     }
/*     */ 
/* 244 */     return this.m_hmRoles.values();
/*     */   }
/*     */ 
/*     */   public void setUsername(String a_sUsername)
/*     */   {
/* 250 */     this.m_sUsername = a_sUsername;
/*     */   }
/*     */ 
/*     */   public String getUsername() {
/* 254 */     return this.m_sUsername;
/*     */   }
/*     */ 
/*     */   public void setPassword(String a_sPassword) {
/* 258 */     this.m_sPassword = a_sPassword;
/*     */   }
/*     */ 
/*     */   public String getPassword() {
/* 262 */     return this.m_sPassword;
/*     */   }
/*     */ 
/*     */   public boolean getIsSuspended()
/*     */   {
/* 267 */     return this.m_bIsSuspended;
/*     */   }
/*     */ 
/*     */   public void setIsSuspended(boolean a_bIsSuspended) {
/* 271 */     this.m_bIsSuspended = a_bIsSuspended;
/*     */   }
/*     */ 
/*     */   public Language getLanguage() {
/* 275 */     if (this.m_lLanguage == null)
/*     */     {
/* 277 */       this.m_lLanguage = ((Language)LanguageConstants.k_defaultLanguage.clone());
/*     */     }
/* 279 */     return this.m_lLanguage;
/*     */   }
/*     */ 
/*     */   public void setLanguage(Language language)
/*     */   {
/* 284 */     this.m_lLanguage = language;
/*     */   }
/*     */ 
/*     */   public boolean isRemoteUser()
/*     */   {
/* 290 */     return this.m_bRemoteUser;
/*     */   }
/*     */ 
/*     */   public void setRemoteUser(boolean a_bIsRemoteUser)
/*     */   {
/* 296 */     this.m_bRemoteUser = a_bIsRemoteUser;
/*     */   }
/*     */ 
/*     */   public String getRemoteUsername()
/*     */   {
/* 302 */     return this.m_sRemoteUsername;
/*     */   }
/*     */ 
/*     */   public void setRemoteUsername(String a_sUsername)
/*     */   {
/* 308 */     this.m_sRemoteUsername = a_sUsername;
/*     */   }
/*     */ 
/*     */   public int getRemoteServerIndex()
/*     */   {
/* 313 */     return this.m_iRemoteServerIndex;
/*     */   }
/*     */ 
/*     */   public void setRemoteServerIndex(int a_iServerIndex)
/*     */   {
/* 318 */     this.m_iRemoteServerIndex = a_iServerIndex;
/*     */   }
/*     */ 
/*     */   public Vector getGroups()
/*     */   {
/* 324 */     return this.m_vGroups;
/*     */   }
/*     */ 
/*     */   public void setGroups(Vector a_sGroups)
/*     */   {
/* 330 */     this.m_vGroups = a_sGroups;
/*     */   }
/*     */ 
/*     */   public int getGroupCount()
/*     */   {
/* 335 */     if (getGroups() != null)
/*     */     {
/* 337 */       return getGroups().size();
/*     */     }
/* 339 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean getHidden()
/*     */   {
/* 345 */     return this.m_bHidden;
/*     */   }
/*     */ 
/*     */   public void setHidden(boolean a_bHidden)
/*     */   {
/* 351 */     this.m_bHidden = a_bHidden;
/*     */   }
/*     */ 
/*     */   public String getDisplayName()
/*     */   {
/* 357 */     return this.m_sDisplayName;
/*     */   }
/*     */ 
/*     */   public void setDisplayName(String a_sDisplayName)
/*     */   {
/* 363 */     this.m_sDisplayName = a_sDisplayName;
/*     */   }
/*     */ 
/*     */   public String getEmailAddress()
/*     */   {
/* 369 */     return this.m_sEmailAddress;
/*     */   }
/*     */ 
/*     */   public void setEmailAddress(String a_sEmailAddress)
/*     */   {
/* 375 */     this.m_sEmailAddress = a_sEmailAddress;
/*     */   }
/*     */ 
/*     */   public String getForename()
/*     */   {
/* 381 */     return this.m_sForename;
/*     */   }
/*     */ 
/*     */   public void setForename(String a_sForename)
/*     */   {
/* 387 */     this.m_sForename = a_sForename;
/*     */   }
/*     */ 
/*     */   public String getSurname()
/*     */   {
/* 393 */     return this.m_sSurname;
/*     */   }
/*     */ 
/*     */   public void setSurname(String a_sSurname)
/*     */   {
/* 399 */     this.m_sSurname = a_sSurname;
/*     */   }
/*     */ 
/*     */   public String getMailbox()
/*     */   {
/* 405 */     return this.m_sMailbox;
/*     */   }
/*     */ 
/*     */   public void setMailbox(String a_sMailbox)
/*     */   {
/* 411 */     this.m_sMailbox = a_sMailbox;
/*     */   }
/*     */ 
/*     */   public boolean getHasRole(int a_iRoleId)
/*     */   {
/* 416 */     return hasRole(a_iRoleId);
/*     */   }
/*     */ 
/*     */   public String getCommonName()
/*     */   {
/* 422 */     return this.m_sCommonName;
/*     */   }
/*     */ 
/*     */   public void setCommonName(String a_sCn)
/*     */   {
/* 428 */     this.m_sCommonName = a_sCn;
/*     */   }
/*     */ 
/*     */   public void setPasswordExpired(boolean a_bPasswordExpired)
/*     */   {
/* 433 */     this.m_bPasswordExpired = a_bPasswordExpired;
/*     */   }
/*     */ 
/*     */   public boolean getPasswordExpired()
/*     */   {
/* 438 */     return this.m_bPasswordExpired;
/*     */   }
/*     */ 
/*     */   public int getSearchResultsPerPage()
/*     */   {
/* 443 */     return this.m_iSearchResultsPerPage;
/*     */   }
/*     */ 
/*     */   public void setSearchResultsPerPage(int a_iSearchResultsPerPage)
/*     */   {
/* 448 */     this.m_iSearchResultsPerPage = a_iSearchResultsPerPage;
/*     */   }
/*     */ 
/*     */   public void setCustomFieldValues(CustomFieldSelectedValueSet a_customFieldValues)
/*     */   {
/* 453 */     this.m_customFieldValues = a_customFieldValues;
/*     */   }
/*     */ 
/*     */   public CustomFieldSelectedValueSet getCustomFieldValues()
/*     */   {
/* 458 */     return this.m_customFieldValues;
/*     */   }
/*     */ 
/*     */   public long getItemId()
/*     */   {
/* 463 */     return getId();
/*     */   }
/*     */ 
/*     */   public String getOrganisation()
/*     */   {
/* 469 */     return this.m_sOrganisation;
/*     */   }
/*     */ 
/*     */   public void setOrganisation(String a_sOrganisation)
/*     */   {
/* 474 */     this.m_sOrganisation = a_sOrganisation;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.bean.User
 * JD-Core Version:    0.6.0
 */