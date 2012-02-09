/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.attribute.filter.service.FilterManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.GroupFilterExclusionForm;
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
/*     */ public class ViewGroupFilterExclusionAction extends BTransactionAction
/*     */ {
/*     */   private static final String c_ksClassName = "ViewGroupFilterExclusionAction";
/*  45 */   private ABUserManager m_userManager = null;
/*     */ 
/*  51 */   private FilterManager m_filterManager = null;
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/*  48 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setFilterManager(FilterManager a_filterManager)
/*     */   {
/*  54 */     this.m_filterManager = a_filterManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  76 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  77 */     GroupFilterExclusionForm form = (GroupFilterExclusionForm)a_form;
/*     */ 
/*  80 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  82 */       this.m_logger.error("ViewGroupFilterExclusionAction : User does not have admin permission : " + userProfile);
/*  83 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  87 */     long lGroupId = getLongParameter(a_request, "id");
/*  88 */     if (lGroupId <= 0L)
/*     */     {
/*  90 */       throw new Bn2Exception("ViewGroupFilterExclusionAction : no group id passed");
/*     */     }
/*     */ 
/*  94 */     form.setLinkedFilters(this.m_filterManager.getAllCategoryFilters(a_dbTransaction, userProfile));
/*  95 */     form.setGlobalFilters(this.m_filterManager.getGlobalFilters(a_dbTransaction, userProfile));
/*     */ 
/*  98 */     form.setExcludedList(this.m_userManager.getFilterExclusionsForGroup(a_dbTransaction, lGroupId));
/*  99 */     form.setGroupId(lGroupId);
/*     */ 
/* 101 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewGroupFilterExclusionAction
 * JD-Core Version:    0.6.0
 */