/*     */ package com.bright.assetbank.report.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.report.constant.ReportConstants;
/*     */ import com.bright.assetbank.report.form.AssetReportForm;
/*     */ import com.bright.assetbank.report.service.AssetReportManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class DownloadAssetReportAction extends BTransactionAction
/*     */   implements AssetBankConstants, ReportConstants, CategoryConstants
/*     */ {
/*     */   private static final String c_ksClassName = "DownloadAssetReportAction";
/*  57 */   private AssetReportManager m_assetReportManager = null;
/*     */ 
/*  63 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*     */   public void setAssetReportManager(AssetReportManager a_assetReportManager)
/*     */   {
/*  60 */     this.m_assetReportManager = a_assetReportManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_sAttributeManager)
/*     */   {
/*  66 */     this.m_attributeManager = a_sAttributeManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  85 */     String ksMethodName = "execute";
/*  86 */     String sFilename = null;
/*  87 */     AssetReportForm form = (AssetReportForm)a_form;
/*  88 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  91 */     if ((!userProfile.getIsAdmin()) && (!userProfile.checkForRolePermission()))
/*     */     {
/*  93 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  97 */     long lAttributeId = form.getAttribute().getId();
/*  98 */     Attribute attr = null;
/*  99 */     if (lAttributeId > 0L)
/*     */     {
/* 101 */       attr = this.m_attributeManager.getAttribute(a_dbTransaction, lAttributeId);
/* 102 */       form.setAttribute(attr);
/*     */     }
/*     */ 
/* 106 */     switch (form.getReportType())
/*     */     {
/*     */     case 1:
/* 110 */       Vector vResults = this.m_assetReportManager.getAssetsByCategoryAndAttributeReport(a_dbTransaction, 1L, attr, form.getShowCategoryTotalsOnly());
/*     */ 
/* 113 */       if ((vResults == null) || (vResults.size() <= 0))
/*     */         break;
/* 115 */       sFilename = this.m_assetReportManager.createAssetsByCategoryAndAttributeReportFile(vResults, form.getAttribute()); break;
/*     */     case 2:
/* 121 */       if (form.getHasErrors())
/*     */         break;
/* 123 */       form.processReportDates();
/* 124 */       Date dtStartDate = form.getStartDate();
/* 125 */       Date dtEndDate = form.getEndDate();
/*     */ 
/* 127 */       vResults = this.m_assetReportManager.getAssetsByDateReport(a_dbTransaction, dtStartDate, dtEndDate, form.getDateType(), form.getFileSizeOperator(), (int)(form.getFileSize() * form.getFileSizeMultiplier()));
/*     */ 
/* 134 */       if ((vResults != null) && (vResults.size() > 0))
/*     */       {
/* 136 */         sFilename = this.m_assetReportManager.createAssetsByDateReportFile(vResults, dtStartDate, dtEndDate, form.getDateType(), form.getFileSizeOperator(), (int)form.getFileSize(), form.getFileSizeMultiplier());
/*     */       }
/*     */ 
/* 144 */       break;
/*     */     case 3:
/* 150 */       long lTreeId = getLongParameter(a_request, "categoryTypeId");
/*     */ 
/* 152 */       if (lTreeId <= 0L)
/*     */       {
/* 154 */         this.m_logger.error("DownloadAssetReportAction.execute : No tree id supplied");
/* 155 */         throw new Bn2Exception("DownloadAssetReportAction.execute : No tree id supplied");
/*     */       }
/*     */ 
/* 158 */        vResults = this.m_assetReportManager.getAssetsByKeyword(a_dbTransaction, lTreeId);
/* 159 */       if ((vResults == null) || (vResults.size() <= 0))
/*     */         break;
/* 161 */       sFilename = this.m_assetReportManager.createAssetsByKeywordReportFile(vResults); break;
/*     */     }
/*     */ 
/* 167 */     a_request.setAttribute("downloadFile", FileUtil.encryptFilepath(sFilename));
/* 168 */     a_request.setAttribute("downloadFilename", "asset_report.xls");
/*     */ 
/* 170 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.report.action.DownloadAssetReportAction
 * JD-Core Version:    0.6.0
 */