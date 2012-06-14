/*     */ package com.bright.assetbank.orgunit.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*     */ import com.bright.assetbank.orgunit.constant.OrgUnitConstants;
/*     */ import com.bright.assetbank.orgunit.form.OrgUnitForm;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
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
/*     */ public class ViewOrgUnitAction extends BTransactionAction
/*     */   implements OrgUnitConstants
/*     */ {
/*  55 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*  65 */   private CategoryManager m_categoryManager = null;
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/*  62 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_manager)
/*     */   {
/*  68 */     this.m_categoryManager = a_manager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  91 */     ActionForward afForward = null;
/*  92 */     OrgUnitForm form = (OrgUnitForm)a_form;
/*  93 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  96 */     if ((!userProfile.getIsLoggedIn()) || ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin())))
/*     */     {
/*  98 */       this.m_logger.error("ViewOrgUnitAction.execute : User must be an admin or an org unit admin.");
/*  99 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 102 */     Vector vCats = this.m_categoryManager.getCategories(a_dbTransaction, 1L, -1L);
/* 103 */     form.setDescriptiveCategories(vCats);
/*     */ 
/* 105 */     if (!form.getHasErrors())
/*     */     {
/* 107 */       long lId = getLongParameter(a_request, "id");
/*     */ 
/* 110 */       OrgUnit ou = new OrgUnit();
/* 111 */       if (lId > 0L)
/*     */       {
/* 113 */         ou = this.m_orgUnitManager.getOrgUnit(a_dbTransaction, lId);
/*     */       }
/*     */ 
/* 116 */       form.setOrgUnit(ou);
/* 117 */       form.setDiskQuotaString(String.valueOf(ou.getDiskQuotaMb()));
/* 118 */       form.setRootDescriptiveCategoryId(ou.getRootDescriptiveCategory() != null ? ou.getRootDescriptiveCategory().getId() : 0L);
/*     */     }
/*     */ 
/* 121 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 123 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.action.ViewOrgUnitAction
 * JD-Core Version:    0.6.0
 */