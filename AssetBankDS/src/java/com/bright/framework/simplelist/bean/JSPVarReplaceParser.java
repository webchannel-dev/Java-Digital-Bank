/*    */ package com.bright.framework.simplelist.bean;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.util.StringReplaceParser;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ 
/*    */ public class JSPVarReplaceParser extends StringReplaceParser
/*    */ {
/*    */   public static final char k_cJSPVariableStartToken = '?';
/*    */   public static final char k_cJSPVariableEndToken = '?';
/* 26 */   private PageContext m_pageContext = null;
/*    */ 
/*    */   public JSPVarReplaceParser(PageContext a_pageContext)
/*    */   {
/* 35 */     super('?', '?');
/* 36 */     this.m_pageContext = a_pageContext;
/*    */   }
/*    */ 
/*    */   public JSPVarReplaceParser(PageContext a_pageContext, char a_cVariableStartToken, char a_cVariableEndToken)
/*    */   {
/* 49 */     super(a_cVariableStartToken, a_cVariableEndToken);
/* 50 */     this.m_pageContext = a_pageContext;
/*    */   }
/*    */ 
/*    */   protected String getReplacement(String a_sVarName)
/*    */     throws Bn2Exception
/*    */   {
/* 60 */     String sReplacement = null;
/*    */ 
/* 62 */     Object oValue = this.m_pageContext.findAttribute(a_sVarName);
/*    */ 
/* 64 */     if (oValue != null)
/*    */     {
/* 66 */       sReplacement = formatValue(oValue);
/*    */     }
/*    */ 
/* 69 */     return sReplacement;
/*    */   }
/*    */ 
/*    */   protected static String formatValue(Object a_value)
/*    */   {
/* 75 */     if ((a_value instanceof String))
/*    */     {
/* 77 */       return (String)a_value;
/*    */     }
/*    */ 
/* 81 */     return a_value.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.simplelist.bean.JSPVarReplaceParser
 * JD-Core Version:    0.6.0
 */