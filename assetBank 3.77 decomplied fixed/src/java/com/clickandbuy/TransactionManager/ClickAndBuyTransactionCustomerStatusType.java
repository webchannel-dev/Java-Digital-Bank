/*    */ package com.clickandbuy.TransactionManager;
/*    */ 
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ import javax.xml.namespace.QName;
/*    */ import org.apache.axis.description.TypeDesc;
/*    */ import org.apache.axis.encoding.Deserializer;
/*    */ import org.apache.axis.encoding.Serializer;
/*    */ import org.apache.axis.encoding.ser.EnumDeserializer;
/*    */ import org.apache.axis.encoding.ser.EnumSerializer;
/*    */ 
/*    */ public class ClickAndBuyTransactionCustomerStatusType
/*    */   implements Serializable
/*    */ {
/*    */   private String _value_;
/* 12 */   private static HashMap _table_ = new HashMap();
/*    */   public static final String _OK = "OK";
/*    */   public static final String _LOCKED = "LOCKED";
/*    */   public static final String _TERMINATED = "TERMINATED";
/* 23 */   public static final ClickAndBuyTransactionCustomerStatusType OK = new ClickAndBuyTransactionCustomerStatusType("OK");
/* 24 */   public static final ClickAndBuyTransactionCustomerStatusType LOCKED = new ClickAndBuyTransactionCustomerStatusType("LOCKED");
/* 25 */   public static final ClickAndBuyTransactionCustomerStatusType TERMINATED = new ClickAndBuyTransactionCustomerStatusType("TERMINATED");
/*    */ 
/* 59 */   private static TypeDesc typeDesc = new TypeDesc(ClickAndBuyTransactionCustomerStatusType.class);
/*    */ 
/*    */   protected ClickAndBuyTransactionCustomerStatusType(String value)
/*    */   {
/* 16 */     this._value_ = value;
/* 17 */     _table_.put(this._value_, this);
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 26 */     return this._value_;
/*    */   }
/*    */   public static ClickAndBuyTransactionCustomerStatusType fromValue(String value) throws IllegalArgumentException {
/* 29 */     ClickAndBuyTransactionCustomerStatusType enumeration = (ClickAndBuyTransactionCustomerStatusType)_table_.get(value);
/*    */ 
/* 31 */     if (enumeration == null) throw new IllegalArgumentException();
/* 32 */     return enumeration;
/*    */   }
/*    */ 
/*    */   public static ClickAndBuyTransactionCustomerStatusType fromString(String value) throws IllegalArgumentException {
/* 36 */     return fromValue(value);
/*    */   }
/* 38 */   public boolean equals(Object obj) { return obj == this; } 
/* 39 */   public int hashCode() { return toString().hashCode(); } 
/* 40 */   public String toString() { return this._value_; } 
/* 41 */   public Object readResolve() throws ObjectStreamException { return fromValue(this._value_);
/*    */   }
/*    */ 
/*    */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*    */   {
/* 46 */     return new EnumSerializer(_javaType, _xmlType);
/*    */   }
/*    */ 
/*    */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*    */   {
/* 54 */     return new EnumDeserializer(_javaType, _xmlType);
/*    */   }
/*    */ 
/*    */   public static TypeDesc getTypeDesc()
/*    */   {
/* 69 */     return typeDesc;
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 63 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CustomerStatusType"));
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.ClickAndBuyTransactionCustomerStatusType
 * JD-Core Version:    0.6.0
 */