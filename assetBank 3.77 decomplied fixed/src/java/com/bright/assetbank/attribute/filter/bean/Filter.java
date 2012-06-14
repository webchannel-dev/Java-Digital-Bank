/*     */ package com.bright.assetbank.attribute.filter.bean;
/*     */ 
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.TranslatableWithLanguage;
/*     */ import com.bright.framework.language.bean.Translation;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.Serializable;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;

/*     */ 
/*     */ public class Filter extends BaseFilter
/*     */   implements Serializable, Cloneable, TranslatableWithLanguage, AttributeConstants
/*     */ {
/*     */   private static final long serialVersionUID = -3695396412487956705L;
/*     */   private boolean m_bIsDefault;
/*     */   private Vector m_vecAttributeValues;
/*     */   private Vector m_vecSearchHiddenAttributeValues;
/*     */   private String m_sCategoryIds;
/*     */   private String m_sAccessLevelIds;
/*     */   private Vector m_vecSelectedCategoryIds;
/*     */   private Vector m_vecGroups;
/*     */   private int m_iSequence;
/*     */   private Vector m_vecUserGroupExclusions;
/*     */   private Vector m_vecLinkedToCategories;
/*     */   private Vector m_vTranslations;
/*     */ 
/*     */   public Filter()
/*     */   {
/*  53 */     this.m_bIsDefault = false;
/*  54 */     this.m_vecAttributeValues = new Vector();
/*  55 */     this.m_vecSearchHiddenAttributeValues = null;
/*     */ 
/*  57 */     this.m_sCategoryIds = null;
/*  58 */     this.m_sAccessLevelIds = null;
/*  59 */     this.m_vecSelectedCategoryIds = null;
/*     */ 
/*  61 */     this.m_vecGroups = null;
/*     */ 
/*  63 */     this.m_iSequence = 0;
/*     */ 
/*  65 */     this.m_vecUserGroupExclusions = null;
/*  66 */     this.m_vecLinkedToCategories = null;
/*     */ 
/*  69 */     this.m_vTranslations = null;
/*     */   }
/*     */ 
/*     */   public void setIsDefault(boolean a_bIsDefault) {
/*  73 */     this.m_bIsDefault = a_bIsDefault;
/*     */   }
/*     */ 
/*     */   public boolean getIsDefault()
/*     */   {
/*  78 */     return this.m_bIsDefault;
/*     */   }
/*     */ 
/*     */   public void setCategoryIds(String a_sCategoryIds)
/*     */   {
/*  83 */     this.m_sCategoryIds = a_sCategoryIds;
/*     */   }
/*     */ 
/*     */   public String getCategoryIds()
/*     */   {
/*  88 */     return this.m_sCategoryIds;
/*     */   }
/*     */ 
/*     */   public void setAccessLevelIds(String a_sAccessLevelIds)
/*     */   {
/*  93 */     this.m_sAccessLevelIds = a_sAccessLevelIds;
/*     */   }
/*     */ 
/*     */   public String getAccessLevelIds()
/*     */   {
/*  98 */     return this.m_sAccessLevelIds;
/*     */   }
/*     */ 
/*     */   public void setGroups(Vector a_vecGroups)
/*     */   {
/* 103 */     this.m_vecGroups = a_vecGroups;
/*     */   }
/*     */ 
/*     */   public Vector getGroups()
/*     */   {
/* 108 */     return this.m_vecGroups;
/*     */   }
/*     */ 
/*     */   public void setUserGroupExclusions(Vector a_vecUserGroupExclusions)
/*     */   {
/* 113 */     this.m_vecUserGroupExclusions = a_vecUserGroupExclusions;
/*     */   }
/*     */ 
/*     */   public Vector getUserGroupExclusions()
/*     */   {
/* 118 */     return this.m_vecUserGroupExclusions;
/*     */   }
/*     */ 
/*     */   public void setLinkedToCategories(Vector a_vecLinkedToCategories)
/*     */   {
/* 123 */     this.m_vecLinkedToCategories = a_vecLinkedToCategories;
/*     */   }
/*     */ 
/*     */   public Vector getLinkedToCategories()
/*     */   {
/* 128 */     return this.m_vecLinkedToCategories;
/*     */   }
/*     */ 
/*     */   public boolean getIsLinkedToCategory(String a_sCategoryId)
/*     */   {
/* 133 */     if (getLinkedToCategories() != null)
/*     */     {
/* 135 */       long lCatId = Long.parseLong(a_sCategoryId);
/*     */ 
/* 137 */       for (int i = 0; i < getLinkedToCategories().size(); i++)
/*     */       {
/* 139 */         if (lCatId == ((Long)getLinkedToCategories().elementAt(i)).longValue())
/*     */         {
/* 141 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 145 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getFilterInGroup(String a_sGroupId)
/*     */   {
/* 150 */     if (getGroups() != null)
/*     */     {
/* 152 */       long lGroupId = Long.parseLong(a_sGroupId);
/*     */ 
/* 154 */       for (int i = 0; i < getGroups().size(); i++)
/*     */       {
/* 156 */         FilterGroup group = (FilterGroup)getGroups().elementAt(i);
/* 157 */         if (group.getId() == lGroupId)
/*     */         {
/* 159 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getHasSelectedCategory(String a_sCatId)
/*     */   {
/* 168 */     if (getCategoryIds() != null)
/*     */     {
/* 170 */       if (getCategoryIds().indexOf("," + a_sCatId + ",") >= 0)
/*     */       {
/* 172 */         return true;
/*     */       }
/*     */     }
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getHasSelectedAccessLevel(String a_sAlId)
/*     */   {
/* 180 */     if (getAccessLevelIds() != null)
/*     */     {
/* 182 */       if (getAccessLevelIds().indexOf("," + a_sAlId + ",") >= 0)
/*     */       {
/* 184 */         return true;
/*     */       }
/*     */     }
/* 187 */     return false;
/*     */   }
/*     */ 
/*     */   public Vector getSelectedCategoryIds()
/*     */   {
/* 192 */     if (this.m_vecSelectedCategoryIds == null)
/*     */     {
/* 194 */       if (StringUtil.stringIsPopulated(getCategoryIds()))
/*     */       {
/* 196 */         String[] aCats = getCategoryIds().split(",");
/* 197 */         for (int i = 0; i < aCats.length; i++)
/*     */         {
/* 199 */           if (!StringUtil.stringIsPopulated(aCats[i]))
/*     */             continue;
/* 201 */           if (this.m_vecSelectedCategoryIds == null)
/*     */           {
/* 203 */             this.m_vecSelectedCategoryIds = new Vector();
/*     */           }
/* 205 */           this.m_vecSelectedCategoryIds.add(aCats[i]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 210 */     return this.m_vecSelectedCategoryIds;
/*     */   }
/*     */ 
/*     */   public void setAttributeValues(Vector a_vecAttributeValues)
/*     */   {
/* 215 */     this.m_vecAttributeValues = a_vecAttributeValues;
/*     */   }
/*     */ 
/*     */   public Vector getAttributeValues()
/*     */   {
/* 220 */     return this.m_vecAttributeValues;
/*     */   }
/*     */ 
/*     */   public void setSequence(int a_iSequence)
/*     */   {
/* 225 */     this.m_iSequence = a_iSequence;
/*     */   }
/*     */ 
/*     */   public int getSequence()
/*     */   {
/* 230 */     return this.m_iSequence;
/*     */   }
/*     */ 
/*     */   public boolean getAttributeHasValue(String a_sIdValuePair)
/*     */   {
/* 235 */     return AttributeUtil.getAttributeHasValue(a_sIdValuePair, getAttributeValues());
/*     */   }
/*     */ 
/*     */   public AttributeValue getAttributeValueForAttribute(String a_sAttributeId)
/*     */   {
/* 240 */     return AttributeUtil.getAttributeValueForAttribute(getAttributeValues(), a_sAttributeId);
/*     */   }
/*     */ 
/*     */   public Vector getHiddenSearchAttributeValues()
/*     */   {
/* 246 */     if (this.m_vecSearchHiddenAttributeValues == null)
/*     */     {
/* 249 */       if ((getAttributeValues() != null) && (getAttributeValues().size() > 0))
/*     */       {
/* 251 */         for (int i = 0; i < getAttributeValues().size(); i++)
/*     */         {
/* 253 */           AttributeValue temp = (AttributeValue)getAttributeValues().elementAt(i);
/* 254 */           boolean bAdd = false;
/*     */ 
/* 256 */           if (!temp.getAttribute().isSearchField())
/*     */           {
/* 258 */             bAdd = true;
/*     */           }
/*     */ 
/* 261 */           if (!bAdd)
/*     */             continue;
/* 263 */           if (this.m_vecSearchHiddenAttributeValues == null)
/*     */           {
/* 265 */             this.m_vecSearchHiddenAttributeValues = new Vector();
/*     */           }
/*     */ 
/* 268 */           this.m_vecSearchHiddenAttributeValues.add(temp);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 274 */     return this.m_vecSearchHiddenAttributeValues;
/*     */   }
/*     */ 
/*     */   public Translation createTranslation(Language a_language)
/*     */   {
/* 279 */     return new Translation(a_language);
/*     */   }
/*     */ 
/*     */   public Vector getTranslations()
/*     */   {
/* 284 */     if (this.m_vTranslations == null)
/*     */     {
/* 286 */       this.m_vTranslations = new Vector();
/*     */     }
/* 288 */     return this.m_vTranslations;
/*     */   }
/*     */ 
/*     */   public void setTranslations(Vector a_translations)
/*     */   {
/* 293 */     this.m_vTranslations = a_translations;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 298 */     if ((this.m_language != null) && (!LanguageConstants.k_defaultLanguage.equals(this.m_language)))
/*     */     {
/* 300 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, getTranslations());
/* 301 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getName())))
/*     */       {
/* 303 */         return translation.getName();
/*     */       }
/*     */     }
/* 306 */     return super.getName();
/*     */   }
/*     */ 
/*     */   public class Translation extends BaseFilter
/*     */     implements com.bright.framework.language.bean.Translation
/*     */   {
/*     */     public Translation(Language a_language)
/*     */     {
/* 317 */       this.m_language = a_language;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.filter.bean.Filter
 * JD-Core Version:    0.6.0
 */