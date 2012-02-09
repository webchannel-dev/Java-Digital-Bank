/*    */ package com.bright.framework.language.action;
/*    */ 
/*    */ import com.bright.assetbank.language.service.LanguageManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.simplelist.service.ListManager;
/*    */ 
/*    */ public abstract class LanguageAction extends BTransactionAction
/*    */ {
/* 23 */   private LanguageManager m_languageManager = null;
/*    */ 
/* 35 */   protected ListManager m_listManager = null;
/*    */ 
/*    */   public LanguageManager getLanguageManager()
/*    */   {
/* 27 */     return this.m_languageManager;
/*    */   }
/*    */ 
/*    */   public void setLanguageManager(LanguageManager marketingGroupManager)
/*    */   {
/* 32 */     this.m_languageManager = marketingGroupManager;
/*    */   }
/*    */ 
/*    */   public void setListManager(ListManager listManager)
/*    */   {
/* 38 */     this.m_listManager = listManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.language.action.LanguageAction
 * JD-Core Version:    0.6.0
 */