/*    */ package com.bright.assetbank.plugin.taglib;
/*    */ 
/*    */ import com.bright.assetbank.plugin.service.PluginManager;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ 
/*    */ public class IncludeViewExtensionsTag extends IncludeExtensionsTag
/*    */ {
/*    */   private String m_position;
/*    */ 
/*    */   public int doStartTag()
/*    */     throws JspException
/*    */   {
/* 31 */     ViewExtensionsUtil.checkPopulateViewRequestCalled((HttpServletRequest)this.pageContext.getRequest());
/*    */ 
/* 34 */     return super.doStartTag();
/*    */   }
/*    */ 
/*    */   protected List<String> getIncludes()
/*    */     throws JspException
/*    */   {
/* 40 */     return getPluginManager().getIncludesForView(getExtensibleEntity(), getPosition());
/*    */   }
/*    */ 
/*    */   public String getPosition()
/*    */   {
/* 46 */     return this.m_position;
/*    */   }
/*    */ 
/*    */   public void setPosition(String a_position)
/*    */   {
/* 56 */     this.m_position = a_position;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.taglib.IncludeViewExtensionsTag
 * JD-Core Version:    0.6.0
 */