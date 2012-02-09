/*     */ package com.bright.assetbank.ecommerce.bean;
/*     */ 
/*     */ import com.bright.assetbank.commercialoption.bean.CommercialOption;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceSettings;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class AssetPurchase extends Purchase
/*     */ {
/*  40 */   private boolean m_bHasPrints = false;
/*     */ 
/*  43 */   private boolean m_bHasDownloads = false;
/*     */ 
/*  46 */   private boolean m_bHasCommercialUsage = false;
/*     */ 
/*  49 */   private boolean m_bHasNonCommercialUsage = false;
/*     */ 
/*  52 */   private BrightMoney m_basketCost = null;
/*     */ 
/*  54 */   private int m_iDiscountPercentage = 0;
/*     */   private Vector m_assetList;
/*     */ 
/*     */   public AssetPurchase()
/*     */   {
/*  63 */     this.m_basketCost = new BrightMoney();
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  81 */     String sDescription = EcommerceSettings.getPspDescStem() + " ";
/*     */ 
/*  83 */     sDescription = sDescription + convertAssetsToString(getAssetList());
/*     */ 
/*  85 */     return sDescription;
/*     */   }
/*     */ 
/*     */   public void setFlags()
/*     */   {
/* 103 */     this.m_bHasPrints = false;
/* 104 */     this.m_bHasDownloads = false;
/* 105 */     this.m_bHasCommercialUsage = false;
/* 106 */     this.m_bHasNonCommercialUsage = false;
/*     */ 
/* 108 */     if ((this.m_assetList != null) && (this.m_assetList.size() > 0))
/*     */     {
/* 110 */       for (int i = 0; i < this.m_assetList.size(); i++)
/*     */       {
/* 112 */         AssetInOrder aio = (AssetInOrder)this.m_assetList.get(i);
/* 113 */         Vector vecPriceBands = aio.getPriceBands();
/*     */ 
/* 115 */         if (vecPriceBands == null)
/*     */           continue;
/* 117 */         for (int j = 0; j < vecPriceBands.size(); j++)
/*     */         {
/* 119 */           AssetPurchasePriceBand appb = (AssetPurchasePriceBand)vecPriceBands.get(j);
/* 120 */           if (appb.getPriceBandType().getId() == 2L)
/*     */           {
/* 122 */             this.m_bHasPrints = true;
/*     */           }
/* 124 */           else if (appb.getPriceBandType().getId() == 1L)
/*     */           {
/* 126 */             this.m_bHasDownloads = true;
/*     */           }
/*     */ 
/* 129 */           if (appb.getCommercialOptionPurchase().getCommercialOption().getId() != 0L)
/*     */           {
/* 131 */             this.m_bHasCommercialUsage = true;
/*     */           }
/*     */           else
/*     */           {
/* 135 */             this.m_bHasNonCommercialUsage = true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String convertAssetsToString(Vector a_vec)
/*     */   {
/* 150 */     StringBuffer sb = new StringBuffer("");
/*     */ 
/* 152 */     if ((a_vec != null) && (a_vec.size() > 0))
/*     */     {
/* 155 */       AssetInOrder aio = (AssetInOrder)a_vec.get(0);
/* 156 */       sb.append(aio.getAssetId());
/*     */ 
/* 158 */       if (a_vec.size() > 1)
/*     */       {
/* 160 */         for (int i = 1; i < a_vec.size(); i++)
/*     */         {
/* 162 */           aio = (AssetInOrder)a_vec.get(i);
/* 163 */           sb.append(", " + aio.getAssetId());
/*     */         }
/*     */       }
/*     */     }
/* 167 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public Vector getAssetList()
/*     */   {
/* 184 */     if (this.m_assetList == null)
/*     */     {
/* 186 */       this.m_assetList = new Vector();
/*     */     }
/* 188 */     return this.m_assetList;
/*     */   }
/*     */ 
/*     */   public void setAssetList(Vector a_sAssetList)
/*     */   {
/* 196 */     this.m_assetList = a_sAssetList;
/*     */   }
/*     */ 
/*     */   public boolean getHasCommercialUsage()
/*     */   {
/* 205 */     return this.m_bHasCommercialUsage;
/*     */   }
/*     */ 
/*     */   public void setHasCommercialUsage(boolean a_bHasCommercialUsage)
/*     */   {
/* 210 */     this.m_bHasCommercialUsage = a_bHasCommercialUsage;
/*     */   }
/*     */ 
/*     */   public boolean getHasDownloads()
/*     */   {
/* 219 */     return this.m_bHasDownloads;
/*     */   }
/*     */ 
/*     */   public boolean getHasNonCommercialUsage()
/*     */   {
/* 228 */     return this.m_bHasNonCommercialUsage;
/*     */   }
/*     */ 
/*     */   public boolean getHasPrints()
/*     */   {
/* 237 */     return this.m_bHasPrints;
/*     */   }
/*     */ 
/*     */   public BrightMoney getBasketCost()
/*     */   {
/* 246 */     return this.m_basketCost;
/*     */   }
/*     */ 
/*     */   public void setBasketCost(BrightMoney a_dtBasketCost)
/*     */   {
/* 255 */     this.m_basketCost = a_dtBasketCost;
/*     */   }
/*     */ 
/*     */   public void setDiscountPercentage(int a_iDiscountPercentage)
/*     */   {
/* 261 */     this.m_iDiscountPercentage = a_iDiscountPercentage;
/*     */   }
/*     */ 
/*     */   public int getDiscountPercentage()
/*     */   {
/* 266 */     return this.m_iDiscountPercentage;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.bean.AssetPurchase
 * JD-Core Version:    0.6.0
 */