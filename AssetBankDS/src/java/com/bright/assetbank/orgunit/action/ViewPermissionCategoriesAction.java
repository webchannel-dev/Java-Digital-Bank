/*     */ package com.bright.assetbank.orgunit.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.category.util.CategoryUtil;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*     */ import com.bright.assetbank.orgunit.constant.OrgUnitConstants;
/*     */ import com.bright.assetbank.orgunit.form.OrgUnitCategoryAdminForm;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.action.ViewCategoryAdminAction;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewPermissionCategoriesAction extends ViewCategoryAdminAction
/*     */   implements OrgUnitConstants
/*     */ {
/*  47 */   private OrgUnitManager m_orgUnitManager = null;
/*  48 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  58 */     ActionForward afForward = null;
/*  59 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  60 */     OrgUnitCategoryAdminForm form = (OrgUnitCategoryAdminForm)a_form;
/*     */ 
/*  63 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  65 */       this.m_logger.error("ViewPermissionCategoriesAction.execute : User not logged in.");
/*  66 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*  68 */     ABUser user = (ABUser)userProfile.getUser();
/*     */ 
/*  72 */     long lOrgUnitId = getLongParameter(a_request, "ouid");
/*     */ 
/*  74 */     long lCatId = getLongParameter(a_request, "categoryId");
/*     */ 
/*  76 */     if (lCatId < 1L)
/*     */     {
/*  78 */       lCatId = getLongParameter(a_request, "id");
/*     */     }
/*     */ 
/*  82 */     if (lCatId <= 0L)
/*     */     {
/*  84 */       if (a_request.getAttribute("categoryId") != null)
/*     */       {
/*  86 */         lCatId = ((Long)a_request.getAttribute("categoryId")).longValue();
/*     */       }
/*     */     }
/*     */ 
/*  90 */     if (lOrgUnitId > 0L)
/*     */     {
/*  94 */       if ((userProfile.getIsAdmin()) || ((userProfile.getIsOrgUnitAdmin()) && (user.getIsAdminOfOrgUnit(lOrgUnitId))))
/*     */       {
/*  97 */         OrgUnit ou = this.m_orgUnitManager.getOrgUnit(a_dbTransaction, lOrgUnitId);
/*  98 */         form.setOrgUnit(ou);
/*     */ 
/* 100 */         if (lCatId > 0L)
/*     */         {
/* 103 */           if (!ou.containsCategory(lCatId))
/*     */           {
/* 105 */             this.m_logger.error("ViewPermissionCategoriesAction.execute : Category not in the given org unit.");
/* 106 */             return a_mapping.findForward("NoPermission");
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 112 */           lCatId = ou.getCategory().getId();
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 118 */         this.m_logger.error("ViewPermissionCategoriesAction.execute : User does not have admin permission for the given org unit: " + lOrgUnitId + " : " + userProfile);
/* 119 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 135 */     CategoryUtil.checkForCategoryExtensionError(a_request, form);
/* 136 */     super.getCategories(form, a_dbTransaction, 2L, lCatId, null);
/*     */ 
/* 138 */     if ((!form.getHasErrors()) && (AssetBankSettings.getSupportMultiLanguage()))
/*     */     {
/* 141 */       this.m_languageManager.createEmptyTranslations(a_dbTransaction, form.getNewCategory());
/*     */     }
/*     */ 
/* 144 */     afForward = a_mapping.findForward("Success");
/* 145 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/* 151 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 156 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.action.ViewPermissionCategoriesAction
 * JD-Core Version:    0.6.0
 */