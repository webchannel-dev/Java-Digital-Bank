/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnitMetadata;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.Group;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.assetbank.user.form.GroupForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveGroupAction extends UserAction
/*     */   implements FrameworkConstants, UserConstants
/*     */ {
/*  63 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*  68 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*     */   {
/*  65 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  71 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  94 */     ActionForward afForward = null;
/*  95 */     GroupForm form = (GroupForm)a_form;
/*  96 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  99 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/* 101 */       this.m_logger.error("SaveGroupAction.execute : User is not an admin.");
/* 102 */       return a_mapping.findForward("NoPermission");
/*     */     }
/* 104 */     ABUser user = (ABUser)userProfile.getUser();
/*     */ 
/* 107 */     long lGroupId = form.getGroup().getId();
/*     */ 
/* 109 */     if (lGroupId > 0L)
/*     */     {
/* 111 */       OrgUnit ouGroup = this.m_orgUnitManager.getOrgUnitForGroup(a_dbTransaction, lGroupId);
/* 112 */       if ((!userProfile.getIsAdmin()) && ((!userProfile.getIsOrgUnitAdmin()) || (!user.getIsAdminOfOrgUnit(ouGroup.getId()))))
/*     */       {
/* 114 */         this.m_logger.error("SaveGroupAction.execute : User is not an admin of group: " + lGroupId);
/* 115 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 120 */     form.validate(a_request, userProfile, a_dbTransaction, this.m_listManager);
/*     */ 
/* 122 */     if (form.getHasErrors())
/*     */     {
/* 125 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/* 129 */       Group group = form.getGroup();
/*     */ 
/* 131 */       if (AssetBankSettings.ecommerce())
/*     */       {
/* 133 */         group.setDiscountPercentage(Integer.parseInt(form.getDiscountString()));
/*     */       }
/*     */ 
/* 137 */       getUserManager().saveGroup(a_dbTransaction, group);
/*     */ 
/* 140 */       if (lGroupId <= 0L)
/*     */       {
/* 142 */         int iAttributeVisibilityLevel = AssetBankSettings.getDefaultNewGroupAttributeVisibilityLevel();
/* 143 */         getUserManager().setAttributeVisibilityForGroup(a_dbTransaction, group, iAttributeVisibilityLevel);
/*     */       }
/*     */ 
/* 147 */       lGroupId = form.getGroup().getId();
/*     */ 
/* 150 */       long lOrgUnitId = form.getGroup().getOrgUnitReference().getId();
/* 151 */       if (lOrgUnitId > 0L)
/*     */       {
/* 153 */         this.m_orgUnitManager.addGroupToOrgUnit(a_dbTransaction, lOrgUnitId, lGroupId);
/*     */       }
/*     */ 
/* 156 */       afForward = createRedirectingForward(RequestUtil.getAsQueryParameter(a_request, "name") + "&" + RequestUtil.getAsQueryParameter(a_request, "page") + "&" + RequestUtil.getAsQueryParameter(a_request, "pageSize"), a_mapping, "Success");
/*     */     }
/*     */ 
/* 163 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.SaveGroupAction
 * JD-Core Version:    0.6.0
 */