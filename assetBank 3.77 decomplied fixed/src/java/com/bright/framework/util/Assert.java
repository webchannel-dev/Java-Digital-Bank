/*    */ package com.bright.framework.util;
/*    */ 
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import com.bright.framework.constant.FrameworkSettings;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class Assert
/*    */ {
/*    */   public static void assertionFailed(String a_sMessage)
/*    */   {
/* 46 */     RuntimeException e = new RuntimeException(a_sMessage);
/* 47 */     if (FrameworkSettings.getThrowExceptionOnFailedAssertion())
/*    */     {
/* 49 */       throw e;
/*    */     }
/*    */ 
/* 55 */     GlobalApplication.getInstance().getLogger().error("", e);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.Assert
 * JD-Core Version:    0.6.0
 */