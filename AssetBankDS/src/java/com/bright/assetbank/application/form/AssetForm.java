/*      */ package com.bright.assetbank.application.form;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.bean.LightweightAsset;
/*      */ import com.bright.assetbank.application.bean.SubmitOptionGroup;
/*      */ import com.bright.assetbank.application.bean.SubmitOptions;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*      */ import com.bright.assetbank.category.bean.ExtendedCategoryInfo;
/*      */ import com.bright.assetbank.category.util.CategoryUtil;
/*      */ import com.bright.assetbank.feedback.bean.AssetFeedback;
/*      */ import com.bright.assetbank.priceband.bean.PriceBand;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.category.form.CategorySelectionForm;
/*      */ import com.bright.framework.category.form.CategorySelectionListener;
/*      */ import com.bright.framework.category.service.CategoryManager;
/*      */ import com.bright.framework.common.bean.BrightMoney;
/*      */ import com.bright.framework.common.bean.IdValueBean;
/*      */ import com.bright.framework.common.bean.TranslatableStringDataBean;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.image.bean.ImageFile;
/*      */ import com.bright.framework.simplelist.bean.ListItem;
/*      */ import com.bright.framework.simplelist.service.ListManager;
/*      */ import com.bright.framework.storage.bean.FileInStorageDevice;
/*      */ import com.bright.framework.user.bean.UserProfile;
/*      */ import com.bright.framework.util.FileUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.text.DateFormat;
/*      */ import java.text.ParseException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.upload.FormFile;
/*      */ 
/*      */ public class AssetForm extends UploadFileForm
/*      */ {
/*      */   private static final String c_ksClassName = "AssetForm";
/*      */   CategorySelectionForm m_descriptiveCategoryForm;
/*      */   CategorySelectionForm m_permissionCategoryForm;
/*  110 */   private Asset m_asset = null;
/*  111 */   private FormFile m_substituteFile = null;
/*  112 */   private boolean m_bRemoveSubstitute = false;
/*  113 */   private String m_sTempFileLocation = null;
/*      */ 
/*  115 */   private String m_sDateAdded = null;
/*  116 */   private String m_sExpiryDate = null;
/*  117 */   private boolean m_bUserCanDownloadAsset = false;
/*  118 */   private boolean m_bUserCanUpdateAsset = false;
/*  119 */   private boolean m_bUserCanDeleteAsset = false;
/*  120 */   private boolean m_bAssetInAssetBox = false;
/*  121 */   private boolean m_bDirectDownloadFileType = false;
/*      */ 
/*  123 */   protected Vector m_vAssetAttributes = null;
/*  124 */   private String m_sOriginalFilename = null;
/*  125 */   private String m_sThumbnailUrl = null;
/*  126 */   private String m_sEncryptedFilePath = null;
/*  127 */   private String m_sEncryptedOriginalFilePath = null;
/*  128 */   private boolean m_bUserCanDownloadAssetWithApproval = false;
/*      */ 
/*  130 */   private ImageFile m_previewImageFile = null;
/*  131 */   private long m_lAddToCategoryId = 0L;
/*  132 */   private int m_iRotationAngle = 0;
/*  133 */   private int m_iWidth = 0;
/*  134 */   private int m_iHeight = 0;
/*  135 */   private long m_lFileSizeInBytes = 0L;
/*  136 */   private int m_iNumLayers = 0;
/*  137 */   private Vector m_vecWorkflows = null;
/*      */ 
/*  140 */   private Vector m_priceBands = null;
/*      */ 
/*  142 */   private boolean m_bAgreementAccepted = false;
/*      */   private long[] m_brandSelectedList;
/*  148 */   private Vector m_brandList = null;
/*      */ 
/*  150 */   private boolean m_bChangedFrame = false;
/*  151 */   private int m_iSelectedFrame = -1;
/*      */ 
/*  153 */   private String m_sTempDirName = null;
/*  154 */   private String m_sTempFileIndex = null;
/*      */   private boolean m_bCanRemoveAssetFromAssetBox;
/*  158 */   private int m_iSaveTypeId = 0;
/*      */ 
/*  160 */   private boolean m_deferEntitySelection = false;
/*  161 */   private Vector m_entities = null;
/*  162 */   private long m_lSelectedAssetEntityId = 0L;
/*  163 */   private long m_lParentId = 0L;
/*  164 */   private long m_lPeerId = 0L;
/*  165 */   private boolean m_bEntityPreSelected = false;
/*  166 */   private String m_sParentRelationshipName = null;
/*  167 */   private String m_sSiblingRelationshipName = null;
/*  168 */   private boolean m_bEmptyAsset = false;
/*      */ 
/*  170 */   private Vector m_vecTemplates = null;
/*  171 */   private Filter m_template = null;
/*  172 */   private int m_iCurrentTemplateId = 0;
/*      */ 
/*  174 */   private Vector m_vecAgreements = null;
/*  175 */   private String m_sSensitvityNotes = null;
/*  176 */   private boolean m_bDownloadAsDocument = false;
/*  177 */   private String m_sUploadedFilename = null;
/*      */ 
/*  179 */   private boolean m_bUserCanReviewAsset = false;
/*  180 */   private boolean m_bUserCanViewRestrictedAsset = false;
/*  181 */   private boolean m_bEntityCanHaveParents = false;
/*      */ 
/*  183 */   boolean m_bChildrenMustHaveParents = false;
/*      */ 
/*  185 */   boolean m_bCanBeRated = false;
/*      */ 
/*  188 */   private int m_iSelectedSubmitOption = -1;
/*      */ 
/*  192 */   private SubmitOptions m_submitOptions = null;
/*  193 */   private Vector m_vecWorkflowOptions = null;
/*      */ 
/*  195 */   private boolean m_bHideCategoriesBecauseEmpty = false;
/*  196 */   private Vector m_vPossibleFileFormats = null;
/*      */ 
/*  199 */   private HashMap m_hmParentAttributes = null;
/*      */ 
/*  201 */   private boolean m_bUserMustRequestApprovalForHighRes = false;
/*  202 */   private boolean m_bUserHasApprovalForHighRes = false;
/*      */ 
/*  204 */   private boolean m_bApprovalIsPending = false;
/*  205 */   private WorkflowUpdate m_workflowUpdate = null;
/*      */ 
/*  207 */   private FileInStorageDevice m_fileInStorageDevice = null;
/*  208 */   private Vector<IdValueBean> m_vecDataLookupValues = null;
/*  209 */   private Vector<String> m_vecInfo = null;
/*      */ 
/*  211 */   private String m_sReturnUrl = null;
/*  212 */   private long m_lExpectedAssetId = 0L;
/*      */ 
/*  214 */   private boolean m_bRedirectedFromDownloadPage = false;
/*      */ 
/*  216 */   private long[] m_aPeerAssetIdArray = null;
/*  217 */   private long[] m_aChildAssetIdArray = null;
/*  218 */   private long[] m_aParentAssetIdArray = null;
/*      */ 
/*  220 */   private long m_lCatExtensionCatId = -1L;
/*  221 */   private long m_lCatExtensionParentId = -1L;
/*  222 */   private long m_lCatExtensionTreeId = -1L;
/*  223 */   private String m_sCatExtensionReturnLocation = "";
/*      */ 
/*  225 */   private long m_lAddingFromBrowseCatId = -1L;
/*  226 */   private long m_lAddingFromBrowseTreeId = -1L;
/*      */ 
/* 1581 */   private HashMap<Long, ArrayList<Asset>> m_hmDefaultRelatedAssets = null;
/*      */ 
/* 1787 */   private boolean m_bEnableSaveAsNewVersion = true;
/*      */ 
/* 1808 */   private Vector<LightweightAsset> m_vPeerAssets = null;
/* 1809 */   private Vector<LightweightAsset> m_vChildAssets = null;
/* 1810 */   private Vector<? extends LightweightAsset> m_vParentAssets = null;
/* 1811 */   private Vector<LightweightAsset> m_vSiblingAssets = null;
/* 1812 */   private HashMap<String, Vector<LightweightAsset>> m_hmGroupedPeerAssets = null;
/* 1813 */   private HashMap<String, Vector<LightweightAsset>> m_hmGroupedChildAssets = null;
/* 1814 */   private Vector<String> m_vPeerAssetCategories = null;
/*      */ 
/*      */   public AssetForm()
/*      */   {
/*  233 */     this.m_descriptiveCategoryForm = new CategorySelectionForm();
/*  234 */     this.m_permissionCategoryForm = new CategorySelectionForm();
/*      */ 
/*  238 */     this.m_descriptiveCategoryForm.addCategorySelectionListener(new CategorySelectionListener()
/*      */     {
/*      */       public void categoriesSelected(CategorySelectionForm a_source, Set<Long> a_categoryIds)
/*      */       {
/*  245 */         Vector vecCats = CategoryUtil.getCategoriesFromIds(1L, a_categoryIds);
/*      */ 
/*  247 */         AssetForm.this.m_asset.setDescriptiveCategories(vecCats);
/*      */       }
/*      */     });
/*  252 */     this.m_permissionCategoryForm.addCategorySelectionListener(new CategorySelectionListener()
/*      */     {
/*      */       public void categoriesSelected(CategorySelectionForm a_source, Set<Long> a_categoryIds)
/*      */       {
/*  258 */         Vector vecCats = CategoryUtil.getCategoriesFromIds(2L, a_categoryIds);
/*      */ 
/*  260 */         AssetForm.this.m_asset.setPermissionCategories(vecCats);
/*      */       }
/*      */     });
/*  266 */     this.m_asset = new Asset();
/*      */   }
/*      */ 
/*      */   public void reset(ActionMapping mapping, HttpServletRequest request)
/*      */   {
/*  279 */     this.m_brandSelectedList = new long[0];
/*  280 */     super.reset(mapping, request);
/*      */   }
/*      */ 
/*      */   public Asset getAsset()
/*      */   {
/*  287 */     return this.m_asset;
/*      */   }
/*      */ 
/*      */   public void setAsset(Asset a_asset)
/*      */   {
/*  293 */     this.m_asset = a_asset;
/*      */   }
/*      */ 
/*      */   public CategorySelectionForm getDescriptiveCategoryForm()
/*      */   {
/*  298 */     return this.m_descriptiveCategoryForm;
/*      */   }
/*      */ 
/*      */   public CategorySelectionForm getPermissionCategoryForm() {
/*  302 */     return this.m_permissionCategoryForm;
/*      */   }
/*      */ 
/*      */   public long[] getCategoryIdArray()
/*      */   {
/*  313 */     long[] alIds = null;
/*      */ 
/*  315 */     String[] asIds = null;
/*      */ 
/*  317 */     List categories = this.m_asset.getDescriptiveCategories();
/*  318 */     if (categories != null)
/*      */     {
/*  320 */       alIds = new long[categories.size()];
/*  321 */       asIds = new String[categories.size()];
/*      */ 
/*  323 */       for (int i = 0; i < categories.size(); i++)
/*      */       {
/*  325 */         alIds[i] = ((Category)categories.get(i)).getId();
/*  326 */         asIds[i] = Long.toString(((Category)categories.get(i)).getId());
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  331 */       alIds = new long[0];
/*      */     }
/*      */ 
/*  334 */     this.m_descriptiveCategoryForm.setSelectedCategories(asIds);
/*      */ 
/*  336 */     return alIds;
/*      */   }
/*      */ 
/*      */   public long[] getPermissionCategoryIdArray()
/*      */   {
/*  348 */     long[] alIds = null;
/*      */ 
/*  350 */     String[] asIds = null;
/*      */ 
/*  352 */     List categories = this.m_asset.getPermissionCategories();
/*  353 */     if ((this.m_asset != null) && (categories != null))
/*      */     {
/*  355 */       alIds = new long[categories.size()];
/*  356 */       asIds = new String[categories.size()];
/*      */ 
/*  358 */       for (int i = 0; i < categories.size(); i++)
/*      */       {
/*  360 */         alIds[i] = ((Category)categories.get(i)).getId();
/*  361 */         asIds[i] = Long.toString(((Category)categories.get(i)).getId());
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  366 */       alIds = new long[0];
/*      */     }
/*      */ 
/*  369 */     this.m_permissionCategoryForm.setSelectedCategories(asIds);
/*      */ 
/*  371 */     return alIds;
/*      */   }
/*      */ 
/*      */   public String getAllCategoryIds()
/*      */   {
/*  382 */     String sAllIds = "";
/*  383 */     if (StringUtil.stringIsPopulated(this.m_descriptiveCategoryForm.getCategoryIds()))
/*      */     {
/*  385 */       sAllIds = this.m_descriptiveCategoryForm.getCategoryIds();
/*      */     }
/*  387 */     if (StringUtil.stringIsPopulated(this.m_permissionCategoryForm.getCategoryIds()))
/*      */     {
/*  389 */       if (StringUtil.stringIsPopulated(sAllIds))
/*      */       {
/*  391 */         sAllIds = sAllIds + ",";
/*      */       }
/*  393 */       sAllIds = sAllIds + this.m_permissionCategoryForm.getCategoryIds();
/*      */     }
/*  395 */     return sAllIds;
/*      */   }
/*      */ 
/*      */   public String getDescriptiveCategoryIds()
/*      */   {
/*  406 */     String sIds = "";
/*  407 */     if (StringUtil.stringIsPopulated(this.m_descriptiveCategoryForm.getCategoryIds()))
/*      */     {
/*  409 */       sIds = this.m_descriptiveCategoryForm.getCategoryIds();
/*      */     }
/*  411 */     return sIds;
/*      */   }
/*      */ 
/*      */   public String getPermissionCategoryIds()
/*      */   {
/*  422 */     String sIds = "";
/*  423 */     if (StringUtil.stringIsPopulated(this.m_permissionCategoryForm.getCategoryIds()))
/*      */     {
/*  425 */       sIds = this.m_permissionCategoryForm.getCategoryIds();
/*      */     }
/*  427 */     return sIds;
/*      */   }
/*      */ 
/*      */   public String getDateAdded()
/*      */   {
/*  435 */     return this.m_sDateAdded;
/*      */   }
/*      */ 
/*      */   public void setDateAdded(String a_sDateAdded)
/*      */   {
/*  441 */     this.m_sDateAdded = a_sDateAdded;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanUpdateAsset()
/*      */   {
/*  447 */     return this.m_bUserCanUpdateAsset;
/*      */   }
/*      */ 
/*      */   public void setUserCanUpdateAsset(boolean a_sUserCanUpdateAsset)
/*      */   {
/*  453 */     this.m_bUserCanUpdateAsset = a_sUserCanUpdateAsset;
/*      */   }
/*      */ 
/*      */   public boolean isAssetInAssetBox()
/*      */   {
/*  461 */     return this.m_bAssetInAssetBox;
/*      */   }
/*      */ 
/*      */   public void setAssetInAssetBox(boolean a_sImageInAssetBox)
/*      */   {
/*  469 */     this.m_bAssetInAssetBox = a_sImageInAssetBox;
/*      */   }
/*      */ 
/*      */   public boolean getCanRemoveAssetFromAssetBox()
/*      */   {
/*  474 */     return this.m_bCanRemoveAssetFromAssetBox;
/*      */   }
/*      */ 
/*      */   public void setCanRemoveAssetFromAssetBox(boolean a_bCanRemove)
/*      */   {
/*  479 */     this.m_bCanRemoveAssetFromAssetBox = a_bCanRemove;
/*      */   }
/*      */ 
/*      */   public String getRc4EncryptedFileLocation()
/*      */   {
/*  484 */     if (getAsset().getFileLocation() != null)
/*      */     {
/*  486 */       return FileUtil.encryptFilepath(getAsset().getFileLocation());
/*      */     }
/*      */ 
/*  489 */     return null;
/*      */   }
/*      */ 
/*      */   public Vector getAssetAttributes()
/*      */   {
/*  497 */     return this.m_vAssetAttributes;
/*      */   }
/*      */ 
/*      */   public void setAssetAttributes(Vector a_sImageAttributes)
/*      */   {
/*  505 */     this.m_vAssetAttributes = a_sImageAttributes;
/*      */   }
/*      */ 
/*      */   public Attribute getAttributeById(String a_sAttributeId)
/*      */   {
/*  518 */     return getAttribute(Integer.parseInt(a_sAttributeId));
/*      */   }
/*      */ 
/*      */   public Attribute getAttribute(int a_iAttributeId)
/*      */   {
/*  532 */     Attribute att = null;
/*      */ 
/*  534 */     for (int i = 0; i < this.m_vAssetAttributes.size(); i++)
/*      */     {
/*  536 */       if (((Attribute)this.m_vAssetAttributes.get(i)).getId() != a_iAttributeId)
/*      */         continue;
/*  538 */       att = (Attribute)this.m_vAssetAttributes.get(i);
/*      */     }
/*      */ 
/*  542 */     return att;
/*      */   }
/*      */ 
/*      */   public void setAttribute(int a_iAttributeId, Object a_object)
/*      */   {
/*      */   }
/*      */ 
/*      */   public Attribute getAttribute(String a_sLabel)
/*      */   {
/*  565 */     Attribute att = null;
/*      */ 
/*  567 */     for (int i = 0; i < this.m_vAssetAttributes.size(); i++)
/*      */     {
/*  569 */       if (!((Attribute)this.m_vAssetAttributes.get(i)).getLabel().equalsIgnoreCase(a_sLabel))
/*      */         continue;
/*  571 */       att = (Attribute)this.m_vAssetAttributes.get(i);
/*      */     }
/*      */ 
/*  575 */     return att;
/*      */   }
/*      */ 
/*      */   public Attribute getStaticAttribute(String a_sStaticFieldName)
/*      */   {
/*  590 */     for (int i = 0; i < this.m_vAssetAttributes.size(); i++)
/*      */     {
/*  592 */       String sFieldname = ((Attribute)this.m_vAssetAttributes.get(i)).getFieldName();
/*  593 */       if ((sFieldname != null) && (sFieldname.equalsIgnoreCase(a_sStaticFieldName)))
/*      */       {
/*  595 */         return (Attribute)this.m_vAssetAttributes.get(i);
/*      */       }
/*      */     }
/*  598 */     return null;
/*      */   }
/*      */ 
/*      */   public void setAttribute(String a_sLabel, Object a_object)
/*      */   {
/*      */   }
/*      */ 
/*      */   public boolean validate(HttpServletRequest a_request)
/*      */   {
/*  618 */     return getHasErrors();
/*      */   }
/*      */ 
/*      */   public boolean validateDates(HttpServletRequest a_request, ListManager a_listManager, UserProfile a_userProfile, DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  628 */     boolean bDateError = false;
/*      */ 
/*  630 */     DateFormat appFormat = AssetBankSettings.getStandardDateFormat();
/*      */     try
/*      */     {
/*  634 */       if (StringUtil.stringIsPopulated(getExpiryDate()))
/*      */       {
/*  636 */         this.m_asset.setExpiryDate(appFormat.parse(getExpiryDate()));
/*      */       }
/*      */     }
/*      */     catch (ParseException pe)
/*      */     {
/*  641 */       addError(a_listManager.getListItem(a_dbTransaction, "failedValidationDateFormat", a_userProfile.getCurrentLanguage()).getBody());
/*  642 */       bDateError = true;
/*      */     }
/*      */ 
/*  645 */     return bDateError;
/*      */   }
/*      */ 
/*      */   public void validatePrice(HttpServletRequest a_request, ListManager a_listManager, UserProfile a_userProfile, DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  656 */     BrightMoney price = this.m_asset.getPrice();
/*  657 */     if (price.getIsFormAmountEntered())
/*      */     {
/*  659 */       if (!price.processFormData())
/*      */       {
/*  661 */         addError(a_listManager.getListItem(a_dbTransaction, "failedValidationPrice", a_userProfile.getCurrentLanguage()).getBody());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getOriginalFilename()
/*      */   {
/*  669 */     return this.m_sOriginalFilename;
/*      */   }
/*      */ 
/*      */   public void setOriginalFilename(String a_sWebappPath)
/*      */   {
/*  675 */     this.m_sOriginalFilename = a_sWebappPath;
/*      */   }
/*      */ 
/*      */   public String getThumbnailUrl()
/*      */   {
/*  681 */     return this.m_sThumbnailUrl;
/*      */   }
/*      */ 
/*      */   public void setThumbnailUrl(String a_sThumbnailUrl)
/*      */   {
/*  687 */     this.m_sThumbnailUrl = a_sThumbnailUrl;
/*      */   }
/*      */ 
/*      */   public String getExpiryDate()
/*      */   {
/*  693 */     return this.m_sExpiryDate;
/*      */   }
/*      */ 
/*      */   public void setExpiryDate(String a_sExpiryDate)
/*      */   {
/*  699 */     this.m_sExpiryDate = a_sExpiryDate;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanDownloadAsset()
/*      */   {
/*  705 */     return this.m_bUserCanDownloadAsset;
/*      */   }
/*      */ 
/*      */   public void setUserCanDownloadAsset(boolean a_sUserCanDownloadAsset)
/*      */   {
/*  711 */     this.m_bUserCanDownloadAsset = a_sUserCanDownloadAsset;
/*      */   }
/*      */ 
/*      */   public String getEncryptedFilePath()
/*      */   {
/*  717 */     return this.m_sEncryptedFilePath;
/*      */   }
/*      */ 
/*      */   public void setEncryptedFilePath(String a_sEncryptedFilePath)
/*      */   {
/*  723 */     this.m_sEncryptedFilePath = a_sEncryptedFilePath;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanDownloadAssetWithApproval()
/*      */   {
/*  729 */     return this.m_bUserCanDownloadAssetWithApproval;
/*      */   }
/*      */ 
/*      */   public void setUserCanDownloadAssetWithApproval(boolean a_bUserCanDownloadAssetWithApproval)
/*      */   {
/*  734 */     this.m_bUserCanDownloadAssetWithApproval = a_bUserCanDownloadAssetWithApproval;
/*      */   }
/*      */ 
/*      */   public String getEncryptedOriginalFilePath() {
/*  738 */     return this.m_sEncryptedOriginalFilePath;
/*      */   }
/*      */ 
/*      */   public void setEncryptedOriginalFilePath(String a_sEncryptedOriginalFilePath) {
/*  742 */     this.m_sEncryptedOriginalFilePath = a_sEncryptedOriginalFilePath;
/*      */   }
/*      */ 
/*      */   public String getTempFileLocation()
/*      */   {
/*  748 */     return this.m_sTempFileLocation;
/*      */   }
/*      */ 
/*      */   public String getTempFilename()
/*      */   {
/*  753 */     return FileUtil.getFilename(this.m_sTempFileLocation);
/*      */   }
/*      */ 
/*      */   public void setTempFileLocation(String a_sTempFileLocation)
/*      */   {
/*  759 */     this.m_sTempFileLocation = a_sTempFileLocation;
/*      */   }
/*      */ 
/*      */   public ImageFile getPreviewImageFile()
/*      */   {
/*  766 */     if (this.m_previewImageFile != null)
/*      */     {
/*  768 */       return this.m_previewImageFile;
/*      */     }
/*      */ 
/*  772 */     if ((this.m_asset != null) && (this.m_asset.getPreviewImageFile() != null) && (StringUtil.stringIsPopulated(this.m_asset.getPreviewImageFile().getPath())))
/*      */     {
/*  774 */       return this.m_asset.getPreviewImageFile();
/*      */     }
/*      */ 
/*  778 */     return null;
/*      */   }
/*      */ 
/*      */   public void setPreviewImageFile(ImageFile a_imageFile)
/*      */   {
/*  784 */     this.m_previewImageFile = a_imageFile;
/*      */   }
/*      */ 
/*      */   public long getAddToCategoryId()
/*      */   {
/*  789 */     return this.m_lAddToCategoryId;
/*      */   }
/*      */ 
/*      */   public void setAddToCategoryId(long a_lAddToCategoryId) {
/*  793 */     this.m_lAddToCategoryId = a_lAddToCategoryId;
/*      */   }
/*      */ 
/*      */   public int getRotationAngle()
/*      */   {
/*  799 */     return this.m_iRotationAngle;
/*      */   }
/*      */ 
/*      */   public void setRotationAngle(int a_sRotationAngle)
/*      */   {
/*  804 */     this.m_iRotationAngle = a_sRotationAngle;
/*      */   }
/*      */ 
/*      */   public int getHeight() {
/*  808 */     return this.m_iHeight;
/*      */   }
/*      */ 
/*      */   public void setHeight(int a_iHeight) {
/*  812 */     this.m_iHeight = a_iHeight;
/*      */   }
/*      */ 
/*      */   public int getWidth() {
/*  816 */     return this.m_iWidth;
/*      */   }
/*      */ 
/*      */   public void setWidth(int a_iWidth) {
/*  820 */     this.m_iWidth = a_iWidth;
/*      */   }
/*      */ 
/*      */   public FormFile getSubstituteFile() {
/*  824 */     return this.m_substituteFile;
/*      */   }
/*      */ 
/*      */   public void setSubstituteFile(FormFile a_substituteFile) {
/*  828 */     this.m_substituteFile = a_substituteFile;
/*      */   }
/*      */ 
/*      */   public boolean getRemoveSubstitute() {
/*  832 */     return this.m_bRemoveSubstitute;
/*      */   }
/*      */ 
/*      */   public void setRemoveSubstitute(boolean a_bRemoveSubstitute) {
/*  836 */     this.m_bRemoveSubstitute = a_bRemoveSubstitute;
/*      */   }
/*      */ 
/*      */   public Vector getPriceBands()
/*      */   {
/*  841 */     return this.m_priceBands;
/*      */   }
/*      */ 
/*      */   public void setPriceBands(Vector a_sPriceBands) {
/*  845 */     this.m_priceBands = a_sPriceBands;
/*      */   }
/*      */ 
/*      */   public int getNumPrintPriceBands()
/*      */   {
/*  850 */     int count = 0;
/*  851 */     if (this.m_priceBands != null)
/*      */     {
/*  853 */       for (int i = 0; i < this.m_priceBands.size(); i++)
/*      */       {
/*  855 */         if (((PriceBand)this.m_priceBands.get(i)).getPriceBandType().getId() != 2L)
/*      */           continue;
/*  857 */         count++;
/*      */       }
/*      */     }
/*      */ 
/*  861 */     return count;
/*      */   }
/*      */ 
/*      */   public int getNumDownloadPriceBands()
/*      */   {
/*  866 */     int count = 0;
/*  867 */     if (this.m_priceBands != null)
/*      */     {
/*  869 */       for (int i = 0; i < this.m_priceBands.size(); i++)
/*      */       {
/*  871 */         if (((PriceBand)this.m_priceBands.get(i)).getPriceBandType().getId() != 1L)
/*      */           continue;
/*  873 */         count++;
/*      */       }
/*      */     }
/*      */ 
/*  877 */     return count;
/*      */   }
/*      */ 
/*      */   public boolean getAgreementAccepted()
/*      */   {
/*  883 */     return this.m_bAgreementAccepted;
/*      */   }
/*      */ 
/*      */   public void setAgreementAccepted(boolean a_bAgreementAccepted)
/*      */   {
/*  888 */     this.m_bAgreementAccepted = a_bAgreementAccepted;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanDeleteAsset()
/*      */   {
/*  893 */     return this.m_bUserCanDeleteAsset;
/*      */   }
/*      */ 
/*      */   public void setUserCanDeleteAsset(boolean a_sUserCanDeleteAsset)
/*      */   {
/*  898 */     this.m_bUserCanDeleteAsset = a_sUserCanDeleteAsset;
/*      */   }
/*      */ 
/*      */   public boolean isDirectDownloadFileType()
/*      */   {
/*  903 */     return this.m_bDirectDownloadFileType;
/*      */   }
/*      */ 
/*      */   public void setDirectDownloadFileType(boolean a_sDirectDownloadFileType)
/*      */   {
/*  908 */     this.m_bDirectDownloadFileType = a_sDirectDownloadFileType;
/*      */   }
/*      */ 
/*      */   public long getFileSizeInBytes()
/*      */   {
/*  913 */     return this.m_lFileSizeInBytes;
/*      */   }
/*      */ 
/*      */   public void setFileSizeInBytes(long a_lFilesizeInBytes)
/*      */   {
/*  918 */     this.m_lFileSizeInBytes = a_lFilesizeInBytes;
/*      */   }
/*      */ 
/*      */   public Vector getBrandList()
/*      */   {
/*  924 */     return this.m_brandList;
/*      */   }
/*      */ 
/*      */   public void setBrandList(Vector a_sBrandList)
/*      */   {
/*  930 */     this.m_brandList = a_sBrandList;
/*      */   }
/*      */ 
/*      */   public long[] getBrandSelectedList()
/*      */   {
/*  936 */     return this.m_brandSelectedList;
/*      */   }
/*      */ 
/*      */   public void setBrandSelectedList(long[] a_sBrandSelectedList)
/*      */   {
/*  942 */     this.m_brandSelectedList = a_sBrandSelectedList;
/*      */   }
/*      */ 
/*      */   public void setChangedFrame(boolean a_bChangedFrame)
/*      */   {
/*  947 */     this.m_bChangedFrame = a_bChangedFrame;
/*      */   }
/*      */ 
/*      */   public boolean getChangedFrame()
/*      */   {
/*  952 */     return this.m_bChangedFrame;
/*      */   }
/*      */ 
/*      */   public void setSelectedFrame(int a_iSelectedFrame)
/*      */   {
/*  957 */     this.m_iSelectedFrame = a_iSelectedFrame;
/*      */   }
/*      */ 
/*      */   public int getSelectedFrame()
/*      */   {
/*  962 */     return this.m_iSelectedFrame;
/*      */   }
/*      */ 
/*      */   public String getTempDirName()
/*      */   {
/*  968 */     return this.m_sTempDirName;
/*      */   }
/*      */ 
/*      */   public void setTempDirName(String a_sTempDirName)
/*      */   {
/*  974 */     this.m_sTempDirName = a_sTempDirName;
/*      */   }
/*      */ 
/*      */   public String getTempFileIndex()
/*      */   {
/*  980 */     return this.m_sTempFileIndex;
/*      */   }
/*      */ 
/*      */   public void setTempFileIndex(String a_sTempFileIndex)
/*      */   {
/*  986 */     this.m_sTempFileIndex = a_sTempFileIndex;
/*      */   }
/*      */ 
/*      */   public int getSaveTypeId()
/*      */   {
/*  991 */     return this.m_iSaveTypeId;
/*      */   }
/*      */ 
/*      */   public void setSaveTypeId(int saveTypeId)
/*      */   {
/*  996 */     this.m_iSaveTypeId = saveTypeId;
/*      */   }
/*      */ 
/*      */   public int getNumLayers()
/*      */   {
/* 1001 */     return this.m_iNumLayers;
/*      */   }
/*      */ 
/*      */   public void setNumLayers(int numLayers)
/*      */   {
/* 1006 */     this.m_iNumLayers = numLayers;
/*      */   }
/*      */ 
/*      */   public long getSelectedAssetEntityId()
/*      */   {
/* 1011 */     return this.m_lSelectedAssetEntityId;
/*      */   }
/*      */ 
/*      */   public void setSelectedAssetEntityId(long selectedAssetEntityId)
/*      */   {
/* 1016 */     this.m_lSelectedAssetEntityId = selectedAssetEntityId;
/*      */   }
/*      */ 
/*      */   public Vector getEntities()
/*      */   {
/* 1021 */     return this.m_entities;
/*      */   }
/*      */ 
/*      */   public void setEntities(Vector entities)
/*      */   {
/* 1026 */     this.m_entities = entities;
/*      */   }
/*      */ 
/*      */   public boolean getDeferEntitySelection()
/*      */   {
/* 1031 */     return this.m_deferEntitySelection;
/*      */   }
/*      */ 
/*      */   public void setDeferEntitySelection(boolean deferEntitySelection)
/*      */   {
/* 1036 */     this.m_deferEntitySelection = deferEntitySelection;
/*      */   }
/*      */ 
/*      */   public long getParentId()
/*      */   {
/* 1041 */     return this.m_lParentId;
/*      */   }
/*      */ 
/*      */   public void setParentId(long parentId)
/*      */   {
/* 1046 */     this.m_lParentId = parentId;
/*      */   }
/*      */ 
/*      */   public long getPeerId()
/*      */   {
/* 1051 */     return this.m_lPeerId;
/*      */   }
/*      */ 
/*      */   public void setPeerId(long peerId)
/*      */   {
/* 1056 */     this.m_lPeerId = peerId;
/*      */   }
/*      */ 
/*      */   public boolean isEntityPreSelected()
/*      */   {
/* 1061 */     return this.m_bEntityPreSelected;
/*      */   }
/*      */ 
/*      */   public void setEntityPreSelected(boolean entityPreSelected)
/*      */   {
/* 1066 */     this.m_bEntityPreSelected = entityPreSelected;
/*      */   }
/*      */ 
/*      */   public String getParentRelationshipName()
/*      */   {
/* 1071 */     return this.m_sParentRelationshipName;
/*      */   }
/*      */ 
/*      */   public void setParentRelationshipName(String parentRelationshipName)
/*      */   {
/* 1076 */     this.m_sParentRelationshipName = parentRelationshipName;
/*      */   }
/*      */ 
/*      */   public String getSiblingRelationshipName()
/*      */   {
/* 1082 */     return this.m_sSiblingRelationshipName;
/*      */   }
/*      */ 
/*      */   public void setSiblingRelationshipName(String siblingRelationshipName)
/*      */   {
/* 1088 */     this.m_sSiblingRelationshipName = siblingRelationshipName;
/*      */   }
/*      */ 
/*      */   public void setEntityCanHaveParents(boolean a_bEntityCanHaveParents)
/*      */   {
/* 1094 */     this.m_bEntityCanHaveParents = a_bEntityCanHaveParents;
/*      */   }
/*      */ 
/*      */   public boolean getEntityCanHaveParents()
/*      */   {
/* 1099 */     return this.m_bEntityCanHaveParents;
/*      */   }
/*      */ 
/*      */   public boolean isEmptyAsset()
/*      */   {
/* 1104 */     return this.m_bEmptyAsset;
/*      */   }
/*      */ 
/*      */   public void setEmptyAsset(boolean emptyAsset)
/*      */   {
/* 1110 */     this.m_bEmptyAsset = emptyAsset;
/*      */   }
/*      */ 
/*      */   public Vector getTemplates()
/*      */   {
/* 1115 */     return this.m_vecTemplates;
/*      */   }
/*      */ 
/*      */   public void setTemplates(Vector a_vecTemplates)
/*      */   {
/* 1120 */     this.m_vecTemplates = a_vecTemplates;
/*      */   }
/*      */ 
/*      */   public int getCurrentTemplateId()
/*      */   {
/* 1125 */     return this.m_iCurrentTemplateId;
/*      */   }
/*      */ 
/*      */   public void setCurrentTemplateId(int a_iCurrentTemplateId) {
/* 1129 */     this.m_iCurrentTemplateId = a_iCurrentTemplateId;
/*      */   }
/*      */ 
/*      */   public Filter getTemplate()
/*      */   {
/* 1134 */     return this.m_template;
/*      */   }
/*      */ 
/*      */   public void setTemplate(Filter a_template) {
/* 1138 */     this.m_template = a_template;
/*      */   }
/*      */ 
/*      */   public Vector getAgreements()
/*      */   {
/* 1143 */     return this.m_vecAgreements;
/*      */   }
/*      */ 
/*      */   public void setAgreements(Vector a_vecAgreements)
/*      */   {
/* 1148 */     this.m_vecAgreements = a_vecAgreements;
/*      */   }
/*      */ 
/*      */   public String getSensitvityNotes()
/*      */   {
/* 1153 */     return this.m_sSensitvityNotes;
/*      */   }
/*      */ 
/*      */   public void setSensitvityNotes(String sensitvityNotes)
/*      */   {
/* 1159 */     this.m_sSensitvityNotes = sensitvityNotes;
/*      */   }
/*      */ 
/*      */   public boolean getDownloadAsDocument()
/*      */   {
/* 1165 */     return this.m_bDownloadAsDocument;
/*      */   }
/*      */ 
/*      */   public void setDownloadAsDocument(boolean downloadAsDocument)
/*      */   {
/* 1171 */     this.m_bDownloadAsDocument = downloadAsDocument;
/*      */   }
/*      */ 
/*      */   public String getUploadedFilename()
/*      */   {
/* 1177 */     return this.m_sUploadedFilename;
/*      */   }
/*      */ 
/*      */   public void setUploadedFilename(String uploadedFilename)
/*      */   {
/* 1183 */     this.m_sUploadedFilename = uploadedFilename;
/*      */   }
/*      */ 
/*      */   public boolean getAreCategoriesVisible()
/*      */   {
/* 1188 */     return getIsAttributeVisible("categories");
/*      */   }
/*      */ 
/*      */   private boolean getIsAttributeVisible(String a_sAttributeName)
/*      */   {
/* 1193 */     if (getAssetAttributes() != null)
/*      */     {
/* 1195 */       for (int i = 0; i < getAssetAttributes().size(); i++)
/*      */       {
/* 1197 */         Attribute temp = (Attribute)getAssetAttributes().elementAt(i);
/*      */ 
/* 1199 */         if ((temp.getFieldName() != null) && (temp.getFieldName().equals(a_sAttributeName)))
/*      */         {
/* 1201 */           return temp.getIsVisible();
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1206 */     return true;
/*      */   }
/*      */ 
/*      */   public int getVisibleRatingCount(String a_sUserId)
/*      */   {
/* 1212 */     long lUserId = Long.parseLong(a_sUserId);
/* 1213 */     int iCount = 0;
/*      */ 
/* 1215 */     if ((getAsset() != null) && (getAsset().getAssetFeedback() != null))
/*      */     {
/* 1217 */       boolean bVisible = getAsset().getIsAttributeValueVisible("rating");
/* 1218 */       for (int i = 0; i < getAsset().getAssetFeedback().size(); i++)
/*      */       {
/* 1220 */         AssetFeedback temp = (AssetFeedback)getAsset().getAssetFeedback().elementAt(i);
/*      */ 
/* 1224 */         if ((!bVisible) && (temp.getUserId() != lUserId))
/*      */           continue;
/* 1226 */         iCount++;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1231 */     return iCount;
/*      */   }
/*      */ 
/*      */   public int getUserRatingCount(String a_sUserId)
/*      */   {
/* 1236 */     long lUserId = Long.parseLong(a_sUserId);
/* 1237 */     int iCount = 0;
/* 1238 */     if ((getAsset() != null) && (getAsset().getAssetFeedback() != null))
/*      */     {
/* 1240 */       for (int i = 0; i < getAsset().getAssetFeedback().size(); i++)
/*      */       {
/* 1242 */         AssetFeedback temp = (AssetFeedback)getAsset().getAssetFeedback().elementAt(i);
/*      */ 
/* 1244 */         if (temp.getUserId() != lUserId)
/*      */           continue;
/* 1246 */         iCount++;
/*      */       }
/*      */     }
/*      */ 
/* 1250 */     return iCount;
/*      */   }
/*      */ 
/*      */   public void setUserCanReviewAsset(boolean a_bUserCanReviewAsset)
/*      */   {
/* 1255 */     this.m_bUserCanReviewAsset = a_bUserCanReviewAsset;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanReviewAsset()
/*      */   {
/* 1260 */     return this.m_bUserCanReviewAsset;
/*      */   }
/*      */ 
/*      */   public void setUserCanViewRestrictedAsset(boolean a_bUserCanViewRestrictedAsset)
/*      */   {
/* 1265 */     this.m_bUserCanViewRestrictedAsset = a_bUserCanViewRestrictedAsset;
/*      */   }
/*      */ 
/*      */   public boolean getUserCanViewRestrictedAsset()
/*      */   {
/* 1270 */     return this.m_bUserCanViewRestrictedAsset;
/*      */   }
/*      */ 
/*      */   public boolean getChildrenMustHaveParents()
/*      */   {
/* 1275 */     return this.m_bChildrenMustHaveParents;
/*      */   }
/*      */ 
/*      */   public void setChildrenMustHaveParents(boolean a_bChildrenMustHaveParents)
/*      */   {
/* 1280 */     this.m_bChildrenMustHaveParents = a_bChildrenMustHaveParents;
/*      */   }
/*      */ 
/*      */   public void setCanBeRated(boolean a_bCanBeRated)
/*      */   {
/* 1285 */     this.m_bCanBeRated = a_bCanBeRated;
/*      */   }
/*      */ 
/*      */   public boolean getCanBeRated()
/*      */   {
/* 1290 */     return this.m_bCanBeRated;
/*      */   }
/*      */ 
/*      */   public int getSelectedSubmitOption()
/*      */   {
/* 1296 */     return this.m_iSelectedSubmitOption;
/*      */   }
/*      */ 
/*      */   public void setSelectedSubmitOption(int a_sSelectedSubmitOption)
/*      */   {
/* 1301 */     this.m_iSelectedSubmitOption = a_sSelectedSubmitOption;
/*      */   }
/*      */ 
/*      */   public SubmitOptions getSubmitOptions()
/*      */   {
/* 1307 */     return this.m_submitOptions;
/*      */   }
/*      */ 
/*      */   public void setSubmitOptions(SubmitOptions a_sSubmitOptions)
/*      */   {
/* 1313 */     this.m_submitOptions = a_sSubmitOptions;
/*      */   }
/*      */ 
/*      */   public boolean getHideCategoriesBecauseEmpty()
/*      */   {
/* 1318 */     return this.m_bHideCategoriesBecauseEmpty;
/*      */   }
/*      */ 
/*      */   public void setHideCategoriesBecauseEmpty(boolean a_bHideCategoriesBecauseEmpty)
/*      */   {
/* 1323 */     this.m_bHideCategoriesBecauseEmpty = a_bHideCategoriesBecauseEmpty;
/*      */   }
/*      */ 
/*      */   public boolean getHasBrandTemplateFileExtension()
/*      */   {
/* 1335 */     String fileName = StringUtils.isNotEmpty(this.m_asset.getFileName()) ? this.m_asset.getFileName() : this.m_sOriginalFilename;
/*      */ 
/* 1338 */     return AssetBankSettings.fileNameHasBrandTemplateExtension(fileName);
/*      */   }
/*      */ 
/*      */   public HashMap getParentAttributes()
/*      */   {
/* 1343 */     return this.m_hmParentAttributes;
/*      */   }
/*      */ 
/*      */   public void setParentAttributes(HashMap a_sParentAttributes)
/*      */   {
/* 1348 */     this.m_hmParentAttributes = a_sParentAttributes;
/*      */   }
/*      */ 
/*      */   public Vector getPossibleFileFormats()
/*      */   {
/* 1353 */     return this.m_vPossibleFileFormats;
/*      */   }
/*      */ 
/*      */   public void setPossibleFileFormats(Vector possibleFileFormats)
/*      */   {
/* 1358 */     this.m_vPossibleFileFormats = possibleFileFormats;
/*      */   }
/*      */ 
/*      */   public void setUserMustRequestApprovalForHighRes(boolean a_bUserMustRequestApprovalForHighRes)
/*      */   {
/* 1363 */     this.m_bUserMustRequestApprovalForHighRes = a_bUserMustRequestApprovalForHighRes;
/*      */   }
/*      */ 
/*      */   public boolean getUserMustRequestApprovalForHighRes()
/*      */   {
/* 1368 */     return this.m_bUserMustRequestApprovalForHighRes;
/*      */   }
/*      */ 
/*      */   public void setUserHasApprovalForHighRes(boolean a_bUserHasApprovalForHighRes)
/*      */   {
/* 1373 */     this.m_bUserHasApprovalForHighRes = a_bUserHasApprovalForHighRes;
/*      */   }
/*      */ 
/*      */   public boolean getUserHasApprovalForHighRes()
/*      */   {
/* 1378 */     return this.m_bUserHasApprovalForHighRes;
/*      */   }
/*      */ 
/*      */   public void setApprovalIsPending(boolean a_bApprovalIsPending)
/*      */   {
/* 1383 */     this.m_bApprovalIsPending = a_bApprovalIsPending;
/*      */   }
/*      */ 
/*      */   public boolean getApprovalIsPending()
/*      */   {
/* 1388 */     return this.m_bApprovalIsPending;
/*      */   }
/*      */ 
/*      */   public void setWorkflowOptions(Vector a_vecWorkflowOptions)
/*      */   {
/* 1393 */     this.m_vecWorkflowOptions = a_vecWorkflowOptions;
/*      */   }
/*      */ 
/*      */   public Vector getWorkflowOptions()
/*      */   {
/* 1398 */     return this.m_vecWorkflowOptions;
/*      */   }
/*      */ 
/*      */   public void setWorkflows(Vector a_vecWorkflows)
/*      */   {
/* 1403 */     this.m_vecWorkflows = a_vecWorkflows;
/*      */   }
/*      */ 
/*      */   public Vector getWorkflows()
/*      */   {
/* 1408 */     return this.m_vecWorkflows;
/*      */   }
/*      */ 
/*      */   public void setDataLookupValues(Vector<IdValueBean> a_vecDataLookupValues)
/*      */   {
/* 1413 */     this.m_vecDataLookupValues = a_vecDataLookupValues;
/*      */   }
/*      */ 
/*      */   public Vector<IdValueBean> getDataLookupValues()
/*      */   {
/* 1418 */     return this.m_vecDataLookupValues;
/*      */   }
/*      */ 
/*      */   public String getDataLookupValue(String a_sAttributeId)
/*      */   {
/* 1423 */     String ksMethodName = "getDataLookupValue";
/* 1424 */     String sValue = null;
/*      */     try
/*      */     {
/* 1429 */       long lAttributeId = Long.parseLong(a_sAttributeId);
/* 1430 */       sValue = getDataLookupValue(lAttributeId);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1434 */       GlobalApplication.getInstance().getLogger().error("AssetForm.getDataLookupValue: Unable to parse attribute id: " + a_sAttributeId);
/*      */     }
/* 1436 */     return sValue;
/*      */   }
/*      */ 
/*      */   public String getDataLookupValue(long a_lAttributeId)
/*      */   {
/* 1441 */     String sValue = "";
/* 1442 */     if (this.m_vecDataLookupValues != null)
/*      */     {
/* 1444 */       for (IdValueBean pair : this.m_vecDataLookupValues)
/*      */       {
/* 1446 */         if ((pair.getId() == a_lAttributeId) && (pair.getValue() != null))
/*      */         {
/* 1448 */           return pair.getValue();
/*      */         }
/*      */       }
/*      */     }
/* 1452 */     return sValue;
/*      */   }
/*      */ 
/*      */   public void addInfo(String a_sInfo)
/*      */   {
/* 1457 */     if (this.m_vecInfo == null)
/*      */     {
/* 1459 */       this.m_vecInfo = new Vector();
/*      */     }
/* 1461 */     this.m_vecInfo.add(a_sInfo);
/*      */   }
/*      */ 
/*      */   public boolean getHasInfo()
/*      */   {
/* 1466 */     return (this.m_vecInfo != null) && (this.m_vecInfo.size() > 0);
/*      */   }
/*      */ 
/*      */   public boolean getHasErrors()
/*      */   {
/* 1471 */     return (super.getHasErrors()) || (getHasInfo());
/*      */   }
/*      */ 
/*      */   public Vector<String> getInfo()
/*      */   {
/* 1476 */     return this.m_vecInfo;
/*      */   }
/*      */ 
/*      */   public boolean getAllWorkflowOptionsAreHidden()
/*      */   {
/* 1481 */     if (this.m_vecWorkflowOptions != null)
/*      */     {
/* 1483 */       for (int i = 0; i < this.m_vecWorkflowOptions.size(); i++)
/*      */       {
/* 1485 */         SubmitOptionGroup group = (SubmitOptionGroup)this.m_vecWorkflowOptions.elementAt(i);
/* 1486 */         if (group.getOptions().getOptionCount() > 0)
/*      */         {
/* 1488 */           return false;
/*      */         }
/*      */       }
/*      */     }
/* 1492 */     return true;
/*      */   }
/*      */ 
/*      */   public int getNoOfWorkflowOptions()
/*      */   {
/* 1497 */     if (getWorkflowOptions() != null)
/*      */     {
/* 1499 */       return getWorkflowOptions().size();
/*      */     }
/* 1501 */     return 0;
/*      */   }
/*      */ 
/*      */   public void setWorkflowUpdate(WorkflowUpdate a_workflowUpdate)
/*      */   {
/* 1507 */     this.m_workflowUpdate = a_workflowUpdate;
/*      */   }
/*      */ 
/*      */   private WorkflowUpdate getWorkflowUpdate()
/*      */   {
/* 1512 */     return this.m_workflowUpdate;
/*      */   }
/*      */ 
/*      */   public int getSelectedWorkflowLevel(String a_sName)
/*      */   {
/* 1517 */     if (getWorkflowUpdate() != null)
/*      */     {
/* 1519 */       if ((getWorkflowUpdate() != null) && (getWorkflowUpdate().getWorkflowApprovalUpdates() != null))
/*      */       {
/* 1521 */         Integer intLevel = (Integer)getWorkflowUpdate().getWorkflowApprovalUpdates().get(a_sName);
/* 1522 */         if (intLevel != null)
/*      */         {
/* 1524 */           return intLevel.intValue();
/*      */         }
/*      */       }
/*      */     }
/* 1528 */     return -1;
/*      */   }
/*      */ 
/*      */   public void validateSubmitOptions(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, HttpServletRequest a_request, CategoryManager a_categoryManager, ListManager a_listManager, boolean a_bInitialisingWorkflows)
/*      */     throws Bn2Exception
/*      */   {
/* 1534 */     if (getAsset().getId() <= 0L)
/*      */     {
/* 1537 */       if (getSelectedSubmitOption() < 0)
/*      */       {
/* 1539 */         addError(a_listManager.getListItem(a_dbTransaction, "submitOption", a_userProfile.getCurrentLanguage()).getBody());
/*      */       }
/*      */     }
/* 1542 */     else if (a_bInitialisingWorkflows)
/*      */     {
/* 1545 */       Vector vecMissingWorkflows = getAsset().getMissingWorkflows();
/* 1546 */       String sParam = "selectedSubmitOption_";
/* 1547 */       for (int i = 0; i < vecMissingWorkflows.size(); i++)
/*      */       {
/* 1549 */         String sWorkflowName = (String)vecMissingWorkflows.elementAt(i);
/* 1550 */         if (a_request.getParameter(sParam + sWorkflowName) != null)
/*      */           continue;
/* 1552 */         addError(a_listManager.getListItem(a_dbTransaction, "submitOptionAllWorkflows", a_userProfile.getCurrentLanguage()).getBody());
/* 1553 */         break;
/*      */       }
/*      */ 
/*      */     }
/* 1557 */     else if ((getAsset().getId() > 0L) && (getAsset().getIsUnsubmitted()))
/*      */     {
/* 1559 */       if (getSelectedSubmitOption() != 2)
/*      */       {
/* 1563 */         if ((getPermissionCategoryForm() != null) && (getPermissionCategoryForm().getSelectedCategories() != null))
/*      */         {
/* 1565 */           for (int i = 0; i < getPermissionCategoryForm().getSelectedCategories().length; i++)
/*      */           {
/* 1567 */             Category cat = a_categoryManager.getCategory(a_dbTransaction, 2L, Long.parseLong(getPermissionCategoryForm().getSelectedCategories()[i]));
/* 1568 */             String sParam = "selectedSubmitOption_" + cat.getWorkflowName();
/* 1569 */             if (a_request.getParameter(sParam) != null)
/*      */               continue;
/* 1571 */             addError(a_listManager.getListItem(a_dbTransaction, "submitOptionAllWorkflows", a_userProfile.getCurrentLanguage()).getBody());
/* 1572 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setDefaultRelatedAssets(HashMap<Long, ArrayList<Asset>> a_hmDefaultRelatedAssets)
/*      */   {
/* 1585 */     this.m_hmDefaultRelatedAssets = a_hmDefaultRelatedAssets;
/*      */   }
/*      */ 
/*      */   public ArrayList<Asset> getChildDefaultRelatedAssets()
/*      */   {
/* 1590 */     if (this.m_hmDefaultRelatedAssets != null)
/*      */     {
/* 1592 */       return (ArrayList)this.m_hmDefaultRelatedAssets.get(new Long(2L));
/*      */     }
/* 1594 */     return null;
/*      */   }
/*      */ 
/*      */   public ArrayList<Asset> getPeerDefaultRelatedAssets()
/*      */   {
/* 1599 */     if (this.m_hmDefaultRelatedAssets != null)
/*      */     {
/* 1601 */       return (ArrayList)this.m_hmDefaultRelatedAssets.get(new Long(1L));
/*      */     }
/* 1603 */     return null;
/*      */   }
/*      */ 
/*      */   public void preselectAllDefaultRelatedAssets()
/*      */   {
/* 1609 */     ArrayList alPotentialChildren = getChildDefaultRelatedAssets();
/* 1610 */     ArrayList alPotentialPeers = getPeerDefaultRelatedAssets();
/*      */ 
/* 1612 */     if (alPotentialChildren != null)
/*      */     {
/* 1614 */       this.m_aChildAssetIdArray = new long[alPotentialChildren.size()];
/* 1615 */       for (int i = 0; i < this.m_aChildAssetIdArray.length; i++)
/*      */       {
/* 1617 */         this.m_aChildAssetIdArray[i] = ((LightweightAsset)alPotentialChildren.get(i)).getId();
/*      */       }
/*      */     }
/*      */ 
/* 1621 */     if (alPotentialPeers != null)
/*      */     {
/* 1623 */       this.m_aPeerAssetIdArray = new long[alPotentialPeers.size()];
/* 1624 */       for (int i = 0; i < this.m_aPeerAssetIdArray.length; i++)
/*      */       {
/* 1626 */         this.m_aPeerAssetIdArray[i] = ((LightweightAsset)alPotentialPeers.get(i)).getId();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public FileInStorageDevice getFileInStorageDevice()
/*      */   {
/* 1634 */     return this.m_fileInStorageDevice;
/*      */   }
/*      */ 
/*      */   public void setFileInStorageDevice(FileInStorageDevice a_fileInStorageDevice)
/*      */   {
/* 1640 */     this.m_fileInStorageDevice = a_fileInStorageDevice;
/*      */   }
/*      */ 
/*      */   public String getReturnUrl()
/*      */   {
/* 1645 */     return this.m_sReturnUrl;
/*      */   }
/*      */ 
/*      */   public void setReturnUrl(String a_sReturnUrl)
/*      */   {
/* 1650 */     this.m_sReturnUrl = a_sReturnUrl;
/*      */   }
/*      */ 
/*      */   public long getExpectedAssetId()
/*      */   {
/* 1655 */     return this.m_lExpectedAssetId;
/*      */   }
/*      */ 
/*      */   public void setExpectedAssetId(long a_lExpectedAssetId)
/*      */   {
/* 1660 */     this.m_lExpectedAssetId = a_lExpectedAssetId;
/*      */   }
/*      */ 
/*      */   public void setRedirectedFromDownloadPage(boolean a_bRedirectedFromDownloadPage)
/*      */   {
/* 1665 */     this.m_bRedirectedFromDownloadPage = a_bRedirectedFromDownloadPage;
/*      */   }
/*      */ 
/*      */   public boolean getRedirectedFromDownloadPage()
/*      */   {
/* 1670 */     return this.m_bRedirectedFromDownloadPage;
/*      */   }
/*      */ 
/*      */   public void setChildAssetIdArray(long[] a_aChildAssetIdArray)
/*      */   {
/* 1675 */     this.m_aChildAssetIdArray = a_aChildAssetIdArray;
/*      */   }
/*      */ 
/*      */   public long[] getChildAssetIdArray()
/*      */   {
/* 1680 */     return this.m_aChildAssetIdArray;
/*      */   }
/*      */ 
/*      */   public void setPeerAssetIdArray(long[] a_aPeerAssetIdArray)
/*      */   {
/* 1685 */     this.m_aPeerAssetIdArray = a_aPeerAssetIdArray;
/*      */   }
/*      */ 
/*      */   public long[] getPeerAssetIdArray()
/*      */   {
/* 1690 */     return this.m_aPeerAssetIdArray;
/*      */   }
/*      */ 
/*      */   public void setParentAssetIdArray(long[] a_aParentAssetIdArray)
/*      */   {
/* 1695 */     this.m_aParentAssetIdArray = a_aParentAssetIdArray;
/*      */   }
/*      */ 
/*      */   public long[] getParentAssetIdArray()
/*      */   {
/* 1700 */     return this.m_aParentAssetIdArray;
/*      */   }
/*      */ 
/*      */   public long getCatExtensionCatId()
/*      */   {
/* 1706 */     if ((this.m_asset != null) && (this.m_asset.getExtendsCategory() != null) && (this.m_asset.getExtendsCategory().getId() > 0L))
/*      */     {
/* 1710 */       return this.m_asset.getExtendsCategory().getId();
/*      */     }
/*      */ 
/* 1713 */     return this.m_lCatExtensionCatId;
/*      */   }
/*      */ 
/*      */   public void setCatExtensionCatId(long a_lCatExtensionCatId)
/*      */   {
/* 1718 */     this.m_lCatExtensionCatId = a_lCatExtensionCatId;
/*      */   }
/*      */ 
/*      */   public long getCatExtensionParentId()
/*      */   {
/* 1724 */     if ((this.m_asset != null) && (this.m_asset.getExtendsCategory() != null) && (this.m_asset.getExtendsCategory().getParentId() > 0L))
/*      */     {
/* 1728 */       return this.m_asset.getExtendsCategory().getParentId();
/*      */     }
/*      */ 
/* 1731 */     return this.m_lCatExtensionParentId;
/*      */   }
/*      */ 
/*      */   public void setCatExtensionParentId(long a_lCatExtensionParentId)
/*      */   {
/* 1736 */     this.m_lCatExtensionParentId = a_lCatExtensionParentId;
/*      */   }
/*      */ 
/*      */   public long getCatExtensionTreeId()
/*      */   {
/* 1741 */     if ((this.m_asset != null) && (this.m_asset.getExtendsCategory() != null) && (this.m_asset.getExtendsCategory().getCategoryTypeId() > 0L))
/*      */     {
/* 1745 */       return this.m_asset.getExtendsCategory().getCategoryTypeId();
/*      */     }
/*      */ 
/* 1748 */     return this.m_lCatExtensionTreeId;
/*      */   }
/*      */ 
/*      */   public void setCatExtensionTreeId(long a_lCatExtensionTreeId)
/*      */   {
/* 1754 */     this.m_lCatExtensionTreeId = a_lCatExtensionTreeId;
/*      */   }
/*      */ 
/*      */   public void setCatExtensionReturnLocation(String a_sCatExtensionReturnLocation)
/*      */   {
/* 1759 */     this.m_sCatExtensionReturnLocation = a_sCatExtensionReturnLocation;
/*      */   }
/*      */ 
/*      */   public String getCatExtensionReturnLocation()
/*      */   {
/* 1764 */     return this.m_sCatExtensionReturnLocation;
/*      */   }
/*      */ 
/*      */   public void setAddingFromBrowseCatId(long a_lAddingFromBrowseCatId)
/*      */   {
/* 1769 */     this.m_lAddingFromBrowseCatId = a_lAddingFromBrowseCatId;
/*      */   }
/*      */ 
/*      */   public long getAddingFromBrowseCatId()
/*      */   {
/* 1774 */     return this.m_lAddingFromBrowseCatId;
/*      */   }
/*      */ 
/*      */   public void setAddingFromBrowseTreeId(long a_lAddingFromBrowseTreeId)
/*      */   {
/* 1779 */     this.m_lAddingFromBrowseTreeId = a_lAddingFromBrowseTreeId;
/*      */   }
/*      */ 
/*      */   public long getAddingFromBrowseTreeId()
/*      */   {
/* 1784 */     return this.m_lAddingFromBrowseTreeId;
/*      */   }
/*      */ 
/*      */   public void setEnableSaveAsNewVersion(boolean a_bEnableSaveAsNewVersion)
/*      */   {
/* 1791 */     this.m_bEnableSaveAsNewVersion = a_bEnableSaveAsNewVersion;
/*      */   }
/*      */ 
/*      */   public boolean getEnableSaveAsNewVersion()
/*      */   {
/* 1796 */     return this.m_bEnableSaveAsNewVersion;
/*      */   }
/*      */ 
/*      */   public Vector<LightweightAsset> getChildAssets()
/*      */   {
/* 1818 */     return this.m_vChildAssets;
/*      */   }
/*      */ 
/*      */   public void setChildAssets(Vector<LightweightAsset> childAssets)
/*      */   {
/* 1823 */     this.m_vChildAssets = childAssets;
/*      */   }
/*      */ 
/*      */   public Vector<LightweightAsset> getSiblingAssets()
/*      */   {
/* 1828 */     return this.m_vSiblingAssets;
/*      */   }
/*      */ 
/*      */   public void setSiblingAssets(Vector<LightweightAsset> a_vSiblingAssets)
/*      */   {
/* 1833 */     this.m_vSiblingAssets = a_vSiblingAssets;
/*      */   }
/*      */ 
/*      */   public Vector<? extends LightweightAsset> getParentAssets()
/*      */   {
/* 1838 */     return this.m_vParentAssets;
/*      */   }
/*      */ 
/*      */   public void setParentAssets(Vector<? extends LightweightAsset> parentAssets)
/*      */   {
/* 1843 */     this.m_vParentAssets = parentAssets;
/*      */   }
/*      */ 
/*      */   public void setPeerAssets(Vector<LightweightAsset> a_vPeerAssets)
/*      */   {
/* 1848 */     this.m_vPeerAssets = a_vPeerAssets;
/*      */   }
/*      */ 
/*      */   public Vector<LightweightAsset> getPeerAssets()
/*      */   {
/* 1853 */     return this.m_vPeerAssets;
/*      */   }
/*      */ 
/*      */   public void setGroupedPeerAssets(HashMap<String, Vector<LightweightAsset>> a_hmGroupedPeerAssets)
/*      */   {
/* 1858 */     this.m_hmGroupedPeerAssets = a_hmGroupedPeerAssets;
/*      */   }
/*      */ 
/*      */   public HashMap<String, Vector<LightweightAsset>> getGroupedPeerAssets()
/*      */   {
/* 1863 */     return this.m_hmGroupedPeerAssets;
/*      */   }
/*      */ 
/*      */   public Vector<String> getPeerAssetCategories()
/*      */   {
/* 1868 */     if (this.m_vPeerAssetCategories == null)
/*      */     {
/* 1870 */       if (getGroupedPeerAssets() != null)
/*      */       {
/* 1872 */         Set keys = getGroupedPeerAssets().keySet();
/* 1873 */         Iterator iterator = keys.iterator();
/*      */ 
/* 1875 */         while (iterator.hasNext())
/*      */         {
/* 1877 */           if (this.m_vPeerAssetCategories == null)
/*      */           {
/* 1879 */             this.m_vPeerAssetCategories = new Vector();
/*      */           }
/* 1881 */           this.m_vPeerAssetCategories.add((String)iterator.next());
/*      */         }
/*      */       }
/*      */     }
/* 1885 */     return this.m_vPeerAssetCategories;
/*      */   }
/*      */ 
/*      */   public Vector<LightweightAsset> getPeerAssetsForCategory(String a_sCatName)
/*      */   {
/* 1891 */     if (getGroupedPeerAssets() != null)
/*      */     {
/* 1893 */       return (Vector)getGroupedPeerAssets().get(a_sCatName);
/*      */     }
/* 1895 */     return null;
/*      */   }
/*      */ 
/*      */   public HashMap<String, Vector<LightweightAsset>> getGroupedChildAssets()
/*      */   {
/* 1900 */     return this.m_hmGroupedChildAssets;
/*      */   }
/*      */ 
/*      */   public void setGroupedChildAssets(HashMap<String, Vector<LightweightAsset>> a_hmGroupedChildAssets)
/*      */   {
/* 1905 */     this.m_hmGroupedChildAssets = a_hmGroupedChildAssets;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.form.AssetForm
 * JD-Core Version:    0.6.0
 */