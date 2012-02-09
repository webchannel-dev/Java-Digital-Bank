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
/*     */ public class UpdateUsageTypeAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  50 */   private UsageManager m_usageManager = null;
/*     */ 
/* 130 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  69 */     ActionForward afForward = null;
/*  70 */     UsageAdminForm form = (UsageAdminForm)a_form;
/*  71 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  74 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  76 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  77 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  81 */     long lParentId = form.getParentUsageType().getId();
/*  82 */     String sQueryString = "parentId=" + lParentId;
/*     */ 
/*  85 */     if (a_request.getParameter("b_save") == null)
/*     */     {
/*  87 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Cancel");
/*  88 */       return afForward;
/*     */     }
/*     */ 
/*  92 */     if ((form.getUsageType().getDescription() == null) || (form.getUsageType().getDescription().trim().length() == 0))
/*     */     {
/*  94 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationUsageType", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/*  97 */     if (!form.getHasErrors())
/*     */     {
/* 100 */       if (form.getUsageType().getDetailsMandatory())
/*     */       {
/* 102 */         form.getUsageType().setCanEnterDetails(true);
/*     */       }
/*     */ 
/* 105 */       this.m_usageManager.updateUsageTypeValue(a_dbTransaction, form.getUsageType());
/*     */ 
/* 108 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */     else
/*     */     {
/* 112 */       afForward = a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/* 115 */     return afForward;
/*     */   }
/*     */ 
/*     */   public UsageManager getUsageManager()
/*     */   {
/* 121 */     return this.m_usageManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageMaager)
/*     */   {
/* 127 */     this.m_usageManager = a_sUsageMaager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 133 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.UpdateUsageTypeAction
 * JD-Core Version:    0.6.0
 */