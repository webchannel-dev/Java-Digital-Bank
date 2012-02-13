/*     */ package com.bright.assetbank.usage.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.BrightDateFormat;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class DateRangeForm extends Bn2Form
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/*  48 */   private String m_sStartDateString = null;
/*  49 */   private String m_sEndDateString = null;
/*  50 */   private Date m_dtStartDate = null;
/*  51 */   private Date m_dtEndDate = null;
/*     */ 
/*     */   public String getEndDateString()
/*     */   {
/*  56 */     return this.m_sEndDateString;
/*     */   }
/*     */ 
/*     */   public void setEndDateString(String a_sEndDateString)
/*     */   {
/*  62 */     this.m_sEndDateString = a_sEndDateString;
/*     */   }
/*     */ 
/*     */   public String getStartDateString()
/*     */   {
/*  68 */     return this.m_sStartDateString;
/*     */   }
/*     */ 
/*     */   public void setStartDateString(String a_sStartDateString)
/*     */   {
/*  74 */     this.m_sStartDateString = a_sStartDateString;
/*     */   }
/*     */ 
/*     */   public void setStartDate(Date a_dtStartDate)
/*     */   {
/*  79 */     this.m_dtStartDate = a_dtStartDate;
/*     */   }
/*     */ 
/*     */   public Date getStartDate()
/*     */   {
/*  84 */     return this.m_dtStartDate;
/*     */   }
/*     */ 
/*     */   public void setEndDate(Date a_dtEndDate)
/*     */   {
/*  89 */     this.m_dtEndDate = a_dtEndDate;
/*     */   }
/*     */ 
/*     */   public Date getEndDate()
/*     */   {
/*  94 */     return this.m_dtEndDate;
/*     */   }
/*     */ 
/*     */   public void validate(HttpServletRequest a_request, UserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager)
/*     */     throws Bn2Exception
/*     */   {
/* 101 */     DateFormat dfDateFormat = AssetBankSettings.getStandardDateFormat();
/* 102 */     Date dtStartDate = null;
/* 103 */     Date dtEndDate = null;
/*     */     try
/*     */     {
/* 107 */       if (StringUtil.stringIsPopulated(getStartDateString()))
/*     */       {
/* 109 */         dtStartDate = dfDateFormat.parse(getStartDateString());
/*     */       }
/* 111 */       if (StringUtil.stringIsPopulated(getEndDateString()))
/*     */       {
/* 113 */         dtEndDate = dfDateFormat.parse(getEndDateString());
/*     */       }
/*     */ 
/* 116 */       if ((dtStartDate != null) && (dtEndDate != null))
/*     */       {
/* 118 */         if (dtStartDate.getTime() > dtEndDate.getTime())
/*     */         {
/* 120 */           addError(a_listManager.getListItem(a_dbTransaction, "failedValidationDateOrder", a_userProfile.getCurrentLanguage()).getBody());
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (ParseException pe)
/*     */     {
/* 126 */       addError(a_listManager.getListItem(a_dbTransaction, "failedValidationDateFormat", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void validateDatesPopulated(HttpServletRequest a_request, UserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager)
/*     */     throws Bn2Exception
/*     */   {
/* 135 */     if ((this.m_dtStartDate == null) || (this.m_dtEndDate == null))
/*     */     {
/* 137 */       addError(a_listManager.getListItem(a_dbTransaction, "failedValidationDateFormat", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void processReportDates()
/*     */     throws Bn2Exception
/*     */   {
/* 149 */     BrightDateFormat bdf = AssetBankSettings.getStandardDateFormat();
/* 150 */     Date dtStartDate = null;
/* 151 */     Date dtEndDate = null;
/*     */     try
/*     */     {
/* 156 */       if (StringUtil.stringIsPopulated(getStartDateString()))
/*     */       {
/* 158 */         dtStartDate = bdf.parse(getStartDateString());
/*     */       }
/* 160 */       if (StringUtil.stringIsPopulated(getEndDateString()))
/*     */       {
/* 162 */         dtEndDate = bdf.parse(getEndDateString());
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (ParseException pe)
/*     */     {
/* 168 */       throw new Bn2Exception("ParseException in ReportForm.processReportDates", pe);
/*     */     }
/*     */ 
/* 172 */     Calendar calendar = Calendar.getInstance();
/* 173 */     if (dtStartDate != null)
/*     */     {
/* 175 */       calendar.setTime(dtStartDate);
/* 176 */       calendar.set(11, 0);
/* 177 */       calendar.set(12, 0);
/* 178 */       calendar.set(13, 0);
/* 179 */       calendar.set(14, 0);
/* 180 */       dtStartDate = calendar.getTime();
/*     */     }
/*     */ 
/* 183 */     if (dtEndDate != null)
/*     */     {
/* 185 */       calendar.setTime(dtEndDate);
/* 186 */       calendar.set(11, 23);
/* 187 */       calendar.set(12, 59);
/* 188 */       calendar.set(13, 59);
/* 189 */       calendar.set(14, 999);
/* 190 */       dtEndDate = calendar.getTime();
/*     */     }
/*     */ 
/* 194 */     setStartDate(dtStartDate);
/* 195 */     setEndDate(dtEndDate);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.form.DateRangeForm
 * JD-Core Version:    0.6.0
 */