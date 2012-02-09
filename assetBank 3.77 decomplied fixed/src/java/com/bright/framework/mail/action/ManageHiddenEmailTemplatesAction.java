/*    */ package com.bright.framework.mail.action;
/*    */ 
/*    */ import com.bright.framework.mail.form.EmailTemplateForm;
/*    */ 
/*    */ public class ManageHiddenEmailTemplatesAction extends ManageEmailTemplateAction
/*    */ {
/*    */   protected int getIsHidden(EmailTemplateForm a_form)
/*    */   {
/* 32 */     int iTemplatesToGet = 0;
/* 33 */     a_form.setHidden(true);
/*    */ 
/* 35 */     return iTemplatesToGet;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.action.ManageHiddenEmailTemplatesAction
 * JD-Core Version:    0.6.0
 */