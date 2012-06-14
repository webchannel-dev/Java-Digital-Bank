/*     */ package com.bright.assetbank.attribute.filter.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.attribute.filter.form.FilterForm;
/*     */ import com.bright.assetbank.attribute.filter.service.FilterManager;
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
/*     */ public class ViewManageFiltersAction extends BTransactionAction
/*     */ {
/*  46 */   private FilterManager m_filterManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  74 */     ActionForward forward = null;
/*     */ 
/*  77 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  79 */       this.m_logger.error("This user does not have permission to view the admin pages");
/*  80 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  84 */     int iFilterType = getIntParameter(a_request, "type");
/*     */ 
/*  87 */     FilterForm form = (FilterForm)a_form;
/*  88 */     form.setType(iFilterType);
/*  89 */     form.setDefaultFilter(this.m_filterManager.getDefaultFilter(a_transaction));
/*     */ 
/*  91 */     if (form.getType() == 1)
/*     */     {
/*  93 */       forward = a_mapping.findForward("Filter");
/*  94 */       form.setAllLinkedFilters(this.m_filterManager.getAllCategoryFilters(a_transaction, userProfile));
/*  95 */       form.setFilters(this.m_filterManager.getGlobalFilters(a_transaction, userProfile));
/*     */     }
/*     */     else
/*     */     {
/*  99 */       forward = a_mapping.findForward("Template");
/* 100 */       form.setFilters(this.m_filterManager.getTemplates(a_transaction, userProfile));
/*     */     }
/*     */ 
/* 103 */     return forward;
/*     */   }
/*     */ 
/*     */   public void setFilterManager(FilterManager a_filterManager)
/*     */   {
/* 108 */     this.m_filterManager = a_filterManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.filter.action.ViewManageFiltersAction
 * JD-Core Version:    0.6.0
 */