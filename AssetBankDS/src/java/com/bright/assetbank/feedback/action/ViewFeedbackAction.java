/*     */ package com.bright.assetbank.feedback.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.feedback.bean.AssetFeedback;
/*     */ import com.bright.assetbank.feedback.form.FeedbackForm;
/*     */ import com.bright.assetbank.feedback.service.AssetFeedbackManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewFeedbackAction extends BTransactionAction
/*     */ {
/*  46 */   private AssetFeedbackManager m_feedbackManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  70 */     ActionForward afForward = null;
/*  71 */     FeedbackForm form = (FeedbackForm)a_form;
/*  72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  75 */     long lFeedbackId = getLongParameter(a_request, "feedbackId");
/*  76 */     long lAssetId = getLongParameter(a_request, "id");
/*     */ 
/*  78 */     AssetFeedback feedback = null;
/*     */ 
/*  81 */     if (lFeedbackId > 0L)
/*     */     {
/*  84 */       feedback = getAssetFeedbackManager().getFeedback(a_dbTransaction, lFeedbackId);
/*  85 */       if (!userProfile.getIsAdmin())
/*     */       {
/*  87 */         if (feedback.getUserId() != userProfile.getUser().getId())
/*     */         {
/*  89 */           return a_mapping.findForward("NoPermission");
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*  94 */     else if (lAssetId > 0L)
/*     */     {
/*  97 */       feedback = getAssetFeedbackManager().getFeedback(a_dbTransaction, lAssetId, userProfile.getUser().getId());
/*     */     }
/*     */     else
/*     */     {
/* 102 */       feedback = (AssetFeedback)a_request.getAttribute("feedback");
/*     */     }
/*     */ 
/* 106 */     if (feedback != null)
/*     */     {
/* 108 */       form.setFeedback(feedback);
/*     */     }
/*     */     else
/*     */     {
/* 113 */       form.getFeedback().setAssetId(lAssetId);
/*     */     }
/*     */ 
/* 116 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 118 */     return afForward;
/*     */   }
/*     */ 
/*     */   public AssetFeedbackManager getAssetFeedbackManager()
/*     */   {
/* 124 */     return this.m_feedbackManager;
/*     */   }
/*     */ 
/*     */   public void setAssetFeedbackManager(AssetFeedbackManager a_feedbackManager)
/*     */   {
/* 130 */     this.m_feedbackManager = a_feedbackManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.feedback.action.ViewFeedbackAction
 * JD-Core Version:    0.6.0
 */