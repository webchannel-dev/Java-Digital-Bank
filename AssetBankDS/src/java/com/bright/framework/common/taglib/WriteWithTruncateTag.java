/*     */ package com.bright.framework.common.taglib;
/*     */ 
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.taglib.bean.WriteTag;
/*     */ 
/*     */ public class WriteWithTruncateTag extends WriteTag
/*     */ {
/*  38 */   private int m_iMaxLength = 0;
/*  39 */   private String m_sEndString = null;
/*  40 */   private String m_sMaxLengthBean = null;
/*     */ 
/*     */   protected String formatValue(Object a_valueToFormat)
/*     */     throws JspException
/*     */   {
/*  60 */     String sValue = super.formatValue(a_valueToFormat);
/*     */ 
/*  63 */     int iMaxLength = this.m_iMaxLength;
/*     */ 
/*  65 */     if (iMaxLength <= 0)
/*     */     {
/*  68 */       String intMaxLength = (String)this.pageContext.getAttribute(this.m_sMaxLengthBean, 1);
/*     */ 
/*  70 */       if (StringUtils.isNotEmpty(intMaxLength))
/*     */       {
/*  72 */         iMaxLength = Integer.parseInt(intMaxLength);
/*     */       }
/*     */       else
/*     */       {
/*  76 */         iMaxLength = 2147483647;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  81 */     if ((sValue != null) && (sValue.length() > iMaxLength))
/*     */     {
/*  84 */       int iPos = iMaxLength;
/*     */ 
/*  86 */       while ((iPos > -1) && (sValue.charAt(iPos) != ' '))
/*     */       {
/*  88 */         iPos--;
/*     */       }
/*     */ 
/*  91 */       if (iPos > -1)
/*     */       {
/*  93 */         sValue = sValue.substring(0, iPos) + this.m_sEndString;
/*     */       }
/*     */       else
/*     */       {
/*  97 */         sValue = sValue.substring(0, iMaxLength) + this.m_sEndString;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 102 */     return sValue;
/*     */   }
/*     */ 
/*     */   public void release()
/*     */   {
/* 111 */     super.release();
/* 112 */     this.m_iMaxLength = 0;
/* 113 */     this.m_sEndString = null;
/*     */   }
/*     */ 
/*     */   public int getMaxLength()
/*     */   {
/* 119 */     return this.m_iMaxLength;
/*     */   }
/*     */ 
/*     */   public void setMaxLength(int a_iMaxLength) {
/* 123 */     this.m_iMaxLength = a_iMaxLength;
/*     */   }
/*     */ 
/*     */   public String getEndString() {
/* 127 */     return this.m_sEndString;
/*     */   }
/*     */ 
/*     */   public void setEndString(String a_sEndString) {
/* 131 */     this.m_sEndString = a_sEndString;
/*     */   }
/*     */ 
/*     */   public String getMaxLengthBean() {
/* 135 */     return this.m_sMaxLengthBean;
/*     */   }
/*     */ 
/*     */   public void setMaxLengthBean(String a_sMaxLengthBean) {
/* 139 */     this.m_sMaxLengthBean = a_sMaxLengthBean;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.taglib.WriteWithTruncateTag
 * JD-Core Version:    0.6.0
 */