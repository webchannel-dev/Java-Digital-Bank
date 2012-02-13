/*     */ package com.bright.assetbank.synchronise.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.agreements.bean.Agreement;
/*     */ import com.bright.assetbank.agreements.util.AgreementUtil;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue.Translation;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.category.bean.ExtendedCategoryInfo;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.synchronise.util.SynchUtil;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.common.bean.BrightDateTime;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.file.BeanWrapper;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.util.DateUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public abstract class ExportAssetBeanWrapper
/*     */   implements ExportAsset, ImportAsset, BeanWrapper
/*     */ {
/*  69 */   private static AttributeManager m_attributeManager = null;
/*  70 */   private static final Object m_attributeManagerLock = new Object();
/*     */   private static final String c_ksExtendsCategorySeparator = ":";
/*  73 */   private Asset m_asset = null;
/*  74 */   private DateFormat m_format = DateUtil.getExportDateFormat();
/*     */ 
/*     */   public ExportAssetBeanWrapper()
/*     */   {
/*  78 */     this.m_asset = new Asset();
/*     */   }
/*     */ 
/*     */   public ExportAssetBeanWrapper(Asset a_asset)
/*     */   {
/*  83 */     if (a_asset == null)
/*     */     {
/*  85 */       throw new IllegalArgumentException("The passed asset cannot be null");
/*     */     }
/*  87 */     this.m_asset = a_asset;
/*     */   }
/*     */ 
/*     */   public void setDateFormat(DateFormat a_format)
/*     */   {
/*  92 */     this.m_format = a_format;
/*     */   }
/*     */ 
/*     */   public void setObjectToWrap(Object a_source)
/*     */   {
/* 103 */     if (!(a_source instanceof Asset))
/*     */     {
/* 105 */       throw new IllegalArgumentException("ExportAssetBeanWrapper can only wrap Asset types");
/*     */     }
/* 107 */     setAsset((Asset)a_source);
/*     */   }
/*     */ 
/*     */   public Object getWrappedObject()
/*     */   {
/* 112 */     return getAsset();
/*     */   }
/*     */ 
/*     */   public Asset getAsset()
/*     */   {
/* 124 */     return this.m_asset;
/*     */   }
/*     */ 
/*     */   public void setAsset(Asset a_asset)
/*     */   {
/* 129 */     this.m_asset = a_asset;
/*     */   }
/*     */ 
/*     */   public String getAccessLevels()
/*     */   {
/* 136 */     return getCategoryIdList(this.m_asset.getPermissionCategories());
/*     */   }
/*     */ 
/*     */   public String getApproved()
/*     */   {
/* 141 */     String sApproval = "";
/* 142 */     Vector vecCategories = this.m_asset.getApprovedPermissionCategories();
/* 143 */     if ((vecCategories != null) && (vecCategories.size() > 0))
/*     */     {
/* 145 */       String[] sFullCategoryNames = new String[vecCategories.size()];
/*     */ 
/* 147 */       for (int i = 0; i < vecCategories.size(); i++)
/*     */       {
/* 149 */         Category cat = (Category)vecCategories.elementAt(i);
/* 150 */         sFullCategoryNames[i] = cat.getJSUnicodeFullName();
/*     */       }
/*     */ 
/* 153 */       sApproval = SynchUtil.pack(sFullCategoryNames);
/*     */     }
/* 155 */     return sApproval;
/*     */   }
/*     */ 
/*     */   public String getAttributeValue(long a_iId)
/*     */   {
/* 160 */     AttributeValue value = this.m_asset.getAttributeValue(a_iId);
/* 161 */     String sValue = null;
/*     */ 
/* 163 */     if (value != null)
/*     */     {
/* 165 */       if (value.getAttribute().getIsDatepicker())
/*     */       {
/* 167 */         if (value.getDateValue() != null)
/*     */         {
/* 169 */           sValue = valueOf(value.getDateValue().getDate());
/*     */         }
/*     */       }
/* 172 */       else if (value.getAttribute().getIsDateTime())
/*     */       {
/* 174 */         if (value.getDateTimeValue() != null)
/*     */         {
/* 176 */           sValue = valueOf(value.getDateTimeValue().getDate());
/*     */         }
/*     */       }
/* 179 */       else if (value.getAttribute().getIsKeywordPicker())
/*     */       {
/* 181 */         if (value.getKeywordCategories() != null)
/*     */         {
/* 183 */           sValue = "";
/* 184 */           for (int i = 0; i < value.getKeywordCategories().size(); i++)
/*     */           {
/* 186 */             Category keyword = (Category)value.getKeywordCategories().elementAt(i);
/* 187 */             sValue = sValue + getCategoryPath(keyword, AssetBankSettings.getKeywordAnscestorDelimiter()) + AssetBankSettings.getKeywordDelimiter();
/*     */           }
/*     */         }
/*     */       }
/* 191 */       else if (value.getAttribute().getIsExternalDictionary())
/*     */       {
/* 193 */         if (AssetBankSettings.getExportExternalDictionaryAsString())
/*     */         {
/* 195 */           sValue = valueOf(value.getAdditionalValue());
/*     */         }
/*     */         else
/*     */         {
/* 199 */           sValue = valueOf(value.getValue());
/*     */         }
/*     */       }
/* 202 */       else if ((value.getAttribute().getIsCheckList()) || (value.getAttribute().getIsOptionList()))
/*     */       {
/* 204 */         sValue = "";
/* 205 */         if (value.getAttribute().getListOptionValues() != null)
/*     */         {
/* 207 */           int iCount = value.getAttribute().getListOptionValues().size();
/* 208 */           String[] values = new String[iCount];
/* 209 */           for (int i = 0; i < iCount; i++)
/*     */           {
/* 211 */             AttributeValue listvalue = (AttributeValue)value.getAttribute().getListOptionValues().elementAt(i);
/* 212 */             values[i] = listvalue.getValue();
/*     */           }
/* 214 */           sValue = SynchUtil.pack(values);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 219 */         sValue = valueOf(value.getValue());
/*     */       }
/*     */     }
/* 222 */     return sValue;
/*     */   }
/*     */ 
/*     */   public String getAttributeValueTranslation(long a_lId, Language language)
/*     */   {
/* 227 */     AttributeValue value = this.m_asset.getAttributeValue(a_lId);
/*     */ 
/* 229 */     if ((value != null) && (value.getAttribute().getIsTranslatable()))
/*     */     {
/* 231 */       AttributeValue.Translation translation = (AttributeValue.Translation)LanguageUtils.getTranslation(language, value.getTranslations());
/* 232 */       return translation.getValue();
/*     */     }
/* 234 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isAttributeTranslatable(long a_lId)
/*     */   {
/* 239 */     AttributeValue val = this.m_asset.getAttributeValue(a_lId);
/* 240 */     if (val != null)
/*     */     {
/* 242 */       Attribute attribute = val.getAttribute();
/* 243 */       return attribute.getIsTranslatable();
/*     */     }
/* 245 */     return false;
/*     */   }
/*     */ 
/*     */   public String getAuthor()
/*     */   {
/* 250 */     return valueOf(this.m_asset.getAuthor());
/*     */   }
/*     */ 
/*     */   public String getCode()
/*     */   {
/* 255 */     return valueOf(this.m_asset.getCode());
/*     */   }
/*     */ 
/*     */   public String getDateAdded()
/*     */   {
/* 260 */     return valueOf(this.m_asset.getDateAdded());
/*     */   }
/*     */ 
/*     */   public String getDateModified()
/*     */   {
/* 265 */     return valueOf(this.m_asset.getDateLastModified());
/*     */   }
/*     */ 
/*     */   public String getDescriptiveCategories()
/*     */   {
/* 270 */     return getCategoryIdList(this.m_asset.getDescriptiveCategories());
/*     */   }
/*     */ 
/*     */   public String getExpiryDate()
/*     */   {
/* 275 */     return valueOf(this.m_asset.getExpiryDate());
/*     */   }
/*     */ 
/*     */   public String getExtendsCategory()
/*     */   {
/* 280 */     if ((this.m_asset.getExtendsCategory() != null) && (this.m_asset.getExtendsCategory().getId() > 0L))
/*     */     {
/* 282 */       String sIdentifier = String.valueOf(this.m_asset.getExtendsCategory().getCategoryTypeId()) + ":" + this.m_asset.getExtendsCategory().getName();
/* 283 */       return sIdentifier;
/*     */     }
/* 285 */     return "";
/*     */   }
/*     */ 
/*     */   public String getFilename()
/*     */   {
/* 290 */     return SynchUtil.getExportFilename(this.m_asset);
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 295 */     return String.valueOf(this.m_asset.getId());
/*     */   }
/*     */ 
/*     */   public String getIsPreviewRestricted()
/*     */   {
/* 300 */     return String.valueOf(this.m_asset.getIsRestricted());
/*     */   }
/*     */ 
/*     */   public String getAdvancedViewing()
/*     */   {
/* 305 */     return String.valueOf(this.m_asset.getAdvancedViewing());
/*     */   }
/*     */ 
/*     */   public String getIsSensitive()
/*     */   {
/* 310 */     return String.valueOf(this.m_asset.getIsSensitive());
/*     */   }
/*     */ 
/*     */   public String getSensitivityNotes()
/*     */   {
/* 315 */     return getAttributeValue(301L);
/*     */   }
/*     */ 
/*     */   public String getLastModifiedByUser()
/*     */   {
/* 320 */     if ((this.m_asset.getLastModifiedByUser() != null) && (this.m_asset.getLastModifiedByUser().getId() > 0L))
/*     */     {
/* 322 */       return String.valueOf(this.m_asset.getLastModifiedByUser().getId());
/*     */     }
/* 324 */     return null;
/*     */   }
/*     */ 
/*     */   public String getAddedByUser()
/*     */   {
/* 329 */     if ((this.m_asset.getAddedByUser() != null) && (this.m_asset.getAddedByUser().getId() > 0L))
/*     */     {
/* 331 */       return String.valueOf(this.m_asset.getAddedByUser().getId());
/*     */     }
/* 333 */     return null;
/*     */   }
/*     */ 
/*     */   public String getPrice()
/*     */   {
/* 338 */     if (this.m_asset.getPrice() != null)
/*     */     {
/* 340 */       return String.valueOf(this.m_asset.getPrice().getAmount());
/*     */     }
/* 342 */     return null;
/*     */   }
/*     */ 
/*     */   public String getAgreementType()
/*     */   {
/* 347 */     if (this.m_asset.getAgreementTypeId() > 0L)
/*     */     {
/* 349 */       return String.valueOf(this.m_asset.getAgreementTypeId());
/*     */     }
/* 351 */     return null;
/*     */   }
/*     */ 
/*     */   public String getAgreements()
/*     */   {
/* 357 */     if ((AssetBankSettings.getAgreementsEnabled()) && (AssetBankSettings.exportAgreementData()) && (this.m_asset.getHasAgreement()))
/*     */     {
/* 359 */       List agreements = new ArrayList();
/* 360 */       if (this.m_asset.getHasAgreement())
/*     */       {
/* 362 */         agreements.add(this.m_asset.getAgreement());
/*     */       }
/* 364 */       if (this.m_asset.getPreviousAgreementsCount() > 0)
/*     */       {
/* 366 */         agreements.addAll(this.m_asset.getPreviousAgreements());
/*     */       }
/* 368 */       return AgreementUtil.getAgreementsAsXml(agreements);
/*     */     }
/* 370 */     return null;
/*     */   }
/*     */ 
/*     */   public abstract void setAccessLevels(String paramString)
/*     */     throws Bn2Exception;
/*     */ 
/*     */   public void setAddedByUser(String a_sValue)
/*     */   {
/* 382 */     if (StringUtils.isNotEmpty(a_sValue))
/*     */     {
/*     */       try
/*     */       {
/* 386 */         long lId = Long.parseLong(a_sValue);
/* 387 */         if (this.m_asset.getAddedByUser() == null)
/*     */         {
/* 389 */           this.m_asset.setAddedByUser(new ABUser());
/* 390 */           this.m_asset.getAddedByUser().setId(lId);
/*     */         }
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setLastModifiedByUser(String a_sValue)
/*     */   {
/* 405 */     if (StringUtils.isNotEmpty(a_sValue))
/*     */     {
/*     */       try
/*     */       {
/* 409 */         long lId = Long.parseLong(a_sValue);
/* 410 */         if (this.m_asset.getLastModifiedByUser() == null)
/*     */         {
/* 412 */           this.m_asset.setLastModifiedByUser(new ABUser());
/* 413 */           this.m_asset.getLastModifiedByUser().setId(lId);
/*     */         }
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setApproved(String a_sValue)
/*     */   {
/* 425 */     if ((a_sValue.equals("TRUE")) || (a_sValue.equals("FALSE")))
/*     */     {
/* 428 */       this.m_asset.setImportApprovalDirective(a_sValue);
/*     */     }
/*     */     else
/*     */     {
/* 433 */       Vector vecApprovedCategoryName = SynchUtil.unpackToVector(a_sValue);
/* 434 */       this.m_asset.setImportApprovedAccessLevels(vecApprovedCategoryName);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAttributeValue(AttributeValue a_value)
/*     */   {
/* 448 */     if (a_value != null)
/*     */     {
/* 450 */       this.m_asset.addAttributeValue(a_value);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAuthor(String a_sValue)
/*     */   {
/* 456 */     this.m_asset.setAuthor(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setCode(String a_sValue)
/*     */   {
/* 461 */     this.m_asset.setCode(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setDateAdded(String a_sValue)
/*     */   {
/* 466 */     this.m_asset.setDateAdded(getDateValue(a_sValue));
/*     */   }
/*     */ 
/*     */   public void setDateModified(String a_sValue)
/*     */   {
/* 471 */     this.m_asset.setDateLastModified(getDateValue(a_sValue));
/*     */   }
/*     */ 
/*     */   public abstract void setDescriptiveCategories(String paramString) throws Bn2Exception;
/*     */ 
/*     */   public void setExpiryDate(String a_sValue) {
/* 478 */     this.m_asset.setExpiryDate(getDateValue(a_sValue));
/*     */   }
/*     */ 
/*     */   public void setFilename(String a_sValue)
/*     */   {
/* 483 */     this.m_asset.setFileLocation(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setId(String a_sValue)
/*     */   {
/* 488 */     this.m_asset.setImportedAssetId(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setIsPreviewRestricted(String a_sValue)
/*     */   {
/* 493 */     this.m_asset.setIsRestricted(Boolean.parseBoolean(a_sValue));
/*     */   }
/*     */ 
/*     */   public void setAdvancedViewing(String a_sValue)
/*     */   {
/* 498 */     this.m_asset.setAdvancedViewing(Boolean.parseBoolean(a_sValue));
/*     */   }
/*     */ 
/*     */   public void setExtendsCategory(String a_sValue)
/*     */   {
/* 503 */     if (StringUtil.stringIsPopulated(a_sValue))
/*     */     {
/* 505 */       String[] aValues = a_sValue.split(":");
/*     */       try
/*     */       {
/* 508 */         long lTypeId = Long.parseLong(aValues[0]);
/* 509 */         String sName = aValues[1];
/*     */ 
/* 512 */         CategoryManager catMan = (CategoryManager)(CategoryManager)GlobalApplication.getInstance().getComponentManager().lookup("CategoryManager");
/* 513 */         Category category = catMan.getCategory(null, lTypeId, sName, true);
/* 514 */         if (category != null)
/*     */         {
/* 516 */           this.m_asset.getExtendsCategory().setCategoryTypeId(category.getCategoryTypeId());
/* 517 */           this.m_asset.getExtendsCategory().setId(category.getId());
/* 518 */           this.m_asset.getExtendsCategory().setParentId(category.getParentId());
/* 519 */           this.m_asset.getExtendsCategory().setName(category.getName());
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 524 */         String sMessage = "Unable to link to extension category: " + a_sValue;
/* 525 */         GlobalApplication.getInstance().getLogger().error(sMessage, e);
/* 526 */         throw new RuntimeException(sMessage, e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setIsSensitive(String a_sValue)
/*     */   {
/* 533 */     this.m_asset.setIsSensitive(("Y".equalsIgnoreCase(a_sValue)) || (Boolean.parseBoolean(a_sValue)));
/*     */   }
/*     */ 
/*     */   public void setSensitivityNotes(String a_sValue)
/*     */   {
/*     */     Attribute attribute;
/*     */     try {
/* 541 */       attribute = getAttributeManager().getAttribute(null, 301L);
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 545 */       throw new RuntimeException(e);
/*     */     }
/*     */ 
/* 548 */     AttributeValue value = new AttributeValue();
/* 549 */     value.setValue(a_sValue);
/* 550 */     value.setAttribute(attribute);
/* 551 */     setAttributeValue(value);
/*     */   }
/*     */ 
/*     */   private static AttributeManager getAttributeManager()
/*     */   {
/* 556 */     synchronized (m_attributeManagerLock)
/*     */     {
/* 558 */       if (m_attributeManager == null)
/*     */       {
/*     */         try
/*     */         {
/* 562 */           m_attributeManager = (AttributeManager)GlobalApplication.getInstance().getComponentManager().lookup("AttributeManager");
/*     */         }
/*     */         catch (ComponentException ce)
/*     */         {
/* 567 */           throw new RuntimeException(ce);
/*     */         }
/*     */       }
/*     */     }
/* 571 */     return m_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setPrice(String a_sValue)
/*     */   {
/* 576 */     this.m_asset.setPrice(new BrightMoney(getLongValue(a_sValue)));
/*     */   }
/*     */ 
/*     */   public void setRelatedAssets(String a_sValue)
/*     */   {
/* 581 */     this.m_asset.setPeerAssetIdsAsString(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setParentAssets(String a_sValue)
/*     */   {
/* 586 */     this.m_asset.setParentAssetIdsAsString(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setChildAssets(String a_sValue)
/*     */   {
/* 591 */     this.m_asset.setChildAssetIdsAsString(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setEntityId(String a_sValue)
/*     */   {
/* 596 */     this.m_asset.getEntity().setId(getLongValue(a_sValue));
/*     */   }
/*     */ 
/*     */   public void setOriginalFilename(String a_sOriginalFilename)
/*     */   {
/* 601 */     this.m_asset.setOriginalFilename(a_sOriginalFilename);
/*     */   }
/*     */ 
/*     */   public void setAgreementType(String a_sAgreementType)
/*     */   {
/* 606 */     this.m_asset.setAgreementTypeId(getLongValue(a_sAgreementType));
/*     */   }
/*     */ 
/*     */   public void setAgreements(String a_sAgreements)
/*     */   {
/* 611 */     if ((AssetBankSettings.getAgreementsEnabled()) && (StringUtils.isNotEmpty(a_sAgreements)))
/*     */     {
/* 613 */       List agreements = AgreementUtil.getAgreementsFromXml(a_sAgreements);
/*     */ 
/* 615 */       if ((agreements != null) && (agreements.size() > 0))
/*     */       {
/* 617 */         this.m_asset.setAgreement((Agreement)agreements.remove(0));
/*     */ 
/* 619 */         if (agreements.size() > 0)
/*     */         {
/* 621 */           this.m_asset.setPreviousAgreements(new Vector(agreements));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getCategoryIdList(List<Category> a_categories)
/*     */   {
/* 638 */     if ((a_categories == null) || (a_categories.isEmpty()))
/*     */     {
/* 640 */       return null;
/*     */     }
/*     */ 
/* 643 */     String[] categoryIds = new String[a_categories.size()];
/*     */ 
/* 645 */     for (int i = 0; i < a_categories.size(); i++)
/*     */     {
/* 647 */       Category cat = (Category)a_categories.get(i);
/* 648 */       categoryIds[i] = String.valueOf(cat.getId());
/*     */     }
/*     */ 
/* 651 */     return SynchUtil.pack(categoryIds);
/*     */   }
/*     */ 
/*     */   protected static String getCategoryPath(Category a_cat, String a_sAncestorDelimiter)
/*     */   {
/* 661 */     if (a_cat == null)
/*     */     {
/* 663 */       return "";
/*     */     }
/* 665 */     Category parent = a_cat.getParent();
/* 666 */     if (parent == null)
/*     */     {
/* 668 */       return a_cat.getName();
/*     */     }
/* 670 */     return getCategoryPath(parent, a_sAncestorDelimiter) + a_sAncestorDelimiter + a_cat.getName();
/*     */   }
/*     */ 
/*     */   protected String valueOf(Date a_date)
/*     */   {
/* 681 */     if (a_date != null)
/*     */     {
/* 683 */       return this.m_format.format(a_date);
/*     */     }
/* 685 */     return null;
/*     */   }
/*     */ 
/*     */   protected Date getDateValue(String a_sValue)
/*     */   {
/*     */     try
/*     */     {
/* 698 */       return this.m_format.parse(a_sValue);
/*     */     }
/*     */     catch (ParseException pe) {
/*     */     }
/* 702 */     return null;
/*     */   }
/*     */ 
/*     */   protected static String valueOf(Object a_object)
/*     */   {
/* 715 */     if (a_object != null)
/*     */     {
/* 717 */       return String.valueOf(a_object);
/*     */     }
/* 719 */     return null;
/*     */   }
/*     */ 
/*     */   protected static long getLongValue(String a_sValue)
/*     */   {
/* 730 */     if (a_sValue != null)
/*     */     {
/* 732 */       a_sValue = a_sValue.trim();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 737 */       return Long.parseLong(a_sValue);
/*     */     }
/*     */     catch (NumberFormatException nfe) {
/*     */     }
/* 741 */     return 0L;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.ExportAssetBeanWrapper
 * JD-Core Version:    0.6.0
 */