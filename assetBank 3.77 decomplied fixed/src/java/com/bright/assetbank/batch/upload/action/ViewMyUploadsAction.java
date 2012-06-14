/*     */ package com.bright.assetbank.batch.upload.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.batch.upload.form.MyUploadsForm;
/*     */ import com.bright.assetbank.batch.upload.service.ImportManager;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workset.service.AssetWorksetManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewMyUploadsAction extends BTransactionAction
/*     */ {
/*     */   ImportManager m_importManager;
/*     */   private MultiLanguageSearchManager m_searchManager;
/*  46 */   private AssetWorksetManager m_assetWorksetManager = null;
/*     */ 
/*     */   public final ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  58 */     this.m_logger.debug("In ViewMyUploadsActiob.execute");
/*  59 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  61 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  63 */       this.m_logger.debug("This user does not have permission to view the admin page");
/*  64 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  67 */     int iNumDaysToInclude = AssetBankSettings.getNumberDaysMyUploads();
/*  68 */     MyUploadsForm form = (MyUploadsForm)a_form;
/*  69 */     form.setUserUploadsForDayList(this.m_importManager.getUserUploadsInfoByDay(a_dbTransaction, userProfile.getUserId(), iNumDaysToInclude, Boolean.TRUE));
/*     */ 
/*  75 */     BrightDate fromDate = BrightDate.todayPlusOffsetDays(-iNumDaysToInclude);
/*  76 */     form.setAssetsShownFromDate(fromDate);
/*     */ 
/*  79 */     SearchCriteria searchCriteria = new SearchCriteria();
/*  80 */     searchCriteria.setAddedByUserId(userProfile.getUserId());
/*  81 */     searchCriteria.setIsComplete(Boolean.FALSE);
/*  82 */     searchCriteria.setIsUnsubmitted(Boolean.FALSE);
/*  83 */     searchCriteria.addApprovalStatus(3);
/*  84 */     SearchResults searchResults = this.m_searchManager.search(searchCriteria, userProfile.getCurrentLanguage().getCode());
/*  85 */     form.setNumIncompleteAssets(searchResults.getNumResults());
/*     */ 
/*  88 */     form.setNumUnsubmittedAssets(this.m_assetWorksetManager.getNumUnsubmittedAssets(userProfile));
/*     */ 
/*  90 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setImportManager(ImportManager manager)
/*     */   {
/*  95 */     this.m_importManager = manager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager) {
/*  99 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setAssetWorksetManager(AssetWorksetManager a_aManager) {
/* 103 */     this.m_assetWorksetManager = a_aManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.upload.action.ViewMyUploadsAction
 * JD-Core Version:    0.6.0
 */