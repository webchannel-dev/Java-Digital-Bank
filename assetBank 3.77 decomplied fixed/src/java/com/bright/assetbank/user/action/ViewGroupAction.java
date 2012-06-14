/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*     */ import com.bright.assetbank.orgunit.constant.OrgUnitConstants;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.Group;
/*     */ import com.bright.assetbank.user.form.GroupForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.FlatCategoryList;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.service.CategoryManager;
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
/*     */ public class ViewGroupAction extends UserAction
/*     */   implements OrgUnitConstants, CategoryConstants
/*     */ {
/*     */   private static final String k_sParamName_Expandable = "expandable";
/* 170 */   private CategoryManager m_categoryManager = null;
/*     */ 
/* 176 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  75 */     ActionForward afForward = null;
/*  76 */     GroupForm form = (GroupForm)a_form;
/*  77 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  80 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  82 */       this.m_logger.error("ViewGroupAction.execute : User is not an admin.");
/*  83 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*  85 */     ABUser user = (ABUser)userProfile.getUser();
/*     */ 
/*  89 */     long lGroupId = getLongParameter(a_request, "id");
/*     */ 
/*  91 */     if (lGroupId > 0L)
/*     */     {
/*  95 */       OrgUnit ouGroup = this.m_orgUnitManager.getOrgUnitForGroup(a_dbTransaction, lGroupId);
/*  96 */       if ((userProfile.getIsOrgUnitAdmin()) && (!user.getIsAdminOfOrgUnit(ouGroup.getId())) && (!userProfile.getIsAdmin()))
/*     */       {
/*  98 */         this.m_logger.error("ViewGroupAction.execute : User not admin for group: id=" + lGroupId);
/*  99 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/* 102 */       Group group = getUserManager().getGroup(lGroupId);
/* 103 */       form.setGroup(group);
/*     */ 
/* 106 */       if (form.getSelectedPermissions() == null)
/*     */       {
/* 108 */         form.setSelectedPermissions(group.getCategoryPermissions());
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 113 */       form.getGroup().setUserCanEmailAssets(true);
/* 114 */       form.getGroup().setUsersCanViewLargerSize(true);
/*     */     }
/*     */ 
/* 118 */     if (userProfile.getIsAdmin())
/*     */     {
/* 120 */       form.setOrgUnitList(this.m_orgUnitManager.getOrgUnitList(a_dbTransaction));
/*     */     }
/*     */     else
/*     */     {
/* 125 */       form.setOrgUnitList(this.m_orgUnitManager.getOrgUnitListForAdminUser(a_dbTransaction, user.getId()));
/*     */     }
/*     */ 
/* 129 */     form.setBrandList(getUserManager().getAllBrands(a_dbTransaction));
/*     */ 
/* 132 */     long lTreeId = getLongParameter(a_request, "treeId");
/*     */ 
/* 135 */     if (lTreeId == 2L)
/*     */     {
/* 137 */       if (userProfile.getIsAdmin())
/*     */       {
/* 139 */         FlatCategoryList fcl = this.m_categoryManager.getFlatCategoryList(a_dbTransaction, 2L);
/*     */ 
/* 143 */         if ((Boolean.parseBoolean(a_request.getParameter("expandable"))) && (fcl.getCategories().size() > AssetBankSettings.getGroupAdminMaxAccessLevels()))
/*     */         {
/* 146 */           fcl = new FlatCategoryList();
/* 147 */           fcl.setCategories(this.m_categoryManager.getCategory(a_dbTransaction, 2L, -1L).getChildCategories());
/* 148 */           form.setAccessLevelsExpandable(true);
/*     */         }
/* 150 */         form.setCategoryList(fcl);
/*     */       }
/*     */       else
/*     */       {
/* 155 */         int iMaxSize = !Boolean.parseBoolean(a_request.getParameter("expandable")) ? 2147483647 : AssetBankSettings.getGroupAdminMaxAccessLevels();
/* 156 */         form.setCategoryList(this.m_orgUnitManager.getOrgUnitAdminUserCategories(a_dbTransaction, user.getId(), iMaxSize));
/* 157 */         form.setAccessLevelsExpandable(form.getCategoryList().getDepth() <= 1);
/*     */       }
/*     */     }
/* 160 */     else if (lTreeId == 1L)
/*     */     {
/* 162 */       form.setCategoryList(this.m_categoryManager.getFlatCategoryList(a_dbTransaction, 1L));
/*     */     }
/*     */ 
/* 165 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 167 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_sCategoryManager)
/*     */   {
/* 173 */     this.m_categoryManager = a_sCategoryManager;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/* 178 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewGroupAction
 * JD-Core Version:    0.6.0
 */