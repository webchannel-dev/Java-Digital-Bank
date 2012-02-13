/*     */ package com.bright.assetbank.report.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
import com.bright.assetbank.application.bean.AssetAuditLogEntry;
/*     */ import com.bright.assetbank.application.bean.AssetAuditLogEntry.DateComparatorAsc;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetLogManager;
/*     */ import com.bright.assetbank.report.form.AuditReportForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.BrightDateFormat;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.text.ParseException;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewAuditReportAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  57 */   protected ListManager m_listManager = null;
/*  58 */   protected AssetLogManager m_assetLogManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  67 */     ActionForward afForward = null;
/*  68 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  70 */     AuditReportForm form = (AuditReportForm)a_form;
/*     */ 
/*  73 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  75 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  78 */     form.validate(a_request, userProfile, a_dbTransaction, this.m_listManager);
/*     */ 
/*  80 */     if (form.getHasErrors())
/*     */     {
/*  82 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  86 */     Date dtStartDate = null;
/*  87 */     Date dtEndDate = null;
/*  88 */     String sUsername = "";
/*  89 */     String sIpAddress = "";
/*     */ 
/*  91 */     BrightDateFormat bdf = AssetBankSettings.getStandardDateFormat();
/*     */     try
/*     */     {
/*  95 */       if (StringUtil.stringIsPopulated(form.getStartDateString()))
/*     */       {
/*  97 */         dtStartDate = bdf.parse(form.getStartDateString());
/*  98 */         form.setStartDate(dtStartDate);
/*     */       }
/*     */ 
/* 101 */       if (StringUtil.stringIsPopulated(form.getEndDateString()))
/*     */       {
/* 103 */         dtEndDate = bdf.parse(form.getEndDateString());
/* 104 */         Calendar calendar = Calendar.getInstance();
/* 105 */         calendar.add(5, 1);
/* 106 */         dtEndDate = calendar.getTime();
/* 107 */         form.setEndDate(dtEndDate);
/*     */       }
/*     */     }
/*     */     catch (ParseException pe)
/*     */     {
/* 112 */       this.m_logger.error("ViewSearchReportAction.execute: Error parsing dates ", pe);
/*     */     }
/*     */ 
/* 115 */     if (StringUtil.stringIsPopulated(form.getUsername()))
/*     */     {
/* 117 */       sUsername = form.getUsername();
/*     */     }
/*     */ 
/* 120 */     if (StringUtil.stringIsPopulated(form.getIpAddress()))
/*     */     {
/* 122 */       sIpAddress = form.getIpAddress();
/*     */     }
/*     */ 
/* 125 */     Vector vResults = this.m_assetLogManager.getAssetAuditLog(a_dbTransaction, 0L, 0L, dtStartDate, dtEndDate, sUsername, sIpAddress, form.getIncludeViewsDownloads(), 0L);
/*     */ 
/* 128 */     if ((vResults != null) && (vResults.size() > 0))
/*     */     {
/* 130 */       Collections.sort(vResults, new AssetAuditLogEntry.DateComparatorAsc());
/*     */     }
/*     */ 
/* 134 */     form.setReportLines(vResults);
/*     */ 
/* 137 */     afForward = a_mapping.findForward("Success");
/* 138 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 144 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setAssetLogManager(AssetLogManager a_assetLogManager)
/*     */   {
/* 149 */     this.m_assetLogManager = a_assetLogManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.report.action.ViewAuditReportAction
 * JD-Core Version:    0.6.0
 */