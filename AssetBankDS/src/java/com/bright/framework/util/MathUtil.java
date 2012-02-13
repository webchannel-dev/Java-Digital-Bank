/*    */ package com.bright.framework.util;
/*    */ 
/*    */ public class MathUtil
/*    */ {
/*    */   public static int roundDoubleToInt(double a_d)
/*    */   {
/* 35 */     return castToNearestInt(Math.round(a_d));
/*    */   }
/*    */ 
/*    */   private static int castToNearestInt(long a_l)
/*    */   {
/* 44 */     if (a_l > 2147483647L)
/*    */     {
/* 46 */       return 2147483647;
/*    */     }
/* 48 */     if (a_l < -2147483648L)
/*    */     {
/* 50 */       return -2147483648;
/*    */     }
/*    */ 
/* 53 */     return (int)a_l;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.MathUtil
 * JD-Core Version:    0.6.0
 */