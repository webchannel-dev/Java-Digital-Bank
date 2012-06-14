/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.assetbox.form.RequestAssetBoxForm;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.RefDataItem;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.mail.constant.MailConstants;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.ServletUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class DoRequestAssetBoxAction extends BTransactionAction
/*     */   implements AssetBoxConstants, MailConstants, MessageConstants, AssetBankConstants
/*     */ {
/*  69 */   private EmailManager m_emailManager = null;
/*     */ 
/*  75 */   private AssetBoxManager m_assetBoxManager = null;
/*     */ 
/*  81 */   private ABUserManager m_userManager = null;
/*     */ 
/*  87 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setEmailManager(EmailManager a_emailManager)
/*     */   {
/*  72 */     this.m_emailManager = a_emailManager;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/*  78 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/*  84 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  90 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 113 */     ActionForward afForward = null;
/* 114 */     RequestAssetBoxForm form = (RequestAssetBoxForm)a_form;
/* 115 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 117 */     String sDownloadableAssetIds = "";
/* 118 */     String sRecipients = "";
/* 119 */     HashMap hmAdminEmails = new HashMap();
/* 120 */     boolean bNeedSuperUsers = false;
/*     */ 
/* 123 */     validateMandatoryFields(form, a_request);
/* 124 */     if (form.getHasErrors())
/*     */     {
/* 126 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 130 */     long lDivisionId = getLongParameter(a_request, "divisionId");
/*     */ 
/* 132 */     if (form.getAssetId() > 0L)
/*     */     {
/* 134 */       sDownloadableAssetIds = Long.valueOf(form.getAssetId()).toString();
/*     */ 
/* 136 */       if (!this.m_userManager.getApproverEmailsForAsset(form.getAssetId(), hmAdminEmails))
/*     */       {
/* 139 */         bNeedSuperUsers = true;
/*     */       }
/*     */ 
/* 142 */       if (AssetBankSettings.getUsersHaveDivisions())
/*     */       {
/* 145 */         String sDivisionAdmins = this.m_userManager.getAdminEmailAddressesInDivision(lDivisionId, true);
/*     */ 
/* 148 */         if (StringUtil.stringIsPopulated(sDivisionAdmins))
/*     */         {
/* 150 */           sRecipients = sDivisionAdmins;
/*     */         }
/*     */         else
/*     */         {
/* 154 */           sRecipients = this.m_userManager.getAdminEmailAddressesInDivision(-1L, true);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 161 */         sRecipients = StringUtil.getEmailAddressesFromHashMap(hmAdminEmails);
/*     */ 
/* 163 */         if (bNeedSuperUsers)
/*     */         {
/* 165 */           String sSuperEmailAddress = this.m_userManager.getAdminEmailAddresses();
/*     */ 
/* 167 */           if (StringUtil.stringIsPopulated(sRecipients))
/*     */           {
/* 169 */             if (StringUtil.stringIsPopulated(sSuperEmailAddress))
/*     */             {
/* 171 */               sRecipients = sRecipients + ";" + sSuperEmailAddress;
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 176 */             sRecipients = sSuperEmailAddress;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 186 */       this.m_assetBoxManager.refreshAssetBoxesInProfile(a_dbTransaction, userProfile);
/* 187 */       Collection assetBox = userProfile.getAssetBox().getAssetsInState(7);
/*     */ 
/* 189 */       if (assetBox.size() > 0)
/*     */       {
/* 197 */         StringBuffer sbLinks = new StringBuffer();
/*     */ 
/* 199 */         Iterator it = assetBox.iterator();
/* 200 */         while (it.hasNext())
/*     */         {
/* 202 */           AssetInList ail = (AssetInList)it.next();
/* 203 */           Asset asset = ail.getAsset();
/*     */ 
/* 205 */           sbLinks.append("<br/><a href='");
/*     */ 
/* 207 */           if (StringUtils.isNotEmpty(AssetBankSettings.getApplicationUrl()))
/*     */           {
/* 209 */             sbLinks.append(AssetBankSettings.getApplicationUrl());
/*     */           }
/*     */           else
/*     */           {
/* 213 */             sbLinks.append(ServletUtil.getApplicationUrl(a_request));
/*     */           }
/*     */ 
/* 216 */           sbLinks.append("/action/viewAsset");
/* 217 */           sbLinks.append("?id=");
/* 218 */           sbLinks.append(asset.getId());
/* 219 */           sbLinks.append("'>");
/*     */ 
/* 221 */           String sName = asset.getName();
/* 222 */           if (!asset.getIdWithPadding().equals(sName))
/*     */           {
/* 224 */             sbLinks.append(sName);
/* 225 */             sbLinks.append(" (");
/*     */           }
/* 227 */           sbLinks.append(asset.getIdWithPadding());
/* 228 */           if (!asset.getIdWithPadding().equals(sName))
/*     */           {
/* 230 */             sbLinks.append(")");
/*     */           }
/*     */ 
/* 233 */           sbLinks.append("</a>");
/*     */ 
/* 236 */           if (!this.m_userManager.getApproverEmailsForAsset(asset.getId(), hmAdminEmails))
/*     */           {
/* 239 */             bNeedSuperUsers = true;
/*     */           }
/*     */         }
/*     */ 
/* 243 */         sDownloadableAssetIds = sbLinks.toString();
/*     */ 
/* 246 */         if (AssetBankSettings.getUsersHaveDivisions())
/*     */         {
/* 249 */           String sDivisionAdmins = this.m_userManager.getAdminEmailAddressesInDivision(lDivisionId, true);
/*     */ 
/* 252 */           if (StringUtil.stringIsPopulated(sDivisionAdmins))
/*     */           {
/* 254 */             sRecipients = sDivisionAdmins;
/*     */           }
/*     */           else
/*     */           {
/* 258 */             sRecipients = this.m_userManager.getAdminEmailAddressesInDivision(-1L, true);
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 265 */           sRecipients = StringUtil.getEmailAddressesFromHashMap(hmAdminEmails);
/*     */ 
/* 267 */           if (bNeedSuperUsers)
/*     */           {
/* 269 */             String sSuperEmailAddress = this.m_userManager.getAdminEmailAddresses();
/*     */ 
/* 271 */             if (StringUtil.stringIsPopulated(sRecipients))
/*     */             {
/* 273 */               if (StringUtil.stringIsPopulated(sSuperEmailAddress))
/*     */               {
/* 275 */                 sRecipients = sRecipients + ";" + sSuperEmailAddress;
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 280 */               sRecipients = sSuperEmailAddress;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 289 */     Enumeration e = a_request.getParameterNames();
/* 290 */     HashMap params = new HashMap();
/*     */ 
/* 293 */     params.put("template", "requestAssetbox");
/* 294 */     params.put("downloadableIds", sDownloadableAssetIds);
/* 295 */     params.put("adminEmailAddresses", sRecipients);
/*     */ 
/* 298 */     String sDivision = "";
/* 299 */     if (lDivisionId > 0L)
/*     */     {
/* 301 */       RefDataItem division = this.m_userManager.getDivision(a_dbTransaction, lDivisionId);
/* 302 */       sDivision = division.getDescription();
/*     */     }
/* 304 */     params.put("division", sDivision);
/*     */ 
/* 307 */     params.put("customFieldName", this.m_listManager.getListItem(a_dbTransaction, "label-cd-custom-field", userProfile.getCurrentLanguage()).getBody());
/* 308 */     params.put("addressFieldName", this.m_listManager.getListItem(a_dbTransaction, "label-cd-address", userProfile.getCurrentLanguage()).getBody());
/* 309 */     params.put("commentsFieldName", this.m_listManager.getListItem(a_dbTransaction, "label-add-comments", userProfile.getCurrentLanguage()).getBody());
/*     */ 
/* 312 */     while (e.hasMoreElements())
/*     */     {
/* 315 */       String sParamName = (String)e.nextElement();
/* 316 */       String sParam = a_request.getParameter(sParamName);
/*     */ 
/* 318 */       if ((sParam != null) && (sParam.length() > 4096))
/*     */       {
/* 320 */         sParam = sParam.substring(0, 4096);
/*     */       }
/*     */ 
/* 323 */       params.put(sParamName, sParam);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 329 */       this.m_emailManager.sendTemplatedEmail(params, LanguageConstants.k_defaultLanguage);
/* 330 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */     catch (Bn2Exception bne)
/*     */     {
/* 334 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedEmailSend", userProfile.getCurrentLanguage()).getBody());
/* 335 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 338 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.DoRequestAssetBoxAction
 * JD-Core Version:    0.6.0
 */