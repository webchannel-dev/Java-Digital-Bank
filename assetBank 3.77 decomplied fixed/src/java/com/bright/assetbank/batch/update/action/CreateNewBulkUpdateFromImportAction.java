/*     */ package com.bright.assetbank.batch.update.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.batch.update.service.BatchUpdateController;
/*     */ import com.bright.assetbank.search.bean.BaseSearchQuery;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.form.BaseSearchForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class CreateNewBulkUpdateFromImportAction extends CreateNewBulkUpdateAction
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  55 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  56 */     BaseSearchForm searchForm = (BaseSearchForm)a_form;
/*     */ 
/*  58 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  60 */       this.m_logger.debug("This user does not have permission to view the batch update page");
/*  61 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  65 */     String sTimestamp = a_request.getParameter("batchid");
/*  66 */     if ((!StringUtil.stringIsPopulated(sTimestamp)) || (!StringUtil.stringIsLong(sTimestamp)))
/*     */     {
/*  68 */       throw new Bn2Exception("CreateNewBulkUpdateFromImportAction: no valid batch id in request.");
/*     */     }
/*     */ 
/*  71 */     boolean bIncompleteOnly = false;
/*  72 */     String sIncompleteOnly = a_request.getParameter("incomplete");
/*  73 */     if ((sIncompleteOnly != null) && (sIncompleteOnly.equalsIgnoreCase("true")))
/*     */     {
/*  75 */       bIncompleteOnly = true;
/*     */     }
/*     */ 
/*  78 */     BaseSearchQuery searchCriteria = new SearchCriteria();
/*  79 */     searchCriteria.setBulkUpload(Long.parseLong(sTimestamp));
/*     */ 
/*  82 */     if (bIncompleteOnly)
/*     */     {
/*  84 */       searchCriteria.setIsComplete(Boolean.FALSE);
/*     */     }
/*     */ 
/*  88 */     boolean bAssetsLocked = createControllerFromCriteria(a_request, searchCriteria, userProfile, a_dbTransaction);
/*  89 */     BatchUpdateController controller = userProfile.getBatchUpdateController();
/*     */ 
/*  92 */     if (bAssetsLocked)
/*     */     {
/*  94 */       controller.cancelCurrentBatchUpdate();
/*  95 */       userProfile.setBatchUpdateController(null);
/*  96 */       searchForm.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationAssetsLocked", userProfile.getCurrentLanguage()).getBody());
/*  97 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 100 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.CreateNewBulkUpdateFromImportAction
 * JD-Core Version:    0.6.0
 */