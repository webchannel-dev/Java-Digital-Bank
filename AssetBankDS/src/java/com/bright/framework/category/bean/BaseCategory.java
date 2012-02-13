/*    */ package com.bright.framework.category.bean;
/*    */ 
/*    */ public abstract class BaseCategory
/*    */ {
/* 29 */   protected String m_sName = null;
/* 30 */   protected String m_sSummary = null;
/* 31 */   protected String m_sDescription = null;
/*    */ 
/*    */   public BaseCategory()
/*    */   {
/*    */   }
/*    */ 
/*    */   public BaseCategory(BaseCategory a_catToCopy)
/*    */   {
/* 39 */     this.m_sName = a_catToCopy.m_sName;
/* 40 */     this.m_sSummary = a_catToCopy.m_sSummary;
/* 41 */     this.m_sDescription = a_catToCopy.m_sDescription;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 46 */     return this.m_sName;
/*    */   }
/*    */ 
/*    */   public void setName(String a_sName)
/*    */   {
/* 51 */     this.m_sName = a_sName;
/*    */   }
/*    */ 
/*    */   public String getSummary()
/*    */   {
/* 56 */     return this.m_sSummary;
/*    */   }
/*    */ 
/*    */   public void setSummary(String a_sSummary)
/*    */   {
/* 61 */     this.m_sSummary = a_sSummary;
/*    */   }
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 66 */     return this.m_sDescription;
/*    */   }
/*    */ 
/*    */   public void setDescription(String a_sDescription)
/*    */   {
/* 71 */     this.m_sDescription = a_sDescription;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.bean.BaseCategory
 * JD-Core Version:    0.6.0
 */