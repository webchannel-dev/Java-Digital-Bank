/*     */ package com.bright.assetbank.orgunit.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.orgunit.constant.OrgUnitConstants;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.action.ViewCategoryAdminAction;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.FlatCategoryList;
/*     */ import com.bright.framework.category.form.DeleteCategoryForm;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewDeletePermissionCategoryAction extends ViewCategoryAdminAction
/*     */   implements OrgUnitConstants
/*     */ {
/*  57 */   private OrgUnitManager m_orgUnitManager = null;
/*  58 */   private LanguageManager m_languageManager = null;
/*  59 */   private CategoryManager m_categoryManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  68 */     ActionForward afForward = null;
/*  69 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  72 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  74 */       this.m_logger.error("ViewDeletePermissionCategoryAction.execute : User not logged in.");
/*  75 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*  77 */     ABUser user = (ABUser)userProfile.getUser();
/*     */ 
/*  81 */     long lOrgUnitId = getLongParameter(a_request, "ouid");
/*     */ 
/*  83 */     if (lOrgUnitId > 0L)
/*     */     {
/*  85 */       if ((!userProfile.getIsAdmin()) && ((!userProfile.getIsOrgUnitAdmin()) || (!user.getIsAdminOfOrgUnit(lOrgUnitId))))
/*     */       {
/*  87 */         this.m_logger.error("ViewDeletePermissionCategoryAction.execute : User does not have admin permission for the given org unit: " + lOrgUnitId + " : " + userProfile);
/*  88 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/*     */     }
/*  94 */     else if (!userProfile.getIsAdmin())
/*     */     {
/*  96 */       this.m_logger.error("ViewDeletePermissionCategoryAction.execute : User does not have admin permission to browse all permissions categories: " + userProfile);
/*  97 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 101 */     DeleteCategoryForm form = (DeleteCategoryForm)a_form;
/*     */ 
/* 104 */     Category category = this.m_categoryManager.getCategory(a_dbTransaction, 2L, form.getCategoryIdToDelete());
/* 105 */     form.setCategory(category);
/*     */ 
/* 108 */     if (category.getNumChildCategories() <= 0)
/*     */     {
/* 111 */       FlatCategoryList flatCategoryList = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, 2L);
/* 112 */       form.setFlatCategoryList(flatCategoryList);
/*     */     }
/*     */ 
/* 115 */     afForward = a_mapping.findForward("Success");
/* 116 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_categoryManager)
/*     */   {
/* 122 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/* 127 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 132 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.action.ViewDeletePermissionCategoryAction
 * JD-Core Version:    0.6.0
 */