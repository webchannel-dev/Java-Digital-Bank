/*     */ package com.bright.assetbank.user.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnitContent;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnitMetadata;
/*     */ import com.bright.framework.common.bean.Address;
/*     */ import com.bright.framework.common.bean.Country;
/*     */ import com.bright.framework.common.bean.RefDataItem;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class ABUser extends User
/*     */ {
/*  75 */   private boolean m_bIsAdmin = false;
/*  76 */   private boolean m_bIsOrgUnitAdmin = false;
/*  77 */   private boolean m_bIsDeleted = false;
/*  78 */   private boolean m_bNotApproved = false;
/*  79 */   private boolean m_bCanBeCategoryAdmin = false;
/*  80 */   private boolean m_bRequiresUpdate = false;
/*  81 */   private String m_sTitle = null;
/*  82 */   private String m_sAddress = null;
/*  83 */   private String m_sRegistrationInfo = null;
/*  84 */   private Date m_dtExpiryDate = null;
/*  85 */   private String m_sJobTitle = null;
/*  86 */   private String m_sTaxNumber = null;
/*  87 */   private Date m_registerDate = null;
/*  88 */   private Date m_passwordChangedDate = null;
/*  89 */   private String m_sAdminNotes = null;
/*  90 */   private long m_lLastModifiedBy = 0L;
/*  91 */   private long m_lInvitedByUserId = 0L;
/*  92 */   private User m_initiallyApprovedBy = null;
/*  93 */   private String m_sAdditionalApproverDetails = null;
//added
            private String m_sTelephone = null;
            private String m_sFax = null;
/*     */ 
/*  96 */   private Vector<? extends OrgUnitMetadata> m_vOrgUnits = null;
/*     */ 
/*  99 */   private long m_lRequestedOrgUnitId = 0L;
/*     */ 
/* 102 */   private RefDataItem m_refDivision = null;
/*     */ 
/* 105 */   private Address m_homeAddress = null;
/*     */ 
/* 108 */   private boolean m_bCanLoginFromCms = false;
/*     */ 
/* 111 */   private boolean m_bReceiveAlerts = false;
/*     */ 
/* 113 */   private boolean m_bReactivationPending = false;
/*     */ 
/* 115 */   private long m_lNewAssetBoxId = 0L;
/*     */ 
/*     */   public ABUser()
/*     */   {
/* 124 */     this.m_refDivision = new RefDataItem();
/* 125 */     this.m_homeAddress = new Address();
/*     */ 
/* 128 */     setLanguage((Language)LanguageConstants.k_defaultLanguage.clone());
/*     */   }
/*     */ 
/*     */   public ABUser(long a_lUserId)
/*     */   {
/* 136 */     this();
/* 137 */     setId(a_lUserId);
/*     */   }
/*     */ 
/*     */   public String getRegistrationInfoAsHtml()
/*     */   {
/* 152 */     if (this.m_sRegistrationInfo == null)
/*     */     {
/* 154 */       return null;
/*     */     }
/*     */ 
/* 157 */     return StringUtil.formatNewlineForHTML(this.m_sRegistrationInfo);
/*     */   }
/*     */ 
/*     */   public String getAddressAsHtml()
/*     */   {
/* 172 */     if (this.m_sAddress == null)
/*     */     {
/* 174 */       return null;
/*     */     }
/*     */ 
/* 177 */     return StringUtil.formatNewlineForHTML(this.m_sAddress);
/*     */   }
/*     */ 
/*     */   public String getFullName()
/*     */   {
/* 182 */     if ((getDisplayName() != null) && (getDisplayName().length() > 0))
/*     */     {
/* 184 */       return getDisplayName();
/*     */     }
/*     */ 
/* 187 */     String sFullname = "";
/*     */ 
/* 189 */     if ((getTitle() != null) && (getTitle().length() > 0))
/*     */     {
/* 191 */       sFullname = sFullname + getTitle();
/*     */     }
/*     */ 
/* 194 */     if ((getForename() != null) && (getForename().length() > 0))
/*     */     {
/* 196 */       if (sFullname.length() > 0)
/*     */       {
/* 198 */         sFullname = sFullname + " ";
/*     */       }
/* 200 */       sFullname = sFullname + getForename();
/*     */     }
/*     */ 
/* 203 */     if ((getSurname() != null) && (getSurname().length() > 0))
/*     */     {
/* 205 */       if (sFullname.length() > 0)
/*     */       {
/* 207 */         sFullname = sFullname + " ";
/*     */       }
/*     */ 
/* 210 */       sFullname = sFullname + getSurname();
/*     */     }
/*     */ 
/* 213 */     return sFullname;
/*     */   }
/*     */ 
/*     */   public boolean getIsInGroup(int a_iGroupId)
/*     */   {
/* 233 */     Group group = null;
/*     */ 
/* 235 */     if (getGroups() != null)
/*     */     {
/* 237 */       for (int i = 0; i < getGroups().size(); i++)
/*     */       {
/* 239 */         group = (Group)(Group)getGroups().elementAt(i);
/*     */ 
/* 241 */         if (group.getId() == a_iGroupId)
/*     */         {
/* 243 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 248 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getIsAdminOfOrgUnit(long a_lOrgUnitId)
/*     */   {
/* 260 */     boolean bIsAdmin = false;
/*     */ 
/* 262 */     if (this.m_vOrgUnits != null)
/*     */     {
/* 264 */       for (OrgUnitMetadata ou : this.m_vOrgUnits)
/*     */       {
/* 266 */         long lId = ou.getId();
/* 267 */         if (a_lOrgUnitId == lId)
/*     */         {
/* 269 */           bIsAdmin = true;
/* 270 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 274 */     return bIsAdmin;
/*     */   }
/*     */ 
/*     */   public boolean getIsAdminOfAtLeastOneOrgUnit(Vector<OrgUnit> a_vecOrgUnits)
/*     */   {
/* 286 */     boolean bIsAdmin = false;
/*     */ 
/* 289 */     if (this.m_vOrgUnits != null)
/*     */     {
/* 291 */       for (OrgUnitMetadata oum : this.m_vOrgUnits)
/*     */       {
/* 293 */         Long lId = Long.valueOf(oum.getId());
/*     */ 
/* 295 */         if (a_vecOrgUnits != null)
/*     */         {
/* 297 */           for (OrgUnit ou : a_vecOrgUnits)
/*     */           {
/* 299 */             if (ou.getId() == lId.longValue())
/*     */             {
/* 301 */               bIsAdmin = true;
/* 302 */               break;
/*     */             }
/*     */           }
/* 305 */           if (bIsAdmin)
/*     */           {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 312 */     return bIsAdmin;
/*     */   }
/*     */ 
/*     */   public boolean getIsAdmin()
/*     */   {
/* 321 */     return this.m_bIsAdmin;
/*     */   }
/*     */ 
/*     */   public void setIsAdmin(boolean a_bIsAdmin)
/*     */   {
/* 326 */     this.m_bIsAdmin = a_bIsAdmin;
/*     */   }
/*     */ 
/*     */   public boolean getIsDeleted()
/*     */   {
/* 331 */     return this.m_bIsDeleted;
/*     */   }
/*     */ 
/*     */   public void setIsDeleted(boolean a_sIsDeleted)
/*     */   {
/* 336 */     this.m_bIsDeleted = a_sIsDeleted;
/*     */   }
/*     */ 
/*     */   public boolean getNotApproved()
/*     */   {
/* 341 */     return this.m_bNotApproved;
/*     */   }
/*     */ 
/*     */   public void setNotApproved(boolean a_bNotApproved)
/*     */   {
/* 346 */     this.m_bNotApproved = a_bNotApproved;
/*     */   }
/*     */ 
/*     */   public boolean getCanBeCategoryAdmin()
/*     */   {
/* 351 */     return this.m_bCanBeCategoryAdmin;
/*     */   }
/*     */ 
/*     */   public void setCanBeCategoryAdmin(boolean a_bCanBeCategoryAdmin)
/*     */   {
/* 356 */     this.m_bCanBeCategoryAdmin = a_bCanBeCategoryAdmin;
/*     */   }
/*     */ 
/*     */   public String getAddress()
/*     */   {
/* 362 */     return this.m_sAddress;
/*     */   }
/*     */ 
/*     */   public void setAddress(String a_sAddress)
/*     */   {
/* 367 */     this.m_sAddress = a_sAddress;
/*     */   }
/*     */ 
            public void setTelephone(String tel)
            {
                 this.m_sTelephone = tel;
            }
            public void setFax(String fax){
                this.m_sFax = fax;
            }
            public String getTelephone(String tel)
            {
                 return this.m_sTelephone;
            }
            public String getFax(String fax){
                return this.m_sFax;
            }
/*     */   public String getRegistrationInfo()
/*     */   {
/* 372 */     return this.m_sRegistrationInfo;
/*     */   }
/*     */ 
/*     */   public void setRegistrationInfo(String a_sRegistrationInfo)
/*     */   {
/* 377 */     this.m_sRegistrationInfo = a_sRegistrationInfo;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 382 */     return this.m_sTitle;
/*     */   }
/*     */ 
/*     */   public void setTitle(String a_sTitle)
/*     */   {
/* 387 */     this.m_sTitle = a_sTitle;
/*     */   }
/*     */ 
/*     */   public void setAdmin(boolean a_sIsAdmin)
/*     */   {
/* 392 */     this.m_bIsAdmin = a_sIsAdmin;
/*     */   }
/*     */ 
/*     */   public void setDeleted(boolean a_sIsDeleted)
/*     */   {
/* 397 */     this.m_bIsDeleted = a_sIsDeleted;
/*     */   }
/*     */ 
/*     */   public Date getExpiryDate()
/*     */   {
/* 402 */     return this.m_dtExpiryDate;
/*     */   }
/*     */ 
/*     */   public void setExpiryDate(Date a_dtExpiryDate)
/*     */   {
/* 407 */     this.m_dtExpiryDate = a_dtExpiryDate;
/*     */   }
/*     */ 
/*     */   public String getJobTitle() {
/* 411 */     return this.m_sJobTitle;
/*     */   }
/*     */ 
/*     */   public void setJobTitle(String a_sJobTitle) {
/* 415 */     this.m_sJobTitle = a_sJobTitle;
/*     */   }
/*     */   public boolean getIsOrgUnitAdmin() {
/* 418 */     return this.m_bIsOrgUnitAdmin;
/*     */   }
/*     */ 
/*     */   public void setIsOrgUnitAdmin(boolean a_sIsOrgUnitAdmin) {
/* 422 */     this.m_bIsOrgUnitAdmin = a_sIsOrgUnitAdmin;
/*     */   }
/*     */ 
/*     */   public Vector<? extends OrgUnitMetadata> getOrgUnits() {
/* 426 */     return this.m_vOrgUnits;
/*     */   }
/*     */ 
/*     */   public void setOrgUnits(Vector<? extends OrgUnitMetadata> a_vOrgUnits) {
/* 430 */     this.m_vOrgUnits = a_vOrgUnits;
/*     */   }
/*     */ 
/*     */   public ArrayList<? extends OrgUnitMetadata> getAllOrgUnits()
/*     */   {
/* 441 */     ArrayList alOrgUnits = new ArrayList();
/*     */ 
/* 443 */     if (this.m_vOrgUnits != null)
/*     */     {
/* 445 */       alOrgUnits.addAll(this.m_vOrgUnits);
/*     */     }
/*     */ 
/* 448 */     if (getGroups() != null)
/*     */     {
/* 450 */       for (int i = 0; i < getGroups().size(); i++)
/*     */       {
/* 452 */         Group group = (Group)getGroups().elementAt(i);
/* 453 */         if ((group.getOrgUnitReference() == null) || (group.getOrgUnitReference().getId() <= 0L) || (alOrgUnits.contains(group.getOrgUnitReference())))
/*     */         {
/*     */           continue;
/*     */         }
/* 457 */         alOrgUnits.add(group.getOrgUnitReference());
/*     */       }
/*     */     }
/*     */ 
/* 461 */     return alOrgUnits;
/*     */   }
/*     */ 
/*     */   public ArrayList<OrgUnitContent> getOrgUnitExtraContent()
/*     */   {
/* 471 */     ArrayList<? extends OrgUnitMetadata> alOrgUnits = getAllOrgUnits();
/* 472 */     ArrayList alContent = new ArrayList();
/* 473 */     for (OrgUnitMetadata org : alOrgUnits)
/*     */     {
/* 475 */       alContent.addAll(org.getOrgUnitContentForPurpose(1L, true));
/*     */     }
/* 477 */     return alContent;
/*     */   }
/*     */ 
/*     */   public boolean getRequiresUpdate()
/*     */   {
/* 482 */     return this.m_bRequiresUpdate;
/*     */   }
/*     */ 
/*     */   public void setRequiresUpdate(boolean a_sRequiresUpdate) {
/* 486 */     this.m_bRequiresUpdate = a_sRequiresUpdate;
/*     */   }
/*     */ 
/*     */   public long getRequestedOrgUnitId()
/*     */   {
/* 491 */     return this.m_lRequestedOrgUnitId;
/*     */   }
/*     */ 
/*     */   public void setRequestedOrgUnitId(long a_sRequestedOrgUnitId) {
/* 495 */     this.m_lRequestedOrgUnitId = a_sRequestedOrgUnitId;
/*     */   }
/*     */ 
/*     */   public RefDataItem getRefDivision()
/*     */   {
/* 501 */     return this.m_refDivision;
/*     */   }
/*     */ 
/*     */   public void setRefDivision(RefDataItem a_sRefDivision) {
/* 505 */     this.m_refDivision = a_sRefDivision;
/*     */   }
/*     */ 
/*     */   public Address getHomeAddress()
/*     */   {
/* 511 */     return this.m_homeAddress;
/*     */   }
/*     */ 
/*     */   public void setHomeAddress(Address a_sHomeAddress) {
/* 515 */     this.m_homeAddress = a_sHomeAddress;
/*     */   }
/*     */ 
/*     */   public String getTaxNumber()
/*     */   {
/* 520 */     return this.m_sTaxNumber;
/*     */   }
/*     */ 
/*     */   public void setTaxNumber(String a_sTaxNumber) {
/* 524 */     this.m_sTaxNumber = a_sTaxNumber;
/*     */   }
/*     */ 
/*     */   public boolean getCanLoginFromCms() {
/* 528 */     return this.m_bCanLoginFromCms;
/*     */   }
/*     */ 
/*     */   public void setCanLoginFromCms(boolean a_bCanLoginFromCms) {
/* 532 */     this.m_bCanLoginFromCms = a_bCanLoginFromCms;
/*     */   }
/*     */ 
/*     */   public boolean getReceiveAlerts() {
/* 536 */     return this.m_bReceiveAlerts;
/*     */   }
/*     */ 
/*     */   public void setReceiveAlerts(boolean a_bReceiveAlerts) {
/* 540 */     this.m_bReceiveAlerts = a_bReceiveAlerts;
/*     */   }
/*     */ 
/*     */   public boolean isContactDetailsPopulated()
/*     */   {
/* 545 */     return StringUtil.stringIsPopulated(getEmailAddress());
/*     */   }
/*     */ 
/*     */   public Date getRegisterDate()
/*     */   {
/* 551 */     return this.m_registerDate;
/*     */   }
/*     */ 
/*     */   public void setRegisterDate(Date a_sRegisterDate)
/*     */   {
/* 557 */     this.m_registerDate = a_sRegisterDate;
/*     */   }
/*     */ 
/*     */   public Date getPasswordChangedDate()
/*     */   {
/* 562 */     return this.m_passwordChangedDate;
/*     */   }
/*     */ 
/*     */   public void setPasswordChangedDate(Date a_passwordChangedDate)
/*     */   {
/* 568 */     this.m_passwordChangedDate = a_passwordChangedDate;
/*     */   }
/*     */ 
/*     */   public String getAdminNotes()
/*     */   {
/* 573 */     return this.m_sAdminNotes;
/*     */   }
/*     */ 
/*     */   public void setAdminNotes(String a_sAdminNotes)
/*     */   {
/* 578 */     this.m_sAdminNotes = a_sAdminNotes;
/*     */   }
/*     */ 
/*     */   public long getNewAssetBoxId()
/*     */   {
/* 583 */     return this.m_lNewAssetBoxId;
/*     */   }
/*     */ 
/*     */   public void setNewAssetBoxId(long a_lNewAssetBoxId)
/*     */   {
/* 588 */     this.m_lNewAssetBoxId = a_lNewAssetBoxId;
/*     */   }
/*     */ 
/*     */   public long getInvitedByUserId()
/*     */   {
/* 593 */     return this.m_lInvitedByUserId;
/*     */   }
/*     */ 
/*     */   public void setInvitedByUserId(long a_lInvitedByUserId)
/*     */   {
/* 598 */     this.m_lInvitedByUserId = a_lInvitedByUserId;
/*     */   }
/*     */ 
/*     */   public String getCountry()
/*     */   {
/* 603 */     return getHomeAddress().getCountry().getName();
/*     */   }
/*     */ 
/*     */   public void setInitiallyApprovedBy(User a_initiallyApprovedBy)
/*     */   {
/* 608 */     this.m_initiallyApprovedBy = a_initiallyApprovedBy;
/*     */   }
/*     */ 
/*     */   public User getInitiallyApprovedBy()
/*     */   {
/* 613 */     return this.m_initiallyApprovedBy;
/*     */   }
/*     */ 
/*     */   public String getAdditionalApproverDetails()
/*     */   {
/* 619 */     return this.m_sAdditionalApproverDetails;
/*     */   }
/*     */ 
/*     */   public void setAdditionalApproverDetails(String a_sAdditionalApproverDetails)
/*     */   {
/* 624 */     this.m_sAdditionalApproverDetails = a_sAdditionalApproverDetails;
/*     */   }
/*     */ 
/*     */   public boolean getReactivationPending()
/*     */   {
/* 629 */     return this.m_bReactivationPending;
/*     */   }
/*     */ 
/*     */   public void setReactivationPending(boolean a_bReactivationPending) {
/* 633 */     this.m_bReactivationPending = a_bReactivationPending;
/*     */   }
/*     */ 
/*     */   public long getLastModifiedBy()
/*     */   {
/* 638 */     return this.m_lLastModifiedBy;
/*     */   }
/*     */ 
/*     */   public void setLastModifiedBy(long a_lLastModifiedBy) {
/* 642 */     this.m_lLastModifiedBy = a_lLastModifiedBy;
/*     */   }
/*     */ 
/*     */   public void overwriteWithPopulatedStringValues(ABUser a_user)
/*     */     throws Bn2Exception
/*     */   {
/* 649 */     HashMap hmGetMethods = new HashMap();
/* 650 */     HashMap hmSetMethods = new HashMap();
/*     */ 
/* 653 */     Method[] aMethods = getClass().getMethods();
/*     */ 
/* 656 */     if (aMethods != null)
/*     */     {
/* 658 */       for (int i = 0; i < aMethods.length; i++)
/*     */       {
/* 660 */         String sMethodName = aMethods[i].getName().substring(3, aMethods[i].getName().length());
/*     */ 
/* 663 */         if ((aMethods[i].getReturnType().equals(String.class)) && (aMethods[i].getName().substring(0, 3).equals("get")) && (aMethods[i].getParameterTypes().length == 0))
/*     */         {
/* 667 */           hmGetMethods.put(sMethodName, aMethods[i]);
/*     */         } else {
/* 669 */           if (!aMethods[i].getName().substring(0, 3).equals("set"))
/*     */             continue;
/* 671 */           hmSetMethods.put(sMethodName, aMethods[i]);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 677 */     Set<String> keys = hmGetMethods.keySet();
/* 678 */     if (keys != null)
/*     */     {
/* 680 */       for (String sKey : keys)
/*     */       {
/*     */         try
/*     */         {
/* 686 */           Method getMethod = (Method)hmGetMethods.get(sKey);
/* 687 */           String sResult = (String)getMethod.invoke(a_user, (Object[])null);
/* 688 */           if ((StringUtil.stringIsPopulated(sResult)) && (hmSetMethods.containsKey(sKey)))
/*     */           {
/* 690 */             Method setMethod = (Method)hmSetMethods.get(sKey);
/* 691 */             Object[] aArgs = { sResult };
/* 692 */             setMethod.invoke(this, aArgs);
/*     */           }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 697 */           throw new Bn2Exception("ABUser.overwriteWithPopulatedStringValues: Error " + e.getMessage(), e);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean getIsLocalUser()
/*     */   {
/* 711 */     return (StringUtils.isEmpty(AssetBankSettings.getLocalUserEmailDomain())) || (StringUtils.isEmpty(getEmailAddress())) || (getEmailAddress().toLowerCase().endsWith("@" + AssetBankSettings.getLocalUserEmailDomain().toLowerCase()));
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.bean.ABUser
 * JD-Core Version:    0.6.0
 */