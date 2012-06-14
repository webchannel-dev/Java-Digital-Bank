/*    */ package com.bright.assetbank.agreements.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.agreements.form.AgreementsForm;
/*    */ import com.bright.assetbank.agreements.service.AgreementsManager;
/*    */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewAgreementsAction extends BTransactionAction
/*    */ {
/* 45 */   private AgreementsManager m_agreementsManager = null;
/*    */ 
/* 51 */   private OrgUnitManager m_orgUnitManager = null;
/*    */ 
/*    */   public void setAgreementsManager(AgreementsManager a_agreementsManager)
/*    */   {
/* 48 */     this.m_agreementsManager = a_agreementsManager;
/*    */   }
/*    */ 
/*    */   public void setOrgUnitManager(OrgUnitManager orgUnitManager)
/*    */   {
/* 54 */     this.m_orgUnitManager = orgUnitManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 64 */     ActionForward afForward = null;
/* 65 */     AgreementsForm form = (AgreementsForm)a_form;
/* 66 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 67 */     Vector vecAgreements = null;
/* 68 */     Vector vOrgUnits = new Vector();
/*    */ 
/* 71 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()) && (!userProfile.getCanUpdateAssets()))
/*    */     {
/* 73 */       this.m_logger.error("ViewAgreementsAction.execute : User must be an admin.");
/* 74 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 78 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 81 */       vOrgUnits = this.m_orgUnitManager.getOrgUnitsForUser(a_dbTransaction, userProfile.getUser().getId());
/*    */     }
/*    */ 
/* 85 */     vecAgreements = this.m_agreementsManager.getAgreements(a_dbTransaction, true, vOrgUnits, 0L, false);
/*    */ 
/* 87 */     form.setAgreements(vecAgreements);
/*    */ 
/* 89 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 91 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.agreements.action.ViewAgreementsAction
 * JD-Core Version:    0.6.0
 */