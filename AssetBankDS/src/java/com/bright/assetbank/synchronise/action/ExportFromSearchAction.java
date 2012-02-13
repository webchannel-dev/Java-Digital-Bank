/*     */ package com.bright.assetbank.synchronise.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.synchronise.form.ExportForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ExportFromSearchAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  54 */   protected MultiLanguageSearchManager m_searchManager = null;
/*  55 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  78 */     ActionForward forward = null;
/*  79 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  80 */     ExportForm form = (ExportForm)a_form;
/*     */ 
/*  82 */     if ((!userProfile.getIsAdmin()) && ((!AssetBankSettings.getOrgUnitAdminsCanExport()) || (!userProfile.getIsOrgUnitAdmin())))
/*     */     {
/*  84 */       this.m_logger.error("ExportFromSearchAction.execute : User does not have admin permission : " + userProfile);
/*  85 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  88 */     SearchQuery query = userProfile.getSearchCriteria();
/*     */ 
/*  91 */     if ((query == null) || (query.isEmpty()))
/*     */     {
/*  93 */       this.m_logger.error("ExportFromSearchAction.execute : No search criteria found : " + userProfile);
/*  94 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  97 */     int iMaxResults = query.getMaxNoOfResults();
/*  98 */     query.setMaxNoOfResults(2147483647);
/*     */ 
/* 101 */     SearchResults searchResults = getSearchManager().search(query, userProfile.getCurrentLanguage().getCode());
/*     */ 
/* 103 */     query.setMaxNoOfResults(iMaxResults);
/*     */ 
/* 106 */     if (searchResults.getNumResults() == 0)
/*     */     {
/* 108 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "noSearchResultsToExport", userProfile.getCurrentLanguage()).getBody());
/* 109 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 113 */     Vector results = searchResults.getSearchResults();
/* 114 */     Vector ids = new Vector(results.size());
/* 115 */     for (int i = 0; i < results.size(); i++)
/*     */     {
/* 117 */       ids.add(Long.valueOf(((LightweightAsset)results.get(i)).getId()));
/*     */     }
/*     */ 
/* 121 */     a_request.getSession().setAttribute("ExportAssetIds", ids);
/*     */ 
/* 123 */     form.setNumAssets(ids.size());
/*     */ 
/* 125 */     forward = a_mapping.findForward("Success");
/* 126 */     return forward;
/*     */   }
/*     */ 
/*     */   public MultiLanguageSearchManager getSearchManager()
/*     */   {
/* 131 */     return this.m_searchManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_sSearchManager)
/*     */   {
/* 136 */     this.m_searchManager = a_sSearchManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 141 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.action.ExportFromSearchAction
 * JD-Core Version:    0.6.0
 */