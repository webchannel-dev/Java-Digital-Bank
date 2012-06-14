/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnitSearchCriteria;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.RequestUserUpdateForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewRequestUserUpdateAction extends UserAction
/*     */ {
/*  47 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager) {
/*  50 */     this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     RequestUserUpdateForm form = (RequestUserUpdateForm)a_form;
/*     */ 
/*  76 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  78 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  80 */       this.m_logger.debug("User not logged in.");
/*  81 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  85 */     long lUserId = userProfile.getUser().getId();
/*  86 */     ABUser user = (ABUser)getUserManager().getUser(a_dbTransaction, lUserId);
/*  87 */     form.setUser(user);
/*     */ 
/*  90 */     OrgUnitSearchCriteria search = new OrgUnitSearchCriteria();
/*  91 */     Vector vecAllOrgUnits = this.m_orgUnitManager.getOrgUnitList(a_dbTransaction, search, false);
/*  92 */     form.setOrgUnitList(vecAllOrgUnits);
/*     */ 
/*  95 */     Vector vecUserOrgUnits = this.m_orgUnitManager.getOrgUnitsForUser(a_dbTransaction, lUserId);
/*  96 */     form.setUserOrgUnits(vecUserOrgUnits);
/*     */ 
/*  99 */     Vector vecBrands = getUserManager().getAllBrands(a_dbTransaction);
/* 100 */     form.setRegisterGroupList(vecBrands);
/*     */ 
/* 102 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewRequestUserUpdateAction
 * JD-Core Version:    0.6.0
 */