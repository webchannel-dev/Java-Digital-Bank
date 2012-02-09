/*    */ package com.bright.assetbank.application.exception;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ 
/*    */ public class PluginInstantiationException extends Bn2Exception
/*    */ {
/*    */   private static final long serialVersionUID = 6949403382818716907L;
/*    */ 
/*    */   public PluginInstantiationException(String a_sMessage)
/*    */   {
/* 33 */     super(a_sMessage);
/*    */   }
/*    */ 
/*    */   public PluginInstantiationException(String a_sMessage, Exception e)
/*    */   {
/* 38 */     super(a_sMessage, e);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.exception.PluginInstantiationException
 * JD-Core Version:    0.6.0
 */