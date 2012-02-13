/*     */ package com.bright.assetbank.usage.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.usage.bean.UsageType;
/*     */ import com.bright.assetbank.usage.form.UsageAdminForm;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.usage.util.MaskUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewUsageTypesAction extends BTransactionAction
/*     */ {
/*  51 */   private UsageManager m_usageManager = null;
/*  52 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     UsageAdminForm form = (UsageAdminForm)a_form;
/*  72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  75 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  77 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  78 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  82 */     long lParentId = getLongParameter(a_request, "parentId");
/*     */ 
/*  85 */     form.setUsageTypes(this.m_usageManager.getUsageTypes(a_dbTransaction, lParentId));
/*     */ 
/*  88 */     if (lParentId > 0L)
/*     */     {
/*  90 */       form.setParentUsageType(this.m_usageManager.getUsageType(a_dbTransaction, lParentId));
/*     */     }
/*     */ 
/*  93 */     if (!form.getHasErrors())
/*     */     {
/*  96 */       UsageType newType = new UsageType();
/*  97 */       newType.setParentId(lParentId);
/*     */ 
/* 100 */       if (AssetBankSettings.getSupportMultiLanguage())
/*     */       {
/* 102 */         this.m_languageManager.createEmptyTranslations(a_dbTransaction, newType);
/*     */       }
/*     */ 
/* 105 */       form.setUsageType(newType);
/*     */     }
/*     */ 
/* 109 */     a_request.setAttribute("showMaskTab", Boolean.valueOf(MaskUtil.masksAllowedBySettings()));
/*     */ 
/* 112 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/* 117 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 122 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.ViewUsageTypesAction
 * JD-Core Version:    0.6.0
 */