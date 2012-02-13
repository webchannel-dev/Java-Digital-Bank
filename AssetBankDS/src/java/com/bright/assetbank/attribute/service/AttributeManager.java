/*      */ package com.bright.assetbank.attribute.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.bean.AssetConversionInfo;
/*      */ import com.bright.assetbank.application.bean.ImageAsset;
/*      */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*      */ import com.bright.assetbank.application.service.FileAssetManagerImpl;
/*      */ import com.bright.assetbank.application.service.IAssetManager;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.Attribute.Translation;
/*      */ import com.bright.assetbank.attribute.bean.AttributeInList;
/*      */ import com.bright.assetbank.attribute.bean.AttributeType;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ //import com.bright.assetbank.attribute.bean.AttributeValue.Translation;
/*      */ import com.bright.assetbank.attribute.bean.DataLookupRequest;
/*      */ import com.bright.assetbank.attribute.bean.DisplayAttribute;
/*      */ import com.bright.assetbank.attribute.bean.EmbeddedDataMapping;
/*      */ import com.bright.assetbank.attribute.bean.EmbeddedDataType;
/*      */ import com.bright.assetbank.attribute.bean.EmbeddedDataValue;
/*      */ import com.bright.assetbank.attribute.bean.MappingDirection;
/*      */ import com.bright.assetbank.attribute.bean.SortAttribute;
/*      */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*      */ import com.bright.assetbank.attribute.constant.AttributeStorageType;
/*      */ import com.bright.assetbank.attribute.plugin.DataLookupPlugin;
/*      */ import com.bright.assetbank.attribute.plugin.DataLookupPluginFactory;
/*      */ import com.bright.assetbank.attribute.util.AttributeDBUtil;
/*      */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*      */ import com.bright.assetbank.attribute.util.AttributeValueUtil;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*      */ import com.bright.assetbank.search.constant.AssetBankSearchConstants;
/*      */ import com.bright.assetbank.taxonomy.bean.Keyword;
/*      */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.framework.category.service.CategoryManager;
/*      */ import com.bright.framework.common.bean.BrightDate;
/*      */ import com.bright.framework.common.bean.BrightDateTime;
/*      */ import com.bright.framework.common.bean.BrightMoney;
/*      */ import com.bright.framework.common.bean.IdValueBean;
/*      */ import com.bright.framework.common.bean.KeyValueBean;
/*      */ import com.bright.framework.common.bean.StringDataBean;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.exception.SQLStatementException;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.image.util.ExifTool;
/*      */ import com.bright.framework.image.util.ImageMagick;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.search.constant.SearchConstants;
/*      */ import com.bright.framework.search.service.SortFieldSpecifier;
/*      */ import com.bright.framework.service.FileStoreManager;
/*      */ import com.bright.framework.util.CollectionUtil;
/*      */ import com.bright.framework.util.FileUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.lucene.search.SortField;
/*      */ 
/*      */ public class AttributeManager extends Bn2Manager
/*      */   implements SortFieldSpecifier, AttributeConstants, AssetBankSearchConstants, SearchConstants, AssetBankConstants
/*      */ {
/*      */   private static final String c_ksClassName = "AttributeManager";
/*      */   private static final int c_kiExcludedFromSequenceNo = -1;
/*      */   private DBTransactionManager m_transactionManager;
/*      */   private AttributeStorageManager m_attributeStorageManager;
/*      */   private AttributeValueManager m_attributeValueManager;
/*      */   private IAssetManager m_assetManager;
/*      */   private CategoryManager m_categoryManager;
/*      */   protected TaxonomyManager m_taxonomyManager;
/*      */   private FileAssetManagerImpl m_fileAssetManager;
/*      */   private FileStoreManager m_fileStoreManager;
/*      */   private AssetEntityManager m_assetEntityManager;
/*      */   private final Map<GetAttributeArgs, Attribute> m_attributeCache;
/*      */   private final Map<GetAttributesArgs, Vector<Attribute>> m_attributesCache;
/*      */   private Vector<Attribute> m_vecAttributePositions;
/*      */   private final Object m_oAttributeLock;
/*      */   private HashMap m_hmSortLists;
/*      */   private HashMap m_hmSortFields;
/*      */   private Object m_oSortLock;
/*      */   private HashMap<Long, Vector<DisplayAttribute>> m_hmDisplayAttributes;
/*      */   private Vector<DisplayAttribute> m_vecAllDisplayAttributes;
/*      */   private Object m_oDisplayLock;
/*      */   private HashMap m_hmEmbeddedDataMappings;
/*      */   private Vector m_vecAllMappings;
/*      */   private Object m_oMappingLock;
/*      */   private ArrayList<IdValueBean> m_alDisplayAttributeGroupCache;
/*      */   private Object m_oDisplayAttributeGroupLock;
/*      */   private List<AttributeType> m_attributeTypes;
/*      */   private Map<Long, AttributeType> m_attributeTypesById;
/*      */   private final Object m_attributeTypesLock;
/*      */   Object m_oTasklock;
/*      */   private static final String c_ksAttributeJoin = "Attribute a LEFT JOIN TranslatedAttribute ta ON a.Id = ta.AttributeId LEFT JOIN Language l ON l.Id = ta.LanguageId ";
/*      */   public static final String c_ksDisplayAttributeFields = "da.DisplayLength, da.SequenceNumber daSequence, da.ShowLabel, da.IsLink, da.DisplayAttributeGroupId, da.ShowOnChild daShowOnChild";
/*      */   public static final String c_ksAttributeFields = "a.Id aId, a.IsStatic, a.IsReadOnly, a.StaticFieldName, a.Label, a.IsMandatory, a.IsMandatoryBulkUpload, a.IsKeywordSearchable, a.AttributeTypeId, a.IsSearchField, a.IsSearchBuilderField, a.DefaultValue, a.NameAttribute, a.IsDescriptionAttribute, a.ValueIfNotVisible, a.SequenceNumber, a.MaxDisplayLength, a.Highlight, a.HelpText, a.TreeId, a.OnChangeScript, a.DefaultKeywordFilter, a.IsHtml, a.IsRequiredForCompleteness, a.RequiresTranslation, a.DisplayName, a.BaseUrl, a.AltText, a.HasSearchTokens, a.TokenDelimiterRegex, a.Height, a.Width, a.MaxSize, a.IsFiltered, a.Seed, a.IncrementAmount, a.Prefix, a.IsExtendableList, a.InputMask, a.ShowOnChild, a.IncludeInSearchForChild, a.GetDataFromChildren, a.HideForSort, a.IsAutoComplete, a.PluginClass, a.PluginParamsAttributeIds, a.ValueColumnName, a.ShowOnDownload, a.MaxDecimalPlaces, a.MinDecimalPlaces, a.Hidden ";
/*      */   public static final String c_ksLanguageFieldsAliased = "l.Id lId, l.Name lName, l.NativeName lNativeName, l.Code lCode";
/*      */   public static final String c_ksAttributeFieldsWithTranslations = "a.Id aId, a.IsStatic, a.IsReadOnly, a.StaticFieldName, a.Label, a.IsMandatory, a.IsMandatoryBulkUpload, a.IsKeywordSearchable, a.AttributeTypeId, a.IsSearchField, a.IsSearchBuilderField, a.DefaultValue, a.NameAttribute, a.IsDescriptionAttribute, a.ValueIfNotVisible, a.SequenceNumber, a.MaxDisplayLength, a.Highlight, a.HelpText, a.TreeId, a.OnChangeScript, a.DefaultKeywordFilter, a.IsHtml, a.IsRequiredForCompleteness, a.RequiresTranslation, a.DisplayName, a.BaseUrl, a.AltText, a.HasSearchTokens, a.TokenDelimiterRegex, a.Height, a.Width, a.MaxSize, a.IsFiltered, a.Seed, a.IncrementAmount, a.Prefix, a.IsExtendableList, a.InputMask, a.ShowOnChild, a.IncludeInSearchForChild, a.GetDataFromChildren, a.HideForSort, a.IsAutoComplete, a.PluginClass, a.PluginParamsAttributeIds, a.ValueColumnName, a.ShowOnDownload, a.MaxDecimalPlaces, a.MinDecimalPlaces, a.Hidden ,l.Id lId, l.Name lName, l.NativeName lNativeName, l.Code lCode, ta.LanguageId taLanguageId, ta.Label taLabel, ta.DefaultValue taDefaultValue, ta.ValueIfNotVisible taValueIfNotVisible, ta.HelpText taHelpText, ta.AltText taAltText, ta.InputMask taInputMask, ta.DisplayName taDisplayName";
/*      */ 
/*      */   public AttributeManager()
/*      */   {
/*  209 */     this.m_transactionManager = null;
/*  210 */     this.m_attributeStorageManager = null;
/*  211 */     this.m_attributeValueManager = null;
/*      */ 
/*  213 */     this.m_assetManager = null;
/*  214 */     this.m_categoryManager = null;
/*      */ 
/*  216 */     this.m_taxonomyManager = null;
/*  217 */     this.m_fileAssetManager = null;
/*  218 */     this.m_fileStoreManager = null;
/*  219 */     this.m_assetEntityManager = null;
/*      */ 
/*  223 */     this.m_attributeCache = new HashMap();
/*      */ 
/*  226 */     this.m_attributesCache = new HashMap();
/*      */ 
/*  229 */     this.m_vecAttributePositions = null;
/*  230 */     this.m_oAttributeLock = new Object();
/*      */ 
/*  232 */     this.m_hmSortLists = new HashMap();
/*  233 */     this.m_hmSortFields = new HashMap();
/*  234 */     this.m_oSortLock = new Object();
/*      */ 
/*  236 */     this.m_hmDisplayAttributes = new HashMap();
/*  237 */     this.m_vecAllDisplayAttributes = null;
/*  238 */     this.m_oDisplayLock = new Object();
/*      */ 
/*  240 */     this.m_hmEmbeddedDataMappings = new HashMap();
/*  241 */     this.m_vecAllMappings = null;
/*  242 */     this.m_oMappingLock = new Object();
/*      */ 
/*  244 */     this.m_alDisplayAttributeGroupCache = null;
/*  245 */     this.m_oDisplayAttributeGroupLock = new Object();
/*      */ 
/*  247 */     this.m_attributeTypes = null;
/*  248 */     this.m_attributeTypesById = null;
/*  249 */     this.m_attributeTypesLock = new Object();
/*      */ 
/*  251 */     this.m_oTasklock = new Object();
/*      */   }
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*  344 */     super.startup();
/*  345 */     optimiseAttributeSequences(null);
/*      */   }
/*      */ 
/*      */   private void optimiseAttributeSequences(DBTransaction a_transaction)
/*      */     throws Bn2Exception
/*      */   {
/*  366 */     DBTransaction transaction = a_transaction;
/*  367 */     String ksMethodName = "organiseAttributeSequence";
/*      */     try
/*      */     {
/*  371 */       if (transaction == null)
/*      */       {
/*  373 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/*  376 */       Vector<Attribute> vecAttributePositions = getAttributePositionList(transaction);
            /*  377 */
            int iSequence = 1;
/*      */ 
/*  379 */       for (Attribute att : vecAttributePositions)
/*      */       {
/*  382 */         int iNewSequence = -1;
/*  383 */         if (!att.isHidden())
/*      */         {
/*  385 */           if ((att.getFieldName() == null) || (((!att.getFieldName().equals("price")) || (AssetBankSettings.ecommerce())) && ((!att.getFieldName().equals("version")) || (AssetBankSettings.getCanCreateAssetVersions())) && ((!att.getFieldName().equals("audit")) || (AssetBankSettings.getAuditLogEnabled())) && ((!att.getFieldName().equals("agreements")) || (AssetBankSettings.getAgreementsEnabled())) && ((!att.getFieldName().equals("rating")) || (AssetBankSettings.getFeedbackRatings())) && (((!att.getFieldName().equals("sensitive")) && (att.getId() != 301L)) || (AssetBankSettings.getShowSensitivityFields()))))
/*      */           {
/*  393 */             iNewSequence = iSequence;
/*  394 */             iSequence++;
/*      */           }
/*      */         }
/*      */ 
/*  398 */         updateAttributePosition(transaction, att.getId(), iNewSequence);
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */       //int iSequence;
/*  404 */       if ((a_transaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  408 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  412 */           this.m_logger.error("AttributeManagerorganiseAttributeSequence : SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Attribute getAttributeById(DBTransaction a_dbTransaction, String a_sId)
/*      */     throws Bn2Exception
/*      */   {
/*  432 */     return getAttribute(a_dbTransaction, Long.parseLong(a_sId));
/*      */   }
/*      */ 
/*      */   public Attribute getAttribute(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  454 */     return getAttribute(a_dbTransaction, a_lId, null);
/*      */   }
/*      */ 
/*      */   public Attribute getAttribute(DBTransaction a_dbTransaction, String a_StaticFieldName)
/*      */     throws Bn2Exception
/*      */   {
/*  476 */     return getAttribute(a_dbTransaction, 0L, a_StaticFieldName);
/*      */   }
/*      */ 
/*      */   public Attribute getStaticAttribute(String a_StaticFieldName)
/*      */     throws Bn2Exception
/*      */   {
/*  497 */     return getAttribute(null, a_StaticFieldName);
/*      */   }
/*      */ 
/*      */   private Attribute getAttribute(DBTransaction a_dbTransaction, long a_lId, String a_StaticFieldName)
/*      */     throws Bn2Exception
/*      */   {
/*      */     Attribute attribute;
/*  510 */     synchronized (this.m_oAttributeLock)
/*      */     {
/*  512 */       GetAttributeArgs args = new GetAttributeArgs(a_lId, a_StaticFieldName);
/*  513 */       attribute = (Attribute)this.m_attributeCache.get(args);
/*  514 */       if (attribute == null)
/*      */       {
/*  516 */         attribute = getAttributeFromDB(a_dbTransaction, a_lId, a_StaticFieldName);
/*  517 */         this.m_attributeCache.put(args, attribute);
/*      */       }
/*      */     }
/*  520 */     return attribute;
/*      */   }
/*      */ 
/*      */   private Attribute getAttributeFromDB(DBTransaction a_dbTransaction, long a_lId, String a_StaticFieldName)
/*      */     throws Bn2Exception
/*      */   {
/*  576 */     Attribute attribute = null;
/*  577 */     Connection con = null;
/*  578 */     ResultSet rs = null;
/*  579 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/*  581 */     if (transaction == null)
/*      */     {
/*  583 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  588 */       con = transaction.getConnection();
/*      */ 
/*  590 */       String sSQL = "SELECT a.Id aId, a.IsStatic, a.IsReadOnly, a.StaticFieldName, a.Label, a.IsMandatory, a.IsMandatoryBulkUpload, a.IsKeywordSearchable, a.AttributeTypeId, a.IsSearchField, a.IsSearchBuilderField, a.DefaultValue, a.NameAttribute, a.IsDescriptionAttribute, a.ValueIfNotVisible, a.SequenceNumber, a.MaxDisplayLength, a.Highlight, a.HelpText, a.TreeId, a.OnChangeScript, a.DefaultKeywordFilter, a.IsHtml, a.IsRequiredForCompleteness, a.RequiresTranslation, a.DisplayName, a.BaseUrl, a.AltText, a.HasSearchTokens, a.TokenDelimiterRegex, a.Height, a.Width, a.MaxSize, a.IsFiltered, a.Seed, a.IncrementAmount, a.Prefix, a.IsExtendableList, a.InputMask, a.ShowOnChild, a.IncludeInSearchForChild, a.GetDataFromChildren, a.HideForSort, a.IsAutoComplete, a.PluginClass, a.PluginParamsAttributeIds, a.ValueColumnName, a.ShowOnDownload, a.MaxDecimalPlaces, a.MinDecimalPlaces, a.Hidden ,l.Id lId, l.Name lName, l.NativeName lNativeName, l.Code lCode, ta.LanguageId taLanguageId, ta.Label taLabel, ta.DefaultValue taDefaultValue, ta.ValueIfNotVisible taValueIfNotVisible, ta.HelpText taHelpText, ta.AltText taAltText, ta.InputMask taInputMask, ta.DisplayName taDisplayName FROM Attribute a LEFT JOIN TranslatedAttribute ta ON a.Id = ta.AttributeId LEFT JOIN Language l ON l.Id = ta.LanguageId LEFT JOIN AttributeType at ON a.AttributeTypeId = at.Id WHERE";
/*      */ 
/*  595 */       if (a_lId > 0L)
/*      */       {
/*  597 */         sSQL = sSQL + " a.Id=?";
/*      */       }
/*      */       else
/*      */       {
/*  601 */         sSQL = sSQL + " a.StaticFieldName=?";
/*      */       }
/*      */ 
/*  605 */       PreparedStatement psql = con.prepareStatement(sSQL, 1004, 1007);
/*      */ 
/*  607 */       if (a_lId > 0L)
/*      */       {
/*  609 */         psql.setLong(1, a_lId);
/*      */       }
/*      */       else
/*      */       {
/*  613 */         psql.setString(1, a_StaticFieldName);
/*      */       }
/*      */ 
/*  616 */       rs = psql.executeQuery();
/*      */ 
/*  618 */       if (rs.next())
/*      */       {
/*  621 */         attribute = AttributeDBUtil.buildAttribute(rs);
/*  622 */         buildAttributeTranslations(rs, attribute);
/*      */       }
/*      */ 
/*  625 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  629 */       this.m_logger.error("SQL Exception whilst getting an attribute from the database : " + e);
/*  630 */       throw new Bn2Exception("SQL Exception whilst getting an attribute from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  635 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  639 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  643 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  648 */     return attribute;
/*      */   }
/*      */ 
/*      */   public Vector<Attribute> getAttributes(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  657 */     return getAttributes(a_dbTransaction, -1L, false);
/*      */   }
/*      */ 
/*      */   public Map<Long, Attribute> getAttributesById(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  666 */     Collection<Attribute> attributes = getAttributes(a_dbTransaction);
/*      */ 
/*  668 */     Map attributesById = new HashMap();
/*  669 */     for (Attribute attribute : attributes)
/*      */     {
/*  671 */       attributesById.put(Long.valueOf(attribute.getId()), attribute);
/*      */     }
/*      */ 
/*  675 */     return attributesById;
/*      */   }
/*      */ 
/*      */   public Vector<Attribute> getAttributes(DBTransaction a_dbTransaction, long a_lTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  689 */     return getAttributes(a_dbTransaction, a_lTypeId, false);
/*      */   }
/*      */ 
/*      */   public Vector<Attribute> getAttributes(DBTransaction a_dbTransaction, long a_lTypeId, boolean a_bIncludeHeadings)
/*      */     throws Bn2Exception
/*      */   {
/*  698 */     return getAttributes(a_dbTransaction, a_lTypeId, a_bIncludeHeadings, false, 2, false);
/*      */   }
/*      */ 
/*      */   public Vector<Attribute> getVisibleAttributes(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  704 */     return getAttributes(null, -1L, false, false, 1, false);
/*      */   }
/*      */ 
/*      */   public Vector<Attribute> getHiddenAttributes()
/*      */     throws Bn2Exception
/*      */   {
/*  710 */     return getAttributes(null, -1L, false, false, 0, false);
/*      */   }
/*      */ 
/*      */   public Vector<Attribute> getAttributes(DBTransaction a_dbTransaction, long a_lTypeId, boolean a_bIncludeHeadings, boolean a_bIgnoreSettings, int a_iVisibileStatus, boolean a_bNameAttributes)
/*      */     throws Bn2Exception
/*      */   {
/*      */     Vector attributes;
/*  723 */     synchronized (this.m_oAttributeLock)
/*      */     {
/*  725 */       GetAttributesArgs args = new GetAttributesArgs(a_lTypeId, a_bIncludeHeadings, a_bIgnoreSettings, a_iVisibileStatus, a_bNameAttributes);
/*  726 */       attributes = (Vector)this.m_attributesCache.get(args);
/*  727 */       if (attributes == null)
/*      */       {
/*  729 */         attributes = getAttributesFromDB(a_dbTransaction, a_lTypeId, a_bIncludeHeadings, a_bIgnoreSettings, a_iVisibileStatus, a_bNameAttributes);
/*  730 */         this.m_attributesCache.put(args, attributes);
/*      */       }
/*      */     }
/*      */ 
/*  734 */     return CollectionUtil.deepCloneVectorOfDataBeans(attributes);
/*      */   }
/*      */ 
/*      */   private Vector<Attribute> getAttributesFromDB(DBTransaction a_dbTransaction, long a_lTypeId, boolean a_bIncludeHeadings, boolean a_bIgnoreSettings, int a_iVisibleStatus, boolean a_bNameAttributes)
/*      */     throws Bn2Exception
/*      */   {
/*  791 */     Vector vecAttributes = null;
/*      */ 
/*  794 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/*  796 */     if (transaction == null)
/*      */     {
/*  798 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*  801 */     String sSQL = null;
/*      */     try
/*      */     {
/*  804 */       Connection con = transaction.getConnection();
/*      */ 
/*  806 */       sSQL = "SELECT a.Id aId, a.IsStatic, a.IsReadOnly, a.StaticFieldName, a.Label, a.IsMandatory, a.IsMandatoryBulkUpload, a.IsKeywordSearchable, a.AttributeTypeId, a.IsSearchField, a.IsSearchBuilderField, a.DefaultValue, a.NameAttribute, a.IsDescriptionAttribute, a.ValueIfNotVisible, a.SequenceNumber, a.MaxDisplayLength, a.Highlight, a.HelpText, a.TreeId, a.OnChangeScript, a.DefaultKeywordFilter, a.IsHtml, a.IsRequiredForCompleteness, a.RequiresTranslation, a.DisplayName, a.BaseUrl, a.AltText, a.HasSearchTokens, a.TokenDelimiterRegex, a.Height, a.Width, a.MaxSize, a.IsFiltered, a.Seed, a.IncrementAmount, a.Prefix, a.IsExtendableList, a.InputMask, a.ShowOnChild, a.IncludeInSearchForChild, a.GetDataFromChildren, a.HideForSort, a.IsAutoComplete, a.PluginClass, a.PluginParamsAttributeIds, a.ValueColumnName, a.ShowOnDownload, a.MaxDecimalPlaces, a.MinDecimalPlaces, a.Hidden ,l.Id lId, l.Name lName, l.NativeName lNativeName, l.Code lCode, ta.LanguageId taLanguageId, ta.Label taLabel, ta.DefaultValue taDefaultValue, ta.ValueIfNotVisible taValueIfNotVisible, ta.HelpText taHelpText, ta.AltText taAltText, ta.InputMask taInputMask, ta.DisplayName taDisplayName FROM Attribute a LEFT JOIN TranslatedAttribute ta ON a.Id = ta.AttributeId LEFT JOIN Language l ON l.Id = ta.LanguageId LEFT JOIN AttributeType at ON a.AttributeTypeId = at.Id WHERE 1=1 ";
/*      */ 
/*  811 */       if (a_lTypeId > 0L)
/*      */       {
/*  813 */         sSQL = sSQL + " AND a.AttributeTypeId=?";
/*      */       }
/*  815 */       else if (!a_bIncludeHeadings)
/*      */       {
/*  817 */         sSQL = sSQL + " AND (a.AttributeTypeId<>? OR " + SQLGenerator.getInstance().getNullCheckStatement("a.AttributeTypeId") + ")";
/*      */       }
/*      */ 
/*  820 */       if (a_bNameAttributes)
/*      */       {
/*  822 */         sSQL = sSQL + " AND a.NameAttribute=?";
/*      */       }
/*      */ 
/*  825 */       if (!a_bIgnoreSettings)
/*      */       {
/*  827 */         sSQL = sSQL + settingsDependentFilterSQL("a");
/*      */       }
/*      */ 
/*  831 */       if (a_iVisibleStatus >= 0)
/*      */       {
/*  833 */         switch (a_iVisibleStatus)
/*      */         {
/*      */         case 0:
/*  836 */           sSQL = sSQL + " AND Hidden=1";
/*  837 */           break;
/*      */         case 1:
/*  839 */           sSQL = sSQL + " AND Hidden=0";
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  845 */       sSQL = sSQL + " ORDER BY a.SequenceNumber";
/*      */ 
/*  848 */       PreparedStatement psql = con.prepareStatement(sSQL, 1004, 1007);
/*  849 */       int iCol = 1;
/*      */ 
/*  851 */       if (a_lTypeId > 0L)
/*      */       {
/*  853 */         psql.setLong(iCol++, a_lTypeId);
/*      */       }
/*  855 */       else if (!a_bIncludeHeadings)
/*      */       {
/*  857 */         psql.setLong(iCol++, 10L);
/*      */       }
/*      */ 
/*  860 */       if (a_bNameAttributes)
/*      */       {
/*  862 */         psql.setBoolean(iCol++, true);
/*      */       }
/*      */ 
/*  865 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  867 */       while (rs.next())
/*      */       {
/*  869 */         if (vecAttributes == null)
/*      */         {
/*  871 */           vecAttributes = new Vector();
/*      */         }
/*      */ 
/*  875 */         Attribute attribute = AttributeDBUtil.buildAttribute(rs);
/*      */ 
/*  878 */         buildAttributeTranslations(rs, attribute);
/*      */ 
/*  880 */         attribute.setIsVisible(true);
/*      */ 
/*  882 */         vecAttributes.add(attribute);
/*      */       }
/*      */ 
/*  885 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  889 */       throw new SQLStatementException(sSQL, e);
/*      */     }
/*      */     finally
/*      */     {
/*  894 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  898 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  902 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  907 */     return vecAttributes;
/*      */   }
/*      */ 
/*      */   public Set<Long> getAttributeIdsForAutoCompleteIndex()
/*      */     throws Bn2Exception
/*      */   {
/*  916 */     return getAttributeIdsForAutoCompleteIndex(null);
/*      */   }
/*      */ 
/*      */   public Set<Long> getAttributeIdsForAutoCompleteIndex(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  926 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/*  928 */     Set autoCompleteAttributeIds = new HashSet();
/*      */ 
/*  930 */     if (transaction == null)
/*      */     {
/*  932 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  937 */       Connection con = transaction.getConnection();
/*      */ 
/*  945 */       String sSQL = "SELECT Id FROM Attribute WHERE IsAutoComplete=1 OR (IsKeywordSearchable=1 AND AttributeTypeId <> 11)";
/*      */ 
/*  950 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/*  952 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  954 */       while (rs.next())
/*      */       {
/*  956 */         long lAttributeId = rs.getLong("Id");
/*  957 */         autoCompleteAttributeIds.add(Long.valueOf(lAttributeId));
/*      */       }
/*      */ 
/*  960 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  964 */       throw new Bn2Exception("SQL Exception whilst getting attributes from the database", e);
/*      */     }
/*      */     finally
/*      */     {
/*  969 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  973 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  977 */           this.m_logger.error("SQL Exception whilst trying to close connection", sqle);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  982 */     return autoCompleteAttributeIds;
/*      */   }
/*      */ 
/*      */   public void deleteAttribute(DBTransaction a_dbTransaction, long a_lAttributeId)
/*      */     throws Bn2Exception
/*      */   {
/* 1005 */     String ksMethodName = "deleteAttribute";
/*      */     try
/*      */     {
/* 1013 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/* 1016 */       Attribute attr = getAttribute(a_dbTransaction, a_lAttributeId);
/*      */ 
/* 1018 */       if (attr.getTypeId() == 7L)
/*      */       {
/* 1020 */         this.m_categoryManager.deleteCategoryTree(a_dbTransaction, attr.getTreeId());
/*      */       }
/*      */ 
/* 1023 */       if (!attr.getStatic())
/*      */       {
/* 1025 */         String sSQL = "SELECT attrrule.Id ruleId, attrrule.RuleName ruleName, attrrule.Enabled FROM SendEmailDateRule attrrule WHERE attrrule.AttributeId=? ";
/*      */ 
/* 1034 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 1035 */         psql.setLong(1, a_lAttributeId);
/* 1036 */         ResultSet rs = psql.executeQuery();
/*      */ 
/* 1038 */         while (rs.next())
/*      */         {
/* 1041 */           sSQL = "DELETE FROM GroupEmailRule WHERE RuleId=?";
/* 1042 */           PreparedStatement psql2 = con.prepareStatement(sSQL);
/* 1043 */           psql2.setLong(1, rs.getLong("ruleId"));
/* 1044 */           psql2.executeUpdate();
/* 1045 */           psql2.close();
/*      */ 
/* 1048 */           sSQL = "DELETE FROM SendEmailDateRule WHERE Id=?";
/* 1049 */           psql2 = con.prepareStatement(sSQL);
/* 1050 */           psql2.setLong(1, rs.getLong("ruleId"));
/* 1051 */           psql2.executeUpdate();
/* 1052 */           psql2.close();
/*      */         }
/* 1054 */         psql.close();
/*      */ 
/* 1057 */         sSQL = "DELETE FROM ChangeAttributeValueDateRule WHERE AttributeId=? OR ChangeAttributeId=?";
/* 1058 */         psql = con.prepareStatement(sSQL);
/* 1059 */         psql.setLong(1, a_lAttributeId);
/* 1060 */         psql.setLong(2, a_lAttributeId);
/* 1061 */         psql.executeUpdate();
/* 1062 */         psql.close();
/*      */ 
/* 1065 */         String[] aSQL = { "DELETE FROM FilterAssetAttributeValue WHERE AttributeId=?", "DELETE FROM FilterListAttributeValue WHERE ListAttributeValueId IN (SELECT Id FROM ListAttributeValue WHERE AttributeId=?)", "DELETE FROM AssetListAttributeValue WHERE ListAttributeValueId IN (SELECT Id FROM ListAttributeValue WHERE AttributeId=?)", "DELETE FROM TranslatedListAttributeValue WHERE ListAttributeValueId IN (SELECT Id FROM ListAttributeValue WHERE AttributeId=?)", "DELETE FROM ListAttributeValue WHERE AttributeId=?", "UPDATE AssetEntity SET MatchOnAttributeId = NULL WHERE MatchOnAttributeId = ?", "DELETE FROM AttributeVisibleToGroup WHERE AttributeId=?", "DELETE FROM GroupAttributeExclusion WHERE AttributeId=?", "DELETE FROM AssetEntityAttribute WHERE AttributeId=?", "DELETE FROM SortAttribute WHERE AttributeId=?", "DELETE FROM DisplayAttribute WHERE AttributeId=?", "DELETE FROM EmbeddedDataMapping WHERE AttributeId=?", "DELETE FROM TranslatedAttribute WHERE AttributeId=?", "DELETE FROM Attribute WHERE Id=?" };
/*      */ 
/* 1081 */         for (int i = 0; i < aSQL.length; i++)
/*      */         {
/* 1083 */           sSQL = aSQL[i];
/* 1084 */           psql = con.prepareStatement(sSQL);
/* 1085 */           psql.setLong(1, a_lAttributeId);
/* 1086 */           int rows = psql.executeUpdate();
/* 1087 */           if (rows > 0)
/*      */           {
/* 1089 */             this.m_logger.debug("Deleted " + rows + " rows with SQL: " + sSQL);
/*      */           }
/* 1091 */           psql.close();
/*      */         }
/*      */ 
/* 1095 */         optimiseAttributeSequences(a_dbTransaction);
/*      */ 
/* 1098 */         invalidateAttributeCache(a_lAttributeId);
/*      */ 
/* 1102 */         if (attr.getIsNameAttribute())
/*      */         {
/* 1105 */           sSQL = "SELECT Id FROM Attribute WHERE NameAttribute=1";
/* 1106 */           psql = con.prepareStatement(sSQL);
/* 1107 */           rs = psql.executeQuery();
/* 1108 */           boolean bHasName = rs.next();
/* 1109 */           psql.close();
/*      */ 
/* 1111 */           if (!bHasName)
/*      */           {
/* 1113 */             Attribute newNameAttribute = (Attribute)getAttributes(a_dbTransaction, -1L).firstElement();
/* 1114 */             Vector vecTemp = new Vector();
/* 1115 */             vecTemp.add(new Long(newNameAttribute.getId()));
/* 1116 */             this.m_fileAssetManager.setAssetNamingAttributes(a_dbTransaction, vecTemp);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1125 */         this.m_attributeStorageManager.deleteStorageForAttribute(a_dbTransaction, attr);
/*      */       }
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 1130 */       this.m_logger.error("AttributeManager.deleteAttribute - " + sqe);
/* 1131 */       throw new Bn2Exception("AttributeManager.deleteAttribute", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteSortAttribute(DBTransaction a_dbTransaction, long a_lAttributeId, long a_lSortAreaId)
/*      */     throws Bn2Exception
/*      */   {
/* 1154 */     String ksMethodName = "deleteSortAttribute";
/*      */ 
/* 1156 */     Connection con = null;
/* 1157 */     PreparedStatement psql = null;
/* 1158 */     String sSQL = null;
/*      */     try
/*      */     {
/* 1163 */       sSQL = "DELETE FROM SortAttribute WHERE AttributeId=? AND SortAreaId=?";
/* 1164 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1167 */       psql = con.prepareStatement(sSQL);
/* 1168 */       psql.setLong(1, a_lAttributeId);
/* 1169 */       psql.setLong(2, a_lSortAreaId);
/* 1170 */       psql.executeUpdate();
/* 1171 */       psql.close();
/*      */ 
/* 1173 */       refreshSortCaches(a_lSortAreaId);
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 1177 */       this.m_logger.error("AttributeManager.deleteSortAttribute - " + sqe);
/* 1178 */       throw new Bn2Exception("AttributeManager.deleteSortAttribute", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public long saveAttribute(DBTransaction a_dbTransaction, Attribute a_attribute)
/*      */     throws Bn2Exception
/*      */   {
/* 1211 */     String ksMethodName = "saveAttribute";
/*      */ 
/* 1213 */     long lAttId = a_attribute.getId();
/*      */ 
/* 1220 */     if ((a_attribute.isAutoComplete()) && (!a_attribute.isAutoCompleteAllowed()))
/*      */     {
/* 1222 */       String sAttributeDescription = "Attribute" + a_attribute.getId();
/*      */ 
/* 1225 */       this.m_logger.error(sAttributeDescription + " (" + a_attribute.getLabel() + ") had autocomplete set but this is not allowed.  " + "Type=" + a_attribute.getTypeId() + ".  " + "Resetting AutoComplete to false before saving to DB.");
/*      */ 
/* 1229 */       a_attribute.setAutoComplete(false);
/*      */     }
/*      */     boolean bNew;
/*      */     try
/*      */     {
/* 1235 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/* 1238 */       bNew = lAttId <= 0L;
/* 1239 */       if (!bNew)
/*      */       {
/* 1241 */         String sSQL = "UPDATE Attribute SET AttributeTypeId=?, SequenceNumber=?, Label=?, IsKeywordSearchable=?, IsMandatory=?, IsMandatoryBulkUpload=?, IsSearchField=?, IsSearchBuilderField=?, DefaultValue=?, ValueIfNotVisible=?, IsStatic=?, StaticFieldName=?, IsReadOnly=?, MaxDisplayLength=?, TreeId=?, Highlight=?, HelpText=?, NameAttribute=?, IsDescriptionAttribute=?, DefaultKeywordFilter=?, OnChangeScript=?, IsHtml=?, IsRequiredForCompleteness=?, RequiresTranslation=?, DisplayName=?, BaseUrl=?, AltText=?, HasSearchTokens=?, TokenDelimiterRegex=?, Height=?, Width=?, MaxSize=?, IsFiltered=?, Seed=?, IncrementAmount=?, Prefix=?, IsExtendableList=?, InputMask=?, GetDataFromChildren=?, ShowOnChild=?, IncludeInSearchForChild=?, HideForSort=?, IsAutoComplete=?, PluginClass=?, PluginParamsAttributeIds=?, ValueColumnName=?, ShowOnDownload=?, MaxDecimalPlaces=?, MinDecimalPlaces=?, Hidden=? WHERE Id=?";
/*      */ 
/* 1294 */         PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/* 1296 */         int iField = 1;
/* 1297 */         DBUtil.setFieldIdOrNull(psql, iField++, a_attribute.getTypeId());
/* 1298 */         psql.setInt(iField++, a_attribute.getSequence());
/* 1299 */         psql.setString(iField++, a_attribute.getLabel());
/* 1300 */         psql.setBoolean(iField++, a_attribute.getKeywordSearchable());
/* 1301 */         psql.setBoolean(iField++, a_attribute.isMandatory());
/* 1302 */         psql.setBoolean(iField++, a_attribute.isMandatoryBulkUpload());
/* 1303 */         psql.setBoolean(iField++, a_attribute.isSearchField());
/* 1304 */         psql.setBoolean(iField++, a_attribute.isSearchBuilderField());
/* 1305 */         psql.setString(iField++, a_attribute.getDefaultValue());
/* 1306 */         psql.setString(iField++, a_attribute.getValueIfNotVisible());
/* 1307 */         psql.setBoolean(iField++, a_attribute.getStatic());
/* 1308 */         psql.setString(iField++, a_attribute.getFieldName());
/* 1309 */         psql.setBoolean(iField++, a_attribute.getReadOnly());
/* 1310 */         psql.setInt(iField++, a_attribute.getMaxDisplayLength());
/* 1311 */         psql.setLong(iField++, a_attribute.getTreeId());
/* 1312 */         psql.setBoolean(iField++, a_attribute.getHighlight());
/* 1313 */         psql.setString(iField++, a_attribute.getHelpText());
/* 1314 */         psql.setBoolean(iField++, a_attribute.getIsNameAttribute());
/* 1315 */         psql.setBoolean(iField++, a_attribute.getIsDescriptionAttribute());
/* 1316 */         psql.setString(iField++, a_attribute.getDefaultKeywordFilter());
/* 1317 */         psql.setString(iField++, a_attribute.getOnChangeScript());
/* 1318 */         psql.setBoolean(iField++, a_attribute.getIsHtml());
/* 1319 */         psql.setBoolean(iField++, a_attribute.isRequiredForCompleteness());
/* 1320 */         psql.setBoolean(iField++, a_attribute.getRequiresTranslation());
/* 1321 */         psql.setString(iField++, a_attribute.getDisplayName());
/* 1322 */         psql.setString(iField++, a_attribute.getBaseUrl());
/* 1323 */         psql.setString(iField++, a_attribute.getAltText());
/* 1324 */         psql.setBoolean(iField++, a_attribute.getHasSearchTokens());
/* 1325 */         psql.setString(iField++, a_attribute.getTokenDelimiterRegex());
/* 1326 */         psql.setInt(iField++, a_attribute.getHeight());
/* 1327 */         psql.setInt(iField++, a_attribute.getWidth());
/* 1328 */         psql.setInt(iField++, a_attribute.getMaxSize());
/* 1329 */         psql.setBoolean(iField++, a_attribute.isFiltered());
/* 1330 */         psql.setLong(iField++, a_attribute.getSeed());
/* 1331 */         psql.setInt(iField++, a_attribute.getIncrement());
/* 1332 */         psql.setString(iField++, a_attribute.getPrefix());
/* 1333 */         psql.setBoolean(iField++, a_attribute.getIsExtendableList());
/* 1334 */         psql.setString(iField++, a_attribute.getInputMask());
/* 1335 */         psql.setBoolean(iField++, a_attribute.getDataFromChildren());
/* 1336 */         psql.setBoolean(iField++, a_attribute.getShowOnChild());
/* 1337 */         psql.setBoolean(iField++, a_attribute.getIncludeInSearchForChild());
/* 1338 */         psql.setBoolean(iField++, a_attribute.getHideForSort());
/* 1339 */         psql.setBoolean(iField++, a_attribute.isAutoComplete());
/* 1340 */         psql.setString(iField++, a_attribute.getPluginClass());
/* 1341 */         psql.setString(iField++, a_attribute.getAttributeIdsForPluginParams());
/* 1342 */         psql.setString(iField++, a_attribute.getValueColumnName());
/* 1343 */         psql.setBoolean(iField++, a_attribute.getShowOnDownload());
/* 1344 */         psql.setInt(iField++, a_attribute.getMaxDecimalPlaces());
/* 1345 */         psql.setInt(iField++, a_attribute.getMinDecimalPlaces());
/* 1346 */         psql.setBoolean(iField++, a_attribute.isHidden());
/*      */ 
/* 1348 */         psql.setLong(iField++, lAttId);
/* 1349 */         psql.executeUpdate();
/* 1350 */         psql.close();
/*      */       }
/*      */       else
/*      */       {
/* 1355 */         AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 1356 */         long lNewId = 0L;
/*      */ 
/* 1359 */         String sSQL = "INSERT INTO Attribute (";
/*      */ 
/* 1361 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1363 */           lNewId = sqlGenerator.getUniqueId(con, "AttributeSequence");
/* 1364 */           sSQL = sSQL + "Id, ";
/*      */         }
/*      */ 
/* 1367 */         sSQL = sSQL + "AttributeTypeId, SequenceNumber, Label, IsKeywordSearchable, IsMandatory, IsMandatoryBulkUpload, IsSearchField, IsSearchBuilderField, DefaultValue, ValueIfNotVisible, IsStatic, StaticFieldName, MaxDisplayLength, TreeId, Highlight, HelpText, NameAttribute, IsDescriptionAttribute, DefaultKeywordFilter, OnChangeScript, IsHtml, DisplayName, BaseUrl, AltText, HasSearchTokens, TokenDelimiterRegex, Height, Width, MaxSize, IsFiltered, Seed, IncrementAmount, Prefix, IsExtendableList, InputMask, ShowOnChild, IncludeInSearchForChild, IsRequiredForCompleteness, GetDataFromChildren, HideForSort, IsAutoComplete, PluginClass, PluginParamsAttributeIds, ValueColumnName, ShowOnDownload, MinDecimalPlaces, MaxDecimalPlaces, Hidden) VALUES (";
/*      */ 
/* 1369 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1371 */           sSQL = sSQL + "?,";
/*      */         }
/*      */ 
/* 1374 */         sSQL = sSQL + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */ 
/* 1376 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 1377 */         int iField = 1;
/*      */ 
/* 1379 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1381 */           psql.setLong(iField++, lNewId);
/*      */         }
/*      */ 
/* 1384 */         DBUtil.setFieldIdOrNull(psql, iField++, a_attribute.getTypeId());
/* 1385 */         psql.setInt(iField++, a_attribute.getSequence());
/* 1386 */         psql.setString(iField++, a_attribute.getLabel());
/* 1387 */         psql.setBoolean(iField++, a_attribute.getKeywordSearchable());
/* 1388 */         psql.setBoolean(iField++, a_attribute.isMandatory());
/* 1389 */         psql.setBoolean(iField++, a_attribute.isMandatoryBulkUpload());
/* 1390 */         psql.setBoolean(iField++, a_attribute.isSearchField());
/* 1391 */         psql.setBoolean(iField++, a_attribute.isSearchBuilderField());
/* 1392 */         psql.setString(iField++, a_attribute.getDefaultValue());
/* 1393 */         psql.setString(iField++, a_attribute.getValueIfNotVisible());
/* 1394 */         psql.setBoolean(iField++, a_attribute.getStatic());
/* 1395 */         psql.setString(iField++, a_attribute.getFieldName());
/* 1396 */         psql.setInt(iField++, a_attribute.getMaxDisplayLength());
/* 1397 */         psql.setLong(iField++, a_attribute.getTreeId());
/* 1398 */         psql.setBoolean(iField++, a_attribute.getHighlight());
/* 1399 */         psql.setString(iField++, a_attribute.getHelpText());
/* 1400 */         psql.setBoolean(iField++, a_attribute.getIsNameAttribute());
/* 1401 */         psql.setBoolean(iField++, a_attribute.getIsDescriptionAttribute());
/* 1402 */         psql.setString(iField++, a_attribute.getDefaultKeywordFilter());
/* 1403 */         psql.setString(iField++, a_attribute.getOnChangeScript());
/* 1404 */         psql.setBoolean(iField++, a_attribute.getIsHtml());
/* 1405 */         psql.setString(iField++, a_attribute.getDisplayName());
/* 1406 */         psql.setString(iField++, a_attribute.getBaseUrl());
/* 1407 */         psql.setString(iField++, a_attribute.getAltText());
/* 1408 */         psql.setBoolean(iField++, a_attribute.getHasSearchTokens());
/* 1409 */         psql.setString(iField++, a_attribute.getTokenDelimiterRegex());
/* 1410 */         psql.setInt(iField++, a_attribute.getHeight());
/* 1411 */         psql.setInt(iField++, a_attribute.getWidth());
/* 1412 */         psql.setInt(iField++, a_attribute.getMaxSize());
/* 1413 */         psql.setBoolean(iField++, a_attribute.isFiltered());
/* 1414 */         psql.setLong(iField++, a_attribute.getSeed());
/* 1415 */         psql.setInt(iField++, a_attribute.getIncrement());
/* 1416 */         psql.setString(iField++, a_attribute.getPrefix());
/* 1417 */         psql.setBoolean(iField++, a_attribute.getIsExtendableList());
/* 1418 */         psql.setString(iField++, a_attribute.getInputMask());
/* 1419 */         psql.setBoolean(iField++, a_attribute.getShowOnChild());
/* 1420 */         psql.setBoolean(iField++, a_attribute.getIncludeInSearchForChild());
/* 1421 */         psql.setBoolean(iField++, a_attribute.isRequiredForCompleteness());
/* 1422 */         psql.setBoolean(iField++, a_attribute.getDataFromChildren());
/* 1423 */         psql.setBoolean(iField++, a_attribute.getHideForSort());
/* 1424 */         psql.setBoolean(iField++, a_attribute.isAutoComplete());
/* 1425 */         psql.setString(iField++, a_attribute.getPluginClass());
/* 1426 */         psql.setString(iField++, a_attribute.getAttributeIdsForPluginParams());
/* 1427 */         psql.setString(iField++, a_attribute.getValueColumnName());
/* 1428 */         psql.setBoolean(iField++, a_attribute.getShowOnDownload());
/* 1429 */         psql.setInt(iField++, a_attribute.getMinDecimalPlaces());
/* 1430 */         psql.setInt(iField++, a_attribute.getMaxDecimalPlaces());
/* 1431 */         psql.setBoolean(iField++, a_attribute.isHidden());
/*      */ 
/* 1433 */         psql.executeUpdate();
/*      */ 
/* 1435 */         if (sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1437 */           lNewId = sqlGenerator.getUniqueId(con, "Attribute");
/*      */         }
/* 1439 */         lAttId = lNewId;
/* 1440 */         a_attribute.setId(lAttId);
/*      */ 
/* 1442 */         psql.close();
/*      */       }
/*      */ 
/* 1448 */       Iterator itTranslations = a_attribute.getTranslations().iterator();
/* 1449 */       while (itTranslations.hasNext())
/*      */       {
/* 1451 */         Attribute.Translation translation = (Attribute.Translation)itTranslations.next();
/* 1452 */         if (translation.getLanguage().getId() > 0L)
/*      */         {
/* 1454 */           saveAttributeTranslation(a_dbTransaction, translation);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1459 */       invalidateAttributeCache(lAttId);
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 1463 */       this.m_logger.error("AttributeManager.saveAttribute - " + sqe);
/* 1464 */       throw new Bn2Exception("AttributeManager.saveAttribute", sqe);
/*      */     }
/*      */ 
/* 1467 */     if (bNew)
/*      */     {
/*      */       try
/*      */       {
/* 1478 */         this.m_attributeStorageManager.createStorageForAttribute(a_dbTransaction, a_attribute);
/*      */       }
/*      */       catch (Bn2Exception e)
/*      */       {
/*      */         try
/*      */         {
/* 1484 */           Connection con = a_dbTransaction.getConnection();
/* 1485 */           PreparedStatement psql = con.prepareStatement("DELETE FROM TranslatedAttribute WHERE AttributeId=?");
/*      */ 
/* 1487 */           psql.setLong(1, a_attribute.getId());
/* 1488 */           psql.executeUpdate();
/* 1489 */           psql.close();
/*      */ 
/* 1491 */           psql = con.prepareStatement("DELETE FROM Attribute WHERE Id=?");
/*      */ 
/* 1493 */           psql.setLong(1, a_attribute.getId());
/* 1494 */           psql.executeUpdate();
/* 1495 */           psql.close();
/*      */ 
/* 1502 */           con.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1506 */           this.m_logger.error("Couldn't roll back by deleting rows from Attribute and TranslatedAttribute tables after createStorageForAttribute() failed", sqle);
/*      */         }
/*      */ 
/* 1509 */         throw e;
/*      */       }
/*      */     }
/*      */ 
/* 1513 */     return lAttId;
/*      */   }
/*      */ 
/*      */   public void saveAttributeTranslation(DBTransaction a_dbTransaction, Attribute.Translation a_translation)
/*      */     throws Bn2Exception
/*      */   {
/* 1524 */     String ksMethodName = "saveAttributeTranslation";
/*      */ 
/* 1526 */     Connection con = null;
/* 1527 */     PreparedStatement psql = null;
/* 1528 */     String sSQL = null;
/*      */     try
/*      */     {
/* 1532 */       con = a_dbTransaction.getConnection();
/* 1533 */       int iCol = 1;
/*      */ 
/* 1535 */       sSQL = "DELETE FROM TranslatedAttribute WHERE AttributeId=? AND LanguageId=?";
/* 1536 */       psql = con.prepareStatement(sSQL);
/* 1537 */       psql.setLong(iCol++, a_translation.getAttributeId());
/* 1538 */       psql.setLong(iCol++, a_translation.getLanguage().getId());
/* 1539 */       psql.executeUpdate();
/* 1540 */       psql.close();
/*      */ 
/* 1542 */       iCol = 1;
/* 1543 */       sSQL = "INSERT INTO TranslatedAttribute (Label,DefaultValue,ValueIfNotVisible,HelpText,AltText,DisplayName,AttributeId,LanguageId,InputMask) VALUES (?,?,?,?,?,?,?,?,?)";
/* 1544 */       psql = con.prepareStatement(sSQL);
/* 1545 */       psql.setString(iCol++, a_translation.getLabel());
/* 1546 */       psql.setString(iCol++, a_translation.getDefaultValue());
/* 1547 */       psql.setString(iCol++, a_translation.getValueIfNotVisible());
/* 1548 */       psql.setString(iCol++, a_translation.getHelpText());
/* 1549 */       psql.setString(iCol++, a_translation.getAltText());
/* 1550 */       psql.setString(iCol++, a_translation.getDisplayName());
/* 1551 */       psql.setLong(iCol++, a_translation.getAttributeId());
/* 1552 */       psql.setLong(iCol++, a_translation.getLanguage().getId());
/* 1553 */       psql.setString(iCol++, a_translation.getInputMask());
/* 1554 */       psql.executeUpdate();
/* 1555 */       psql.close();
/*      */ 
/* 1557 */       invalidateAttributeCache(a_translation.getAttributeId());
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 1561 */       throw new Bn2Exception("AttributeManager.saveAttributeTranslation", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void moveSortAttribute(DBTransaction a_dbTransaction, long a_lAttributeId, long a_lSortArea, boolean a_bMoveUp)
/*      */     throws Bn2Exception
/*      */   {
/* 1583 */     Vector vecSortAttributes = getSortAttributeList(a_dbTransaction, a_lSortArea);
/* 1584 */     SortAttribute attBeingMoved = null;
/* 1585 */     SortAttribute attAffectedAttribute = null;
/*      */ 
/* 1587 */     for (int i = 0; i < vecSortAttributes.size(); i++)
/*      */     {
/* 1589 */       attBeingMoved = (SortAttribute)vecSortAttributes.elementAt(i);
/* 1590 */       if (attBeingMoved.getAttribute().getId() != a_lAttributeId)
/*      */         continue;
/* 1592 */       if (a_bMoveUp)
/*      */       {
/* 1594 */         attAffectedAttribute = (SortAttribute)vecSortAttributes.elementAt(i - 1); break;
/*      */       }
/*      */ 
/* 1598 */       attAffectedAttribute = (SortAttribute)vecSortAttributes.elementAt(i + 1);
/*      */ 
/* 1600 */       break;
/*      */     }
/*      */ 
/* 1605 */     int iTempOrder = attBeingMoved.getOrder();
/* 1606 */     attBeingMoved.setOrder(attAffectedAttribute.getOrder());
/* 1607 */     attAffectedAttribute.setOrder(iTempOrder);
/*      */ 
/* 1610 */     saveSortAttribute(a_dbTransaction, attBeingMoved, false);
/* 1611 */     saveSortAttribute(a_dbTransaction, attAffectedAttribute, false);
/*      */   }
/*      */ 
/*      */   public void moveDisplayAttribute(DBTransaction a_dbTransaction, long a_lAttributeId, long a_lGroupId, boolean a_bMoveUp)
/*      */     throws Bn2Exception
/*      */   {
/* 1637 */     Vector vecDisplayAttributes = getDisplayAttributes(a_dbTransaction, a_lGroupId);
/* 1638 */     DisplayAttribute attBeingMoved = null;
/* 1639 */     DisplayAttribute attAffectedAttribute = null;
/*      */ 
/* 1641 */     if (vecDisplayAttributes != null)
/*      */     {
/* 1643 */       for (int i = 0; i < vecDisplayAttributes.size(); i++)
/*      */       {
/* 1645 */         attBeingMoved = (DisplayAttribute)vecDisplayAttributes.elementAt(i);
/* 1646 */         if (attBeingMoved.getAttribute().getId() != a_lAttributeId)
/*      */           continue;
/* 1648 */         if (a_bMoveUp)
/*      */         {
/* 1650 */           attAffectedAttribute = (DisplayAttribute)vecDisplayAttributes.elementAt(i - 1); break;
/*      */         }
/*      */ 
/* 1654 */         attAffectedAttribute = (DisplayAttribute)vecDisplayAttributes.elementAt(i + 1);
/*      */ 
/* 1656 */         break;
/*      */       }
/*      */ 
/* 1661 */       int iTempOrder = attBeingMoved.getSequenceNumber();
/* 1662 */       attBeingMoved.setSequenceNumber(attAffectedAttribute.getSequenceNumber());
/* 1663 */       attAffectedAttribute.setSequenceNumber(iTempOrder);
/*      */ 
/* 1666 */       saveDisplayAttribute(a_dbTransaction, attBeingMoved, false);
/* 1667 */       saveDisplayAttribute(a_dbTransaction, attAffectedAttribute, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void moveEmbeddedDataMapping(DBTransaction a_dbTransaction, long a_lAttributeId, long a_lEmbeddedDataValueId, long a_lDirectionId, boolean a_bMoveUp)
/*      */     throws Bn2Exception
/*      */   {
/* 1691 */     Vector vecEmbeddedDataMappings = getEmbeddedDataMappings(a_dbTransaction, -1L);
/* 1692 */     EmbeddedDataMapping mappingBeingMoved = null;
/* 1693 */     EmbeddedDataMapping mappingAffected = null;
/*      */ 
/* 1695 */     for (int i = 0; i < vecEmbeddedDataMappings.size(); i++)
/*      */     {
/* 1697 */       mappingBeingMoved = (EmbeddedDataMapping)vecEmbeddedDataMappings.elementAt(i);
/*      */ 
/* 1699 */       if ((mappingBeingMoved.getAttribute().getId() != a_lAttributeId) || (mappingBeingMoved.getValue().getId() != a_lEmbeddedDataValueId) || (mappingBeingMoved.getDirection().getId() != a_lDirectionId))
/*      */       {
/*      */         continue;
/*      */       }
/* 1703 */       if (a_bMoveUp)
/*      */       {
/* 1705 */         mappingAffected = (EmbeddedDataMapping)vecEmbeddedDataMappings.elementAt(i - 1); break;
/*      */       }
/*      */ 
/* 1709 */       mappingAffected = (EmbeddedDataMapping)vecEmbeddedDataMappings.elementAt(i + 1);
/*      */ 
/* 1711 */       break;
/*      */     }
/*      */ 
/* 1716 */     int iTempOrder = mappingBeingMoved.getSequence();
/* 1717 */     mappingBeingMoved.setSequence(mappingAffected.getSequence());
/* 1718 */     mappingAffected.setSequence(iTempOrder);
/*      */ 
/* 1721 */     saveEmbeddedDataMapping(a_dbTransaction, mappingBeingMoved, mappingBeingMoved.getValue().getType().getId(), mappingBeingMoved);
/* 1722 */     saveEmbeddedDataMapping(a_dbTransaction, mappingAffected, mappingAffected.getValue().getType().getId(), mappingAffected);
/*      */   }
/*      */ 
/*      */   public void deleteDisplayAttribute(DBTransaction a_dbTransaction, long a_lAttributeId, long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 1744 */     String ksMethodName = "deleteDisplayAttribute";
/*      */ 
/* 1746 */     Connection con = null;
/* 1747 */     PreparedStatement psql = null;
/* 1748 */     String sSQL = null;
/*      */     try
/*      */     {
/* 1753 */       sSQL = "DELETE FROM DisplayAttribute WHERE AttributeId=? AND DisplayAttributeGroupId=?";
/* 1754 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1757 */       psql = con.prepareStatement(sSQL);
/* 1758 */       psql.setLong(1, a_lAttributeId);
/* 1759 */       psql.setLong(2, a_lGroupId);
/* 1760 */       psql.executeUpdate();
/* 1761 */       psql.close();
/*      */ 
/* 1763 */       refreshDisplayCache(a_lGroupId);
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 1767 */       this.m_logger.error("AttributeManager.deleteDisplayAttribute - " + sqe);
/* 1768 */       throw new Bn2Exception("AttributeManager.deleteDisplayAttribute", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getSortAttributeListBySearchOrder()
/*      */     throws Bn2Exception
/*      */   {
/* 1789 */     return getSortAttributeList(null, 1L);
/*      */   }
/*      */ 
/*      */   public Vector getSortAttributeListBySearchOrder(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 1808 */     return getSortAttributeList(a_dbTransaction, 1L);
/*      */   }
/*      */ 
/*      */   public Vector getSortAttributeListByBrowseOrder()
/*      */     throws Bn2Exception
/*      */   {
/* 1827 */     return getSortAttributeList(null, 2L);
/*      */   }
/*      */ 
/*      */   public Vector getSortAttributeListByBrowseOrder(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 1846 */     return getSortAttributeList(a_dbTransaction, 2L);
/*      */   }
/*      */ 
/*      */   public Vector getSortAttributeList(DBTransaction a_dbTransaction, long a_lSortArea)
/*      */     throws Bn2Exception
/*      */   {
/* 1866 */     String ksMethodName = "getSortAttributeList";
/*      */ 
/* 1868 */     Vector vecSortAttributes = null;
/*      */ 
/* 1871 */     Long longKey = new Long(a_lSortArea);
/*      */ 
/* 1873 */     if (this.m_hmSortLists.containsKey(longKey))
/*      */     {
/* 1876 */       vecSortAttributes = (Vector)this.m_hmSortLists.get(longKey);
/*      */     }
/*      */     else
/*      */     {
/* 1881 */       DBTransaction transaction = null;
/* 1882 */       Connection con = null;
/* 1883 */       PreparedStatement psql = null;
/* 1884 */       String sSQL = null;
/* 1885 */       vecSortAttributes = new Vector();
/*      */       try
/*      */       {
/* 1889 */         if (a_dbTransaction == null)
/*      */         {
/* 1891 */           transaction = this.m_transactionManager.getNewTransaction();
/*      */         }
/*      */         else
/*      */         {
/* 1895 */           transaction = a_dbTransaction;
/*      */         }
/* 1897 */         con = transaction.getConnection();
/*      */ 
/* 1899 */         sSQL = "SELECT a.Id aId, a.IsStatic, a.IsReadOnly, a.StaticFieldName, a.Label, a.IsMandatory, a.IsMandatoryBulkUpload, a.IsKeywordSearchable, a.AttributeTypeId, a.IsSearchField, a.IsSearchBuilderField, a.DefaultValue, a.NameAttribute, a.IsDescriptionAttribute, a.ValueIfNotVisible, a.SequenceNumber, a.MaxDisplayLength, a.Highlight, a.HelpText, a.TreeId, a.OnChangeScript, a.DefaultKeywordFilter, a.IsHtml, a.IsRequiredForCompleteness, a.RequiresTranslation, a.DisplayName, a.BaseUrl, a.AltText, a.HasSearchTokens, a.TokenDelimiterRegex, a.Height, a.Width, a.MaxSize, a.IsFiltered, a.Seed, a.IncrementAmount, a.Prefix, a.IsExtendableList, a.InputMask, a.ShowOnChild, a.IncludeInSearchForChild, a.GetDataFromChildren, a.HideForSort, a.IsAutoComplete, a.PluginClass, a.PluginParamsAttributeIds, a.ValueColumnName, a.ShowOnDownload, a.MaxDecimalPlaces, a.MinDecimalPlaces, a.Hidden ,l.Id lId, l.Name lName, l.NativeName lNativeName, l.Code lCode, ta.LanguageId taLanguageId, ta.Label taLabel, ta.DefaultValue taDefaultValue, ta.ValueIfNotVisible taValueIfNotVisible, ta.HelpText taHelpText, ta.AltText taAltText, ta.InputMask taInputMask, ta.DisplayName taDisplayName, sa.Sequence, sa.Reverse, sa.SortAreaId, sa.Type FROM SortAttribute sa LEFT JOIN Attribute a ON sa.AttributeId=a.Id LEFT JOIN TranslatedAttribute ta ON a.Id = ta.AttributeId LEFT JOIN Language l ON l.Id = ta.LanguageId WHERE sa.SortAreaId=? AND (a.AttributeTypeId<>? OR " + SQLGenerator.getInstance().getNullCheckStatement("a.AttributeTypeId") + ") " + "ORDER BY sa.Sequence";
/*      */ 
/* 1913 */         psql = con.prepareStatement(sSQL, 1004, 1007);
/* 1914 */         psql.setLong(1, a_lSortArea);
/* 1915 */         psql.setLong(2, 10L);
/* 1916 */         ResultSet rs = psql.executeQuery();
/*      */ 
/* 1918 */         while (rs.next())
/*      */         {
/* 1920 */           Attribute attribute = AttributeDBUtil.buildAttribute(rs);
/* 1921 */           SortAttribute sortAttribute = new SortAttribute();
/* 1922 */           sortAttribute.setAttribute(attribute);
/* 1923 */           sortAttribute.setOrder(rs.getInt("Sequence"));
/* 1924 */           sortAttribute.setReverse(rs.getBoolean("Reverse"));
/* 1925 */           sortAttribute.setSortAreaId(rs.getLong("SortAreaId"));
/* 1926 */           sortAttribute.setType(rs.getInt("Type"));
/*      */ 
/* 1929 */           buildAttributeTranslations(rs, attribute);
/*      */ 
/* 1931 */           vecSortAttributes.add(sortAttribute);
/*      */         }
/*      */ 
/* 1935 */         synchronized (this.m_oSortLock)
/*      */         {
/* 1937 */           this.m_hmSortLists.put(longKey, vecSortAttributes);
/*      */         }
/*      */ 
/* 1940 */         psql.close();
/*      */       }
/*      */       catch (SQLException sqe)
/*      */       {
/* 1944 */         this.m_logger.error("AttributeManager.getSortAttributeList - " + sqe);
/* 1945 */         throw new Bn2Exception("AttributeManager.getSortAttributeList", sqe);
/*      */       }
/*      */       finally
/*      */       {
/* 1950 */         if ((transaction != null) && (a_dbTransaction == null))
/*      */         {
/*      */           try
/*      */           {
/* 1954 */             transaction.commit();
/*      */           }
/*      */           catch (SQLException sqle)
/*      */           {
/* 1958 */             this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1964 */     return vecSortAttributes;
/*      */   }
/*      */ 
/*      */   public SortAttribute getSortAttribute(DBTransaction a_dbTransaction, long a_lAttributeId, long a_lSortAreaId)
/*      */     throws Bn2Exception
/*      */   {
/* 1984 */     String ksMethodName = "getSortAttribute";
/* 1985 */     SortAttribute sortAttribute = null;
/*      */ 
/* 1988 */     DBTransaction transaction = null;
/* 1989 */     Connection con = null;
/* 1990 */     PreparedStatement psql = null;
/* 1991 */     String sSQL = null;
/*      */     try
/*      */     {
/* 1995 */       if (a_dbTransaction == null)
/*      */       {
/* 1997 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */       else
/*      */       {
/* 2001 */         transaction = a_dbTransaction;
/*      */       }
/* 2003 */       con = transaction.getConnection();
/* 2004 */       sSQL = "SELECT a.Id aId, a.IsStatic, a.IsReadOnly, a.StaticFieldName, a.Label, a.IsMandatory, a.IsMandatoryBulkUpload, a.IsKeywordSearchable, a.AttributeTypeId, a.IsSearchField, a.IsSearchBuilderField, a.DefaultValue, a.NameAttribute, a.IsDescriptionAttribute, a.ValueIfNotVisible, a.SequenceNumber, a.MaxDisplayLength, a.Highlight, a.HelpText, a.TreeId, a.OnChangeScript, a.DefaultKeywordFilter, a.IsHtml, a.IsRequiredForCompleteness, a.RequiresTranslation, a.DisplayName, a.BaseUrl, a.AltText, a.HasSearchTokens, a.TokenDelimiterRegex, a.Height, a.Width, a.MaxSize, a.IsFiltered, a.Seed, a.IncrementAmount, a.Prefix, a.IsExtendableList, a.InputMask, a.ShowOnChild, a.IncludeInSearchForChild, a.GetDataFromChildren, a.HideForSort, a.IsAutoComplete, a.PluginClass, a.PluginParamsAttributeIds, a.ValueColumnName, a.ShowOnDownload, a.MaxDecimalPlaces, a.MinDecimalPlaces, a.Hidden , sa.Sequence, sa.SortAreaId, sa.Reverse, sa.Type FROM SortAttribute sa LEFT JOIN Attribute a ON sa.AttributeId=a.Id WHERE sa.AttributeId=? AND SortAreaId=?";
/*      */ 
/* 2012 */       psql = con.prepareStatement(sSQL, 1004, 1007);
/* 2013 */       psql.setLong(1, a_lAttributeId);
/* 2014 */       psql.setLong(2, a_lSortAreaId);
/* 2015 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 2017 */       if (rs.next())
/*      */       {
/* 2019 */         Attribute attribute = AttributeDBUtil.buildAttribute(rs);
/* 2020 */         sortAttribute = new SortAttribute();
/* 2021 */         sortAttribute.setAttribute(attribute);
/* 2022 */         sortAttribute.setOrder(rs.getInt("Sequence"));
/* 2023 */         sortAttribute.setSortAreaId(rs.getLong("SortAreaId"));
/* 2024 */         sortAttribute.setReverse(rs.getBoolean("Reverse"));
/* 2025 */         sortAttribute.setType(rs.getInt("Type"));
/*      */       }
/* 2027 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 2031 */       this.m_logger.error("AttributeManager.getSortAttribute - " + sqe);
/* 2032 */       throw new Bn2Exception("AttributeManager.getSortAttribute", sqe);
/*      */     }
/*      */     finally
/*      */     {
/* 2037 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */       {
/*      */         try
/*      */         {
/* 2041 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2045 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2050 */     return sortAttribute;
/*      */   }
/*      */ 
/*      */   public void saveSortAttribute(DBTransaction a_dbTransaction, SortAttribute a_sortAttribute, boolean a_bNew)
/*      */     throws Bn2Exception
/*      */   {
/* 2073 */     String ksMethodName = "saveSortAttribute";
/*      */ 
/* 2075 */     Connection con = null;
/* 2076 */     PreparedStatement psql = null;
/* 2077 */     String sSQL = null;
/*      */     try
/*      */     {
/* 2081 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2084 */       if (a_bNew)
/*      */       {
/* 2086 */         sSQL = "SELECT MAX(Sequence) maxOrder FROM SortAttribute WHERE SortAreaId=?";
/* 2087 */         psql = con.prepareStatement(sSQL);
/* 2088 */         psql.setLong(1, a_sortAttribute.getSortAreaId());
/* 2089 */         ResultSet rs = psql.executeQuery();
/* 2090 */         if (rs.next())
/*      */         {
/* 2092 */           a_sortAttribute.setOrder(rs.getInt("maxOrder") + 1);
/*      */         }
/*      */         else
/*      */         {
/* 2096 */           a_sortAttribute.setOrder(1);
/*      */         }
/* 2098 */         psql.close();
/*      */ 
/* 2101 */         sSQL = "INSERT INTO SortAttribute (Sequence, Type, Reverse, AttributeId, SortAreaId) VALUES (?,?,?,?,?)";
/*      */       }
/*      */       else
/*      */       {
/* 2105 */         sSQL = "UPDATE SortAttribute SET Sequence=?, Type=?, Reverse=? WHERE AttributeId=? AND SortAreaId=?";
/*      */       }
/*      */ 
/* 2108 */       psql = con.prepareStatement(sSQL);
/* 2109 */       int iCol = 1;
/* 2110 */       psql.setInt(iCol++, a_sortAttribute.getOrder());
/* 2111 */       psql.setInt(iCol++, a_sortAttribute.getType());
/* 2112 */       psql.setBoolean(iCol++, a_sortAttribute.getReverse());
/* 2113 */       psql.setLong(iCol++, a_sortAttribute.getAttribute().getId());
/* 2114 */       psql.setLong(iCol++, a_sortAttribute.getSortAreaId());
/*      */ 
/* 2116 */       psql.executeUpdate();
/* 2117 */       psql.close();
/*      */ 
/* 2119 */       refreshSortCaches(a_sortAttribute.getSortAreaId());
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 2123 */       this.m_logger.error("AttributeManager.saveSortAttribute - " + sqe);
/* 2124 */       throw new Bn2Exception("AttributeManager.saveSortAttribute", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector<Attribute> getAttributePositionList()
/*      */     throws Bn2Exception
/*      */   {
/* 2145 */     return getAttributePositionList(null);
/*      */   }
/*      */ 
/*      */   public Vector<Attribute> getVisibleAttributePositionList()
/*      */     throws Bn2Exception
/*      */   {
/* 2153 */     Vector<Attribute> vecAttributePositionList = getAttributePositionList(null);
/* 2154 */     Vector vecSelectedAttributes = new Vector();
/* 2155 */     for (Attribute att : vecAttributePositionList)
/*      */     {
/* 2157 */       if (!att.isHidden())
/*      */       {
/* 2159 */         vecSelectedAttributes.add(att);
/*      */       }
/*      */     }
/* 2162 */     return vecSelectedAttributes;
/*      */   }
/*      */ 
/*      */   public Vector<Attribute> getAttributePositionList(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 2186 */     String ksMethodName = "getAttributePositionList";
/*      */ 
/* 2189 */     if (this.m_vecAttributePositions == null)
/*      */     {
/* 2191 */       DBTransaction transaction = null;
/* 2192 */       Connection con = null;
/* 2193 */       PreparedStatement psql = null;
/* 2194 */       String sSQL = null;
/* 2195 */       Vector vecAttributePositionList = new Vector();
/*      */       try
/*      */       {
/* 2199 */         if (a_dbTransaction == null)
/*      */         {
/* 2201 */           transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */         }
/*      */         else
/*      */         {
/* 2205 */           transaction = a_dbTransaction;
/*      */         }
/* 2207 */         con = transaction.getConnection();
/*      */ 
/* 2209 */         sSQL = "SELECT a.Id aId, a.IsStatic, a.IsReadOnly, a.StaticFieldName, a.Label, a.IsMandatory, a.IsMandatoryBulkUpload, a.IsKeywordSearchable, a.AttributeTypeId, a.IsSearchField, a.IsSearchBuilderField, a.DefaultValue, a.NameAttribute, a.IsDescriptionAttribute, a.ValueIfNotVisible, a.SequenceNumber, a.MaxDisplayLength, a.Highlight, a.HelpText, a.TreeId, a.OnChangeScript, a.DefaultKeywordFilter, a.IsHtml, a.IsRequiredForCompleteness, a.RequiresTranslation, a.DisplayName, a.BaseUrl, a.AltText, a.HasSearchTokens, a.TokenDelimiterRegex, a.Height, a.Width, a.MaxSize, a.IsFiltered, a.Seed, a.IncrementAmount, a.Prefix, a.IsExtendableList, a.InputMask, a.ShowOnChild, a.IncludeInSearchForChild, a.GetDataFromChildren, a.HideForSort, a.IsAutoComplete, a.PluginClass, a.PluginParamsAttributeIds, a.ValueColumnName, a.ShowOnDownload, a.MaxDecimalPlaces, a.MinDecimalPlaces, a.Hidden ,l.Id lId, l.Name lName, l.NativeName lNativeName, l.Code lCode, ta.LanguageId taLanguageId, ta.Label taLabel, ta.DefaultValue taDefaultValue, ta.ValueIfNotVisible taValueIfNotVisible, ta.HelpText taHelpText, ta.AltText taAltText, ta.InputMask taInputMask, ta.DisplayName taDisplayName FROM Attribute a LEFT JOIN TranslatedAttribute ta ON a.Id = ta.AttributeId LEFT JOIN Language l ON l.Id = ta.LanguageId ORDER BY SequenceNumber";
/*      */ 
/* 2212 */         psql = con.prepareStatement(sSQL, 1004, 1007);
/*      */ 
/* 2214 */         ResultSet rs = psql.executeQuery();
/*      */ 
/* 2216 */         while (rs.next())
/*      */         {
/* 2218 */           Attribute attribute = AttributeDBUtil.buildAttribute(rs);
/* 2219 */           vecAttributePositionList.add(attribute);
/*      */ 
/* 2222 */           buildAttributeTranslations(rs, attribute);
/*      */         }
/*      */ 
/* 2225 */         psql.close();
/*      */ 
/* 2227 */         synchronized (this.m_oAttributeLock)
/*      */         {
/* 2229 */           this.m_vecAttributePositions = vecAttributePositionList;
/*      */         }
/*      */       }
/*      */       catch (SQLException sqe)
/*      */       {
/* 2234 */         this.m_logger.error("AttributeManager.getAttributePositionList - " + sqe);
/* 2235 */         throw new Bn2Exception("AttributeManager.getAttributePositionList", sqe);
/*      */       }
/*      */       finally
/*      */       {
/* 2240 */         if ((transaction != null) && (a_dbTransaction == null))
/*      */         {
/*      */           try
/*      */           {
/* 2244 */             transaction.commit();
/*      */           }
/*      */           catch (SQLException sqle)
/*      */           {
/* 2248 */             this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2254 */     return this.m_vecAttributePositions;
/*      */   }
/*      */ 
/*      */   public Vector getUnsortedAttributes(long a_lSortArea)
/*      */     throws Bn2Exception
/*      */   {
/* 2272 */     return getUnsortedAttributes(null, a_lSortArea);
/*      */   }
/*      */ 
/*      */   public Vector getUnsortedAttributes(DBTransaction a_dbTransaction, long a_lSortArea)
/*      */     throws Bn2Exception
/*      */   {
/* 2289 */     return getUnsortedAttributes(a_dbTransaction, a_lSortArea, null);
/*      */   }
/*      */ 
/*      */   private Vector getSortAttributes(DBTransaction a_dbTransaction, Vector a_vVisibleAttributeIds, boolean a_bIncludeUntypedAttributes, String[] a_aStaticExclusions, boolean a_bIndexing)
/*      */     throws Bn2Exception
/*      */   {
/* 2302 */     AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/* 2304 */     String sSQL = "SELECT a.Id aId, a.IsStatic, a.IsReadOnly, a.StaticFieldName, a.Label, a.IsMandatory, a.IsMandatoryBulkUpload, a.IsKeywordSearchable, a.AttributeTypeId, a.IsSearchField, a.IsSearchBuilderField, a.DefaultValue, a.NameAttribute, a.IsDescriptionAttribute, a.ValueIfNotVisible, a.SequenceNumber, a.MaxDisplayLength, a.Highlight, a.HelpText, a.TreeId, a.OnChangeScript, a.DefaultKeywordFilter, a.IsHtml, a.IsRequiredForCompleteness, a.RequiresTranslation, a.DisplayName, a.BaseUrl, a.AltText, a.HasSearchTokens, a.TokenDelimiterRegex, a.Height, a.Width, a.MaxSize, a.IsFiltered, a.Seed, a.IncrementAmount, a.Prefix, a.IsExtendableList, a.InputMask, a.ShowOnChild, a.IncludeInSearchForChild, a.GetDataFromChildren, a.HideForSort, a.IsAutoComplete, a.PluginClass, a.PluginParamsAttributeIds, a.ValueColumnName, a.ShowOnDownload, a.MaxDecimalPlaces, a.MinDecimalPlaces, a.Hidden ,l.Id lId, l.Name lName, l.NativeName lNativeName, l.Code lCode, ta.LanguageId taLanguageId, ta.Label taLabel, ta.DefaultValue taDefaultValue, ta.ValueIfNotVisible taValueIfNotVisible, ta.HelpText taHelpText, ta.AltText taAltText, ta.InputMask taInputMask, ta.DisplayName taDisplayName, -1 AS SortAreaId FROM Attribute a LEFT JOIN TranslatedAttribute ta ON a.Id = ta.AttributeId LEFT JOIN Language l ON l.Id = ta.LanguageId WHERE a.Hidden=0 ";
/*      */ 
/* 2313 */     if (!a_bIncludeUntypedAttributes)
/*      */     {
/* 2315 */       sSQL = sSQL + "AND (" + sqlGenerator.getNullCheckStatement("a.AttributeTypeId") + " OR a.AttributeTypeId<>" + 10L + ") ";
/*      */     }
/*      */ 
/* 2318 */     return getSortingAttributes(a_dbTransaction, sSQL, -1L, a_vVisibleAttributeIds, a_aStaticExclusions, a_bIndexing);
/*      */   }
/*      */ 
/*      */   private Vector getUnsortedAttributes(DBTransaction a_dbTransaction, long a_lSortArea, Vector a_vVisibleAttributeIds)
/*      */     throws Bn2Exception
/*      */   {
/* 2331 */     AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/* 2333 */     String sSQL = "SELECT a.Id aId, a.IsStatic, a.IsReadOnly, a.StaticFieldName, a.Label, a.IsMandatory, a.IsMandatoryBulkUpload, a.IsKeywordSearchable, a.AttributeTypeId, a.IsSearchField, a.IsSearchBuilderField, a.DefaultValue, a.NameAttribute, a.IsDescriptionAttribute, a.ValueIfNotVisible, a.SequenceNumber, a.MaxDisplayLength, a.Highlight, a.HelpText, a.TreeId, a.OnChangeScript, a.DefaultKeywordFilter, a.IsHtml, a.IsRequiredForCompleteness, a.RequiresTranslation, a.DisplayName, a.BaseUrl, a.AltText, a.HasSearchTokens, a.TokenDelimiterRegex, a.Height, a.Width, a.MaxSize, a.IsFiltered, a.Seed, a.IncrementAmount, a.Prefix, a.IsExtendableList, a.InputMask, a.ShowOnChild, a.IncludeInSearchForChild, a.GetDataFromChildren, a.HideForSort, a.IsAutoComplete, a.PluginClass, a.PluginParamsAttributeIds, a.ValueColumnName, a.ShowOnDownload, a.MaxDecimalPlaces, a.MinDecimalPlaces, a.Hidden ,l.Id lId, l.Name lName, l.NativeName lNativeName, l.Code lCode, ta.LanguageId taLanguageId, ta.Label taLabel, ta.DefaultValue taDefaultValue, ta.ValueIfNotVisible taValueIfNotVisible, ta.HelpText taHelpText, ta.AltText taAltText, ta.InputMask taInputMask, ta.DisplayName taDisplayName, sa.AttributeId, sa.SortAreaId FROM Attribute a LEFT JOIN SortAttribute sa ON a.Id=sa.AttributeId LEFT JOIN TranslatedAttribute ta ON a.Id = ta.AttributeId LEFT JOIN Language l ON l.Id = ta.LanguageId WHERE (" + sqlGenerator.getNullCheckStatement("a.AttributeTypeId") + " OR a.AttributeTypeId<>" + 10L + ") ";
/*      */ 
/* 2344 */     return getSortingAttributes(a_dbTransaction, sSQL, a_lSortArea, a_vVisibleAttributeIds, k_aNonSortAttributes, false);
/*      */   }
/*      */ 
/*      */   private Vector getSortingAttributes(DBTransaction a_dbTransaction, String a_sSQL, long a_lSortArea, Vector a_vVisibleAttributeIds, String[] a_aStaticExclusions, boolean a_bIndexing)
/*      */     throws Bn2Exception
/*      */   {
/* 2360 */     String ksMethodName = "getUnsortedAttributes";
/*      */ 
/* 2362 */     DBTransaction transaction = null;
/* 2363 */     Connection con = null;
/* 2364 */     PreparedStatement psql = null;
/* 2365 */     String sSQL = null;
/* 2366 */     Vector vecUnsortedAttributes = new Vector();
/*      */     try
/*      */     {
/* 2370 */       if (a_dbTransaction == null)
/*      */       {
/* 2372 */         transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */       }
/*      */       else
/*      */       {
/* 2376 */         transaction = a_dbTransaction;
/*      */       }
/* 2378 */       con = transaction.getConnection();
/*      */ 
/* 2380 */       sSQL = a_sSQL;
/*      */ 
/* 2382 */       if ((a_vVisibleAttributeIds != null) && (a_vVisibleAttributeIds.size() > 0))
/*      */       {
/* 2384 */         sSQL = sSQL + " AND a.Id IN (" + StringUtil.convertNumbersToString(a_vVisibleAttributeIds, ",") + ") ";
/*      */       }
/*      */ 
/* 2387 */       if (a_bIndexing)
/*      */       {
/* 2391 */         sSQL = sSQL + "AND (a.HideForSort=? OR a.StaticFieldName=?) ";
/*      */       }
/*      */       else
/*      */       {
/* 2395 */         sSQL = sSQL + "AND a.HideForSort=? ";
/*      */       }
/*      */ 
/* 2398 */       sSQL = sSQL + "ORDER BY a.SequenceNumber";
/*      */ 
/* 2400 */       psql = con.prepareStatement(sSQL, 1004, 1007);
/* 2401 */       psql.setBoolean(1, false);
/* 2402 */       if (a_bIndexing)
/*      */       {
/* 2404 */         psql.setString(2, "dateAdded");
/*      */       }
/* 2406 */       ResultSet rs = psql.executeQuery();
/* 2407 */       boolean bFirst = true;
/* 2408 */       String sLabel = null;
/* 2409 */       boolean bDontAdd = false;
/* 2410 */       Attribute attribute = null;
/*      */ 
/* 2412 */       while (rs.next())
/*      */       {
/* 2414 */         if (bFirst)
/*      */         {
/* 2416 */           sLabel = rs.getString("Label");
/* 2417 */           bFirst = false;
/*      */         }
/*      */ 
/* 2420 */         if (!rs.getString("Label").equals(sLabel))
/*      */         {
/* 2422 */           if (!bDontAdd)
/*      */           {
/* 2426 */             if ((!StringUtil.arrayContains(a_aStaticExclusions, attribute.getFieldName())) && ((attribute.getStatic()) || (StringUtils.isNotEmpty(attribute.getLabel().trim()))))
/*      */             {
/* 2429 */               addAttributeToResults(vecUnsortedAttributes, attribute);
/*      */             }
/*      */           }
/* 2432 */           sLabel = rs.getString("Label");
/* 2433 */           bDontAdd = false;
/*      */         }
/*      */ 
/* 2436 */         attribute = AttributeDBUtil.buildAttribute(rs);
/*      */ 
/* 2439 */         buildAttributeTranslations(rs, attribute);
/*      */ 
/* 2441 */         if ((a_lSortArea == -1L) || (rs.getLong("SortAreaId") != a_lSortArea))
/*      */           continue;
/* 2443 */         bDontAdd = true;
/*      */       }
/*      */ 
/* 2448 */       if ((attribute != null) && (!bDontAdd))
/*      */       {
/* 2450 */         if ((!StringUtil.arrayContains(k_aNonSortAttributes, attribute.getFieldName())) && ((attribute.getStatic()) || (StringUtils.isNotEmpty(attribute.getLabel().trim()))))
/*      */         {
/* 2453 */           addAttributeToResults(vecUnsortedAttributes, attribute);
/*      */         }
/*      */       }
/*      */ 
/* 2457 */       psql.close();
/*      */     }
/*      */     catch (Exception sqe)
/*      */     {
/* 2461 */       this.m_logger.error("AttributeManager.getUnsortedAttributes - " + sqe);
/* 2462 */       throw new Bn2Exception("AttributeManager.getUnsortedAttributes", sqe);
/*      */     }
/*      */     finally
/*      */     {
/* 2467 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */       {
/*      */         try
/*      */         {
/* 2471 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2475 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2480 */     return vecUnsortedAttributes;
/*      */   }
/*      */ 
/*      */   Set<Long> attributeIdsExcludedBySettings()
/*      */   {
/* 2489 */     Set excludedIds = new HashSet();
/*      */ 
/* 2491 */     if (!AssetBankSettings.getAgreementsEnabled())
/*      */     {
/* 2493 */       excludedIds.add(Long.valueOf(400L));
/*      */     }
/*      */ 
/* 2496 */     if (!AssetBankSettings.getShowSensitivityFields())
/*      */     {
/* 2498 */       excludedIds.add(Long.valueOf(301L));
/* 2499 */       excludedIds.add(Long.valueOf(300L));
/*      */     }
/*      */ 
/* 2502 */     if (!AssetBankSettings.getFeedbackRatings())
/*      */     {
/* 2504 */       excludedIds.add(Long.valueOf(600L));
/*      */     }
/*      */ 
/* 2507 */     return excludedIds;
/*      */   }
/*      */ 
/*      */   String settingsDependentFilterSQL(String a_sAttTableAlias)
/*      */   {
/* 2519 */     Set excludedIds = attributeIdsExcludedBySettings();
/* 2520 */     if (excludedIds.isEmpty())
/*      */     {
/* 2522 */       return "";
/*      */     }
/*      */ 
/* 2526 */     String sAttributeIdsExcludedBySettings = StringUtil.convertNumbersToString(excludedIds, ",");
/*      */ 
/* 2529 */     return " AND " + a_sAttTableAlias + ".Id NOT IN (" + sAttributeIdsExcludedBySettings + ")";
/*      */   }
/*      */ 
/*      */   private void addAttributeToResults(Vector a_vecAttributes, Attribute a_attribute)
/*      */   {
/* 2536 */     if ((a_attribute.getId() != 200L) && (!attributeIdsExcludedBySettings().contains(Long.valueOf(a_attribute.getId()))))
/*      */     {
/* 2539 */       a_vecAttributes.add(a_attribute);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getFlexibleAttributeList(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 2555 */     return getFlexibleAttributeList(a_dbTransaction, 0L);
/*      */   }
/*      */ 
/*      */   public Vector getAllDropdownAttributes() throws Bn2Exception
/*      */   {
/* 2567 */     String ksMethodName = "getAllDropdownAttributes";
/*      */ 
/* 2569 */     DBTransaction transaction = null;
/*      */     Vector vec;
/*      */     try {
/* 2574 */       transaction = this.m_transactionManager.getNewTransaction();
/* 2575 */       vec = getFlexibleAttributeList(transaction, 4L);
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 2579 */       this.m_logger.error("AttributeManager.getAllDropdownAttributes - " + e);
/* 2580 */       throw new Bn2Exception("AttributeManager.getAllDropdownAttributes", e);
/*      */     }
/*      */     finally
/*      */     {
/* 2585 */       if (transaction != null)
/*      */       {
/*      */         try
/*      */         {
/* 2589 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2593 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2598 */     return vec;
/*      */   }
/*      */ 
/*      */   public Vector<AttributeInList> getFlexibleAttributeList(DBTransaction a_dbTransaction, long a_lAttributeType)
/*      */     throws Bn2Exception
/*      */   {
/* 2613 */     return getFlexibleAndSpecifiedStaticAttributeList(a_dbTransaction, a_lAttributeType, null);
/*      */   }
/*      */ 
/*      */   public Vector<AttributeInList> getFlexibleAndSpecifiedStaticAttributeList(DBTransaction a_dbTransaction, long a_lAttributeType, String[] a_sIncludedStaticAttributes)
/*      */     throws Bn2Exception
/*      */   {
/* 2636 */     String ksMethodName = "getFlexibleAttributeList";
/*      */ 
/* 2638 */     Connection con = null;
/* 2639 */     PreparedStatement psql = null;
/* 2640 */     String sSQL = null;
/* 2641 */     Vector vec = new Vector();
/*      */     try
/*      */     {
/* 2645 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2647 */       sSQL = "SELECT Id, Label, AttributeTypeId, StaticFieldName FROM Attribute WHERE ((IsStatic=0 OR IsStatic IS NULL) AND Label <> ' ') ";
/*      */ 
/* 2649 */       if ((a_sIncludedStaticAttributes != null) && (a_sIncludedStaticAttributes.length > 0))
/*      */       {
/* 2651 */         sSQL = sSQL + "OR StaticFieldName IN (";
/*      */ 
/* 2653 */         for (int i = 0; i < a_sIncludedStaticAttributes.length; i++)
/*      */         {
/* 2655 */           sSQL = sSQL + "'" + a_sIncludedStaticAttributes[i] + "'";
/*      */ 
/* 2657 */           if (i >= a_sIncludedStaticAttributes.length - 1)
/*      */             continue;
/* 2659 */           sSQL = sSQL + ",";
/*      */         }
/*      */ 
/* 2662 */         sSQL = sSQL + ") ";
/*      */       }
/*      */ 
/* 2666 */       if (a_lAttributeType > 0L)
/*      */       {
/* 2668 */         sSQL = sSQL + "AND AttributeTypeId = " + a_lAttributeType + " ";
/*      */       }
/*      */ 
/* 2671 */       sSQL = sSQL + "ORDER BY SequenceNumber";
/* 2672 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 2674 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 2676 */       while (rs.next())
/*      */       {
/* 2678 */         AttributeInList entry = new AttributeInList(rs.getLong("Id"), rs.getString("Label"), rs.getLong("AttributeTypeId"), rs.getString("StaticFieldName"));
/* 2679 */         vec.add(entry);
/*      */       }
/*      */ 
/* 2682 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 2686 */       throw new Bn2Exception("AttributeManager.getFlexibleAttributeList", sqe);
/*      */     }
/*      */ 
/* 2689 */     return vec;
/*      */   }
/*      */ 
/*      */   public Vector getStaticAttributeList(DBTransaction a_dbTransaction, long a_lAttributeType)
/*      */     throws Bn2Exception
/*      */   {
/* 2709 */     String ksMethodName = "getStaticAttributeList";
/*      */ 
/* 2711 */     Connection con = null;
/* 2712 */     PreparedStatement psql = null;
/* 2713 */     String sSQL = null;
/* 2714 */     Vector vec = new Vector();
/*      */     try
/*      */     {
/* 2718 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2720 */       sSQL = "SELECT Id, StaticFieldName FROM Attribute WHERE IsStatic=?";
/*      */ 
/* 2723 */       if (a_lAttributeType > 0L)
/*      */       {
/* 2725 */         sSQL = sSQL + " AND AttributeTypeId = " + a_lAttributeType;
/*      */       }
/*      */ 
/* 2728 */       sSQL = sSQL + " ORDER BY SequenceNumber";
/* 2729 */       psql = con.prepareStatement(sSQL);
/* 2730 */       psql.setBoolean(1, true);
/*      */ 
/* 2732 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 2734 */       while (rs.next())
/*      */       {
/* 2736 */         StringDataBean entry = new StringDataBean(rs.getLong("Id"), rs.getString("StaticFieldName"));
/* 2737 */         vec.add(entry);
/*      */       }
/*      */ 
/* 2740 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 2744 */       throw new Bn2Exception("AttributeManager.getStaticAttributeList SQLException. sSQL = " + sSQL, sqe);
/*      */     }
/*      */ 
/* 2747 */     return vec;
/*      */   }
/*      */ 
/*      */   public void updateAttributePosition(DBTransaction a_dbTransaction, Attribute a_attribute)
/*      */     throws Bn2Exception
/*      */   {
/* 2755 */     updateAttributePosition(a_dbTransaction, a_attribute.getId(), a_attribute.getSequence());
/*      */   }
/*      */ 
/*      */   public void updateAttributePosition(DBTransaction a_dbTransaction, long a_lId, int a_iSequence)
/*      */     throws Bn2Exception
/*      */   {
/* 2776 */     String ksMethodName = "updateAttributePosition";
/*      */ 
/* 2778 */     Connection con = null;
/* 2779 */     PreparedStatement psql = null;
/* 2780 */     String sSQL = null;
/*      */     try
/*      */     {
/* 2784 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2786 */       sSQL = "UPDATE Attribute SET SequenceNumber = ? WHERE Id=?";
/* 2787 */       psql = con.prepareStatement(sSQL);
/* 2788 */       psql.setInt(1, a_iSequence);
/* 2789 */       psql.setLong(2, a_lId);
/*      */ 
/* 2791 */       psql.executeUpdate();
/* 2792 */       psql.close();
/*      */ 
/* 2794 */       invalidateAttributeCache(a_lId);
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 2798 */       this.m_logger.error("AttributeManager.updateAttributePosition - " + sqe);
/* 2799 */       throw new Bn2Exception("AttributeManager.updateAttributePosition", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public List<AttributeType> getAttributeTypeList()
/*      */     throws Bn2Exception
/*      */   {
/* 2823 */     ensureAttributeTypesLoaded();
/* 2824 */     return this.m_attributeTypes;
/*      */   }
/*      */ 
/*      */   public AttributeType getAttributeTypeById(long a_lAttributeTypeId)
/*      */     throws Bn2Exception
/*      */   {
/* 2835 */     ensureAttributeTypesLoaded();
/* 2836 */     return (AttributeType)this.m_attributeTypesById.get(Long.valueOf(a_lAttributeTypeId));
/*      */   }
/*      */ 
/*      */   private void ensureAttributeTypesLoaded()
/*      */     throws Bn2Exception
/*      */   {
/* 2844 */     String ksMethodName = "ensureAttributeTypesLoaded";
/*      */ 
/* 2846 */     synchronized (this.m_attributeTypesLock)
/*      */     {
/* 2848 */       if (this.m_attributeTypes == null)
/*      */       {
/* 2850 */         DBTransaction transaction = null;
/* 2851 */         List attributeTypes = new ArrayList();
/* 2852 */         Map attributeTypesById = new HashMap();
/*      */         try
/*      */         {
/* 2855 */           transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/* 2856 */           Connection con = transaction.getConnection();
/*      */ 
/* 2858 */           String sSQL = "SELECT Id, Name, AttributeStorageTypeId FROM AttributeType ORDER BY SequenceNumber";
/* 2859 */           PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/* 2861 */           ResultSet rs = psql.executeQuery();
/*      */ 
/* 2863 */           while (rs.next())
/*      */           {
/* 2865 */             AttributeType attType = new AttributeType();
/* 2866 */             attType.setId(rs.getLong("Id"));
/* 2867 */             attType.setName(rs.getString("Name"));
/* 2868 */             long lStorageTypeId = rs.getLong("AttributeStorageTypeId");
/* 2869 */             attType.setAttributeStorageType(AttributeStorageType.get(lStorageTypeId));
/*      */ 
/* 2871 */             attributeTypes.add(attType);
/* 2872 */             attributeTypesById.put(Long.valueOf(attType.getId()), attType);
/*      */           }
/*      */ 
/* 2876 */           psql.close();
/*      */         }
/*      */         catch (SQLException sqe)
/*      */         {
/* 2880 */           this.m_logger.error("AttributeManager.ensureAttributeTypesLoaded - " + sqe);
/* 2881 */           throw new Bn2Exception("AttributeManager.ensureAttributeTypesLoaded", sqe);
/*      */         }
/*      */         finally
/*      */         {
/* 2886 */           if (transaction != null)
/*      */           {
/*      */             try
/*      */             {
/* 2890 */               transaction.commit();
/*      */             }
/*      */             catch (SQLException sqle)
/*      */             {
/* 2894 */               this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2900 */         this.m_attributeTypes = Collections.unmodifiableList(attributeTypes);
/* 2901 */         this.m_attributeTypesById = Collections.unmodifiableMap(attributeTypesById);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector<AttributeType> getAttributeTypeListExcludingGroupHeading() throws Bn2Exception
/*      */   {
/* 2908 */     List<AttributeType> list = getAttributeTypeList();
/* 2909 */     Vector listExcludingGH = new Vector(list.size() - 1);
/* 2910 */     for (AttributeType attributeType : list)
/*      */     {
/* 2912 */       if (attributeType.getId() != 10L)
/*      */       {
/* 2914 */         listExcludingGH.add(attributeType);
/*      */       }
/*      */     }
/* 2917 */     return listExcludingGH;
/*      */   }
/*      */ 
/*      */   public void populateEmbeddedMetadataValues(String a_sImageFilePath, Asset a_asset, Vector a_vecAssetAttributes, boolean a_bOverwriteExistingValues)
/*      */   {
/* 2946 */     String ksMethodName = "populateEmbeddedMetadataValues";
/*      */     try
/*      */     {
/* 2950 */       EmbeddedDataMapping mapping = null;
/* 2951 */       Attribute attribute = null;
/* 2952 */       String sKey = null;
/* 2953 */       String sValue = null;
/*      */ 
/* 2956 */       Vector vecAllMappings = getEmbeddedDataMappings(null, -1L, 1L);
/*      */ 
/* 2961 */       HashMap hmEmbeddedValues = new HashMap();
/* 2962 */       boolean bCheckAutoRotate = false;
/*      */ 
/* 2965 */       if ((vecAllMappings != null) && (vecAllMappings.size() > 0))
/*      */       {
/* 2968 */         Vector vTags = new Vector();
/* 2969 */         Vector vBinaryTags = new Vector();
/*      */ 
/* 2972 */         for (int i = 0; i < vecAllMappings.size(); i++)
/*      */         {
/* 2974 */           mapping = (EmbeddedDataMapping)vecAllMappings.get(i);
/*      */ 
/* 2977 */           attribute = getAttribute(null, mapping.getAttributeId());
/*      */ 
/* 2980 */           sKey = getExifToolKey(mapping);
/*      */ 
/* 2983 */           if (mapping.getValue().getType().getId() == 7L)
/*      */           {
/* 2986 */             if ((a_asset instanceof ImageAsset))
/*      */             {
/* 2988 */               ImageAsset image = (ImageAsset)a_asset;
/* 2989 */               sValue = "";
/*      */ 
/* 2992 */               if (((mapping.getValue().getId() == 600L) && (image.getHeight() >= image.getWidth())) || ((mapping.getValue().getId() == 601L) && (image.getHeight() <= image.getWidth())))
/*      */               {
/* 2995 */                 sValue = "" + image.getHeight();
/*      */               }
/* 2997 */               else if (((mapping.getValue().getId() == 600L) && (image.getHeight() < image.getWidth())) || ((mapping.getValue().getId() == 601L) && (image.getHeight() > image.getWidth())))
/*      */               {
/* 3000 */                 sValue = "" + image.getWidth();
/*      */               }
/* 3002 */               else if (mapping.getValue().getId() == 602L)
/*      */               {
/* 3004 */                 sValue = "" + image.getWidth() * image.getHeight();
/*      */               }
/* 3006 */               else if (mapping.getValue().getId() == 603L)
/*      */               {
/* 3009 */                 bCheckAutoRotate = true;
/*      */               }
/* 3011 */               else if (mapping.getValue().getId() == 604L)
/*      */               {
/* 3013 */                 vTags.addAll(ExifTool.getGPSLatitudeTags());
/*      */               }
/* 3015 */               else if (mapping.getValue().getId() == 605L)
/*      */               {
/* 3017 */                 vTags.addAll(ExifTool.getGPSLongitudeTags());
/*      */               }
/* 3019 */               else if (mapping.getValue().getId() == 606L)
/*      */               {
/* 3021 */                 vTags.addAll(ExifTool.getGPSMapDatumTags());
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 3027 */             if (mapping.getValue().getId() == 607L)
/*      */             {
/* 3029 */               sValue = FileUtil.getSuffix(a_sImageFilePath);
/*      */             }
/*      */ 
/* 3032 */             if (sValue == null)
/*      */             {
/* 3034 */               sValue = "";
/*      */             }
/*      */ 
/* 3038 */             hmEmbeddedValues.put(sKey, sValue);
/*      */           }
/* 3043 */           else if (mapping.getBinaryData())
/*      */           {
/* 3045 */             vBinaryTags.add(sKey);
/*      */           }
/*      */           else
/*      */           {
/* 3049 */             vTags.add(sKey);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 3057 */         for (int i = 0; (bCheckAutoRotate) && (i < ExifTool.c_sAutoRotateFields.length); i++)
/*      */         {
/* 3059 */           vTags.add(ExifTool.c_sAutoRotateFields[i]);
/*      */         }
/*      */ 
/* 3063 */         if (vTags.size() > 0)
/*      */         {
/* 3065 */           ExifTool.getData(hmEmbeddedValues, a_sImageFilePath, vTags, true);
/*      */         }
/*      */ 
/* 3069 */         if (vBinaryTags.size() > 0)
/*      */         {
/* 3071 */           ExifTool.getBinaryData(hmEmbeddedValues, a_sImageFilePath, vBinaryTags);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3077 */       if (bCheckAutoRotate)
/*      */       {
/* 3079 */         a_asset.setAutoRotate(ExifTool.getAutoRotate(hmEmbeddedValues));
/*      */       }
/*      */ 
/* 3085 */       int i = 0;
/* 3086 */       for (; (vecAllMappings != null) && (i < vecAllMappings.size()); i++)
/*      */       {
/* 3089 */         mapping = (EmbeddedDataMapping)vecAllMappings.get(i);
/*      */ 
/* 3092 */         attribute = getAttribute(null, mapping.getAttributeId());
/*      */ 
/* 3095 */         sKey = getExifToolKey(mapping);
/*      */ 
/* 3098 */         if (mapping.getValue().getId() == 604L)
/*      */         {
/* 3100 */           sValue = ExifTool.getSignedLatitude(hmEmbeddedValues);
/*      */         }
/* 3102 */         else if (mapping.getValue().getId() == 605L)
/*      */         {
/* 3104 */           sValue = ExifTool.getSignedLongitude(hmEmbeddedValues);
/*      */         }
/* 3106 */         else if (mapping.getValue().getId() == 606L)
/*      */         {
/* 3108 */           sValue = ExifTool.getMapDatumMapped(hmEmbeddedValues);
/*      */         }
/*      */         else
/*      */         {
/* 3112 */           sValue = (String)hmEmbeddedValues.get(sKey);
/*      */         }
/*      */ 
/* 3116 */         if (sValue == null) {
/*      */           continue;
/*      */         }
/* 3119 */         String[] saValues = null;
/*      */ 
/* 3121 */         if ((mapping.getDelimiter() != null) && (mapping.getDelimiter().length() > 0))
/*      */         {
/* 3124 */           saValues = sValue.split(mapping.getDelimiter());
/*      */         }
/*      */         else
/*      */         {
/* 3128 */           saValues = new String[] { sValue };
/*      */         }
/*      */ 
/* 3132 */         if (attribute.getStatic())
/*      */         {
/* 3135 */           if ((!attribute.getFieldName().equals("categories")) && (!attribute.getFieldName().equals("accessLevels")))
/*      */           {
/*      */             continue;
/*      */           }
/* 3139 */           Vector vecMatchingCats = null;
/* 3140 */           List vecCats = null;
/*      */ 
/* 3143 */           if (attribute.getFieldName().equals("categories"))
/*      */           {
/* 3145 */             if (a_asset.getDescriptiveCategories() == null)
/*      */             {
/* 3147 */               a_asset.setDescriptiveCategories(new Vector());
/*      */             }
/* 3149 */             vecCats = a_asset.getDescriptiveCategories();
/*      */           }
/*      */           else
/*      */           {
/* 3153 */             if (a_asset.getPermissionCategories() == null)
/*      */             {
/* 3155 */               a_asset.setPermissionCategories(new Vector());
/*      */             }
/* 3157 */             vecCats = a_asset.getPermissionCategories();
/*      */           }
/*      */ 
/* 3160 */           DBTransaction transaction = null;
/*      */           try
/*      */           {
/* 3164 */             transaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/* 3166 */             String sInvalidCatNames = "";
/*      */ 
/* 3168 */             for (int iIndex = 0; iIndex < saValues.length; iIndex++)
/*      */             {
/* 3171 */               vecMatchingCats = this.m_categoryManager.findCategoriesByName(transaction, attribute.getFieldName().equals("categories") ? 1L : 2L, saValues[iIndex]);
/*      */ 
/* 3178 */               if (((vecMatchingCats == null) || (vecMatchingCats.size() <= 0)) && (mapping.getDelimiter() != null))
/*      */               {
/* 3180 */                 boolean bLookUpNow = false;
/*      */ 
/* 3183 */                 if (sInvalidCatNames.length() > 0)
/*      */                 {
/* 3186 */                   sInvalidCatNames = sInvalidCatNames + mapping.getDelimiter();
/* 3187 */                   bLookUpNow = true;
/*      */                 }
/*      */ 
/* 3191 */                 sInvalidCatNames = sInvalidCatNames + saValues[iIndex];
/*      */ 
/* 3193 */                 if (bLookUpNow)
/*      */                 {
/* 3195 */                   vecMatchingCats = this.m_categoryManager.findCategoriesByName(transaction, attribute.getFieldName().equals("categories") ? 1L : 2L, sInvalidCatNames);
/*      */                 }
/*      */ 
/*      */               }
/*      */ 
/* 3203 */               if ((vecMatchingCats == null) || (vecMatchingCats.size() <= 0))
/*      */                 continue;
/* 3205 */               vecCats.addAll(vecMatchingCats);
/* 3206 */               sInvalidCatNames = "";
/*      */             }
/*      */ 
/*      */           }
/*      */           finally
/*      */           {
/*      */             try
/*      */             {
/* 3216 */               if (transaction != null)
/*      */               {
/* 3218 */                 transaction.commit();
/*      */               }
/*      */             }
/*      */             catch (SQLException sqle)
/*      */             {
/* 3223 */               this.m_logger.error("Exception commiting transaction in AttributeManager.populateEmbeddedMetadataValues:", sqle);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 3231 */           for (String sCurrentValue : saValues)
/*      */           {
/* 3235 */             if ((attribute.getTypeId() == 3L) || (attribute.getTypeId() == 8L))
/*      */             {
/* 3237 */               Date dtDate = ExifTool.getValueAsDate(sCurrentValue);
/*      */ 
/* 3239 */               if (dtDate != null)
/*      */               {
/* 3243 */                 if (attribute.getTypeId() == 3L)
/*      */                 {
/* 3245 */                   BrightDate bdtDate = new BrightDate(dtDate);
/* 3246 */                   sCurrentValue = bdtDate.getFormDate();
/*      */                 }
/* 3248 */                 else if (attribute.getTypeId() == 8L)
/*      */                 {
/* 3250 */                   BrightDateTime bdtDateTime = new BrightDateTime(dtDate);
/* 3251 */                   sCurrentValue = bdtDateTime.getFormDateTime();
/*      */                 }
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/* 3258 */                 this.m_logger.warn("AttributeManager.populateEmbeddedMetadataValues: could not parse date");
/* 3259 */                 sCurrentValue = "";
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 3265 */             findAndSetAttributeValue(a_asset, a_vecAssetAttributes, attribute.getId(), sCurrentValue, mapping.getDelimiter(), false);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/* 3279 */       this.m_logger.error("AttributeManager.populateEmbeddedMetadataValues Could not read embedded metadata", t);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void embedMetadataValues(String a_sImageFilePath, Asset a_asset, long a_lMappingDirection, Map a_hmKeyValues)
/*      */     throws Bn2Exception
/*      */   {
/* 3303 */     Vector vecMappings = getEmbeddedDataMappings(null, -1L, a_lMappingDirection);
/*      */ 
/* 3307 */     if (a_hmKeyValues == null)
/*      */     {
/* 3309 */       a_hmKeyValues = new HashMap();
/*      */     }
/*      */ 
/* 3313 */     for (int i = 0; (vecMappings != null) && (i < vecMappings.size()); i++)
/*      */     {
/* 3315 */       EmbeddedDataMapping mapping = (EmbeddedDataMapping)vecMappings.get(i);
/* 3316 */       String sValue = null;
/*      */ 
/* 3319 */       if ((mapping.getValue().getType().getName().equals("File")) && (mapping.getValue().getExpression().equals("FileName")))
/*      */       {
/*      */         continue;
/*      */       }
/*      */ 
/* 3326 */       String sFieldName = mapping.getValue().getType().getName() + ":" + mapping.getValue().getExpression();
/*      */ 
/* 3328 */       AttributeValue avalue = a_asset.getAttributeValue(mapping.getAttributeId());
/*      */ 
/* 3331 */       if (avalue != null)
/*      */       {
/* 3334 */         BrightDateTime date = avalue.getDateTimeValue();
/* 3335 */         if ((date == null) || (date.getDate() == null))
/*      */         {
/* 3337 */           BrightDate bd = avalue.getDateValue();
/* 3338 */           if (bd != null)
/*      */           {
/* 3340 */             date = bd.getBrightDateTime();
/*      */           }
/*      */         }
/*      */ 
/* 3344 */         if ((date != null) && (date.getDate() != null))
/*      */         {
/* 3347 */           date.setDisplayDateTimeFormat("yyyy.MM.dd HH:mm:ss");
/* 3348 */           sValue = date.getDisplayDateTime();
/*      */         }
/* 3352 */         else if (avalue.getAttribute().getIsKeywordPicker())
/*      */         {
/* 3355 */           if (avalue.getKeywordCategories() != null)
/*      */           {
/* 3357 */             for (int x = 0; x < avalue.getKeywordCategories().size(); x++)
/*      */             {
/* 3359 */               if (sValue == null)
/*      */               {
/* 3361 */                 sValue = "";
/*      */               }
/* 3363 */               Keyword keyword = (Keyword)avalue.getKeywordCategories().elementAt(x);
/* 3364 */               sValue = sValue + keyword.getName();
/* 3365 */               if (i >= avalue.getKeywordCategories().size() - 1)
/*      */                 continue;
/* 3367 */               sValue = sValue + AssetBankSettings.getKeywordDelimiter();
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 3375 */           sValue = avalue.getValue();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3381 */       if (sValue == null) {
/*      */         continue;
/*      */       }
/* 3384 */       if (a_hmKeyValues.containsKey(sFieldName))
/*      */       {
/* 3387 */         sValue = (String)a_hmKeyValues.get(sFieldName) + "\r\n" + sValue;
/*      */       }
/*      */ 
/* 3392 */       a_hmKeyValues.put(sFieldName, sValue);
/*      */     }
/*      */ 
/* 3396 */     if (a_hmKeyValues.size() > 0)
/*      */     {
/* 3399 */       KeyValueBean[] aKeyValues = new KeyValueBean[a_hmKeyValues.size()];
/*      */ 
/* 3401 */       Iterator it = a_hmKeyValues.keySet().iterator();
/* 3402 */       int i = 0;
/*      */ 
/* 3404 */       while (it.hasNext())
/*      */       {
/* 3406 */         KeyValueBean keyValue = new KeyValueBean();
/*      */ 
/* 3409 */         keyValue.setKey((String)it.next());
/*      */ 
/* 3412 */         keyValue.setValue((String)a_hmKeyValues.get(keyValue.getKey()));
/*      */ 
/* 3414 */         aKeyValues[i] = keyValue;
/* 3415 */         i++;
/*      */       }
/*      */ 
/* 3419 */       ExifTool.addMetadata(aKeyValues, a_sImageFilePath);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void buildAttributeTranslations(ResultSet a_rs, Attribute a_attribute)
/*      */     throws SQLException
/*      */   {
/* 3434 */     while ((!a_rs.isAfterLast()) && (a_rs.getLong("aId") == a_attribute.getId()) && (a_rs.getLong("taLanguageId") > 0L))
/*      */     {
/* 3436 */       AttributeDBUtil.addAttributeTranslation(a_rs, a_attribute);
/* 3437 */       a_rs.next();
/*      */     }
/*      */ 
/* 3441 */     if ((a_rs.isAfterLast()) || (a_rs.getLong("aId") != a_attribute.getId()))
/*      */     {
/* 3443 */       a_rs.previous();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void refreshSortCaches(long a_lSortArea)
/*      */   {
/* 3461 */     synchronized (this.m_oSortLock)
/*      */     {
/* 3463 */       this.m_hmSortLists.remove(new Long(a_lSortArea));
/* 3464 */       this.m_hmSortFields.remove(String.valueOf(a_lSortArea) + true);
/* 3465 */       this.m_hmSortFields.remove(String.valueOf(a_lSortArea) + false);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void refreshDisplayCache(long a_lGroupId)
/*      */   {
/* 3480 */     synchronized (this.m_oDisplayLock)
/*      */     {
/* 3482 */       this.m_hmDisplayAttributes.remove(new Long(a_lGroupId));
/* 3483 */       this.m_vecAllDisplayAttributes = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void refreshMappingCache(long a_lTypeId)
/*      */   {
/* 3490 */     synchronized (this.m_oMappingLock)
/*      */     {
/* 3492 */       this.m_vecAllMappings = null;
/* 3493 */       this.m_hmEmbeddedDataMappings.remove(new Long(a_lTypeId));
/*      */     }
/*      */   }
/*      */ 
/*      */   public SortField[] getSortFieldsForAttributeSort(DBTransaction a_dbTransaction, long a_lAttributeId, boolean a_bReverse)
/*      */     throws Bn2Exception
/*      */   {
/* 3506 */     SortField[] aSortFields = new SortField[2];
/*      */ 
/* 3508 */     Attribute att = getAttribute(a_dbTransaction, a_lAttributeId, null);
/*      */ 
/* 3510 */     aSortFields[0] = AttributeUtil.getSortFieldForAttributeSort(att, a_bReverse);
/*      */ 
/* 3513 */     aSortFields[1] = SortField.FIELD_SCORE;
/*      */ 
/* 3515 */     return aSortFields;
/*      */   }
/*      */ 
/*      */   public SortField[] getSortFields(DBTransaction a_dbTransaction, long a_lSortAreaId, boolean a_bSortDescending)
/*      */     throws Bn2Exception
/*      */   {
/* 3540 */     String sKey = String.valueOf(a_lSortAreaId) + a_bSortDescending;
/* 3541 */     SortField[] aSortFields = new SortField[0];
/*      */ 
/* 3543 */     if (this.m_hmSortFields.containsKey(sKey))
/*      */     {
/* 3546 */       aSortFields = (SortField[])(SortField[])this.m_hmSortFields.get(sKey);
/*      */     }
/*      */     else
/*      */     {
/* 3550 */       Vector vecSortAttributes = getSortAttributeList(a_dbTransaction, a_lSortAreaId);
/*      */ 
/* 3552 */       if (vecSortAttributes.size() > 0)
/*      */       {
/* 3554 */         aSortFields = new SortField[vecSortAttributes.size() + 1];
/*      */ 
/* 3556 */         for (int i = 0; i < vecSortAttributes.size(); i++)
/*      */         {
/* 3559 */           SortAttribute sa = (SortAttribute)vecSortAttributes.elementAt(i);
/* 3560 */           String sField = AttributeUtil.getIndexFieldName(sa.getAttribute()) + "_sort";
/*      */ 
/* 3563 */           SortField field = new SortField(sField, sa.getType(), a_bSortDescending ^ sa.getReverse());
/* 3564 */           aSortFields[i] = field;
/*      */         }
/*      */ 
/* 3568 */         aSortFields[vecSortAttributes.size()] = SortField.FIELD_SCORE;
/*      */       }
/*      */       else
/*      */       {
/* 3572 */         aSortFields = new SortField[] { new SortField(null, 0, !a_bSortDescending) };
/*      */       }
/*      */ 
/* 3575 */       if (aSortFields.length > 0)
/*      */       {
/* 3578 */         synchronized (this.m_oSortLock)
/*      */         {
/* 3580 */           this.m_hmSortFields.put(sKey, aSortFields);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3585 */     return aSortFields;
/*      */   }
/*      */ 
/*      */   public boolean getCustomSortOrderDefined(DBTransaction a_dbTransaction, long a_lSortAreaId)
/*      */     throws Bn2Exception
/*      */   {
/* 3599 */     String sKey = String.valueOf(a_lSortAreaId) + true;
/* 3600 */     SortField[] aSortFields = null;
/*      */ 
/* 3602 */     if (this.m_hmSortFields.containsKey(sKey))
/*      */     {
/* 3605 */       aSortFields = (SortField[])(SortField[])this.m_hmSortFields.get(sKey);
/*      */     }
/*      */     else
/*      */     {
/* 3609 */       aSortFields = getSortFields(a_dbTransaction, a_lSortAreaId, true);
/*      */     }
/*      */ 
/* 3612 */     return (aSortFields.length != 0) && (!aSortFields[0].equals(SortField.FIELD_SCORE));
/*      */   }
/*      */ 
/*      */   public Vector<Attribute> getNonDisplayAttributes(DBTransaction a_dbTransaction, long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 3629 */     String ksMethodName = "getNonDisplayAttributes";
/*      */ 
/* 3631 */     DBTransaction transaction = null;
/* 3632 */     Connection con = null;
/* 3633 */     PreparedStatement psql = null;
/* 3634 */     String sSQL = null;
/* 3635 */     Vector vecNonDisplayAttributes = new Vector();
/*      */     try
/*      */     {
/* 3639 */       if (a_dbTransaction == null)
/*      */       {
/* 3641 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */       else
/*      */       {
/* 3645 */         transaction = a_dbTransaction;
/*      */       }
/* 3647 */       con = transaction.getConnection();
/* 3648 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/* 3651 */       sSQL = "SELECT a.Id, da.DisplayAttributeGroupId FROM Attribute a LEFT JOIN DisplayAttribute da ON a.Id=da.AttributeId WHERE NOT " + sqlGenerator.getNullCheckStatement("da.DisplayAttributeGroupId") + " " + "ORDER BY a.Id";
/*      */ 
/* 3655 */       psql = con.prepareStatement(sSQL);
/* 3656 */       ResultSet rs = psql.executeQuery();
/* 3657 */       ArrayList alIds = new ArrayList();
/* 3658 */       long lLastId = -1L;
/* 3659 */       boolean bCanUse = true;
/* 3660 */       while (rs.next())
/*      */       {
/* 3662 */         long lAttId = rs.getLong("Id");
/* 3663 */         if ((lAttId != lLastId) && (lLastId > 0L))
/*      */         {
/* 3665 */           if (bCanUse)
/*      */           {
/* 3667 */             alIds.add(String.valueOf(lLastId));
/*      */           }
/* 3669 */           bCanUse = true;
/*      */         }
/*      */ 
/* 3672 */         long lGroupId = rs.getLong("DisplayAttributeGroupId");
/* 3673 */         if (lGroupId == a_lGroupId)
/*      */         {
/* 3675 */           bCanUse = false;
/*      */         }
/* 3677 */         lLastId = lAttId;
/*      */       }
/*      */ 
/* 3681 */       if ((bCanUse) && (lLastId > 0L))
/*      */       {
/* 3683 */         alIds.add(String.valueOf(lLastId));
/*      */       }
/* 3685 */       psql.close();
/*      */ 
/* 3689 */       StringBuilder sbSQL = new StringBuilder("SELECT a.Id aId, a.IsStatic, a.IsReadOnly, a.StaticFieldName, a.Label, a.IsMandatory, a.IsMandatoryBulkUpload, a.IsKeywordSearchable, a.AttributeTypeId, a.IsSearchField, a.IsSearchBuilderField, a.DefaultValue, a.NameAttribute, a.IsDescriptionAttribute, a.ValueIfNotVisible, a.SequenceNumber, a.MaxDisplayLength, a.Highlight, a.HelpText, a.TreeId, a.OnChangeScript, a.DefaultKeywordFilter, a.IsHtml, a.IsRequiredForCompleteness, a.RequiresTranslation, a.DisplayName, a.BaseUrl, a.AltText, a.HasSearchTokens, a.TokenDelimiterRegex, a.Height, a.Width, a.MaxSize, a.IsFiltered, a.Seed, a.IncrementAmount, a.Prefix, a.IsExtendableList, a.InputMask, a.ShowOnChild, a.IncludeInSearchForChild, a.GetDataFromChildren, a.HideForSort, a.IsAutoComplete, a.PluginClass, a.PluginParamsAttributeIds, a.ValueColumnName, a.ShowOnDownload, a.MaxDecimalPlaces, a.MinDecimalPlaces, a.Hidden ,l.Id lId, l.Name lName, l.NativeName lNativeName, l.Code lCode, ta.LanguageId taLanguageId, ta.Label taLabel, ta.DefaultValue taDefaultValue, ta.ValueIfNotVisible taValueIfNotVisible, ta.HelpText taHelpText, ta.AltText taAltText, ta.InputMask taInputMask, ta.DisplayName taDisplayName, da.AttributeId, da.DisplayLength, da.ShowLabel, da.IsLink, da.ShowOnChild daShowOnChild FROM Attribute a LEFT JOIN DisplayAttribute da ON a.Id=da.AttributeId LEFT JOIN TranslatedAttribute ta ON a.Id = ta.AttributeId LEFT JOIN Language l ON l.Id = ta.LanguageId WHERE (" + sqlGenerator.getNullCheckStatement("da.DisplayAttributeGroupId"));
/*      */ 
/* 3703 */       if ((alIds != null) && (alIds.size() > 0))
/*      */       {
/* 3705 */         sbSQL.append(" OR a.Id IN (" + StringUtil.convertStringCollectionToString(alIds, ",") + ")");
/*      */       }
/*      */ 
/* 3708 */       sbSQL.append(") AND (" + sqlGenerator.getNullCheckStatement("a.AttributeTypeId") + " OR a.AttributeTypeId<>?)");
/* 3709 */       sbSQL.append(settingsDependentFilterSQL("a"));
/*      */ 
/* 3711 */       sbSQL.append(" ORDER BY a.SequenceNumber");
/* 3712 */       sSQL = sbSQL.toString();
/*      */ 
/* 3714 */       psql = con.prepareStatement(sSQL, 1004, 1007);
/* 3715 */       psql.setLong(1, 10L);
/* 3716 */       rs = psql.executeQuery();
/*      */ 
/* 3718 */       boolean bFirst = true;
/* 3719 */       String sLabel = null;
/* 3720 */       Attribute attribute = null;
/*      */ 
/* 3723 */       String[] asNonDisplayAttributes = null;
/*      */ 
/* 3726 */       if ((!AssetBankSettings.getFeedbackRatings()) || (AssetBankSettings.getFeedbackRatingsAreVotes()))
/*      */       {
/* 3729 */         asNonDisplayAttributes = new String[k_aNonDisplayAttributes.length + 1];
/* 3730 */         System.arraycopy(k_aNonDisplayAttributes, 0, asNonDisplayAttributes, 0, k_aNonDisplayAttributes.length);
/*      */ 
/* 3734 */         asNonDisplayAttributes[k_aNonDisplayAttributes.length] = "rating";
/*      */       }
/*      */       else
/*      */       {
/* 3738 */         asNonDisplayAttributes = k_aNonDisplayAttributes;
/*      */       }
/*      */ 
/* 3741 */       while (rs.next())
/*      */       {
/* 3743 */         if (bFirst)
/*      */         {
/* 3745 */           sLabel = rs.getString("Label");
/* 3746 */           bFirst = false;
/*      */         }
/*      */ 
/* 3749 */         if (!rs.getString("Label").equals(sLabel))
/*      */         {
/* 3753 */           if (!StringUtil.arrayContains(asNonDisplayAttributes, attribute.getFieldName()))
/*      */           {
/* 3755 */             vecNonDisplayAttributes.add(attribute);
/*      */           }
/*      */ 
/* 3758 */           sLabel = rs.getString("Label");
/*      */         }
/*      */ 
/* 3761 */         attribute = AttributeDBUtil.buildAttribute(rs);
/*      */ 
/* 3764 */         buildAttributeTranslations(rs, attribute);
/*      */       }
/*      */ 
/* 3768 */       if (!StringUtil.arrayContains(k_aNonDisplayAttributes, attribute.getFieldName()))
/*      */       {
/* 3770 */         vecNonDisplayAttributes.add(attribute);
/*      */       }
/*      */ 
/* 3773 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 3778 */       this.m_logger.error("AttributeManager.getNonDisplayAttributes - " + sqe);
/* 3779 */       throw new Bn2Exception("AttributeManager.getNonDisplayAttributes", sqe);
/*      */     }
/*      */     finally
/*      */     {
/* 3784 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */       {
/*      */         try
/*      */         {
/* 3788 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 3792 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 3797 */     return vecNonDisplayAttributes;
/*      */   }
/*      */ 
/*      */   public Vector<DisplayAttribute> getAllDisplayAttributes(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 3811 */     return getDisplayAttributes(a_dbTransaction, -1L);
/*      */   }
/*      */ 
/*      */   public ArrayList<String> getBrowseListDisplayAttributeLabels(DBTransaction a_transaction, ABUserProfile a_profile)
/*      */     throws Bn2Exception
/*      */   {
/* 3824 */     Vector<DisplayAttribute> dispAtts = getDisplayAttributes(a_transaction, 5L);
/* 3825 */     ArrayList alLabels = new ArrayList();
/*      */ 
/* 3827 */     for (DisplayAttribute dispAtt : dispAtts)
/*      */     {
/* 3829 */       dispAtt.getAttribute().setLanguage(a_profile.getCurrentLanguage());
/* 3830 */       alLabels.add(dispAtt.getAttribute().getLabel());
/*      */     }
/* 3832 */     return alLabels;
/*      */   }
/*      */ 
/*      */   public Vector<DisplayAttribute> getDisplayAttributes(DBTransaction a_dbTransaction, long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 3847 */     String ksMethodName = "getDisplayAttributes";
/*      */ 
/* 3849 */     Vector vecDisplayAttributes = null;
/*      */ 
/* 3851 */     if ((a_lGroupId > 0L) && (this.m_hmDisplayAttributes.get(new Long(a_lGroupId)) != null))
/*      */     {
/* 3854 */       vecDisplayAttributes = (Vector)this.m_hmDisplayAttributes.get(new Long(a_lGroupId));
/*      */     }
/* 3856 */     else if ((a_lGroupId <= 0L) && (this.m_vecAllDisplayAttributes != null))
/*      */     {
/* 3858 */       vecDisplayAttributes = this.m_vecAllDisplayAttributes;
/*      */     }
/*      */     else
/*      */     {
/* 3863 */       DBTransaction transaction = null;
/* 3864 */       Connection con = null;
/* 3865 */       PreparedStatement psql = null;
/* 3866 */       String sSQL = null;
/* 3867 */       vecDisplayAttributes = new Vector();
/*      */       try
/*      */       {
/* 3871 */         if (a_dbTransaction == null)
/*      */         {
/* 3873 */           transaction = this.m_transactionManager.getNewTransaction();
/*      */         }
/*      */         else
/*      */         {
/* 3877 */           transaction = a_dbTransaction;
/*      */         }
/* 3879 */         con = transaction.getConnection();
/*      */ 
/* 3881 */         StringBuilder sbSQL = new StringBuilder("SELECT a.Id aId, a.IsStatic, a.IsReadOnly, a.StaticFieldName, a.Label, a.IsMandatory, a.IsMandatoryBulkUpload, a.IsKeywordSearchable, a.AttributeTypeId, a.IsSearchField, a.IsSearchBuilderField, a.DefaultValue, a.NameAttribute, a.IsDescriptionAttribute, a.ValueIfNotVisible, a.SequenceNumber, a.MaxDisplayLength, a.Highlight, a.HelpText, a.TreeId, a.OnChangeScript, a.DefaultKeywordFilter, a.IsHtml, a.IsRequiredForCompleteness, a.RequiresTranslation, a.DisplayName, a.BaseUrl, a.AltText, a.HasSearchTokens, a.TokenDelimiterRegex, a.Height, a.Width, a.MaxSize, a.IsFiltered, a.Seed, a.IncrementAmount, a.Prefix, a.IsExtendableList, a.InputMask, a.ShowOnChild, a.IncludeInSearchForChild, a.GetDataFromChildren, a.HideForSort, a.IsAutoComplete, a.PluginClass, a.PluginParamsAttributeIds, a.ValueColumnName, a.ShowOnDownload, a.MaxDecimalPlaces, a.MinDecimalPlaces, a.Hidden ,l.Id lId, l.Name lName, l.NativeName lNativeName, l.Code lCode, ta.LanguageId taLanguageId, ta.Label taLabel, ta.DefaultValue taDefaultValue, ta.ValueIfNotVisible taValueIfNotVisible, ta.HelpText taHelpText, ta.AltText taAltText, ta.InputMask taInputMask, ta.DisplayName taDisplayName, da.DisplayLength, da.SequenceNumber daSequence, da.ShowLabel, da.IsLink, da.DisplayAttributeGroupId, da.ShowOnChild daShowOnChild FROM DisplayAttribute da LEFT JOIN Attribute a ON da.AttributeId=a.Id LEFT JOIN TranslatedAttribute ta ON a.Id = ta.AttributeId LEFT JOIN Language l ON l.Id = ta.LanguageId WHERE 1=1");
/*      */ 
/* 3891 */         sbSQL.append(settingsDependentFilterSQL("a"));
/*      */ 
/* 3893 */         if (a_lGroupId > 0L)
/*      */         {
/* 3895 */           sbSQL.append(" AND da.DisplayAttributeGroupId=?");
/*      */         }
/*      */ 
/* 3898 */         sbSQL.append(" ORDER BY da.SequenceNumber");
/* 3899 */         sSQL = sbSQL.toString();
/*      */ 
/* 3901 */         psql = con.prepareStatement(sSQL, 1004, 1007);
/*      */ 
/* 3903 */         if (a_lGroupId > 0L)
/*      */         {
/* 3905 */           psql.setLong(1, a_lGroupId);
/*      */         }
/*      */ 
/* 3908 */         ResultSet rs = psql.executeQuery();
/*      */ 
/* 3910 */         while (rs.next())
/*      */         {
/* 3912 */           Attribute attribute = AttributeDBUtil.buildAttribute(rs);
/* 3913 */           DisplayAttribute disAttribute = buildDisplayAttribute(rs, attribute);
/* 3914 */           vecDisplayAttributes.add(disAttribute);
/*      */ 
/* 3917 */           buildAttributeTranslations(rs, attribute);
/*      */         }
/*      */ 
/* 3920 */         cacheDisplayAttributes(a_lGroupId, vecDisplayAttributes);
/*      */ 
/* 3922 */         psql.close();
/*      */       }
/*      */       catch (SQLException sqe)
/*      */       {
/* 3926 */         this.m_logger.error("AttributeManager.getDisplayAttributes - " + sqe);
/* 3927 */         throw new Bn2Exception("AttributeManager.getDisplayAttributes", sqe);
/*      */       }
/*      */       finally
/*      */       {
/* 3932 */         if ((transaction != null) && (a_dbTransaction == null))
/*      */         {
/*      */           try
/*      */           {
/* 3936 */             transaction.commit();
/*      */           }
/*      */           catch (SQLException sqle)
/*      */           {
/* 3940 */             this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 3947 */     return vecDisplayAttributes;
/*      */   }
/*      */ 
/*      */   private void cacheDisplayAttributes(long a_lGroupId, Vector<DisplayAttribute> a_vecDisplayAttributes)
/*      */   {
/* 3960 */     synchronized (this.m_oDisplayLock)
/*      */     {
/* 3962 */       if (a_lGroupId > 0L)
/*      */       {
/* 3964 */         this.m_hmDisplayAttributes.put(new Long(a_lGroupId), a_vecDisplayAttributes);
/*      */       }
/*      */       else
/*      */       {
/* 3968 */         this.m_vecAllDisplayAttributes = a_vecDisplayAttributes;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public DisplayAttribute getDisplayAttribute(DBTransaction a_dbTransaction, long a_lId, long a_lGroupId)
/*      */     throws Bn2Exception
/*      */   {
/* 3990 */     String ksMethodName = "getDisplayAttribute";
/*      */ 
/* 3992 */     DisplayAttribute attribute = null;
/*      */ 
/* 3995 */     DBTransaction transaction = null;
/* 3996 */     Connection con = null;
/* 3997 */     PreparedStatement psql = null;
/* 3998 */     String sSQL = null;
/*      */     try
/*      */     {
/* 4002 */       if (a_dbTransaction == null)
/*      */       {
/* 4004 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */       else
/*      */       {
/* 4008 */         transaction = a_dbTransaction;
/*      */       }
/* 4010 */       con = transaction.getConnection();
/*      */ 
/* 4012 */       sSQL = "SELECT a.Id aId, a.IsStatic, a.IsReadOnly, a.StaticFieldName, a.Label, a.IsMandatory, a.IsMandatoryBulkUpload, a.IsKeywordSearchable, a.AttributeTypeId, a.IsSearchField, a.IsSearchBuilderField, a.DefaultValue, a.NameAttribute, a.IsDescriptionAttribute, a.ValueIfNotVisible, a.SequenceNumber, a.MaxDisplayLength, a.Highlight, a.HelpText, a.TreeId, a.OnChangeScript, a.DefaultKeywordFilter, a.IsHtml, a.IsRequiredForCompleteness, a.RequiresTranslation, a.DisplayName, a.BaseUrl, a.AltText, a.HasSearchTokens, a.TokenDelimiterRegex, a.Height, a.Width, a.MaxSize, a.IsFiltered, a.Seed, a.IncrementAmount, a.Prefix, a.IsExtendableList, a.InputMask, a.ShowOnChild, a.IncludeInSearchForChild, a.GetDataFromChildren, a.HideForSort, a.IsAutoComplete, a.PluginClass, a.PluginParamsAttributeIds, a.ValueColumnName, a.ShowOnDownload, a.MaxDecimalPlaces, a.MinDecimalPlaces, a.Hidden , da.DisplayLength, da.SequenceNumber daSequence, da.ShowLabel, da.IsLink, da.DisplayAttributeGroupId, da.ShowOnChild daShowOnChild FROM DisplayAttribute da LEFT JOIN Attribute a ON da.AttributeId=a.Id WHERE da.AttributeId=? AND da.DisplayAttributeGroupId=?";
/*      */ 
/* 4017 */       psql = con.prepareStatement(sSQL);
/* 4018 */       psql.setLong(1, a_lId);
/* 4019 */       psql.setLong(2, a_lGroupId);
/* 4020 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 4022 */       while (rs.next())
/*      */       {
/* 4024 */         Attribute newAttribute = AttributeDBUtil.buildAttribute(rs);
/* 4025 */         attribute = buildDisplayAttribute(rs, newAttribute);
/*      */       }
/*      */ 
/* 4028 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 4032 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */       {
/*      */         try
/*      */         {
/* 4036 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 4040 */           this.m_logger.error("SQL Exception whilst trying to rollback connection " + sqle.getMessage());
/*      */         }
/*      */       }
/* 4043 */       this.m_logger.error("AttributeManager.getDisplayAttribute - " + sqe);
/* 4044 */       throw new Bn2Exception("AttributeManager.getDisplayAttribute", sqe);
/*      */     }
/*      */     finally
/*      */     {
/* 4049 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */       {
/*      */         try
/*      */         {
/* 4053 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 4057 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 4062 */     return attribute;
/*      */   }
/*      */ 
/*      */   public boolean isDisplayAttribute(String a_sStaticFieldName)
/*      */     throws Bn2Exception
/*      */   {
/* 4073 */     boolean bIsDisplayAtt = false;
/* 4074 */     Vector<DisplayAttribute> vecDisplayAttributes = getAllDisplayAttributes(null);
/*      */ 
/* 4076 */     for (DisplayAttribute att : vecDisplayAttributes)
/*      */     {
/* 4078 */       if ((att.getAttribute().getFieldName() != null) && (att.getAttribute().getFieldName().equals(a_sStaticFieldName)))
/*      */       {
/* 4080 */         bIsDisplayAtt = true;
/* 4081 */         break;
/*      */       }
/*      */     }
/*      */ 
/* 4085 */     return bIsDisplayAtt;
/*      */   }
/*      */ 
/*      */   public Vector<Attribute> getNameAttributes(DBTransaction a_transaction)
/*      */     throws Bn2Exception
/*      */   {
/* 4098 */     return getAttributes(a_transaction, -1L, false, false, 2, true);
/*      */   }
/*      */ 
/*      */   public long getNameAttributeId(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 4117 */     return getAttributeIdForFlagValue(a_dbTransaction, "NameAttribute");
/*      */   }
/*      */ 
/*      */   public long getDescriptionAttributeId(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 4134 */     return getAttributeIdForFlagValue(a_dbTransaction, "IsDescriptionAttribute");
/*      */   }
/*      */ 
/*      */   private long getAttributeIdForFlagValue(DBTransaction a_dbTransaction, String a_sFlagColumnName)
/*      */     throws Bn2Exception
/*      */   {
/* 4147 */     String ksMethodName = "getAttributeIdForFlagValue";
/* 4148 */     long lId = 0L;
/*      */ 
/* 4151 */     DBTransaction transaction = null;
/* 4152 */     Connection con = null;
/* 4153 */     PreparedStatement psql = null;
/* 4154 */     String sSQL = null;
/*      */     try
/*      */     {
/* 4158 */       if (a_dbTransaction == null)
/*      */       {
/* 4160 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */       else
/*      */       {
/* 4164 */         transaction = a_dbTransaction;
/*      */       }
/* 4166 */       con = transaction.getConnection();
/*      */ 
/* 4168 */       sSQL = "SELECT Id FROM Attribute WHERE " + a_sFlagColumnName + "=?";
/*      */ 
/* 4170 */       psql = con.prepareStatement(sSQL);
/* 4171 */       psql.setBoolean(1, true);
/* 4172 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 4174 */       if (rs.next())
/*      */       {
/* 4176 */         lId = rs.getLong("Id");
/*      */       }
/*      */ 
/* 4179 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 4183 */       this.m_logger.error("AttributeManager.getAttributeIdForFlagValue - " + sqe);
/* 4184 */       throw new Bn2Exception("AttributeManager.getAttributeIdForFlagValue", sqe);
/*      */     }
/*      */     finally
/*      */     {
/* 4189 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */       {
/*      */         try
/*      */         {
/* 4193 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 4197 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 4202 */     return lId;
/*      */   }
/*      */ 
/*      */   public ArrayList<IdValueBean> getDisplayAtttributeGroups(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 4216 */     ArrayList alGroups = null;
/*      */ 
/* 4219 */     if (this.m_alDisplayAttributeGroupCache != null)
/*      */     {
/* 4221 */       return this.m_alDisplayAttributeGroupCache;
/*      */     }
/*      */ 
/* 4225 */     DBTransaction transaction = a_dbTransaction;
/*      */     try
/*      */     {
/* 4229 */       if (transaction == null)
/*      */       {
/* 4231 */         transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */       }
/* 4233 */       Connection con = transaction.getConnection();
/*      */ 
/* 4235 */       String sSQL = "SELECT Id, Name FROM DisplayAttributeGroup";
/* 4236 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 4237 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 4239 */       while (rs.next())
/*      */       {
/* 4241 */         IdValueBean group = new IdValueBean();
/* 4242 */         group.setId(rs.getLong("Id"));
/* 4243 */         group.setValue(rs.getString("Name"));
/* 4244 */         if (alGroups == null)
/*      */         {
/* 4246 */           alGroups = new ArrayList();
/*      */         }
/* 4248 */         alGroups.add(group);
/*      */       }
/*      */ 
/* 4251 */       cacheDisplayAttributeGroups(alGroups);
/*      */ 
/* 4253 */       psql.close();
/*      */     }
/*      */     catch (Exception sqe)
/*      */     {
/* 4257 */       this.m_logger.error("AttributeManager Error: ", sqe);
/* 4258 */       throw new Bn2Exception("AttributeManager Error: ", sqe);
/*      */     }
/*      */     finally
/*      */     {
/* 4263 */       if ((transaction != null) && (a_dbTransaction == null))
/*      */       {
/*      */         try
/*      */         {
/* 4267 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 4271 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 4276 */     return alGroups;
/*      */   }
/*      */ 
/*      */   private void cacheDisplayAttributeGroups(ArrayList<IdValueBean> a_alGroups)
/*      */   {
/* 4288 */     synchronized (this.m_oDisplayAttributeGroupLock)
/*      */     {
/* 4290 */       this.m_alDisplayAttributeGroupCache = a_alGroups;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void saveDisplayAttribute(DBTransaction a_dbTransaction, DisplayAttribute a_dispAttribute, boolean a_bNew)
/*      */     throws Bn2Exception
/*      */   {
/* 4311 */     String ksMethodName = "saveDisplayAttribute";
/*      */ 
/* 4313 */     Connection con = null;
/* 4314 */     PreparedStatement psql = null;
/* 4315 */     String sSQL = null;
/*      */     try
/*      */     {
/* 4319 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 4321 */       if (a_bNew)
/*      */       {
/* 4323 */         sSQL = "SELECT MAX(SequenceNumber) seq FROM DisplayAttribute";
/* 4324 */         psql = con.prepareStatement(sSQL);
/* 4325 */         ResultSet rs = psql.executeQuery();
/* 4326 */         rs.next();
/* 4327 */         int iSeq = rs.getInt("seq") + 1;
/* 4328 */         a_dispAttribute.setSequenceNumber(iSeq);
/* 4329 */         psql.close();
/*      */ 
/* 4331 */         sSQL = "INSERT INTO DisplayAttribute (DisplayLength, SequenceNumber, ShowLabel, IsLink, ShowOnChild, DisplayAttributeGroupId, AttributeId) VALUES (?,?,?,?,?,?,?)";
/*      */       }
/*      */       else
/*      */       {
/* 4335 */         sSQL = "UPDATE DisplayAttribute SET DisplayLength=?, SequenceNumber=?, ShowLabel=?, IsLink=?, ShowOnChild=? WHERE DisplayAttributeGroupId=? AND AttributeId=?";
/*      */       }
/*      */ 
/* 4338 */       psql = con.prepareStatement(sSQL);
/* 4339 */       int iCol = 1;
/* 4340 */       psql.setInt(iCol++, a_dispAttribute.getDisplayLength());
/* 4341 */       psql.setInt(iCol++, a_dispAttribute.getSequenceNumber());
/* 4342 */       psql.setBoolean(iCol++, a_dispAttribute.getShowLabel());
/* 4343 */       psql.setBoolean(iCol++, a_dispAttribute.getIsLink());
/* 4344 */       psql.setBoolean(iCol++, a_dispAttribute.getShowOnChild());
/* 4345 */       psql.setLong(iCol++, a_dispAttribute.getGroupId());
/* 4346 */       psql.setLong(iCol++, a_dispAttribute.getAttribute().getId());
/*      */ 
/* 4348 */       psql.executeUpdate();
/* 4349 */       psql.close();
/*      */ 
/* 4351 */       refreshDisplayCache(a_dispAttribute.getGroupId());
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 4355 */       this.m_logger.error("AttributeManager.saveDisplayAttribute - " + sqe);
/* 4356 */       throw new Bn2Exception("AttributeManager.saveDisplayAttribute", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void invalidateAttributeCache()
/*      */   {
/* 4362 */     invalidateAttributeCache(-1L);
/*      */   }
/*      */ 
/*      */   public void invalidateAttributeCache(long a_lAttributeId)
/*      */   {
/* 4367 */     this.m_attributeValueManager.invalidateAttributeCaches(a_lAttributeId);
/* 4368 */     synchronized (this.m_oAttributeLock)
/*      */     {
/* 4370 */       this.m_vecAttributePositions = null;
/* 4371 */       this.m_attributeCache.clear();
/* 4372 */       this.m_attributesCache.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getLightboxUserSortAttributes(DBTransaction a_dbTransaction, Vector a_vVisibleAttributeIds)
/*      */     throws Bn2Exception
/*      */   {
/* 4379 */     return getSortAttributes(a_dbTransaction, a_vVisibleAttributeIds, true, null, false);
/*      */   }
/*      */ 
/*      */   public Vector getUserSortAttributes(DBTransaction a_dbTransaction, Vector a_vVisibleAttributeIds, boolean a_bIndexing)
/*      */     throws Bn2Exception
/*      */   {
/* 4389 */     return getSortAttributes(a_dbTransaction, a_vVisibleAttributeIds, false, k_aNonSortAttributes, a_bIndexing);
/*      */   }
/*      */ 
/*      */   public String[] getSortFieldsNames()
/*      */     throws Bn2Exception
/*      */   {
/* 4405 */     String[] asFieldNames = null;
/*      */ 
/* 4407 */     if ((AssetBankSettings.getUserDrivenSortingEnabled()) || (AssetBankSettings.getUserDrivenBrowseSortingEnabled()))
/*      */     {
/* 4409 */       Vector vAtts = getUserSortAttributes(null, null, true);
/* 4410 */       asFieldNames = new String[vAtts.size() + 3];
/*      */ 
/* 4412 */       for (int i = 0; i < vAtts.size(); i++)
/*      */       {
/* 4414 */         asFieldNames[i] = AttributeUtil.getIndexFieldName((Attribute)vAtts.get(i));
/*      */       }
/*      */ 
/* 4417 */       asFieldNames[vAtts.size()] = "f_views";
/* 4418 */       asFieldNames[(vAtts.size() + 1)] = "f_downloads";
/* 4419 */       asFieldNames[(vAtts.size() + 2)] = "f_long_feedbackCount";
/*      */     }
/*      */     else
/*      */     {
/* 4424 */       Vector vecSearchList = getSortAttributeListBySearchOrder();
/* 4425 */       Vector vecBrowseList = getSortAttributeListByBrowseOrder();
/*      */ 
/* 4427 */       int iHasChildrenNo = AssetBankSettings.getSortByPresenceOfChildAssets() ? 1 : 0;
/*      */ 
/* 4430 */       asFieldNames = new String[vecSearchList.size() + vecBrowseList.size() + iHasChildrenNo];
/* 4431 */       SortAttribute sa = null;
/*      */ 
/* 4433 */       if (AssetBankSettings.getSortByPresenceOfChildAssets())
/*      */       {
/* 4435 */         asFieldNames[0] = "f_hasChildren";
/*      */       }
/*      */ 
/* 4438 */       for (int i = 0; i < vecSearchList.size(); i++)
/*      */       {
/* 4440 */         sa = (SortAttribute)vecSearchList.get(i);
/* 4441 */         asFieldNames[(i + iHasChildrenNo)] = AttributeUtil.getIndexFieldName(sa.getAttribute());
/*      */       }
/* 4443 */       for (int i = 0; i < vecBrowseList.size(); i++)
/*      */       {
/* 4445 */         sa = (SortAttribute)vecBrowseList.get(i);
/* 4446 */         asFieldNames[(vecSearchList.size() + i + iHasChildrenNo)] = AttributeUtil.getIndexFieldName(sa.getAttribute());
/*      */       }
/*      */     }
/*      */ 
/* 4450 */     return asFieldNames;
/*      */   }
/*      */ 
/*      */   public Vector getEmbeddedDataMappingDirections(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 4471 */     Vector vecMappingDirections = new Vector();
/* 4472 */     Connection con = null;
/* 4473 */     ResultSet rs = null;
/* 4474 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 4476 */     if (transaction == null)
/*      */     {
/* 4478 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 4483 */       con = transaction.getConnection();
/*      */ 
/* 4485 */       String sSQL = "SELECT md.Id mdId, md.Name mdName FROM MappingDirection md";
/*      */ 
/* 4488 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 4489 */       rs = psql.executeQuery();
/*      */ 
/* 4491 */       while (rs.next())
/*      */       {
/* 4493 */         if (vecMappingDirections == null)
/*      */         {
/* 4495 */           vecMappingDirections = new Vector();
/*      */         }
/*      */ 
/* 4499 */         MappingDirection direction = buildMappingDirection(rs);
/* 4500 */         vecMappingDirections.add(direction);
/*      */       }
/*      */ 
/* 4503 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 4507 */       this.m_logger.error("SQL Exception whilst getting mapping directions from the database : " + e);
/* 4508 */       throw new Bn2Exception("SQL Exception whilst getting mapping directions from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 4513 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 4517 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 4521 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 4526 */     return vecMappingDirections;
/*      */   }
/*      */ 
/*      */   public HashMap getEmbeddedDataValuesByType(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 4545 */     HashMap hmEmbeddedData = null;
/* 4546 */     Connection con = null;
/* 4547 */     ResultSet rs = null;
/* 4548 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 4550 */     if (transaction == null)
/*      */     {
/* 4552 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 4557 */       con = transaction.getConnection();
/*      */ 
/* 4559 */       String sSQL = "SELECT edv.Id edvId, edv.Name edvName, edv.Expression edvExpression, edt.Id edtId, edt.Name edtName FROM EmbeddedDataValue edv JOIN EmbeddedDataType edt ON edv.EmbeddedDataTypeId=edt.Id ORDER BY edtName, edvName";
/*      */ 
/* 4565 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 4566 */       rs = psql.executeQuery();
/* 4567 */       long lLastTypeId = -1L;
/*      */ 
/* 4569 */       while (rs.next())
/*      */       {
/* 4571 */         if (hmEmbeddedData == null)
/*      */         {
/* 4573 */           hmEmbeddedData = new HashMap();
/*      */         }
/*      */ 
/* 4577 */         if (lLastTypeId != rs.getLong("edtId"))
/*      */         {
/* 4579 */           hmEmbeddedData.put(new Long(rs.getLong("edtId")), new Vector());
/* 4580 */           lLastTypeId = rs.getLong("edtId");
/*      */         }
/*      */ 
/* 4584 */         EmbeddedDataValue val = buildEmbeddedDataValue(rs);
/* 4585 */         Vector vecValues = (Vector)hmEmbeddedData.get(new Long(lLastTypeId));
/* 4586 */         vecValues.add(val);
/* 4587 */         hmEmbeddedData.put(new Long(lLastTypeId), vecValues);
/*      */       }
/*      */ 
/* 4590 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 4594 */       this.m_logger.error("SQL Exception whilst getting embedded data values from the database : " + e);
/* 4595 */       throw new Bn2Exception("SQL Exception whilst getting embedded data values from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 4600 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 4604 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 4608 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 4613 */     return hmEmbeddedData;
/*      */   }
/*      */ 
/*      */   public Vector getEmbeddedDataTypes(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 4634 */     Vector vecTypes = null;
/* 4635 */     Connection con = null;
/* 4636 */     ResultSet rs = null;
/* 4637 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 4639 */     if (transaction == null)
/*      */     {
/* 4641 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 4646 */       con = transaction.getConnection();
/*      */ 
/* 4648 */       String sSQL = "SELECT edt.Id edtId, edt.Name edtName FROM EmbeddedDataType edt";
/*      */ 
/* 4652 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 4653 */       rs = psql.executeQuery();
/*      */ 
/* 4655 */       while (rs.next())
/*      */       {
/* 4657 */         if (vecTypes == null)
/*      */         {
/* 4659 */           vecTypes = new Vector();
/*      */         }
/*      */ 
/* 4663 */         EmbeddedDataType type = buildEmbeddedDataType(rs);
/* 4664 */         vecTypes.add(type);
/*      */       }
/*      */ 
/* 4667 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 4671 */       this.m_logger.error("SQL Exception whilst getting embedded data types from the database : " + e);
/* 4672 */       throw new Bn2Exception("SQL Exception whilst getting embedded data types from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 4677 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 4681 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 4685 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 4690 */     return vecTypes;
/*      */   }
/*      */ 
/*      */   public boolean mappingExists(DBTransaction a_dbTransaction, EmbeddedDataMapping a_mapping)
/*      */     throws Bn2Exception
/*      */   {
/* 4710 */     String ksMethodName = "mappingExists";
/* 4711 */     boolean bExists = false;
/*      */ 
/* 4713 */     Connection con = null;
/* 4714 */     PreparedStatement psql = null;
/* 4715 */     String sSQL = null;
/*      */     try
/*      */     {
/* 4719 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 4722 */       sSQL = "SELECT * FROM EmbeddedDataMapping WHERE AttributeId=? AND EmbeddedDataValueId=? AND MappingDirectionId=?";
/* 4723 */       psql = con.prepareStatement(sSQL);
/* 4724 */       int iCol = 1;
/* 4725 */       psql.setLong(iCol++, a_mapping.getAttributeId());
/* 4726 */       psql.setLong(iCol++, a_mapping.getEmbeddedDataValueId());
/* 4727 */       psql.setLong(iCol++, a_mapping.getMappingDirectionId());
/* 4728 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 4730 */       if (rs.next())
/*      */       {
/* 4732 */         bExists = true;
/*      */       }
/* 4734 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 4738 */       this.m_logger.error("AttributeManager.mappingExists - " + sqe);
/* 4739 */       throw new Bn2Exception("AttributeManager.mappingExists", sqe);
/*      */     }
/*      */ 
/* 4742 */     return bExists;
/*      */   }
/*      */ 
/*      */   public void saveEmbeddedDataMapping(DBTransaction a_dbTransaction, EmbeddedDataMapping a_mapping, long a_lTypeId, EmbeddedDataMapping a_oldMapping)
/*      */     throws Bn2Exception
/*      */   {
/* 4762 */     String ksMethodName = "saveEmbeddedDataMapping";
/*      */ 
/* 4764 */     Connection con = null;
/* 4765 */     PreparedStatement psql = null;
/* 4766 */     String sSQL = null;
/*      */     try
/*      */     {
/* 4770 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 4772 */       if (a_oldMapping != null)
/*      */       {
/* 4775 */         deleteEmbeddedDataMapping(a_dbTransaction, a_oldMapping.getAttribute().getId(), a_oldMapping.getValue().getId(), a_oldMapping.getDirection().getId(), a_oldMapping.getValue().getType().getId());
/*      */       }
/*      */ 
/* 4779 */       int iSeq = 0;
/* 4780 */       if (a_oldMapping == null)
/*      */       {
/* 4782 */         sSQL = "SELECT Max(Sequence) seq FROM EmbeddedDataMapping";
/* 4783 */         psql = con.prepareStatement(sSQL);
/* 4784 */         ResultSet rs = psql.executeQuery();
/* 4785 */         rs.next();
/* 4786 */         iSeq = rs.getInt("seq");
/* 4787 */         iSeq++;
/* 4788 */         psql.close();
/*      */       }
/*      */       else
/*      */       {
/* 4792 */         iSeq = a_mapping.getSequence();
/*      */       }
/*      */ 
/* 4796 */       sSQL = "INSERT INTO EmbeddedDataMapping (AttributeId, EmbeddedDataValueId, MappingDirectionId, DataDelim, Sequence, IsBinary) VALUES (?,?,?,?,?,?)";
/*      */ 
/* 4798 */       psql = con.prepareStatement(sSQL);
/* 4799 */       int iCol = 1;
/* 4800 */       psql.setLong(iCol++, a_mapping.getAttributeId());
/* 4801 */       psql.setLong(iCol++, a_mapping.getEmbeddedDataValueId());
/* 4802 */       psql.setLong(iCol++, a_mapping.getMappingDirectionId());
/* 4803 */       psql.setString(iCol++, a_mapping.getDelimiter());
/* 4804 */       psql.setInt(iCol++, iSeq);
/* 4805 */       psql.setBoolean(iCol++, a_mapping.getBinaryData());
/*      */ 
/* 4807 */       psql.executeUpdate();
/* 4808 */       psql.close();
/*      */ 
/* 4810 */       refreshMappingCache(a_lTypeId);
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 4814 */       this.m_logger.error("AttributeManager.saveEmbeddedDataMapping - " + sqe);
/* 4815 */       throw new Bn2Exception("AttributeManager.saveEmbeddedDataMapping", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteEmbeddedDataMapping(DBTransaction a_dbTransaction, long a_lAttributeId, long a_lEmbeddedDataValueId, long a_lMappingDirectionId, long a_lTypeId)
/*      */     throws Bn2Exception
/*      */   {
/* 4838 */     String ksMethodName = "deleteEmbeddedDataMapping";
/*      */ 
/* 4840 */     Connection con = null;
/* 4841 */     PreparedStatement psql = null;
/* 4842 */     String sSQL = null;
/*      */     try
/*      */     {
/* 4846 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 4849 */       sSQL = "DELETE FROM EmbeddedDataMapping WHERE AttributeId=? AND EmbeddedDataValueId=? AND MappingDirectionId=?";
/* 4850 */       psql = con.prepareStatement(sSQL);
/* 4851 */       int iCol = 1;
/* 4852 */       psql.setLong(iCol++, a_lAttributeId);
/* 4853 */       psql.setLong(iCol++, a_lEmbeddedDataValueId);
/* 4854 */       psql.setLong(iCol++, a_lMappingDirectionId);
/* 4855 */       psql.executeUpdate();
/*      */ 
/* 4857 */       psql.close();
/*      */ 
/* 4859 */       refreshMappingCache(a_lTypeId);
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 4863 */       this.m_logger.error("AttributeManager.deleteEmbeddedDataMapping - " + sqe);
/* 4864 */       throw new Bn2Exception("AttributeManager.deleteEmbeddedDataMapping", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getEmbeddedDataMappings(DBTransaction a_dbTransaction, long a_lTypeId, long a_lDirectionId)
/*      */     throws Bn2Exception
/*      */   {
/* 4885 */     Vector vecRet = null;
/* 4886 */     Vector vecAll = getEmbeddedDataMappings(a_dbTransaction, a_lTypeId);
/*      */ 
/* 4889 */     if ((a_lDirectionId <= 0L) || (vecAll == null))
/*      */     {
/* 4891 */       return vecAll;
/*      */     }
/*      */ 
/* 4894 */     vecRet = new Vector();
/*      */ 
/* 4897 */     for (int i = 0; i < vecAll.size(); i++)
/*      */     {
/* 4899 */       if (((EmbeddedDataMapping)vecAll.get(i)).getDirection().getId() != a_lDirectionId)
/*      */         continue;
/* 4901 */       vecRet.add(vecAll.get(i));
/*      */     }
/*      */ 
/* 4905 */     return vecRet;
/*      */   }
/*      */ 
/*      */   public Vector getEmbeddedDataMappings(DBTransaction a_dbTransaction, long a_lTypeId)
/*      */     throws Bn2Exception
/*      */   {
/* 4925 */     Vector vecMappings = null;
/*      */ 
/* 4927 */     if (a_lTypeId <= 0L)
/*      */     {
/* 4929 */       vecMappings = this.m_vecAllMappings;
/*      */     }
/* 4933 */     else if (this.m_hmEmbeddedDataMappings.containsKey(new Long(a_lTypeId)))
/*      */     {
/* 4935 */       vecMappings = (Vector)this.m_hmEmbeddedDataMappings.get(new Long(a_lTypeId));
/*      */     }
/*      */ 
/* 4939 */     if (vecMappings == null)
/*      */     {
/* 4942 */       Connection con = null;
/* 4943 */       ResultSet rs = null;
/* 4944 */       DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 4946 */       if (transaction == null)
/*      */       {
/* 4948 */         transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/* 4953 */         con = transaction.getConnection();
/*      */ 
/* 4955 */         String sSQL = "SELECT edv.Id edvId, edv.Name edvName, edv.Expression edvExpression, edt.Id edtId, edt.Name edtName, md.Id mdId, md.Name mdName, a.Id aId, a.Label aLabel, edm.DataDelim, edm.Sequence, edm.IsBinary FROM EmbeddedDataMapping edm JOIN EmbeddedDataValue edv ON edm.EmbeddedDataValueId=edv.Id JOIN EmbeddedDataType edt ON edv.EmbeddedDataTypeId=edt.Id JOIN Attribute a ON edm.AttributeId=a.Id JOIN MappingDirection md ON edm.MappingDirectionId=md.Id ";
/*      */ 
/* 4964 */         if (a_lTypeId > 0L)
/*      */         {
/* 4966 */           sSQL = sSQL + " WHERE edv.EmbeddedDataTypeId=?";
/*      */         }
/*      */ 
/* 4969 */         sSQL = sSQL + " ORDER BY edm.Sequence";
/*      */ 
/* 4972 */         PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/* 4974 */         if (a_lTypeId > 0L)
/*      */         {
/* 4976 */           psql.setLong(1, a_lTypeId);
/*      */         }
/*      */ 
/* 4979 */         rs = psql.executeQuery();
/*      */ 
/* 4981 */         while (rs.next())
/*      */         {
/* 4983 */           if (vecMappings == null)
/*      */           {
/* 4985 */             vecMappings = new Vector();
/*      */           }
/*      */ 
/* 4989 */           EmbeddedDataMapping mapping = buildMapping(rs);
/* 4990 */           vecMappings.add(mapping);
/*      */         }
/*      */ 
/* 4993 */         psql.close();
/*      */ 
/* 4996 */         synchronized (this.m_oMappingLock)
/*      */         {
/* 4998 */           if (a_lTypeId > 0L)
/*      */           {
/* 5000 */             this.m_hmEmbeddedDataMappings.put(new Long(a_lTypeId), vecMappings);
/*      */           }
/*      */           else
/*      */           {
/* 5004 */             this.m_vecAllMappings = vecMappings;
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 5010 */         this.m_logger.error("SQL Exception whilst getting mappings from the database : " + e);
/* 5011 */         throw new Bn2Exception("SQL Exception whilst getting mappings from the database : " + e, e);
/*      */       }
/*      */       finally
/*      */       {
/* 5016 */         if ((a_dbTransaction == null) && (transaction != null))
/*      */         {
/*      */           try
/*      */           {
/* 5020 */             transaction.commit();
/*      */           }
/*      */           catch (SQLException sqle)
/*      */           {
/* 5024 */             this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 5030 */     return vecMappings;
/*      */   }
/*      */ 
/*      */   public boolean populateAssetFromEmbeddedDataMappings(long a_lUserId, long a_lAssetId, Vector a_vecAssetAttributes)
/*      */     throws Bn2Exception, AssetNotFoundException
/*      */   {
/* 5046 */     boolean bExtractedData = false;
/* 5047 */     DBTransaction dbTransaction = null;
/*      */     try
/*      */     {
/* 5051 */       Vector vecAllMappings = getEmbeddedDataMappings(null, -1L);
/* 5052 */       if ((vecAllMappings != null) && (vecAllMappings.size() > 0))
/*      */       {
/* 5055 */         dbTransaction = this.m_transactionManager.getNewTransaction();
/* 5056 */         Asset newAsset = this.m_assetManager.getAsset(dbTransaction, a_lAssetId, null, false, true);
/* 5057 */         dbTransaction.commit();
/* 5058 */         dbTransaction = null;
/*      */ 
/* 5062 */         String sFilelocation = newAsset.getFileLocation();
/*      */ 
/* 5064 */         if (StringUtil.stringIsPopulated(sFilelocation))
/*      */         {
/* 5066 */           String sImageFullPath = null;
/*      */ 
/* 5068 */           if (StringUtil.stringIsPopulated(newAsset.getOriginalFileLocation()))
/*      */           {
/* 5070 */             sImageFullPath = this.m_fileStoreManager.getAbsolutePath(newAsset.getOriginalFileLocation());
/*      */           }
/*      */           else
/*      */           {
/* 5074 */             sImageFullPath = this.m_fileStoreManager.getAbsolutePath(newAsset.getFileLocation());
/*      */           }
/*      */ 
/* 5078 */           populateEmbeddedMetadataValues(sImageFullPath, newAsset, a_vecAssetAttributes, true);
/*      */ 
/* 5084 */           AssetConversionInfo conversionInfo = null;
/* 5085 */           if (newAsset.getAutoRotate() > 0)
/*      */           {
/* 5088 */             ImageConversionInfo imageConversionInfo = new ImageConversionInfo();
/* 5089 */             imageConversionInfo.setRotationAngle(newAsset.getAutoRotate());
/* 5090 */             conversionInfo = imageConversionInfo;
/*      */ 
/* 5094 */             ImageMagick.clearCaches();
/*      */           }
/*      */ 
/* 5098 */           dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/* 5102 */           for (int i = 0; i < newAsset.getAttributeValues().size(); i++)
/*      */           {
/* 5104 */             AttributeValue attVal = (AttributeValue)newAsset.getAttributeValues().get(i);
/*      */ 
/* 5106 */             if (!attVal.getAttribute().getIsKeywordPicker())
/*      */               continue;
/* 5108 */             this.m_taxonomyManager.checkKeywordAutoAdd(dbTransaction, attVal.getKeywordCategories(), attVal.getAttribute().getTreeId(), AssetBankSettings.getKeywordAutoAdd());
/*      */           }
/*      */ 
/* 5117 */           this.m_assetManager.saveAsset(dbTransaction, newAsset, null, a_lUserId, conversionInfo, null, false, 0);
/*      */ 
/* 5126 */           dbTransaction.commit();
/* 5127 */           dbTransaction = null;
/* 5128 */           bExtractedData = true;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       throw new Bn2Exception("AttributeManager.populateAssetFromEmbeddedDataMappings: sql exception", e);
/*      */     }
/*      */     finally
/*      */     {
/* 5150 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/* 5154 */           dbTransaction.commit();
/*      */         }
/*      */         catch (SQLException se)
/*      */         {
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 5163 */     return bExtractedData;
/*      */   }
/*      */ 
/*      */   public EmbeddedDataMapping getEmbeddedDataMapping(DBTransaction a_dbTransaction, long a_lAttributeId, long a_lEmbeddedDataValueId, long a_lMappingDirectionId)
/*      */     throws Bn2Exception
/*      */   {
/* 5188 */     EmbeddedDataMapping mapping = null;
/*      */ 
/* 5190 */     Connection con = null;
/* 5191 */     ResultSet rs = null;
/* 5192 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 5194 */     if (transaction == null)
/*      */     {
/* 5196 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 5201 */       con = transaction.getConnection();
/*      */ 
/* 5203 */       String sSQL = "SELECT edv.Id edvId, edv.Name edvName, edv.Expression edvExpression, edt.Id edtId, edt.Name edtName, md.Id mdId, md.Name mdName, a.Id aId, a.Label aLabel, edm.DataDelim, edm.Sequence, edm.IsBinary FROM EmbeddedDataMapping edm JOIN EmbeddedDataValue edv ON edm.EmbeddedDataValueId=edv.Id JOIN EmbeddedDataType edt ON edv.EmbeddedDataTypeId=edt.Id JOIN Attribute a ON edm.AttributeId=a.Id JOIN MappingDirection md ON edm.MappingDirectionId=md.Id WHERE edm.AttributeId=? AND edm.EmbeddedDataValueId=? AND edm.MappingDirectionId=?";
/*      */ 
/* 5214 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 5215 */       psql.setLong(1, a_lAttributeId);
/* 5216 */       psql.setLong(2, a_lEmbeddedDataValueId);
/* 5217 */       psql.setLong(3, a_lMappingDirectionId);
/*      */ 
/* 5219 */       rs = psql.executeQuery();
/*      */ 
/* 5221 */       if (rs.next())
/*      */       {
/* 5224 */         mapping = buildMapping(rs);
/*      */       }
/*      */ 
/* 5227 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 5231 */       this.m_logger.error("SQL Exception whilst getting mappings from the database : " + e);
/* 5232 */       throw new Bn2Exception("SQL Exception whilst getting mappings from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 5237 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 5241 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 5245 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 5250 */     return mapping;
/*      */   }
/*      */ 
/*      */   public List<String> getNextAutoincrementValues(DBTransaction a_dbTransaction, long a_lId, int a_iNumValues)
/*      */     throws Bn2Exception
/*      */   {
/* 5271 */     List values = null;
/*      */ 
/* 5273 */     Connection con = null;
/* 5274 */     ResultSet rs = null;
/* 5275 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 5277 */     String sPrefix = "";
/* 5278 */     int iWidth = 0;
/*      */ 
/* 5280 */     if (transaction == null)
/*      */     {
/* 5282 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 5287 */       con = transaction.getConnection();
/* 5288 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/* 5291 */       String sSQL = "SELECT Seed, IncrementAmount, Prefix, Width FROM Attribute WHERE Id=?";
/* 5292 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 5293 */       psql.setLong(1, a_lId);
/* 5294 */       rs = psql.executeQuery();
/* 5295 */       rs.next();
/*      */ 
/* 5297 */       long lSeed = rs.getLong("Seed");
/* 5298 */       int iIncrement = rs.getInt("IncrementAmount");
/* 5299 */       sPrefix = rs.getString("Prefix");
/* 5300 */       if (sPrefix == null)
/*      */       {
/* 5302 */         sPrefix = "";
/*      */       }
/*      */       else
/*      */       {
/* 5306 */         sPrefix = sPrefix.trim();
/*      */       }
/* 5308 */       iWidth = rs.getInt("Width");
/*      */ 
/* 5310 */       psql.close();
/*      */ 
/* 5312 */       sSQL = sqlGenerator.getAsSelectForUpdate("SELECT NumberValue FROM LastAttributeValue WHERE AttributeId=?");
/* 5313 */       psql = con.prepareStatement(sSQL);
/* 5314 */       psql.setLong(1, a_lId);
/* 5315 */       rs = psql.executeQuery();
/*      */       boolean bFirstValue;
/*      */       long lNextValue;
/*      */       //boolean bFirstValue;
/* 5319 */       if (rs.next())
/*      */       {
/* 5321 */          lNextValue = rs.getLong("NumberValue") + iIncrement;
/* 5322 */         bFirstValue = false;
/*      */       }
/*      */       else
/*      */       {
/* 5326 */         lNextValue = lSeed;
/* 5327 */         bFirstValue = true;
/*      */       }
/* 5329 */       psql.close();
/*      */ 
/* 5331 */       values = new ArrayList(a_iNumValues);
/*      */ 
/* 5333 */       for (int i = 0; i < a_iNumValues; i++)
/*      */       {
/* 5335 */         values.add(sPrefix + StringUtil.padLeadingZeros(lNextValue, iWidth));
/* 5336 */         lNextValue += iIncrement;
/*      */       }
/*      */ 
/* 5339 */       if (bFirstValue)
/*      */       {
/* 5341 */         sSQL = "INSERT INTO LastAttributeValue (NumberValue,AttributeId) VALUES (?,?)";
/*      */       }
/*      */       else
/*      */       {
/* 5345 */         sSQL = "UPDATE LastAttributeValue SET NumberValue=? WHERE AttributeId=?";
/*      */       }
/* 5347 */       psql = con.prepareStatement(sSQL);
/* 5348 */       psql.setLong(1, lNextValue - iIncrement);
/* 5349 */       psql.setLong(2, a_lId);
/* 5350 */       psql.executeUpdate();
/* 5351 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 5355 */       this.m_logger.error("SQL Exception whilst getting next autoincrement values : " + e);
/* 5356 */       throw new Bn2Exception("SQL Exception whilst getting next autoincrement values : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 5361 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 5365 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 5369 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 5374 */     return values;
/*      */   }
/*      */ 
/*      */   public String getNextAutoincrementValue(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 5394 */     List values = getNextAutoincrementValues(a_dbTransaction, a_lId, 1);
/*      */ 
/* 5396 */     if ((values != null) && (values.size() > 0))
/*      */     {
/* 5398 */       return (String)values.get(0);
/*      */     }
/* 5400 */     return null;
/*      */   }
/*      */ 
/*      */   private EmbeddedDataType buildEmbeddedDataType(ResultSet a_rs)
/*      */     throws SQLException
/*      */   {
/* 5406 */     EmbeddedDataType type = new EmbeddedDataType();
/* 5407 */     type.setId(a_rs.getLong("edtId"));
/* 5408 */     type.setName(a_rs.getString("edtName"));
/*      */ 
/* 5410 */     return type;
/*      */   }
/*      */ 
/*      */   private EmbeddedDataValue buildEmbeddedDataValue(ResultSet a_rs)
/*      */     throws SQLException
/*      */   {
/* 5416 */     EmbeddedDataType type = buildEmbeddedDataType(a_rs);
/*      */ 
/* 5418 */     EmbeddedDataValue value = new EmbeddedDataValue();
/* 5419 */     value.setId(a_rs.getLong("edvId"));
/* 5420 */     value.setName(a_rs.getString("edvName"));
/* 5421 */     value.setExpression(a_rs.getString("edvExpression"));
/* 5422 */     value.setType(type);
/*      */ 
/* 5424 */     return value;
/*      */   }
/*      */ 
/*      */   private MappingDirection buildMappingDirection(ResultSet a_rs)
/*      */     throws SQLException
/*      */   {
/* 5430 */     MappingDirection direction = new MappingDirection();
/* 5431 */     direction.setId(a_rs.getLong("mdId"));
/* 5432 */     direction.setName(a_rs.getString("mdName"));
/*      */ 
/* 5434 */     return direction;
/*      */   }
/*      */ 
/*      */   public EmbeddedDataMapping buildMapping(ResultSet a_rs)
/*      */     throws SQLException
/*      */   {
/* 5440 */     MappingDirection direction = buildMappingDirection(a_rs);
/*      */ 
/* 5442 */     EmbeddedDataValue value = buildEmbeddedDataValue(a_rs);
/*      */ 
/* 5444 */     Attribute attribute = new Attribute();
/* 5445 */     attribute.setId(a_rs.getLong("aId"));
/* 5446 */     attribute.setLabel(a_rs.getString("aLabel"));
/*      */ 
/* 5448 */     EmbeddedDataMapping mapping = new EmbeddedDataMapping();
/* 5449 */     mapping.setAttribute(attribute);
/* 5450 */     mapping.setAttributeId(a_rs.getLong("aId"));
/* 5451 */     mapping.setValue(value);
/* 5452 */     mapping.setDirection(direction);
/* 5453 */     mapping.setDelimiter(a_rs.getString("DataDelim"));
/* 5454 */     mapping.setSequence(a_rs.getInt("Sequence"));
/* 5455 */     mapping.setBinaryData(a_rs.getBoolean("IsBinary"));
/* 5456 */     mapping.setAttributeId(attribute.getId());
/* 5457 */     mapping.setEmbeddedDataValueId(value.getId());
/* 5458 */     mapping.setMappingDirectionId(direction.getId());
/*      */ 
/* 5460 */     return mapping;
/*      */   }
/*      */ 
/*      */   public void findAndSetAttributeValue(Asset a_asset, Vector a_vecAttributes, long a_lAttributeId, String a_sValue, String a_sAppendDelimiter, boolean a_bPopulateTranslationDefaults)
/*      */     throws Bn2Exception
/*      */   {
/* 5499 */     Attribute attribute = null;
/*      */ 
/* 5501 */     for (int iAttIndex = 0; iAttIndex < a_vecAttributes.size(); iAttIndex++)
/*      */     {
/* 5503 */       attribute = (Attribute)a_vecAttributes.get(iAttIndex);
/*      */ 
/* 5506 */       if (attribute.getId() == a_lAttributeId)
/*      */       {
/*      */         break;
/*      */       }
/*      */ 
/* 5511 */       attribute = null;
/*      */     }
/*      */ 
/* 5514 */     if (attribute != null)
/*      */     {
/* 5516 */       AttributeValue attVal = null;
/*      */ 
/* 5519 */       if ((attribute.getIsDropdownList()) || (attribute.getIsCheckList()))
/*      */       {
/* 5521 */         String sListOptionValue = null;
/*      */ 
/* 5524 */         if ((!attribute.getIsVisible()) && (attribute.getValueIfNotVisible() != null))
/*      */         {
/* 5526 */           sListOptionValue = attribute.getValueIfNotVisible();
/*      */         }
/*      */         else
/*      */         {
/* 5530 */           sListOptionValue = a_sValue;
/*      */         }
/*      */ 
/* 5534 */         attVal = AttributeUtil.getListOptionValue(attribute.getListOptionValues(), sListOptionValue);
/*      */ 
/* 5537 */         if (attVal != null)
/*      */         {
/* 5539 */           attVal.setIsSelected(true);
/*      */         }
/*      */ 
/*      */       }
/* 5544 */       else if (attribute.getIsAutoincrement())
/*      */       {
/* 5546 */         attVal = attribute.getValue();
/* 5547 */         attVal.setAttribute(attribute);
/*      */ 
/* 5551 */         attVal.setValue(null);
/*      */ 
/* 5553 */         if (attribute.getIsRelevant())
/*      */         {
/* 5555 */           DBTransaction transaction = null;
/*      */           try
/*      */           {
/* 5559 */             transaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/* 5561 */             String sNextValue = getNextAutoincrementValue(transaction, a_lAttributeId);
/*      */ 
/* 5563 */             attVal.setValue(sNextValue);
/*      */           }
/*      */           finally
/*      */           {
/* 5568 */             if (transaction != null)
/*      */             {
/*      */               try
/*      */               {
/* 5572 */                 transaction.commit();
/*      */               }
/*      */               catch (SQLException sqle)
/*      */               {
/* 5576 */                 this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 5586 */         attVal = attribute.getValue();
/*      */ 
/* 5588 */         attVal.setAttribute(attribute);
/*      */ 
/* 5591 */         if (!attribute.getIsVisible())
/*      */         {
/* 5594 */           attVal.setValue(attribute.getValueIfNotVisible());
/*      */         }
/* 5599 */         else if (attribute.getIsKeywordPicker())
/*      */         {
/* 5601 */           DBTransaction transaction = null;
/*      */           try
/*      */           {
/* 5605 */             transaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/* 5607 */             Vector vecKeywords = this.m_taxonomyManager.getKeywordStatusList(transaction, a_sValue, attribute.getTreeId(), LanguageConstants.k_defaultLanguage, false);
/*      */ 
/* 5614 */             attVal.setKeywordCategories(vecKeywords);
/*      */           }
/*      */           finally
/*      */           {
/* 5619 */             if (transaction != null)
/*      */             {
/*      */               try
/*      */               {
/* 5623 */                 transaction.commit();
/*      */               }
/*      */               catch (SQLException sqle)
/*      */               {
/* 5627 */                 this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 5636 */           if (((attribute.getIsTextfield()) || (attribute.getIsTextarea())) && (a_sAppendDelimiter != null) && (a_sAppendDelimiter.length() > 0) && (attVal.getValue() != null) && (attVal.getValue().length() > 0))
/*      */           {
/* 5642 */             attVal.setValue(attVal.getValue() + a_sAppendDelimiter + a_sValue);
/*      */           }
/* 5644 */           else if ((a_sValue != null) && (a_sValue.length() > 0))
/*      */           {
/* 5646 */             a_sValue = AttributeValueUtil.substituteCalculatedDate(attribute, a_sValue);
/*      */ 
/* 5649 */             attVal.setValue(a_sValue);
/*      */           }
/*      */ 
/* 5654 */           Iterator itTranslations = attribute.getTranslations().iterator();
/* 5655 */           while (itTranslations.hasNext())
/*      */           {
/* 5657 */             Attribute.Translation translation = (Attribute.Translation)itTranslations.next();
/*      */            // AttributeValue tmp640_638 = attVal; tmp640_638.getClass(); AttributeValue.Translation valueTranslation = new AttributeValue.Translation(tmp640_638, translation.getLanguage());
/* 5659 */             AttributeValue.Translation valueTranslation = attVal.new Translation( translation.getLanguage());
                        valueTranslation.setAttributeTranslation(translation);
/* 5660 */             if ((a_bPopulateTranslationDefaults) && (StringUtils.isNotEmpty(translation.getDefaultValue())))
/*      */             {
/* 5662 */               valueTranslation.setValue(translation.getDefaultValue());
/*      */             }
/* 5664 */             attVal.getTranslations().add(valueTranslation);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 5671 */       if (attVal != null)
/*      */       {
/* 5674 */         Vector vecTemp = new Vector();
/* 5675 */         vecTemp.add(attVal);
/* 5676 */         AttributeUtil.setAttributeValuesForAttributeInAsset(a_asset, vecTemp, false);
/*      */ 
/* 5679 */         attribute.setValue(attVal);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void hideAttribute(DBTransaction a_transaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 5690 */     setAttributeVisibility(a_transaction, a_lId, true);
/*      */   }
/*      */ 
/*      */   public void showAttribute(DBTransaction a_transaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 5696 */     setAttributeVisibility(a_transaction, a_lId, false);
/*      */   }
/*      */ 
/*      */   private void setAttributeVisibility(DBTransaction a_transaction, long a_lAttributeId, boolean a_bHide)
/*      */     throws Bn2Exception
/*      */   {
/* 5702 */     DBTransaction transaction = a_transaction;
/*      */     try
/*      */     {
/* 5706 */       if (transaction == null)
/*      */       {
/* 5708 */         transaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/* 5710 */       Connection con = transaction.getConnection();
/*      */ 
/* 5713 */       String sSQL = "UPDATE Attribute SET Hidden=? WHERE Id=?";
/* 5714 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 5715 */       psql.setBoolean(1, a_bHide);
/* 5716 */       psql.setLong(2, a_lAttributeId);
/* 5717 */       psql.executeUpdate();
/* 5718 */       psql.close();
/*      */ 
/* 5720 */       invalidateAttributeCache(a_lAttributeId);
/* 5721 */       optimiseAttributeSequences(transaction);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */       throw new Bn2Exception(getClass().getSimpleName() + ": Error: ", e);
/*      */     }
/*      */     finally
/*      */     {
/* 5734 */       if ((transaction != null) && (a_transaction == null))
/*      */         try {
/* 5736 */           transaction.commit();
/*      */         }
/*      */         catch (Exception se)
/*      */         {
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void populateMetadataDefaults(Asset a_asset, Vector a_vecAssetAttributes, boolean a_bPopulateFlexibleAttributes)
/*      */     throws Bn2Exception
/*      */   {
/* 5764 */     Vector vecAttPositions = getAttributePositionList();
/* 5765 */     Attribute attPos = null;
/*      */ 
/* 5767 */     for (int i = 0; i < vecAttPositions.size(); i++)
/*      */     {
/* 5769 */       attPos = (Attribute)vecAttPositions.get(i);
/*      */ 
/* 5772 */       if (((attPos.getDefaultValue() == null) || (attPos.getIsHyperlink())) && (!attPos.getIsAutoincrement())) {
/*      */         continue;
/*      */       }
/* 5775 */       if (attPos.getStatic())
/*      */       {
/* 5778 */         if (!attPos.getFieldName().equals("price"))
/*      */           continue;
/* 5780 */         a_asset.getPrice().setFormAmount(attPos.getDefaultValue());
/* 5781 */         a_asset.getPrice().processFormData();
/*      */       }
/*      */       else
/*      */       {
/* 5786 */         if ((!a_bPopulateFlexibleAttributes) && (!attPos.getIsAutoincrement()))
/*      */           continue;
/* 5788 */         String sValueToSet = "";
/*      */ 
/* 5791 */         Attribute att = (Attribute)a_vecAssetAttributes.get(0);
/* 5792 */         Language lang = att.getLanguage();
/*      */ 
/* 5795 */         if ((!lang.isDefault()) && (attPos.getIsList()))
/*      */         {
/* 5798 */           AttributeValue av = this.m_attributeValueManager.getListAttributeValue(null, attPos.getId(), LanguageConstants.k_defaultLanguage, attPos.getDefaultValue());
/*      */ 
/* 5803 */           if (av != null)
/*      */           {
/* 5806 */             sValueToSet = av.getValue(lang);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 5811 */           sValueToSet = attPos.getDefaultValue();
/*      */         }
/*      */ 
/* 5816 */         findAndSetAttributeValue(a_asset, a_vecAssetAttributes, attPos.getId(), sValueToSet, null, true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String getExifToolKey(EmbeddedDataMapping a_mapping)
/*      */   {
/* 5838 */     String sKey = "";
/*      */ 
/* 5841 */     if (a_mapping.getValue().getType().getId() != 6L)
/*      */     {
/* 5843 */       sKey = sKey + a_mapping.getValue().getType().getName() + ":";
/*      */     }
/*      */ 
/* 5846 */     sKey = sKey + a_mapping.getValue().getExpression();
/*      */ 
/* 5848 */     return sKey;
/*      */   }
/*      */ 
/*      */   public Vector<IdValueBean> dataLookup(DBTransaction a_dbTransaction, DataLookupRequest a_dlr)
/*      */     throws Bn2Exception
/*      */   {
/* 5869 */     Vector vecValues = null;
/* 5870 */     if (a_dlr != null)
/*      */     {
/* 5872 */       Attribute attribute = getAttribute(a_dbTransaction, a_dlr.getAttributeId());
/* 5873 */       if ((attribute != null) && (StringUtil.stringIsPopulated(attribute.getPluginClass())))
/*      */       {
/* 5876 */         DataLookupPlugin plugin = DataLookupPluginFactory.getPluginInstance(attribute.getPluginClass());
/*      */ 
/* 5879 */         String sAllParams = "";
/* 5880 */         if (StringUtil.stringIsPopulated(attribute.getAttributeIdsForPluginParams()))
/*      */         {
/* 5883 */           String[] aAttributeIds = attribute.getAttributeIdsForPluginParams().split(",");
/* 5884 */           for (String sId : aAttributeIds)
/*      */           {
/* 5887 */             String sParamName = "attribute_" + sId;
/* 5888 */             String sValue = a_dlr.getRequest().getParameter(sParamName);
/*      */ 
/* 5890 */             if (!StringUtil.stringIsPopulated(sValue))
/*      */               continue;
/* 5892 */             sAllParams = sAllParams + sValue;
/* 5893 */             sAllParams = sAllParams + ",";
/*      */           }
/*      */ 
/* 5898 */           if (StringUtil.stringIsPopulated(sAllParams))
/*      */           {
/* 5900 */             sAllParams = sAllParams.substring(0, sAllParams.length() - 1);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 5905 */         vecValues = plugin.getAttributeValueMap(sAllParams);
/*      */       }
/*      */     }
/* 5908 */     return vecValues;
/*      */   }
/*      */ 
/*      */   public boolean downloadAttributesExist()
/*      */     throws Bn2Exception
/*      */   {
/* 5917 */     Vector<Attribute> atts = getAttributePositionList();
/*      */ 
/* 5919 */     for (Attribute attribute : atts)
/*      */     {
/* 5921 */       if (attribute.getShowOnDownload())
/*      */       {
/* 5923 */         return true;
/*      */       }
/*      */     }
/* 5926 */     return false;
/*      */   }
/*      */ 
/*      */   private DisplayAttribute buildDisplayAttribute(ResultSet a_rs, Attribute a_attribute)
/*      */     throws SQLException
/*      */   {
/* 5933 */     DisplayAttribute disAttribute = new DisplayAttribute();
/* 5934 */     disAttribute.setAttribute(a_attribute);
/* 5935 */     disAttribute.setDisplayLength(a_rs.getInt("DisplayLength"));
/* 5936 */     disAttribute.setSequenceNumber(a_rs.getInt("daSequence"));
/* 5937 */     disAttribute.setShowLabel(a_rs.getBoolean("ShowLabel"));
/* 5938 */     disAttribute.setIsLink(a_rs.getBoolean("IsLink"));
/* 5939 */     disAttribute.setShowOnChild(a_rs.getBoolean("daShowOnChild"));
/* 5940 */     disAttribute.setGroupId(a_rs.getLong("DisplayAttributeGroupId"));
/* 5941 */     return disAttribute;
/*      */   }
/*      */ 
/*      */   public void setAssetManager(IAssetManager a_sAssetManager)
/*      */   {
/* 5949 */     this.m_assetManager = a_sAssetManager;
/*      */   }
/*      */ 
/*      */   public void setAttributeStorageManager(AttributeStorageManager a_attributeStorageManager)
/*      */   {
/* 5954 */     this.m_attributeStorageManager = a_attributeStorageManager;
/*      */   }
/*      */ 
/*      */   public void setAttributeValueManager(AttributeValueManager a_attributeValueManager)
/*      */   {
/* 5959 */     this.m_attributeValueManager = a_attributeValueManager;
/*      */   }
/*      */ 
/*      */   public void setCategoryManager(CategoryManager a_categoryManager)
/*      */   {
/* 5964 */     this.m_categoryManager = a_categoryManager;
/*      */   }
/*      */ 
/*      */   public DBTransactionManager getTransactionManager()
/*      */   {
/* 5970 */     return this.m_transactionManager;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*      */   {
/* 5976 */     this.m_transactionManager = a_sTransactionManager;
/*      */   }
/*      */ 
/*      */   public void setTaxonomyManager(TaxonomyManager a_taxonomyManager)
/*      */   {
/* 5981 */     this.m_taxonomyManager = a_taxonomyManager;
/*      */   }
/*      */ 
/*      */   public void setFileAssetManager(FileAssetManagerImpl a_fileAssetManager)
/*      */   {
/* 5986 */     this.m_fileAssetManager = a_fileAssetManager;
/*      */   }
/*      */ 
/*      */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*      */   {
/* 5991 */     this.m_fileStoreManager = a_fileStoreManager;
/*      */   }
/*      */ 
/*      */   public AssetEntityManager getAssetEntityManager()
/*      */   {
/* 5996 */     return this.m_assetEntityManager;
/*      */   }
/*      */ 
/*      */   public void setAssetEntityManager(AssetEntityManager a_sAssetEntityManager) {
/* 6000 */     this.m_assetEntityManager = a_sAssetEntityManager;
/*      */   }
//11/27/2011
    public AttributeValue getAttributeValueId(DBTransaction object, long id, String string, boolean b) throws Bn2Exception{
     return this.m_attributeValueManager.getListAttributeValueId(null, id, string, b);
    }
/*      */ 
/*      */   private static class GetAttributesArgs
/*      */   {
/*      */     private long m_lTypeId;
/*      */     private boolean m_bIncludeHeadings;
/*      */     private boolean m_bIgnoreSettings;
/*  743 */     private int m_iVisibleStatus = -1;
/*      */     private boolean m_bNameAttribute;
/*      */ 
/*      */     private GetAttributesArgs(long a_lTypeId, boolean a_bIncludeHeadings, boolean a_bIgnoreSettings, int a_iVisibleStatus, boolean a_bNameAttribute)
/*      */     {
/*  748 */       this.m_lTypeId = a_lTypeId;
/*  749 */       this.m_bIncludeHeadings = a_bIncludeHeadings;
/*  750 */       this.m_bIgnoreSettings = a_bIgnoreSettings;
/*  751 */       this.m_iVisibleStatus = a_iVisibleStatus;
/*  752 */       this.m_bNameAttribute = a_bNameAttribute;
/*      */     }
/*      */ 
/*      */     public boolean equals(Object o)
/*      */     {
/*  758 */       if (this == o) return true;
/*  759 */       if ((o == null) || (getClass() != o.getClass())) return false;
/*      */ 
/*  761 */       GetAttributesArgs that = (GetAttributesArgs)o;
/*      */ 
/*  763 */       if (this.m_bIgnoreSettings != that.m_bIgnoreSettings) return false;
/*  764 */       if (this.m_bIncludeHeadings != that.m_bIncludeHeadings) return false;
/*  765 */       if (this.m_lTypeId != that.m_lTypeId) return false;
/*  766 */       if (this.m_iVisibleStatus != that.m_iVisibleStatus) return false;
/*  767 */       return this.m_bNameAttribute == that.m_bNameAttribute;
/*      */     }
/*      */ 
/*      */     public int hashCode()
/*      */     {
/*  774 */       int prime = 31;
/*  775 */       int result = 1;
/*  776 */       result = 31 * result + (this.m_bIgnoreSettings ? 1231 : 1237);
/*  777 */       result = 31 * result + (this.m_bIncludeHeadings ? 1231 : 1237);
/*  778 */       result = 31 * result + (this.m_bNameAttribute ? 1231 : 1237);
/*  779 */       result = 31 * result + this.m_iVisibleStatus;
/*  780 */       result = 31 * result + (int)(this.m_lTypeId ^ this.m_lTypeId >>> 32);
/*  781 */       return result;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class GetAttributeArgs
/*      */   {
/*      */     private long m_lId;
/*      */     private String m_sStaticFieldName;
/*      */ 
/*      */     private GetAttributeArgs(long a_lId, String a_sStaticFieldName)
/*      */     {
/*  532 */       this.m_lId = a_lId;
/*  533 */       this.m_sStaticFieldName = a_sStaticFieldName;
/*      */     }
/*      */ 
/*      */     public boolean equals(Object o)
/*      */     {
/*  539 */       if (this == o) return true;
/*  540 */       if ((o == null) || (getClass() != o.getClass())) return false;
/*      */ 
/*  542 */       GetAttributeArgs that = (GetAttributeArgs)o;
/*      */ 
/*  544 */       if (this.m_lId != that.m_lId) return false;
/*      */ 
/*  546 */       return this.m_sStaticFieldName != null ? this.m_sStaticFieldName.equals(that.m_sStaticFieldName) : that.m_sStaticFieldName == null;
/*      */     }
/*      */ 
/*      */     public int hashCode()
/*      */     {
/*  554 */       int result = (int)(this.m_lId ^ this.m_lId >>> 32);
/*  555 */       result = 31 * result + (this.m_sStaticFieldName != null ? this.m_sStaticFieldName.hashCode() : 0);
/*  556 */       return result;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.service.AttributeManager
 * JD-Core Version:    0.6.0
 */