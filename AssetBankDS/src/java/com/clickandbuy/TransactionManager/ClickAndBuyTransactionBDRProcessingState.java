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
/*     */ public class ClickAndBuyTransactionBDRProcessingState
/*     */   implements Serializable
/*     */ {
/*     */   private boolean isCommitted;
/*     */   private long BDRID;
/*  65 */   private Object __equalsCalc = null;
/*     */ 
/*  83 */   private boolean __hashCodeCalc = false;
/*     */ 
/*  97 */   private static TypeDesc typeDesc = new TypeDesc(ClickAndBuyTransactionBDRProcessingState.class, true);
/*     */ 
/*     */   public ClickAndBuyTransactionBDRProcessingState()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ClickAndBuyTransactionBDRProcessingState(boolean isCommitted, long BDRID)
/*     */   {
/*  21 */     this.isCommitted = isCommitted;
/*  22 */     this.BDRID = BDRID;
/*     */   }
/*     */ 
/*     */   public boolean isIsCommitted()
/*     */   {
/*  32 */     return this.isCommitted;
/*     */   }
/*     */ 
/*     */   public void setIsCommitted(boolean isCommitted)
/*     */   {
/*  42 */     this.isCommitted = isCommitted;
/*     */   }
/*     */ 
/*     */   public long getBDRID()
/*     */   {
/*  52 */     return this.BDRID;
/*     */   }
/*     */ 
/*     */   public void setBDRID(long BDRID)
/*     */   {
/*  62 */     this.BDRID = BDRID;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/*  67 */     if (!(obj instanceof ClickAndBuyTransactionBDRProcessingState)) return false;
/*  68 */     ClickAndBuyTransactionBDRProcessingState other = (ClickAndBuyTransactionBDRProcessingState)obj;
/*  69 */     if (obj == null) return false;
/*  70 */     if (this == obj) return true;
/*  71 */     if (this.__equalsCalc != null) {
/*  72 */       return this.__equalsCalc == obj;
/*     */     }
/*  74 */     this.__equalsCalc = obj;
/*     */ 
/*  76 */     boolean _equals = (this.isCommitted == other.isIsCommitted()) && (this.BDRID == other.getBDRID());
/*     */ 
/*  79 */     this.__equalsCalc = null;
/*  80 */     return _equals;
/*     */   }
/*     */ 
/*     */   public synchronized int hashCode()
/*     */   {
/*  85 */     if (this.__hashCodeCalc) {
/*  86 */       return 0;
/*     */     }
/*  88 */     this.__hashCodeCalc = true;
/*  89 */     int _hashCode = 1;
/*  90 */     _hashCode += (isIsCommitted() ? Boolean.TRUE : Boolean.FALSE).hashCode();
/*  91 */     _hashCode += new Long(getBDRID()).hashCode();
/*  92 */     this.__hashCodeCalc = false;
/*  93 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 120 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 130 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 142 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 101 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.BDRProcessingState"));
/* 102 */     ElementDesc elemField = new ElementDesc();
/* 103 */     elemField.setFieldName("isCommitted");
/* 104 */     elemField.setXmlName(new QName("", "isCommitted"));
/* 105 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 106 */     elemField.setNillable(false);
/* 107 */     typeDesc.addFieldDesc(elemField);
/* 108 */     elemField = new ElementDesc();
/* 109 */     elemField.setFieldName("BDRID");
/* 110 */     elemField.setXmlName(new QName("", "BDRID"));
/* 111 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 112 */     elemField.setNillable(false);
/* 113 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.ClickAndBuyTransactionBDRProcessingState
 * JD-Core Version:    0.6.0
 */