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
/*     */ public class ClickAndBuyTransactionCancelQuery
/*     */   implements Serializable
/*     */ {
/*     */   private Long slaveMerchantID;
/*     */   private long BDRID;
/*     */   private String externalBDRID;
/*     */   private String externalCancelID;
/*     */   private boolean lockUser;
/*     */   private boolean cancelSubscription;
/*     */   private UnsignedInt amount;
/*     */   private String currency;
/* 209 */   private Object __equalsCalc = null;
/*     */ 
/* 243 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 273 */   private static TypeDesc typeDesc = new TypeDesc(ClickAndBuyTransactionCancelQuery.class, true);
/*     */ 
/*     */   public ClickAndBuyTransactionCancelQuery()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ClickAndBuyTransactionCancelQuery(Long slaveMerchantID, long BDRID, String externalBDRID, String externalCancelID, boolean lockUser, boolean cancelSubscription, UnsignedInt amount, String currency)
/*     */   {
/*  39 */     this.slaveMerchantID = slaveMerchantID;
/*  40 */     this.BDRID = BDRID;
/*  41 */     this.externalBDRID = externalBDRID;
/*  42 */     this.externalCancelID = externalCancelID;
/*  43 */     this.lockUser = lockUser;
/*  44 */     this.cancelSubscription = cancelSubscription;
/*  45 */     this.amount = amount;
/*  46 */     this.currency = currency;
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
/*     */   public long getBDRID()
/*     */   {
/*  76 */     return this.BDRID;
/*     */   }
/*     */ 
/*     */   public void setBDRID(long BDRID)
/*     */   {
/*  86 */     this.BDRID = BDRID;
/*     */   }
/*     */ 
/*     */   public String getExternalBDRID()
/*     */   {
/*  96 */     return this.externalBDRID;
/*     */   }
/*     */ 
/*     */   public void setExternalBDRID(String externalBDRID)
/*     */   {
/* 106 */     this.externalBDRID = externalBDRID;
/*     */   }
/*     */ 
/*     */   public String getExternalCancelID()
/*     */   {
/* 116 */     return this.externalCancelID;
/*     */   }
/*     */ 
/*     */   public void setExternalCancelID(String externalCancelID)
/*     */   {
/* 126 */     this.externalCancelID = externalCancelID;
/*     */   }
/*     */ 
/*     */   public boolean isLockUser()
/*     */   {
/* 136 */     return this.lockUser;
/*     */   }
/*     */ 
/*     */   public void setLockUser(boolean lockUser)
/*     */   {
/* 146 */     this.lockUser = lockUser;
/*     */   }
/*     */ 
/*     */   public boolean isCancelSubscription()
/*     */   {
/* 156 */     return this.cancelSubscription;
/*     */   }
/*     */ 
/*     */   public void setCancelSubscription(boolean cancelSubscription)
/*     */   {
/* 166 */     this.cancelSubscription = cancelSubscription;
/*     */   }
/*     */ 
/*     */   public UnsignedInt getAmount()
/*     */   {
/* 176 */     return this.amount;
/*     */   }
/*     */ 
/*     */   public void setAmount(UnsignedInt amount)
/*     */   {
/* 186 */     this.amount = amount;
/*     */   }
/*     */ 
/*     */   public String getCurrency()
/*     */   {
/* 196 */     return this.currency;
/*     */   }
/*     */ 
/*     */   public void setCurrency(String currency)
/*     */   {
/* 206 */     this.currency = currency;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/* 211 */     if (!(obj instanceof ClickAndBuyTransactionCancelQuery)) return false;
/* 212 */     ClickAndBuyTransactionCancelQuery other = (ClickAndBuyTransactionCancelQuery)obj;
/* 213 */     if (obj == null) return false;
/* 214 */     if (this == obj) return true;
/* 215 */     if (this.__equalsCalc != null) {
/* 216 */       return this.__equalsCalc == obj;
/*     */     }
/* 218 */     this.__equalsCalc = obj;
/*     */ 
/* 220 */     boolean _equals = ((this.slaveMerchantID == null) && (other.getSlaveMerchantID() == null)) || ((this.slaveMerchantID != null) && (this.slaveMerchantID.equals(other.getSlaveMerchantID())) && (this.BDRID == other.getBDRID()) && (((this.externalBDRID == null) && (other.getExternalBDRID() == null)) || ((this.externalBDRID != null) && (this.externalBDRID.equals(other.getExternalBDRID())) && (((this.externalCancelID == null) && (other.getExternalCancelID() == null)) || ((this.externalCancelID != null) && (this.externalCancelID.equals(other.getExternalCancelID())) && (this.lockUser == other.isLockUser()) && (this.cancelSubscription == other.isCancelSubscription()) && (((this.amount == null) && (other.getAmount() == null)) || ((this.amount != null) && (this.amount.equals(other.getAmount())) && (((this.currency == null) && (other.getCurrency() == null)) || ((this.currency != null) && (this.currency.equals(other.getCurrency())))))))))));
/*     */ 
/* 239 */     this.__equalsCalc = null;
/* 240 */     return _equals;
/*     */   }
/*     */ 
/*     */   public synchronized int hashCode()
/*     */   {
/* 245 */     if (this.__hashCodeCalc) {
/* 246 */       return 0;
/*     */     }
/* 248 */     this.__hashCodeCalc = true;
/* 249 */     int _hashCode = 1;
/* 250 */     if (getSlaveMerchantID() != null) {
/* 251 */       _hashCode += getSlaveMerchantID().hashCode();
/*     */     }
/* 253 */     _hashCode += new Long(getBDRID()).hashCode();
/* 254 */     if (getExternalBDRID() != null) {
/* 255 */       _hashCode += getExternalBDRID().hashCode();
/*     */     }
/* 257 */     if (getExternalCancelID() != null) {
/* 258 */       _hashCode += getExternalCancelID().hashCode();
/*     */     }
/* 260 */     _hashCode += (isLockUser() ? Boolean.TRUE : Boolean.FALSE).hashCode();
/* 261 */     _hashCode += (isCancelSubscription() ? Boolean.TRUE : Boolean.FALSE).hashCode();
/* 262 */     if (getAmount() != null) {
/* 263 */       _hashCode += getAmount().hashCode();
/*     */     }
/* 265 */     if (getCurrency() != null) {
/* 266 */       _hashCode += getCurrency().hashCode();
/*     */     }
/* 268 */     this.__hashCodeCalc = false;
/* 269 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 333 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 343 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 355 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 277 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CancelQuery"));
/* 278 */     ElementDesc elemField = new ElementDesc();
/* 279 */     elemField.setFieldName("slaveMerchantID");
/* 280 */     elemField.setXmlName(new QName("", "slaveMerchantID"));
/* 281 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 282 */     elemField.setMinOccurs(0);
/* 283 */     elemField.setNillable(false);
/* 284 */     typeDesc.addFieldDesc(elemField);
/* 285 */     elemField = new ElementDesc();
/* 286 */     elemField.setFieldName("BDRID");
/* 287 */     elemField.setXmlName(new QName("", "BDRID"));
/* 288 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 289 */     elemField.setNillable(false);
/* 290 */     typeDesc.addFieldDesc(elemField);
/* 291 */     elemField = new ElementDesc();
/* 292 */     elemField.setFieldName("externalBDRID");
/* 293 */     elemField.setXmlName(new QName("", "externalBDRID"));
/* 294 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 295 */     elemField.setNillable(false);
/* 296 */     typeDesc.addFieldDesc(elemField);
/* 297 */     elemField = new ElementDesc();
/* 298 */     elemField.setFieldName("externalCancelID");
/* 299 */     elemField.setXmlName(new QName("", "externalCancelID"));
/* 300 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 301 */     elemField.setNillable(false);
/* 302 */     typeDesc.addFieldDesc(elemField);
/* 303 */     elemField = new ElementDesc();
/* 304 */     elemField.setFieldName("lockUser");
/* 305 */     elemField.setXmlName(new QName("", "lockUser"));
/* 306 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 307 */     elemField.setNillable(false);
/* 308 */     typeDesc.addFieldDesc(elemField);
/* 309 */     elemField = new ElementDesc();
/* 310 */     elemField.setFieldName("cancelSubscription");
/* 311 */     elemField.setXmlName(new QName("", "cancelSubscription"));
/* 312 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 313 */     elemField.setNillable(false);
/* 314 */     typeDesc.addFieldDesc(elemField);
/* 315 */     elemField = new ElementDesc();
/* 316 */     elemField.setFieldName("amount");
/* 317 */     elemField.setXmlName(new QName("", "amount"));
/* 318 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"));
/* 319 */     elemField.setNillable(false);
/* 320 */     typeDesc.addFieldDesc(elemField);
/* 321 */     elemField = new ElementDesc();
/* 322 */     elemField.setFieldName("currency");
/* 323 */     elemField.setXmlName(new QName("", "currency"));
/* 324 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 325 */     elemField.setNillable(false);
/* 326 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.ClickAndBuyTransactionCancelQuery
 * JD-Core Version:    0.6.0
 */