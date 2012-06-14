/*     */ package com.bright.assetbank.ecommerce.form;
/*     */ 
/*     */ import com.bright.assetbank.ecommerce.bean.CommercialOptionPurchase;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class CommercialOrderForm extends OrderForm
/*     */ {
/*  25 */   private Vector m_commercialOptionsList = null;
/*     */ 
/*  27 */   private CommercialOptionPurchase m_commercialOptionPurchase = null;
/*     */ 
/*  29 */   private long m_lPriceBandId = 0L;
/*  30 */   private long m_lOrderId = 0L;
/*  31 */   private long m_lAssetId = 0L;
/*     */ 
/*     */   public Vector getCommercialOptionsList()
/*     */   {
/*  38 */     return this.m_commercialOptionsList;
/*     */   }
/*     */ 
/*     */   public void setCommercialOptionsList(Vector a_sCommercialOptionsList)
/*     */   {
/*  47 */     this.m_commercialOptionsList = a_sCommercialOptionsList;
/*     */   }
/*     */ 
/*     */   public CommercialOptionPurchase getCommercialOptionPurchase()
/*     */   {
/*  56 */     return this.m_commercialOptionPurchase;
/*     */   }
/*     */ 
/*     */   public void setCommercialOptionPurchase(CommercialOptionPurchase a_sCommercialOptionPurchase)
/*     */   {
/*  65 */     this.m_commercialOptionPurchase = a_sCommercialOptionPurchase;
/*     */   }
/*     */ 
/*     */   public long getAssetId()
/*     */   {
/*  74 */     return this.m_lAssetId;
/*     */   }
/*     */ 
/*     */   public void setAssetId(long a_lAssetId)
/*     */   {
/*  83 */     this.m_lAssetId = a_lAssetId;
/*     */   }
/*     */ 
/*     */   public long getOrderId()
/*     */   {
/*  92 */     return this.m_lOrderId;
/*     */   }
/*     */ 
/*     */   public void setOrderId(long a_lOrderId)
/*     */   {
/* 101 */     this.m_lOrderId = a_lOrderId;
/*     */   }
/*     */ 
/*     */   public long getPriceBandId()
/*     */   {
/* 110 */     return this.m_lPriceBandId;
/*     */   }
/*     */ 
/*     */   public void setPriceBandId(long a_lPriceBandId)
/*     */   {
/* 119 */     this.m_lPriceBandId = a_lPriceBandId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.form.CommercialOrderForm
 * JD-Core Version:    0.6.0
 */