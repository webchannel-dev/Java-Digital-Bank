/*     */ package com.bright.assetbank.marketing.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.marketing.form.SendMarketingEmailForm;
/*     */ import com.bright.assetbank.marketing.service.MarketingEmailManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SendMarketingEmailAction extends BTransactionAction
/*     */   implements AssetBoxConstants, AssetBankConstants, FrameworkConstants
/*     */ {
/*  46 */   private MarketingEmailManager m_marketingEmailManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  55 */     ActionForward afForward = null;
/*  56 */     SendMarketingEmailForm form = (SendMarketingEmailForm)a_form;
/*  57 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  58 */     String[] asUserIds = a_request.getParameterValues("selectedUsers");
/*     */ 
/*  61 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  63 */       this.m_logger.debug("The user must be admin to access " + getClass().getSimpleName());
/*  64 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  67 */     if ((asUserIds != null) && (asUserIds.length > 0))
/*     */     {
/*  69 */       long[] alUserIds = new long[asUserIds.length];
/*     */ 
/*  71 */       for (int i = 0; i < asUserIds.length; i++)
/*     */       {
/*  73 */         alUserIds[i] = Long.parseLong(asUserIds[i]);
/*     */       }
/*     */ 
/*  76 */       int iSuccessCount = this.m_marketingEmailManager.sendMarketingEmail(a_dbTransaction, form.getEmailContent(), form.getFromAddress(), alUserIds, form.getMarketingEmail(), form.getAssetBoxId());
/*     */ 
/*  83 */       if (iSuccessCount > 0)
/*     */       {
/*  85 */         afForward = a_mapping.findForward("Success");
/*     */       }
/*     */       else
/*     */       {
/*  89 */         form.addError("No emails were sent - check the email addresses of intended recipients and/or the log files for any errors.");
/*  90 */         afForward = a_mapping.findForward("ValidationFailure");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  95 */       afForward = a_mapping.findForward("SystemFailure");
/*     */     }
/*     */ 
/*  98 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setMarketingEmailManager(MarketingEmailManager marketingEmailManager)
/*     */   {
/* 103 */     this.m_marketingEmailManager = marketingEmailManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.action.SendMarketingEmailAction
 * JD-Core Version:    0.6.0
 */