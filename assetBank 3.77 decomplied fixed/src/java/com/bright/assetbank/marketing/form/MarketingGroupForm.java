/*    */ package com.bright.assetbank.marketing.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.marketing.bean.MarketingGroup;
/*    */ import com.bright.framework.constant.FrameworkSettings;
/*    */ import com.bright.framework.language.util.LanguageUtils;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MarketingGroupForm extends Bn2Form
/*    */ {
/* 36 */   private MarketingGroup m_group = null;
/* 37 */   private List m_marketingGroups = null;
/*    */ 
/*    */   public MarketingGroup getGroup()
/*    */   {
/* 41 */     if (this.m_group == null)
/*    */     {
/* 43 */       this.m_group = new MarketingGroup();
/*    */ 
/* 46 */       if (FrameworkSettings.getSupportMultiLanguage())
/*    */       {
/* 48 */         LanguageUtils.createEmptyTranslations(this.m_group, 20);
/*    */       }
/*    */     }
/* 51 */     return this.m_group;
/*    */   }
/*    */ 
/*    */   public void setGroup(MarketingGroup a_group)
/*    */   {
/* 56 */     this.m_group = a_group;
/*    */   }
/*    */ 
/*    */   public List getMarketingGroups()
/*    */   {
/* 61 */     return this.m_marketingGroups;
/*    */   }
/*    */ 
/*    */   public void setMarketingGroups(List marketingGroups)
/*    */   {
/* 66 */     this.m_marketingGroups = marketingGroups;
/*    */   }
/*    */ 
/*    */   public int getNumMarketingGroups()
/*    */   {
/* 71 */     return this.m_marketingGroups == null ? 0 : this.m_marketingGroups.size();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.form.MarketingGroupForm
 * JD-Core Version:    0.6.0
 */