/*    */ package com.bright.framework.mail.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.mail.service.EmailTemplateManager;
/*    */ 
/*    */ public class ShowEmailTemplateAction extends RunEmailTemplateTaskAction
/*    */ {
/*    */   protected void runTask(DBTransaction a_dbTransaction, String a_sTextId, long a_lTypeId)
/*    */     throws Bn2Exception
/*    */   {
/* 33 */     this.m_emailTemplateManager.showEmailTemplate(a_dbTransaction, a_lTypeId, a_sTextId);
/*    */   }
/*    */ 
/*    */   protected boolean validate(String a_sTextId, long a_lTypeId)
/*    */   {
/* 38 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.action.ShowEmailTemplateAction
 * JD-Core Version:    0.6.0
 */