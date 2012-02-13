/*    */ package com.bright.assetbank.plugin.taglib;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ 
/*    */ public abstract class IncludeExtensionsTag extends PluginTag
/*    */ {
/*    */   private String m_extensibleEntity;
/*    */ 
/*    */   public int doStartTag()
/*    */     throws JspException
/*    */   {
/* 31 */     List<String> includes = getIncludes();
/*    */ 
/* 33 */     for (String include : includes)
/*    */     {
/*    */       try
/*    */       {
/* 37 */         this.pageContext.include(include);
/*    */       }
/*    */       catch (ServletException e)
/*    */       {
/* 41 */         throw new JspException(e);
/*    */       }
/*    */       catch (IOException e)
/*    */       {
/* 45 */         throw new JspException(e);
/*    */       }
/*    */     }
/*    */ 
/* 49 */     return 0;
/*    */   }
/*    */ 
/*    */   protected abstract List<String> getIncludes()
/*    */     throws JspException;
/*    */ 
/*    */   public String getExtensibleEntity()
/*    */   {
/* 59 */     return this.m_extensibleEntity;
/*    */   }
/*    */ 
/*    */   public void setExtensibleEntity(String a_extensibleEntity)
/*    */   {
/* 67 */     this.m_extensibleEntity = a_extensibleEntity;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.taglib.IncludeExtensionsTag
 * JD-Core Version:    0.6.0
 */