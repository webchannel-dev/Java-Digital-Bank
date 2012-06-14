/*    */ package com.bright.assetbank.subscription.bean;
/*    */ 
/*    */ import com.bright.framework.database.bean.DataBean;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ 
/*    */ public abstract class BaseSubscriptionModel extends DataBean
/*    */ {
/*    */   protected Language m_language;
/* 30 */   private String m_sDescription = null;
/*    */ 
/*    */   public Language getLanguage()
/*    */   {
/* 34 */     return this.m_language;
/*    */   }
/*    */ 
/*    */   public void setLanguage(Language language)
/*    */   {
/* 39 */     this.m_language = language;
/*    */   }
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 44 */     return this.m_sDescription;
/*    */   }
/*    */ 
/*    */   public void setDescription(String a_sDescription)
/*    */   {
/* 49 */     this.m_sDescription = a_sDescription;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.bean.BaseSubscriptionModel
 * JD-Core Version:    0.6.0
 */