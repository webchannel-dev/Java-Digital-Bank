/*     */ package com.bright.assetbank.batch.update.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.batch.update.constant.UpdateSettings;
/*     */ import com.bright.assetbank.batch.update.service.BatchUpdateController;
/*     */ import com.bright.assetbank.batch.update.service.BatchUpdateControllerImpl;
/*     */ import com.bright.assetbank.batch.update.service.UpdateManager;
/*     */ import com.bright.assetbank.search.action.SearchAction;
/*     */ import com.bright.assetbank.search.form.BaseSearchForm;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.search.util.SearchUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.text.ParseException;
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
/*     */ public class CreateNewBatchAction extends SearchAction
/*     */   implements MessageConstants
/*     */ {
/*  74 */   private UpdateManager m_updateManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  83 */     SearchQuery searchCriteria = null;
/*  84 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  85 */     BaseSearchForm searchForm = (BaseSearchForm)a_form;
/*     */ 
/*  87 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  89 */       this.m_logger.debug("This user does not have permission to view the batch update page");
/*  90 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  94 */     if (!searchForm.getHasErrors())
/*     */     {
/*  96 */       boolean bUseCache = getIntParameter(a_request, "cachedCriteria") == 1;
/*     */ 
/*  98 */       if ((!bUseCache) || (userProfile.getSearchCriteria() == null))
/*     */       {
/*     */         try
/*     */         {
/* 103 */           searchCriteria = getNewSearchCriteria(a_dbTransaction, searchForm, a_request);
/* 104 */           userProfile.setSearchCriteria(searchCriteria);
/*     */         }
/*     */         catch (ParseException pe)
/*     */         {
/* 108 */           searchForm.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationDateFormat", userProfile.getCurrentLanguage()).getBody());
/* 109 */           return a_mapping.findForward("Failure");
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 114 */         searchCriteria = userProfile.getSearchCriteria();
/*     */       }
/*     */ 
/* 118 */       if ((!searchForm.getHasErrors()) && (!bUseCache) && (!searchCriteriaSpecified(searchForm, searchCriteria)))
/*     */       {
/* 121 */         searchForm.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationSearchCriteria", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */ 
/* 125 */       validateMandatoryFields(searchForm, a_request);
/*     */ 
/* 128 */       if (searchForm.getHasErrors())
/*     */       {
/* 130 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/* 134 */       BatchUpdateControllerImpl batchUpdateController = createNewBatchUpdateController(this.m_updateManager, userProfile);
/*     */ 
/* 137 */       batchUpdateController.setFinishedUrl(a_request.getParameter("finishedUrl"));
/*     */ 
/* 140 */       String sCancelURL = a_request.getParameter("cancelUrl");
/* 141 */       if ((sCancelURL == null) || (sCancelURL.length() == 0))
/*     */       {
/* 143 */         sCancelURL = a_request.getParameter("finishedUrl");
/*     */       }
/*     */ 
/* 146 */       batchUpdateController.setCancelUrl(sCancelURL);
/*     */ 
/* 149 */       batchUpdateController.startNewBatchUpdate(null, userProfile.getUser().getId());
/*     */ 
/* 152 */       batchUpdateController.setApproval(false);
/* 153 */       batchUpdateController.setUnsubmitted(false);
/* 154 */       batchUpdateController.setOwner(false);
/*     */ 
/* 156 */       int iPageIndex = 0;
/* 157 */       int iMaxBatchSize = UpdateSettings.getMaxBatchUpdateResults();
/* 158 */       int iMaxPageSize = iMaxBatchSize;
/*     */ 
/* 160 */       searchCriteria.setMaxNoOfResults(FrameworkSettings.getMaxNoOfSearchResults());
/* 161 */       SearchResults searchResults = null;
/*     */ 
/* 163 */       if (userProfile.getUserCanOnlyEditOwnFiles())
/*     */       {
/* 165 */         SearchUtil.addAddedByUserId(searchCriteria, userProfile);
/*     */       }
/*     */ 
/* 169 */       Set a_updatableCatIds = null;
/* 170 */       if (!userProfile.getIsAdmin())
/*     */       {
/* 172 */         a_updatableCatIds = userProfile.getPermissionCategoryIds(3);
/*     */       }
/*     */ 
/* 176 */       String sLangCode = userProfile.getCurrentLanguage().getCode();
/* 177 */       if (StringUtils.isNotEmpty(searchCriteria.getLanguageCode()))
/*     */       {
/* 179 */         sLangCode = searchCriteria.getLanguageCode();
/*     */       }
/*     */ 
/*     */       do
/*     */       {
/* 185 */         searchResults = this.m_searchManager.searchByPageIndex(searchCriteria, iPageIndex, iMaxPageSize, sLangCode);
/*     */ 
/* 192 */         Vector vecVisibleResults = new Vector();
/*     */ 
/* 198 */         for (int i = 0; i < searchResults.getSearchResults().size(); i++)
/*     */         {
/* 200 */           LightweightAsset asset = (LightweightAsset)searchResults.getSearchResults().get(i);
/* 201 */           Long olId = new Long(asset.getId());
/*     */ 
/* 203 */           if (userProfile.getIsAdmin())
/*     */           {
/* 205 */             vecVisibleResults.add(olId);
/*     */           }
/*     */           else
/*     */           {
/* 210 */             if (!AssetManager.userHasPermissionForAsset(asset, a_updatableCatIds))
/*     */               continue;
/* 212 */             vecVisibleResults.add(olId);
/*     */           }
/*     */         }
/*     */ 
/* 216 */         batchUpdateController.addToBatchUpdate(vecVisibleResults);
/*     */ 
/* 218 */         batchUpdateController.setNumberMatchingSearch(searchResults.getNumResults());
/*     */ 
/* 221 */         iPageIndex++;
/*     */       }
/*     */ 
/* 225 */       while ((searchResults != null) && (batchUpdateController.getNumberInBatch() < iMaxBatchSize) && (searchResults.getNumResultsPopulated() == iMaxPageSize));
/*     */     }
/*     */ 
/* 230 */     ActionForward afForward = createRedirectingForward("", a_mapping, "Success");
/* 231 */     return afForward;
/*     */   }
/*     */ 
/*     */   public static BatchUpdateControllerImpl createNewBatchUpdateController(UpdateManager m_updateManager, ABUserProfile userProfile)
/*     */   {
/* 244 */     BatchUpdateController controller = userProfile.getBatchUpdateController();
/*     */ 
/* 247 */     if ((controller != null) && (controller.getType() != "BATCHUPDATE"))
/*     */     {
/* 249 */       controller.cancelCurrentBatchUpdate();
/* 250 */       controller = null;
/*     */     }
/*     */ 
/* 253 */     if (controller == null)
/*     */     {
/* 256 */       controller = m_updateManager.createNewBatchUpdateController();
/* 257 */       userProfile.setBatchUpdateController(controller);
/*     */     }
/*     */ 
/* 260 */     BatchUpdateControllerImpl batchUpdateController = (BatchUpdateControllerImpl)controller;
/*     */ 
/* 262 */     return batchUpdateController;
/*     */   }
/*     */ 
/*     */   public void setUpdateManager(UpdateManager a_updateManager)
/*     */   {
/* 268 */     this.m_updateManager = a_updateManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.CreateNewBatchAction
 * JD-Core Version:    0.6.0
 */