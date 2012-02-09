/*     */ package com.bright.assetbank.search.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewItemAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  53 */   protected MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager) {
/*  56 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  85 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  86 */     ActionForward afForward = null;
/*     */ 
/*  89 */     int iIndex = getIntParameter(a_request, "index");
/*     */ 
/*  92 */     SearchQuery searchQuery = userProfile.getSearchCriteria();
/*  93 */     long lItemId = 0L;
/*  94 */     String sQueryString = null;
/*  95 */     String sForwardKey = null;
/*     */ 
/*  97 */     if (searchQuery == null)
/*     */     {
/* 100 */       lItemId = getLongParameter(a_request, "id");
/* 101 */       sQueryString = "id=" + lItemId;
/* 102 */       sForwardKey = "NoCriteria";
/*     */     }
/*     */     else
/*     */     {
/* 107 */       SearchResults results = this.m_searchManager.search(searchQuery, userProfile.getCurrentLanguage().getCode());
/*     */ 
/* 109 */       long lExpectedId = getLongParameter(a_request, "expectedAssetId");
/*     */ 
/* 111 */       if (results.getSearchResults().size() > iIndex)
/*     */       {
/* 114 */         LightweightAsset result = (LightweightAsset)results.getSearchResults().elementAt(iIndex);
/* 115 */         lItemId = result.getId();
/*     */       }
/*     */ 
/* 121 */       if ((lExpectedId > 0L) && (lExpectedId != lItemId))
/*     */       {
/* 123 */         sQueryString = "id=" + lExpectedId;
/*     */       }
/*     */       else
/*     */       {
/* 128 */         long lTotal = results.getNumResults();
/*     */ 
/* 131 */         sQueryString = "id=" + lItemId + "&" + "index" + "=" + iIndex + "&" + "total" + "=" + lTotal + "&view=viewSearchItem";
/*     */       }
/*     */ 
/* 137 */       sForwardKey = "Success";
/*     */     }
/*     */ 
/* 141 */     String sShowAdd = a_request.getParameter("showadd");
/* 142 */     if (StringUtil.stringIsPopulated(sShowAdd))
/*     */     {
/* 144 */       sQueryString = sQueryString + "&showadd=true";
/*     */     }
/*     */ 
/* 147 */     afForward = createRedirectingForward(sQueryString, a_mapping, sForwardKey);
/* 148 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.ViewItemAction
 * JD-Core Version:    0.6.0
 */