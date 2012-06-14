/*     */ package com.bright.framework.common.taglib;
/*     */ 
/*     */ import com.bn2web.common.constant.GlobalSettings;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ 
/*     */ public class ApplicationSettingTag extends BodyTagSupport
/*     */ {
/*  33 */   protected String m_sId = null;
/*  34 */   protected String m_sSettingName = null;
/*     */ 
/*     */   public String getId()
/*     */   {
/*  39 */     return this.m_sId;
/*     */   }
/*     */ 
/*     */   public void setId(String a_sId)
/*     */   {
/*  44 */     this.m_sId = a_sId;
/*     */   }
/*     */ 
/*     */   public String getSettingName()
/*     */   {
/*  49 */     return this.m_sSettingName;
/*     */   }
/*     */ 
/*     */   public void setSettingName(String a_sSettingName)
/*     */   {
/*  54 */     this.m_sSettingName = a_sSettingName;
/*     */   }
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/*  72 */     return 2;
/*     */   }
/*     */ 
/*     */   public int doAfterBody()
/*     */     throws JspException
/*     */   {
/*  89 */     return 0;
/*     */   }
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/* 107 */     String sValue = GlobalSettings.getInstance().getStringSetting(this.m_sSettingName);
/*     */ 
/* 109 */     this.pageContext.setAttribute(this.m_sId, sValue, 1);
/*     */ 
/* 112 */     return 6;
/*     */   }
/*     */ 
/*     */   public void release()
/*     */   {
/* 121 */     super.release();
/* 122 */     this.m_sId = null;
/* 123 */     this.m_sSettingName = null;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.taglib.ApplicationSettingTag
 * JD-Core Version:    0.6.0
 */