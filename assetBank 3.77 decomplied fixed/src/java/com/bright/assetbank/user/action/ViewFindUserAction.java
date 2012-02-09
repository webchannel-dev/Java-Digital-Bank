/*    */ package com.bright.assetbank.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*    */ import com.bright.assetbank.user.bean.ABUser;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.user.bean.UserSearchCriteria;
/*    */ import com.bright.assetbank.user.form.UserForm;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
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
/*    */ public class ViewFindUserAction extends UserAction
/*    */ {
/* 45 */   private OrgUnitManager m_orgUnitManager = null;
/*    */ 
/* 47 */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager) { this.m_orgUnitManager = a_orgUnitManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 70 */     ActionForward afForward = null;
/* 71 */     UserForm form = (UserForm)a_form;
/* 72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 75 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*    */     {
/* 77 */       this.m_logger.error("ViewFindUserAction.execute : User does not have admin permission : " + userProfile);
/* 78 */       return a_mapping.findForward("NoPermission");
/*    */     }
/* 80 */     ABUser user = (ABUser)userProfile.getUser();
/*    */     Vector vecGroups;
/*    */    // Vector vecGroups;
/* 84 */     if ((userProfile.getIsAdmin()) || (AssetBankSettings.getOrgUnitAdminCanAccessAllUsers()))
/*    */     {
/* 86 */       vecGroups = getUserManager().getAllGroups();
/*    */     }
/*    */     else
/*    */     {
/* 91 */       vecGroups = this.m_orgUnitManager.getOrgUnitAdminUserManagedGroups(a_dbTransaction, user.getId());
/* 92 */       form.getSearchCriteria().setAdminUserId(user.getId());
/*    */     }
/* 94 */     form.setGroups(vecGroups);
/*    */ 
/* 96 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 98 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewFindUserAction
 * JD-Core Version:    0.6.0
 */