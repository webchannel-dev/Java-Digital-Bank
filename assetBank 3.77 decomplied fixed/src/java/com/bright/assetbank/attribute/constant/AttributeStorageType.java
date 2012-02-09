/*    */ package com.bright.assetbank.attribute.constant;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public enum AttributeStorageType
/*    */ {
/* 28 */   VALUE_PER_ASSET(1L), 
/* 29 */   LIST(2L), 
/* 30 */   CATEGORIES(3L), 
/* 31 */   NOT_STORED(100L);
/*    */ 
/*    */   private static final Map<Long, AttributeStorageType> c_lookup;
/*    */   private long m_lId;
/*    */ 
/*    */   private AttributeStorageType(long a_lId)
/*    */   {
/* 48 */     this.m_lId = a_lId;
/*    */   }
/*    */ 
/*    */   public long getId()
/*    */   {
/* 57 */     return this.m_lId;
/*    */   }
/*    */ 
/*    */   public static AttributeStorageType get(long a_lId)
/*    */   {
/* 65 */     AttributeStorageType attributeStorageType = (AttributeStorageType)c_lookup.get(Long.valueOf(a_lId));
/* 66 */     if (attributeStorageType == null)
/*    */     {
/* 68 */       throw new IllegalArgumentException("There is no AttributeStorageType with ID " + a_lId);
/*    */     }
/* 70 */     return attributeStorageType;
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 33 */     c_lookup = new HashMap();
/*    */ 
/* 38 */     for (AttributeStorageType t : values())
/*    */     {
/* 40 */       c_lookup.put(Long.valueOf(t.getId()), t);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.constant.AttributeStorageType
 * JD-Core Version:    0.6.0
 */