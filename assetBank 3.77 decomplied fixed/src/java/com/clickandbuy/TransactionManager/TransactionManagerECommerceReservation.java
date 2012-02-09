/*     */ package com.clickandbuy.TransactionManager;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.apache.axis.description.ElementDesc;
/*     */ import org.apache.axis.description.TypeDesc;
/*     */ import org.apache.axis.encoding.Deserializer;
/*     */ import org.apache.axis.encoding.Serializer;
/*     */ import org.apache.axis.encoding.ser.BeanDeserializer;
/*     */ import org.apache.axis.encoding.ser.BeanSerializer;
/*     */ 
/*     */ public class TransactionManagerECommerceReservation
/*     */   implements Serializable
/*     */ {
/*     */   private TransactionManagerPaymentPaymentResponse540 paymentResponse;
/*     */   private String expirationDateTime;
/*  65 */   private Object __equalsCalc = null;
/*     */ 
/*  87 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 105 */   private static TypeDesc typeDesc = new TypeDesc(TransactionManagerECommerceReservation.class, true);
/*     */ 
/*     */   public TransactionManagerECommerceReservation()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TransactionManagerECommerceReservation(TransactionManagerPaymentPaymentResponse540 paymentResponse, String expirationDateTime)
/*     */   {
/*  21 */     this.paymentResponse = paymentResponse;
/*  22 */     this.expirationDateTime = expirationDateTime;
/*     */   }
/*     */ 
/*     */   public TransactionManagerPaymentPaymentResponse540 getPaymentResponse()
/*     */   {
/*  32 */     return this.paymentResponse;
/*     */   }
/*     */ 
/*     */   public void setPaymentResponse(TransactionManagerPaymentPaymentResponse540 paymentResponse)
/*     */   {
/*  42 */     this.paymentResponse = paymentResponse;
/*     */   }
/*     */ 
/*     */   public String getExpirationDateTime()
/*     */   {
/*  52 */     return this.expirationDateTime;
/*     */   }
/*     */ 
/*     */   public void setExpirationDateTime(String expirationDateTime)
/*     */   {
/*  62 */     this.expirationDateTime = expirationDateTime;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/*  67 */     if (!(obj instanceof TransactionManagerECommerceReservation)) return false;
/*  68 */     TransactionManagerECommerceReservation other = (TransactionManagerECommerceReservation)obj;
/*  69 */     if (obj == null) return false;
/*  70 */     if (this == obj) return true;
/*  71 */     if (this.__equalsCalc != null) {
/*  72 */       return this.__equalsCalc == obj;
/*     */     }
/*  74 */     this.__equalsCalc = obj;
/*     */ 
/*  76 */     boolean _equals = ((this.paymentResponse == null) && (other.getPaymentResponse() == null)) || ((this.paymentResponse != null) && (this.paymentResponse.equals(other.getPaymentResponse())) && (((this.expirationDateTime == null) && (other.getExpirationDateTime() == null)) || ((this.expirationDateTime != null) && (this.expirationDateTime.equals(other.getExpirationDateTime())))));
/*     */ 
/*  83 */     this.__equalsCalc = null;
/*  84 */     return _equals;
/*     */   }
/*     */ 
/*     */   public synchronized int hashCode()
/*     */   {
/*  89 */     if (this.__hashCodeCalc) {
/*  90 */       return 0;
/*     */     }
/*  92 */     this.__hashCodeCalc = true;
/*  93 */     int _hashCode = 1;
/*  94 */     if (getPaymentResponse() != null) {
/*  95 */       _hashCode += getPaymentResponse().hashCode();
/*     */     }
/*  97 */     if (getExpirationDateTime() != null) {
/*  98 */       _hashCode += getExpirationDateTime().hashCode();
/*     */     }
/* 100 */     this.__hashCodeCalc = false;
/* 101 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 128 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 138 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 150 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 109 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.ECommerce.Reservation"));
/* 110 */     ElementDesc elemField = new ElementDesc();
/* 111 */     elemField.setFieldName("paymentResponse");
/* 112 */     elemField.setXmlName(new QName("", "paymentResponse"));
/* 113 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentResponse540"));
/* 114 */     elemField.setNillable(false);
/* 115 */     typeDesc.addFieldDesc(elemField);
/* 116 */     elemField = new ElementDesc();
/* 117 */     elemField.setFieldName("expirationDateTime");
/* 118 */     elemField.setXmlName(new QName("", "expirationDateTime"));
/* 119 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 120 */     elemField.setNillable(false);
/* 121 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerECommerceReservation
 * JD-Core Version:    0.6.0
 */