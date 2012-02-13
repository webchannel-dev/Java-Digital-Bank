/*    */ package com.bright.framework.user.exception;
/*    */ 
/*    */ public class AuthenticationErrorException extends LoginException
/*    */ {
/*    */   public AuthenticationErrorException(String a_sMessage)
/*    */   {
/* 24 */     super(a_sMessage);
/*    */   }
/*    */ 
/*    */   public AuthenticationErrorException(String a_sMessage, Throwable a_cause)
/*    */   {
/* 29 */     super(a_sMessage, a_cause);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.exception.AuthenticationErrorException
 * JD-Core Version:    0.6.0
 */