/*     */ package com.bright.assetbank.ecommerce.bean;
/*     */ 
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceSettings;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.framework.common.bean.Address;
/*     */ import com.bright.framework.common.bean.BrightDecimal;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class Order extends DataBean
/*     */ {
/*  54 */   private Date m_dtDatePlaced = null;
/*  55 */   private Date m_dtDateFulfilled = null;
/*  56 */   private ABUser m_user = null;
/*  57 */   private String userTelephone = null;
/*  58 */   private BrightMoney m_shippingCost = null;
/*  59 */   private BrightMoney m_basketCost = null;
/*  60 */   private BrightMoney m_subtotal = null;
/*     */   private BrightDecimal m_fVATPercent;
/*  62 */   private BrightMoney m_total = null;
/*     */ 
/*  65 */   private String m_purchaseId = null;
/*  66 */   private String m_pspTransId = null;
/*     */ 
/*  69 */   private Vector m_vecAssets = null;
/*     */ 
/*  72 */   private OrderStatus m_status = null;
/*     */ 
/*  75 */   private String m_sRecipientName = null;
/*     */ 
/*  78 */   private Address m_shippingAddress = null;
/*     */ 
/*  80 */   private String m_sUserNotes = null;
/*     */ 
/*  82 */   private boolean m_bPrefersOfflinePayment = false;
/*     */ 
/*  84 */   private int m_iDiscountPercentage = 0;
/*     */ 
/*     */   public Date getDatePlaced()
/*     */   {
/*  91 */     return this.m_dtDatePlaced;
/*     */   }
/*     */ 
/*     */   public void setDatePlaced(Date a_dtDatePlaced)
/*     */   {
/*  98 */     this.m_dtDatePlaced = a_dtDatePlaced;
/*     */   }
/*     */ 
/*     */   public ABUser getUser()
/*     */   {
/* 105 */     if (this.m_user == null)
/*     */     {
/* 107 */       this.m_user = new ABUser();
/*     */     }
/*     */ 
/* 110 */     return this.m_user;
/*     */   }
/*     */ 
/*     */   public void setUser(ABUser a_user)
/*     */   {
/* 117 */     this.m_user = a_user;
/*     */   }
/*     */ 
/*     */   public Vector getAssets()
/*     */   {
/* 124 */     return this.m_vecAssets;
/*     */   }
/*     */ 
/*     */   public void setAssets(Vector a_vecAssets)
/*     */   {
/* 131 */     this.m_vecAssets = a_vecAssets;
/*     */   }
/*     */ 
/*     */   public BrightDecimal getVatPercent()
/*     */   {
/* 138 */     if (this.m_fVATPercent == null)
/*     */     {
/* 140 */       this.m_fVATPercent = new BrightDecimal();
/*     */     }
/* 142 */     return this.m_fVATPercent;
/*     */   }
/*     */ 
/*     */   public void setVatPercent(BrightDecimal a_fPercent)
/*     */   {
/* 149 */     this.m_fVATPercent = a_fPercent;
/*     */   }
/*     */ 
/*     */   public BrightMoney getSubtotal()
/*     */   {
/* 156 */     if (this.m_subtotal == null)
/*     */     {
/* 158 */       this.m_subtotal = new BrightMoney();
/*     */     }
/*     */ 
/* 161 */     return this.m_subtotal;
/*     */   }
/*     */ 
/*     */   public void setSubtotal(BrightMoney a_sSubtotal)
/*     */   {
/* 168 */     this.m_subtotal = a_sSubtotal;
/*     */   }
/*     */ 
/*     */   public BrightMoney getTotal()
/*     */   {
/* 175 */     if (this.m_total == null)
/*     */     {
/* 177 */       this.m_total = new BrightMoney();
/*     */     }
/* 179 */     return this.m_total;
/*     */   }
/*     */ 
/*     */   public void setTotal(BrightMoney a_sTotal)
/*     */   {
/* 186 */     this.m_total = a_sTotal;
/*     */   }
/*     */ 
/*     */   public String getPspTransId()
/*     */   {
/* 192 */     return this.m_pspTransId;
/*     */   }
/*     */ 
/*     */   public void setPspTransId(String a_sPspTransId)
/*     */   {
/* 198 */     this.m_pspTransId = a_sPspTransId;
/*     */   }
/*     */ 
/*     */   public String getPurchaseId()
/*     */   {
/* 204 */     return this.m_purchaseId;
/*     */   }
/*     */ 
/*     */   public String getDisplayPurchaseId()
/*     */   {
/* 210 */     String sId = this.m_purchaseId;
/* 211 */     String sStem = EcommerceSettings.getPurchaseIdStem();
/* 212 */     if (StringUtil.stringIsPopulated(sStem))
/*     */     {
/* 214 */       sId = sStem + this.m_purchaseId;
/*     */     }
/* 216 */     return sId;
/*     */   }
/*     */ 
/*     */   public void setPurchaseId(String a_sPurchaseId)
/*     */   {
/* 222 */     this.m_purchaseId = a_sPurchaseId;
/*     */   }
/*     */ 
/*     */   public Address getShippingAddress()
/*     */   {
/* 231 */     if (this.m_shippingAddress == null)
/*     */     {
/* 233 */       this.m_shippingAddress = new Address();
/*     */     }
/* 235 */     return this.m_shippingAddress;
/*     */   }
/*     */ 
/*     */   public void setShippingAddress(Address a_sShippingAddress)
/*     */   {
/* 244 */     this.m_shippingAddress = a_sShippingAddress;
/*     */   }
/*     */ 
/*     */   public OrderStatus getStatus()
/*     */   {
/* 253 */     if (this.m_status == null)
/*     */     {
/* 255 */       this.m_status = new OrderStatus();
/*     */     }
/*     */ 
/* 258 */     return this.m_status;
/*     */   }
/*     */ 
/*     */   public void setStatus(OrderStatus a_sStatus)
/*     */   {
/* 267 */     this.m_status = a_sStatus;
/*     */   }
/*     */ 
/*     */   public String getRecipientName()
/*     */   {
/* 276 */     return this.m_sRecipientName;
/*     */   }
/*     */ 
/*     */   public void setRecipientName(String a_sRecipientName)
/*     */   {
/* 285 */     this.m_sRecipientName = a_sRecipientName;
/*     */   }
/*     */ 
/*     */   public boolean isPrefersOfflinePayment()
/*     */   {
/* 294 */     return this.m_bPrefersOfflinePayment;
/*     */   }
/*     */ 
/*     */   public void setPrefersOfflinePayment(boolean a_dtPrefersOfflinePayment)
/*     */   {
/* 303 */     this.m_bPrefersOfflinePayment = a_dtPrefersOfflinePayment;
/*     */   }
/*     */ 
/*     */   public Date getDateFulfilled()
/*     */   {
/* 312 */     return this.m_dtDateFulfilled;
/*     */   }
/*     */ 
/*     */   public void setDateFulfilled(Date a_dtDateFulfilled)
/*     */   {
/* 321 */     this.m_dtDateFulfilled = a_dtDateFulfilled;
/*     */   }
/*     */ 
/*     */   public String getUserNotes()
/*     */   {
/* 330 */     return this.m_sUserNotes;
/*     */   }
/*     */ 
/*     */   public void setUserNotes(String a_dtUserNotes)
/*     */   {
/* 339 */     this.m_sUserNotes = a_dtUserNotes;
/*     */   }
/*     */ 
/*     */   public BrightMoney getBasketCost()
/*     */   {
/* 349 */     if (this.m_basketCost == null)
/*     */     {
/* 351 */       this.m_basketCost = new BrightMoney();
/*     */     }
/* 353 */     return this.m_basketCost;
/*     */   }
/*     */ 
/*     */   public void setBasketCost(BrightMoney a_dtBasketCost)
/*     */   {
/* 362 */     this.m_basketCost = a_dtBasketCost;
/*     */   }
/*     */ 
/*     */   public BrightMoney getShippingCost()
/*     */   {
/* 371 */     if (this.m_shippingCost == null)
/*     */     {
/* 373 */       this.m_shippingCost = new BrightMoney();
/*     */     }
/* 375 */     return this.m_shippingCost;
/*     */   }
/*     */ 
/*     */   public void setShippingCost(BrightMoney a_dtShippingCost)
/*     */   {
/* 384 */     this.m_shippingCost = a_dtShippingCost;
/*     */   }
/*     */ 
/*     */   public void setDiscountPercentage(int a_iDiscountPercentage)
/*     */   {
/* 389 */     this.m_iDiscountPercentage = a_iDiscountPercentage;
/*     */   }
/*     */ 
/*     */   public int getDiscountPercentage()
/*     */   {
/* 394 */     return this.m_iDiscountPercentage;
/*     */   }
/*     */ 
/*     */   public String getUserTelephone()
/*     */   {
/* 399 */     return this.userTelephone;
/*     */   }
/*     */ 
/*     */   public void setUserTelephone(String userTelephone)
/*     */   {
/* 405 */     this.userTelephone = userTelephone;
/*     */   }
/*     */ 
/*     */   public boolean isPersonal()
/*     */   {
/* 422 */     return this.m_status.getOrderWorkflow().getId() == 1L;
/*     */   }
/*     */ 
/*     */   public boolean getHasPrints()
/*     */   {
        
/* 439 */     for (Iterator assetIt = this.m_vecAssets.iterator(); assetIt.hasNext(); )
/*     */     {
/* 441 */       AssetInOrder asset = (AssetInOrder)assetIt.next();
/* 442 */       for (Iterator priceBandIt = asset.getPriceBands().iterator(); priceBandIt.hasNext(); )
/*     */       {
/* 444 */         AssetPurchasePriceBand priceBand = (AssetPurchasePriceBand)priceBandIt.next();
/* 445 */         if (priceBand.getPriceBandType().getId() == 2L)
/*     */         {
/* 447 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */    // Iterator priceBandIt;
/* 452 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getHasDownloads()
/*     */   {
/* 469 */     for (Iterator assetIt = this.m_vecAssets.iterator(); assetIt.hasNext(); )
/*     */     {
/* 471 */       AssetInOrder asset = (AssetInOrder)assetIt.next();
/* 472 */       for (Iterator priceBandIt = asset.getPriceBands().iterator(); priceBandIt.hasNext(); )
/*     */       {
/* 474 */         AssetPurchasePriceBand priceBand = (AssetPurchasePriceBand)priceBandIt.next();
/* 475 */         if (priceBand.getPriceBandType().getId() == 1L)
/*     */         {
/* 477 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */     Iterator priceBandIt;
/* 482 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isStatusEditable()
/*     */   {
/* 499 */     return (this.m_status.getId() != 1L) && (this.m_status.getId() != 6L) && (this.m_status.getId() != 8L) && (this.m_status.getId() != 7L);
/*     */   }
/*     */ 
/*     */   public boolean isRequiresApproval()
/*     */   {
/* 523 */     return this.m_status.getId() == 3L;
/*     */   }
/*     */ 
/*     */   public boolean getRequiresOnlinePayment()
/*     */   {
/* 540 */     return this.m_status.getId() == 4L;
/*     */   }
/*     */ 
/*     */   public void recalculateCommercialCost()
/*     */   {
/* 556 */     long lSubTotal = 0L;
/*     */ 
/* 558 */     for (Iterator assetIt = this.m_vecAssets.iterator(); assetIt.hasNext(); )
/*     */     {
/* 560 */       lSubTotal += ((AssetInOrder)assetIt.next()).getCommercialCost();
/*     */     }
/*     */ 
/* 563 */     this.m_subtotal.setAmount(lSubTotal);
/*     */ 
/* 565 */     this.m_total.setAmount((long)(lSubTotal * (1.0D + this.m_fVATPercent.getNumber() * 0.01D)));
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.bean.Order
 * JD-Core Version:    0.6.0
 */