/*    */ package com.bright.framework.util;
/*    */ 
/*    */ import javax.servlet.jsp.tagext.TagData;
/*    */ import javax.servlet.jsp.tagext.VariableInfo;
/*    */ 
/*    */ public class TEIUtil
/*    */ {
/*    */   public static VariableInfo[] createVariableInfoForAttribute(TagData a_tagData, String a_attributeName, String a_className, int a_scope)
/*    */   {
/* 48 */     Object jspVariableId = a_tagData.getAttribute(a_attributeName);
/*    */ 
/* 56 */     if ((jspVariableId == null) || (jspVariableId == TagData.REQUEST_TIME_VALUE))
/*    */     {
/* 59 */       return null;
/*    */     }
/*    */ 
/* 63 */     return new VariableInfo[] { new VariableInfo(jspVariableId.toString(), a_className, true, a_scope) };
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.TEIUtil
 * JD-Core Version:    0.6.0
 */