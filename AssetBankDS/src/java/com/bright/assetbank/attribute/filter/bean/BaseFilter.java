/*    */ package com.bright.assetbank.attribute.filter.bean;
/*    */ 
/*    */ import com.bright.assetbank.user.bean.GroupExcludable;
/*    */ import com.bright.framework.database.bean.DataBean;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ 
/*    */ public abstract class BaseFilter extends DataBean
/*    */   implements GroupExcludable
/*    */ {
/* 31 */   protected Language m_language = null;
/* 32 */   private String m_sName = null;
/* 33 */   private int m_iType = 0;
/*    */ 
/*    */   public Language getLanguage()
/*    */   {
/* 37 */     return this.m_language;
/*    */   }
/*    */ 
/*    */   public void setLanguage(Language language)
/*    */   {
/* 42 */     this.m_language = language;
/*    */   }
/*    */ 
/*    */   public void setName(String a_sName)
/*    */   {
/* 47 */     this.m_sName = a_sName;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 52 */     return this.m_sName;
/*    */   }
/*    */ 
/*    */   public void setType(int a_iType)
/*    */   {
/* 57 */     this.m_iType = a_iType;
/*    */   }
/*    */ 
/*    */   public int getType()
/*    */   {
/* 62 */     return this.m_iType;
/*    */   }
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 67 */     return getName();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.filter.bean.BaseFilter
 * JD-Core Version:    0.6.0
 */