/*     */ package com.bright.assetbank.usage.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.usage.bean.UsageType;
/*     */ import com.bright.assetbank.usage.form.UsageAdminForm;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewUpdateUsageTypeAction extends BTransactionAction
/*     */ {
/*  47 */   private UsageManager m_usageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  66 */     ActionForward afForward = null;
/*  67 */     UsageAdminForm form = (UsageAdminForm)a_form;
/*  68 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  71 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  73 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  74 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  78 */     long lUsageTypeId = getLongParameter(a_request, "id");
/*  79 */     long lParentId = getLongParameter(a_request, "parentId");
/*     */ 
/*  81 */     if (lUsageTypeId <= 0L)
/*     */     {
/*  83 */       lUsageTypeId = form.getUsageType().getId();
/*     */     }
/*     */ 
/*  87 */     form.setUsageTypes(this.m_usageManager.getUsageTypes(a_dbTransaction, lParentId));
/*     */ 
/*  90 */     if (lParentId > 0L)
/*     */     {
/*  92 */       form.setParentUsageType(this.m_usageManager.getUsageType(a_dbTransaction, lParentId));
/*     */     }
/*     */ 
/*  96 */     form.setUsageType(this.m_usageManager.getUsageType(a_dbTransaction, lUsageTypeId));
/*     */ 
/*  98 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 100 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/* 105 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.ViewUpdateUsageTypeAction
 * JD-Core Version:    0.6.0
 */