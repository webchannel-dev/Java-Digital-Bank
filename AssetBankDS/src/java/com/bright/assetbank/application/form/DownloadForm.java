/*      */ package com.bright.assetbank.application.form;
/*      */ 
/*      */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*      */ import com.bright.assetbank.application.bean.VideoConversionResult;
/*      */ import com.bright.assetbank.approval.bean.AssetApproval;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.usage.bean.AssetUse;
/*      */ import com.bright.assetbank.usage.bean.UsageType;
/*      */ import com.bright.assetbank.usage.bean.UsageTypeFormat;
/*      */ import com.bright.assetbank.usage.bean.UsageTypeSelectOption;
/*      */ import com.bright.framework.image.bean.Colour;
/*      */ import com.bright.framework.image.util.ColourUtil;
/*      */ import com.bright.framework.struts.ValidationException;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ 
/*      */ public class DownloadForm extends AssetForm
/*      */ {
/*   70 */   private int m_iCropX = 0;
/*   71 */   private int m_iCropY = 0;
/*   72 */   private int m_iCropHeight = 0;
/*   73 */   private int m_iCropWidth = 0;
/*      */   private boolean m_cropMask;
/*   78 */   private long m_cropMaskId = 0L;
/*   79 */   private String m_cropMaskColour = null;
/*   80 */   private int m_iHeight = 0;
/*   81 */   private int m_iWidth = 0;
/*   82 */   private String m_sPresetMaxDimension = null;
/*   83 */   private String m_sImageFormat = null;
/*   84 */   private int m_iJpgQuality = 0;
/*   85 */   private boolean m_bCompress = false;
/*   86 */   private boolean m_bConvertToRGB = false;
/*   87 */   private int m_iDensity = 0;
/*   88 */   private boolean m_bApplyStrip = false;
/*   89 */   private String m_sTempLocation = null;
/*   90 */   private boolean m_bAllowAddAssetFromExisting = false;
/*   91 */   private int m_iLayerToDownload = -1;
/*   92 */   private String m_sPathToAddExisting = null;
/*   93 */   private boolean m_bAssetsHaveAgreement = false;
/*   94 */   private boolean m_bAssetHasDownloadMetdata = false;
/*   95 */   private boolean m_bDontEmbedMappedData = false;
/*   96 */   private AssetApproval m_assetApproval = null;
/*   97 */   private boolean m_bIsHighResRequest = false;
/*   98 */   private boolean m_bImageConsideredLowRes = false;
/*      */ 
/*  101 */   private String m_sEncryptedDownloadPath = null;
/*      */ 
/*  104 */   private AssetUse m_assetUse = null;
/*  105 */   private Vector<UsageTypeFormat> m_vecUsageTypeFormats = null;
/*  106 */   private long m_lUsageTypeFormatId = 0L;
/*      */ 
/*  109 */   private Vector m_usageTypeLists = null;
/*      */ 
/*  112 */   private boolean m_bUsageListIsFlat = false;
/*      */ 
/*  115 */   UsageTypeSelectOption m_selectedUsageType = null;
/*      */ 
/*  118 */   private boolean m_bValidateUsageType = false;
/*  119 */   private boolean m_bValidateUsageDescription = false;
/*      */ 
/*  122 */   private boolean m_bAdvanced = false;
/*      */ 
/*  125 */   private boolean m_bUserCanDownloadOriginal = false;
/*  126 */   private boolean m_bUserCanDownloadAdvanced = false;
/*  127 */   private boolean m_bUserCanDownloadOriginalAny = false;
/*  128 */   private boolean m_bUserCanDownloadAdvancedAny = false;
/*      */ 
/*  130 */   private boolean m_bAlreadyLoggedUse = false;
/*      */ 
/*  132 */   private String m_sCmsReturnUrl = null;
/*  133 */   private String m_sCmsCallbackElementId = null;
/*  134 */   private String m_sCmsCallbackElementValue = null;
/*      */ 
/*  137 */   private boolean m_bIsEmail = false;
/*      */ 
/*  140 */   private boolean m_bIsRepurpose = false;
/*      */ 
/*  142 */   private boolean m_bWatermarkImageOption = false;
/*      */ 
/*  144 */   private boolean m_bConversionInProgress = true;
/*  145 */   private VideoConversionResult m_result = null;
/*      */ 
/*  149 */   private boolean m_bIsCommercialPurchase = false;
/*      */ 
/*  151 */   private String m_sFrameRate = null;
/*  152 */   private String m_sAudioSampleRate = null;
/*  153 */   private double m_dStartOffset = -1.0D;
/*  154 */   private double m_dDuration = -1.0D;
/*  155 */   private String m_sVideoBitrate = "";
/*  156 */   private long m_sAudioBitrate = -1L;
/*      */ 
/*  158 */   private double m_dFilesizeInMegabytes = 0.0D;
/*  159 */   private boolean m_bDownloadingImages = false;
/*      */ 
/*  161 */   private Vector m_vecTints = null;
/*  162 */   private String m_sTint = null;
/*      */ 
/*  164 */   private Vector m_vecColorSpaces = null;
/*      */ 
/*  166 */   private int m_iDownloadOriginalCount = 0;
/*  167 */   private String m_sExcludedIds = "";
/*      */ 
/*  169 */   private boolean m_bDirectDownload = false;
/*      */ 
/*  171 */   private boolean m_bHighResOptionsDisabled = false;
/*  172 */   private boolean m_bHighResDirectDownload = false;
/*      */ 
/*  174 */   private String m_sReturnUrl = null;
/*      */ 
/*  176 */   private long m_lUsageTypeId = -1L;
/*  177 */   private int m_iMaxDownloadAttributes = 0;
/*  178 */   private List<AttributeValue> m_vDownloadAttributes = null;
/*      */ 
/*  180 */   private boolean m_bUserDoesNotHavePermission = false;
/*      */ 
/*  182 */   private List<UsageType> m_vecSecondaryUsageTypes = null;
/*      */ 
/*  184 */   private Set<Long> m_secondaryUsageTypeIds = null;
/*      */ 
/*  186 */   private int m_iSelectedColorSpaceId = 0;
/*      */ 
/*  188 */   private String m_sAttributeXml = null;
/*      */ 
/*      */   public DownloadForm()
/*      */   {
/*  195 */     this.m_selectedUsageType = new UsageTypeSelectOption();
/*  196 */     this.m_assetUse = new AssetUse();
/*      */   }
/*      */ 
/*      */   public boolean getUsageTypesAvailable()
/*      */   {
/*  208 */     if ((this.m_usageTypeLists != null) && (this.m_usageTypeLists.size() > 0))
/*      */     {
/*  210 */       return ((Vector)this.m_usageTypeLists.get(0)).size() > 0;
/*      */     }
/*  212 */     return false;
/*      */   }
/*      */ 
/*      */   public void copyMaskAndColourToConversionInfo(ImageConversionInfo a_conversionInfo)
/*      */   {
/*  222 */     if (getCropMask())
/*      */     {
/*  224 */       a_conversionInfo.setCropMaskId(getCropMaskId());
/*  225 */       if (StringUtils.isNotEmpty(getCropMaskColour()))
/*      */       {
/*  230 */         Colour maskColour = ColourUtil.colourFromHexString(getCropMaskColour());
/*  231 */         a_conversionInfo.setCropMaskColour(maskColour);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void validateCropMaskColour() throws ValidationException
/*      */   {
/*  238 */     String hexString = getCropMaskColour();
               Colour maskColour;
/*  239 */     if (StringUtils.isNotEmpty(hexString))
/*      */     {
/*      */       try
/*      */       {
/*  243 */         maskColour = ColourUtil.colourFromHexString(hexString);
/*      */       }
/*      */       catch (IllegalArgumentException e)
/*      */       {
/*      */         //Colour maskColour;
/*  247 */         throw new ValidationException("failedValidationHexColour", new String[] { hexString });
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setCropX(int a_iCropX)
/*      */   {
/*  258 */     this.m_iCropX = a_iCropX;
/*      */   }
/*      */ 
/*      */   public int getCropX()
/*      */   {
/*  263 */     return this.m_iCropX;
/*      */   }
/*      */ 
/*      */   public void setCropY(int a_iCropY)
/*      */   {
/*  268 */     this.m_iCropY = a_iCropY;
/*      */   }
/*      */ 
/*      */   public int getCropY()
/*      */   {
/*  273 */     return this.m_iCropY;
/*      */   }
/*      */ 
/*      */   public void setCropHeight(int a_iCropHeight)
/*      */   {
/*  278 */     this.m_iCropHeight = a_iCropHeight;
/*      */   }
/*      */ 
/*      */   public int getCropHeight()
/*      */   {
/*  283 */     return this.m_iCropHeight;
/*      */   }
/*      */ 
/*      */   public void setCropWidth(int a_iCropWidth)
/*      */   {
/*  288 */     this.m_iCropWidth = a_iCropWidth;
/*      */   }
/*      */ 
/*      */   public int getCropWidth()
/*      */   {
/*  293 */     return this.m_iCropWidth;
/*      */   }
/*      */ 
/*      */   public boolean getCropMask()
/*      */   {
/*  298 */     return this.m_cropMask;
/*      */   }
/*      */ 
/*      */   public void setCropMask(boolean a_cropMask)
/*      */   {
/*  303 */     this.m_cropMask = a_cropMask;
/*      */   }
/*      */ 
/*      */   public long getCropMaskId()
/*      */   {
/*  308 */     return this.m_cropMaskId;
/*      */   }
/*      */ 
/*      */   public void setCropMaskId(long a_cropMaskId)
/*      */   {
/*  313 */     this.m_cropMaskId = a_cropMaskId;
/*      */   }
/*      */ 
/*      */   public String getCropMaskColour()
/*      */   {
/*  318 */     return this.m_cropMaskColour;
/*      */   }
/*      */ 
/*      */   public void setCropMaskColour(String a_cropMaskColour)
/*      */   {
/*  323 */     this.m_cropMaskColour = a_cropMaskColour;
/*      */   }
/*      */ 
/*      */   public void setHeight(int a_iHeight)
/*      */   {
/*  328 */     this.m_iHeight = a_iHeight;
/*      */   }
/*      */ 
/*      */   public int getHeight()
/*      */   {
/*  333 */     return this.m_iHeight;
/*      */   }
/*      */ 
/*      */   public void setWidth(int a_iWidth)
/*      */   {
/*  338 */     this.m_iWidth = a_iWidth;
/*      */   }
/*      */ 
/*      */   public int getWidth()
/*      */   {
/*  343 */     return this.m_iWidth;
/*      */   }
/*      */ 
/*      */   public int getSize() {
/*  347 */     return Math.max(getHeight(), getWidth());
/*      */   }
/*      */ 
/*      */   public void setImageFormat(String a_sImageFormat)
/*      */   {
/*  352 */     this.m_sImageFormat = a_sImageFormat;
/*      */   }
/*      */ 
/*      */   public String getImageFormat()
/*      */   {
/*  357 */     return this.m_sImageFormat;
/*      */   }
/*      */ 
/*      */   public void setJpegQuality(int a_iJpgQuality)
/*      */   {
/*  362 */     this.m_iJpgQuality = a_iJpgQuality;
/*      */   }
/*      */ 
/*      */   public int getJpegQuality()
/*      */   {
/*  367 */     return this.m_iJpgQuality;
/*      */   }
/*      */ 
/*      */   public void setEncryptedDownloadPath(String a_sDownloadPath)
/*      */   {
/*  372 */     this.m_sEncryptedDownloadPath = a_sDownloadPath;
/*      */   }
/*      */ 
/*      */   public String getEncryptedDownloadPath()
/*      */   {
/*  377 */     return this.m_sEncryptedDownloadPath;
/*      */   }
/*      */ 
/*      */   public void setUsageTypeFormats(Vector<UsageTypeFormat> a_vecUsageTypeFormats)
/*      */   {
/*  382 */     this.m_vecUsageTypeFormats = a_vecUsageTypeFormats;
/*      */   }
/*      */ 
/*      */   public Vector<UsageTypeFormat> getUsageTypeFormats()
/*      */   {
/*  387 */     return this.m_vecUsageTypeFormats;
/*      */   }
/*      */ 
/*      */   public boolean getHasUsageTypeFormats()
/*      */   {
/*  394 */     return (getUsageTypeFormats() != null) && (getUsageTypeFormats().size() > 0);
/*      */   }
/*      */ 
/*      */   public void setUsageTypeFormatId(long a_lUsageTypeFormatId)
/*      */   {
/*  401 */     this.m_lUsageTypeFormatId = a_lUsageTypeFormatId;
/*      */   }
/*      */ 
/*      */   public long getUsageTypeFormatId()
/*      */   {
/*  406 */     return this.m_lUsageTypeFormatId;
/*      */   }
/*      */ 
/*      */   public UsageTypeFormat getSelectedUsageTypeFormat()
/*      */   {
/*  417 */     if (getAdvanced())
/*      */     {
/*  419 */       return null;
/*      */     }
/*      */ 
/*  422 */     List <UsageTypeFormat> usageTypeFormats = getUsageTypeFormats();
/*  423 */     if (usageTypeFormats == null)
/*      */     {
/*  425 */       return null;
/*      */     }
/*      */ 
/*  434 */     if ((this.m_lUsageTypeFormatId <= 0L) && (!usageTypeFormats.isEmpty()))
/*      */     {
/*  436 */       return (UsageTypeFormat)usageTypeFormats.get(0);
/*      */     }
/*      */ 
/*  439 */     for (UsageTypeFormat usageTypeFormat : usageTypeFormats)
/*      */     {
/*  441 */       if (usageTypeFormat.getId() == this.m_lUsageTypeFormatId)
/*      */       {
/*  443 */         return usageTypeFormat;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  448 */     return null;
/*      */   }
/*      */ 
/*      */   public AssetUse getAssetUse()
/*      */   {
/*  454 */     return this.m_assetUse;
/*      */   }
/*      */ 
/*      */   public void setAssetUse(AssetUse a_sAssetUse)
/*      */   {
/*  460 */     this.m_assetUse = a_sAssetUse;
/*      */   }
/*      */ 
/*      */   public String getPresetMaxDimension()
/*      */   {
/*  466 */     return this.m_sPresetMaxDimension;
/*      */   }
/*      */ 
/*      */   public void setPresetMaxDimension(String a_sPresetMaxDimension)
/*      */   {
/*  471 */     this.m_sPresetMaxDimension = a_sPresetMaxDimension;
/*      */   }
/*      */ 
/*      */   public boolean getCompress()
/*      */   {
/*  476 */     return this.m_bCompress;
/*      */   }
/*      */ 
/*      */   public void setCompress(boolean a_sCompress) {
/*  480 */     this.m_bCompress = a_sCompress;
/*      */   }
/*      */ 
/*      */   public Vector getUsageTypeLists()
/*      */   {
/*  485 */     return this.m_usageTypeLists;
/*      */   }
/*      */ 
/*      */   public void setUsageTypeLists(Vector a_sUsageTypeLists)
/*      */   {
/*  490 */     this.m_usageTypeLists = a_sUsageTypeLists;
/*      */   }
/*      */ 
/*      */   public boolean getUsageListIsFlat()
/*      */   {
/*  495 */     return this.m_bUsageListIsFlat;
/*      */   }
/*      */ 
/*      */   public void setUsageListIsFlat(boolean a_sUsageListIsFlat) {
/*  499 */     this.m_bUsageListIsFlat = a_sUsageListIsFlat;
/*      */   }
/*      */ 
/*      */   public UsageTypeSelectOption getSelectedUsageType()
/*      */   {
/*  504 */     return this.m_selectedUsageType;
/*      */   }
/*      */ 
/*      */   public void setSelectedUsageType(UsageTypeSelectOption a_sSelectedUsageType) {
/*  508 */     this.m_selectedUsageType = a_sSelectedUsageType;
/*      */   }
/*      */ 
/*      */   public boolean getAdvanced()
/*      */   {
/*  513 */     return this.m_bAdvanced;
/*      */   }
/*      */ 
/*      */   public void setAdvanced(boolean a_sAdvanced) {
/*  517 */     this.m_bAdvanced = a_sAdvanced;
/*      */   }
/*      */ 
/*      */   public boolean getConvertToRGB() {
/*  521 */     return this.m_bConvertToRGB;
/*      */   }
/*      */ 
/*      */   public void setConvertToRGB(boolean a_bConvertToRGB) {
/*  525 */     this.m_bConvertToRGB = a_bConvertToRGB;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanDownloadAdvanced()
/*      */   {
/*  531 */     return this.m_bUserCanDownloadAdvanced;
/*      */   }
/*      */ 
/*      */   public void setUserCanDownloadAdvanced(boolean a_sUserCanDownloadAdvanced) {
/*  535 */     this.m_bUserCanDownloadAdvanced = a_sUserCanDownloadAdvanced;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanDownloadOriginal() {
/*  539 */     return this.m_bUserCanDownloadOriginal;
/*      */   }
/*      */ 
/*      */   public void setUserCanDownloadOriginal(boolean a_sUserCanDownloadOriginal) {
/*  543 */     this.m_bUserCanDownloadOriginal = a_sUserCanDownloadOriginal;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanDownloadAdvancedAny()
/*      */   {
/*  548 */     return this.m_bUserCanDownloadAdvancedAny;
/*      */   }
/*      */ 
/*      */   public void setUserCanDownloadAdvancedAny(boolean a_sUserCanDownloadAdvancedAny) {
/*  552 */     this.m_bUserCanDownloadAdvancedAny = a_sUserCanDownloadAdvancedAny;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanDownloadOriginalAny() {
/*  556 */     return this.m_bUserCanDownloadOriginalAny;
/*      */   }
/*      */ 
/*      */   public void setUserCanDownloadOriginalAny(boolean a_sUserCanDownloadOriginalAny) {
/*  560 */     this.m_bUserCanDownloadOriginalAny = a_sUserCanDownloadOriginalAny;
/*      */   }
/*      */ 
/*      */   public boolean getAlreadyLoggedUse()
/*      */   {
/*  565 */     return this.m_bAlreadyLoggedUse;
/*      */   }
/*      */ 
/*      */   public void setAlreadyLoggedUse(boolean a_bAlreadyLoggedUse) {
/*  569 */     this.m_bAlreadyLoggedUse = a_bAlreadyLoggedUse;
/*      */   }
/*      */ 
/*      */   public int getDensity()
/*      */   {
/*  574 */     return this.m_iDensity;
/*      */   }
/*      */ 
/*      */   public void setDensity(int a_iDensity) {
/*  578 */     this.m_iDensity = a_iDensity;
/*      */   }
/*      */ 
/*      */   public String getCmsReturnUrl()
/*      */   {
/*  583 */     return this.m_sCmsReturnUrl;
/*      */   }
/*      */ 
/*      */   public void setCmsReturnUrl(String a_sCmsReturnUrl) {
/*  587 */     this.m_sCmsReturnUrl = a_sCmsReturnUrl;
/*      */   }
/*      */ 
/*      */   public boolean getEmail()
/*      */   {
/*  592 */     return this.m_bIsEmail;
/*      */   }
/*      */ 
/*      */   public void setEmail(boolean a_bIsEmail) {
/*  596 */     this.m_bIsEmail = a_bIsEmail;
/*      */   }
/*      */ 
/*      */   public boolean getIsCommercialPurchase()
/*      */   {
/*  601 */     return this.m_bIsCommercialPurchase;
/*      */   }
/*      */ 
/*      */   public void setIsCommercialPurchase(boolean a_sIsCommercialPurchase)
/*      */   {
/*  606 */     this.m_bIsCommercialPurchase = a_sIsCommercialPurchase;
/*      */   }
/*      */ 
/*      */   public boolean getValidateUsageType()
/*      */   {
/*  611 */     return this.m_bValidateUsageType;
/*      */   }
/*      */ 
/*      */   public void setValidateUsageType(boolean a_sValidateUsageType)
/*      */   {
/*  616 */     this.m_bValidateUsageType = a_sValidateUsageType;
/*      */   }
/*      */ 
/*      */   public boolean getValidateUsageDescription()
/*      */   {
/*  622 */     return this.m_bValidateUsageDescription;
/*      */   }
/*      */ 
/*      */   public void setValidateUsageDescription(boolean a_sValidateUsageDescription)
/*      */   {
/*  627 */     this.m_bValidateUsageDescription = a_sValidateUsageDescription;
/*      */   }
/*      */ 
/*      */   public boolean getApplyStrip()
/*      */   {
/*  632 */     return this.m_bApplyStrip;
/*      */   }
/*      */ 
/*      */   public void setApplyStrip(boolean a_bApplyStrip) {
/*  636 */     this.m_bApplyStrip = a_bApplyStrip;
/*      */   }
/*      */ 
/*      */   public boolean isAllowAddAssetFromExisting()
/*      */   {
/*  641 */     return this.m_bAllowAddAssetFromExisting;
/*      */   }
/*      */ 
/*      */   public void setAllowAddAssetFromExisting(boolean a_bAllowAddAssetFromExisting) {
/*  645 */     this.m_bAllowAddAssetFromExisting = a_bAllowAddAssetFromExisting;
/*      */   }
/*      */ 
/*      */   public String getTempLocation()
/*      */   {
/*  650 */     return this.m_sTempLocation;
/*      */   }
/*      */ 
/*      */   public void setTempLocation(String a_sTempLocation) {
/*  654 */     this.m_sTempLocation = a_sTempLocation;
/*      */   }
/*      */ 
/*      */   public String getCmsCallbackElementId()
/*      */   {
/*  661 */     return this.m_sCmsCallbackElementId;
/*      */   }
/*      */ 
/*      */   public void setCmsCallbackElementId(String cmsCallbackElementId)
/*      */   {
/*  668 */     this.m_sCmsCallbackElementId = cmsCallbackElementId;
/*      */   }
/*      */ 
/*      */   public String getCmsCallbackElementValue()
/*      */   {
/*  675 */     return this.m_sCmsCallbackElementValue;
/*      */   }
/*      */ 
/*      */   public void setCmsCallbackElementValue(String cmsCallbackElementValue)
/*      */   {
/*  682 */     this.m_sCmsCallbackElementValue = cmsCallbackElementValue;
/*      */   }
/*      */ 
/*      */   public void setConversionInProgress(boolean a_bConversionInProgress)
/*      */   {
/*  687 */     this.m_bConversionInProgress = a_bConversionInProgress;
/*      */   }
/*      */ 
/*      */   public boolean getConversionInProgress()
/*      */   {
/*  692 */     return this.m_bConversionInProgress;
/*      */   }
/*      */ 
/*      */   public void setVideoConversionResult(VideoConversionResult a_result)
/*      */   {
/*  697 */     this.m_result = a_result;
/*      */   }
/*      */ 
/*      */   public VideoConversionResult getVideoConversionResult()
/*      */   {
/*  702 */     return this.m_result;
/*      */   }
/*      */ 
/*      */   public void setFrameRate(String a_sFrameRate)
/*      */   {
/*  707 */     this.m_sFrameRate = a_sFrameRate;
/*      */   }
/*      */ 
/*      */   public String getFrameRate()
/*      */   {
/*  712 */     return this.m_sFrameRate;
/*      */   }
/*      */ 
/*      */   public void setAudioSampleRate(String a_sAudioSampleRate)
/*      */   {
/*  717 */     this.m_sAudioSampleRate = a_sAudioSampleRate;
/*      */   }
/*      */ 
/*      */   public String getAudioSampleRate()
/*      */   {
/*  722 */     return this.m_sAudioSampleRate;
/*      */   }
/*      */ 
/*      */   public void setVideoBitrate(String a_sVideoBitrate)
/*      */   {
/*  727 */     this.m_sVideoBitrate = a_sVideoBitrate;
/*      */   }
/*      */ 
/*      */   public String getVideoBitrate()
/*      */   {
/*  732 */     return this.m_sVideoBitrate;
/*      */   }
/*      */ 
/*      */   public void setAudioBitrate(long a_sBitrate)
/*      */   {
/*  737 */     this.m_sAudioBitrate = a_sBitrate;
/*      */   }
/*      */ 
/*      */   public long getAudioBitrate()
/*      */   {
/*  742 */     return this.m_sAudioBitrate;
/*      */   }
/*      */ 
/*      */   public void setStartOffset(double a_iStartOffset)
/*      */   {
/*  747 */     this.m_dStartOffset = a_iStartOffset;
/*      */   }
/*      */ 
/*      */   public double getStartOffset()
/*      */   {
/*  752 */     return this.m_dStartOffset;
/*      */   }
/*      */ 
/*      */   public void setDuration(double a_dDuration)
/*      */   {
/*  757 */     this.m_dDuration = a_dDuration;
/*      */   }
/*      */ 
/*      */   public double getDuration()
/*      */   {
/*  762 */     return this.m_dDuration;
/*      */   }
/*      */ 
/*      */   public void setFilesizeInMegabytes(double a_dFilesizeInMegabytes)
/*      */   {
/*  767 */     this.m_dFilesizeInMegabytes = a_dFilesizeInMegabytes;
/*      */   }
/*      */ 
/*      */   public double getFilesizeInMegabytes()
/*      */   {
/*  772 */     return this.m_dFilesizeInMegabytes;
/*      */   }
/*      */ 
/*      */   public void setDownloadingImages(boolean a_bDownloadingImages)
/*      */   {
/*  777 */     this.m_bDownloadingImages = a_bDownloadingImages;
/*      */   }
/*      */ 
/*      */   public boolean getDownloadingImages()
/*      */   {
/*  782 */     return this.m_bDownloadingImages;
/*      */   }
/*      */ 
/*      */   public int getLayerToDownload()
/*      */   {
/*  787 */     return this.m_iLayerToDownload;
/*      */   }
/*      */ 
/*      */   public void setLayerToDownload(int layerToDownload)
/*      */   {
/*  792 */     this.m_iLayerToDownload = layerToDownload;
/*      */   }
/*      */ 
/*      */   public boolean getWatermarkImageOption()
/*      */   {
/*  797 */     return this.m_bWatermarkImageOption;
/*      */   }
/*      */ 
/*      */   public void setWatermarkImageOption(boolean watermarkImageOption)
/*      */   {
/*  802 */     this.m_bWatermarkImageOption = watermarkImageOption;
/*      */   }
/*      */ 
/*      */   public String getPathToAddExisting()
/*      */   {
/*  807 */     return this.m_sPathToAddExisting;
/*      */   }
/*      */ 
/*      */   public void setPathToAddExisting(String a_sPathToAddExisting) {
/*  811 */     this.m_sPathToAddExisting = a_sPathToAddExisting;
/*      */   }
/*      */ 
/*      */   public boolean isRepurpose()
/*      */   {
/*  816 */     return this.m_bIsRepurpose;
/*      */   }
/*      */ 
/*      */   public void setRepurpose(boolean isRepurpose)
/*      */   {
/*  821 */     this.m_bIsRepurpose = isRepurpose;
/*      */   }
/*      */ 
/*      */   public boolean getAssetsHaveAgreement()
/*      */   {
/*  826 */     return this.m_bAssetsHaveAgreement;
/*      */   }
/*      */ 
/*      */   public void setAssetsHaveAgreement(boolean a_bAssetsHaveAgreement) {
/*  830 */     this.m_bAssetsHaveAgreement = a_bAssetsHaveAgreement;
/*      */   }
/*      */ 
/*      */   public void setTints(Vector a_vecTints)
/*      */   {
/*  835 */     this.m_vecTints = a_vecTints;
/*      */   }
/*      */ 
/*      */   public Vector getTints()
/*      */   {
/*  840 */     return this.m_vecTints;
/*      */   }
/*      */ 
/*      */   public void setTint(String a_sTint)
/*      */   {
/*  845 */     this.m_sTint = a_sTint;
/*      */   }
/*      */ 
/*      */   public String getTint()
/*      */   {
/*  850 */     return this.m_sTint;
/*      */   }
/*      */ 
/*      */   public boolean getAssetHasDownloadMetadata()
/*      */   {
/*  855 */     return this.m_bAssetHasDownloadMetdata;
/*      */   }
/*      */ 
/*      */   public void setAssetHasDownloadMetadata(boolean a_bAssetHasDownloadMetdata)
/*      */   {
/*  860 */     this.m_bAssetHasDownloadMetdata = a_bAssetHasDownloadMetdata;
/*      */   }
/*      */ 
/*      */   public boolean getDontEmbedMappedData()
/*      */   {
/*  865 */     return this.m_bDontEmbedMappedData;
/*      */   }
/*      */ 
/*      */   public void setDontEmbedMappedData(boolean a_bDontEmbedMappedData)
/*      */   {
/*  870 */     this.m_bDontEmbedMappedData = a_bDontEmbedMappedData;
/*      */   }
/*      */ 
/*      */   public void setColorSpaces(Vector a_vecColorSpaces)
/*      */   {
/*  875 */     this.m_vecColorSpaces = a_vecColorSpaces;
/*      */   }
/*      */ 
/*      */   public Vector getColorSpaces()
/*      */   {
/*  880 */     return this.m_vecColorSpaces;
/*      */   }
/*      */ 
/*      */   public void setAssetApproval(AssetApproval a_assetApproval)
/*      */   {
/*  885 */     this.m_assetApproval = a_assetApproval;
/*      */   }
/*      */ 
/*      */   public AssetApproval getAssetApproval()
/*      */   {
/*  890 */     return this.m_assetApproval;
/*      */   }
/*      */ 
/*      */   public void setDownloadOriginalCount(int a_iDownloadOriginalCount)
/*      */   {
/*  895 */     this.m_iDownloadOriginalCount = a_iDownloadOriginalCount;
/*      */   }
/*      */ 
/*      */   public int getDownloadOriginalCount()
/*      */   {
/*  900 */     return this.m_iDownloadOriginalCount;
/*      */   }
/*      */ 
/*      */   public void setExcludedIds(String a_sExcludedIds)
/*      */   {
/*  905 */     this.m_sExcludedIds = a_sExcludedIds;
/*      */   }
/*      */ 
/*      */   public String getExcludedIds()
/*      */   {
/*  910 */     return this.m_sExcludedIds;
/*      */   }
/*      */ 
/*      */   public void setDirectDownload(boolean a_bDirectDownload)
/*      */   {
/*  915 */     this.m_bDirectDownload = a_bDirectDownload;
/*      */   }
/*      */ 
/*      */   public boolean getDirectDownload()
/*      */   {
/*  920 */     return this.m_bDirectDownload;
/*      */   }
/*      */ 
/*      */   public void setHighResDirectDownload(boolean a_bHighResDirectDownload)
/*      */   {
/*  925 */     this.m_bHighResDirectDownload = a_bHighResDirectDownload;
/*      */   }
/*      */ 
/*      */   public boolean getHighResDirectDownload()
/*      */   {
/*  930 */     return this.m_bHighResDirectDownload;
/*      */   }
/*      */ 
/*      */   public boolean getHighResOptionsDisabled()
/*      */   {
/*  935 */     return this.m_bHighResOptionsDisabled;
/*      */   }
/*      */ 
/*      */   public void setHighResOptionsDisabled(boolean a_bHighResOptionsDisabled) {
/*  939 */     this.m_bHighResOptionsDisabled = a_bHighResOptionsDisabled;
/*      */   }
/*      */ 
/*      */   public boolean getIsHighResRequest()
/*      */   {
/*  944 */     return this.m_bIsHighResRequest;
/*      */   }
/*      */ 
/*      */   public void setIsHighResRequest(boolean a_bIsHighResRequest) {
/*  948 */     this.m_bIsHighResRequest = a_bIsHighResRequest;
/*      */   }
/*      */ 
/*      */   public void setReturnUrl(String a_sReturnUrl)
/*      */   {
/*  953 */     this.m_sReturnUrl = a_sReturnUrl;
/*      */   }
/*      */ 
/*      */   public String getReturnUrl()
/*      */   {
/*  958 */     return this.m_sReturnUrl;
/*      */   }
/*      */ 
/*      */   public boolean getImageConsideredLowRes()
/*      */   {
/*  963 */     return this.m_bImageConsideredLowRes;
/*      */   }
/*      */ 
/*      */   public void setImageConsideredLowRes(boolean a_bImageConsideredLowRes) {
/*  967 */     this.m_bImageConsideredLowRes = a_bImageConsideredLowRes;
/*      */   }
/*      */ 
/*      */   public List<AttributeValue> getDownloadAttributes()
/*      */   {
/*  972 */     return this.m_vDownloadAttributes;
/*      */   }
/*      */ 
/*      */   public void setDownloadAttributes(List<AttributeValue> a_vDownloadAttributes)
/*      */   {
/*  977 */     this.m_vDownloadAttributes = a_vDownloadAttributes;
/*      */   }
/*      */ 
/*      */   public int getMaxDownloadAttributes()
/*      */   {
/*  982 */     return this.m_iMaxDownloadAttributes;
/*      */   }
/*      */ 
/*      */   public void setMaxDownloadAttributes(int a_iMaxDownloadAttributes)
/*      */   {
/*  987 */     this.m_iMaxDownloadAttributes = a_iMaxDownloadAttributes;
/*      */   }
/*      */ 
/*      */   public void setUsageTypeId(long a_lUsageTypeId)
/*      */   {
/*  992 */     this.m_lUsageTypeId = a_lUsageTypeId;
/*      */   }
/*      */ 
/*      */   public long getUsageTypeId()
/*      */   {
/*  997 */     return this.m_lUsageTypeId;
/*      */   }
/*      */ 
/*      */   public boolean getUserDoesNotHavePermission()
/*      */   {
/* 1002 */     return this.m_bUserDoesNotHavePermission;
/*      */   }
/*      */ 
/*      */   public void setUserDoesNotHavePermission(boolean a_sUserDoesNotHavePermission) {
/* 1006 */     this.m_bUserDoesNotHavePermission = a_sUserDoesNotHavePermission;
/*      */   }
/*      */ 
/*      */   public List<UsageType> getSecondaryUsageTypes()
/*      */   {
/* 1011 */     return this.m_vecSecondaryUsageTypes;
/*      */   }
/*      */ 
/*      */   public void setSecondaryUsageTypes(List<UsageType> a_vecSecondaryUsageTypes) {
/* 1015 */     this.m_vecSecondaryUsageTypes = a_vecSecondaryUsageTypes;
/*      */   }
/*      */ 
/*      */   public Set<Long> getSecondaryUsageTypeIds()
/*      */   {
/* 1020 */     return this.m_secondaryUsageTypeIds;
/*      */   }
/*      */ 
/*      */   public void setSecondaryUsageTypeIds(Set<Long> a_secondaryUsageTypeIds) {
/* 1024 */     this.m_secondaryUsageTypeIds = a_secondaryUsageTypeIds;
/*      */   }
/*      */ 
/*      */   public void setSelectedColorSpaceId(int a_iSelectedColorSpaceId)
/*      */   {
/* 1029 */     this.m_iSelectedColorSpaceId = a_iSelectedColorSpaceId;
/*      */   }
/*      */ 
/*      */   public int getSelectedColorSpaceId()
/*      */   {
/* 1034 */     return this.m_iSelectedColorSpaceId;
/*      */   }
/*      */ 
/*      */   public void setAttributeXml(String a_sAttributeXml)
/*      */   {
/* 1039 */     this.m_sAttributeXml = a_sAttributeXml;
/*      */   }
/*      */ 
/*      */   public String getAttributeXml() {
/* 1043 */     return this.m_sAttributeXml;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.form.DownloadForm
 * JD-Core Version:    0.6.0
 */