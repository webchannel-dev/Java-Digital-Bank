/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.commercialoption.bean.CommercialOption;
/*     */ import com.bright.assetbank.commercialoption.service.CommercialOptionManager;
/*     */ import com.bright.assetbank.ecommerce.bean.CommercialOptionPurchase;
/*     */ import com.bright.assetbank.ecommerce.form.CommercialOrderForm;
/*     */ import com.bright.assetbank.ecommerce.service.OrderManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class EditCommercialOptionPurchaseAction extends BTransactionAction
/*     */ {
/*  52 */   private OrderManager m_orderManager = null;
/*     */ 
/*  59 */   private CommercialOptionManager m_coManager = null;
/*     */ 
/*     */   public void setOrderManager(OrderManager a_orderManager)
/*     */   {
/*  56 */     this.m_orderManager = a_orderManager;
/*     */   }
/*     */ 
/*     */   public void setCommercialOptionManager(CommercialOptionManager a_coManager)
/*     */   {
/*  63 */     this.m_coManager = a_coManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  92 */     ActionForward afForward = null;
/*  93 */     CommercialOrderForm form = (CommercialOrderForm)a_form;
/*  94 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  97 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  99 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 100 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 104 */     String sReset = a_request.getParameter("commercialOptionPurchaseEditType");
/*     */ 
/* 106 */     if ((StringUtil.stringIsPopulated(sReset)) && (sReset.compareToIgnoreCase("reset") == 0))
/*     */     {
/* 108 */       Vector commercialOptionList = this.m_coManager.getCommercialOptionList(a_dbTransaction);
/* 109 */       form.setCommercialOptionsList(commercialOptionList);
/* 110 */       long lOrderId = getLongParameter(a_request, "orderId");
/* 111 */       long lAssetId = getLongParameter(a_request, "assetId");
/* 112 */       long lPriceBandId = getLongParameter(a_request, "priceBandId");
/* 113 */       CommercialOptionPurchase commOptPur = this.m_orderManager.getCommercialOptionPurchase(a_dbTransaction, lPriceBandId, lOrderId, lAssetId);
/*     */ 
/* 115 */       if (commOptPur == null)
/*     */       {
/* 117 */         commOptPur = new CommercialOptionPurchase();
/*     */       }
/*     */ 
/* 120 */       form.setCommercialOptionPurchase(commOptPur);
/* 121 */       form.setOrderId(lOrderId);
/* 122 */       form.setAssetId(lAssetId);
/* 123 */       form.setPriceBandId(lPriceBandId);
/*     */     }
/*     */     else
/*     */     {
/* 128 */       CommercialOptionPurchase commOptPur = form.getCommercialOptionPurchase();
/* 129 */       CommercialOption commOpt = this.m_coManager.getCommercialOption(a_dbTransaction, commOptPur.getCommercialOption().getId());
/* 130 */       commOptPur.setCommercialOption(commOpt);
/* 131 */       commOptPur.setToDefault();
/*     */     }
/*     */ 
/* 134 */     afForward = a_mapping.findForward("Success");
/* 135 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.EditCommercialOptionPurchaseAction
 * JD-Core Version:    0.6.0
 */