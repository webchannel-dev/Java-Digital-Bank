/*    */ package com.bright.assetbank.agreements.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.agreements.service.AgreementsManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DeleteAgreementAction extends BTransactionAction
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
/* 55 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 57 */     long a_lAgreementId = getLongParameter(a_request, "id");
/*    */ 
/* 60 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()) && (!userProfile.getCanUpdateAssets()))
/*    */     {
/* 62 */       this.m_logger.error("DeleteAgreementsAction.execute : User does not have permission.");
/* 63 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 67 */     this.m_agreementsManager.deleteAgreement(a_dbTransaction, a_lAgreementId);
/*    */ 
/* 69 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 71 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.agreements.action.DeleteAgreementAction
 * JD-Core Version:    0.6.0
 */