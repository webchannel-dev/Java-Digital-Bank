/*     */ package com.bright.assetbank.priceband.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.priceband.bean.PrintPriceBand;
/*     */ import com.bright.assetbank.priceband.form.PrintPriceBandForm;
/*     */ import com.bright.assetbank.priceband.service.PriceBandManager;
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
/*     */ public class ViewPrintPriceBandAction extends BTransactionAction
/*     */ {
/*  46 */   private PriceBandManager m_pbManager = null;
/*     */ 
/* 113 */   private LanguageManager m_languageManager = null;
/*     */ 
/*     */   public void setPriceBandManager(PriceBandManager a_pbManager)
/*     */   {
/*  49 */     this.m_pbManager = a_pbManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  72 */     ActionForward afForward = null;
/*  73 */     PrintPriceBandForm form = (PrintPriceBandForm)a_form;
/*  74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  77 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  79 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  80 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  84 */     long lId = getLongParameter(a_request, "id");
/*  85 */     if (lId > 0L)
/*     */     {
/*  87 */       if (!form.getHasErrors())
/*     */       {
/*  89 */         PrintPriceBand pb = (PrintPriceBand)this.m_pbManager.getPriceBand(a_dbTransaction, lId);
/*  90 */         form.setPriceBand(pb);
/*  91 */         form.setShippingCosts(pb.getShippingCostsMatrix());
/*     */       }
/*     */ 
/*     */     }
/*  98 */     else if (!form.getHasErrors())
/*     */     {
/* 101 */       this.m_languageManager.createEmptyTranslations(a_dbTransaction, form.getPriceBand());
/*     */     }
/*     */ 
/* 106 */     form.setQuantityRangeList(this.m_pbManager.getQuantityRangeList());
/* 107 */     form.setRegionList(this.m_pbManager.getRegionList());
/*     */ 
/* 109 */     afForward = a_mapping.findForward("Success");
/* 110 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/* 116 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.priceband.action.ViewPrintPriceBandAction
 * JD-Core Version:    0.6.0
 */