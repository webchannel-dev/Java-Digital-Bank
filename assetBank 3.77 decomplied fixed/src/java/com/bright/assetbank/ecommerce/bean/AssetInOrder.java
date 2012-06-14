/*     */ package com.bright.assetbank.ecommerce.bean;
/*     */ 
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class AssetInOrder
/*     */ {
/*  33 */   private long m_lAssetId = 0L;
/*  34 */   private String m_sDescription = null;
/*  35 */   private BrightMoney m_price = null;
/*  36 */   private BrightMoney m_shippingCost = null;
/*     */ 
/*  40 */   private Vector m_vecPriceBands = null;
/*     */ 
/*     */   public AssetInOrder()
/*     */   {
/*  48 */     this.m_shippingCost = new BrightMoney();
/*     */   }
/*     */ 
/*     */   public Vector getPriceBands()
/*     */   {
/*  56 */     if (this.m_vecPriceBands == null)
/*     */     {
/*  58 */       this.m_vecPriceBands = new Vector();
/*     */     }
/*  60 */     return this.m_vecPriceBands;
/*     */   }
/*     */ 
/*     */   public void setPriceBands(Vector a_sPriceBands)
/*     */   {
/*  69 */     this.m_vecPriceBands = a_sPriceBands;
/*     */   }
/*     */ 
/*     */   public AssetPurchasePriceBand getPriceBandsIndexed(int a_iIndex)
/*     */   {
/*  76 */     for (int i = getPriceBands().size(); i <= a_iIndex; i++)
/*     */     {
/*  78 */       getPriceBands().add(new AssetPurchasePriceBand());
/*     */     }
/*     */ 
/*  81 */     return (AssetPurchasePriceBand)getPriceBands().get(a_iIndex);
/*     */   }
/*     */ 
/*     */   public BrightMoney getShippingCost()
/*     */   {
/*  90 */     if (this.m_shippingCost == null)
/*     */     {
/*  92 */       this.m_shippingCost = new BrightMoney();
/*     */     }
/*  94 */     return this.m_shippingCost;
/*     */   }
/*     */ 
/*     */   public void setShippingCost(BrightMoney a_sShippingCost)
/*     */   {
/* 103 */     this.m_shippingCost = a_sShippingCost;
/*     */   }
/*     */ 
/*     */   public long getAssetId()
/*     */   {
/* 110 */     return this.m_lAssetId;
/*     */   }
/*     */ 
/*     */   public void setAssetId(long a_lAssetId)
/*     */   {
/* 117 */     this.m_lAssetId = a_lAssetId;
/*     */   }
/*     */ 
/*     */   public BrightMoney getPrice()
/*     */   {
/* 124 */     if (this.m_price == null)
/*     */     {
/* 126 */       this.m_price = new BrightMoney();
/*     */     }
/*     */ 
/* 129 */     return this.m_price;
/*     */   }
/*     */ 
/*     */   public void setPrice(BrightMoney a_price)
/*     */   {
/* 136 */     this.m_price = a_price;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 143 */     return this.m_sDescription;
/*     */   }
/*     */ 
/*     */   public void setDescription(String a_sDescription)
/*     */   {
/* 150 */     this.m_sDescription = a_sDescription;
/*     */   }
/*     */ 
/*     */   public BrightMoney getTotalCost()
/*     */   {
/* 167 */     return new BrightMoney(this.m_price.getAmount() + this.m_shippingCost.getAmount());
/*     */   }
/*     */ 
/*     */   public long getCommercialCost()
/*     */   {
/* 183 */     long sum = 0L;
/*     */ 
/* 185 */     for (Iterator pbIt = this.m_vecPriceBands.iterator(); pbIt.hasNext(); )
/*     */     {
/* 187 */       sum += ((AssetPurchasePriceBand)pbIt.next()).getCommercialCost();
/*     */     }
/*     */ 
/* 190 */     return sum;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.bean.AssetInOrder
 * JD-Core Version:    0.6.0
 */