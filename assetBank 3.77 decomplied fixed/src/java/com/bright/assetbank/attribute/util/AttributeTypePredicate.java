/*    */ package com.bright.assetbank.attribute.util;
/*    */ 
/*    */ import com.bright.assetbank.attribute.bean.Attribute;
/*    */ import com.bright.framework.util.ListSearchUnaryPredicateAdapter;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class AttributeTypePredicate extends ListSearchUnaryPredicateAdapter<Attribute>
/*    */ {
/*    */   private long m_lAttributeTypeId;
/*    */   private Set<Long> m_allowedAttributeIds;
/*    */ 
/*    */   public AttributeTypePredicate(long a_lAttributeTypeId, Set<Long> a_allowedAttributeIds)
/*    */   {
/* 38 */     this.m_lAttributeTypeId = a_lAttributeTypeId;
/* 39 */     this.m_allowedAttributeIds = a_allowedAttributeIds;
/*    */   }
/*    */ 
/*    */   public boolean test(Attribute a_attribute)
/*    */   {
/* 44 */     return (a_attribute.getTypeId() == this.m_lAttributeTypeId) && ((this.m_allowedAttributeIds == null) || (this.m_allowedAttributeIds.contains(Long.valueOf(a_attribute.getId()))));
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.util.AttributeTypePredicate
 * JD-Core Version:    0.6.0
 */