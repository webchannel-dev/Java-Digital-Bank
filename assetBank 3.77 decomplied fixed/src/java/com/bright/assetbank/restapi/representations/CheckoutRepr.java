/*    */ package com.bright.assetbank.restapi.representations;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ 
/*    */ @XmlRootElement
/*    */ public class CheckoutRepr
/*    */ {
/*    */   public long userId;
/*    */ 
/*    */   public CheckoutRepr()
/*    */   {
/* 25 */     this.userId = -1L;
/*    */   }
/*    */ 
/*    */   public CheckoutRepr(long a_userId) {
/* 29 */     this.userId = a_userId;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.restapi.representations.CheckoutRepr
 * JD-Core Version:    0.6.0
 */