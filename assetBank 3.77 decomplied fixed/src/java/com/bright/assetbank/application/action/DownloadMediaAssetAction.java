/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.bean.MediaAssetConversionInfo;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.exception.AssetBoxNotFoundException;
/*     */ import com.bright.assetbank.application.exception.AssetConversionDeferredException;
/*     */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*     */ import com.bright.assetbank.application.form.DownloadForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.application.util.DownloadUtil;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.approval.service.AssetApprovalManager;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.usage.bean.AssetUse;
/*     */ import com.bright.assetbank.usage.bean.UsageTypeSelectOption;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.assetbank.user.service.DownloadRestrictionManager;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.storage.bean.StorageDevice;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.storage.service.StorageDeviceManager;
/*     */ import com.bright.framework.struts.ValidationException;
/*     */ import com.bright.framework.struts.ValidationUtil;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.RC4CipherUtil;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public abstract class DownloadMediaAssetAction extends DownloadAssetAction
/*     */   implements FrameworkConstants, AssetBankConstants, MessageConstants
/*     */ {
/*  81 */   protected AttributeManager m_attributeManager = null;
/*  82 */   protected FileStoreManager m_fileStoreManager = null;
/*  83 */   protected AssetApprovalManager m_approvalManager = null;
/*  84 */   protected ABUserManager m_userManager = null;
/*  85 */   protected AssetBoxManager m_assetBoxManager = null;
/*  86 */   protected StorageDeviceManager m_storageDeviceManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  95 */     ActionForward afForward = null;
/*  96 */     DownloadForm form = (DownloadForm)a_form;
/*  97 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 100 */     if (needsReload(form, a_request))
/*     */     {
/* 102 */       return a_mapping.findForward("Reload");
/*     */     }
/*     */ 
/* 106 */     long lUserId = userProfile.getUserId();
/* 107 */     long lAssetId = getAssetId(form, a_request);
/* 108 */     long lUsageType = form.getAssetUse().getUsageTypeId();
/* 109 */     String sUsageTypeOther = form.getAssetUse().getUsageOther();
/* 110 */     Set secondaryUsageTypes = new HashSet();
/*     */ 
/* 113 */     Map fieldValues = RequestUtil.getRequestParametersAsMap(a_request, "secondary_", false);
/*     */ 
            // Set entries= fieldValues.entrySet();
             Iterator entries = fieldValues.entrySet().iterator();
             while (entries.hasNext()) {
             Entry fieldEntry = (Entry) entries.next();
             String sfieldName = (String)fieldEntry.getKey();
             long lUsageTypeId = Long.valueOf(sfieldName).longValue();
             secondaryUsageTypes.add(Long.valueOf(lUsageTypeId));
             //Object key = thisEntry.getKey();
             //Object value = thisEntry.getValue();
             }     
             
/* 115      for (Map.Entry : fieldEntry  )
/*         {
/* 117       String sfieldName = (String)fieldEntry.getKey();
/*     
/* 119       long lUsageTypeId = Long.valueOf(sfieldName).longValue();
/*      
/* 121        secondaryUsageTypes.add(Long.valueOf(lUsageTypeId));
/*          }
/*     */ 

/* 125 */     form.setSecondaryUsageTypeIds(secondaryUsageTypes);
/*     */ 
/* 128 */     form.setAllowAddAssetFromExisting(AssetBankSettings.isAddAssetFromExistingEnabled());
/*     */ 
/* 131 */     Asset asset = getAsset(a_dbTransaction, lAssetId, userProfile.getVisibleAttributeIds());
/*     */ 
/* 134 */     String sOriginalFormatExt = asset.getFormat().getFileExtension();
/*     */ 
/* 136 */     if ((!AssetBankSettings.getIsDirectDownloadFileType(FileUtil.getSuffix(asset.getFileName()))) && (!form.getDirectDownload()) && (!form.getHighResDirectDownload()))
/*     */     {
/* 139 */       validateUsageAndConditions(form, userProfile, a_dbTransaction, this.m_listManager, asset);
/*     */     }
/*     */     else
/*     */     {
/* 143 */       lUsageType = 0L;
/* 144 */       sUsageTypeOther = "Direct download";
/*     */     }
/*     */ 
/* 147 */     validateImplementationValues(a_dbTransaction, form, userProfile);
/* 148 */     validateCropMaskColour(a_dbTransaction, userProfile, form);
/*     */ 
/* 150 */     if (!form.getHasErrors())
/*     */     {
/* 153 */       if ((!userProfile.getIsAdmin()) && (!this.m_assetManager.userCanDownloadAssetNow(userProfile, asset)))
/*     */       {
/* 155 */         this.m_logger.debug("This user does not have permission to download image id=" + lAssetId);
/*     */ 
/* 160 */         String sQueryString = "id=" + lAssetId + "&" + "noDownloadPermission" + "=true";
/*     */ 
/* 163 */         return createRedirectingForward(sQueryString, a_mapping, "NoDownloadPermission");
/*     */       }
/*     */ 
/* 167 */       this.m_downloadRestrictionManager.validateDownload(a_dbTransaction, form, lUserId, userProfile, asset);
/*     */ 
/* 169 */       if (!form.getHasErrors())
/*     */       {
/* 171 */         String sClientFilename = "";
/*     */ 
/* 173 */         if ((a_request.getParameter("b_download") != null) || (a_request.getParameter("b_preview") != null) || (a_request.getParameter("b_save") != null))
/*     */         {
/* 179 */           String sFormatExt = getFormatExt(a_dbTransaction, form, sOriginalFormatExt);
/*     */ 
/* 181 */           validateImplementationDownload(a_dbTransaction, a_request, form, userProfile, sFormatExt, asset);
/* 182 */           MediaAssetConversionInfo conversionInfo = getConversionInfo(a_dbTransaction, a_request, form, asset);
/*     */ 
/* 185 */           if (a_request.getParameter("b_save") != null)
/*     */           {
/* 187 */             afForward = a_mapping.findForward("AddAsAsset");
/* 188 */             conversionInfo.setAddingAsNewAsset(true);
/*     */           }
/*     */ 
/* 193 */           if ((!form.getAsset().getIsAudio()) && ((form.getHeight() <= 0) || (form.getWidth() <= 0)))
/*     */           {
/* 195 */             form.addError(this.m_listManager.getListItem(a_dbTransaction, "imageErrorZero", userProfile.getCurrentLanguage()).getBody());
/*     */           }
/*     */ 
/* 198 */           if (!form.getHasErrors())
/*     */           {
/* 201 */             String sDownloadPath = form.getEncryptedDownloadPath();
/* 202 */             boolean bDeferred = false;
/*     */ 
/* 205 */             if (sDownloadPath == null)
/*     */             {
/*     */               try
/*     */               {
/* 210 */                 sDownloadPath = this.m_assetManager.getDownloadableAssetPath(asset, sFormatExt, conversionInfo);
/* 211 */                 form.setTempLocation(sDownloadPath);
/*     */ 
/* 214 */                 if (a_request.getParameter("b_save") != null)
/*     */                 {
/* 216 */                   form.setPathToAddExisting(sDownloadPath);
/*     */                 }
/* 218 */                 embedMetadata(sDownloadPath, asset, false, false, conversionInfo);
/*     */               }
/*     */               catch (AssetConversionDeferredException asde)
/*     */               {
/* 222 */                 bDeferred = true;
/* 223 */                 sDownloadPath = asde.getFilePath();
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/* 228 */             if ((!form.getAlreadyLoggedUse()) && (!conversionInfo.getAddWatermark()))
/*     */             {
/* 230 */               long lDownloadType = 0L;
/*     */ 
/* 233 */               if (form.getEmail())
/*     */               {
/* 235 */                 lDownloadType = 5L;
/*     */               }
/* 237 */               else if (form.isRepurpose())
/*     */               {
/* 239 */                 lDownloadType = 6L;
/*     */               }
/*     */               else
/*     */               {
/* 243 */                 lDownloadType = 2L;
/*     */               }
/*     */ 
/* 247 */               if (!asset.getIsUnsubmitted())
/*     */               {
/* 250 */                 this.m_usageManager.logAssetUseAsynchronously(asset.getId(), lUserId, lUsageType, sUsageTypeOther, lDownloadType, userProfile.getSessionId(), secondaryUsageTypes);
/*     */ 
/* 258 */                 form.setAlreadyLoggedUse(true);
/*     */               }
/*     */             }
/*     */ 
/* 262 */             if (a_request.getParameter("b_preview") != null)
/*     */             {
/* 264 */               afForward = doPreviewWork(form, a_mapping, sDownloadPath);
/*     */             }
/*     */ 
/* 267 */             if (afForward == null)
/*     */             {
/* 269 */               sClientFilename = DownloadUtil.getClientFilename(asset, FileUtil.getSuffix(sDownloadPath).toLowerCase());
/*     */ 
/* 271 */               a_request.setAttribute("downloadFilename", sClientFilename);
/*     */ 
/* 273 */               if (bDeferred)
/*     */               {
/* 275 */                 String sQueryString = "filePath=" + sDownloadPath;
/* 276 */                 sQueryString = sQueryString + "&emailAsset=" + Boolean.valueOf(form.getEmail());
/* 277 */                 sQueryString = sQueryString + "&repurposeAsset=" + Boolean.valueOf(form.isRepurpose());
/* 278 */                 sQueryString = sQueryString + "&watermarkEmailImage=" + Boolean.valueOf(form.getWatermarkImageOption());
/* 279 */                 sQueryString = sQueryString + "&id=" + asset.getId();
/* 280 */                 sQueryString = sQueryString + "&downloadFilename=" + sClientFilename;
/* 281 */                 afForward = createRedirectingForward(sQueryString, a_mapping, "DownloadDeferred");
/*     */               }
/*     */               else
/*     */               {
/* 285 */                 a_request.setAttribute("compressFile", new Boolean(form.getCompress()));
/* 286 */                 a_request.setAttribute("downloadFile", sDownloadPath);
/*     */ 
/* 289 */                 if (!conversionInfo.getUseOriginal())
/*     */                 {
/* 291 */                   a_request.setAttribute("deleteFileAfterUse", Boolean.TRUE);
/*     */                 }
/* 293 */                 a_request.setAttribute("downloadFaliureUrl", a_mapping.findForward("DownloadFailure").getPath());
/*     */ 
/* 295 */                 form.setAsset(asset);
/*     */ 
/* 297 */                 afForward = getForward(a_mapping, a_request, form);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 302 */         else if (a_request.getParameter("b_downloadOriginal") != null)
/*     */         {
/* 304 */           long sDownloadType = 0L;
/*     */ 
/* 307 */           if (form.getEmail())
/*     */           {
/* 309 */             sDownloadType = 5L;
/*     */           }
/*     */           else
/*     */           {
/* 313 */             sDownloadType = 2L;
/*     */           }
/*     */ 
/* 316 */           this.m_usageManager.logAssetUseAsynchronously(asset.getId(), lUserId, lUsageType, sUsageTypeOther, sDownloadType, userProfile.getSessionId(), secondaryUsageTypes);
/*     */ 
/* 326 */           sClientFilename = DownloadUtil.getDownloadFilename(asset, null);
/*     */ 
/* 328 */           String sDownloadPath = null;
/*     */ 
/* 334 */           if ((asset.getOriginalFileLocation() != null) && (asset.getOriginalFileLocation().length() > 0))
/*     */           {
/* 336 */             sDownloadPath = asset.getOriginalFileLocation();
/*     */           }
/*     */           else
/*     */           {
/* 340 */             sDownloadPath = asset.getFileLocation();
/*     */           }
/*     */ 
/* 343 */           String url = null;
/* 344 */           StorageDevice device = this.m_storageDeviceManager.getDeviceForRelativePath(sDownloadPath);
/* 345 */           if ((AssetBankSettings.getDownloadRemoteStorageDirectly()) && (!device.isLocalFileStorage()) && (!form.getEmail()) && (!form.getCompress()))
/*     */           {
/*     */             try
/*     */             {
/* 351 */               url = device.getHttpUrl(sDownloadPath);
/* 352 */               afForward = createRedirectingForward(url);
/*     */             }
/*     */             catch (IOException exc)
/*     */             {
/*     */             }
/*     */           }
/*     */ 
/* 359 */           if (StringUtils.isBlank(url))
/*     */           {
/* 361 */             sDownloadPath = embedMetadata(sDownloadPath, asset, true, form.getDontEmbedMappedData(), null);
/*     */ 
/* 363 */             a_request.setAttribute("downloadFile", sDownloadPath);
/* 364 */             a_request.setAttribute("compressFile", new Boolean(form.getCompress()));
/* 365 */             a_request.setAttribute("downloadFilename", sClientFilename);
/* 366 */             a_request.setAttribute("downloadFaliureUrl", a_mapping.findForward("DownloadFailure").getPath());
/*     */ 
/* 369 */             if (StoredFileType.isTransientFileType(sDownloadPath))
/*     */             {
/* 371 */               a_request.setAttribute("deleteFileAfterUse", Boolean.TRUE);
/*     */             }
/*     */             else
/*     */             {
/* 375 */               a_request.setAttribute("deleteFileAfterUse", Boolean.FALSE);
/*     */             }
/*     */ 
/* 378 */             if (form.getEmail())
/*     */             {
/* 380 */               afForward = a_mapping.findForward("DownloadEmailSuccess");
/*     */             }
/*     */             else
/*     */             {
/* 384 */               afForward = a_mapping.findForward("DownloadSuccess");
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/* 389 */         else if (a_request.getParameter("b_cancel") != null)
/*     */         {
/* 392 */           if (form.getEncryptedDownloadPath() != null)
/*     */           {
/*     */             try
/*     */             {
/* 396 */               String sFilePath = RC4CipherUtil.decryptFromHex(form.getEncryptedDownloadPath());
/*     */ 
/* 398 */               File fFileToDelete = new File(this.m_fileStoreManager.getAbsolutePath(sFilePath));
/*     */ 
/* 400 */               if (fFileToDelete.exists())
/*     */               {
/* 402 */                 fFileToDelete.delete();
/* 403 */                 FileUtil.logFileDeletion(fFileToDelete);
/*     */               }
/*     */             }
/*     */             catch (NumberFormatException nfe)
/*     */             {
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 412 */           afForward = a_mapping.findForward("PreviewCancelled");
/*     */         }
/* 416 */         else if (a_request.getParameter("b_requestApproval") != null)
/*     */         {
/* 420 */           AssetInList ail = new AssetInList();
/* 421 */           ail.setAsset(asset);
/* 422 */           ail.setUserNotes(sUsageTypeOther);
/* 423 */           ail.setUsageTypeId(lUsageType);
/*     */ 
/* 425 */           this.m_approvalManager.addAssetForApproval(a_dbTransaction, ail, userProfile.getUser().getId(), true);
/*     */ 
/* 428 */           HashMap hmAdminEmails = new HashMap();
/* 429 */           boolean bNeedSuperUsers = false;
/*     */ 
/* 431 */           String sRequestedAssetIds = Long.valueOf(lAssetId).toString();
/*     */ 
/* 433 */           if (!this.m_userManager.getApproverEmailsForAsset(lAssetId, hmAdminEmails))
/*     */           {
/* 436 */             bNeedSuperUsers = true;
/*     */           }
/*     */ 
/* 439 */           ABUser user = (ABUser)userProfile.getUser();
/* 440 */           this.m_approvalManager.sendApprovalEmail(user, hmAdminEmails, bNeedSuperUsers, sRequestedAssetIds);
/*     */ 
/* 442 */           form.setIsHighResRequest(true);
/*     */           try
/*     */           {
/* 446 */             this.m_assetBoxManager.addAsset(a_dbTransaction, userProfile, lAssetId);
/* 447 */             this.m_assetBoxManager.refreshAssetBoxesInProfile(a_dbTransaction, userProfile);
/*     */           }
/*     */           catch (AssetBoxNotFoundException e)
/*     */           {
/* 451 */             this.m_logger.error("assetbox not found: " + e);
/*     */           }
/*     */ 
/* 454 */           afForward = a_mapping.findForward("RequestApproval");
/*     */         }
/*     */ 
/* 458 */         refreshDownloadCounter(a_dbTransaction, lUserId, userProfile);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 464 */     if (form.getHasErrors())
/*     */     {
/* 467 */       form.getSelectedUsageType().setId(form.getAssetUse().getUsageTypeId());
/*     */ 
/* 469 */       afForward = a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/* 473 */     return afForward;
/*     */   }
/*     */ 
/*     */   private void validateCropMaskColour(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, DownloadForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 481 */       a_form.validateCropMaskColour();
/*     */     }
/*     */     catch (ValidationException e)
/*     */     {
/* 485 */       a_form.addError(ValidationUtil.displayStringFromException(a_dbTransaction, this.m_listManager, a_userProfile.getCurrentLanguage(), e));
/*     */     }
/*     */   }
/*     */ 
/*     */   public String embedMetadata(String a_sRelativeDownloadPath, Asset a_asset, boolean a_bMakeCopyIfChanging, boolean a_bDontEmbedMetadata, MediaAssetConversionInfo conversionInfo)
/*     */   {
/* 510 */     return DownloadUtil.embedMetadata(a_sRelativeDownloadPath, a_asset, a_bMakeCopyIfChanging, a_bDontEmbedMetadata, conversionInfo, this.m_attributeManager, this.m_fileStoreManager); } 
/*     */   public abstract Asset getAsset(DBTransaction paramDBTransaction, long paramLong, Vector paramVector) throws AssetNotFoundException, Bn2Exception;
/*     */ 
/*     */   public abstract void validateImplementationValues(DBTransaction paramDBTransaction, DownloadForm paramDownloadForm, ABUserProfile paramABUserProfile) throws Bn2Exception;
/*     */ 
/*     */   public abstract MediaAssetConversionInfo getConversionInfo(DBTransaction paramDBTransaction, HttpServletRequest paramHttpServletRequest, DownloadForm paramDownloadForm, Asset paramAsset) throws Bn2Exception;
/*     */ 
/*     */   public abstract String getFormatExt(DBTransaction paramDBTransaction, DownloadForm paramDownloadForm, String paramString) throws Bn2Exception;
/*     */ 
/*     */   public abstract void validateImplementationDownload(DBTransaction paramDBTransaction, HttpServletRequest paramHttpServletRequest, DownloadForm paramDownloadForm, ABUserProfile paramABUserProfile, String paramString, Asset paramAsset) throws Bn2Exception;
/*     */ 
/*     */   public abstract ActionForward getForward(ActionMapping paramActionMapping, HttpServletRequest paramHttpServletRequest, DownloadForm paramDownloadForm);
/*     */ 
/*     */   public abstract ActionForward doPreviewWork(DownloadForm paramDownloadForm, ActionMapping paramActionMapping, String paramString);
/*     */ 
/* 530 */   public void setAttributeManager(AttributeManager a_sAttributeManager) { this.m_attributeManager = a_sAttributeManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_sFileStoreManager)
/*     */   {
/* 535 */     this.m_fileStoreManager = a_sFileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setStorageDeviceManager(StorageDeviceManager a_StorageDeviceManager)
/*     */   {
/* 540 */     this.m_storageDeviceManager = a_StorageDeviceManager;
/*     */   }
/*     */ 
/*     */   public void setAssetApprovalManager(AssetApprovalManager a_approvalManager)
/*     */   {
/* 545 */     this.m_approvalManager = a_approvalManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 550 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/* 556 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.DownloadMediaAssetAction
 * JD-Core Version:    0.6.0
 */