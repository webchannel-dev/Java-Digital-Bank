/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.MessageUsersForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewMessageUsersAction extends UserAction
/*     */ {
/*  46 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager) {
/*  49 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  78 */     MessageUsersForm form = (MessageUsersForm)a_form;
/*  79 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  82 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  84 */       this.m_logger.error("ViewAddUserAction.execute : User does not have admin permission : " + userProfile);
/*  85 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*  87 */     ABUser user = (ABUser)userProfile.getUser();
/*     */     Vector vecGroups;
/*     */    // Vector vecGroups;
/*  90 */     if (userProfile.getIsAdmin())
/*     */     {
/*  92 */       vecGroups = getUserManager().getAllGroups();
/*     */     }
/*     */     else
/*     */     {
/*  96 */       vecGroups = this.m_orgUnitManager.getOrgUnitAdminUserManagedGroups(a_dbTransaction, user.getId());
/*     */     }
/*  98 */     form.setGroups(vecGroups);
/*     */ 
/* 101 */     Vector vecGroupIds = getGroupIds(a_request);
/* 102 */     form.setGroupIds(vecGroupIds);
/*     */ 
/* 104 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewMessageUsersAction
 * JD-Core Version:    0.6.0
 */