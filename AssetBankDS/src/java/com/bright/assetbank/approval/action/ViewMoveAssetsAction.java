/*     */ package com.bright.assetbank.approval.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.approval.constant.AssetApprovalConstants;
/*     */ import com.bright.assetbank.approval.form.MoveAssetsForm;
/*     */ import com.bright.assetbank.search.bean.BaseSearchQuery;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewMoveAssetsAction extends BTransactionAction
/*     */   implements AssetApprovalConstants, AssetBankConstants
/*     */ {
/*  54 */   private AssetCategoryManager m_categoryManager = null;
/*  55 */   private MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  82 */     ActionForward afForward = null;
/*     */ 
/*  84 */     MoveAssetsForm form = (MoveAssetsForm)a_form;
/*  85 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  87 */     if ((userProfile.getIsAdmin()) || (userProfile.getIsInitialOrgUnitAdmin()))
/*     */     {
/*  89 */       long lFromCatId = getLongParameter(a_request, "fromCatId");
/*  90 */       long lToCatId = getLongParameter(a_request, "toCatId");
/*     */ 
/*  92 */       if ((lFromCatId > 0L) && (lToCatId > 0L))
/*     */       {
/*  95 */         form.setFromCategory(this.m_categoryManager.getCategory(a_dbTransaction, 2L, lFromCatId));
/*  96 */         form.setToCategory(this.m_categoryManager.getCategory(a_dbTransaction, 2L, lToCatId));
/*     */ 
/*  99 */         BaseSearchQuery searchCriteria = new SearchCriteria();
/* 100 */         searchCriteria.setCategoryIds(String.valueOf(lFromCatId));
/*     */ 
/* 102 */         SearchResults sr = this.m_searchManager.search(searchCriteria);
/* 103 */         Vector vecItems = sr.getSearchResults();
/*     */ 
/* 105 */         form.setAssetsToMove(vecItems);
/* 106 */         afForward = a_mapping.findForward("Success");
/*     */       }
/*     */       else
/*     */       {
/* 110 */         afForward = a_mapping.findForward("Failure");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 115 */       afForward = a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 118 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_categoryManager)
/*     */   {
/* 123 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 128 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.action.ViewMoveAssetsAction
 * JD-Core Version:    0.6.0
 */