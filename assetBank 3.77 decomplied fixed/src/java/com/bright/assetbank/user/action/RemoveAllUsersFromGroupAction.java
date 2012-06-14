/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class RemoveAllUsersFromGroupAction extends UserAction
/*     */ {
/*  44 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*  46 */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager) { this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     ActionForward afForward = null;
/*  74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  77 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  79 */       this.m_logger.error("RemoveAllUsersFromGroupAction.execute : User does not have admin permission : " + userProfile);
/*  80 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  84 */     long lGroupId = getLongParameter(a_request, "id");
/*  85 */     ABUser user = (ABUser)userProfile.getUser();
/*     */ 
/*  88 */     OrgUnit ouGroup = this.m_orgUnitManager.getOrgUnitForGroup(a_dbTransaction, lGroupId);
/*  89 */     if ((userProfile.getIsOrgUnitAdmin()) && (!user.getIsAdminOfOrgUnit(ouGroup.getId())))
/*     */     {
/*  91 */       this.m_logger.error("RemoveAllUsersFromGroupAction.execute : User is not an admin of group: " + lGroupId);
/*  92 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  96 */     boolean bSuccess = getUserManager().removeUsersFromGroup(lGroupId);
/*  97 */     String sQueryString = "";
/*  98 */     if (bSuccess)
/*     */     {
/* 100 */       sQueryString = "groupCleared=1";
/*     */     }
/*     */ 
/* 103 */     afForward = createRedirectingForward(sQueryString + "&" + RequestUtil.getAsQueryParameter(a_request, "name") + "&" + RequestUtil.getAsQueryParameter(a_request, "page") + "&" + RequestUtil.getAsQueryParameter(a_request, "pageSize"), a_mapping, "Success");
/*     */ 
/* 110 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.RemoveAllUsersFromGroupAction
 * JD-Core Version:    0.6.0
 */