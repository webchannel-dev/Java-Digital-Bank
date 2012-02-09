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
/*     */ public class ClickAndBuyEasyCollectEasyCollectQuery
/*     */   implements Serializable
/*     */ {
/*     */   private Long slaveMerchantID;
/*     */   private long crn;
/*     */   private long easyCollectID;
/*     */   private String externalBDRID;
/*     */   private UnsignedInt amount;
/*     */   private String currency;
/*     */   private String urlInfo;
/*     */   private String internalContentDescription;
/* 209 */   private Object __equalsCalc = null;
/*     */ 
/* 245 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 277 */   private static TypeDesc typeDesc = new TypeDesc(ClickAndBuyEasyCollectEasyCollectQuery.class, true);
/*     */ 
/*     */   public ClickAndBuyEasyCollectEasyCollectQuery()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ClickAndBuyEasyCollectEasyCollectQuery(Long slaveMerchantID, long crn, long easyCollectID, String externalBDRID, UnsignedInt amount, String currency, String urlInfo, String internalContentDescription)
/*     */   {
/*  39 */     this.slaveMerchantID = slaveMerchantID;
/*  40 */     this.crn = crn;
/*  41 */     this.easyCollectID = easyCollectID;
/*  42 */     this.externalBDRID = externalBDRID;
/*  43 */     this.amount = amount;
/*  44 */     this.currency = currency;
/*  45 */     this.urlInfo = urlInfo;
/*  46 */     this.internalContentDescription = internalContentDescription;
/*     */   }
/*     */ 
/*     */   public Long getSlaveMerchantID()
/*     */   {
/*  56 */     return this.slaveMerchantID;
/*     */   }
/*     */ 
/*     */   public void setSlaveMerchantID(Long slaveMerchantID)
/*     */   {
/*  66 */     this.slaveMerchantID = slaveMerchantID;
/*     */   }
/*     */ 
/*     */   public long getCrn()
/*     */   {
/*  76 */     return this.crn;
/*     */   }
/*     */ 
/*     */   public void setCrn(long crn)
/*     */   {
/*  86 */     this.crn = crn;
/*     */   }
/*     */ 
/*     */   public long getEasyCollectID()
/*     */   {
/*  96 */     return this.easyCollectID;
/*     */   }
/*     */ 
/*     */   public void setEasyCollectID(long easyCollectID)
/*     */   {
/* 106 */     this.easyCollectID = easyCollectID;
/*     */   }
/*     */ 
/*     */   public String getExternalBDRID()
/*     */   {
/* 116 */     return this.externalBDRID;
/*     */   }
/*     */ 
/*     */   public void setExternalBDRID(String externalBDRID)
/*     */   {
/* 126 */     this.externalBDRID = externalBDRID;
/*     */   }
/*     */ 
/*     */   public UnsignedInt getAmount()
/*     */   {
/* 136 */     return this.amount;
/*     */   }
/*     */ 
/*     */   public void setAmount(UnsignedInt amount)
/*     */   {
/* 146 */     this.amount = amount;
/*     */   }
/*     */ 
/*     */   public String getCurrency()
/*     */   {
/* 156 */     return this.currency;
/*     */   }
/*     */ 
/*     */   public void setCurrency(String currency)
/*     */   {
/* 166 */     this.currency = currency;
/*     */   }
/*     */ 
/*     */   public String getUrlInfo()
/*     */   {
/* 176 */     return this.urlInfo;
/*     */   }
/*     */ 
/*     */   public void setUrlInfo(String urlInfo)
/*     */   {
/* 186 */     this.urlInfo = urlInfo;
/*     */   }
/*     */ 
/*     */   public String getInternalContentDescription()
/*     */   {
/* 196 */     return this.internalContentDescription;
/*     */   }
/*     */ 
/*     */   public void setInternalContentDescription(String internalContentDescription)
/*     */   {
/* 206 */     this.internalContentDescription = internalContentDescription;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/* 211 */     if (!(obj instanceof ClickAndBuyEasyCollectEasyCollectQuery)) return false;
/* 212 */     ClickAndBuyEasyCollectEasyCollectQuery other = (ClickAndBuyEasyCollectEasyCollectQuery)obj;
/* 213 */     if (obj == null) return false;
/* 214 */     if (this == obj) return true;
/* 215 */     if (this.__equalsCalc != null) {
/* 216 */       return this.__equalsCalc == obj;
/*     */     }
/* 218 */     this.__equalsCalc = obj;
/*     */ 
/* 220 */     boolean _equals = ((this.slaveMerchantID == null) && (other.getSlaveMerchantID() == null)) || ((this.slaveMerchantID != null) && (this.slaveMerchantID.equals(other.getSlaveMerchantID())) && (this.crn == other.getCrn()) && (this.easyCollectID == other.getEasyCollectID()) && (((this.externalBDRID == null) && (other.getExternalBDRID() == null)) || ((this.externalBDRID != null) && (this.externalBDRID.equals(other.getExternalBDRID())) && (((this.amount == null) && (other.getAmount() == null)) || ((this.amount != null) && (this.amount.equals(other.getAmount())) && (((this.currency == null) && (other.getCurrency() == null)) || ((this.currency != null) && (this.currency.equals(other.getCurrency())) && (((this.urlInfo == null) && (other.getUrlInfo() == null)) || ((this.urlInfo != null) && (this.urlInfo.equals(other.getUrlInfo())) && (((this.internalContentDescription == null) && (other.getInternalContentDescription() == null)) || ((this.internalContentDescription != null) && (this.internalContentDescription.equals(other.getInternalContentDescription())))))))))))));
/*     */ 
/* 241 */     this.__equalsCalc = null;
/* 242 */     return _equals;
/*     */   }
/*     */ 
/*     */   public synchronized int hashCode()
/*     */   {
/* 247 */     if (this.__hashCodeCalc) {
/* 248 */       return 0;
/*     */     }
/* 250 */     this.__hashCodeCalc = true;
/* 251 */     int _hashCode = 1;
/* 252 */     if (getSlaveMerchantID() != null) {
/* 253 */       _hashCode += getSlaveMerchantID().hashCode();
/*     */     }
/* 255 */     _hashCode += new Long(getCrn()).hashCode();
/* 256 */     _hashCode += new Long(getEasyCollectID()).hashCode();
/* 257 */     if (getExternalBDRID() != null) {
/* 258 */       _hashCode += getExternalBDRID().hashCode();
/*     */     }
/* 260 */     if (getAmount() != null) {
/* 261 */       _hashCode += getAmount().hashCode();
/*     */     }
/* 263 */     if (getCurrency() != null) {
/* 264 */       _hashCode += getCurrency().hashCode();
/*     */     }
/* 266 */     if (getUrlInfo() != null) {
/* 267 */       _hashCode += getUrlInfo().hashCode();
/*     */     }
/* 269 */     if (getInternalContentDescription() != null) {
/* 270 */       _hashCode += getInternalContentDescription().hashCode();
/*     */     }
/* 272 */     this.__hashCodeCalc = false;
/* 273 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 337 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 347 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 359 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 281 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectQuery"));
/* 282 */     ElementDesc elemField = new ElementDesc();
/* 283 */     elemField.setFieldName("slaveMerchantID");
/* 284 */     elemField.setXmlName(new QName("", "slaveMerchantID"));
/* 285 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 286 */     elemField.setMinOccurs(0);
/* 287 */     elemField.setNillable(false);
/* 288 */     typeDesc.addFieldDesc(elemField);
/* 289 */     elemField = new ElementDesc();
/* 290 */     elemField.setFieldName("crn");
/* 291 */     elemField.setXmlName(new QName("", "crn"));
/* 292 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 293 */     elemField.setNillable(false);
/* 294 */     typeDesc.addFieldDesc(elemField);
/* 295 */     elemField = new ElementDesc();
/* 296 */     elemField.setFieldName("easyCollectID");
/* 297 */     elemField.setXmlName(new QName("", "easyCollectID"));
/* 298 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 299 */     elemField.setNillable(false);
/* 300 */     typeDesc.addFieldDesc(elemField);
/* 301 */     elemField = new ElementDesc();
/* 302 */     elemField.setFieldName("externalBDRID");
/* 303 */     elemField.setXmlName(new QName("", "externalBDRID"));
/* 304 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 305 */     elemField.setNillable(false);
/* 306 */     typeDesc.addFieldDesc(elemField);
/* 307 */     elemField = new ElementDesc();
/* 308 */     elemField.setFieldName("amount");
/* 309 */     elemField.setXmlName(new QName("", "amount"));
/* 310 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"));
/* 311 */     elemField.setNillable(false);
/* 312 */     typeDesc.addFieldDesc(elemField);
/* 313 */     elemField = new ElementDesc();
/* 314 */     elemField.setFieldName("currency");
/* 315 */     elemField.setXmlName(new QName("", "currency"));
/* 316 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 317 */     elemField.setNillable(false);
/* 318 */     typeDesc.addFieldDesc(elemField);
/* 319 */     elemField = new ElementDesc();
/* 320 */     elemField.setFieldName("urlInfo");
/* 321 */     elemField.setXmlName(new QName("", "urlInfo"));
/* 322 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 323 */     elemField.setNillable(false);
/* 324 */     typeDesc.addFieldDesc(elemField);
/* 325 */     elemField = new ElementDesc();
/* 326 */     elemField.setFieldName("internalContentDescription");
/* 327 */     elemField.setXmlName(new QName("", "internalContentDescription"));
/* 328 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 329 */     elemField.setNillable(false);
/* 330 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.ClickAndBuyEasyCollectEasyCollectQuery
 * JD-Core Version:    0.6.0
 */