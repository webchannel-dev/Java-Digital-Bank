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
/*     */ public class TransactionManagerPaymentPaymentRequest
/*     */   implements Serializable
/*     */ {
/*     */   private TransactionManagerPaymentTransactionType discriminator;
/*     */   private ClickAndBuyEasyCollectEasyCollectQuery debReq;
/*     */   private ClickAndBuyEasyCollectEasyCollectQuery credReq;
/*     */   private ClickAndBuyTransactionCancelQuery cancReq;
/*     */   private ClickAndBuyTransactionCancelQuery partcancReq;
/* 137 */   private Object __equalsCalc = null;
/*     */ 
/* 168 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 195 */   private static TypeDesc typeDesc = new TypeDesc(TransactionManagerPaymentPaymentRequest.class, true);
/*     */ 
/*     */   public TransactionManagerPaymentPaymentRequest()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TransactionManagerPaymentPaymentRequest(TransactionManagerPaymentTransactionType discriminator, ClickAndBuyEasyCollectEasyCollectQuery debReq, ClickAndBuyEasyCollectEasyCollectQuery credReq, ClickAndBuyTransactionCancelQuery cancReq, ClickAndBuyTransactionCancelQuery partcancReq)
/*     */   {
/*  30 */     this.discriminator = discriminator;
/*  31 */     this.debReq = debReq;
/*  32 */     this.credReq = credReq;
/*  33 */     this.cancReq = cancReq;
/*  34 */     this.partcancReq = partcancReq;
/*     */   }
/*     */ 
/*     */   public TransactionManagerPaymentTransactionType getDiscriminator()
/*     */   {
/*  44 */     return this.discriminator;
/*     */   }
/*     */ 
/*     */   public void setDiscriminator(TransactionManagerPaymentTransactionType discriminator)
/*     */   {
/*  54 */     this.discriminator = discriminator;
/*     */   }
/*     */ 
/*     */   public ClickAndBuyEasyCollectEasyCollectQuery getDebReq()
/*     */   {
/*  64 */     return this.debReq;
/*     */   }
/*     */ 
/*     */   public void setDebReq(ClickAndBuyEasyCollectEasyCollectQuery debReq)
/*     */   {
/*  74 */     this.debReq = debReq;
/*     */   }
/*     */ 
/*     */   public ClickAndBuyEasyCollectEasyCollectQuery getCredReq()
/*     */   {
/*  84 */     return this.credReq;
/*     */   }
/*     */ 
/*     */   public void setCredReq(ClickAndBuyEasyCollectEasyCollectQuery credReq)
/*     */   {
/*  94 */     this.credReq = credReq;
/*     */   }
/*     */ 
/*     */   public ClickAndBuyTransactionCancelQuery getCancReq()
/*     */   {
/* 104 */     return this.cancReq;
/*     */   }
/*     */ 
/*     */   public void setCancReq(ClickAndBuyTransactionCancelQuery cancReq)
/*     */   {
/* 114 */     this.cancReq = cancReq;
/*     */   }
/*     */ 
/*     */   public ClickAndBuyTransactionCancelQuery getPartcancReq()
/*     */   {
/* 124 */     return this.partcancReq;
/*     */   }
/*     */ 
/*     */   public void setPartcancReq(ClickAndBuyTransactionCancelQuery partcancReq)
/*     */   {
/* 134 */     this.partcancReq = partcancReq;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/* 139 */     if (!(obj instanceof TransactionManagerPaymentPaymentRequest)) return false;
/* 140 */     TransactionManagerPaymentPaymentRequest other = (TransactionManagerPaymentPaymentRequest)obj;
/* 141 */     if (obj == null) return false;
/* 142 */     if (this == obj) return true;
/* 143 */     if (this.__equalsCalc != null) {
/* 144 */       return this.__equalsCalc == obj;
/*     */     }
/* 146 */     this.__equalsCalc = obj;
/*     */ 
/* 148 */     boolean _equals = ((this.discriminator == null) && (other.getDiscriminator() == null)) || ((this.discriminator != null) && (this.discriminator.equals(other.getDiscriminator())) && (((this.debReq == null) && (other.getDebReq() == null)) || ((this.debReq != null) && (this.debReq.equals(other.getDebReq())) && (((this.credReq == null) && (other.getCredReq() == null)) || ((this.credReq != null) && (this.credReq.equals(other.getCredReq())) && (((this.cancReq == null) && (other.getCancReq() == null)) || ((this.cancReq != null) && (this.cancReq.equals(other.getCancReq())) && (((this.partcancReq == null) && (other.getPartcancReq() == null)) || ((this.partcancReq != null) && (this.partcancReq.equals(other.getPartcancReq())))))))))));
/*     */ 
/* 164 */     this.__equalsCalc = null;
/* 165 */     return _equals;
/*     */   }
/*     */ 
/*     */   public synchronized int hashCode()
/*     */   {
/* 170 */     if (this.__hashCodeCalc) {
/* 171 */       return 0;
/*     */     }
/* 173 */     this.__hashCodeCalc = true;
/* 174 */     int _hashCode = 1;
/* 175 */     if (getDiscriminator() != null) {
/* 176 */       _hashCode += getDiscriminator().hashCode();
/*     */     }
/* 178 */     if (getDebReq() != null) {
/* 179 */       _hashCode += getDebReq().hashCode();
/*     */     }
/* 181 */     if (getCredReq() != null) {
/* 182 */       _hashCode += getCredReq().hashCode();
/*     */     }
/* 184 */     if (getCancReq() != null) {
/* 185 */       _hashCode += getCancReq().hashCode();
/*     */     }
/* 187 */     if (getPartcancReq() != null) {
/* 188 */       _hashCode += getPartcancReq().hashCode();
/*     */     }
/* 190 */     this.__hashCodeCalc = false;
/* 191 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 240 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 250 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 262 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 199 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentRequest"));
/* 200 */     ElementDesc elemField = new ElementDesc();
/* 201 */     elemField.setFieldName("discriminator");
/* 202 */     elemField.setXmlName(new QName("", "discriminator"));
/* 203 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.TransactionType"));
/* 204 */     elemField.setNillable(false);
/* 205 */     typeDesc.addFieldDesc(elemField);
/* 206 */     elemField = new ElementDesc();
/* 207 */     elemField.setFieldName("debReq");
/* 208 */     elemField.setXmlName(new QName("", "debReq"));
/* 209 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectQuery"));
/* 210 */     elemField.setMinOccurs(0);
/* 211 */     elemField.setNillable(false);
/* 212 */     typeDesc.addFieldDesc(elemField);
/* 213 */     elemField = new ElementDesc();
/* 214 */     elemField.setFieldName("credReq");
/* 215 */     elemField.setXmlName(new QName("", "credReq"));
/* 216 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectQuery"));
/* 217 */     elemField.setMinOccurs(0);
/* 218 */     elemField.setNillable(false);
/* 219 */     typeDesc.addFieldDesc(elemField);
/* 220 */     elemField = new ElementDesc();
/* 221 */     elemField.setFieldName("cancReq");
/* 222 */     elemField.setXmlName(new QName("", "cancReq"));
/* 223 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CancelQuery"));
/* 224 */     elemField.setMinOccurs(0);
/* 225 */     elemField.setNillable(false);
/* 226 */     typeDesc.addFieldDesc(elemField);
/* 227 */     elemField = new ElementDesc();
/* 228 */     elemField.setFieldName("partcancReq");
/* 229 */     elemField.setXmlName(new QName("", "partcancReq"));
/* 230 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CancelQuery"));
/* 231 */     elemField.setMinOccurs(0);
/* 232 */     elemField.setNillable(false);
/* 233 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentRequest
 * JD-Core Version:    0.6.0
 */