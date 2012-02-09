/*     */ package com.bright.assetbank.cmsintegration.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.action.LoginAction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.form.LoginForm;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class CmsLoginAction extends LoginAction
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     ActionForward afForward = null;
/*     */ 
/*  73 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(a_request);
/*     */ 
/*  76 */     LoginForm loginForm = (LoginForm)a_form;
/*     */ 
/*  78 */     afForward = doLogin(a_dbTransaction, a_mapping, loginForm, a_request, a_response, loginForm.getUsername(), loginForm.getPassword(), !AssetBankSettings.getCmsLoginRequiresPassword());
/*     */ 
/*  87 */     afForward = a_mapping.findForward("Success");
/*     */ 
/*  90 */     if (userProfile.getIsLoggedIn())
/*     */     {
/*  93 */       if (!((ABUser)userProfile.getUser()).getCanLoginFromCms())
/*     */       {
/*  95 */         this.m_logger.error("User with id + " + userProfile.getUser().getId() + " does not have permission to log in from the CMS");
/*  96 */         afForward = a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 101 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.cmsintegration.action.CmsLoginAction
 * JD-Core Version:    0.6.0
 */