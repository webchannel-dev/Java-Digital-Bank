/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.commercialoption.service.CommercialOptionManager;
/*     */ import com.bright.assetbank.ecommerce.bean.Order;
/*     */ import com.bright.assetbank.ecommerce.bean.OrderPaymentPurchase;
/*     */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceSettings;
/*     */ import com.bright.assetbank.ecommerce.form.OrderPaymentForm;
/*     */ import com.bright.assetbank.ecommerce.psp.PSPPlugin;
/*     */ import com.bright.assetbank.ecommerce.psp.PSPPluginFactory;
/*     */ import com.bright.assetbank.ecommerce.service.EcommerceManager;
/*     */ import com.bright.assetbank.ecommerce.service.OrderManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewOrderPaymentAction extends BTransactionAction
/*     */ {
/*  51 */   private EcommerceManager m_ecommerceManager = null;
/*     */ 
/*  57 */   private OrderManager m_orderManager = null;
/*     */ 
/*  63 */   private CommercialOptionManager m_coManager = null;
/*     */ 
/*     */   public void setEcommerceManager(EcommerceManager a_ecommerceManager)
/*     */   {
/*  54 */     this.m_ecommerceManager = a_ecommerceManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(OrderManager a_orderManager)
/*     */   {
/*  60 */     this.m_orderManager = a_orderManager;
/*     */   }
/*     */ 
/*     */   public void setCommercialOptionManager(CommercialOptionManager a_coManager)
/*     */   {
/*  66 */     this.m_coManager = a_coManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  76 */     ActionForward afForward = null;
/*  77 */     OrderPaymentForm form = (OrderPaymentForm)a_form;
/*     */ 
/*  80 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  81 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  83 */       this.m_logger.debug("This user does not have permission");
/*  84 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  88 */     long lOrderId = getLongParameter(a_request, "id");
/*     */ 
/*  91 */     if (lOrderId > 0L)
/*     */     {
/*  93 */       form.resetOrderPaymentForm();
/*     */ 
/*  95 */       Order order = this.m_orderManager.getOrder(a_dbTransaction, lOrderId);
/*  96 */       form.setOrder(order);
/*     */     }
/*     */ 
/* 100 */     if (!form.getOrder().getRequiresOnlinePayment())
/*     */     {
/* 102 */       afForward = a_mapping.findForward("NoPaymentRequired");
/* 103 */       return afForward;
/*     */     }
/*     */ 
/* 107 */     String sTCDone = a_request.getParameter("accepttcs");
/* 108 */     if (StringUtil.stringIsPopulated(sTCDone))
/*     */     {
/* 110 */       form.setTcsDone(true);
/*     */     }
/*     */ 
/* 114 */     String sCommercialTCDone = a_request.getParameter("acceptcommercialtcs");
/* 115 */     if ((StringUtil.stringIsPopulated(sCommercialTCDone)) || (this.m_coManager.getCommercialOptionList(a_dbTransaction).isEmpty()))
/*     */     {
/* 117 */       form.setCommercialTcsDone(true);
/*     */     }
/*     */ 
/* 124 */     if (!form.getTcsDone())
/*     */     {
/* 126 */       afForward = a_mapping.findForward("Tscs");
/* 127 */       return afForward;
/*     */     }
/*     */ 
/* 131 */     if ((!form.getCommercialTcsDone()) && (AssetBankSettings.getUseCommercialOptions()))
/*     */     {
/* 133 */       afForward = a_mapping.findForward("CommercialTscs");
/* 134 */       return afForward;
/*     */     }
/*     */ 
/* 139 */     String sKey = "";
/* 140 */     Purchase purchase = populatePurchase(form, userProfile);
/*     */ 
/* 142 */     if (purchase != null)
/*     */     {
/* 145 */       sKey = this.m_ecommerceManager.getNextPurchaseId(a_dbTransaction);
/* 146 */       purchase.setPurchaseId(sKey);
/*     */ 
/* 149 */       this.m_ecommerceManager.addPurchase(purchase, sKey);
/*     */ 
/* 152 */       String sPSPPluginClassname = EcommerceSettings.getPspPluginClass();
/* 153 */       PSPPlugin pspPlugin = PSPPluginFactory.getPSPPluginInstance(sPSPPluginClassname);
/* 154 */       HashMap hmPSPForm = pspPlugin.createPurchaseForm(purchase, sKey);
/* 155 */       form.setPurchaseForm(hmPSPForm);
/* 156 */       form.setPurchaseFormKeys(hmPSPForm.keySet());
/*     */ 
/* 158 */       String sPostUrl = pspPlugin.getPSPUrl(purchase, sKey);
/* 159 */       form.setPspUrl(sPostUrl);
/*     */     }
/*     */ 
/* 163 */     String sQueryString = "trans_id=" + sKey;
/*     */ 
/* 165 */     afForward = createForward(sQueryString, a_mapping, "Ready");
/* 166 */     return afForward;
/*     */   }
/*     */ 
/*     */   private Purchase populatePurchase(OrderPaymentForm a_form, ABUserProfile userProfile)
/*     */   {
/* 186 */     OrderPaymentPurchase purchase = new OrderPaymentPurchase();
/*     */ 
/* 189 */     ABUser user = (ABUser)userProfile.getUser();
/* 190 */     purchase.setEmailAddress(user.getEmailAddress());
/* 191 */     purchase.setUserId(user.getId());
/* 192 */     purchase.setLoginUser(user.getUsername());
/* 193 */     purchase.setLoginPassword(user.getPassword());
/*     */ 
/* 196 */     Order order = a_form.getOrder();
/* 197 */     purchase.setOrder(order);
/*     */ 
/* 200 */     purchase.setChargedAmount(order.getTotal());
/* 201 */     purchase.setSubtotal(order.getSubtotal());
/* 202 */     purchase.setVatPercent(order.getVatPercent());
/*     */ 
/* 205 */     purchase.setRefPaymentProcessor(this.m_orderManager);
/*     */ 
/* 207 */     return purchase;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.ViewOrderPaymentAction
 * JD-Core Version:    0.6.0
 */