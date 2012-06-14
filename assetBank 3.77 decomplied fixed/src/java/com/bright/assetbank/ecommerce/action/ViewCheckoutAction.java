/*     */ package com.bright.assetbank.ecommerce.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBasket;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.assetbox.bean.AssetPrice;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.commercialoption.bean.CommercialOption;
/*     */ import com.bright.assetbank.commercialoption.service.CommercialOptionManager;
/*     */ import com.bright.assetbank.ecommerce.bean.AssetInOrder;
/*     */ import com.bright.assetbank.ecommerce.bean.AssetPurchase;
/*     */ import com.bright.assetbank.ecommerce.bean.AssetPurchasePriceBand;
/*     */ import com.bright.assetbank.ecommerce.bean.AssetWithShippingPurchase;
/*     */ import com.bright.assetbank.ecommerce.bean.CommercialOptionPurchase;
/*     */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*     */ import com.bright.assetbank.ecommerce.constant.EcommerceSettings;
/*     */ import com.bright.assetbank.ecommerce.form.CheckoutForm;
/*     */ import com.bright.assetbank.ecommerce.psp.PSPPlugin;
/*     */ import com.bright.assetbank.ecommerce.psp.PSPPluginFactory;
/*     */ import com.bright.assetbank.ecommerce.service.EcommerceManager;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.priceband.bean.PriceBand;
/*     */ import com.bright.assetbank.tax.bean.TaxValue;
/*     */ import com.bright.assetbank.tax.bean.TaxablePrice;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.Address;
/*     */ import com.bright.framework.common.bean.BrightDecimal;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewCheckoutAction extends BTransactionAction
/*     */   implements AssetBoxConstants, AssetBankConstants
/*     */ {
/* 417 */   private EcommerceManager m_ecommerceManager = null;
/*     */ 
/* 423 */   private AssetBoxManager m_assetBoxManager = null;
/*     */ 
/* 429 */   private LanguageManager m_languageManager = null;
/*     */ 
/* 435 */   private CommercialOptionManager m_coManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  91 */     ActionForward afForward = null;
/*  92 */     CheckoutForm form = (CheckoutForm)a_form;
/*  93 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  96 */     StringDataBean shippingRegion = ((AssetBasket)userProfile.getAssetBox()).getShippingRegion();
/*     */ 
/*  99 */     this.m_assetBoxManager.refreshAssetBoxInProfile(a_dbTransaction, userProfile, userProfile.getAssetBox().getId());
/*     */ 
/* 101 */     AssetBasket assetBasket = userProfile.getBasket();
/*     */ 
/* 103 */     assetBasket.setShippingRegion(shippingRegion);
/*     */ 
/* 106 */     String sCheckout = a_request.getParameter("checkout");
/*     */ 
/* 110 */     if ((StringUtil.stringIsPopulated(sCheckout)) && (sCheckout.compareToIgnoreCase("checkout") == 0))
/*     */     {
/* 112 */       form.resetCheckoutForm();
/*     */ 
/* 115 */       this.m_assetBoxManager.refreshAssetBoxShippingRegion(a_dbTransaction, userProfile, null);
/*     */     }
/*     */ 
/* 119 */     if ((assetBasket.getHasCommercialUsage()) && (assetBasket.getHasNonCommercialUsage()))
/*     */     {
/* 121 */       afForward = a_mapping.findForward("MixedCommercial");
/* 122 */       return afForward;
/*     */     }
/*     */ 
/* 126 */     if ((StringUtil.stringIsPopulated(sCheckout)) && (sCheckout.compareToIgnoreCase("register") == 0))
/*     */     {
/* 128 */       form.setIsProfileEntered(false);
/*     */     }
/*     */ 
/* 132 */     if (EcommerceSettings.getPurchaseShowTCs())
/*     */     {
/* 134 */       String sTCDone = a_request.getParameter("accepttcs");
/* 135 */       if (StringUtil.stringIsPopulated(sTCDone))
/*     */       {
/* 137 */         form.setTcsDone(true);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 143 */       form.setTcsDone(true);
/*     */     }
/*     */ 
/* 147 */     Collection colToBeApproved = null;
/* 148 */     if (AssetBankSettings.getUsePriceBands())
/*     */     {
/* 150 */       colToBeApproved = assetBasket.getAssetsWithPriceBands();
/*     */     }
/*     */     else
/*     */     {
/* 154 */       colToBeApproved = assetBasket.getAssetsInState(3);
/*     */     }
/* 156 */     form.setApprovalList(colToBeApproved);
/*     */ 
/* 162 */     if ((!userProfile.getIsLoggedIn()) && (!form.getIsRegistered()))
/*     */     {
/* 164 */       afForward = a_mapping.findForward("Login");
/* 165 */       return afForward;
/*     */     }
/*     */ 
/* 169 */     if ((AssetBankSettings.getEcommerceUserAddressMandatory()) && (form.getIsRegistered()) && (!form.getIsProfileEntered()))
/*     */     {
/* 172 */       form.getUser().setEmailAddress(form.getRegisterEmailAddress());
/* 173 */       form.setLanguages(this.m_languageManager.getLanguages(a_dbTransaction, true, false));
/* 174 */       afForward = a_mapping.findForward("Register");
/* 175 */       return afForward;
/*     */     }
/*     */ 
/* 179 */     if ((AssetBankSettings.getUseShippingCosts()) && (assetBasket.getHasPrint()) && (!form.getIsShippingEntered()))
/*     */     {
/* 182 */       if ((userProfile.getIsLoggedIn()) && (!form.getHasErrors()))
/*     */       {
/* 184 */         ABUser user = (ABUser)userProfile.getUser();
/* 185 */         Address newAddress = new Address();
/* 186 */         newAddress.copy(user.getHomeAddress());
/* 187 */         form.setShippingAddress(newAddress);
/*     */ 
/* 189 */         String sName = user.getFullName();
/* 190 */         form.setShippingUser(sName);
/*     */       }
/*     */ 
/* 193 */       afForward = a_mapping.findForward("Shipping");
/* 194 */       return afForward;
/*     */     }
/*     */ 
/* 198 */     if (!form.getTcsDone())
/*     */     {
/* 200 */       afForward = a_mapping.findForward("Tscs");
/* 201 */       return afForward;
/*     */     }
/*     */ 
/* 205 */     form.setCommercialOptionsList(this.m_coManager.getCommercialOptionList(a_dbTransaction));
/*     */ 
/* 207 */     String sQueryString = "";
/*     */ 
/* 218 */     if ((!AssetBankSettings.getUseCommercialOptions()) || (assetBasket.getHasNonCommercialUsage()))
/*     */     {
/* 222 */       String sKey = "";
/* 223 */       Purchase purchase = populatePurchase(form, userProfile, assetBasket);
/*     */ 
/* 225 */       if (purchase != null)
/*     */       {
/* 228 */         purchase.setRefPaymentProcessor(this.m_ecommerceManager);
/*     */ 
/* 231 */         sKey = this.m_ecommerceManager.getNextPurchaseId(a_dbTransaction);
/* 232 */         purchase.setPurchaseId(sKey);
/*     */ 
/* 235 */         this.m_ecommerceManager.addPurchase(purchase, sKey);
/*     */ 
/* 238 */         String sPSPPluginClassname = EcommerceSettings.getPspPluginClass();
/* 239 */         PSPPlugin pspPlugin = PSPPluginFactory.getPSPPluginInstance(sPSPPluginClassname);
/* 240 */         HashMap hmPSPForm = pspPlugin.createPurchaseForm(purchase, sKey);
/* 241 */         form.setPurchaseForm(hmPSPForm);
/* 242 */         form.setPurchaseFormKeys(hmPSPForm.keySet());
/*     */ 
/* 244 */         String sPostUrl = pspPlugin.getPSPUrl(purchase, sKey);
/* 245 */         form.setPspUrl(sPostUrl);
/*     */       }
/*     */ 
/* 249 */       sQueryString = "trans_id=" + sKey;
/*     */     }
/*     */ 
/* 252 */     afForward = createForward(sQueryString, a_mapping, "Ready");
/* 253 */     return afForward;
/*     */   }
/*     */ 
/*     */   public static Purchase populatePurchase(CheckoutForm form, ABUserProfile userProfile, AssetBasket assetBox)
/*     */   {
/* 275 */     AssetPurchase purchase = null;
/*     */ 
/* 277 */     if (AssetBankSettings.getUsePriceBands())
/*     */     {
/* 279 */       purchase = new AssetWithShippingPurchase();
/*     */ 
/* 282 */       AssetWithShippingPurchase awsp = (AssetWithShippingPurchase)purchase;
/* 283 */       awsp.setHasPrints(assetBox.getHasPrint());
/*     */ 
/* 286 */       awsp.setRecipient(form.getShippingUser());
/* 287 */       awsp.setShippingAddress(form.getShippingAddress());
/* 288 */       awsp.getShippingCost().setAmount(assetBox.getShippingTotal().getAmount());
/*     */     }
/*     */     else
/*     */     {
/* 293 */       purchase = new AssetPurchase();
/*     */     }
/*     */ 
/* 297 */     String sEmailAddress = null;
/* 298 */     if (userProfile.getIsLoggedIn())
/*     */     {
/* 300 */       ABUser user = (ABUser)userProfile.getUser();
/* 301 */       sEmailAddress = user.getEmailAddress();
/* 302 */       purchase.setEmailAddress(sEmailAddress);
/* 303 */       purchase.setUserId(user.getId());
/* 304 */       purchase.setLoginUser(user.getUsername());
/* 305 */       purchase.setLoginPassword(user.getPassword());
/*     */     }
/* 309 */     else if (form.getIsRegistered())
/*     */     {
/* 311 */       sEmailAddress = form.getRegisterEmailAddress();
/* 312 */       purchase.setEmailAddress(sEmailAddress);
/* 313 */       purchase.setHasRegistered(true);
/*     */ 
/* 316 */       purchase.setRegisterUser(form.getUser());
/*     */     }
/*     */ 
/* 321 */     Vector assetList = new Vector();
/*     */ 
/* 324 */     HashMap hmAssetPrices = assetBox.getAssetPrices();
/* 325 */     HashMap hmAssetPriceAmounts = assetBox.getAssetPriceAmounts();
/*     */ 
/* 328 */     Collection assetsToCopy = null;
/* 329 */     if (AssetBankSettings.getUsePriceBands())
/*     */     {
/* 331 */       assetsToCopy = assetBox.getAssetsWithPriceBands();
/*     */     }
/*     */     else
/*     */     {
/* 335 */       assetsToCopy = assetBox.getAssetsInState(3);
/*     */     }
/* 337 */     Iterator itAssets = assetsToCopy.iterator();
/* 338 */     while (itAssets.hasNext())
/*     */     {
/* 340 */       AssetInList ail = (AssetInList)itAssets.next();
/*     */ 
/* 342 */       AssetInOrder aio = new AssetInOrder();
/* 343 */       long lAssetId = ail.getId();
/* 344 */       aio.setAssetId(lAssetId);
/* 345 */       aio.setDescription(ail.getAsset().getName());
/* 346 */       aio.setPrice(ail.getAsset().getPrice());
/*     */ 
/* 349 */       if (AssetBankSettings.getUsePriceBands())
/*     */       {
/* 352 */         BrightMoney assetAmount = (BrightMoney)hmAssetPriceAmounts.get(new Long(lAssetId));
/* 353 */         if (assetAmount != null)
/*     */         {
/* 355 */           aio.setPrice(assetAmount);
/*     */         }
/*     */ 
/* 360 */         Vector vecAPPBs = new Vector();
/* 361 */         long lAssetShippingCost = 0L;
/*     */ 
/* 364 */         Vector vecAssetPrices = (Vector)hmAssetPrices.get(new Long(lAssetId));
/* 365 */         Iterator itPriceBands = vecAssetPrices.iterator();
/* 366 */         while (itPriceBands.hasNext())
/*     */         {
/* 368 */           AssetPrice ap = (AssetPrice)itPriceBands.next();
/*     */ 
/* 370 */           AssetPurchasePriceBand appb = new AssetPurchasePriceBand();
/*     */ 
/* 372 */           long lTaxRegionId = assetBox.getShippingRegion().getId();
/* 373 */           appb.setCost(ap.getAssetPrice());
/* 374 */           appb.setShippingCost(ap.getShippingCost(lTaxRegionId));
/* 375 */           appb.setQuantity(ap.getQuantity());
/* 376 */           appb.setPriceBand(new StringDataBean(ap.getPriceBand().getId(), ap.getPriceBand().getName()));
/* 377 */           appb.setPriceBandType(ap.getPriceBand().getPriceBandType());
/*     */ 
/* 380 */           lAssetShippingCost += ap.getShippingCost(lTaxRegionId).getAmount();
/*     */ 
/* 383 */           CommercialOption commOpt = assetBox.getAssetPriceCommercialOption(ap.getHashInAssetBox());
/* 384 */           if ((commOpt != null) && (commOpt.getId() > 0L))
/*     */           {
/* 386 */             appb.setCommercialOptionPurchase(new CommercialOptionPurchase(commOpt));
/*     */           }
/*     */ 
/* 389 */           vecAPPBs.add(appb);
/*     */         }
/*     */ 
/* 392 */         aio.setShippingCost(new BrightMoney(lAssetShippingCost));
/* 393 */         aio.setPriceBands(vecAPPBs);
/*     */       }
/*     */ 
/* 397 */       assetList.add(aio);
/*     */     }
/*     */ 
/* 400 */     purchase.setAssetList(assetList);
/* 401 */     purchase.setFlags();
/* 402 */     purchase.setPrefersOfflinePayment(form.getPrefersOfflinePayment());
/* 403 */     purchase.setUserNotes(form.getUserNotes());
/*     */ 
/* 405 */     purchase.getBasketCost().setAmount(assetBox.getBasketTotal().getAmount());
/*     */ 
/* 409 */     purchase.getChargedAmount().setAmount(assetBox.getTotalWithDiscount(userProfile.getMaxDiscount()).getAmount());
/* 410 */     purchase.getSubtotal().setAmount(assetBox.getPrice().getSubtotalAmount().getAmount());
/* 411 */     purchase.getVatPercent().setNumber(assetBox.getPrice().getTax().getTaxPercent().getNumber());
/* 412 */     purchase.setDiscountPercentage(userProfile.getMaxDiscount());
/*     */ 
/* 414 */     return purchase;
/*     */   }
/*     */ 
/*     */   public void setEcommerceManager(EcommerceManager a_ecommerceManager)
/*     */   {
/* 420 */     this.m_ecommerceManager = a_ecommerceManager;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/* 426 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/* 432 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ 
/*     */   public void setCommercialOptionManager(CommercialOptionManager a_coManager)
/*     */   {
/* 438 */     this.m_coManager = a_coManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.ecommerce.action.ViewCheckoutAction
 * JD-Core Version:    0.6.0
 */