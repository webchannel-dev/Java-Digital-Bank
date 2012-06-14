/*     */ package com.bright.assetbank.workset.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workset.service.AssetWorksetManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SubmitUnsubmittedAssetAction extends BTransactionAction
/*     */ {
/*  40 */   private AssetWorksetManager m_assetWorksetManager = null;
/*     */ 
/*     */   public void setAssetWorksetManager(AssetWorksetManager a_wm) {
/*  43 */     this.m_assetWorksetManager = a_wm;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  54 */     ActionForward afForward = null;
/*  55 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  58 */     if ((!userProfile.getCanUploadWithApproval()) && (!userProfile.getCanUpdateAssets()))
/*     */     {
/*  60 */       this.m_logger.debug("ViewUnsubmittedAssetsAction: not upload permission");
/*  61 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  65 */     long lUserIdParam = 0L;
/*  66 */     if (userProfile.getIsAdmin())
/*     */     {
/*  68 */       lUserIdParam = getLongParameter(a_request, "userId");
/*     */     }
/*     */ 
/*  72 */     int iOption = getIntParameter(a_request, "option");
/*  73 */     long lAssetId = getLongParameter(a_request, "id");
/*     */ 
/*  75 */     if (lAssetId > 0L)
/*     */     {
/*  77 */       switch (iOption)
/*     */       {
/*     */       case 0:
/*  80 */         this.m_assetWorksetManager.submitAssetToLive(a_dbTransaction, lAssetId);
/*  81 */         break;
/*     */       case 1:
/*  84 */         this.m_assetWorksetManager.submitAssetForApproval(a_dbTransaction, lAssetId, userProfile.getUser().getId(), userProfile.getSessionId());
/*  85 */         break;
/*     */       case 2:
/*  88 */         String sQueryString = "id=" + lAssetId + "&";
/*  89 */         if (lUserIdParam > 0L)
/*     */         {
/*  91 */           sQueryString = sQueryString + "unsubmittedAdmin" + "=" + lUserIdParam;
/*     */         }
/*     */         else
/*     */         {
/*  95 */           sQueryString = sQueryString + "unsubmitted" + "=1";
/*     */         }
/*  97 */         return createRedirectingForward(sQueryString, a_mapping, "DeleteAsset");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 105 */     String sQueryString = "";
/*     */ 
/* 108 */     if (lUserIdParam > 0L)
/*     */     {
/* 110 */       sQueryString = "selectedUserId=" + lUserIdParam;
/*     */     }
/*     */ 
/* 113 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/* 114 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workset.action.SubmitUnsubmittedAssetAction
 * JD-Core Version:    0.6.0
 */