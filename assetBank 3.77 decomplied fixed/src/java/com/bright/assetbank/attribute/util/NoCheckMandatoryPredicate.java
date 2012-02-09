/*    */ package com.bright.assetbank.attribute.util;
/*    */ 
/*    */ import com.bright.assetbank.attribute.bean.Attribute;
/*    */ 
/*    */ public class NoCheckMandatoryPredicate
/*    */   implements MandatoryPredicate
/*    */ {
/*    */   public boolean isMandatory(Attribute a_attribute)
/*    */   {
/* 30 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.util.NoCheckMandatoryPredicate
 * JD-Core Version:    0.6.0
 */