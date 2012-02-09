/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.MediaAssetConversionInfo;
/*     */ import com.bright.assetbank.application.bean.VideoConversionInfo;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*     */ import com.bright.assetbank.application.form.DownloadForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class DownloadVideoAction extends DownloadMediaAssetAction
/*     */ {
/*     */   public Asset getAsset(DBTransaction a_dbTransaction, long a_lAssetId, Vector a_vecVisibleAttributeIds)
/*     */     throws AssetNotFoundException, Bn2Exception
/*     */   {
/*  53 */     return this.m_assetManager.getAsset(a_dbTransaction, a_lAssetId, a_vecVisibleAttributeIds, false, false);
/*     */   }
/*     */ 
/*     */   public void validateImplementationValues(DBTransaction a_dbTransaction, DownloadForm a_form, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/*  60 */     if (a_form.isRepurpose())
/*     */     {
/*  62 */       a_form.setImageFormat("flv");
/*     */     }
/*     */   }
/*     */ 
/*     */   public MediaAssetConversionInfo getConversionInfo(DBTransaction a_dbTransaction, HttpServletRequest a_request, DownloadForm a_form, Asset a_asset)
/*     */     throws Bn2Exception
/*     */   {
/*  69 */     VideoConversionInfo conversionInfo = new VideoConversionInfo();
/*  70 */     conversionInfo.setScaleUp(true);
/*     */ 
/*  73 */     if (a_request.getParameter("aspect") != null)
/*     */     {
/*  75 */       conversionInfo.setMaintainAspectRatio(true);
/*     */     }
/*     */ 
/*  79 */     if (AssetBankSettings.getWatermarkDownload())
/*     */     {
/*  81 */       conversionInfo.setAddWatermark(true);
/*     */     }
/*     */ 
/*  84 */     conversionInfo.setMaxHeight(a_form.getHeight());
/*  85 */     conversionInfo.setMaxWidth(a_form.getWidth());
/*     */ 
/*  87 */     if (a_form.getDuration() > 0.0D)
/*     */     {
/*  89 */       conversionInfo.setDuration(a_form.getDuration());
/*     */     }
/*     */ 
/*  92 */     if (a_request.getParameter("b_preview") != null)
/*     */     {
/*  95 */       conversionInfo.setPreview(true);
/*  96 */       conversionInfo.setDuration(AssetBankSettings.getVideoConversionPreviewDuration());
/*     */     }
/*     */ 
/*  99 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 100 */     conversionInfo.setUserProfile(userProfile);
/*     */ 
/* 103 */     conversionInfo.setCompressFile(a_form.getCompress());
/*     */ 
/* 105 */     if ((StringUtil.stringIsPopulated(a_form.getFrameRate())) || ("flv".equalsIgnoreCase(a_form.getImageFormat())))
/*     */     {
/* 107 */       String sFR = a_form.getFrameRate();
/* 108 */       if (!StringUtil.stringIsPopulated(sFR))
/*     */       {
/* 110 */         sFR = "standard";
/*     */       }
/* 112 */       conversionInfo.setFrameRate(AssetBankSettings.getFrameRate(sFR, a_form.getImageFormat()));
/*     */     }
/*     */ 
/* 115 */     if ((StringUtil.stringIsPopulated(a_form.getAudioSampleRate())) || ("flv".equalsIgnoreCase(a_form.getImageFormat())))
/*     */     {
/* 117 */       String sAR = a_form.getAudioSampleRate();
/* 118 */       if (!StringUtil.stringIsPopulated(sAR))
/*     */       {
/* 120 */         sAR = "standard";
/*     */       }
/* 122 */       conversionInfo.setAudioSampleRate(AssetBankSettings.getAudioSampleRate(sAR, a_form.getImageFormat()));
/*     */     }
/*     */ 
/* 125 */     conversionInfo.setStartOffset(a_form.getStartOffset());
/* 126 */     conversionInfo.setAudioBitrate(a_form.getAudioBitrate());
/* 127 */     conversionInfo.setVideoBitrate(a_form.getVideoBitrate());
/*     */ 
/* 129 */     return conversionInfo;
/*     */   }
/*     */ 
/*     */   public String getFormatExt(DBTransaction a_dbTransaction, DownloadForm a_form, String a_sOriginalFormatExt)
/*     */     throws Bn2Exception
/*     */   {
/* 136 */     return a_form.getImageFormat();
/*     */   }
/*     */ 
/*     */   public void validateImplementationDownload(DBTransaction a_dbTransaction, HttpServletRequest a_request, DownloadForm a_form, ABUserProfile a_userProfile, String a_sFormatExt, Asset a_asset)
/*     */     throws Bn2Exception
/*     */   {
/* 144 */     if (a_form.getHeight() % 2 != 0)
/*     */     {
/* 147 */       a_form.setHeight(a_form.getHeight() + 1);
/*     */     }
/*     */ 
/* 150 */     if (a_form.getWidth() % 2 != 0)
/*     */     {
/* 152 */       a_form.setWidth(a_form.getWidth() + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ActionForward getForward(ActionMapping a_mapping, HttpServletRequest a_request, DownloadForm a_form)
/*     */   {
/* 158 */     if (a_form.getEmail())
/*     */     {
/* 160 */       return a_mapping.findForward("DownloadEmailSuccess");
/*     */     }
/* 162 */     if (a_request.getParameter("b_preview") != null)
/*     */     {
/* 164 */       return a_mapping.findForward("PreviewSuccess");
/*     */     }
/*     */ 
/* 168 */     return createForward(a_form.isRepurpose() ? "?repurpose=" + String.valueOf(true) : "", a_mapping, "DownloadConvertSuccess");
/*     */   }
/*     */ 
/*     */   public ActionForward doPreviewWork(DownloadForm a_form, ActionMapping a_mapping, String a_sDownloadPath)
/*     */   {
/* 175 */     return null;
/*     */   }
/*     */ 
/*     */   public String embedMetadata(String a_sDownloadPath, Asset a_asset, boolean a_bMakeCopyIfChanging, boolean a_bDontEmbedMetadata, MediaAssetConversionInfo conversionInfo)
/*     */   {
/* 185 */     return a_sDownloadPath;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.DownloadVideoAction
 * JD-Core Version:    0.6.0
 */