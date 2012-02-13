/*     */ package com.bright.assetbank.usage.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.usage.bean.UsageType;
/*     */ import com.bright.assetbank.usage.form.UsageAdminForm;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AddUsageTypeAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  50 */   private UsageManager m_usageManager = null;
/*  51 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  70 */     ActionForward afForward = null;
/*  71 */     UsageAdminForm form = (UsageAdminForm)a_form;
/*  72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  75 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  77 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  78 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  82 */     long lParentId = form.getParentUsageType().getId();
/*     */ 
/*  85 */     if ((form.getUsageType().getDescription() == null) || (form.getUsageType().getDescription().trim().length() == 0))
/*     */     {
/*  87 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationUsageType", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/*  90 */     if (!form.getHasErrors())
/*     */     {
/*  93 */       if (form.getUsageType().getDetailsMandatory())
/*     */       {
/*  95 */         form.getUsageType().setCanEnterDetails(true);
/*     */       }
/*     */ 
/*  98 */       this.m_usageManager.addUsageTypeValue(a_dbTransaction, form.getUsageType(), lParentId);
/*     */ 
/* 103 */       String sQueryString = "parentId=" + lParentId;
/* 104 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */     else
/*     */     {
/* 108 */       afForward = a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/* 111 */     return afForward;
/*     */   }
/*     */ 
/*     */   public UsageManager getUsageManager()
/*     */   {
/* 117 */     return this.m_usageManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageMaager)
/*     */   {
/* 123 */     this.m_usageManager = a_sUsageMaager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 128 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.AddUsageTypeAction
 * JD-Core Version:    0.6.0
 */