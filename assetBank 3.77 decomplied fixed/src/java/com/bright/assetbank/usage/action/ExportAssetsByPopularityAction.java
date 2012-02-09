/*     */ package com.bright.assetbank.usage.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.synchronise.form.ExportForm;
/*     */ import com.bright.assetbank.usage.bean.TotalsReport;
/*     */ import com.bright.assetbank.usage.service.UsageReportManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
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
/*     */ public class ExportAssetsByPopularityAction extends BTransactionAction
/*     */ {
/*  50 */   private UsageReportManager m_usageReportManager = null;
/*     */ 
/*     */   public final ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  69 */     this.m_logger.debug("In ViewRecentAssetsAction.execute");
/*  70 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  71 */     ExportForm form = (ExportForm)a_form;
/*     */ 
/*  73 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  75 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  78 */     int iNumToExport = getIntParameter(a_request, "numAssets");
/*     */ 
/*  80 */     if ((iNumToExport <= 0) || (iNumToExport > 1000))
/*     */     {
/*  82 */       iNumToExport = 100;
/*     */     }
/*     */ 
/*  86 */     TotalsReport totals = null;
/*     */ 
/*  88 */     int iReportType = getIntParameter(a_request, "reportType");
/*     */ 
/*  90 */     switch (iReportType)
/*     */     {
/*     */     case 1:
/*  94 */       totals = this.m_usageReportManager.getMostViewedAssets(a_dbTransaction, 0, iNumToExport);
/*  95 */       break;
/*     */     case 2:
/*  99 */       totals = this.m_usageReportManager.getLeastViewedAssets(a_dbTransaction, 0, iNumToExport);
/* 100 */       break;
/*     */     case 3:
/* 104 */       totals = this.m_usageReportManager.getMostDownloadedAssets(a_dbTransaction, 0, iNumToExport);
/* 105 */       break;
/*     */     case 4:
/* 109 */       totals = this.m_usageReportManager.getLeastDownloadedAssets(a_dbTransaction, 0, iNumToExport);
/*     */     }
/*     */ 
/* 114 */     Vector ids = totals.getAssetIds();
/*     */ 
/* 117 */     a_request.getSession().setAttribute("ExportAssetIds", ids);
/*     */ 
/* 119 */     form.setNumAssets(ids.size());
/*     */ 
/* 121 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setUsageReportManager(UsageReportManager usageReportManager)
/*     */   {
/* 126 */     this.m_usageReportManager = usageReportManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.ExportAssetsByPopularityAction
 * JD-Core Version:    0.6.0
 */