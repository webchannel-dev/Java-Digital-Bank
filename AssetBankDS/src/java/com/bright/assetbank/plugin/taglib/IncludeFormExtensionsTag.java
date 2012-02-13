/*    */ package com.bright.assetbank.plugin.taglib;
/*    */ 
/*    */ import com.bright.assetbank.plugin.service.PluginManager;
/*    */ import java.util.List;
/*    */ import javax.servlet.jsp.JspException;
/*    */ 
/*    */ public class IncludeFormExtensionsTag extends IncludeExtensionsTag
/*    */ {
/*    */   private String m_verb;
/*    */   private String m_position;
/*    */ 
/*    */   protected List<String> getIncludes()
/*    */     throws JspException
/*    */   {
/* 33 */     if (this.m_verb == null)
/*    */     {
/* 35 */       throw new JspException("A verb must be specified for this tag using the \"verb\" or \"determineVerbFromId\" attribute");
/*    */     }
/*    */ 
/* 39 */     PluginManager pluginManager = getPluginManager();
/*    */     List includes;
/* 41 */     if (getVerb().equals("add"))
/*    */     {
/* 43 */       includes = pluginManager.getIncludesForAdd(getExtensibleEntity(), getPosition());
/*    */     }
/*    */     else
/*    */     {
/*    */       //List includes;
/* 45 */       if (getVerb().equals("edit"))
/*    */       {
/* 47 */         includes = pluginManager.getIncludesForEditExisting(getExtensibleEntity(), getPosition());
/*    */       }
/*    */       else
/*    */       {
/* 51 */         throw new JspException("Verb must be one of the values in FormVerbConstants, not \"" + getVerb() + "\"");
/*    */       }
/*    */     }
/*    */     //List includes;
/* 53 */     return includes;
/*    */   }
/*    */ 
/*    */   public String getVerb()
/*    */   {
/* 58 */     return this.m_verb;
/*    */   }
/*    */ 
/*    */   public void setVerb(String a_verb)
/*    */   {
/* 67 */     this.m_verb = a_verb;
/*    */   }
/*    */ 
/*    */   public void setDetermineVerbFromId(Long a_id)
/*    */   {
/* 72 */     if (a_id == null) throw new NullPointerException();
/*    */ 
/* 74 */     setVerb(a_id.longValue() <= 0L ? "add" : "edit");
/*    */   }
/*    */ 
/*    */   public String getPosition()
/*    */   {
/* 81 */     return this.m_position;
/*    */   }
/*    */ 
/*    */   public void setPosition(String a_position)
/*    */   {
/* 91 */     this.m_position = a_position;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.taglib.IncludeFormExtensionsTag
 * JD-Core Version:    0.6.0
 */