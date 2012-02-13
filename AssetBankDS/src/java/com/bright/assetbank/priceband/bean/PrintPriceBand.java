/*    */ package com.bright.assetbank.priceband.bean;
/*    */ 
/*    */ import com.bright.framework.common.bean.BrightNaturalNumber;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class PrintPriceBand extends PriceBand
/*    */ {
/* 35 */   private HashMap m_shippingCosts = null;
/*    */   private BrightNaturalNumber m_maxQuantity;
/*    */ 
/*    */   public PrintPriceBand()
/*    */   {
/* 46 */     this.m_maxQuantity = new BrightNaturalNumber();
/*    */   }
/*    */ 
/*    */   public Vector getShippingCostList()
/*    */   {
/* 62 */     Vector vec = new Vector();
/*    */ 
/* 65 */     if (this.m_shippingCosts != null)
/*    */     {
/* 68 */       Iterator itRegion = this.m_shippingCosts.values().iterator();
/* 69 */       while (itRegion.hasNext())
/*    */       {
/* 71 */         HashMap hmQuantityRanges = (HashMap)itRegion.next();
/* 72 */         vec.addAll(hmQuantityRanges.values());
/*    */       }
/*    */     }
/*    */ 
/* 76 */     return vec;
/*    */   }
/*    */ 
/*    */   public HashMap getShippingCostsMatrix()
/*    */   {
/* 82 */     return this.m_shippingCosts;
/*    */   }
/*    */ 
/*    */   public void setShippingCostsMatrix(HashMap a_sShippingCosts)
/*    */   {
/* 87 */     this.m_shippingCosts = a_sShippingCosts;
/*    */   }
/*    */ 
/*    */   public BrightNaturalNumber getMaxQuantity()
/*    */   {
/* 93 */     return this.m_maxQuantity;
/*    */   }
/*    */ 
/*    */   public void setMaxQuantity(BrightNaturalNumber a_sMaxQuantity)
/*    */   {
/* 99 */     this.m_maxQuantity = a_sMaxQuantity;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.priceband.bean.PrintPriceBand
 * JD-Core Version:    0.6.0
 */