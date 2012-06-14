/*    */ package com.bright.assetbank.user.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*    */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*    */ import com.bright.assetbank.user.bean.ABUser;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.util.RequestUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DeleteGroupAction extends UserAction
/*    */ {
/* 46 */   private OrgUnitManager m_orgUnitManager = null;
/*    */ 
/* 48 */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager) { this.m_orgUnitManager = a_orgUnitManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 70 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 73 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*    */     {
/* 75 */       this.m_logger.error("DeleteGroupAction.execute : User does not have admin permission : " + userProfile);
/* 76 */       return a_mapping.findForward("NoPermission");
/*    */     }
/* 78 */     ABUser user = (ABUser)userProfile.getUser();
/*    */ 
/* 81 */     long lGroupId = getLongParameter(a_request, "id");
/*    */ 
/* 84 */     OrgUnit ouGroup = this.m_orgUnitManager.getOrgUnitForGroup(a_dbTransaction, lGroupId);
/* 85 */     if ((userProfile.getIsOrgUnitAdmin()) && (!user.getIsAdminOfOrgUnit(ouGroup.getId())))
/*    */     {
/* 87 */       this.m_logger.error("DeleteGroupAction.execute : User is not an admin of group: " + lGroupId);
/* 88 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 92 */     getUserManager().deleteGroup(lGroupId);
/*    */ 
/* 94 */     return createRedirectingForward(RequestUtil.getAsQueryParameter(a_request, "name") + "&" + RequestUtil.getAsQueryParameter(a_request, "page") + "&" + RequestUtil.getAsQueryParameter(a_request, "pageSize"), a_mapping, "Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.DeleteGroupAction
 * JD-Core Version:    0.6.0
 */