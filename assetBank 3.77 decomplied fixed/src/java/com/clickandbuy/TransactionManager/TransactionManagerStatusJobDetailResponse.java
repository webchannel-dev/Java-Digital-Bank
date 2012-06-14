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
/*     */ public class TransactionManagerStatusJobDetailResponse
/*     */   implements Serializable
/*     */ {
/*     */   private TransactionManagerStatusItemStatus status;
/*     */   private int itemsAll;
/*     */   private int itemsProcessed;
/*     */   private String lastChange;
/*     */   private TransactionManagerStatusTransStatusResponse[] transactionsList;
/* 137 */   private Object __equalsCalc = null;
/*     */ 
/* 164 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 195 */   private static TypeDesc typeDesc = new TypeDesc(TransactionManagerStatusJobDetailResponse.class, true);
/*     */ 
/*     */   public TransactionManagerStatusJobDetailResponse()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TransactionManagerStatusJobDetailResponse(TransactionManagerStatusItemStatus status, int itemsAll, int itemsProcessed, String lastChange, TransactionManagerStatusTransStatusResponse[] transactionsList)
/*     */   {
/*  30 */     this.status = status;
/*  31 */     this.itemsAll = itemsAll;
/*  32 */     this.itemsProcessed = itemsProcessed;
/*  33 */     this.lastChange = lastChange;
/*  34 */     this.transactionsList = transactionsList;
/*     */   }
/*     */ 
/*     */   public TransactionManagerStatusItemStatus getStatus()
/*     */   {
/*  44 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(TransactionManagerStatusItemStatus status)
/*     */   {
/*  54 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public int getItemsAll()
/*     */   {
/*  64 */     return this.itemsAll;
/*     */   }
/*     */ 
/*     */   public void setItemsAll(int itemsAll)
/*     */   {
/*  74 */     this.itemsAll = itemsAll;
/*     */   }
/*     */ 
/*     */   public int getItemsProcessed()
/*     */   {
/*  84 */     return this.itemsProcessed;
/*     */   }
/*     */ 
/*     */   public void setItemsProcessed(int itemsProcessed)
/*     */   {
/*  94 */     this.itemsProcessed = itemsProcessed;
/*     */   }
/*     */ 
/*     */   public String getLastChange()
/*     */   {
/* 104 */     return this.lastChange;
/*     */   }
/*     */ 
/*     */   public void setLastChange(String lastChange)
/*     */   {
/* 114 */     this.lastChange = lastChange;
/*     */   }
/*     */ 
/*     */   public TransactionManagerStatusTransStatusResponse[] getTransactionsList()
/*     */   {
/* 124 */     return this.transactionsList;
/*     */   }
/*     */ 
/*     */   public void setTransactionsList(TransactionManagerStatusTransStatusResponse[] transactionsList)
/*     */   {
/* 134 */     this.transactionsList = transactionsList;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/* 139 */     if (!(obj instanceof TransactionManagerStatusJobDetailResponse)) return false;
/* 140 */     TransactionManagerStatusJobDetailResponse other = (TransactionManagerStatusJobDetailResponse)obj;
/* 141 */     if (obj == null) return false;
/* 142 */     if (this == obj) return true;
/* 143 */     if (this.__equalsCalc != null) {
/* 144 */       return this.__equalsCalc == obj;
/*     */     }
/* 146 */     this.__equalsCalc = obj;
/*     */ 
/* 148 */     boolean _equals = ((this.status == null) && (other.getStatus() == null)) || ((this.status != null) && (this.status.equals(other.getStatus())) && (this.itemsAll == other.getItemsAll()) && (this.itemsProcessed == other.getItemsProcessed()) && (((this.lastChange == null) && (other.getLastChange() == null)) || ((this.lastChange != null) && (this.lastChange.equals(other.getLastChange())) && (((this.transactionsList == null) && (other.getTransactionsList() == null)) || ((this.transactionsList != null) && (Arrays.equals(this.transactionsList, other.getTransactionsList())))))));
/*     */ 
/* 160 */     this.__equalsCalc = null;
/* 161 */     return _equals;
/*     */   }
/*     */ 
/*     */   public synchronized int hashCode()
/*     */   {
/* 166 */     if (this.__hashCodeCalc) {
/* 167 */       return 0;
/*     */     }
/* 169 */     this.__hashCodeCalc = true;
/* 170 */     int _hashCode = 1;
/* 171 */     if (getStatus() != null) {
/* 172 */       _hashCode += getStatus().hashCode();
/*     */     }
/* 174 */     _hashCode += getItemsAll();
/* 175 */     _hashCode += getItemsProcessed();
/* 176 */     if (getLastChange() != null) {
/* 177 */       _hashCode += getLastChange().hashCode();
/*     */     }
/* 179 */     if (getTransactionsList() != null) {
/* 180 */       int i = 0;
/* 181 */       while (i < Array.getLength(getTransactionsList()))
/*     */       {
/* 183 */         Object obj = Array.get(getTransactionsList(), i);
/* 184 */         if ((obj != null) && (!obj.getClass().isArray()))
/*     */         {
/* 186 */           _hashCode += obj.hashCode();
/*     */         }
/* 182 */         i++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 190 */     this.__hashCodeCalc = false;
/* 191 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 237 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 247 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 259 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 199 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.JobDetailResponse"));
/* 200 */     ElementDesc elemField = new ElementDesc();
/* 201 */     elemField.setFieldName("status");
/* 202 */     elemField.setXmlName(new QName("", "status"));
/* 203 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.ItemStatus"));
/* 204 */     elemField.setNillable(false);
/* 205 */     typeDesc.addFieldDesc(elemField);
/* 206 */     elemField = new ElementDesc();
/* 207 */     elemField.setFieldName("itemsAll");
/* 208 */     elemField.setXmlName(new QName("", "itemsAll"));
/* 209 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 210 */     elemField.setNillable(false);
/* 211 */     typeDesc.addFieldDesc(elemField);
/* 212 */     elemField = new ElementDesc();
/* 213 */     elemField.setFieldName("itemsProcessed");
/* 214 */     elemField.setXmlName(new QName("", "itemsProcessed"));
/* 215 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 216 */     elemField.setNillable(false);
/* 217 */     typeDesc.addFieldDesc(elemField);
/* 218 */     elemField = new ElementDesc();
/* 219 */     elemField.setFieldName("lastChange");
/* 220 */     elemField.setXmlName(new QName("", "lastChange"));
/* 221 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 222 */     elemField.setNillable(false);
/* 223 */     typeDesc.addFieldDesc(elemField);
/* 224 */     elemField = new ElementDesc();
/* 225 */     elemField.setFieldName("transactionsList");
/* 226 */     elemField.setXmlName(new QName("", "transactionsList"));
/* 227 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.TransStatusResponse"));
/* 228 */     elemField.setNillable(false);
/* 229 */     elemField.setItemQName(new QName("", "item"));
/* 230 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerStatusJobDetailResponse
 * JD-Core Version:    0.6.0
 */