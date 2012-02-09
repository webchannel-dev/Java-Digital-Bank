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
/*     */ public class ClickAndBuyEasyCollectEasyCollectResult
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
/* 233 */   private Object __equalsCalc = null;
/*     */ 
/* 270 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 303 */   private static TypeDesc typeDesc = new TypeDesc(ClickAndBuyEasyCollectEasyCollectResult.class, true);
/*     */ 
/*     */   public ClickAndBuyEasyCollectEasyCollectResult()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ClickAndBuyEasyCollectEasyCollectResult(long BDRID, String externalBDRID, UnsignedInt amount, String currency, long systemID, boolean explicitCommit, UnsignedInt paidAmount, UnsignedInt billedAmount, String billedCurrency)
/*     */   {
/*  42 */     this.BDRID = BDRID;
/*  43 */     this.externalBDRID = externalBDRID;
/*  44 */     this.amount = amount;
/*  45 */     this.currency = currency;
/*  46 */     this.systemID = systemID;
/*  47 */     this.explicitCommit = explicitCommit;
/*  48 */     this.paidAmount = paidAmount;
/*  49 */     this.billedAmount = billedAmount;
/*  50 */     this.billedCurrency = billedCurrency;
/*     */   }
/*     */ 
/*     */   public long getBDRID()
/*     */   {
/*  60 */     return this.BDRID;
/*     */   }
/*     */ 
/*     */   public void setBDRID(long BDRID)
/*     */   {
/*  70 */     this.BDRID = BDRID;
/*     */   }
/*     */ 
/*     */   public String getExternalBDRID()
/*     */   {
/*  80 */     return this.externalBDRID;
/*     */   }
/*     */ 
/*     */   public void setExternalBDRID(String externalBDRID)
/*     */   {
/*  90 */     this.externalBDRID = externalBDRID;
/*     */   }
/*     */ 
/*     */   public UnsignedInt getAmount()
/*     */   {
/* 100 */     return this.amount;
/*     */   }
/*     */ 
/*     */   public void setAmount(UnsignedInt amount)
/*     */   {
/* 110 */     this.amount = amount;
/*     */   }
/*     */ 
/*     */   public String getCurrency()
/*     */   {
/* 120 */     return this.currency;
/*     */   }
/*     */ 
/*     */   public void setCurrency(String currency)
/*     */   {
/* 130 */     this.currency = currency;
/*     */   }
/*     */ 
/*     */   public long getSystemID()
/*     */   {
/* 140 */     return this.systemID;
/*     */   }
/*     */ 
/*     */   public void setSystemID(long systemID)
/*     */   {
/* 150 */     this.systemID = systemID;
/*     */   }
/*     */ 
/*     */   public boolean isExplicitCommit()
/*     */   {
/* 160 */     return this.explicitCommit;
/*     */   }
/*     */ 
/*     */   public void setExplicitCommit(boolean explicitCommit)
/*     */   {
/* 170 */     this.explicitCommit = explicitCommit;
/*     */   }
/*     */ 
/*     */   public UnsignedInt getPaidAmount()
/*     */   {
/* 180 */     return this.paidAmount;
/*     */   }
/*     */ 
/*     */   public void setPaidAmount(UnsignedInt paidAmount)
/*     */   {
/* 190 */     this.paidAmount = paidAmount;
/*     */   }
/*     */ 
/*     */   public UnsignedInt getBilledAmount()
/*     */   {
/* 200 */     return this.billedAmount;
/*     */   }
/*     */ 
/*     */   public void setBilledAmount(UnsignedInt billedAmount)
/*     */   {
/* 210 */     this.billedAmount = billedAmount;
/*     */   }
/*     */ 
/*     */   public String getBilledCurrency()
/*     */   {
/* 220 */     return this.billedCurrency;
/*     */   }
/*     */ 
/*     */   public void setBilledCurrency(String billedCurrency)
/*     */   {
/* 230 */     this.billedCurrency = billedCurrency;
/*     */   }
/*     */ 
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/* 235 */     if (!(obj instanceof ClickAndBuyEasyCollectEasyCollectResult)) return false;
/* 236 */     ClickAndBuyEasyCollectEasyCollectResult other = (ClickAndBuyEasyCollectEasyCollectResult)obj;
/* 237 */     if (obj == null) return false;
/* 238 */     if (this == obj) return true;
/* 239 */     if (this.__equalsCalc != null) {
/* 240 */       return this.__equalsCalc == obj;
/*     */     }
/* 242 */     this.__equalsCalc = obj;
/*     */ 
/* 244 */     boolean _equals = (this.BDRID == other.getBDRID()) && (((this.externalBDRID == null) && (other.getExternalBDRID() == null)) || ((this.externalBDRID != null) && (this.externalBDRID.equals(other.getExternalBDRID())) && (((this.amount == null) && (other.getAmount() == null)) || ((this.amount != null) && (this.amount.equals(other.getAmount())) && (((this.currency == null) && (other.getCurrency() == null)) || ((this.currency != null) && (this.currency.equals(other.getCurrency())) && (this.systemID == other.getSystemID()) && (this.explicitCommit == other.isExplicitCommit()) && (((this.paidAmount == null) && (other.getPaidAmount() == null)) || ((this.paidAmount != null) && (this.paidAmount.equals(other.getPaidAmount())) && (((this.billedAmount == null) && (other.getBilledAmount() == null)) || ((this.billedAmount != null) && (this.billedAmount.equals(other.getBilledAmount())) && (((this.billedCurrency == null) && (other.getBilledCurrency() == null)) || ((this.billedCurrency != null) && (this.billedCurrency.equals(other.getBilledCurrency()))))))))))))));
/*     */ 
/* 266 */     this.__equalsCalc = null;
/* 267 */     return _equals;
/*     */   }
/*     */ 
/*     */   public synchronized int hashCode()
/*     */   {
/* 272 */     if (this.__hashCodeCalc) {
/* 273 */       return 0;
/*     */     }
/* 275 */     this.__hashCodeCalc = true;
/* 276 */     int _hashCode = 1;
/* 277 */     _hashCode += new Long(getBDRID()).hashCode();
/* 278 */     if (getExternalBDRID() != null) {
/* 279 */       _hashCode += getExternalBDRID().hashCode();
/*     */     }
/* 281 */     if (getAmount() != null) {
/* 282 */       _hashCode += getAmount().hashCode();
/*     */     }
/* 284 */     if (getCurrency() != null) {
/* 285 */       _hashCode += getCurrency().hashCode();
/*     */     }
/* 287 */     _hashCode += new Long(getSystemID()).hashCode();
/* 288 */     _hashCode += (isExplicitCommit() ? Boolean.TRUE : Boolean.FALSE).hashCode();
/* 289 */     if (getPaidAmount() != null) {
/* 290 */       _hashCode += getPaidAmount().hashCode();
/*     */     }
/* 292 */     if (getBilledAmount() != null) {
/* 293 */       _hashCode += getBilledAmount().hashCode();
/*     */     }
/* 295 */     if (getBilledCurrency() != null) {
/* 296 */       _hashCode += getBilledCurrency().hashCode();
/*     */     }
/* 298 */     this.__hashCodeCalc = false;
/* 299 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 368 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 378 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 390 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 307 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectResult"));
/* 308 */     ElementDesc elemField = new ElementDesc();
/* 309 */     elemField.setFieldName("BDRID");
/* 310 */     elemField.setXmlName(new QName("", "BDRID"));
/* 311 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 312 */     elemField.setNillable(false);
/* 313 */     typeDesc.addFieldDesc(elemField);
/* 314 */     elemField = new ElementDesc();
/* 315 */     elemField.setFieldName("externalBDRID");
/* 316 */     elemField.setXmlName(new QName("", "externalBDRID"));
/* 317 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 318 */     elemField.setNillable(false);
/* 319 */     typeDesc.addFieldDesc(elemField);
/* 320 */     elemField = new ElementDesc();
/* 321 */     elemField.setFieldName("amount");
/* 322 */     elemField.setXmlName(new QName("", "amount"));
/* 323 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"));
/* 324 */     elemField.setNillable(false);
/* 325 */     typeDesc.addFieldDesc(elemField);
/* 326 */     elemField = new ElementDesc();
/* 327 */     elemField.setFieldName("currency");
/* 328 */     elemField.setXmlName(new QName("", "currency"));
/* 329 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 330 */     elemField.setNillable(false);
/* 331 */     typeDesc.addFieldDesc(elemField);
/* 332 */     elemField = new ElementDesc();
/* 333 */     elemField.setFieldName("systemID");
/* 334 */     elemField.setXmlName(new QName("", "systemID"));
/* 335 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 336 */     elemField.setNillable(false);
/* 337 */     typeDesc.addFieldDesc(elemField);
/* 338 */     elemField = new ElementDesc();
/* 339 */     elemField.setFieldName("explicitCommit");
/* 340 */     elemField.setXmlName(new QName("", "explicitCommit"));
/* 341 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 342 */     elemField.setNillable(false);
/* 343 */     typeDesc.addFieldDesc(elemField);
/* 344 */     elemField = new ElementDesc();
/* 345 */     elemField.setFieldName("paidAmount");
/* 346 */     elemField.setXmlName(new QName("", "paidAmount"));
/* 347 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"));
/* 348 */     elemField.setNillable(false);
/* 349 */     typeDesc.addFieldDesc(elemField);
/* 350 */     elemField = new ElementDesc();
/* 351 */     elemField.setFieldName("billedAmount");
/* 352 */     elemField.setXmlName(new QName("", "billedAmount"));
/* 353 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"));
/* 354 */     elemField.setNillable(false);
/* 355 */     typeDesc.addFieldDesc(elemField);
/* 356 */     elemField = new ElementDesc();
/* 357 */     elemField.setFieldName("billedCurrency");
/* 358 */     elemField.setXmlName(new QName("", "billedCurrency"));
/* 359 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 360 */     elemField.setNillable(false);
/* 361 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.ClickAndBuyEasyCollectEasyCollectResult
 * JD-Core Version:    0.6.0
 */