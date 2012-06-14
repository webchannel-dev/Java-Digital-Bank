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
/*     */ public class DownloadAudioAction extends DownloadMediaAssetAction
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
/*     */ 
/*  71 */     if (a_form.getDuration() > 0.0D)
/*     */     {
/*  73 */       conversionInfo.setDuration(a_form.getDuration());
/*     */     }
/*     */ 
/*  76 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  77 */     conversionInfo.setUserProfile(userProfile);
/*     */ 
/*  80 */     conversionInfo.setCompressFile(a_form.getCompress());
/*  81 */     if ((StringUtil.stringIsPopulated(a_form.getAudioSampleRate())) || ("flv".equalsIgnoreCase(a_form.getImageFormat())))
/*     */     {
/*  83 */       String sAR = a_form.getAudioSampleRate();
/*  84 */       if (!StringUtil.stringIsPopulated(sAR))
/*     */       {
/*  86 */         sAR = "standard";
/*     */       }
/*  88 */       conversionInfo.setAudioSampleRate(AssetBankSettings.getAudioSampleRate(sAR, a_form.getImageFormat()));
/*     */     }
/*  90 */     conversionInfo.setStartOffset(a_form.getStartOffset());
/*  91 */     conversionInfo.setAudioBitrate(a_form.getAudioBitrate());
/*     */ 
/*  93 */     return conversionInfo;
/*     */   }
/*     */ 
/*     */   public String getFormatExt(DBTransaction a_dbTransaction, DownloadForm a_form, String a_sOriginalFormatExt)
/*     */     throws Bn2Exception
/*     */   {
/* 100 */     return a_form.getImageFormat();
/*     */   }
/*     */ 
/*     */   public void validateImplementationDownload(DBTransaction a_dbTransaction, HttpServletRequest a_request, DownloadForm a_form, ABUserProfile a_userProfile, String a_sFormatExt, Asset a_asset)
/*     */     throws Bn2Exception
/*     */   {
/* 108 */     if (a_form.getHeight() % 2 != 0)
/*     */     {
/* 111 */       a_form.setHeight(a_form.getHeight() + 1);
/*     */     }
/*     */ 
/* 114 */     if (a_form.getWidth() % 2 != 0)
/*     */     {
/* 116 */       a_form.setWidth(a_form.getWidth() + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ActionForward getForward(ActionMapping a_mapping, HttpServletRequest a_request, DownloadForm a_form)
/*     */   {
/* 124 */     if (a_form.getEmail())
/*     */     {
/* 126 */       return a_mapping.findForward("DownloadEmailSuccess");
/*     */     }
/* 128 */     if (a_request.getParameter("b_preview") != null)
/*     */     {
/* 130 */       return a_mapping.findForward("PreviewSuccess");
/*     */     }
/*     */ 
/* 134 */     return createForward(a_form.isRepurpose() ? "?repurpose=" + String.valueOf(true) : "", a_mapping, "DownloadConvertSuccess");
/*     */   }
/*     */ 
/*     */   public ActionForward doPreviewWork(DownloadForm a_form, ActionMapping a_mapping, String a_sDownloadPath)
/*     */   {
/* 141 */     return null;
/*     */   }
/*     */ 
/*     */   public String embedMetadata(String a_sDownloadPath, Asset a_asset, boolean a_bMakeCopyIfChanging, boolean a_bDontEmbedMetadata, MediaAssetConversionInfo conversionInfo)
/*     */   {
/* 151 */     return a_sDownloadPath;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.DownloadAudioAction
 * JD-Core Version:    0.6.0
 */