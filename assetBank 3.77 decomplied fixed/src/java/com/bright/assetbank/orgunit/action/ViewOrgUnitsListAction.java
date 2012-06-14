/*    */ package com.bright.assetbank.orgunit.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.orgunit.bean.OrgUnitSearchCriteria;
/*    */ import com.bright.assetbank.orgunit.constant.OrgUnitConstants;
/*    */ import com.bright.assetbank.orgunit.form.OrgUnitForm;
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
/*    */ public class ViewOrgUnitsListAction extends BTransactionAction
/*    */   implements OrgUnitConstants
/*    */ {
/* 48 */   private OrgUnitManager m_orgUnitManager = null;
/*    */ 
/*    */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager) {
/* 51 */     this.m_orgUnitManager = a_orgUnitManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 61 */     ActionForward afForward = null;
/* 62 */     OrgUnitForm form = (OrgUnitForm)a_form;
/* 63 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 66 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*    */     {
/* 68 */       this.m_logger.error("ViewOrgUnitsListAction.execute : User must be an admin.");
/* 69 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 73 */     OrgUnitSearchCriteria search = new OrgUnitSearchCriteria();
/* 74 */     if (userProfile.getIsOrgUnitAdmin())
/*    */     {
/* 76 */       search.setAdminUserId(userProfile.getUser().getId());
/*    */     }
/* 78 */     Vector vec = this.m_orgUnitManager.getOrgUnitList(a_dbTransaction, search, true);
/* 79 */     form.setOrgUnitList(vec);
/*    */ 
/* 81 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 83 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.action.ViewOrgUnitsListAction
 * JD-Core Version:    0.6.0
 */