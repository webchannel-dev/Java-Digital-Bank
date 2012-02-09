/*    */ package com.bright.framework.user.exception;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ 
/*    */ public class LoginException extends Bn2Exception
/*    */ {
/*    */   public LoginException(String a_sMessage)
/*    */   {
/* 25 */     super(a_sMessage);
/*    */   }
/*    */ 
/*    */   public LoginException(String a_sMessage, Throwable a_cause)
/*    */   {
/* 30 */     super(a_sMessage, a_cause);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.exception.LoginException
 * JD-Core Version:    0.6.0
 */