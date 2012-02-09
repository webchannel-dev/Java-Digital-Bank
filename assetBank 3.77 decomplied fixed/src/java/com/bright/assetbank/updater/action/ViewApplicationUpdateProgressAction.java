/*     */ package com.bright.assetbank.updater.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.updater.form.ApplicationUpdateForm;
/*     */ import com.bright.assetbank.updater.service.ApplicationUpdateManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.ServletUtil;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewApplicationUpdateProgressAction extends BTransactionAction
/*     */ {
/*  52 */   private ApplicationUpdateManager m_appUpdateManager = null;
/*     */ 
/*     */   public void setApplicationUpdateManager(ApplicationUpdateManager a_appUpdateManager) {
/*  55 */     this.m_appUpdateManager = a_appUpdateManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  68 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  70 */     if ((!AssetBankSettings.isApplicationUpdateInProgress()) && (!userProfile.getIsAdmin()) && (!userProfile.getIsInitialOrgUnitAdmin()))
/*     */     {
/*  72 */       this.m_logger.error("ViewApplicationUpdateProgress : User does not have admin permission : " + userProfile);
/*  73 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  76 */     ApplicationUpdateForm form = (ApplicationUpdateForm)a_form;
/*     */ 
/*  78 */     int iUpdateState = this.m_appUpdateManager.getUpdateStatus();
/*     */ 
/*  81 */     if (iUpdateState == 1)
/*     */     {
/*  83 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  87 */     if (iUpdateState == 3)
/*     */     {
/*  89 */       form.setFailureError(this.m_appUpdateManager.getFailureError());
/*     */     }
/*     */ 
/*  92 */     String sVersion = a_request.getParameter("version");
/*     */ 
/*  96 */     if (iUpdateState == 4)
/*     */     {
/*     */       try
/*     */       {
/* 100 */         form.setUpToDate(!this.m_appUpdateManager.updateIsAvailableFullCheck(a_dbTransaction, sVersion));
/*     */       }
/*     */       catch (IOException ioe)
/*     */       {
/* 104 */         this.m_logger.error("IOException in ViewApplicationUpdateProgressAction - Could not connect to update server : " + ioe);
/* 105 */         return a_mapping.findForward("NoConnection");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 110 */     form.setUpdateStaus(iUpdateState);
/* 111 */     form.setUpdateProgressMessages(this.m_appUpdateManager.getProgressDisplayMessages());
/* 112 */     form.setUpdateErrorMessages(this.m_appUpdateManager.getErrorDisplayMessages());
/* 113 */     form.setVersionDetails(this.m_appUpdateManager.getStoredUpdateDetails());
/*     */ 
/* 116 */     form.setTomcatReloadUrl(ServletUtil.getTomcatReloadUrl(a_request));
/* 117 */     form.setContextPath(a_request.getContextPath());
/*     */ 
/* 119 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public boolean getAvailableNotLoggedIn()
/*     */   {
/* 124 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.action.ViewApplicationUpdateProgressAction
 * JD-Core Version:    0.6.0
 */