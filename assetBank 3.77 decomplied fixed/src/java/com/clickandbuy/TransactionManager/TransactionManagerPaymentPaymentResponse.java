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
/*     */ public class TransactionManagerPaymentPaymentResponse
/*     */   implements Serializable
/*     */ {
/*     */   private TransactionManagerPaymentTransactionType discriminator;
/*     */   private ClickAndBuyEasyCollectEasyCollectResult debResp;
/*     */   private ClickAndBuyEasyCollectEasyCollectResult credResp;
/*     */   private ClickAndBuyTransactionCancelResult cancResp;
/*     */   private ClickAndBuyTransactionCancelResult partcancResp;
/* 137 */   private Object __equalsCalc = null;
/*     */ 
/* 168 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 195 */   private static TypeDesc typeDesc = new TypeDesc(TransactionManagerPaymentPaymentResponse.class, true);
/*     */ 
/*     */   public TransactionManagerPaymentPaymentResponse()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TransactionManagerPaymentPaymentResponse(TransactionManagerPaymentTransactionType discriminator, ClickAndBuyEasyCollectEasyCollectResult debResp, ClickAndBuyEasyCollectEasyCollectResult credResp, ClickAndBuyTransactionCancelResult cancResp, ClickAndBuyTransactionCancelResult partcancResp)
/*     */   {
/*  30 */     this.discriminator = discriminator;
/*  31 */     this.debResp = debResp;
/*  32 */     this.credResp = credResp;
/*  33 */     this.cancResp = cancResp;
/*  34 */     this.partcancResp = partcancResp;
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
/*     */   public ClickAndBuyEasyCollectEasyCollectResult getDebResp()
/*     */   {
/*  64 */     return this.debResp;
/*     */   }
/*     */ 
/*     */   public void setDebResp(ClickAndBuyEasyCollectEasyCollectResult debResp)
/*     */   {
/*  74 */     this.debResp = debResp;
/*     */   }
/*     */ 
/*     */   public ClickAndBuyEasyCollectEasyCollectResult getCredResp()
/*     */   {
/*  84 */     return this.credResp;
/*     */   }
/*     */ 
/*     */   public void setCredResp(ClickAndBuyEasyCollectEasyCollectResult credResp)
/*     */   {
/*  94 */     this.credResp = credResp;
/*     */   }
/*     */ 
/*     */   public ClickAndBuyTransactionCancelResult getCancResp()
/*     */   {
/* 104 */     return this.cancResp;
/*     */   }
/*     */ 
/*     */   public void setCancResp(ClickAndBuyTransactionCancelResult cancResp)
/*     */   {
/* 114 */     this.cancResp = cancResp;
/*     */   }
/*     */ 
/*     */   public ClickAndBuyTransactionCancelResult getPartcancResp()
/*     */   {
/* 124 */     return this.partcancResp;
/*     */   }
/*     */ 
/*     */   public void setPartcancResp(ClickAndBuyTransactionCancelResult partcancResp)
/*     */   {
/* 134 */     this.partcancResp = partcancResp;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/* 139 */     if (!(obj instanceof TransactionManagerPaymentPaymentResponse)) return false;
/* 140 */     TransactionManagerPaymentPaymentResponse other = (TransactionManagerPaymentPaymentResponse)obj;
/* 141 */     if (obj == null) return false;
/* 142 */     if (this == obj) return true;
/* 143 */     if (this.__equalsCalc != null) {
/* 144 */       return this.__equalsCalc == obj;
/*     */     }
/* 146 */     this.__equalsCalc = obj;
/*     */ 
/* 148 */     boolean _equals = ((this.discriminator == null) && (other.getDiscriminator() == null)) || ((this.discriminator != null) && (this.discriminator.equals(other.getDiscriminator())) && (((this.debResp == null) && (other.getDebResp() == null)) || ((this.debResp != null) && (this.debResp.equals(other.getDebResp())) && (((this.credResp == null) && (other.getCredResp() == null)) || ((this.credResp != null) && (this.credResp.equals(other.getCredResp())) && (((this.cancResp == null) && (other.getCancResp() == null)) || ((this.cancResp != null) && (this.cancResp.equals(other.getCancResp())) && (((this.partcancResp == null) && (other.getPartcancResp() == null)) || ((this.partcancResp != null) && (this.partcancResp.equals(other.getPartcancResp())))))))))));
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
/* 178 */     if (getDebResp() != null) {
/* 179 */       _hashCode += getDebResp().hashCode();
/*     */     }
/* 181 */     if (getCredResp() != null) {
/* 182 */       _hashCode += getCredResp().hashCode();
/*     */     }
/* 184 */     if (getCancResp() != null) {
/* 185 */       _hashCode += getCancResp().hashCode();
/*     */     }
/* 187 */     if (getPartcancResp() != null) {
/* 188 */       _hashCode += getPartcancResp().hashCode();
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
/* 199 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentResponse"));
/* 200 */     ElementDesc elemField = new ElementDesc();
/* 201 */     elemField.setFieldName("discriminator");
/* 202 */     elemField.setXmlName(new QName("", "discriminator"));
/* 203 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.TransactionType"));
/* 204 */     elemField.setNillable(false);
/* 205 */     typeDesc.addFieldDesc(elemField);
/* 206 */     elemField = new ElementDesc();
/* 207 */     elemField.setFieldName("debResp");
/* 208 */     elemField.setXmlName(new QName("", "debResp"));
/* 209 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectResult"));
/* 210 */     elemField.setMinOccurs(0);
/* 211 */     elemField.setNillable(false);
/* 212 */     typeDesc.addFieldDesc(elemField);
/* 213 */     elemField = new ElementDesc();
/* 214 */     elemField.setFieldName("credResp");
/* 215 */     elemField.setXmlName(new QName("", "credResp"));
/* 216 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectResult"));
/* 217 */     elemField.setMinOccurs(0);
/* 218 */     elemField.setNillable(false);
/* 219 */     typeDesc.addFieldDesc(elemField);
/* 220 */     elemField = new ElementDesc();
/* 221 */     elemField.setFieldName("cancResp");
/* 222 */     elemField.setXmlName(new QName("", "cancResp"));
/* 223 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CancelResult"));
/* 224 */     elemField.setMinOccurs(0);
/* 225 */     elemField.setNillable(false);
/* 226 */     typeDesc.addFieldDesc(elemField);
/* 227 */     elemField = new ElementDesc();
/* 228 */     elemField.setFieldName("partcancResp");
/* 229 */     elemField.setXmlName(new QName("", "partcancResp"));
/* 230 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CancelResult"));
/* 231 */     elemField.setMinOccurs(0);
/* 232 */     elemField.setNillable(false);
/* 233 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentResponse
 * JD-Core Version:    0.6.0
 */