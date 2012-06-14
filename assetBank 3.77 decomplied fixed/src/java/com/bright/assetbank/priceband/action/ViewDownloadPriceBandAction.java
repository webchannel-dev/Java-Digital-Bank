/*     */ package com.bright.assetbank.priceband.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.priceband.bean.PriceBand;
/*     */ import com.bright.assetbank.priceband.form.DownloadPriceBandForm;
/*     */ import com.bright.assetbank.priceband.service.PriceBandManager;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.service.LanguageManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewDownloadPriceBandAction extends BTransactionAction
/*     */ {
/* 112 */   private PriceBandManager m_pbManager = null;
/*     */ 
/* 118 */   private UsageManager m_usageManager = null;
/*     */ 
/* 124 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  68 */     ActionForward afForward = null;
/*  69 */     DownloadPriceBandForm form = (DownloadPriceBandForm)a_form;
/*  70 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  73 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  75 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  76 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  80 */     long lId = getLongParameter(a_request, "id");
/*  81 */     if (lId > 0L)
/*     */     {
/*  83 */       if (!form.getHasErrors())
/*     */       {
/*  85 */         PriceBand pb = this.m_pbManager.getPriceBand(a_dbTransaction, lId);
/*  86 */         form.setPriceBand(pb);
/*     */       }
/*     */ 
/*     */     }
/*  93 */     else if (!form.getHasErrors())
/*     */     {
/*  96 */       this.m_languageManager.createEmptyTranslations(a_dbTransaction, form.getPriceBand());
/*     */     }
/*     */ 
/* 102 */     form.setUsageTypeList(this.m_usageManager.getUsageTypes(a_dbTransaction, 0L, userProfile.getCurrentLanguage()));
/*     */ 
/* 105 */     form.setIncludedUsageList(this.m_pbManager.getUsagesForPriceBand(a_dbTransaction, lId));
/*     */ 
/* 108 */     afForward = a_mapping.findForward("Success");
/* 109 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setPriceBandManager(PriceBandManager a_pbManager)
/*     */   {
/* 115 */     this.m_pbManager = a_pbManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/* 121 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/* 127 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.priceband.action.ViewDownloadPriceBandAction
 * JD-Core Version:    0.6.0
 */