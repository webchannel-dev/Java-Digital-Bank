/*    */ package com.bright.assetbank.plugin.taglib;
/*    */ 
/*    */ import com.bright.framework.util.Assert;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class ViewExtensionsUtil
/*    */ {
/*    */   public static void checkPopulateViewRequestCalled(HttpServletRequest a_request)
/*    */   {
/* 34 */     Boolean populateViewRequestCalled = (Boolean)a_request.getAttribute("plugin_populateViewRequestCalled");
/*    */ 
/* 36 */     if ((populateViewRequestCalled == null) || (!populateViewRequestCalled.booleanValue()))
/*    */     {
/* 39 */       Assert.assertionFailed("View extension tag used in a JSP without the corresponding Action having called PluginManager.populateViewRequest(). URL = " + a_request.getRequestURI());
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.taglib.ViewExtensionsUtil
 * JD-Core Version:    0.6.0
 */