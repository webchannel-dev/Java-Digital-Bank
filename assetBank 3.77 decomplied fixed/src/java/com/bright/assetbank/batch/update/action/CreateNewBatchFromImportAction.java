/*     */ package com.bright.assetbank.batch.update.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.batch.update.constant.UpdateSettings;
/*     */ import com.bright.assetbank.batch.update.service.BatchUpdateControllerImpl;
/*     */ import com.bright.assetbank.batch.update.service.UpdateManager;
/*     */ import com.bright.assetbank.search.action.SearchAction;
/*     */ import com.bright.assetbank.search.bean.BaseSearchQuery;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class CreateNewBatchFromImportAction extends SearchAction
/*     */ {
/*  58 */   private UpdateManager m_updateManager = null;
/*     */ 
/*     */   public void setUpdateManager(UpdateManager a_updateManager) {
/*  61 */     this.m_updateManager = a_updateManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  73 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  75 */       this.m_logger.debug("This user does not have permission to view the batch update page");
/*  76 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  80 */     String sTimestamp = a_request.getParameter("batchid");
/*  81 */     if ((!StringUtil.stringIsPopulated(sTimestamp)) || (!StringUtil.stringIsLong(sTimestamp)))
/*     */     {
/*  83 */       throw new Bn2Exception("CreateNewBulkUpdateFromImportAction: no valid batch id in request.");
/*     */     }
/*     */ 
/*  86 */     boolean bIncompleteOnly = false;
/*  87 */     String sIncompleteOnly = a_request.getParameter("incomplete");
/*  88 */     if ((sIncompleteOnly != null) && (sIncompleteOnly.equalsIgnoreCase("true")))
/*     */     {
/*  90 */       bIncompleteOnly = true;
/*     */     }
/*     */ 
/*  93 */     BaseSearchQuery searchCriteria = new SearchCriteria();
/*  94 */     searchCriteria.setBulkUpload(Long.parseLong(sTimestamp));
/*     */ 
/*  98 */     BatchUpdateControllerImpl batchUpdateController = CreateNewBatchAction.createNewBatchUpdateController(this.m_updateManager, userProfile);
/*     */ 
/* 101 */     batchUpdateController.setCancelUrl(a_request.getParameter("cancelUrl"));
/* 102 */     batchUpdateController.setFinishedUrl(a_request.getParameter("finishedUrl"));
/*     */ 
/* 105 */     batchUpdateController.startNewBatchUpdate(null, userProfile.getUser().getId());
/*     */ 
/* 108 */     batchUpdateController.setApproval(false);
/* 109 */     batchUpdateController.setUnsubmitted(false);
/* 110 */     batchUpdateController.setOwner(false);
/*     */ 
/* 112 */     int iPageIndex = 0;
/* 113 */     int iMaxBatchSize = UpdateSettings.getMaxBatchUpdateResults();
/* 114 */     int iMaxPageSize = iMaxBatchSize;
/*     */ 
/* 116 */     searchCriteria.setMaxNoOfResults(FrameworkSettings.getMaxNoOfSearchResults());
/*     */ 
/* 119 */     if (bIncompleteOnly)
/*     */     {
/* 121 */       searchCriteria.setIsComplete(Boolean.FALSE);
/*     */     }
/*     */ 
/* 124 */     SearchResults searchResults = null;
/*     */ 
/* 127 */     Set a_updatableCatIds = null;
/* 128 */     if (!userProfile.getIsAdmin())
/*     */     {
/* 130 */       a_updatableCatIds = userProfile.getPermissionCategoryIds(3);
/*     */     }
/*     */ 
/* 134 */     String sLangCode = userProfile.getCurrentLanguage().getCode();
/* 135 */     if (StringUtils.isNotEmpty(searchCriteria.getLanguageCode()))
/*     */     {
/* 137 */       sLangCode = searchCriteria.getLanguageCode();
/*     */     }
/*     */ 
/*     */     do
/*     */     {
/* 143 */       searchResults = this.m_searchManager.searchByPageIndex(searchCriteria, iPageIndex, iMaxPageSize, sLangCode);
/*     */ 
/* 150 */       Vector vecVisibleResults = new Vector();
/*     */ 
/* 156 */       for (int i = 0; i < searchResults.getSearchResults().size(); i++)
/*     */       {
/* 158 */         LightweightAsset asset = (LightweightAsset)searchResults.getSearchResults().get(i);
/* 159 */         Long olId = new Long(asset.getId());
/*     */ 
/* 161 */         if (userProfile.getIsAdmin())
/*     */         {
/* 163 */           vecVisibleResults.add(olId);
/*     */         }
/*     */         else
/*     */         {
/* 168 */           if (!AssetManager.userHasPermissionForAsset(asset, a_updatableCatIds))
/*     */             continue;
/* 170 */           vecVisibleResults.add(olId);
/*     */         }
/*     */       }
/*     */ 
/* 174 */       batchUpdateController.addToBatchUpdate(vecVisibleResults);
/*     */ 
/* 176 */       batchUpdateController.setNumberMatchingSearch(searchResults.getNumResults());
/*     */ 
/* 179 */       iPageIndex++;
/*     */     }
/*     */ 
/* 183 */     while ((searchResults != null) && (batchUpdateController.getNumberInBatch() < iMaxBatchSize) && (searchResults.getNumResultsPopulated() == iMaxPageSize));
/*     */ 
/* 187 */     ActionForward afForward = createRedirectingForward("", a_mapping, "Success");
/* 188 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.CreateNewBatchFromImportAction
 * JD-Core Version:    0.6.0
 */