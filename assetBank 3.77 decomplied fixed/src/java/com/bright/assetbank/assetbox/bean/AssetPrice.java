/*     */ package com.bright.assetbank.assetbox.bean;
/*     */ 
/*     */ import com.bright.assetbank.priceband.bean.PriceBand;
/*     */ import com.bright.assetbank.priceband.bean.PrintPriceBand;
/*     */ import com.bright.assetbank.priceband.bean.QuantityRange;
/*     */ import com.bright.assetbank.priceband.bean.ShippingCost;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.common.bean.TranslatableStringDataBean;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class AssetPrice
/*     */ {
/*  30 */   private PriceBand m_priceBand = null;
/*  31 */   private int m_iQuantity = 0;
/*  32 */   private long m_lAssetId = 0L;
/*     */ 
/*     */   public BrightMoney getAssetPrice()
/*     */   {
/*  57 */     long lAssetBasePrice = getPriceBand().getBasePrice().getAmount();
/*  58 */     long lExtraPrice = getPriceBand().getUnitPrice().getAmount() * getQuantity();
/*  59 */     long lAssetTotal = lAssetBasePrice + lExtraPrice;
/*     */ 
/*  61 */     BrightMoney price = new BrightMoney(lAssetTotal);
/*  62 */     return price;
/*     */   }
/*     */ 
/*     */   public BrightMoney getShippingCost(long a_lTaxRegionId)
/*     */   {
/*  79 */     BrightMoney cost = new BrightMoney();
/*     */ 
/*  82 */     if (this.m_priceBand.getPriceBandType().getId() == 2L)
/*     */     {
/*  84 */       PrintPriceBand ppb = (PrintPriceBand)this.m_priceBand;
/*  85 */       Vector vecShipping = ppb.getShippingCostList();
/*     */ 
/*  88 */       long lMinCost = -1L;
/*  89 */       Iterator it = vecShipping.iterator();
/*  90 */       while (it.hasNext())
/*     */       {
/*  92 */         ShippingCost sc = (ShippingCost)it.next();
/*     */ 
/*  95 */         if ((sc.getTaxRegion().getId() == a_lTaxRegionId) && (this.m_iQuantity >= sc.getQuantityRange().getLowerLimit()))
/*     */         {
/*  97 */           long lCost = sc.getPrice().getAmount();
/*  98 */           if ((lMinCost == -1L) || (lMinCost > lCost))
/*     */           {
/* 100 */             lMinCost = lCost;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 105 */       if (lMinCost > 0L)
/*     */       {
/* 107 */         cost.setAmount(lMinCost);
/*     */       }
/*     */     }
/*     */ 
/* 111 */     return cost;
/*     */   }
/*     */ 
/*     */   public String getHashInAssetBox()
/*     */   {
/* 132 */     return String.valueOf(this.m_lAssetId).concat("_").concat(String.valueOf(this.m_priceBand.getId()));
/*     */   }
/*     */ 
/*     */   public int getQuantity()
/*     */   {
/* 140 */     return this.m_iQuantity;
/*     */   }
/*     */ 
/*     */   public void setQuantity(int a_sQuantity) {
/* 144 */     this.m_iQuantity = a_sQuantity;
/*     */   }
/*     */ 
/*     */   public long getAssetId() {
/* 148 */     return this.m_lAssetId;
/*     */   }
/*     */ 
/*     */   public void setAssetId(long a_sAssetId) {
/* 152 */     this.m_lAssetId = a_sAssetId;
/*     */   }
/*     */ 
/*     */   public PriceBand getPriceBand() {
/* 156 */     return this.m_priceBand;
/*     */   }
/*     */ 
/*     */   public void setPriceBand(PriceBand a_sPriceBand) {
/* 160 */     this.m_priceBand = a_sPriceBand;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.bean.AssetPrice
 * JD-Core Version:    0.6.0
 */