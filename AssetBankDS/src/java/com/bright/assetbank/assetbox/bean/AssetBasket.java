/*     */ package com.bright.assetbank.assetbox.bean;
/*     */ 
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.commercialoption.bean.CommercialOption;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceSettings;
/*     */ import com.bright.assetbank.priceband.bean.DownloadPriceBand;
/*     */ import com.bright.assetbank.priceband.bean.PriceBand;
/*     */ import com.bright.assetbank.tax.bean.TaxValue;
/*     */ import com.bright.assetbank.tax.bean.TaxablePrice;
/*     */ import com.bright.framework.common.bean.BrightDecimal;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.common.bean.TranslatableStringDataBean;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class AssetBasket extends AssetBox
/*     */ {
/*  44 */   private TaxValue m_taxValue = null;
/*  45 */   private TaxablePrice m_price = null;
/*  46 */   private String m_sTaxNumber = "";
/*  47 */   private BrightMoney m_shippingTotal = null;
/*  48 */   private StringDataBean m_shippingRegion = null;
/*  49 */   private BrightMoney m_basketTotal = null;
/*  50 */   private HashMap m_hmAssetPrices = null;
/*  51 */   private HashMap m_hmAssetPriceAmounts = null;
/*  52 */   private HashMap m_hmCommercialOptions = null;
/*  53 */   private boolean m_bHasCommercialUsage = false;
/*  54 */   private boolean m_bHasNonCommercialUsage = false;
/*     */ 
/*     */   public AssetBasket()
/*     */   {
/*  61 */     this.m_hmAssetPrices = new HashMap(50);
/*  62 */     this.m_hmAssetPriceAmounts = new HashMap(50);
/*  63 */     this.m_hmCommercialOptions = new HashMap(50);
/*     */ 
/*  67 */     this.m_taxValue = new TaxValue();
/*  68 */     this.m_taxValue.getTaxPercent().setNumber(EcommerceSettings.getVATPercent());
/*  69 */     this.m_taxValue.getTaxType().setName(EcommerceSettings.getVATName());
/*     */ 
/*  72 */     this.m_price = new TaxablePrice(this.m_taxValue, 0L);
/*     */ 
/*  75 */     this.m_shippingTotal = new BrightMoney();
/*  76 */     this.m_basketTotal = new BrightMoney();
/*     */ 
/*  78 */     this.m_shippingRegion = new StringDataBean();
/*     */   }
/*     */ 
/*     */   public Vector getAssetsWithPriceBandsAsVec()
/*     */   {
/*  89 */     return new Vector(getAssetsWithPriceBands());
/*     */   }
/*     */ 
/*     */   public Collection getAssetsWithPriceBands()
/*     */   {
/* 104 */     Collection colAssetList = new TreeSet(new AssetBoxComparator());
/* 105 */     Iterator it = this.m_hmAssets.values().iterator();
/*     */ 
/* 107 */     while (it.hasNext())
/*     */     {
/* 109 */       AssetInList ail = (AssetInList)it.next();
/*     */ 
/* 111 */       if (containsPriceBand(ail.getId()))
/*     */       {
/* 113 */         colAssetList.add(ail);
/*     */       }
/*     */     }
/*     */ 
/* 117 */     return colAssetList;
/*     */   }
/*     */ 
/*     */   public Collection getAssetsRequiringPurchaseWithoutPriceBands()
/*     */   {
/* 132 */     Collection colAssetList = new TreeSet(new AssetBoxComparator());
/* 133 */     Iterator it = this.m_hmAssets.values().iterator();
/*     */ 
/* 135 */     while (it.hasNext())
/*     */     {
/* 137 */       AssetInList ail = (AssetInList)it.next();
/*     */ 
/* 139 */       if ((ail.getIsApprovalRequestable()) && (!containsPriceBand(ail.getId())))
/*     */       {
/* 141 */         colAssetList.add(ail);
/*     */       }
/*     */     }
/*     */ 
/* 145 */     return colAssetList;
/*     */   }
/*     */ 
/*     */   public HashMap getAssetPriceAmounts()
/*     */   {
/* 150 */     return this.m_hmAssetPriceAmounts;
/*     */   }
/*     */ 
/*     */   public HashMap getAssetPrices()
/*     */   {
/* 165 */     return this.m_hmAssetPrices;
/*     */   }
/*     */ 
/*     */   public void setAssetPrices(HashMap a_hm)
/*     */   {
/* 180 */     this.m_hmAssetPrices = a_hm;
/* 181 */     recalculate();
/*     */   }
/*     */ 
/*     */   public Vector getPricesForAsset(long a_lAssetId)
/*     */   {
/* 197 */     Long olId = new Long(a_lAssetId);
/*     */ 
/* 199 */     Vector vecPrices = (Vector)this.m_hmAssetPrices.get(olId);
/* 200 */     return vecPrices;
/*     */   }
/*     */ 
/*     */   public void setPricesForAsset(long a_lAssetId, Vector a_vecAssetPrices)
/*     */   {
/* 216 */     Long olId = new Long(a_lAssetId);
/* 217 */     this.m_hmAssetPrices.put(olId, a_vecAssetPrices);
/* 218 */     recalculate();
/*     */   }
/*     */ 
/*     */   public boolean containsPriceBand(long a_lId)
/*     */   {
/* 234 */     boolean bContainsPriceBand = false;
/*     */ 
/* 236 */     if (this.m_hmAssetPrices.containsKey(new Long(a_lId)))
/*     */     {
/* 238 */       Vector vecPrices = (Vector)this.m_hmAssetPrices.get(new Long(a_lId));
/* 239 */       if (vecPrices.size() > 0)
/*     */       {
/* 241 */         bContainsPriceBand = true;
/*     */       }
/*     */     }
/* 244 */     return bContainsPriceBand;
/*     */   }
/*     */ 
/*     */   public Vector getAssetPrices(long a_lId)
/*     */   {
/* 260 */     return (Vector)this.m_hmAssetPrices.get(new Long(a_lId));
/*     */   }
/*     */ 
/*     */   public Vector getAssetPricesWithInt(int a_lId)
/*     */   {
/* 271 */     return getAssetPrices(new Long(a_lId).longValue());
/*     */   }
/*     */ 
/*     */   public int getNumAssetPrices()
/*     */   {
/* 287 */     return this.m_hmAssetPrices.size();
/*     */   }
/*     */ 
/*     */   public void removeAssetPrices(long a_lId)
/*     */   {
/* 302 */     Long olId = new Long(a_lId);
/*     */     Iterator itPrices;
/* 304 */     if (this.m_hmAssetPrices.containsKey(olId))
/*     */     {
/* 307 */       Vector vecAssetPrices = (Vector)this.m_hmAssetPrices.remove(olId);
/*     */ 
/* 310 */       for (itPrices = vecAssetPrices.iterator(); itPrices.hasNext(); )
/*     */       {
/* 312 */         removeAssetPriceBandCommercialOption(((AssetPrice)itPrices.next()).getHashInAssetBox());
/*     */       }
/*     */     }
/* 315 */     recalculate();
/*     */   }
/*     */ 
/*     */   public void removeAllAssetPrices()
/*     */   {
/* 331 */     this.m_hmAssetPrices.clear();
/*     */ 
/* 334 */     removeAllAssetPriceBandCommercialOption();
/* 335 */     recalculate();
/*     */   }
/*     */ 
/*     */   private void removeAssetPriceBandCommercialOption(String a_sHashInAssetBox)
/*     */   {
/* 352 */     this.m_hmCommercialOptions.remove(a_sHashInAssetBox);
/*     */   }
/*     */ 
/*     */   private void removeAllAssetPriceBandCommercialOption()
/*     */   {
/* 367 */     this.m_hmCommercialOptions.clear();
/*     */   }
/*     */ 
/*     */   public boolean getHasCommercialUsage()
/*     */   {
/* 377 */     return this.m_bHasCommercialUsage;
/*     */   }
/*     */ 
/*     */   public boolean getHasNonCommercialUsage()
/*     */   {
/* 387 */     return this.m_bHasNonCommercialUsage;
/*     */   }
/*     */ 
/*     */   public CommercialOption getAssetPriceCommercialOption(String a_sAssetPriceHash)
/*     */   {
/* 400 */     if (this.m_hmCommercialOptions.containsKey(a_sAssetPriceHash))
/*     */     {
/* 402 */       return (CommercialOption)this.m_hmCommercialOptions.get(a_sAssetPriceHash);
/*     */     }
/*     */ 
/* 405 */     CommercialOption newCommOpt = new CommercialOption();
/* 406 */     this.m_hmCommercialOptions.put(a_sAssetPriceHash, newCommOpt);
/* 407 */     return newCommOpt;
/*     */   }
/*     */ 
/*     */   public HashMap getAssetPriceCommercialOptions()
/*     */   {
/* 417 */     return this.m_hmCommercialOptions;
/*     */   }
/*     */ 
/*     */   public boolean addAsset(AssetInList a_assetRecord)
/*     */   {
/* 426 */     boolean alreadyInBasket = super.addAsset(a_assetRecord);
/*     */ 
/* 428 */     if (!alreadyInBasket) {
/* 429 */       recalculate();
/*     */     }
/*     */ 
/* 432 */     return alreadyInBasket;
/*     */   }
/*     */ 
/*     */   public void removeAllAssets()
/*     */   {
/* 440 */     super.removeAllAssets();
/* 441 */     recalculate();
/*     */   }
/*     */ 
/*     */   public void removeAsset(long a_lAssetId)
/*     */   {
/* 449 */     super.removeAsset(a_lAssetId);
/* 450 */     recalculate();
/*     */   }
/*     */ 
/*     */   private void recalculate()
/*     */   {
/* 455 */     long lBasketTotal = 0L;
/* 456 */     long lShippingTotal = 0L;
/*     */ 
/* 458 */     if (AssetBankSettings.getUsePriceBands())
/*     */     {
/* 460 */       this.m_hmAssetPriceAmounts.clear();
/*     */ 
/* 463 */       this.m_bHasPrint = false;
/* 464 */       this.m_bHasCommercialUsage = false;
/* 465 */       this.m_bHasNonCommercialUsage = false;
/*     */ 
/* 468 */       Iterator itAssets = this.m_hmAssetPrices.keySet().iterator();
/* 469 */       while (itAssets.hasNext())
/*     */       {
/* 472 */         Long olAssetId = (Long)itAssets.next();
/*     */ 
/* 475 */         Vector vecPrices = (Vector)this.m_hmAssetPrices.get(olAssetId);
/*     */ 
/* 478 */         long lAssetTotal = 0L;
/* 479 */         Iterator itPrices = vecPrices.iterator();
/* 480 */         while (itPrices.hasNext())
/*     */         {
/* 482 */           AssetPrice price = (AssetPrice)itPrices.next();
/* 483 */           lAssetTotal += price.getAssetPrice().getAmount();
/*     */ 
/* 485 */           if (AssetBankSettings.getShippingCalculationType() == 1)
/*     */           {
/* 488 */             lShippingTotal += price.getShippingCost(this.m_shippingRegion.getId()).getAmount();
/*     */           }
/* 490 */           else if (AssetBankSettings.getShippingCalculationType() == 2)
/*     */           {
/* 492 */             if (lShippingTotal == 0L)
/*     */             {
/* 494 */               lShippingTotal += price.getShippingCost(this.m_shippingRegion.getId()).getAmount();
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 499 */           if (price.getPriceBand().getPriceBandType().getId() == 2L)
/*     */           {
/* 501 */             this.m_bHasPrint = true;
/* 502 */             this.m_bHasNonCommercialUsage = true;
/*     */           }
/*     */ 
/* 506 */           if (price.getPriceBand().getPriceBandType().getId() == 1L)
/*     */           {
/* 508 */             DownloadPriceBand dpb = (DownloadPriceBand)price.getPriceBand();
/* 509 */             if (dpb.getIsCommercial())
/*     */             {
/* 511 */               this.m_bHasCommercialUsage = true;
/*     */             }
/*     */             else
/*     */             {
/* 515 */               this.m_bHasNonCommercialUsage = true;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 522 */         BrightMoney assetTotal = new BrightMoney(lAssetTotal);
/* 523 */         this.m_hmAssetPriceAmounts.put(olAssetId, assetTotal);
/*     */ 
/* 526 */         lBasketTotal += lAssetTotal;
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 533 */       Iterator it = this.m_hmAssets.values().iterator();
/* 534 */       while (it.hasNext())
/*     */       {
/* 536 */         AssetInList ail = (AssetInList)it.next();
/*     */ 
/* 538 */         if (ail.getIsApprovalRequestable())
/*     */         {
/* 540 */           lBasketTotal += ail.getAsset().getPrice().getAmount();
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 546 */     this.m_basketTotal.setAmount(lBasketTotal);
/* 547 */     this.m_shippingTotal.setAmount(lShippingTotal);
/*     */ 
/* 549 */     long lSubtotal = lBasketTotal + lShippingTotal;
/*     */ 
/* 552 */     this.m_price = new TaxablePrice(this.m_taxValue, lSubtotal);
/* 553 */     this.m_price.setTaxNumber(this.m_sTaxNumber);
/*     */   }
/*     */ 
/*     */   public void calculateCommercialCost()
/*     */   {
/* 571 */     long lSubtotal = 0L;
/*     */ 
/* 573 */     for (Iterator itCommOpt = this.m_hmCommercialOptions.values().iterator(); itCommOpt.hasNext(); )
/*     */     {
/* 575 */       lSubtotal += ((CommercialOption)itCommOpt.next()).getPrice().getAmount();
/*     */     }
/*     */ 
/* 579 */     this.m_price = new TaxablePrice(this.m_taxValue, lSubtotal);
/* 580 */     this.m_price.setTaxNumber(this.m_sTaxNumber);
/*     */   }
/*     */ 
/*     */   public BrightMoney getBasketTotal()
/*     */   {
/* 590 */     return this.m_basketTotal;
/*     */   }
/*     */ 
/*     */   public BrightMoney getShippingTotal()
/*     */   {
/* 600 */     return this.m_shippingTotal;
/*     */   }
/*     */ 
/*     */   public TaxablePrice getPrice()
/*     */   {
/* 610 */     return this.m_price;
/*     */   }
/*     */ 
/*     */   public void setTaxValue(TaxValue a_sTaxValue)
/*     */   {
/* 620 */     this.m_taxValue = a_sTaxValue;
/* 621 */     recalculate();
/*     */   }
/*     */ 
/*     */   public void setTaxNumber(String a_sTaxNumber)
/*     */   {
/* 626 */     this.m_sTaxNumber = a_sTaxNumber;
/* 627 */     recalculate();
/*     */   }
/*     */ 
/*     */   public boolean getIsShippingRegionSet()
/*     */   {
/* 639 */     boolean bSet = this.m_shippingRegion.getId() > 0L;
/* 640 */     return bSet;
/*     */   }
/*     */ 
/*     */   public BrightMoney getTotalWithDiscount(int a_iDiscount)
/*     */   {
/* 645 */     BrightMoney total = getPrice().getTotalAmount();
/* 646 */     return adjustTotalWithDiscount(total, a_iDiscount);
/*     */   }
/*     */ 
/*     */   public BrightMoney getTotalWithDiscountPriceBands(int a_iDiscount)
/*     */   {
/* 651 */     BrightMoney total = getBasketTotal();
/* 652 */     return adjustTotalWithDiscount(total, a_iDiscount);
/*     */   }
/*     */ 
/*     */   private BrightMoney adjustTotalWithDiscount(BrightMoney a_total, int a_iDiscount)
/*     */   {
/* 658 */     double dNewTotal = a_total.getAmount() * a_iDiscount / 100.0D;
/* 659 */     long lNewTotal = a_total.getAmount() - Math.round(dNewTotal);
/*     */ 
/* 661 */     BrightMoney newTotal = new BrightMoney(lNewTotal);
/*     */ 
/* 663 */     return newTotal;
/*     */   }
/*     */ 
/*     */   public StringDataBean getShippingRegion()
/*     */   {
/* 668 */     return this.m_shippingRegion;
/*     */   }
/*     */ 
/*     */   public void setShippingRegion(StringDataBean a_sShippingRegion)
/*     */   {
/* 673 */     this.m_shippingRegion = a_sShippingRegion;
/* 674 */     recalculate();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.bean.AssetBasket
 * JD-Core Version:    0.6.0
 */