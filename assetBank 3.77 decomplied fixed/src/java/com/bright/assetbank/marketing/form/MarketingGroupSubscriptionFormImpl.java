/*     */ package com.bright.assetbank.marketing.form;
/*     */ 
/*     */ import com.bright.assetbank.marketing.bean.MarketingGroup;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public final class MarketingGroupSubscriptionFormImpl
/*     */   implements MarketingGroupSubscriptionForm
/*     */ {
/*  33 */   private List m_marketingGroups = null;
/*  34 */   private String[] m_asMarketingGroupIds = null;
/*  35 */   private boolean m_bMarketingGroupsHaveDescriptions = false;
/*  36 */   private boolean m_bPopulatedViaReload = false;
/*     */ 
/*     */   public List getMarketingGroups()
/*     */   {
/*  44 */     return this.m_marketingGroups;
/*     */   }
/*     */ 
/*     */   public void setMarketingGroups(List marketingGroups)
/*     */   {
/*  53 */     this.m_marketingGroups = marketingGroups;
/*  54 */     this.m_bMarketingGroupsHaveDescriptions = false;
/*  55 */     if (this.m_marketingGroups != null)
/*     */     {
/*  57 */       Iterator itGroups = this.m_marketingGroups.iterator();
/*  58 */       while (itGroups.hasNext())
/*     */       {
/*  60 */         this.m_bMarketingGroupsHaveDescriptions |= StringUtils.isNotEmpty(((MarketingGroup)itGroups.next()).getDescription());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String[] getMarketingGroupIds()
/*     */   {
/*  71 */     if (this.m_asMarketingGroupIds == null)
/*     */     {
/*  73 */       this.m_asMarketingGroupIds = new String[0];
/*     */     }
/*  75 */     return this.m_asMarketingGroupIds;
/*     */   }
/*     */ 
/*     */   public void setMarketingGroupIds(String[] asMarketingGroupIds)
/*     */   {
/*  84 */     this.m_asMarketingGroupIds = asMarketingGroupIds;
/*     */   }
/*     */ 
/*     */   public int getNumberOfGroupSubscriptions()
/*     */   {
/*  93 */     int i = 0;
/*  94 */     if (this.m_asMarketingGroupIds != null)
/*     */     {
/*  96 */       while (i < this.m_asMarketingGroupIds.length)
/*     */       {
/*  98 */         if (this.m_asMarketingGroupIds[i] == null)
/*     */           continue;
/* 100 */         i++;
/*     */       }
/*     */     }
/*     */ 
/* 104 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean getMarketingGroupsHaveDescriptions()
/*     */   {
/* 113 */     return this.m_bMarketingGroupsHaveDescriptions;
/*     */   }
/*     */ 
/*     */   public void setMarketingGroupsHaveDescriptions(boolean marketingGroupsHaveDescriptions)
/*     */   {
/* 122 */     this.m_bMarketingGroupsHaveDescriptions = marketingGroupsHaveDescriptions;
/*     */   }
/*     */ 
/*     */   public void setPopulatedViaReload(boolean a_bPopulated)
/*     */   {
/* 131 */     this.m_bPopulatedViaReload = a_bPopulated;
/*     */   }
/*     */ 
/*     */   public boolean isPopulatedviaReload()
/*     */   {
/* 140 */     return this.m_bPopulatedViaReload;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.form.MarketingGroupSubscriptionFormImpl
 * JD-Core Version:    0.6.0
 */