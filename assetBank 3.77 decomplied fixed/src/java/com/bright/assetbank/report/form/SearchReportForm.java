/*     */ package com.bright.assetbank.report.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class SearchReportForm extends Bn2Form
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/*  42 */   private Vector m_vecReportLines = null;
/*  43 */   private int m_iSuccessType = 0;
/*  44 */   private String m_sStartDateString = null;
/*  45 */   private String m_sEndDateString = null;
/*  46 */   private Date m_dtStartDate = null;
/*  47 */   private Date m_dtEndDate = null;
/*  48 */   private boolean m_bGroupedReport = false;
/*  49 */   private String m_sReportFilename = null;
/*     */ 
/*     */   public void setReportLines(Vector a_vecReportLines)
/*     */   {
/*  53 */     this.m_vecReportLines = a_vecReportLines;
/*     */   }
/*     */ 
/*     */   public Vector getReportLines()
/*     */   {
/*  58 */     return this.m_vecReportLines;
/*     */   }
/*     */ 
/*     */   public void setSuccessType(int a_iSuccessType)
/*     */   {
/*  63 */     this.m_iSuccessType = a_iSuccessType;
/*     */   }
/*     */ 
/*     */   public int getSuccessType()
/*     */   {
/*  68 */     return this.m_iSuccessType;
/*     */   }
/*     */ 
/*     */   public void setStartDate(Date a_dtStartDate)
/*     */   {
/*  73 */     this.m_dtStartDate = a_dtStartDate;
/*     */   }
/*     */ 
/*     */   public Date getStartDate()
/*     */   {
/*  78 */     return this.m_dtStartDate;
/*     */   }
/*     */ 
/*     */   public void setEndDate(Date a_dtEndDate)
/*     */   {
/*  83 */     this.m_dtEndDate = a_dtEndDate;
/*     */   }
/*     */ 
/*     */   public Date getEndDate()
/*     */   {
/*  88 */     return this.m_dtEndDate;
/*     */   }
/*     */ 
/*     */   public String getEndDateString()
/*     */   {
/*  94 */     return this.m_sEndDateString;
/*     */   }
/*     */ 
/*     */   public void setEndDateString(String a_sEndDateString)
/*     */   {
/* 100 */     this.m_sEndDateString = a_sEndDateString;
/*     */   }
/*     */ 
/*     */   public String getStartDateString()
/*     */   {
/* 106 */     return this.m_sStartDateString;
/*     */   }
/*     */ 
/*     */   public void setStartDateString(String a_sStartDateString)
/*     */   {
/* 112 */     this.m_sStartDateString = a_sStartDateString;
/*     */   }
/*     */ 
/*     */   public void setGroupedReport(boolean a_bGroupedReport)
/*     */   {
/* 117 */     this.m_bGroupedReport = a_bGroupedReport;
/*     */   }
/*     */ 
/*     */   public boolean getGroupedReport()
/*     */   {
/* 122 */     return this.m_bGroupedReport;
/*     */   }
/*     */ 
/*     */   public void validate(HttpServletRequest a_request, UserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager) throws Bn2Exception
/*     */   {
/* 127 */     if (((StringUtil.stringIsPopulated(getStartDateString())) && (!StringUtil.stringIsDate(getStartDateString()))) || ((StringUtil.stringIsPopulated(getEndDateString())) && (!StringUtil.stringIsDate(getEndDateString()))))
/*     */     {
/* 131 */       addError(a_listManager.getListItem(a_dbTransaction, "failedValidationDateFormat", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getReportFilename()
/*     */   {
/* 137 */     return this.m_sReportFilename;
/*     */   }
/*     */ 
/*     */   public void setReportFilename(String a_sReportFilename)
/*     */   {
/* 142 */     this.m_sReportFilename = a_sReportFilename;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.report.form.SearchReportForm
 * JD-Core Version:    0.6.0
 */