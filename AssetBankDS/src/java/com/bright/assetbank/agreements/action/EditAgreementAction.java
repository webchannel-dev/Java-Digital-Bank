/*     */ package com.bright.assetbank.agreements.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.agreements.bean.Agreement;
/*     */ import com.bright.assetbank.agreements.form.AgreementsForm;
/*     */ import com.bright.assetbank.agreements.service.AgreementsManager;
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
/*     */ public class EditAgreementAction extends BTransactionAction
/*     */ {
/*  44 */   private AgreementsManager m_agreementsManager = null;
/*     */ 
/*     */   public void setAgreementsManager(AgreementsManager a_agreementsManager) {
/*  47 */     this.m_agreementsManager = a_agreementsManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  57 */     ActionForward afForward = null;
/*  58 */     AgreementsForm form = (AgreementsForm)a_form;
/*  59 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  60 */     Agreement agreement = null;
/*     */ 
/*  62 */     long a_lAgreementId = getLongParameter(a_request, "id");
/*  63 */     int a_iCopyEdit = getIntParameter(a_request, "copy");
/*     */ 
/*  66 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()) && (!userProfile.getCanUpdateAssets()))
/*     */     {
/*  68 */       this.m_logger.error("EditAgreementsAction.execute : User does not have permission.");
/*  69 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  72 */     if (!form.getHasErrors())
/*     */     {
/*  75 */       if (a_lAgreementId > 0L)
/*     */       {
/*  77 */         agreement = this.m_agreementsManager.getAgreement(a_dbTransaction, a_lAgreementId);
/*     */       }
/*     */       else
/*     */       {
/*  81 */         agreement = new Agreement();
/*     */ 
/*  84 */         agreement.setIsSharedWithOU(true);
/*     */       }
/*     */ 
/*  87 */       form.setAgreement(agreement);
/*     */ 
/*  90 */       if ((a_iCopyEdit > 0) && ((form.getAgreement().getIsAvailableToAll()) || (form.getAgreement().getIsSharedWithOU())))
/*     */       {
/*  92 */         form.getAgreement().setId(-1L);
/*  93 */         form.getAgreement().setIsAvailableToAll(false);
/*  94 */         form.getAgreement().setIsSharedWithOU(false);
/*     */       }
/*     */     }
/*     */ 
/*  98 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 100 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.agreements.action.EditAgreementAction
 * JD-Core Version:    0.6.0
 */