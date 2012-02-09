/*     */ package com.bright.assetbank.report.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.report.form.SearchReportForm;
/*     */ import com.bright.assetbank.report.service.SearchReportManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.BrightDateFormat;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class DownloadSearchReportAction extends Bn2Action
/*     */   implements AssetBankConstants
/*     */ {
/*  51 */   private SearchReportManager m_searchReportManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  66 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  68 */     SearchReportForm form = (SearchReportForm)a_form;
/*     */ 
/*  71 */     if ((!userProfile.getIsAdmin()) && (!userProfile.checkForRolePermission()))
/*     */     {
/*  73 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  77 */     Date dtStartDate = null;
/*  78 */     Date dtEndDate = null;
/*  79 */     BrightDateFormat bdf = AssetBankSettings.getStandardDateFormat();
/*     */     try
/*     */     {
/*  83 */       if (StringUtil.stringIsPopulated(form.getStartDateString()))
/*     */       {
/*  85 */         dtStartDate = bdf.parse(form.getStartDateString());
/*  86 */         form.setStartDate(dtStartDate);
/*     */       }
/*     */ 
/*  89 */       if (StringUtil.stringIsPopulated(form.getEndDateString()))
/*     */       {
/*  91 */         dtEndDate = bdf.parse(form.getEndDateString());
/*  92 */         form.setEndDate(dtEndDate);
/*     */       }
/*     */     }
/*     */     catch (ParseException pe)
/*     */     {
/*  97 */       this.m_logger.error("ViewSearchReportAction.execute: Error parsing dates ", pe);
/*     */     }
/*     */ 
/* 101 */     Vector vResults = this.m_searchReportManager.getSearchReport(dtStartDate, dtEndDate, form.getGroupedReport(), form.getSuccessType());
/*     */ 
/* 103 */     String sFilename = null;
/*     */ 
/* 105 */     if ((vResults != null) && (vResults.size() > 0))
/*     */     {
/* 108 */       sFilename = this.m_searchReportManager.createSearchReportFile(vResults, form.getStartDate(), form.getEndDate(), form.getGroupedReport(), form.getSuccessType() == 1);
/*     */     }
/*     */ 
/* 115 */     a_request.setAttribute("downloadFile", FileUtil.encryptFilepath(sFilename));
/* 116 */     a_request.setAttribute("downloadFilename", "search_report.xls");
/*     */ 
/* 119 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setSearchReportManager(SearchReportManager a_searchReportManager)
/*     */   {
/* 124 */     this.m_searchReportManager = a_searchReportManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.report.action.DownloadSearchReportAction
 * JD-Core Version:    0.6.0
 */