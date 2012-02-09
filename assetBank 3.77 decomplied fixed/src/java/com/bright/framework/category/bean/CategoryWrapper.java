/*     */ package com.bright.framework.category.bean;
/*     */ 
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public abstract class CategoryWrapper<T extends Category>
/*     */   implements Category
/*     */ {
/*     */   private Category m_category;
/*     */ 
/*     */   public CategoryWrapper(Category a_category)
/*     */   {
/*  29 */     this.m_category = a_category;
/*     */   }
/*     */ 
/*     */   public abstract T clone();
/*     */ 
/*     */   protected abstract T doWrapCategory(Category paramCategory);
/*     */ 
/*     */   protected T wrapCategory(Category a_catToBeWrapped)
/*     */   {
/*  53 */     if (a_catToBeWrapped == null)
/*     */     {
/*  55 */       return null;
/*     */     }
/*     */ 
/*  59 */     return doWrapCategory(a_catToBeWrapped);
/*     */   }
/*     */ 
/*     */   protected Vector<Category> wrapCategories(Vector<Category> a_categories)
/*     */   {
/*  68 */     Vector result = new Vector(a_categories.size());
/*  69 */     for (Category category : a_categories)
/*     */     {
/*  71 */       result.add(wrapCategory(category));
/*     */     }
/*     */ 
/*  74 */     return result;
/*     */   }
/*     */ 
/*     */   protected Category getWrappedCategory()
/*     */   {
/*  79 */     return this.m_category;
/*     */   }
/*     */ 
/*     */   public Vector<Category> getAncestors()
/*     */   {
/*  86 */     return wrapCategories(this.m_category.getAncestors());
/*     */   }
/*     */ 
/*     */   public Vector<Category> getChildCategories()
/*     */   {
/*  91 */     return wrapCategories(this.m_category.getChildCategories());
/*     */   }
/*     */ 
/*     */   public T getClosestRestrictiveAncestor()
/*     */   {
/*  96 */     return wrapCategory(this.m_category.getClosestRestrictiveAncestor());
/*     */   }
/*     */ 
/*     */   public T getParent()
/*     */   {
/* 101 */     return wrapCategory(this.m_category.getParent());
/*     */   }
/*     */ 
/*     */   public T getParentCategory()
/*     */   {
/* 106 */     return wrapCategory(this.m_category.getParentCategory());
/*     */   }
/*     */ 
/*     */   public int compareTo(Object obj)
/*     */   {
/* 112 */     return this.m_category.compareTo(obj);
/*     */   }
/*     */ 
/*     */   public void copyUpdateFieldsTo(Category cat)
/*     */   {
/* 117 */     this.m_category.copyUpdateFieldsTo(cat);
/*     */   }
/*     */ 
/*     */   public Category.Translation createTranslation(Language a_language)
/*     */   {
/* 122 */     return this.m_category.createTranslation(a_language);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 128 */     return this.m_category.equals(obj);
/*     */   }
/*     */ 
/*     */   public long[] getAllAncestorIds()
/*     */   {
/* 133 */     return this.m_category.getAllAncestorIds();
/*     */   }
/*     */ 
/*     */   public long[] getAllDescendantsIds()
/*     */   {
/* 138 */     return this.m_category.getAllDescendantsIds();
/*     */   }
/*     */ 
/*     */   public boolean getAllowAdvancedOptions()
/*     */   {
/* 143 */     return this.m_category.getAllowAdvancedOptions();
/*     */   }
/*     */ 
/*     */   public boolean getAncestorCategoryListIsEmpty()
/*     */   {
/* 148 */     return this.m_category.getAncestorCategoryListIsEmpty();
/*     */   }
/*     */ 
/*     */   public boolean getAtLeastOneChildHasImage()
/*     */   {
/* 153 */     return this.m_category.getAtLeastOneChildHasImage();
/*     */   }
/*     */ 
/*     */   public boolean getCanAssignIfNotLeaf()
/*     */   {
/* 158 */     return this.m_category.getCanAssignIfNotLeaf();
/*     */   }
/*     */ 
/*     */   public boolean getCannotBeDeleted()
/*     */   {
/* 163 */     return this.m_category.getCannotBeDeleted();
/*     */   }
/*     */ 
/*     */   public long getCategoryTypeId()
/*     */   {
/* 168 */     return this.m_category.getCategoryTypeId();
/*     */   }
/*     */ 
/*     */   public boolean getChildCategoryListIsEmpty()
/*     */   {
/* 173 */     return this.m_category.getChildCategoryListIsEmpty();
/*     */   }
/*     */ 
/*     */   public int getDepth()
/*     */   {
/* 178 */     return this.m_category.getDepth();
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 183 */     return this.m_category.getDescription();
/*     */   }
/*     */ 
/*     */   public String getDescription(Language a_language)
/*     */   {
/* 188 */     return this.m_category.getDescription(a_language);
/*     */   }
/*     */ 
/*     */   public String getFullName()
/*     */   {
/* 193 */     return this.m_category.getFullName();
/*     */   }
/*     */ 
/*     */   public boolean getHasDescendants()
/*     */   {
/* 198 */     return this.m_category.getHasDescendants();
/*     */   }
/*     */ 
/*     */   public boolean getHasRestrictiveChildCategories()
/*     */   {
/* 203 */     return this.m_category.getHasRestrictiveChildCategories();
/*     */   }
/*     */ 
/*     */   public long getId()
/*     */   {
/* 208 */     return this.m_category.getId();
/*     */   }
/*     */ 
/*     */   public String getIdentifier()
/*     */   {
/* 213 */     return this.m_category.getIdentifier();
/*     */   }
/*     */ 
/*     */   public String getImageUrl()
/*     */   {
/* 218 */     return this.m_category.getImageUrl();
/*     */   }
/*     */ 
/*     */   public boolean getIsBrowsable()
/*     */   {
/* 223 */     return this.m_category.getIsBrowsable();
/*     */   }
/*     */ 
/*     */   public boolean getIsListboxCategory()
/*     */   {
/* 228 */     return this.m_category.getIsListboxCategory();
/*     */   }
/*     */ 
/*     */   public boolean getIsRestrictive()
/*     */   {
/* 233 */     return this.m_category.getIsRestrictive();
/*     */   }
/*     */ 
/*     */   public String getJavaScriptEncodedName()
/*     */   {
/* 238 */     return this.m_category.getJavaScriptEncodedName();
/*     */   }
/*     */ 
/*     */   public String getJSUnicodeFullName()
/*     */   {
/* 243 */     return this.m_category.getJSUnicodeFullName();
/*     */   }
/*     */ 
/*     */   public String getJSUnicodeName()
/*     */   {
/* 248 */     return this.m_category.getJSUnicodeName();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 253 */     return this.m_category.getName();
/*     */   }
/*     */ 
/*     */   public String getName(Language a_language)
/*     */   {
/* 258 */     return this.m_category.getName(a_language);
/*     */   }
/*     */ 
/*     */   public String getNameWithEscapedQuotes()
/*     */   {
/* 263 */     return this.m_category.getNameWithEscapedQuotes();
/*     */   }
/*     */ 
/*     */   public int getNumChildCategories()
/*     */   {
/* 268 */     return this.m_category.getNumChildCategories();
/*     */   }
/*     */ 
/*     */   public long getNumDownloads()
/*     */   {
/* 273 */     return this.m_category.getNumDownloads();
/*     */   }
/*     */ 
/*     */   public long getNumViews()
/*     */   {
/* 278 */     return this.m_category.getNumViews();
/*     */   }
/*     */ 
/*     */   public long getParentId()
/*     */   {
/* 283 */     return this.m_category.getParentId();
/*     */   }
/*     */ 
/*     */   public int getParentIndex()
/*     */   {
/* 288 */     return this.m_category.getParentIndex();
/*     */   }
/*     */ 
/*     */   public Category getRootAncestor()
/*     */   {
/* 293 */     return this.m_category.getRootAncestor();
/*     */   }
/*     */ 
/*     */   public boolean getSelectedOnLoad()
/*     */   {
/* 298 */     return this.m_category.getSelectedOnLoad();
/*     */   }
/*     */ 
/*     */   public long getExtensionAssetId()
/*     */   {
/* 303 */     return this.m_category.getExtensionAssetId();
/*     */   }
/*     */ 
/*     */   public String getExtensionEntityId()
/*     */   {
/* 308 */     return this.m_category.getExtensionEntityId();
/*     */   }
/*     */ 
/*     */   public String getStartLetter()
/*     */   {
/* 313 */     return this.m_category.getStartLetter();
/*     */   }
/*     */ 
/*     */   public String getSummary()
/*     */   {
/* 318 */     return this.m_category.getSummary();
/*     */   }
/*     */ 
/*     */   public String getSummary(Language a_language)
/*     */   {
/* 323 */     return this.m_category.getSummary(a_language);
/*     */   }
/*     */ 
/*     */   public Vector getTranslations()
/*     */   {
/* 328 */     return this.m_category.getTranslations();
/*     */   }
/*     */ 
/*     */   public String getWorkflowName()
/*     */   {
/* 333 */     return this.m_category.getWorkflowName();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 339 */     return this.m_category.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean isAssignable()
/*     */   {
/* 344 */     return this.m_category.isAssignable();
/*     */   }
/*     */ 
/*     */   public boolean isDescendedFrom(Category a_other)
/*     */   {
/* 349 */     return this.m_category.isDescendedFrom(a_other);
/*     */   }
/*     */ 
/*     */   public boolean isExpired()
/*     */   {
/* 354 */     return this.m_category.isExpired();
/*     */   }
/*     */ 
/*     */   public boolean isSynchronised()
/*     */   {
/* 359 */     return this.m_category.isSynchronised();
/*     */   }
/*     */ 
/*     */   public void setAllDescendantsIds(long[] a_alAllDescendantsIds)
/*     */   {
/* 364 */     this.m_category.setAllDescendantsIds(a_alAllDescendantsIds);
/*     */   }
/*     */ 
/*     */   public void setAllowAdvancedOptions(boolean a_bAllowAdvancedOptions)
/*     */   {
/* 369 */     this.m_category.setAllowAdvancedOptions(a_bAllowAdvancedOptions);
/*     */   }
/*     */ 
/*     */   public void setAncestors(Vector a_vecAncestors)
/*     */   {
/* 374 */     this.m_category.setAncestors(a_vecAncestors);
/*     */   }
/*     */ 
/*     */   public void setAtLeastOneChildHasImage(boolean a_bAtLeastOneChildHasImage)
/*     */   {
/* 379 */     this.m_category.setAtLeastOneChildHasImage(a_bAtLeastOneChildHasImage);
/*     */   }
/*     */ 
/*     */   public void setCanAssignIfNotLeaf(boolean a_bCanAssignIfNotLeaf)
/*     */   {
/* 384 */     this.m_category.setCanAssignIfNotLeaf(a_bCanAssignIfNotLeaf);
/*     */   }
/*     */ 
/*     */   public void setCannotBeDeleted(boolean a_sCannotBeDeleted)
/*     */   {
/* 389 */     this.m_category.setCannotBeDeleted(a_sCannotBeDeleted);
/*     */   }
/*     */ 
/*     */   public void setCategoryTypeId(long a_lCategoryTypeId)
/*     */   {
/* 394 */     this.m_category.setCategoryTypeId(a_lCategoryTypeId);
/*     */   }
/*     */ 
/*     */   public void setChildCategories(Vector a_vecChildCategories)
/*     */   {
/* 399 */     this.m_category.setChildCategories(a_vecChildCategories);
/*     */   }
/*     */ 
/*     */   public void setDepth(int a_iDepth)
/*     */   {
/* 404 */     this.m_category.setDepth(a_iDepth);
/*     */   }
/*     */ 
/*     */   public void setDescription(String description)
/*     */   {
/* 409 */     this.m_category.setDescription(description);
/*     */   }
/*     */ 
/*     */   public void setExpired(boolean isExpired)
/*     */   {
/* 414 */     this.m_category.setExpired(isExpired);
/*     */   }
/*     */ 
/*     */   public void setId(long a_lId)
/*     */   {
/* 419 */     this.m_category.setId(a_lId);
/*     */   }
/*     */ 
/*     */   public void setIdentifier(String a_sIdentifier)
/*     */   {
/* 424 */     this.m_category.setIdentifier(a_sIdentifier);
/*     */   }
/*     */ 
/*     */   public void setImageUrl(String a_sImageUrl)
/*     */   {
/* 429 */     this.m_category.setImageUrl(a_sImageUrl);
/*     */   }
/*     */ 
/*     */   public boolean isImmutable()
/*     */   {
/* 434 */     return this.m_category.isImmutable();
/*     */   }
/*     */ 
/*     */   public void setImmutable(boolean a_bImmutable)
/*     */   {
/* 439 */     this.m_category.setImmutable(a_bImmutable);
/*     */   }
/*     */ 
/*     */   public void setIsBrowsable(boolean a_bIsBrowsable)
/*     */   {
/* 444 */     this.m_category.setIsBrowsable(a_bIsBrowsable);
/*     */   }
/*     */ 
/*     */   public void setIsListboxCategory(boolean a_bIsListboxCategory)
/*     */   {
/* 449 */     this.m_category.setIsListboxCategory(a_bIsListboxCategory);
/*     */   }
/*     */ 
/*     */   public void setIsRestrictive(boolean a_sIsRestrictive)
/*     */   {
/* 454 */     this.m_category.setIsRestrictive(a_sIsRestrictive);
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 459 */     this.m_category.setName(name);
/*     */   }
/*     */ 
/*     */   public void setNumDownloads(long a_lNumDownloads)
/*     */   {
/* 464 */     this.m_category.setNumDownloads(a_lNumDownloads);
/*     */   }
/*     */ 
/*     */   public void setNumViews(long a_lNumViews)
/*     */   {
/* 469 */     this.m_category.setNumViews(a_lNumViews);
/*     */   }
/*     */ 
/*     */   public void setParentIndex(int a_iParentIndex)
/*     */   {
/* 474 */     this.m_category.setParentIndex(a_iParentIndex);
/*     */   }
/*     */ 
/*     */   public void setSelectedOnLoad(boolean a_bSelectedOnLoad)
/*     */   {
/* 479 */     this.m_category.setSelectedOnLoad(a_bSelectedOnLoad);
/*     */   }
/*     */ 
/*     */   public void setExtensionAssetId(long a_lExtensionAssetId)
/*     */   {
/* 484 */     this.m_category.setExtensionAssetId(a_lExtensionAssetId);
/*     */   }
/*     */ 
/*     */   public void setExtensionEntityId(String a_sExtensionEntityId)
/*     */   {
/* 489 */     this.m_category.setExtensionEntityId(a_sExtensionEntityId);
/*     */   }
/*     */ 
/*     */   public void setSummary(String summary)
/*     */   {
/* 494 */     this.m_category.setSummary(summary);
/*     */   }
/*     */ 
/*     */   public void setSynchronised(boolean a_dtSynchronised)
/*     */   {
/* 499 */     this.m_category.setSynchronised(a_dtSynchronised);
/*     */   }
/*     */ 
/*     */   public void setTranslations(Vector a_vTranslations)
/*     */   {
/* 504 */     this.m_category.setTranslations(a_vTranslations);
/*     */   }
/*     */ 
/*     */   public void setWorkflowName(String a_sWorkflowName)
/*     */   {
/* 509 */     this.m_category.setWorkflowName(a_sWorkflowName);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.bean.CategoryWrapper
 * JD-Core Version:    0.6.0
 */