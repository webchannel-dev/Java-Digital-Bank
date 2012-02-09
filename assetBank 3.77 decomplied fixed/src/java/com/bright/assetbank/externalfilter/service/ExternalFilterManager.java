/*     */ package com.bright.assetbank.externalfilter.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.common.bean.BrightDateTime;
/*     */ import com.bright.framework.common.exception.SettingNotFoundException;
/*     */ import com.bright.framework.common.service.RefDataManager;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.search.bean.IndexableDocument;
/*     */ import com.bright.framework.search.bean.IterableIndexableDocument;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class ExternalFilterManager extends Bn2Manager
/*     */ {
/*     */   private static final String c_ksClassName = "ExternalFilterManager";
/*  62 */   private RefDataManager m_refDataManager = null;
/*     */   private boolean m_bHaveFilterImpl;
/*     */   private ExternalFilter m_filterImpl;
/*  73 */   private HashMap<Long, String> m_hmAttributeToFieldName = new HashMap();
/*  74 */   private HashMap<String, Long> m_hmFieldNameToAttribute = new HashMap();
/*     */ 
/*  77 */   private HashMap<Long, String> m_hmAttributeValueToFieldValue = new HashMap();
/*  78 */   private HashMap<String, Long> m_hmFieldValueToAttributeValue = new HashMap();
/*     */ 
/*  81 */   private HashMap<Long, Long> m_hmExcludedTypeIds = new HashMap();
/*     */ 
/*     */   public void setRefDataManager(RefDataManager a_refDataManager)
/*     */   {
/*  65 */     this.m_refDataManager = a_refDataManager;
/*     */   }
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  87 */     super.startup();
/*     */ 
/*  89 */     String filterClassName = FrameworkSettings.getExternalFilterClassName();
/*  90 */     if (StringUtils.isEmpty(filterClassName))
/*     */     {
/*  92 */       this.m_logger.info("ExternalFilterManager: no external filter class configured");
/*  93 */       this.m_bHaveFilterImpl = false;
/*     */     }
/*     */     else
/*     */     {
/*  97 */       this.m_logger.info("ExternalFilterManager: instantiating external filter class " + filterClassName);
/*     */       Class filterClass;
/*     */       try {
/* 102 */         filterClass = Class.forName(filterClassName);
/*     */       }
/*     */       catch (ClassNotFoundException e)
/*     */       {
/* 106 */         throw new Bn2Exception("Could not load external filter class " + filterClassName, e);
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 111 */         this.m_filterImpl = ((ExternalFilter)filterClass.newInstance());
/*     */       }
/*     */       catch (InstantiationException e)
/*     */       {
/* 115 */         throw new Bn2Exception("Could not instantiate external filter class " + filterClassName, e);
/*     */       }
/*     */       catch (IllegalAccessException e)
/*     */       {
/* 119 */         throw new Bn2Exception("Could not instantiate external filter class " + filterClassName, e);
/*     */       }
/*     */ 
/* 122 */       this.m_filterImpl.setLogger(this.m_logger);
/*     */ 
/* 124 */       this.m_bHaveFilterImpl = true;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 131 */       String sAttributeMappings = this.m_refDataManager.getSystemSetting("ExternalFilterAttributeMapping");
/* 132 */       if (StringUtil.stringIsPopulated(sAttributeMappings))
/*     */       {
/* 134 */         populateAttributeMappings(sAttributeMappings);
/*     */       }
/*     */ 
/* 138 */       String sAttributeValueMappings = this.m_refDataManager.getSystemSetting("ExternalFilterAttributeValueMapping");
/* 139 */       if (StringUtil.stringIsPopulated(sAttributeValueMappings))
/*     */       {
/* 141 */         populateAttributeValueMappings(sAttributeValueMappings);
/*     */       }
/*     */ 
/* 145 */       String sExcludedTypeIds = this.m_refDataManager.getSystemSetting("ExternalFilterExcludedAssetTypes");
/* 146 */       if (StringUtil.stringIsPopulated(sExcludedTypeIds))
/*     */       {
/* 148 */         populateExcludedTypeIds(sExcludedTypeIds);
/*     */       }
/*     */     }
/*     */     catch (SettingNotFoundException e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void populateSearchCriteria(HttpServletRequest a_request, SearchQuery a_criteria, List<String> a_errors)
/*     */   {
/* 166 */     if (this.m_bHaveFilterImpl)
/*     */     {
/* 169 */       Map externalFilterCriteria = RequestUtil.getRequestParametersAsMap(a_request, "ef_", false);
/*     */ 
/* 173 */       a_criteria.setExternalFilterCriteria(externalFilterCriteria);
/*     */ 
/* 176 */       this.m_filterImpl.validateSearchCriteria(externalFilterCriteria, a_errors);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean emptySearchCriteria(SearchQuery a_criteria)
/*     */   {
/* 189 */     if (this.m_bHaveFilterImpl)
/*     */     {
/* 191 */       return this.m_filterImpl.emptySearchCriteria(a_criteria);
/*     */     }
/*     */ 
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */   public Collection<Long> externalSearch(SearchQuery a_searchQuery)
/*     */     throws Bn2Exception
/*     */   {
/* 206 */     if (this.m_bHaveFilterImpl)
/*     */     {
/* 208 */       return this.m_filterImpl.externalSearch(a_searchQuery);
/*     */     }
/*     */ 
/* 211 */     return null;
/*     */   }
/*     */ 
/*     */   public void clearIndex()
/*     */     throws Bn2Exception
/*     */   {
/* 223 */     if (this.m_bHaveFilterImpl)
/*     */     {
/* 225 */       this.m_filterImpl.clearIndex();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void indexDocument(IndexableDocument a_doc, boolean a_bReindex)
/*     */     throws Bn2Exception
/*     */   {
/* 239 */     if (this.m_bHaveFilterImpl)
/*     */     {
/* 242 */       Asset asset = (Asset)a_doc;
/* 243 */       this.m_filterImpl.indexAsset(asset, a_bReindex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void indexDocuments(Vector a_vecDocs, boolean a_bReindex, boolean a_bQuick, boolean a_bOnlyUsageChanged)
/*     */     throws Bn2Exception
/*     */   {
/* 260 */     if (this.m_bHaveFilterImpl)
/*     */     {
/* 263 */       this.m_filterImpl.indexAssets(a_vecDocs, a_bReindex, a_bQuick, a_bOnlyUsageChanged);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeDocument(IterableIndexableDocument a_doc)
/*     */     throws Bn2Exception
/*     */   {
/* 276 */     if (this.m_bHaveFilterImpl)
/*     */     {
/* 278 */       this.m_filterImpl.removeAsset(a_doc.getId());
/*     */     }
/*     */   }
/*     */ 
/*     */   public HashMap<Long, Long> getExcludedTypeIdsMap()
/*     */   {
/* 287 */     return this.m_hmExcludedTypeIds;
/*     */   }
/*     */ 
/*     */   public boolean isAssetTypeExcluded(long a_lId)
/*     */   {
/* 298 */     boolean bExcluded = getExcludedTypeIdsMap().containsKey(Long.valueOf(a_lId));
/*     */ 
/* 300 */     return bExcluded;
/*     */   }
/*     */ 
/*     */   public HashMap<Long, String> getAttributeToFieldNameMap()
/*     */   {
/* 307 */     return this.m_hmAttributeToFieldName;
/*     */   }
/*     */ 
/*     */   public HashMap<String, Long> getFieldNameToAttributeMap()
/*     */   {
/* 312 */     return this.m_hmFieldNameToAttribute;
/*     */   }
/*     */ 
/*     */   public HashMap<Long, String> getAttributeValueToFieldValueMap()
/*     */   {
/* 317 */     return this.m_hmAttributeValueToFieldValue;
/*     */   }
/*     */ 
/*     */   public HashMap<String, Long> getFieldValueToAttributeValueMap()
/*     */   {
/* 322 */     return this.m_hmFieldValueToAttributeValue;
/*     */   }
/*     */ 
/*     */   public AttributeValue getFieldValue(Asset a_asset, String a_field)
/*     */   {
/* 330 */     Long attributeId = (Long)getFieldNameToAttributeMap().get(a_field);
/* 331 */     if (attributeId == null)
/*     */     {
/* 333 */       return null;
/*     */     }
/* 335 */     return a_asset.getAttributeValue(attributeId.longValue());
/*     */   }
/*     */ 
/*     */   public long getAttributeValueId(Asset a_asset, String a_field)
/*     */     throws Bn2Exception
/*     */   {
/* 345 */     AttributeValue av = getFieldValue(a_asset, a_field);
/* 346 */     if (av == null)
/*     */     {
/* 348 */       return 0L;
/*     */     }
/*     */ 
/* 351 */     return av.getId();
/*     */   }
/*     */ 
/*     */   public long getMappedFieldValueAsLong(Asset a_asset, String a_field)
/*     */     throws Bn2Exception
/*     */   {
/* 359 */     long attributeId = getAttributeValueId(a_asset, a_field);
/*     */     long fieldValue;
/*     */    // long fieldValue;
/* 361 */     if (attributeId == 0L)
/*     */     {
/* 363 */       fieldValue = 0L;
/*     */     }
/*     */     else
/*     */     {
/* 367 */       String sFieldValue = (String)getAttributeValueToFieldValueMap().get(new Long(attributeId));
/* 368 */       if (sFieldValue == null)
/*     */       {
/* 370 */         throw new Bn2Exception("Could not map attribute ID " + attributeId + " to a field value");
/*     */       }
/* 372 */       fieldValue = Long.parseLong(sFieldValue);
/*     */     }
/*     */ 
/* 375 */     return fieldValue;
/*     */   }
/*     */ 
/*     */   public String getMappedFieldValueAsString(Asset a_asset, String a_field) throws Bn2Exception
/*     */   {
/* 380 */     long attributeId = getAttributeValueId(a_asset, a_field);
/*     */     String sFieldValue;
/*     */     //String sFieldValue;
/* 382 */     if (attributeId == 0L)
/*     */     {
/* 384 */       sFieldValue = null;
/*     */     }
/*     */     else
/*     */     {
/* 388 */       sFieldValue = (String)getAttributeValueToFieldValueMap().get(new Long(attributeId));
/*     */     }
/*     */ 
/* 391 */     return sFieldValue;
/*     */   }
/*     */ 
/*     */   public String getFieldValueAsString(Asset a_asset, String a_field) throws Bn2Exception
/*     */   {
/* 396 */     String s = null;
/* 397 */     AttributeValue av = getFieldValue(a_asset, a_field);
/* 398 */     if (av != null)
/*     */     {
/* 400 */       s = av.getValue();
/*     */     }
/*     */ 
/* 403 */     if (s == null)
/*     */     {
/* 405 */       s = "";
/*     */     }
/*     */ 
/* 408 */     return s;
/*     */   }
/*     */ 
/*     */   public BrightDate getFieldValueAsDate(Asset a_asset, String a_field) throws Bn2Exception
/*     */   {
/* 413 */     AttributeValue av = getFieldValue(a_asset, a_field);
/* 414 */     if ((av == null) || (av.getAttribute() == null) || (!av.getAttribute().getIsDatepicker()))
/*     */     {
/* 416 */       return new BrightDate();
/*     */     }
/*     */ 
/* 419 */     return av.getDateValue();
/*     */   }
/*     */ 
/*     */   public BrightDateTime getFieldValueAsDateTime(Asset a_asset, String a_field) throws Bn2Exception
/*     */   {
/* 424 */     AttributeValue av = getFieldValue(a_asset, a_field);
/* 425 */     if ((av == null) || (av.getAttribute() == null) || (!av.getAttribute().getIsDateTime()))
/*     */     {
/* 427 */       return new BrightDateTime();
/*     */     }
/*     */ 
/* 430 */     return av.getDateTimeValue();
/*     */   }
/*     */ 
/*     */   public BigDecimal getFieldValueAsBigDecimal(Asset a_asset, String a_field) throws Bn2Exception
/*     */   {
/* 435 */     AttributeValue av = getFieldValue(a_asset, a_field);
/* 436 */     if (av == null)
/*     */     {
/* 438 */       return null;
/*     */     }
/*     */ 
/* 441 */     String sValue = av.getValue();
/* 442 */     if (StringUtils.isEmpty(sValue))
/*     */     {
/* 444 */       return null;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 449 */       return new BigDecimal(sValue);
/*     */     }
/*     */     catch (NumberFormatException e) {
/*     */     
/* 453 */     throw new Bn2Exception("ExternalFilterManager: couldn't parse value \"" + sValue + "\" of asset " + a_asset.getId() + " field \"" + a_field + "\" as a BigDecimal", e);}
/*     */   }
/*     */ 
/*     */   private void populateAttributeMappings(String a_sMappings)
/*     */   {
/* 467 */     String[] asEntries = a_sMappings.split(",");
/* 468 */     if (asEntries != null)
/*     */     {
/* 471 */       for (int i = 0; i < asEntries.length; i++)
/*     */       {
/* 473 */         String sEntry = asEntries[i];
/*     */ 
/* 475 */         if (!StringUtil.stringIsPopulated(sEntry))
/*     */           continue;
/* 477 */         String[] asParts = sEntry.split("=");
/*     */ 
/* 479 */         if ((asParts == null) || (asParts.length != 2) || (!StringUtil.stringIsPopulated(asParts[0])) || (!StringUtil.stringIsPopulated(asParts[1]))) {
/*     */           continue;
/*     */         }
/*     */         try
/*     */         {
/* 484 */           Long olId = new Long(Long.parseLong(asParts[0]));
/* 485 */           String sFieldName = asParts[1];
/*     */ 
/* 487 */           this.m_hmAttributeToFieldName.put(olId, sFieldName);
/* 488 */           this.m_hmFieldNameToAttribute.put(sFieldName, olId);
/*     */         }
/*     */         catch (NumberFormatException e)
/*     */         {
/* 492 */           this.m_logger.error("ExternalFilterManager.populateAttributeMappings: NumberFormatException while parsing mappings system setting");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void populateAttributeValueMappings(String a_sMappings)
/*     */   {
/* 510 */     String[] asEntries = a_sMappings.split(",");
/* 511 */     if (asEntries != null)
/*     */     {
/* 514 */       for (int i = 0; i < asEntries.length; i++)
/*     */       {
/* 516 */         String sEntry = asEntries[i];
/*     */ 
/* 518 */         if (!StringUtil.stringIsPopulated(sEntry))
/*     */           continue;
/* 520 */         String[] asParts = sEntry.split("=");
/*     */ 
/* 522 */         if ((asParts == null) || (asParts.length != 2) || (!StringUtil.stringIsPopulated(asParts[0])) || (!StringUtil.stringIsPopulated(asParts[1]))) {
/*     */           continue;
/*     */         }
/*     */         try
/*     */         {
/* 527 */           Long olId = new Long(Long.parseLong(asParts[0]));
/* 528 */           String sFieldValue = asParts[1];
/*     */ 
/* 530 */           this.m_hmAttributeValueToFieldValue.put(olId, sFieldValue);
/* 531 */           this.m_hmFieldValueToAttributeValue.put(sFieldValue, olId);
/*     */         }
/*     */         catch (NumberFormatException e)
/*     */         {
/* 535 */           this.m_logger.error("ExternalFilterManager.populateAttributeValueMappings: NumberFormatException while parsing attribute values mappings system setting");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void populateExcludedTypeIds(String a_sTypeIds)
/*     */   {
/* 553 */     String[] asEntries = a_sTypeIds.split(",");
/* 554 */     if (asEntries != null)
/*     */     {
/* 557 */       for (int i = 0; i < asEntries.length; i++)
/*     */       {
/* 560 */         String sEntry = asEntries[i].replace(" ", "");
/*     */ 
/* 562 */         if (!StringUtil.stringIsPopulated(sEntry))
/*     */           continue;
/*     */         try
/*     */         {
/* 566 */           Long olId = new Long(Long.parseLong(sEntry));
/*     */ 
/* 568 */           this.m_hmExcludedTypeIds.put(olId, olId);
/*     */         }
/*     */         catch (NumberFormatException e)
/*     */         {
/* 572 */           this.m_logger.error("ExternalFilterManager.populateExcludedTypeIds: NumberFormatException while parsing excluded type ids system setting");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.externalfilter.service.ExternalFilterManager
 * JD-Core Version:    0.6.0
 */