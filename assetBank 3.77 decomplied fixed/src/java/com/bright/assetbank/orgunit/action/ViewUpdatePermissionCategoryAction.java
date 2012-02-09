/*     */ package com.bright.assetbank.orgunit.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*     */ import com.bright.assetbank.orgunit.constant.OrgUnitConstants;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.action.ViewUpdateCategoryAction;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.form.CategoryForm;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewUpdatePermissionCategoryAction extends ViewUpdateCategoryAction
/*     */   implements OrgUnitConstants
/*     */ {
/*  44 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager) {
/*  47 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  57 */     ActionForward afForward = null;
/*  58 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  59 */     CategoryForm categoryForm = (CategoryForm)a_form;
/*     */ 
/*  62 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  64 */       this.m_logger.error("ViewUpdatePermissionCategoryAction.execute : User not logged in.");
/*  65 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*  67 */     ABUser user = (ABUser)userProfile.getUser();
/*     */ 
/*  71 */     long lOrgUnitId = getLongParameter(a_request, "ouid");
/*  72 */     long lCatId = getLongParameter(a_request, "categoryId");
/*     */ 
/*  74 */     if (lOrgUnitId > 0L)
/*     */     {
/*  78 */       if ((userProfile.getIsAdmin()) || ((userProfile.getIsOrgUnitAdmin()) && (user.getIsAdminOfOrgUnit(lOrgUnitId))))
/*     */       {
/*  81 */         OrgUnit ou = this.m_orgUnitManager.getOrgUnit(a_dbTransaction, lOrgUnitId);
/*     */ 
/*  83 */         if (lCatId > 0L)
/*     */         {
/*  86 */           if (!ou.containsCategory(lCatId))
/*     */           {
/*  88 */             this.m_logger.error("ViewUpdatePermissionCategoryAction.execute : Category not in the given org unit.");
/*  89 */             return a_mapping.findForward("NoPermission");
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*  95 */           lCatId = ou.getCategory().getId();
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 101 */         this.m_logger.error("ViewUpdatePermissionCategoryAction.execute : User does not have admin permission for the given org unit: " + lOrgUnitId + " : " + userProfile);
/* 102 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/*     */     }
/* 108 */     else if (!userProfile.getIsAdmin())
/*     */     {
/* 110 */       this.m_logger.error("ViewUpdatePermissionCategoryAction.execute : User does not have admin permission to browse all permissions categories: " + userProfile);
/* 111 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 116 */     if (!categoryForm.failedValidation())
/*     */     {
/* 118 */       super.getCategory(categoryForm, a_dbTransaction, 2L, lCatId);
/*     */     }
/*     */ 
/* 121 */     afForward = a_mapping.findForward("Success");
/* 122 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.action.ViewUpdatePermissionCategoryAction
 * JD-Core Version:    0.6.0
 */