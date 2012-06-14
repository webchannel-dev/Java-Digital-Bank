/*    */ package com.bright.framework.storage.exception;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ 
/*    */ public class NoAvailableStorageException extends Bn2Exception
/*    */ {
/*    */   public NoAvailableStorageException()
/*    */   {
/* 29 */     this(null);
/*    */   }
/*    */ 
/*    */   public NoAvailableStorageException(Throwable a_cause)
/*    */   {
/* 34 */     super("There is no space available for storing files.", a_cause);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.exception.NoAvailableStorageException
 * JD-Core Version:    0.6.0
 */