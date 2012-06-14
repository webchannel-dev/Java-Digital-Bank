/*     */ package com.bright.assetbank.approval.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.approval.bean.AssetApprovalSearchCriteria;
/*     */ import com.bright.assetbank.approval.constant.AssetApprovalConstants;
/*     */ import com.bright.assetbank.approval.form.AssetApprovalForm;
/*     */ import com.bright.assetbank.approval.service.AssetApprovalManager;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewUserAssetsForApprovalAction extends BTransactionAction
/*     */   implements AssetApprovalConstants
/*     */ {
/*  53 */   private AssetApprovalManager m_approvalManager = null;
/*     */ 
/*  59 */   private UsageManager m_usageManager = null;
/*     */ 
/*  69 */   private ABUserManager m_userManager = null;
/*     */ 
/*     */   public void setAssetApprovalManager(AssetApprovalManager a_approvalManager)
/*     */   {
/*  56 */     this.m_approvalManager = a_approvalManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_usageManager)
/*     */   {
/*  62 */     this.m_usageManager = a_usageManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/*  76 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 100 */     ActionForward afForward = null;
/* 101 */     AssetApprovalForm form = (AssetApprovalForm)a_form;
/* 102 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 105 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/* 107 */       this.m_logger.error("ViewUnapprovedUsersAction.execute : User does not have admin permission : " + userProfile);
/* 108 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 112 */     long lUserId = getIntParameter(a_request, "userId");
/*     */ 
/* 115 */     ABUser user = (ABUser)this.m_userManager.getUser(a_dbTransaction, lUserId);
/* 116 */     form.setUser(user);
/*     */ 
/* 119 */     AssetApprovalSearchCriteria search = new AssetApprovalSearchCriteria();
/* 120 */     search.setUserId(lUserId);
/* 121 */     search.setApprovalStatusId(1L);
/*     */ 
/* 124 */     if (!userProfile.getIsAdmin())
/*     */     {
/* 126 */       search.setCategoryIds(userProfile.getCategoryIdsAsVector(7));
/*     */     }
/*     */ 
/* 129 */     Vector vec = this.m_approvalManager.getAssetApprovalList(a_dbTransaction, search, userProfile.getCurrentLanguage());
/* 130 */     form.setApprovalList(vec);
/*     */ 
/* 133 */     form.setUsageTypeList(this.m_usageManager.getUsageTypes(a_dbTransaction, -9223372036854775808L, userProfile.getCurrentLanguage()));
/*     */ 
/* 135 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 137 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.action.ViewUserAssetsForApprovalAction
 * JD-Core Version:    0.6.0
 */