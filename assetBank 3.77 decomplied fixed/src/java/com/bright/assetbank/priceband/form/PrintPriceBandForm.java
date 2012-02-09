/*    */ package com.bright.assetbank.priceband.form;
/*    */ 
/*    */ import com.bright.assetbank.priceband.bean.PrintPriceBand;
/*    */ import com.bright.framework.constant.FrameworkSettings;
/*    */ import com.bright.framework.language.util.LanguageUtils;
/*    */ import java.util.HashMap;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class PrintPriceBandForm extends PriceBandForm
/*    */ {
/* 30 */   private HashMap m_shippingCosts = null;
/* 31 */   private Vector m_regionList = null;
/* 32 */   private Vector m_quantityRangeList = null;
/*    */ 
/*    */   public PrintPriceBandForm()
/*    */   {
/* 40 */     this.m_priceBand = new PrintPriceBand();
/*    */ 
/* 43 */     if (FrameworkSettings.getSupportMultiLanguage())
/*    */     {
/* 45 */       LanguageUtils.createEmptyTranslations(this.m_priceBand, 20);
/*    */     }
/*    */   }
/*    */ 
/*    */   public Vector getRegionList()
/*    */   {
/* 51 */     return this.m_regionList;
/*    */   }
/*    */ 
/*    */   public void setRegionList(Vector a_sRegionList)
/*    */   {
/* 56 */     this.m_regionList = a_sRegionList;
/*    */   }
/*    */ 
/*    */   public HashMap getShippingCosts()
/*    */   {
/* 61 */     return this.m_shippingCosts;
/*    */   }
/*    */ 
/*    */   public void setShippingCosts(HashMap a_sShippingCosts)
/*    */   {
/* 66 */     this.m_shippingCosts = a_sShippingCosts;
/*    */   }
/*    */ 
/*    */   public Vector getQuantityRangeList()
/*    */   {
/* 71 */     return this.m_quantityRangeList;
/*    */   }
/*    */ 
/*    */   public void setQuantityRangeList(Vector a_sQuantityRangeList)
/*    */   {
/* 76 */     this.m_quantityRangeList = a_sQuantityRangeList;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.priceband.form.PrintPriceBandForm
 * JD-Core Version:    0.6.0
 */