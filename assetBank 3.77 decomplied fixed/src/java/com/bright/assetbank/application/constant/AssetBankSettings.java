/*      */ package com.bright.assetbank.application.constant;
/*      */ 
/*      */ import com.bn2web.common.constant.GlobalSettings;
/*      */ import com.bright.framework.constant.FrameworkSettings;
/*      */ import com.bright.framework.util.FileUtil;
/*      */ import com.bright.framework.util.ServletUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ 
/*      */ public class AssetBankSettings extends FrameworkSettings
/*      */ {
/*   50 */   private static String[] m_asCmsAllowedFormats = null;
/*      */ 
/*      */   public static float getJpgConversionQuality()
/*      */   {
/*   61 */     return getInstance().getFloatSetting("jpeg-conversion-quality");
/*      */   }
/*      */ 
/*      */   public static String getAssetBoxFilename()
/*      */   {
/*   66 */     return getInstance().getStringSetting("asset-box-filename");
/*      */   }
/*      */ 
/*      */   public static String getApprovedFilename()
/*      */   {
/*   71 */     return getInstance().getStringSetting("approved-filename");
/*      */   }
/*      */ 
/*      */   public static int getPreviewImageMaxHeight()
/*      */   {
/*   76 */     return getInstance().getIntSetting("image-preview-max-height");
/*      */   }
/*      */ 
/*      */   public static int getPreviewImageMaxWidth()
/*      */   {
/*   81 */     return getInstance().getIntSetting("image-preview-max-width");
/*      */   }
/*      */ 
/*      */   public static int getVideoPreviewImageMaxHeight()
/*      */   {
/*   86 */     return getInstance().getIntSetting("video-preview-max-height");
/*      */   }
/*      */ 
/*      */   public static int getVideoPreviewImageMaxWidth()
/*      */   {
/*   91 */     return getInstance().getIntSetting("video-preview-max-width");
/*      */   }
/*      */ 
/*      */   public static int getThumbnailImageMaxHeight()
/*      */   {
/*   96 */     return getInstance().getIntSetting("image-thumbnail-max-height");
/*      */   }
/*      */ 
/*      */   public static int getThumbnailImageMaxWidth()
/*      */   {
/*  101 */     return getInstance().getIntSetting("image-thumbnail-max-width");
/*      */   }
/*      */ 
/*      */   public static int getHomogenizedImageMaxHeight()
/*      */   {
/*  106 */     return getInstance().getIntSetting("image-homogenized-max-height");
/*      */   }
/*      */ 
/*      */   public static int getHomogenizedImageMaxWidth()
/*      */   {
/*  111 */     return getInstance().getIntSetting("image-homogenized-max-width");
/*      */   }
/*      */ 
/*      */   public static int getFeaturedImageHeight()
/*      */   {
/*  116 */     return getInstance().getIntSetting("featured-image-height");
/*      */   }
/*      */ 
/*      */   public static int getFeaturedImageWidth()
/*      */   {
/*  121 */     return getInstance().getIntSetting("featured-image-width");
/*      */   }
/*      */ 
/*      */   public static int getLargeImageSize()
/*      */   {
/*  126 */     return getInstance().getIntSetting("view-large-image-size");
/*      */   }
/*      */ 
/*      */   public static int getUnwatermarkedLargeImageSize()
/*      */   {
/*  131 */     return getInstance().getIntSetting("view-unwatermarked-large-image-size");
/*      */   }
/*      */ 
/*      */   public static int getDefaultNumResultsPerPage()
/*      */   {
/*  136 */     return getInstance().getIntSetting("max-num-search-results");
/*      */   }
/*      */ 
/*      */   public static int getDefaultNumImagesPerBrowsePage()
/*      */   {
/*  141 */     return getInstance().getIntSetting("default-num-images-per-browse-page");
/*      */   }
/*      */ 
/*      */   public static int getMaxImageDownloadDimension()
/*      */   {
/*  146 */     return getInstance().getIntSetting("max-image-download-dimension");
/*      */   }
/*      */ 
/*      */   public static String getImageNotFoundImage()
/*      */   {
/*  151 */     return getInstance().getStringSetting("image-not-found-image");
/*      */   }
/*      */ 
/*      */   public static String getThumbnailsPendingImage()
/*      */   {
/*  156 */     return getInstance().getStringSetting("thumbnails-pending-image");
/*      */   }
/*      */ 
/*      */   public static boolean getImagesAreCroppable()
/*      */   {
/*  161 */     return getInstance().getBooleanSetting("images-are-croppable");
/*      */   }
/*      */ 
/*      */   public static boolean getImagesAreEmailable()
/*      */   {
/*  166 */     return getInstance().getBooleanSetting("images-are-emailable");
/*      */   }
/*      */ 
/*      */   public static int getAssetSummaryNumChars()
/*      */   {
/*  171 */     return getInstance().getIntSetting("asset-summary-num-chars");
/*      */   }
/*      */ 
/*      */   public static int getNumberRecentImages()
/*      */   {
/*  176 */     return getInstance().getIntSetting("num-recent-images");
/*      */   }
/*      */ 
/*      */   public static int getNumberMoreRecentAssets()
/*      */   {
/*  181 */     return getInstance().getIntSetting("num-more-recent-images");
/*      */   }
/*      */ 
/*      */   public static int getPromotedImagesOnHomepage()
/*      */   {
/*  186 */     return getInstance().getIntSetting("num-promoted-images-homepage");
/*      */   }
/*      */ 
/*      */   public static boolean getShowEmptyCategories()
/*      */   {
/*  191 */     return getInstance().getBooleanSetting("show-empty-categories");
/*      */   }
/*      */ 
/*      */   public static boolean getShowEmptyAccessLevels()
/*      */   {
/*  196 */     return getInstance().getBooleanSetting("show-empty-access-levels");
/*      */   }
/*      */ 
/*      */   public static String getIndexDirectory()
/*      */   {
/*  201 */     return getInstance().getStringSetting("index-directory");
/*      */   }
/*      */ 
/*      */   public static boolean getGuestUserCanViewUncategorisedAssets()
/*      */   {
/*  206 */     return Boolean.valueOf(getInstance().getStringSetting("guest-user-can-see-uncategorised-assets")).booleanValue();
/*      */   }
/*      */ 
/*      */   public static boolean getGuestUserCanHaveAssetBox()
/*      */   {
/*  211 */     return Boolean.valueOf(getInstance().getStringSetting("guest-user-can-have-assetbox")).booleanValue();
/*      */   }
/*      */ 
/*      */   public static boolean includeCategoryNamesInKeywordSearch()
/*      */   {
/*  216 */     return Boolean.valueOf(getInstance().getStringSetting("include-category-names-in-keyword-search")).booleanValue();
/*      */   }
/*      */ 
/*      */   public static boolean getThumbnailsCroppedNotScaled()
/*      */   {
/*  221 */     return Boolean.valueOf(getInstance().getStringSetting("thumbnails-cropped-not-scaled")).booleanValue();
/*      */   }
/*      */ 
/*      */   public static String getUnknownFileThumbnail()
/*      */   {
/*  226 */     return getInstance().getStringSetting("unknown-file-thumbnail");
/*      */   }
/*      */ 
/*      */   public static String getNoFileThumbnail()
/*      */   {
/*  231 */     return getInstance().getStringSetting("no-file-thumbnail");
/*      */   }
/*      */ 
/*      */   public static boolean getUsesActiveDirectory()
/*      */   {
/*  236 */     return getInstance().getBooleanSetting("uses-active-directory");
/*      */   }
/*      */ 
/*      */   public static boolean getWatermarkDownload()
/*      */   {
/*  241 */     return getInstance().getBooleanSetting("watermark-download");
/*      */   }
/*      */ 
/*      */   public static boolean getWatermarkPreviewDownload()
/*      */   {
/*  246 */     return getInstance().getBooleanSetting("watermark-preview-download");
/*      */   }
/*      */ 
/*      */   public static boolean getWatermarkFullSize()
/*      */   {
/*  251 */     return getInstance().getBooleanSetting("watermark-full-size");
/*      */   }
/*      */ 
/*      */   public static boolean getWatermarkPreview()
/*      */   {
/*  256 */     return getInstance().getBooleanSetting("watermark-preview");
/*      */   }
/*      */ 
/*      */   public static String getWatermarkFilename()
/*      */   {
/*  261 */     return getApplicationPath() + "/" + getInstance().getStringSetting("watermark-filename");
/*      */   }
/*      */ 
/*      */   public static float getWatermarkScaleFactor()
/*      */   {
/*  266 */     return getInstance().getFloatSetting("watermark-scale-factor");
/*      */   }
/*      */ 
/*      */   public static String getWatermarkGravity()
/*      */   {
/*  271 */     return getInstance().getStringSetting("watermark-gravity");
/*      */   }
/*      */ 
/*      */   public static float getWatermarkBrightness()
/*      */   {
/*  276 */     return getInstance().getFloatSetting("watermark-brightness");
/*      */   }
/*      */ 
/*      */   public static String getBulkUploadDirectory()
/*      */   {
/*  281 */     return getInstance().getStringSetting("bulk-upload-directory");
/*      */   }
/*      */ 
/*      */   public static String getBulkUploadProcessedDirectory()
/*      */   {
/*  286 */     return getInstance().getStringSetting("bulk-upload-processed-directory");
/*      */   }
/*      */ 
/*      */   public static boolean getDeleteProcessedUploadFiles()
/*      */   {
/*  291 */     return getInstance().getBooleanSetting("deleteProcessedUploadFiles");
/*      */   }
/*      */ 
/*      */   public static boolean getLinkItemsDefault()
/*      */   {
/*  296 */     return getInstance().getBooleanSetting("link-items-default");
/*      */   }
/*      */ 
/*      */   public static boolean getDeferThumbnailCreationDefault()
/*      */   {
/*  301 */     return getInstance().getBooleanSetting("defer-thumbnail-creation-default");
/*      */   }
/*      */ 
/*      */   public static boolean getPopulateNameFromFilenameDefault()
/*      */   {
/*  306 */     return getInstance().getBooleanSetting("populate-name-from-filename-default");
/*      */   }
/*      */ 
/*      */   public static boolean getUseFTP()
/*      */   {
/*  311 */     return getInstance().getBooleanSetting("use-ftp");
/*      */   }
/*      */ 
/*      */   public static String getFTPCommand()
/*      */   {
/*  316 */     return getInstance().getStringSetting("ftp-command");
/*      */   }
/*      */ 
/*      */   public static int getFTPPort()
/*      */   {
/*  321 */     return getInstance().getIntSetting("ftp-port");
/*      */   }
/*      */ 
/*      */   public static String getFTPHost()
/*      */   {
/*  326 */     return getInstance().getStringSetting("ftp-host");
/*      */   }
/*      */ 
/*      */   public static String getFTPUsername()
/*      */   {
/*  331 */     return getInstance().getStringSetting("ftp-username");
/*      */   }
/*      */ 
/*      */   public static String getFTPPassword()
/*      */   {
/*  336 */     return getInstance().getStringSetting("ftp-password");
/*      */   }
/*      */ 
/*      */   public static String getFTPChangeDirectory()
/*      */   {
/*  341 */     return getInstance().getStringSetting("ftp-change-directory");
/*      */   }
/*      */ 
/*      */   public static int getRemoveExpiredApprovalsHourOfDay()
/*      */   {
/*  346 */     return getInstance().getIntSetting("remove-expired-approvals-hour-of-day");
/*      */   }
/*      */ 
/*      */   public static int getDefaultApprovalExpiryPeriod()
/*      */   {
/*  351 */     return getInstance().getIntSetting("default_approval_expiry_period");
/*      */   }
/*      */ 
/*      */   public static int getAssetCodeCharCount()
/*      */   {
/*  356 */     return getInstance().getIntSetting("asset-code-char-count");
/*      */   }
/*      */ 
/*      */   public static String getAssetCodePaddingChar()
/*      */   {
/*  361 */     return getInstance().getStringSetting("asset-code-padding-char");
/*      */   }
/*      */ 
/*      */   public static boolean getUsersCanRegisterWithoutApproval()
/*      */   {
/*  367 */     return getInstance().getBooleanSetting("users-can-register-without-approval");
/*      */   }
/*      */ 
/*      */   public static boolean getExternalUsersCanRegisterWithoutApproval()
/*      */   {
/*  372 */     return getInstance().getBooleanSetting("external-users-can-register-without-approval");
/*      */   }
/*      */ 
/*      */   public static int getCleanupUploadedFilesHourOfDay()
/*      */   {
/*  377 */     return getInstance().getIntSetting("cleanup-uploaded-files-hour-of-day");
/*      */   }
/*      */ 
/*      */   public static int getUniversalOrgUnitCategoryId()
/*      */   {
/*  382 */     return getInstance().getIntSetting("universal-orgunit-category-id");
/*      */   }
/*      */ 
/*      */   public static int getSubmitUniversalCategoryId()
/*      */   {
/*  387 */     return getInstance().getIntSetting("submit-universal-category-id");
/*      */   }
/*      */ 
/*      */   public static String getOrgUnitGroupSeparator()
/*      */   {
/*  392 */     return getInstance().getStringSetting("orgunit-group-separator");
/*      */   }
/*      */ 
/*      */   public static String getOrgUnitStandardGroupName()
/*      */   {
/*  397 */     return getInstance().getStringSetting("orgunit-standard-group-name");
/*      */   }
/*      */ 
/*      */   public static String getOrgUnitAdminGroupName()
/*      */   {
/*  402 */     return getInstance().getStringSetting("orgunit-admin-group-name");
/*      */   }
/*      */ 
/*      */   public static boolean getOrgUnitUse()
/*      */   {
/*  407 */     return getInstance().getBooleanSetting("orgunit-use");
/*      */   }
/*      */ 
/*      */   public static String getDaysBeforeMonths()
/*      */   {
/*  412 */     return getInstance().getStringSetting("days-before-months");
/*      */   }
/*      */ 
/*      */   public static boolean getCanSearchAllAccessLevels()
/*      */   {
/*  417 */     return getInstance().getBooleanSetting("orgunit-can-search-all-access-levels");
/*      */   }
/*      */ 
/*      */   public static boolean getStoreFullQuery()
/*      */   {
/*  422 */     return getInstance().getBooleanSetting("store-full-query-for-report");
/*      */   }
/*      */ 
/*      */   public static boolean getRecordSearches()
/*      */   {
/*  427 */     return getInstance().getBooleanSetting("record-searches");
/*      */   }
/*      */ 
/*      */   public static int getStremlineSearchReportHour()
/*      */   {
/*  432 */     return getInstance().getIntSetting("streamline-search-report-hour-of-day");
/*      */   }
/*      */ 
/*      */   public static int getSearchReportDaysToKeep()
/*      */   {
/*  437 */     return getInstance().getIntSetting("search-report-days-to-keep");
/*      */   }
/*      */ 
/*      */   public static String getADUsernameExclusionPattern()
/*      */   {
/*  442 */     return getInstance().getStringSetting("ad-username-exclusion-pattern");
/*      */   }
/*      */ 
/*      */   public static String getApplicationVersion()
/*      */   {
/*  447 */     return getInstance().getStringSetting("version");
/*      */   }
/*      */ 
/*      */   public static boolean getShowImplicitAssetsInAccessLevelBrowser()
/*      */   {
/*  452 */     return getInstance().getBooleanSetting("show_implicit_assets_in_access_level_browser");
/*      */   }
/*      */ 
/*      */   public static boolean getShowImplicitAssetsInDescriptiveCategoryBrowser()
/*      */   {
/*  457 */     return getInstance().getBooleanSetting("show_implicit_assets_in_descriptive_category_browser");
/*      */   }
/*      */ 
/*      */   public static boolean getGetRelatedAssets()
/*      */   {
/*  462 */     return getInstance().getBooleanSetting("get-related-assets");
/*      */   }
/*      */ 
/*      */   public static boolean getSimpleConvertOptionsForDownload()
/*      */   {
/*  467 */     return getInstance().getBooleanSetting("simpleConvertOptionsForDownload");
/*      */   }
/*      */ 
/*      */   public static boolean getEnableKeywordTaxonomy()
/*      */   {
/*  472 */     return getInstance().getBooleanSetting("enable-keyword-taxonomy");
/*      */   }
/*      */ 
/*      */   public static String getKeywordDelimiter()
/*      */   {
/*  477 */     String delimiter = getInstance().getStringSetting("keyword-delimiter");
/*  478 */     if (StringUtils.isEmpty(delimiter))
/*      */     {
/*  480 */       return " ";
/*      */     }
/*      */ 
/*  484 */     return delimiter;
/*      */   }
/*      */ 
/*      */   public static boolean getKeywordAutoAdd()
/*      */   {
/*  490 */     return getInstance().getBooleanSetting("keyword-auto-add");
/*      */   }
/*      */ 
/*      */   public static boolean isMultipleLightboxesEnabled()
/*      */   {
/*  495 */     return getInstance().getBooleanSetting("multiple-lightboxes");
/*      */   }
/*      */ 
/*      */   public static boolean isSharedLightboxesEnabled()
/*      */   {
/*  500 */     return (isMultipleLightboxesEnabled()) && (getInstance().getBooleanSetting("shared-lightboxes"));
/*      */   }
/*      */ 
/*      */   public static boolean getShareLightboxGroupOnly()
/*      */   {
/*  505 */     return getInstance().getBooleanSetting("share-lightbox-group-only");
/*      */   }
/*      */ 
/*      */   public static boolean getEmailUserUponLightboxShare()
/*      */   {
/*  510 */     return getInstance().getBooleanSetting("email-user-upon-lightbox-share");
/*      */   }
/*      */ 
/*      */   public static boolean getShowRequestOnCD()
/*      */   {
/*  515 */     return getInstance().getBooleanSetting("show-request-on-cd");
/*      */   }
/*      */ 
/*      */   public static int getDownloadZipMaxSizeInMBs()
/*      */   {
/*  520 */     return getInstance().getIntSetting("download-zip-max-size-in-mb");
/*      */   }
/*      */ 
/*      */   public static int getMaxUsageTypeDepth()
/*      */   {
/*  525 */     return getInstance().getIntSetting("maxUsageTypeDepth");
/*      */   }
/*      */ 
/*      */   public static boolean getEnableGroupUsageExlusions()
/*      */   {
/*  530 */     return getInstance().getBooleanSetting("enable-group-usage-exclusions");
/*      */   }
/*      */ 
/*      */   public static boolean getStrictUsageExclusions()
/*      */   {
/*  535 */     return getInstance().getBooleanSetting("strict-usage-exclusions");
/*      */   }
/*      */ 
/*      */   public static boolean getStrictFilterExclusions()
/*      */   {
/*  540 */     return getInstance().getBooleanSetting("strict-filter-exclusions");
/*      */   }
/*      */ 
/*      */   public static boolean getUsersHaveDivisions()
/*      */   {
/*  545 */     return getInstance().getBooleanSetting("users-have-divisions");
/*      */   }
/*      */ 
/*      */   public static boolean getUsersHaveTitle()
/*      */   {
/*  550 */     return getInstance().getBooleanSetting("users-have-title");
/*      */   }
/*      */ 
/*      */   public static boolean getUsersHaveStructuredAddress()
/*      */   {
/*  555 */     return getInstance().getBooleanSetting("users-have-structured-address");
/*      */   }
/*      */ 
/*      */   public static int getUserDefaultExpiryDays()
/*      */   {
/*  560 */     return getInstance().getIntSetting("user-default-expiry-days");
/*      */   }
/*      */ 
/*      */   public static int getNumberMonthsApprovedItemsDefault()
/*      */   {
/*  565 */     return getInstance().getIntSetting("number-months-approved-items-default");
/*      */   }
/*      */ 
/*      */   public static boolean ecommerce()
/*      */   {
/*  570 */     return getInstance().getBooleanSetting("ecommerce");
/*      */   }
/*      */ 
/*      */   public static boolean getCanCreateAssetVersions()
/*      */   {
/*  575 */     return getInstance().getBooleanSetting("can-create-asset-versions");
/*      */   }
/*      */ 
/*      */   public static String getRotatedSourceFormat()
/*      */   {
/*  580 */     return getInstance().getStringSetting("rotated-source-format");
/*      */   }
/*      */ 
/*      */   public static boolean getSubscription()
/*      */   {
/*  585 */     return getInstance().getBooleanSetting("subscription");
/*      */   }
/*      */ 
/*      */   public static boolean getUsernameIsEmailAddress()
/*      */   {
/*  590 */     return getInstance().getBooleanSetting("username-is-emailaddress");
/*      */   }
/*      */ 
/*      */   public static boolean getMarketingGroupsMandatory()
/*      */   {
/*  595 */     return getInstance().getBooleanSetting("marketing-groups-mandatory");
/*      */   }
/*      */ 
/*      */   public static int getRunSubscriptionActivationHourOfDay()
/*      */   {
/*  600 */     return getInstance().getIntSetting("run-subscription-activation-hour-of-day");
/*      */   }
/*      */ 
/*      */   public static String getDaysBeforeExpirySendAlerts()
/*      */   {
/*  605 */     return getInstance().getStringSetting("days-before-expiry-send-alerts");
/*      */   }
/*      */ 
/*      */   public static boolean getEcommerceUserAddressMandatory()
/*      */   {
/*  610 */     return getInstance().getBooleanSetting("ecommerce-user-address-mandatory");
/*      */   }
/*      */ 
/*      */   public static boolean getShowConditionsOnRegister()
/*      */   {
/*  615 */     return getInstance().getBooleanSetting("showConditionsOnRegister");
/*      */   }
/*      */ 
/*      */   public static boolean getShowConditionsOnLogin()
/*      */   {
/*  620 */     return getInstance().getBooleanSetting("showConditionsOnLogin");
/*      */   }
/*      */ 
/*      */   public static int getDefaultDensity()
/*      */   {
/*  625 */     return getInstance().getIntSetting("default-density");
/*      */   }
/*      */ 
/*      */   public static String getImageMagickPath()
/*      */   {
/*  630 */     return getInstance().getStringSetting("imagemagick-path");
/*      */   }
/*      */ 
/*      */   public static boolean getCmsLoginRequiresPassword()
/*      */   {
/*  635 */     return getInstance().getBooleanSetting("cms-login-requires-password");
/*      */   }
/*      */ 
/*      */   public static boolean getEnableCmsIntegration()
/*      */   {
/*  640 */     return getInstance().getBooleanSetting("enable-cms-integration");
/*      */   }
/*      */ 
/*      */   public static String getCmsRepositoryPath(int a_iRepositoryNumber)
/*      */   {
/*  657 */     String sSettingName = "cms-repository-path";
/*      */ 
/*  659 */     if (a_iRepositoryNumber > 1)
/*      */     {
/*  661 */       sSettingName = sSettingName + a_iRepositoryNumber;
/*      */     }
/*      */ 
/*  664 */     return getInstance().getStringSetting(sSettingName);
/*      */   }
/*      */ 
/*      */   public static String getCmsReturnUrl(int a_iRepositoryNumber)
/*      */   {
/*  681 */     String sSettingName = "cms-return-url";
/*      */ 
/*  683 */     if (a_iRepositoryNumber > 1)
/*      */     {
/*  685 */       sSettingName = sSettingName + a_iRepositoryNumber;
/*      */     }
/*      */ 
/*  688 */     return getInstance().getStringSetting(sSettingName);
/*      */   }
/*      */ 
/*      */   public static String getCmsReturnUrlSuffix(int a_iRepositoryNumber)
/*      */   {
/*  705 */     String sSettingName = "cms-return-url-suffix";
/*      */ 
/*  707 */     if (a_iRepositoryNumber > 1)
/*      */     {
/*  709 */       sSettingName = sSettingName + a_iRepositoryNumber;
/*      */     }
/*      */ 
/*  712 */     return getInstance().getStringSetting(sSettingName);
/*      */   }
/*      */ 
/*      */   public static String getCmsCancelUrl(int a_iRepositoryNumber)
/*      */   {
/*  717 */     String sSettingName = "cms-cancel-url";
/*      */ 
/*  719 */     if (a_iRepositoryNumber > 1)
/*      */     {
/*  721 */       sSettingName = sSettingName + a_iRepositoryNumber;
/*      */     }
/*      */ 
/*  724 */     return getInstance().getStringSetting(sSettingName);
/*      */   }
/*      */ 
/*      */   public static int getCmsAutoSubfolderCount(int a_iRepositoryNumber)
/*      */   {
/*  729 */     String sSettingName = "cms-auto-subfolder-count";
/*      */ 
/*  731 */     if (a_iRepositoryNumber > 1)
/*      */     {
/*  733 */       sSettingName = sSettingName + a_iRepositoryNumber;
/*      */     }
/*      */ 
/*  736 */     return getInstance().getIntSetting(sSettingName);
/*      */   }
/*      */ 
/*      */   public static String[] getCmsAllowedFormats()
/*      */   {
/*  752 */     if (m_asCmsAllowedFormats == null)
/*      */     {
/*  754 */       String sAllFormats = getInstance().getStringSetting("cms-allowed-formats");
/*      */ 
/*  756 */       if (sAllFormats != null)
/*      */       {
/*  758 */         m_asCmsAllowedFormats = sAllFormats.split(";");
/*      */       }
/*      */     }
/*      */ 
/*  762 */     return m_asCmsAllowedFormats;
/*      */   }
/*      */ 
/*      */   public static boolean getCmsEmbedMetadata()
/*      */   {
/*  767 */     return getInstance().getBooleanSetting("cms-embed-metadata");
/*      */   }
/*      */ 
/*      */   public static boolean getCmsMakeDestinationFilenamesUnique()
/*      */   {
/*  772 */     return getInstance().getBooleanSetting("cms-destination-filenames-unique");
/*      */   }
/*      */ 
/*      */   public static String getCmsFileTransferMethod()
/*      */   {
/*  777 */     return getInstance().getStringSetting("cms-file-transfer-method");
/*      */   }
/*      */ 
/*      */   public static String getCmsRemoteHost()
/*      */   {
/*  782 */     return getInstance().getStringSetting("cms-remote-host");
/*      */   }
/*      */ 
/*      */   public static int getCmsRemotePort()
/*      */   {
/*  787 */     return getInstance().getIntSetting("cms-remote-port");
/*      */   }
/*      */ 
/*      */   public static String getCmsRemoteUsername()
/*      */   {
/*  792 */     return getInstance().getStringSetting("cms-remote-username");
/*      */   }
/*      */ 
/*      */   public static String getCmsRemotePassword()
/*      */   {
/*  797 */     return getInstance().getStringSetting("cms-remote-password");
/*      */   }
/*      */ 
/*      */   public static String getKnownHostsFilepath()
/*      */   {
/*  802 */     return getInstance().getStringSetting("known-hosts-file");
/*      */   }
/*      */ 
/*      */   public static String getExifToolPath()
/*      */   {
/*  807 */     return getInstance().getStringSetting("exiftool-path");
/*      */   }
/*      */ 
/*      */   public static int getWatchDirectoryCount()
/*      */   {
/*  812 */     return getInstance().getIntSetting("watch-directory-count");
/*      */   }
/*      */ 
/*      */   public static String getWatchDirectory(int a_iIndex)
/*      */   {
/*  817 */     return getInstance().getStringSetting(getIndexedPropertyName("watch-directory", a_iIndex));
/*      */   }
/*      */ 
/*      */   public static boolean getWatchDirectoryAutoAdd()
/*      */   {
/*  822 */     return Boolean.valueOf(getInstance().getStringSetting("watch-directory-auto-add")).booleanValue();
/*      */   }
/*      */ 
/*      */   public static String getWatchDirectoryAccessLevels(int a_iIndex)
/*      */   {
/*  827 */     return getInstance().getStringSetting(getIndexedPropertyName("watch-directory-access-levels", a_iIndex));
/*      */   }
/*      */ 
/*      */   public static int getWatchDirectoryPeriod()
/*      */   {
/*  832 */     return getInstance().getIntSetting("watch-directory-period-millis");
/*      */   }
/*      */ 
/*      */   public static boolean getBulkUploadPopulateDefaults()
/*      */   {
/*  837 */     return Boolean.valueOf(getInstance().getStringSetting("bulk-upload-populate-defaults")).booleanValue();
/*      */   }
/*      */ 
/*      */   public static int getEmailAttachmentMaxSizeInMb()
/*      */   {
/*  842 */     return getInstance().getIntSetting("email-attachment-max-size-in-mb");
/*      */   }
/*      */ 
/*      */   public static int getVideoPreviewDuration()
/*      */   {
/*  847 */     return getInstance().getIntSetting("video-preview-duration");
/*      */   }
/*      */ 
/*      */   public static String getVideoPreviewFormat()
/*      */   {
/*  852 */     return getInstance().getStringSetting("video-preview-format");
/*      */   }
/*      */ 
/*      */   public static String getEmbeddedVideoPreviewFormat()
/*      */   {
/*  857 */     return getInstance().getStringSetting("embedded-video-preview-format");
/*      */   }
/*      */ 
/*      */   public static String getVideoFramesTempDir()
/*      */   {
/*  862 */     return getInstance().getStringSetting("video-frames-temp-dir");
/*      */   }
/*      */ 
/*      */   public static int getVideoFrameSelectionPageSize()
/*      */   {
/*  867 */     return getInstance().getIntSetting("video-frame-selection-page-size");
/*      */   }
/*      */ 
/*      */   public static String getVideoFramesSelectionInterval()
/*      */   {
/*  872 */     return getInstance().getStringSetting("video-frame-selection-interval");
/*      */   }
/*      */ 
/*      */   public static int getVideoFrameThumbnailWidth()
/*      */   {
/*  877 */     return getInstance().getIntSetting("video-frame-thumbnail-width");
/*      */   }
/*      */ 
/*      */   public static String getAudioPreviewFormat()
/*      */   {
/*  882 */     return getInstance().getStringSetting("audio-preview-format");
/*      */   }
/*      */ 
/*      */   public static String getEmbeddedAudioPreviewFormat()
/*      */   {
/*  887 */     return getInstance().getStringSetting("embedded-audio-preview-format");
/*      */   }
/*      */ 
/*      */   public static int getAudioPreviewDuration()
/*      */   {
/*  892 */     return getInstance().getIntSetting("audio-preview-duration");
/*      */   }
/*      */ 
/*      */   public static boolean getCacheLargeImage()
/*      */   {
/*  897 */     return getInstance().getBooleanSetting("cache-large-image");
/*      */   }
/*      */ 
/*      */   public static boolean getCacheUnwatermarkedLargeImage()
/*      */   {
/*  902 */     return getInstance().getBooleanSetting("cache-unwatermarked-large-image");
/*      */   }
/*      */ 
/*      */   public static int getUpdateFeaturedIndexHourOfDay()
/*      */   {
/*  907 */     return getInstance().getIntSetting("update-featured-index-time-of-day");
/*      */   }
/*      */ 
/*      */   public static String getDefaultSearchOperator()
/*      */   {
/*  912 */     return getInstance().getStringSetting("default-search-operator");
/*      */   }
/*      */ 
/*      */   public static String getBulkUploadFileSkip()
/*      */   {
/*  917 */     return getInstance().getStringSetting("bulk-upload-file-skip");
/*      */   }
/*      */ 
/*      */   public static boolean getUsePriceBands()
/*      */   {
/*  922 */     return getInstance().getBooleanSetting("price-bands");
/*      */   }
/*      */ 
/*      */   public static boolean getUseShippingCosts()
/*      */   {
/*  927 */     return getInstance().getBooleanSetting("shipping-costs");
/*      */   }
/*      */ 
/*      */   public static boolean getUseCommercialOptions()
/*      */   {
/*  932 */     return getInstance().getBooleanSetting("commercial-options");
/*      */   }
/*      */ 
/*      */   public static String getDirectDownloadFileExtensions()
/*      */   {
/*  937 */     return getInstance().getStringSetting("direct-download-file-types");
/*      */   }
/*      */ 
/*      */   public static boolean getIsDirectDownloadFileType(String sExtension)
/*      */   {
/*  949 */     return StringUtil.isInList(getDirectDownloadFileExtensions(), sExtension);
/*      */   }
/*      */ 
/*      */   public static boolean getUseCategoryExplorer()
/*      */   {
/*  955 */     return getInstance().getBooleanSetting("useCategoryExplorer");
/*      */   }
/*      */ 
/*      */   public static int getShippingCalculationType()
/*      */   {
/*  960 */     return getInstance().getIntSetting("shipping-calculation-type");
/*      */   }
/*      */ 
/*      */   public static boolean getUseBrands()
/*      */   {
/*  965 */     return getInstance().getBooleanSetting("multiple-brands");
/*      */   }
/*      */ 
/*      */   public static String getBrandParameter()
/*      */   {
/*  970 */     return getInstance().getStringSetting("brand-parameter");
/*      */   }
/*      */ 
/*      */   public static String getExportedAssetFilenameFormat()
/*      */   {
/*  975 */     return getInstance().getStringSetting("exported-asset-filename-format").trim();
/*      */   }
/*      */ 
/*      */   public static String getExportedAssetFilenameFieldDelimiter()
/*      */   {
/*  980 */     return getInstance().getStringSetting("exported-asset-filename-field-delimiter");
/*      */   }
/*      */ 
/*      */   public static String getExportedAssetFilenameDelimiterReplacement()
/*      */   {
/*  985 */     return getInstance().getStringSetting("exported-asset-filename-delimiter-replacement");
/*      */   }
/*      */ 
/*      */   public static String getImportDateFormat()
/*      */   {
/*  990 */     return getInstance().getStringSetting("import-date-format");
/*      */   }
/*      */ 
/*      */   public static String getExportDateFormat()
/*      */   {
/*  995 */     return getInstance().getStringSetting("export-date-format");
/*      */   }
/*      */ 
/*      */   public static String getApplicationUpdateBackupDir()
/*      */   {
/* 1000 */     return getInstance().getStringSetting("application-update-backup-directory");
/*      */   }
/*      */ 
/*      */   public static String getApplicationUpdateInProgressType()
/*      */   {
/* 1005 */     return getInstance().getStringSetting("updateType");
/*      */   }
/*      */ 
/*      */   public static String getApplicationUpdateBackupDirLinux()
/*      */   {
/* 1010 */     return getInstance().getStringSetting("application-update-backup-directory-linux");
/*      */   }
/*      */ 
/*      */   public static int getDeferredConversionImageAreaThreshold()
/*      */   {
/* 1015 */     return getInstance().getIntSetting("deferred-conversion-image-area-threshold");
/*      */   }
/*      */ 
/*      */   public static int getDownloadAssetBoxMaxAttempts()
/*      */   {
/* 1020 */     return getInstance().getIntSetting("download-assetbox-max-attempts");
/*      */   }
/*      */ 
/*      */   public static int getDownloadImageMaxAttempts()
/*      */   {
/* 1025 */     return getInstance().getIntSetting("download-image-max-attempts");
/*      */   }
/*      */ 
/*      */   public static int getCheckForApplicationUpdatesHourOfDay()
/*      */   {
/* 1030 */     return getInstance().getIntSetting("check-for-application-updates-hour-of-day");
/*      */   }
/*      */ 
/*      */   public static String getApplicationUpdateUrl(String a_sVersion)
/*      */   {
/* 1035 */     String sUrl = getApplicationUpdateUrl();
/* 1036 */     if (StringUtil.stringIsPopulated(a_sVersion))
/*      */     {
/* 1038 */       sUrl = sUrl + a_sVersion + "/";
/*      */     }
/* 1040 */     return sUrl;
/*      */   }
/*      */ 
/*      */   private static String getApplicationUpdateUrl()
/*      */   {
/* 1045 */     return getInstance().getStringSetting("application-update-url");
/*      */   }
/*      */ 
/*      */   public static int getDefaultNewGroupAttributeVisibilityLevel()
/*      */   {
/* 1050 */     String sVisibility = getInstance().getStringSetting("default-attribute-visibility");
/*      */ 
/* 1052 */     if ("visible".equalsIgnoreCase(sVisibility))
/*      */     {
/* 1054 */       return 2;
/*      */     }
/* 1056 */     if ("writable".equalsIgnoreCase(sVisibility))
/*      */     {
/* 1058 */       return 3;
/*      */     }
/*      */ 
/* 1061 */     return 1;
/*      */   }
/*      */ 
/*      */   public static boolean getUseIPMappings()
/*      */   {
/* 1066 */     return getInstance().getBooleanSetting("users-have-IP");
/*      */   }
/*      */ 
/*      */   public static boolean getUseURLMappings() {
/* 1070 */     return getInstance().getBooleanSetting("users-have-URL");
/*      */   }
/*      */ 
/*      */   public static String getURLMappingParameter()
/*      */   {
/* 1075 */     return getInstance().getStringSetting("url-mapping-parameter");
/*      */   }
/*      */ 
/*      */   public static boolean getShowUseDropdownBeforeDownload()
/*      */   {
/* 1080 */     return getInstance().getBooleanSetting("showUseDropdownBeforeDownload");
/*      */   }
/*      */ 
/*      */   public static String getPublishToUrl()
/*      */   {
/* 1085 */     return getInstance().getStringSetting("publish-to-url");
/*      */   }
/*      */ 
/*      */   public static int getSynchAssetsHourOfDay()
/*      */   {
/* 1090 */     return getInstance().getIntSetting("synch-assets-hour-of-day");
/*      */   }
/*      */ 
/*      */   public static boolean getAllowPublishing()
/*      */   {
/* 1095 */     return getInstance().getBooleanSetting("allow-publishing");
/*      */   }
/*      */ 
/*      */   public static int getKeywordBrowseId()
/*      */   {
/* 1100 */     return getInstance().getIntSetting("keyword-browse-page-id");
/*      */   }
/*      */ 
/*      */   public static int getRecentlyRegisteredDays()
/*      */   {
/* 1105 */     return getInstance().getIntSetting("recently-registered-days");
/*      */   }
/*      */ 
/*      */   public static boolean getNotifyUserOnUpdateDefault()
/*      */   {
/* 1110 */     return getInstance().getBooleanSetting("notify-user-on-update-default");
/*      */   }
/*      */ 
/*      */   public static boolean getShowSingleAccessLevel()
/*      */   {
/* 1115 */     return getInstance().getBooleanSetting("showSingleAccessLevel");
/*      */   }
/*      */ 
/*      */   public static boolean getUseGroupRoles()
/*      */   {
/* 1120 */     return getInstance().getBooleanSetting("use-group-roles");
/*      */   }
/*      */ 
/*      */   public static String getRolesSecurityFileDirectory()
/*      */   {
/* 1125 */     return getInstance().getStringSetting("rolesSecurityFileDir");
/*      */   }
/*      */ 
/*      */   public static boolean getGroupRelatedAssets()
/*      */   {
/* 1130 */     return getInstance().getBooleanSetting("group-related-assets");
/*      */   }
/*      */ 
/*      */   public static String getAudioPreviewSampleRate()
/*      */   {
/* 1135 */     return getInstance().getStringSetting("audio-preview-sample-rate");
/*      */   }
/*      */ 
/*      */   public static String getAudioBitRate()
/*      */   {
/* 1140 */     return getInstance().getStringSetting("audio-bit-rate");
/*      */   }
/*      */ 
/*      */   public static String getRGBProfilePath()
/*      */   {
/* 1145 */     String sPath = getInstance().getStringSetting("rgb-color-profile");
/*      */ 
/* 1148 */     if ((sPath != null) && (!sPath.startsWith("/")))
/*      */     {
/* 1151 */       sPath = getApplicationPath() + "/" + sPath;
/*      */     }
/*      */ 
/* 1154 */     return sPath;
/*      */   }
/*      */ 
/*      */   public static String getDefaultCMYKProfilePath()
/*      */   {
/* 1159 */     String sPath = getInstance().getStringSetting("default-cmyk-color-profile");
/*      */ 
/* 1162 */     if ((sPath != null) && (!sPath.startsWith("/")))
/*      */     {
/* 1165 */       sPath = getApplicationPath() + "/" + sPath;
/*      */     }
/*      */ 
/* 1168 */     return sPath;
/*      */   }
/*      */ 
/*      */   public static boolean getUploadWithApprovalCanEditOwnWithApproval()
/*      */   {
/* 1174 */     return getInstance().getBooleanSetting("upload-with-approval-can-edit-own-with-approval");
/*      */   }
/*      */ 
/*      */   public static boolean getBulkUploadUseApplet()
/*      */   {
/* 1179 */     return getInstance().getBooleanSetting("bulk-upload-use-applet");
/*      */   }
/*      */ 
/*      */   public static boolean getCreditStripEnabled()
/*      */   {
/* 1184 */     return getInstance().getBooleanSetting("credit-strip-enabled");
/*      */   }
/*      */ 
/*      */   public static String getCreditBackgroundColour()
/*      */   {
/* 1189 */     return getInstance().getStringSetting("credit-background-colour");
/*      */   }
/*      */ 
/*      */   public static boolean getDownloadFilenameUseOriginal()
/*      */   {
/* 1195 */     return getInstance().getBooleanSetting("download-filename-use-original");
/*      */   }
/*      */ 
/*      */   public static boolean getUseIdForDownloadFilenames()
/*      */   {
/* 1200 */     return getInstance().getBooleanSetting("use-id-for-download-filenames");
/*      */   }
/*      */ 
/*      */   public static String getCreditTextColour()
/*      */   {
/* 1205 */     return getInstance().getStringSetting("credit-text-colour");
/*      */   }
/*      */ 
/*      */   public static String getCreditText()
/*      */   {
/* 1210 */     return getInstance().getStringSetting("credit-text");
/*      */   }
/*      */ 
/*      */   public static int getCreditAttributeId()
/*      */   {
/* 1215 */     return getInstance().getIntSetting("credit-attribute-id");
/*      */   }
/*      */ 
/*      */   public static boolean isMarketingEnabled()
/*      */   {
/* 1220 */     return getInstance().getBooleanSetting("marketing-enabled");
/*      */   }
/*      */ 
/*      */   public static boolean isMarketingGroupsEnabled()
/*      */   {
/* 1225 */     return getInstance().getBooleanSetting("marketing-groups-enabled");
/*      */   }
/*      */ 
/*      */   public static int getForcePasswordChangeAfter()
/*      */   {
/* 1230 */     return getInstance().getIntSetting("force-password-change-after");
/*      */   }
/*      */ 
/*      */   public static int getHourToScheduleReports() {
/* 1234 */     return getInstance().getIntSetting("hour-to-schedule-reports");
/*      */   }
/*      */ 
/*      */   public static boolean isAddAssetFromExistingEnabled() {
/* 1238 */     return getInstance().getBooleanSetting("add-asset-from-existing-enabled");
/*      */   }
/*      */ 
/*      */   public static boolean isStrongValidationEnabled() {
/* 1242 */     return getInstance().getBooleanSetting("force-strong-password");
/*      */   }
/*      */ 
/*      */   public static boolean getFiltersAffectFeatured()
/*      */   {
/* 1247 */     return getInstance().getBooleanSetting("filters-affect-featured");
/*      */   }
/*      */ 
/*      */   public static long getUploadAppletMaxFilesize()
/*      */   {
/* 1252 */     return getInstance().getLongSetting("upload-applet-max-filesize");
/*      */   }
/*      */ 
/*      */   public static String getLanguageIconDirectory()
/*      */   {
/* 1257 */     return getInstance().getStringSetting("languageIconDir");
/*      */   }
/*      */ 
/*      */   public static boolean getSingleUploadUseAdvancedByDefault()
/*      */   {
/* 1262 */     return getInstance().getBooleanSetting("single-upload-use-advanced-by-default");
/*      */   }
/*      */ 
/*      */   public static boolean getBulkUploadUseFlash()
/*      */   {
/* 1267 */     return getInstance().getBooleanSetting("bulk-upload-use-flash");
/*      */   }
/*      */ 
/*      */   public static boolean getBulkUploadUseFtpOrUnc()
/*      */   {
/* 1272 */     return getInstance().getBooleanSetting("bulk-upload-use-ftp-or-unc");
/*      */   }
/*      */ 
/*      */   public static int getMaxFileUploadSize()
/*      */   {
/* 1277 */     return getInstance().getIntSetting("max-upload-file-size");
/*      */   }
/*      */ 
/*      */   public static int getVideoConversionPreviewDuration()
/*      */   {
/* 1282 */     return getInstance().getIntSetting("video-conversion-preview-duration");
/*      */   }
/*      */ 
/*      */   public static String getWmvAudioCodec()
/*      */   {
/* 1287 */     return getInstance().getStringSetting("wmv-audio-codec");
/*      */   }
/*      */ 
/*      */   public static boolean getApiRemoteDownloadDestinationFilenamesUnique()
/*      */   {
/* 1292 */     return getInstance().getBooleanSetting("api-remote-download-destination-filenames-unique");
/*      */   }
/*      */ 
/*      */   public static int getApiRemoteDownloadPort()
/*      */   {
/* 1297 */     return getInstance().getIntSetting("api-remote-download-port");
/*      */   }
/*      */ 
/*      */   public static int getApiPageSize()
/*      */   {
/* 1302 */     return getInstance().getIntSetting("api-page-size");
/*      */   }
/*      */ 
/*      */   public static boolean getHideIncompleteAssets()
/*      */   {
/* 1307 */     return getInstance().getBooleanSetting("hide-incomplete-assets");
/*      */   }
/*      */ 
/*      */   public static boolean isApplicationUpdateInProgress()
/*      */   {
/* 1312 */     return getInstance().getBooleanSetting("application-update-in-progress");
/*      */   }
/*      */ 
/*      */   public static boolean getChangeFeaturedImageOnRefresh()
/*      */   {
/* 1317 */     return getInstance().getBooleanSetting("change-featured-image-on-refresh");
/*      */   }
/*      */ 
/*      */   public static boolean getSavedSearchesEnabled()
/*      */   {
/* 1322 */     return getInstance().getBooleanSetting("saved-searches-enabled");
/*      */   }
/*      */ 
/*      */   public static int getMaxSavedSearches()
/*      */   {
/* 1327 */     return getInstance().getIntSetting("max-saved-searches");
/*      */   }
/*      */ 
/*      */   public static int getMaxRecentSearches()
/*      */   {
/* 1332 */     return getInstance().getIntSetting("max-recent-searches");
/*      */   }
/*      */ 
/*      */   public static boolean getSendAdminUserRegEmails()
/*      */   {
/* 1337 */     return getInstance().getBooleanSetting("send-admin-user-reg-emails");
/*      */   }
/*      */ 
/*      */   public static boolean getCanEditPreviousAssetVersions()
/*      */   {
/* 1342 */     return getInstance().getBooleanSetting("can-edit-asset-versions");
/*      */   }
/*      */ 
/*      */   public static int getNumberMostPopularAssets()
/*      */   {
/* 1347 */     return getInstance().getIntSetting("num-most-popular-assets");
/*      */   }
/*      */ 
/*      */   public static int getNumberLeastPopularAssets()
/*      */   {
/* 1352 */     return getInstance().getIntSetting("num-least-popular-assets");
/*      */   }
/*      */ 
/*      */   public static boolean getAuditLogEnabled()
/*      */   {
/* 1357 */     return getInstance().getBooleanSetting("enable-audit-logging");
/*      */   }
/*      */ 
/*      */   public static long getUsageStatsReindexPeriod()
/*      */   {
/* 1362 */     return getInstance().getIntSetting("usage-stats-reindex-period-minutes");
/*      */   }
/*      */ 
/*      */   public static boolean getAllowEmptyAssets()
/*      */   {
/* 1367 */     return getInstance().getBooleanSetting("allow-empty-assets");
/*      */   }
/*      */ 
/*      */   public static int getMaxReportResults()
/*      */   {
/* 1372 */     return getInstance().getIntSetting("max-report-results");
/*      */   }
/*      */ 
/*      */   public static boolean getWatermarkOptionOnDownload()
/*      */   {
/* 1377 */     return getInstance().getBooleanSetting("watermark-option-on-download");
/*      */   }
/*      */ 
/*      */   public static String getDownloadWatermarkFilename()
/*      */   {
/* 1382 */     return getInstance().getStringSetting("download-watermark-filename");
/*      */   }
/*      */ 
/*      */   public static String getDownloadWatermarkGravity()
/*      */   {
/* 1387 */     return getInstance().getStringSetting("download-watermark-gravity");
/*      */   }
/*      */ 
/*      */   public static float getDownloadWatermarkScaleFactor()
/*      */   {
/* 1392 */     return getInstance().getFloatSetting("download-watermark-scale-factor");
/*      */   }
/*      */ 
/*      */   public static float getDownloadWatermarkBrightness()
/*      */   {
/* 1397 */     return getInstance().getFloatSetting("download-watermark-brightness");
/*      */   }
/*      */ 
/*      */   public static boolean getAssetEntitiesEnabled()
/*      */   {
/* 1402 */     return getInstance().getBooleanSetting("asset-entities-enabled");
/*      */   }
/*      */ 
/*      */   public static String getCreditStripGravity()
/*      */   {
/* 1407 */     return getInstance().getStringSetting("credit-strip-gravity");
/*      */   }
/*      */ 
/*      */   public static boolean getExportRelationshipData()
/*      */   {
/* 1412 */     return getInstance().getBooleanSetting("export-relationship-data");
/*      */   }
/*      */ 
/*      */   public static boolean getAttributeTemplatesEnabled()
/*      */   {
/* 1417 */     return getInstance().getBooleanSetting("enable-attribute-templates");
/*      */   }
/*      */ 
/*      */   public static boolean getAssetRepurposingEnabled()
/*      */   {
/* 1422 */     return getInstance().getBooleanSetting("asset-repurposing-enabled");
/*      */   }
/*      */ 
/*      */   public static int getMaxImageRepurposeDimension()
/*      */   {
/* 1427 */     return getInstance().getIntSetting("max-image-repurpose-dimension");
/*      */   }
/*      */ 
/*      */   public static String getRepurposedFileBasePath()
/*      */   {
/* 1432 */     if (StringUtils.isNotEmpty(getInstance().getStringSetting("repurposed-file-base-path")))
/*      */     {
/* 1434 */       return getInstance().getStringSetting("repurposed-file-base-path");
/*      */     }
/* 1436 */     return getApplicationPath();
/*      */   }
/*      */ 
/*      */   public static String getRepurposedFileBaseUrl(HttpServletRequest a_request)
/*      */   {
/* 1441 */     if (StringUtils.isNotEmpty(getInstance().getStringSetting("repurposed-file-base-url")))
/*      */     {
/* 1443 */       return getInstance().getStringSetting("repurposed-file-base-url");
/*      */     }
/*      */ 
/* 1446 */     if (a_request != null)
/*      */     {
/* 1448 */       return ServletUtil.getApplicationUrl(a_request);
/*      */     }
/* 1450 */     return getApplicationUrl();
/*      */   }
/*      */ 
/*      */   public static String getRepurposedFileUrlPath()
/*      */   {
/* 1455 */     return getInstance().getStringSetting("repurposed-file-url-path");
/*      */   }
/*      */ 
/*      */   public static String getRepurposedSlideshowPath()
/*      */   {
/* 1460 */     return getInstance().getStringSetting("repurposed-slideshow-path");
/*      */   }
/*      */ 
/*      */   public static boolean getAutoBrowseIntoSingleCategories()
/*      */   {
/* 1465 */     return getInstance().getBooleanSetting("auto-browse-into-single-cats");
/*      */   }
/*      */ 
/*      */   public static boolean getUseFirstChildAssetAsSurrogate()
/*      */   {
/* 1470 */     return getInstance().getBooleanSetting("use-first-child-asset-as-surrogate");
/*      */   }
/*      */ 
/*      */   public static int getDefaultRepurposedImageMaxHeight()
/*      */   {
/* 1475 */     return getInstance().getIntSetting("default-repurposed-image-max-height");
/*      */   }
/*      */ 
/*      */   public static int getDefaultRepurposedImageMaxWidth()
/*      */   {
/* 1480 */     return getInstance().getIntSetting("default-repurposed-image-max-width");
/*      */   }
/*      */ 
/*      */   public static String getDefaultRepurposedImageExtension()
/*      */   {
/* 1485 */     return getInstance().getStringSetting("default-repurposed-image-extension");
/*      */   }
/*      */ 
/*      */   public static boolean getSelectFirstAssetEntityInAdvancedSearch()
/*      */   {
/* 1490 */     return getInstance().getBooleanSetting("select-first-asset-entity-in-advanced-search");
/*      */   }
/*      */ 
/*      */   public static String getKeywordAnscestorDelimiter()
/*      */   {
/* 1495 */     return getInstance().getStringSetting("keyword-anscestor-delimiter");
/*      */   }
/*      */ 
/*      */   public static boolean getKeywordAnscestorsSelectable()
/*      */   {
/* 1500 */     return getInstance().getBooleanSetting("keyword-anscestors-selectable");
/*      */   }
/*      */ 
/*      */   public static boolean getIncludeParentMetadataForSearch()
/*      */   {
/* 1506 */     return getInstance().getBooleanSetting("include-metadata-from-parents-for-search");
/*      */   }
/*      */ 
/*      */   public static boolean getIncludeParentMetadataForExport()
/*      */   {
/* 1511 */     return getInstance().getBooleanSetting("include-metadata-from-parents-for-export");
/*      */   }
/*      */ 
/*      */   public static int getDuplicateAssetSizeThreshold()
/*      */   {
/* 1517 */     return getInstance().getIntSetting("duplicate-asset-size-threshold");
/*      */   }
/*      */ 
/*      */   public static String getExportDataMargedFieldDelimiter()
/*      */   {
/* 1523 */     String sDelim = getInstance().getStringSetting("export-data-merged-field-delimiter");
/*      */ 
/* 1525 */     if (StringUtils.isEmpty(sDelim))
/*      */     {
/* 1527 */       return " ";
/*      */     }
/* 1529 */     return sDelim;
/*      */   }
/*      */ 
/*      */   public static boolean getCanNonAdminPrint()
/*      */   {
/* 1534 */     return getInstance().getBooleanSetting("can-non-admin-print");
/*      */   }
/*      */ 
/*      */   public static boolean getBulkUploadMatchDirNamesToCats()
/*      */   {
/* 1539 */     return getInstance().getBooleanSetting("bulk-upload-match-dir-names-to-cats");
/*      */   }
/*      */ 
/*      */   public static int getPrintImageSize()
/*      */   {
/* 1544 */     return getInstance().getIntSetting("print-image-size");
/*      */   }
/*      */ 
/*      */   public static int getNumberOfSearchResultsViaRss()
/*      */   {
/* 1549 */     return getInstance().getIntSetting("max-search-results-via-rss");
/*      */   }
/*      */ 
/*      */   public static boolean getAgreementsEnabled()
/*      */   {
/* 1554 */     return getInstance().getBooleanSetting("agreements-enabled");
/*      */   }
/*      */ 
/*      */   public static boolean exportAgreementData()
/*      */   {
/* 1559 */     return getInstance().getBooleanSetting("export-agreement-data");
/*      */   }
/*      */ 
/*      */   public static boolean getCanSearchAgreements()
/*      */   {
/* 1564 */     return (getInstance().getBooleanSetting("can-search-agreements")) || ("admin".equalsIgnoreCase(getInstance().getStringSetting("can-search-agreements")));
/*      */   }
/*      */ 
/*      */   public static boolean getCanSearchAgreementsAdminOnly()
/*      */   {
/* 1569 */     return "admin".equalsIgnoreCase(getInstance().getStringSetting("can-search-agreements"));
/*      */   }
/*      */ 
/*      */   public static boolean getSortByPresenceOfChildAssets()
/*      */   {
/* 1574 */     return getInstance().getBooleanSetting("sort-by-presence-of-child-assets");
/*      */   }
/*      */ 
/*      */   public static boolean getRestrictChildAssetsWithParent()
/*      */   {
/* 1579 */     return getInstance().getBooleanSetting("restrict-child-assets-with-parent");
/*      */   }
/*      */ 
/*      */   public static String getSubstituteFilePrefix()
/*      */   {
/* 1584 */     return getInstance().getStringSetting("substitute-file-prefix");
/*      */   }
/*      */ 
/*      */   public static String getDownloadImageFileAsDocumentExtensions()
/*      */   {
/* 1589 */     return getInstance().getStringSetting("download-image-as-document-extensions");
/*      */   }
/*      */ 
/*      */   public static String getSingleUploadDirectory()
/*      */   {
/* 1594 */     return getInstance().getStringSetting("single-upload-directory");
/*      */   }
/*      */ 
/*      */   public static boolean getAllowImageSubstitutes()
/*      */   {
/* 1599 */     return getInstance().getBooleanSetting("allow-image-substitutes");
/*      */   }
/*      */ 
/*      */   public static String getDownloadTints()
/*      */   {
/* 1604 */     return getInstance().getStringSetting("download-tints");
/*      */   }
/*      */ 
/*      */   public static String getRemoteUserDirectoryManagerClassName()
/*      */   {
/* 1609 */     return getInstance().getStringSetting("remote-user-directory-manager");
/*      */   }
/*      */ 
/*      */   public static boolean getMultipleFeedbackEntries()
/*      */   {
/* 1614 */     return getInstance().getBooleanSetting("multiple-comments-per-asset");
/*      */   }
/*      */ 
/*      */   public static int getMaxRating()
/*      */   {
/* 1619 */     return getInstance().getIntSetting("max-rating");
/*      */   }
/*      */ 
/*      */   public static boolean getFeedbackComments()
/*      */   {
/* 1625 */     return getInstance().getBooleanSetting("comments");
/*      */   }
/*      */ 
/*      */   public static boolean getRatingsCommentsMandatory()
/*      */   {
/* 1630 */     return getInstance().getBooleanSetting("ratings-comments-mandatory");
/*      */   }
/*      */ 
/*      */   public static boolean getFeedbackRatings()
/*      */   {
/* 1635 */     return getInstance().getBooleanSetting("ratings");
/*      */   }
/*      */ 
/*      */   public static boolean getFeedbackRatingsAreVotes()
/*      */   {
/* 1640 */     return getInstance().getBooleanSetting("ratings-are-votes");
/*      */   }
/*      */ 
/*      */   public static int getSynchronisePeriodMillis()
/*      */   {
/* 1645 */     return getInstance().getIntSetting("exchange-synchronise-period-millis");
/*      */   }
/*      */ 
/*      */   public static int getAgreementExpirySyncPeriod()
/*      */   {
/* 1650 */     return getInstance().getIntSetting("agreement-expiry-synchronisation-period-minutes");
/*      */   }
/*      */ 
/*      */   public static boolean getUserRequiresAllAccessLevelsToDeleteAsset()
/*      */   {
/* 1655 */     return getInstance().getBooleanSetting("user-requires-delete-permission-on-all");
/*      */   }
/*      */ 
/*      */   public static boolean getUserRequiresAllAccessLevelsToViewAsset()
/*      */   {
/* 1660 */     return getInstance().getBooleanSetting("user-requires-view-permission-on-all");
/*      */   }
/*      */ 
/*      */   public static boolean getEntityDefaultCategoryIdsDescriptive()
/*      */   {
/* 1665 */     return getInstance().getBooleanSetting("entity-default-category-ids-descriptive");
/*      */   }
/*      */ 
/*      */   public static String getFlashPlayerBaseUrl()
/*      */   {
/* 1670 */     return getInstance().getStringSetting("flash-player-base-url");
/*      */   }
/*      */ 
/*      */   public static String getRepurposedVideoFlashPlayerBaseUrl()
/*      */   {
/* 1675 */     return getInstance().getStringSetting("repurposed-video-flash-player-base-url");
/*      */   }
/*      */ 
/*      */   public static int getDefaultVideoPreviewFrameRate()
/*      */   {
/* 1680 */     return getInstance().getIntSetting("video-preview-frame-rate");
/*      */   }
/*      */ 
/*      */   public static String getDefaultVideoPreviewBitrate()
/*      */   {
/* 1685 */     return getInstance().getStringSetting("video-preview-bitrate");
/*      */   }
/*      */ 
/*      */   public static boolean getAllowSearchByCompleteness()
/*      */   {
/* 1690 */     return getInstance().getBooleanSetting("allow-search-by-completeness");
/*      */   }
/*      */ 
/*      */   public static boolean getShowSensitivityFields()
/*      */   {
/* 1695 */     return getInstance().getBooleanSetting("show-sensitivity-fields");
/*      */   }
/*      */ 
/*      */   public static boolean getUserDrivenSortingEnabled()
/*      */   {
/* 1700 */     return getInstance().getBooleanSetting("user-driven-sorting-enabled");
/*      */   }
/*      */ 
/*      */   public static boolean getUserDrivenBrowseSortingEnabled()
/*      */   {
/* 1705 */     return getInstance().getBooleanSetting("user-driven-browse-sorting-enabled");
/*      */   }
/*      */ 
/*      */   public static boolean getShowMappingDirection()
/*      */   {
/* 1710 */     return getInstance().getBooleanSetting("show-mapping-direction");
/*      */   }
/*      */ 
/*      */   public static boolean getSearchBuilderIsDefault()
/*      */   {
/* 1715 */     return getInstance().getBooleanSetting("search-builder-default");
/*      */   }
/*      */ 
/*      */   public static int getCategoryCountRefreshPeriod()
/*      */   {
/* 1720 */     return getInstance().getIntSetting("category-count-refresh-period-minutes");
/*      */   }
/*      */ 
/*      */   public static int getMinimumCategoryCountRefreshSize()
/*      */   {
/* 1725 */     return getInstance().getIntSetting("minimum-category-count-refresh-size");
/*      */   }
/*      */ 
/*      */   public static boolean getWorkflowUsersWithUpdateCanAddAssetsToWorkflow()
/*      */   {
/* 1730 */     return getInstance().getBooleanSetting("workflow-users-with-update-can-add-assets-to-workflow");
/*      */   }
/*      */ 
/*      */   public static boolean getEnableUnsubmittedAssets()
/*      */   {
/* 1735 */     return getInstance().getBooleanSetting("workflow-enable-unsubmitted-assets");
/*      */   }
/*      */ 
/*      */   public static int getSecondsToSleepBetweenEmails()
/*      */   {
/* 1740 */     return getInstance().getIntSetting("seconds-to-sleep-between-emails");
/*      */   }
/*      */ 
/*      */   public static int getNewsMaxCharacters()
/*      */   {
/* 1745 */     return getInstance().getIntSetting("news-max-characters");
/*      */   }
/*      */ 
/*      */   public static int getNewsMaxItems()
/*      */   {
/* 1750 */     return getInstance().getIntSetting("news-max-items");
/*      */   }
/*      */ 
/*      */   public static boolean getCanRestrictAssets()
/*      */   {
/* 1755 */     return getInstance().getBooleanSetting("can-restrict-assets");
/*      */   }
/*      */ 
/*      */   public static boolean getHideRestrictedImages()
/*      */   {
/* 1760 */     return getInstance().getBooleanSetting("hide-restricted-asset-images");
/*      */   }
/*      */ 
/*      */   public static boolean getStorageDevicesLocked()
/*      */   {
/* 1765 */     return getInstance().getBooleanSetting("storage-device-edit-lock");
/*      */   }
/*      */ 
/*      */   public static boolean getDownloadRemoteStorageDirectly()
/*      */   {
/* 1770 */     return getInstance().getBooleanSetting("download-remote-storage-directly");
/*      */   }
/*      */ 
/*      */   public static int getFrameRate(String a_sLevel, String a_sFormat)
/*      */   {
/* 1775 */     int iFR = getInstance().getIntSetting("frame-rate-" + a_sLevel + "-" + a_sFormat);
/* 1776 */     if (iFR > 0)
/*      */     {
/* 1778 */       return iFR;
/*      */     }
/* 1780 */     return getInstance().getIntSetting("frame-rate-" + a_sLevel + "-default");
/*      */   }
/*      */ 
/*      */   public static int getAudioSampleRate(String a_sLevel, String a_sFormat)
/*      */   {
/* 1785 */     int iFR = getInstance().getIntSetting("audio-rate-" + a_sLevel + "-" + a_sFormat);
/* 1786 */     if (iFR > 0)
/*      */     {
/* 1788 */       return iFR;
/*      */     }
/* 1790 */     return getInstance().getIntSetting("audio-rate-" + a_sLevel + "-default");
/*      */   }
/*      */ 
/*      */   public static String getSavedSearchImagesDir()
/*      */   {
/* 1795 */     return getInstance().getStringSetting("saved-search-images-dir");
/*      */   }
/*      */ 
/*      */   public static int getSavedSearchAlertPeriod()
/*      */   {
/* 1800 */     return getInstance().getIntSetting("saved-search-alert-period");
/*      */   }
/*      */ 
/*      */   public static boolean getBrandTemplatesEnabled()
/*      */   {
/* 1805 */     return getInstance().getBooleanSetting("brand-templates-enabled");
/*      */   }
/*      */ 
/*      */   public static String getBrandTemplateExtensions()
/*      */   {
/* 1810 */     return getInstance().getStringSetting("brand-template-extensions");
/*      */   }
/*      */ 
/*      */   public static boolean isBrandTemplateExtension(String a_sExtension)
/*      */   {
/* 1820 */     return StringUtil.isInList(getBrandTemplateExtensions(), a_sExtension);
/*      */   }
/*      */ 
/*      */   public static boolean fileNameHasBrandTemplateExtension(String a_fileName)
/*      */   {
/* 1825 */     String sExtension = FileUtil.getSuffix(a_fileName);
/* 1826 */     return isBrandTemplateExtension(sExtension);
/*      */   }
/*      */ 
/*      */   public static boolean getFileTransferEnabled()
/*      */   {
/* 1831 */     return getInstance().getBooleanSetting("file-transfer-enabled");
/*      */   }
/*      */ 
/*      */   public static boolean getAdditionalUserApprovalEnabled()
/*      */   {
/* 1836 */     return getInstance().getBooleanSetting("additional-user-approval-enabled");
/*      */   }
/*      */ 
/*      */   public static int getBulkUploadConcurrencyCount()
/*      */   {
/* 1841 */     return getInstance().getIntSetting("bulk-upload-concurrency-count");
/*      */   }
/*      */ 
/*      */   public static int getThumbnailGenerationConcurrencyCount()
/*      */   {
/* 1846 */     return getInstance().getIntSetting("thumbnail-generation-concurrency-count");
/*      */   }
/*      */ 
/*      */   public static String getStaticResourcePathSuffix()
/*      */   {
/* 1851 */     return getInstance().getStringSetting("static-resource-path-suffix");
/*      */   }
/*      */ 
/*      */   public static String getLegacyThumbnailDirectory()
/*      */   {
/* 1856 */     return getInstance().getStringSetting("legacy-thumbnail-directory");
/*      */   }
/*      */ 
/*      */   public static String getLegacyThumbnailSuffix()
/*      */   {
/* 1861 */     return getInstance().getStringSetting("legacy-thumbnail-suffix");
/*      */   }
/*      */ 
/*      */   public static boolean getLegacyThumbnailUseForLarge()
/*      */   {
/* 1866 */     return getInstance().getBooleanSetting("legacy-thumbail-use-for-large");
/*      */   }
/*      */ 
/*      */   public static int getRelatedAssetsMaxForView()
/*      */   {
/* 1871 */     return getInstance().getIntSetting("related-assets-max-for-view");
/*      */   }
/*      */ 
/*      */   public static String getFirstAttributePosition()
/*      */   {
/* 1876 */     return getInstance().getStringSetting("first-attribute-position");
/*      */   }
/*      */ 
/*      */   public static boolean getExportExternalDictionaryAsString()
/*      */   {
/* 1881 */     return getInstance().getBooleanSetting("export-external-dictionary-as-string");
/*      */   }
/*      */ 
/*      */   public static boolean getSpecifyEntityIdInPublish()
/*      */   {
/* 1886 */     return getInstance().getBooleanSetting("specify-entity-id-in-publish");
/*      */   }
/*      */ 
/*      */   public static String getPublishOrignalFileDestinationFormat()
/*      */   {
/* 1892 */     return getInstance().getStringSetting("publish-original-file-destination-format");
/*      */   }
/*      */ 
/*      */   public static int getPublishOrignalFileDestinationMaxHeight()
/*      */   {
/* 1897 */     return getInstance().getIntSetting("publish-original-file-destination-max-height");
/*      */   }
/*      */ 
/*      */   public static int getPublishOrignalFileDestinationMaxWidth()
/*      */   {
/* 1902 */     return getInstance().getIntSetting("publish-original-file-destination-max-width");
/*      */   }
/*      */ 
/*      */   public static boolean getReembedMetadataOnSave()
/*      */   {
/* 1907 */     return getInstance().getBooleanSetting("reembed-metadata-on-save");
/*      */   }
/*      */ 
/*      */   public static String getEmbeddedGPSCoordinateFormat()
/*      */   {
/* 1912 */     return getInstance().getStringSetting("embedded-gps-coordinate-format");
/*      */   }
/*      */ 
/*      */   public static String getAdditionalApprovalDomain()
/*      */   {
/* 1918 */     return getInstance().getStringSetting("additional-approval-domain");
/*      */   }
/*      */ 
/*      */   public static int getPostscriptInputDensity()
/*      */   {
/* 1923 */     return getInstance().getIntSetting("postscript-input-density");
/*      */   }
/*      */ 
/*      */   public static boolean getOrgUnitAdminCanAccessAllUsers()
/*      */   {
/* 1928 */     return getInstance().getBooleanSetting("org-unit-admin-can-access-all-users");
/*      */   }
/*      */ 
/*      */   public static int getLightboxMaxDownloadSize()
/*      */   {
/* 1933 */     return getInstance().getIntSetting("lightbox-max-download-size");
/*      */   }
/*      */ 
/*      */   public static String getOptionsNumSearchResults()
/*      */   {
/* 1938 */     return getInstance().getStringSetting("options-num-search-results");
/*      */   }
/*      */ 
/*      */   public static int getDuplicateAssetReportLimit()
/*      */   {
/* 1943 */     return getInstance().getIntSetting("duplicate-asset-report-limit");
/*      */   }
/*      */ 
/*      */   public static int getAddAllToAssetBoxLimit()
/*      */   {
/* 1948 */     return getInstance().getIntSetting("add-all-to-assetbox-limit");
/*      */   }
/*      */ 
/*      */   public static int getVerifyUsersInactiveFor()
/*      */   {
/* 1953 */     return getInstance().getIntSetting("verify-users-inactive-for");
/*      */   }
/*      */ 
/*      */   public static int getVerifyUsersReactivationPeriod()
/*      */   {
/* 1958 */     return getInstance().getIntSetting("verify-users-reactivation-period");
/*      */   }
/*      */ 
/*      */   public static int getImageSizeConsideredLowRes()
/*      */   {
/* 1963 */     return getInstance().getIntSetting("image-size-considered-low-res");
/*      */   }
/*      */ 
/*      */   public static boolean getAutoCompleteEnabled()
/*      */   {
/* 1968 */     return getInstance().getBooleanSetting("auto-complete-enabled");
/*      */   }
/*      */ 
/*      */   public static boolean getAutoCompleteIncludeFileKeywords()
/*      */   {
/* 1973 */     return getInstance().getBooleanSetting("auto-complete-include-file-keywords");
/*      */   }
/*      */ 
/*      */   public static int getAutoCompleteMaxResults()
/*      */   {
/* 1978 */     return getInstance().getIntSetting("auto-complete-max-results");
/*      */   }
/*      */ 
/*      */   public static boolean getDownloadFromFilesystem()
/*      */   {
/* 1983 */     return getInstance().getBooleanSetting("download-from-filesystem");
/*      */   }
/*      */ 
/*      */   public static String getSearchResultsTransformationDirectory()
/*      */   {
/* 1988 */     return getInstance().getStringSetting("search-results-transformation-directory");
/*      */   }
/*      */ 
/*      */   public static boolean getRepurposedVideoMaintainPreview()
/*      */   {
/* 1993 */     return getInstance().getBooleanSetting("repurposed-video-maintain-preview");
/*      */   }
/*      */ 
/*      */   public static String getCheckPermissionDirs()
/*      */   {
/* 1998 */     return getInstance().getStringSetting("check-permission-dirs");
/*      */   }
/*      */ 
/*      */   public static String getCheckPermissionDirsIgnore()
/*      */   {
/* 2003 */     return getInstance().getStringSetting("check-permission-dirs-ignore");
/*      */   }
/*      */ 
/*      */   public static int getMaxVarcharAttributeLength()
/*      */   {
/* 2008 */     return getInstance().getIntSetting("max-varchar-attribute-length");
/*      */   }
/*      */ 
/*      */   public static int getMaxPromotedImageResults()
/*      */   {
/* 2013 */     return getInstance().getIntSetting("max-promoted-image-results");
/*      */   }
/*      */ 
/*      */   public static boolean getCategoryImageUsedForLogo()
/*      */   {
/* 2018 */     return getInstance().getBooleanSetting("category-image-used-for-logo");
/*      */   }
/*      */ 
/*      */   public static int getLogoHeight()
/*      */   {
/* 2023 */     return getInstance().getIntSetting("logo-image-height");
/*      */   }
/*      */ 
/*      */   public static int getLogoWidth()
/*      */   {
/* 2028 */     return getInstance().getIntSetting("logo-image-width");
/*      */   }
/*      */ 
/*      */   public static int getRepurposedSlideshowRefreshPeriod()
/*      */   {
/* 2033 */     return getInstance().getIntSetting("repurposed-slideshow-refresh-period");
/*      */   }
/*      */ 
/*      */   public static String getExportFileExtension()
/*      */   {
/* 2038 */     return getInstance().getStringSetting("export-file-extension");
/*      */   }
/*      */ 
/*      */   public static Boolean getRemoveIdFromUploadFilenames()
/*      */   {
/* 2043 */     String sValue = getInstance().getStringSetting("remove-id-from-uploaded-filename");
/* 2044 */     if (StringUtils.isEmpty(sValue))
/*      */     {
/* 2046 */       return null;
/*      */     }
/* 2048 */     return Boolean.valueOf(sValue);
/*      */   }
/*      */ 
/*      */   public static DiscardAlphaChannel getDiscardAlphaChannel()
/*      */   {
/* 2053 */     String sValue = getInstance().getStringSetting("discard-alpha-channel-on-convert").trim();
/*      */ 
/* 2055 */     if (sValue.equalsIgnoreCase("yes"))
/*      */     {
/* 2057 */       return DiscardAlphaChannel.YES;
/*      */     }
/* 2059 */     if (sValue.equalsIgnoreCase("selection"))
/*      */     {
/* 2061 */       return DiscardAlphaChannel.SELECTION;
/*      */     }
/* 2063 */     return DiscardAlphaChannel.NO;
/*      */   }
/*      */ 
/*      */   public static String getLargeAssetToggle()
/*      */   {
/* 2068 */     return getInstance().getStringSetting("large-asset-toggle");
/*      */   }
/*      */ 
/*      */   public static boolean getMapWatchSubdirectoriesToAccessLevels()
/*      */   {
/* 2073 */     return getInstance().getBooleanSetting("map-watch-subdirectories-to-access-levels");
/*      */   }
/*      */ 
/*      */   public static boolean getMapWatchSubdirectoriesToCategories()
/*      */   {
/* 2078 */     return getInstance().getBooleanSetting("map-watch-subdirectories-to-categories");
/*      */   }
/*      */ 
/*      */   public static int getMaxGroupsPerPage()
/*      */   {
/* 2083 */     return getInstance().getIntSetting("max-groups-per-page");
/*      */   }
/*      */ 
/*      */   public static int getGroupAdminMaxAccessLevels()
/*      */   {
/* 2088 */     return getInstance().getIntSetting("group-admin-max-access-levels");
/*      */   }
/*      */ 
/*      */   public static boolean getAutomaticBrandRegistration()
/*      */   {
/* 2093 */     return getInstance().getBooleanSetting("automatic-brand-registration");
/*      */   }
/*      */ 
/*      */   public static String getLocalUserEmailDomain()
/*      */   {
/* 2098 */     return getInstance().getStringSetting("local-registration-email-domain");
/*      */   }
/*      */ 
/*      */   public static long getDirectImageLinkPermissionGroup()
/*      */   {
/* 2103 */     return getInstance().getLongSetting("direct-image-link-permission-group");
/*      */   }
/*      */ 
/*      */   public static String getDirectImageLinkCacheDirectory()
/*      */   {
/* 2108 */     return getInstance().getStringSetting("direct-image-link-cache-dir");
/*      */   }
/*      */ 
/*      */   public static long getDefaultLocalGroupId()
/*      */   {
/* 2113 */     return getInstance().getIntSetting("default-local-user-group-id");
/*      */   }
/*      */ 
/*      */   public static long getDefaultExternalGroupId()
/*      */   {
/* 2118 */     return getInstance().getIntSetting("default-external-user-group-id");
/*      */   }
/*      */ 
/*      */   public static boolean getRequiresEndorsedLibs()
/*      */   {
/* 2123 */     return getInstance().getBooleanSetting("requires-endorsed-libs");
/*      */   }
/*      */ 
/*      */   public static int getDefaultRepurposedSlideshowHeight()
/*      */   {
/* 2128 */     return getInstance().getIntSetting("default-repurposed-slideshow-height");
/*      */   }
/*      */ 
/*      */   public static int getDefaultRepurposedSlideshowWidth()
/*      */   {
/* 2133 */     return getInstance().getIntSetting("default-repurposed-slideshow-width");
/*      */   }
/*      */ 
/*      */   public static String getThirdPartyLicenseDirectory()
/*      */   {
/* 2138 */     return getInstance().getStringSetting("third-party-license-directory");
/*      */   }
/*      */ 
/*      */   public static boolean getCanSelectMultipleUsageTypes()
/*      */   {
/* 2143 */     return getInstance().getBooleanSetting("can-select-multiple-usage-types");
/*      */   }
/*      */ 
/*      */   public static boolean getEcommerceOfflineForceLogin()
/*      */   {
/* 2148 */     return getInstance().getBooleanSetting("ecommerce-offline-force-login");
/*      */   }
/*      */ 
/*      */   public static boolean getAdvancedViewingEnabled()
/*      */   {
/* 2153 */     return getInstance().getBooleanSetting("advanced-viewing-enabled");
/*      */   }
/*      */ 
/*      */   public static String getCmsAttributes() {
/* 2157 */     return getInstance().getStringSetting("cms-attributes");
/*      */   }
/*      */ 
/*      */   public static String getCmsRepositoryUrl()
/*      */   {
/* 2162 */     return getInstance().getStringSetting("cms-repository-url");
/*      */   }
/*      */ 
/*      */   public static int getCopyAssetChildChoiceLimit()
/*      */   {
/* 2167 */     return getInstance().getIntSetting("copy-asset-child-choice-limit");
/*      */   }
/*      */ 
/*      */   public static String getPluginClasses()
/*      */   {
/* 2172 */     return getInstance().getStringSetting("plugin-classes");
/*      */   }
/*      */ 
/*      */   public static String getDisplayDateTimeFormat()
/*      */   {
/* 2177 */     return getInstance().getStringSetting("display-datetime-format");
/*      */   }
/*      */ 
/*      */   public static boolean getPopulateNameFromFilenameRemoveExt()
/*      */   {
/* 2182 */     return getInstance().getBooleanSetting("populate-name-from-filename-remove-ext");
/*      */   }
/*      */ 
/*      */   public static String getAssetEntityBrowsePanelBreakdown()
/*      */   {
/* 2187 */     return getInstance().getStringSetting("asset-entity-browse-panel-breakdown");
/*      */   }
/*      */ 
/*      */   public static boolean getListViewIsDefault()
/*      */   {
/* 2192 */     return getInstance().getBooleanSetting("list-view-default");
/*      */   }
/*      */ 
/*      */   public static boolean getCategoryExtensionAssetsEnabled()
/*      */   {
/* 2197 */     return getInstance().getBooleanSetting("category-extension-assets-enabled");
/*      */   }
/*      */ 
/*      */   public static int getNumberDaysMyUploads()
/*      */   {
/* 2202 */     return getInstance().getIntSetting("number-days-my-uploads");
/*      */   }
/*      */ 
/*      */   public static long getOrgUnitParentALId()
/*      */   {
/* 2207 */     return getInstance().getLongSetting("org-unit-parent-access-level-id");
/*      */   }
/*      */ 
/*      */   public static boolean getDefaultAccessLevelsToAssignable()
/*      */   {
/* 2212 */     return getInstance().getBooleanSetting("default-new-accesslevels-to-assignable");
/*      */   }
/*      */ 
/*      */   public static boolean getReplaceExtendedCategoryNamesOnImport()
/*      */   {
/* 2217 */     return getInstance().getBooleanSetting("replace-extended-category-names-on-import");
/*      */   }
/*      */ 
/*      */   public static boolean getOrgUnitAdminsCanExport()
/*      */   {
/* 2222 */     return getInstance().getBooleanSetting("org-unit-admins-can-export");
/*      */   }
/*      */ 
/*      */   public static boolean getSlideshowEnabled()
/*      */   {
/* 2227 */     return getInstance().getBooleanSetting("enable-slideshow");
/*      */   }
/*      */ 
/*      */   public static boolean getShowCaptchaOnRegistration()
/*      */   {
/* 2232 */     return getInstance().getBooleanSetting("show-captcha-on-registration");
/*      */   }
/*      */ 
/*      */   public static boolean getSlideshowRepurposingEnabled()
/*      */   {
/* 2237 */     return getInstance().getBooleanSetting("slideshow-repurposing-enabled");
/*      */   }
/*      */ 
/*      */   public static int getDefaultHomepageSlideshowHeight()
/*      */   {
/* 2242 */     return getInstance().getIntSetting("default-homepage-slideshow-height");
/*      */   }
/*      */ 
/*      */   public static int getDefaultHomepageSlideshowWidth()
/*      */   {
/* 2247 */     return getInstance().getIntSetting("default-homepage-slideshow-width");
/*      */   }
/*      */ 
/*      */   public static enum DiscardAlphaChannel
/*      */   {
/*   54 */     YES, 
/*   55 */     NO, 
/*   56 */     SELECTION;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.constant.AssetBankSettings
 * JD-Core Version:    0.6.0
 */