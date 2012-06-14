/*    */ package com.bright.framework.simplelist.util;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import com.bright.framework.simplelist.bean.ListItem;
/*    */ import com.bright.framework.simplelist.service.ListManager;
/*    */ import com.bright.framework.util.StringReplaceParser;
/*    */ 
/*    */ public class ItemBodyReplaceParser extends StringReplaceParser
/*    */ {
/*    */   private DBTransaction m_transaction;
/* 27 */   private ListManager m_listManager = null;
/* 28 */   private Language m_language = null;
/*    */ 
/*    */   public ItemBodyReplaceParser(DBTransaction a_transaction, ListManager a_listManager, Language a_language)
/*    */   {
/* 37 */     this.m_transaction = a_transaction;
/* 38 */     this.m_listManager = a_listManager;
/* 39 */     this.m_language = a_language;
/*    */   }
/*    */ 
/*    */   protected String getReplacement(String a_varName)
/*    */     throws Bn2Exception
/*    */   {
/* 49 */     String sReplacement = null;
/*    */ 
/* 52 */     ListItem item = this.m_listManager.getListItem(this.m_transaction, a_varName, this.m_language);
/*    */ 
/* 54 */     if (item != null)
/*    */     {
/* 56 */       sReplacement = item.getBody();
/*    */     }
/*    */ 
/* 59 */     return sReplacement;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.simplelist.util.ItemBodyReplaceParser
 * JD-Core Version:    0.6.0
 */