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
/*    */ public class TransactionManagerPaymentTransactionType
/*    */   implements Serializable
/*    */ {
/*    */   private String _value_;
/* 12 */   private static HashMap _table_ = new HashMap();
/*    */   public static final String _DEBIT = "DEBIT";
/*    */   public static final String _CREDIT = "CREDIT";
/*    */   public static final String _CANCEL = "CANCEL";
/*    */   public static final String _PARTCANCEL = "PARTCANCEL";
/* 24 */   public static final TransactionManagerPaymentTransactionType DEBIT = new TransactionManagerPaymentTransactionType("DEBIT");
/* 25 */   public static final TransactionManagerPaymentTransactionType CREDIT = new TransactionManagerPaymentTransactionType("CREDIT");
/* 26 */   public static final TransactionManagerPaymentTransactionType CANCEL = new TransactionManagerPaymentTransactionType("CANCEL");
/* 27 */   public static final TransactionManagerPaymentTransactionType PARTCANCEL = new TransactionManagerPaymentTransactionType("PARTCANCEL");
/*    */ 
/* 61 */   private static TypeDesc typeDesc = new TypeDesc(TransactionManagerPaymentTransactionType.class);
/*    */ 
/*    */   protected TransactionManagerPaymentTransactionType(String value)
/*    */   {
/* 16 */     this._value_ = value;
/* 17 */     _table_.put(this._value_, this);
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 28 */     return this._value_;
/*    */   }
/*    */   public static TransactionManagerPaymentTransactionType fromValue(String value) throws IllegalArgumentException {
/* 31 */     TransactionManagerPaymentTransactionType enumeration = (TransactionManagerPaymentTransactionType)_table_.get(value);
/*    */ 
/* 33 */     if (enumeration == null) throw new IllegalArgumentException();
/* 34 */     return enumeration;
/*    */   }
/*    */ 
/*    */   public static TransactionManagerPaymentTransactionType fromString(String value) throws IllegalArgumentException {
/* 38 */     return fromValue(value);
/*    */   }
/* 40 */   public boolean equals(Object obj) { return obj == this; } 
/* 41 */   public int hashCode() { return toString().hashCode(); } 
/* 42 */   public String toString() { return this._value_; } 
/* 43 */   public Object readResolve() throws ObjectStreamException { return fromValue(this._value_);
/*    */   }
/*    */ 
/*    */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/*    */   {
/* 48 */     return new EnumSerializer(_javaType, _xmlType);
/*    */   }
/*    */ 
/*    */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/*    */   {
/* 56 */     return new EnumDeserializer(_javaType, _xmlType);
/*    */   }
/*    */ 
/*    */   public static TypeDesc getTypeDesc()
/*    */   {
/* 71 */     return typeDesc;
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 65 */     typeDesc.setXmlType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.TransactionType"));
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerPaymentTransactionType
 * JD-Core Version:    0.6.0
 */