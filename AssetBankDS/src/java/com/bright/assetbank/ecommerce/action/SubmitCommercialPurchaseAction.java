/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.approval.service.AssetApprovalManager;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.ecommerce.bean.AssetInOrder;
/*     */ import com.bright.assetbank.ecommerce.bean.AssetPurchase;
/*     */ import com.bright.assetbank.ecommerce.form.CheckoutForm;
/*     */ import com.bright.assetbank.ecommerce.service.EcommerceManager;
/*     */ import com.bright.assetbank.ecommerce.service.OrderManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SubmitCommercialPurchaseAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  53 */   private OrderManager m_orderManager = null;
/*     */ 
/*  61 */   private AssetApprovalManager m_approvalManager = null;
/*     */ 
/*  67 */   private AssetBoxManager m_assetBoxManager = null;
/*     */ 
/*  72 */   private EcommerceManager m_ecommerceManager = null;
/*     */ 
/*  78 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setOrderManager(OrderManager a_orderManager)
/*     */   {
/*  57 */     this.m_orderManager = a_orderManager;
/*     */   }
/*     */ 
/*     */   public void setAssetApprovalManager(AssetApprovalManager a_approvalManager)
/*     */   {
/*  64 */     this.m_approvalManager = a_approvalManager;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/*  69 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public void setEcommerceManager(EcommerceManager a_ecommerceManager)
/*     */   {
/*  75 */     this.m_ecommerceManager = a_ecommerceManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  81 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 111 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 114 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/* 116 */       this.m_logger.debug("This user does not have permission to submit a commercial order");
/* 117 */       return a_mapping.findForward("NoPermission");
/*     */     }
/* 119 */     ActionForward afForward = null;
/*     */ 
/* 121 */     CheckoutForm form = (CheckoutForm)a_form;
/* 122 */     form.getErrors().clear();
/*     */ 
/* 124 */     if (!StringUtil.stringIsPopulated(form.getUserNotes()))
/*     */     {
/* 126 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "commercialPurchaseNoUserNotes", userProfile.getCurrentLanguage()).getBody());
/* 127 */       afForward = a_mapping.findForward("NoUserNotes");
/* 128 */       return afForward;
/*     */     }
/*     */ 
/* 131 */     AssetPurchase purchase = (AssetPurchase)ViewCheckoutAction.populatePurchase(form, userProfile, userProfile.getBasket());
/*     */ 
/* 134 */     String sKey = this.m_ecommerceManager.getNextPurchaseId(a_dbTransaction);
/* 135 */     purchase.setPurchaseId(sKey);
/*     */ 
/* 138 */     this.m_ecommerceManager.addPurchase(purchase, sKey);
/*     */ 
/* 140 */     for (Iterator assetIt = purchase.getAssetList().iterator(); assetIt.hasNext(); )
/*     */     {
/* 142 */       long lUserId = userProfile.getUser().getId();
/* 143 */       long lAssetId = ((AssetInOrder)assetIt.next()).getAssetId();
/*     */ 
/* 146 */       this.m_approvalManager.setAssetPendingApprovalForUser(a_dbTransaction, lAssetId, lUserId);
/*     */ 
/* 149 */       this.m_assetBoxManager.removeAssetPricesFromDBForUser(a_dbTransaction, lUserId, lAssetId);
/*     */     }
/*     */ 
/* 152 */     purchase.setHasCommercialUsage(true);
/*     */ 
/* 154 */     long lOrderId = this.m_orderManager.logOrder(a_dbTransaction, purchase);
/* 155 */     this.m_orderManager.updateOrderStatus(a_dbTransaction, lOrderId, 3L);
/* 156 */     this.m_orderManager.sendCommercialRequestSubmitted(a_dbTransaction, lOrderId);
/*     */ 
/* 158 */     this.m_assetBoxManager.refreshAssetBoxesInProfile(a_dbTransaction, userProfile);
/* 159 */     String sQueryString = StringUtil.makeQueryString(a_request.getParameterMap());
/*     */ 
/* 161 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */ 
/* 163 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.SubmitCommercialPurchaseAction
 * JD-Core Version:    0.6.0
 */