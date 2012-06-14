/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.GroupUsageExclusionForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
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
/*     */ public class ViewGroupUsageExclusionAction extends BTransactionAction
/*     */ {
/*     */   private static final String c_ksClassName = "ViewGroupUsageExclusionAction";
/*  45 */   private ABUserManager m_userManager = null;
/*     */ 
/*  51 */   private UsageManager m_usageManager = null;
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/*  48 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/*  54 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  78 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  79 */     GroupUsageExclusionForm form = (GroupUsageExclusionForm)a_form;
/*     */ 
/*  82 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  84 */       this.m_logger.error("ViewGroupUsageExclusionAction : User does not have admin permission : " + userProfile);
/*  85 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  89 */     long lGroupId = getLongParameter(a_request, "id");
/*  90 */     if (lGroupId <= 0L)
/*     */     {
/*  92 */       throw new Bn2Exception("ViewGroupUsageExclusionAction : no group id passed");
/*     */     }
/*     */ 
/*  96 */     form.setUsageTypeList(this.m_usageManager.getUsageTypes(a_dbTransaction, 0L));
/*     */ 
/*  99 */     form.setExcludedList(this.m_userManager.getUsageExclusionsForGroup(a_dbTransaction, lGroupId));
/*     */ 
/* 101 */     form.setGroupId(lGroupId);
/*     */ 
/* 103 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewGroupUsageExclusionAction
 * JD-Core Version:    0.6.0
 */