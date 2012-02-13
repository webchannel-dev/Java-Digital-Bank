/*     */ package com.bright.framework.struts.taglib.html;
/*     */ 
/*     */ import javax.servlet.jsp.JspException;
/*     */ import org.apache.struts.taglib.TagUtils;
/*     */ 
/*     */ public class FormTag extends org.apache.struts.taglib.html.FormTag
/*     */ {
/*     */   private static final String c_ksIdPrefix = "STRUTSFORM_";
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/*  37 */     if ((isXhtml()) && (getFocus() != null) && (getStyleId() == null))
/*     */     {
/*  40 */       lookup();
/*  41 */       setStyleId("STRUTSFORM_" + getBeanName());
/*     */     }
/*  43 */     return super.doStartTag();
/*     */   }
/*     */ 
/*     */   protected String renderFocusJavascript()
/*     */   {
/*  50 */     if (!isXhtml())
/*     */     {
/*  52 */       return super.renderFocusJavascript();
/*     */     }
/*     */ 
/*  55 */     StringBuffer results = new StringBuffer();
/*     */ 
/*  57 */     results.append(lineEnd);
/*  58 */     results.append("<script type=\"text/javascript\">");
/*  59 */     results.append(lineEnd);
/*  60 */     results.append("/* <![CDATA[ */");
/*  61 */     results.append(lineEnd);
/*     */ 
/*  65 */     StringBuffer focusControl = new StringBuffer("$('");
/*  66 */     focusControl.append(getStyleId());
/*  67 */     focusControl.append("').elements[\"");
/*  68 */     focusControl.append(this.focus);
/*  69 */     focusControl.append("\"]");
/*     */ 
/*  71 */     results.append("  var focusControl = ");
/*  72 */     results.append(focusControl.toString());
/*  73 */     results.append(";");
/*  74 */     results.append(lineEnd);
/*  75 */     results.append(lineEnd);
/*     */ 
/*  77 */     results.append("  if (focusControl.type != \"hidden\" && !focusControl.disabled) {");
/*  78 */     results.append(lineEnd);
/*     */ 
/*  81 */     String index = "";
/*  82 */     if (this.focusIndex != null) {
/*  83 */       StringBuffer sb = new StringBuffer("[");
/*  84 */       sb.append(this.focusIndex);
/*  85 */       sb.append("]");
/*  86 */       index = sb.toString();
/*     */     }
/*  88 */     results.append("     focusControl");
/*  89 */     results.append(index);
/*  90 */     results.append(".focus();");
/*  91 */     results.append(lineEnd);
/*     */ 
/*  93 */     results.append("  }");
/*  94 */     results.append(lineEnd);
/*     */ 
/*  96 */     results.append("/* ]]> */");
/*  97 */     results.append(lineEnd);
/*  98 */     results.append("</script>");
/*  99 */     results.append(lineEnd);
/* 100 */     return results.toString();
/*     */   }
/*     */ 
/*     */   protected boolean isXhtml()
/*     */   {
/* 107 */     return TagUtils.getInstance().isXhtml(this.pageContext);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.struts.taglib.html.FormTag
 * JD-Core Version:    0.6.0
 */