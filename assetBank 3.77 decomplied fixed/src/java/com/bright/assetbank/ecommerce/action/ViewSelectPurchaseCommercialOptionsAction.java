/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBasket;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.commercialoption.service.CommercialOptionManager;
/*     */ import com.bright.assetbank.ecommerce.form.CheckoutForm;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.Address;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewSelectPurchaseCommercialOptionsAction extends BTransactionAction
/*     */ {
/*  56 */   private CommercialOptionManager m_coManager = null;
/*     */ 
/*  63 */   private ABUserManager m_userManager = null;
/*     */ 
/*  69 */   private AssetBoxManager m_assetBoxManager = null;
/*     */ 
/*     */   public void setCommercialOptionManager(CommercialOptionManager a_coManager)
/*     */   {
/*  59 */     this.m_coManager = a_coManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/*  66 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/*  72 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  97 */     ActionForward afForward = null;
/*  98 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 101 */     this.m_assetBoxManager.refreshAssetBoxInProfile(a_dbTransaction, userProfile, userProfile.getAssetBox().getId());
/*     */ 
/* 104 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/* 106 */       this.m_logger.debug("This user does not have permission to view the select commercial option page");
/* 107 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 111 */     ABUser user = (ABUser)this.m_userManager.getUser(a_dbTransaction, userProfile.getUser().getId());
/* 112 */     if ((!user.getHomeAddress().isValidPostalAddress()) || (!user.isContactDetailsPopulated()))
/*     */     {
/* 114 */       return a_mapping.findForward("NoProfileAddress");
/*     */     }
/*     */ 
/* 117 */     userProfile.getBasket().getAssetPriceCommercialOptions().clear();
/*     */ 
/* 119 */     CheckoutForm form = (CheckoutForm)a_form;
/*     */ 
/* 121 */     form.setAssetPriceCommercialOptions(userProfile.getBasket().getAssetPriceCommercialOptions());
/*     */ 
/* 123 */     Vector vecCommOptList = this.m_coManager.getCommercialOptionList(a_dbTransaction);
/*     */ 
/* 126 */     if (vecCommOptList.isEmpty())
/*     */     {
/* 128 */       return a_mapping.findForward("NoCommercialOptions");
/*     */     }
/*     */ 
/* 131 */     if (!userProfile.getCurrentLanguage().equals(LanguageConstants.k_defaultLanguage))
/*     */     {
/* 133 */       LanguageUtils.setLanguageOnAll(vecCommOptList, userProfile.getCurrentLanguage());
/*     */     }
/*     */ 
/* 136 */     form.setCommercialOptionsList(vecCommOptList);
/*     */ 
/* 138 */     afForward = a_mapping.findForward("Success");
/* 139 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.ViewSelectPurchaseCommercialOptionsAction
 * JD-Core Version:    0.6.0
 */