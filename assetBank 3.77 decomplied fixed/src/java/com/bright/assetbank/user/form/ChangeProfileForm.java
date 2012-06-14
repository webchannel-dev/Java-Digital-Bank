/*     */ package com.bright.assetbank.user.form;
/*     */ 
/*     */ import com.bright.assetbank.marketing.form.MarketingGroupSubscriptionForm;
/*     */ import com.bright.assetbank.marketing.form.MarketingGroupSubscriptionFormImpl;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ChangeProfileForm extends UpdateUserForm
/*     */   implements MarketingGroupSubscriptionForm
/*     */ {
/*  43 */   private User m_usLastUpdatedBy = null;
/*  44 */   private User m_usInvitedByUser = null;
/*  45 */   private MarketingGroupSubscriptionFormImpl marketingForm = new MarketingGroupSubscriptionFormImpl();
/*     */ 
/*     */   public String[] getMarketingGroupIds()
/*     */   {
/*  49 */     return this.marketingForm.getMarketingGroupIds();
/*     */   }
/*     */ 
/*     */   public List getMarketingGroups()
/*     */   {
/*  54 */     return this.marketingForm.getMarketingGroups();
/*     */   }
/*     */ 
/*     */   public boolean getMarketingGroupsHaveDescriptions()
/*     */   {
/*  59 */     return this.marketingForm.getMarketingGroupsHaveDescriptions();
/*     */   }
/*     */ 
/*     */   public void setMarketingGroupIds(String[] a_asMarketingGroupIds)
/*     */   {
/*  64 */     this.marketingForm.setMarketingGroupIds(a_asMarketingGroupIds);
/*     */   }
/*     */ 
/*     */   public int getNumberOfGroupSubscriptions()
/*     */   {
/*  69 */     return this.marketingForm.getNumberOfGroupSubscriptions();
/*     */   }
/*     */ 
/*     */   public void setMarketingGroups(List a_marketingGroups)
/*     */   {
/*  74 */     this.marketingForm.setMarketingGroups(a_marketingGroups);
/*     */   }
/*     */ 
/*     */   public void setMarketingGroupsHaveDescriptions(boolean a_bMarketingGroupsHaveDescriptions)
/*     */   {
/*  79 */     this.marketingForm.setMarketingGroupsHaveDescriptions(a_bMarketingGroupsHaveDescriptions);
/*     */   }
/*     */ 
/*     */   public boolean isPopulatedviaReload()
/*     */   {
/*  84 */     return this.marketingForm.isPopulatedviaReload();
/*     */   }
/*     */ 
/*     */   public void setPopulatedViaReload(boolean a_bPopulated)
/*     */   {
/*  89 */     this.marketingForm.setPopulatedViaReload(a_bPopulated);
/*     */   }
/*     */ 
/*     */   public User getLastUpdatedBy()
/*     */   {
/*  94 */     if (this.m_usLastUpdatedBy == null)
/*     */     {
/*  96 */       this.m_usLastUpdatedBy = new ABUser();
/*     */     }
/*  98 */     return this.m_usLastUpdatedBy;
/*     */   }
/*     */ 
/*     */   public void setLastUpdatedBy(User a_usLastUpdatedBy)
/*     */   {
/* 103 */     this.m_usLastUpdatedBy = a_usLastUpdatedBy;
/*     */   }
/*     */ 
/*     */   public User getInvitedByUser()
/*     */   {
/* 108 */     if (this.m_usInvitedByUser == null)
/*     */     {
/* 110 */       this.m_usInvitedByUser = new ABUser();
/*     */     }
/* 112 */     return this.m_usInvitedByUser;
/*     */   }
/*     */ 
/*     */   public void setInvitedByUser(User a_usInvitedByUser)
/*     */   {
/* 117 */     this.m_usInvitedByUser = a_usInvitedByUser;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.form.ChangeProfileForm
 * JD-Core Version:    0.6.0
 */