/*     */ package com.bright.assetbank.priceband.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.priceband.bean.PriceBand;
/*     */ import com.bright.assetbank.priceband.bean.PrintPriceBand;
/*     */ import com.bright.assetbank.priceband.bean.QuantityRange;
/*     */ import com.bright.assetbank.priceband.bean.ShippingCost;
/*     */ import com.bright.assetbank.priceband.constant.PriceBandConstants;
/*     */ import com.bright.assetbank.priceband.form.PriceBandForm;
/*     */ import com.bright.assetbank.priceband.service.PriceBandManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.common.bean.BrightNaturalNumber;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.common.bean.TranslatableStringDataBean;
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
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SavePriceBandAction extends BTransactionAction
/*     */   implements MessageConstants, PriceBandConstants, AssetBankConstants
/*     */ {
/*  56 */   private PriceBandManager m_pbManager = null;
/*     */ 
/*  62 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setPriceBandManager(PriceBandManager a_pbManager)
/*     */   {
/*  59 */     this.m_pbManager = a_pbManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  65 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  88 */     ActionForward afForward = null;
/*  89 */     PriceBandForm form = (PriceBandForm)a_form;
/*  90 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  93 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  95 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  96 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 100 */     long lTypeId = form.getPriceBand().getPriceBandType().getId();
/*     */ 
/* 103 */     if (!StringUtil.stringIsPopulated(form.getPriceBand().getName()))
/*     */     {
/* 105 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "priceBandInvalidName", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 111 */     BrightMoney basePrice = form.getPriceBand().getBasePrice();
/* 112 */     if ((!basePrice.getIsFormAmountEntered()) || (!basePrice.processFormData()))
/*     */     {
/* 114 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "priceBandInvalidBasePrice", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 118 */     BrightMoney unitPrice = form.getPriceBand().getUnitPrice();
/* 119 */     if (((!unitPrice.getIsFormAmountEntered()) && (lTypeId == 2L)) || (!unitPrice.processFormData()))
/*     */     {
/* 121 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "priceBandInvalidUnitPrice", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 125 */     if (lTypeId == 2L)
/*     */     {
/* 127 */       PrintPriceBand ppb = (PrintPriceBand)form.getPriceBand();
/* 128 */       BrightNaturalNumber maxQuantity = ppb.getMaxQuantity();
/* 129 */       if ((!maxQuantity.getIsFormNumberEntered()) || (!maxQuantity.processFormData()))
/*     */       {
/* 131 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "priceBandInvalidMaxQuantity", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */     }
/*     */ 
/* 135 */     String sQueryString = "";
/* 136 */     if (!form.getHasErrors())
/*     */     {
/* 138 */       this.m_pbManager.savePriceBand(a_dbTransaction, form.getPriceBand());
/*     */ 
/* 141 */       long lPriceBandId = form.getPriceBand().getId();
/*     */ 
/* 144 */       if (lTypeId == 1L)
/*     */       {
/* 147 */         this.m_pbManager.removeUsagesForPriceBand(a_dbTransaction, lPriceBandId);
/*     */ 
/* 150 */         Enumeration enumParams = a_request.getParameterNames();
/*     */ 
/* 152 */         while (enumParams.hasMoreElements())
/*     */         {
/* 154 */           String sParamName = (String)enumParams.nextElement();
/*     */ 
/* 157 */           if (sParamName.startsWith("includedUsage_"))
/*     */           {
/* 160 */             String sId = sParamName.substring("includedUsage_".length());
/* 161 */             long lUsageTypeId = Long.parseLong(sId);
/*     */ 
/* 164 */             this.m_pbManager.addPriceBandUsage(a_dbTransaction, lPriceBandId, lUsageTypeId);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 173 */       if (lTypeId == 2L)
/*     */       {
/* 175 */         Vector vecCosts = new Vector();
/*     */ 
/* 178 */         Enumeration enumParams = a_request.getParameterNames();
/*     */ 
/* 180 */         boolean bCostsValid = true;
/* 181 */         while (enumParams.hasMoreElements())
/*     */         {
/* 183 */           String sParamName = (String)enumParams.nextElement();
/*     */ 
/* 186 */           if (sParamName.startsWith("region_"))
/*     */           {
/* 192 */             int iPosQuantity = sParamName.indexOf("quantity_");
/* 193 */             if (iPosQuantity > 0)
/*     */             {
/* 195 */               String sRegionId = sParamName.substring("region_".length(), iPosQuantity - 1);
/* 196 */               long lRegionId = Long.parseLong(sRegionId);
/*     */ 
/* 198 */               String sQuantityId = sParamName.substring(iPosQuantity + "quantity_".length());
/* 199 */               long lQuantityId = Long.parseLong(sQuantityId);
/*     */ 
/* 202 */               String sCost = a_request.getParameter(sParamName);
/* 203 */               BrightMoney cost = new BrightMoney();
/* 204 */               cost.setFormAmount(sCost);
/* 205 */               if (!cost.processFormData())
/*     */               {
/* 207 */                 bCostsValid = false;
/* 208 */                 break;
/*     */               }
/*     */ 
/* 211 */               ShippingCost sc = new ShippingCost();
/* 212 */               sc.setPriceBandId(lPriceBandId);
/* 213 */               sc.setPrice(cost);
/* 214 */               sc.getQuantityRange().setId(lQuantityId);
/* 215 */               sc.getTaxRegion().setId(lRegionId);
/* 216 */               vecCosts.add(sc);
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 222 */         if (!bCostsValid)
/*     */         {
/* 224 */           form.addError(this.m_listManager.getListItem(a_dbTransaction, "priceBandInvalidShippingCost", userProfile.getCurrentLanguage()).getBody());
/*     */         }
/*     */         else
/*     */         {
/* 228 */           this.m_pbManager.saveShippingCosts(a_dbTransaction, vecCosts, lPriceBandId);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 234 */     if (!form.getHasErrors())
/*     */     {
/* 237 */       afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */     }
/*     */     else
/*     */     {
/* 241 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 244 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.priceband.action.SavePriceBandAction
 * JD-Core Version:    0.6.0
 */