/*     */ package com.bright.assetbank.ecommerce.form;
/*     */ 
/*     */ import com.bright.assetbank.commercialoption.bean.CommercialOption;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.framework.common.bean.Address;
/*     */ import com.bright.framework.common.bean.Country;
/*     */ import com.bright.framework.user.form.LoginForm;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class CheckoutForm extends LoginForm
/*     */ {
/*     */   private boolean m_bTcsDone;
/*     */   private String m_sSelectedUse;
/*     */   private boolean m_bIsRegistered;
/*     */   private boolean m_bIsProfileEntered;
/*     */   private boolean m_bInvalidEmailFormat;
/*     */   private HashMap m_purchaseForm;
/*     */   private Collection m_purchaseFormKeys;
/*     */   private String m_sPspUrl;
/*  50 */   private ABUser m_user = null;
/*  51 */   private Vector m_divisionList = null;
/*     */ 
/*  54 */   private boolean m_bIsShippingEntered = false;
/*     */   private String m_sShippingUser;
/*  56 */   private Address m_shippingAddress = null;
/*  57 */   private boolean m_bCopyShippingAddressToProfile = false;
/*     */ 
/*  60 */   private String m_sUserNotes = null;
/*     */ 
/*  63 */   private boolean m_bPrefersOfflinePayment = false;
/*     */ 
/*  66 */   Vector m_vecCommercialOptionsList = null;
/*     */ 
/*  72 */   private HashMap m_hmCommercialOptions = null;
/*     */ 
/*  74 */   private List m_languages = null;
/*     */   private Collection m_approvalList;
/*     */   private String m_registerEmailAddress;
/*     */   private boolean m_usernameExists;
/*     */   private boolean m_emailAddressLost;
/*     */   private boolean m_registerEmailsDontMatch;
/*     */   private String m_confirmRegisterEmailAddress;
/*     */ 
/*     */   public CheckoutForm()
/*     */   {
/*  79 */     resetCheckoutForm();
/*     */   }
/*     */ 
/*     */   public void resetFlags()
/*     */   {
/*  87 */     super.resetFlags();
/*     */ 
/*  89 */     this.m_bInvalidEmailFormat = false;
/*  90 */     this.m_bIsRegistered = false;
/*  91 */     this.m_bIsProfileEntered = false;
/*  92 */     this.m_bIsShippingEntered = false;
/*  93 */     this.m_emailAddressLost = false;
/*  94 */     this.m_registerEmailsDontMatch = false;
/*  95 */     this.m_usernameExists = false;
/*  96 */     this.m_sSelectedUse = "";
/*  97 */     this.m_bCopyShippingAddressToProfile = false;
/*     */   }
/*     */ 
/*     */   public void resetCheckoutForm()
/*     */   {
/* 104 */     resetFlags();
/* 105 */     resetUser();
/* 106 */     resetShipping();
/* 107 */     resetErrors();
/*     */ 
/* 109 */     super.resetLoginForm();
/*     */ 
/* 111 */     this.m_bTcsDone = false;
/* 112 */     this.m_confirmRegisterEmailAddress = "";
/* 113 */     this.m_registerEmailAddress = "";
/*     */   }
/*     */ 
/*     */   public void resetUser()
/*     */   {
/* 119 */     this.m_user = new ABUser();
/*     */   }
/*     */ 
/*     */   public void resetShipping()
/*     */   {
/* 125 */     this.m_shippingAddress = new Address();
/* 126 */     this.m_sShippingUser = "";
/*     */   }
/*     */ 
/*     */   public void resetErrors()
/*     */   {
/* 134 */     getErrors().clear();
/*     */   }
/*     */ 
/*     */   public boolean getUserHasCountry()
/*     */   {
/* 146 */     boolean bHasCountry = false;
/*     */ 
/* 148 */     if (this.m_user != null)
/*     */     {
/* 150 */       if (this.m_user.getHomeAddress().getCountry().getId() > 0L)
/*     */       {
/* 152 */         bHasCountry = true;
/*     */       }
/*     */     }
/*     */ 
/* 156 */     return bHasCountry;
/*     */   }
/*     */ 
/*     */   public boolean getShippingAddressHasCountry()
/*     */   {
/* 167 */     boolean bHasCountry = false;
/*     */ 
/* 169 */     if (this.m_shippingAddress != null)
/*     */     {
/* 171 */       if (this.m_shippingAddress.getCountry().getId() > 0L)
/*     */       {
/* 173 */         bHasCountry = true;
/*     */       }
/*     */     }
/*     */ 
/* 177 */     return bHasCountry;
/*     */   }
/*     */ 
/*     */   public Collection getApprovalList()
/*     */   {
/* 196 */     return this.m_approvalList;
/*     */   }
/*     */ 
/*     */   public void setApprovalList(Collection a_sApprovalList)
/*     */   {
/* 205 */     this.m_approvalList = a_sApprovalList;
/*     */   }
/*     */ 
/*     */   public String getRegisterEmailAddress()
/*     */   {
/* 221 */     return this.m_registerEmailAddress;
/*     */   }
/*     */ 
/*     */   public void setRegisterEmailAddress(String a_sRegisterEmailAddress)
/*     */   {
/* 230 */     this.m_registerEmailAddress = a_sRegisterEmailAddress;
/*     */   }
/*     */ 
/*     */   public boolean getUsernameExists()
/*     */   {
/* 246 */     return this.m_usernameExists;
/*     */   }
/*     */ 
/*     */   public void setUsernameExists(boolean a_sUsernameExists)
/*     */   {
/* 255 */     this.m_usernameExists = a_sUsernameExists;
/*     */   }
/*     */ 
/*     */   public boolean getEmailAddressLost()
/*     */   {
/* 271 */     return this.m_emailAddressLost;
/*     */   }
/*     */ 
/*     */   public void setEmailAddressLost(boolean a_sEmailAddressLost)
/*     */   {
/* 280 */     this.m_emailAddressLost = a_sEmailAddressLost;
/*     */   }
/*     */ 
/*     */   public boolean getRegisterEmailsDontMatch()
/*     */   {
/* 296 */     return this.m_registerEmailsDontMatch;
/*     */   }
/*     */ 
/*     */   public void setRegisterEmailsDontMatch(boolean a_sRegisterEmailsDontMatch)
/*     */   {
/* 305 */     this.m_registerEmailsDontMatch = a_sRegisterEmailsDontMatch;
/*     */   }
/*     */ 
/*     */   public String getConfirmRegisterEmailAddress()
/*     */   {
/* 321 */     return this.m_confirmRegisterEmailAddress;
/*     */   }
/*     */ 
/*     */   public void setConfirmRegisterEmailAddress(String a_sConfirmRegisterEmailAddress)
/*     */   {
/* 330 */     this.m_confirmRegisterEmailAddress = a_sConfirmRegisterEmailAddress;
/*     */   }
/*     */ 
/*     */   public String getSelectedUse()
/*     */   {
/* 335 */     return this.m_sSelectedUse;
/*     */   }
/*     */ 
/*     */   public void setSelectedUse(String a_sSelectedUse) {
/* 339 */     this.m_sSelectedUse = a_sSelectedUse;
/*     */   }
/*     */ 
/*     */   public boolean getIsRegistered()
/*     */   {
/* 344 */     return this.m_bIsRegistered;
/*     */   }
/*     */ 
/*     */   public void setIsRegistered(boolean a_sIsRegistered) {
/* 348 */     this.m_bIsRegistered = a_sIsRegistered;
/*     */   }
/*     */ 
/*     */   public boolean getIsProfileEntered()
/*     */   {
/* 354 */     return this.m_bIsProfileEntered;
/*     */   }
/*     */ 
/*     */   public void setIsProfileEntered(boolean a_sIsProfileEntered) {
/* 358 */     this.m_bIsProfileEntered = a_sIsProfileEntered;
/*     */   }
/*     */ 
/*     */   public boolean getInvalidEmailFormat()
/*     */   {
/* 363 */     return this.m_bInvalidEmailFormat;
/*     */   }
/*     */ 
/*     */   public void setInvalidEmailFormat(boolean a_sInvalidEmailFormat) {
/* 367 */     this.m_bInvalidEmailFormat = a_sInvalidEmailFormat;
/*     */   }
/*     */ 
/*     */   public HashMap getPurchaseForm()
/*     */   {
/* 373 */     return this.m_purchaseForm;
/*     */   }
/*     */ 
/*     */   public void setPurchaseForm(HashMap a_sPurchaseForm) {
/* 377 */     this.m_purchaseForm = a_sPurchaseForm;
/*     */   }
/*     */ 
/*     */   public Collection getPurchaseFormKeys()
/*     */   {
/* 383 */     return this.m_purchaseFormKeys;
/*     */   }
/*     */ 
/*     */   public void setPurchaseFormKeys(Collection a_sPurchaseFormKeys) {
/* 387 */     this.m_purchaseFormKeys = a_sPurchaseFormKeys;
/*     */   }
/*     */ 
/*     */   public String getPspUrl()
/*     */   {
/* 393 */     return this.m_sPspUrl;
/*     */   }
/*     */ 
/*     */   public void setPspUrl(String a_sPspUrl) {
/* 397 */     this.m_sPspUrl = a_sPspUrl;
/*     */   }
/*     */ 
/*     */   public boolean getTcsDone()
/*     */   {
/* 403 */     return this.m_bTcsDone;
/*     */   }
/*     */ 
/*     */   public void setTcsDone(boolean a_sTcsDone) {
/* 407 */     this.m_bTcsDone = a_sTcsDone;
/*     */   }
/*     */ 
/*     */   public void setUser(ABUser a_user)
/*     */   {
/* 412 */     this.m_user = a_user;
/*     */   }
/*     */ 
/*     */   public ABUser getUser() {
/* 416 */     return this.m_user;
/*     */   }
/*     */ 
/*     */   public Vector getDivisionList() {
/* 420 */     return this.m_divisionList;
/*     */   }
/*     */ 
/*     */   public void setDivisionList(Vector a_sDivisionList) {
/* 424 */     this.m_divisionList = a_sDivisionList;
/*     */   }
/*     */ 
/*     */   public Address getShippingAddress()
/*     */   {
/* 429 */     return this.m_shippingAddress;
/*     */   }
/*     */ 
/*     */   public void setShippingAddress(Address a_sShippingAddress)
/*     */   {
/* 434 */     this.m_shippingAddress = a_sShippingAddress;
/*     */   }
/*     */ 
/*     */   public String getShippingUser()
/*     */   {
/* 439 */     return this.m_sShippingUser;
/*     */   }
/*     */ 
/*     */   public void setShippingUser(String a_sShippingUser)
/*     */   {
/* 444 */     this.m_sShippingUser = a_sShippingUser;
/*     */   }
/*     */ 
/*     */   public boolean getIsShippingEntered()
/*     */   {
/* 449 */     return this.m_bIsShippingEntered;
/*     */   }
/*     */ 
/*     */   public void setIsShippingEntered(boolean a_sIsShippingEntered)
/*     */   {
/* 454 */     this.m_bIsShippingEntered = a_sIsShippingEntered;
/*     */   }
/*     */ 
/*     */   public boolean getCopyShippingAddressToProfile()
/*     */   {
/* 459 */     return this.m_bCopyShippingAddressToProfile;
/*     */   }
/*     */ 
/*     */   public void setCopyShippingAddressToProfile(boolean a_sCopyShippingAddressToProfile)
/*     */   {
/* 464 */     this.m_bCopyShippingAddressToProfile = a_sCopyShippingAddressToProfile;
/*     */   }
/*     */ 
/*     */   public boolean getPrefersOfflinePayment()
/*     */   {
/* 472 */     return this.m_bPrefersOfflinePayment;
/*     */   }
/*     */ 
/*     */   public void setPrefersOfflinePayment(boolean a_dtPrefersOfflinePayment)
/*     */   {
/* 480 */     this.m_bPrefersOfflinePayment = a_dtPrefersOfflinePayment;
/*     */   }
/*     */ 
/*     */   public String getUserNotes()
/*     */   {
/* 488 */     return this.m_sUserNotes;
/*     */   }
/*     */ 
/*     */   public void setUserNotes(String a_dtUserNotes)
/*     */   {
/* 496 */     this.m_sUserNotes = a_dtUserNotes;
/*     */   }
/*     */ 
/*     */   public Vector getCommercialOptionsList()
/*     */   {
/* 505 */     return this.m_vecCommercialOptionsList;
/*     */   }
/*     */ 
/*     */   public void setCommercialOptionsList(Vector a_vecCommercialOptionsList)
/*     */   {
/* 513 */     this.m_vecCommercialOptionsList = a_vecCommercialOptionsList;
/*     */   }
/*     */ 
/*     */   public CommercialOption getAssetPriceCommercialOption(String a_sAssetPriceHash)
/*     */   {
/* 533 */     if (this.m_hmCommercialOptions.containsKey(a_sAssetPriceHash))
/*     */     {
/* 535 */       return (CommercialOption)this.m_hmCommercialOptions.get(a_sAssetPriceHash);
/*     */     }
/*     */ 
/* 538 */     CommercialOption newCommOpt = new CommercialOption();
/* 539 */     this.m_hmCommercialOptions.put(a_sAssetPriceHash, newCommOpt);
/* 540 */     return newCommOpt;
/*     */   }
/*     */ 
/*     */   public HashMap getAssetPriceCommercialOptions()
/*     */   {
/* 552 */     return this.m_hmCommercialOptions;
/*     */   }
/*     */ 
/*     */   public void setAssetPriceCommercialOptions(HashMap a_hmCommercialOptions)
/*     */   {
/* 561 */     this.m_hmCommercialOptions = a_hmCommercialOptions;
/*     */   }
/*     */ 
/*     */   public List getLanguages()
/*     */   {
/* 566 */     return this.m_languages;
/*     */   }
/*     */ 
/*     */   public void setLanguages(List languages)
/*     */   {
/* 571 */     this.m_languages = languages;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.form.CheckoutForm
 * JD-Core Version:    0.6.0
 */