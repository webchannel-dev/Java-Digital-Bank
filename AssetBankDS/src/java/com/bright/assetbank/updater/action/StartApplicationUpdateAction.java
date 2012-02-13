/*     */ package com.bright.assetbank.updater.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.updater.exception.UpdatePermissionsException;
/*     */ import com.bright.assetbank.updater.form.ApplicationUpdateForm;
/*     */ import com.bright.assetbank.updater.service.ApplicationUpdateManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import com.websina.license.LicenseManager;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class StartApplicationUpdateAction extends ApplicationUpdateAction
/*     */   implements AssetBankConstants
/*     */ {
/*  56 */   private ApplicationUpdateManager m_appUpdateManager = null;
/*     */ 
/*     */   public void setApplicationUpdateManager(ApplicationUpdateManager a_appUpdateManager) {
/*  59 */     this.m_appUpdateManager = a_appUpdateManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  88 */     ActionForward afForward = null;
/*  89 */     ApplicationUpdateForm form = (ApplicationUpdateForm)a_form;
/*     */ 
/*  92 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  94 */     if ((!AssetBankSettings.isApplicationUpdateInProgress()) && (!userProfile.getIsAdmin()) && (!userProfile.getIsInitialOrgUnitAdmin()))
/*     */     {
/*  96 */       this.m_logger.error("StartApplicationUpdate : User does not have admin permission : " + userProfile);
/*  97 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 100 */     boolean bUpdatesEnabled = false;
/* 101 */     boolean bBrightAdmin = false;
/*     */ 
/* 103 */     String sBrightAdmin = a_request.getParameter("BrightAdmin");
/* 104 */     if ((StringUtil.stringIsPopulated(sBrightAdmin)) && (sBrightAdmin.equals("true")))
/*     */     {
/* 106 */       bUpdatesEnabled = true;
/* 107 */       bBrightAdmin = true;
/*     */     }
/*     */     else
/*     */     {
/* 112 */       LicenseManager licManager = LicenseManager.getInstance();
/* 113 */       String sUpdaterExpiry = licManager.getFeature("UpdaterExpiry");
/* 114 */       if (sUpdaterExpiry != null)
/*     */       {
/* 116 */         if (sUpdaterExpiry.equals(""))
/*     */         {
/* 118 */           bUpdatesEnabled = true;
/*     */         }
/*     */         else
/*     */         {
/*     */           try
/*     */           {
/* 124 */             Date expire = new SimpleDateFormat("yyyy-MM-dd").parse(sUpdaterExpiry);
/* 125 */             Date now = new Date();
/* 126 */             bUpdatesEnabled = expire.after(now);
/*     */           }
/*     */           catch (ParseException e)
/*     */           {
/* 131 */             this.m_logger.error("StartApplicationUpdateAction: " + e);
/* 132 */             bUpdatesEnabled = false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 138 */     if (bUpdatesEnabled)
/*     */     {
/* 140 */       String sVersion = getVersion(a_request);
/* 141 */       if ((this.m_appUpdateManager.updateIsAvailableQuickCheck(sVersion)) || (bBrightAdmin))
/*     */       {
/*     */         try
/*     */         {
/* 146 */           if (this.m_appUpdateManager.startUpdate(a_request.getContextPath(), bBrightAdmin, userProfile, sVersion))
/*     */           {
/* 148 */             afForward = a_mapping.findForward("Success");
/*     */           }
/*     */           else
/*     */           {
/* 152 */             afForward = a_mapping.findForward("Failure");
/*     */           }
/*     */         }
/*     */         catch (UpdatePermissionsException e)
/*     */         {
/* 157 */           form.setUpdatePermissionDetails(e.getUpdatePermissionDetails());
/* 158 */           afForward = a_mapping.findForward("InsufficientPermissions");
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 164 */         afForward = a_mapping.findForward("NoUpdateAvailable");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 169 */       afForward = a_mapping.findForward("UpdatesNotEnabled");
/*     */     }
/*     */ 
/* 173 */     return afForward;
/*     */   }
/*     */ 
/*     */   public boolean getAvailableNotLoggedIn()
/*     */   {
/* 178 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.action.StartApplicationUpdateAction
 * JD-Core Version:    0.6.0
 */