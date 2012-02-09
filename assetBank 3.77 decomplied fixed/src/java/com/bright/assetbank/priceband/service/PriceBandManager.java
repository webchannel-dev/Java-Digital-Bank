/*      */ package com.bright.assetbank.priceband.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.priceband.bean.DownloadPriceBand;
/*      */ import com.bright.assetbank.priceband.bean.PriceBand;
/*      */ import com.bright.assetbank.priceband.bean.PriceBand.Translation;
/*      */ import com.bright.assetbank.priceband.bean.PrintPriceBand;
/*      */ import com.bright.assetbank.priceband.bean.QuantityRange;
/*      */ import com.bright.assetbank.priceband.bean.ShippingCost;
/*      */ import com.bright.assetbank.priceband.constant.PriceBandConstants;
/*      */ import com.bright.framework.common.bean.BrightMoney;
/*      */ import com.bright.framework.common.bean.BrightNaturalNumber;
/*      */ import com.bright.framework.common.bean.StringDataBean;
/*      */ import com.bright.framework.common.bean.TranslatableStringDataBean;
/*      */ //import com.bright.framework.common.bean.TranslatableStringDataBean.Translation;
/*      */ import com.bright.framework.common.service.RefDataManager;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Vector;
/*      */ import org.apache.avalon.excalibur.datasource.DataSourceComponent;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class PriceBandManager extends Bn2Manager
/*      */   implements PriceBandConstants
/*      */ {
/*      */   private static final String c_ksClassName = "PriceBandManager";
/*   56 */   protected DataSourceComponent m_dataSource = null;
/*      */ 
/*   63 */   private BrightMoney[] m_lowestPrice = null;
/*   64 */   private BrightMoney[] m_highestPrice = null;
/*      */ 
/*      */   public void setDataSourceComponent(DataSourceComponent a_datasource)
/*      */   {
/*   59 */     this.m_dataSource = a_datasource;
/*      */   }
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*   71 */     this.m_lowestPrice = new BrightMoney[4];
/*   72 */     this.m_highestPrice = new BrightMoney[4];
/*      */   }
/*      */ 
/*      */   public Vector getPriceBandList(DBTransaction a_dbTransaction, long a_lAssetTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*   90 */     String ksMethodName = "getPriceBandList";
/*   91 */     Connection con = null;
/*   92 */     Vector vecList = null;
/*      */     try
/*      */     {
/*   96 */       con = a_dbTransaction.getConnection();
/*   97 */       vecList = getPriceBandList(con, a_lAssetTypeId);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  103 */       throw new Bn2Exception("PriceBandManager.getPriceBandList: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  106 */     return vecList;
/*      */   }
/*      */ 
/*      */   public Vector getPriceBandList(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  124 */     return getPriceBandList(a_dbTransaction, 0L);
/*      */   }
/*      */ 
/*      */   private Vector getPriceBandList(Connection a_con, long a_lAssetTypeId)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/*  143 */     String ksMethodName = "getPriceBandList";
/*  144 */     String sSQL = null;
/*  145 */     PreparedStatement psql = null;
/*  146 */     Vector vecList = new Vector();
/*      */ 
/*  148 */     sSQL = "SELECT pbt.Name pbtName, pbt.Id pbtId, tpbt.Name tpbtName, pb.Name pbName, pb.Id pbId, pb.Description pbDescription, pb.BasePrice, pb.UnitPrice, pb.DownloadOriginal, pb.MaxQuantity, pb.IsCommercial, at.Id atId, at.Name atName, tpb.Name tpbName, tpb.Description tpbDescription, l.Id lId, l.Code lCode, l.Name lName FROM PriceBand pb INNER JOIN PriceBandType pbt ON pb.PriceBandTypeId = pbt.Id INNER JOIN AssetType at ON pb.AssetTypeId = at.Id LEFT JOIN TranslatedPriceBand tpb ON tpb.PriceBandId = pb.Id LEFT JOIN TranslatedPriceBandType tpbt ON tpbt.PriceBandTypeId = pbt.Id LEFT JOIN Language l ON l.Id = tpb.LanguageId AND l.Id = tpbt.LanguageId ";
/*      */ 
/*  175 */     if (a_lAssetTypeId > 0L)
/*      */     {
/*  177 */       sSQL = sSQL + "WHERE pb.AssetTypeId=? ";
/*      */     }
/*      */ 
/*  180 */     sSQL = sSQL + "ORDER BY at.Id, pbt.Id, pb.Name ";
/*      */ 
/*  182 */     psql = a_con.prepareStatement(sSQL);
/*      */ 
/*  184 */     if (a_lAssetTypeId > 0L)
/*      */     {
/*  186 */       psql.setLong(1, a_lAssetTypeId);
/*      */     }
/*      */ 
/*  189 */     ResultSet rs = psql.executeQuery();
/*  190 */     long lLastPriceBandId = 0L;
/*  191 */     PriceBand pb = null;
/*      */ 
/*  193 */     while (rs.next())
/*      */     {
/*  195 */       if (lLastPriceBandId != rs.getLong("pbId"))
/*      */       {
/*  197 */         long lPriceBandType = rs.getLong("pbtId");
/*  198 */         if (lPriceBandType == 1L)
/*      */         {
/*  200 */           pb = new DownloadPriceBand();
/*  201 */           DownloadPriceBand dpb = (DownloadPriceBand)pb;
/*  202 */           dpb.setCanDownloadOriginal(rs.getBoolean("DownloadOriginal"));
/*  203 */           dpb.setIsCommercial(rs.getBoolean("IsCommercial"));
/*      */         }
/*  205 */         else if (lPriceBandType == 2L)
/*      */         {
/*  207 */           pb = new PrintPriceBand();
/*  208 */           PrintPriceBand ppb = (PrintPriceBand)pb;
/*  209 */           ppb.getMaxQuantity().setNumber(rs.getLong("MaxQuantity"));
/*      */         }
/*      */         else
/*      */         {
/*  213 */           throw new Bn2Exception("PriceBandManager.getPriceBandList: Reference data in PriceBandType invalid. ");
/*      */         }
/*      */ 
/*  216 */         pb.setId(rs.getLong("pbId"));
/*  217 */         pb.setName(rs.getString("pbName"));
/*  218 */         pb.getPriceBandType().setId(lPriceBandType);
/*  219 */         pb.getPriceBandType().setName(rs.getString("pbtName"));
/*  220 */         pb.setDescription(rs.getString("pbDescription"));
/*  221 */         pb.getBasePrice().setAmount(rs.getLong("BasePrice"));
/*  222 */         pb.getUnitPrice().setAmount(rs.getLong("UnitPrice"));
/*  223 */         pb.getAssetType().setId(rs.getLong("atId"));
/*  224 */         pb.getAssetType().setName(rs.getString("atName"));
/*      */ 
/*  226 */         vecList.add(pb);
/*      */ 
/*  228 */         lLastPriceBandId = pb.getId();
/*      */       }
/*      */ 
/*  232 */       buildTranslation(rs, pb);
/*      */     }
/*  234 */     psql.close();
/*      */ 
/*  236 */     return vecList;
/*      */   }
/*      */ 
/*      */   private void buildTranslation(ResultSet rs, PriceBand pb) throws SQLException
/*      */   {
/*  241 */     if (rs.getLong("lId") > 0L)
/*      */     {
/*  243 */       Language lang = new Language(rs.getLong("lId"), rs.getString("lName"), rs.getString("lCode"));
/*      */       //PriceBand tmp50_49 = pb; tmp50_49.getClass(); PriceBand.Translation translation = new PriceBand.Translation(tmp50_49, lang);
/*  246 */       PriceBand.Translation translation = pb.new Translation(lang);
                 translation.setDescription(rs.getString("tpbDescription"));
/*  247 */       translation.setName(rs.getString("tpbName"));
/*  248 */       pb.getTranslations().add(translation);
/*      */      // TranslatableStringDataBean tmp105_102 = pb.getPriceBandType(); tmp105_102.getClass(); TranslatableStringDataBean.Translation type = new TranslatableStringDataBean.Translation(tmp105_102, lang);
                 TranslatableStringDataBean.Translation type = pb.getPriceBandType().new Translation(lang);
/*  251 */       type.setName(rs.getString("tpbtName"));
/*  252 */       pb.getPriceBandType().getTranslations().add(type);
/*      */     }
/*      */   }
/*      */ 
/*      */   public PriceBand getPriceBand(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  274 */     String ksMethodName = "getPriceBand";
/*  275 */     Connection con = null;
/*  276 */     String sSQL = null;
/*  277 */     PreparedStatement psql = null;
/*  278 */     PriceBand pb = null;
/*      */     try
/*      */     {
/*  282 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  284 */       sSQL = "SELECT pbt.Name pbtName, pbt.Id pbtId, tpbt.Name tpbtName, pb.Name pbName, pb.Id pbId, pb.Description pbDescription, pb.BasePrice, pb.UnitPrice, pb.DownloadOriginal, pb.MaxQuantity, pb.IsCommercial, at.Id atId, at.Name atName, tpb.Name tpbName, tpb.Description tpbDescription, l.Id lId, l.Code lCode, l.Name lName FROM PriceBand pb INNER JOIN PriceBandType pbt ON pb.PriceBandTypeId = pbt.Id INNER JOIN AssetType at ON pb.AssetTypeId = at.Id LEFT JOIN TranslatedPriceBand tpb ON tpb.PriceBandId = pb.Id LEFT JOIN TranslatedPriceBandType tpbt ON tpbt.PriceBandTypeId = pbt.Id LEFT JOIN Language l ON l.Id = tpb.LanguageId AND l.Id = tpbt.LanguageId WHERE pb.Id = ? ";
/*      */ 
/*  312 */       psql = con.prepareStatement(sSQL);
/*  313 */       psql.setLong(1, a_lId);
/*  314 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  316 */       while (rs.next())
/*      */       {
/*  318 */         if (pb == null)
/*      */         {
/*  320 */           long lPriceBandType = rs.getLong("pbtId");
/*  321 */           if (lPriceBandType == 1L)
/*      */           {
/*  323 */             pb = new DownloadPriceBand();
/*  324 */             DownloadPriceBand dpb = (DownloadPriceBand)pb;
/*  325 */             dpb.setCanDownloadOriginal(rs.getBoolean("DownloadOriginal"));
/*  326 */             dpb.setIsCommercial(rs.getBoolean("IsCommercial"));
/*      */           }
/*  328 */           else if (lPriceBandType == 2L)
/*      */           {
/*  330 */             pb = new PrintPriceBand();
/*  331 */             PrintPriceBand ppb = (PrintPriceBand)pb;
/*  332 */             ppb.getMaxQuantity().setNumber(rs.getLong("MaxQuantity"));
/*      */           }
/*      */           else
/*      */           {
/*  336 */             throw new Bn2Exception("PriceBandManager.getPriceBand: Reference data in PriceBandType invalid. ");
/*      */           }
/*      */ 
/*  339 */           pb.setId(rs.getLong("pbId"));
/*  340 */           pb.setName(rs.getString("pbName"));
/*  341 */           pb.getPriceBandType().setId(lPriceBandType);
/*  342 */           pb.getPriceBandType().setName(rs.getString("pbtName"));
/*  343 */           pb.setDescription(rs.getString("pbDescription"));
/*  344 */           pb.getBasePrice().setAmount(rs.getLong("BasePrice"));
/*  345 */           pb.getUnitPrice().setAmount(rs.getLong("UnitPrice"));
/*  346 */           pb.getAssetType().setId(rs.getLong("atId"));
/*  347 */           pb.getAssetType().setName(rs.getString("atName"));
/*      */         }
/*      */ 
/*  351 */         buildTranslation(rs, pb);
/*      */       }
/*      */ 
/*  354 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  359 */       throw new Bn2Exception("PriceBandManager.getPriceBand: Exception occurred: " + e);
/*      */     }
/*      */ 
/*  363 */     if (pb != null)
/*      */     {
/*  365 */       long lPriceBandTypeId = pb.getPriceBandType().getId();
/*  366 */       long lId = pb.getId();
/*      */ 
/*  368 */       if (lPriceBandTypeId == 1L)
/*      */       {
/*  370 */         DownloadPriceBand dpb = (DownloadPriceBand)pb;
/*  371 */         dpb.setUsageTypes(getUsagesForPriceBand(a_dbTransaction, lId));
/*      */       }
/*  373 */       else if (lPriceBandTypeId == 2L)
/*      */       {
/*  375 */         PrintPriceBand ppb = (PrintPriceBand)pb;
/*  376 */         ppb.setShippingCostsMatrix(getShippingCostsForPriceBand(a_dbTransaction, lId));
/*      */       }
/*      */     }
/*      */ 
/*  380 */     return pb;
/*      */   }
/*      */ 
/*      */   public void savePriceBand(DBTransaction a_dbTransaction, PriceBand a_pb)
/*      */     throws Bn2Exception
/*      */   {
/*  399 */     long lId = a_pb.getId();
/*      */ 
/*  401 */     if (lId > 0L)
/*      */     {
/*  403 */       updatePriceBand(a_dbTransaction, a_pb);
/*      */     }
/*      */     else
/*      */     {
/*  407 */       newPriceBand(a_dbTransaction, a_pb);
/*      */     }
/*      */ 
/*  410 */     refreshCachedPrices(a_dbTransaction);
/*      */   }
/*      */ 
/*      */   private void newPriceBand(DBTransaction a_dbTransaction, PriceBand a_pb)
/*      */     throws Bn2Exception
/*      */   {
/*  429 */     String ksMethodName = "newPriceBand";
/*  430 */     Connection con = null;
/*  431 */     String sSQL = null;
/*  432 */     PreparedStatement psql = null;
/*  433 */     long lNewId = 0L;
/*      */     try
/*      */     {
/*  437 */       con = a_dbTransaction.getConnection();
/*  438 */       sSQL = "INSERT INTO PriceBand (";
/*      */ 
/*  440 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*  441 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  443 */         lNewId = sqlGenerator.getUniqueId(con, "PriceBandSequence");
/*  444 */         sSQL = sSQL + "Id, ";
/*      */       }
/*  446 */       sSQL = sSQL + "PriceBandTypeId, Name, Description, BasePrice, UnitPrice, DownloadOriginal, MaxQuantity, IsCommercial, AssetTypeId) VALUES (";
/*      */ 
/*  448 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  450 */         sSQL = sSQL + "?, ";
/*      */       }
/*  452 */       sSQL = sSQL + "?,?,?,?,?,?,?,?,?)";
/*      */ 
/*  454 */       psql = con.prepareStatement(sSQL);
/*  455 */       int iCol = 1;
/*  456 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  458 */         psql.setLong(iCol++, lNewId);
/*      */       }
/*  460 */       psql.setLong(iCol++, a_pb.getPriceBandType().getId());
/*  461 */       psql.setString(iCol++, a_pb.getName());
/*  462 */       psql.setString(iCol++, a_pb.getDescription());
/*  463 */       psql.setLong(iCol++, a_pb.getBasePrice().getAmount());
/*  464 */       psql.setLong(iCol++, a_pb.getUnitPrice().getAmount());
/*      */ 
/*  466 */       boolean bDownloadOriginal = false;
/*  467 */       boolean bIsCommercial = false;
/*  468 */       if (a_pb.getPriceBandType().getId() == 1L)
/*      */       {
/*  470 */         DownloadPriceBand dpb = (DownloadPriceBand)a_pb;
/*  471 */         bDownloadOriginal = dpb.getCanDownloadOriginal();
/*  472 */         bIsCommercial = dpb.getIsCommercial();
/*      */       }
/*      */ 
/*  475 */       long lMaxQuantity = 0L;
/*  476 */       if (a_pb.getPriceBandType().getId() == 2L)
/*      */       {
/*  478 */         PrintPriceBand ppb = (PrintPriceBand)a_pb;
/*  479 */         lMaxQuantity = ppb.getMaxQuantity().getNumber();
/*      */       }
/*      */ 
/*  482 */       psql.setBoolean(iCol++, bDownloadOriginal);
/*  483 */       psql.setLong(iCol++, lMaxQuantity);
/*  484 */       psql.setBoolean(iCol++, bIsCommercial);
/*  485 */       psql.setLong(iCol++, a_pb.getAssetType().getId());
/*      */ 
/*  487 */       psql.executeUpdate();
/*  488 */       psql.close();
/*      */ 
/*  490 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  492 */         lNewId = sqlGenerator.getUniqueId(con, "PriceBand");
/*      */       }
/*  494 */       a_pb.setId(lNewId);
/*      */ 
/*  497 */       insertTranslations(a_pb, con);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  502 */       throw new Bn2Exception("PriceBandManager.newPriceBand: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updatePriceBand(DBTransaction a_dbTransaction, PriceBand a_pb)
/*      */     throws Bn2Exception
/*      */   {
/*  522 */     String ksMethodName = "updatePriceBand";
/*  523 */     Connection con = null;
/*  524 */     String sSQL = null;
/*  525 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  529 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  531 */       sSQL = "UPDATE PriceBand SET PriceBandTypeId = ?, Name = ?, Description = ?, BasePrice = ?, UnitPrice = ?, DownloadOriginal = ?, MaxQuantity = ?, IsCommercial=?, AssetTypeId=?  WHERE Id = ? ";
/*      */ 
/*  534 */       psql = con.prepareStatement(sSQL);
/*  535 */       int iCol = 1;
/*  536 */       psql.setLong(iCol++, a_pb.getPriceBandType().getId());
/*  537 */       psql.setString(iCol++, a_pb.getName());
/*  538 */       psql.setString(iCol++, a_pb.getDescription());
/*  539 */       psql.setLong(iCol++, a_pb.getBasePrice().getAmount());
/*  540 */       psql.setLong(iCol++, a_pb.getUnitPrice().getAmount());
/*      */ 
/*  542 */       boolean bDownloadOriginal = false;
/*  543 */       boolean bIsCommercial = false;
/*  544 */       if (a_pb.getPriceBandType().getId() == 1L)
/*      */       {
/*  546 */         DownloadPriceBand dpb = (DownloadPriceBand)a_pb;
/*  547 */         bDownloadOriginal = dpb.getCanDownloadOriginal();
/*  548 */         bIsCommercial = dpb.getIsCommercial();
/*      */       }
/*      */ 
/*  551 */       long lMaxQuantity = 0L;
/*  552 */       if (a_pb.getPriceBandType().getId() == 2L)
/*      */       {
/*  554 */         PrintPriceBand ppb = (PrintPriceBand)a_pb;
/*  555 */         lMaxQuantity = ppb.getMaxQuantity().getNumber();
/*      */       }
/*      */ 
/*  558 */       psql.setBoolean(iCol++, bDownloadOriginal);
/*  559 */       psql.setLong(iCol++, lMaxQuantity);
/*  560 */       psql.setBoolean(iCol++, bIsCommercial);
/*  561 */       psql.setLong(iCol++, a_pb.getAssetType().getId());
/*      */ 
/*  563 */       psql.setLong(iCol++, a_pb.getId());
/*      */ 
/*  565 */       psql.executeUpdate();
/*  566 */       psql.close();
/*      */ 
/*  568 */       sSQL = "DELETE FROM TranslatedPriceBand WHERE PriceBandId=?";
/*  569 */       psql = con.prepareStatement(sSQL);
/*  570 */       psql.setLong(1, a_pb.getId());
/*  571 */       psql.executeUpdate();
/*  572 */       psql.close();
/*      */ 
/*  575 */       insertTranslations(a_pb, con);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  580 */       throw new Bn2Exception("PriceBandManager.updatePriceBand: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void insertTranslations(PriceBand a_pb, Connection a_con)
/*      */     throws SQLException
/*      */   {
/*  588 */     Iterator itTranslations = a_pb.getTranslations().iterator();
/*      */ 
/*  590 */     while (itTranslations.hasNext())
/*      */     {
/*  592 */       PriceBand.Translation translation = (PriceBand.Translation)itTranslations.next();
/*      */ 
/*  594 */       if (translation.getLanguage().getId() > 0L)
/*      */       {
/*  596 */         String sSQL = "INSERT INTO TranslatedPriceBand (PriceBandId,LanguageId,Name,Description) VALUES (?,?,?,?)";
/*  597 */         PreparedStatement psql = a_con.prepareStatement(sSQL);
/*  598 */         psql.setLong(1, a_pb.getId());
/*  599 */         psql.setLong(2, translation.getLanguage().getId());
/*  600 */         psql.setString(3, translation.getName());
/*  601 */         psql.setString(4, translation.getDescription());
/*  602 */         psql.executeUpdate();
/*  603 */         psql.close();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deletePriceBand(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  623 */     String ksMethodName = "deletePriceBand";
/*  624 */     Connection con = null;
/*  625 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  629 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  631 */       String[] aSql = { "DELETE FROM TranslatedPriceBand WHERE PriceBandId=?", "DELETE FROM PriceBandUsage WHERE PriceBandId=?", "DELETE FROM ShippingCost WHERE PriceBandId=?", "DELETE FROM AssetBoxPriceBand WHERE PriceBandId=?", "DELETE FROM PriceBand WHERE Id=?" };
/*      */ 
/*  639 */       for (int i = 0; i < aSql.length; i++)
/*      */       {
/*  641 */         psql = con.prepareStatement(aSql[i]);
/*  642 */         psql.setLong(1, a_lId);
/*  643 */         psql.executeUpdate();
/*  644 */         psql.close();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  651 */       throw new Bn2Exception("PriceBandManager.deletePriceBand: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  654 */     refreshCachedPrices(a_dbTransaction);
/*      */   }
/*      */ 
/*      */   public void refreshCachedPrices(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  668 */     String ksMethodName = "refreshCachedPrices";
/*  669 */     Connection con = null;
/*      */     try
/*      */     {
/*  673 */       con = a_dbTransaction.getConnection();
/*  674 */       refreshCachedPrices(con);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  680 */       throw new Bn2Exception("PriceBandManager.refreshCachedPrices: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void refreshCachedPrices()
/*      */     throws Bn2Exception
/*      */   {
/*  699 */     String ksMethodName = "refreshCachedPrices";
/*  700 */     Connection cCon = null;
/*      */     try
/*      */     {
/*  704 */       cCon = this.m_dataSource.getConnection();
/*      */ 
/*  706 */       refreshCachedPrices(cCon);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */       try
/*      */       {
/*  713 */         cCon.rollback();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*      */       }
/*      */ 
/*  720 */       throw new Bn2Exception("PriceBandManager.refreshCachedPrices: Exception occurred: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  725 */       if (cCon != null)
/*      */       {
/*      */         try
/*      */         {
/*  729 */           cCon.commit();
/*  730 */           cCon.close();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void refreshCachedPrices(Connection a_con)
/*      */     throws Bn2Exception
/*      */   {
/*  745 */     String ksMethodName = "refreshCachedPrices";
/*      */     try
/*      */     {
/*  749 */       Vector vec = getPriceBandList(a_con, 0L);
/*  750 */       Iterator it = vec.iterator();
/*      */ 
/*  752 */       long[] lLowest = { 0L, 0L, 0L, 0L };
/*  753 */       long[] lHighest = { 0L, 0L, 0L, 0L };
/*      */ 
/*  755 */       while (it.hasNext())
/*      */       {
/*  757 */         PriceBand pb = (PriceBand)it.next();
/*  758 */         long lPriceBandPrice = pb.getBasePrice().getAmount();
/*      */ 
/*  760 */         int lAssetTypeIndex = (int)pb.getAssetType().getId() - 1;
/*      */ 
/*  762 */         if (lLowest[lAssetTypeIndex] == 0L)
/*      */         {
/*  764 */           lLowest[lAssetTypeIndex] = lPriceBandPrice;
/*      */         }
/*  766 */         else if ((lPriceBandPrice < lLowest[lAssetTypeIndex]) && (lPriceBandPrice > 0L))
/*      */         {
/*  768 */           lLowest[lAssetTypeIndex] = lPriceBandPrice;
/*      */         }
/*      */ 
/*  771 */         if (lPriceBandPrice > lHighest[lAssetTypeIndex])
/*      */         {
/*  773 */           lHighest[lAssetTypeIndex] = lPriceBandPrice;
/*      */         }
/*      */       }
/*      */ 
/*  777 */       for (int i = 0; i < 4; i++)
/*      */       {
/*  779 */         this.m_lowestPrice[i] = new BrightMoney(lLowest[i]);
/*  780 */         this.m_highestPrice[i] = new BrightMoney(lHighest[i]);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  785 */       throw new Bn2Exception("PriceBandManager.refreshCachedPrices: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public BrightMoney getLowestPriceBandPrice(String a_sAssetTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  799 */     return getLowestPriceBandPrice(Long.parseLong(a_sAssetTypeId));
/*      */   }
/*      */ 
/*      */   public BrightMoney getLowestPriceBandPrice(long a_lAssetTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  816 */     if (this.m_lowestPrice[0] == null)
/*      */     {
/*  818 */       refreshCachedPrices();
/*      */     }
/*      */ 
/*  821 */     return this.m_lowestPrice[((int)a_lAssetTypeId - 1)];
/*      */   }
/*      */ 
/*      */   public BrightMoney getHighestPriceBandPrice(long a_lAssetTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  839 */     if (this.m_highestPrice[0] == null)
/*      */     {
/*  841 */       refreshCachedPrices();
/*      */     }
/*      */ 
/*  844 */     return this.m_highestPrice[((int)a_lAssetTypeId - 1)];
/*      */   }
/*      */ 
/*      */   public Vector getUsagesForPriceBand(DBTransaction a_transaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  867 */     String ksMethodName = "getUsagesForPriceBand";
/*  868 */     Vector vecResults = new Vector();
/*      */     try
/*      */     {
/*  872 */       Connection con = a_transaction.getConnection();
/*      */ 
/*  875 */       String sSql = "SELECT UsageTypeId FROM PriceBandUsage WHERE PriceBandId=?";
/*  876 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  877 */       psql.setLong(1, a_lId);
/*      */ 
/*  879 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  881 */       while (rs.next())
/*      */       {
/*  883 */         Long olUsageId = new Long(rs.getLong("UsageTypeId"));
/*  884 */         vecResults.add(olUsageId);
/*      */       }
/*      */ 
/*  887 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/*  892 */       this.m_logger.error("PriceBandManager.getUsagesForPriceBand - " + sqe);
/*  893 */       throw new Bn2Exception("PriceBandManager.getUsagesForPriceBand", sqe);
/*      */     }
/*      */ 
/*  896 */     return vecResults;
/*      */   }
/*      */ 
/*      */   public void removeUsagesForPriceBand(DBTransaction a_transaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  911 */     String ksMethodName = "removeUsagesForPriceBand";
/*      */     try
/*      */     {
/*  915 */       Connection con = a_transaction.getConnection();
/*      */ 
/*  918 */       String sSql = "DELETE FROM PriceBandUsage WHERE PriceBandId=?";
/*  919 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  920 */       psql.setLong(1, a_lId);
/*      */ 
/*  922 */       psql.executeUpdate();
/*  923 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/*  927 */       this.m_logger.error("PriceBandManager.removeUsagesForPriceBand - " + sqe);
/*  928 */       throw new Bn2Exception("PriceBandManager.removeUsagesForPriceBand", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addPriceBandUsage(DBTransaction a_transaction, long a_lPriceBandId, long a_lUsageTypeId)
/*      */     throws Bn2Exception
/*      */   {
/*  945 */     String ksMethodName = "addPriceBandUsage";
/*      */     try
/*      */     {
/*  949 */       Connection con = a_transaction.getConnection();
/*      */ 
/*  952 */       String sSql = "INSERT INTO PriceBandUsage (PriceBandId, UsageTypeId) VALUES (?,?)";
/*  953 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  954 */       psql.setLong(1, a_lPriceBandId);
/*  955 */       psql.setLong(2, a_lUsageTypeId);
/*      */ 
/*  957 */       psql.executeUpdate();
/*  958 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/*  962 */       this.m_logger.error("PriceBandManager.addPriceBandUsage - " + sqe);
/*  963 */       throw new Bn2Exception("PriceBandManager.addPriceBandUsage", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public HashMap getShippingCostsForPriceBand(DBTransaction a_dbTransaction, long a_lPriceBandId)
/*      */     throws Bn2Exception
/*      */   {
/*  986 */     String ksMethodName = "getShippingCostsForPriceBand";
/*  987 */     Connection con = null;
/*  988 */     String sSQL = null;
/*  989 */     PreparedStatement psql = null;
/*      */ 
/*  992 */     HashMap hmRegion = new HashMap();
/*      */     try
/*      */     {
/*  996 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  998 */       sSQL = "SELECT sc.Price, qr.Id qrId, qr.Name qrName, qr.LowerLimit qrLowerLimit, tr.Id trId, tr.Name trName FROM ShippingCost sc INNER JOIN QuantityRange qr ON sc.QuantityRangeId = qr.Id INNER JOIN TaxRegion tr ON sc.TaxRegionId = tr.Id WHERE sc.PriceBandId = ? ORDER BY tr.Id, qr.LowerLimit ";
/*      */ 
/* 1007 */       psql = con.prepareStatement(sSQL);
/* 1008 */       psql.setLong(1, a_lPriceBandId);
/* 1009 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1011 */       while (rs.next())
/*      */       {
/* 1014 */         long lTaxRegionId = rs.getLong("trId");
/* 1015 */         long lQuantityRangeId = rs.getLong("qrId");
/*      */ 
/* 1018 */         ShippingCost sc = new ShippingCost();
/* 1019 */         sc.setPriceBandId(a_lPriceBandId);
/* 1020 */         sc.getPrice().setAmount(rs.getLong("Price"));
/* 1021 */         sc.getQuantityRange().setId(lQuantityRangeId);
/* 1022 */         sc.getQuantityRange().setName(rs.getString("qrName"));
/* 1023 */         sc.getQuantityRange().setLowerLimit(rs.getLong("qrLowerLimit"));
/* 1024 */         sc.getTaxRegion().setId(lTaxRegionId);
/* 1025 */         sc.getTaxRegion().setName(rs.getString("trName"));
/*      */ 
/* 1028 */         HashMap hmQuantities = null;
/* 1029 */         if (hmRegion.containsKey(new Long(lTaxRegionId)))
/*      */         {
/* 1031 */           hmQuantities = (HashMap)hmRegion.get(new Long(lTaxRegionId));
/*      */         }
/*      */         else
/*      */         {
/* 1035 */           hmQuantities = new HashMap();
/* 1036 */           hmRegion.put(new Long(lTaxRegionId), hmQuantities);
/*      */         }
/*      */ 
/* 1040 */         hmQuantities.put(new Long(lQuantityRangeId), sc);
/*      */       }
/* 1042 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1048 */       throw new Bn2Exception("PriceBandManager.getShippingCostsForPriceBand: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1051 */     return hmRegion;
/*      */   }
/*      */ 
/*      */   public void saveShippingCosts(DBTransaction a_dbTransaction, Vector a_vec, long a_lPriceBandId)
/*      */     throws Bn2Exception
/*      */   {
/* 1071 */     String ksMethodName = "saveShippingCosts";
/*      */ 
/* 1074 */     deleteShippingCosts(a_dbTransaction, a_lPriceBandId);
/*      */ 
/* 1076 */     Connection con = null;
/* 1077 */     String sSql = null;
/* 1078 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/* 1082 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1084 */       sSql = "INSERT INTO ShippingCost (PriceBandId, TaxRegionId, QuantityRangeId, Price) VALUES (?, ?, ?, ?) ";
/*      */ 
/* 1086 */       Iterator it = a_vec.iterator();
/* 1087 */       while (it.hasNext())
/*      */       {
/* 1089 */         ShippingCost sc = (ShippingCost)it.next();
/*      */ 
/* 1091 */         psql = con.prepareStatement(sSql);
/* 1092 */         psql.setLong(1, a_lPriceBandId);
/* 1093 */         psql.setLong(2, sc.getTaxRegion().getId());
/* 1094 */         psql.setLong(3, sc.getQuantityRange().getId());
/* 1095 */         psql.setLong(4, sc.getPrice().getAmount());
/*      */ 
/* 1097 */         psql.executeUpdate();
/* 1098 */         psql.close();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1105 */       throw new Bn2Exception("PriceBandManager.saveShippingCosts: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteShippingCosts(DBTransaction a_dbTransaction, long a_lPriceBandId)
/*      */     throws Bn2Exception
/*      */   {
/* 1126 */     String ksMethodName = "deleteShippingCosts";
/* 1127 */     Connection con = null;
/* 1128 */     String sSql = null;
/* 1129 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/* 1133 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1135 */       sSql = "DELETE FROM ShippingCost WHERE PriceBandId=? ";
/*      */ 
/* 1137 */       psql = con.prepareStatement(sSql);
/* 1138 */       psql.setLong(1, a_lPriceBandId);
/* 1139 */       psql.executeUpdate();
/* 1140 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1146 */       throw new Bn2Exception("PriceBandManager.deleteShippingCosts: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getRegionList()
/*      */     throws Bn2Exception
/*      */   {
/* 1168 */     Vector vec = RefDataManager.getNameList(this.m_dataSource, "SELECT Id, Name FROM TaxRegion ORDER BY Id");
/* 1169 */     return vec;
/*      */   }
/*      */ 
/*      */   public Vector getPriceBandTypeList()
/*      */     throws Bn2Exception
/*      */   {
/* 1187 */     Vector vec = RefDataManager.getNameList(this.m_dataSource, "SELECT Id, Name FROM PriceBandType ORDER BY Name");
/* 1188 */     return vec;
/*      */   }
/*      */ 
/*      */   public Vector getQuantityRangeList()
/*      */     throws Bn2Exception
/*      */   {
/* 1206 */     Vector vec = RefDataManager.getNameList(this.m_dataSource, "SELECT Id, Name FROM QuantityRange ORDER BY LowerLimit");
/* 1207 */     return vec;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.priceband.service.PriceBandManager
 * JD-Core Version:    0.6.0
 */