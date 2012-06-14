/*    */ package com.bright.assetbank.orgunit.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.customfield.service.CustomFieldManager;
/*    */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*    */ import com.bright.assetbank.orgunit.constant.OrgUnitConstants;
/*    */ import com.bright.assetbank.orgunit.form.OrgUnitForm;
/*    */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*    */ import com.bright.assetbank.user.bean.ABUser;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.customfield.util.CustomFieldUtil;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public abstract class ViewOrgUnitAndUsersAction extends BTransactionAction
/*    */   implements OrgUnitConstants
/*    */ {
/* 42 */   private OrgUnitManager m_orgUnitManager = null;
/* 43 */   private CustomFieldManager m_fieldManager = null;
/*    */ 
/*    */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*    */   {
/* 47 */     this.m_orgUnitManager = a_orgUnitManager;
/*    */   }
/*    */ 
/*    */   public void setCustomFieldManager(CustomFieldManager a_fieldManager)
/*    */   {
/* 52 */     this.m_fieldManager = a_fieldManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 75 */     ActionForward afForward = null;
/* 76 */     OrgUnitForm form = (OrgUnitForm)a_form;
/* 77 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 80 */     if ((!userProfile.getIsLoggedIn()) || ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin())))
/*    */     {
/* 82 */       this.m_logger.error(getClass().getSimpleName() + ".execute : User must be an admin or an org unit admin.");
/* 83 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 86 */     long lId = getLongParameter(a_request, "ouid");
/* 87 */     OrgUnit ou = this.m_orgUnitManager.getOrgUnit(a_dbTransaction, lId);
/*    */ 
/* 89 */     form.setOrgUnit(ou);
/*    */ 
/* 92 */     Vector vecUsers = getUsers(ou);
/* 93 */     form.setUserList(vecUsers);
/*    */ 
/* 95 */     CustomFieldUtil.prepCustomFields(a_request, form, vecUsers, this.m_fieldManager, a_dbTransaction, 1L, userProfile.getUser());
/*    */ 
/* 97 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 99 */     return afForward;
/*    */   }
/*    */ 
/*    */   protected abstract Vector<ABUser> getUsers(OrgUnit paramOrgUnit)
/*    */     throws Bn2Exception;
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.action.ViewOrgUnitAndUsersAction
 * JD-Core Version:    0.6.0
 */