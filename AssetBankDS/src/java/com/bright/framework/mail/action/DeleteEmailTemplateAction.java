/*    */ package com.bright.framework.mail.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.mail.service.EmailTemplateManager;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class DeleteEmailTemplateAction extends RunEmailTemplateTaskAction
/*    */ {
/*    */   protected void runTask(DBTransaction a_dbTransaction, String a_sTextId, long a_lTypeId)
/*    */     throws Bn2Exception
/*    */   {
/* 34 */     this.m_emailTemplateManager.deleteEmailTemplate(a_dbTransaction, a_lTypeId, a_sTextId);
/*    */   }
/*    */ 
/*    */   protected boolean validate(String a_sTextId, long a_lTypeId)
/*    */   {
/* 39 */     if (a_lTypeId == 1L)
/*    */     {
/* 41 */       this.m_logger.error("DeleteEmailTemplateAction.execute : Cannot delete general email templates.");
/* 42 */       return false;
/*    */     }
/* 44 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.action.DeleteEmailTemplateAction
 * JD-Core Version:    0.6.0
 */