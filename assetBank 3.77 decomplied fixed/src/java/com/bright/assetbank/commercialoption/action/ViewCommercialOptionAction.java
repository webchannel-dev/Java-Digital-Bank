/*     */ package com.bright.assetbank.commercialoption.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.commercialoption.bean.CommercialOption;
/*     */ import com.bright.assetbank.commercialoption.form.CommercialOptionForm;
/*     */ import com.bright.assetbank.commercialoption.service.CommercialOptionManager;
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
/*     */ public class ViewCommercialOptionAction extends BTransactionAction
/*     */ {
/*  47 */   private CommercialOptionManager m_coManager = null;
/*     */ 
/* 119 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public void setCommercialOptionManager(CommercialOptionManager a_coManager)
/*     */   {
/*  51 */     this.m_coManager = a_coManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  81 */     ActionForward afForward = null;
/*  82 */     CommercialOptionForm form = (CommercialOptionForm)a_form;
/*  83 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  86 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  88 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  89 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  93 */     long lId = getLongParameter(a_request, "id");
/*  94 */     if (lId > 0L)
/*     */     {
/*  96 */       if (!form.getHasErrors())
/*     */       {
/*  98 */         CommercialOption commOpt = this.m_coManager.getCommercialOption(a_dbTransaction, lId);
/*  99 */         form.setCommercialOption(commOpt);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 109 */       this.m_languageManager.createEmptyTranslations(a_dbTransaction, form.getCommercialOption());
/*     */     }
/*     */ 
/* 114 */     afForward = a_mapping.findForward("Success");
/* 115 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/* 122 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.commercialoption.action.ViewCommercialOptionAction
 * JD-Core Version:    0.6.0
 */