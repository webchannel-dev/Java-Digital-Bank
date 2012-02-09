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
/*     */ public class ClickAndBuyTransactionCustomerStatus
/*     */   implements Serializable
/*     */ {
/*     */   private long crn;
/*     */   private String nationCode;
/*     */   private String languageCode;
/*     */   private ClickAndBuyTransactionCustomerStatusType status;
/*     */   private boolean isPrepaid;
/*     */   private boolean isCreditExhausted;
/*     */   private boolean isEasyCollectGranted;
/* 185 */   private Object __equalsCalc = null;
/*     */ 
/* 214 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 239 */   private static TypeDesc typeDesc = new TypeDesc(ClickAndBuyTransactionCustomerStatus.class, true);
/*     */ 
/*     */   public ClickAndBuyTransactionCustomerStatus()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ClickAndBuyTransactionCustomerStatus(long crn, String nationCode, String languageCode, ClickAndBuyTransactionCustomerStatusType status, boolean isPrepaid, boolean isCreditExhausted, boolean isEasyCollectGranted)
/*     */   {
/*  36 */     this.crn = crn;
/*  37 */     this.nationCode = nationCode;
/*  38 */     this.languageCode = languageCode;
/*  39 */     this.status = status;
/*  40 */     this.isPrepaid = isPrepaid;
/*  41 */     this.isCreditExhausted = isCreditExhausted;
/*  42 */     this.isEasyCollectGranted = isEasyCollectGranted;
/*     */   }
/*     */ 
/*     */   public long getCrn()
/*     */   {
/*  52 */     return this.crn;
/*     */   }
/*     */ 
/*     */   public void setCrn(long crn)
/*     */   {
/*  62 */     this.crn = crn;
/*     */   }
/*     */ 
/*     */   public String getNationCode()
/*     */   {
/*  72 */     return this.nationCode;
/*     */   }
/*     */ 
/*     */   public void setNationCode(String nationCode)
/*     */   {
/*  82 */     this.nationCode = nationCode;
/*     */   }
/*     */ 
/*     */   public String getLanguageCode()
/*     */   {
/*  92 */     return this.languageCode;
/*     */   }
/*     */ 
/*     */   public void setLanguageCode(String languageCode)
/*     */   {
/* 102 */     this.languageCode = languageCode;
/*     */   }
/*     */ 
/*     */   public ClickAndBuyTransactionCustomerStatusType getStatus()
/*     */   {
/* 112 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(ClickAndBuyTransactionCustomerStatusType status)
/*     */   {
/* 122 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public boolean isIsPrepaid()
/*     */   {
/* 132 */     return this.isPrepaid;
/*     */   }
/*     */ 
/*     */   public void setIsPrepaid(boolean isPrepaid)
/*     */   {
/* 142 */     this.isPrepaid = isPrepaid;
/*     */   }
/*     */ 
/*     */   public boolean isIsCreditExhausted()
/*     */   {
/* 152 */     return this.isCreditExhausted;
/*     */   }
/*     */ 
/*     */   public void setIsCreditExhausted(boolean isCreditExhausted)
/*     */   {
/* 162 */     this.isCreditExhausted = isCreditExhausted;
/*     */   }
/*     */ 
/*     */   public boolean isIsEasyCollectGranted()
/*     */   {
/* 172 */     return this.isEasyCollectGranted;
/*     */   }
/*     */ 
/*     */   public void setIsEasyCollectGranted(boolean isEasyCollectGranted)
/*     */   {
/* 182 */     this.isEasyCollectGranted = isEasyCollectGranted;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/* 187 */     if (!(obj instanceof ClickAndBuyTransactionCustomerStatus)) return false;
/* 188 */     ClickAndBuyTransactionCustomerStatus other = (ClickAndBuyTransactionCustomerStatus)obj;
/* 189 */     if (obj == null) return false;
/* 190 */     if (this == obj) return true;
/* 191 */     if (this.__equalsCalc != null) {
/* 192 */       return this.__equalsCalc == obj;
/*     */     }
/* 194 */     this.__equalsCalc = obj;
/*     */ 
/* 196 */     boolean _equals = (this.crn == other.getCrn()) && (((this.nationCode == null) && (other.getNationCode() == null)) || ((this.nationCode != null) && (this.nationCode.equals(other.getNationCode())) && (((this.languageCode == null) && (other.getLanguageCode() == null)) || ((this.languageCode != null) && (this.languageCode.equals(other.getLanguageCode())) && (((this.status == null) && (other.getStatus() == null)) || ((this.status != null) && (this.status.equals(other.getStatus())) && (this.isPrepaid == other.isIsPrepaid()) && (this.isCreditExhausted == other.isIsCreditExhausted()) && (this.isEasyCollectGranted == other.isIsEasyCollectGranted())))))));
/*     */ 
/* 210 */     this.__equalsCalc = null;
/* 211 */     return _equals;
/*     */   }
/*     */ 
/*     */   public synchronized int hashCode()
/*     */   {
/* 216 */     if (this.__hashCodeCalc) {
/* 217 */       return 0;
/*     */     }
/* 219 */     this.__hashCodeCalc = true;
/* 220 */     int _hashCode = 1;
/* 221 */     _hashCode += new Long(getCrn()).hashCode();
/* 222 */     if (getNationCode() != null) {
/* 223 */       _hashCode += getNationCode().hashCode();
/*     */     }
/* 225 */     if (getLanguageCode() != null) {
/* 226 */       _hashCode += getLanguageCode().hashCode();
/*     */     }
/* 228 */     if (getStatus() != null) {
/* 229 */       _hashCode += getStatus().hashCode();
/*     */     }
/* 231 */     _hashCode += (isIsPrepaid() ? Boolean.TRUE : Boolean.FALSE).hashCode();
/* 232 */     _hashCode += (isIsCreditExhausted() ? Boolean.TRUE : Boolean.FALSE).hashCode();
/* 233 */     _hashCode += (isIsEasyCollectGranted() ? Boolean.TRUE : Boolean.FALSE).hashCode();
/* 234 */     this.__hashCodeCalc = false;
/* 235 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 292 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 302 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 314 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 243 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CustomerStatus"));
/* 244 */     ElementDesc elemField = new ElementDesc();
/* 245 */     elemField.setFieldName("crn");
/* 246 */     elemField.setXmlName(new QName("", "crn"));
/* 247 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 248 */     elemField.setNillable(false);
/* 249 */     typeDesc.addFieldDesc(elemField);
/* 250 */     elemField = new ElementDesc();
/* 251 */     elemField.setFieldName("nationCode");
/* 252 */     elemField.setXmlName(new QName("", "nationCode"));
/* 253 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 254 */     elemField.setNillable(false);
/* 255 */     typeDesc.addFieldDesc(elemField);
/* 256 */     elemField = new ElementDesc();
/* 257 */     elemField.setFieldName("languageCode");
/* 258 */     elemField.setXmlName(new QName("", "languageCode"));
/* 259 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 260 */     elemField.setNillable(false);
/* 261 */     typeDesc.addFieldDesc(elemField);
/* 262 */     elemField = new ElementDesc();
/* 263 */     elemField.setFieldName("status");
/* 264 */     elemField.setXmlName(new QName("", "status"));
/* 265 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CustomerStatusType"));
/* 266 */     elemField.setNillable(false);
/* 267 */     typeDesc.addFieldDesc(elemField);
/* 268 */     elemField = new ElementDesc();
/* 269 */     elemField.setFieldName("isPrepaid");
/* 270 */     elemField.setXmlName(new QName("", "isPrepaid"));
/* 271 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 272 */     elemField.setNillable(false);
/* 273 */     typeDesc.addFieldDesc(elemField);
/* 274 */     elemField = new ElementDesc();
/* 275 */     elemField.setFieldName("isCreditExhausted");
/* 276 */     elemField.setXmlName(new QName("", "isCreditExhausted"));
/* 277 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 278 */     elemField.setNillable(false);
/* 279 */     typeDesc.addFieldDesc(elemField);
/* 280 */     elemField = new ElementDesc();
/* 281 */     elemField.setFieldName("isEasyCollectGranted");
/* 282 */     elemField.setXmlName(new QName("", "isEasyCollectGranted"));
/* 283 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 284 */     elemField.setNillable(false);
/* 285 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.ClickAndBuyTransactionCustomerStatus
 * JD-Core Version:    0.6.0
 */