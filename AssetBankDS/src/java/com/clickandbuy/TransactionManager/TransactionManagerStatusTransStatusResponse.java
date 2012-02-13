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
/*     */ public class TransactionManagerStatusTransStatusResponse
/*     */   implements Serializable
/*     */ {
/*     */   private TransactionManagerPaymentPaymentResponse transResponse;
/*     */   private long jobID;
/*     */   private long actionID;
/*     */   private TransactionManagerStatusItemStatus status;
/*     */   private String lastChange;
/*     */   private String error;
/*     */   private long slaveMerchantID;
/* 185 */   private Object __equalsCalc = null;
/*     */ 
/* 216 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 243 */   private static TypeDesc typeDesc = new TypeDesc(TransactionManagerStatusTransStatusResponse.class, true);
/*     */ 
/*     */   public TransactionManagerStatusTransStatusResponse()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TransactionManagerStatusTransStatusResponse(TransactionManagerPaymentPaymentResponse transResponse, long jobID, long actionID, TransactionManagerStatusItemStatus status, String lastChange, String error, long slaveMerchantID)
/*     */   {
/*  36 */     this.transResponse = transResponse;
/*  37 */     this.jobID = jobID;
/*  38 */     this.actionID = actionID;
/*  39 */     this.status = status;
/*  40 */     this.lastChange = lastChange;
/*  41 */     this.error = error;
/*  42 */     this.slaveMerchantID = slaveMerchantID;
/*     */   }
/*     */ 
/*     */   public TransactionManagerPaymentPaymentResponse getTransResponse()
/*     */   {
/*  52 */     return this.transResponse;
/*     */   }
/*     */ 
/*     */   public void setTransResponse(TransactionManagerPaymentPaymentResponse transResponse)
/*     */   {
/*  62 */     this.transResponse = transResponse;
/*     */   }
/*     */ 
/*     */   public long getJobID()
/*     */   {
/*  72 */     return this.jobID;
/*     */   }
/*     */ 
/*     */   public void setJobID(long jobID)
/*     */   {
/*  82 */     this.jobID = jobID;
/*     */   }
/*     */ 
/*     */   public long getActionID()
/*     */   {
/*  92 */     return this.actionID;
/*     */   }
/*     */ 
/*     */   public void setActionID(long actionID)
/*     */   {
/* 102 */     this.actionID = actionID;
/*     */   }
/*     */ 
/*     */   public TransactionManagerStatusItemStatus getStatus()
/*     */   {
/* 112 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(TransactionManagerStatusItemStatus status)
/*     */   {
/* 122 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getLastChange()
/*     */   {
/* 132 */     return this.lastChange;
/*     */   }
/*     */ 
/*     */   public void setLastChange(String lastChange)
/*     */   {
/* 142 */     this.lastChange = lastChange;
/*     */   }
/*     */ 
/*     */   public String getError()
/*     */   {
/* 152 */     return this.error;
/*     */   }
/*     */ 
/*     */   public void setError(String error)
/*     */   {
/* 162 */     this.error = error;
/*     */   }
/*     */ 
/*     */   public long getSlaveMerchantID()
/*     */   {
/* 172 */     return this.slaveMerchantID;
/*     */   }
/*     */ 
/*     */   public void setSlaveMerchantID(long slaveMerchantID)
/*     */   {
/* 182 */     this.slaveMerchantID = slaveMerchantID;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/* 187 */     if (!(obj instanceof TransactionManagerStatusTransStatusResponse)) return false;
/* 188 */     TransactionManagerStatusTransStatusResponse other = (TransactionManagerStatusTransStatusResponse)obj;
/* 189 */     if (obj == null) return false;
/* 190 */     if (this == obj) return true;
/* 191 */     if (this.__equalsCalc != null) {
/* 192 */       return this.__equalsCalc == obj;
/*     */     }
/* 194 */     this.__equalsCalc = obj;
/*     */ 
/* 196 */     boolean _equals = ((this.transResponse == null) && (other.getTransResponse() == null)) || ((this.transResponse != null) && (this.transResponse.equals(other.getTransResponse())) && (this.jobID == other.getJobID()) && (this.actionID == other.getActionID()) && (((this.status == null) && (other.getStatus() == null)) || ((this.status != null) && (this.status.equals(other.getStatus())) && (((this.lastChange == null) && (other.getLastChange() == null)) || ((this.lastChange != null) && (this.lastChange.equals(other.getLastChange())) && (((this.error == null) && (other.getError() == null)) || ((this.error != null) && (this.error.equals(other.getError())) && (this.slaveMerchantID == other.getSlaveMerchantID()))))))));
/*     */ 
/* 212 */     this.__equalsCalc = null;
/* 213 */     return _equals;
/*     */   }
/*     */ 
/*     */   public synchronized int hashCode()
/*     */   {
/* 218 */     if (this.__hashCodeCalc) {
/* 219 */       return 0;
/*     */     }
/* 221 */     this.__hashCodeCalc = true;
/* 222 */     int _hashCode = 1;
/* 223 */     if (getTransResponse() != null) {
/* 224 */       _hashCode += getTransResponse().hashCode();
/*     */     }
/* 226 */     _hashCode += new Long(getJobID()).hashCode();
/* 227 */     _hashCode += new Long(getActionID()).hashCode();
/* 228 */     if (getStatus() != null) {
/* 229 */       _hashCode += getStatus().hashCode();
/*     */     }
/* 231 */     if (getLastChange() != null) {
/* 232 */       _hashCode += getLastChange().hashCode();
/*     */     }
/* 234 */     if (getError() != null) {
/* 235 */       _hashCode += getError().hashCode();
/*     */     }
/* 237 */     _hashCode += new Long(getSlaveMerchantID()).hashCode();
/* 238 */     this.__hashCodeCalc = false;
/* 239 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 296 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 306 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 318 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 247 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.TransStatusResponse"));
/* 248 */     ElementDesc elemField = new ElementDesc();
/* 249 */     elemField.setFieldName("transResponse");
/* 250 */     elemField.setXmlName(new QName("", "transResponse"));
/* 251 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentResponse"));
/* 252 */     elemField.setNillable(false);
/* 253 */     typeDesc.addFieldDesc(elemField);
/* 254 */     elemField = new ElementDesc();
/* 255 */     elemField.setFieldName("jobID");
/* 256 */     elemField.setXmlName(new QName("", "jobID"));
/* 257 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 258 */     elemField.setNillable(false);
/* 259 */     typeDesc.addFieldDesc(elemField);
/* 260 */     elemField = new ElementDesc();
/* 261 */     elemField.setFieldName("actionID");
/* 262 */     elemField.setXmlName(new QName("", "actionID"));
/* 263 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 264 */     elemField.setNillable(false);
/* 265 */     typeDesc.addFieldDesc(elemField);
/* 266 */     elemField = new ElementDesc();
/* 267 */     elemField.setFieldName("status");
/* 268 */     elemField.setXmlName(new QName("", "status"));
/* 269 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.ItemStatus"));
/* 270 */     elemField.setNillable(false);
/* 271 */     typeDesc.addFieldDesc(elemField);
/* 272 */     elemField = new ElementDesc();
/* 273 */     elemField.setFieldName("lastChange");
/* 274 */     elemField.setXmlName(new QName("", "lastChange"));
/* 275 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 276 */     elemField.setNillable(false);
/* 277 */     typeDesc.addFieldDesc(elemField);
/* 278 */     elemField = new ElementDesc();
/* 279 */     elemField.setFieldName("error");
/* 280 */     elemField.setXmlName(new QName("", "error"));
/* 281 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 282 */     elemField.setNillable(false);
/* 283 */     typeDesc.addFieldDesc(elemField);
/* 284 */     elemField = new ElementDesc();
/* 285 */     elemField.setFieldName("slaveMerchantID");
/* 286 */     elemField.setXmlName(new QName("", "slaveMerchantID"));
/* 287 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 288 */     elemField.setNillable(false);
/* 289 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerStatusTransStatusResponse
 * JD-Core Version:    0.6.0
 */