/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBasket;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceConstants;
/*     */ import com.bright.assetbank.ecommerce.form.CheckoutForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.action.LoginAction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Collection;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class CheckoutLoginAction extends LoginAction
/*     */   implements EcommerceConstants, AssetBoxConstants
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  58 */     ABUserManager userManager = (ABUserManager)this.m_userManager;
/*     */ 
/*  60 */     ActionForward afForward = null;
/*  61 */     CheckoutForm form = (CheckoutForm)a_form;
/*  62 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  65 */     AssetBasket assetBasket = userProfile.getBasket();
/*  66 */     Collection colToBeApproved = null;
/*  67 */     if (AssetBankSettings.getUsePriceBands())
/*     */     {
/*  69 */       colToBeApproved = assetBasket.getAssetsWithPriceBands();
/*     */     }
/*     */     else
/*     */     {
/*  73 */       colToBeApproved = assetBasket.getAssetsInState(3);
/*     */     }
/*  75 */     form.setApprovalList(colToBeApproved);
/*     */ 
/*  78 */     form.resetFlags();
/*     */ 
/*  81 */     String sRegister = a_request.getParameter("register");
/*  82 */     if (StringUtil.stringIsPopulated(sRegister))
/*     */     {
/*  85 */       afForward = a_mapping.findForward("Failure");
/*     */ 
/*  88 */       if (!StringUtil.isValidEmailAddress(form.getRegisterEmailAddress()))
/*     */       {
/*  90 */         form.setInvalidEmailFormat(true);
/*     */       }
/*  95 */       else if (userManager.isUsernameInUse(a_dbTransaction, form.getRegisterEmailAddress()))
/*     */       {
/*  97 */         form.setUsernameExists(true);
/*     */       }
/* 102 */       else if (form.getRegisterEmailAddress().compareToIgnoreCase(form.getConfirmRegisterEmailAddress()) != 0)
/*     */       {
/* 104 */         form.setRegisterEmailsDontMatch(true);
/*     */       }
/*     */       else
/*     */       {
/* 108 */         form.setIsRegistered(true);
/* 109 */         afForward = a_mapping.findForward("Success");
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 117 */       afForward = super.execute(a_mapping, a_form, a_request, a_response, a_dbTransaction);
/*     */     }
/*     */ 
/* 120 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.CheckoutLoginAction
 * JD-Core Version:    0.6.0
 */