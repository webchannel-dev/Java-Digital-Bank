/*     */ package com.bright.assetbank.workset.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.batch.update.constant.UpdateSettings;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*     */ import com.bright.assetbank.workset.bean.UnsubmittedAssets;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class AssetWorksetManager extends Bn2Manager
/*     */ {
/*     */   private static final String c_ksClassName = "AssetWorksetManager";
/*  47 */   protected MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*  53 */   private AssetWorkflowManager m_assetWorkflowManager = null;
/*     */ 
/*  59 */   private AssetManager m_assetManager = null;
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/*  50 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setAssetWorkflowManager(AssetWorkflowManager a_wm)
/*     */   {
/*  56 */     this.m_assetWorkflowManager = a_wm;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/*  62 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public UnsubmittedAssets getUnsubmittedAssetsForUser(long a_lUserId, String a_sLangCode)
/*     */     throws Bn2Exception
/*     */   {
/*  78 */     UnsubmittedAssets assets = new UnsubmittedAssets();
/*  79 */     Vector vecAssets = null;
/*     */ 
/*  81 */     SearchCriteria searchCriteria = new SearchCriteria();
/*  82 */     searchCriteria.setIsUnsubmitted(Boolean.valueOf(true));
/*  83 */     searchCriteria.setAddedByUserId(a_lUserId);
/*     */ 
/*  85 */     searchCriteria.setMaxNoOfResults(UpdateSettings.getMaxBulkUpdateResults());
/*     */ 
/*  88 */     SearchResults searchResults = this.m_searchManager.search(searchCriteria, a_sLangCode);
/*     */ 
/*  91 */     vecAssets = searchResults.getSearchResults();
/*  92 */     assets.setListAssets(vecAssets);
/*  93 */     assets.setMaxResultsExceeded(searchResults.getMaxResultsExceeded());
/*     */ 
/*  95 */     return assets;
/*     */   }
/*     */ 
/*     */   public Vector<StringDataBean> getUsersWithUnsubmittedAssets(DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 107 */     String ksMethodName = "getUsersWithUnsubmittedAssets";
/* 108 */     Vector vecResults = new Vector();
/*     */     try
/*     */     {
/* 112 */       Connection con = a_dbTransaction.getConnection();
/*     */ 
/* 114 */       String sSql = "SELECT u.Id userId, Forename, Surname, Username FROM AssetBankUser u WHERE EXISTS (SELECT 1 FROM Asset a WHERE u.Id = a.AddedByUserId AND a.IsUnsubmitted = 1)";
/*     */ 
/* 118 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 119 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 121 */       AssetWorkflowManager.populateUsersFromRs(rs, vecResults);
/*     */ 
/* 123 */       psql.close();
/*     */     }
/*     */     catch (SQLException sqe)
/*     */     {
/* 128 */       this.m_logger.error("AssetWorksetManager.getUsersWithUnsubmittedAssets - " + sqe);
/* 129 */       throw new Bn2Exception("AssetWorksetManager.getUsersWithUnsubmittedAssets", sqe);
/*     */     }
/*     */ 
/* 132 */     return vecResults;
/*     */   }
/*     */ 
/*     */   public Vector<LightweightAsset> getAssetsForBatchUpdate(long a_lUserId, String a_sLangCode)
/*     */     throws Bn2Exception
/*     */   {
/* 149 */     UnsubmittedAssets unsubmittedAssets = getUnsubmittedAssetsForUser(a_lUserId, a_sLangCode);
/* 150 */     Vector vecAssets = unsubmittedAssets.getListAssets();
/* 151 */     return vecAssets;
/*     */   }
/*     */ 
/*     */   public long getNumUnsubmittedAssets(ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 166 */     long lNumUnsubmittedAssets = 0L;
/* 167 */     if ((a_userProfile != null) && (a_userProfile.getUser() != null))
/*     */     {
/* 169 */       long lUserId = a_userProfile.getUser().getId();
/* 170 */       String sLangCode = a_userProfile.getCurrentLanguage().getCode();
/* 171 */       UnsubmittedAssets unsubmittedAssets = getUnsubmittedAssetsForUser(lUserId, sLangCode);
/* 172 */       Vector vecAssets = unsubmittedAssets.getListAssets();
/* 173 */       lNumUnsubmittedAssets = vecAssets.size();
/*     */     }
/* 175 */     return lNumUnsubmittedAssets;
/*     */   }
/*     */ 
/*     */   public void submitAssetToLive(DBTransaction a_dbTransaction, long a_lAssetId)
/*     */     throws Bn2Exception
/*     */   {
/* 189 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, a_lAssetId, null, false, false);
/* 190 */     submitAssetToLive(a_dbTransaction, asset);
/*     */   }
/*     */ 
/*     */   public void submitAssetToLive(DBTransaction a_dbTransaction, Asset a_asset)
/*     */     throws Bn2Exception
/*     */   {
/* 197 */     this.m_assetWorkflowManager.approveAssetEndAllWorkflows(a_dbTransaction, a_asset, true);
/*     */   }
/*     */ 
/*     */   public void submitAssetForApproval(DBTransaction a_dbTransaction, long a_lAssetId, long a_lUserId, long a_lSessionId)
/*     */     throws Bn2Exception
/*     */   {
/* 212 */     this.m_assetWorkflowManager.initiateAssetWorkflow(a_dbTransaction, a_lAssetId, true, a_lUserId, a_lSessionId, false);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workset.service.AssetWorksetManager
 * JD-Core Version:    0.6.0
 */