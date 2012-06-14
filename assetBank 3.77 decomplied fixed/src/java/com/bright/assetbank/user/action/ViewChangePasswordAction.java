/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.constant.CommonConstants;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.assetbank.user.form.ChangePasswordForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewChangePasswordAction extends UserAction
/*     */   implements ImageConstants, UserConstants, CommonConstants
/*     */ {
/*  53 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*  55 */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager) { this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  75 */     ChangePasswordForm form = (ChangePasswordForm)a_form;
/*  76 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  79 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  81 */       this.m_logger.debug("This user does not have permission to view the admin page");
/*  82 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  85 */     ABUser user = (ABUser)userProfile.getUser();
/*     */ 
/*  87 */     long lUserId = getIntParameter(a_request, "id");
/*     */ 
/*  90 */     if (lUserId > 0L)
/*     */     {
/*  92 */       Vector vecOUs = this.m_orgUnitManager.getOrgUnitsForUser(a_dbTransaction, lUserId);
/*  93 */       if ((userProfile.getIsOrgUnitAdmin()) && (!user.getIsAdminOfAtLeastOneOrgUnit(vecOUs)))
/*     */       {
/*  95 */         this.m_logger.error("ViewChangePasswordAction.execute : User not admin for user: id=" + lUserId);
/*  96 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */     }
/*     */ 
/* 100 */     if (lUserId > 0L)
/*     */     {
/* 102 */       form.setUser((ABUser)getUserManager().getUser(a_dbTransaction, lUserId));
/* 103 */       form.setNotifyUser(true);
/*     */     }
/*     */ 
/* 106 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewChangePasswordAction
 * JD-Core Version:    0.6.0
 */