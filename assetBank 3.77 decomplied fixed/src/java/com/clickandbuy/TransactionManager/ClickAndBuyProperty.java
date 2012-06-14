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
/*     */ public class ClickAndBuyProperty
/*     */   implements Serializable
/*     */ {
/*     */   private String name;
/*     */   private String value;
/*  65 */   private Object __equalsCalc = null;
/*     */ 
/*  87 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 105 */   private static TypeDesc typeDesc = new TypeDesc(ClickAndBuyProperty.class, true);
/*     */ 
/*     */   public ClickAndBuyProperty()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ClickAndBuyProperty(String name, String value)
/*     */   {
/*  21 */     this.name = name;
/*  22 */     this.value = value;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  32 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  42 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getValue()
/*     */   {
/*  52 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void setValue(String value)
/*     */   {
/*  62 */     this.value = value;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/*  67 */     if (!(obj instanceof ClickAndBuyProperty)) return false;
/*  68 */     ClickAndBuyProperty other = (ClickAndBuyProperty)obj;
/*  69 */     if (obj == null) return false;
/*  70 */     if (this == obj) return true;
/*  71 */     if (this.__equalsCalc != null) {
/*  72 */       return this.__equalsCalc == obj;
/*     */     }
/*  74 */     this.__equalsCalc = obj;
/*     */ 
/*  76 */     boolean _equals = ((this.name == null) && (other.getName() == null)) || ((this.name != null) && (this.name.equals(other.getName())) && (((this.value == null) && (other.getValue() == null)) || ((this.value != null) && (this.value.equals(other.getValue())))));
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
/*  94 */     if (getName() != null) {
/*  95 */       _hashCode += getName().hashCode();
/*     */     }
/*  97 */     if (getValue() != null) {
/*  98 */       _hashCode += getValue().hashCode();
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
/* 109 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Property"));
/* 110 */     ElementDesc elemField = new ElementDesc();
/* 111 */     elemField.setFieldName("name");
/* 112 */     elemField.setXmlName(new QName("", "name"));
/* 113 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 114 */     elemField.setNillable(false);
/* 115 */     typeDesc.addFieldDesc(elemField);
/* 116 */     elemField = new ElementDesc();
/* 117 */     elemField.setFieldName("value");
/* 118 */     elemField.setXmlName(new QName("", "value"));
/* 119 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 120 */     elemField.setNillable(false);
/* 121 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.ClickAndBuyProperty
 * JD-Core Version:    0.6.0
 */