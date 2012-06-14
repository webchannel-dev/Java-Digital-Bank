/*     */ package com.bright.assetbank.search.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.search.bean.SavedSearch;
/*     */ import com.bright.assetbank.search.service.SavedSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.constant.SearchConstants;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class RunSavedSearchRssAction extends BTransactionAction
/*     */   implements SearchConstants
/*     */ {
/*  46 */   private SavedSearchManager m_savedSearchManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  55 */     ActionForward forward = null;
/*  56 */     String sName = a_request.getParameter("name");
/*  57 */     long lUserId = getLongParameter(a_request, "userId");
/*     */ 
/*  59 */     SavedSearch search = this.m_savedSearchManager.getSavedSearch(a_transaction, sName, lUserId);
/*  60 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  62 */     if ((search != null) && (search.isRssFeed()) && (search.getCriteria() != null))
/*     */     {
/*  64 */       userProfile.setSearchCriteria(search.getCriteria());
/*     */ 
/*  67 */       a_request.setAttribute("rssSearch", Boolean.TRUE);
/*     */ 
/*  69 */       String sForwardKey = null;
/*     */ 
/*  71 */       if (search.isBuilderSearch())
/*     */       {
/*  73 */         sForwardKey = "SearchBuilder";
/*     */       }
/*     */       else
/*     */       {
/*  77 */         sForwardKey = "SearchForm";
/*     */       }
/*     */ 
/*  80 */       forward = new ActionForward(a_mapping.findForward(sForwardKey).getPath() + "?" + "cachedCriteria" + "=1" + "&" + "sortAttributeId" + "=" + search.getSortAttributeId() + "&" + "sortDescending" + "=" + search.isDescending() + "&" + "searchPage" + "=" + "savedSearches", false);
/*     */     }
/*     */     else
/*     */     {
/*  89 */       forward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  92 */     return forward;
/*     */   }
/*     */ 
/*     */   public void setSavedSearchManager(SavedSearchManager savedSearchManager)
/*     */   {
/*  97 */     this.m_savedSearchManager = savedSearchManager;
/*     */   }
/*     */ 
/*     */   public boolean getAvailableNotLoggedIn()
/*     */   {
/* 102 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.RunSavedSearchRssAction
 * JD-Core Version:    0.6.0
 */