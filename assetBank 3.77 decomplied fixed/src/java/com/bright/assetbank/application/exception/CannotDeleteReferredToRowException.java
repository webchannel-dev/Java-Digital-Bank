/*    */ package com.bright.assetbank.application.exception;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ 
/*    */ public class CannotDeleteReferredToRowException extends Bn2Exception
/*    */ {
/*    */   public CannotDeleteReferredToRowException(String a_sTableName, long a_lId)
/*    */   {
/* 34 */     super(message(a_sTableName, a_lId));
/*    */   }
/*    */ 
/*    */   public CannotDeleteReferredToRowException(String a_sTableName, long a_lId, Throwable a_thrCause)
/*    */   {
/* 44 */     super(message(a_sTableName, a_lId), a_thrCause);
/*    */   }
/*    */ 
/*    */   private static String message(String a_sTableName, long a_lId)
/*    */   {
/* 50 */     return "Cannot delete row with ID " + a_lId + " from " + a_sTableName + " because it is referred to";
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.exception.CannotDeleteReferredToRowException
 * JD-Core Version:    0.6.0
 */