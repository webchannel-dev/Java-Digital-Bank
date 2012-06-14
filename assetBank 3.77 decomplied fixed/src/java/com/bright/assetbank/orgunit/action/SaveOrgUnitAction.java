/*     */ package com.bright.assetbank.orgunit.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.category.util.CategoryUtil;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*     */ import com.bright.assetbank.orgunit.constant.OrgUnitConstants;
/*     */ import com.bright.assetbank.orgunit.form.OrgUnitForm;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveOrgUnitAction extends BTransactionAction
/*     */   implements OrgUnitConstants, MessageConstants
/*     */ {
/*  57 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*  62 */   protected ListManager m_listManager = null;
/*     */ 
/*  68 */   private CategoryManager m_categoryManager = null;
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/*  59 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  65 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_manager)
/*     */   {
/*  71 */     this.m_categoryManager = a_manager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  94 */     ActionForward afForward = null;
/*  95 */     OrgUnitForm form = (OrgUnitForm)a_form;
/*  96 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  99 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/* 101 */       this.m_logger.error("SaveOrgUnitAction.execute : User must be an admin or an org unit admin.");
/* 102 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 105 */     OrgUnit ou = form.getOrgUnit();
/* 106 */     long lOrgUnitId = form.getOrgUnit().getId();
/*     */ 
/* 109 */     Category cat = ou.getCategory();
/* 110 */     boolean bValid = CategoryUtil.validateCategory(cat.getName(), form, this.m_listManager, userProfile, a_dbTransaction);
/*     */ 
/* 112 */     if ((bValid) && (StringUtil.stringIsPopulated(form.getDiskQuotaString())) && (!StringUtil.stringIsInteger(form.getDiskQuotaString())))
/*     */     {
/* 114 */       form.addError("The file storage quota value must be numeric (or blank or zero for 'no limit').");
/* 115 */       bValid = false;
/*     */     }
/*     */ 
/* 118 */     if (bValid)
/*     */     {
/* 120 */       if (StringUtil.stringIsPopulated(form.getDiskQuotaString()))
/*     */       {
/* 122 */         ou.setDiskQuotaMb(Integer.parseInt(form.getDiskQuotaString()));
/*     */       }
/*     */ 
/* 125 */       if (form.getRootDescriptiveCategoryId() > 0L)
/*     */       {
/* 127 */         ou.setRootDescriptiveCategory(this.m_categoryManager.getCategory(a_dbTransaction, 1L, form.getRootDescriptiveCategoryId()));
/*     */       }
/*     */       else
/*     */       {
/* 131 */         ou.setRootDescriptiveCategory(null);
/*     */       }
/*     */ 
/* 135 */       if (lOrgUnitId > 0L)
/*     */       {
/* 138 */         ABUser user = (ABUser)userProfile.getUser();
/* 139 */         if ((userProfile.getIsOrgUnitAdmin()) && (!user.getIsAdminOfOrgUnit(lOrgUnitId)))
/*     */         {
/* 141 */           this.m_logger.error("SaveOrgUnitAction.execute : User is not org unit admin for this org unit.");
/* 142 */           return a_mapping.findForward("NoPermission");
/*     */         }
/*     */ 
/* 146 */         if (StringUtil.stringIsPopulated(form.getDiskQuotaString()))
/*     */         {
/* 148 */           ou.setDiskQuotaMb(Integer.parseInt(form.getDiskQuotaString()));
/*     */         }
/*     */ 
/* 151 */         if (!this.m_orgUnitManager.updateOrgUnit(a_dbTransaction, ou, userProfile.getIsAdmin()))
/*     */         {
/* 153 */           form.addError(this.m_listManager.getListItem(a_dbTransaction, "categoryErrorDuplicateAccessLevelName", userProfile.getCurrentLanguage()).getBody());
/* 154 */           bValid = false;
/*     */         }
/*     */ 
/*     */       }
/* 160 */       else if (!this.m_orgUnitManager.newOrgUnit(a_dbTransaction, ou))
/*     */       {
/* 162 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "categoryErrorDuplicateAccessLevelName", userProfile.getCurrentLanguage()).getBody());
/* 163 */         bValid = false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 168 */     if (!bValid)
/*     */     {
/* 170 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/* 174 */       afForward = createRedirectingForward("", a_mapping, "Success");
/*     */     }
/*     */ 
/* 177 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.action.SaveOrgUnitAction
 * JD-Core Version:    0.6.0
 */