/*    */ package com.bright.framework.simplelist.bean;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import com.bright.framework.simplelist.service.ListManager;
/*    */ import com.bright.framework.util.StringReplaceParser;
/*    */ 
/*    */ public class ItemBodyReplaceParser extends StringReplaceParser
/*    */ {
/* 24 */   private ListManager m_listManager = null;
/* 25 */   private Language m_language = null;
/*    */ 
/*    */   public ItemBodyReplaceParser(ListManager a_listManager, Language a_language)
/*    */   {
/* 33 */     this.m_listManager = a_listManager;
/* 34 */     this.m_language = a_language;
/*    */   }
/*    */ 
/*    */   public ItemBodyReplaceParser(ListManager a_listManager, String m_sLanguageCode, char a_cVariableStartToken, char a_cVariableEndToken)
/*    */   {
/* 48 */     super(a_cVariableStartToken, a_cVariableEndToken);
/* 49 */     this.m_listManager = a_listManager;
/*    */   }
/*    */ 
/*    */   protected String getReplacement(String a_varName)
/*    */     throws Bn2Exception
/*    */   {
/* 59 */     String sReplacement = null;
/*    */ 
/* 62 */     ListItem item = this.m_listManager.getListItem(a_varName, this.m_language);
/*    */ 
/* 64 */     if (item != null)
/*    */     {
/* 66 */       sReplacement = item.getBody();
/*    */     }
/*    */ 
/* 69 */     return sReplacement;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.simplelist.bean.ItemBodyReplaceParser
 * JD-Core Version:    0.6.0
 */