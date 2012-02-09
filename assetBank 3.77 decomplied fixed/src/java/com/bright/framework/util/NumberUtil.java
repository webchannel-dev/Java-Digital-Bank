/*    */ package com.bright.framework.util;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import java.util.Random;
/*    */ 
/*    */ public class NumberUtil
/*    */ {
/*    */   public static int getRandomInt(int a_iStart, int a_iEnd)
/*    */     throws Bn2Exception
/*    */   {
/* 49 */     if (a_iStart > a_iEnd)
/*    */     {
/* 51 */       throw new Bn2Exception("NumberUtil.getRandomInt: Start cannot exceed End.");
/*    */     }
/*    */ 
/* 55 */     int iDiff = a_iEnd - a_iStart;
/*    */ 
/* 58 */     Random random = new Random();
/*    */ 
/* 61 */     int iFraction = (int)(iDiff * random.nextDouble());
/* 62 */     int iRandom = iFraction + a_iStart;
/*    */ 
/* 64 */     return iRandom;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.NumberUtil
 * JD-Core Version:    0.6.0
 */