/*    */ package com.bright.assetbank.marketing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.marketing.bean.MarketingEmail;
/*    */ import com.bright.assetbank.marketing.constant.MarketingConstants;
/*    */ import com.bright.assetbank.marketing.form.SendMarketingEmailForm;
/*    */ import com.bright.assetbank.marketing.service.MarketingEmailManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewMarketingEmailAssetsAction extends BTransactionAction
/*    */   implements MarketingConstants
/*    */ {
/* 43 */   private MarketingEmailManager m_marketingEmailManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 52 */     SendMarketingEmailForm form = (SendMarketingEmailForm)a_form;
/* 53 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 54 */     ActionForward forward = null;
/*    */ 
/* 56 */     long lMarketingEmailId = getLongParameter(a_request, "emailId");
/*    */ 
/* 58 */     if (lMarketingEmailId > 0L)
/*    */     {
/* 60 */       MarketingEmail email = this.m_marketingEmailManager.getMarketingEmailPageData(a_dbTransaction, lMarketingEmailId, userProfile);
/*    */ 
/* 62 */       if ((email.getAssets().size() > 0) || (userProfile.getIsLoggedIn()))
/*    */       {
/* 64 */         form.setMarketingEmail(email);
/* 65 */         forward = a_mapping.findForward("Success");
/*    */       }
/*    */       else
/*    */       {
/* 70 */         forward = a_mapping.findForward("NoPermission");
/*    */       }
/*    */     }
/*    */     else
/*    */     {
/* 75 */       forward = a_mapping.findForward("SystemFailure");
/*    */     }
/*    */ 
/* 78 */     return forward;
/*    */   }
/*    */ 
/*    */   public void setMarketingEmailManager(MarketingEmailManager marketingEmailManager)
/*    */   {
/* 83 */     this.m_marketingEmailManager = marketingEmailManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.action.ViewMarketingEmailAssetsAction
 * JD-Core Version:    0.6.0
 */