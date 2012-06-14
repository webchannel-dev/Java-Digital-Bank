/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.UserSearchCriteria;
/*     */ import com.bright.assetbank.user.form.UserApprovalForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewUnapprovedUsersAction extends UserAction
/*     */ {
/*  53 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*  55 */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager) { this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  78 */     ActionForward afForward = null;
/*  79 */     UserApprovalForm form = (UserApprovalForm)a_form;
/*  80 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  83 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  85 */       this.m_logger.error("ViewUnapprovedUsersAction.execute : User does not have admin permission : " + userProfile);
/*  86 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*  88 */     ABUser user = (ABUser)userProfile.getUser();
/*     */ 
/*  91 */     UserSearchCriteria search = new UserSearchCriteria();
/*     */     Vector vecGroups;
/*     */    // Vector vecGroups;
/*  95 */     if (userProfile.getIsAdmin())
/*     */     {
/*  97 */       vecGroups = getUserManager().getAllGroups();
/*     */     }
/*     */     else
/*     */     {
/* 102 */       vecGroups = this.m_orgUnitManager.getOrgUnitAdminUserManagedGroups(a_dbTransaction, user.getId());
/* 103 */       search.setAdminUserId(user.getId());
/*     */     }
/* 105 */     form.setGroups(vecGroups);
/*     */ 
/* 108 */     search.setNotApproved(Boolean.TRUE);
/*     */ 
/* 110 */     Vector vecUnapprovedUsers = getUserManager().findUsers(search, 13);
/*     */ 
/* 112 */     form.setUsers(vecUnapprovedUsers);
/*     */ 
/* 116 */     search.setNotApproved(Boolean.FALSE);
/* 117 */     search.setAdminUserId(0L);
/* 118 */     search.setRequiresUpdate(Boolean.TRUE);
/* 119 */     Vector vecAllRequestUpdateUsers = getUserManager().findUsers(search, 1);
/* 120 */     Vector vecFilteredRequestUpdateUsers = new Vector();
/*     */ 
/* 125 */     Iterator it = vecAllRequestUpdateUsers.iterator();
/* 126 */     while (it.hasNext())
/*     */     {
/* 128 */       ABUser requestUser = (ABUser)it.next();
/*     */ 
/* 132 */       long lRequestedOrgUnit = requestUser.getRequestedOrgUnitId();
/*     */ 
/* 134 */       if ((userProfile.getIsAdmin()) || ((lRequestedOrgUnit > 0L) && (user.getIsAdminOfOrgUnit(lRequestedOrgUnit))))
/*     */       {
/* 137 */         vecFilteredRequestUpdateUsers.add(requestUser);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 142 */     form.setUsersRequiringUpdates(vecFilteredRequestUpdateUsers);
/*     */ 
/* 144 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 146 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewUnapprovedUsersAction
 * JD-Core Version:    0.6.0
 */