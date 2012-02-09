/*    */ package com.bright.framework.user.exception;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ 
/*    */ public class InvalidSaltedHashException extends Bn2Exception
/*    */ {
/*    */   public InvalidSaltedHashException(String a_sSaltedHash)
/*    */   {
/* 32 */     super("\"" + a_sSaltedHash + "\" is not a valid salted hash");
/*    */   }
/*    */ 
/*    */   public InvalidSaltedHashException(String a_sSaltedHash, String a_sReason)
/*    */   {
/* 41 */     super("\"" + a_sSaltedHash + "\" is not a valid salted hash.  Reason: " + a_sReason);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.exception.InvalidSaltedHashException
 * JD-Core Version:    0.6.0
 */