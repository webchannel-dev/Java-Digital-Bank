/*     */ package com.bright.assetbank.commercialoption.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.commercialoption.bean.CommercialOption;
/*     */ import com.bright.assetbank.commercialoption.form.CommercialOptionForm;
/*     */ import com.bright.assetbank.commercialoption.service.CommercialOptionManager;
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
/*     */ public class ViewTermsAction extends BTransactionAction
/*     */ {
/*  38 */   private CommercialOptionManager m_coManager = null;
/*     */ 
/*     */   public void setCommercialOptionManager(CommercialOptionManager a_coManager)
/*     */   {
/*  42 */     this.m_coManager = a_coManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     ActionForward afForward = null;
/*  72 */     CommercialOptionForm form = (CommercialOptionForm)a_form;
/*  73 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  76 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  78 */       this.m_logger.debug("This user is not logged in");
/*  79 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  83 */     long lId = getLongParameter(a_request, "id");
/*  84 */     if (lId > 0L)
/*     */     {
/*  86 */       if (!form.getHasErrors())
/*     */       {
/*  88 */         CommercialOption commOpt = this.m_coManager.getCommercialOption(a_dbTransaction, lId);
/*  89 */         form.setCommercialOption(commOpt);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 101 */     afForward = a_mapping.findForward("Success");
/* 102 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.commercialoption.action.ViewTermsAction
 * JD-Core Version:    0.6.0
 */