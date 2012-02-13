/*     */ package com.bright.assetbank.updater.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.updater.form.ApplicationUpdateForm;
/*     */ import com.bright.assetbank.updater.service.ApplicationUpdateManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewApplicationUpdateDetailsAction extends ApplicationUpdateAction
/*     */ {
/*  48 */   private ApplicationUpdateManager m_appUpdateManager = null;
/*     */ 
/*     */   public void setApplicationUpdateManager(ApplicationUpdateManager a_appUpdateManager) {
/*  51 */     this.m_appUpdateManager = a_appUpdateManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  61 */     ActionForward afForward = null;
/*     */ 
/*  64 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  66 */     if ((!AssetBankSettings.isApplicationUpdateInProgress()) && (!userProfile.getIsAdmin()) && (!userProfile.getIsInitialOrgUnitAdmin()))
/*     */     {
/*  68 */       this.m_logger.error("ViewApplicationUpdateDetails : User does not have admin permission : " + userProfile);
/*  69 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  72 */     ApplicationUpdateForm form = (ApplicationUpdateForm)a_form;
/*     */ 
/*  75 */     String sVersion = getVersion(a_request);
/*     */ 
/*  78 */     if (this.m_appUpdateManager.updateIsAvailableQuickCheck(sVersion))
/*     */     {
/*     */       try
/*     */       {
/*  82 */         form.setVersionDetails(this.m_appUpdateManager.getUpdateDetails(null, sVersion));
/*     */       }
/*     */       catch (IOException ioe)
/*     */       {
/*  86 */         this.m_logger.error("IOException in ViewApplicationUpdateProgressAction - Could not connect to update server : " + ioe);
/*  87 */         return a_mapping.findForward("NoConnection");
/*     */       }
/*     */ 
/*  90 */       if (AssetBankSettings.isApplicationUpdateInProgress())
/*     */       {
/*  92 */         afForward = a_mapping.findForward("InProgress");
/*     */       }
/*     */       else
/*     */       {
/*  96 */         afForward = a_mapping.findForward("Success");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 101 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 104 */     return afForward;
/*     */   }
/*     */ 
/*     */   public boolean getAvailableNotLoggedIn()
/*     */   {
/* 109 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.action.ViewApplicationUpdateDetailsAction
 * JD-Core Version:    0.6.0
 */