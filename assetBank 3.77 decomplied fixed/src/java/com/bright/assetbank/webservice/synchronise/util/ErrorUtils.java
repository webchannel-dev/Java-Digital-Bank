/*    */ package com.bright.assetbank.webservice.synchronise.util;
/*    */ 
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.impl.Log4JLogger;
/*    */ 
/*    */ public class ErrorUtils
/*    */ {
/*    */   public static RuntimeException getRuntimeException(Object in, Throwable cause, String message)
/*    */   {
/* 64 */     Log log = new Log4JLogger(in.getClass().getName());
/*    */ 
/* 66 */     log.error(message, cause);
/*    */ 
/* 68 */     return new RuntimeException(message, cause);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.webservice.synchronise.util.ErrorUtils
 * JD-Core Version:    0.6.0
 */