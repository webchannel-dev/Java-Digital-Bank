/*     */ package com.bright.assetbank.custom.indesign.search.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.custom.indesign.search.bean.InDesignSearchCriteria;
/*     */ import com.bright.assetbank.externalfilter.service.ExternalFilter;
/*     */ import com.bright.assetbank.externalfilter.util.ExternalFilterUtil;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.exception.SQLStatementException;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class InDesignExternalFilter
/*     */   implements ExternalFilter
/*     */ {
/*     */   public static final String c_ksClassName = "InDesignExternalFilter";
/*     */   private DBTransactionManager m_transactionManager;
/*     */   private Log m_logger;
/*     */ 
/*     */   public InDesignExternalFilter()
/*     */     throws Bn2Exception
/*     */   {
/*  57 */     String dataSourceName = FrameworkSettings.getExternalFilterTransactionManagerName();
/*     */     try
/*     */     {
/*  60 */       ComponentManager componentManager = GlobalApplication.getInstance().getComponentManager();
/*  61 */       this.m_transactionManager = ((DBTransactionManager)componentManager.lookup(dataSourceName));
/*     */     }
/*     */     catch (ComponentException e)
/*     */     {
/*  65 */       throw new Bn2Exception("InDesignExternalFilter: exception getting component " + e.getKey() + " from component manager", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void validateSearchCriteria(Map<String, String> a_criteria, List<String> a_errors)
/*     */   {
/*  72 */     inDesignCriteriaFromMap(a_criteria, a_errors);
/*     */   }
/*     */ 
/*     */   public boolean emptySearchCriteria(SearchQuery a_criteria)
/*     */   {
/*  77 */     Map externalFilterCriteria = a_criteria.getExternalFilterCriteria();
/*     */ 
/*  79 */     boolean bEmpty = true;
/*     */ 
/*  81 */     if ((externalFilterCriteria != null) && (!externalFilterCriteria.isEmpty()))
/*     */     {
/*  83 */       InDesignSearchCriteria criteria = inDesignCriteriaFromMap(externalFilterCriteria);
/*  84 */       bEmpty = criteria.isEmpty();
/*     */     }
/*     */ 
/*  87 */     return bEmpty;
/*     */   }
/*     */ 
/*     */   private InDesignSearchCriteria inDesignCriteriaFromMap(Map<String, String> a_externalFilterCriteria)
/*     */   {
/*  92 */     return inDesignCriteriaFromMap(a_externalFilterCriteria, new ArrayList());
/*     */   }
/*     */ 
/*     */   private InDesignSearchCriteria inDesignCriteriaFromMap(Map<String, String> a_externalFilterCriteria, List<String> a_errors)
/*     */   {
/*  97 */     InDesignSearchCriteria criteria = new InDesignSearchCriteria();
/*  98 */     criteria.setIsTemplate(ExternalFilterUtil.readBooleanParam(a_externalFilterCriteria, "isTemplate", "template", a_errors));
/*     */ 
/* 103 */     return criteria;
/*     */   }
/*     */ 
/*     */   public Collection<Long> externalSearch(SearchQuery a_searchQuery) throws Bn2Exception
/*     */   {
/* 108 */     String ksMethodName = "externalSearch";
/* 109 */     Map criteriaMap = a_searchQuery.getExternalFilterCriteria();
/* 110 */     if ((criteriaMap == null) || (criteriaMap.isEmpty()))
/*     */     {
/* 112 */       this.m_logger.info("InDesignExternalFilter.externalSearch: The external filter criteria map was null or empty, returning null to indicate that no filtering should be done");
/*     */ 
/* 114 */       return null;
/*     */     }
/*     */ 
/* 117 */     InDesignSearchCriteria criteria = inDesignCriteriaFromMap(criteriaMap);
/* 118 */     if (criteria.isEmpty())
/*     */     {
/* 120 */       this.m_logger.info("InDesignExternalFilter.externalSearch: The InDesign search criteria object did not contain any criteria, returning null to indicate that no filtering should be done");
/*     */ 
/* 122 */       return null;
/*     */     }
/*     */ 
/* 127 */     DBTransaction transaction = getTransactionManager().getCurrentOrNewTransaction();
/*     */     Set filterIds;
/*     */     try
/*     */     {
/* 131 */       filterIds = findAssetIds(transaction, criteria);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 138 */         transaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 142 */         this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */       }
/*     */     }
/*     */ 
/* 146 */     return filterIds;
/*     */   }
/*     */ 
/*     */   private Set<Long> findAssetIds(DBTransaction a_transaction, InDesignSearchCriteria a_criteria)
/*     */     throws Bn2Exception
/*     */   {
/* 159 */     if (a_criteria.isEmpty())
/*     */     {
/* 161 */       throw new IllegalArgumentException("findAssetIds passed empty search criteria");
/*     */     }
/*     */ 
/* 168 */     boolean isTemplate = a_criteria.getIsTemplate().booleanValue();
/*     */ 
/* 170 */     String sSQL = "SELECT AssetId FROM InDesignAsset WHERE IsTemplate = ?";
/*     */ 
/* 175 */     Set assetIds = new HashSet();
/*     */     try
/*     */     {
/* 179 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 181 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 182 */       psql.setBoolean(1, isTemplate);
/*     */ 
/* 184 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 186 */       while (rs.next())
/*     */       {
/* 188 */         assetIds.add(Long.valueOf(rs.getLong("AssetId")));
/*     */       }
/*     */ 
/* 191 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 195 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */ 
/* 198 */     return assetIds;
/*     */   }
/*     */ 
/*     */   public void setLogger(Log a_logger)
/*     */   {
/* 203 */     this.m_logger = a_logger;
/*     */   }
/*     */ 
/*     */   public void clearIndex()
/*     */     throws Bn2Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   public void indexAsset(Asset a_asset, boolean a_bReindex)
/*     */     throws Bn2Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   public void indexAssets(List<Asset> a_vec, boolean a_bReindex, boolean a_bQuick, boolean a_bOnlyUsageChanged)
/*     */     throws Bn2Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   public void removeAsset(long a_lAssetId)
/*     */     throws Bn2Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   private DBTransactionManager getTransactionManager()
/*     */   {
/* 232 */     return this.m_transactionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.search.service.InDesignExternalFilter
 * JD-Core Version:    0.6.0
 */