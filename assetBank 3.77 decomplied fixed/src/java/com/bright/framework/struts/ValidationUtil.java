/*    */ package com.bright.framework.struts;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import com.bright.framework.simplelist.bean.ListItem;
/*    */ import com.bright.framework.simplelist.service.ListManager;
/*    */ 
/*    */ public class ValidationUtil
/*    */ {
/*    */   public static String displayStringFromException(ListManager a_listManager, Language a_language, ValidationException a_e)
/*    */     throws Bn2Exception
/*    */   {
/* 38 */     return displayStringFromException(null, a_listManager, a_language, a_e);
/*    */   }
/*    */ 
/*    */   public static String displayStringFromException(DBTransaction a_transaction, ListManager a_listManager, Language a_language, ValidationException a_e)
/*    */     throws Bn2Exception
/*    */   {
/* 52 */     String listItemId = a_e.getListItemId();
/* 53 */     String[] fields = a_e.getFields();
/* 54 */     if (fields == null)
/*    */     {
/* 56 */       return a_listManager.getListItem(a_transaction, listItemId, a_language).getBody();
/*    */     }
/*    */ 
/* 63 */     return a_listManager.getListItem(a_transaction, listItemId, a_language, fields);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.struts.ValidationUtil
 * JD-Core Version:    0.6.0
 */