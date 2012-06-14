/*      */ package com.bright.assetbank.usage.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bright.assetbank.application.bean.ImageAsset;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.service.AssetManager;
/*      */ import com.bright.assetbank.application.util.ABImageMagick;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.ecommerce.service.OrderManager;
/*      */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*      */ import com.bright.assetbank.usage.bean.AssetUseLogEntry;
/*      */ import com.bright.assetbank.usage.bean.ColorSpace;
/*      */ import com.bright.assetbank.usage.bean.UsageType;
/*      */ import com.bright.assetbank.usage.bean.UsageType.Translation;
/*      */ import com.bright.assetbank.usage.bean.UsageTypeFormat;
/*      */ //import com.bright.assetbank.usage.bean.UsageTypeFormat.Translation;
/*      */ import com.bright.assetbank.usage.bean.UsageTypeSelectOption;
/*      */ import com.bright.assetbank.usage.constant.UsageConstants;
/*      */ import com.bright.assetbank.usage.util.UsageTypeFormatDBUtil;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.framework.common.service.ScheduleManager;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.language.util.LanguageUtils;
/*      */ import com.bright.framework.queue.bean.QueuedItem;
/*      */ import com.bright.framework.queue.service.QueueManager;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.Stack;
/*      */ import java.util.TimerTask;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class UsageManager extends QueueManager
/*      */   implements UsageConstants
/*      */ {
/*      */   public static final long k_iGetAllRecordsId = -9223372036854775808L;
/*      */   private static final String c_ksClassName = "UsageManager";
/*  132 */   private DBTransactionManager m_transactionManager = null;
/*  133 */   private ScheduleManager m_scheduleManager = null;
/*  134 */   private OrderManager m_orderManager = null;
/*      */ 
/*  136 */   private AssetManager m_assetManager = null;
/*  137 */   private MultiLanguageSearchManager m_searchManager = null;
/*      */ 
/*  139 */   private Set m_assetIdsToIndex = null;
/*  140 */   private volatile long m_lLastReindexEndTime = 0L;
/*      */   public static final String c_ksUsageTypeFields = "ut.Id utId, ut.Description utDescription, ut.IsEditable utIsEditable, ut.DownloadTabId utDownloadTabId, ut.AssetTypeId utAssetTypeId, ut.ParentId, ut.CanEnterDetails utCanEnterDetails, ut.DetailsMandatory utDetailsMandatory, ut.Message utMessage, ut.DownloadOriginal utDownloadOriginal, ut.IsConsideredHighRes utIsConsideredHighRes, ";
/*      */   public static final String c_ksUsageTypeFormatFields = "utf.Id utfId, utf.Description utfDescription, utf.FormatId utfFormatId, utf.ImageWidth utfImageWidth, utf.ImageHeight utfImageHeight, utf.UsageTypeId utfUsageTypeId, utf.ScaleUp utfScaleUp, utf.Density utfDensity, utf.JpegQuality utfJpegQuality, utf.PreserveFormatList utfPreserveFormatList, utf.ApplyStrip utfApplyStrip, utf.CropToFit, utf.OmitIfLowerRes utfOmitIfLowerRes, utf.ConvertToColorSpaceId, utf.Watermark, utf.AllowMasking, utf.PresetMaskId, utf.PresetMaskColourId, ";
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*  183 */     super.startup();
/*  184 */     this.m_assetIdsToIndex = Collections.synchronizedSet(new HashSet());
/*      */ 
/*  187 */     long lReindexPeriod = AssetBankSettings.getUsageStatsReindexPeriod() * 60L * 1000L;
/*  188 */     if (lReindexPeriod > 0L)
/*      */     {
/*  190 */       TimerTask task = new TimerTask()
/*      */       {
/*      */         public void run()
/*      */         {
/*      */           try
/*      */           {
/*  196 */             UsageManager.this.indexAssets();
/*      */           }
/*      */           catch (Bn2Exception bn2e)
/*      */           {
/*  200 */             UsageManager.this.m_logger.error("UsageManager: Bn2Exception whilst reindexing assets : " + bn2e);
/*      */           }
/*      */         }
/*      */       };
/*  205 */       getScheduleManager().schedule(task, lReindexPeriod, lReindexPeriod, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void indexAssets()
/*      */     throws Bn2Exception
/*      */   {
/*  214 */     synchronized (this.m_assetIdsToIndex)
/*      */     {
/*  217 */       if (System.currentTimeMillis() - this.m_lLastReindexEndTime > 60000L)
/*      */       {
/*  219 */         Vector ids = new Vector(this.m_assetIdsToIndex);
/*  220 */         if (ids.size() > 0)
/*      */         {
/*  222 */           this.m_logger.debug("UsageManager.indexAssets() : Starting reindex of " + ids.size() + " assets with updated usage stats.");
/*      */ 
/*  224 */           final Vector vAssets = this.m_assetManager.getAssets(null, ids, true, true);
/*      */ 
/*  227 */           new Thread()
/*      */           {
/*      */             public void run()
/*      */             {
/*      */               try
/*      */               {
/*  233 */                 long lStartTime = System.currentTimeMillis();
/*  234 */                 UsageManager.this.m_searchManager.indexDocuments(null, vAssets, true, true, true);
/*  235 */                 UsageManager.this.m_logger.debug("UsageManager.indexAssets() : Finished reindex in " + (System.currentTimeMillis() - lStartTime) + "ms");
/*  236 */                // UsageManager.access$402(UsageManager.this, System.currentTimeMillis());
/*      */               }
/*      */               catch (Bn2Exception e)
/*      */               {
/*  240 */                 UsageManager.this.m_logger.error("UsageManager.indexAssets() : Exception whilst reindexing assets in new thread : " + e, e);
/*      */               }
/*      */             }
/*      */           }
/*  227 */           .start();
/*      */ 
/*  245 */           this.m_assetIdsToIndex.clear();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void processQueueItem(QueuedItem a_item)
/*      */     throws Bn2Exception
/*      */   {
/*  258 */     if ((a_item instanceof LogAssetUseQueueItem))
/*      */     {
/*  260 */       LogAssetUseQueueItem item = (LogAssetUseQueueItem)a_item;
/*  261 */       logAssetUse(null, item.getAssetId(), item.getUserId(), item.getUsageTypeId(), item.getOtherDescription(), item.getDownloadTypeId(), item.getLoginSessionId(), item.getSecondaryUsageTypeIds());
/*      */     }
/*  263 */     else if ((a_item instanceof LogAssetViewQueueItem))
/*      */     {
/*  265 */       LogAssetViewQueueItem item = (LogAssetViewQueueItem)a_item;
/*  266 */       logAssetView(null, item.getAssetId(), item.getUserId(), item.getLoginSessionId());
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getUsageTypes(DBTransaction a_dbTransaction, long a_lParentId)
/*      */     throws Bn2Exception
/*      */   {
/*  281 */     Vector vec = getUsageTypeList(a_dbTransaction, a_lParentId, null, 0L, false);
/*  282 */     return vec;
/*      */   }
/*      */ 
/*      */   public Vector getUsageTypes(DBTransaction a_dbTransaction, long a_lParentId, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  296 */     Vector vec = getUsageTypeList(a_dbTransaction, a_lParentId, null, 0L, false);
/*  297 */     LanguageUtils.setLanguageOnAll(vec, a_language);
/*  298 */     return vec;
/*      */   }
/*      */ 
/*      */   public Vector getUsageTypes(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  312 */     Vector vec = getUsageTypeList(a_dbTransaction, -9223372036854775808L, null, 0L, false);
/*  313 */     return vec;
/*      */   }
/*      */ 
/*      */   public Vector getUsageTypesForAssetType(DBTransaction a_dbTransaction, long a_lAssetTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  319 */     return getUsageTypeList(a_dbTransaction, -9223372036854775808L, null, a_lAssetTypeId, false);
/*      */   }
/*      */ 
/*      */   public ArrayList<UsageType> getUsageTypesTree(DBTransaction a_dbTransaction, long a_lAssetTypeId, Set<Long> a_selectedSecondaryIds)
/*      */     throws Bn2Exception
/*      */   {
/*  326 */     Vector vecAllUsageTypes = getUsageTypeList(a_dbTransaction, -9223372036854775808L, null, a_lAssetTypeId, false);
/*      */ 
/*  328 */     ArrayList listTree = new ArrayList();
/*      */ 
/*  331 */     HashMap m_hmUsageTypes = new HashMap();
/*      */ 
/*  333 */     Iterator itUsageTypes = vecAllUsageTypes.iterator();
/*  334 */     while (itUsageTypes.hasNext())
/*      */     {
/*  337 */       UsageType usage = (UsageType)itUsageTypes.next();
/*      */ 
/*  339 */       m_hmUsageTypes.put(Long.valueOf(usage.getId()), usage);
/*      */     }
/*      */ 
/*  343 */     itUsageTypes = vecAllUsageTypes.iterator();
/*  344 */     while (itUsageTypes.hasNext())
/*      */     {
/*  346 */       UsageType usage = (UsageType)itUsageTypes.next();
/*      */ 
/*  349 */       if (a_selectedSecondaryIds != null)
/*      */       {
/*  351 */         if (a_selectedSecondaryIds.contains(Long.valueOf(usage.getId())))
/*      */         {
/*  353 */           usage.setSelected(true);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  358 */       if (usage.getParentId() > 0L)
/*      */       {
/*  361 */         UsageType parent = (UsageType)m_hmUsageTypes.get(Long.valueOf(usage.getParentId()));
/*      */ 
/*  364 */         parent.getChildren().add(usage);
/*      */       }
/*      */       else
/*      */       {
/*  369 */         listTree.add(usage);
/*      */       }
/*      */     }
/*      */ 
/*  373 */     return listTree;
/*      */   }
/*      */ 
/*      */   public Vector getUsageTypes(DBTransaction a_dbTransaction, long a_lParentId, Vector a_vecExclusions, long a_lUserId, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  391 */     Vector vec = getUsageTypes(a_dbTransaction, a_lParentId, a_vecExclusions, a_lUserId, null, null, 0L, false);
/*  392 */     LanguageUtils.setLanguageOnAll(vec, a_language);
/*  393 */     return vec;
/*      */   }
/*      */ 
/*      */   private Vector getUsageTypes(DBTransaction a_dbTransaction, long a_lParentId, Vector a_vecExclusions, long a_lUserId, Vector a_assets, Boolean a_bAdvanced, long a_lAssetTypeId, boolean a_bImageConsideredLowRes)
/*      */     throws Bn2Exception
/*      */   {
/*  423 */     Vector vec = getUsageTypeList(a_dbTransaction, a_lParentId, a_bAdvanced, a_lAssetTypeId, a_bImageConsideredLowRes);
/*      */ 
/*  426 */     boolean bDoExclusions = AssetBankSettings.getEnableGroupUsageExlusions();
/*  427 */     if (bDoExclusions)
/*      */     {
/*  429 */       vec = removeExclusions(a_dbTransaction, vec, a_vecExclusions, a_lUserId, a_assets);
/*      */     }
/*      */ 
/*  432 */     return vec;
/*      */   }
/*      */ 
/*      */   private Vector getUsageTypeList(DBTransaction a_dbTransaction, long a_lParentId, Boolean a_bAdvanced, long a_lAssetTypeId, boolean a_bImageConsideredLowRes)
/*      */     throws Bn2Exception
/*      */   {
/*  450 */     String ksMethodName = "getUsageTypes";
/*  451 */     Vector vUsageTypes = null;
/*  452 */     Connection con = null;
/*  453 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  457 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  460 */       String sSQL = "SELECT ut.Id utId, ut.Description utDescription, ut.IsEditable utIsEditable, ut.DownloadTabId utDownloadTabId, ut.AssetTypeId utAssetTypeId, ut.ParentId, ut.CanEnterDetails utCanEnterDetails, ut.DetailsMandatory utDetailsMandatory, ut.Message utMessage, ut.DownloadOriginal utDownloadOriginal, ut.IsConsideredHighRes utIsConsideredHighRes, at.Name atName, tut.Description tutDescription, tut.Message tutMessage,l.Id lId, l.Name lName, l.Code lCode FROM UsageType ut LEFT JOIN AssetType at ON ut.AssetTypeId=at.Id LEFT JOIN TranslatedUsageType tut ON ut.Id = tut.UsageTypeId LEFT JOIN Language l ON l.Id = tut.LanguageId ";
/*      */ 
/*  473 */       if (a_lParentId > 0L)
/*      */       {
/*  475 */         sSQL = sSQL + "WHERE ut.ParentId=" + a_lParentId;
/*      */       }
/*  477 */       else if (a_lParentId != -9223372036854775808L)
/*      */       {
/*  479 */         sSQL = sSQL + "WHERE ut.ParentId IS NULL ";
/*      */       }
/*      */ 
/*  482 */       if (a_bAdvanced != null)
/*      */       {
/*  484 */         sSQL = sSQL + " AND (ut.ParentId>0 OR ut.DownloadTabId IN (3,";
/*  485 */         sSQL = sSQL + (a_bAdvanced.booleanValue() ? 2 : 1) + "))";
/*      */       }
/*      */ 
/*  488 */       if (a_lAssetTypeId > 0L)
/*      */       {
/*  490 */         sSQL = sSQL + " AND (ut.AssetTypeId IS NULL OR ut.AssetTypeId = " + a_lAssetTypeId + ")";
/*      */       }
/*      */ 
/*  493 */       if (a_bImageConsideredLowRes)
/*      */       {
/*  495 */         sSQL = sSQL + " AND IsConsideredHighRes=?";
/*      */       }
/*      */ 
/*  498 */       sSQL = sSQL + " ORDER BY ut.SequenceNumber ASC";
/*      */ 
/*  500 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/*  502 */       int iCol = 1;
/*      */ 
/*  504 */       if (a_bImageConsideredLowRes)
/*      */       {
/*  506 */         psql.setBoolean(iCol++, false);
/*      */       }
/*      */ 
/*  509 */       rs = psql.executeQuery();
/*      */ 
/*  511 */       vUsageTypes = new Vector();
/*  512 */       long lLastUsageTypeId = 0L;
/*  513 */       UsageType usageType = null;
/*      */ 
/*  515 */       while (rs.next())
/*      */       {
/*  517 */         if (rs.getLong("utId") != lLastUsageTypeId)
/*      */         {
/*  519 */           usageType = buildUsageType(rs);
/*  520 */           usageType.setAssetTypeDescription(rs.getString("atName"));
/*  521 */           vUsageTypes.add(usageType);
/*  522 */           lLastUsageTypeId = usageType.getId();
/*      */         }
/*      */ 
/*  525 */         if (rs.getLong("lId") <= 0L)
/*      */           continue;
/*      */         UsageType.Translation translation = usageType.new Translation(new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
/*  528 */         translation.setMessage(rs.getString("tutMessage"));
/*  529 */         translation.setDescription(rs.getString("tutDescription"));
/*  530 */         usageType.getTranslations().add(translation);
/*      */       }
/*      */ 
/*  534 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  538 */       throw new Bn2Exception("UsageManager.getUsageTypes: SQL Exception whilst getting the usage types from the database : " + e, e);
/*      */     }
/*      */ 
/*  541 */     return vUsageTypes;
/*      */   }
/*      */ 
/*      */   private Vector removeExclusions(DBTransaction a_dbTransaction, Vector a_vUsageTypes, Vector a_vecExcludedUsageTypes, long a_lUserId, Vector a_assets)
/*      */     throws Bn2Exception
/*      */   {
/*  566 */     HashMap hmExcludedUsageTypeIds = new HashMap();
/*  567 */     if (a_vecExcludedUsageTypes != null)
/*      */     {
/*  569 */       Iterator itExcludedTypes = a_vecExcludedUsageTypes.iterator();
/*  570 */       while (itExcludedTypes.hasNext())
/*      */       {
/*  572 */         UsageType exclusion = (UsageType)itExcludedTypes.next();
/*  573 */         Long olId = new Long(exclusion.getId());
/*  574 */         hmExcludedUsageTypeIds.put(olId, olId);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  579 */     HashMap hmAllowedUsageTypeIds = getPriceBandUsageSet(a_dbTransaction, a_lUserId, a_assets);
/*      */ 
/*  582 */     Vector vecIncludedUsages = new Vector();
/*      */ 
/*  584 */     Iterator itInputTypes = a_vUsageTypes.iterator();
/*  585 */     while (itInputTypes.hasNext())
/*      */     {
/*  587 */       UsageType usage = (UsageType)itInputTypes.next();
/*      */ 
/*  590 */       if ((!hmExcludedUsageTypeIds.containsKey(new Long(usage.getId()))) || (hmAllowedUsageTypeIds.containsKey(new Long(usage.getId()))))
/*      */       {
/*  592 */         vecIncludedUsages.add(usage);
/*      */       }
/*      */     }
/*      */ 
/*  596 */     return vecIncludedUsages;
/*      */   }
/*      */ 
/*      */   private HashMap getPriceBandUsageSet(DBTransaction a_dbTransaction, long a_lUserId, Vector a_assets)
/*      */     throws Bn2Exception
/*      */   {
/*  618 */     HashMap hmUsages = new HashMap();
/*      */ 
/*  620 */     if (a_assets != null)
/*      */     {
/*  622 */       Iterator it = a_assets.iterator();
/*  623 */       while (it.hasNext())
/*      */       {
/*  625 */         Long ol = (Long)it.next();
/*  626 */         long lAssetId = ol.longValue();
/*      */ 
/*  629 */         HashMap hmAssetUsages = this.m_orderManager.getPriceBandUsagesForUserAssetPurchase(a_dbTransaction, a_lUserId, lAssetId);
/*      */ 
/*  631 */         if (hmUsages.size() == 0)
/*      */         {
/*  634 */           hmUsages = hmAssetUsages;
/*      */         }
/*      */         else
/*      */         {
/*  639 */           HashMap hmIntersection = new HashMap();
/*  640 */           Collection colAssetKeys = hmAssetUsages.keySet();
/*  641 */           Iterator itAsset = colAssetKeys.iterator();
/*  642 */           while (itAsset.hasNext())
/*      */           {
/*  644 */             Long olId = (Long)itAsset.next();
/*  645 */             if (hmUsages.containsKey(olId))
/*      */             {
/*  647 */               hmIntersection.put(olId, olId);
/*      */             }
/*      */           }
/*      */ 
/*  651 */           hmUsages = hmIntersection;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  657 */     return hmUsages;
/*      */   }
/*      */ 
/*      */   public Vector getUsageTypeLists(DBTransaction a_dbTransaction, long a_lUsageTypeId, UsageTypeSelectOption a_selectedUsage, Vector a_vecExclusions, long a_lUserId, Vector a_assets, boolean a_bAdvanced, long a_lAssetTypeId, Language a_language, boolean a_bImageConsideredLowRes)
/*      */     throws Bn2Exception
/*      */   {
/*  689 */     String ksMethodName = "getUsageTypeLists";
/*  690 */     Vector vec = new Vector();
/*  691 */     Connection con = null;
/*  692 */     ResultSet rs = null;
/*  693 */     String sSQL = null;
/*  694 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  698 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  703 */       Stack stackAncestorList = new Stack();
/*  704 */       long lId = a_lUsageTypeId;
/*      */ 
/*  706 */       while (lId > 0L)
/*      */       {
/*  709 */         Long olId = new Long(lId);
/*  710 */         stackAncestorList.push(olId);
/*      */ 
/*  713 */         sSQL = "SELECT ParentId FROM UsageType WHERE Id=? AND (ParentId>0 OR DownloadTabId=? OR DownloadTabId=?)";
/*  714 */         if (a_bImageConsideredLowRes)
/*      */         {
/*  716 */           sSQL = sSQL + " AND IsConsideredHighRes=?";
/*      */         }
/*      */ 
/*  719 */         psql = con.prepareStatement(sSQL);
/*      */ 
/*  721 */         int iCol = 1;
/*      */ 
/*  723 */         psql.setLong(iCol++, lId);
/*      */ 
/*  725 */         if (a_bAdvanced)
/*      */         {
/*  727 */           psql.setInt(iCol++, 2);
/*      */         }
/*      */         else
/*      */         {
/*  731 */           psql.setInt(iCol++, 1);
/*      */         }
/*  733 */         psql.setInt(iCol++, 3);
/*      */ 
/*  735 */         if (a_bImageConsideredLowRes)
/*      */         {
/*  737 */           psql.setBoolean(iCol++, false);
/*      */         }
/*      */ 
/*  740 */         rs = psql.executeQuery();
/*  741 */         long lParentId = 0L;
/*  742 */         if (rs.next())
/*      */         {
/*  744 */           lParentId = rs.getLong("ParentId");
/*      */         }
/*      */         else
/*      */         {
/*  748 */           lId = 0L;
/*  749 */           stackAncestorList.clear();
/*      */         }
/*  751 */         psql.close();
/*      */ 
/*  753 */         lId = lParentId;
/*      */       }
/*      */ 
/*  757 */       UsageType selectedUsage = new UsageType();
/*      */ 
/*  760 */       boolean bLeaf = false;
/*      */ 
/*  764 */       boolean bFinished = false;
/*  765 */       long lSelectedId = 0L;
/*      */ 
/*  767 */       while (!bFinished)
/*      */       {
/*  770 */         Vector vecTypes = getUsageTypes(a_dbTransaction, lSelectedId, a_vecExclusions, a_lUserId, a_assets, Boolean.valueOf(a_bAdvanced), a_lAssetTypeId, a_bImageConsideredLowRes);
/*      */ 
/*  773 */         if (stackAncestorList.empty())
/*      */         {
/*  776 */           bFinished = true;
/*  777 */           lSelectedId = 0L;
/*      */         }
/*      */         else
/*      */         {
/*  782 */           lSelectedId = ((Long)stackAncestorList.pop()).longValue();
/*      */         }
/*      */ 
/*  787 */         Vector vecOptions = new Vector();
/*  788 */         Iterator it = vecTypes.iterator();
/*  789 */         while (it.hasNext())
/*      */         {
/*  791 */           UsageType u = (UsageType)it.next();
/*      */ 
/*  793 */           if (!LanguageConstants.k_defaultLanguage.equals(a_language))
/*      */           {
/*  795 */             u.setLanguage(a_language);
/*      */           }
/*      */ 
/*  798 */           UsageTypeSelectOption option = new UsageTypeSelectOption();
/*  799 */           option.setId(u.getId());
/*  800 */           option.setName(u.getDescription());
/*  801 */           option.setUsageType(u);
/*      */ 
/*  803 */           if (u.getId() == lSelectedId)
/*      */           {
/*  805 */             option.setSelected(true);
/*      */ 
/*  808 */             selectedUsage = u;
/*      */           }
/*      */ 
/*  811 */           vecOptions.add(option);
/*      */         }
/*      */ 
/*  815 */         vec.add(vecOptions);
/*      */ 
/*  818 */         if (vecOptions.size() == 0)
/*      */         {
/*  820 */           bLeaf = true;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  825 */       a_selectedUsage.setLeaf(bLeaf);
/*  826 */       a_selectedUsage.setId(selectedUsage.getId());
/*  827 */       a_selectedUsage.setName(selectedUsage.getDescription());
/*  828 */       a_selectedUsage.setUsageType(selectedUsage);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  833 */       throw new Bn2Exception("UsageManager.getUsageTypeLists: SQL Exception whilst getting the usage types from the database : " + e, e);
/*      */     }
/*      */ 
/*  836 */     return vec;
/*      */   }
/*      */ 
/*      */   public boolean getListIsFlat(DBTransaction a_dbTransaction, long a_lAssetTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  852 */     String ksMethodName = "getListIsFlat";
/*  853 */     boolean bListIsFlat = true;
/*  854 */     Connection con = null;
/*  855 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  859 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  861 */       String sSQL = "SELECT 1 FROM UsageType WHERE ParentId IS NOT NULL AND (AssetTypeId IS NULL OR AssetTypeId = ?)";
/*      */ 
/*  863 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  864 */       psql.setLong(1, a_lAssetTypeId);
/*  865 */       rs = psql.executeQuery();
/*      */ 
/*  867 */       if (rs.next())
/*      */       {
/*  869 */         bListIsFlat = false;
/*      */       }
/*      */ 
/*  872 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  876 */       throw new Bn2Exception("UsageManager.getListIsFlat: SQL Exception: " + e, e);
/*      */     }
/*      */ 
/*  879 */     return bListIsFlat;
/*      */   }
/*      */ 
/*      */   public Vector getUsageTypeFormats(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  897 */     return getUsageTypeFormats(a_dbTransaction, -9223372036854775808L);
/*      */   }
/*      */ 
/*      */   public Vector<UsageTypeFormat> getUsageTypeFormats(DBTransaction a_dbTransaction, long a_lUsageTypeId, Language a_language, int a_iHeight, int a_iWidth)
/*      */     throws Bn2Exception
/*      */   {
/*  916 */     Vector vFormats = getUsageTypeFormats(a_dbTransaction, a_lUsageTypeId);
/*      */ 
/*  919 */     Vector vFormatsFinal = (Vector)vFormats.clone();
/*      */ 
/*  922 */     Iterator itFormats = vFormats.iterator();
/*  923 */     while (itFormats.hasNext())
/*      */     {
/*  925 */       UsageTypeFormat usageTypeFormat = (UsageTypeFormat)itFormats.next();
/*      */ 
/*  927 */       if (usageTypeFormat.getOmitIfLowerRes())
/*      */       {
/*  929 */         if ((a_iHeight < usageTypeFormat.getHeight()) && (a_iWidth < usageTypeFormat.getWidth()))
/*      */         {
/*  932 */           vFormatsFinal.remove(usageTypeFormat);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  937 */     LanguageUtils.setLanguageOnAll(vFormatsFinal, a_language);
/*  938 */     return vFormatsFinal;
/*      */   }
/*      */ 
/*      */   public Vector<UsageTypeFormat> getUsageTypeFormats(DBTransaction a_dbTransaction, long a_lUsageTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  959 */     Vector vecUsageTypeFormats = null;
/*  960 */     Connection con = null;
/*  961 */     ResultSet rs = null;
/*  962 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/*  964 */     if (transaction == null)
/*      */     {
/*  966 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  971 */       con = transaction.getConnection();
/*      */ 
/*  973 */       String sSQL = "SELECT utf.Id utfId, utf.Description utfDescription, utf.FormatId utfFormatId, utf.ImageWidth utfImageWidth, utf.ImageHeight utfImageHeight, utf.UsageTypeId utfUsageTypeId, utf.ScaleUp utfScaleUp, utf.Density utfDensity, utf.JpegQuality utfJpegQuality, utf.PreserveFormatList utfPreserveFormatList, utf.ApplyStrip utfApplyStrip, utf.CropToFit, utf.OmitIfLowerRes utfOmitIfLowerRes, utf.ConvertToColorSpaceId, utf.Watermark, utf.AllowMasking, utf.PresetMaskId, utf.PresetMaskColourId, tutf.Description tutfDescription, l.Id lId, l.Name lName, l.Code lCode FROM UsageTypeFormat utf LEFT JOIN TranslatedUsageTypeFormat tutf ON utf.Id = tutf.UsageTypeFormatId LEFT JOIN Language l ON l.Id = tutf.LanguageId ";
/*      */ 
/*  983 */       if (a_lUsageTypeId > 0L)
/*      */       {
/*  985 */         sSQL = sSQL + " WHERE utf.UsageTypeId=?";
/*      */       }
/*  987 */       else if (a_lUsageTypeId != -9223372036854775808L)
/*      */       {
/*  989 */         sSQL = sSQL + " WHERE utf.UsageTypeId IS NULL";
/*      */       }
/*      */ 
/*  992 */       sSQL = sSQL + " ORDER BY utf.Id";
/*      */ 
/*  995 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/*  997 */       if (a_lUsageTypeId > 0L)
/*      */       {
/*  999 */         psql.setLong(1, a_lUsageTypeId);
/*      */       }
/* 1001 */       rs = psql.executeQuery();
/*      */ 
/* 1003 */       vecUsageTypeFormats = new Vector();
/* 1004 */       long lLastFomatId = 0L;
/* 1005 */       UsageTypeFormat usageTypeFormat = null;
/*      */ 
/* 1007 */       while (rs.next())
/*      */       {
/* 1009 */         if (rs.getLong("utfId") != lLastFomatId)
/*      */         {
/* 1011 */           usageTypeFormat = buildUsageTypeFormat(rs);
/* 1012 */           vecUsageTypeFormats.add(usageTypeFormat);
/*      */ 
/* 1014 */           lLastFomatId = usageTypeFormat.getId();
/*      */         }
/*      */ 
/* 1017 */         if (rs.getLong("lId") <= 0L)
/*      */           continue;
/*      */         //UsageTypeFormat tmp238_236 = usageTypeFormat; tmp238_236.getClass(); UsageTypeFormat.Translation translation = new UsageTypeFormat.Translation(tmp238_236, new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
/* 1020 */          UsageTypeFormat.Translation translation = usageTypeFormat.new Translation(new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
                    translation.setDescription(rs.getString("tutfDescription"));
/* 1021 */          usageTypeFormat.getTranslations().add(translation);
/*      */       }
/*      */ 
/* 1025 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1029 */       this.m_logger.error("SQL Exception whilst getting the usage type formats from the database : " + e);
/* 1030 */       throw new Bn2Exception("SQL Exception whilst getting the usage type formats from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1035 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1039 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1043 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1048 */     return vecUsageTypeFormats;
/*      */   }
/*      */ 
/*      */   public UsageType getUsageType(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 1061 */     String ksMethodName = "getUsageType";
/* 1062 */     UsageType usageType = null;
/* 1063 */     Connection con = null;
/* 1064 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1068 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1070 */       String sSQL = "SELECT ut.Id utId, ut.Description utDescription, ut.IsEditable utIsEditable, ut.DownloadTabId utDownloadTabId, ut.AssetTypeId utAssetTypeId, ut.ParentId, ut.CanEnterDetails utCanEnterDetails, ut.DetailsMandatory utDetailsMandatory, ut.Message utMessage, ut.DownloadOriginal utDownloadOriginal, ut.IsConsideredHighRes utIsConsideredHighRes, tut.Description tutDescription, tut.Message tutMessage, l.Id lId, l.Code lCode, l.Name lName FROM UsageType ut LEFT JOIN TranslatedUsageType tut ON ut.Id = tut.UsageTypeId LEFT JOIN Language l ON l.Id = tut.LanguageId WHERE ut.Id=?";
/*      */ 
/* 1083 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/* 1085 */       psql.setLong(1, a_lId);
/*      */ 
/* 1087 */       rs = psql.executeQuery();
/*      */ 
/* 1089 */       if (rs.next())
/*      */       {
/* 1091 */         usageType = buildUsageType(rs);
/*      */         do
/*      */         {
/* 1095 */           if (rs.getLong("lId") <= 0L)
/*      */             continue;
/*      */           //UsageType tmp90_88 = usageType; tmp90_88.getClass(); UsageType.Translation translation = new UsageType.Translation(tmp90_88, new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
/* 1098 */         UsageType.Translation translation = usageType.new Translation(new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
                     translation.setMessage(rs.getString("tutMessage"));
/* 1099 */           translation.setDescription(rs.getString("tutDescription"));
/* 1100 */           usageType.getTranslations().add(translation);
/*      */         }
/*      */ 
/* 1103 */         while (rs.next());
/*      */       }
/*      */ 
/* 1106 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1110 */       throw new Bn2Exception("UsageManager.getUsageType: SQL Exception whilst getting a usage type from the database : " + e, e);
/*      */     }
/*      */ 
/* 1113 */     return usageType;
/*      */   }
/*      */ 
/*      */   public UsageTypeFormat getUsageTypeFormat(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 1127 */     UsageTypeFormat usageTypeFormat = null;
/* 1128 */     Connection con = null;
/* 1129 */     ResultSet rs = null;
/* 1130 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1132 */     if (transaction == null)
/*      */     {
/* 1134 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1139 */       con = transaction.getConnection();
/*      */ 
/* 1141 */       String sSQL = "SELECT utf.Id utfId, utf.Description utfDescription, utf.FormatId utfFormatId, utf.ImageWidth utfImageWidth, utf.ImageHeight utfImageHeight, utf.UsageTypeId utfUsageTypeId, utf.ScaleUp utfScaleUp, utf.Density utfDensity, utf.JpegQuality utfJpegQuality, utf.PreserveFormatList utfPreserveFormatList, utf.ApplyStrip utfApplyStrip, utf.CropToFit, utf.OmitIfLowerRes utfOmitIfLowerRes, utf.ConvertToColorSpaceId, utf.Watermark, utf.AllowMasking, utf.PresetMaskId, utf.PresetMaskColourId, tutf.Description tutfDescription, l.Id lId, l.Name lName, l.Code lCode FROM UsageTypeFormat utf LEFT JOIN TranslatedUsageTypeFormat tutf ON utf.Id = tutf.UsageTypeFormatId LEFT JOIN Language l ON l.Id = tutf.LanguageId WHERE utf.Id=?";
/*      */ 
/* 1153 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*      */ 
/* 1155 */       psql.setLong(1, a_lId);
/*      */ 
/* 1157 */       rs = psql.executeQuery();
/*      */ 
/* 1159 */       if (rs.next())
/*      */       {
/* 1161 */         usageTypeFormat = buildUsageTypeFormat(rs);
/* 1162 */         if (rs.getLong("lId") > 0L) {
/*      */           do
/*      */           {
/*      */             //UsageTypeFormat tmp104_102 = usageTypeFormat; tmp104_102.getClass(); UsageTypeFormat.Translation translation = new UsageTypeFormat.Translation(tmp104_102, new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
/* 1167 */            UsageTypeFormat.Translation translation = usageTypeFormat.new Translation( new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode")));
                        translation.setDescription(rs.getString("tutfDescription"));
/* 1168 */             usageTypeFormat.getTranslations().add(translation);
/*      */           }
/* 1170 */           while (rs.next());
/*      */         }
/*      */       }
/*      */ 
/* 1174 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1178 */       this.m_logger.error("SQL Exception whilst getting a usage type format from the database : " + e);
/* 1179 */       throw new Bn2Exception("SQL Exception whilst getting a usage type format from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1184 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1188 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1192 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1197 */     return usageTypeFormat;
/*      */   }
/*      */ 
/*      */   public void logAssetUseAsynchronously(long a_lAssetId, long a_lUserId, long a_lUsageTypeId, String a_sOtherDescription, long a_lDownloadTypeId, long a_lLoginSessionId, Set<Long> a_listSecondaryUsageTypeIds)
/*      */     throws Bn2Exception
/*      */   {
/* 1218 */     LogAssetUseQueueItem item = new LogAssetUseQueueItem(a_lAssetId, a_lUserId, a_lUsageTypeId, a_sOtherDescription, a_lDownloadTypeId, a_lLoginSessionId, a_listSecondaryUsageTypeIds);
/* 1219 */     queueItem(item);
/*      */   }
/*      */ 
/*      */   private void logAssetUse(DBTransaction a_dbTransaction, long a_lAssetId, long a_lUserId, long a_lUsageTypeId, String a_sOtherDescription, long a_lDownloadTypeId, long a_lSessionId, Set<Long> a_secondaryUsageTypeIds)
/*      */     throws Bn2Exception
/*      */   {
/* 1249 */     Connection con = null;
/* 1250 */     ResultSet rs = null;
/* 1251 */     PreparedStatement psql = null;
/* 1252 */     String sSQL = null;
/* 1253 */     String sOriginalDescription = "";
/* 1254 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1256 */     if (transaction == null)
/*      */     {
/* 1258 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1263 */       con = transaction.getConnection();
/*      */ 
/* 1265 */       Timestamp now = new Timestamp(System.currentTimeMillis());
/*      */ 
/* 1267 */       sSQL = "UPDATE Asset SET NumDownloads=NumDownloads+1, LastDownloaded=? WHERE Id=?";
/* 1268 */       psql = con.prepareStatement(sSQL);
/* 1269 */       psql.setTimestamp(1, now);
/* 1270 */       psql.setLong(2, a_lAssetId);
/* 1271 */       psql.executeUpdate();
/* 1272 */       psql.close();
/*      */ 
/* 1275 */       if (a_dbTransaction == null)
/*      */       {
/* 1277 */         transaction.commit();
/* 1278 */         transaction = this.m_transactionManager.getNewTransaction();
/* 1279 */         con = transaction.getConnection();
/*      */       }
/*      */ 
/* 1282 */       sSQL = "SELECT Description FROM UsageType WHERE Id=?";
/*      */ 
/* 1284 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 1286 */       psql.setLong(1, a_lUsageTypeId);
/*      */ 
/* 1288 */       rs = psql.executeQuery();
/*      */ 
/* 1290 */       if (rs.next())
/*      */       {
/* 1292 */         sOriginalDescription = rs.getString("Description");
/*      */       }
/*      */ 
/* 1295 */       psql.close();
/*      */ 
/* 1297 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 1298 */       long lNewId = 0L;
/*      */ 
/* 1300 */       sSQL = "INSERT INTO AssetUse (";
/*      */ 
/* 1302 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1304 */         lNewId = sqlGenerator.getUniqueId(con, "AssetUseSequence");
/* 1305 */         sSQL = sSQL + "Id, ";
/*      */       }
/*      */ 
/* 1309 */       sSQL = sSQL + "AssetId,UserId,UsageTypeId,AssetDownloadTypeId,OriginalDescription,OtherDescription,TimeOfDownload";
/*      */ 
/* 1317 */       if (AssetBankSettings.getAuditLogEnabled())
/*      */       {
/* 1319 */         sSQL = sSQL + ",SessionLogId";
/*      */       }
/*      */ 
/* 1323 */       sSQL = sSQL + ") VALUES (";
/*      */ 
/* 1325 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1327 */         sSQL = sSQL + "?,";
/*      */       }
/*      */ 
/* 1330 */       sSQL = sSQL + "?,?,?,?,?,?,?";
/*      */ 
/* 1332 */       if (AssetBankSettings.getAuditLogEnabled())
/*      */       {
/* 1334 */         sSQL = sSQL + ",?";
/*      */       }
/*      */ 
/* 1337 */       sSQL = sSQL + ")";
/*      */ 
/* 1339 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 1341 */       int iCol = 1;
/*      */ 
/* 1343 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1345 */         psql.setLong(iCol++, lNewId);
/*      */       }
/*      */ 
/* 1348 */       psql.setLong(iCol++, a_lAssetId);
/* 1349 */       DBUtil.setFieldIdOrNull(psql, iCol++, a_lUserId);
/* 1350 */       DBUtil.setFieldIdOrNull(psql, iCol++, a_lUsageTypeId);
/* 1351 */       psql.setLong(iCol++, a_lDownloadTypeId);
/* 1352 */       psql.setString(iCol++, sOriginalDescription);
/* 1353 */       psql.setString(iCol++, a_sOtherDescription);
/* 1354 */       psql.setTimestamp(iCol++, now);
/*      */ 
/* 1356 */       if (AssetBankSettings.getAuditLogEnabled())
/*      */       {
/* 1358 */         psql.setLong(iCol++, a_lSessionId);
/*      */       }
/*      */ 
/* 1361 */       psql.executeUpdate();
/*      */ 
/* 1363 */       psql.close();
/*      */ 
/* 1365 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1367 */         lNewId = sqlGenerator.getUniqueId(con, null);
/*      */       }
/*      */ 
/* 1371 */       if (a_secondaryUsageTypeIds != null)
/*      */       {
/* 1373 */         for (Long lNextUsageTypeId : a_secondaryUsageTypeIds)
/*      */         {
/* 1375 */           String sSecondaryUsageDescription = "";
/*      */ 
/* 1378 */           sSQL = "SELECT Description FROM UsageType WHERE Id=?";
/*      */ 
/* 1380 */           psql = con.prepareStatement(sSQL);
/*      */ 
/* 1382 */           psql.setLong(1, lNextUsageTypeId.longValue());
/*      */ 
/* 1384 */           rs = psql.executeQuery();
/*      */ 
/* 1386 */           if (rs.next())
/*      */           {
/* 1388 */             sSecondaryUsageDescription = rs.getString("Description");
/*      */           }
/*      */ 
/* 1391 */           psql.close();
/*      */ 
/* 1393 */           sSQL = "INSERT INTO SecondaryDownloadUsageType (AssetUseId,UsageTypeId,OriginalDescription) VALUES (?,?,?)";
/*      */ 
/* 1397 */           psql = con.prepareStatement(sSQL);
/*      */ 
/* 1399 */           iCol = 1;
/*      */ 
/* 1401 */           psql.setLong(iCol++, lNewId);
/* 1402 */           psql.setLong(iCol++, lNextUsageTypeId.longValue());
/* 1403 */           psql.setString(iCol++, sSecondaryUsageDescription);
/*      */ 
/* 1405 */           psql.executeUpdate();
/* 1406 */           psql.close();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1411 */       reindexAsset(a_lAssetId);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1415 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1419 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1423 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1427 */       this.m_logger.error("SQL Exception whilst logging an asset use in the database : " + e);
/* 1428 */       throw new Bn2Exception("SQL Exception whilst logging an asset use in the database : " + e, e);
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
/* 1441 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void logAssetViewAsynchronously(long a_lAssetId, long a_lUserId, long a_lSessionId)
/*      */   {
/* 1461 */     LogAssetViewQueueItem item = new LogAssetViewQueueItem(a_lAssetId, a_lUserId, a_lSessionId);
/* 1462 */     queueItem(item);
/*      */   }
/*      */ 
/*      */   public void logAssetView(DBTransaction a_dbTransaction, long a_lAssetId, long a_lUserId, long a_lSessionId)
/*      */     throws Bn2Exception
/*      */   {
/* 1483 */     Connection con = null;
/* 1484 */     PreparedStatement psql = null;
/* 1485 */     String sSQL = null;
/* 1486 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1488 */     if (transaction == null)
/*      */     {
/* 1490 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1495 */       con = transaction.getConnection();
/*      */ 
/* 1497 */       sSQL = "UPDATE Asset SET NumViews=(NumViews+1) WHERE Id=?";
/* 1498 */       psql = con.prepareStatement(sSQL);
/* 1499 */       psql.setLong(1, a_lAssetId);
/* 1500 */       psql.executeUpdate();
/* 1501 */       psql.close();
/*      */ 
/* 1504 */       if (a_dbTransaction == null)
/*      */       {
/* 1506 */         transaction.commit();
/* 1507 */         transaction = this.m_transactionManager.getNewTransaction();
/* 1508 */         con = transaction.getConnection();
/*      */       }
/*      */ 
/* 1511 */       sSQL = "INSERT INTO AssetView (AssetId,UserId,Time";
/*      */ 
/* 1513 */       if (AssetBankSettings.getAuditLogEnabled())
/*      */       {
/* 1515 */         sSQL = sSQL + ",SessionLogId";
/*      */       }
/*      */ 
/* 1518 */       sSQL = sSQL + ") VALUES (?,?,?";
/*      */ 
/* 1520 */       if (AssetBankSettings.getAuditLogEnabled())
/*      */       {
/* 1522 */         sSQL = sSQL + ",?";
/*      */       }
/*      */ 
/* 1525 */       sSQL = sSQL + ")";
/*      */ 
/* 1527 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 1529 */       int iCol = 1;
/*      */ 
/* 1531 */       psql.setLong(iCol++, a_lAssetId);
/* 1532 */       DBUtil.setFieldIdOrNull(psql, iCol++, a_lUserId);
/* 1533 */       psql.setTimestamp(iCol++, new Timestamp(new Date().getTime()));
/*      */ 
/* 1535 */       if (AssetBankSettings.getAuditLogEnabled())
/*      */       {
/* 1537 */         psql.setLong(iCol++, a_lSessionId);
/*      */       }
/*      */ 
/* 1540 */       psql.executeUpdate();
/* 1541 */       psql.close();
/*      */ 
/* 1543 */       reindexAsset(a_lAssetId);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1547 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1551 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1555 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1559 */       this.m_logger.error("SQL Exception whilst logging an asset view in the database : " + e);
/* 1560 */       throw new Bn2Exception("SQL Exception whilst logging an asset view in the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1565 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1569 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1573 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void reindexAsset(long a_lAssetId)
/*      */   {
/* 1586 */     if (AssetBankSettings.getUsageStatsReindexPeriod() > 0L)
/*      */     {
/* 1589 */       this.m_assetIdsToIndex.add(Long.valueOf(a_lAssetId));
/*      */     }
/*      */   }
/*      */ 
/*      */   public List<AssetUseLogEntry> getAssetUsage(DBTransaction a_dbTransaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1610 */     List results = null;
/* 1611 */     Connection con = null;
/* 1612 */     ResultSet rs = null;
/* 1613 */     PreparedStatement psql = null;
/* 1614 */     String sSQL = null;
/* 1615 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1617 */     if (transaction == null)
/*      */     {
/* 1619 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1624 */       con = transaction.getConnection();
/*      */ 
/* 1626 */       sSQL = "SELECT au.Id as AssetUseId, au.TimeOfDownload, au.UsageTypeId, au.OriginalDescription, au.OtherDescription, adt.Description, u.Id as UserId, u.Forename, u.Surname, u.Username, u.EmailAddress,sdut.OriginalDescription as SecondaryUsageType";
/*      */ 
/* 1630 */       if (AssetBankSettings.getAuditLogEnabled())
/*      */       {
/* 1632 */         sSQL = sSQL + ", sl.IpAddress ";
/*      */       }
/*      */ 
/* 1635 */       sSQL = sSQL + " FROM AssetUse au INNER JOIN AssetDownloadType adt ON adt.Id = au.AssetDownloadTypeId LEFT OUTER JOIN AssetBankUser u ON u.Id = au.UserId LEFT OUTER JOIN SecondaryDownloadUsageType sdut ON sdut.AssetUseId = au.Id ";
/*      */ 
/* 1641 */       if (AssetBankSettings.getAuditLogEnabled())
/*      */       {
/* 1643 */         sSQL = sSQL + "INNER JOIN SessionLog sl ON sl.Id = au.SessionLogId ";
/*      */       }
/*      */ 
/* 1646 */       sSQL = sSQL + "WHERE au.AssetId=? ORDER BY au.Id DESC ";
/*      */ 
/* 1648 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 1650 */       psql.setLong(1, a_lAssetId);
/*      */ 
/* 1652 */       rs = psql.executeQuery();
/*      */ 
/* 1654 */       results = new Vector();
/*      */ 
/* 1656 */       AssetUseLogEntry entry = null;
/*      */ 
/* 1658 */       while (rs.next())
/*      */       {
/* 1661 */         if ((entry != null) && (entry.getId() == rs.getLong("AssetUseId")))
/*      */         {
/* 1663 */           entry.getSecondaryUsageTypes().add(rs.getString("SecondaryUsageType")); continue;
/*      */         }
/*      */ 
/* 1668 */         if (entry != null)
/*      */         {
/* 1670 */           results.add(entry);
/*      */         }
/*      */ 
/* 1673 */         entry = new AssetUseLogEntry();
/*      */ 
/* 1675 */         entry.setId(rs.getLong("AssetUseId"));
/* 1676 */         entry.setDate(rs.getTimestamp("TimeOfDownload"));
/* 1677 */         entry.setType(rs.getString("Description"));
/*      */ 
/* 1679 */         if (AssetBankSettings.getAuditLogEnabled())
/*      */         {
/* 1681 */           entry.setIpAddress(rs.getString("IpAddress"));
/*      */         }
/*      */ 
/* 1684 */         if (rs.getLong("UsageTypeId") == 1L)
/*      */         {
/* 1686 */           entry.setDescription(rs.getString("OtherDescription"));
/*      */         }
/*      */         else
/*      */         {
/* 1690 */           entry.setDescription(rs.getString("OriginalDescription"));
/* 1691 */           entry.setMoreDetails(rs.getString("OtherDescription"));
/*      */         }
/*      */ 
/* 1695 */         if (StringUtil.stringIsPopulated(rs.getString("SecondaryUsageType")))
/*      */         {
/* 1697 */           entry.getSecondaryUsageTypes().add(rs.getString("SecondaryUsageType"));
/*      */         }
/*      */ 
/* 1700 */         ABUser user = null;
/*      */ 
/* 1702 */         Long userId = DBUtil.getLongOrNull(rs, "UserId");
/* 1703 */         if (userId != null) {
/* 1704 */           user = new ABUser();
/* 1705 */           user.setId(userId.longValue());
/* 1706 */           user.setForename(rs.getString("Forename"));
/* 1707 */           user.setSurname(rs.getString("Surname"));
/* 1708 */           user.setUsername(rs.getString("Username"));
/* 1709 */           user.setEmailAddress(rs.getString("EmailAddress"));
/*      */         }
/*      */ 
/* 1712 */         entry.setUser(user);
/*      */       }
/*      */ 
/* 1718 */       if (entry != null)
/*      */       {
/* 1720 */         results.add(entry);
/*      */       }
/*      */ 
/* 1723 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1727 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1731 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1735 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1739 */       this.m_logger.error("SQL Exception whilst getting asset usage inforamtion from the database : " + e);
/* 1740 */       throw new Bn2Exception("SQL Exception whilst getting asset usage inforamtion from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1745 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1749 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1753 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1758 */     return results;
/*      */   }
/*      */ 
/*      */   public void addUsageTypeValue(DBTransaction a_dbTransaction, UsageType a_usageType, long a_lParentId)
/*      */     throws Bn2Exception
/*      */   {
/* 1782 */     Connection con = null;
/* 1783 */     PreparedStatement psql = null;
/* 1784 */     String sSQL = null;
/* 1785 */     ResultSet rs = null;
/*      */ 
/* 1787 */     int iSequenceNumber = 0;
/*      */     try
/*      */     {
/* 1791 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1794 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 1795 */       long lNewId = 0L;
/*      */ 
/* 1798 */       sSQL = "SELECT MAX(SequenceNumber) maxSeq FROM UsageType WHERE IsEditable=1 ";
/*      */ 
/* 1800 */       if (a_lParentId > 0L)
/*      */       {
/* 1802 */         sSQL = sSQL + "AND ParentId = " + a_lParentId;
/*      */       }
/*      */       else
/*      */       {
/* 1806 */         sSQL = sSQL + "AND ParentId IS NULL";
/*      */       }
/*      */ 
/* 1809 */       psql = con.prepareStatement(sSQL);
/* 1810 */       rs = psql.executeQuery();
/*      */ 
/* 1812 */       if (rs.next())
/*      */       {
/* 1814 */         iSequenceNumber = rs.getInt("maxSeq");
/*      */       }
/* 1816 */       psql.close();
/*      */ 
/* 1819 */       iSequenceNumber++;
/*      */ 
/* 1822 */       sSQL = "INSERT INTO UsageType (";
/*      */ 
/* 1824 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1826 */         lNewId = sqlGenerator.getUniqueId(con, "UsageTypeSequence");
/* 1827 */         a_usageType.setId(lNewId);
/* 1828 */         sSQL = sSQL + "Id, ";
/*      */       }
/*      */ 
/* 1831 */       sSQL = sSQL + "Description,DownloadTabId,SequenceNumber,IsEditable,CanEnterDetails,DetailsMandatory,AssetTypeId,ParentId,Message,DownloadOriginal,IsConsideredHighRes) VALUES (";
/*      */ 
/* 1833 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1835 */         sSQL = sSQL + "?,";
/*      */       }
/*      */ 
/* 1838 */       sSQL = sSQL + "?,?,?,?,?,?,?,?,?,?,?)";
/*      */ 
/* 1840 */       psql = con.prepareStatement(sSQL);
/* 1841 */       int iCol = 1;
/*      */ 
/* 1843 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1845 */         psql.setLong(iCol++, lNewId);
/*      */       }
/*      */ 
/* 1848 */       psql.setString(iCol++, a_usageType.getDescription());
/* 1849 */       psql.setInt(iCol++, a_usageType.getDownloadTabId());
/* 1850 */       psql.setInt(iCol++, iSequenceNumber);
/* 1851 */       psql.setBoolean(iCol++, true);
/* 1852 */       psql.setBoolean(iCol++, a_usageType.getCanEnterDetails());
/* 1853 */       psql.setBoolean(iCol++, a_usageType.getDetailsMandatory());
/* 1854 */       DBUtil.setFieldIdOrNull(psql, iCol++, a_usageType.getAssetTypeId());
/* 1855 */       DBUtil.setFieldIdOrNull(psql, iCol++, a_lParentId);
/* 1856 */       psql.setString(iCol++, a_usageType.getMessage());
/* 1857 */       psql.setBoolean(iCol++, a_usageType.getDownloadOriginal());
/* 1858 */       psql.setBoolean(iCol++, a_usageType.getHighResolution());
/*      */ 
/* 1860 */       psql.executeUpdate();
/* 1861 */       psql.close();
/*      */ 
/* 1863 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1865 */         a_usageType.setId(sqlGenerator.getUniqueId(con, null));
/*      */       }
/*      */ 
/* 1869 */       insertUsageTypeTranslatsions(a_usageType, con);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1873 */       throw new Bn2Exception("SQL Exception whilst adding a usage type value to the database : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addUsageTypeFormat(DBTransaction a_dbTransaction, UsageTypeFormat a_format)
/*      */     throws Bn2Exception
/*      */   {
/* 1894 */     Connection con = null;
/* 1895 */     PreparedStatement psql = null;
/* 1896 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1898 */     if (transaction == null)
/*      */     {
/* 1900 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1905 */       con = transaction.getConnection();
/*      */ 
/* 1908 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/* 1911 */       String sSQL = "INSERT INTO UsageTypeFormat (UsageTypeId, Description, ImageWidth, ImageHeight, FormatId, ScaleUp, Density, JpegQuality, PreserveFormatList,ApplyStrip,CropToFit, OmitIfLowerRes, ConvertToColorSpaceId, Watermark, AllowMasking, PresetMaskId, PresetMaskColourId";
/* 1912 */       String sFields = ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
/*      */ 
/* 1914 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1916 */         a_format.setId(sqlGenerator.getUniqueId(con, "UsageTypeFormatSequence"));
/* 1917 */         sSQL = sSQL + ", Id";
/* 1918 */         sFields = sFields + ",?";
/*      */       }
/*      */ 
/* 1921 */       sSQL = sSQL + sFields + ")";
/*      */ 
/* 1923 */       psql = con.prepareStatement(sSQL);
/* 1924 */       int iCol = 1;
/* 1925 */       DBUtil.setFieldIdOrNull(psql, iCol++, a_format.getUsageTypeId());
/* 1926 */       iCol = UsageTypeFormatDBUtil.prepareUsageTypeFormatStatement(a_format, psql, iCol);
/*      */ 
/* 1928 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1930 */         psql.setLong(iCol++, a_format.getId());
/*      */       }
/*      */ 
/* 1934 */       psql.executeUpdate();
/*      */ 
/* 1936 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1938 */         a_format.setId(sqlGenerator.getUniqueId(con, null));
/*      */       }
/*      */ 
/* 1941 */       psql.close();
/*      */ 
/* 1944 */       insertUsageTypeFormatTranslations(a_format, con);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1948 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1952 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1956 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1960 */       this.m_logger.error("SQL Exception whilst adding a usage type format value to the database : " + e);
/* 1961 */       throw new Bn2Exception("SQL Exception whilst adding a usage type value to the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1966 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1970 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1974 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateUsageTypeFormat(DBTransaction a_dbTransaction, UsageTypeFormat a_format)
/*      */     throws Bn2Exception
/*      */   {
/* 1997 */     Connection con = null;
/* 1998 */     PreparedStatement psql = null;
/* 1999 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 2001 */     if (transaction == null)
/*      */     {
/* 2003 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2008 */       con = transaction.getConnection();
/*      */ 
/* 2011 */       String sSQL = "UPDATE UsageTypeFormat SET Description=?, ImageWidth=?, ImageHeight=?,FormatId=?, ScaleUp=?, Density=?, JpegQuality=?, PreserveFormatList=?, ApplyStrip=?, CropToFit=?, OmitIfLowerRes=?, ConvertToColorSpaceId=?, Watermark=?, AllowMasking=?, PresetMaskId=?, PresetMaskColourId=? WHERE Id=?";
/*      */ 
/* 2013 */       psql = con.prepareStatement(sSQL);
/* 2014 */       int iCol = 1;
/* 2015 */       iCol = UsageTypeFormatDBUtil.prepareUsageTypeFormatStatement(a_format, psql, iCol);
/*      */ 
/* 2018 */       psql.setLong(iCol++, a_format.getId());
/*      */ 
/* 2021 */       psql.executeUpdate();
/* 2022 */       psql.close();
/*      */ 
/* 2024 */       sSQL = "DELETE FROM TranslatedUsageTypeFormat WHERE UsageTypeFormatId=?";
/* 2025 */       psql = con.prepareStatement(sSQL);
/* 2026 */       psql.setLong(1, a_format.getId());
/* 2027 */       psql.executeUpdate();
/* 2028 */       psql.close();
/*      */ 
/* 2031 */       insertUsageTypeFormatTranslations(a_format, con);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2035 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2039 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2043 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 2047 */       this.m_logger.error("SQL Exception whilst updating a usage type format : " + e);
/* 2048 */       throw new Bn2Exception("SQL Exception whilst updating a usage type format : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 2053 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2057 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2061 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void insertUsageTypeFormatTranslations(UsageTypeFormat a_format, Connection con)
/*      */     throws SQLException
/*      */   {
/* 2077 */     Iterator itTranslations = a_format.getTranslations().iterator();
/* 2078 */     while (itTranslations.hasNext())
/*      */     {
/* 2080 */       UsageTypeFormat.Translation translation = (UsageTypeFormat.Translation)itTranslations.next();
/*      */ 
/* 2082 */       if (translation.getLanguage().getId() > 0L)
/*      */       {
/* 2084 */         String sSQL = "INSERT INTO TranslatedUsageTypeFormat (UsageTypeFormatId,LanguageId,Description) VALUES (?,?,?)";
/* 2085 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 2086 */         psql.setLong(1, a_format.getId());
/* 2087 */         psql.setLong(2, translation.getLanguage().getId());
/* 2088 */         psql.setString(3, translation.getDescription());
/* 2089 */         psql.executeUpdate();
/* 2090 */         psql.close();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteUsageTypeValue(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 2112 */     Connection con = null;
/* 2113 */     PreparedStatement psql = null;
/* 2114 */     String sSQL = null;
/*      */     try
/*      */     {
/* 2118 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2121 */       Vector vecChildren = getUsageTypes(a_dbTransaction, a_lId);
/*      */ 
/* 2124 */       for (int i = 0; i < vecChildren.size(); i++)
/*      */       {
/* 2127 */         UsageType childUsage = (UsageType)vecChildren.get(i);
/*      */ 
/* 2130 */         deleteUsageTypeValue(a_dbTransaction, childUsage.getId());
/*      */       }
/*      */ 
/* 2136 */       sSQL = "UPDATE AssetUse SET UsageTypeId=? WHERE UsageTypeId=?";
/* 2137 */       psql = con.prepareStatement(sSQL);
/* 2138 */       psql.setNull(1, -5);
/* 2139 */       psql.setLong(2, a_lId);
/* 2140 */       psql.executeUpdate();
/* 2141 */       psql.close();
/*      */ 
/* 2143 */       sSQL = "UPDATE AssetApproval SET UsageTypeId=? WHERE UsageTypeId=?";
/* 2144 */       psql = con.prepareStatement(sSQL);
/* 2145 */       psql.setNull(1, -5);
/* 2146 */       psql.setLong(2, a_lId);
/* 2147 */       psql.executeUpdate();
/* 2148 */       psql.close();
/*      */ 
/* 2150 */       sSQL = "UPDATE SecondaryDownloadUsageType SET UsageTypeId=null WHERE UsageTypeId=?";
/* 2151 */       psql = con.prepareStatement(sSQL);
/* 2152 */       psql.setLong(1, a_lId);
/* 2153 */       psql.executeUpdate();
/* 2154 */       psql.close();
/*      */ 
/* 2165 */       sSQL = "DELETE FROM GroupUsageExclusion WHERE UsageTypeId=?";
/* 2166 */       psql = con.prepareStatement(sSQL);
/* 2167 */       psql.setLong(1, a_lId);
/* 2168 */       psql.executeUpdate();
/* 2169 */       psql.close();
/*      */ 
/* 2171 */       sSQL = "DELETE FROM PriceBandUsage WHERE UsageTypeId=?";
/* 2172 */       psql = con.prepareStatement(sSQL);
/* 2173 */       psql.setLong(1, a_lId);
/* 2174 */       psql.executeUpdate();
/* 2175 */       psql.close();
/*      */ 
/* 2177 */       sSQL = "SELECT Id FROM UsageTypeFormat WHERE UsageTypeId=?";
/* 2178 */       psql = con.prepareStatement(sSQL);
/* 2179 */       psql.setLong(1, a_lId);
/* 2180 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 2182 */       while (rs.next())
/*      */       {
/* 2184 */         sSQL = "DELETE FROM TranslatedUsageTypeFormat WHERE UsageTypeFormatId=?";
/* 2185 */         PreparedStatement psqlInner = con.prepareStatement(sSQL);
/* 2186 */         psqlInner.setLong(1, rs.getLong("Id"));
/* 2187 */         psqlInner.executeUpdate();
/* 2188 */         psqlInner.close();
/*      */       }
/*      */ 
/* 2191 */       psql.close();
/*      */ 
/* 2193 */       sSQL = "DELETE FROM UsageTypeFormat WHERE UsageTypeId=?";
/* 2194 */       psql = con.prepareStatement(sSQL);
/* 2195 */       psql.setLong(1, a_lId);
/* 2196 */       psql.executeUpdate();
/* 2197 */       psql.close();
/*      */ 
/* 2199 */       sSQL = "DELETE FROM TranslatedUsageType WHERE UsageTypeId=?";
/* 2200 */       psql = con.prepareStatement(sSQL);
/* 2201 */       psql.setLong(1, a_lId);
/* 2202 */       psql.executeUpdate();
/* 2203 */       psql.close();
/*      */ 
/* 2205 */       sSQL = "DELETE FROM UsageType WHERE Id=?";
/* 2206 */       psql = con.prepareStatement(sSQL);
/* 2207 */       psql.setLong(1, a_lId);
/* 2208 */       psql.executeUpdate();
/* 2209 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2216 */       throw new Bn2Exception("SQL Exception whilst deleting a usage type value from the database : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteUsageTypeFormat(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 2234 */     Connection con = null;
/* 2235 */     PreparedStatement psql = null;
/* 2236 */     String sSQL = null;
/* 2237 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 2239 */     if (transaction == null)
/*      */     {
/* 2241 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2246 */       con = transaction.getConnection();
/*      */ 
/* 2248 */       sSQL = "DELETE FROM TranslatedUsageTypeFormat WHERE UsageTypeFormatId=?";
/* 2249 */       psql = con.prepareStatement(sSQL);
/* 2250 */       psql.setLong(1, a_lId);
/* 2251 */       psql.executeUpdate();
/* 2252 */       psql.close();
/*      */ 
/* 2254 */       sSQL = "DELETE FROM UsageTypeFormat WHERE Id=?";
/* 2255 */       psql = con.prepareStatement(sSQL);
/* 2256 */       psql.setLong(1, a_lId);
/* 2257 */       psql.executeUpdate();
/* 2258 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2262 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2266 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2270 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 2274 */       this.m_logger.error("SQL Exception whilst deleting a usage type format from the database : " + e);
/* 2275 */       throw new Bn2Exception("SQL Exception whilst deleting a usage type format from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 2280 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2284 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2288 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateUsageTypeValue(DBTransaction a_dbTransaction, UsageType a_usageType)
/*      */     throws Bn2Exception
/*      */   {
/* 2313 */     Connection con = null;
/* 2314 */     PreparedStatement psql = null;
/* 2315 */     String sSQL = null;
/*      */     try
/*      */     {
/* 2319 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2321 */       sSQL = "UPDATE UsageType SET Description=?, DownloadTabId=?, AssetTypeId=?, CanEnterDetails=?, DetailsMandatory=?, Message=?, DownloadOriginal=?, IsConsideredHighRes=? WHERE Id=?";
/*      */ 
/* 2323 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 2325 */       int iField = 1;
/* 2326 */       psql.setString(iField++, a_usageType.getDescription());
/* 2327 */       psql.setInt(iField++, a_usageType.getDownloadTabId());
/* 2328 */       DBUtil.setFieldIdOrNull(psql, iField++, a_usageType.getAssetTypeId());
/* 2329 */       psql.setBoolean(iField++, a_usageType.getCanEnterDetails());
/* 2330 */       psql.setBoolean(iField++, a_usageType.getDetailsMandatory());
/* 2331 */       psql.setString(iField++, a_usageType.getMessage());
/* 2332 */       psql.setBoolean(iField++, a_usageType.getDownloadOriginal());
/* 2333 */       psql.setBoolean(iField++, a_usageType.getHighResolution());
/* 2334 */       psql.setLong(iField++, a_usageType.getId());
/*      */ 
/* 2336 */       psql.executeUpdate();
/* 2337 */       psql.close();
/*      */ 
/* 2339 */       sSQL = "DELETE FROM TranslatedUsageType WHERE UsageTypeId=?";
/* 2340 */       psql = con.prepareStatement(sSQL);
/* 2341 */       psql.setLong(1, a_usageType.getId());
/* 2342 */       psql.executeUpdate();
/*      */ 
/* 2345 */       insertUsageTypeTranslatsions(a_usageType, con);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2349 */       throw new Bn2Exception("SQL Exception whilst updating a usage type value in the database : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void insertUsageTypeTranslatsions(UsageType a_usageType, Connection con)
/*      */     throws SQLException
/*      */   {
/* 2363 */     Iterator itTranslations = a_usageType.getTranslations().iterator();
/* 2364 */     while (itTranslations.hasNext())
/*      */     {
/* 2366 */       UsageType.Translation translation = (UsageType.Translation)itTranslations.next();
/*      */ 
/* 2368 */       if (translation.getLanguage().getId() > 0L)
/*      */       {
/* 2370 */         String sSQL = "INSERT INTO TranslatedUsageType (UsageTypeId,LanguageId,Description,Message) VALUES (?,?,?,?)";
/* 2371 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 2372 */         psql.setLong(1, a_usageType.getId());
/* 2373 */         psql.setLong(2, translation.getLanguage().getId());
/* 2374 */         psql.setString(3, translation.getDescription());
/* 2375 */         psql.setString(4, translation.getMessage());
/* 2376 */         psql.executeUpdate();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void moveUsageTypeValue(DBTransaction a_dbTransaction, long a_lId, boolean a_bUp)
/*      */     throws Bn2Exception
/*      */   {
/* 2398 */     Connection con = null;
/* 2399 */     PreparedStatement psql1 = null;
/* 2400 */     String sSQL = null;
/* 2401 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2405 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2411 */       sSQL = "SELECT SequenceNumber, ParentId FROM UsageType WHERE Id=?";
/* 2412 */       psql1 = con.prepareStatement(sSQL);
/* 2413 */       psql1.setLong(1, a_lId);
/* 2414 */       rs = psql1.executeQuery();
/*      */ 
/* 2416 */       if (rs.next())
/*      */       {
/* 2418 */         int iOldSequenceNumber = rs.getInt("SequenceNumber");
/* 2419 */         long lParentId = rs.getLong("ParentId");
/*      */ 
/* 2424 */         sSQL = "SELECT Id,SequenceNumber FROM UsageType ";
/*      */ 
/* 2426 */         if (lParentId > 0L)
/*      */         {
/* 2428 */           sSQL = sSQL + "WHERE ParentId = " + lParentId;
/*      */         }
/*      */         else
/*      */         {
/* 2432 */           sSQL = sSQL + "WHERE ParentId IS NULL";
/*      */         }
/*      */ 
/* 2435 */         if (!a_bUp)
/*      */         {
/* 2437 */           sSQL = sSQL + " AND SequenceNumber>? AND IsEditable=1 ORDER BY SequenceNumber ASC";
/*      */         }
/*      */         else
/*      */         {
/* 2441 */           sSQL = sSQL + " AND SequenceNumber<? AND IsEditable=1 ORDER BY SequenceNumber DESC";
/*      */         }
/*      */ 
/* 2444 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 2445 */         psql.setInt(1, iOldSequenceNumber);
/* 2446 */         rs = psql.executeQuery();
/*      */ 
/* 2449 */         if (rs.next())
/*      */         {
/* 2451 */           sSQL = "UPDATE UsageType SET SequenceNumber=? WHERE Id=?";
/* 2452 */           PreparedStatement psql3 = con.prepareStatement(sSQL);
/* 2453 */           psql3.setInt(1, iOldSequenceNumber);
/* 2454 */           psql3.setLong(2, rs.getLong("Id"));
/* 2455 */           psql3.executeUpdate();
/* 2456 */           psql3.close();
/*      */ 
/* 2458 */           sSQL = "UPDATE UsageType SET SequenceNumber=? WHERE Id=?";
/* 2459 */           psql3 = con.prepareStatement(sSQL);
/* 2460 */           psql3.setInt(1, rs.getInt("SequenceNumber"));
/* 2461 */           psql3.setLong(2, a_lId);
/* 2462 */           psql3.executeUpdate();
/* 2463 */           psql3.close();
/*      */         }
/*      */ 
/* 2466 */         psql.close();
/*      */       }
/*      */ 
/* 2469 */       psql1.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2473 */       throw new Bn2Exception("SQL Exception whilst moving a usage type value in the database : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getAllColorSpaces(DBTransaction a_dbTransaction, boolean a_bAdvancedDownload)
/*      */     throws Bn2Exception
/*      */   {
/* 2485 */     return getColorSpaces(a_dbTransaction, -1, a_bAdvancedDownload);
/*      */   }
/*      */ 
/*      */   public Vector getColorSpaces(DBTransaction a_dbTransaction, int a_iAssetColorSpace, boolean a_bShowOnDownload)
/*      */     throws Bn2Exception
/*      */   {
/* 2496 */     Vector vResults = null;
/* 2497 */     Connection con = null;
/* 2498 */     ResultSet rs = null;
/* 2499 */     PreparedStatement psql = null;
/* 2500 */     String sSQL = null;
/* 2501 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 2503 */     if (transaction == null)
/*      */     {
/* 2505 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2510 */       con = transaction.getConnection();
/*      */ 
/* 2512 */       sSQL = "SELECT Id, Description, FileLocation FROM ColorSpace WHERE 1=1";
/*      */ 
/* 2515 */       if (a_bShowOnDownload)
/*      */       {
/* 2517 */         sSQL = sSQL + " AND ShowOnDownload = ?";
/*      */       }
/*      */ 
/* 2522 */       if ((a_iAssetColorSpace == 1) || (a_iAssetColorSpace == 13) || (a_iAssetColorSpace == 12))
/*      */       {
/* 2525 */         sSQL = sSQL + " AND Id != ?";
/*      */       }
/*      */ 
/* 2528 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 2530 */       int iCol = 1;
/*      */ 
/* 2532 */       if (a_bShowOnDownload)
/*      */       {
/* 2534 */         psql.setBoolean(iCol++, true);
/*      */       }
/*      */ 
/* 2538 */       if ((a_iAssetColorSpace == 1) || (a_iAssetColorSpace == 13))
/*      */       {
/* 2540 */         psql.setInt(iCol++, 1);
/*      */       }
/*      */ 
/* 2544 */       if (a_iAssetColorSpace == 12)
/*      */       {
/* 2546 */         psql.setInt(iCol++, 2);
/*      */       }
/*      */ 
/* 2549 */       rs = psql.executeQuery();
/*      */ 
/* 2551 */       vResults = new Vector();
/*      */ 
/* 2553 */       while (rs.next())
/*      */       {
/* 2555 */         ColorSpace cs = new ColorSpace();
/* 2556 */         cs.setId(rs.getLong("Id"));
/* 2557 */         cs.setDescription(rs.getString("Description"));
/* 2558 */         cs.setFileLocation(rs.getString("FileLocation"));
/* 2559 */         vResults.add(cs);
/*      */       }
/*      */ 
/* 2562 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2566 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2570 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2574 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 2578 */       this.m_logger.error("SQL Exception whilst getting color spaces from the database : " + e);
/* 2579 */       throw new Bn2Exception("SQL Exception whilst getting color spaces from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 2584 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2588 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2592 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2599 */     return vResults;
/*      */   }
/*      */ 
/*      */   public ColorSpace getColorSpace(DBTransaction a_dbTransaction, int a_iColorSpaceId)
/*      */     throws Bn2Exception
/*      */   {
/* 2605 */     DBTransaction transaction = a_dbTransaction;
/* 2606 */     ColorSpace cs = null;
/*      */ 
/* 2608 */     if (transaction == null)
/*      */     {
/* 2610 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2615 */       Connection con = transaction.getConnection();
/* 2616 */       String sSQL = "SELECT Description, FileLocation FROM ColorSpace WHERE Id=?";
/* 2617 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 2618 */       psql.setLong(1, a_iColorSpaceId);
/* 2619 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 2621 */       if (rs.next())
/*      */       {
/* 2623 */         cs = new ColorSpace();
/* 2624 */         cs.setId(a_iColorSpaceId);
/* 2625 */         cs.setDescription(rs.getString("Description"));
/* 2626 */         cs.setFileLocation(rs.getString("FileLocation"));
/*      */       }
/*      */ 
/* 2629 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2633 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2637 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2641 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 2645 */       this.m_logger.error("SQL Exception whilst getting color space from the database : " + e);
/* 2646 */       throw new Bn2Exception("SQL Exception whilst getting color space from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 2651 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2655 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2659 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2665 */     return cs;
/*      */   }
/*      */ 
/*      */   public ColorSpace getCurrentColorSpace(ImageAsset a_image, DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 2671 */     ColorSpace colorSpace = null;
/*      */ 
/* 2674 */     if (ABImageMagick.getIsCMYK(a_image.getColorSpace()))
/*      */     {
/* 2676 */       colorSpace = getColorSpace(a_dbTransaction, 2);
/*      */     }
/*      */     else
/*      */     {
/* 2681 */       colorSpace = getColorSpace(a_dbTransaction, 1);
/*      */     }
/*      */ 
/* 2684 */     return colorSpace;
/*      */   }
/*      */ 
/*      */   private UsageType buildUsageType(ResultSet a_rs) throws SQLException
/*      */   {
/* 2689 */     UsageType usageType = new UsageType();
/* 2690 */     usageType.setId(a_rs.getLong("utId"));
/* 2691 */     usageType.setParentId(a_rs.getLong("ParentId"));
/* 2692 */     usageType.setDescription(a_rs.getString("utDescription"));
/* 2693 */     usageType.setEditable(a_rs.getBoolean("utIsEditable"));
/* 2694 */     usageType.setDownloadTabId(a_rs.getInt("utDownloadTabId"));
/* 2695 */     usageType.setAssetTypeId(a_rs.getLong("utAssetTypeId"));
/* 2696 */     usageType.setCanEnterDetails(a_rs.getBoolean("utCanEnterDetails"));
/* 2697 */     usageType.setDetailsMandatory(a_rs.getBoolean("utDetailsMandatory"));
/* 2698 */     usageType.setMessage(a_rs.getString("utMessage"));
/* 2699 */     usageType.setDownloadOriginal(a_rs.getBoolean("utDownloadOriginal"));
/* 2700 */     usageType.setHighResolution(a_rs.getBoolean("utIsConsideredHighRes"));
/* 2701 */     return usageType;
/*      */   }
/*      */ 
/*      */   public UsageTypeFormat buildUsageTypeFormat(ResultSet a_rs)
/*      */     throws SQLException
/*      */   {
/* 2707 */     UsageTypeFormat usageTypeFormat = new UsageTypeFormat();
/* 2708 */     usageTypeFormat.setId(a_rs.getLong("utfId"));
/* 2709 */     usageTypeFormat.setDescription(a_rs.getString("utfDescription"));
/* 2710 */     usageTypeFormat.setFormatId(a_rs.getLong("utfFormatId"));
/* 2711 */     usageTypeFormat.setWidth(a_rs.getInt("utfImageWidth"));
/* 2712 */     usageTypeFormat.setHeight(a_rs.getInt("utfImageHeight"));
/* 2713 */     usageTypeFormat.setScaleUp(a_rs.getBoolean("utfScaleUp"));
/* 2714 */     usageTypeFormat.setUsageTypeId(a_rs.getLong("utfUsageTypeId"));
/* 2715 */     usageTypeFormat.setDensity(a_rs.getInt("utfDensity"));
/* 2716 */     usageTypeFormat.setJpegQuality(a_rs.getInt("utfJpegQuality"));
/* 2717 */     usageTypeFormat.setPreserveFormatList(a_rs.getString("utfPreserveFormatList"));
/* 2718 */     usageTypeFormat.setApplyStrip(a_rs.getBoolean("utfApplyStrip"));
/* 2719 */     usageTypeFormat.setCropToFit(a_rs.getBoolean("CropToFit"));
/* 2720 */     usageTypeFormat.setOmitIfLowerRes(a_rs.getBoolean("utfOmitIfLowerRes"));
/* 2721 */     usageTypeFormat.setColorSpace(a_rs.getLong("ConvertToColorSpaceId"));
/* 2722 */     usageTypeFormat.setWatermark(a_rs.getBoolean("Watermark"));
/* 2723 */     usageTypeFormat.setAllowMasking(a_rs.getBoolean("AllowMasking"));
/* 2724 */     usageTypeFormat.setPresetMaskId(a_rs.getLong("PresetMaskId"));
/* 2725 */     usageTypeFormat.setPresetMaskColourId(a_rs.getLong("PresetMaskColourId"));
/*      */ 
/* 2727 */     return usageTypeFormat;
/*      */   }
/*      */ 
/*      */   public ScheduleManager getScheduleManager()
/*      */   {
/* 2734 */     return this.m_scheduleManager;
/*      */   }
/*      */ 
/*      */   public void setScheduleManager(ScheduleManager a_sScheduleManager)
/*      */   {
/* 2740 */     this.m_scheduleManager = a_sScheduleManager;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*      */   {
/* 2745 */     this.m_transactionManager = a_sTransactionManager;
/*      */   }
/*      */ 
/*      */   public void setOrderManager(OrderManager a_sManager)
/*      */   {
/* 2750 */     this.m_orderManager = a_sManager;
/*      */   }
/*      */ 
/*      */   public void setAssetManager(AssetManager assetManager)
/*      */   {
/* 2755 */     this.m_assetManager = assetManager;
/*      */   }
/*      */ 
/*      */   public void setSearchManager(MultiLanguageSearchManager searchManager)
/*      */   {
/* 2760 */     this.m_searchManager = searchManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.service.UsageManager
 * JD-Core Version:    0.6.0
 */