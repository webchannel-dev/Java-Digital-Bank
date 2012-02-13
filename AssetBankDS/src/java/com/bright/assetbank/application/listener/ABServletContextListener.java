/*    */ package com.bright.assetbank.application.listener;
/*    */ 
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import java.beans.Introspector;
/*    */ import javax.servlet.ServletContextEvent;
/*    */ import javax.servlet.ServletContextListener;
/*    */ import org.apache.axis.utils.XMLUtils;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class ABServletContextListener
/*    */   implements ServletContextListener
/*    */ {
/* 37 */   private static String c_ksClassName = "ABServletContextListener";
/*    */ 
/* 39 */   private Log m_logger = null;
/*    */ 
/*    */   public void contextInitialized(ServletContextEvent sce)
/*    */   {
/* 45 */     System.setProperty("java.awt.headless", "true");
/*    */   }
/*    */ 
/*    */   public void contextDestroyed(ServletContextEvent sce)
/*    */   {
/* 54 */     Log logger = getLogger();
/* 55 */     if (logger != null)
/*    */     {
/* 57 */       logger.debug(c_ksClassName + " : flushing Introspector caches and Axis ThreadLocals");
/*    */     }
/* 59 */     Introspector.flushCaches();
/*    */ 
/* 62 */    // XMLUtils.flushThreadLocals();
/*    */   }
/*    */ 
/*    */   protected Log getLogger()
/*    */   {
/* 70 */     if (this.m_logger == null)
/*    */     {
/* 72 */       GlobalApplication globalApplication = GlobalApplication.getInstance();
/* 73 */       if (globalApplication.isInitialised())
/*    */       {
/* 75 */         this.m_logger = globalApplication.getLogger();
/*    */       }
/*    */     }
/* 78 */     return this.m_logger;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.listener.ABServletContextListener
 * JD-Core Version:    0.6.0
 */