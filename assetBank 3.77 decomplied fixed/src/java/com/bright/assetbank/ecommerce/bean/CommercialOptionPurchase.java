/*     */ package com.bright.assetbank.ecommerce.bean;
/*     */ 
/*     */ import com.bright.assetbank.commercialoption.bean.CommercialOption;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ 
/*     */ public class CommercialOptionPurchase
/*     */ {
/*  32 */   private CommercialOption m_commercialOption = null;
/*     */ 
/*  34 */   private BrightMoney m_price = null;
/*     */ 
/*  36 */   private String m_sDescription = null;
/*     */ 
/*     */   public CommercialOptionPurchase()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CommercialOptionPurchase(CommercialOption a_commOpt)
/*     */   {
/*  55 */     getCommercialOption().copyFrom(a_commOpt);
/*  56 */     getPrice().setAmount(a_commOpt.getPrice().getAmount());
/*  57 */     this.m_sDescription = "";
/*     */   }
/*     */ 
/*     */   public CommercialOption getCommercialOption()
/*     */   {
/*  65 */     if (this.m_commercialOption == null)
/*     */     {
/*  67 */       this.m_commercialOption = new CommercialOption();
/*     */     }
/*  69 */     return this.m_commercialOption;
/*     */   }
/*     */ 
/*     */   public void setCommercialOption(CommercialOption a_sCommercialOption)
/*     */   {
/*  77 */     this.m_commercialOption = a_sCommercialOption;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  85 */     return this.m_sDescription;
/*     */   }
/*     */ 
/*     */   public void setDescription(String a_sDescription)
/*     */   {
/*  93 */     this.m_sDescription = a_sDescription;
/*     */   }
/*     */ 
/*     */   public BrightMoney getPrice()
/*     */   {
/* 101 */     if (this.m_price == null)
/*     */     {
/* 103 */       this.m_price = new BrightMoney();
/*     */     }
/* 105 */     return this.m_price;
/*     */   }
/*     */ 
/*     */   public void setPrice(BrightMoney a_sPrice)
/*     */   {
/* 113 */     this.m_price = a_sPrice;
/*     */   }
/*     */ 
/*     */   public void setToDefault()
/*     */   {
/* 128 */     this.m_price = this.m_commercialOption.getPrice();
/* 129 */     this.m_sDescription = "";
/*     */   }
/*     */ 
/*     */   public String getFormDescription()
/*     */   {
/* 147 */     if ((this.m_sDescription != null) && (this.m_sDescription.compareTo("") == 0))
/*     */     {
/* 149 */       return this.m_commercialOption.getDescription();
/*     */     }
/*     */ 
/* 152 */     return getDescription();
/*     */   }
/*     */ 
/*     */   public void setFormDescription(String a_sDescription)
/*     */   {
/* 170 */     if ((this.m_commercialOption != null) && (a_sDescription.compareTo(this.m_commercialOption.getDescription()) == 0))
/*     */     {
/* 172 */       this.m_sDescription = "";
/*     */     }
/*     */     else
/*     */     {
/* 176 */       setDescription(a_sDescription);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.bean.CommercialOptionPurchase
 * JD-Core Version:    0.6.0
 */