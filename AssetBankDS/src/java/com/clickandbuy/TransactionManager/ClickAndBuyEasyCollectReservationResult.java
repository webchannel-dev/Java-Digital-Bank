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
/*     */ import org.apache.axis.types.UnsignedInt;
/*     */ 
/*     */ public class ClickAndBuyEasyCollectReservationResult
/*     */   implements Serializable
/*     */ {
/*     */   private long reservationID;
/*     */   private long systemID;
/*     */   private UnsignedInt amount;
/*     */   private String currency;
/* 113 */   private Object __equalsCalc = null;
/*     */ 
/* 137 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 157 */   private static TypeDesc typeDesc = new TypeDesc(ClickAndBuyEasyCollectReservationResult.class, true);
/*     */ 
/*     */   public ClickAndBuyEasyCollectReservationResult()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ClickAndBuyEasyCollectReservationResult(long reservationID, long systemID, UnsignedInt amount, String currency)
/*     */   {
/*  27 */     this.reservationID = reservationID;
/*  28 */     this.systemID = systemID;
/*  29 */     this.amount = amount;
/*  30 */     this.currency = currency;
/*     */   }
/*     */ 
/*     */   public long getReservationID()
/*     */   {
/*  40 */     return this.reservationID;
/*     */   }
/*     */ 
/*     */   public void setReservationID(long reservationID)
/*     */   {
/*  50 */     this.reservationID = reservationID;
/*     */   }
/*     */ 
/*     */   public long getSystemID()
/*     */   {
/*  60 */     return this.systemID;
/*     */   }
/*     */ 
/*     */   public void setSystemID(long systemID)
/*     */   {
/*  70 */     this.systemID = systemID;
/*     */   }
/*     */ 
/*     */   public UnsignedInt getAmount()
/*     */   {
/*  80 */     return this.amount;
/*     */   }
/*     */ 
/*     */   public void setAmount(UnsignedInt amount)
/*     */   {
/*  90 */     this.amount = amount;
/*     */   }
/*     */ 
/*     */   public String getCurrency()
/*     */   {
/* 100 */     return this.currency;
/*     */   }
/*     */ 
/*     */   public void setCurrency(String currency)
/*     */   {
/* 110 */     this.currency = currency;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/* 115 */     if (!(obj instanceof ClickAndBuyEasyCollectReservationResult)) return false;
/* 116 */     ClickAndBuyEasyCollectReservationResult other = (ClickAndBuyEasyCollectReservationResult)obj;
/* 117 */     if (obj == null) return false;
/* 118 */     if (this == obj) return true;
/* 119 */     if (this.__equalsCalc != null) {
/* 120 */       return this.__equalsCalc == obj;
/*     */     }
/* 122 */     this.__equalsCalc = obj;
/*     */ 
/* 124 */     boolean _equals = (this.reservationID == other.getReservationID()) && (this.systemID == other.getSystemID()) && (((this.amount == null) && (other.getAmount() == null)) || ((this.amount != null) && (this.amount.equals(other.getAmount())) && (((this.currency == null) && (other.getCurrency() == null)) || ((this.currency != null) && (this.currency.equals(other.getCurrency()))))));
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
/* 144 */     _hashCode += new Long(getReservationID()).hashCode();
/* 145 */     _hashCode += new Long(getSystemID()).hashCode();
/* 146 */     if (getAmount() != null) {
/* 147 */       _hashCode += getAmount().hashCode();
/*     */     }
/* 149 */     if (getCurrency() != null) {
/* 150 */       _hashCode += getCurrency().hashCode();
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
/* 161 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.ReservationResult"));
/* 162 */     ElementDesc elemField = new ElementDesc();
/* 163 */     elemField.setFieldName("reservationID");
/* 164 */     elemField.setXmlName(new QName("", "reservationID"));
/* 165 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 166 */     elemField.setNillable(false);
/* 167 */     typeDesc.addFieldDesc(elemField);
/* 168 */     elemField = new ElementDesc();
/* 169 */     elemField.setFieldName("systemID");
/* 170 */     elemField.setXmlName(new QName("", "systemID"));
/* 171 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 172 */     elemField.setNillable(false);
/* 173 */     typeDesc.addFieldDesc(elemField);
/* 174 */     elemField = new ElementDesc();
/* 175 */     elemField.setFieldName("amount");
/* 176 */     elemField.setXmlName(new QName("", "amount"));
/* 177 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"));
/* 178 */     elemField.setNillable(false);
/* 179 */     typeDesc.addFieldDesc(elemField);
/* 180 */     elemField = new ElementDesc();
/* 181 */     elemField.setFieldName("currency");
/* 182 */     elemField.setXmlName(new QName("", "currency"));
/* 183 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 184 */     elemField.setNillable(false);
/* 185 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.ClickAndBuyEasyCollectReservationResult
 * JD-Core Version:    0.6.0
 */