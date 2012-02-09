/*     */ package com.bright.assetbank.orgunit.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*     */ import com.bright.assetbank.orgunit.constant.OrgUnitConstants;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.Group;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
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
/*     */ public class DeleteOrgUnitAction extends BTransactionAction
/*     */   implements OrgUnitConstants
/*     */ {
/*  52 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*  58 */   private AssetCategoryManager m_categoryManager = null;
/*     */ 
/*  64 */   private ABUserManager m_userManager = null;
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/*  55 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_categoryManager)
/*     */   {
/*  61 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/*  67 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  83 */     ActionForward afForward = null;
/*  84 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  87 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  89 */       this.m_logger.error("DeleteOrgUnitAction.execute : User not logged in.");
/*  90 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  94 */     long a_lOrgUnitId = getLongParameter(a_request, "id");
/*     */ 
/*  97 */     Vector vecGroups = this.m_orgUnitManager.getOrgUnitGroups(a_dbTransaction, a_lOrgUnitId);
/*  98 */     OrgUnit ou = this.m_orgUnitManager.getOrgUnit(a_dbTransaction, a_lOrgUnitId);
/*     */ 
/* 101 */     this.m_orgUnitManager.deleteOrgUnit(a_dbTransaction, a_lOrgUnitId);
/*     */ 
/* 104 */     if (!commitTransaction(a_dbTransaction))
/*     */     {
/* 107 */       return a_mapping.findForward("SystemFailure");
/*     */     }
/*     */ 
/* 111 */     a_dbTransaction = getNewTransaction();
/*     */ 
/* 114 */     Iterator it = vecGroups.iterator();
/* 115 */     while (it.hasNext())
/*     */     {
/* 117 */       Group group = (Group)it.next();
/* 118 */       this.m_userManager.deleteGroup(group.getId());
/*     */     }
/*     */ 
/* 121 */     if (!commitTransaction(a_dbTransaction));
/* 127 */     a_dbTransaction = getNewTransaction();
/*     */ 
/* 129 */     this.m_categoryManager.deleteCategory(a_dbTransaction, 2L, ou.getCategory().getId(), userProfile.getUser().getId());
/*     */ 
/* 131 */     if (!commitTransaction(a_dbTransaction));
/* 136 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 139 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.action.DeleteOrgUnitAction
 * JD-Core Version:    0.6.0
 */