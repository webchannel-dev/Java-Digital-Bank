/*     */ package com.bright.assetbank.feedback.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.feedback.bean.AssetFeedback;
/*     */ import com.bright.assetbank.feedback.form.FeedbackForm;
/*     */ import com.bright.assetbank.feedback.service.AssetFeedbackManager;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.GregorianCalendar;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveFeedbackAction extends BTransactionAction
/*     */ {
/*  58 */   private AssetFeedbackManager m_feedbackManager = null;
/*  59 */   private AssetManager m_assetManager = null;
/*  60 */   private MultiLanguageSearchManager m_searchManager = null;
/*  61 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  90 */     ActionForward afForward = null;
/*  91 */     FeedbackForm form = (FeedbackForm)a_form;
/*  92 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  93 */     AssetFeedback feedback = form.getFeedback();
/*     */ 
/*  95 */     String sCancel = a_request.getParameter("cancelButton");
/*     */ 
/*  97 */     if ((sCancel == null) || (sCancel.length() == 0))
/*     */     {
/* 100 */       form.validate(this.m_listManager, a_dbTransaction, userProfile);
/* 101 */       if (form.getHasErrors())
/*     */       {
/* 103 */         a_request.setAttribute("feedback", feedback);
/* 104 */         return a_mapping.findForward("ValidationFailure");
/*     */       }
/*     */ 
/* 107 */       if (feedback.getUserId() <= 0L)
/*     */       {
/* 110 */         feedback.setUserId(userProfile.getUser().getId());
/*     */       }
/*     */ 
/* 113 */       feedback.setDate(new GregorianCalendar().getTime());
/*     */ 
/* 116 */       Asset asset = this.m_assetManager.getAsset(a_dbTransaction, feedback.getAssetId(), null, false, false);
/* 117 */       if (!userProfile.getIsAdmin())
/*     */       {
/* 119 */         if (!this.m_assetManager.userCanReviewAsset(userProfile, asset))
/*     */         {
/* 121 */           return a_mapping.findForward("Failure");
/*     */         }
/*     */       }
/*     */ 
/* 125 */       boolean bSavedFeedback = getAssetFeedbackManager().saveFeedback(a_dbTransaction, feedback);
/*     */ 
/* 128 */       if (!bSavedFeedback)
/*     */       {
/* 131 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/* 135 */       asset = this.m_assetManager.getAsset(a_dbTransaction, feedback.getAssetId(), null, true, true);
/* 136 */       this.m_searchManager.indexDocument(asset, true);
/*     */     }
/*     */ 
/* 140 */     String sQueryString = a_request.getParameter("queryString");
/*     */ 
/* 142 */     if (StringUtil.stringIsPopulated(sQueryString))
/*     */     {
/* 144 */       if (sQueryString.indexOf("&feedback=") < 0)
/*     */       {
/* 146 */         sQueryString = sQueryString + "&" + "feedback" + "=1";
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 151 */       sQueryString = "id=" + feedback.getAssetId();
/*     */     }
/* 153 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */ 
/* 155 */     return afForward;
/*     */   }
/*     */ 
/*     */   public AssetFeedbackManager getAssetFeedbackManager()
/*     */   {
/* 161 */     return this.m_feedbackManager;
/*     */   }
/*     */ 
/*     */   public void setAssetFeedbackManager(AssetFeedbackManager a_feedbackManager)
/*     */   {
/* 167 */     this.m_feedbackManager = a_feedbackManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 172 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 177 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/* 182 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.feedback.action.SaveFeedbackAction
 * JD-Core Version:    0.6.0
 */