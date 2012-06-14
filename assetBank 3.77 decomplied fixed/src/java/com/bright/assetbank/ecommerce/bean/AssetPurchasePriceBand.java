/*     */ package com.bright.assetbank.ecommerce.bean;
/*     */ 
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ 
/*     */ public class AssetPurchasePriceBand
/*     */ {
/*  34 */   private StringDataBean m_priceBand = null;
/*     */ 
/*  36 */   private StringDataBean m_priceBandType = null;
/*     */ 
/*  38 */   private int m_iQuantity = 0;
/*     */ 
/*  40 */   private BrightMoney m_cost = null;
/*  41 */   private BrightMoney m_shippingCost = null;
/*     */ 
/*  44 */   private CommercialOptionPurchase m_commercialOptionPurchase = null;
/*     */ 
/*     */   public BrightMoney getCost()
/*     */   {
/*  52 */     if (this.m_cost == null)
/*     */     {
/*  54 */       this.m_cost = new BrightMoney();
/*     */     }
/*  56 */     return this.m_cost;
/*     */   }
/*     */ 
/*     */   public void setCost(BrightMoney a_sCost)
/*     */   {
/*  65 */     this.m_cost = a_sCost;
/*     */   }
/*     */ 
/*     */   public int getQuantity()
/*     */   {
/*  74 */     return this.m_iQuantity;
/*     */   }
/*     */ 
/*     */   public void setQuantity(int a_sQuantity)
/*     */   {
/*  83 */     this.m_iQuantity = a_sQuantity;
/*     */   }
/*     */ 
/*     */   public StringDataBean getPriceBand()
/*     */   {
/*  92 */     if (this.m_priceBand == null)
/*     */     {
/*  94 */       this.m_priceBand = new StringDataBean();
/*     */     }
/*  96 */     return this.m_priceBand;
/*     */   }
/*     */ 
/*     */   public void setPriceBand(StringDataBean a_sPriceBand)
/*     */   {
/* 105 */     this.m_priceBand = a_sPriceBand;
/*     */   }
/*     */ 
/*     */   public StringDataBean getPriceBandType()
/*     */   {
/* 114 */     if (this.m_priceBandType == null)
/*     */     {
/* 116 */       this.m_priceBandType = new StringDataBean();
/*     */     }
/* 118 */     return this.m_priceBandType;
/*     */   }
/*     */ 
/*     */   public void setPriceBandType(StringDataBean a_sPriceBandType)
/*     */   {
/* 127 */     this.m_priceBandType = a_sPriceBandType;
/*     */   }
/*     */ 
/*     */   public BrightMoney getShippingCost()
/*     */   {
/* 136 */     if (this.m_shippingCost == null)
/*     */     {
/* 138 */       this.m_shippingCost = new BrightMoney();
/*     */     }
/* 140 */     return this.m_shippingCost;
/*     */   }
/*     */ 
/*     */   public void setShippingCost(BrightMoney a_sShippingCost)
/*     */   {
/* 149 */     this.m_shippingCost = a_sShippingCost;
/*     */   }
/*     */ 
/*     */   public BrightMoney getTotalCost()
/*     */   {
/* 166 */     return new BrightMoney(this.m_cost.getAmount() + this.m_shippingCost.getAmount());
/*     */   }
/*     */ 
/*     */   public CommercialOptionPurchase getCommercialOptionPurchase()
/*     */   {
/* 174 */     if (this.m_commercialOptionPurchase == null)
/*     */     {
/* 176 */       this.m_commercialOptionPurchase = new CommercialOptionPurchase();
/*     */     }
/* 178 */     return this.m_commercialOptionPurchase;
/*     */   }
/*     */ 
/*     */   public void setCommercialOptionPurchase(CommercialOptionPurchase a_sCommercialOption)
/*     */   {
/* 187 */     this.m_commercialOptionPurchase = a_sCommercialOption;
/*     */   }
/*     */ 
/*     */   public long getCommercialCost()
/*     */   {
/* 200 */     return this.m_commercialOptionPurchase.getPrice().getAmount();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.bean.AssetPurchasePriceBand
 * JD-Core Version:    0.6.0
 */