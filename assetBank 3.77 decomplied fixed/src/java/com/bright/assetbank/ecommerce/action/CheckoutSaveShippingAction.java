/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.ecommerce.form.CheckoutForm;
/*     */ import com.bright.assetbank.user.action.UserAction;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.bean.Address;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class CheckoutSaveShippingAction extends UserAction
/*     */   implements MessageConstants
/*     */ {
/*  40 */   private AssetBoxManager m_assetBoxManager = null;
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager) {
/*  43 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  53 */     ActionForward afForward = null;
/*  54 */     CheckoutForm form = (CheckoutForm)a_form;
/*  55 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  58 */     form.resetErrors();
/*  59 */     validateMandatoryFields(form, a_request);
/*  60 */     validateFieldLengths(form, a_request);
/*     */ 
/*  63 */     Address shippingAddress = form.getShippingAddress();
/*  64 */     String sShippingUser = form.getShippingUser();
/*     */ 
/*  67 */     if (form.getHasErrors())
/*     */     {
/*  69 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */     else
/*     */     {
/*  76 */       if (form.getCopyShippingAddressToProfile())
/*     */       {
/*  78 */         Address userAddress = new Address();
/*  79 */         userAddress.copy(shippingAddress);
/*  80 */         form.getUser().setHomeAddress(userAddress);
/*     */ 
/*  83 */         String[] asName = StringUtil.convertToArray(sShippingUser, " ");
/*  84 */         if (asName.length > 0)
/*     */         {
/*  86 */           form.getUser().setForename(asName[0]);
/*     */         }
/*  88 */         if (asName.length > 1)
/*     */         {
/*  90 */           form.getUser().setSurname(asName[1]);
/*     */         }
/*     */ 
/*  94 */         if (userProfile.getIsLoggedIn())
/*     */         {
/*  96 */           long lUserId = userProfile.getUser().getId();
/*  97 */           getUserManager().updateUserAddress(a_dbTransaction, lUserId, shippingAddress);
/*  98 */           userProfile.setUser(getUserManager().getUser(a_dbTransaction, lUserId));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 103 */       this.m_assetBoxManager.refreshAssetBoxShippingRegion(a_dbTransaction, userProfile, shippingAddress);
/*     */ 
/* 106 */       form.setIsShippingEntered(true);
/*     */ 
/* 108 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 111 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.CheckoutSaveShippingAction
 * JD-Core Version:    0.6.0
 */