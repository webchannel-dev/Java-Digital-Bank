/*    */ package com.bright.framework.user.bean;
/*    */ 
/*    */ import com.bright.framework.database.bean.DataBean;
/*    */ 
/*    */ public class RemoteUserGroup extends DataBean
/*    */ {
/* 29 */   private String m_sMapping = null;
/* 30 */   private String[] m_asMappingValues = null;
/*    */ 
/*    */   public String getMapping()
/*    */   {
/* 34 */     return this.m_sMapping;
/*    */   }
/*    */ 
/*    */   public void setMapping(String a_sMapping) {
/* 38 */     this.m_sMapping = a_sMapping;
/*    */   }
/*    */ 
/*    */   public String[] getMappingValues(String a_sDelimiter)
/*    */   {
/* 55 */     if (this.m_asMappingValues == null)
/*    */     {
/* 57 */       if ((a_sDelimiter == null) || (a_sDelimiter.length() == 0))
/*    */       {
/* 59 */         this.m_asMappingValues = new String[1];
/* 60 */         this.m_asMappingValues[0] = this.m_sMapping;
/*    */       }
/*    */       else
/*    */       {
/* 64 */         this.m_asMappingValues = this.m_sMapping.split(a_sDelimiter);
/*    */       }
/*    */     }
/*    */ 
/* 68 */     return this.m_asMappingValues;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.bean.RemoteUserGroup
 * JD-Core Version:    0.6.0
 */