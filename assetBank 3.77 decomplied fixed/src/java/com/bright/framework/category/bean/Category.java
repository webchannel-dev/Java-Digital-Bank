/*     */ package com.bright.framework.category.bean;
/*     */ 
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.Translatable;
/*     */// import com.bright.framework.language.bean.Translation;
//com.bright.framework.language.bean
/*     */ import java.util.Vector;
/*     */ 
/*     */ public abstract interface Category extends Translatable, Comparable
/*     */ {
/*     */   public abstract void copyUpdateFieldsTo(Category paramCategory);
/*     */ 
/*     */   public abstract Category clone();
/*     */ 
/*     */   public abstract Category getParentCategory();
/*     */ 
/*     */   public abstract String getStartLetter();
/*     */ 
/*     */   public abstract long getId();
/*     */ 
/*     */   public abstract void setId(long paramLong);
/*     */ 
/*     */   public abstract void setDescription(String paramString);
/*     */ 
/*     */   public abstract void setName(String paramString);
/*     */ 
/*     */   public abstract void setSummary(String paramString);
/*     */ 
/*     */   public abstract String getIdentifier();
/*     */ 
/*     */   public abstract void setIdentifier(String paramString);
/*     */ 
/*     */   public abstract boolean getIsBrowsable();
/*     */ 
/*     */   public abstract void setIsBrowsable(boolean paramBoolean);
/*     */ 
/*     */   public abstract int getDepth();
/*     */ 
/*     */   public abstract void setDepth(int paramInt);
/*     */ 
/*     */   public abstract long getCategoryTypeId();
/*     */ 
/*     */   public abstract void setCategoryTypeId(long paramLong);
/*     */ 
/*     */   public abstract long getNumViews();
/*     */ 
/*     */   public abstract void setNumViews(long paramLong);
/*     */ 
/*     */   public abstract long getNumDownloads();
/*     */ 
/*     */   public abstract void setNumDownloads(long paramLong);
/*     */ 
/*     */   public abstract String getJSUnicodeName();
/*     */ 
/*     */   public abstract String getJavaScriptEncodedName();
/*     */ 
/*     */   public abstract Vector<Category> getChildCategories();
/*     */ 
/*     */   public abstract void setChildCategories(Vector paramVector);
/*     */ 
/*     */   public abstract boolean getChildCategoryListIsEmpty();
/*     */ 
/*     */   public abstract int getNumChildCategories();
/*     */ 
/*     */   public abstract boolean getHasRestrictiveChildCategories();
/*     */ 
/*     */   public abstract Vector<Category> getAncestors();
/*     */ 
/*     */   public abstract void setAncestors(Vector paramVector);
/*     */ 
/*     */   public abstract Category getClosestRestrictiveAncestor();
/*     */ 
/*     */   public abstract boolean isDescendedFrom(Category paramCategory);
/*     */ 
/*     */   public abstract boolean getAncestorCategoryListIsEmpty();
/*     */ 
/*     */   public abstract long[] getAllAncestorIds();
/*     */ 
/*     */   public abstract long[] getAllDescendantsIds();
/*     */ 
/*     */   public abstract void setAllDescendantsIds(long[] paramArrayOfLong);
/*     */ 
/*     */   public abstract int getParentIndex();
/*     */ 
/*     */   public abstract void setParentIndex(int paramInt);
/*     */ 
/*     */   public abstract Category getParent();
/*     */ 
/*     */   public abstract long getParentId();
/*     */ 
/*     */   public abstract Category getRootAncestor();
/*     */ 
/*     */   public abstract void setIsListboxCategory(boolean paramBoolean);
/*     */ 
/*     */   public abstract boolean getIsListboxCategory();
/*     */ 
/*     */   public abstract boolean getIsRestrictive();
/*     */ 
/*     */   public abstract void setIsRestrictive(boolean paramBoolean);
/*     */ 
/*     */   public abstract boolean getCannotBeDeleted();
/*     */ 
/*     */   public abstract void setCannotBeDeleted(boolean paramBoolean);
/*     */ 
/*     */   public abstract String getFullName();
/*     */ 
/*     */   public abstract String getJSUnicodeFullName();
/*     */ 
/*     */   public abstract String getImageUrl();
/*     */ 
/*     */   public abstract void setImageUrl(String paramString);
/*     */ 
/*     */   public abstract boolean isSynchronised();
/*     */ 
/*     */   public abstract void setSynchronised(boolean paramBoolean);
/*     */ 
/*     */   public abstract boolean getSelectedOnLoad();
/*     */ 
/*     */   public abstract void setSelectedOnLoad(boolean paramBoolean);
/*     */ 
/*     */   public abstract long getExtensionAssetId();
/*     */ 
/*     */   public abstract void setExtensionAssetId(long paramLong);
/*     */ 
/*     */   public abstract String getExtensionEntityId();
/*     */ 
/*     */   public abstract void setExtensionEntityId(String paramString);
/*     */ 
/*     */   public abstract Vector getTranslations();
/*     */ 
/*     */   public abstract void setTranslations(Vector paramVector);
/*     */ 
/*     */   public abstract String getDescription();
/*     */ 
/*     */   public abstract String getDescription(Language paramLanguage);
/*     */ 
/*     */   public abstract String getName();
/*     */ 
/*     */   public abstract String getName(Language paramLanguage);
/*     */ 
/*     */   public abstract String getNameWithEscapedQuotes();
/*     */ 
/*     */   public abstract String getSummary();
/*     */ 
/*     */   public abstract String getSummary(Language paramLanguage);
/*     */ 
/*     */   public abstract Category.Translation createTranslation(Language paramLanguage);
/*     */ 
/*     */   public abstract void setAllowAdvancedOptions(boolean paramBoolean);
/*     */ 
/*     */   public abstract boolean getAllowAdvancedOptions();
/*     */ 
/*     */   public abstract boolean isExpired();
/*     */ 
/*     */   public abstract void setExpired(boolean paramBoolean);
/*     */ 
/*     */   public abstract boolean equals(Object paramObject);
/*     */ 
/*     */   public abstract int hashCode();
/*     */ 
/*     */   public abstract int compareTo(Object paramObject);
/*     */ 
/*     */   public abstract String getWorkflowName();
/*     */ 
/*     */   public abstract void setWorkflowName(String paramString);
/*     */ 
/*     */   public abstract boolean getHasDescendants();
/*     */ 
/*     */   public abstract boolean getCanAssignIfNotLeaf();
/*     */ 
/*     */   public abstract void setCanAssignIfNotLeaf(boolean paramBoolean);
/*     */ 
/*     */   public abstract boolean isAssignable();
/*     */ 
/*     */   public abstract boolean isImmutable();
/*     */ 
/*     */   public abstract void setImmutable(boolean paramBoolean);
/*     */ 
/*     */   public abstract boolean getAtLeastOneChildHasImage();
/*     */ 
/*     */   public abstract void setAtLeastOneChildHasImage(boolean paramBoolean);
/*     */ 
/*     */   public static class Translation extends BaseCategory
/*     */     implements com.bright.framework.language.bean.Translation
/*     */   {
/*     */     private Language m_language;
/*     */ 
/*     */     public Translation(Translation a_translationToCopy)
/*     */     {
/* 259 */       super();
/* 260 */       this.m_language = a_translationToCopy.m_language;
/*     */     }
/*     */ 
/*     */     public Translation(Language a_language)
/*     */     {
/* 265 */       this.m_language = a_language;
/*     */     }
/*     */ 
/*     */     public Language getLanguage()
/*     */     {
/* 270 */       return this.m_language;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.bean.Category
 * JD-Core Version:    0.6.0
 */