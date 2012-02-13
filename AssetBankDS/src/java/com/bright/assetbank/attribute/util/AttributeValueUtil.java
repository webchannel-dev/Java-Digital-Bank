/*    */ package com.bright.assetbank.attribute.util;
/*    */ 
/*    */ import com.bright.assetbank.attribute.bean.Attribute;
/*    */ import com.bright.framework.common.bean.BrightDate;
/*    */ import com.bright.framework.common.bean.BrightDateTime;
/*    */ import java.util.GregorianCalendar;
/*    */ 
/*    */ public class AttributeValueUtil
/*    */ {
/*    */   public static String substituteCalculatedDate(Attribute a_attribute, String a_sValue)
/*    */   {
/* 40 */     if (a_sValue == null)
/*    */     {
/* 42 */       return null;
/*    */     }
/*    */ 
/* 46 */     if (((a_attribute.getIsDatepicker()) || (a_attribute.getIsDateTime())) && (a_sValue.contains("now")))
/*    */     {
/* 51 */       GregorianCalendar today = new GregorianCalendar();
/*    */       try
/*    */       {
/* 56 */         if (a_sValue.contains("+"))
/*    */         {
/* 59 */           a_sValue = a_sValue.replace("now+", "");
/* 60 */           today.add(5, new Integer(a_sValue).intValue());
/*    */         }
/* 62 */         else if (a_sValue.contains("-"))
/*    */         {
/* 65 */           a_sValue = a_sValue.replace("now-", "");
/* 66 */           today.add(5, -new Integer(a_sValue).intValue());
/*    */         }
/*    */ 
/*    */       }
/*    */       catch (NumberFormatException nfe)
/*    */       {
/*    */       }
/*    */ 
/* 74 */       if (a_attribute.getIsDatepicker())
/*    */       {
/* 76 */         a_sValue = new BrightDate(today.getTime()).getFormDate();
/*    */       }
/*    */       else
/*    */       {
/* 80 */         a_sValue = new BrightDateTime(today.getTime()).getFormDateTime();
/*    */       }
/*    */     }
/* 83 */     return a_sValue;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.util.AttributeValueUtil
 * JD-Core Version:    0.6.0
 */