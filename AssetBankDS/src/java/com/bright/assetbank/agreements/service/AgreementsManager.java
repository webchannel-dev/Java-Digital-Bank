/*      */ package com.bright.assetbank.agreements.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.actiononasset.action.ActionOnAsset;
/*      */ import com.bright.assetbank.actiononasset.action.RestrictAssetAction;
/*      */ import com.bright.assetbank.actiononasset.action.UnrestrictAssetAction;
/*      */ import com.bright.assetbank.agreements.bean.Agreement;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*      */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*      */ import com.bright.framework.common.bean.BrightDate;
/*      */ import com.bright.framework.common.service.ScheduleManager;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.TimerTask;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class AgreementsManager extends Bn2Manager
/*      */ {
/*      */   private static final String c_ksClassName = "AgreementsManager";
/*   62 */   private DBTransactionManager m_transactionManager = null;
/*   63 */   private ScheduleManager m_scheduleManager = null;
/*   64 */   private AssetRepurposingManager m_repurposingManager = null;
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*   76 */     super.startup();
/*      */ 
/*   78 */     int iSyncPeriod = AssetBankSettings.getAgreementExpirySyncPeriod();
/*      */ 
/*   80 */     if ((AssetBankSettings.getAgreementsEnabled()) && (iSyncPeriod >= 0))
/*      */     {
/*   83 */       TimerTask task = new TimerTask()
/*      */       {
/*      */         public void run()
/*      */         {
/*      */           try
/*      */           {
/*   89 */             AgreementsManager.this.processAgreementExpiry();
/*      */           }
/*      */           catch (Bn2Exception e)
/*      */           {
/*   93 */             AgreementsManager.this.m_logger.error("Could not process agreement expiry in scheduled task due to exception: " + e.getLocalizedMessage(), e);
/*      */           }
/*      */         }
/*      */       };
/*   99 */       this.m_logger.info("Scheduling agreement expiry task to run every " + iSyncPeriod + " minutes");
/*      */ 
/*  101 */       this.m_scheduleManager.schedule(task, 60000L, iSyncPeriod * 60L * 1000L, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void processAgreementExpiry()
/*      */     throws Bn2Exception
/*      */   {
/*  111 */     String ksMethodName = "processAgreementExpiry";
/*      */ 
/*  113 */     Connection con = null;
/*  114 */     String sSql = null;
/*  115 */     PreparedStatement psql = null;
/*  116 */     DBTransaction transaction = this.m_transactionManager.getNewTransaction();
/*      */     try
/*      */     {
/*  120 */       con = transaction.getConnection();
/*      */ 
/*  124 */       sSql = "SELECT ass.Id, a.Expiry FROM AssetAgreement aa INNER JOIN Agreement a ON aa.AgreementId=a.Id INNER JOIN Asset ass ON ass.Id=aa.AssetId WHERE aa.IsCurrent=? AND ass.ExpiryDate!=a.Expiry";
/*      */ 
/*  133 */       psql = con.prepareStatement(sSql);
/*      */ 
/*  135 */       psql.setBoolean(1, true);
/*      */ 
/*  137 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  139 */       if (rs.isBeforeFirst())
/*      */       {
/*  141 */         sSql = "UPDATE Asset SET ExpiryDate=? WHERE Id=?";
/*  142 */         PreparedStatement psqlUpdate = con.prepareStatement(sSql);
/*      */ 
/*  144 */         while (rs.next())
/*      */         {
/*  146 */           long lAssetId = rs.getLong("ass.Id");
/*  147 */           psqlUpdate.setDate(1, rs.getDate("a.Expiry"));
/*  148 */           psqlUpdate.setLong(2, lAssetId);
/*  149 */           psqlUpdate.executeUpdate();
/*      */ 
/*  151 */           if (AssetBankSettings.getAssetRepurposingEnabled())
/*      */           {
/*  153 */             this.m_repurposingManager.removeRepurposedImages(transaction, lAssetId);
/*      */           }
/*      */         }
/*      */ 
/*  157 */         psqlUpdate.close();
/*      */       }
/*      */ 
/*  160 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  164 */       if (transaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  168 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  172 */           this.m_logger.error("AgreementsManager.processAgreementExpiry : SQL Exception while rolling back : " + e);
/*      */         }
/*      */       }
/*  175 */       this.m_logger.error("AgreementsManager.processAgreementExpiry : SQL Exception : " + e);
/*  176 */       throw new Bn2Exception("AgreementsManager.processAgreementExpiry : SQL Exception : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  181 */       if (transaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  185 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  189 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void processAgreementsBeforeSave(DBTransaction a_dbTransaction, Asset a_asset)
/*      */     throws Bn2Exception
/*      */   {
/*  210 */     if (a_asset.getAgreementTypeId() > 0L)
/*      */     {
/*  213 */       if (a_asset.getAgreementTypeId() == 1L)
/*      */       {
/*  215 */         ActionOnAsset unrestrictAction = new UnrestrictAssetAction();
/*  216 */         unrestrictAction.performOnAssetBeforeSave(a_dbTransaction, a_asset);
/*      */       }
/*      */ 
/*  220 */       if (a_asset.getAgreementTypeId() == 2L)
/*      */       {
/*  222 */         ActionOnAsset unrestrictAction = new UnrestrictAssetAction();
/*  223 */         unrestrictAction.performOnAssetBeforeSave(a_dbTransaction, a_asset);
/*      */ 
/*  226 */         if (a_asset.getAgreement().getId() > 0L)
/*      */         {
/*  228 */           Agreement agreement = getAgreement(a_dbTransaction, a_asset.getAgreement().getId());
/*  229 */           if (agreement.getExpiry() != null)
/*      */           {
/*  231 */             a_asset.setExpiryDate(agreement.getExpiry().getDate());
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  237 */       if (a_asset.getAgreementTypeId() == 3L)
/*      */       {
/*  239 */         ActionOnAsset restrictAction = new RestrictAssetAction();
/*  240 */         restrictAction.performOnAssetBeforeSave(a_dbTransaction, a_asset);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void processAgreementAfterSave(DBTransaction a_dbTransaction, Asset a_asset, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/*  259 */     if (a_asset.getAgreementTypeId() > 0L)
/*      */     {
/*  262 */       if (a_asset.getAgreementTypeId() == 1L)
/*      */       {
/*  264 */         ActionOnAsset unrestrictAction = new UnrestrictAssetAction();
/*  265 */         unrestrictAction.performOnAssetAfterSave(a_dbTransaction, a_asset.getId(), a_lUserId);
/*      */       }
/*      */ 
/*  268 */       if (a_asset.getAgreementTypeId() == 2L)
/*      */       {
/*  271 */         if (a_asset.getAgreement().getId() > 0L)
/*      */         {
/*  273 */           addCurrentAssetAgreement(a_dbTransaction, a_asset.getAgreement().getId(), a_asset.getId());
/*      */         }
/*      */         else
/*      */         {
/*  278 */           deleteAllAssetAgreements(a_dbTransaction, a_asset.getId());
/*      */         }
/*      */ 
/*  281 */         ActionOnAsset unrestrictAction = new UnrestrictAssetAction();
/*  282 */         unrestrictAction.performOnAssetAfterSave(a_dbTransaction, a_asset.getId(), a_lUserId);
/*      */       }
/*      */ 
/*  285 */       if (a_asset.getAgreementTypeId() == 3L)
/*      */       {
/*  287 */         ActionOnAsset restrictAction = new RestrictAssetAction();
/*  288 */         restrictAction.performOnAssetBeforeSave(a_dbTransaction, a_asset);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getAgreements(DBTransaction a_dbTransaction, boolean a_bGetSharedAgreements, Vector a_vecOrgUnits, long a_lAssetId, boolean a_bGetAvailableAgreementsForAsset)
/*      */     throws Bn2Exception
/*      */   {
/*  312 */     String ksMethodName = "getAgreements";
/*  313 */     Connection con = null;
/*  314 */     String sSQL = null;
/*  315 */     PreparedStatement psql = null;
/*  316 */     Vector vecList = new Vector();
/*  317 */     Agreement a = new Agreement();
/*      */     try
/*      */     {
/*  321 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  323 */       sSQL = "SELECT a.Id, a.Title, a.Body, a.Expiry, a.AvailableToAll, a.SharedWithOU, oufa.OrgUnitId ";
/*      */ 
/*  325 */       if (a_lAssetId > 0L)
/*      */       {
/*  327 */         sSQL = sSQL + ", aa.DateActivated ";
/*      */       }
/*      */ 
/*  330 */       sSQL = sSQL + "FROM Agreement a ";
/*      */ 
/*  332 */       if (a_lAssetId > 0L)
/*      */       {
/*  334 */         if (a_bGetAvailableAgreementsForAsset)
/*      */         {
/*  336 */           sSQL = sSQL + "LEFT JOIN AssetAgreement aa ON aa.AgreementId = a.Id AND aa.AssetId=? ";
/*      */         }
/*      */         else
/*      */         {
/*  340 */           sSQL = sSQL + "INNER JOIN AssetAgreement aa ON aa.AgreementId = a.Id AND aa.AssetId=? ";
/*      */         }
/*      */       }
/*      */ 
/*  344 */       sSQL = sSQL + "LEFT JOIN OrgUnitForAgreement oufa ON oufa.AgreementId = a.Id ";
/*      */ 
/*  346 */       sSQL = sSQL + "WHERE 1=1 ";
/*      */ 
/*  348 */       if (a_bGetSharedAgreements)
/*      */       {
/*  350 */         sSQL = sSQL + "AND (a.SharedWithOU=? OR a.AvailableToAll=? ";
/*      */ 
/*  352 */         if ((a_lAssetId > 0L) && (a_bGetAvailableAgreementsForAsset))
/*      */         {
/*  354 */           sSQL = sSQL + " OR NOT " + SQLGenerator.getInstance().getNullCheckStatement("aa.AssetId");
/*      */         }
/*      */ 
/*  357 */         sSQL = sSQL + ") ";
/*      */       }
/*      */ 
/*  361 */       if (a_lAssetId > 0L)
/*      */       {
/*  363 */         sSQL = sSQL + "AND aa.IsCurrent=? ";
/*      */ 
/*  365 */         if (a_bGetAvailableAgreementsForAsset)
/*      */         {
/*  367 */           sSQL = sSQL + "ORDER BY " + SQLGenerator.getInstance().getLowerCaseFunction("a.Title");
/*      */         }
/*      */         else
/*      */         {
/*  371 */           sSQL = sSQL + "ORDER BY aa.DateActivated DESC";
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  376 */         sSQL = sSQL + "ORDER BY " + SQLGenerator.getInstance().getLowerCaseFunction("a.Title");
/*      */       }
/*      */ 
/*  379 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  381 */       int iCol = 1;
/*      */ 
/*  383 */       if (a_lAssetId > 0L)
/*      */       {
/*  385 */         psql.setLong(iCol++, a_lAssetId);
/*      */       }
/*      */ 
/*  388 */       if (a_bGetSharedAgreements)
/*      */       {
/*  390 */         psql.setBoolean(iCol++, true);
/*  391 */         psql.setBoolean(iCol++, true);
/*      */       }
/*      */ 
/*  394 */       if (a_lAssetId > 0L)
/*      */       {
/*  396 */         psql.setBoolean(iCol++, false);
/*      */       }
/*      */ 
/*  399 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  401 */       while (rs.next())
/*      */       {
/*  403 */         if (rs.getLong("Id") != a.getId())
/*      */         {
/*  405 */           a = new Agreement();
/*  406 */           a.setId(rs.getLong("Id"));
/*  407 */           a.setTitle(rs.getString("Title"));
/*  408 */           a.setBody(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "Body"));
/*  409 */           a.getExpiry().setDate(rs.getDate("Expiry"));
/*  410 */           a.setExpiryString(a.getExpiry().getDisplayDate());
/*  411 */           a.setIsAvailableToAll(rs.getBoolean("AvailableToAll"));
/*  412 */           a.setIsSharedWithOU(rs.getBoolean("SharedWithOU"));
/*      */ 
/*  414 */           if (a_lAssetId > 0L)
/*      */           {
/*  416 */             a.setDateActivated(rs.getTimestamp("DateActivated"));
/*      */           }
/*      */         }
/*      */ 
/*  420 */         if (((a_lAssetId <= 0L) || (a_bGetAvailableAgreementsForAsset)) && (vecList.contains(a)))
/*      */         {
/*  422 */           vecList.remove(a);
/*      */         }
/*      */ 
/*  425 */         Vector vecAgreementOrgUnits = a.getOrgUnitIds();
/*  426 */         vecAgreementOrgUnits.add(new Long(rs.getLong("OrgUnitId")));
/*  427 */         a.setOrgUnitIds(vecAgreementOrgUnits);
/*      */ 
/*  431 */         if ((a_vecOrgUnits == null) || (a_vecOrgUnits.isEmpty()))
/*      */         {
/*  433 */           vecList.add(a);
/*      */         }
/*      */         else
/*      */         {
/*  438 */           Iterator itOrgUnits = a_vecOrgUnits.iterator();
/*      */ 
/*  440 */           while (itOrgUnits.hasNext())
/*      */           {
/*  442 */             long lOrgUnitId = ((OrgUnit)itOrgUnits.next()).getId();
/*      */ 
/*  444 */             if ((a.getOrgUnitIds().contains(new Long(lOrgUnitId))) || (a.getIsAvailableToAll()))
/*      */             {
/*  446 */               vecList.add(a);
/*  447 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  453 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  462 */       throw new Bn2Exception("AgreementsManager.getAgreements: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  465 */     return vecList;
/*      */   }
/*      */ 
/*      */   public Agreement getAgreement(DBTransaction a_dbTransaction, long a_lAgreementId)
/*      */     throws Bn2Exception
/*      */   {
/*  482 */     return getAgreement(a_dbTransaction, a_lAgreementId, null, null);
/*      */   }
/*      */ 
/*      */   public long getAgreementId(DBTransaction a_dbTransaction, String a_sTitle)
/*      */     throws Bn2Exception
/*      */   {
/*  499 */     Agreement agreement = getAgreement(a_dbTransaction, 0L, a_sTitle, Boolean.TRUE);
/*      */ 
/*  501 */     return agreement == null ? -1L : agreement.getId();
/*      */   }
/*      */ 
/*      */   private Agreement getAgreement(DBTransaction a_dbTransaction, long a_lAgreementId, String a_sTitle, Boolean a_boolShared)
/*      */     throws Bn2Exception
/*      */   {
/*  518 */     String ksMethodName = "getAgreement";
/*  519 */     DBTransaction transaction = a_dbTransaction;
/*  520 */     Connection con = null;
/*  521 */     String sSQL = null;
/*  522 */     PreparedStatement psql = null;
/*  523 */     Agreement a = null;
/*      */ 
/*  525 */     if (transaction == null)
/*      */     {
/*  527 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  532 */       con = transaction.getConnection();
/*      */ 
/*  534 */       sSQL = "SELECT a.Id, a.Title, a.Body, a.Expiry, a.AvailableToAll, a.SharedWithOU, oufa.OrgUnitId FROM Agreement a LEFT JOIN OrgUnitForAgreement oufa ON oufa.AgreementId = a.Id ";
/*      */ 
/*  538 */       if (a_lAgreementId > 0L)
/*      */       {
/*  540 */         sSQL = sSQL + "WHERE a.Id=? ";
/*      */       }
/*      */       else
/*      */       {
/*  544 */         sSQL = sSQL + "WHERE " + SQLGenerator.getInstance().getLowerCaseFunction("a.Title") + "=? ";
/*      */       }
/*      */ 
/*  547 */       if (a_boolShared != null)
/*      */       {
/*  549 */         if (a_boolShared.booleanValue())
/*      */         {
/*  551 */           sSQL = sSQL + " AND (AvailableToAll=? OR SharedWithOU=?)";
/*      */         }
/*      */         else
/*      */         {
/*  555 */           sSQL = sSQL + " AND AvailableToAll=? AND SharedWithOU=?";
/*      */         }
/*      */       }
/*      */ 
/*  559 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  561 */       if (a_lAgreementId > 0L)
/*      */       {
/*  563 */         psql.setLong(1, a_lAgreementId);
/*      */       }
/*      */       else
/*      */       {
/*  567 */         String sTitle = a_sTitle;
/*  568 */         if (sTitle != null)
/*      */         {
/*  570 */           sTitle = StringUtils.trim(StringUtils.deleteWhitespace(sTitle));
/*      */         }
/*  572 */         psql.setString(1, sTitle);
/*      */       }
/*      */ 
/*  575 */       if (a_boolShared != null)
/*      */       {
/*  577 */         psql.setBoolean(2, a_boolShared.booleanValue());
/*  578 */         psql.setBoolean(3, a_boolShared.booleanValue());
/*      */       }
/*      */ 
/*  581 */       ResultSet rs = psql.executeQuery();
/*  582 */       long a_lLastId = 0L;
/*      */ 
/*  584 */       while (rs.next())
/*      */       {
/*  586 */         if (rs.getLong("Id") != a_lLastId)
/*      */         {
/*  588 */           a = new Agreement();
/*  589 */           a.setId(rs.getLong("Id"));
/*  590 */           a.setTitle(rs.getString("Title"));
/*  591 */           a.setBody(rs.getString("Body"));
/*  592 */           a.getExpiry().setDate(rs.getDate("Expiry"));
/*  593 */           a.setExpiryString(a.getExpiry().getDisplayDate());
/*  594 */           a.setIsAvailableToAll(rs.getBoolean("AvailableToAll"));
/*  595 */           a.setIsSharedWithOU(rs.getBoolean("SharedWithOU"));
/*      */ 
/*  597 */           a_lLastId = a.getId();
/*      */         }
/*      */ 
/*  601 */         a.getOrgUnitIds().add(new Long(rs.getLong("OrgUnitId")));
/*      */       }
/*      */ 
/*  604 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  611 */       throw new Bn2Exception("AgreementsManager.getAgreement: Exception occurred: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  616 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  620 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  624 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  629 */     return a;
/*      */   }
/*      */ 
/*      */   public long getAgreementStatusForAsset(DBTransaction a_dbTransaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/*  646 */     String ksMethodName = "getAgreementStatusForAsset";
/*  647 */     DBTransaction transaction = a_dbTransaction;
/*  648 */     Connection con = null;
/*  649 */     String sSQL = null;
/*  650 */     PreparedStatement psql = null;
/*  651 */     long lResult = 0L;
/*      */ 
/*  653 */     if (transaction == null)
/*      */     {
/*  655 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  660 */       con = transaction.getConnection();
/*      */ 
/*  662 */       sSQL = "SELECT AgreementTypeId FROM Asset WHERE Id=? ";
/*      */ 
/*  664 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  666 */       psql.setLong(1, a_lAssetId);
/*      */ 
/*  668 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  670 */       if (rs.next())
/*      */       {
/*  672 */         lResult = rs.getLong("AgreementTypeId");
/*      */       }
/*      */ 
/*  675 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  682 */       throw new Bn2Exception("AgreementsManager.getAgreementStatusForAsset: Exception occurred: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  687 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  691 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  695 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  700 */     return lResult;
/*      */   }
/*      */ 
/*      */   public void deleteAgreement(DBTransaction a_dbTransaction, long a_lAgreementId)
/*      */     throws Bn2Exception
/*      */   {
/*  717 */     String ksMethodName = "deleteAgreement";
/*  718 */     Connection con = null;
/*  719 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  723 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  725 */       String[] aSQL = { "DELETE FROM TranslatedAgreement WHERE AgreementId=?", "DELETE FROM OrgUnitForAgreement WHERE AgreementId=?", "DELETE FROM AssetAgreement WHERE AgreementId=?", "DELETE FROM Agreement WHERE Id=?" };
/*      */ 
/*  730 */       for (int i = 0; i < aSQL.length; i++)
/*      */       {
/*  732 */         psql = con.prepareStatement(aSQL[i]);
/*  733 */         psql.setLong(1, a_lAgreementId);
/*  734 */         psql.executeUpdate();
/*  735 */         psql.close();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  742 */       throw new Bn2Exception("AgreementsManager.deleteAgreement: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateAgreement(DBTransaction a_dbTransaction, Agreement a_agreement)
/*      */     throws Bn2Exception
/*      */   {
/*  757 */     String ksMethodName = "updateAgreement";
/*      */ 
/*  759 */     Connection con = null;
/*  760 */     String sSQL = null;
/*  761 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  765 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  767 */       sSQL = "UPDATE Agreement Set Title=?, Body=?, Expiry=?, AvailableToAll=?, SharedWithOU=? WHERE Id=?";
/*      */ 
/*  771 */       int iCol = 1;
/*      */ 
/*  773 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  775 */       psql.setString(iCol++, StringUtils.trim(a_agreement.getTitle()));
/*  776 */       psql.setString(iCol++, a_agreement.getBody());
/*  777 */       DBUtil.setFieldDateOrNull(psql, iCol++, a_agreement.getExpiry().getDate());
/*  778 */       psql.setBoolean(iCol++, a_agreement.getIsAvailableToAll());
/*  779 */       psql.setBoolean(iCol++, a_agreement.getIsSharedWithOU());
/*  780 */       psql.setLong(iCol++, a_agreement.getId());
/*      */ 
/*  782 */       psql.executeUpdate();
/*  783 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  788 */       throw new Bn2Exception("AgreementsManager.updateAgreement: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public long addAgreement(DBTransaction a_dbTransaction, Agreement a_agreement, Vector a_vecOrgUnits)
/*      */     throws Bn2Exception
/*      */   {
/*  803 */     String ksMethodName = "addAgreement";
/*      */ 
/*  805 */     Connection con = null;
/*  806 */     String sSQL = null;
/*  807 */     PreparedStatement psql = null;
/*  808 */     long lNewId = 0L;
/*      */ 
/*  810 */     AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */     try
/*      */     {
/*  814 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  816 */       sSQL = "INSERT INTO Agreement (";
/*      */ 
/*  818 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  820 */         lNewId = sqlGenerator.getUniqueId(con, "AgreementSequence");
/*  821 */         sSQL = sSQL + "Id, ";
/*      */       }
/*      */ 
/*  824 */       sSQL = sSQL + "Title, Body, Expiry, AvailableToAll, SharedWithOU) VALUES (";
/*      */ 
/*  826 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  828 */         sSQL = sSQL + "?,";
/*      */       }
/*      */ 
/*  831 */       sSQL = sSQL + "?,?,?,?,?)";
/*      */ 
/*  833 */       int iCol = 1;
/*      */ 
/*  835 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  837 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  839 */         psql.setLong(iCol++, lNewId);
/*      */       }
/*  841 */       psql.setString(iCol++, StringUtils.trim(a_agreement.getTitle()));
/*  842 */       psql.setString(iCol++, a_agreement.getBody());
/*  843 */       DBUtil.setFieldDateOrNull(psql, iCol++, a_agreement.getExpiry().getDate());
/*  844 */       psql.setBoolean(iCol++, a_agreement.getIsAvailableToAll());
/*  845 */       psql.setBoolean(iCol++, a_agreement.getIsSharedWithOU());
/*  846 */       psql.executeUpdate();
/*  847 */       psql.close();
/*      */ 
/*  849 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  851 */         lNewId = sqlGenerator.getUniqueId(con, null);
/*      */       }
/*      */ 
/*  855 */       if ((a_vecOrgUnits != null) && (!a_vecOrgUnits.isEmpty()))
/*      */       {
/*  857 */         Iterator itOrgUnits = a_vecOrgUnits.iterator();
/*      */ 
/*  859 */         while (itOrgUnits.hasNext())
/*      */         {
/*  861 */           long lOrgUnitId = ((OrgUnit)itOrgUnits.next()).getId();
/*      */ 
/*  863 */           sSQL = "INSERT INTO OrgUnitForAgreement (OrgUnitId, AgreementId) VALUES (?,?)";
/*      */ 
/*  865 */           psql = con.prepareStatement(sSQL);
/*  866 */           psql.setLong(1, lOrgUnitId);
/*  867 */           psql.setLong(2, lNewId);
/*  868 */           psql.executeUpdate();
/*  869 */           psql.close();
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  876 */       throw new Bn2Exception("AgreementsManager.addAgreement: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  879 */     return lNewId;
/*      */   }
/*      */ 
/*      */   public void addCurrentAssetAgreement(DBTransaction a_dbTransaction, long a_lAgreementId, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/*  892 */     addCurrentAssetAgreement(a_dbTransaction, a_lAgreementId, a_lAssetId, null);
/*      */   }
/*      */ 
/*      */   public void addCurrentAssetAgreement(DBTransaction a_dbTransaction, long a_lAgreementId, long a_lAssetId, Date a_dtDateActivated)
/*      */     throws Bn2Exception
/*      */   {
/*  905 */     String ksMethodName = "addAssetAgreement";
/*  906 */     DBTransaction transaction = a_dbTransaction;
/*  907 */     Connection con = null;
/*  908 */     String sSQL = null;
/*  909 */     PreparedStatement psql = null;
/*      */ 
/*  911 */     if (transaction == null)
/*      */     {
/*  913 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  920 */       con = transaction.getConnection();
/*      */ 
/*  922 */       sSQL = "SELECT * FROM AssetAgreement WHERE AssetId=? AND AgreementId=? AND IsCurrent=?";
/*  923 */       psql = con.prepareStatement(sSQL);
/*  924 */       psql.setLong(1, a_lAssetId);
/*  925 */       psql.setLong(2, a_lAgreementId);
/*  926 */       psql.setBoolean(3, true);
/*  927 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  929 */       boolean bExists = rs.next();
/*      */ 
/*  931 */       psql.close();
/*      */ 
/*  934 */       if (!bExists)
/*      */       {
/*  936 */         sSQL = "UPDATE AssetAgreement Set IsCurrent=? WHERE AssetId=? AND IsCurrent=?";
/*      */ 
/*  940 */         int iCol = 1;
/*      */ 
/*  942 */         psql = con.prepareStatement(sSQL);
/*      */ 
/*  944 */         psql.setBoolean(iCol++, false);
/*  945 */         psql.setLong(iCol++, a_lAssetId);
/*  946 */         psql.setBoolean(iCol++, true);
/*      */ 
/*  948 */         psql.executeUpdate();
/*  949 */         psql.close();
/*      */ 
/*  952 */         sSQL = "INSERT INTO AssetAgreement (AssetId, AgreementId, DateActivated, IsCurrent) VALUES (?,?,?,?)";
/*      */ 
/*  955 */         iCol = 1;
/*      */ 
/*  957 */         psql = con.prepareStatement(sSQL);
/*      */ 
/*  959 */         psql.setLong(iCol++, a_lAssetId);
/*  960 */         psql.setLong(iCol++, a_lAgreementId);
/*  961 */         DBUtil.setFieldTimestampOrNull(psql, iCol++, a_dtDateActivated != null ? a_dtDateActivated : new Date());
/*  962 */         psql.setBoolean(iCol++, true);
/*  963 */         psql.executeUpdate();
/*  964 */         psql.close();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       throw new Bn2Exception("AgreementsManager.addAssetAgreement: Exception occurred: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  987 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  991 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  995 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteAllAssetAgreements(DBTransaction a_dbTransaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1012 */     String ksMethodName = "deleteAssetAgreement";
/* 1013 */     DBTransaction transaction = a_dbTransaction;
/* 1014 */     Connection con = null;
/* 1015 */     String sSQL = null;
/* 1016 */     PreparedStatement psql = null;
/*      */ 
/* 1018 */     if (transaction == null)
/*      */     {
/* 1020 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1027 */       con = transaction.getConnection();
/*      */ 
/* 1029 */       sSQL = "DELETE FROM AssetAgreement WHERE AssetId=?";
/*      */ 
/* 1031 */       int iCol = 1;
/*      */ 
/* 1033 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 1035 */       psql.setLong(iCol++, a_lAssetId);
/*      */ 
/* 1037 */       psql.executeUpdate();
/* 1038 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       throw new Bn2Exception("AgreementsManager.deleteAssetAgreement: Exception occurred: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1061 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1065 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1069 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getOrgUnitsForAgreement(DBTransaction a_dbTransaction, long a_lAgreementId)
/*      */     throws Bn2Exception
/*      */   {
/* 1084 */     String ksMethodName = "getOrgUnitsForAgreement";
/* 1085 */     Connection con = null;
/* 1086 */     String sSQL = null;
/* 1087 */     PreparedStatement psql = null;
/* 1088 */     Vector vecOrgUnitIds = new Vector();
/*      */     try
/*      */     {
/* 1092 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1094 */       sSQL = "SELECT OrgUnitId FROM OrgUnitForAgreement oufa WHERE oufa.AgreementId=? ";
/*      */ 
/* 1098 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 1100 */       psql.setLong(1, a_lAgreementId);
/*      */ 
/* 1102 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1104 */       while (rs.next())
/*      */       {
/* 1106 */         vecOrgUnitIds.add(new Long(rs.getLong("OrgUnitId")));
/*      */       }
/*      */ 
/* 1109 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1115 */       throw new Bn2Exception("AgreementsManager.getOrgUnitsForAgreement: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1118 */     return vecOrgUnitIds;
/*      */   }
/*      */ 
/*      */   public Agreement getCurrentAgreementForAsset(DBTransaction a_dbTransaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1135 */     String ksMethodName = "getAgreementForAsset";
/* 1136 */     DBTransaction transaction = a_dbTransaction;
/* 1137 */     Connection con = null;
/* 1138 */     String sSQL = null;
/* 1139 */     PreparedStatement psql = null;
/* 1140 */     Agreement a = null;
/*      */ 
/* 1142 */     if (transaction == null)
/*      */     {
/* 1144 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1149 */       con = transaction.getConnection();
/*      */ 
/* 1151 */       sSQL = "SELECT a.Id, a.Title, a.Body, a.Expiry, a.AvailableToAll, a.SharedWithOU, aa.DateActivated FROM AssetAgreement aa INNER JOIN Agreement a ON aa.AgreementId = a.Id WHERE aa.IsCurrent=? AND aa.AssetId=? ";
/*      */ 
/* 1156 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 1158 */       int iCol = 1;
/* 1159 */       psql.setBoolean(iCol++, true);
/* 1160 */       psql.setLong(iCol++, a_lAssetId);
/*      */ 
/* 1162 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1164 */       if (rs.next())
/*      */       {
/* 1166 */         a = new Agreement();
/* 1167 */         a.setId(rs.getLong("Id"));
/* 1168 */         a.setTitle(rs.getString("Title"));
/* 1169 */         a.setBody(rs.getString("Body"));
/* 1170 */         a.getExpiry().setDate(rs.getDate("Expiry"));
/* 1171 */         a.setExpiryString(a.getExpiry().getDisplayDate());
/* 1172 */         a.setIsAvailableToAll(rs.getBoolean("AvailableToAll"));
/* 1173 */         a.setIsSharedWithOU(rs.getBoolean("SharedWithOU"));
/* 1174 */         a.setDateActivated(rs.getTimestamp("DateActivated"));
/*      */       }
/*      */ 
/* 1177 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1183 */       throw new Bn2Exception("AgreementsManager.getAgreementForAsset: Exception occurred: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1188 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1192 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1196 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1201 */     return a;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*      */   {
/* 1206 */     this.m_transactionManager = a_transactionManager;
/*      */   }
/*      */ 
/*      */   public void setScheduleManager(ScheduleManager a_sScheduleManager)
/*      */   {
/* 1211 */     this.m_scheduleManager = a_sScheduleManager;
/*      */   }
/*      */ 
/*      */   public void setRepurposingManager(AssetRepurposingManager a_manager)
/*      */   {
/* 1216 */     this.m_repurposingManager = a_manager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.agreements.service.AgreementsManager
 * JD-Core Version:    0.6.0
 */