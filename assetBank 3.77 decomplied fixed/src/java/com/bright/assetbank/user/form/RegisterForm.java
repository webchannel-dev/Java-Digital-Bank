/*     */ package com.bright.assetbank.user.form;
/*     */ 
/*     */ import com.bright.assetbank.marketing.form.MarketingGroupSubscriptionForm;
/*     */ import com.bright.assetbank.marketing.form.MarketingGroupSubscriptionFormImpl;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class RegisterForm extends UpdateUserForm
/*     */   implements MarketingGroupSubscriptionForm
/*     */ {
/*  38 */   private boolean m_bConditionsAccepted = false;
/*     */ 
/*  41 */   private String m_sRegInfo = "";
/*     */   private Vector m_orgUnitList;
/*  45 */   private long m_lSelectedOrgUnit = 0L;
/*     */   private Vector m_userOrgUnits;
/*  49 */   private Vector m_registerGroupList = null;
/*  50 */   private long m_lRequestedGroupId = 0L;
/*     */ 
/*  52 */   private String m_sVerifyName = "";
/*  53 */   private String m_sVerifyEmail = "";
/*     */ 
/*  55 */   private MarketingGroupSubscriptionFormImpl marketingForm = new MarketingGroupSubscriptionFormImpl();
/*     */ 
/*  57 */   private boolean m_bRegisteringForPurchase = false;
/*     */ 
/*     */   public boolean getConditionsAccepted()
/*     */   {
/*  61 */     return this.m_bConditionsAccepted;
/*     */   }
/*     */ 
/*     */   public void setConditionsAccepted(boolean a_sConditionsAccepted) {
/*  65 */     this.m_bConditionsAccepted = a_sConditionsAccepted;
/*     */   }
/*     */ 
/*     */   public Vector getOrgUnitList() {
/*  69 */     return this.m_orgUnitList;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitList(Vector a_sOrgUnitList) {
/*  73 */     this.m_orgUnitList = a_sOrgUnitList;
/*     */   }
/*     */ 
/*     */   public long getSelectedOrgUnit()
/*     */   {
/*  79 */     return this.m_lSelectedOrgUnit;
/*     */   }
/*     */ 
/*     */   public void setSelectedOrgUnit(long a_sSelectedOrgUnit) {
/*  83 */     this.m_lSelectedOrgUnit = a_sSelectedOrgUnit;
/*     */   }
/*     */ 
/*     */   public String getRegInfo()
/*     */   {
/*  88 */     return this.m_sRegInfo;
/*     */   }
/*     */ 
/*     */   public void setRegInfo(String a_sRegInfo) {
/*  92 */     this.m_sRegInfo = a_sRegInfo;
/*     */   }
/*     */ 
/*     */   public Vector getUserOrgUnits()
/*     */   {
/*  97 */     return this.m_userOrgUnits;
/*     */   }
/*     */ 
/*     */   public void setUserOrgUnits(Vector a_sUserOrgUnits) {
/* 101 */     this.m_userOrgUnits = a_sUserOrgUnits;
/*     */   }
/*     */ 
/*     */   public Vector getRegisterGroupList()
/*     */   {
/* 106 */     return this.m_registerGroupList;
/*     */   }
/*     */ 
/*     */   public void setRegisterGroupList(Vector a_sBrandList)
/*     */   {
/* 111 */     this.m_registerGroupList = a_sBrandList;
/*     */   }
/*     */ 
/*     */   public long getRequestedGroupId()
/*     */   {
/* 116 */     return this.m_lRequestedGroupId;
/*     */   }
/*     */ 
/*     */   public void setRequestedGroupId(long a_sRequestedBrandId)
/*     */   {
/* 121 */     this.m_lRequestedGroupId = a_sRequestedBrandId;
/*     */   }
/*     */ 
/*     */   public String[] getMarketingGroupIds()
/*     */   {
/* 126 */     return this.marketingForm.getMarketingGroupIds();
/*     */   }
/*     */ 
/*     */   public int getNumberOfGroupSubscriptions()
/*     */   {
/* 131 */     return this.marketingForm.getNumberOfGroupSubscriptions();
/*     */   }
/*     */ 
/*     */   public List getMarketingGroups()
/*     */   {
/* 136 */     return this.marketingForm.getMarketingGroups();
/*     */   }
/*     */ 
/*     */   public boolean getMarketingGroupsHaveDescriptions()
/*     */   {
/* 141 */     return this.marketingForm.getMarketingGroupsHaveDescriptions();
/*     */   }
/*     */ 
/*     */   public void setMarketingGroupIds(String[] a_asMarketingGroupIds)
/*     */   {
/* 146 */     this.marketingForm.setMarketingGroupIds(a_asMarketingGroupIds);
/*     */   }
/*     */ 
/*     */   public void setMarketingGroups(List a_marketingGroups)
/*     */   {
/* 151 */     this.marketingForm.setMarketingGroups(a_marketingGroups);
/*     */   }
/*     */ 
/*     */   public void setMarketingGroupsHaveDescriptions(boolean a_bMarketingGroupsHaveDescriptions)
/*     */   {
/* 156 */     this.marketingForm.setMarketingGroupsHaveDescriptions(a_bMarketingGroupsHaveDescriptions);
/*     */   }
/*     */ 
/*     */   public boolean isPopulatedviaReload()
/*     */   {
/* 161 */     return this.marketingForm.isPopulatedviaReload();
/*     */   }
/*     */ 
/*     */   public void setPopulatedViaReload(boolean a_bPopulated)
/*     */   {
/* 166 */     this.marketingForm.setPopulatedViaReload(a_bPopulated);
/*     */   }
/*     */ 
/*     */   public String getVerifyName()
/*     */   {
/* 171 */     return this.m_sVerifyName;
/*     */   }
/*     */ 
/*     */   public void setVerifyName(String a_sVerifyName) {
/* 175 */     this.m_sVerifyName = a_sVerifyName;
/*     */   }
/*     */ 
/*     */   public String getVerifyEmail()
/*     */   {
/* 180 */     return this.m_sVerifyEmail;
/*     */   }
/*     */ 
/*     */   public void setVerifyEmail(String a_sVerifyEmail) {
/* 184 */     this.m_sVerifyEmail = a_sVerifyEmail;
/*     */   }
/*     */ 
/*     */   public boolean getRegisteringForPurchase()
/*     */   {
/* 189 */     return this.m_bRegisteringForPurchase;
/*     */   }
/*     */ 
/*     */   public void setRegisteringForPurchase(boolean a_bRegisteringForPurchase) {
/* 193 */     this.m_bRegisteringForPurchase = a_bRegisteringForPurchase;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.form.RegisterForm
 * JD-Core Version:    0.6.0
 */