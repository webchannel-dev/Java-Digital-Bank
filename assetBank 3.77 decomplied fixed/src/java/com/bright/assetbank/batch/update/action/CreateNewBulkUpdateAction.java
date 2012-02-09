/*     */ package com.bright.assetbank.batch.update.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.batch.update.constant.UpdateSettings;
/*     */ import com.bright.assetbank.batch.update.service.BatchUpdateController;
/*     */ import com.bright.assetbank.batch.update.service.UpdateManager;
/*     */ import com.bright.assetbank.search.action.SearchAction;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.search.util.SearchUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public abstract class CreateNewBulkUpdateAction extends SearchAction
/*     */   implements MessageConstants
/*     */ {
/*  59 */   private UpdateManager m_updateManager = null;
/*     */ 
/*  65 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setUpdateManager(UpdateManager a_updateManager)
/*     */   {
/*  62 */     this.m_updateManager = a_updateManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  68 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public abstract ActionForward execute(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, DBTransaction paramDBTransaction)
/*     */     throws Bn2Exception;
/*     */ 
/*     */   protected boolean createControllerFromCriteria(HttpServletRequest a_request, SearchQuery a_searchQuery, ABUserProfile a_userProfile, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  83 */     BatchUpdateController existingController = a_userProfile.getBatchUpdateController();
/*  84 */     if (existingController != null)
/*     */     {
/*  86 */       existingController.cancelCurrentBatchUpdate();
/*     */     }
/*     */ 
/*  90 */     BatchUpdateController controller = this.m_updateManager.createNewBulkUpdateController();
/*  91 */     a_userProfile.setBatchUpdateController(controller);
/*     */ 
/*  94 */     controller.setCancelUrl(a_request.getParameter("cancelUrl"));
/*     */ 
/*  97 */     controller.startNewBatchUpdate(null, a_userProfile.getUser().getId());
/*     */ 
/* 101 */     int iPageIndex = 0;
/* 102 */     int iMaxResults = UpdateSettings.getMaxBulkUpdateResults();
/* 103 */     a_searchQuery.setMaxNoOfResults(iMaxResults);
/*     */ 
/* 105 */     SearchResults searchResults = null;
/*     */ 
/* 108 */     Set updatableCatIds = null;
/* 109 */     if (!a_userProfile.getIsAdmin())
/*     */     {
/* 111 */       updatableCatIds = a_userProfile.getPermissionCategoryIds(3);
/*     */     }
/*     */ 
/* 115 */     String sLangCode = a_userProfile.getCurrentLanguage().getCode();
/* 116 */     if (StringUtils.isNotEmpty(a_searchQuery.getLanguageCode()))
/*     */     {
/* 118 */       sLangCode = a_searchQuery.getLanguageCode();
/*     */     }
/*     */ 
/* 121 */     if (a_userProfile.getUserCanOnlyEditOwnFiles())
/*     */     {
/* 123 */       SearchUtil.addAddedByUserId(a_searchQuery, a_userProfile);
/*     */     }
/*     */ 
/* 128 */     boolean bAssetsLocked = false;
/*     */     do
/*     */     {
/* 132 */       searchResults = this.m_searchManager.searchByPageIndex(a_searchQuery, iPageIndex, iMaxResults, sLangCode);
/*     */ 
/* 135 */       Vector vecVisibleResults = null;
/*     */ 
/* 137 */       if (a_userProfile.getIsAdmin())
/*     */       {
/* 139 */         vecVisibleResults = searchResults.getSearchResults();
/*     */       }
/*     */       else
/*     */       {
/* 143 */         vecVisibleResults = new Vector();
/*     */ 
/* 146 */         for (int i = 0; i < searchResults.getSearchResults().size(); i++)
/*     */         {
/* 148 */           LightweightAsset asset = (LightweightAsset)searchResults.getSearchResults().get(i);
/*     */ 
/* 151 */           if (!AssetManager.userHasPermissionForAsset(asset, updatableCatIds))
/*     */             continue;
/* 153 */           vecVisibleResults.add(asset);
/*     */         }
/*     */       }
/*     */ 
/* 157 */       bAssetsLocked = controller.addToBatchUpdate(vecVisibleResults);
/*     */ 
/* 159 */       if (bAssetsLocked)
/*     */       {
/*     */         break;
/*     */       }
/*     */ 
/* 164 */       controller.setNumberMatchingSearch(searchResults.getNumResults());
/*     */ 
/* 167 */       iPageIndex++;
/*     */     }
/*     */ 
/* 171 */     while ((searchResults != null) && (controller.getNumberInBatch() < iMaxResults) && (searchResults.getNumResultsPopulated() == iMaxResults));
/*     */ 
/* 174 */     if ((bAssetsLocked) || (controller.getNumberInBatch() == 0))
/*     */     {
/* 176 */       controller.cancelCurrentBatchUpdate();
/*     */     }
/*     */ 
/* 179 */     return bAssetsLocked;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.CreateNewBulkUpdateAction
 * JD-Core Version:    0.6.0
 */