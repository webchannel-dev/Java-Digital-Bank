/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.UploadedFileInfo;
/*     */ import com.bright.assetbank.application.bean.VideoAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.VideoAssetManagerImpl;
/*     */ import com.bright.assetbank.application.util.ABImageMagick;
/*     */ import com.bright.assetbank.application.util.VideoUtil;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.usage.bean.ColorSpace;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.bean.ImageFile;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class UpdateVideoThumbnailAction extends ViewUpdateAssetAction
/*     */   implements AssetBankConstants
/*     */ {
/*  57 */   private VideoAssetManagerImpl m_videoAssetManager = null;
/*  58 */   private UsageManager m_usageManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  74 */     ActionForward afForward = null;
/*     */ 
/*  77 */     String sOriginalFile = null;
/*  78 */     long lId = getLongParameter(a_request, "id");
/*  79 */     int iFrame = getIntParameter(a_request, "frame");
/*     */ 
/*  81 */     if (lId > 0L)
/*     */     {
/*  83 */       VideoAsset video = (VideoAsset)this.m_videoAssetManager.getAsset(a_dbTransaction, lId, null, false);
/*  84 */       sOriginalFile = video.getOriginalFileLocation();
/*     */ 
/*  86 */       String sChangedStart = a_request.getParameter("changePreviewStart");
/*  87 */       boolean bChangedStart = false;
/*     */       try
/*     */       {
/*  90 */         bChangedStart = Boolean.parseBoolean(sChangedStart);
/*     */       }
/*     */       catch (Exception e) {
/*     */       }
/*  94 */       if (bChangedStart)
/*     */       {
/*  96 */         this.m_videoAssetManager.saveVideo(a_dbTransaction, video, true, false, iFrame);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 105 */       sOriginalFile = a_request.getParameter("fileName");
/*     */     }
/*     */ 
/* 109 */     AssetForm form = (AssetForm)a_form;
/* 110 */     String sFile = FileUtil.getFilepathWithoutSuffix(sOriginalFile);
/* 111 */     sFile = sFile + "." + "png";
/* 112 */     sFile = this.m_fileStoreManager.getUniqueFilepath(sFile, StoredFileType.PREVIEW_OR_THUMBNAIL);
/*     */ 
/* 114 */     VideoUtil.createPreviewImage(this.m_fileStoreManager.getAbsolutePath(sOriginalFile), this.m_fileStoreManager.getAbsolutePath(sFile), iFrame);
/*     */ 
/* 116 */     String sPreviewFile = this.m_fileStoreManager.getUniqueFilenameForRelatedFile(sFile, "-m.jpg", StoredFileType.PREVIEW_OR_THUMBNAIL);
/*     */ 
/* 121 */     String sDirName = a_request.getParameter("dirName");
/* 122 */     String sIndex = a_request.getParameter("index");
/* 123 */     form.setTempDirName(sDirName);
/* 124 */     form.setTempFileIndex(sIndex);
/*     */ 
/* 127 */     String sRgbColorProfile = this.m_usageManager.getColorSpace(null, 1).getFileLocation();
/* 128 */     String sCmykColorProfile = this.m_usageManager.getColorSpace(null, 2).getFileLocation();
/*     */ 
/* 131 */     ABImageMagick.resizeToJpg(this.m_fileStoreManager.getAbsolutePath(sFile), this.m_fileStoreManager.getAbsolutePath(sPreviewFile), this.m_videoAssetManager.getMaxPreviewWidth(), this.m_videoAssetManager.getMaxPreviewHeight(), sRgbColorProfile, sCmykColorProfile);
/*     */ 
/* 140 */     int[] dim = ABImageMagick.getDimensions(this.m_fileStoreManager.getAbsolutePath(sPreviewFile));
/*     */ 
/* 143 */     if (AssetBankSettings.getWatermarkPreview())
/*     */     {
/* 145 */       ABImageMagick.addWatermark(this.m_fileStoreManager.getAbsolutePath(sPreviewFile), this.m_fileStoreManager.getAbsolutePath(sPreviewFile), dim[0], dim[1], null, false);
/*     */     }
/*     */ 
/* 155 */     ImageFile file = new ImageFile();
/* 156 */     file.setPath(sPreviewFile);
/*     */ 
/* 159 */     if (lId > 0L)
/*     */     {
/* 161 */       afForward = super.execute(a_mapping, a_form, a_request, a_response, a_dbTransaction);
/* 162 */       form.setChangedFrame(true);
/*     */ 
/* 165 */       form.getAsset().setPreviewImageFile(file);
/* 166 */       form.getAsset().setFileLocation(sFile);
/*     */     }
/*     */     else
/*     */     {
/* 170 */       form.setAsset(new VideoAsset(form.getAsset()));
/* 171 */       form.setTempFileLocation(sOriginalFile);
/*     */ 
/* 174 */       form.setPreviewImageFile(file);
/* 175 */       ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 176 */       if (form.getSelectedAssetEntityId() > 0L)
/*     */       {
/* 178 */         form.getAsset().setEntity(this.m_assetEntityManager.getEntity(a_dbTransaction, form.getSelectedAssetEntityId()));
/* 179 */         form.getAsset().getEntity().setLanguage(userProfile.getCurrentLanguage());
/*     */       }
/*     */ 
/* 183 */       UploadedFileInfo uploadedFileInfo = this.m_videoAssetManager.getUploadedFileInfo(sOriginalFile, false);
/* 184 */       uploadedFileInfo.setPreviewImage(file);
/* 185 */       uploadedFileInfo.setConvertedLocation(sFile);
/*     */ 
/* 187 */       afForward = a_mapping.findForward("AddAsset");
/*     */     }
/*     */ 
/* 190 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setVideoAssetManager(VideoAssetManagerImpl a_videoAssetManager)
/*     */   {
/* 195 */     this.m_videoAssetManager = a_videoAssetManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/* 200 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.UpdateVideoThumbnailAction
 * JD-Core Version:    0.6.0
 */