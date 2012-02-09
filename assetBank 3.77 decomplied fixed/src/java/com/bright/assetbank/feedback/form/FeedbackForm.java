/*    */ package com.bright.assetbank.feedback.form;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.feedback.bean.AssetFeedback;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.simplelist.bean.ListItem;
/*    */ import com.bright.framework.simplelist.service.ListManager;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ 
/*    */ public class FeedbackForm extends Bn2Form
/*    */ {
/* 37 */   private AssetFeedback m_feedback = new AssetFeedback();
/*    */ 
/*    */   public void setFeedback(AssetFeedback a_feedback)
/*    */   {
/* 41 */     this.m_feedback = a_feedback;
/*    */   }
/*    */ 
/*    */   public AssetFeedback getFeedback()
/*    */   {
/* 46 */     return this.m_feedback;
/*    */   }
/*    */ 
/*    */   public void validate(ListManager a_listManager, DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*    */     throws Bn2Exception
/*    */   {
/* 52 */     if ((AssetBankSettings.getFeedbackComments()) && (!AssetBankSettings.getFeedbackRatingsAreVotes()) && (AssetBankSettings.getRatingsCommentsMandatory()))
/*    */     {
/* 54 */       if (!StringUtil.stringIsPopulated(getFeedback().getComments()))
/*    */       {
/* 56 */         addError(a_listManager.getListItem(a_dbTransaction, "feedbackComments", a_userProfile.getCurrentLanguage()).getBody());
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.feedback.form.FeedbackForm
 * JD-Core Version:    0.6.0
 */