/*    */ package com.bright.assetbank.attribute.bean;
/*    */ 
/*    */ import com.bright.assetbank.attribute.constant.AttributeStorageType;
/*    */ 
/*    */ public class AttributeType
/*    */ {
/* 29 */   private long m_lId = 0L;
/* 30 */   private String m_sName = null;
/*    */   private AttributeStorageType m_attributeStorageType;
/*    */ 
/*    */   public void setId(long a_lId)
/*    */   {
/* 35 */     this.m_lId = a_lId;
/*    */   }
/*    */ 
/*    */   public long getId()
/*    */   {
/* 40 */     return this.m_lId;
/*    */   }
/*    */ 
/*    */   public void setName(String a_sName)
/*    */   {
/* 45 */     this.m_sName = a_sName;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 50 */     return this.m_sName;
/*    */   }
/*    */ 
/*    */   public AttributeStorageType getAttributeStorageType()
/*    */   {
/* 55 */     return this.m_attributeStorageType;
/*    */   }
/*    */ 
/*    */   public void setAttributeStorageType(AttributeStorageType a_attributeStorageType)
/*    */   {
/* 60 */     this.m_attributeStorageType = a_attributeStorageType;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.bean.AttributeType
 * JD-Core Version:    0.6.0
 */