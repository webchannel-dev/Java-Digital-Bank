/*    */ package com.bright.assetbank.plugin.taglib;
/*    */ 
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import com.bright.assetbank.plugin.service.PluginManager;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import javax.servlet.jsp.tagext.TagSupport;
/*    */ import org.apache.avalon.framework.component.ComponentException;
/*    */ import org.apache.avalon.framework.component.ComponentManager;
/*    */ 
/*    */ public abstract class PluginTag extends TagSupport
/*    */ {
/*    */   protected PluginManager getPluginManager()
/*    */     throws JspException
/*    */   {
/*    */     try
/*    */     {
/* 32 */       return (PluginManager)GlobalApplication.getInstance().getComponentManager().lookup("PluginManager");
/*    */     }
/*    */     catch (ComponentException e) {
/*    */     
/* 36 */     throw new JspException(e);}
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.taglib.PluginTag
 * JD-Core Version:    0.6.0
 */