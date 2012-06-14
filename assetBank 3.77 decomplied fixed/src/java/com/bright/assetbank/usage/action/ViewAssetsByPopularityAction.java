/*     */ package com.bright.assetbank.usage.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.search.form.BaseSearchForm;
/*     */ import com.bright.assetbank.usage.bean.TotalsReport;
/*     */ import com.bright.assetbank.usage.service.UsageReportManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewAssetsByPopularityAction extends BTransactionAction
/*     */ {
/*  50 */   private UsageReportManager m_usageReportManager = null;
/*  51 */   private IAssetManager m_assetManager = null;
/*     */ 
/*     */   public final ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  70 */     this.m_logger.debug("In ViewAssetsByPopularityAction.execute");
/*  71 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  72 */     BaseSearchForm form = (BaseSearchForm)a_form;
/*     */ 
/*  75 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  77 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  80 */     int iNumToShow = getIntParameter(a_request, "numAssets");
/*  81 */     TotalsReport totals = null;
/*     */ 
/*  84 */     int iPageIndex = getIntParameter(a_request, "page");
/*     */ 
/*  86 */     int iPageSize = getIntParameter(a_request, "pageSize");
/*     */ 
/*  89 */     if ((iPageIndex < 0) || (iPageSize <= 0))
/*     */     {
/*  91 */       iPageIndex = 0;
/*     */     }
/*     */ 
/*  95 */     if (iPageSize <= 0)
/*     */     {
/*  97 */       iPageSize = iNumToShow;
/*     */     }
/*     */ 
/* 100 */     int iReportType = getIntParameter(a_request, "reportType");
/* 101 */     int iStartIndex = iPageIndex * iPageSize;
/* 102 */     int iNumResults = Math.min(iPageSize, iNumToShow - iPageIndex * iPageSize);
/*     */ 
/* 104 */     switch (iReportType)
/*     */     {
/*     */     case 1:
/* 108 */       totals = this.m_usageReportManager.getMostViewedAssets(a_dbTransaction, iStartIndex, iNumResults);
/* 109 */       break;
/*     */     case 2:
/* 113 */       totals = this.m_usageReportManager.getLeastViewedAssets(a_dbTransaction, iStartIndex, iNumResults);
/* 114 */       break;
/*     */     case 3:
/* 118 */       totals = this.m_usageReportManager.getMostDownloadedAssets(a_dbTransaction, iStartIndex, iNumResults);
/* 119 */       break;
/*     */     case 4:
/* 123 */       totals = this.m_usageReportManager.getLeastDownloadedAssets(a_dbTransaction, iStartIndex, iNumResults);
/* 124 */       break;
/*     */     default:
/* 128 */       form.addError("Please select a report.");
/* 129 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 133 */     Vector assets = this.m_assetManager.getAssetsForViewByPopularity(a_dbTransaction, totals.getAssetIds(), true);
/*     */ 
/* 135 */     form.getSearchResults().setSearchResults(assets);
/*     */ 
/* 137 */     if (iPageSize > 0)
/*     */     {
/* 139 */       form.getSearchResults().setPageSize(iPageSize);
/*     */     }
/* 141 */     if (iPageIndex > 0)
/*     */     {
/* 143 */       form.getSearchResults().setPageIndex(iPageIndex);
/*     */     }
/*     */ 
/* 147 */     iNumToShow = Math.min(iNumToShow, this.m_assetManager.getTotalAssetCount(a_dbTransaction));
/*     */ 
/* 149 */     form.getSearchResults().setNumResults(iNumToShow);
/*     */ 
/* 151 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager assetManager)
/*     */   {
/* 156 */     this.m_assetManager = assetManager;
/*     */   }
/*     */ 
/*     */   public void setUsageReportManager(UsageReportManager usageReportManager)
/*     */   {
/* 161 */     this.m_usageReportManager = usageReportManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.ViewAssetsByPopularityAction
 * JD-Core Version:    0.6.0
 */