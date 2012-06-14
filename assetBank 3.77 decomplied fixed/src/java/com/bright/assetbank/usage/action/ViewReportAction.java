/*     */ package com.bright.assetbank.usage.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
import com.bright.assetbank.usage.bean.ReportEntity;
/*     */ import com.bright.assetbank.usage.bean.ReportEntity.CountComparatorAsc;
/*     */ import com.bright.assetbank.usage.bean.ReportEntity.CountComparatorDesc;
/*     */ import com.bright.assetbank.usage.bean.TotalsReport;
/*     */ import com.bright.assetbank.usage.constant.UsageConstants;
/*     */ import com.bright.assetbank.usage.form.ReportForm;
/*     */ import com.bright.assetbank.usage.service.UsageReportManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewReportAction extends BTransactionAction
/*     */   implements UsageConstants, FrameworkConstants
/*     */ {
/*  62 */   private UsageReportManager m_usageReportManager = null;
/*  63 */   private ABUserManager m_userManager = null;
/*  64 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  90 */     ActionForward afForward = null;
/*  91 */     ReportForm form = (ReportForm)a_form;
/*  92 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  94 */     Vector allGroups = this.m_userManager.getAllGroups();
/*     */ 
/*  97 */     form.setAllGroups(allGroups);
/*     */ 
/* 100 */     if ((!userProfile.getIsAdmin()) && (!userProfile.checkForRolePermission()))
/*     */     {
/* 102 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 103 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 107 */     if (form.getReportOption().equals("Schedule report"))
/*     */     {
/* 109 */       return a_mapping.findForward("ScheduleReport");
/*     */     }
/*     */ 
/* 112 */     form.validate(a_request, userProfile, a_dbTransaction, this.m_listManager);
/*     */ 
/* 114 */     if (!form.getHasErrors())
/*     */     {
/* 116 */       form.processReportDates();
/* 117 */       form.validateDatesPopulated(a_request, userProfile, a_dbTransaction, this.m_listManager);
/*     */     }
/*     */ 
/* 120 */     if (!form.getHasErrors())
/*     */     {
/* 122 */       Date dtStartDate = form.getStartDate();
/* 123 */       Date dtEndDate = form.getEndDate();
/*     */ 
/* 127 */       Vector vResults = null;
/*     */ 
/* 129 */       switch (form.getReportType())
/*     */       {
/*     */       case 1:
/* 133 */         vResults = this.m_usageReportManager.getUsageTypeReport(a_dbTransaction, dtStartDate, dtEndDate);
/* 134 */         sortResults(vResults, form.getSortAscending());
/* 135 */         afForward = a_mapping.findForward("UsageReport");
/* 136 */         break;
/*     */       case 14:
/* 141 */         TotalsReport report = this.m_usageReportManager.getAssetViewReport(a_dbTransaction, dtStartDate, dtEndDate);
/* 142 */         vResults = report.getAssetLines();
/* 143 */         sortResults(vResults, form.getSortAscending());
/* 144 */         afForward = a_mapping.findForward("ViewsReport");
/* 145 */         form.setShowGroups(false);
/* 146 */         form.setShowUsers(false);
/* 147 */         form.setTotalAssets(report.getTotalAssets());
/* 148 */         form.setTotalTimes(report.getTotalTimes());
/* 149 */         break;
/*     */       case 2:
/* 154 */         vResults = this.m_usageReportManager.getAssetViewReportByGroup(a_dbTransaction, dtStartDate, dtEndDate);
/* 155 */         sortResults(vResults, form.getSortAscending());
/* 156 */         afForward = a_mapping.findForward("ViewsReport");
/* 157 */         form.setShowGroups(true);
/* 158 */         form.setShowUsers(false);
/* 159 */         break;
/*     */       case 3:
/* 164 */         vResults = this.m_usageReportManager.getAssetViewReportByUser(a_dbTransaction, dtStartDate, dtEndDate);
/* 165 */         sortResults(vResults, form.getSortAscending());
/* 166 */         afForward = a_mapping.findForward("ViewsReport");
/* 167 */         form.setShowGroups(false);
/* 168 */         form.setShowUsers(true);
/* 169 */         break;
/*     */       case 4:
/*     */       case 6:
/* 175 */         vResults = this.m_usageReportManager.getUserGroupUploadReport(a_dbTransaction, dtStartDate, dtEndDate, true);
/* 176 */         sortResults(vResults, form.getSortAscending());
/* 177 */         afForward = a_mapping.findForward("UploadsReport");
/* 178 */         form.setShowGroups(true);
/* 179 */         form.setShowUsers(form.getReportType() == 6);
/* 180 */         break;
/*     */       case 5:
/* 185 */         vResults = this.m_usageReportManager.getUserGroupUploadReport(a_dbTransaction, dtStartDate, dtEndDate, false);
/* 186 */         sortResults(vResults, form.getSortAscending());
/* 187 */         afForward = a_mapping.findForward("UploadsReport");
/* 188 */         form.setShowGroups(false);
/* 189 */         form.setShowUsers(true);
/* 190 */         break;
/*     */       case 13:
/* 195 */         vResults = this.m_usageReportManager.getDownloadsByUploaderReport(a_dbTransaction, dtStartDate, dtEndDate);
/* 196 */         sortResults(vResults, form.getSortAscending());
/* 197 */         afForward = a_mapping.findForward("DownloadsByUploaderReport");
/* 198 */         break;
/*     */       case 7:
/* 203 */         report = this.m_usageReportManager.getAssetDownloadCount(a_dbTransaction, dtStartDate, dtEndDate);
/* 204 */         vResults = report.getAssetLines();
/* 205 */         sortResults(vResults, form.getSortAscending());
/* 206 */         afForward = a_mapping.findForward("DownloadReport");
/* 207 */         form.setTotalAssets(report.getTotalAssets());
/* 208 */         form.setTotalTimes(report.getTotalTimes());
/* 209 */         break;
/*     */       case 8:
/*     */       case 9:
/* 215 */         afForward = a_mapping.findForward("DownloadReport");
/*     */ 
/* 218 */         if (form.getReportType() == 9)
/*     */         {
/* 220 */           vResults = this.m_usageReportManager.getAssetDownloadReport(a_dbTransaction, dtStartDate, dtEndDate, true);
/* 221 */           sortResults(vResults, form.getSortAscending());
/* 222 */           form.setShowUsers(true);
/* 223 */           form.setShowGroups(true);
/*     */         }
/*     */         else
/*     */         {
/* 227 */           vResults = this.m_usageReportManager.getAssetDownloadReport(a_dbTransaction, dtStartDate, dtEndDate, false);
/* 228 */           sortResults(vResults, form.getSortAscending());
/* 229 */           if (form.getReportType() != 8)
/*     */             break;
/* 231 */           form.setShowUsers(true); } break;
/*     */       case 10:
/*     */       case 11:
/* 241 */         vResults = this.m_usageReportManager.getUserGroupDownloadReport(a_dbTransaction, dtStartDate, dtEndDate, form.getReportType() == 11);
/* 242 */         sortResults(vResults, form.getSortAscending());
/* 243 */         form.setShowUsers(form.getReportType() == 10);
/* 244 */         form.setShowGroups(form.getReportType() == 11);
/* 245 */         afForward = a_mapping.findForward("UserGroupDownloadsReport");
/* 246 */         break;
/*     */       case 12:
/* 251 */         vResults = this.m_usageReportManager.getReasonForDownloadReport(a_dbTransaction, dtStartDate, dtEndDate, form.getUsername());
/*     */ 
/* 255 */         sortResults(vResults, form.getSortAscending());
/* 256 */         afForward = a_mapping.findForward("ReasonForDownloadReport");
/* 257 */         break;
/*     */       case 15:
/* 262 */        report = this.m_usageReportManager.getAccessLevelViewReport(a_dbTransaction, dtStartDate, dtEndDate);
/*     */ 
/* 265 */         vResults = report.getAssetLines();
/* 266 */         form.setTotalTimes(report.getTotalTimes());
/* 267 */         afForward = a_mapping.findForward("AccessLevelReport");
/* 268 */         form.setShowViews(true);
/* 269 */         break;
/*     */       case 16:
/* 274 */         report = this.m_usageReportManager.getAccessLevelDownloadReport(a_dbTransaction, dtStartDate, dtEndDate);
/*     */ 
/* 277 */         vResults = report.getAssetLines();
/* 278 */         form.setTotalTimes(report.getTotalTimes());
/* 279 */         afForward = a_mapping.findForward("AccessLevelReport");
/* 280 */         form.setShowDownloads(true);
/* 281 */         break;
/*     */       }
/*     */ 
/* 285 */       form.setDetails(vResults);
/*     */     }
/*     */     else
/*     */     {
/* 289 */       afForward = a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/* 292 */     return afForward;
/*     */   }
/*     */ 
/*     */   private void sortResults(List a_results, boolean a_bAcsending)
/*     */   {
/* 303 */     if ((a_results != null) && (a_results.size() > 0))
/*     */     {
/* 305 */       if (a_bAcsending)
/*     */       {
/* 307 */         Collections.sort(a_results, new ReportEntity.CountComparatorAsc());
/*     */       }
/*     */       else
/*     */       {
/* 311 */         Collections.sort(a_results, new ReportEntity.CountComparatorDesc());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setUsageReportManager(UsageReportManager a_usageReportManager)
/*     */   {
/* 318 */     this.m_usageReportManager = a_usageReportManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 323 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 328 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.ViewReportAction
 * JD-Core Version:    0.6.0
 */