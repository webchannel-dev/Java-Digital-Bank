/*    */ package com.bright.assetbank.agreements.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.agreements.bean.Agreement;
/*    */ import com.bright.assetbank.agreements.form.AgreementsForm;
/*    */ import com.bright.assetbank.agreements.service.AgreementsManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewAgreementAction extends BTransactionAction
/*    */ {
/* 41 */   private AgreementsManager m_agreementsManager = null;
/*    */ 
/*    */   public void setAgreementsManager(AgreementsManager a_agreementsManager) {
/* 44 */     this.m_agreementsManager = a_agreementsManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 54 */     ActionForward afForward = null;
/* 55 */     AgreementsForm form = (AgreementsForm)a_form;
/* 56 */     Agreement agreement = null;
/*    */ 
/* 58 */     long a_lAgreementId = getLongParameter(a_request, "id");
/*    */ 
/* 61 */     agreement = this.m_agreementsManager.getAgreement(a_dbTransaction, a_lAgreementId);
/*    */ 
/* 63 */     form.setAgreement(agreement);
/*    */ 
/* 65 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 67 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.agreements.action.ViewAgreementAction
 * JD-Core Version:    0.6.0
 */