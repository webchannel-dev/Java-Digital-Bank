/*     */ package com.bright.assetbank.application.util;
/*     */ 
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings.DiscardAlphaChannel;
/*     */ import com.bright.framework.image.util.ExifTool;
/*     */ import com.bright.framework.image.util.GeometryUtil;
/*     */ import com.bright.framework.image.util.ImageMagickOptionList;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.awt.Dimension;
/*     */ import java.io.File;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class ABImageMagickOptionList extends ImageMagickOptionList
/*     */ {
/*     */   public void convertColorSpace(String a_sCurrentColorSpacePath, String a_sNewColorSpacePath, boolean a_bUseEmbeddedProfile)
/*     */   {
/*  51 */     String sAppPath = AssetBankSettings.getApplicationPath() + File.separator;
/*     */ 
/*  55 */     if (a_bUseEmbeddedProfile)
/*     */     {
/*  57 */       addProfile(sAppPath + a_sNewColorSpacePath);
/*     */     }
/*     */     else
/*     */     {
/*  62 */       addProfile(sAppPath + a_sCurrentColorSpacePath);
/*  63 */       addProfile(sAppPath + a_sNewColorSpacePath);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void changeColorProfile(String a_sSourceProfile, String a_sDestProfile)
/*     */   {
/*  82 */     addProfile(a_sSourceProfile);
/*  83 */     addProfile(a_sDestProfile);
/*     */   }
/*     */ 
/*     */   public void addWatermark(int a_iBackgroundWidth, int a_iBackgroundHeight, boolean a_bIsDownload)
/*     */   {
/* 101 */     float fWatermarkBrightness = -1.0F;
/* 102 */     String sWatermarkGravity = "";
/* 103 */     float fWatermarkScaleFactor = -1.0F;
/* 104 */     String sWatermarkFilename = "";
/*     */ 
/* 106 */     if (a_bIsDownload)
/*     */     {
/* 108 */       fWatermarkBrightness = AssetBankSettings.getDownloadWatermarkBrightness();
/* 109 */       sWatermarkGravity = AssetBankSettings.getDownloadWatermarkGravity();
/* 110 */       fWatermarkScaleFactor = AssetBankSettings.getDownloadWatermarkScaleFactor();
/* 111 */       sWatermarkFilename = AssetBankSettings.getApplicationPath() + "/" + AssetBankSettings.getDownloadWatermarkFilename();
/*     */     }
/*     */ 
/* 115 */     if (fWatermarkBrightness == -1.0F)
/*     */     {
/* 117 */       fWatermarkBrightness = AssetBankSettings.getWatermarkBrightness();
/*     */     }
/* 119 */     if (!StringUtil.stringIsPopulated(sWatermarkGravity))
/*     */     {
/* 121 */       sWatermarkGravity = AssetBankSettings.getWatermarkGravity();
/*     */     }
/* 123 */     if (fWatermarkScaleFactor == -1.0F)
/*     */     {
/* 125 */       fWatermarkScaleFactor = AssetBankSettings.getWatermarkScaleFactor();
/*     */     }
/* 127 */     if ((sWatermarkFilename.equals(AssetBankSettings.getApplicationPath() + "/")) || (sWatermarkFilename == ""))
/*     */     {
/* 129 */       sWatermarkFilename = AssetBankSettings.getWatermarkFilename();
/*     */     }
/*     */ 
/* 132 */     addWatermark(fWatermarkBrightness);
/* 133 */     addGravity(sWatermarkGravity);
/*     */ 
/* 135 */     ImageMagickOptionList innerOptions = new ImageMagickOptionList();
/* 136 */     innerOptions.addResize((int)Math.ceil(a_iBackgroundWidth * fWatermarkScaleFactor), (int)Math.ceil(a_iBackgroundHeight * fWatermarkScaleFactor), null);
/*     */ 
/* 142 */     addToImageStack(innerOptions, sWatermarkFilename);
/*     */   }
/*     */ 
/*     */   public int[] addResize(int a_iWidthAspect, int a_iHeightAspect, int a_iBoundingWidth, int a_iBoundingHeight, String a_sOption)
/*     */   {
/* 164 */     if (a_sOption == null)
/*     */     {
/* 166 */       a_sOption = "";
/*     */     }
/* 168 */     a_sOption = a_sOption + "!";
/*     */ 
/* 170 */     int[] aiNewSize = new int[2];
/*     */ 
/* 173 */     Dimension newSize = GeometryUtil.fit(new Dimension(a_iWidthAspect, a_iHeightAspect), new Dimension(a_iBoundingWidth, a_iBoundingHeight));
/*     */ 
/* 176 */     aiNewSize[0] = newSize.width;
/* 177 */     aiNewSize[1] = newSize.height;
/*     */ 
/* 179 */     super.addResize(aiNewSize[0], aiNewSize[1], a_sOption);
/*     */ 
/* 184 */     return aiNewSize;
/*     */   }
/*     */ 
/*     */   public void addFormatSpecificOptions(String a_sInputPath, String a_sOutputPath)
/*     */   {
/* 202 */     if (FileUtil.hasSuffix(a_sOutputPath, "jpg"))
/*     */     {
/* 204 */       addQuality(AssetBankSettings.getJpgConversionQuality());
/*     */     }
/*     */ 
/* 208 */     if ((FileUtil.hasSuffix(a_sInputPath, "psd")) && (!FileUtil.hasSuffix(a_sOutputPath, "psd")))
/*     */     {
/* 210 */       specifyInputLayer(0);
/*     */     }
/*     */ 
/* 213 */     boolean bRemoveAlpha = false;
/*     */ 
/* 217 */     if (((FileUtil.hasSuffix(a_sInputPath, "tif")) || (FileUtil.hasSuffix(a_sInputPath, "tiff")) || (FileUtil.hasSuffix(a_sInputPath, "psd"))) && (AssetBankSettings.getDiscardAlphaChannel().equals(AssetBankSettings.DiscardAlphaChannel.SELECTION)))
/*     */     {
/* 222 */       String sAlphaChannelNames = ExifTool.getAlphaChannelsNames(a_sInputPath);
/* 223 */       bRemoveAlpha = (StringUtils.isNotEmpty(sAlphaChannelNames)) && (!sAlphaChannelNames.startsWith("Transparency"));
/*     */     }
/*     */ 
/* 227 */     if ((bRemoveAlpha) || ((FileUtil.hasSuffix(a_sInputPath, "gif")) && (!FileUtil.hasSuffix(a_sOutputPath, "gif"))) || (AssetBankSettings.getDiscardAlphaChannel().equals(AssetBankSettings.DiscardAlphaChannel.YES)))
/*     */     {
/* 231 */       addAlpha("Off");
/*     */     }
/*     */ 
/* 235 */     if (((FileUtil.hasSuffix(a_sInputPath, "png")) || (FileUtil.hasSuffix(a_sInputPath, "tif")) || (FileUtil.hasSuffix(a_sInputPath, "tiff"))) && (FileUtil.hasSuffix(a_sOutputPath, "jpg")))
/*     */     {
/* 240 */       addFlatten();
/*     */     }
/*     */ 
/* 244 */     if ((FileUtil.hasSuffix(a_sOutputPath, "tif")) || (FileUtil.hasSuffix(a_sOutputPath, "tiff")))
/*     */     {
/* 246 */       addCompress("LZW");
/*     */ 
/* 251 */       addDepth("8");
/*     */     }
/*     */ 
/* 255 */     if ((FileUtil.hasSuffix(a_sInputPath, "pdf")) || (FileUtil.hasSuffix(a_sInputPath, "ai")))
/*     */     {
/* 257 */       if (AssetBankSettings.getPostscriptInputDensity() > 0)
/*     */       {
/* 259 */         addInputDensity(AssetBankSettings.getPostscriptInputDensity());
/*     */       }
/*     */ 
/* 263 */       addUseCropbox();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.util.ABImageMagickOptionList
 * JD-Core Version:    0.6.0
 */