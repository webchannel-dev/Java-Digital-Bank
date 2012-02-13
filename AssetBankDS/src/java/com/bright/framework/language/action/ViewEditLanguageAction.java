/*    */ package com.bright.framework.language.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.language.service.LanguageManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.language.form.LanguageForm;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewEditLanguageAction extends LanguageAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 55 */     LanguageForm form = (LanguageForm)a_form;
/* 56 */     long lId = getLongParameter(a_request, "id");
/*    */ 
/* 58 */     form.setLanguage(getLanguageManager().getLanguage(a_dbTransaction, lId));
/*    */ 
/* 60 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.language.action.ViewEditLanguageAction
 * JD-Core Version:    0.6.0
 */