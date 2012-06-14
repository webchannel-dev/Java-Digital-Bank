/*     */ package com.bright.assetbank.orgunit.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.category.action.UpdateCategoryAction;
/*     */ import com.bright.assetbank.category.form.ABCategoryForm;
/*     */ import com.bright.assetbank.category.util.CategoryUtil;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*     */ import com.bright.assetbank.orgunit.constant.OrgUnitConstants;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class UpdatePermissionCategoryAction extends UpdateCategoryAction
/*     */   implements OrgUnitConstants, CategoryConstants, MessageConstants
/*     */ {
/*  57 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*  63 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/*  60 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  66 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  76 */     ActionForward afForward = null;
/*  77 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  78 */     ABCategoryForm form = (ABCategoryForm)a_form;
/*     */ 
/*  81 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  83 */       this.m_logger.error("UpdatePermissionCategoryAction.execute : User not logged in.");
/*  84 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*  86 */     ABUser user = (ABUser)userProfile.getUser();
/*     */ 
/*  89 */     long lOrgUnitId = getLongParameter(a_request, "ouid");
/*  90 */     long lCatId = getLongParameter(a_request, "categoryId");
/*  91 */     long lParentId = getLongParameter(a_request, "parentId");
/*     */ 
/*  94 */     String sQueryString = "";
/*  95 */     String sParentQueryString = "";
/*  96 */     if (lCatId > 0L)
/*     */     {
/*  98 */       sQueryString = "categoryId=" + lCatId;
/*     */     }
/* 100 */     if (lParentId > 0L)
/*     */     {
/* 102 */       sParentQueryString = "categoryId=" + lParentId;
/*     */     }
/* 104 */     if (lOrgUnitId > 0L)
/*     */     {
/* 106 */       sParentQueryString = sParentQueryString + "&ouid=" + lOrgUnitId;
/*     */     }
/*     */ 
/* 113 */     if (a_request.getParameter("cancel") != null)
/*     */     {
/* 115 */       afForward = createRedirectingForward(sParentQueryString, a_mapping, "Success");
/* 116 */       return afForward;
/*     */     }
/*     */ 
/* 121 */     if (lOrgUnitId > 0L)
/*     */     {
/* 125 */       if ((userProfile.getIsAdmin()) || ((userProfile.getIsOrgUnitAdmin()) && (user.getIsAdminOfOrgUnit(lOrgUnitId))))
/*     */       {
/* 128 */         OrgUnit ou = this.m_orgUnitManager.getOrgUnit(a_dbTransaction, lOrgUnitId);
/*     */ 
/* 130 */         if (lCatId > 0L)
/*     */         {
/* 133 */           if (!ou.containsCategory(lCatId))
/*     */           {
/* 135 */             this.m_logger.error("ViewUpdatePermissionCategoryAction.execute : Category not in the given org unit.");
/* 136 */             return a_mapping.findForward("NoPermission");
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 142 */           lCatId = ou.getCategory().getId();
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 148 */         this.m_logger.error("ViewUpdatePermissionCategoryAction.execute : User does not have admin permission for the given org unit: " + lOrgUnitId + " : " + userProfile);
/* 149 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/*     */     }
/* 155 */     else if (!userProfile.getIsAdmin())
/*     */     {
/* 157 */       this.m_logger.error("ViewUpdatePermissionCategoryAction.execute : User does not have admin permission to browse all permissions categories: " + userProfile);
/* 158 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 163 */     Category cat = form.getCategory();
/* 164 */     cat.setId(lCatId);
/* 165 */     cat.setIsBrowsable(form.isBrowsable());
/* 166 */     boolean bValid = CategoryUtil.validateCategory(cat.getName(), form, this.m_listManager, userProfile, a_dbTransaction);
/* 167 */     form.failValidation();
/*     */ 
/* 169 */     if (bValid)
/*     */     {
/* 171 */       if (!this.m_categoryManager.updateCategoryDetails(a_dbTransaction, 2L, cat))
/*     */       {
/* 173 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "categoryErrorDuplicateAccessLevelName", userProfile.getCurrentLanguage()).getBody());
/* 174 */         bValid = false;
/*     */       }
/*     */ 
/* 177 */       if (bValid)
/*     */       {
/* 179 */         bValid = updateCategoryImage(form, a_dbTransaction, userProfile);
/*     */ 
/* 182 */         int iUpdateChildWorkflows = getIntParameter(a_request, "updateChildWorkflows");
/* 183 */         if (iUpdateChildWorkflows > 0)
/*     */         {
/* 185 */           this.m_categoryManager.updateCategoryWorkflowsForChildren(a_dbTransaction, cat.getId(), 2L, cat.getWorkflowName());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 190 */     if (!bValid)
/*     */     {
/* 192 */       afForward = createForward(sQueryString, a_mapping, "Failure");
/*     */     }
/*     */     else
/*     */     {
/* 196 */       afForward = createRedirectingForward(sParentQueryString, a_mapping, "Success");
/*     */     }
/*     */ 
/* 199 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.action.UpdatePermissionCategoryAction
 * JD-Core Version:    0.6.0
 */