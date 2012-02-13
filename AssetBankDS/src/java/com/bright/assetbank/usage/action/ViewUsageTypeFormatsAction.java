/*     */ package com.bright.assetbank.usage.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.usage.bean.UsageTypeFormat;
/*     */ import com.bright.assetbank.usage.form.UsageFormatAdminForm;
/*     */ import com.bright.assetbank.usage.service.MaskManager;
/*     */ import com.bright.assetbank.usage.service.NamedColourManager;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewUsageTypeFormatsAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  58 */   private UsageManager m_usageManager = null;
/*  59 */   private LanguageManager m_languageManager = null;
/*     */   private MaskManager m_maskManager;
/*     */   private NamedColourManager m_namedColourManager;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  81 */     UsageFormatAdminForm form = (UsageFormatAdminForm)a_form;
/*  82 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  85 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  87 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  88 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  92 */     long lUsageTypeId = getLongParameter(a_request, "id");
/*     */ 
/*  94 */     if ((lUsageTypeId <= 0L) && (a_request.getAttribute("id") != null))
/*     */     {
/*  96 */       lUsageTypeId = ((Long)a_request.getAttribute("id")).longValue();
/*     */     }
/*     */ 
/* 100 */     long lUsageTypeFormatId = getLongParameter(a_request, "usageTypeFormatId");
/* 101 */     boolean bDelete = false;
/*     */ 
/* 103 */     if (a_request.getAttribute("delete") != null)
/*     */     {
/* 105 */       bDelete = ((Boolean)a_request.getAttribute("delete")).booleanValue();
/*     */     }
/*     */ 
/* 109 */     if (!form.getHasErrors())
/*     */     {
/* 111 */       if ((lUsageTypeFormatId > 0L) && (!bDelete))
/*     */       {
/* 114 */         form.setUsageTypeFormat(this.m_usageManager.getUsageTypeFormat(a_dbTransaction, lUsageTypeFormatId));
/* 115 */         form.setWidthString(String.valueOf(form.getUsageTypeFormat().getWidth()));
/* 116 */         form.setHeightString(String.valueOf(form.getUsageTypeFormat().getHeight()));
/*     */ 
/* 119 */         String sPreserveFormats = form.getUsageTypeFormat().getPreserveFormatList();
/* 120 */         String[] asPreserveFormats = StringUtil.convertToArray(sPreserveFormats, ";");
/* 121 */         form.setPreserveFormatList(asPreserveFormats);
/*     */       }
/*     */       else
/*     */       {
/* 126 */         form.reset(a_mapping, a_request);
/* 127 */         form.setUsageTypeFormat(UsageTypeFormat.createDefault());
/*     */       }
/*     */ 
/* 131 */       if ((form.getUsageTypeFormat().getId() == 0L) && (AssetBankSettings.getSupportMultiLanguage()))
/*     */       {
/* 133 */         this.m_languageManager.createEmptyTranslations(a_dbTransaction, form.getUsageTypeFormat());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 138 */     form.setUsageTypeFormats(this.m_usageManager.getUsageTypeFormats(a_dbTransaction, lUsageTypeId));
/* 139 */     form.setUsageType(this.m_usageManager.getUsageType(a_dbTransaction, lUsageTypeId));
/* 140 */     form.setColorSpaces(this.m_usageManager.getAllColorSpaces(a_dbTransaction, false));
/*     */ 
/* 143 */     List colours = getNamedColourManager().getColours(a_dbTransaction);
/* 144 */     a_request.setAttribute("colours", colours);
/* 145 */     List masks = getMaskManager().getMasks(a_dbTransaction);
/* 146 */     a_request.setAttribute("masks", masks);
/*     */ 
/* 148 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public UsageManager getUsageManager()
/*     */   {
/* 154 */     return this.m_usageManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/* 160 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 165 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ 
/*     */   public MaskManager getMaskManager()
/*     */   {
/* 170 */     return this.m_maskManager;
/*     */   }
/*     */ 
/*     */   public void setMaskManager(MaskManager a_maskManager)
/*     */   {
/* 175 */     this.m_maskManager = a_maskManager;
/*     */   }
/*     */ 
/*     */   public NamedColourManager getNamedColourManager()
/*     */   {
/* 180 */     return this.m_namedColourManager;
/*     */   }
/*     */ 
/*     */   public void setNamedColourManager(NamedColourManager a_namedColourManager)
/*     */   {
/* 185 */     this.m_namedColourManager = a_namedColourManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.ViewUsageTypeFormatsAction
 * JD-Core Version:    0.6.0
 */