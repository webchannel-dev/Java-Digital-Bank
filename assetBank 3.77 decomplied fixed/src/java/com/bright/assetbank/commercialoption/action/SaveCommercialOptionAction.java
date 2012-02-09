/*     */ package com.bright.assetbank.commercialoption.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.commercialoption.bean.CommercialOption;
/*     */ import com.bright.assetbank.commercialoption.form.CommercialOptionForm;
/*     */ import com.bright.assetbank.commercialoption.service.CommercialOptionManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
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
/*     */ public class SaveCommercialOptionAction extends BTransactionAction
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/*  49 */   private CommercialOptionManager m_coManager = null;
/*     */ 
/* 121 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setCommercialOptionManager(CommercialOptionManager a_coManager)
/*     */   {
/*  53 */     this.m_coManager = a_coManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  82 */     ActionForward afForward = null;
/*  83 */     CommercialOptionForm form = (CommercialOptionForm)a_form;
/*  84 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  87 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  89 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  90 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  94 */     if (!StringUtil.stringIsPopulated(form.getCommercialOption().getName()))
/*     */     {
/*  96 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "commercialOptionInvalidName", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 100 */     BrightMoney price = form.getCommercialOption().getPrice();
/* 101 */     if ((!price.getIsFormAmountEntered()) || (!price.processFormData()))
/*     */     {
/* 103 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "commercialOptionInvalidPrice", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 106 */     String sQueryString = "";
/* 107 */     if (!form.getHasErrors())
/*     */     {
/* 109 */       this.m_coManager.saveCommercialOption(a_dbTransaction, form.getCommercialOption());
/*     */ 
/* 112 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */     else
/*     */     {
/* 116 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 119 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 124 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.commercialoption.action.SaveCommercialOptionAction
 * JD-Core Version:    0.6.0
 */