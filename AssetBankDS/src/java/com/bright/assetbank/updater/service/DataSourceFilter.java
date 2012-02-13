/*    */ package com.bright.assetbank.updater.service;
/*    */ 
/*    */ import org.jdom.Element;
/*    */ import org.jdom.filter.ElementFilter;
/*    */ 
/*    */ public class DataSourceFilter extends ElementFilter
/*    */ {
/*    */   public boolean matches(Object a_obj)
/*    */   {
/* 41 */     return (super.matches(a_obj)) && (((Element)a_obj).getName().equals("component")) && (((Element)a_obj).getAttributeValue("role").equals("JdbcDataSource"));
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.service.DataSourceFilter
 * JD-Core Version:    0.6.0
 */