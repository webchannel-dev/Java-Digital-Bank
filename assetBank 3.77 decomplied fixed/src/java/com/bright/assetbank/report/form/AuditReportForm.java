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
/*     */ public class AuditReportForm extends Bn2Form
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/*  41 */   private Vector m_vecReportLines = null;
/*     */ 
/*  43 */   private String m_sStartDateString = null;
/*  44 */   private String m_sEndDateString = null;
/*  45 */   private Date m_dtStartDate = null;
/*  46 */   private Date m_dtEndDate = null;
/*  47 */   private String m_sReportFilename = null;
/*  48 */   private String m_sUsername = null;
/*  49 */   private String m_sIpAddress = null;
/*  50 */   private boolean m_bIncludeViewsDownloads = false;
/*     */ 
/*     */   public void setReportLines(Vector a_vecReportLines)
/*     */   {
/*  54 */     this.m_vecReportLines = a_vecReportLines;
/*     */   }
/*     */ 
/*     */   public Vector getReportLines()
/*     */   {
/*  59 */     return this.m_vecReportLines;
/*     */   }
/*     */ 
/*     */   public void setStartDate(Date a_dtStartDate)
/*     */   {
/*  64 */     this.m_dtStartDate = a_dtStartDate;
/*     */   }
/*     */ 
/*     */   public Date getStartDate()
/*     */   {
/*  69 */     return this.m_dtStartDate;
/*     */   }
/*     */ 
/*     */   public void setEndDate(Date a_dtEndDate)
/*     */   {
/*  74 */     this.m_dtEndDate = a_dtEndDate;
/*     */   }
/*     */ 
/*     */   public Date getEndDate()
/*     */   {
/*  79 */     return this.m_dtEndDate;
/*     */   }
/*     */ 
/*     */   public String getEndDateString()
/*     */   {
/*  85 */     return this.m_sEndDateString;
/*     */   }
/*     */ 
/*     */   public void setEndDateString(String a_sEndDateString)
/*     */   {
/*  91 */     this.m_sEndDateString = a_sEndDateString;
/*     */   }
/*     */ 
/*     */   public String getStartDateString()
/*     */   {
/*  97 */     return this.m_sStartDateString;
/*     */   }
/*     */ 
/*     */   public void setStartDateString(String a_sStartDateString)
/*     */   {
/* 103 */     this.m_sStartDateString = a_sStartDateString;
/*     */   }
/*     */ 
/*     */   public String getReportFilename()
/*     */   {
/* 108 */     return this.m_sReportFilename;
/*     */   }
/*     */ 
/*     */   public void setReportFilename(String a_sReportFilename)
/*     */   {
/* 113 */     this.m_sReportFilename = a_sReportFilename;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/* 119 */     return this.m_sUsername;
/*     */   }
/*     */ 
/*     */   public void setUsername(String a_sUsername)
/*     */   {
/* 124 */     this.m_sUsername = a_sUsername;
/*     */   }
/*     */ 
/*     */   public String getIpAddress()
/*     */   {
/* 130 */     return this.m_sIpAddress;
/*     */   }
/*     */ 
/*     */   public void setIpAddress(String a_sIpAddress)
/*     */   {
/* 135 */     this.m_sIpAddress = a_sIpAddress;
/*     */   }
/*     */ 
/*     */   public boolean getIncludeViewsDownloads()
/*     */   {
/* 141 */     return this.m_bIncludeViewsDownloads;
/*     */   }
/*     */ 
/*     */   public void setIncludeViewsDownloads(boolean a_bIncludeViewsDownloads)
/*     */   {
/* 146 */     this.m_bIncludeViewsDownloads = a_bIncludeViewsDownloads;
/*     */   }
/*     */ 
/*     */   public void validate(HttpServletRequest a_request, UserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager) throws Bn2Exception
/*     */   {
/* 151 */     if (((StringUtil.stringIsPopulated(getStartDateString())) && (!StringUtil.stringIsDate(getStartDateString()))) || ((StringUtil.stringIsPopulated(getEndDateString())) && (!StringUtil.stringIsDate(getEndDateString()))))
/*     */     {
/* 155 */       addError(a_listManager.getListItem(a_dbTransaction, "failedValidationDateFormat", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.report.form.AuditReportForm
 * JD-Core Version:    0.6.0
 */