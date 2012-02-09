/*     */ package com.bright.assetbank.ecommerce.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.ecommerce.bean.Order;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class OrderPaymentForm extends Bn2Form
/*     */ {
/*  27 */   private boolean m_bTcsDone = false;
/*  28 */   private boolean m_bCommercialTcsDone = false;
/*     */ 
/*  30 */   private Order m_order = null;
/*     */   private HashMap m_purchaseForm;
/*     */   private Collection m_purchaseFormKeys;
/*     */   private String m_sPspUrl;
/*     */ 
/*     */   public OrderPaymentForm()
/*     */   {
/*  39 */     resetOrderPaymentForm();
/*     */   }
/*     */ 
/*     */   public void resetOrderPaymentForm()
/*     */   {
/*  47 */     resetFlags();
/*  48 */     resetErrors();
/*     */   }
/*     */ 
/*     */   public void resetFlags()
/*     */   {
/*  54 */     this.m_bTcsDone = false;
/*  55 */     this.m_bCommercialTcsDone = false;
/*     */   }
/*     */ 
/*     */   public void resetErrors()
/*     */   {
/*  63 */     getErrors().clear();
/*     */   }
/*     */ 
/*     */   public boolean getTcsDone()
/*     */   {
/*  68 */     return this.m_bTcsDone;
/*     */   }
/*     */ 
/*     */   public void setTcsDone(boolean a_sTcsDone)
/*     */   {
/*  73 */     this.m_bTcsDone = a_sTcsDone;
/*     */   }
/*     */ 
/*     */   public Order getOrder()
/*     */   {
/*  78 */     return this.m_order;
/*     */   }
/*     */ 
/*     */   public boolean getCommercialTcsDone()
/*     */   {
/*  83 */     return this.m_bCommercialTcsDone;
/*     */   }
/*     */ 
/*     */   public void setCommercialTcsDone(boolean a_sCommercialTcsDone)
/*     */   {
/*  88 */     this.m_bCommercialTcsDone = a_sCommercialTcsDone;
/*     */   }
/*     */ 
/*     */   public void setOrder(Order a_sOrder)
/*     */   {
/*  93 */     this.m_order = a_sOrder;
/*     */   }
/*     */ 
/*     */   public HashMap getPurchaseForm()
/*     */   {
/*  98 */     return this.m_purchaseForm;
/*     */   }
/*     */ 
/*     */   public void setPurchaseForm(HashMap a_sPurchaseForm)
/*     */   {
/* 103 */     this.m_purchaseForm = a_sPurchaseForm;
/*     */   }
/*     */ 
/*     */   public Collection getPurchaseFormKeys()
/*     */   {
/* 108 */     return this.m_purchaseFormKeys;
/*     */   }
/*     */ 
/*     */   public void setPurchaseFormKeys(Collection a_sPurchaseFormKeys)
/*     */   {
/* 113 */     this.m_purchaseFormKeys = a_sPurchaseFormKeys;
/*     */   }
/*     */ 
/*     */   public String getPspUrl()
/*     */   {
/* 118 */     return this.m_sPspUrl;
/*     */   }
/*     */ 
/*     */   public void setPspUrl(String a_sPspUrl)
/*     */   {
/* 123 */     this.m_sPspUrl = a_sPspUrl;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.form.OrderPaymentForm
 * JD-Core Version:    0.6.0
 */