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
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AssetReportAction extends BTransactionAction
/*     */   implements AssetBankConstants, ReportConstants, CategoryConstants
/*     */ {
/*     */   private static final String c_ksClassName = "AssetReportAction";
/*  57 */   private AssetReportManager m_assetReportManager = null;
/*     */ 
/*  63 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*  69 */   protected ListManager m_listManager = null;
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
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  72 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  91 */     String ksMethodName = "execute";
/*  92 */     ActionForward afForward = null;
/*  93 */     AssetReportForm form = (AssetReportForm)a_form;
/*  94 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  97 */     if ((!userProfile.getIsAdmin()) && (!userProfile.checkForRolePermission()))
/*     */     {
/*  99 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 103 */     long lAttributeId = form.getAttribute().getId();
/* 104 */     Attribute attr = null;
/* 105 */     if (lAttributeId > 0L)
/*     */     {
/* 107 */       attr = this.m_attributeManager.getAttribute(a_dbTransaction, lAttributeId);
/* 108 */       form.setAttribute(attr);
/*     */     }
/*     */ 
/* 112 */     Vector vResults = null;
/*     */ 
/* 114 */     switch (form.getReportType())
/*     */     {
/*     */     case 1:
/* 118 */       vResults = this.m_assetReportManager.getAssetsByCategoryAndAttributeReport(a_dbTransaction, 1L, attr, form.getShowCategoryTotalsOnly());
/*     */ 
/* 121 */       afForward = a_mapping.findForward("ByCategoryReport");
/* 122 */       break;
/*     */     case 2:
/* 126 */       form.validate(a_request, userProfile, a_dbTransaction, this.m_listManager);
/*     */ 
/* 128 */       if (form.getHasErrors())
/*     */         break;
/* 130 */       form.processReportDates();
/* 131 */       Date dtStartDate = form.getStartDate();
/* 132 */       Date dtEndDate = form.getEndDate();
/*     */ 
/* 134 */       vResults = this.m_assetReportManager.getAssetsByDateReport(a_dbTransaction, dtStartDate, dtEndDate, form.getDateType(), form.getFileSizeOperator(), (long)(form.getFileSize() * form.getFileSizeMultiplier()));
/*     */ 
/* 141 */       afForward = a_mapping.findForward("ByDateReport");
/* 142 */       break;
/*     */     case 3:
/* 148 */       long lTreeId = getLongParameter(a_request, "categoryTypeId");
/*     */ 
/* 150 */       if (lTreeId <= 0L)
/*     */       {
/* 152 */         this.m_logger.error("AssetReportAction.execute : No tree id supplied");
/* 153 */         throw new Bn2Exception("AssetReportAction.execute : No tree id supplied");
/*     */       }
/*     */ 
/* 156 */       vResults = this.m_assetReportManager.getAssetsByKeyword(a_dbTransaction, lTreeId);
/* 157 */       afForward = a_mapping.findForward("ByKeywordReport");
/* 158 */       break;
/*     */     case 4:
/* 162 */       vResults = this.m_assetReportManager.getPotentiallyDuplicateFiles(a_dbTransaction);
/* 163 */       afForward = a_mapping.findForward("DuplicatesReport");
/* 164 */       break;
/*     */     default:
/* 168 */       afForward = a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/* 172 */     if (!form.getHasErrors())
/*     */     {
/* 174 */       form.setDetails(vResults);
/*     */     }
/*     */     else
/*     */     {
/* 179 */       afForward = a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/* 182 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.report.action.AssetReportAction
 * JD-Core Version:    0.6.0
 */