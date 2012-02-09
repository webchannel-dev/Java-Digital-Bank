/*     */ package com.bright.assetbank.feedback.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.feedback.bean.AssetFeedback;
/*     */ import com.bright.assetbank.feedback.service.AssetFeedbackManager;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class DeleteFeedbackAction extends BTransactionAction
/*     */   implements UserConstants, AssetBankConstants
/*     */ {
/*  51 */   private AssetFeedbackManager m_feedbackManager = null;
/*  52 */   private MultiLanguageSearchManager m_searchManager = null;
/*  53 */   private AssetManager m_assetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  78 */     ActionForward afForward = null;
/*  79 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  82 */     long lFeedbackId = getLongParameter(a_request, "id");
/*  83 */     long lAssetId = getLongParameter(a_request, "assetId");
/*  84 */     long lUserId = getLongParameter(a_request, "userId");
/*     */ 
/*  86 */     if (lFeedbackId > 0L)
/*     */     {
/*  88 */       AssetFeedback feedback = getAssetFeedbackManager().getFeedback(a_dbTransaction, lFeedbackId);
/*  89 */       lAssetId = feedback.getAssetId();
/*     */ 
/*  92 */       if (!userProfile.getIsAdmin())
/*     */       {
/*  94 */         if (feedback.getUserId() != userProfile.getUser().getId())
/*     */         {
/*  96 */           return a_mapping.findForward("NoPermission");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 101 */       getAssetFeedbackManager().deleteFeedback(a_dbTransaction, lFeedbackId);
/*     */     }
/*     */     else
/*     */     {
/* 105 */       getAssetFeedbackManager().deleteFeedback(a_dbTransaction, lAssetId, lUserId);
/*     */     }
/*     */ 
/* 109 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, null, true, true);
/* 110 */     this.m_searchManager.indexDocument(asset, true);
/*     */ 
/* 112 */     String sQueryString = a_request.getParameter("queryString");
/* 113 */     if (StringUtil.stringIsPopulated(sQueryString))
/*     */     {
/* 115 */       if (sQueryString.indexOf("&feedback=") < 0)
/*     */       {
/* 117 */         sQueryString = sQueryString + "&" + "feedback" + "=1";
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 122 */       sQueryString = "id=" + asset.getId();
/*     */     }
/*     */ 
/* 125 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */ 
/* 127 */     return afForward;
/*     */   }
/*     */ 
/*     */   public AssetFeedbackManager getAssetFeedbackManager()
/*     */   {
/* 133 */     return this.m_feedbackManager;
/*     */   }
/*     */ 
/*     */   public void setAssetFeedbackManager(AssetFeedbackManager a_feedbackManager)
/*     */   {
/* 139 */     this.m_feedbackManager = a_feedbackManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 144 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 149 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.feedback.action.DeleteFeedbackAction
 * JD-Core Version:    0.6.0
 */