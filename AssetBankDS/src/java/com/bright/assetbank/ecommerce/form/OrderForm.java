/*     */ package com.bright.assetbank.ecommerce.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.ecommerce.bean.Order;
/*     */ import com.bright.assetbank.ecommerce.bean.OrderSearchCriteria;
/*     */ import com.bright.assetbank.usage.form.DateRangeForm;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class OrderForm extends DateRangeForm
/*     */ {
/*  43 */   private Vector m_orderList = null;
/*  44 */   private Order m_order = null;
/*     */ 
/*  46 */   private Vector m_orderStatusList = null;
/*  47 */   private Vector m_orderPriceBandTypeSearchList = null;
/*     */ 
/*  50 */   private String m_sPurchaseId = null;
/*  51 */   private long m_lOrderStatus = 0L;
/*  52 */   private long m_lWithPriceBandType = 0L;
/*  53 */   private long m_lOrderWorkflow = 0L;
/*     */ 
/*  55 */   private boolean m_bCommercialOptionsExist = false;
/*     */ 
/*     */   public OrderForm()
/*     */   {
/*  60 */     this.m_order = new Order();
/*     */   }
/*     */ 
/*     */   public OrderSearchCriteria getSearchCriteria(HttpServletRequest a_request, UserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager)
/*     */     throws Bn2Exception
/*     */   {
/*  79 */     OrderSearchCriteria searchCriteria = new OrderSearchCriteria();
/*     */ 
/*  81 */     validate(a_request, a_userProfile, a_dbTransaction, a_listManager);
/*  82 */     if (!getHasErrors())
/*     */     {
/*  84 */       processReportDates();
/*     */     }
/*     */ 
/*  87 */     searchCriteria.setStartDate(getStartDate());
/*  88 */     searchCriteria.setEndDate(getEndDate());
/*     */ 
/*  91 */     if (getPurchaseId() != "")
/*     */     {
/*  93 */       searchCriteria.setPurchaseId(getPurchaseId());
/*     */     }
/*  95 */     searchCriteria.setOrderStatus(getOrderStatus());
/*  96 */     searchCriteria.setOrderWorkflow(getOrderWorkflow());
/*  97 */     searchCriteria.setOrderPriceBandType(getWithPriceBandType());
/*     */ 
/*  99 */     return searchCriteria;
/*     */   }
/*     */ 
/*     */   public void setSearchFields(OrderSearchCriteria a_orderSearchCriteria)
/*     */   {
/* 116 */     DateFormat format = AssetBankSettings.getStandardDateFormat();
/*     */ 
/* 118 */     if (a_orderSearchCriteria.getStartDate() != null)
/*     */     {
/* 120 */       setStartDateString(format.format(a_orderSearchCriteria.getStartDate()));
/*     */     }
/* 122 */     if (a_orderSearchCriteria.getEndDate() != null)
/*     */     {
/* 124 */       setEndDateString(format.format(a_orderSearchCriteria.getEndDate()));
/*     */     }
/* 126 */     setPurchaseId(a_orderSearchCriteria.getPurchaseId());
/* 127 */     setOrderStatus(a_orderSearchCriteria.getOrderStatus());
/* 128 */     setOrderWorkflow(a_orderSearchCriteria.getOrderWorkflow());
/* 129 */     setWithPriceBandType(a_orderSearchCriteria.getOrderPriceBandType());
/*     */   }
/*     */ 
/*     */   public Order getOrder()
/*     */   {
/* 138 */     return this.m_order;
/*     */   }
/*     */ 
/*     */   public void setOrder(Order a_sOrder)
/*     */   {
/* 147 */     this.m_order = a_sOrder;
/*     */   }
/*     */ 
/*     */   public Vector getOrderList()
/*     */   {
/* 156 */     return this.m_orderList;
/*     */   }
/*     */ 
/*     */   public void setOrderList(Vector a_sOrderList)
/*     */   {
/* 165 */     this.m_orderList = a_sOrderList;
/*     */   }
/*     */ 
/*     */   public long getOrderStatus()
/*     */   {
/* 174 */     return this.m_lOrderStatus;
/*     */   }
/*     */ 
/*     */   public void setOrderStatus(long a_sOrderStatus)
/*     */   {
/* 183 */     this.m_lOrderStatus = a_sOrderStatus;
/*     */   }
/*     */ 
/*     */   public long getWithPriceBandType()
/*     */   {
/* 192 */     return this.m_lWithPriceBandType;
/*     */   }
/*     */ 
/*     */   public void setWithPriceBandType(long a_sWithPriceBandType)
/*     */   {
/* 201 */     this.m_lWithPriceBandType = a_sWithPriceBandType;
/*     */   }
/*     */ 
/*     */   public String getPurchaseId()
/*     */   {
/* 210 */     return this.m_sPurchaseId;
/*     */   }
/*     */ 
/*     */   public void setPurchaseId(String a_sPurchaseId)
/*     */   {
/* 219 */     this.m_sPurchaseId = a_sPurchaseId;
/*     */   }
/*     */ 
/*     */   public long getOrderWorkflow()
/*     */   {
/* 228 */     return this.m_lOrderWorkflow;
/*     */   }
/*     */ 
/*     */   public void setOrderWorkflow(long a_sOrderWorkflow)
/*     */   {
/* 237 */     this.m_lOrderWorkflow = a_sOrderWorkflow;
/*     */   }
/*     */ 
/*     */   public Vector getOrderStatusList()
/*     */   {
/* 246 */     return this.m_orderStatusList;
/*     */   }
/*     */ 
/*     */   public void setOrderStatusList(Vector a_sOrderStatusList)
/*     */   {
/* 255 */     this.m_orderStatusList = a_sOrderStatusList;
/*     */   }
/*     */ 
/*     */   public Vector getOrderPriceBandTypeSearchList()
/*     */   {
/* 264 */     return this.m_orderPriceBandTypeSearchList;
/*     */   }
/*     */ 
/*     */   public void setOrderPriceBandTypeSearchList(Vector a_sOrderPriceBandTypeSearchList)
/*     */   {
/* 273 */     this.m_orderPriceBandTypeSearchList = a_sOrderPriceBandTypeSearchList;
/*     */   }
/*     */ 
/*     */   public boolean getCommercialOptionsExist()
/*     */   {
/* 279 */     return this.m_bCommercialOptionsExist;
/*     */   }
/*     */ 
/*     */   public void setCommercialOptionsExist(boolean a_bCommercialOptionsExist)
/*     */   {
/* 284 */     this.m_bCommercialOptionsExist = a_bCommercialOptionsExist;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.form.OrderForm
 * JD-Core Version:    0.6.0
 */