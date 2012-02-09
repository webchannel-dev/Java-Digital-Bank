/*    */ package com.bright.assetbank.priceband.form;
/*    */ 
/*    */ import com.bright.assetbank.priceband.bean.DownloadPriceBand;
/*    */ import com.bright.framework.constant.FrameworkSettings;
/*    */ import com.bright.framework.language.util.LanguageUtils;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class DownloadPriceBandForm extends PriceBandForm
/*    */ {
/* 35 */   private Vector m_usageTypeList = null;
/* 36 */   private Vector m_includedUsageList = null;
/*    */ 
/*    */   public DownloadPriceBandForm()
/*    */   {
/* 44 */     this.m_priceBand = new DownloadPriceBand();
/*    */ 
/* 47 */     if (FrameworkSettings.getSupportMultiLanguage())
/*    */     {
/* 49 */       LanguageUtils.createEmptyTranslations(this.m_priceBand, 20);
/*    */     }
/*    */   }
/*    */ 
/*    */   public Vector getUsageTypeList()
/*    */   {
/* 56 */     return this.m_usageTypeList;
/*    */   }
/*    */ 
/*    */   public void setUsageTypeList(Vector a_sUsageTypeList) {
/* 60 */     this.m_usageTypeList = a_sUsageTypeList;
/*    */   }
/*    */ 
/*    */   public Vector getIncludedUsageList()
/*    */   {
/* 66 */     return this.m_includedUsageList;
/*    */   }
/*    */ 
/*    */   public void setIncludedUsageList(Vector a_sIncludedUsageList) {
/* 70 */     this.m_includedUsageList = a_sIncludedUsageList;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.priceband.form.DownloadPriceBandForm
 * JD-Core Version:    0.6.0
 */