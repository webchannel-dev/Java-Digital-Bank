/*     */ package com.bright.framework.category.bean;
/*     */ 
/*     */// import J;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.util.CategoryUtil;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.util.Assert;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class CategoryImpl extends BaseCategory
/*     */   implements CategoryConstants, Category
/*     */ {
/*  71 */   private long m_lId = 0L;
/*  72 */   private int m_iDepth = 0;
/*  73 */   private long m_lCategoryTypeId = 0L;
/*  74 */   private boolean m_bIsBrowsable = true;
/*  75 */   private String m_sIdentifier = null;
/*  76 */   private Vector<Category> m_vecChildCategories = null;
/*  77 */   private long[] m_alAllDescendantsIds = null;
/*  78 */   private Vector<Category> m_vecAncestors = null;
/*  79 */   private int m_iParentIndex = -1;
/*  80 */   private boolean m_bIsRestrictive = false;
/*  81 */   private boolean m_bIsListboxCategory = false;
/*  82 */   private boolean m_bCannotBeDeleted = false;
/*  83 */   private String m_sJSUnicodeName = null;
/*  84 */   private String m_sImageUrl = null;
/*  85 */   private boolean m_bSynchronised = false;
/*  86 */   private boolean m_bSelectedOnLoad = false;
/*  87 */   private boolean m_bAllowAdvancedOptions = true;
/*  88 */   private boolean m_bIsExpired = false;
/*  89 */   private String m_sWorkflowName = "default";
/*  90 */   private long m_lNumViews = 0L;
/*  91 */   private long m_lNumDownloads = 0L;
/*  92 */   private boolean m_bCanAssignIfNotLeaf = false;
/*  93 */   private boolean m_bImmutable = false;
/*  94 */   private boolean m_bAtLeastOneChildHasImage = false;
/*  95 */   private long m_lExtensionAssetId = -1L;
/*  96 */   private String m_sExtensionEntityId = null;
/*     */ 
/*  99 */   protected Vector m_vTranslations = null;
/*     */ 
/*     */   public CategoryImpl()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CategoryImpl(long a_lId, String a_sName)
/*     */   {
/* 108 */     setId(a_lId);
/* 109 */     setName(a_sName);
/*     */   }
/*     */ 
/*     */   public CategoryImpl(long a_lId)
/*     */   {
/* 114 */     setId(a_lId);
/*     */   }
/*     */ 
/*     */   public CategoryImpl(Category a_cat)
/*     */   {
/* 120 */     this.m_lId = a_cat.getId();
/* 121 */     this.m_iDepth = a_cat.getDepth();
/* 122 */     this.m_lCategoryTypeId = a_cat.getCategoryTypeId();
/* 123 */     this.m_bIsBrowsable = a_cat.getIsBrowsable();
/* 124 */     this.m_sName = a_cat.getName();
/* 125 */     this.m_sIdentifier = a_cat.getIdentifier();
/* 126 */     this.m_sSummary = a_cat.getSummary();
/* 127 */     this.m_sDescription = a_cat.getDescription();
/* 128 */     this.m_iParentIndex = a_cat.getParentIndex();
/* 129 */     this.m_bIsRestrictive = a_cat.getIsRestrictive();
/* 130 */     this.m_bCannotBeDeleted = a_cat.getCannotBeDeleted();
/* 131 */     this.m_bIsListboxCategory = a_cat.getIsListboxCategory();
/* 132 */     this.m_sImageUrl = a_cat.getImageUrl();
/* 133 */     this.m_bSelectedOnLoad = a_cat.getSelectedOnLoad();
/* 134 */     this.m_lExtensionAssetId = a_cat.getExtensionAssetId();
/* 135 */     this.m_sWorkflowName = a_cat.getWorkflowName();
/* 136 */     this.m_lNumViews = a_cat.getNumViews();
/* 137 */     this.m_lNumDownloads = a_cat.getNumDownloads();
/* 138 */     this.m_bSynchronised = a_cat.isSynchronised();
/*     */ 
/* 140 */     if (a_cat.getAncestors() == null)
/*     */     {
/* 142 */       this.m_vecAncestors = null;
/*     */     }
/*     */     else
/*     */     {
/* 146 */       this.m_vecAncestors = ((Vector)a_cat.getAncestors().clone());
/*     */     }
/*     */ 
/* 149 */     this.m_vTranslations = a_cat.getTranslations();
/*     */   }
/*     */ 
/*     */   public Category clone()
/*     */   {
/* 157 */     CategoryImpl clone = new CategoryImpl();
/*     */ 
/* 159 */     clone.setId(this.m_lId);
/* 160 */     clone.setDepth(this.m_iDepth);
/* 161 */     clone.setCategoryTypeId(this.m_lCategoryTypeId);
/* 162 */     clone.setIsBrowsable(this.m_bIsBrowsable);
/* 163 */     clone.setParentIndex(this.m_iParentIndex);
/* 164 */     clone.setCannotBeDeleted(this.m_bCannotBeDeleted);
/* 165 */     clone.setSelectedOnLoad(this.m_bSelectedOnLoad);
/* 166 */     clone.setWorkflowName(this.m_sWorkflowName);
/* 167 */     clone.setNumViews(this.m_lNumViews);
/* 168 */     clone.setNumDownloads(this.m_lNumDownloads);
/* 169 */     clone.setExpired(this.m_bIsExpired);
/* 170 */     clone.setSynchronised(this.m_bSynchronised);
/* 171 */     clone.setExtensionAssetId(this.m_lExtensionAssetId);
/*     */ 
/* 174 */     copyUpdateFieldsTo(clone);
/*     */ 
/* 176 */     if (this.m_vecChildCategories != null)
/*     */     {
/* 178 */       clone.setChildCategories((Vector)this.m_vecChildCategories.clone());
/*     */     }
/*     */ 
/* 181 */     if (this.m_vecAncestors != null)
/*     */     {
/* 183 */       clone.setAncestors((Vector)this.m_vecAncestors.clone());
/*     */     }
/*     */ 
/* 186 */     if (this.m_alAllDescendantsIds != null)
/*     */     {
/* 188 */       clone.setAllDescendantsIds((long[])this.m_alAllDescendantsIds.clone());
/*     */     }
/*     */ 
/* 191 */     return clone;
/*     */   }
/*     */ 
/*     */   public void copyUpdateFieldsTo(Category cat)
/*     */   {
/* 199 */     cat.setName(this.m_sName);
/* 200 */     cat.setIdentifier(this.m_sIdentifier);
/* 201 */     cat.setSummary(this.m_sSummary);
/* 202 */     cat.setIsListboxCategory(this.m_bIsListboxCategory);
/* 203 */     cat.setDescription(this.m_sDescription);
/* 204 */     cat.setImageUrl(this.m_sImageUrl);
/* 205 */     cat.setIsBrowsable(this.m_bIsBrowsable);
/* 206 */     cat.setAllowAdvancedOptions(this.m_bAllowAdvancedOptions);
/* 207 */     cat.setCanAssignIfNotLeaf(this.m_bCanAssignIfNotLeaf);
/* 208 */     cat.setIsRestrictive(this.m_bIsRestrictive);
/* 209 */     cat.setWorkflowName(this.m_sWorkflowName);
/* 210 */     cat.setSynchronised(this.m_bSynchronised);
/* 211 */     cat.setExtensionAssetId(this.m_lExtensionAssetId);
/*     */ 
/* 213 */     cat.setNumViews(this.m_lNumViews);
/* 214 */     cat.setNumDownloads(this.m_lNumDownloads);
/*     */ 
/* 216 */     Vector translationsCopy = null;
/*     */     Iterator i$;
/* 217 */     if (this.m_vTranslations != null)
/*     */     {
/* 219 */       translationsCopy = new Vector();
/* 220 */       for (i$ = this.m_vTranslations.iterator(); i$.hasNext(); ) { Object oMyTranslation = i$.next();
/*     */ 
/* 222 */         Category.Translation myTranslation = (Category.Translation)oMyTranslation;
/* 223 */         Category.Translation copyTranslation = new Category.Translation(myTranslation);
/* 224 */         translationsCopy.add(copyTranslation);
/*     */       }
/*     */     }
/* 227 */     cat.setTranslations(translationsCopy);
/*     */   }
/*     */ 
/*     */   public Category getParentCategory()
/*     */   {
/* 232 */     if ((this.m_vecAncestors != null) && (this.m_vecAncestors.size() > 0))
/*     */     {
/* 234 */       return (Category)this.m_vecAncestors.get(this.m_vecAncestors.size() - 1);
/*     */     }
/*     */ 
/* 238 */     Category rootCat = CategoryUtil.createRootCategory(null, this.m_lCategoryTypeId, false);
/*     */ 
/* 240 */     rootCat.setImmutable(this.m_bImmutable);
/*     */ 
/* 242 */     return rootCat;
/*     */   }
/*     */ 
/*     */   public String getStartLetter()
/*     */   {
/* 258 */     String sStartLetter = "";
/* 259 */     if (StringUtil.stringIsPopulated(this.m_sName))
/*     */     {
/* 261 */       sStartLetter = this.m_sName.substring(0, 1).toLowerCase();
/*     */     }
/*     */ 
/* 264 */     return sStartLetter;
/*     */   }
/*     */ 
/*     */   public long getId()
/*     */   {
/* 270 */     return this.m_lId;
/*     */   }
/*     */ 
/*     */   public void setId(long a_lId) {
/* 274 */     assertNotImmutable("setId");
/*     */ 
/* 276 */     this.m_lId = a_lId;
/*     */   }
/*     */ 
/*     */   public void setDescription(String description)
/*     */   {
/* 282 */     assertNotImmutable("setDescription");
/*     */ 
/* 284 */     super.setDescription(description);
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 290 */     assertNotImmutable("setName");
/*     */ 
/* 292 */     super.setName(name);
/* 293 */     this.m_sJSUnicodeName = null;
/*     */   }
/*     */ 
/*     */   public void setSummary(String summary)
/*     */   {
/* 299 */     assertNotImmutable("setSummary");
/*     */ 
/* 301 */     super.setSummary(summary);
/*     */   }
/*     */ 
/*     */   public String getIdentifier()
/*     */   {
/* 306 */     return this.m_sIdentifier;
/*     */   }
/*     */ 
/*     */   public void setIdentifier(String a_sIdentifier)
/*     */   {
/* 311 */     assertNotImmutable("setIdentifier");
/*     */ 
/* 313 */     this.m_sIdentifier = a_sIdentifier;
/*     */   }
/*     */ 
/*     */   public boolean getIsBrowsable()
/*     */   {
/* 318 */     return this.m_bIsBrowsable;
/*     */   }
/*     */ 
/*     */   public void setIsBrowsable(boolean a_bIsBrowsable) {
/* 322 */     assertNotImmutable("setIsBrowsable");
/*     */ 
/* 324 */     this.m_bIsBrowsable = a_bIsBrowsable;
/*     */   }
/*     */ 
/*     */   public int getDepth()
/*     */   {
/* 330 */     return this.m_iDepth;
/*     */   }
/*     */ 
/*     */   public void setDepth(int a_iDepth) {
/* 334 */     assertNotImmutable("setDepth");
/*     */ 
/* 336 */     this.m_iDepth = a_iDepth;
/*     */   }
/*     */ 
/*     */   public long getCategoryTypeId()
/*     */   {
/* 342 */     return this.m_lCategoryTypeId;
/*     */   }
/*     */ 
/*     */   public void setCategoryTypeId(long a_lCategoryTypeId)
/*     */   {
/* 347 */     assertNotImmutable("setCategoryTypeId");
/*     */ 
/* 349 */     this.m_lCategoryTypeId = a_lCategoryTypeId;
/*     */   }
/*     */ 
/*     */   public long getNumViews()
/*     */   {
/* 354 */     return this.m_lNumViews;
/*     */   }
/*     */ 
/*     */   public void setNumViews(long a_lNumViews)
/*     */   {
/* 359 */     this.m_lNumViews = a_lNumViews;
/*     */   }
/*     */ 
/*     */   public long getNumDownloads()
/*     */   {
/* 364 */     return this.m_lNumDownloads;
/*     */   }
/*     */ 
/*     */   public void setNumDownloads(long a_lNumDownloads)
/*     */   {
/* 369 */     this.m_lNumDownloads = a_lNumDownloads;
/*     */   }
/*     */ 
/*     */   public String getJSUnicodeName()
/*     */   {
/* 378 */     if (this.m_sJSUnicodeName == null)
/*     */     {
/* 381 */       this.m_sJSUnicodeName = CategoryUtil.getJSUnicodeName(getName());
/*     */     }
/*     */ 
/* 384 */     return this.m_sJSUnicodeName;
/*     */   }
/*     */ 
/*     */   public String getJavaScriptEncodedName()
/*     */   {
/* 389 */     return StringUtil.getJavascriptLiteralString(getName());
/*     */   }
/*     */ 
/*     */   public Vector<Category> getChildCategories()
/*     */   {
/* 394 */     return this.m_vecChildCategories;
/*     */   }
/*     */ 
/*     */   public void setChildCategories(Vector a_vecChildCategories)
/*     */   {
/* 399 */     assertNotImmutable("setChildCategories");
/*     */ 
/* 401 */     this.m_vecChildCategories = a_vecChildCategories;
/*     */   }
/*     */ 
/*     */   public boolean getChildCategoryListIsEmpty()
/*     */   {
/* 406 */     return (this.m_vecChildCategories == null) || (this.m_vecChildCategories.isEmpty());
/*     */   }
/*     */ 
/*     */   public int getNumChildCategories()
/*     */   {
/* 411 */     if (this.m_vecChildCategories == null)
/*     */     {
/* 413 */       return 0;
/*     */     }
/* 415 */     return this.m_vecChildCategories.size();
/*     */   }
/*     */ 
/*     */   public boolean getHasRestrictiveChildCategories()
/*     */   {
/* 420 */     if (this.m_vecChildCategories != null)
/*     */     {
/* 422 */       for (Category child : this.m_vecChildCategories)
/*     */       {
/* 424 */         if (child.getIsRestrictive())
/*     */         {
/* 426 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 430 */     return false;
/*     */   }
/*     */ 
/*     */   public Vector<Category> getAncestors()
/*     */   {
/* 435 */     return this.m_vecAncestors;
/*     */   }
/*     */ 
/*     */   public void setAncestors(Vector a_vecAncestors)
/*     */   {
/* 440 */     assertNotImmutable("setAncestors");
/*     */ 
/* 442 */     this.m_vecAncestors = a_vecAncestors;
/*     */   }
/*     */ 
/*     */   public Category getClosestRestrictiveAncestor()
/*     */   {
/* 452 */     if (this.m_bIsRestrictive)
/*     */     {
/* 454 */       return this;
/*     */     }
/* 456 */     if (getParent() != null)
/*     */     {
/* 458 */       return getParent().getClosestRestrictiveAncestor();
/*     */     }
/* 460 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isDescendedFrom(Category a_other)
/*     */   {
/* 465 */     for (Category ancestor : this.m_vecAncestors)
/*     */     {
/* 467 */       if (ancestor.getId() == a_other.getId())
/*     */       {
/* 469 */         return true;
/*     */       }
/*     */     }
/* 472 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getAncestorCategoryListIsEmpty()
/*     */   {
/* 477 */     return (this.m_vecAncestors == null) || (this.m_vecAncestors.isEmpty());
/*     */   }
/*     */ 
/*     */   public long[] getAllAncestorIds()
/*     */   {
/* 483 */     if (getAncestorCategoryListIsEmpty())
/*     */     {
/* 485 */       return new long[0];
/*     */     }
/*     */ 
/* 488 */     long[] alAllAncestorIds = new long[this.m_vecAncestors.size()];
/*     */ 
/* 490 */     for (int i = 0; i < this.m_vecAncestors.size(); i++)
/*     */     {
/* 492 */       alAllAncestorIds[i] = ((Category)this.m_vecAncestors.get(i)).getId();
/*     */     }
/*     */ 
/* 495 */     return alAllAncestorIds;
/*     */   }
/*     */ 
/*     */   public long[] getAllDescendantsIds()
/*     */   {
/* 501 */     return this.m_alAllDescendantsIds;
/*     */   }
/*     */ 
/*     */   public void setAllDescendantsIds(long[] a_alAllDescendantsIds) {
/* 505 */     this.m_alAllDescendantsIds = a_alAllDescendantsIds;
/*     */   }
/*     */ 
/*     */   public int getParentIndex()
/*     */   {
/* 511 */     return this.m_iParentIndex;
/*     */   }
/*     */ 
/*     */   public void setParentIndex(int a_iParentIndex) {
/* 515 */     this.m_iParentIndex = a_iParentIndex;
/*     */   }
/*     */ 
/*     */   public Category getParent()
/*     */   {
/* 520 */     Category parent = null;
/*     */ 
/* 522 */     if ((getAncestors() != null) && (getAncestors().size() > 0))
/*     */     {
/* 524 */       Object objParent = getAncestors().lastElement();
/* 525 */       parent = (Category)objParent;
/*     */     }
/*     */ 
/* 528 */     return parent;
/*     */   }
/*     */ 
/*     */   public long getParentId()
/*     */   {
/* 533 */     Category parent = getParent();
/*     */ 
/* 535 */     if (parent != null)
/*     */     {
/* 537 */       return parent.getId();
/*     */     }
/*     */ 
/* 540 */     return -1L;
/*     */   }
/*     */ 
/*     */   public Category getRootAncestor()
/*     */   {
/* 545 */     Category ancestor = null;
/*     */ 
/* 547 */     if ((getAncestors() != null) && (getAncestors().size() > 0))
/*     */     {
/* 549 */       Object objAncestor = getAncestors().firstElement();
/* 550 */       ancestor = (Category)objAncestor;
/*     */     }
/*     */ 
/* 553 */     return ancestor;
/*     */   }
/*     */ 
/*     */   public void setIsListboxCategory(boolean a_bIsListboxCategory)
/*     */   {
/* 558 */     assertNotImmutable("setIsListboxCategory");
/*     */ 
/* 560 */     this.m_bIsListboxCategory = a_bIsListboxCategory;
/*     */   }
/*     */ 
/*     */   public boolean getIsListboxCategory()
/*     */   {
/* 565 */     return this.m_bIsListboxCategory;
/*     */   }
/*     */ 
/*     */   public boolean getIsRestrictive()
/*     */   {
/* 570 */     return this.m_bIsRestrictive;
/*     */   }
/*     */ 
/*     */   public void setIsRestrictive(boolean a_sIsRestrictive)
/*     */   {
/* 575 */     assertNotImmutable("setIsRestrictive");
/*     */ 
/* 577 */     this.m_bIsRestrictive = a_sIsRestrictive;
/*     */   }
/*     */ 
/*     */   public boolean getCannotBeDeleted()
/*     */   {
/* 582 */     return this.m_bCannotBeDeleted;
/*     */   }
/*     */ 
/*     */   public void setCannotBeDeleted(boolean a_sCannotBeDeleted) {
/* 586 */     assertNotImmutable("setCannotBeDeleted");
/*     */ 
/* 588 */     this.m_bCannotBeDeleted = a_sCannotBeDeleted;
/*     */   }
/*     */ 
/*     */   public String getFullName()
/*     */   {
/* 596 */     return CategoryUtil.getFullName(this);
/*     */   }
/*     */ 
/*     */   public String getJSUnicodeFullName()
/*     */   {
/* 607 */     return CategoryUtil.getJSUnicodeFullName(this);
/*     */   }
/*     */ 
/*     */   public String getImageUrl()
/*     */   {
/* 613 */     return this.m_sImageUrl;
/*     */   }
/*     */ 
/*     */   public void setImageUrl(String a_sImageUrl) {
/* 617 */     assertNotImmutable("setImageUrl");
/*     */ 
/* 619 */     this.m_sImageUrl = a_sImageUrl;
/*     */   }
/*     */ 
/*     */   public boolean isSynchronised() {
/* 623 */     return this.m_bSynchronised;
/*     */   }
/*     */ 
/*     */   public void setSynchronised(boolean a_dtSynchronised) {
/* 627 */     assertNotImmutable("setSynchronised");
/*     */ 
/* 629 */     this.m_bSynchronised = a_dtSynchronised;
/*     */   }
/*     */ 
/*     */   public boolean getSelectedOnLoad()
/*     */   {
/* 634 */     return this.m_bSelectedOnLoad;
/*     */   }
/*     */ 
/*     */   public void setSelectedOnLoad(boolean a_bSelectedOnLoad)
/*     */   {
/* 639 */     assertNotImmutable("setSelectedOnLoad");
/*     */ 
/* 641 */     this.m_bSelectedOnLoad = a_bSelectedOnLoad;
/*     */   }
/*     */ 
/*     */   public void setExtensionAssetId(long a_lExtensionAssetId)
/*     */   {
/* 646 */     assertNotImmutable("setExtensionAssetId");
/*     */ 
/* 648 */     this.m_lExtensionAssetId = a_lExtensionAssetId;
/*     */   }
/*     */ 
/*     */   public long getExtensionAssetId()
/*     */   {
/* 653 */     return this.m_lExtensionAssetId;
/*     */   }
/*     */ 
/*     */   public void setExtensionEntityId(String a_sExtensionEntityId)
/*     */   {
/* 658 */     assertNotImmutable("setExtensionEntityId");
/*     */ 
/* 660 */     this.m_sExtensionEntityId = a_sExtensionEntityId;
/*     */   }
/*     */ 
/*     */   public String getExtensionEntityId()
/*     */   {
/* 665 */     return this.m_sExtensionEntityId;
/*     */   }
/*     */ 
/*     */   public Vector getTranslations()
/*     */   {
/* 670 */     if (this.m_vTranslations == null)
/*     */     {
/* 672 */       this.m_vTranslations = new Vector();
/*     */     }
/* 674 */     return this.m_vTranslations;
/*     */   }
/*     */ 
/*     */   public void setTranslations(Vector a_vTranslations)
/*     */   {
/* 679 */     assertNotImmutable("setTranslations");
/*     */ 
/* 681 */     this.m_vTranslations = a_vTranslations;
/*     */   }
/*     */ 
/*     */   protected Language getLanguage()
/*     */   {
/* 690 */     return null;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 699 */     return getDescription(getLanguage());
/*     */   }
/*     */ 
/*     */   public String getDescription(Language a_language)
/*     */   {
/* 708 */     if ((a_language != null) && (!a_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/* 710 */       Category.Translation translation = (Category.Translation)LanguageUtils.getTranslation(a_language, this.m_vTranslations);
/* 711 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getDescription())))
/*     */       {
/* 713 */         return translation.getDescription();
/*     */       }
/*     */     }
/* 716 */     return super.getDescription();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 725 */     return getName(getLanguage());
/*     */   }
/*     */ 
/*     */   public String getName(Language a_language)
/*     */   {
/* 733 */     if ((a_language != null) && (!a_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/* 735 */       Category.Translation translation = (Category.Translation)LanguageUtils.getTranslation(a_language, this.m_vTranslations);
/* 736 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getName())))
/*     */       {
/* 738 */         return translation.getName();
/*     */       }
/*     */     }
/* 741 */     return super.getName();
/*     */   }
/*     */ 
/*     */   public String getNameWithEscapedQuotes()
/*     */   {
/* 750 */     return CategoryUtil.getNameWithEscapedQuotes(this);
/*     */   }
/*     */ 
/*     */   public String getSummary()
/*     */   {
/* 759 */     return getSummary(getLanguage());
/*     */   }
/*     */ 
/*     */   public String getSummary(Language a_language)
/*     */   {
/* 768 */     if ((a_language != null) && (!a_language.equals(LanguageConstants.k_defaultLanguage)))
/*     */     {
/* 770 */       Category.Translation translation = (Category.Translation)LanguageUtils.getTranslation(a_language, this.m_vTranslations);
/* 771 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getSummary())))
/*     */       {
/* 773 */         return translation.getSummary();
/*     */       }
/*     */     }
/* 776 */     return super.getSummary();
/*     */   }
/*     */ 
/*     */   public Category.Translation createTranslation(Language a_language)
/*     */   {
/* 781 */     return new Category.Translation(a_language);
/*     */   }
/*     */ 
/*     */   public void setAllowAdvancedOptions(boolean a_bAllowAdvancedOptions)
/*     */   {
/* 786 */     assertNotImmutable("setAllowAdvancedOptions");
/*     */ 
/* 788 */     this.m_bAllowAdvancedOptions = a_bAllowAdvancedOptions;
/*     */   }
/*     */ 
/*     */   public boolean getAllowAdvancedOptions()
/*     */   {
/* 793 */     return this.m_bAllowAdvancedOptions;
/*     */   }
/*     */ 
/*     */   public boolean isExpired()
/*     */   {
/* 798 */     return this.m_bIsExpired;
/*     */   }
/*     */ 
/*     */   public void setExpired(boolean isExpired)
/*     */   {
/* 803 */     assertNotImmutable("setExpired");
/*     */ 
/* 805 */     this.m_bIsExpired = isExpired;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 810 */     if (!(obj instanceof Category))
/*     */     {
/* 812 */       return false;
/*     */     }
/*     */ 
/* 815 */     Category toCompare = (Category)obj;
/*     */ 
/* 817 */     return getId() == toCompare.getId();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 823 */     return new Long(this.m_lId).hashCode();
/*     */   }
/*     */ 
/*     */   public int compareTo(Object obj)
/*     */   {
/* 828 */     return CategoryUtil.compareTo(this, obj);
/*     */   }
/*     */ 
/*     */   public String getWorkflowName()
/*     */   {
/* 838 */     if ((!this.m_bIsRestrictive) && (getParent() != null))
/*     */     {
/* 840 */       return getParent().getWorkflowName();
/*     */     }
/* 842 */     return this.m_sWorkflowName;
/*     */   }
/*     */ 
/*     */   public void setWorkflowName(String a_sWorkflowName)
/*     */   {
/* 851 */     assertNotImmutable("setWorkflowName");
/*     */ 
/* 853 */     if ((!this.m_bIsRestrictive) && (getParent() != null))
/*     */     {
/* 855 */       Category parent = getParent();
/* 856 */       boolean bParentImmutable = parent.isImmutable();
/* 857 */       parent.setImmutable(false);
/* 858 */       parent.setWorkflowName(a_sWorkflowName);
/* 859 */       parent.setImmutable(bParentImmutable);
/*     */     }
/* 861 */     this.m_sWorkflowName = a_sWorkflowName;
/*     */   }
/*     */ 
/*     */   public boolean getHasDescendants()
/*     */   {
/* 868 */     return (this.m_alAllDescendantsIds != null) && (this.m_alAllDescendantsIds.length > 0);
/*     */   }
/*     */ 
/*     */   public boolean getCanAssignIfNotLeaf()
/*     */   {
/* 875 */     return this.m_bCanAssignIfNotLeaf;
/*     */   }
/*     */ 
/*     */   public void setCanAssignIfNotLeaf(boolean a_bCanAssignIfNotLeaf)
/*     */   {
/* 880 */     assertNotImmutable("setCanAssignIfNotLeaf");
/*     */ 
/* 882 */     this.m_bCanAssignIfNotLeaf = a_bCanAssignIfNotLeaf;
/*     */   }
/*     */ 
/*     */   public boolean isAssignable()
/*     */   {
/* 887 */     return (this.m_bCanAssignIfNotLeaf) || (getChildCategories() == null) || (getChildCategories().isEmpty());
/*     */   }
/*     */ 
/*     */   public boolean isImmutable()
/*     */   {
/* 892 */     return this.m_bImmutable;
/*     */   }
/*     */ 
/*     */   public void setImmutable(boolean a_bImmutable)
/*     */   {
/* 901 */     this.m_bImmutable = a_bImmutable;
/*     */   }
/*     */ 
/*     */   private void assertNotImmutable(String a_sMethodName)
/*     */   {
/* 906 */     if (this.m_bImmutable)
/*     */     {
/* 908 */       Assert.assertionFailed(getClass().getName() + "." + a_sMethodName + "() must not be called on an immutable instance.");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean getAtLeastOneChildHasImage()
/*     */   {
/* 914 */     return this.m_bAtLeastOneChildHasImage;
/*     */   }
/*     */ 
/*     */   public void setAtLeastOneChildHasImage(boolean a_bAtLeastOneChildHasImage)
/*     */   {
/* 919 */     assertNotImmutable("setAtLeastOneChildHasImage");
/*     */ 
/* 921 */     this.m_bAtLeastOneChildHasImage = a_bAtLeastOneChildHasImage;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.bean.CategoryImpl
 * JD-Core Version:    0.6.0
 */