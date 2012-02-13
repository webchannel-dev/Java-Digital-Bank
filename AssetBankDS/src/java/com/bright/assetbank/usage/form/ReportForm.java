/*     */ package com.bright.assetbank.usage.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.usage.bean.ScheduledReport;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class ReportForm extends DateRangeForm
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/*  45 */   private int m_iReportType = 0;
/*  46 */   private Vector m_vecDetails = null;
/*  47 */   private boolean m_bShowUsers = false;
/*  48 */   private boolean m_bShowGroups = false;
/*  49 */   private boolean m_bSortAscending = false;
/*  50 */   private String m_bReportOption = null;
/*  51 */   private String m_sUsername = null;
/*  52 */   private String m_sReportFrequency = null;
/*  53 */   private String m_sReportName = null;
/*  54 */   private Vector m_allGroups = null;
/*  55 */   private long[] m_groupSelectedList = null;
/*  56 */   private Vector m_vGroups = null;
/*  57 */   private Vector m_vecGroupIds = null;
/*  58 */   private Vector m_vecScheduledReports = null;
/*  59 */   private ScheduledReport m_scheduledReport = null;
/*  60 */   private boolean m_bShowViews = false;
/*  61 */   private boolean m_bShowDownloads = false;
/*     */ 
/*  64 */   private long m_lTotalAssets = 0L;
/*  65 */   private long m_lTotalTimes = 0L;
/*     */ 
/*     */   public void validate(HttpServletRequest a_request, ABUserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager)
/*     */     throws Bn2Exception
/*     */   {
/*  70 */     if (getReportType() <= 0)
/*     */     {
/*  72 */       addError(a_listManager.getListItem(a_dbTransaction, "failedValidationReportType", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/*  75 */     super.validate(a_request, a_userProfile, a_dbTransaction, a_listManager);
/*     */   }
/*     */ 
/*     */   public boolean getShowGroups()
/*     */   {
/*  83 */     return this.m_bShowGroups;
/*     */   }
/*     */ 
/*     */   public void setShowGroups(boolean a_bShowGroups)
/*     */   {
/*  89 */     this.m_bShowGroups = a_bShowGroups;
/*     */   }
/*     */ 
/*     */   public boolean getShowUsers()
/*     */   {
/*  95 */     return this.m_bShowUsers;
/*     */   }
/*     */ 
/*     */   public void setShowUsers(boolean a_bShowUsers)
/*     */   {
/* 101 */     this.m_bShowUsers = a_bShowUsers;
/*     */   }
/*     */ 
/*     */   public int getReportType()
/*     */   {
/* 107 */     return this.m_iReportType;
/*     */   }
/*     */ 
/*     */   public void setReportType(int a_iReportType)
/*     */   {
/* 113 */     this.m_iReportType = a_iReportType;
/*     */   }
/*     */ 
/*     */   public void setDetails(Vector a_vecDetails)
/*     */   {
/* 118 */     this.m_vecDetails = a_vecDetails;
/*     */   }
/*     */ 
/*     */   public Vector getDetails()
/*     */   {
/* 123 */     return this.m_vecDetails;
/*     */   }
/*     */ 
/*     */   public boolean getSortAscending()
/*     */   {
/* 129 */     return this.m_bSortAscending;
/*     */   }
/*     */ 
/*     */   public void setSortAscending(boolean a_sSortAscending)
/*     */   {
/* 135 */     this.m_bSortAscending = a_sSortAscending;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/* 140 */     return this.m_sUsername;
/*     */   }
/*     */ 
/*     */   public void setUsername(String a_sUsername)
/*     */   {
/* 145 */     this.m_sUsername = a_sUsername;
/*     */   }
/*     */ 
/*     */   public long getTotalAssets()
/*     */   {
/* 150 */     return this.m_lTotalAssets;
/*     */   }
/*     */ 
/*     */   public void setTotalAssets(long a_sTotalAssets) {
/* 154 */     this.m_lTotalAssets = a_sTotalAssets;
/*     */   }
/*     */ 
/*     */   public long getTotalTimes() {
/* 158 */     return this.m_lTotalTimes;
/*     */   }
/*     */ 
/*     */   public void setTotalTimes(long a_sTotalTimes) {
/* 162 */     this.m_lTotalTimes = a_sTotalTimes;
/*     */   }
/*     */ 
/*     */   public String getReportOption() {
/* 166 */     return this.m_bReportOption;
/*     */   }
/*     */ 
/*     */   public void setReportOption(String a_bReportOption) {
/* 170 */     this.m_bReportOption = a_bReportOption;
/*     */   }
/*     */ 
/*     */   public String getReportFrequency() {
/* 174 */     return this.m_sReportFrequency;
/*     */   }
/*     */ 
/*     */   public void setReportFrequency(String a_sReportFrequency) {
/* 178 */     this.m_sReportFrequency = a_sReportFrequency;
/*     */   }
/*     */ 
/*     */   public String getReportName()
/*     */   {
/* 183 */     return this.m_sReportName;
/*     */   }
/*     */ 
/*     */   public void setReportName(String a_sReportName) {
/* 187 */     this.m_sReportName = a_sReportName;
/*     */   }
/*     */ 
/*     */   public Vector getAllGroups()
/*     */   {
/* 192 */     return this.m_allGroups;
/*     */   }
/*     */ 
/*     */   public void setAllGroups(Vector a_sAllGroups) {
/* 196 */     this.m_allGroups = a_sAllGroups;
/*     */   }
/*     */   public long[] getGroupSelectedList() {
/* 199 */     return this.m_groupSelectedList;
/*     */   }
/*     */   public void setGroupSelectedList(long[] a_selectedList) {
/* 202 */     this.m_groupSelectedList = a_selectedList;
/*     */   }
/*     */ 
/*     */   public Vector getGroups()
/*     */   {
/* 209 */     return this.m_vGroups;
/*     */   }
/*     */ 
/*     */   public void setGroups(Vector a_vGroups)
/*     */   {
/* 217 */     this.m_vGroups = a_vGroups;
/*     */   }
/*     */ 
/*     */   public boolean getSelectedGroup(int a_iGroupId)
/*     */   {
/* 225 */     Long longVal = null;
/*     */ 
/* 227 */     if (getGroupIds() != null)
/*     */     {
/* 229 */       for (int i = 0; i < getGroupIds().size(); i++)
/*     */       {
/* 231 */         longVal = (Long)(Long)getGroupIds().elementAt(i);
/*     */ 
/* 233 */         if (a_iGroupId == longVal.longValue())
/*     */         {
/* 235 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 240 */     return false;
/*     */   }
/*     */ 
/*     */   public Vector getGroupIds()
/*     */   {
/* 248 */     return this.m_vecGroupIds;
/*     */   }
/*     */ 
/*     */   public void setGroupIds(Vector a_GroupIds)
/*     */   {
/* 256 */     this.m_vecGroupIds = a_GroupIds;
/*     */   }
/*     */ 
/*     */   public Vector getScheduledReports()
/*     */   {
/* 261 */     return this.m_vecScheduledReports;
/*     */   }
/*     */ 
/*     */   public void setScheduledReports(Vector a_vecScheduledReports) {
/* 265 */     this.m_vecScheduledReports = a_vecScheduledReports;
/*     */   }
/*     */ 
/*     */   public ScheduledReport getScheduledReport()
/*     */   {
/* 270 */     if (this.m_scheduledReport == null)
/*     */     {
/* 272 */       this.m_scheduledReport = new ScheduledReport();
/*     */     }
/*     */ 
/* 275 */     return this.m_scheduledReport;
/*     */   }
/*     */ 
/*     */   public void setScheduledReport(ScheduledReport a_scheduledReport)
/*     */   {
/* 280 */     this.m_scheduledReport = a_scheduledReport;
/*     */   }
/*     */ 
/*     */   public boolean getShowViews()
/*     */   {
/* 285 */     return this.m_bShowViews;
/*     */   }
/*     */ 
/*     */   public void setShowViews(boolean a_bShowViews) {
/* 289 */     this.m_bShowViews = a_bShowViews;
/*     */   }
/*     */ 
/*     */   public boolean getShowDownloads()
/*     */   {
/* 294 */     return this.m_bShowDownloads;
/*     */   }
/*     */ 
/*     */   public void setShowDownloads(boolean a_bShowDownloads) {
/* 298 */     this.m_bShowDownloads = a_bShowDownloads;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.form.ReportForm
 * JD-Core Version:    0.6.0
 */