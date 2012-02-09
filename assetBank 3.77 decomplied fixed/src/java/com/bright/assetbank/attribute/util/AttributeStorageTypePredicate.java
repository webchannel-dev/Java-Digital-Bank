/*    */ package com.bright.assetbank.attribute.util;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.attribute.bean.Attribute;
/*    */ import com.bright.assetbank.attribute.bean.AttributeType;
/*    */ import com.bright.assetbank.attribute.constant.AttributeStorageType;
/*    */ import com.bright.assetbank.attribute.service.AttributeManager;
/*    */ import com.bright.framework.util.ListSearchUnaryPredicateAdapter;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class AttributeStorageTypePredicate extends ListSearchUnaryPredicateAdapter<Attribute>
/*    */ {
/*    */   private AttributeManager m_attributeManager;
/*    */   private AttributeStorageType m_desiredStorageType;
/*    */   private Set<Long> m_allowedAttributeIds;
/*    */ 
/*    */   public AttributeStorageTypePredicate(AttributeManager a_attributeManager, AttributeStorageType a_desiredStorageType, Set<Long> a_allowedAttributeIds)
/*    */   {
/* 46 */     this.m_attributeManager = a_attributeManager;
/* 47 */     this.m_allowedAttributeIds = a_allowedAttributeIds;
/* 48 */     this.m_desiredStorageType = a_desiredStorageType;
/*    */   }
/*    */ 
/*    */   public AttributeStorageTypePredicate(AttributeManager a_attributeManager, AttributeStorageType a_desiredStorageType)
/*    */   {
/* 56 */     this(a_attributeManager, a_desiredStorageType, null);
/*    */   }
/*    */ 
/*    */   public boolean test(Attribute a_attribute)
/*    */   {
/* 64 */     if (a_attribute.getStatic())
/*    */     {
/* 66 */       return false;
/*    */     }
/*    */ 
/* 70 */     if ((this.m_allowedAttributeIds != null) && (!this.m_allowedAttributeIds.contains(Long.valueOf(a_attribute.getId()))))
/*    */     {
/* 73 */       return false;
/*    */     }
/*    */ 
/*    */     AttributeType attributeType;
/*    */     try
/*    */     {
/* 80 */       attributeType = this.m_attributeManager.getAttributeTypeById(a_attribute.getTypeId());
/*    */     }
/*    */     catch (Bn2Exception e)
/*    */     {
/* 84 */       throw new RuntimeException(e);
/*    */     }
/* 86 */     return attributeType.getAttributeStorageType() == this.m_desiredStorageType;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.util.AttributeStorageTypePredicate
 * JD-Core Version:    0.6.0
 */