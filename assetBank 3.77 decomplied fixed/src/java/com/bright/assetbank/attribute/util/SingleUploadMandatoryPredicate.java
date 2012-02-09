/*    */ package com.bright.assetbank.attribute.util;
/*    */ 
/*    */ import com.bright.assetbank.attribute.bean.Attribute;
/*    */ 
/*    */ public class SingleUploadMandatoryPredicate
/*    */   implements MandatoryPredicate
/*    */ {
/*    */   public boolean isMandatory(Attribute a_attribute)
/*    */   {
/* 29 */     return a_attribute.isMandatory();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.util.SingleUploadMandatoryPredicate
 * JD-Core Version:    0.6.0
 */