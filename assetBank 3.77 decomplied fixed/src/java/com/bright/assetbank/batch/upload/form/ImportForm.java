/*     */ package com.bright.assetbank.batch.upload.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*     */ import com.bright.framework.common.bean.BrightNaturalNumber;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class ImportForm extends AssetForm
/*     */   implements ImageConstants, AssetBankConstants
/*     */ {
/*  55 */   private String m_sZipPath = null;
/*  56 */   private String m_sDirectoryPath = null;
/*  57 */   private int m_iTopLevelFileCount = 0;
/*  58 */   private int m_iTopLevelFileCountIncludeZips = 0;
/*  59 */   private int m_iCurrentTemplateId = 0;
/*     */ 
/*  62 */   private Vector m_vecMessages = null;
/*  63 */   private boolean m_bIsImportInProgress = false;
/*     */ 
/*  65 */   private List<String> m_directoryList = null;
/*  66 */   private List<String> m_zipFileList = null;
/*  67 */   private List<String> m_allTopLevelFilesList = null;
/*  68 */   private Vector m_vecTemplates = null;
/*  69 */   private String m_sFTPPath = null;
/*  70 */   private String m_sFTPDir = null;
/*  71 */   private String m_sBulkUploadDirectory = null;
/*  72 */   private String m_sImportFileOption = null;
/*  73 */   private boolean m_bLinkAssets = false;
/*  74 */   private boolean m_bUseFilenameAsTitle = false;
/*  75 */   private boolean m_bRemoveIdFromFilename = false;
/*  76 */   private boolean m_bSuccess = false;
/*     */ 
/*  78 */   private Vector m_assetEntities = null;
/*  79 */   private boolean m_bCanRelateAssets = true;
/*     */ 
/*  81 */   private Filter m_template = null;
/*  82 */   private boolean m_bNoUploadableTypes = false;
/*     */ 
/*  84 */   private boolean m_bDeferThumbnailGeneration = false;
/*  85 */   private boolean m_bBulkUploadFirst = false;
/*     */ 
/*  87 */   private Vector m_formatOptions = null;
/*  88 */   private long m_chosenFileFormat = 0L;
/*     */ 
/*  90 */   private boolean m_bEntitiesWithMatchAttributeExist = false;
/*  91 */   private boolean m_bChildEntitiesWithMatchAttributeExist = false;
/*  92 */   private boolean m_bImportNewAssets = false;
/*  93 */   private boolean m_bImportFilesToExistingAssets = false;
/*  94 */   private boolean m_bImportChildAssets = false;
/*  95 */   private boolean m_bAddPlaceholders = false;
/*     */ 
/*  97 */   private boolean m_bIdAppearsInFilenames = false;
/*     */ 
/* 100 */   private BrightNaturalNumber m_numAssetsToAdd = null;
/*     */   int m_iNumIncompleteAssets;
/*     */ 
/*     */   public ImportForm()
/*     */   {
/* 107 */     this.m_numAssetsToAdd = new BrightNaturalNumber();
/*     */   }
/*     */ 
/*     */   public void validateNumberPlaceholdersToAdd(HttpServletRequest a_request, ListManager a_listManager, UserProfile a_userProfile, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 121 */     if ((!this.m_numAssetsToAdd.getIsFormNumberEntered()) || (!this.m_numAssetsToAdd.processFormData()) || (this.m_numAssetsToAdd.getNumber() < 1L))
/*     */     {
/* 123 */       addError(a_listManager.getListItem(a_dbTransaction, "failedValidationNumPlaceholders", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setBulkUploadDirectory(String a_sBulkUploadDirectory)
/*     */   {
/* 131 */     this.m_sBulkUploadDirectory = a_sBulkUploadDirectory;
/*     */   }
/*     */ 
/*     */   public String getBulkUploadDirectory()
/*     */   {
/* 136 */     return this.m_sBulkUploadDirectory;
/*     */   }
/*     */ 
/*     */   public void setDirectoryPath(String a_sDirectoryPath)
/*     */   {
/* 141 */     this.m_sDirectoryPath = a_sDirectoryPath;
/*     */   }
/*     */ 
/*     */   public String getDirectoryPath()
/*     */   {
/* 146 */     return this.m_sDirectoryPath;
/*     */   }
/*     */ 
/*     */   public void setMessages(Vector a_vecMessages)
/*     */   {
/* 151 */     this.m_vecMessages = a_vecMessages;
/*     */   }
/*     */ 
/*     */   public Vector getMessages()
/*     */   {
/* 156 */     return this.m_vecMessages;
/*     */   }
/*     */ 
/*     */   public void setIsImportInProgress(boolean a_bIsImportInProgress)
/*     */   {
/* 161 */     this.m_bIsImportInProgress = a_bIsImportInProgress;
/*     */   }
/*     */ 
/*     */   public boolean getIsImportInProgress()
/*     */   {
/* 166 */     return this.m_bIsImportInProgress;
/*     */   }
/*     */ 
/*     */   public boolean validate(HttpServletRequest a_request, UserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager)
/*     */     throws Bn2Exception
/*     */   {
/* 184 */     if ((getImportFileOption() == null) || (getImportFileOption().trim().length() == 0))
/*     */     {
/* 186 */       addError(a_listManager.getListItem(a_dbTransaction, "failedValidationZipDir", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 190 */     if ((getImportFileOption().equals("zip_files")) && ((getZipPath() == null) || (getZipPath().trim().length() == 0)))
/*     */     {
/* 193 */       addError(a_listManager.getListItem(a_dbTransaction, "failedValidationZipDir", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 197 */     if ((getImportFileOption().equals("directory_files")) && ((getDirectoryPath() == null) || (getDirectoryPath().trim().length() == 0)))
/*     */     {
/* 199 */       addError(a_listManager.getListItem(a_dbTransaction, "failedValidationZipDir", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 202 */     return getHasErrors();
/*     */   }
/*     */ 
/*     */   public String getZipPath()
/*     */   {
/* 207 */     return this.m_sZipPath;
/*     */   }
/*     */ 
/*     */   public void setZipPath(String a_sZipPath)
/*     */   {
/* 212 */     this.m_sZipPath = a_sZipPath;
/*     */   }
/*     */ 
/*     */   public List<String> getDirectoryList()
/*     */   {
/* 217 */     return this.m_directoryList;
/*     */   }
/*     */ 
/*     */   public void setDirectoryList(List<String> a_directoryList)
/*     */   {
/* 222 */     this.m_directoryList = a_directoryList;
/*     */   }
/*     */ 
/*     */   public List<String> getZipFileList()
/*     */   {
/* 227 */     return this.m_zipFileList;
/*     */   }
/*     */ 
/*     */   public void setZipFileList(List<String> a_zipFileList)
/*     */   {
/* 232 */     this.m_zipFileList = a_zipFileList;
/*     */   }
/*     */ 
/*     */   public String getFTPPath()
/*     */   {
/* 237 */     return this.m_sFTPPath;
/*     */   }
/*     */ 
/*     */   public void setFTPPath(String a_sPath)
/*     */   {
/* 242 */     this.m_sFTPPath = a_sPath;
/*     */   }
/*     */ 
/*     */   public String getFTPDir()
/*     */   {
/* 247 */     return this.m_sFTPDir;
/*     */   }
/*     */ 
/*     */   public void setFTPDir(String a_sDir)
/*     */   {
/* 252 */     this.m_sFTPDir = a_sDir;
/*     */   }
/*     */ 
/*     */   public int getTopLevelFileCount()
/*     */   {
/* 258 */     return this.m_iTopLevelFileCount;
/*     */   }
/*     */ 
/*     */   public void setTopLevelFileCount(int a_iTopLevelFileCount)
/*     */   {
/* 263 */     this.m_iTopLevelFileCount = a_iTopLevelFileCount;
/*     */   }
/*     */ 
/*     */   public int getTopLevelFileCountIncludeZips()
/*     */   {
/* 268 */     return this.m_iTopLevelFileCountIncludeZips;
/*     */   }
/*     */ 
/*     */   public void setTopLevelFileCountIncludeZips(int a_iTopLevelFileCountIncludeZips)
/*     */   {
/* 273 */     this.m_iTopLevelFileCountIncludeZips = a_iTopLevelFileCountIncludeZips;
/*     */   }
/*     */ 
/*     */   public void setLinkAssets(boolean a_bLinkAssets) {
/* 277 */     this.m_bLinkAssets = a_bLinkAssets;
/*     */   }
/*     */ 
/*     */   public boolean getLinkAssets()
/*     */   {
/* 282 */     return this.m_bLinkAssets;
/*     */   }
/*     */ 
/*     */   public boolean getUseFilenameAsTitle()
/*     */   {
/* 287 */     return this.m_bUseFilenameAsTitle;
/*     */   }
/*     */ 
/*     */   public void setUseFilenameAsTitle(boolean a_sUseFilenameAsTitle)
/*     */   {
/* 292 */     this.m_bUseFilenameAsTitle = a_sUseFilenameAsTitle;
/*     */   }
/*     */ 
/*     */   public void setSuccess(boolean a_bSuccess)
/*     */   {
/* 297 */     this.m_bSuccess = a_bSuccess;
/*     */   }
/*     */ 
/*     */   public boolean getSuccess()
/*     */   {
/* 302 */     return this.m_bSuccess;
/*     */   }
/*     */ 
/*     */   public void setImportFileOption(String a_sImportFileOption)
/*     */   {
/* 307 */     this.m_sImportFileOption = a_sImportFileOption;
/*     */   }
/*     */ 
/*     */   public String getImportFileOption()
/*     */   {
/* 312 */     return this.m_sImportFileOption;
/*     */   }
/*     */ 
/*     */   public Vector getAssetEntities()
/*     */   {
/* 317 */     return this.m_assetEntities;
/*     */   }
/*     */ 
/*     */   public void setAssetEntities(Vector assetEntities)
/*     */   {
/* 322 */     this.m_assetEntities = assetEntities;
/*     */   }
/*     */ 
/*     */   public boolean getCanRelateAssets()
/*     */   {
/* 327 */     return this.m_bCanRelateAssets;
/*     */   }
/*     */ 
/*     */   public void setCanRelateAssets(boolean canRelateAssets)
/*     */   {
/* 332 */     this.m_bCanRelateAssets = canRelateAssets;
/*     */   }
/*     */ 
/*     */   public Vector getTemplates()
/*     */   {
/* 337 */     return this.m_vecTemplates;
/*     */   }
/*     */ 
/*     */   public void setTemplates(Vector a_vecTemplates)
/*     */   {
/* 342 */     this.m_vecTemplates = a_vecTemplates;
/*     */   }
/*     */ 
/*     */   public int getCurrentTemplateId()
/*     */   {
/* 347 */     return this.m_iCurrentTemplateId;
/*     */   }
/*     */ 
/*     */   public void setCurrentTemplateId(int a_iCurrentTemplateId) {
/* 351 */     this.m_iCurrentTemplateId = a_iCurrentTemplateId;
/*     */   }
/*     */ 
/*     */   public Filter getTemplate()
/*     */   {
/* 356 */     return this.m_template;
/*     */   }
/*     */ 
/*     */   public void setTemplate(Filter a_template) {
/* 360 */     this.m_template = a_template;
/*     */   }
/*     */ 
/*     */   public boolean isNoUploadableTypes()
/*     */   {
/* 365 */     return this.m_bNoUploadableTypes;
/*     */   }
/*     */ 
/*     */   public void setNoUploadableTypes(boolean noUploadableTypes)
/*     */   {
/* 370 */     this.m_bNoUploadableTypes = noUploadableTypes;
/*     */   }
/*     */ 
/*     */   public boolean getDeferThumbnailGeneration()
/*     */   {
/* 375 */     return this.m_bDeferThumbnailGeneration;
/*     */   }
/*     */ 
/*     */   public void setDeferThumbnailGeneration(boolean a_bDeferThumbnailGeneration)
/*     */   {
/* 380 */     this.m_bDeferThumbnailGeneration = a_bDeferThumbnailGeneration;
/*     */   }
/*     */ 
/*     */   public Vector getFormatOptions()
/*     */   {
/* 385 */     return this.m_formatOptions;
/*     */   }
/*     */ 
/*     */   public void setFormatOptions(Vector options)
/*     */   {
/* 390 */     this.m_formatOptions = options;
/*     */   }
/*     */ 
/*     */   public long getChosenFileFormat()
/*     */   {
/* 395 */     return this.m_chosenFileFormat;
/*     */   }
/*     */ 
/*     */   public void setChosenFileFormat(long a_lFileFormat)
/*     */   {
/* 400 */     this.m_chosenFileFormat = a_lFileFormat;
/*     */   }
/*     */ 
/*     */   public void setBulkUploadFirst(boolean a_bBulkUploadFirst)
/*     */   {
/* 405 */     this.m_bBulkUploadFirst = a_bBulkUploadFirst;
/*     */   }
/*     */ 
/*     */   public boolean getBulkUploadFirst()
/*     */   {
/* 410 */     return this.m_bBulkUploadFirst;
/*     */   }
/*     */ 
/*     */   public void setImportNewAssets(boolean a_bImportNewAssets)
/*     */   {
/* 415 */     this.m_bImportNewAssets = a_bImportNewAssets;
/*     */   }
/*     */ 
/*     */   public boolean getImportNewAssets()
/*     */   {
/* 420 */     return this.m_bImportNewAssets;
/*     */   }
/*     */ 
/*     */   public void setImportFilesToExistingAssets(boolean a_bImportFilesToExistingAssets)
/*     */   {
/* 425 */     this.m_bImportFilesToExistingAssets = a_bImportFilesToExistingAssets;
/*     */   }
/*     */ 
/*     */   public boolean getImportFilesToExistingAssets()
/*     */   {
/* 430 */     return this.m_bImportFilesToExistingAssets;
/*     */   }
/*     */ 
/*     */   public void setEntitiesWithMatchAttributeExist(boolean a_bEntitiesWithMatchAttributeExist)
/*     */   {
/* 435 */     this.m_bEntitiesWithMatchAttributeExist = a_bEntitiesWithMatchAttributeExist;
/*     */   }
/*     */ 
/*     */   public boolean getEntitiesWithMatchAttributeExist()
/*     */   {
/* 440 */     return this.m_bEntitiesWithMatchAttributeExist;
/*     */   }
/*     */ 
/*     */   public void setChildEntitiesWithMatchAttributeExist(boolean a_bChildEntitiesWithMatchAttributeExist)
/*     */   {
/* 445 */     this.m_bChildEntitiesWithMatchAttributeExist = a_bChildEntitiesWithMatchAttributeExist;
/*     */   }
/*     */ 
/*     */   public boolean getChildEntitiesWithMatchAttributeExist()
/*     */   {
/* 450 */     return this.m_bChildEntitiesWithMatchAttributeExist;
/*     */   }
/*     */ 
/*     */   public void setImportChildAssets(boolean a_bImportChildAssets)
/*     */   {
/* 455 */     this.m_bImportChildAssets = a_bImportChildAssets;
/*     */   }
/*     */ 
/*     */   public boolean getImportChildAssets()
/*     */   {
/* 460 */     return this.m_bImportChildAssets;
/*     */   }
/*     */ 
/*     */   public boolean getAddPlaceholders()
/*     */   {
/* 465 */     return this.m_bAddPlaceholders;
/*     */   }
/*     */ 
/*     */   public void setAddPlaceholders(boolean a_sAddPlaceholders)
/*     */   {
/* 470 */     this.m_bAddPlaceholders = a_sAddPlaceholders;
/*     */   }
/*     */ 
/*     */   public BrightNaturalNumber getNumAssetsToAdd()
/*     */   {
/* 476 */     return this.m_numAssetsToAdd;
/*     */   }
/*     */ 
/*     */   public void setNumAssetsToAdd(BrightNaturalNumber a_sNumAssetsToAdd)
/*     */   {
/* 482 */     this.m_numAssetsToAdd = a_sNumAssetsToAdd;
/*     */   }
/*     */ 
/*     */   public boolean isRemoveIdFromFilename()
/*     */   {
/* 487 */     return this.m_bRemoveIdFromFilename;
/*     */   }
/*     */ 
/*     */   public void setRemoveIdFromFilename(boolean a_bRemoveIdFromFilename)
/*     */   {
/* 492 */     this.m_bRemoveIdFromFilename = a_bRemoveIdFromFilename;
/*     */   }
/*     */ 
/*     */   public boolean getIdAppearsInFilenames()
/*     */   {
/* 497 */     return this.m_bIdAppearsInFilenames;
/*     */   }
/*     */ 
/*     */   public void setIdAppearsInFilenames(boolean a_bIdAppearsInFilenames)
/*     */   {
/* 502 */     this.m_bIdAppearsInFilenames = a_bIdAppearsInFilenames;
/*     */   }
/*     */ 
/*     */   public int getTopLevelDirectoryCount()
/*     */   {
/* 507 */     if (this.m_directoryList == null)
/*     */     {
/* 509 */       return 0;
/*     */     }
/* 511 */     return this.m_directoryList.size();
/*     */   }
/*     */ 
/*     */   public void setAllTopLevelFilesList(List<String> m_allTopLevelFilesList)
/*     */   {
/* 516 */     this.m_allTopLevelFilesList = m_allTopLevelFilesList;
/*     */   }
/*     */ 
/*     */   public List<String> getAllTopLevelFilesList()
/*     */   {
/* 521 */     return this.m_allTopLevelFilesList;
/*     */   }
/*     */ 
/*     */   public int getNumIncompleteAssets()
/*     */   {
/* 526 */     return this.m_iNumIncompleteAssets;
/*     */   }
/*     */ 
/*     */   public void setNumIncompleteAssets(int a_iNumIncompleteAssets)
/*     */   {
/* 531 */     this.m_iNumIncompleteAssets = a_iNumIncompleteAssets;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.upload.form.ImportForm
 * JD-Core Version:    0.6.0
 */