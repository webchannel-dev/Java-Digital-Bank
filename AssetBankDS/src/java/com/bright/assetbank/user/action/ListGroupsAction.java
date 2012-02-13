/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.orgunit.constant.OrgUnitConstants;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.UserForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ListGroupsAction extends UserAction
/*     */   implements OrgUnitConstants
/*     */ {
/*  55 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/*  62 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  84 */     ActionForward afForward = null;
/*  85 */     UserForm form = (UserForm)a_form;
/*  86 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  89 */     if (a_request.getParameter("Cancel") != null)
/*     */     {
/*  91 */       return createRedirectingForward("", a_mapping, "Cancel");
/*     */     }
/*     */ 
/*  95 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  97 */       this.m_logger.error("ListGroupsAction.execute : User is not an admin.");
/*  98 */       return a_mapping.findForward("NoPermission");
/*     */     }
/* 100 */     ABUser user = (ABUser)userProfile.getUser();
/*     */ 
/* 103 */     long lOrgUnitId = getLongParameter(a_request, "ouid");
/*     */ 
/* 106 */     String sNameFilter = a_request.getParameter("name");
/* 107 */     int iPageIndex = 0;
/* 108 */     int iPageSize = -1;
/*     */ 
/* 111 */     if (AssetBankSettings.getMaxGroupsPerPage() > 0)
/*     */     {
/* 113 */       iPageIndex = getIntParameter(a_request, "page");
/* 114 */       iPageSize = getIntParameter(a_request, "pageSize");
/*     */ 
/* 116 */       if (iPageSize < 0)
/*     */       {
/* 118 */         iPageSize = AssetBankSettings.getMaxGroupsPerPage();
/*     */       }
/*     */ 
/* 121 */       if (iPageIndex < 0)
/*     */       {
/* 123 */         iPageIndex = 0;
/*     */       }
/*     */     }
/*     */     SearchResults results;
/*     */    // SearchResults results;
/* 127 */     if (lOrgUnitId > 0L)
/*     */     {
/* 130 */       if ((userProfile.getIsOrgUnitAdmin()) && (!user.getIsAdminOfOrgUnit(lOrgUnitId)))
/*     */       {
/* 132 */         this.m_logger.error("ListGroupsAction.execute : User does not have access for that OU.");
/* 133 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/* 137 */       results = this.m_orgUnitManager.searchOrgUnitGroups(a_dbTransaction, lOrgUnitId, sNameFilter, iPageIndex * iPageSize, iPageSize);
/*     */     }
/*     */     else
/*     */     {
/*     */       //SearchResults results;
/* 142 */       if (userProfile.getIsAdmin())
/*     */       {
/* 144 */         results = getUserManager().searchGroups(sNameFilter, iPageIndex * iPageSize, iPageSize);
/*     */       }
/*     */       else
/*     */       {
/* 149 */         results = this.m_orgUnitManager.searchOrgUnitAdminUserManagedGroups(a_dbTransaction, user.getId(), sNameFilter, iPageIndex * iPageSize, iPageSize);
/*     */       }
/*     */     }
/*     */ 
/* 153 */     form.setSearchResults(results);
/*     */ 
/* 155 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 157 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ListGroupsAction
 * JD-Core Version:    0.6.0
 */