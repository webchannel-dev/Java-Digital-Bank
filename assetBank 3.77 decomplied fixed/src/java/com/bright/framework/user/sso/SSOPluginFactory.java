/*    */ package com.bright.framework.user.sso;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import org.apache.avalon.framework.component.ComponentManager;
/*    */ 
/*    */ public class SSOPluginFactory
/*    */ {
/*    */   private static final String c_ksClassName = "SSOPluginFactory";
/*    */ 
/*    */   public static SSOPlugin getSSOPluginInstance(String a_sClassName)
/*    */     throws Bn2Exception
/*    */   {
/* 41 */     String ksMethodName = "getSSOPluginInstance";
/*    */ 
/* 43 */     SSOPlugin plugin = null;
/*    */     try
/*    */     {
/* 48 */       plugin = (SSOPlugin)GlobalApplication.getInstance().getComponentManager().lookup(a_sClassName);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 52 */       throw new Bn2Exception("SSOPluginFactory.getSSOPluginInstanceComponent Exception occurred whilst getting plugin " + a_sClassName + ": ", e);
/*    */     }
/*    */ 
/* 55 */     return plugin;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.sso.SSOPluginFactory
 * JD-Core Version:    0.6.0
 */