/*    */ package com.bright.assetbank.ecommerce.psp;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ 
/*    */ public class PSPPluginFactory
/*    */ {
/*    */   private static final String c_ksClassName = "PSPPluginFactory";
/*    */ 
/*    */   public static PSPPlugin getPSPPluginInstance(String a_sClassName)
/*    */     throws Bn2Exception
/*    */   {
/* 45 */     String ksMethodName = "getPSPPluginInstance";
/*    */ 
/* 47 */     PSPPlugin plugin = null;
/*    */     try
/*    */     {
/* 52 */       Class classPlugin = Class.forName(a_sClassName);
/*    */ 
/* 55 */       plugin = (PSPPlugin)classPlugin.newInstance();
/*    */     }
/*    */     catch (ClassNotFoundException cnf)
/*    */     {
/* 59 */       throw new Bn2Exception("PSPPluginFactory.getPSPPluginInstance: ClassNotFoundException occurred: " + cnf, cnf);
/*    */     }
/*    */     catch (IllegalAccessException iae)
/*    */     {
/* 63 */       throw new Bn2Exception("PSPPluginFactory.getPSPPluginInstance: IllegalAccessException occurred: " + iae, iae);
/*    */     }
/*    */     catch (InstantiationException ie)
/*    */     {
/* 67 */       throw new Bn2Exception("PSPPluginFactory.getPSPPluginInstance: InstantiationException occurred: " + ie, ie);
/*    */     }
/*    */ 
/* 70 */     return plugin;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.psp.PSPPluginFactory
 * JD-Core Version:    0.6.0
 */