/*     */ package com.bright.assetbank.application.util;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.ImageFileInfo;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.category.constant.CategorySettings;
/*     */ import com.bright.framework.image.exception.ImageException;
/*     */ import com.bright.framework.image.util.ImageMagick;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class ABImageMagick extends ImageMagick
/*     */ {
/*     */   public static void convertToCategoryImage(String a_sInputPath, String a_sOutputPath, String a_sRgbColorProfile, String a_sCmykColorProfile)
/*     */     throws ImageException
/*     */   {
/*  66 */     ImageFileInfo imageInfo = getInfo(a_sInputPath);
/*     */ 
/*  68 */     int iColorSpace = imageInfo.getColorSpace();
/*  69 */     boolean bIsCMYK = getIsCMYK(iColorSpace);
/*     */ 
/*  71 */     resizeAndCropToJpg(a_sInputPath, a_sOutputPath, imageInfo.getWidth(), imageInfo.getHeight(), CategorySettings.getCategoryImageWidth(), CategorySettings.getCategoryImageHeight(), bIsCMYK, a_sRgbColorProfile, a_sCmykColorProfile, imageInfo.getNumberOfLayers());
/*     */   }
/*     */ 
/*     */   public static void convertToCategoryLogo(String a_sInputPath, String a_sOutputPath, String a_sRgbColorProfile, String a_sCmykColorProfile)
/*     */     throws ImageException
/*     */   {
/* 106 */     ImageFileInfo imageInfo = getInfo(a_sInputPath);
/*     */ 
/* 108 */     int iColorSpace = imageInfo.getColorSpace();
/* 109 */     boolean bIsCMYK = getIsCMYK(iColorSpace);
/*     */ 
/* 111 */     resizeAndCropToJpg(a_sInputPath, a_sOutputPath, imageInfo.getWidth(), imageInfo.getHeight(), AssetBankSettings.getLogoWidth(), AssetBankSettings.getLogoHeight(), bIsCMYK, a_sRgbColorProfile, a_sCmykColorProfile, imageInfo.getNumberOfLayers());
/*     */   }
/*     */ 
/*     */   public static void resizeAndCropToJpg(String a_sInputPath, String a_sOutputPath, int a_iWidthAspect, int a_iHeightAspect, int a_iWidth, int a_iHeight, boolean a_bSourceIsCMYK, String a_sRgbProfilePath, String a_sCmykProfilePath, int a_iNumLayers)
/*     */     throws ImageException
/*     */   {
/* 152 */     ABImageMagickOptionList options = new ABImageMagickOptionList();
/*     */ 
/* 155 */     options.addInputFilename(a_sInputPath);
/*     */ 
/* 157 */     if (a_bSourceIsCMYK)
/*     */     {
/* 159 */       options.convertColorSpace(a_sCmykProfilePath, a_sRgbProfilePath, false);
/*     */     }
/*     */ 
/* 162 */     options.addStrip();
/*     */ 
/* 164 */     options.addFormatSpecificOptions(a_sInputPath, a_sOutputPath);
/*     */ 
/* 166 */     if (a_iNumLayers > 0)
/*     */     {
/* 168 */       options.specifyInputLayer(0);
/*     */     }
/*     */ 
/* 172 */     if (a_iWidthAspect < a_iHeightAspect)
/*     */     {
/* 174 */       options.addResize(a_iWidthAspect, a_iHeightAspect, a_iWidth, 2147483647, null);
/*     */     }
/*     */     else
/*     */     {
/* 179 */       options.addResize(a_iWidthAspect, a_iHeightAspect, 2147483647, a_iHeight, null);
/*     */     }
/*     */ 
/* 183 */     options.addGravity("Center");
/* 184 */     options.addCrop(a_iWidth, a_iHeight, 0, 0);
/*     */ 
/* 187 */     options.addOutputFilename(a_sOutputPath);
/*     */ 
/* 189 */     convert(options);
/*     */   }
/*     */ 
/*     */   public static void addWatermark(String a_sInputPath, String a_sOutputPath, int a_iBoundingWidth, int a_iBoundingHeight, String a_sInputFileIdentifier, boolean a_bIsDownload)
/*     */     throws ImageException
/*     */   {
/* 219 */     ABImageMagickOptionList options = new ABImageMagickOptionList();
/* 220 */     options.addWatermark(a_iBoundingWidth, a_iBoundingHeight, a_bIsDownload);
/* 221 */     options.addInputFilename(a_sInputPath);
/* 222 */     options.addOutputFilename(a_sOutputPath);
/* 223 */     options.setInputFileIdentifier(a_sInputFileIdentifier);
/* 224 */     composite(options);
/*     */   }
/*     */ 
/*     */   public static void addCreditStrip(String a_sInputPath, String a_sOutputPath, String a_sImageCreditText, String a_sOriginalFilePath)
/*     */     throws ImageException
/*     */   {
/* 269 */     ABImageMagickOptionList options = new ABImageMagickOptionList();
/* 270 */     options.addInputFilename(a_sInputPath);
/* 271 */     options.addCredit(AssetBankSettings.getCreditBackgroundColour(), AssetBankSettings.getCreditTextColour(), AssetBankSettings.getCreditText(), a_sImageCreditText, AssetBankSettings.getCreditStripGravity());
/* 272 */     options.addOutputFilename(a_sOutputPath);
/* 273 */     options.setInputFileIdentifier(a_sOriginalFilePath);
/* 274 */     convert(options);
/*     */   }
/*     */ 
/*     */   public static void addTint(String a_sInputPath, String a_sOutputPath, String a_sColour, String a_sPercentage, String a_sOriginalFilePath)
/*     */     throws ImageException
/*     */   {
/* 296 */     ABImageMagickOptionList options = new ABImageMagickOptionList();
/*     */ 
/* 299 */     options.addType("Grayscale");
/* 300 */     options.addInputFilename(a_sInputPath);
/* 301 */     options.addOutputFilename(a_sOutputPath);
/* 302 */     mogrify(options);
/*     */ 
/* 305 */     options = new ABImageMagickOptionList();
/* 306 */     options.addInputFilename(a_sInputPath);
/* 307 */     options.addFill("#" + a_sColour);
/* 308 */     options.addTint(a_sPercentage);
/* 309 */     options.addOutputFilename(a_sOutputPath);
/* 310 */     options.setInputFileIdentifier(a_sOriginalFilePath);
/* 311 */     options.setDoNotCache(true);
/* 312 */     convert(options);
/*     */   }
/*     */ 
/*     */   public static void convertToGreyScale(String a_sInputPath, String a_sOutputPath, String a_sOriginalFilePath)
/*     */     throws ImageException
/*     */   {
/* 332 */     ABImageMagickOptionList options = new ABImageMagickOptionList();
/*     */ 
/* 335 */     options.addType("Grayscale");
/* 336 */     options.addInputFilename(a_sInputPath);
/* 337 */     options.addOutputFilename(a_sOutputPath);
/* 338 */     options.setDoNotCache(true);
/* 339 */     mogrify(options);
/*     */   }
/*     */ 
/*     */   public static void resizeToRGB(String a_sInputPath, String a_sOutputPath, int a_iBoundingWidth, int a_iBoundingHeight, String a_sRgbColorProfile, String a_sCmykColorProfile)
/*     */     throws ImageException
/*     */   {
/* 356 */     resizeToJpg(a_sInputPath, a_sOutputPath, a_iBoundingWidth, a_iBoundingHeight, a_sRgbColorProfile, a_sCmykColorProfile);
/*     */   }
/*     */ 
/*     */   public static void resizeToJpg(String a_sInputPath, String a_sOutputPath, int a_iBoundingWidth, int a_iBoundingHeight, String a_sRgbProfilePath, String a_sCmykProfilePath)
/*     */     throws ImageException
/*     */   {
/* 387 */     ImageFileInfo imageInfo = getInfo(a_sInputPath);
/*     */ 
/* 389 */     int iColorSpace = imageInfo.getColorSpace();
/* 390 */     boolean bIsCMYK = getIsCMYK(iColorSpace);
/*     */ 
/* 392 */     resizeToJpg(a_sInputPath, a_sOutputPath, imageInfo.getWidth(), imageInfo.getHeight(), a_iBoundingWidth, a_iBoundingHeight, bIsCMYK, a_sRgbProfilePath, a_sCmykProfilePath, imageInfo.getNumberOfLayers());
/*     */   }
/*     */ 
/*     */   public static int[] resizeToJpg(String a_sInputPath, String a_sOutputPath, int a_iWidthAspect, int a_iHeightAspect, int a_iBoundingWidth, int a_iBoundingHeight, boolean a_bSourceIsCMYK, String a_sRgbProfilePath, String a_sCmykProfilePath, int a_iNumLayers)
/*     */     throws ImageException
/*     */   {
/* 434 */     int[] aiNewSize = null;
/* 435 */     ABImageMagickOptionList options = new ABImageMagickOptionList();
/*     */ 
/* 437 */     options.addInputFilename(a_sInputPath);
/* 438 */     aiNewSize = options.addResize(a_iWidthAspect, a_iHeightAspect, a_iBoundingWidth, a_iBoundingHeight, null);
/*     */ 
/* 440 */     if (a_bSourceIsCMYK)
/*     */     {
/* 442 */       options.convertColorSpace(a_sCmykProfilePath, a_sRgbProfilePath, false);
/*     */     }
/*     */ 
/* 445 */     options.addStrip();
/*     */ 
/* 447 */     options.addFormatSpecificOptions(a_sInputPath, a_sOutputPath);
/* 448 */     options.addOutputFilename(a_sOutputPath);
/*     */ 
/* 450 */     if (a_iNumLayers > 1)
/*     */     {
/* 452 */       options.specifyInputLayer(0);
/*     */     }
/*     */ 
/* 455 */     convert(options);
/*     */ 
/* 457 */     return aiNewSize;
/*     */   }
/*     */ 
/*     */   public static int[] getDimensions(String a_sPath)
/*     */     throws ImageException
/*     */   {
/* 476 */     ImageFileInfo imageInfo = getInfo(a_sPath);
/* 477 */     return new int[] { imageInfo.getWidth(), imageInfo.getHeight() };
/*     */   }
/*     */ 
/*     */   public static ImageFileInfo getInfo(String a_sPath, int a_iLayer)
/*     */     throws ImageException
/*     */   {
/* 491 */     return getInfo(a_sPath + "[" + a_iLayer + "]");
/*     */   }
/*     */ 
/*     */   public static ImageFileInfo getInfo(String a_sPath)
/*     */     throws ImageException
/*     */   {
/* 512 */     String[] sArgs = { "w", "h", "r" };
/*     */ 
/* 514 */     String[] dim = getInfo(Arrays.asList(sArgs), a_sPath);
/*     */ 
/* 517 */     int iNumberOfLayers = dim.length / 3;
/*     */ 
/* 519 */     ImageFileInfo fileInfo = new ImageFileInfo(iNumberOfLayers);
/*     */ 
/* 522 */     int iOffset = sArgs.length;
/*     */ 
/* 525 */     int iLastIndex = dim.length - iOffset;
/* 526 */     int iLayer = 0;
/*     */ 
/* 529 */     for (int i = 0; i <= iLastIndex; i += iOffset)
/*     */     {
/*     */       try
/*     */       {
/* 533 */         fileInfo.setWidth(iLayer, Integer.parseInt(dim[i]));
/* 534 */         fileInfo.setHeight(iLayer, Integer.parseInt(dim[(i + 1)]));
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 538 */         GlobalApplication.getInstance().getLogger().error("Exception getting height/width for layer: " + " : " + nfe);
/*     */       }
/*     */ 
/* 541 */       iLayer++;
/*     */     }
/*     */ 
/* 547 */     fileInfo.setColorSpace(ImageMagick.parseColorspace(dim[(dim.length - 1)]));
/*     */ 
/* 549 */     return fileInfo;
/*     */   }
/*     */ 
/*     */   public static boolean getIsCMYK(int a_iColorSpace)
/*     */   {
/* 555 */     return a_iColorSpace == 12;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.util.ABImageMagick
 * JD-Core Version:    0.6.0
 */