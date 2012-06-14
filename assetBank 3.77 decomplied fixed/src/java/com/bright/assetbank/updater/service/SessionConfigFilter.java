/*    */ package com.bright.assetbank.updater.service;
/*    */ 
/*    */ import org.jdom.Element;
/*    */ import org.jdom.filter.ElementFilter;
/*    */ 
/*    */ public class SessionConfigFilter extends ElementFilter
/*    */ {
/*    */   public boolean matches(Object a_obj)
/*    */   {
/* 36 */     return (super.matches(a_obj)) && (((Element)a_obj).getName().equals("session-config"));
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.service.SessionConfigFilter
 * JD-Core Version:    0.6.0
 */