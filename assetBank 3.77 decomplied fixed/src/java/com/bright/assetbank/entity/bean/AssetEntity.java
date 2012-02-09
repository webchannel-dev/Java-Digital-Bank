/*     */ package com.bright.assetbank.entity.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.entity.constant.AssetEntityConstants;
/*     */ import com.bright.assetbank.entity.relationship.bean.AssetEntityRelationship;
/*     */ import com.bright.assetbank.plugin.bean.ABExtensibleBean;
/*     */ import com.bright.assetbank.plugin.bean.ABExtensibleBeanHelper;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.TranslatableWithLanguage;
/*     */ import com.bright.framework.language.bean.Translation;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class AssetEntity extends BaseAssetEntity
/*     */   implements TranslatableWithLanguage, ABExtensibleBean, AssetEntityConstants
/*     */ {
/*  51 */   private ABExtensibleBeanHelper m_extensibleBeanHelper = new ABExtensibleBeanHelper();
/*     */ 
/*  54 */   private Vector<Long> m_vAllowableAttributes = null;
/*  55 */   private Vector<Long> m_vAllowableAssetTypes = null;
/*  56 */   private boolean m_bAllowChildren = false;
/*  57 */   private boolean m_bAllowPeers = false;
/*  58 */   private boolean m_bIsSearchable = false;
/*  59 */   private boolean m_bIsQuickSearchable = false;
/*  60 */   private boolean m_bAllowAssetFiles = false;
/*  61 */   private String m_sIncludedFileFormats = null;
/*  62 */   private String m_sExcludedFileFormats = null;
/*  63 */   private boolean m_bMustHaveParent = false;
/*  64 */   private String m_sThumbnailFilename = null;
/*  65 */   private boolean m_bCanCopyAssets = false;
/*  66 */   private long m_lDefaultCategoryId = 0L;
/*  67 */   private long m_lUnrestrictedAgreementId = 0L;
/*  68 */   private long m_lRestrictedAgreementId = 0L;
/*  69 */   private boolean m_bShowAttributeLabels = false;
/*  70 */   private boolean m_bCanDownloadChildrenFromParent = false;
/*  71 */   private boolean m_bShowOnDescendantCategories = false;
/*  72 */   private boolean m_bCategoryExtension = false;
/*     */ 
/*  74 */   private ArrayList<AssetEntityRelationship> m_alChildRelationships = null;
/*  75 */   private ArrayList<AssetEntityRelationship> m_alPeerRelationships = null;
/*     */ 
/* 167 */   private long m_lMatchOnAttributeId = 0L;
/*     */ 
/* 169 */   private Vector<Translation> m_vTranslations = null;
            private Language m_language;
/*     */ 
/*     */   public void setExtensionData(String a_sExtensionKey, Serializable a_extensionData)
/*     */   {
/*  79 */     this.m_extensibleBeanHelper.setExtensionData(a_sExtensionKey, a_extensionData);
/*     */   }
/*     */ 
/*     */   public Serializable getExtensionData(String a_sExtensionKey)
/*     */   {
/*  84 */     return this.m_extensibleBeanHelper.getExtensionData(a_sExtensionKey);
/*     */   }
/*     */ 
/*     */   public String getChildRelationshipToName()
/*     */   {
/*  89 */     if ((this.m_language != null) && (!this.m_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/*  91 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/*  92 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getChildRelationshipToName())))
/*     */       {
/*  94 */         return translation.getChildRelationshipToName();
/*     */       }
/*     */     }
/*  97 */     return super.getChildRelationshipToName();
/*     */   }
/*     */ 
/*     */   public String getChildRelationshipToNamePlural()
/*     */   {
/* 102 */     if ((this.m_language != null) && (!this.m_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/* 104 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/* 105 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getChildRelationshipToNamePlural())))
/*     */       {
/* 107 */         return translation.getChildRelationshipToNamePlural();
/*     */       }
/*     */     }
/* 110 */     return super.getChildRelationshipToNamePlural();
/*     */   }
/*     */ 
/*     */   public String getChildRelationshipFromName()
/*     */   {
/* 115 */     if ((this.m_language != null) && (!this.m_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/* 117 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/* 118 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getChildRelationshipFromName())))
/*     */       {
/* 120 */         return translation.getChildRelationshipFromName();
/*     */       }
/*     */     }
/* 123 */     return super.getChildRelationshipFromName();
/*     */   }
/*     */ 
/*     */   public String getChildRelationshipFromNamePlural()
/*     */   {
/* 129 */     if ((this.m_language != null) && (!this.m_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/* 131 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/* 132 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getChildRelationshipFromNamePlural())))
/*     */       {
/* 134 */         return translation.getChildRelationshipFromNamePlural();
/*     */       }
/*     */     }
/* 137 */     return super.getChildRelationshipFromNamePlural();
/*     */   }
/*     */ 
/*     */   public String getPeerRelationshipToName()
/*     */   {
/* 142 */     if ((this.m_language != null) && (!this.m_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/* 144 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/* 145 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getPeerRelationshipToName())))
/*     */       {
/* 147 */         return translation.getPeerRelationshipToName();
/*     */       }
/*     */     }
/* 150 */     return super.getPeerRelationshipToName();
/*     */   }
/*     */ 
/*     */   public String getPeerRelationshipToNamePlural()
/*     */   {
/* 155 */     if ((this.m_language != null) && (!this.m_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/* 157 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/* 158 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getPeerRelationshipToNamePlural())))
/*     */       {
/* 160 */         return translation.getPeerRelationshipToNamePlural();
/*     */       }
/*     */     }
/* 163 */     return super.getPeerRelationshipToNamePlural();
/*     */   }
/*     */ 
/*     */   public AssetEntity()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AssetEntity(long a_lId)
/*     */   {
/* 178 */     setId(a_lId);
/*     */   }
/*     */ 
/*     */   public AssetEntity(long a_lId, String a_sName)
/*     */   {
/* 183 */     this(a_lId);
/* 184 */     setName(a_sName);
/*     */   }
/*     */ 
/*     */   public Vector<Long> getAllowableAttributes()
/*     */   {
/* 189 */     return this.m_vAllowableAttributes;
/*     */   }
/*     */ 
/*     */   public void setAllowableAttributes(Vector<Long> allowableAttributes)
/*     */   {
/* 194 */     this.m_vAllowableAttributes = allowableAttributes;
/*     */   }
/*     */ 
/*     */   public boolean getAllowChildren()
/*     */   {
/* 199 */     return this.m_bAllowChildren;
/*     */   }
/*     */ 
/*     */   public boolean getHasNonExtensionAssetChildren()
/*     */     throws Bn2Exception
/*     */   {
/* 205 */     return checkHasNonExtensionAssetRelationships(getChildRelationships());
/*     */   }
/*     */ 
/*     */   public void setAllowChildren(boolean a_bAllowChildren)
/*     */   {
/* 210 */     this.m_bAllowChildren = a_bAllowChildren;
/*     */   }
/*     */ 
/*     */   public boolean getAllowPeers()
/*     */   {
/* 215 */     return this.m_bAllowPeers;
/*     */   }
/*     */ 
/*     */   public void setAllowPeers(boolean a_bAllowPeers)
/*     */   {
/* 220 */     this.m_bAllowPeers = a_bAllowPeers;
/*     */   }
/*     */ 
/*     */   public boolean getHasNonExtensionAssetPeers()
/*     */     throws Bn2Exception
/*     */   {
/* 226 */     return checkHasNonExtensionAssetRelationships(getPeerRelationships());
/*     */   }
/*     */ 
/*     */   private boolean checkHasNonExtensionAssetRelationships(ArrayList<AssetEntityRelationship> a_alRels)
/*     */     throws Bn2Exception
/*     */   {
/* 239 */     if (a_alRels != null)
/*     */     {
/* 241 */       for (AssetEntityRelationship rel : a_alRels)
/*     */       {
/* 243 */         if (rel.getRelatesToCategoryExtensionType() == null)
/*     */         {
/* 245 */           throw new Bn2Exception(getClass().getSimpleName() + ".Unable to check for non extension asset relationships, relationships not initialised: ");
/*     */         }
/* 247 */         if (!rel.getRelatesToCategoryExtensionType().booleanValue())
/*     */         {
/* 249 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 253 */     return false;
/*     */   }
/*     */ 
/*     */   public ArrayList<AssetEntityRelationship> getChildRelationships()
/*     */   {
/* 258 */     return this.m_alChildRelationships;
/*     */   }
/*     */ 
/*     */   public void setChildRelationships(ArrayList<AssetEntityRelationship> a_alChildRelationships)
/*     */   {
/* 263 */     this.m_alChildRelationships = a_alChildRelationships;
/*     */   }
/*     */ 
/*     */   public void addChildRelationship(AssetEntityRelationship childRelationship)
/*     */   {
/* 268 */     if (this.m_alChildRelationships == null)
/*     */     {
/* 270 */       this.m_alChildRelationships = new ArrayList();
/*     */     }
/* 272 */     this.m_alChildRelationships.add(childRelationship);
/*     */   }
/*     */ 
/*     */   public ArrayList<AssetEntityRelationship> getPeerRelationships()
/*     */   {
/* 277 */     return this.m_alPeerRelationships;
/*     */   }
/*     */ 
/*     */   public void setPeerRelationships(ArrayList<AssetEntityRelationship> a_alPeerRelationships)
/*     */   {
/* 282 */     this.m_alPeerRelationships = a_alPeerRelationships;
/*     */   }
/*     */ 
/*     */   public void addPeerRelationship(AssetEntityRelationship peerRelationship)
/*     */   {
/* 287 */     if (this.m_alPeerRelationships == null)
/*     */     {
/* 289 */       this.m_alPeerRelationships = new ArrayList();
/*     */     }
/* 291 */     this.m_alPeerRelationships.add(peerRelationship);
/*     */   }
/*     */ 
/*     */   public boolean isValidChildRelationship(long a_lId)
/*     */   {
/* 296 */     return isValidRelationship(a_lId, getChildRelationships());
/*     */   }
/*     */ 
/*     */   public boolean isValidPeerRelationship(long a_lId)
/*     */   {
/* 301 */     return isValidRelationship(a_lId, getPeerRelationships());
/*     */   }
/*     */ 
/*     */   private boolean isValidRelationship(long a_lId, ArrayList<AssetEntityRelationship> a_alRels)
/*     */   {
/* 306 */     if (a_alRels != null)
/*     */     {
/* 308 */       for (AssetEntityRelationship aer : a_alRels)
/*     */       {
/* 310 */         if (aer.getCanRelateToAssetEntity(a_lId))
/*     */         {
/* 312 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 316 */     return false;
/*     */   }
/*     */ 
/*     */   public long[] getChildRelationshipIds()
/*     */   {
/* 321 */     return getIdsFromRelationships(getChildRelationships());
/*     */   }
/*     */ 
/*     */   public long[] getPeerRelationshipIds()
/*     */   {
/* 326 */     return getIdsFromRelationships(getPeerRelationships());
/*     */   }
/*     */ 
/*     */   private long[] getIdsFromRelationships(ArrayList<AssetEntityRelationship> a_alRels)
/*     */   {
/* 331 */     long[] alIds = null;
/*     */     int iIndex;
/* 333 */     if (a_alRels != null)
/*     */     {
/* 335 */       alIds = new long[a_alRels.size()];
/* 336 */       iIndex = 0;
/* 337 */       for (AssetEntityRelationship aer : a_alRels)
/*     */       {
/* 339 */         alIds[iIndex] = aer.getRelatesToAssetEntityId();
/* 340 */         iIndex++;
/*     */       }
/*     */     }
/*     */ 
/* 344 */     return alIds;
/*     */   }
/*     */ 
/*     */   public boolean getAllowAudio()
/*     */   {
/* 349 */     return (this.m_vAllowableAssetTypes != null) && (this.m_vAllowableAssetTypes.contains(Long.valueOf(4L)));
/*     */   }
/*     */ 
/*     */   public boolean getAllowFiles()
/*     */   {
/* 354 */     return (this.m_vAllowableAssetTypes != null) && (this.m_vAllowableAssetTypes.contains(Long.valueOf(1L)));
/*     */   }
/*     */ 
/*     */   public boolean getAllowImages()
/*     */   {
/* 359 */     return (this.m_vAllowableAssetTypes != null) && (this.m_vAllowableAssetTypes.contains(Long.valueOf(2L)));
/*     */   }
/*     */ 
/*     */   public boolean getAllowVideos()
/*     */   {
/* 364 */     return (this.m_vAllowableAssetTypes != null) && (this.m_vAllowableAssetTypes.contains(Long.valueOf(3L)));
/*     */   }
/*     */ 
/*     */   public boolean getAllowAssetFiles()
/*     */   {
/* 374 */     return this.m_bAllowAssetFiles;
/*     */   }
/*     */ 
/*     */   public void setAllowAssetFiles(boolean allowAssetFiles)
/*     */   {
/* 379 */     this.m_bAllowAssetFiles = allowAssetFiles;
/*     */   }
/*     */ 
/*     */   public boolean getAllowAssetType(long a_lTypeId)
/*     */   {
/* 384 */     return (this.m_vAllowableAssetTypes != null) && (this.m_vAllowableAssetTypes.contains(Long.valueOf(a_lTypeId)));
/*     */   }
/*     */ 
/*     */   public Vector<Long> getAllowableAssetTypes()
/*     */   {
/* 393 */     return this.m_vAllowableAssetTypes;
/*     */   }
/*     */ 
/*     */   public void setAllowableAssetTypes(Vector<Long> allowableAssetTypes)
/*     */   {
/* 398 */     this.m_vAllowableAssetTypes = allowableAssetTypes;
/*     */ 
/* 400 */     this.m_bAllowAssetFiles = ((this.m_vAllowableAssetTypes != null) && (this.m_vAllowableAssetTypes.size() > 0));
/*     */   }
/*     */ 
/*     */   public String getTermForSiblings()
/*     */   {
/* 405 */     if ((this.m_language != null) && (!this.m_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/* 407 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/* 408 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getTermForSiblings())))
/*     */       {
/* 410 */         return translation.getTermForSiblings();
/*     */       }
/*     */     }
/* 413 */     return super.getTermForSiblings();
/*     */   }
/*     */ 
/*     */   public String getTermForSibling()
/*     */   {
/* 418 */     if ((this.m_language != null) && (!this.m_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/* 420 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/* 421 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getTermForSibling())))
/*     */       {
/* 423 */         return translation.getTermForSibling();
/*     */       }
/*     */     }
/* 426 */     return super.getTermForSibling();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 431 */     if ((this.m_language != null) && (!this.m_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/* 433 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/* 434 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getName())))
/*     */       {
/* 436 */         return translation.getName();
/*     */       }
/*     */     }
/* 439 */     return super.getName();
/*     */   }
/*     */ 
/*     */   public Translation createTranslation(Language a_language)
/*     */   {
/* 444 */     return new Translation(a_language);
/*     */   }
/*     */ 
/*     */   public Vector<Translation> getTranslations()
/*     */   {
/* 449 */     if (this.m_vTranslations == null)
/*     */     {
/* 451 */       this.m_vTranslations = new Vector();
/*     */     }
/* 453 */     return this.m_vTranslations;
/*     */   }
/*     */ 
/*     */   public void setTranslations(Vector translations)
/*     */   {
/* 458 */     this.m_vTranslations = translations;
/*     */   }
/*     */ 
/*     */   public boolean isSearchable()
/*     */   {
/* 476 */     return this.m_bIsSearchable;
/*     */   }
/*     */ 
/*     */   public void setSearchable(boolean isSearchable)
/*     */   {
/* 481 */     this.m_bIsSearchable = isSearchable;
/*     */   }
/*     */ 
/*     */   public void setLanguage(Language a_language)
/*     */   {
/* 486 */     super.setLanguage(a_language);
/*     */   }
/*     */ 
/*     */   public String getExcludedFileFormats()
/*     */   {
/* 491 */     return this.m_sExcludedFileFormats;
/*     */   }
/*     */ 
/*     */   public void setExcludedFileFormats(String excludedFileFormats)
/*     */   {
/* 496 */     this.m_sExcludedFileFormats = excludedFileFormats;
/*     */   }
/*     */ 
/*     */   public String getIncludedFileFormats()
/*     */   {
/* 501 */     return this.m_sIncludedFileFormats;
/*     */   }
/*     */ 
/*     */   public void setIncludedFileFormats(String includedFileFormats)
/*     */   {
/* 506 */     this.m_sIncludedFileFormats = includedFileFormats;
/*     */   }
/*     */ 
/*     */   public boolean isQuickSearchable()
/*     */   {
/* 511 */     return this.m_bIsQuickSearchable;
/*     */   }
/*     */ 
/*     */   public void setQuickSearchable(boolean isQuickSearchable)
/*     */   {
/* 516 */     this.m_bIsQuickSearchable = isQuickSearchable;
/*     */   }
/*     */ 
/*     */   public boolean getMustHaveParent()
/*     */   {
/* 521 */     return this.m_bMustHaveParent;
/*     */   }
/*     */ 
/*     */   public void setMustHaveParent(boolean mustHaveParent)
/*     */   {
/* 526 */     this.m_bMustHaveParent = mustHaveParent;
/*     */   }
/*     */ 
/*     */   public String getCompactName()
/*     */   {
/* 531 */     String sVal = null;
/*     */ 
/* 533 */     if (StringUtils.isNotEmpty(super.getName()))
/*     */     {
/* 535 */       sVal = super.getName().trim().toLowerCase().replaceAll("[ '\"\\(\\)&\\-]+", "");
/*     */     }
/* 537 */     return sVal;
/*     */   }
/*     */ 
/*     */   public String getThumbnailFilename()
/*     */   {
/* 542 */     return this.m_sThumbnailFilename;
/*     */   }
/*     */ 
/*     */   public void setThumbnailFilename(String iconFilename)
/*     */   {
/* 547 */     this.m_sThumbnailFilename = iconFilename;
/*     */   }
/*     */ 
/*     */   public boolean getIncludesAttribute(long a_lAttributeId)
/*     */   {
/* 552 */     if ((this.m_vAllowableAttributes != null) && (this.m_vAllowableAttributes.size() > 0))
/*     */     {
/* 554 */       return this.m_vAllowableAttributes.contains(Long.valueOf(a_lAttributeId));
/*     */     }
/* 556 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getCanCopyAssets()
/*     */   {
/* 561 */     return this.m_bCanCopyAssets;
/*     */   }
/*     */ 
/*     */   public void setCanCopyAssets(boolean a_canCopyAssets)
/*     */   {
/* 566 */     this.m_bCanCopyAssets = a_canCopyAssets;
/*     */   }
/*     */ 
/*     */   public long getDefaultCategoryId()
/*     */   {
/* 571 */     return this.m_lDefaultCategoryId;
/*     */   }
/*     */ 
/*     */   public void setDefaultCategoryId(long defaultCategoryId)
/*     */   {
/* 576 */     this.m_lDefaultCategoryId = defaultCategoryId;
/*     */   }
/*     */ 
/*     */   public long getUnrestrictedAgreementId()
/*     */   {
/* 581 */     return this.m_lUnrestrictedAgreementId;
/*     */   }
/*     */ 
/*     */   public void setUnrestrictedAgreementId(long a_iUnrestrictedAgreementId)
/*     */   {
/* 586 */     this.m_lUnrestrictedAgreementId = a_iUnrestrictedAgreementId;
/*     */   }
/*     */ 
/*     */   public long getRestrictedAgreementId()
/*     */   {
/* 591 */     return this.m_lRestrictedAgreementId;
/*     */   }
/*     */ 
/*     */   public void setRestrictedAgreementId(long a_iRestrictedAgreementId)
/*     */   {
/* 596 */     this.m_lRestrictedAgreementId = a_iRestrictedAgreementId;
/*     */   }
/*     */ 
/*     */   public boolean getShowAttributeLabels()
/*     */   {
/* 601 */     return this.m_bShowAttributeLabels;
/*     */   }
/*     */ 
/*     */   public void setShowAttributeLabels(boolean a_iShowAttributeLabels)
/*     */   {
/* 606 */     this.m_bShowAttributeLabels = a_iShowAttributeLabels;
/*     */   }
/*     */ 
/*     */   public boolean getCanDownloadChildrenFromParent()
/*     */   {
/* 611 */     return this.m_bCanDownloadChildrenFromParent;
/*     */   }
/*     */ 
/*     */   public void setCanDownloadChildrenFromParent(boolean a_bCanDownloadChildrenFromParent)
/*     */   {
/* 616 */     this.m_bCanDownloadChildrenFromParent = a_bCanDownloadChildrenFromParent;
/*     */   }
/*     */ 
/*     */   public long getMatchOnAttributeId()
/*     */   {
/* 621 */     return this.m_lMatchOnAttributeId;
/*     */   }
/*     */ 
/*     */   public void setMatchOnAttributeId(long a_sMatchOnAttributeId)
/*     */   {
/* 626 */     this.m_lMatchOnAttributeId = a_sMatchOnAttributeId;
/*     */   }
/*     */ 
/*     */   public void setShowOnDescendantCategories(boolean a_bShowOnDescendantCategories)
/*     */   {
/* 631 */     this.m_bShowOnDescendantCategories = a_bShowOnDescendantCategories;
/*     */   }
/*     */ 
/*     */   public boolean getShowOnDescendantCategories()
/*     */   {
/* 643 */     return this.m_bShowOnDescendantCategories;
/*     */   }
/*     */ 
/*     */   public void setIsCategoryExtension(boolean a_bCategoryExtension)
/*     */   {
/* 648 */     this.m_bCategoryExtension = a_bCategoryExtension;
/*     */   }
/*     */ 
/*     */   public boolean getIsCategoryExtension()
/*     */   {
/* 663 */     return this.m_bCategoryExtension;
/*     */   }
/*     */ 
/*     */   public boolean getHasDefaultRelationship()
/*     */   {
/* 668 */     ArrayList<ArrayList<AssetEntityRelationship>> alRelationships = new ArrayList<ArrayList<AssetEntityRelationship>>();
/* 669 */     alRelationships.add(this.m_alChildRelationships);
/* 670 */     alRelationships.add(this.m_alPeerRelationships);
/*     */ 
/* 672 */     for (ArrayList<AssetEntityRelationship> alRels : alRelationships)
/*     */     {
/* 674 */       if (alRels != null)
/*     */       {
/* 676 */         for (AssetEntityRelationship aer : alRels)
/*     */         {
/* 678 */           if (aer.getDefaultRelationshipCategoryId() > 0L)
/*     */           {
/* 680 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 685 */     return false;
/*     */   }
/*     */ 
/*     */   public String getDefaultRelationshipCatIdForChild(String a_sEntityId)
/*     */   {
/* 690 */     return getDefaultRelaitonshipCatId(a_sEntityId, 2);
/*     */   }
/*     */ 
/*     */   public String getDefaultRelationshipCatIdForPeer(String a_sEntityId)
/*     */   {
/* 696 */     return getDefaultRelaitonshipCatId(a_sEntityId, 1);
/*     */   }
/*     */ 
/*     */   private String getDefaultRelaitonshipCatId(String a_sEntityId, int a_iTypeId)
/*     */   {
/* 702 */     long lEntityId = Long.parseLong(a_sEntityId);
/* 703 */     ArrayList<AssetEntityRelationship> alRelationships = getRelationshipsForType(a_iTypeId);
/* 704 */     if (alRelationships != null)
/*     */     {
/* 706 */       for (AssetEntityRelationship aer : alRelationships)
/*     */       {
/* 708 */         if (aer.getRelatesToAssetEntityId() == lEntityId)
/*     */         {
/* 710 */           if (aer.getDefaultRelationshipCategoryId() > 0L)
/*     */           {
/* 712 */             return String.valueOf(aer.getDefaultRelationshipCategoryId());
/*     */           }
/* 714 */           return "";
/*     */         }
/*     */       }
/*     */     }
/* 718 */     return "";
/*     */   }
/*     */ 
/*     */   public String getRelationshipDescriptionLabelForChild(String a_sEntityId)
/*     */   {
/* 724 */     return getRelationshipDescriptionLabel(a_sEntityId, 2);
/*     */   }
/*     */ 
/*     */   public String getRelationshipDescriptionLabelForPeer(String a_sEntityId)
/*     */   {
/* 730 */     return getRelationshipDescriptionLabel(a_sEntityId, 1);
/*     */   }
/*     */ 
/*     */   private String getRelationshipDescriptionLabel(String a_sEntityId, int a_iTypeId)
/*     */   {
/* 736 */     long lEntityId = Long.parseLong(a_sEntityId);
/* 737 */     ArrayList<AssetEntityRelationship> alRelationships = getRelationshipsForType(a_iTypeId);
/*     */ 
/* 739 */     if (alRelationships != null)
/*     */     {
/* 741 */       for (AssetEntityRelationship aer : alRelationships)
/*     */       {
/* 743 */         if (aer.getRelatesToAssetEntityId() == lEntityId)
/*     */         {
/* 745 */           if (aer.getRelationshipDescriptionLabel() != null)
/*     */           {
/* 747 */             return aer.getRelationshipDescriptionLabel();
/*     */           }
/* 749 */           return "";
/*     */         }
/*     */       }
/*     */     }
/* 753 */     return "";
/*     */   }
/*     */ 
/*     */   private ArrayList<AssetEntityRelationship> getRelationshipsForType(int a_iType)
/*     */   {
/* 765 */     ArrayList alRelationships = null;
/* 766 */     switch (a_iType)
/*     */     {
/*     */     case 1:
/* 769 */       alRelationships = this.m_alPeerRelationships;
/* 770 */       break;
/*     */     case 2:
/* 772 */       alRelationships = this.m_alChildRelationships;
/*     */     }
/*     */ 
/* 775 */     return alRelationships;
/*     */   }
/*     */ 
/*     */   public class Translation extends BaseAssetEntity
/*     */     implements com.bright.framework.language.bean.Translation
/*     */   {
/*     */     public Translation(Language a_language)
/*     */     {
/* 465 */       this.m_language = a_language;
/*     */     }
/*     */ 
/*     */     public long getEntityId()
/*     */     {
/* 470 */       return AssetEntity.this.m_lId;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.bean.AssetEntity
 * JD-Core Version:    0.6.0
 */