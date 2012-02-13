/*    */ package com.bright.assetbank.attribute.plugin;
/*    */ 
/*    */ import com.bright.assetbank.application.exception.PluginInstantiationException;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class DataLookupPluginFactory
/*    */ {
/*    */   private static final String c_ksClassName = "ExternalDataLookupPluginFactory";
/* 32 */   private static HashMap<String, DataLookupPlugin> m_hmPluginInstances = new HashMap();
/*    */ 
/*    */   public static synchronized DataLookupPlugin getPluginInstance(String a_sClassName)
/*    */     throws PluginInstantiationException
/*    */   {
/* 43 */     String ksMethodName = "getPluginInstance";
/* 44 */     DataLookupPlugin plugin = (DataLookupPlugin)m_hmPluginInstances.get(a_sClassName);
/*    */ 
/* 46 */     if (plugin == null)
/*    */     {
/*    */       try
/*    */       {
/* 51 */         Class pluginClass = Class.forName(a_sClassName);
/* 52 */         plugin = (DataLookupPlugin)pluginClass.newInstance();
/*    */ 
/* 54 */         if (plugin == null)
/*    */         {
/* 56 */           throw new PluginInstantiationException("ExternalDataLookupPluginFactory.getPluginInstance: Plugin not found");
/*    */         }
/* 58 */         m_hmPluginInstances.put(a_sClassName, plugin);
/*    */       }
/*    */       catch (Exception e)
/*    */       {
/* 62 */         throw new PluginInstantiationException("ExternalDataLookupPluginFactory.getPluginInstanceException occurred whilst getting plugin " + a_sClassName + ": ", e);
/*    */       }
/*    */     }
/*    */ 
/* 66 */     return plugin;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.plugin.DataLookupPluginFactory
 * JD-Core Version:    0.6.0
 */