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
/*     */ public class TransactionManagerStatusJobStatusResponse
/*     */   implements Serializable
/*     */ {
/*     */   private TransactionManagerStatusItemStatus status;
/*     */   private int itemsAll;
/*     */   private int itemsProcessed;
/*     */   private String lastChange;
/* 113 */   private Object __equalsCalc = null;
/*     */ 
/* 137 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 157 */   private static TypeDesc typeDesc = new TypeDesc(TransactionManagerStatusJobStatusResponse.class, true);
/*     */ 
/*     */   public TransactionManagerStatusJobStatusResponse()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TransactionManagerStatusJobStatusResponse(TransactionManagerStatusItemStatus status, int itemsAll, int itemsProcessed, String lastChange)
/*     */   {
/*  27 */     this.status = status;
/*  28 */     this.itemsAll = itemsAll;
/*  29 */     this.itemsProcessed = itemsProcessed;
/*  30 */     this.lastChange = lastChange;
/*     */   }
/*     */ 
/*     */   public TransactionManagerStatusItemStatus getStatus()
/*     */   {
/*  40 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(TransactionManagerStatusItemStatus status)
/*     */   {
/*  50 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public int getItemsAll()
/*     */   {
/*  60 */     return this.itemsAll;
/*     */   }
/*     */ 
/*     */   public void setItemsAll(int itemsAll)
/*     */   {
/*  70 */     this.itemsAll = itemsAll;
/*     */   }
/*     */ 
/*     */   public int getItemsProcessed()
/*     */   {
/*  80 */     return this.itemsProcessed;
/*     */   }
/*     */ 
/*     */   public void setItemsProcessed(int itemsProcessed)
/*     */   {
/*  90 */     this.itemsProcessed = itemsProcessed;
/*     */   }
/*     */ 
/*     */   public String getLastChange()
/*     */   {
/* 100 */     return this.lastChange;
/*     */   }
/*     */ 
/*     */   public void setLastChange(String lastChange)
/*     */   {
/* 110 */     this.lastChange = lastChange;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/* 115 */     if (!(obj instanceof TransactionManagerStatusJobStatusResponse)) return false;
/* 116 */     TransactionManagerStatusJobStatusResponse other = (TransactionManagerStatusJobStatusResponse)obj;
/* 117 */     if (obj == null) return false;
/* 118 */     if (this == obj) return true;
/* 119 */     if (this.__equalsCalc != null) {
/* 120 */       return this.__equalsCalc == obj;
/*     */     }
/* 122 */     this.__equalsCalc = obj;
/*     */ 
/* 124 */     boolean _equals = ((this.status == null) && (other.getStatus() == null)) || ((this.status != null) && (this.status.equals(other.getStatus())) && (this.itemsAll == other.getItemsAll()) && (this.itemsProcessed == other.getItemsProcessed()) && (((this.lastChange == null) && (other.getLastChange() == null)) || ((this.lastChange != null) && (this.lastChange.equals(other.getLastChange())))));
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
/* 144 */     if (getStatus() != null) {
/* 145 */       _hashCode += getStatus().hashCode();
/*     */     }
/* 147 */     _hashCode += getItemsAll();
/* 148 */     _hashCode += getItemsProcessed();
/* 149 */     if (getLastChange() != null) {
/* 150 */       _hashCode += getLastChange().hashCode();
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
/* 161 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.JobStatusResponse"));
/* 162 */     ElementDesc elemField = new ElementDesc();
/* 163 */     elemField.setFieldName("status");
/* 164 */     elemField.setXmlName(new QName("", "status"));
/* 165 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.ItemStatus"));
/* 166 */     elemField.setNillable(false);
/* 167 */     typeDesc.addFieldDesc(elemField);
/* 168 */     elemField = new ElementDesc();
/* 169 */     elemField.setFieldName("itemsAll");
/* 170 */     elemField.setXmlName(new QName("", "itemsAll"));
/* 171 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 172 */     elemField.setNillable(false);
/* 173 */     typeDesc.addFieldDesc(elemField);
/* 174 */     elemField = new ElementDesc();
/* 175 */     elemField.setFieldName("itemsProcessed");
/* 176 */     elemField.setXmlName(new QName("", "itemsProcessed"));
/* 177 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 178 */     elemField.setNillable(false);
/* 179 */     typeDesc.addFieldDesc(elemField);
/* 180 */     elemField = new ElementDesc();
/* 181 */     elemField.setFieldName("lastChange");
/* 182 */     elemField.setXmlName(new QName("", "lastChange"));
/* 183 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 184 */     elemField.setNillable(false);
/* 185 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerStatusJobStatusResponse
 * JD-Core Version:    0.6.0
 */