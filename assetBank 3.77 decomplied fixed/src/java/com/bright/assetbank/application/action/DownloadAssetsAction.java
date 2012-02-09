/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.AssetboxDownloadRequest;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.DownloadAssetBoxBatchManager;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.approval.service.AssetApprovalManager;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.assetbox.form.AssetBoxDownloadForm;
/*     */ import com.bright.assetbank.usage.bean.AssetUse;
/*     */ import com.bright.assetbank.usage.bean.UsageTypeFormat;
/*     */ import com.bright.assetbank.usage.bean.UsageTypeSelectOption;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public abstract class DownloadAssetsAction extends DownloadAssetAction
/*     */   implements AssetBoxConstants, MessageConstants, AssetBankConstants, ImageConstants
/*     */ {
/*  92 */   private DownloadAssetBoxBatchManager m_downloadBatchManager = null;
/*     */ 
/*  98 */   protected AssetApprovalManager m_approvalManager = null;
/*     */ 
/* 104 */   protected ABUserManager m_userManager = null;
/*     */ 
/*     */   protected abstract void prepareDownload(ABUserProfile paramABUserProfile, DBTransaction paramDBTransaction, AssetBoxDownloadForm paramAssetBoxDownloadForm)
/*     */     throws Bn2Exception;
/*     */ 
/*     */   protected abstract Vector getAssetsToDownload(DBTransaction paramDBTransaction, ABUserProfile paramABUserProfile, AssetBoxDownloadForm paramAssetBoxDownloadForm, HttpServletRequest paramHttpServletRequest)
/*     */     throws Bn2Exception;
/*     */ 
/*     */   public void setDownloadBatchManager(DownloadAssetBoxBatchManager a_downloadBatchManager)
/*     */   {
/*  95 */     this.m_downloadBatchManager = a_downloadBatchManager;
/*     */   }
/*     */ 
/*     */   public void setAssetApprovalManager(AssetApprovalManager a_approvalManager)
/*     */   {
/* 101 */     this.m_approvalManager = a_approvalManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 107 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 117 */     ActionForward afForward = null;
/* 118 */     AssetBoxDownloadForm form = (AssetBoxDownloadForm)a_form;
/* 119 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 126 */     if ((!form.getHighResDirectDownload()) && (needsReload(form, a_request)))
/*     */     {
/* 129 */       return a_mapping.findForward("Reload");
/*     */     }
/*     */ 
/* 133 */     FileFormat format = null;
/*     */ 
/* 136 */     long lUserId = userProfile.getUserId();
/* 137 */     long lIdForSession = UserProfile.getLongIdForSession(a_request.getSession());
/*     */ 
/* 140 */     if ((!form.getDirectDownload()) && (!form.getHighResDirectDownload()))
/*     */     {
/* 142 */       validateUsageAndConditions(form, userProfile, a_dbTransaction, this.m_listManager, null);
/*     */     }
/*     */ 
/* 145 */     Set listSecondaryUsageTypes = new HashSet();
/*     */ 
/* 148 */     Map fieldValues = RequestUtil.getRequestParametersAsMap(a_request, "secondary_", false);
/*     */ 
               Iterator it = fieldValues.entrySet().iterator(); 
               while(it.hasNext()){
       
                Entry fieldEntry = (Entry) it.next();
                String sfieldName = (String)fieldEntry.getKey();
                long lUsageTypeId = Long.valueOf(sfieldName).longValue();
                listSecondaryUsageTypes.add(Long.valueOf(lUsageTypeId));
               }


/* 150 */    // for (Map.Entry fieldEntry : fieldValues.entrySet())
/*     */     //{
/* 152 */    //   String sfieldName = (String)fieldEntry.getKey();
/*     */    //
/* 154 */    //  long lUsageTypeId = Long.valueOf(sfieldName).longValue();
/*     */    //
/* 156 */     //  listSecondaryUsageTypes.add(Long.valueOf(lUsageTypeId));
/*     */    // }
/*     */ 
/* 159 */     if (!form.getHasErrors())
/*     */     {
/* 162 */       if (a_request.getParameter("b_requestApproval") != null)
/*     */       {
/* 165 */         Vector vecAssetsToDownload = getAssetsToDownload(a_dbTransaction, userProfile, form, a_request);
/*     */ 
/* 167 */         Iterator itBox = vecAssetsToDownload.iterator();
/*     */ 
/* 169 */         String sAssetIds = "";
/*     */ 
/* 171 */         while (itBox.hasNext())
/*     */         {
/* 173 */           AssetInList ail = (AssetInList)itBox.next();
/*     */ 
/* 176 */           ail.setUsageTypeId(form.getAssetUse().getUsageTypeId());
/* 177 */           ail.setUserNotes(form.getAssetUse().getUsageOther());
/*     */ 
/* 179 */           if ((!form.getOnlyDownloadSelected()) || (ail.getIsSelected()))
/*     */           {
/* 181 */             this.m_approvalManager.addAssetForApproval(a_dbTransaction, ail, userProfile.getUser().getId(), true);
/*     */ 
/* 183 */             sAssetIds = sAssetIds + Long.valueOf(ail.getAsset().getId()).toString() + ", ";
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 189 */         HashMap hmAdminEmails = new HashMap();
/*     */ 
/* 191 */         ABUser user = (ABUser)userProfile.getUser();
/* 192 */         this.m_approvalManager.sendApprovalEmail(user, hmAdminEmails, true, sAssetIds);
/*     */ 
/* 194 */         form.setIsHighResRequest(true);
/*     */ 
/* 196 */         return a_mapping.findForward("RequestApproval");
/*     */       }
/*     */ 
/* 200 */       if (a_request.getParameter("b_download") != null)
/*     */       {
/* 203 */         format = this.m_assetManager.getFileFormatForExtension(a_dbTransaction, form.getImageFormat());
/*     */ 
/* 206 */         if ((form.getWidth() <= 0) && (form.getHeight() <= 0) && (form.getAdvanced()))
/*     */         {
/* 208 */           form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationAssetBoxImageSizes", userProfile.getCurrentLanguage()).getBody());
/*     */         }
/*     */       }
/*     */ 
/* 212 */       prepareDownload(userProfile, a_dbTransaction, form);
/*     */ 
/* 214 */       if (!form.getHasErrors())
/*     */       {
/* 217 */         Vector vecAssetsToDownload = getAssetsToDownload(a_dbTransaction, userProfile, form, a_request);
/*     */ 
/* 219 */         Iterator itBox = vecAssetsToDownload.iterator();
/*     */ 
/* 221 */         Vector vecAssets = new Vector();
/*     */ 
/* 223 */         while (itBox.hasNext())
/*     */         {
/* 225 */           AssetInList ail = (AssetInList)itBox.next();
/* 226 */           Asset asset = ail.getAsset();
/*     */ 
/* 228 */           if (!form.getExcludedIds().contains("," + asset.getId() + ","))
/*     */           {
/* 231 */             if ((form.getOnlyDownloadSelected()) && (!ail.getIsSelected()))
/*     */             {
/*     */               continue;
/*     */             }
/*     */ 
/* 237 */             if (asset.getSurrogateAssetId() > 0L)
/*     */             {
/* 239 */               String sAssetName = asset.getName();
/*     */ 
/* 241 */               asset = this.m_assetManager.getAsset(a_dbTransaction, asset.getSurrogateAssetId(), null, false, false);
/*     */ 
/* 243 */               asset.setName(sAssetName);
/*     */             }
/*     */ 
/* 246 */             if ((asset.getHasFile()) && (!vecAssets.contains(asset)))
/*     */             {
/* 248 */               vecAssets.add(asset);
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 253 */         if (vecAssets.size() == 0)
/*     */         {
/* 255 */           form.addError(this.m_listManager.getListItem(a_dbTransaction, "noAssetFilesToDownload", userProfile.getCurrentLanguage()).getBody());
/* 256 */           return a_mapping.findForward("Failure");
/*     */         }
/*     */ 
/* 259 */         int iMaxWidth = form.getWidth();
/* 260 */         int iMaxHeight = form.getHeight();
/* 261 */         int iDensity = 0;
/* 262 */         ImageConversionInfo conversionInfo = new ImageConversionInfo();
/* 263 */         String sPreserveFormatList = "";
/*     */ 
/* 266 */         if ((a_request.getParameter("b_downloadOriginal") != null) || (form.getHighResDirectDownload()))
/*     */         {
/* 268 */           conversionInfo.setUseOriginal(true);
/*     */         }
/* 270 */         else if (form.getDownloadingImages())
/*     */         {
/* 272 */           if ((AssetBankSettings.getSimpleConvertOptionsForDownload()) && (!form.getAdvanced()))
/*     */           {
/* 275 */             UsageTypeFormat utf = this.m_usageManager.getUsageTypeFormat(a_dbTransaction, form.getUsageTypeFormatId());
/* 276 */             iMaxWidth = utf.getWidth();
/* 277 */             iMaxHeight = utf.getHeight();
/* 278 */             iDensity = utf.getDensity();
/*     */ 
/* 281 */             sPreserveFormatList = utf.getPreserveFormatList();
/*     */ 
/* 284 */             long lFormatId = utf.getFormatId();
/* 285 */             format = this.m_assetManager.getFileFormat(a_dbTransaction, lFormatId);
/*     */           }
/* 292 */           else if (form.getWidth() <= 0)
/*     */           {
/* 294 */             iMaxWidth = 9999999;
/*     */           }
/* 296 */           else if (form.getHeight() <= 0)
/*     */           {
/* 298 */             iMaxHeight = 9999999;
/*     */           }
/*     */ 
/* 303 */           conversionInfo.setMaintainAspectRatio(true);
/*     */ 
/* 307 */           if ((form.getWatermarkImageOption()) || (AssetBankSettings.getWatermarkDownload()))
/*     */           {
/* 309 */             conversionInfo.setAddDownloadWatermark(true);
/*     */           }
/*     */           else
/*     */           {
/* 313 */             conversionInfo.setAddWatermark(AssetBankSettings.getWatermarkDownload());
/*     */           }
/* 315 */           conversionInfo.setMaxHeight(iMaxHeight);
/* 316 */           conversionInfo.setMaxWidth(iMaxWidth);
/*     */ 
/* 318 */           if (iDensity > 0)
/*     */           {
/* 320 */             conversionInfo.setDensity(iDensity);
/*     */           }
/*     */ 
/* 324 */           if (form.getConvertToRGB())
/*     */           {
/* 326 */             conversionInfo.setConvertToColorSpace(this.m_usageManager.getColorSpace(a_dbTransaction, 1));
/*     */           }
/* 332 */           else if (form.getSelectedColorSpaceId() > 0)
/*     */           {
/* 334 */             conversionInfo.setConvertToColorSpace(this.m_usageManager.getColorSpace(a_dbTransaction, form.getSelectedColorSpaceId()));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 354 */         a_request.getSession().setAttribute("assetboxDownloadFiles", null);
/* 355 */         a_request.getSession().setAttribute("emailAssetBox", Boolean.valueOf(form.getEmail()));
/*     */ 
/* 357 */         if (form.getDownloadingLightbox())
/*     */         {
/* 359 */           a_request.getSession().setAttribute("downloadingAssetBox", Boolean.valueOf(true));
/*     */         }
/*     */         else
/*     */         {
/* 363 */           a_request.getSession().setAttribute("downloadingParentId", Long.valueOf(form.getParentId()));
/*     */         }
/*     */ 
/* 367 */         this.m_downloadBatchManager.queueItem(new AssetboxDownloadRequest(vecAssets, form.getFileName(), format, form.getAssetUse(), lUserId, lIdForSession, conversionInfo, sPreserveFormatList, a_request.getSession(), listSecondaryUsageTypes));
/*     */ 
/* 379 */         if (userProfile.getIsLoggedIn())
/*     */         {
/* 381 */           refreshDownloadCounter(a_dbTransaction, lUserId, userProfile);
/*     */         }
/*     */ 
/* 385 */         afForward = createRedirectingForward("", a_mapping, "Success");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 390 */     if (form.getHasErrors())
/*     */     {
/* 393 */       form.getSelectedUsageType().setId(form.getAssetUse().getUsageTypeId());
/* 394 */       afForward = a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/* 397 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.DownloadAssetsAction
 * JD-Core Version:    0.6.0
 */