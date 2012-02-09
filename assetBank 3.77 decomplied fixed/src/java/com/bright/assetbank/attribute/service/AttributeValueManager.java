/*      */ package com.bright.assetbank.attribute.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.bean.LightweightAsset;
/*      */ import com.bright.assetbank.application.service.IAssetManager;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.Attribute.Translation;
/*      */ import com.bright.assetbank.attribute.bean.AttributeType;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ //import com.bright.assetbank.attribute.bean.AttributeValue.Translation;
/*      */ import com.bright.assetbank.attribute.constant.AttributeStorageType;
/*      */ import com.bright.assetbank.attribute.util.AttributeDBUtil;
/*      */ import com.bright.assetbank.attribute.util.AttributeStorageTypePredicate;
/*      */ import com.bright.assetbank.attribute.util.AttributeTypePredicate;
/*      */ import com.bright.assetbank.attribute.util.AttributeValueDBUtil;
/*      */ import com.bright.assetbank.attribute.util.AttributeValueSequenceComparator;
/*      */ import com.bright.assetbank.attribute.util.ListAttributeValueDBUtil;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.entity.constant.AssetEntityConstants;
/*      */ import com.bright.assetbank.search.bean.SearchCriteria;
/*      */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*      */ import com.bright.assetbank.taxonomy.TaxonomyUtil;
/*      */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.framework.category.service.CategoryManager;
/*      */ import com.bright.framework.common.bean.BrightDate;
/*      */ import com.bright.framework.common.bean.BrightDateTime;
/*      */ import com.bright.framework.common.bean.BrightMoney;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.exception.SQLStatementException;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.language.util.LanguageDBUtil;
/*      */ import com.bright.framework.search.bean.SearchResults;
/*      */ import com.bright.framework.util.CollectionUtil;
/*      */ import com.bright.framework.util.ListSearchUnaryPredicate;
/*      */ import com.bright.framework.util.ListSearchUnaryPredicateAdapter;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.collections.Factory;
/*      */ import org.apache.commons.collections.MapUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class AttributeValueManager extends Bn2Manager
/*      */ {
/*      */   private DBTransactionManager m_transactionManager;
/*      */   private IAssetManager m_assetManager;
/*      */   private AttributeManager m_attributeManager;
/*      */   private CategoryManager m_categoryManager;
/*      */   private MultiLanguageSearchManager m_searchManager;
/*      */   private TaxonomyManager m_taxonomyManager;
/*   96 */   private final Map<Long, List<AttributeValue>> m_hmAttributeListValues = new HashMap();
/*      */ 
/*   99 */   private Vector m_vecAssetAttributesCache = null;
/*  100 */   private final Object m_oAssetAttributesLock = new Object();
/*      */ 
/*  102 */   private final HashMap m_hmAttributeValuesToAssetsMap = new HashMap();
/*      */ 
/*      */   public Vector<AttributeValue> getAttributesForAsset(DBTransaction a_dbTransaction, Asset a_asset, Vector a_vecValidAttributeIds, Vector a_vVisibleAttributeIds, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  125 */     return getAttributesForAsset(a_dbTransaction, true, a_asset.getId(), a_asset, a_vecValidAttributeIds, a_vVisibleAttributeIds, a_language);
/*      */   }
/*      */ 
/*      */   public Vector<AttributeValue> getAttributesForAsset(DBTransaction a_dbTransaction, long a_lAssetId, Vector a_vecValidAttributeIds, Vector a_vVisibleAttributeIds, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  143 */     return getAttributesForAsset(a_dbTransaction, true, a_lAssetId, null, a_vecValidAttributeIds, a_vVisibleAttributeIds, a_language);
/*      */   }
/*      */ 
/*      */   public Vector<AttributeValue> getDynamicAttributesForAsset(DBTransaction a_dbTransaction, long a_lAssetId, Vector a_vecValidAttributeIds, Vector a_vVisibleAttributeIds, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  161 */     return getAttributesForAsset(a_dbTransaction, false, a_lAssetId, null, a_vecValidAttributeIds, a_vVisibleAttributeIds, a_language);
/*      */   }
/*      */ 
/*      */   private Vector<AttributeValue> getAttributesForAsset(DBTransaction a_dbTransaction, boolean a_bIncludeStatic, long a_lAssetId, Asset a_asset, Vector a_vecValidAttributeIds, Vector a_vVisibleAttributeIds, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  205 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/*  207 */     Set validAttributeIds = CollectionUtil.asSet(a_vecValidAttributeIds);
/*      */ 
/*  209 */     if (transaction == null)
/*      */     {
/*  211 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  216 */       Vector<AttributeValue> vAttributes = new Vector<AttributeValue>();
/*      */ 
/*  218 */       List valuePerAssetAttributeValues = getValuePerAssetAttributeValues(transaction, a_lAssetId, validAttributeIds, a_vVisibleAttributeIds);
/*      */ 
/*  223 */       vAttributes.addAll(valuePerAssetAttributeValues);
/*      */ 
/*  225 */       List listAttributeValues = getListAttributeValues(transaction, a_lAssetId, validAttributeIds, a_vVisibleAttributeIds);
/*      */ 
/*  230 */       vAttributes.addAll(listAttributeValues);
/*      */ 
/*  232 */       List categoryAttributeValues = getCategoryAttributeValues(transaction, a_lAssetId, validAttributeIds, a_vVisibleAttributeIds, a_language);
/*      */ 
/*  238 */       vAttributes.addAll(categoryAttributeValues);
/*      */ 
/*  240 */       if (a_bIncludeStatic)
/*      */       {
/*  242 */         List staticAttributeValues = getStaticAttributeValues(transaction, a_lAssetId, a_asset, validAttributeIds, a_vVisibleAttributeIds);
/*      */ 
/*  248 */         vAttributes.addAll(staticAttributeValues);
/*      */       }
/*      */ 
/*  265 */       List bogusHeadingAttributeValues = getUnpopulatedHeadingAttributeValues(transaction, validAttributeIds, a_vVisibleAttributeIds);
/*      */ 
/*  269 */       vAttributes.addAll(bogusHeadingAttributeValues);
/*      */ 
/*  273 */       Collections.sort(vAttributes, AttributeValueSequenceComparator.getInstance());
/*      */ 
/*  276 */       for (AttributeValue attVal : vAttributes)
/*      */       {
/*  278 */         attVal.setLanguage(a_language);
/*      */       }
/*      */     
                return vAttributes;
/*  281 */      // ??? = vAttributes;
/*      */      // return ???;
/*      */     }
/*      */     finally
/*      */     {
/*  286 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  290 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  294 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*  295 */     } 
            //throw localObject;
                
/*      */   }
/*      */ 
/*      */   public AttributeValue getAttributeValue(DBTransaction a_transaction, long a_lAssetId, long a_lAttributeId, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  309 */     Vector values = getAttributesForAsset(a_transaction, a_lAssetId, CollectionUtil.asVector(new Long[] { Long.valueOf(a_lAttributeId) }), null, a_language);
/*      */ 
/*  311 */     if ((values == null) || (values.isEmpty()))
/*      */     {
/*  313 */       return null;
/*      */     }
/*  315 */     AttributeValue attVal = (AttributeValue)values.get(0);
/*      */ 
/*  319 */     long lGotAttributeId = attVal.getAttribute().getId();
/*  320 */     if (lGotAttributeId == a_lAttributeId)
/*      */     {
/*  322 */       return attVal;
/*      */     }
/*      */ 
/*  327 */     throw new IllegalStateException("getAttributesForAsset() returned the wrong AttributeValues. Asset ID: " + a_lAssetId + ", wanted attribute ID " + a_lAttributeId + ", got attribute ID " + lGotAttributeId + ", vector size " + values.size() + " (should be 1)");
/*      */   }
/*      */ 
/*      */   public String getMergedAttributeValue(DBTransaction a_dbTransaction, long a_lAttributeId, long[] a_alAssetIds)
/*      */     throws Bn2Exception
/*      */   {
/*  339 */     String sValue = null;
/*  340 */     TreeSet tsValues = new TreeSet();
/*      */ 
/*  342 */     long[] arr$ = a_alAssetIds; int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { Long assetId = Long.valueOf(arr$[i$]);
/*      */ 
/*  344 */       AttributeValue val = getAttributeValue(a_dbTransaction, assetId.longValue(), a_lAttributeId, LanguageConstants.k_defaultLanguage);
/*      */ 
/*  346 */       if ((val == null) || (val.getValue() == null))
/*      */         continue;
/*  348 */       tsValues.add(val.getValue());
/*      */     }
/*      */ 
/*  353 */     if (tsValues.size() > 0)
/*      */     {
/*  355 */       StringBuffer sBuf = new StringBuffer();
/*  356 */       Iterator itValues = tsValues.iterator();
/*      */ 
/*  358 */       while (itValues.hasNext())
/*      */       {
/*  360 */         if (sBuf.length() > 0)
/*      */         {
/*  362 */           sBuf.append(", ");
/*      */         }
/*  364 */         sBuf.append(itValues.next());
/*      */       }
/*  366 */       sValue = sBuf.toString();
/*      */     }
/*      */ 
/*  369 */     return sValue;
/*      */   }
/*      */ 
/*      */   public Vector<Attribute> getAssetAttributes(DBTransaction a_dbTransaction, final long a_lAttributeId, Vector a_vVisibleAttributeIds, final boolean a_bSearchFieldAttributes, final boolean a_bSearchBuilderAttributes)
/*      */     throws Bn2Exception
/*      */   {
/*  391 */     return getAssetAttributes(a_dbTransaction, a_vVisibleAttributeIds, new ListSearchUnaryPredicate()
/*      */     {
/*  393 */       private boolean found = false;
/*      */ 
/*      */       //public boolean test(Attribute a_attribute)
                 public boolean test(Object a_attribute1)
/*      */       {
                     Attribute a_attribute = (Attribute) a_attribute1;
/*  397 */         boolean matches = ((a_lAttributeId <= 0L) || (a_lAttributeId == a_attribute.getId())) && ((!a_bSearchFieldAttributes) || (a_attribute.isSearchField())) && ((!a_bSearchBuilderAttributes) || (a_attribute.isSearchBuilderField()));
/*      */ 
/*  402 */         this.found |= matches;
/*      */ 
/*  404 */         return matches;
/*      */       }
/*      */ 
/*      */       public boolean allFound()
/*      */       {
/*  410 */         return (a_lAttributeId > 0L) && (this.found);
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public Vector<Attribute> getAssetAttributes(DBTransaction a_dbTransaction, Vector a_vVisibleAttributeIds, ListSearchUnaryPredicate<Attribute> a_filter)
/*      */     throws Bn2Exception
/*      */   {
/*  432 */     Vector vecResults = new Vector();
/*      */ 
/*  434 */     synchronized (this.m_oAssetAttributesLock)
/*      */     {
/*  437 */       if (this.m_vecAssetAttributesCache == null)
/*      */       {
/*  439 */         this.m_vecAssetAttributesCache = getAssetAttributesFromDB(a_dbTransaction);
/*      */       }
/*      */ 
/*  442 */       Attribute attribute = null;
/*  443 */       Attribute attributeCopy = null;
/*      */ 
/*  445 */       for (int i = 0; i < this.m_vecAssetAttributesCache.size(); i++)
/*      */       {
/*  447 */         attribute = (Attribute)this.m_vecAssetAttributesCache.get(i);
/*      */ 
/*  450 */         if (!a_filter.test(attribute)) {
/*      */           continue;
/*      */         }
/*  453 */         attributeCopy = new Attribute(attribute);
/*      */ 
/*  456 */         if ((a_vVisibleAttributeIds != null) && (!a_vVisibleAttributeIds.contains(Long.valueOf(attributeCopy.getId()))))
/*      */         {
/*  458 */           attributeCopy.setIsVisible(false);
/*      */         }
/*      */         else
/*      */         {
/*  462 */           attributeCopy.setIsVisible(true);
/*      */         }
/*      */ 
/*  466 */         if (((attributeCopy.getIsDatepicker()) || (attributeCopy.getIsDateTime())) && (attributeCopy.getDefaultValue() != null) && (attributeCopy.getDefaultValue().contains("now")))
/*      */         {
/*  470 */           String sValue = "";
/*      */ 
/*  474 */           GregorianCalendar today = new GregorianCalendar();
/*      */           try
/*      */           {
/*  479 */             if (attributeCopy.getDefaultValue().contains("+"))
/*      */             {
/*  482 */               sValue = attributeCopy.getDefaultValue().replace("now+", "");
/*  483 */               today.add(5, new Integer(sValue).intValue());
/*      */             }
/*  485 */             else if (attributeCopy.getDefaultValue().contains("-"))
/*      */             {
/*  488 */               sValue = sValue.replace("now-", "");
/*  489 */               today.add(5, -new Integer(sValue).intValue());
/*      */             }
/*      */ 
/*      */           }
/*      */           catch (NumberFormatException nfe)
/*      */           {
/*      */           }
/*      */ 
/*  498 */           if (attributeCopy.getIsDatepicker())
/*      */           {
/*  500 */             attributeCopy.setDefaultValue(new BrightDate(today.getTime()).getFormDate());
/*      */           }
/*      */           else
/*      */           {
/*  504 */             attributeCopy.setDefaultValue(new BrightDateTime(today.getTime()).getFormDateTime());
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  510 */         vecResults.add(attributeCopy);
/*      */ 
/*  512 */         if (a_filter.allFound())
/*      */         {
/*      */           break;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  522 */     return vecResults;
/*      */   }
/*      */ 
/*      */   private Vector<Attribute> getAssetAttributesFromDB(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  550 */     Connection con = null;
/*  551 */     PreparedStatement psql = null;
/*  552 */     ResultSet rs = null;
/*  553 */     DBTransaction transaction = a_dbTransaction;
/*  554 */     String sSQL = null;
/*  555 */     Vector vAttributes = new Vector();
/*      */ 
/*  557 */     if (transaction == null)
/*      */     {
/*  559 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  564 */       con = transaction.getConnection();
/*      */ 
/*  567 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/*  569 */       sSQL = "SELECT a.Id aId, a.IsStatic, a.IsReadOnly, a.StaticFieldName, a.Label, a.IsMandatory, a.IsMandatoryBulkUpload, a.IsKeywordSearchable, a.AttributeTypeId, a.IsSearchField, a.IsSearchBuilderField, a.DefaultValue, a.NameAttribute, a.IsDescriptionAttribute, a.ValueIfNotVisible, a.SequenceNumber, a.MaxDisplayLength, a.Highlight, a.HelpText, a.TreeId, a.OnChangeScript, a.DefaultKeywordFilter, a.IsHtml, a.IsRequiredForCompleteness, a.RequiresTranslation, a.DisplayName, a.BaseUrl, a.AltText, a.HasSearchTokens, a.TokenDelimiterRegex, a.Height, a.Width, a.MaxSize, a.IsFiltered, a.Seed, a.IncrementAmount, a.Prefix, a.IsExtendableList, a.InputMask, a.ShowOnChild, a.IncludeInSearchForChild, a.GetDataFromChildren, a.HideForSort, a.IsAutoComplete, a.PluginClass, a.PluginParamsAttributeIds, a.ValueColumnName, a.ShowOnDownload, a.MaxDecimalPlaces, a.MinDecimalPlaces, a.Hidden , lav.Id avId, lav.Value, lav.MapToFieldValue, lav.ActionOnAssetId, ta.Label taLabel, ta.DefaultValue taDefaultValue, ta.ValueIfNotVisible taValueIfNotVisible, ta.HelpText taHelpText, ta.InputMask taInputMask, tlav.Value tavValue,l.Id lId, l.Name lName,l.NativeName lNativeName,l.Code lCode FROM Attribute a  LEFT JOIN ListAttributeValue lav ON lav.AttributeId=a.Id LEFT JOIN TranslatedAttribute ta ON ta.AttributeId = a.Id LEFT JOIN TranslatedListAttributeValue tlav ON tlav.ListAttributeValueId = lav.Id AND ta.LanguageId=tlav.LanguageId LEFT JOIN Language l ON (l.Id = tlav.LanguageId OR ta.LanguageId=l.Id AND (l.IsDefault=0 OR " + sqlGenerator.getNullCheckStatement("ta.AttributeId") + ")) " + "WHERE 1=1";
/*      */ 
/*  592 */       sSQL = sSQL + this.m_attributeManager.settingsDependentFilterSQL("a");
/*      */ 
/*  594 */       sSQL = sSQL + " ORDER BY a.SequenceNumber, a.Id, lav.SequenceNumber, lav.Id, l.Id";
/*      */ 
/*  596 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  598 */       rs = psql.executeQuery();
/*      */ 
/*  600 */       Attribute attribute = null;
/*  601 */       AttributeValue attVal = null;
/*  602 */       AttributeValue firstAttVal = null;
/*  603 */       Attribute.Translation attTranslation = null;
/*  604 */       long lLastAttributeId = 0L;
/*  605 */       long lLastAttributeValueId = 0L;
/*      */ 
/*  607 */       while (rs.next())
/*      */       {
/*  609 */         if (lLastAttributeId != rs.getLong("aId"))
/*      */         {
/*  612 */           attribute = AttributeDBUtil.buildAttribute(rs);
/*      */ 
/*  615 */           attribute.setIsVisible(true);
/*      */ 
/*  617 */           vAttributes.add(attribute);
/*      */ 
/*  619 */           lLastAttributeId = attribute.getId();
/*  620 */           firstAttVal = attVal = null;
/*      */         }
/*      */ 
/*  623 */         if ((rs.getLong("avId") > 0L) && (lLastAttributeValueId != rs.getLong("avId")))
/*      */         {
/*  625 */           attVal = new AttributeValue();
/*  626 */           attVal.setId(rs.getLong("avId"));
/*  627 */           attVal.setMapToFieldValue(rs.getString("MapToFieldValue"));
/*  628 */           attVal.setValue(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "Value"));
/*  629 */           attVal.setActionOnAssetId(rs.getLong("ActionOnAssetId"));
/*  630 */           attVal.setAttribute(attribute);
/*      */ 
/*  632 */           attribute.getListOptionValues().add(attVal);
/*      */ 
/*  634 */           lLastAttributeValueId = attVal.getId();
/*      */ 
/*  636 */           if (firstAttVal == null)
/*      */           {
/*  638 */             firstAttVal = attVal;
/*      */           }
/*      */         }
/*      */ 
/*  642 */         if (rs.getLong("lId") <= 0L)
/*      */           continue;
/*  644 */         Language language = new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode"));
/*  645 */         language.setNativeName(rs.getString("lNativeName"));
/*      */ 
/*  648 */         if ((attVal == firstAttVal) || (attVal == null))
/*      */         {
/*      */           //Attribute tmp447_445 = attribute; tmp447_445.getClass(); attTranslation = new Attribute.Translation(tmp447_445, language);
/*  651 */           attTranslation = attribute.new Translation(language);
                     attTranslation.setLabel(rs.getString("taLabel"));
/*  652 */           attTranslation.setDefaultValue(rs.getString("taDefaultValue"));
/*  653 */           attTranslation.setValueIfNotVisible(rs.getString("taValueIfNotVisible"));
/*  654 */           attTranslation.setHelpText(rs.getString("taHelpText"));
/*  655 */           attTranslation.setInputMask(rs.getString("taInputMask"));
/*  656 */           attribute.getTranslations().add(attTranslation);
/*      */         }
/*      */ 
/*  659 */         if (attVal != null)
/*      */         {
/*      */           //AttributeValue tmp551_549 = attVal; tmp551_549.getClass(); AttributeValue.Translation translation = new AttributeValue.Translation(tmp551_549, language);
/*  662 */            AttributeValue.Translation translation = attVal.new Translation( language);
                     translation.setValue(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "tavValue"));
/*  663 */           translation.setAttributeTranslation(attTranslation);
/*  664 */           attVal.getTranslations().add(translation);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  669 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  673 */       this.m_logger.error("SQL Exception whilst getting attributes for asset : " + e);
/*  674 */       throw new Bn2Exception("SQL Exception whilst getting attributes for asset : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  679 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  683 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  687 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  692 */     return vAttributes;
/*      */   }
/*      */ 
/*      */   private List<AttributeValue> getValuePerAssetAttributeValues(DBTransaction a_transaction, long a_lAssetId, Set<Long> a_validAttributeIds, Vector a_vVisibleAttributeIds)
/*      */     throws Bn2Exception
/*      */   {
/*  703 */     List <Attribute> valuePerAssetAttributes = getAssetAttributes(a_transaction, a_vVisibleAttributeIds, new AttributeStorageTypePredicate(this.m_attributeManager, AttributeStorageType.VALUE_PER_ASSET, a_validAttributeIds));
/*      */ 
/*  708 */     if (valuePerAssetAttributes.size() == 0)
/*      */     {
/*  710 */       return Collections.emptyList();
/*      */     }
/*      */ 
/*  715 */     StringBuilder sbAttributeValuesColumns = new StringBuilder();
/*  716 */     StringBuilder sbTranslatedAttributeValuesColumns = new StringBuilder();
/*  717 */     for (Iterator iterator = valuePerAssetAttributes.iterator(); iterator.hasNext(); )
/*      */     {
/*  719 */       Attribute attribute = (Attribute)iterator.next();
/*  720 */       String columnName = attribute.getValueColumnName();
/*      */ 
/*  722 */       sbAttributeValuesColumns.append("aav.");
/*  723 */       sbAttributeValuesColumns.append(columnName);
/*  724 */       sbAttributeValuesColumns.append(',');
/*      */ 
/*  726 */       if (attribute.getIsTranslatable())
/*      */       {
/*  728 */         sbTranslatedAttributeValuesColumns.append("taav.");
/*  729 */         sbTranslatedAttributeValuesColumns.append(columnName);
/*  730 */         sbTranslatedAttributeValuesColumns.append(',');
/*      */       }
/*      */     }
/*      */ 
/*  734 */     if (sbAttributeValuesColumns.length() > 0)
/*      */     {
/*  736 */       sbAttributeValuesColumns.setLength(sbAttributeValuesColumns.length() - 1);
/*      */     }
/*  738 */     if (sbTranslatedAttributeValuesColumns.length() > 0)
/*      */     {
/*  740 */       sbTranslatedAttributeValuesColumns.setLength(sbTranslatedAttributeValuesColumns.length() - 1);
/*      */     }
/*      */ 
/*  743 */     List<AttributeValue> valuePerAssetAttributeValues = new ArrayList();
/*  744 */     String sSQL = null;
/*      */     try
/*      */     {
/*  747 */       Connection con = a_transaction.getConnection();
/*      */ 
/*  749 */       if (sbAttributeValuesColumns.length() > 0)
/*      */       {
/*  751 */         sSQL = "SELECT " + sbAttributeValuesColumns + " " + "FROM AssetAttributeValues aav WHERE aav.AssetId = ?";
/*      */ 
/*  753 */         PreparedStatement psql = con.prepareStatement(sSQL);
/*  754 */         psql.setLong(1, a_lAssetId);
/*      */ 
/*  756 */         ResultSet rs = psql.executeQuery();
/*  757 */         if (!rs.next())
/*      */         {
/*  760 */           rs = null;
/*      */         }
/*  762 */         valuePerAssetAttributeValues = AttributeValueDBUtil.buildPerAssetAttributeValues(rs, valuePerAssetAttributes);
/*  763 */         psql.close();
/*      */       }
/*      */ 
/*  767 */       if (sbTranslatedAttributeValuesColumns.length() > 0)
/*      */       {
/*  770 */         Map attValsByAttributeId = new HashMap(valuePerAssetAttributeValues.size());
/*  771 */         for (AttributeValue attributeValue : valuePerAssetAttributeValues)
/*      */         {
/*  773 */           attValsByAttributeId.put(Long.valueOf(attributeValue.getAttribute().getId()), attributeValue);
/*      */         }
/*      */ 
/*  777 */         sSQL = "SELECT " + sbTranslatedAttributeValuesColumns + "," + "lang.Id AS langId,lang.Name AS langName,lang.NativeName AS langNativeName,lang.Code AS langCode,lang.IsSuspended AS langIsSuspended,lang.IsDefault AS langIsDefault,lang.IsRightToLeft AS langIsRightToLeft,lang.IconFilename AS langIconFilename,lang.UsesLatinAlphabet AS langUsesLatinAlphabet " + " " + "FROM Language lang " + "LEFT JOIN TranslatedAssetAttributeValues taav ON taav.LanguageId = lang.Id AND taav.AssetId = ? " + "WHERE lang.id <> " + 1L;
/*      */ 
/*  783 */         PreparedStatement psql = con.prepareStatement(sSQL);
/*  784 */         psql.setLong(1, a_lAssetId);
/*      */ 
/*  786 */         ResultSet rs = psql.executeQuery();
/*      */         Language lang;
/*  787 */         while (rs.next())
/*      */         {
/*  789 */           lang = LanguageDBUtil.createLanguageFromRS(rs);
/*      */ 
/*  791 */           for (Attribute attribute : valuePerAssetAttributes)
/*      */           {
/*  793 */             if (attribute.getIsTranslatable())
/*      */             {
/*  795 */               AttributeValue av = (AttributeValue)attValsByAttributeId.get(Long.valueOf(attribute.getId()));
/*  796 */               if (av == null)
/*      */               {
/*  799 */                 av = new AttributeValue();
/*  800 */                 av.setAttribute(attribute);
/*      */ 
/*  805 */                 attValsByAttributeId.put(Long.valueOf(attribute.getId()), av);
/*      */               }
/*      */               //AttributeValue tmp595_593 = av; tmp595_593.getClass(); AttributeValue.Translation translation = new AttributeValue.Translation(tmp595_593, lang);
                          AttributeValue.Translation translation = av.new Translation(lang);
/*  810 */               translation.setValue(AttributeValueDBUtil.getTextValueFromValueColumn(rs, attribute));
/*      */ 
/*  813 */               av.getTranslations().add(translation);
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*  818 */         psql.close();
/*      */       }
/*      */ 
/*  821 */       return valuePerAssetAttributeValues;
/*      */     }
/*      */     catch (SQLException e) {
/*      */     
/*  825 */     throw new Bn2Exception("SQL Exception whilst getting attribute values. SQL: " + sSQL, e);
/*      */  } }
/*      */ 
/*      */   private List<AttributeValue> getListAttributeValues(DBTransaction a_transaction, long a_lAssetId, Set<Long> a_flexibleAttributeIds, Vector a_vVisibleAttributeIds)
/*      */     throws Bn2Exception
/*      */   {
/*  841 */     Vector<Attribute> listAttributes = getAssetAttributes(a_transaction, a_vVisibleAttributeIds, new AttributeStorageTypePredicate(this.m_attributeManager, AttributeStorageType.LIST, a_flexibleAttributeIds));
/*      */ 
/*  846 */     if (listAttributes.size() == 0)
/*      */     {
/*  848 */       return Collections.emptyList();
/*      */     }
/*      */ 
/*  851 */     Map remainingListAttributesById = new HashMap(listAttributes.size());
/*  852 */     for (Attribute att : listAttributes)
/*      */     {
/*  863 */       att.clearListOptionValues();
/*  864 */       remainingListAttributesById.put(Long.valueOf(att.getId()), att);
/*      */     }
/*      */ 
/*  869 */     String sSQL = "SELECT lav.Id lavId, lav.IsEditable, lav.Value, lav.AdditionalValue, lav.ActionOnAssetId, lav.MapToFieldValue ,lav.AttributeId, tlav.Value tlavValue, tlav.AdditionalValue tlavAdditionalValue, lang.Id AS langId,lang.Name AS langName,lang.NativeName AS langNativeName,lang.Code AS langCode,lang.IsSuspended AS langIsSuspended,lang.IsDefault AS langIsDefault,lang.IsRightToLeft AS langIsRightToLeft,lang.IconFilename AS langIconFilename,lang.UsesLatinAlphabet AS langUsesLatinAlphabet  FROM AssetListAttributeValue alav JOIN ListAttributeValue lav ON lav.Id = alav.ListAttributeValueId LEFT JOIN TranslatedListAttributeValue tlav ON tlav.ListAttributeValueId = lav.Id LEFT JOIN Language lang ON lang.Id = tlav.LanguageId WHERE alav.AssetId = ? AND lav.AttributeId IN (" + DBUtil.getPlaceholders(listAttributes.size()) + ") " + "ORDER BY lav.AttributeId, lav.SequenceNumber, lav.Id";
/*      */     List listAttributeValues;
/*      */     try
/*      */     {
/*  886 */       Connection con = a_transaction.getConnection();
/*      */ 
/*  888 */       PreparedStatement psql = con.prepareStatement(sSQL, 1004, 1007);
/*      */ 
/*  893 */       int iParam = 1;
/*  894 */       psql.setLong(iParam++, a_lAssetId);
/*  895 */       for (Attribute att : listAttributes)
/*      */       {
/*  897 */         psql.setLong(iParam++, att.getId());
/*      */       }
/*      */ 
/*  900 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  902 */       listAttributeValues = new ArrayList();
/*  903 */       long lPrevAttributeId = -1L;
/*  904 */       Attribute currentAttribute = null;
/*  905 */       while (rs.next())
/*      */       {
/*  907 */         long lAttributeId = rs.getLong("AttributeId");
/*  908 */         if (lAttributeId != lPrevAttributeId)
/*      */         {
/*  910 */           currentAttribute = (Attribute)remainingListAttributesById.remove(Long.valueOf(lAttributeId));
/*      */         }
/*      */ 
/*  913 */         AttributeValue attVal = new AttributeValue();
/*  914 */         attVal.setAttribute(currentAttribute);
/*  915 */         ListAttributeValueDBUtil.populateListAttributeValueFromRS(attVal, rs);
/*      */ 
/*  918 */         if (currentAttribute.getHasListOptionValues())
/*      */         {
/*  920 */           currentAttribute.addListOptionValue(attVal);
/*      */         }
/*      */ 
/*  923 */         buildListAttributeValueTranslations(rs, attVal);
/*      */ 
/*  925 */         listAttributeValues.add(attVal);
/*  926 */         lPrevAttributeId = lAttributeId;
/*      */       }
/*      */ 
/*  929 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  933 */       throw new Bn2Exception("SQL Exception whilst getting attribute values. SQL: " + sSQL, e);
/*      */     }
/*      */    Collection <Attribute> remainingListAttributesByIdlst = remainingListAttributesById.values();
/*  938 */     for (Attribute attribute : remainingListAttributesByIdlst)
/*      */     {
/*  940 */       AttributeValue dummyAttVal = new AttributeValue();
/*  941 */       dummyAttVal.setAttribute(attribute);
/*  942 */       listAttributeValues.add(dummyAttVal);
/*      */     }
/*      */ 
/*  945 */     return listAttributeValues;
/*      */   }
/*      */ 
/*      */   private static void buildListAttributeValueTranslations(ResultSet a_rs, AttributeValue a_value)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/*  958 */     long lAttributeValueId = a_rs.getLong("lavId");
/*      */     while (true)
/*      */     {
/*  962 */       if ((a_rs.isAfterLast()) || (a_rs.getLong("AttributeId") != a_value.getAttribute().getId()) || (a_rs.getLong("lavId") != lAttributeValueId))
/*      */       {
/*  968 */         a_rs.previous();
/*  969 */         break;
/*      */       }
/*      */ 
/*  972 */       if (a_rs.getLong("langId") <= 0L)
/*      */       {
/*      */         break;
/*      */       }
/*      */ 
/*  980 */       if (a_rs.getLong("langId") > 0L)
/*      */       {
/*  982 */         Language language = LanguageDBUtil.createLanguageFromRS(a_rs);
/*      */        // AttributeValue tmp100_99 = a_value; tmp100_99.getClass(); AttributeValue.Translation valueTranslation = new AttributeValue.Translation(tmp100_99, language);
/*      */         AttributeValue.Translation valueTranslation = a_value.new Translation(language);
/*  985 */         a_value.getTranslations().add(valueTranslation);
/*  986 */         valueTranslation.setValue(SQLGenerator.getInstance().getStringFromLargeTextField(a_rs, "tlavValue"));
/*  987 */         valueTranslation.setAdditionalValue(SQLGenerator.getInstance().getStringFromLargeTextField(a_rs, "tlavAdditionalValue"));
/*      */       }
/*      */ 
/*  990 */       a_rs.next();
/*      */     }
/*      */   }
/*      */ 
/*      */   private List<AttributeValue> getCategoryAttributeValues(DBTransaction a_transaction, long a_lAssetId, Set<Long> a_validAttributeIds, Vector a_vVisibleAttributeIds, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/* 1007 */     List<Attribute> categoryAttributes = getAssetAttributes(a_transaction, a_vVisibleAttributeIds, new AttributeStorageTypePredicate(this.m_attributeManager, AttributeStorageType.CATEGORIES, a_validAttributeIds));
/*      */ 
/* 1011 */     List categoryAttributeValues = new ArrayList(categoryAttributes.size());
/* 1012 */     for (Attribute attribute : categoryAttributes)
/*      */     {
/* 1014 */       AttributeValue av = new AttributeValue();
/* 1015 */       av.setAttribute(attribute);
/*      */ 
/* 1017 */       Vector vecCategories = this.m_categoryManager.getCategoriesForItem(a_transaction, attribute.getTreeId(), a_lAssetId, a_language);
/* 1018 */       Vector vecKeywords = TaxonomyUtil.convertToKeywords(vecCategories);
/* 1019 */       av.setKeywordCategories(vecKeywords);
/*      */ 
/* 1021 */       categoryAttributeValues.add(av);
/*      */     }
/*      */ 
/* 1024 */     return categoryAttributeValues;
/*      */   }
/*      */ 
/*      */   private List<AttributeValue> getStaticAttributeValues(DBTransaction a_transaction, long a_lAssetId, Asset a_asset, final Set<Long> a_validAttributeIds, Vector a_vVisibleAttributeIds)
/*      */     throws Bn2Exception
/*      */   {
/* 1033 */     List staticAttributes = getAssetAttributes(a_transaction, a_vVisibleAttributeIds, new ListSearchUnaryPredicateAdapter()
/*      */     {
/*      */      // public boolean test(Attribute a_attribute)
                 public boolean test(Object a_attribute)
/*      */       {
/* 1040 */         if (!((Attribute)a_attribute).getStatic())
/*      */         {
/* 1042 */           return false;
/*      */         }
/*      */ 
/* 1046 */         if ((a_validAttributeIds != null) && (!a_validAttributeIds.contains(Long.valueOf(((Attribute)a_attribute).getId()))))
/*      */         {
/* 1049 */           return false;
/*      */         }
/*      */ 
/* 1059 */         return (!AssetEntityConstants.k_optionalStaticAttributes.contains(((Attribute)a_attribute).getFieldName())) || (a_validAttributeIds == null) || (a_validAttributeIds.contains(Long.valueOf(((Attribute)a_attribute).getId())));
/*      */       }
/*      */     });
/* 1067 */     List<AttributeValue> attVals = createUnpopulatedAttributeValues(staticAttributes);
/*      */ 
/* 1069 */     if (!attVals.isEmpty())
/*      */     {
/* 1071 */       if (a_asset == null)
/*      */       {
/* 1073 */         a_asset = this.m_assetManager.getAssetStaticOnly(a_transaction, a_lAssetId);
/*      */       }
/*      */ 
/* 1077 */       for (AttributeValue attVal : attVals)
/*      */       {
/* 1079 */         populateStaticAttributeValueFromAsset(attVal, a_asset);
/*      */       }
/*      */     }
/*      */ 
/* 1083 */     return attVals;
/*      */   }
/*      */ 
/*      */   private void populateStaticAttributeValueFromAsset(AttributeValue a_attVal, Asset a_asset)
/*      */   {
/* 1091 */     String ksMethod = "populateStaticAttributeValueFromAsset";
/*      */ 
/* 1093 */     String sAttributeField = a_attVal.getAttribute().getFieldName();
/*      */ 
/* 1095 */     if (sAttributeField == null)
/*      */     {
/* 1097 */       this.m_logger.error("populateStaticAttributeValueFromAsset: attribute.getFieldName() returned null for attribute " + a_attVal.getAttribute().getId() + " when populating value for asset " + a_asset.getId());
/* 1098 */       return;
/*      */     }
/*      */ 
/* 1102 */     if (sAttributeField.equals("assetId"))
/*      */     {
/* 1104 */       a_attVal.setValue(String.valueOf(a_asset.getId()));
/*      */     }
/* 1106 */     else if (sAttributeField.equals("originalFilename"))
/*      */     {
/* 1108 */       a_attVal.setValue(a_asset.getOriginalFilename());
/*      */     }
/* 1110 */     else if (sAttributeField.equals("dateAdded"))
/*      */     {
/* 1112 */       a_attVal.setValue(BrightDateTime.formFormat(a_asset.getDateAdded()));
/*      */     }
/* 1114 */     else if (sAttributeField.equals("dateLastModified"))
/*      */     {
/* 1116 */       a_attVal.setValue(BrightDateTime.formFormat(a_asset.getDateLastModified()));
/*      */     }
/* 1118 */     else if (sAttributeField.equals("addedBy"))
/*      */     {
/* 1120 */       if (a_asset.getAddedByUser() != null)
/*      */       {
/* 1122 */         a_attVal.setValue(String.valueOf(a_asset.getAddedByUser().getId()));
/*      */       }
/*      */     }
/* 1125 */     else if (sAttributeField.equals("lastModifiedBy"))
/*      */     {
/* 1127 */       if (a_asset.getLastModifiedByUser() != null)
/*      */       {
/* 1129 */         a_attVal.setValue(String.valueOf(a_asset.getLastModifiedByUser().getId()));
/*      */       }
/*      */     }
/* 1132 */     else if (sAttributeField.equals("size"))
/*      */     {
/* 1134 */       a_attVal.setValue(String.valueOf(a_asset.getFileSizeInBytes()));
/*      */     }
/* 1136 */     else if (sAttributeField.equals("orientation"))
/*      */     {
/* 1138 */       a_attVal.setValue(String.valueOf(a_asset.getOrientation()));
/*      */     }
/* 1140 */     else if (sAttributeField.equals("price"))
/*      */     {
/* 1142 */       if (a_asset.getPrice() != null)
/*      */       {
/* 1144 */         a_attVal.setValue(a_asset.getPrice().getFormAmount());
/*      */       }
/*      */     }
/* 1147 */     else if (sAttributeField.equals("version"))
/*      */     {
/* 1149 */       a_attVal.setValue(String.valueOf(a_asset.getVersionNumber()));
/*      */     }
/* 1151 */     else if (sAttributeField.equals("sensitive"))
/*      */     {
/* 1153 */       a_attVal.setValue(String.valueOf(a_asset.getIsSensitive()));
/*      */     }
/* 1155 */     else if (sAttributeField.equals("agreements"))
/*      */     {
/* 1157 */       a_attVal.setValue(String.valueOf(a_asset.getAgreementTypeId()));
/*      */     }
/* 1159 */     else if (sAttributeField.equals("rating"))
/*      */     {
/* 1161 */       a_attVal.setValue(String.valueOf(a_asset.getAverageRating()));
/*      */     }
/* 1163 */     else if (sAttributeField.equals("dateLastDownloaded"))
/*      */     {
/* 1165 */       a_attVal.setValue(BrightDateTime.formFormat(a_asset.getDateLastDownloaded()));
/*      */     }
/* 1167 */     else if ((!sAttributeField.equals("file")) && (!sAttributeField.equals("embeddedData")) && (!sAttributeField.equals("usage")) && (!sAttributeField.equals("categories")) && (!sAttributeField.equals("accessLevels")) && (!sAttributeField.equals("audit")))
/*      */     {
/* 1178 */       this.m_logger.error("populateStaticAttributeValueFromAsset: unknown attribute " + a_attVal.getAttribute().getId() + " " + sAttributeField + " for asset " + a_asset.getId());
/*      */     }
/*      */   }
/*      */ 
/*      */   private List<AttributeValue> getUnpopulatedHeadingAttributeValues(DBTransaction a_transaction, Set<Long> a_validAttributeIds, Vector a_vVisibleAttributeIds)
/*      */     throws Bn2Exception
/*      */   {
/* 1186 */     List groupHeadingAttributes = getAssetAttributes(a_transaction, a_vVisibleAttributeIds, new AttributeTypePredicate(10L, a_validAttributeIds));
/*      */ 
/* 1193 */     return createUnpopulatedAttributeValues(groupHeadingAttributes);
/*      */   }
/*      */ 
/*      */   private List<AttributeValue> createUnpopulatedAttributeValues(List<Attribute> a_attributes)
/*      */   {
/* 1201 */     List attVals = new ArrayList(a_attributes.size());
/* 1202 */     for (Attribute attribute : a_attributes)
/*      */     {
/* 1204 */       AttributeValue unpopulatedAttVal = new AttributeValue();
/* 1205 */       unpopulatedAttVal.setAttribute(attribute);
/* 1206 */       attVals.add(unpopulatedAttVal);
/*      */     }
/* 1208 */     return attVals;
/*      */   }
/*      */ 
/*      */   public AttributeValue getListAttributeValue(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 1221 */     String sSQL = null;
/* 1222 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1224 */     if (transaction == null)
/*      */     {
/* 1226 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
        AttributeValue value;
/*      */ 
/*      */    // AttributeValue value;
/*      */     try
/*      */     {
/* 1233 */       Connection con = transaction.getConnection();
/*      */ 
/* 1236 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/* 1238 */       sSQL = "SELECT lav.Id lavId, lav.IsEditable, lav.Value, lav.AdditionalValue, lav.ActionOnAssetId, lav.MapToFieldValue , a.Id aId, a.MaxDisplayLength, a.Highlight, a.HelpText, a.NameAttribute, a.IsDescriptionAttribute, a.Label,a.DefaultKeywordFilter, a.InputMask, a.PluginClass, a.PluginParamsAttributeIds, a.ValueColumnName, tlav.Value tavValue, tlav.AdditionalValue tavAdditionalValue, l.Id lId, l.Name lName, l.Code lCode FROM ListAttributeValue lav INNER JOIN Attribute a ON a.Id = lav.AttributeId LEFT JOIN TranslatedListAttributeValue tlav ON tlav.ListAttributeValueId = lav.Id RIGHT JOIN Language l ON (l.Id = tlav.LanguageId OR " + sqlGenerator.getNullCheckStatement("tlav.ListAttributeValueId") + ") " + "WHERE lav.Id=?";
/*      */ 
/* 1266 */       PreparedStatement psql = con.prepareStatement(sSQL, 1004, 1007);
/*      */ 
/* 1268 */       psql.setLong(1, a_lId);
/*      */ 
/* 1270 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1272 */       if (rs.next())
/*      */       {
/* 1274 */          value = new AttributeValue();
/*      */ 
/* 1276 */         ListAttributeValueDBUtil.populateListAttributeValueFromRS(value, rs);
/*      */ 
/* 1278 */         value.getAttribute().setId(rs.getLong("aId"));
/* 1279 */         value.getAttribute().setLabel(rs.getString("Label"));
/* 1280 */         value.getAttribute().setMaxDisplayLength(rs.getInt("MaxDisplayLength"));
/* 1281 */         value.getAttribute().setHighlight(rs.getBoolean("Highlight"));
/* 1282 */         value.getAttribute().setHelpText(rs.getString("HelpText"));
/* 1283 */         value.getAttribute().setIsNameAttribute(rs.getBoolean("NameAttribute"));
/* 1284 */         value.getAttribute().setIsDescriptionAttribute(rs.getBoolean("IsDescriptionAttribute"));
/* 1285 */         value.getAttribute().setDefaultKeywordFilter(rs.getString("DefaultKeywordFilter"));
/* 1286 */         value.getAttribute().setInputMask(rs.getString("InputMask"));
/* 1287 */         value.getAttribute().setPluginClass(rs.getString("PluginClass"));
/* 1288 */         value.getAttribute().setAttributeIdsForPluginParams(rs.getString("PluginParamsAttributeIds"));
/* 1289 */         value.getAttribute().setValueColumnName(rs.getString("ValueColumnName"));
/*      */ 
/* 1291 */         while (!rs.isAfterLast())
/*      */         {
/* 1293 */           if ((rs.getLong("lId") > 0L) && (rs.getLong("lId") != 1L))
/*      */           {
/*      */             //AttributeValue tmp397_395 = value; tmp397_395.getClass(); AttributeValue.Translation translation = new AttributeValue.Translation(tmp397_395, new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
/* 1296 */              AttributeValue.Translation translation = value.new Translation(new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
                       translation.setValue(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "tavValue"));
/* 1297 */             translation.setAdditionalValue(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "tavAdditionalValue"));
/* 1298 */             value.getTranslations().add(translation);
/*      */           }
/* 1300 */           rs.next();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1305 */       value = null;
/*      */ 
/* 1308 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1312 */       throw new SQLStatementException(sSQL, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1317 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1321 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1325 */           this.m_logger.error("SQL Exception whilst trying to close connection ", sqle);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1330 */     return value;
/*      */   }
/*      */ 
/*      */   public void moveListAttributeValue(DBTransaction a_dbTransaction, long a_lId, boolean a_bUp)
/*      */     throws Bn2Exception
/*      */   {
/* 1341 */     Connection con = null;
/* 1342 */     PreparedStatement psql = null;
/* 1343 */     String sSQL = null;
/* 1344 */     ResultSet rs = null;
/* 1345 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1347 */     if (transaction == null)
/*      */     {
/* 1349 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1354 */       con = transaction.getConnection();
/*      */ 
/* 1357 */       sSQL = "SELECT AttributeId,SequenceNumber FROM ListAttributeValue WHERE Id=?";
/* 1358 */       psql = con.prepareStatement(sSQL);
/* 1359 */       psql.setLong(1, a_lId);
/* 1360 */       rs = psql.executeQuery();
/*      */ 
/* 1362 */       if (rs.next())
/*      */       {
/* 1364 */         long lAttribtueId = rs.getLong("AttributeId");
/* 1365 */         int iOldSequenceNumber = rs.getInt("SequenceNumber");
/*      */ 
/* 1368 */         if (!a_bUp)
/*      */         {
/* 1370 */           sSQL = "SELECT Id,SequenceNumber FROM ListAttributeValue WHERE SequenceNumber>? AND AttributeId=? ORDER BY SequenceNumber ASC";
/*      */         }
/*      */         else
/*      */         {
/* 1377 */           sSQL = "SELECT Id,SequenceNumber FROM ListAttributeValue WHERE SequenceNumber<? AND AttributeId=? ORDER BY SequenceNumber DESC";
/*      */         }
/*      */ 
/* 1383 */         PreparedStatement psql2 = con.prepareStatement(sSQL);
/* 1384 */         psql2.setInt(1, iOldSequenceNumber);
/* 1385 */         psql2.setLong(2, lAttribtueId);
/* 1386 */         rs = psql2.executeQuery();
/*      */ 
/* 1389 */         if (rs.next())
/*      */         {
/* 1391 */           sSQL = "UPDATE ListAttributeValue SET SequenceNumber=? WHERE Id=?";
/* 1392 */           PreparedStatement psql3 = con.prepareStatement(sSQL);
/* 1393 */           psql3.setInt(1, iOldSequenceNumber);
/* 1394 */           psql3.setLong(2, rs.getLong("Id"));
/* 1395 */           psql3.executeUpdate();
/* 1396 */           psql3.close();
/*      */ 
/* 1398 */           sSQL = "UPDATE ListAttributeValue SET SequenceNumber=? WHERE Id=?";
/* 1399 */           psql3 = con.prepareStatement(sSQL);
/* 1400 */           psql3.setInt(1, rs.getInt("SequenceNumber"));
/* 1401 */           psql3.setLong(2, a_lId);
/* 1402 */           psql3.executeUpdate();
/* 1403 */           psql3.close();
/*      */         }
/*      */ 
/* 1406 */         psql2.close();
/*      */       }
/*      */ 
/* 1409 */       psql.close();
/*      */ 
/* 1412 */       invalidateAttributeCaches(-1L);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       throw new SQLStatementException(sSQL, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1433 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1437 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1441 */           this.m_logger.error("SQL Exception whilst trying to close connection", sqle);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getAssetsWithAttributeValues(DBTransaction a_dbTransaction, long a_lAttributeId, String a_sAttributeValues, Vector a_vecEntityIds)
/*      */     throws Bn2Exception
/*      */   {
/* 1457 */     String sReturn = null;
/*      */     Iterator i$;
/* 1460 */     if (StringUtil.stringIsPopulated(a_sAttributeValues))
/*      */     {
/* 1462 */       Vector vec = null;
/*      */ 
/* 1466 */       String sKey = Long.toString(a_lAttributeId) + "|" + a_sAttributeValues;
/* 1467 */       if (this.m_hmAttributeValuesToAssetsMap.containsKey(sKey))
/*      */       {
/* 1470 */         this.m_logger.debug("getAssetsWithAttributeValues: got from cache key=" + sKey);
/* 1471 */         synchronized (this.m_hmAttributeValuesToAssetsMap)
/*      */         {
/* 1473 */           vec = (Vector)this.m_hmAttributeValuesToAssetsMap.get(sKey);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1478 */         vec = new Vector();
/*      */ 
/* 1481 */         Vector attributeSearches = new Vector();
/*      */ 
/* 1484 */         Attribute att = new Attribute();
/* 1485 */         att.setId(a_lAttributeId);
/*      */ 
/* 1488 */         String[] asParamVal = StringUtil.convertToArray(a_sAttributeValues, ",");
/*      */ 
/* 1491 */         for (int j = 0; (asParamVal != null) && (j < asParamVal.length); j++)
/*      */         {
/* 1493 */           AttributeValue attVal = new AttributeValue();
/* 1494 */           attVal.setAttribute(att);
/* 1495 */           attVal.setValue(asParamVal[j]);
/*      */ 
/* 1497 */           attributeSearches.add(attVal);
/*      */         }
/*      */ 
/* 1501 */         SearchCriteria searchCriteria = new SearchCriteria();
/* 1502 */         searchCriteria.setAttributeSearches(attributeSearches);
/* 1503 */         searchCriteria.setAssetEntityIdsToInclude(a_vecEntityIds);
/* 1504 */         searchCriteria.setMaxNoOfResults(10000);
/*      */ 
/* 1506 */         SearchResults searchResults = this.m_searchManager.search(searchCriteria);
/*      */ 
/* 1509 */         int iResIndex = 0;
/* 1510 */         while (iResIndex < searchResults.getSearchResults().size())
/*      */         {
/* 1514 */           LightweightAsset tempAsset = (LightweightAsset)searchResults.getSearchResults().get(iResIndex);
/*      */ 
/* 1516 */           long lAssetId = tempAsset.getId();
/* 1517 */           vec.add(Long.valueOf(lAssetId));
/*      */ 
/* 1511 */           iResIndex++;
/*      */         }
/*      */ 
/* 1522 */         synchronized (this.m_hmAttributeValuesToAssetsMap)
/*      */         {
/* 1524 */           if (this.m_hmAttributeValuesToAssetsMap.size() < 10000)
/*      */           {
/* 1526 */             this.m_hmAttributeValuesToAssetsMap.put(sKey, vec);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1533 */       if ((vec != null) && (vec.size() > 0))
/*      */       {
/* 1535 */         for (i$ = vec.iterator(); i$.hasNext(); ) { long lAssetId = ((Long)i$.next()).longValue();
/*      */ 
/* 1537 */           if (sReturn == null)
/*      */           {
/* 1539 */             sReturn = Long.toString(lAssetId);
/*      */           }
/*      */           else
/*      */           {
/* 1543 */             sReturn = sReturn + "," + lAssetId;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1549 */     return sReturn;
/*      */   }
/*      */ 
/*      */   public void saveAttributeValues(DBTransaction a_transaction, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/* 1561 */     Vector<AttributeValue> attributeValues = a_asset.getAttributeValues();
/*      */ 
/* 1564 */     Map attributesById = new HashMap(attributeValues.size());
/* 1565 */     Collection valuePerAssetAttributeValues = new ArrayList();
/* 1566 */     Collection listAttributeValues = new ArrayList();
/* 1567 */     Collection categoryAttributeValues = new ArrayList();
                
/* 1568 */     for (AttributeValue attVal : attributeValues)
/*      */     {
/* 1570 */       Attribute attribute = attVal.getAttribute();
/* 1571 */       attributesById.put(Long.valueOf(attribute.getId()), attribute);
/* 1572 */       if (!attribute.getStatic())
/*      */       {
/* 1574 */         AttributeType attributeType = this.m_attributeManager.getAttributeTypeById(attribute.getTypeId());
/* 1575 */         //switch (4.$SwitchMap$com$bright$assetbank$attribute$constant$AttributeStorageType[attributeType.getAttributeStorageType().ordinal()])
/*      */         switch(attributeType.getAttributeStorageType().ordinal())
                    {
/*      */         case 1:
/* 1578 */           valuePerAssetAttributeValues.add(attVal);
/* 1579 */           break;
/*      */         case 2:
/* 1582 */           listAttributeValues.add(attVal);
/* 1583 */           break;
/*      */         case 3:
/* 1586 */           categoryAttributeValues.add(attVal);
/*      */         case 4:
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1597 */     saveValuePerAssetAttributeValues(a_transaction, a_asset, attributesById, valuePerAssetAttributeValues);
/*      */ 
/* 1600 */     saveListAttributeValues(a_transaction, a_asset, listAttributeValues);
/*      */ 
/* 1603 */     saveCategoryAttributeValues(a_transaction, a_asset, categoryAttributeValues);
/*      */   }
/*      */ 
/*      */   private Connection saveValuePerAssetAttributeValues(DBTransaction a_transaction, Asset a_asset, Map<Long, Attribute> a_attributesById, Collection<AttributeValue> a_valuePerAssetAttributeValues)
/*      */     throws Bn2Exception
/*      */   {
/* 1609 */     Connection con = a_transaction.getConnection();
/*      */ 
/* 1611 */     Map jdbcValuesByAttributeId = new HashMap(a_valuePerAssetAttributeValues.size());
/* 1612 */     Map jdbcValuesByAttributeIdByLanguage = MapUtils.lazyMap(new HashMap(), new Factory()
/*      */     {
/*      */       public Object create()
/*      */       {
/* 1616 */         return new HashMap();
/*      */       }
/*      */     });
/* 1620 */     for (AttributeValue attributeValue : a_valuePerAssetAttributeValues)
/*      */     {
            /* 1622 */
            Long lAttributeId = Long.valueOf(attributeValue.getAttribute().getId());
/*      */ 
/* 1624 */       jdbcValuesByAttributeId.put(lAttributeId, AttributeValueDBUtil.jdbcValueFromAttributeValue(attributeValue));
/*      */ 
/* 1627 */       for (AttributeValue.Translation translation :(Vector<AttributeValue.Translation> )attributeValue.getTranslations())
/*      */       {
/* 1629 */         Long lLanguageId = Long.valueOf(translation.getLanguage().getId());
/* 1630 */         Map translatedJDBCValuesByAttributeId = (Map)jdbcValuesByAttributeIdByLanguage.get(lLanguageId);
/* 1631 */         translatedJDBCValuesByAttributeId.put(lAttributeId, AttributeValueDBUtil.jdbcValueFromAttributeValueTranslation(translation));
/*      */       }
/*      */     }
/*      */     Long lAttributeId;
/* 1638 */     AttributeValueDBUtil.updateAssetAttributeValues(con, a_asset.getId(), a_attributesById, jdbcValuesByAttributeId);
/*      */      Set <Map.Entry> st =jdbcValuesByAttributeIdByLanguage.entrySet();
/* 1640 */     for (Map.Entry entry : st)
/*      */     {
/* 1642 */       long lLanguageId = ((Long)entry.getKey()).longValue();
/* 1643 */       Map translatedJDBCValuesByAttributeId = (Map)entry.getValue();
/* 1644 */       AttributeValueDBUtil.updateAssetAttributeValues(con, a_asset.getId(), lLanguageId, a_attributesById, translatedJDBCValuesByAttributeId);
/*      */     }
/* 1646 */     return con;
/*      */   }
/*      */ 
/*      */   private void saveListAttributeValues(DBTransaction a_transaction, Asset a_asset, Collection<AttributeValue> a_listAttributeValues)
/*      */     throws Bn2Exception
/*      */   {
/* 1652 */     Set<Long> listAttributeValueIds = new HashSet();
/*      */ 
/* 1654 */     for (AttributeValue attVal : a_listAttributeValues)
/*      */     {
/* 1657 */       if (attVal.getForceAdd())
/*      */       {
/* 1660 */         AttributeValue newAttVal = getListAttributeValueId(a_transaction, attVal.getAttribute().getId(), attVal.getValue(), false);
/*      */ 
/* 1662 */         if (newAttVal == null)
/*      */         {
/* 1665 */           addListAttributeValue(a_transaction, attVal);
/*      */         }
/*      */         else
/*      */         {
/* 1670 */           newAttVal.getAttribute().setId(attVal.getAttribute().getId());
/*      */ 
/* 1672 */           attVal = newAttVal;
/*      */         }
/*      */       }
/*      */ 
/* 1676 */       long attributeValueId = attVal.getId();
/*      */ 
/* 1678 */       if (attributeValueId != 0L)
/*      */       {
/* 1680 */         listAttributeValueIds.add(Long.valueOf(attributeValueId));
/*      */       }
/*      */     }
/*      */ 
/* 1684 */     Connection con = a_transaction.getConnection();
/* 1685 */     String sSQL = null;
/*      */     try
/*      */     {
/* 1688 */       sSQL = "DELETE FROM AssetListAttributeValue WHERE AssetId=?";
/* 1689 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1690 */       psql.setLong(1, a_asset.getId());
/* 1691 */       psql.executeUpdate();
/* 1692 */       psql.close();
/*      */ 
/* 1694 */       sSQL = "INSERT INTO AssetListAttributeValue (AssetId, ListAttributeValueId)VALUES (?,?)";
/*      */ 
/* 1696 */       psql = con.prepareStatement(sSQL);
/* 1697 */       for (Long attributeValueId : listAttributeValueIds)
/*      */       {
/* 1699 */         psql.setLong(1, a_asset.getId());
/* 1700 */         psql.setLong(2, attributeValueId.longValue());
/* 1701 */         psql.addBatch();
/*      */       }
/* 1703 */       psql.executeBatch();
/* 1704 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1709 */       throw new SQLStatementException(sSQL, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void saveCategoryAttributeValues(DBTransaction a_transaction, Asset a_asset, Collection<AttributeValue> a_categoryAttributeValues)
/*      */     throws Bn2Exception
/*      */   {
/* 1717 */     for (AttributeValue attVal : a_categoryAttributeValues)
/*      */     {
/* 1720 */       this.m_categoryManager.deleteItemFromCategories(a_transaction, attVal.getAttribute().getTreeId(), a_asset.getId());
/*      */ 
/* 1723 */       this.m_taxonomyManager.addKeywordsToAsset(a_transaction, a_asset.getId(), attVal.getKeywordCategories());
/*      */     }
/*      */   }
/*      */ 
/*      */   public List<AttributeValue> getListAttributeValues(DBTransaction a_dbTransaction, String a_sListAttributeId)
/*      */     throws Bn2Exception
/*      */   {
/* 1737 */     return getListAttributeValues(a_dbTransaction, Long.parseLong(a_sListAttributeId));
/*      */   }
/*      */ 
/*      */   public List<AttributeValue> getListAttributeValues(DBTransaction a_dbTransaction, long a_lListAttributeId)
/*      */     throws Bn2Exception
/*      */   {
/* 1753 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1758 */     Long longKey = new Long(a_lListAttributeId);
/*      */     List attributeValues;
/*      */     //List attributeValues;
/* 1761 */     if (this.m_hmAttributeListValues.containsKey(longKey))
/*      */     {
/* 1763 */       attributeValues = (List)this.m_hmAttributeListValues.get(longKey);
/*      */     }
/*      */     else
/*      */     {
/* 1769 */       if (transaction == null)
/*      */       {
/* 1771 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/* 1776 */         Connection con = transaction.getConnection();
/*      */ 
/* 1778 */         String sSQL = "SELECT lav.Id lavId, lav.IsEditable, lav.Value, lav.AdditionalValue, lav.ActionOnAssetId, lav.MapToFieldValue , tlav.Value tavValue, l.Id lId, l.Name lName, l.Code lCode FROM ListAttributeValue lav LEFT JOIN TranslatedListAttributeValue tlav ON tlav.ListAttributeValueId = lav.Id LEFT JOIN Language l ON l.Id = tlav.LanguageId WHERE lav.AttributeId=? ORDER BY lav.SequenceNumber";
/*      */ 
/* 1789 */         PreparedStatement psql = con.prepareStatement(sSQL, 1004, 1007);
/*      */ 
/* 1791 */         psql.setLong(1, a_lListAttributeId);
/*      */ 
/* 1793 */         ResultSet rs = psql.executeQuery();
/*      */ 
/* 1795 */         attributeValues = new Vector();
/*      */ 
/* 1797 */         while (rs.next())
/*      */         {
/* 1799 */           AttributeValue value = new AttributeValue();
/*      */ 
/* 1801 */           ListAttributeValueDBUtil.populateListAttributeValueFromRS(value, rs);
/*      */ 
/* 1803 */           if (rs.getLong("lId") > 0L)
/*      */           {
/* 1805 */             while ((!rs.isAfterLast()) && (rs.getLong("lavId") == value.getId()))
/*      */             {
/*      */               //AttributeValue tmp190_188 = value; tmp190_188.getClass(); AttributeValue.Translation translation = new AttributeValue.Translation(tmp190_188, new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
/* 1808 */               AttributeValue.Translation translation = value.new Translation( new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
                         translation.setValue(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "tavValue"));
/* 1809 */               value.getTranslations().add(translation);
/* 1810 */               rs.next();
/*      */             }
/*      */ 
/* 1814 */             if ((!rs.isAfterLast()) && (rs.getLong("lavId") != value.getId()))
/*      */             {
/* 1816 */               rs.previous();
/*      */             }
/*      */           }
/*      */ 
/* 1820 */           attributeValues.add(value);
/*      */         }
/*      */ 
/* 1823 */         psql.close();
/*      */ 
/* 1826 */         synchronized (this.m_hmAttributeListValues)
/*      */         {
/* 1828 */           this.m_hmAttributeListValues.put(longKey, attributeValues);
/*      */         }
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 1833 */         throw new SQLStatementException("SQL Exception whilst getting list attribute values from the database", e);
/*      */       }
/*      */       finally
/*      */       {
/* 1838 */         if ((a_dbTransaction == null) && (transaction != null))
/*      */         {
/*      */           try
/*      */           {
/* 1842 */             transaction.commit();
/*      */           }
/*      */           catch (SQLException sqle)
/*      */           {
/* 1846 */             this.m_logger.error("SQL Exception whilst trying to close connection ", sqle);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1852 */     return attributeValues;
/*      */   }
/*      */ 
/*      */   public AttributeValue getListAttributeValue(DBTransaction a_dbTransaction, long a_lListAttributeId, Language a_language, String a_sValue)
/*      */     throws Bn2Exception
/*      */   {
/* 1869 */     AttributeValue ret = null;
/*      */ 
/* 1872 */     List<AttributeValue> values = getListAttributeValues(a_dbTransaction, a_lListAttributeId);
/*      */ 
/* 1874 */     for (AttributeValue av : values)
/*      */     {
/* 1877 */       if (av.getValue(a_language).equals(a_sValue))
/*      */       {
/* 1879 */         ret = av;
/* 1880 */         break;
/*      */       }
/*      */     }
/*      */ 
/* 1884 */     return ret;
/*      */   }
/*      */ 
/*      */   public void addListAttributeValue(DBTransaction a_dbTransaction, AttributeValue a_attributeValue)
/*      */     throws Bn2Exception
/*      */   {
/* 1895 */     ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/*      */ 
/* 1897 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1899 */     if (transaction == null)
/*      */     {
/* 1901 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/* 1904 */     String sSQL = null;
/*      */     try
/*      */     {
/* 1911 */       Connection con = transaction.getConnection();
/*      */ 
/* 1915 */       sSQL = sqlGenerator.getAsSelectForUpdate("SELECT * FROM ListAttributeValue");
/*      */ 
/* 1917 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1918 */       psql.executeQuery();
/* 1919 */       psql.close();
/*      */ 
/* 1922 */       sSQL = "SELECT MAX(SequenceNumber) As SequenceNumber FROM ListAttributeValue WHERE AttributeId=?";
/*      */ 
/* 1927 */       psql = con.prepareStatement(sSQL);
/* 1928 */       psql.setLong(1, a_attributeValue.getAttribute().getId());
/*      */ 
/* 1930 */       ResultSet rs = psql.executeQuery();
/*      */       int iSequenceNumber;
/*      */       //int iSequenceNumber;
/* 1932 */       if (rs.next())
/*      */       {
/* 1934 */         iSequenceNumber = rs.getInt("SequenceNumber") + 1;
/*      */       }
/*      */       else
/*      */       {
/* 1938 */         iSequenceNumber = 1;
/*      */       }
/*      */ 
/* 1941 */       psql.close();
/*      */ 
/* 1944 */       long lNewId = 0L;
/*      */ 
/* 1947 */       sSQL = "INSERT INTO ListAttributeValue (";
/*      */ 
/* 1949 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1957 */         lNewId = sqlGenerator.getUniqueId(con, "AttributeValueSequence");
/* 1958 */         sSQL = sSQL + "Id, ";
/*      */       }
/*      */ 
/* 1961 */       sSQL = sSQL + "AttributeId,IsEditable,Value,AdditionalValue,SequenceNumber,ActionOnAssetId,MapToFieldValue) VALUES (";
/*      */ 
/* 1963 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1965 */         sSQL = sSQL + "?,";
/*      */       }
/*      */ 
/* 1968 */       sSQL = sSQL + "?,?,?,?,?,?,?)";
/*      */ 
/* 1970 */       psql = con.prepareStatement(sSQL);
/* 1971 */       int iCol = 1;
/*      */ 
/* 1973 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1975 */         psql.setLong(iCol++, lNewId);
/*      */       }
/*      */ 
/* 1978 */       psql.setLong(iCol++, a_attributeValue.getAttribute().getId());
/* 1979 */       psql.setBoolean(iCol++, a_attributeValue.isEditable());
/* 1980 */       psql.setString(iCol++, a_attributeValue.getValue());
/* 1981 */       psql.setString(iCol++, a_attributeValue.getAdditionalValue());
/* 1982 */       psql.setInt(iCol++, iSequenceNumber);
/* 1983 */       DBUtil.setFieldIdOrNull(psql, iCol++, a_attributeValue.getActionOnAssetId());
/* 1984 */       psql.setString(iCol++, a_attributeValue.getMapToFieldValue());
/*      */ 
/* 1986 */       psql.executeUpdate();
/*      */ 
/* 1989 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1991 */         lNewId = sqlGenerator.getUniqueId(con, "ListAttributeValue");
/*      */       }
/*      */ 
/* 1994 */       psql.close();
/*      */ 
/* 1997 */       a_attributeValue.setId(lNewId);
/*      */ 
/* 2000 */       Iterator itTranslations = a_attributeValue.getTranslations().iterator();
/* 2001 */       while (itTranslations.hasNext())
/*      */       {
/* 2003 */         AttributeValue.Translation translation = (AttributeValue.Translation)itTranslations.next();
/* 2004 */         if (translation.getLanguage().getId() > 0L)
/*      */         {
/* 2006 */           saveListAttributeValueTranslation(transaction, translation);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2011 */       invalidateAttributeCaches(a_attributeValue.getAttribute().getId());
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       throw new SQLStatementException(sSQL, e);
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 2031 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2035 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2039 */           this.m_logger.error("SQL Exception whilst rolling back connection," + sqle);
/*      */         }
/*      */       }
/*      */ 
/* 2043 */       throw e;
/*      */     }
/*      */     finally
/*      */     {
/* 2048 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2052 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2056 */           this.m_logger.error("SQL Exception whilst trying to close connection", sqle);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateListAttributeValue(DBTransaction a_dbTransaction, AttributeValue a_attributeValue)
/*      */     throws Bn2Exception
/*      */   {
/* 2069 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 2071 */     if (transaction == null)
/*      */     {
/* 2073 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/* 2076 */     String sSQL = "UPDATE ListAttributeValue SET IsEditable=?, Value=?, AdditionalValue=?, ActionOnAssetId=?, MapToFieldValue=? WHERE Id=?";
/*      */     try
/*      */     {
/* 2079 */       Connection con = transaction.getConnection();
/*      */ 
/* 2081 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/* 2083 */       int iParam = 1;
/* 2084 */       psql.setBoolean(iParam++, a_attributeValue.isEditable());
/* 2085 */       psql.setString(iParam++, a_attributeValue.getValue());
/* 2086 */       psql.setString(iParam++, a_attributeValue.getAdditionalValue());
/* 2087 */       DBUtil.setFieldIdOrNull(psql, iParam++, a_attributeValue.getActionOnAssetId());
/* 2088 */       psql.setString(iParam++, a_attributeValue.getMapToFieldValue());
/*      */ 
/* 2090 */       psql.setLong(iParam++, a_attributeValue.getId());
/*      */ 
/* 2093 */       Iterator itTranslations = a_attributeValue.getTranslations().iterator();
/* 2094 */       while (itTranslations.hasNext())
/*      */       {
/* 2096 */         AttributeValue.Translation translation = (AttributeValue.Translation)itTranslations.next();
/* 2097 */         if (translation.getLanguage().getId() > 0L)
/*      */         {
/* 2099 */           saveListAttributeValueTranslation(transaction, translation);
/*      */         }
/*      */       }
/*      */ 
/* 2103 */       psql.executeUpdate();
/* 2104 */       psql.close();
/*      */ 
/* 2107 */       invalidateAttributeCaches(-1L);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2111 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2115 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2119 */           this.m_logger.error("SQL Exception whilst rolling back connection," + sqle);
/*      */         }
/*      */       }
/*      */ 
/* 2123 */       throw new SQLStatementException(sSQL, e);
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 2127 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2131 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2135 */           this.m_logger.error("SQL Exception whilst rolling back connection," + sqle);
/*      */         }
/*      */       }
/*      */ 
/* 2139 */       throw e;
/*      */     }
/*      */     finally
/*      */     {
/* 2144 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2148 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2152 */           this.m_logger.error("SQL Exception whilst trying to close connection", sqle);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void saveListAttributeValueTranslation(DBTransaction a_dbTransaction, AttributeValue.Translation a_translation)
/*      */     throws Bn2Exception
/*      */   {
/* 2164 */     String sSQL = null;
/*      */     try
/*      */     {
/* 2168 */       Connection con = a_dbTransaction.getConnection();
/* 2169 */       int iCol = 1;
/*      */ 
/* 2171 */       sSQL = "DELETE FROM TranslatedListAttributeValue WHERE ListAttributeValueId=? AND LanguageId=?";
/* 2172 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 2173 */       psql.setLong(iCol++, a_translation.getAttributeValueId());
/* 2174 */       psql.setLong(iCol++, a_translation.getLanguage().getId());
/* 2175 */       psql.executeUpdate();
/* 2176 */       psql.close();
/*      */ 
/* 2178 */       iCol = 1;
/* 2179 */       sSQL = "INSERT INTO TranslatedListAttributeValue (Value,AdditionalValue,ListAttributeValueId,LanguageId) VALUES (?,?,?,?)";
/* 2180 */       psql = con.prepareStatement(sSQL);
/* 2181 */       psql.setString(iCol++, a_translation.getValue());
/* 2182 */       psql.setString(iCol++, a_translation.getAdditionalValue());
/* 2183 */       psql.setLong(iCol++, a_translation.getAttributeValueId());
/* 2184 */       psql.setLong(iCol++, a_translation.getLanguage().getId());
/* 2185 */       psql.executeUpdate();
/* 2186 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 2190 */       throw new SQLStatementException(sSQL, sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public AttributeValue getListAttributeValueId(DBTransaction a_dbTransaction, long a_lAttributeId, String a_sAttributeValue, boolean a_bAddIfNotThere)
/*      */     throws Bn2Exception
/*      */   {
/* 2206 */     AttributeValue value = null;
/*      */ 
/* 2209 */     List attributeValues = getListAttributeValues(a_dbTransaction, a_lAttributeId);
/*      */ 
/* 2211 */     for (int i = 0; i < attributeValues.size(); i++)
/*      */     {
/* 2213 */       value = (AttributeValue)attributeValues.get(i);
/*      */ 
/* 2215 */       if (value.getValue().equalsIgnoreCase(a_sAttributeValue))
/*      */       {
/*      */         break;
/*      */       }
/* 2219 */       value = null;
/*      */     }
/*      */ 
/* 2222 */     if ((value == null) && (a_bAddIfNotThere))
/*      */     {
/* 2225 */       value = new AttributeValue();
/* 2226 */       value.setValue(a_sAttributeValue);
/* 2227 */       value.setEditable(true);
/* 2228 */       value.getAttribute().setId(a_lAttributeId);
/*      */ 
/* 2231 */       addListAttributeValue(a_dbTransaction, value);
/*      */     }
/*      */ 
/* 2234 */     return value;
/*      */   }
/*      */ 
/*      */   public void deleteListAttributeValue(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 2245 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 2247 */     if (transaction == null)
/*      */     {
/* 2249 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/* 2252 */     String sSQL = null;
/*      */     try
/*      */     {
/* 2255 */       Connection con = transaction.getConnection();
/*      */ 
/* 2257 */       sSQL = "DELETE FROM AssetListAttributeValue WHERE ListAttributeValueId=?";
/* 2258 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 2259 */       psql.setLong(1, a_lId);
/* 2260 */       psql.executeUpdate();
/* 2261 */       psql.close();
/*      */ 
/* 2263 */       sSQL = "DELETE FROM TranslatedListAttributeValue WHERE ListAttributeValueId=?";
/* 2264 */       psql = con.prepareStatement(sSQL);
/* 2265 */       psql.setLong(1, a_lId);
/* 2266 */       psql.executeUpdate();
/* 2267 */       psql.close();
/*      */ 
/* 2269 */       sSQL = "DELETE FROM ListAttributeValue WHERE Id=?";
/* 2270 */       psql = con.prepareStatement(sSQL);
/* 2271 */       psql.setLong(1, a_lId);
/* 2272 */       psql.executeUpdate();
/* 2273 */       psql.close();
/*      */ 
/* 2276 */       invalidateAttributeCaches(-1L);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       throw new SQLStatementException(sSQL, e);
/*      */     }
/*      */     finally
/*      */     {
/* 2297 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2301 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2305 */           this.m_logger.error("SQL Exception whilst trying to close connection", sqle);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void reorderListAttributeValues(DBTransaction a_dbTransaction, long a_lAttributeId)
/*      */     throws Bn2Exception
/*      */   {
/* 2317 */     Connection con = null;
/* 2318 */     PreparedStatement psql = null;
/* 2319 */     String sSQL = null;
/* 2320 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2324 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2327 */       List orderedValues = new ArrayList();
/*      */ 
/* 2329 */       sSQL = "SELECT Id FROM ListAttributeValue WHERE AttributeId=? AND IsEditable=? ORDER BY " + SQLGenerator.getInstance().getOrderByForLargeTextField("Value", 100) + " ASC";
/*      */ 
/* 2334 */       psql = con.prepareStatement(sSQL);
/* 2335 */       psql.setLong(1, a_lAttributeId);
/* 2336 */       psql.setBoolean(2, true);
/*      */ 
/* 2338 */       rs = psql.executeQuery();
/*      */ 
/* 2340 */       while (rs.next())
/*      */       {
/* 2342 */         long lAttributeValueId = rs.getLong("Id");
/* 2343 */         orderedValues.add(new Long(lAttributeValueId));
/*      */       }
/* 2345 */       psql.close();
/*      */ 
/* 2348 */       for (int i = 0; i < orderedValues.size(); i++)
/*      */       {
/* 2350 */         long lAttributeValueId = ((Long)orderedValues.get(i)).longValue();
/* 2351 */         sSQL = "UPDATE ListAttributeValue SET SequenceNumber=? WHERE Id=?";
/*      */ 
/* 2353 */         psql = con.prepareStatement(sSQL);
/* 2354 */         psql.setLong(1, i + 1);
/* 2355 */         psql.setLong(2, lAttributeValueId);
/* 2356 */         psql.executeUpdate();
/* 2357 */         psql.close();
/*      */       }
/*      */ 
/* 2361 */       invalidateAttributeCaches(-1L);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2365 */       this.m_logger.error("SQL Exception whilst sorting attribute values : " + e);
/* 2366 */       throw new Bn2Exception("SQL Exception whilst sorting attribute values : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   void invalidateAttributeCaches(long lAttributeId)
/*      */   {
/* 2379 */     synchronized (this.m_oAssetAttributesLock)
/*      */     {
/* 2381 */       this.m_vecAssetAttributesCache = null;
/*      */     }
/*      */ 
/* 2384 */     synchronized (this.m_hmAttributeListValues)
/*      */     {
/* 2386 */       if (lAttributeId > 0L)
/*      */       {
/* 2388 */         this.m_hmAttributeListValues.remove(Long.valueOf(lAttributeId));
/*      */       }
/*      */       else
/*      */       {
/* 2392 */         this.m_hmAttributeListValues.clear();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public IAssetManager getAssetManager()
/*      */   {
/* 2401 */     return this.m_assetManager;
/*      */   }
/*      */ 
/*      */   public void setAssetManager(IAssetManager a_assetManager)
/*      */   {
/* 2406 */     this.m_assetManager = a_assetManager;
/*      */   }
/*      */ 
/*      */   public void setAttributeManager(AttributeManager a_attributeManager)
/*      */   {
/* 2411 */     this.m_attributeManager = a_attributeManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryManager(CategoryManager a_categoryManager)
/*      */   {
/* 2416 */     this.m_categoryManager = a_categoryManager;
/*      */   }
/*      */ 
/*      */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*      */   {
/* 2421 */     this.m_searchManager = a_searchManager;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*      */   {
/* 2426 */     this.m_transactionManager = a_transactionManager;
/*      */   }
/*      */ 
/*      */   public void setTaxonomyManager(TaxonomyManager a_taxonomyManager)
/*      */   {
/* 2431 */     this.m_taxonomyManager = a_taxonomyManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.service.AttributeValueManager
 * JD-Core Version:    0.6.0
 */