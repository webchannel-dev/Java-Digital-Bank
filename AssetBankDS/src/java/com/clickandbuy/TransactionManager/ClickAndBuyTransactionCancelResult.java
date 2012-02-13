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
/*     */ public class ClickAndBuyTransactionCancelResult
/*     */   implements Serializable
/*     */ {
/*     */   private long BDRID;
/*     */   private String externalBDRID;
/*     */   private String externalCancelID;
/*     */   private UnsignedInt amount;
/*     */   private String currency;
/*     */   private long systemID;
/* 161 */   private Object __equalsCalc = null;
/*     */ 
/* 191 */   private boolean __hashCodeCalc = false;
/*     */ 
/* 217 */   private static TypeDesc typeDesc = new TypeDesc(ClickAndBuyTransactionCancelResult.class, true);
/*     */ 
/*     */   public ClickAndBuyTransactionCancelResult()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ClickAndBuyTransactionCancelResult(long BDRID, String externalBDRID, String externalCancelID, UnsignedInt amount, String currency, long systemID)
/*     */   {
/*  33 */     this.BDRID = BDRID;
/*  34 */     this.externalBDRID = externalBDRID;
/*  35 */     this.externalCancelID = externalCancelID;
/*  36 */     this.amount = amount;
/*  37 */     this.currency = currency;
/*  38 */     this.systemID = systemID;
/*     */   }
/*     */ 
/*     */   public long getBDRID()
/*     */   {
/*  48 */     return this.BDRID;
/*     */   }
/*     */ 
/*     */   public void setBDRID(long BDRID)
/*     */   {
/*  58 */     this.BDRID = BDRID;
/*     */   }
/*     */ 
/*     */   public String getExternalBDRID()
/*     */   {
/*  68 */     return this.externalBDRID;
/*     */   }
/*     */ 
/*     */   public void setExternalBDRID(String externalBDRID)
/*     */   {
/*  78 */     this.externalBDRID = externalBDRID;
/*     */   }
/*     */ 
/*     */   public String getExternalCancelID()
/*     */   {
/*  88 */     return this.externalCancelID;
/*     */   }
/*     */ 
/*     */   public void setExternalCancelID(String externalCancelID)
/*     */   {
/*  98 */     this.externalCancelID = externalCancelID;
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
/*     */   public synchronized boolean equals(Object obj)
/*     */   {
/* 163 */     if (!(obj instanceof ClickAndBuyTransactionCancelResult)) return false;
/* 164 */     ClickAndBuyTransactionCancelResult other = (ClickAndBuyTransactionCancelResult)obj;
/* 165 */     if (obj == null) return false;
/* 166 */     if (this == obj) return true;
/* 167 */     if (this.__equalsCalc != null) {
/* 168 */       return this.__equalsCalc == obj;
/*     */     }
/* 170 */     this.__equalsCalc = obj;
/*     */ 
/* 172 */     boolean _equals = (this.BDRID == other.getBDRID()) && (((this.externalBDRID == null) && (other.getExternalBDRID() == null)) || ((this.externalBDRID != null) && (this.externalBDRID.equals(other.getExternalBDRID())) && (((this.externalCancelID == null) && (other.getExternalCancelID() == null)) || ((this.externalCancelID != null) && (this.externalCancelID.equals(other.getExternalCancelID())) && (((this.amount == null) && (other.getAmount() == null)) || ((this.amount != null) && (this.amount.equals(other.getAmount())) && (((this.currency == null) && (other.getCurrency() == null)) || ((this.currency != null) && (this.currency.equals(other.getCurrency())) && (this.systemID == other.getSystemID())))))))));
/*     */ 
/* 187 */     this.__equalsCalc = null;
/* 188 */     return _equals;
/*     */   }
/*     */ 
/*     */   public synchronized int hashCode()
/*     */   {
/* 193 */     if (this.__hashCodeCalc) {
/* 194 */       return 0;
/*     */     }
/* 196 */     this.__hashCodeCalc = true;
/* 197 */     int _hashCode = 1;
/* 198 */     _hashCode += new Long(getBDRID()).hashCode();
/* 199 */     if (getExternalBDRID() != null) {
/* 200 */       _hashCode += getExternalBDRID().hashCode();
/*     */     }
/* 202 */     if (getExternalCancelID() != null) {
/* 203 */       _hashCode += getExternalCancelID().hashCode();
/*     */     }
/* 205 */     if (getAmount() != null) {
/* 206 */       _hashCode += getAmount().hashCode();
/*     */     }
/* 208 */     if (getCurrency() != null) {
/* 209 */       _hashCode += getCurrency().hashCode();
/*     */     }
/* 211 */     _hashCode += new Long(getSystemID()).hashCode();
/* 212 */     this.__hashCodeCalc = false;
/* 213 */     return _hashCode;
/*     */   }
/*     */ 
/*     */   public static TypeDesc getTypeDesc()
/*     */   {
/* 264 */     return typeDesc;
/*     */   }
/*     */ 
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 274 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*     */   {
/* 286 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 221 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CancelResult"));
/* 222 */     ElementDesc elemField = new ElementDesc();
/* 223 */     elemField.setFieldName("BDRID");
/* 224 */     elemField.setXmlName(new QName("", "BDRID"));
/* 225 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 226 */     elemField.setNillable(false);
/* 227 */     typeDesc.addFieldDesc(elemField);
/* 228 */     elemField = new ElementDesc();
/* 229 */     elemField.setFieldName("externalBDRID");
/* 230 */     elemField.setXmlName(new QName("", "externalBDRID"));
/* 231 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 232 */     elemField.setNillable(false);
/* 233 */     typeDesc.addFieldDesc(elemField);
/* 234 */     elemField = new ElementDesc();
/* 235 */     elemField.setFieldName("externalCancelID");
/* 236 */     elemField.setXmlName(new QName("", "externalCancelID"));
/* 237 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 238 */     elemField.setNillable(false);
/* 239 */     typeDesc.addFieldDesc(elemField);
/* 240 */     elemField = new ElementDesc();
/* 241 */     elemField.setFieldName("amount");
/* 242 */     elemField.setXmlName(new QName("", "amount"));
/* 243 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"));
/* 244 */     elemField.setNillable(false);
/* 245 */     typeDesc.addFieldDesc(elemField);
/* 246 */     elemField = new ElementDesc();
/* 247 */     elemField.setFieldName("currency");
/* 248 */     elemField.setXmlName(new QName("", "currency"));
/* 249 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 250 */     elemField.setNillable(false);
/* 251 */     typeDesc.addFieldDesc(elemField);
/* 252 */     elemField = new ElementDesc();
/* 253 */     elemField.setFieldName("systemID");
/* 254 */     elemField.setXmlName(new QName("", "systemID"));
/* 255 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 256 */     elemField.setNillable(false);
/* 257 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.ClickAndBuyTransactionCancelResult
 * JD-Core Version:    0.6.0
 */