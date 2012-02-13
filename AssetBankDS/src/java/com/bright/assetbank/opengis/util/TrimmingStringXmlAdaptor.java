/*    */ package com.bright.assetbank.opengis.util;
/*    */ 
/*    */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*    */ 
/*    */ public class TrimmingStringXmlAdaptor extends XmlAdapter<String, String>
/*    */ {
/*    */   public String marshal(String a_sValue)
/*    */     throws Exception
/*    */   {
/* 32 */     return a_sValue.trim();
/*    */   }
/*    */ 
/*    */   public String unmarshal(String a_sValue)
/*    */     throws Exception
/*    */   {
/* 38 */     return a_sValue.trim();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.opengis.util.TrimmingStringXmlAdaptor
 * JD-Core Version:    0.6.0
 */