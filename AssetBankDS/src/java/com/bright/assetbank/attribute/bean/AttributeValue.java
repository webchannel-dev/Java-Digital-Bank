/*     */ package com.bright.assetbank.attribute.bean;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.taxonomy.bean.Keyword;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.common.bean.BrightDateTime;
/*     */ import com.bright.framework.common.bean.NameValueBean;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.bean.TranslatableWithLanguage;
/*     */ import com.bright.framework.language.bean.Translation;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.Serializable;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class AttributeValue extends BaseAttributeValue
/*     */   implements TranslatableWithLanguage, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2719523998458866164L;
/*  62 */   private long m_lId = 0L;
/*  63 */   private boolean m_bIsEditable = true;
/*  64 */   private String m_sMapToFieldValue = null;
/*  65 */   private boolean m_bRange = false;
/*  66 */   private boolean m_bForceAdd = false;
/*     */   private BrightDate m_dateValue;
/*     */   private BrightDateTime m_dateTimeValue;
/*  74 */   Attribute m_attribute = null;
/*     */ 
/*  77 */   private boolean m_bIsSelected = false;
/*  78 */   private Vector m_vecKeywordCategories = null;
/*     */ 
/*  80 */   private long m_lActionOnAssetId = 0L;
/*     */ 
/*  83 */   private Vector m_vTranslations = null;
            private String m_sValue;
            private Language m_language;
            private String m_sAdditionalValue;
/*     */ 
/*     */   public AttributeValue()
/*     */   {
/*  90 */     this.m_dateValue = new BrightDate();
/*  91 */     this.m_dateTimeValue = new BrightDateTime();
/*     */   }
/*     */ 
/*     */   public AttributeValue(AttributeValue a_toCopy)
/*     */   {
/*  96 */     this.m_lId = a_toCopy.m_lId;
/*  97 */     this.m_sValue = a_toCopy.m_sValue;
/*  98 */     this.m_bIsEditable = a_toCopy.m_bIsEditable;
/*  99 */     this.m_sMapToFieldValue = a_toCopy.m_sMapToFieldValue;
/* 100 */     this.m_bRange = a_toCopy.m_bRange;
/* 101 */     this.m_dateValue = new BrightDate(a_toCopy.m_dateValue);
/* 102 */     this.m_dateTimeValue = new BrightDateTime(a_toCopy.m_dateTimeValue);
/* 103 */     this.m_attribute = a_toCopy.m_attribute;
/* 104 */     this.m_bIsSelected = a_toCopy.m_bIsSelected;
/* 105 */     this.m_vecKeywordCategories = a_toCopy.m_vecKeywordCategories;
/* 106 */     this.m_bForceAdd = a_toCopy.m_bForceAdd;
/* 107 */     this.m_lActionOnAssetId = a_toCopy.m_lActionOnAssetId;
/*     */ 
/* 110 */     setTranslations(a_toCopy.getTranslations());
/*     */   }
/*     */ 
/*     */   public long getId()
/*     */   {
/* 116 */     return this.m_lId;
/*     */   }
/*     */ 
/*     */   public void setId(long a_lId)
/*     */   {
/* 122 */     this.m_lId = a_lId;
/*     */   }
/*     */ 
/*     */   public void setValue(String a_sValue)
/*     */   {
/* 127 */     this.m_sValue = a_sValue;
/*     */ 
/* 130 */     if (getAttribute().getIsDatepicker())
/*     */     {
/* 132 */       this.m_dateValue.setFormDate(a_sValue);
/* 133 */       this.m_dateValue.processFormData();
/*     */     }
/*     */ 
/* 137 */     if (getAttribute().getIsDateTime())
/*     */     {
/* 139 */       this.m_dateTimeValue.setFormDateTime(a_sValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getValue()
/*     */   {
/* 145 */     return getValue(this.m_language);
/*     */   }
/*     */ 
/*     */   public String getValue(Language a_language)
/*     */   {
/* 150 */     String sValue = getValueForLanguage(a_language);
/* 151 */     if (sValue != null)
/*     */     {
/* 153 */       return sValue;
/*     */     }
/* 155 */     return this.m_sValue;
/*     */   }
/*     */ 
/*     */   public String getValueForIndex(Language a_language)
/*     */   {
/* 163 */     String sValue = getValue(a_language);
/* 164 */     if ((sValue != null) && (this.m_attribute.getIsHtml()))
/*     */     {
/* 167 */       sValue = StringUtil.stripHTML(sValue);
/*     */     }
/* 169 */     return sValue;
/*     */   }
/*     */ 
/*     */   public String getValueForLanguage(Language a_language)
/*     */   {
/* 180 */     if (!a_language.equals(LanguageConstants.k_defaultLanguage))
/*     */     {
/* 182 */       Translation translation = (Translation)LanguageUtils.getTranslation(a_language, this.m_vTranslations);
/* 183 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getValue())))
/*     */       {
/* 185 */         return translation.getValue();
/*     */       }
/* 187 */       return null;
/*     */     }
/* 189 */     return this.m_sValue;
/*     */   }
/*     */ 
/*     */   public String getAdditionalValue()
/*     */   {
/* 198 */     return getAdditionalValue(this.m_language);
/*     */   }
/*     */ 
/*     */   public String getAdditionalValue(Language a_language)
/*     */   {
/* 203 */     String sAdditionalValue = getAdditionalValueForLanguage(a_language);
/* 204 */     if (sAdditionalValue != null)
/*     */     {
/* 206 */       return sAdditionalValue;
/*     */     }
/* 208 */     return this.m_sAdditionalValue;
/*     */   }
/*     */ 
/*     */   public String getAdditionalValueForLanguage(Language a_language)
/*     */   {
/* 219 */     if (!a_language.equals(LanguageConstants.k_defaultLanguage))
/*     */     {
/* 221 */       Translation translation = (Translation)LanguageUtils.getTranslation(a_language, this.m_vTranslations);
/* 222 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getAdditionalValue())))
/*     */       {
/* 224 */         return translation.getAdditionalValue();
/*     */       }
/* 226 */       return null;
/*     */     }
/* 228 */     return this.m_sAdditionalValue;
/*     */   }
/*     */ 
/*     */   public String getValueHTML()
/*     */   {
/* 233 */     String sValue = getValue();
/*     */ 
/* 235 */     if (sValue != null)
/*     */     {
/* 237 */       return StringUtil.formatNewlineForHTML(sValue);
/*     */     }
/* 239 */     return null;
/*     */   }
/*     */ 
/*     */   public void setAttribute(Attribute a_field)
/*     */   {
/* 244 */     this.m_attribute = a_field;
/*     */   }
/*     */ 
/*     */   public Attribute getAttribute()
/*     */   {
/* 249 */     if (this.m_attribute == null)
/*     */     {
/* 251 */       this.m_attribute = new Attribute();
/*     */     }
/*     */ 
/* 254 */     return this.m_attribute;
/*     */   }
/*     */ 
/*     */   public void setRange(boolean a_bRange)
/*     */   {
/* 259 */     this.m_bRange = a_bRange;
/*     */   }
/*     */ 
/*     */   public boolean getRange()
/*     */   {
/* 264 */     return this.m_bRange;
/*     */   }
/*     */ 
/*     */   public boolean isEditable()
/*     */   {
/* 275 */     return this.m_bIsEditable;
/*     */   }
/*     */ 
/*     */   public void setEditable(boolean a_sIsEditable)
/*     */   {
/* 281 */     this.m_bIsEditable = a_sIsEditable;
/*     */   }
/*     */ 
/*     */   public boolean getIsSelected()
/*     */   {
/* 286 */     return this.m_bIsSelected;
/*     */   }
/*     */ 
/*     */   public void setIsSelected(boolean a_bIsSelected)
/*     */   {
/* 291 */     this.m_bIsSelected = a_bIsSelected;
/*     */   }
/*     */ 
/*     */   public String getMapToFieldValue()
/*     */   {
/* 296 */     return this.m_sMapToFieldValue;
/*     */   }
/*     */ 
/*     */   public void setMapToFieldValue(String a_sMapToFieldValue)
/*     */   {
/* 301 */     this.m_sMapToFieldValue = a_sMapToFieldValue;
/*     */   }
/*     */ 
/*     */   public BrightDate getDateValue()
/*     */   {
/* 307 */     return this.m_dateValue;
/*     */   }
/*     */ 
/*     */   public BrightDateTime getDateTimeValue()
/*     */   {
/* 313 */     return this.m_dateTimeValue;
/*     */   }
/*     */ 
/*     */   public void setKeywordCategories(Vector a_vecKeywordCategories)
/*     */   {
/* 318 */     this.m_vecKeywordCategories = a_vecKeywordCategories;
/*     */   }
/*     */ 
/*     */   public Vector getKeywordCategories()
/*     */   {
/* 323 */     return this.m_vecKeywordCategories;
/*     */   }
/*     */ 
/*     */   public SortedMap getKeywordMap()
/*     */   {
/* 333 */     TreeMap tmKeywords = null;
/*     */ 
/* 335 */     if (this.m_vecKeywordCategories != null)
/*     */     {
/* 337 */       tmKeywords = new TreeMap();
/*     */ 
/* 339 */       for (int i = 0; i < this.m_vecKeywordCategories.size(); i++)
/*     */       {
/* 341 */         Keyword cat = (Keyword)this.m_vecKeywordCategories.get(i);
/* 342 */         Category rootAncestor = cat.getRootAncestor();
/* 343 */         Vector vCats = null;
/*     */ 
/* 345 */         if (rootAncestor == null)
/*     */         {
/* 347 */           rootAncestor = cat;
/* 348 */           cat = null;
/*     */         }
/*     */ 
/* 351 */         if (!tmKeywords.containsKey(rootAncestor))
/*     */         {
/* 353 */           vCats = new Vector();
/* 354 */           tmKeywords.put(rootAncestor, vCats);
/*     */         }
/*     */         else
/*     */         {
/* 358 */           vCats = (Vector)tmKeywords.get(rootAncestor);
/*     */         }
/*     */ 
/* 361 */         if (cat == null)
/*     */           continue;
/* 363 */         vCats.add(cat);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 368 */     return tmKeywords;
/*     */   }
/*     */ 
/*     */   public boolean isIncomplete(Language a_language)
/*     */   {
/* 377 */     boolean bResult = false;
/*     */ 
/* 379 */     if ((getAttribute().isMandatory()) || (getAttribute().isRequiredForCompleteness()))
/*     */     {
/* 381 */       if (((getAttribute().getIsTextarea()) || (getAttribute().getIsTextfield())) && (getAttribute().getRequiresTranslation()))
/*     */       {
/* 383 */         bResult = StringUtils.isEmpty(getValueForLanguage(a_language));
/*     */       }
/* 385 */       else if (getAttribute().getIsKeywordPicker())
/*     */       {
/* 387 */         bResult = (this.m_vecKeywordCategories == null) || (this.m_vecKeywordCategories.size() == 0);
/*     */       }
/*     */       else
/*     */       {
/* 391 */         bResult = StringUtils.isEmpty(this.m_sValue);
/*     */       }
/*     */     }
/*     */ 
/* 395 */     return bResult;
/*     */   }
/*     */ 
/*     */   public boolean hasNoTranslation(Language a_language)
/*     */   {
/* 404 */     return ((getAttribute().getIsTextarea()) || (getAttribute().getIsTextfield())) && (StringUtils.isEmpty(getValueForLanguage(a_language)));
/*     */   }
/*     */ 
/*     */   public void setLanguage(Language a_language)
/*     */   {
/* 414 */     super.setLanguage(a_language);
/*     */ 
/* 417 */     if ((this.m_attribute != null) && (!this.m_attribute.getLanguage().equals(a_language)))
/*     */     {
/* 419 */       this.m_attribute.setLanguage(a_language);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vector getTranslations()
/*     */   {
/* 428 */     if (this.m_vTranslations == null)
/*     */     {
/* 430 */       this.m_vTranslations = new Vector();
/*     */     }
/* 432 */     return this.m_vTranslations;
/*     */   }
/*     */ 
/*     */   public void setTranslations(Vector translations)
/*     */   {
/* 437 */     this.m_vTranslations = translations;
/*     */   }
/*     */ 
/*     */   public Translation createTranslation(Language a_language)
/*     */   {
/* 442 */     return new Translation(a_language);
/*     */   }
/*     */ 
/*     */   public String[] getDelimitedValues()
/*     */   {
/* 447 */     if (this.m_sValue != null)
/*     */     {
/* 449 */       return this.m_sValue.split(",");
/*     */     }
/* 451 */     return new String[0];
/*     */   }
/*     */ 
/*     */   public NameValueBean[] getNameValuePairs()
/*     */   {
/* 456 */     if (this.m_sValue != null)
/*     */     {
/* 458 */       return StringUtil.getNameValuePairs(this.m_sValue, "[\\n\\r]+", "==", false);
/*     */     }
/* 460 */     return new NameValueBean[0];
/*     */   }
/*     */ 
/*     */   public long getActionOnAssetId()
/*     */   {
/* 548 */     return this.m_lActionOnAssetId;
/*     */   }
/*     */ 
/*     */   public void setActionOnAssetId(long actionOnAssetId)
/*     */   {
/* 556 */     this.m_lActionOnAssetId = actionOnAssetId;
/*     */   }
/*     */ 
/*     */   public void setForceAdd(boolean a_bForceAdd)
/*     */   {
/* 561 */     this.m_bForceAdd = a_bForceAdd;
/*     */   }
/*     */ 
/*     */   public boolean getForceAdd()
/*     */   {
/* 566 */     return this.m_bForceAdd;
/*     */   }
/*     */ 
/*     */   public static long getAutoincrementNumberValue(String a_sPrefix, String a_sValue)
/*     */     throws NumberFormatException
/*     */   {
/* 581 */     long lValue = 0L;
/*     */ 
/* 583 */     String sValue = a_sValue.trim();
/*     */ 
/* 588 */     if ((StringUtil.stringIsPopulated(a_sPrefix)) && (!sValue.equals("-1")))
/*     */     {
/* 590 */       if ((sValue.startsWith(a_sPrefix)) && (sValue.length() > a_sPrefix.length()))
/*     */       {
/* 592 */         sValue = sValue.substring(a_sPrefix.length());
/*     */       }
/*     */       else
/*     */       {
/* 596 */         GlobalApplication.getInstance().getLogger().warn("AttributeValue.getAutoincrementNumberValue: value: " + sValue + " does not start with prefix: " + a_sPrefix);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 602 */     if (StringUtil.stringIsPopulated(sValue))
/*     */     {
/*     */       try
/*     */       {
/* 606 */         lValue = Long.parseLong(sValue.trim());
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/* 610 */         GlobalApplication.getInstance().getLogger().warn("AttributeValue.getAutoincrementNumberValue: value: " + sValue + " does not parse to a number.");
/* 611 */         throw e;
/*     */       }
/*     */     }
/*     */ 
/* 615 */     return lValue;
/*     */   }
/*     */ 
/*     */   public class Translation extends BaseAttributeValue
/*     */     implements com.bright.framework.language.bean.Translation
/*     */   {
/* 470 */     private Attribute.Translation m_attributeTranslation = null;
/*     */ 
/*     */     public Translation(Language a_language)
/*     */     {
/* 474 */       this.m_language = a_language;
/*     */     }
/*     */ 
/*     */     public long getAttributeValueId()
/*     */     {
/* 479 */       return AttributeValue.this.getId();
/*     */     }
/*     */ 
/*     */     public long getAttributeId()
/*     */     {
/* 484 */       return AttributeValue.this.getAttribute().getId();
               // return AttributeValue.this.getAttribute().
/*     */     }
/*     */ 
/*     */     public Attribute.Translation getAttributeTranslation()
/*     */     {
/* 489 */       return this.m_attributeTranslation;
/*     */     }
/*     */ 
/*     */     public void setAttributeTranslation(Attribute.Translation attributeTranslation)
/*     */     {
/* 494 */       this.m_attributeTranslation = attributeTranslation;
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 503 */       int PRIME = 31;
/* 504 */       int result = 1;
/* 505 */       result = (int)(31 * result + this.m_language.getId() + getAttributeId());
/* 506 */       return result;
/*     */     }
/*     */ 
/*     */     public boolean equals(Object obj)
/*     */     {
/* 515 */       if (this == obj)
/*     */       {
/* 517 */         return true;
/*     */       }
/* 519 */       if (obj == null)
/*     */       {
/* 521 */         return false;
/*     */       }
/* 523 */       if (getClass() != obj.getClass())
/*     */       {
/* 525 */         return false;
/*     */       }
/* 527 */       Translation other = (Translation)obj;
/*     */ 
/* 530 */       return (this.m_language.getId() == other.m_language.getId()) && (getAttributeId() == other.getAttributeId());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.bean.AttributeValue
 * JD-Core Version:    0.6.0
 */