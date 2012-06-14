/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.assetbox.bean.AssetPrice;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.priceband.bean.DownloadPriceBand;
/*     */ import com.bright.assetbank.priceband.bean.PrintPriceBand;
/*     */ import com.bright.assetbank.priceband.service.PriceBandManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.BrightNaturalNumber;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AddAssetPricesToAssetBoxAction extends BTransactionAction
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/*     */   private static final String k_sParam_PurchaseItem = "purchaseItem";
/*  52 */   private AssetBoxManager m_assetBoxManager = null;
/*     */ 
/*  58 */   private PriceBandManager m_priceBandManager = null;
/*     */ 
/*  63 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/*  55 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public void setPriceBandManager(PriceBandManager a_manager)
/*     */   {
/*  61 */     this.m_priceBandManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  66 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  76 */     ActionForward afForward = null;
/*  77 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  78 */     AssetForm form = (AssetForm)a_form;
/*     */ 
/*  81 */     long lAssetId = getLongParameter(a_request, "id");
/*     */ 
/*  84 */     Enumeration enumParams = a_request.getParameterNames();
/*  85 */     Vector vecAssetPrices = new Vector();
/*     */ 
/*  87 */     boolean bHasCommercialUsages = false;
/*  88 */     boolean bHasNonCommercialUsages = false;
/*     */ 
/*  90 */     while (enumParams.hasMoreElements())
/*     */     {
/*  92 */       String sParamName = (String)enumParams.nextElement();
/*     */ 
/*  94 */       if (sParamName.startsWith("downloadpriceband_"))
/*     */       {
/*  96 */         String sId = sParamName.substring("downloadpriceband_".length());
/*  97 */         long lPriceBandId = Long.parseLong(sId);
/*     */ 
/* 100 */         DownloadPriceBand pb = (DownloadPriceBand)this.m_priceBandManager.getPriceBand(a_dbTransaction, lPriceBandId);
/*     */ 
/* 102 */         AssetPrice price = new AssetPrice();
/* 103 */         price.setAssetId(lAssetId);
/* 104 */         price.setPriceBand(pb);
/* 105 */         price.setQuantity(1);
/*     */ 
/* 107 */         vecAssetPrices.add(price);
/*     */ 
/* 110 */         if (pb.getIsCommercial())
/*     */         {
/* 112 */           bHasCommercialUsages = true;
/*     */         }
/*     */         else
/*     */         {
/* 116 */           bHasNonCommercialUsages = true;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 121 */       if (sParamName.startsWith("printpriceband_"))
/*     */       {
/* 123 */         String sId = sParamName.substring("printpriceband_".length());
/* 124 */         long lPriceBandId = Long.parseLong(sId);
/*     */ 
/* 127 */         PrintPriceBand pb = (PrintPriceBand)this.m_priceBandManager.getPriceBand(a_dbTransaction, lPriceBandId);
/* 128 */         long lMaxQuantity = pb.getMaxQuantity().getNumber();
/*     */ 
/* 131 */         String sQuantity = a_request.getParameter(sParamName);
/* 132 */         BrightNaturalNumber quantity = new BrightNaturalNumber();
/* 133 */         quantity.setFormNumber(sQuantity);
/*     */ 
/* 135 */         if (quantity.getIsFormNumberEntered())
/*     */         {
/* 137 */           if (!quantity.processFormData())
/*     */           {
/* 139 */             form.addError(this.m_listManager.getListItem(a_dbTransaction, "priceBandInvalidQuantity", userProfile.getCurrentLanguage()).getBody());
/* 140 */             break;
/*     */           }
/*     */ 
/* 143 */           int iQuantity = new Long(quantity.getNumber()).intValue();
/* 144 */           if (iQuantity < 0)
/*     */           {
/* 146 */             form.addError(this.m_listManager.getListItem(a_dbTransaction, "priceBandInvalidQuantity", userProfile.getCurrentLanguage()).getBody());
/* 147 */             break;
/*     */           }
/*     */ 
/* 150 */           if ((lMaxQuantity > 0L) && (iQuantity > lMaxQuantity))
/*     */           {
/* 152 */             form.addError(this.m_listManager.getListItem(a_dbTransaction, "priceBandMaxQuantity", userProfile.getCurrentLanguage()).getBody().replaceFirst("%", new Long(lMaxQuantity).toString()));
/* 153 */             break;
/*     */           }
/*     */ 
/* 157 */           if (iQuantity > 0)
/*     */           {
/* 160 */             AssetPrice price = new AssetPrice();
/* 161 */             price.setAssetId(lAssetId);
/* 162 */             price.setPriceBand(pb);
/* 163 */             price.setQuantity(iQuantity);
/*     */ 
/* 165 */             vecAssetPrices.add(price);
/*     */ 
/* 167 */             bHasNonCommercialUsages = true;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 174 */     if ((!form.getHasErrors()) && (vecAssetPrices.size() == 0) && (StringUtils.isNotEmpty(a_request.getParameter("purchaseItem"))))
/*     */     {
/* 176 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "priceBandNoQuantity", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 180 */     if ((!form.getHasErrors()) && (bHasCommercialUsages) && (bHasNonCommercialUsages))
/*     */     {
/* 182 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "priceBandMixedUsages", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 186 */     if (!form.getHasErrors())
/*     */     {
/* 190 */       this.m_assetBoxManager.setPricesForAsset(a_dbTransaction, userProfile, lAssetId, vecAssetPrices);
/*     */ 
/* 193 */       String sForwardAction = a_request.getParameter("forward");
/*     */ 
/* 196 */       String sQueryString = "?" + StringUtil.makeQueryString(a_request.getParameterMap());
/*     */ 
/* 199 */       afForward = new ActionForward(sForwardAction + sQueryString, true);
/*     */     }
/*     */     else
/*     */     {
/* 204 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/* 206 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.AddAssetPricesToAssetBoxAction
 * JD-Core Version:    0.6.0
 */