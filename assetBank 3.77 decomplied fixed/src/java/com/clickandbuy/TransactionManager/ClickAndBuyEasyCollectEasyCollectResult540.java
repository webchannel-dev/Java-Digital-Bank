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
/*     */ public class ClickAndBuyEasyCollectEasyCollectResult540
/*     */   implements Serializable
/*     */ {
/*     */   private long BDRID;
/*     */   private String externalBDRID;
/*     */   private UnsignedInt amount;
/*     */   private String currency;
/*     */   private long systemID;
/*     */   private boolean explicitCommit;
/*     */   private UnsignedInt paidAmount;
/*     */   private UnsignedInt billedAmount;
/*     */   private String billedCurrency;
/*     */   private ClickAndBuyECommercePaymentMethodSafety paymentMethodSafety;
/*     */   private short customerinfo;
/* 281 */   private Object __equalsCalc = null;
/*     */ 
/* 322 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 359 */   private static TypeDesc typeDesc = new TypeDesc(ClickAndBuyEasyCollectEasyCollectResult540.class, true);
/*     */ 
/*     */   public ClickAndBuyEasyCollectEasyCollectResult540()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ClickAndBuyEasyCollectEasyCollectResult540(long BDRID, String externalBDRID, UnsignedInt amount, String currency, long systemID, boolean explicitCommit, UnsignedInt paidAmount, UnsignedInt billedAmount, String billedCurrency, ClickAndBuyECommercePaymentMethodSafety paymentMethodSafety, short customerinfo)
/*     */   {
/*  48 */     this.BDRID = BDRID;
/*  49 */     this.externalBDRID = externalBDRID;
/*  50 */     this.amount = amount;
/*  51 */     this.currency = currency;
/*  52 */     this.systemID = systemID;
/*  53 */     this.explicitCommit = explicitCommit;
/*  54 */     this.paidAmount = paidAmount;
/*  55 */     this.billedAmount = billedAmount;
/*  56 */     this.billedCurrency = billedCurrency;
/*  57 */     this.paymentMethodSafety = paymentMethodSafety;
/*  58 */     this.customerinfo = customerinfo;
/*     */   }
/*     */ 
/*     */   public long getBDRID()
/*     */   {
/*  68 */     return this.BDRID;
/*     */   }
/*     */ 
/*     */   public void setBDRID(long BDRID)
/*     */   {
/*  78 */     this.BDRID = BDRID;
/*     */   }
/*     */ 
/*     */   public String getExternalBDRID()
/*     */   {
/*  88 */     return this.externalBDRID;
/*     */   }
/*     */ 
/*     */   public void setExternalBDRID(String externalBDRID)
/*     */   {
/*  98 */     this.externalBDRID = externalBDRID;
/*     */   }
/*     */ 
/*     */   public UnsignedInt getAmount()
/*     */   {
/* 108 */     return this.amount;
/*     */   }
/*     */ 
/*     */   public void setAmount(UnsignedInt amount)
/*     */   {
/* 118 */     this.amount = amount;
/*     */   }
/*     */ 
/*     */   public String getCurrency()
/*     */   {
/* 128 */     return this.currency;
/*     */   }
/*     */ 
/*     */   public void setCurrency(String currency)
/*     */   {
/* 138 */     this.currency = currency;
/*     */   }
/*     */ 
/*     */   public long getSystemID()
/*     */   {
/* 148 */     return this.systemID;
/*     */   }
/*     */ 
/*     */   public void setSystemID(long systemID)
/*     */   {
/* 158 */     this.systemID = systemID;
/*     */   }
/*     */ 
/*     */   public boolean isExplicitCommit()
/*     */   {
/* 168 */     return this.explicitCommit;
/*     */   }
/*     */ 
/*     */   public void setExplicitCommit(boolean explicitCommit)
/*     */   {
/* 178 */     this.explicitCommit = explicitCommit;
/*     */   }
/*     */ 
/*     */   public UnsignedInt getPaidAmount()
/*     */   {
/* 188 */     return this.paidAmount;
/*     */   }
/*     */ 
/*     */   public void setPaidAmount(UnsignedInt paidAmount)
/*     */   {
/* 198 */     this.paidAmount = paidAmount;
/*     */   }
/*     */ 
/*     */   public UnsignedInt getBilledAmount()
/*     */   {
/* 208 */     return this.billedAmount;
/*     */   }
/*     */ 
/*     */   public void setBilledAmount(UnsignedInt billedAmount)
/*     */   {
/* 218 */     this.billedAmount = billedAmount;
/*     */   }
/*     */ 
/*     */   public String getBilledCurrency()
/*     */   {
/* 228 */     return this.billedCurrency;
/*     */   }
/*     */ 
/*     */   public void setBilledCurrency(String billedCurrency)
/*     */   {
/* 238 */     this.billedCurrency = billedCurrency;
/*     */   }
/*     */ 
/*     */   public ClickAndBuyECommercePaymentMethodSafety getPaymentMethodSafety()
/*     */   {
/* 248 */     return this.paymentMethodSafety;
/*     */   }
/*     */ 
/*     */   public void setPaymentMethodSafety(ClickAndBuyECommercePaymentMethodSafety paymentMethodSafety)
/*     */   {
/* 258 */     this.paymentMethodSafety = paymentMethodSafety;
/*     */   }
/*     */ 
/*     */   public short getCustomerinfo()
/*     */   {
/* 268 */     return this.customerinfo;
/*     */   }
/*     */ 
/*     */   public void setCustomerinfo(short customerinfo)
/*     */   {
/* 278 */     this.customerinfo = customerinfo;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/* 283 */     if (!(obj instanceof ClickAndBuyEasyCollectEasyCollectResult540)) return false;
/* 284 */     ClickAndBuyEasyCollectEasyCollectResult540 other = (ClickAndBuyEasyCollectEasyCollectResult540)obj;
/* 285 */     if (obj == null) return false;
/* 286 */     if (this == obj) return true;
/* 287 */     if (this.__equalsCalc != null) {
/* 288 */       return this.__equalsCalc == obj;
/*     */     }
/* 290 */     this.__equalsCalc = obj;
/*     */ 
/* 292 */     boolean _equals = (this.BDRID == other.getBDRID()) && (((this.externalBDRID == null) && (other.getExternalBDRID() == null)) || ((this.externalBDRID != null) && (this.externalBDRID.equals(other.getExternalBDRID())) && (((this.amount == null) && (other.getAmount() == null)) || ((this.amount != null) && (this.amount.equals(other.getAmount())) && (((this.currency == null) && (other.getCurrency() == null)) || ((this.currency != null) && (this.currency.equals(other.getCurrency())) && (this.systemID == other.getSystemID()) && (this.explicitCommit == other.isExplicitCommit()) && (((this.paidAmount == null) && (other.getPaidAmount() == null)) || ((this.paidAmount != null) && (this.paidAmount.equals(other.getPaidAmount())) && (((this.billedAmount == null) && (other.getBilledAmount() == null)) || ((this.billedAmount != null) && (this.billedAmount.equals(other.getBilledAmount())) && (((this.billedCurrency == null) && (other.getBilledCurrency() == null)) || ((this.billedCurrency != null) && (this.billedCurrency.equals(other.getBilledCurrency())) && (((this.paymentMethodSafety == null) && (other.getPaymentMethodSafety() == null)) || ((this.paymentMethodSafety != null) && (this.paymentMethodSafety.equals(other.getPaymentMethodSafety())) && (this.customerinfo == other.getCustomerinfo())))))))))))))));
/*     */ 
/* 318 */     this.__equalsCalc = null;
/* 319 */     return _equals;
/*     */   }
/*     */ 
/*     */   public synchronized int hashCode()
/*     */   {
/* 324 */     if (this.__hashCodeCalc) {
/* 325 */       return 0;
/*     */     }
/* 327 */     this.__hashCodeCalc = true;
/* 328 */     int _hashCode = 1;
/* 329 */     _hashCode += new Long(getBDRID()).hashCode();
/* 330 */     if (getExternalBDRID() != null) {
/* 331 */       _hashCode += getExternalBDRID().hashCode();
/*     */     }
/* 333 */     if (getAmount() != null) {
/* 334 */       _hashCode += getAmount().hashCode();
/*     */     }
/* 336 */     if (getCurrency() != null) {
/* 337 */       _hashCode += getCurrency().hashCode();
/*     */     }
/* 339 */     _hashCode += new Long(getSystemID()).hashCode();
/* 340 */     _hashCode += (isExplicitCommit() ? Boolean.TRUE : Boolean.FALSE).hashCode();
/* 341 */     if (getPaidAmount() != null) {
/* 342 */       _hashCode += getPaidAmount().hashCode();
/*     */     }
/* 344 */     if (getBilledAmount() != null) {
/* 345 */       _hashCode += getBilledAmount().hashCode();
/*     */     }
/* 347 */     if (getBilledCurrency() != null) {
/* 348 */       _hashCode += getBilledCurrency().hashCode();
/*     */     }
/* 350 */     if (getPaymentMethodSafety() != null) {
/* 351 */       _hashCode += getPaymentMethodSafety().hashCode();
/*     */     }
/* 353 */     _hashCode += getCustomerinfo();
/* 354 */     this.__hashCodeCalc = false;
/* 355 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 436 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 446 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 458 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 363 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectResult540"));
/* 364 */     ElementDesc elemField = new ElementDesc();
/* 365 */     elemField.setFieldName("BDRID");
/* 366 */     elemField.setXmlName(new QName("", "BDRID"));
/* 367 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 368 */     elemField.setNillable(false);
/* 369 */     typeDesc.addFieldDesc(elemField);
/* 370 */     elemField = new ElementDesc();
/* 371 */     elemField.setFieldName("externalBDRID");
/* 372 */     elemField.setXmlName(new QName("", "externalBDRID"));
/* 373 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 374 */     elemField.setNillable(false);
/* 375 */     typeDesc.addFieldDesc(elemField);
/* 376 */     elemField = new ElementDesc();
/* 377 */     elemField.setFieldName("amount");
/* 378 */     elemField.setXmlName(new QName("", "amount"));
/* 379 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"));
/* 380 */     elemField.setNillable(false);
/* 381 */     typeDesc.addFieldDesc(elemField);
/* 382 */     elemField = new ElementDesc();
/* 383 */     elemField.setFieldName("currency");
/* 384 */     elemField.setXmlName(new QName("", "currency"));
/* 385 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 386 */     elemField.setNillable(false);
/* 387 */     typeDesc.addFieldDesc(elemField);
/* 388 */     elemField = new ElementDesc();
/* 389 */     elemField.setFieldName("systemID");
/* 390 */     elemField.setXmlName(new QName("", "systemID"));
/* 391 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 392 */     elemField.setNillable(false);
/* 393 */     typeDesc.addFieldDesc(elemField);
/* 394 */     elemField = new ElementDesc();
/* 395 */     elemField.setFieldName("explicitCommit");
/* 396 */     elemField.setXmlName(new QName("", "explicitCommit"));
/* 397 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 398 */     elemField.setNillable(false);
/* 399 */     typeDesc.addFieldDesc(elemField);
/* 400 */     elemField = new ElementDesc();
/* 401 */     elemField.setFieldName("paidAmount");
/* 402 */     elemField.setXmlName(new QName("", "paidAmount"));
/* 403 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"));
/* 404 */     elemField.setNillable(false);
/* 405 */     typeDesc.addFieldDesc(elemField);
/* 406 */     elemField = new ElementDesc();
/* 407 */     elemField.setFieldName("billedAmount");
/* 408 */     elemField.setXmlName(new QName("", "billedAmount"));
/* 409 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"));
/* 410 */     elemField.setNillable(false);
/* 411 */     typeDesc.addFieldDesc(elemField);
/* 412 */     elemField = new ElementDesc();
/* 413 */     elemField.setFieldName("billedCurrency");
/* 414 */     elemField.setXmlName(new QName("", "billedCurrency"));
/* 415 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 416 */     elemField.setNillable(false);
/* 417 */     typeDesc.addFieldDesc(elemField);
/* 418 */     elemField = new ElementDesc();
/* 419 */     elemField.setFieldName("paymentMethodSafety");
/* 420 */     elemField.setXmlName(new QName("", "paymentMethodSafety"));
/* 421 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.ECommerce.PaymentMethodSafety"));
/* 422 */     elemField.setNillable(false);
/* 423 */     typeDesc.addFieldDesc(elemField);
/* 424 */     elemField = new ElementDesc();
/* 425 */     elemField.setFieldName("customerinfo");
/* 426 */     elemField.setXmlName(new QName("", "customerinfo"));
/* 427 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "short"));
/* 428 */     elemField.setNillable(false);
/* 429 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.ClickAndBuyEasyCollectEasyCollectResult540
 * JD-Core Version:    0.6.0
 */