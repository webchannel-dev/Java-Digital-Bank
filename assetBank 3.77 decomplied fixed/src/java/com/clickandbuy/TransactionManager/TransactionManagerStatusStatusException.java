/*     */ package com.clickandbuy.TransactionManager;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.apache.axis.AxisFault;
/*     */ import org.apache.axis.description.ElementDesc;
/*     */ import org.apache.axis.description.TypeDesc;
/*     */ import org.apache.axis.encoding.Deserializer;
/*     */ import org.apache.axis.encoding.SerializationContext;
/*     */ import org.apache.axis.encoding.Serializer;
/*     */ import org.apache.axis.encoding.ser.BeanDeserializer;
/*     */ import org.apache.axis.encoding.ser.BeanSerializer;
/*     */ 
/*     */ public class TransactionManagerStatusStatusException extends AxisFault
/*     */   implements Serializable
/*     */ {
/*     */   private short id;
/*     */   private String message1;
/*  65 */   private Object __equalsCalc = null;
/*     */ 
/*  85 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 101 */   private static TypeDesc typeDesc = new TypeDesc(TransactionManagerStatusStatusException.class, true);
/*     */ 
/*     */   public TransactionManagerStatusStatusException()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TransactionManagerStatusStatusException(short id, String message1)
/*     */   {
/*  21 */     this.id = id;
/*  22 */     this.message1 = message1;
/*     */   }
/*     */ 
/*     */   public short getId()
/*     */   {
/*  32 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(short id)
/*     */   {
/*  42 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getMessage1()
/*     */   {
/*  52 */     return this.message1;
/*     */   }
/*     */ 
/*     */   public void setMessage1(String message1)
/*     */   {
/*  62 */     this.message1 = message1;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/*  67 */     if (!(obj instanceof TransactionManagerStatusStatusException)) return false;
/*  68 */     TransactionManagerStatusStatusException other = (TransactionManagerStatusStatusException)obj;
/*  69 */     if (obj == null) return false;
/*  70 */     if (this == obj) return true;
/*  71 */     if (this.__equalsCalc != null) {
/*  72 */       return this.__equalsCalc == obj;
/*     */     }
/*  74 */     this.__equalsCalc = obj;
/*     */ 
/*  76 */     boolean _equals = (this.id == other.getId()) && (((this.message1 == null) && (other.getMessage1() == null)) || ((this.message1 != null) && (this.message1.equals(other.getMessage1()))));
/*     */ 
/*  81 */     this.__equalsCalc = null;
/*  82 */     return _equals;
/*     */   }
/*     */ 
/*     */   public synchronized int hashCode()
/*     */   {
/*  87 */     if (this.__hashCodeCalc) {
/*  88 */       return 0;
/*     */     }
/*  90 */     this.__hashCodeCalc = true;
/*  91 */     int _hashCode = 1;
/*  92 */     _hashCode += getId();
/*  93 */     if (getMessage1() != null) {
/*  94 */       _hashCode += getMessage1().hashCode();
/*     */     }
/*  96 */     this.__hashCodeCalc = false;
/*  97 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 124 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 134 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 146 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public void writeDetails(QName qname, SerializationContext context)
/*     */     throws IOException
/*     */   {
/* 156 */     context.serialize(qname, null, this);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 105 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.StatusException"));
/* 106 */     ElementDesc elemField = new ElementDesc();
/* 107 */     elemField.setFieldName("id");
/* 108 */     elemField.setXmlName(new QName("", "id"));
/* 109 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "short"));
/* 110 */     elemField.setNillable(false);
/* 111 */     typeDesc.addFieldDesc(elemField);
/* 112 */     elemField = new ElementDesc();
/* 113 */     elemField.setFieldName("message1");
/* 114 */     elemField.setXmlName(new QName("", "message"));
/* 115 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 116 */     elemField.setNillable(false);
/* 117 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerStatusStatusException
 * JD-Core Version:    0.6.0
 */