/*    */ package com.bright.assetbank.plugin.taglib;
/*    */ 
/*    */ import com.bright.assetbank.plugin.service.PluginManager;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ 
/*    */ public class HaveViewExtensionIncludesTag extends PluginTag
/*    */ {
/*    */   private String m_id;
/*    */   private String m_extensibleEntity;
/*    */   private String m_position;
/*    */ 
/*    */   public int doStartTag()
/*    */     throws JspException
/*    */   {
/* 34 */     ViewExtensionsUtil.checkPopulateViewRequestCalled((HttpServletRequest)this.pageContext.getRequest());
/*    */ 
/* 37 */     List includes = getPluginManager().getIncludesForView(getExtensibleEntity(), getPosition());
/*    */ 
/* 39 */     boolean haveViewExtensions = !includes.isEmpty();
/* 40 */     this.pageContext.setAttribute(this.m_id, Boolean.valueOf(haveViewExtensions), 1);
/*    */ 
/* 43 */     return 0;
/*    */   }
/*    */ 
/*    */   public String getId()
/*    */   {
/* 48 */     return this.m_id;
/*    */   }
/*    */ 
/*    */   public void setId(String a_id)
/*    */   {
/* 56 */     this.m_id = a_id;
/*    */   }
/*    */ 
/*    */   public String getExtensibleEntity()
/*    */   {
/* 61 */     return this.m_extensibleEntity;
/*    */   }
/*    */ 
/*    */   public void setExtensibleEntity(String a_extensibleEntity)
/*    */   {
/* 69 */     this.m_extensibleEntity = a_extensibleEntity;
/*    */   }
/*    */ 
/*    */   public String getPosition()
/*    */   {
/* 74 */     return this.m_position;
/*    */   }
/*    */ 
/*    */   public void setPosition(String a_position)
/*    */   {
/* 84 */     this.m_position = a_position;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.taglib.HaveViewExtensionIncludesTag
 * JD-Core Version:    0.6.0
 */