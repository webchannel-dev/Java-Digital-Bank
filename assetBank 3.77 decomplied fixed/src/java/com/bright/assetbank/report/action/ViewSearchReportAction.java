/*     */ package com.bright.assetbank.report.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.report.form.SearchReportForm;
/*     */ import com.bright.assetbank.report.service.SearchReportManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.BrightDateFormat;
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
/*     */ public class ViewSearchReportAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  53 */   private SearchReportManager m_searchReportManager = null;
/*  54 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  63 */     ActionForward afForward = null;
/*  64 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  66 */     SearchReportForm form = (SearchReportForm)a_form;
/*     */ 
/*  69 */     if ((!userProfile.getIsAdmin()) && (!userProfile.checkForRolePermission()))
/*     */     {
/*  71 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  74 */     form.validate(a_request, userProfile, a_dbTransaction, this.m_listManager);
/*     */ 
/*  76 */     if (form.getHasErrors())
/*     */     {
/*  78 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  82 */     Date dtStartDate = null;
/*  83 */     Date dtEndDate = null;
/*  84 */     BrightDateFormat bdf = AssetBankSettings.getStandardDateFormat();
/*     */     try
/*     */     {
/*  88 */       if (StringUtil.stringIsPopulated(form.getStartDateString()))
/*     */       {
/*  90 */         dtStartDate = bdf.parse(form.getStartDateString());
/*  91 */         form.setStartDate(dtStartDate);
/*     */       }
/*     */ 
/*  94 */       if (StringUtil.stringIsPopulated(form.getEndDateString()))
/*     */       {
/*  96 */         dtEndDate = bdf.parse(form.getEndDateString());
/*  97 */         form.setEndDate(dtEndDate);
/*     */       }
/*     */     }
/*     */     catch (ParseException pe)
/*     */     {
/* 102 */       this.m_logger.error("ViewSearchReportAction.execute: Error parsing dates ", pe);
/*     */     }
/*     */ 
/* 106 */     Vector vResults = this.m_searchReportManager.getSearchReport(dtStartDate, dtEndDate, form.getGroupedReport(), form.getSuccessType());
/*     */ 
/* 109 */     form.setReportLines(vResults);
/*     */ 
/* 112 */     afForward = a_mapping.findForward("Success");
/* 113 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setSearchReportManager(SearchReportManager a_searchReportManager)
/*     */   {
/* 118 */     this.m_searchReportManager = a_searchReportManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 123 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.report.action.ViewSearchReportAction
 * JD-Core Version:    0.6.0
 */