/*     */ package com.bright.assetbank.batch.update.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.batch.update.service.BatchUpdateController;
/*     */ import com.bright.assetbank.search.form.BaseSearchForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.text.ParseException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class CreateNewBulkUpdateFromSearchAction extends CreateNewBulkUpdateAction
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  54 */     SearchQuery searchQuery = null;
/*  55 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  56 */     BaseSearchForm searchForm = (BaseSearchForm)a_form;
/*     */ 
/*  58 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  60 */       this.m_logger.debug("This user does not have permission to view the batch update page");
/*  61 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  64 */     boolean bUseCache = getIntParameter(a_request, "cachedCriteria") == 1;
/*  65 */     if (bUseCache)
/*     */     {
/*  67 */       searchQuery = userProfile.getSearchCriteria();
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/*  74 */         searchQuery = getNewSearchCriteria(a_dbTransaction, searchForm, a_request);
/*  75 */         userProfile.setSearchCriteria(searchQuery);
/*     */       }
/*     */       catch (ParseException pe)
/*     */       {
/*  79 */         searchForm.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationDateFormat", userProfile.getCurrentLanguage()).getBody());
/*  80 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/*  84 */       if ((!searchForm.getHasErrors()) && (!searchCriteriaSpecified(searchForm, searchQuery)))
/*     */       {
/*  87 */         searchForm.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationSearchCriteria", userProfile.getCurrentLanguage()).getBody());
/*  88 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/*  92 */       validateMandatoryFields(searchForm, a_request);
/*     */ 
/*  95 */       if (searchForm.getHasErrors())
/*     */       {
/*  97 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 102 */     boolean bAssetsLocked = createControllerFromCriteria(a_request, searchQuery, userProfile, a_dbTransaction);
/* 103 */     BatchUpdateController controller = userProfile.getBatchUpdateController();
/*     */ 
/* 106 */     if (bAssetsLocked)
/*     */     {
/* 108 */       controller.cancelCurrentBatchUpdate();
/* 109 */       userProfile.setBatchUpdateController(null);
/* 110 */       searchForm.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationAssetsLocked", userProfile.getCurrentLanguage()).getBody());
/* 111 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 115 */     if (controller.getNumberInBatch() == 0)
/*     */     {
/* 117 */       controller.cancelCurrentBatchUpdate();
/* 118 */       userProfile.setBatchUpdateController(null);
/* 119 */       searchForm.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationNoAssetsFound", userProfile.getCurrentLanguage()).getBody());
/* 120 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 123 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.CreateNewBulkUpdateFromSearchAction
 * JD-Core Version:    0.6.0
 */