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
/*     */ import org.apache.axis.types.UnsignedInt;
/*     */ 
/*     */ public class ClickAndBuyTransactionBDRStatus
/*     */   implements Serializable
/*     */ {
/*     */   private long BDRID;
/*     */   private String externalBDRID;
/*     */   private long URLID;
/*     */   private UnsignedInt price;
/*     */   private String currency;
/*     */   private boolean noVat;
/*     */   private String linkName;
/*     */   private String adtlURLInfo;
/*     */   private String clickURL;
/*     */   private String surferIP;
/*     */   private boolean isChargeBack;
/*     */   private String creationDateTime;
/*     */   private long crn;
/*     */   private ClickAndBuyProperty[] BDRProperties;
/* 353 */   private Object __equalsCalc = null;
/*     */ 
/* 401 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 453 */   private static TypeDesc typeDesc = new TypeDesc(ClickAndBuyTransactionBDRStatus.class, true);
/*     */ 
/*     */   public ClickAndBuyTransactionBDRStatus()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ClickAndBuyTransactionBDRStatus(long BDRID, String externalBDRID, long URLID, UnsignedInt price, String currency, boolean noVat, String linkName, String adtlURLInfo, String clickURL, String surferIP, boolean isChargeBack, String creationDateTime, long crn, ClickAndBuyProperty[] BDRProperties)
/*     */   {
/*  57 */     this.BDRID = BDRID;
/*  58 */     this.externalBDRID = externalBDRID;
/*  59 */     this.URLID = URLID;
/*  60 */     this.price = price;
/*  61 */     this.currency = currency;
/*  62 */     this.noVat = noVat;
/*  63 */     this.linkName = linkName;
/*  64 */     this.adtlURLInfo = adtlURLInfo;
/*  65 */     this.clickURL = clickURL;
/*  66 */     this.surferIP = surferIP;
/*  67 */     this.isChargeBack = isChargeBack;
/*  68 */     this.creationDateTime = creationDateTime;
/*  69 */     this.crn = crn;
/*  70 */     this.BDRProperties = BDRProperties;
/*     */   }
/*     */ 
/*     */   public long getBDRID()
/*     */   {
/*  80 */     return this.BDRID;
/*     */   }
/*     */ 
/*     */   public void setBDRID(long BDRID)
/*     */   {
/*  90 */     this.BDRID = BDRID;
/*     */   }
/*     */ 
/*     */   public String getExternalBDRID()
/*     */   {
/* 100 */     return this.externalBDRID;
/*     */   }
/*     */ 
/*     */   public void setExternalBDRID(String externalBDRID)
/*     */   {
/* 110 */     this.externalBDRID = externalBDRID;
/*     */   }
/*     */ 
/*     */   public long getURLID()
/*     */   {
/* 120 */     return this.URLID;
/*     */   }
/*     */ 
/*     */   public void setURLID(long URLID)
/*     */   {
/* 130 */     this.URLID = URLID;
/*     */   }
/*     */ 
/*     */   public UnsignedInt getPrice()
/*     */   {
/* 140 */     return this.price;
/*     */   }
/*     */ 
/*     */   public void setPrice(UnsignedInt price)
/*     */   {
/* 150 */     this.price = price;
/*     */   }
/*     */ 
/*     */   public String getCurrency()
/*     */   {
/* 160 */     return this.currency;
/*     */   }
/*     */ 
/*     */   public void setCurrency(String currency)
/*     */   {
/* 170 */     this.currency = currency;
/*     */   }
/*     */ 
/*     */   public boolean isNoVat()
/*     */   {
/* 180 */     return this.noVat;
/*     */   }
/*     */ 
/*     */   public void setNoVat(boolean noVat)
/*     */   {
/* 190 */     this.noVat = noVat;
/*     */   }
/*     */ 
/*     */   public String getLinkName()
/*     */   {
/* 200 */     return this.linkName;
/*     */   }
/*     */ 
/*     */   public void setLinkName(String linkName)
/*     */   {
/* 210 */     this.linkName = linkName;
/*     */   }
/*     */ 
/*     */   public String getAdtlURLInfo()
/*     */   {
/* 220 */     return this.adtlURLInfo;
/*     */   }
/*     */ 
/*     */   public void setAdtlURLInfo(String adtlURLInfo)
/*     */   {
/* 230 */     this.adtlURLInfo = adtlURLInfo;
/*     */   }
/*     */ 
/*     */   public String getClickURL()
/*     */   {
/* 240 */     return this.clickURL;
/*     */   }
/*     */ 
/*     */   public void setClickURL(String clickURL)
/*     */   {
/* 250 */     this.clickURL = clickURL;
/*     */   }
/*     */ 
/*     */   public String getSurferIP()
/*     */   {
/* 260 */     return this.surferIP;
/*     */   }
/*     */ 
/*     */   public void setSurferIP(String surferIP)
/*     */   {
/* 270 */     this.surferIP = surferIP;
/*     */   }
/*     */ 
/*     */   public boolean isIsChargeBack()
/*     */   {
/* 280 */     return this.isChargeBack;
/*     */   }
/*     */ 
/*     */   public void setIsChargeBack(boolean isChargeBack)
/*     */   {
/* 290 */     this.isChargeBack = isChargeBack;
/*     */   }
/*     */ 
/*     */   public String getCreationDateTime()
/*     */   {
/* 300 */     return this.creationDateTime;
/*     */   }
/*     */ 
/*     */   public void setCreationDateTime(String creationDateTime)
/*     */   {
/* 310 */     this.creationDateTime = creationDateTime;
/*     */   }
/*     */ 
/*     */   public long getCrn()
/*     */   {
/* 320 */     return this.crn;
/*     */   }
/*     */ 
/*     */   public void setCrn(long crn)
/*     */   {
/* 330 */     this.crn = crn;
/*     */   }
/*     */ 
/*     */   public ClickAndBuyProperty[] getBDRProperties()
/*     */   {
/* 340 */     return this.BDRProperties;
/*     */   }
/*     */ 
/*     */   public void setBDRProperties(ClickAndBuyProperty[] BDRProperties)
/*     */   {
/* 350 */     this.BDRProperties = BDRProperties;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/* 355 */     if (!(obj instanceof ClickAndBuyTransactionBDRStatus)) return false;
/* 356 */     ClickAndBuyTransactionBDRStatus other = (ClickAndBuyTransactionBDRStatus)obj;
/* 357 */     if (obj == null) return false;
/* 358 */     if (this == obj) return true;
/* 359 */     if (this.__equalsCalc != null) {
/* 360 */       return this.__equalsCalc == obj;
/*     */     }
/* 362 */     this.__equalsCalc = obj;
/*     */ 
/* 364 */     boolean _equals = (this.BDRID == other.getBDRID()) && (((this.externalBDRID == null) && (other.getExternalBDRID() == null)) || ((this.externalBDRID != null) && (this.externalBDRID.equals(other.getExternalBDRID())) && (this.URLID == other.getURLID()) && (((this.price == null) && (other.getPrice() == null)) || ((this.price != null) && (this.price.equals(other.getPrice())) && (((this.currency == null) && (other.getCurrency() == null)) || ((this.currency != null) && (this.currency.equals(other.getCurrency())) && (this.noVat == other.isNoVat()) && (((this.linkName == null) && (other.getLinkName() == null)) || ((this.linkName != null) && (this.linkName.equals(other.getLinkName())) && (((this.adtlURLInfo == null) && (other.getAdtlURLInfo() == null)) || ((this.adtlURLInfo != null) && (this.adtlURLInfo.equals(other.getAdtlURLInfo())) && (((this.clickURL == null) && (other.getClickURL() == null)) || ((this.clickURL != null) && (this.clickURL.equals(other.getClickURL())) && (((this.surferIP == null) && (other.getSurferIP() == null)) || ((this.surferIP != null) && (this.surferIP.equals(other.getSurferIP())) && (this.isChargeBack == other.isIsChargeBack()) && (((this.creationDateTime == null) && (other.getCreationDateTime() == null)) || ((this.creationDateTime != null) && (this.creationDateTime.equals(other.getCreationDateTime())) && (this.crn == other.getCrn()) && (((this.BDRProperties == null) && (other.getBDRProperties() == null)) || ((this.BDRProperties != null) && (Arrays.equals(this.BDRProperties, other.getBDRProperties()))))))))))))))))))));
/*     */ 
/* 397 */     this.__equalsCalc = null;
/* 398 */     return _equals;
/*     */   }
/*     */ 
/*     */   public synchronized int hashCode()
/*     */   {
/* 403 */     if (this.__hashCodeCalc) {
/* 404 */       return 0;
/*     */     }
/* 406 */     this.__hashCodeCalc = true;
/* 407 */     int _hashCode = 1;
/* 408 */     _hashCode += new Long(getBDRID()).hashCode();
/* 409 */     if (getExternalBDRID() != null) {
/* 410 */       _hashCode += getExternalBDRID().hashCode();
/*     */     }
/* 412 */     _hashCode += new Long(getURLID()).hashCode();
/* 413 */     if (getPrice() != null) {
/* 414 */       _hashCode += getPrice().hashCode();
/*     */     }
/* 416 */     if (getCurrency() != null) {
/* 417 */       _hashCode += getCurrency().hashCode();
/*     */     }
/* 419 */     _hashCode += (isNoVat() ? Boolean.TRUE : Boolean.FALSE).hashCode();
/* 420 */     if (getLinkName() != null) {
/* 421 */       _hashCode += getLinkName().hashCode();
/*     */     }
/* 423 */     if (getAdtlURLInfo() != null) {
/* 424 */       _hashCode += getAdtlURLInfo().hashCode();
/*     */     }
/* 426 */     if (getClickURL() != null) {
/* 427 */       _hashCode += getClickURL().hashCode();
/*     */     }
/* 429 */     if (getSurferIP() != null) {
/* 430 */       _hashCode += getSurferIP().hashCode();
/*     */     }
/* 432 */     _hashCode += (isIsChargeBack() ? Boolean.TRUE : Boolean.FALSE).hashCode();
/* 433 */     if (getCreationDateTime() != null) {
/* 434 */       _hashCode += getCreationDateTime().hashCode();
/*     */     }
/* 436 */     _hashCode += new Long(getCrn()).hashCode();
/* 437 */     if (getBDRProperties() != null) {
/* 438 */       int i = 0;
/* 439 */       while (i < Array.getLength(getBDRProperties()))
/*     */       {
/* 441 */         Object obj = Array.get(getBDRProperties(), i);
/* 442 */         if ((obj != null) && (!obj.getClass().isArray()))
/*     */         {
/* 444 */           _hashCode += obj.hashCode();
/*     */         }
/* 440 */         i++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 448 */     this.__hashCodeCalc = false;
/* 449 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 549 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 559 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 571 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 457 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.BDRStatus"));
/* 458 */     ElementDesc elemField = new ElementDesc();
/* 459 */     elemField.setFieldName("BDRID");
/* 460 */     elemField.setXmlName(new QName("", "BDRID"));
/* 461 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 462 */     elemField.setNillable(false);
/* 463 */     typeDesc.addFieldDesc(elemField);
/* 464 */     elemField = new ElementDesc();
/* 465 */     elemField.setFieldName("externalBDRID");
/* 466 */     elemField.setXmlName(new QName("", "externalBDRID"));
/* 467 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 468 */     elemField.setNillable(false);
/* 469 */     typeDesc.addFieldDesc(elemField);
/* 470 */     elemField = new ElementDesc();
/* 471 */     elemField.setFieldName("URLID");
/* 472 */     elemField.setXmlName(new QName("", "URLID"));
/* 473 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 474 */     elemField.setNillable(false);
/* 475 */     typeDesc.addFieldDesc(elemField);
/* 476 */     elemField = new ElementDesc();
/* 477 */     elemField.setFieldName("price");
/* 478 */     elemField.setXmlName(new QName("", "price"));
/* 479 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"));
/* 480 */     elemField.setNillable(false);
/* 481 */     typeDesc.addFieldDesc(elemField);
/* 482 */     elemField = new ElementDesc();
/* 483 */     elemField.setFieldName("currency");
/* 484 */     elemField.setXmlName(new QName("", "currency"));
/* 485 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 486 */     elemField.setNillable(false);
/* 487 */     typeDesc.addFieldDesc(elemField);
/* 488 */     elemField = new ElementDesc();
/* 489 */     elemField.setFieldName("noVat");
/* 490 */     elemField.setXmlName(new QName("", "noVat"));
/* 491 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 492 */     elemField.setNillable(false);
/* 493 */     typeDesc.addFieldDesc(elemField);
/* 494 */     elemField = new ElementDesc();
/* 495 */     elemField.setFieldName("linkName");
/* 496 */     elemField.setXmlName(new QName("", "linkName"));
/* 497 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 498 */     elemField.setNillable(false);
/* 499 */     typeDesc.addFieldDesc(elemField);
/* 500 */     elemField = new ElementDesc();
/* 501 */     elemField.setFieldName("adtlURLInfo");
/* 502 */     elemField.setXmlName(new QName("", "adtlURLInfo"));
/* 503 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 504 */     elemField.setNillable(false);
/* 505 */     typeDesc.addFieldDesc(elemField);
/* 506 */     elemField = new ElementDesc();
/* 507 */     elemField.setFieldName("clickURL");
/* 508 */     elemField.setXmlName(new QName("", "clickURL"));
/* 509 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 510 */     elemField.setNillable(false);
/* 511 */     typeDesc.addFieldDesc(elemField);
/* 512 */     elemField = new ElementDesc();
/* 513 */     elemField.setFieldName("surferIP");
/* 514 */     elemField.setXmlName(new QName("", "surferIP"));
/* 515 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 516 */     elemField.setNillable(false);
/* 517 */     typeDesc.addFieldDesc(elemField);
/* 518 */     elemField = new ElementDesc();
/* 519 */     elemField.setFieldName("isChargeBack");
/* 520 */     elemField.setXmlName(new QName("", "isChargeBack"));
/* 521 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 522 */     elemField.setNillable(false);
/* 523 */     typeDesc.addFieldDesc(elemField);
/* 524 */     elemField = new ElementDesc();
/* 525 */     elemField.setFieldName("creationDateTime");
/* 526 */     elemField.setXmlName(new QName("", "creationDateTime"));
/* 527 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 528 */     elemField.setNillable(false);
/* 529 */     typeDesc.addFieldDesc(elemField);
/* 530 */     elemField = new ElementDesc();
/* 531 */     elemField.setFieldName("crn");
/* 532 */     elemField.setXmlName(new QName("", "crn"));
/* 533 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 534 */     elemField.setNillable(false);
/* 535 */     typeDesc.addFieldDesc(elemField);
/* 536 */     elemField = new ElementDesc();
/* 537 */     elemField.setFieldName("BDRProperties");
/* 538 */     elemField.setXmlName(new QName("", "BDRProperties"));
/* 539 */     elemField.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Property"));
/* 540 */     elemField.setNillable(false);
/* 541 */     elemField.setItemQName(new QName("", "item"));
/* 542 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.ClickAndBuyTransactionBDRStatus
 * JD-Core Version:    0.6.0
 */