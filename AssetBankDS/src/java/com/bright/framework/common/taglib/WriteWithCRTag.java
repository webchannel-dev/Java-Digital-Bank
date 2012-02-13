/*    */ package com.bright.framework.common.taglib;
/*    */ 
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import org.apache.struts.taglib.bean.WriteTag;
/*    */ 
/*    */ public class WriteWithCRTag extends WriteTag
/*    */ {
/*    */   protected String formatValue(Object a_valueToFormat)
/*    */     throws JspException
/*    */   {
/* 54 */     String sValue = super.formatValue(a_valueToFormat);
/*    */ 
/* 57 */     sValue = StringUtil.formatNewlineForHTML(sValue);
/*    */ 
/* 59 */     return sValue;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.taglib.WriteWithCRTag
 * JD-Core Version:    0.6.0
 */