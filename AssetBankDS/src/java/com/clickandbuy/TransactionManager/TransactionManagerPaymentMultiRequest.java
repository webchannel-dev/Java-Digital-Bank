/*     */ package com.clickandbuy.TransactionManager;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.apache.axis.description.ElementDesc;
/*     */ import org.apache.axis.description.TypeDesc;
/*     */ import org.apache.axis.encoding.Deserializer;
/*     */ import org.apache.axis.encoding.Serializer;
/*     */ import org.apache.axis.encoding.ser.BeanDeserializer;
/*     */ import org.apache.axis.encoding.ser.BeanSerializer;
/*     */ 
/*     */ public class TransactionManagerPaymentMultiRequest
/*     */   implements Serializable
/*     */ {
/*     */   private long sellerID;
/*     */   private String tmPassword;
/*     */   private long extJobID;
/*     */   private TransactionManagerPaymentPaymentRequest[] requestList;
/* 113 */   private Object __equalsCalc = null;
/*     */ 
/* 137 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 165 */   private static TypeDesc typeDesc = new TypeDesc(TransactionManagerPaymentMultiRequest.class, true);
/*     */ 
/*     */   public TransactionManagerPaymentMultiRequest()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TransactionManagerPaymentMultiRequest(long sellerID, String tmPassword, long extJobID, TransactionManagerPaymentPaymentRequest[] requestList)
/*     */   {
/*  27 */     this.sellerID = sellerID;
/*  28 */     this.tmPassword = tmPassword;
/*  29 */     this.extJobID = extJobID;
/*  30 */     this.requestList = requestList;
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
/*     */   public TransactionManagerPaymentPaymentRequest[] getRequestList()
/*     */   {
/* 100 */     return this.requestList;
/*     */   }
/*     */ 
/*     */   public void setRequestList(TransactionManagerPaymentPaymentRequest[] requestList)
/*     */   {
/* 110 */     this.requestList = requestList;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/* 115 */     if (!(obj instanceof TransactionManagerPaymentMultiRequest)) return false;
/* 116 */     TransactionManagerPaymentMultiRequest other = (TransactionManagerPaymentMultiRequest)obj;
/* 117 */     if (obj == null) return false;
/* 118 */     if (this == obj) return true;
/* 119 */     if (this.__equalsCalc != null) {
/* 120 */       return this.__equalsCalc == obj;
/*     */     }
/* 122 */     this.__equalsCalc = obj;
/*     */ 
/* 124 */     boolean _equals = (this.sellerID == other.getSellerID()) && (((this.tmPassword == null) && (other.getTmPassword() == null)) || ((this.tmPassword != null) && (this.tmPassword.equals(other.getTmPassword())) && (this.extJobID == other.getExtJobID()) && (((this.requestList == null) && (other.getRequestList() == null)) || ((this.requestList != null) && (Arrays.equals(this.requestList, other.getRequestList()))))));
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
/* 149 */     if (getRequestList() != null) {
/* 150 */       int i = 0;
/* 151 */       while (i < Array.getLength(getRequestList()))
/*     */       {
/* 153 */         Object obj = Array.get(getRequestList(), i);
/* 154 */         if ((obj != null) && (!obj.getClass().isArray()))
/*     */         {
/* 156 */           _hashCode += obj.hashCode();
/*     */         }
/* 152 */         i++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 160 */     this.__hashCodeCalc = false;
/* 161 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 201 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 211 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 223 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 169 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.MultiRequest"));
/* 170 */     ElementDesc elemField = new ElementDesc();
/* 171 */     elemField.setFieldName("sellerID");
/* 172 */     elemField.setXmlName(new QName("", "sellerID"));
/* 173 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 174 */     elemField.setNillable(false);
/* 175 */     typeDesc.addFieldDesc(elemField);
/* 176 */     elemField = new ElementDesc();
/* 177 */     elemField.setFieldName("tmPassword");
/* 178 */     elemField.setXmlName(new QName("", "tmPassword"));
/* 179 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 180 */     elemField.setNillable(false);
/* 181 */     typeDesc.addFieldDesc(elemField);
/* 182 */     elemField = new ElementDesc();
/* 183 */     elemField.setFieldName("extJobID");
/* 184 */     elemField.setXmlName(new QName("", "extJobID"));
/* 185 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 186 */     elemField.setNillable(false);
/* 187 */     typeDesc.addFieldDesc(elemField);
/* 188 */     elemField = new ElementDesc();
/* 189 */     elemField.setFieldName("requestList");
/* 190 */     elemField.setXmlName(new QName("", "requestList"));
/* 191 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentRequest"));
/* 192 */     elemField.setNillable(false);
/* 193 */     elemField.setItemQName(new QName("", "item"));
/* 194 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerPaymentMultiRequest
 * JD-Core Version:    0.6.0
 */