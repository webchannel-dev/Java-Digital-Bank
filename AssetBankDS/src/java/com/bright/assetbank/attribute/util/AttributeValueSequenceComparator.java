/*    */ package com.bright.assetbank.attribute.util;
/*    */ 
/*    */ import com.bright.assetbank.attribute.bean.Attribute;
/*    */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class AttributeValueSequenceComparator
/*    */   implements Comparator<AttributeValue>
/*    */ {
/* 31 */   private static final AttributeValueSequenceComparator c_kInstance = new AttributeValueSequenceComparator();
/*    */ 
/*    */   public int compare(AttributeValue a_av1, AttributeValue a_av2)
/*    */   {
/* 60 */     int s1 = a_av1.getAttribute().getSequence();
/* 61 */     int s2 = a_av2.getAttribute().getSequence();
/*    */ 
/* 63 */     return s1 - s2;
/*    */   }
/*    */ 
/*    */   public static AttributeValueSequenceComparator getInstance()
/*    */   {
/* 68 */     return c_kInstance;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.util.AttributeValueSequenceComparator
 * JD-Core Version:    0.6.0
 */