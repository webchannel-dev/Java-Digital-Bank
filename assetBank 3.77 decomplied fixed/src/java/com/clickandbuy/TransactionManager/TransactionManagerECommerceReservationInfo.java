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
/*     */ public class TransactionManagerECommerceReservationInfo
/*     */   implements Serializable
/*     */ {
/*     */   private String expirationDateTime;
/*     */   private TransactionManagerECommerceReservationStatus status;
/*  65 */   private Object __equalsCalc = null;
/*     */ 
/*  87 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 105 */   private static TypeDesc typeDesc = new TypeDesc(TransactionManagerECommerceReservationInfo.class, true);
/*     */ 
/*     */   public TransactionManagerECommerceReservationInfo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TransactionManagerECommerceReservationInfo(String expirationDateTime, TransactionManagerECommerceReservationStatus status)
/*     */   {
/*  21 */     this.expirationDateTime = expirationDateTime;
/*  22 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getExpirationDateTime()
/*     */   {
/*  32 */     return this.expirationDateTime;
/*     */   }
/*     */ 
/*     */   public void setExpirationDateTime(String expirationDateTime)
/*     */   {
/*  42 */     this.expirationDateTime = expirationDateTime;
/*     */   }
/*     */ 
/*     */   public TransactionManagerECommerceReservationStatus getStatus()
/*     */   {
/*  52 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(TransactionManagerECommerceReservationStatus status)
/*     */   {
/*  62 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/*  67 */     if (!(obj instanceof TransactionManagerECommerceReservationInfo)) return false;
/*  68 */     TransactionManagerECommerceReservationInfo other = (TransactionManagerECommerceReservationInfo)obj;
/*  69 */     if (obj == null) return false;
/*  70 */     if (this == obj) return true;
/*  71 */     if (this.__equalsCalc != null) {
/*  72 */       return this.__equalsCalc == obj;
/*     */     }
/*  74 */     this.__equalsCalc = obj;
/*     */ 
/*  76 */     boolean _equals = ((this.expirationDateTime == null) && (other.getExpirationDateTime() == null)) || ((this.expirationDateTime != null) && (this.expirationDateTime.equals(other.getExpirationDateTime())) && (((this.status == null) && (other.getStatus() == null)) || ((this.status != null) && (this.status.equals(other.getStatus())))));
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
/*  94 */     if (getExpirationDateTime() != null) {
/*  95 */       _hashCode += getExpirationDateTime().hashCode();
/*     */     }
/*  97 */     if (getStatus() != null) {
/*  98 */       _hashCode += getStatus().hashCode();
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
/* 109 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.ECommerce.ReservationInfo"));
/* 110 */     ElementDesc elemField = new ElementDesc();
/* 111 */     elemField.setFieldName("expirationDateTime");
/* 112 */     elemField.setXmlName(new QName("", "expirationDateTime"));
/* 113 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 114 */     elemField.setNillable(false);
/* 115 */     typeDesc.addFieldDesc(elemField);
/* 116 */     elemField = new ElementDesc();
/* 117 */     elemField.setFieldName("status");
/* 118 */     elemField.setXmlName(new QName("", "status"));
/* 119 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.ECommerce.ReservationStatus"));
/* 120 */     elemField.setNillable(false);
/* 121 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerECommerceReservationInfo
 * JD-Core Version:    0.6.0
 */