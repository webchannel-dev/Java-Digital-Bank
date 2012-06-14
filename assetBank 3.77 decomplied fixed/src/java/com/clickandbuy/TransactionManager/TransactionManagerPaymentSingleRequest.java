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
/*     */ public class TransactionManagerPaymentSingleRequest
/*     */   implements Serializable
/*     */ {
/*     */   private long sellerID;
/*     */   private String tmPassword;
/*     */   private long extJobID;
/*     */   private TransactionManagerPaymentPaymentRequest request;
/* 113 */   private Object __equalsCalc = null;
/*     */ 
/* 137 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 157 */   private static TypeDesc typeDesc = new TypeDesc(TransactionManagerPaymentSingleRequest.class, true);
/*     */ 
/*     */   public TransactionManagerPaymentSingleRequest()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TransactionManagerPaymentSingleRequest(long sellerID, String tmPassword, long extJobID, TransactionManagerPaymentPaymentRequest request)
/*     */   {
/*  27 */     this.sellerID = sellerID;
/*  28 */     this.tmPassword = tmPassword;
/*  29 */     this.extJobID = extJobID;
/*  30 */     this.request = request;
/*     */   }
/*     */ 
/*     */   public long getSellerID()
/*     */   {
/*  40 */     return this.sellerID;
/*     */   }
/*     */ 
/*     */   public void setSellerID(long sellerID)
/*     */   {
/*  50 */     this.sellerID = sellerID;
/*     */   }
/*     */ 
/*     */   public String getTmPassword()
/*     */   {
/*  60 */     return this.tmPassword;
/*     */   }
/*     */ 
/*     */   public void setTmPassword(String tmPassword)
/*     */   {
/*  70 */     this.tmPassword = tmPassword;
/*     */   }
/*     */ 
/*     */   public long getExtJobID()
/*     */   {
/*  80 */     return this.extJobID;
/*     */   }
/*     */ 
/*     */   public void setExtJobID(long extJobID)
/*     */   {
/*  90 */     this.extJobID = extJobID;
/*     */   }
/*     */ 
/*     */   public TransactionManagerPaymentPaymentRequest getRequest()
/*     */   {
/* 100 */     return this.request;
/*     */   }
/*     */ 
/*     */   public void setRequest(TransactionManagerPaymentPaymentRequest request)
/*     */   {
/* 110 */     this.request = request;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/* 115 */     if (!(obj instanceof TransactionManagerPaymentSingleRequest)) return false;
/* 116 */     TransactionManagerPaymentSingleRequest other = (TransactionManagerPaymentSingleRequest)obj;
/* 117 */     if (obj == null) return false;
/* 118 */     if (this == obj) return true;
/* 119 */     if (this.__equalsCalc != null) {
/* 120 */       return this.__equalsCalc == obj;
/*     */     }
/* 122 */     this.__equalsCalc = obj;
/*     */ 
/* 124 */     boolean _equals = (this.sellerID == other.getSellerID()) && (((this.tmPassword == null) && (other.getTmPassword() == null)) || ((this.tmPassword != null) && (this.tmPassword.equals(other.getTmPassword())) && (this.extJobID == other.getExtJobID()) && (((this.request == null) && (other.getRequest() == null)) || ((this.request != null) && (this.request.equals(other.getRequest()))))));
/*     */ 
/* 133 */     this.__equalsCalc = null;
/* 134 */     return _equals;
/*     */   }
/*     */ 
/*     */   public synchronized int hashCode()
/*     */   {
/* 139 */     if (this.__hashCodeCalc) {
/* 140 */       return 0;
/*     */     }
/* 142 */     this.__hashCodeCalc = true;
/* 143 */     int _hashCode = 1;
/* 144 */     _hashCode += new Long(getSellerID()).hashCode();
/* 145 */     if (getTmPassword() != null) {
/* 146 */       _hashCode += getTmPassword().hashCode();
/*     */     }
/* 148 */     _hashCode += new Long(getExtJobID()).hashCode();
/* 149 */     if (getRequest() != null) {
/* 150 */       _hashCode += getRequest().hashCode();
/*     */     }
/* 152 */     this.__hashCodeCalc = false;
/* 153 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 192 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 202 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 214 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 161 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.SingleRequest"));
/* 162 */     ElementDesc elemField = new ElementDesc();
/* 163 */     elemField.setFieldName("sellerID");
/* 164 */     elemField.setXmlName(new QName("", "sellerID"));
/* 165 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 166 */     elemField.setNillable(false);
/* 167 */     typeDesc.addFieldDesc(elemField);
/* 168 */     elemField = new ElementDesc();
/* 169 */     elemField.setFieldName("tmPassword");
/* 170 */     elemField.setXmlName(new QName("", "tmPassword"));
/* 171 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 172 */     elemField.setNillable(false);
/* 173 */     typeDesc.addFieldDesc(elemField);
/* 174 */     elemField = new ElementDesc();
/* 175 */     elemField.setFieldName("extJobID");
/* 176 */     elemField.setXmlName(new QName("", "extJobID"));
/* 177 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 178 */     elemField.setNillable(false);
/* 179 */     typeDesc.addFieldDesc(elemField);
/* 180 */     elemField = new ElementDesc();
/* 181 */     elemField.setFieldName("request");
/* 182 */     elemField.setXmlName(new QName("", "request"));
/* 183 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentRequest"));
/* 184 */     elemField.setNillable(false);
/* 185 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerPaymentSingleRequest
 * JD-Core Version:    0.6.0
 */