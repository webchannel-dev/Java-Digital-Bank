/*     */ package com.bright.assetbank.usage.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.usage.bean.UsageTypeFormat;
/*     */ import com.bright.assetbank.usage.form.UsageFormatAdminForm;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveUsageTypeFormatAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  52 */   private UsageManager m_usageManager = null;
/*     */ 
/* 174 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageMaager)
/*     */   {
/*  55 */     this.m_usageManager = a_sUsageMaager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  75 */     UsageFormatAdminForm form = (UsageFormatAdminForm)a_form;
/*  76 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  79 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  81 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  82 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  85 */     a_request.setAttribute("id", new Long(form.getUsageTypeFormat().getUsageTypeId()));
/*     */ 
/*  90 */     int iWidth = 0;
/*  91 */     int iHeight = 0;
/*     */ 
/*  93 */     if (StringUtil.stringIsInteger(form.getWidthString()))
/*     */     {
/*  95 */       iWidth = Integer.parseInt(form.getWidthString());
/*     */     }
/*  97 */     if (StringUtil.stringIsInteger(form.getHeightString()))
/*     */     {
/*  99 */       iHeight = Integer.parseInt(form.getHeightString());
/*     */     }
/*     */ 
/* 104 */     validateMaskFields(form);
/* 105 */     if (form.getHasErrors())
/*     */     {
/* 107 */       return a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/* 111 */     if ((!StringUtil.stringIsPopulated(form.getUsageTypeFormat().getDescription())) || (iWidth <= 0) || (iHeight <= 0))
/*     */     {
/* 114 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationUsageTypeFormat", userProfile.getCurrentLanguage()).getBody());
/* 115 */       return a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/* 120 */     form.getUsageTypeFormat().setWidth(iWidth);
/* 121 */     form.getUsageTypeFormat().setHeight(iHeight);
/*     */ 
/* 124 */     String sPreserveFormats = StringUtil.convertStringArrayToString(form.getPreserveFormatList(), ";");
/* 125 */     form.getUsageTypeFormat().setPreserveFormatList(sPreserveFormats);
/*     */ 
/* 128 */     if (form.getUsageTypeFormat().getId() > 0L)
/*     */     {
/* 130 */       this.m_usageManager.updateUsageTypeFormat(null, form.getUsageTypeFormat());
/*     */     }
/*     */     else
/*     */     {
/* 134 */       this.m_usageManager.addUsageTypeFormat(null, form.getUsageTypeFormat());
/*     */     }
/* 136 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   private void validateMaskFields(UsageFormatAdminForm a_form)
/*     */   {
/* 145 */     UsageTypeFormat utf = a_form.getUsageTypeFormat();
/* 146 */     if ((!utf.getNonMaskingOptionsAllowMasking()) && ((utf.getAllowMasking()) || (utf.getPresetMaskId() > 0L)))
/*     */     {
/* 149 */       a_form.addError("Masks cannot be used when crop to fit is enabled");
/*     */     }
/*     */     else
/*     */     {
/* 153 */       if (!utf.getAllowMasking())
/*     */       {
/* 155 */         if (utf.getPresetMaskId() > 0L)
/*     */         {
/* 157 */           a_form.addError("Allow masking must be enabled when a preset mask is selected");
/*     */         }
/*     */ 
/* 160 */         if (utf.getPresetMaskColourId() > 0L)
/*     */         {
/* 162 */           a_form.addError("Allow masking must be enabled when a preset mask colour is selected");
/*     */         }
/*     */       }
/*     */ 
/* 166 */       if ((utf.getPresetMaskColourId() > 0L) && (utf.getPresetMaskId() <= 0L))
/*     */       {
/* 169 */         a_form.addError("A preset mask colour can't be selected without a preset mask");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 177 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.SaveUsageTypeFormatAction
 * JD-Core Version:    0.6.0
 */