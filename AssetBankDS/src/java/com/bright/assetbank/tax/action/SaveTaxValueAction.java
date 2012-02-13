/*     */ package com.bright.assetbank.tax.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.tax.bean.TaxValue;
/*     */ import com.bright.assetbank.tax.form.TaxValueForm;
/*     */ import com.bright.assetbank.tax.service.TaxManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.BrightDecimal;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveTaxValueAction extends BTransactionAction
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/*  48 */   private TaxManager m_taxManager = null;
/*     */ 
/* 109 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setTaxManager(TaxManager a_taxManager)
/*     */   {
/*  51 */     this.m_taxManager = a_taxManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  75 */     ActionForward afForward = null;
/*  76 */     TaxValueForm form = (TaxValueForm)a_form;
/*  77 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  80 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  82 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  83 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  87 */     BrightDecimal percent = form.getTaxValue().getTaxPercent();
/*  88 */     if ((!percent.getIsFormNumberEntered()) || (!percent.processFormData()))
/*     */     {
/*  90 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "taxInvalidPercent", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/*  93 */     String sQueryString = "";
/*  94 */     if (!form.getHasErrors())
/*     */     {
/*  96 */       this.m_taxManager.saveTaxValue(a_dbTransaction, form.getTaxValue());
/*     */ 
/*  99 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */     else
/*     */     {
/* 103 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 106 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 112 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.tax.action.SaveTaxValueAction
 * JD-Core Version:    0.6.0
 */