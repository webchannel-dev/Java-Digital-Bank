/*     */ package com.bright.assetbank.ecommerce.bean;
/*     */ 
/*     */ import java.util.Date;
/*     */ 
/*     */ public class OrderSearchCriteria
/*     */ {
/*  31 */   private Date m_startDate = null;
/*  32 */   private Date m_endDate = null;
/*  33 */   private long m_lOrderId = 0L;
/*  34 */   private long m_lOrderPriceBandType = 0L;
/*  35 */   private long m_lOrderStatus = 0L;
/*  36 */   private long m_lOrderWorkflow = 0L;
/*  37 */   private String m_sPurchaseId = null;
/*  38 */   private long m_lUserId = 0L;
/*     */ 
/*     */   public Date getEndDate()
/*     */   {
/*  45 */     return this.m_endDate;
/*     */   }
/*     */ 
/*     */   public void setEndDate(Date a_sEndDate)
/*     */   {
/*  54 */     this.m_endDate = a_sEndDate;
/*     */   }
/*     */ 
/*     */   public long getOrderPriceBandType()
/*     */   {
/*  63 */     return this.m_lOrderPriceBandType;
/*     */   }
/*     */ 
/*     */   public void setOrderPriceBandType(long a_sOrderPriceBandType)
/*     */   {
/*  72 */     this.m_lOrderPriceBandType = a_sOrderPriceBandType;
/*     */   }
/*     */ 
/*     */   public long getOrderStatus()
/*     */   {
/*  81 */     return this.m_lOrderStatus;
/*     */   }
/*     */ 
/*     */   public void setOrderStatus(long a_sOrderStatus)
/*     */   {
/*  90 */     this.m_lOrderStatus = a_sOrderStatus;
/*     */   }
/*     */ 
/*     */   public long getOrderId()
/*     */   {
/*  99 */     return this.m_lOrderId;
/*     */   }
/*     */ 
/*     */   public void setOrderId(long a_sOrderId)
/*     */   {
/* 108 */     this.m_lOrderId = a_sOrderId;
/*     */   }
/*     */ 
/*     */   public Date getStartDate()
/*     */   {
/* 117 */     return this.m_startDate;
/*     */   }
/*     */ 
/*     */   public void setStartDate(Date a_sStartDate)
/*     */   {
/* 126 */     this.m_startDate = a_sStartDate;
/*     */   }
/*     */ 
/*     */   public String getPurchaseId()
/*     */   {
/* 135 */     return this.m_sPurchaseId;
/*     */   }
/*     */ 
/*     */   public void setPurchaseId(String a_sPurchaseId)
/*     */   {
/* 144 */     this.m_sPurchaseId = a_sPurchaseId;
/*     */   }
/*     */ 
/*     */   public long getOrderWorkflow()
/*     */   {
/* 153 */     return this.m_lOrderWorkflow;
/*     */   }
/*     */ 
/*     */   public void setOrderWorkflow(long a_sOrderWorkflow)
/*     */   {
/* 162 */     this.m_lOrderWorkflow = a_sOrderWorkflow;
/*     */   }
/*     */ 
/*     */   public long getUserId()
/*     */   {
/* 171 */     return this.m_lUserId;
/*     */   }
/*     */ 
/*     */   public void setUserId(long a_sUserId)
/*     */   {
/* 180 */     this.m_lUserId = a_sUserId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.bean.OrderSearchCriteria
 * JD-Core Version:    0.6.0
 */