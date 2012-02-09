/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.agreements.bean.Agreement;
/*     */ import com.bright.assetbank.agreements.service.AgreementsManager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.DownloadForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.application.util.DownloadUtil;
/*     */ import com.bright.assetbank.subscription.service.SubscriptionManager;
/*     */ import com.bright.assetbank.usage.bean.AssetUse;
/*     */ import com.bright.assetbank.usage.bean.UsageTypeSelectOption;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.DownloadRestrictionManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.HashSet;
import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class DownloadAssetAction extends BTransactionAction
/*     */   implements FrameworkConstants, AssetBankConstants, MessageConstants
/*     */ {
/*  85 */   protected UsageManager m_usageManager = null;
/*     */ 
/*  91 */   protected IAssetManager m_assetManager = null;
/*     */ 
/*  97 */   protected SubscriptionManager m_subscriptionManager = null;
/*     */ 
/* 103 */   protected DownloadRestrictionManager m_downloadRestrictionManager = null;
/*     */ 
/* 109 */   protected ListManager m_listManager = null;
/*     */ 
/* 115 */   protected AgreementsManager m_agreementsManager = null;
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/*  88 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/*  94 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setSubscriptionManager(SubscriptionManager a_subscriptionManager)
/*     */   {
/* 100 */     this.m_subscriptionManager = a_subscriptionManager;
/*     */   }
/*     */ 
/*     */   public void setDownloadRestrictionManager(DownloadRestrictionManager a_downloadRestrictionManager)
/*     */   {
/* 106 */     this.m_downloadRestrictionManager = a_downloadRestrictionManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 112 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setAgreementsManager(AgreementsManager a_agreementsManager)
/*     */   {
/* 118 */     this.m_agreementsManager = a_agreementsManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 128 */     ActionForward afForward = null;
/* 129 */     DownloadForm form = (DownloadForm)a_form;
/* 130 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 133 */     if (needsReload(form, a_request))
/*     */     {
/* 135 */       return a_mapping.findForward("Reload");
/*     */     }
/*     */ 
/* 139 */     long lUserId = userProfile.getUserId();
/* 140 */     long lAssetId = getAssetId(form, a_request);
/* 141 */     long usageType = form.getAssetUse().getUsageTypeId();
/* 142 */     String usageTypeOther = form.getAssetUse().getUsageOther();
/*     */ 
/* 144 */     Set secondaryUsageTypes = new HashSet();
/*     */ 
/* 147 */     Map fieldValues = RequestUtil.getRequestParametersAsMap(a_request, "secondary_", false);
/*     */    
              Iterator entries = fieldValues.entrySet().iterator();
               while (entries.hasNext()) {
                Entry fieldEntry = (Entry) entries.next();
                String sfieldName = (String)fieldEntry.getKey();
                long lUsageTypeId = Long.valueOf(sfieldName).longValue();
                secondaryUsageTypes.add(Long.valueOf(lUsageTypeId));
               }


/* 149 */     //for (Map.Entry fieldEntry : entries )
/*     */     //{
/* 151 */     //  String sfieldName = (String)fieldEntry.getKey();
/*     */ 
/* 153 */     //  long lUsageTypeId = Long.valueOf(sfieldName).longValue();
/*     */ 
/* 155 */     //  secondaryUsageTypes.add(Long.valueOf(lUsageTypeId));
/*     */    // }
/*     */ 
/* 159 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, userProfile.getVisibleAttributeIds(), false, false);
/*     */ 
/* 161 */     if ((!AssetBankSettings.getIsDirectDownloadFileType(FileUtil.getSuffix(asset.getFileName()))) && (!form.getDirectDownload()) && (!form.getHighResDirectDownload()))
/*     */     {
/* 164 */       validateUsageAndConditions(form, userProfile, a_dbTransaction, this.m_listManager, asset);
/*     */     }
/*     */     else
/*     */     {
/* 168 */       usageType = 0L;
/* 169 */       usageTypeOther = "Direct download";
/*     */     }
/*     */ 
/* 172 */     if (!form.getHasErrors())
/*     */     {
/* 175 */       if ((!userProfile.getIsAdmin()) && (!this.m_assetManager.userCanDownloadAssetNow(userProfile, asset)))
/*     */       {
/* 177 */         this.m_logger.debug("This user does not have permission to download image id=" + lAssetId);
/* 178 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/* 182 */       this.m_downloadRestrictionManager.validateDownload(a_dbTransaction, form, lUserId, userProfile, asset);
/*     */ 
/* 184 */       if (!form.getHasErrors())
/*     */       {
/* 189 */         if (!asset.getIsUnsubmitted())
/*     */         {
/* 191 */           this.m_usageManager.logAssetUseAsynchronously(asset.getId(), lUserId, usageType, usageTypeOther, 3L, userProfile.getSessionId(), secondaryUsageTypes);
/*     */         }
/*     */ 
/* 202 */         String sClientFilename = DownloadUtil.getDownloadFilename(asset, null);
/*     */ 
/* 205 */         a_request.setAttribute("compressFile", new Boolean(form.getCompress()));
/*     */ 
/* 210 */         if ((asset.getOriginalFileLocation() != null) && (asset.getOriginalFileLocation().length() > 0))
/*     */         {
/* 212 */           a_request.setAttribute("downloadFile", asset.getOriginalFileLocation());
/*     */         }
/*     */         else
/*     */         {
/* 216 */           a_request.setAttribute("downloadFile", asset.getFileLocation());
/*     */         }
/*     */ 
/* 219 */         a_request.setAttribute("downloadFilename", sClientFilename);
/* 220 */         a_request.setAttribute("deleteFileAfterUse", Boolean.FALSE);
/* 221 */         a_request.setAttribute("downloadFaliureUrl", a_mapping.findForward("DownloadFailure").getPath());
/*     */ 
/* 223 */         refreshDownloadCounter(a_dbTransaction, lUserId, userProfile);
/*     */ 
/* 225 */         if (form.getEmail())
/*     */         {
/* 227 */           afForward = a_mapping.findForward("DownloadEmailSuccess");
/*     */         }
/*     */         else
/*     */         {
/* 231 */           afForward = a_mapping.findForward("DownloadSuccess");
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 237 */     if (form.getHasErrors())
/*     */     {
/* 240 */       form.getSelectedUsageType().setId(form.getAssetUse().getUsageTypeId());
/*     */ 
/* 242 */       afForward = a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/* 245 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected boolean needsReload(DownloadForm a_form, HttpServletRequest a_request)
/*     */   {
/* 251 */     long lTypeId = checkForSubtypesRequest(a_request);
/* 252 */     if (lTypeId > 0L)
/*     */     {
/* 255 */       a_form.getSelectedUsageType().setId(lTypeId);
/* 256 */       return true;
/*     */     }
/*     */ 
/* 262 */     return StringUtil.stringIsPopulated(a_request.getParameter("b_usageTypeFormat"));
/*     */   }
/*     */ 
/*     */   protected void refreshDownloadCounter(DBTransaction a_dbTransaction, long a_lUserId, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 286 */     if ((AssetBankSettings.getSubscription()) && (a_lUserId > 0L))
/*     */     {
/* 289 */       long lDownloads = this.m_subscriptionManager.getRemainingDownloadsTodayForUser(a_dbTransaction, a_lUserId);
/* 290 */       a_userProfile.setDownloadsLeft(lDownloads);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static long getAssetId(DownloadForm a_form, HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/* 305 */     long lAssetId = getLongParameter(a_request, "id");
/*     */ 
/* 308 */     if (lAssetId <= 0L)
/*     */     {
/* 310 */       lAssetId = a_form.getAsset().getId();
/*     */     }
/* 312 */     if (lAssetId <= 0L)
/*     */     {
/* 314 */       throw new Bn2Exception("DownloadAssetAction: No asset Id passed in.");
/*     */     }
/*     */ 
/* 317 */     return lAssetId;
/*     */   }
/*     */ 
/*     */   public void validateUsageAndConditions(DownloadForm form, ABUserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager, Asset a_asset)
/*     */     throws Bn2Exception
/*     */   {
/* 335 */     if ((form.getValidateUsageType()) && (form.getAssetUse().getUsageTypeId() == 0L))
/*     */     {
/* 337 */       form.addError(a_listManager.getListItem(a_dbTransaction, "failedValidationUsage", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/* 341 */     else if ((form.getValidateUsageDescription()) && (!StringUtil.stringIsPopulated(form.getAssetUse().getUsageOther())))
/*     */     {
/* 343 */       form.addError(a_listManager.getListItem(a_dbTransaction, "failedValidationUse", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/* 346 */     else if (!form.getConditionsAccepted())
/*     */     {
/* 348 */       form.addError(a_listManager.getListItem(a_dbTransaction, "failedValidationConditions", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/* 352 */     else if ((a_asset != null) && (a_asset.getAgreementTypeId() == 2L) && (!form.getAgreementAccepted()))
/*     */     {
/* 355 */       Agreement agreement = this.m_agreementsManager.getCurrentAgreementForAsset(a_dbTransaction, a_asset.getId());
/*     */ 
/* 357 */       if ((agreement != null) && (agreement.getId() > 0L))
/*     */       {
/* 359 */         form.addError(a_listManager.getListItem(a_dbTransaction, "failedValidationAgreement", a_userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */ 
/*     */     }
/* 363 */     else if ((form.getAssetsHaveAgreement()) && (!form.getAgreementAccepted()))
/*     */     {
/* 365 */       form.addError(a_listManager.getListItem(a_dbTransaction, "failedValidationAgreement", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static long checkForSubtypesRequest(HttpServletRequest a_request)
/*     */   {
/* 377 */     long lParentTypeId = -1L;
/*     */ 
/* 379 */     int iMaxDepth = AssetBankSettings.getMaxUsageTypeDepth();
/* 380 */     if (iMaxDepth == 0)
/*     */     {
/* 382 */       iMaxDepth = 10;
/*     */     }
/*     */ 
/* 385 */     for (int i = 0; i < iMaxDepth; i++)
/*     */     {
/* 387 */       String sButtonName = "b_subtypes_" + i;
/* 388 */       String sGetSubtypes = a_request.getParameter(sButtonName);
/*     */ 
/* 390 */       if (!StringUtil.stringIsPopulated(sGetSubtypes))
/*     */         continue;
/* 392 */       String sSelectName = "usage_" + i;
/* 393 */       lParentTypeId = getLongParameter(a_request, sSelectName);
/* 394 */       break;
/*     */     }
/*     */ 
/* 398 */     return lParentTypeId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.DownloadAssetAction
 * JD-Core Version:    0.6.0
 */