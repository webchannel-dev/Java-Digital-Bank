/*      */ package com.bright.assetbank.assetbox.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.exception.AssetBoxNotFoundException;
/*      */ import com.bright.assetbank.application.exception.StaleDataException;
/*      */ import com.bright.assetbank.application.service.IAssetManager;
/*      */ import com.bright.assetbank.approval.bean.AssetApproval;
/*      */ import com.bright.assetbank.approval.bean.AssetInList;
/*      */ import com.bright.assetbank.approval.service.AssetApprovalManager;
/*      */ import com.bright.assetbank.assetbox.bean.AssetBasket;
/*      */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*      */ import com.bright.assetbank.assetbox.bean.AssetBoxShare;
/*      */ import com.bright.assetbank.assetbox.bean.AssetBoxSummary;
/*      */ import com.bright.assetbank.assetbox.bean.AssetPrice;
/*      */ import com.bright.assetbank.assetbox.bean.SharedAssetBox;
/*      */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.priceband.bean.PriceBand;
/*      */ import com.bright.assetbank.priceband.service.PriceBandManager;
/*      */ import com.bright.assetbank.tax.bean.TaxValue;
/*      */ import com.bright.assetbank.tax.service.TaxManager;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.assetbank.user.exception.NoPermissionException;
/*      */ import com.bright.assetbank.user.service.ABUserManager;
/*      */ import com.bright.framework.common.bean.Address;
/*      */ import com.bright.framework.common.bean.Country;
/*      */ import com.bright.framework.common.bean.StringDataBean;
/*      */ import com.bright.framework.common.bean.TranslatableStringDataBean;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.mail.bean.EmailContent;
/*      */ import com.bright.framework.mail.service.EmailManager;
/*      */ import com.bright.framework.service.FileStoreManager;
/*      */ import com.bright.framework.simplelist.bean.ListItem;
/*      */ import com.bright.framework.simplelist.service.ListManager;
/*      */ import com.bright.framework.user.bean.User;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import javax.mail.MessagingException;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.commons.mail.HtmlEmail;
/*      */ 
/*      */ public class AssetBoxManager extends Bn2Manager
/*      */   implements AssetBankConstants, AssetBoxConstants
/*      */ {
/*      */   private static final String c_ksClassName = "AssetBoxManager";
/*  126 */   private IAssetManager m_assetManager = null;
/*  127 */   private FileStoreManager m_fileStoreManager = null;
/*  128 */   private DBTransactionManager m_transactionManager = null;
/*  129 */   private AssetApprovalManager m_assetApprovalManager = null;
/*  130 */   private TaxManager m_taxManager = null;
/*  131 */   private PriceBandManager m_pbManager = null;
/*  132 */   private ABUserManager m_userManager = null;
/*  133 */   private EmailManager m_emailManager = null;
/*  134 */   private ListManager m_listManager = null;
/*      */ 
/*      */   public void setPriceBandManager(PriceBandManager a_pbManager)
/*      */   {
/*  138 */     this.m_pbManager = a_pbManager;
/*      */   }
/*      */ 
/*      */   public void refreshAssetBoxesInProfile(DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/*      */     try
/*      */     {
/*  155 */       refreshAssetBoxesInProfile(a_dbTransaction, a_userProfile, 0L, false);
/*      */     }
/*      */     catch (AssetBoxNotFoundException e)
/*      */     {
/*  159 */       throw new Bn2Exception("AssetBoxManager.setAssetBoxInProfile(DBTransaction,ABUserProfile) - unexpected AssetBoxNotFoundException caught", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void resetAssetBoxesInProfile(DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/*      */     try
/*      */     {
/*  176 */       a_userProfile.setAssetBox(null);
/*  177 */       refreshAssetBoxesInProfile(a_dbTransaction, a_userProfile, 0L, false);
/*      */     }
/*      */     catch (AssetBoxNotFoundException e)
/*      */     {
/*  181 */       throw new Bn2Exception("AssetBoxManager.setAssetBoxInProfile(DBTransaction,ABUserProfile) - unexpected AssetBoxNotFoundException caught", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void refreshAssetBoxInProfileOrFail(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, long a_lAssetBoxId)
/*      */     throws Bn2Exception, AssetBoxNotFoundException
/*      */   {
/*  196 */     refreshAssetBoxesInProfile(a_dbTransaction, a_userProfile, a_lAssetBoxId, true);
/*      */   }
/*      */ 
/*      */   public void refreshAssetBoxInProfile(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, long a_lAssetBoxId)
/*      */     throws Bn2Exception
/*      */   {
/*      */     try
/*      */     {
/*  213 */       refreshAssetBoxesInProfile(a_dbTransaction, a_userProfile, a_lAssetBoxId, false);
/*      */     }
/*      */     catch (AssetBoxNotFoundException e)
/*      */     {
/*  218 */       this.m_logger.error("AssetBoxManager.refreshAssetBoxInProfile() : Unexpected AssetBoxNotFoundException", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void refreshAssetBoxesInProfile(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, long a_lAssetBoxId, boolean a_bFailIfNotFound)
/*      */     throws Bn2Exception, AssetBoxNotFoundException, NoPermissionException
/*      */   {
/*  243 */     long lCurrentAssetBoxId = a_userProfile.getAssetBox() != null ? a_userProfile.getAssetBox().getId() : a_lAssetBoxId > 0L ? a_lAssetBoxId : 0L;
/*      */ 
/*  245 */     if (a_userProfile.getUser() != null)
/*      */     {
/*  247 */       if (AssetBankSettings.isMultipleLightboxesEnabled())
/*      */       {
/*  249 */         List assetBoxes = loadAssetBoxSummariesFromDB(a_dbTransaction, a_userProfile.getUser().getId());
/*  250 */         a_userProfile.setAssetBoxes(assetBoxes);
/*      */ 
/*  253 */         if (lCurrentAssetBoxId == 0L)
/*      */         {
/*  255 */           AssetBoxSummary summary = (AssetBoxSummary)assetBoxes.get(0);
/*  256 */           lCurrentAssetBoxId = summary.getId();
/*      */         }
/*      */ 
/*      */       }
/*  260 */       else if (lCurrentAssetBoxId == 0L)
/*      */       {
/*  262 */         lCurrentAssetBoxId = getFirstAssetBoxIdForUser(a_dbTransaction, a_userProfile.getUser().getId());
/*      */       }
/*      */ 
/*  266 */       AssetBox assetBox = loadAssetBoxFromDB(a_dbTransaction, a_userProfile, lCurrentAssetBoxId, a_bFailIfNotFound);
/*      */ 
/*  268 */       if ((!a_userProfile.getIsAdmin()) && (AssetBankSettings.getCanRestrictAssets()) && (AssetBankSettings.getHideRestrictedImages()))
/*      */       {
/*  270 */         Iterator it = assetBox.getAssets().iterator();
/*  271 */         while (it.hasNext())
/*      */         {
/*  273 */           AssetInList asset = (AssetInList)it.next();
/*  274 */           asset.getAsset().setOverrideRestriction(this.m_assetManager.userCanViewRestrictedAsset(a_userProfile, asset.getAsset()));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  279 */       AssetBox oldAssetBox = a_userProfile.getAssetBox();
/*      */ 
/*  281 */       if (oldAssetBox != null)
/*      */       {
/*  283 */         Iterator itOldAssetBox = oldAssetBox.getAssets().iterator();
/*      */ 
/*  285 */         while (itOldAssetBox.hasNext())
/*      */         {
/*  287 */           AssetInList oldAsset = (AssetInList)itOldAssetBox.next();
/*      */ 
/*  289 */           Iterator itNewAssetBox = assetBox.getAssets().iterator();
/*  290 */           while (itNewAssetBox.hasNext())
/*      */           {
/*  292 */             AssetInList newAsset = (AssetInList)itNewAssetBox.next();
/*      */ 
/*  294 */             if ((oldAsset.getIsSelected()) && (oldAsset.getId() == newAsset.getId()))
/*      */             {
/*  296 */               newAsset.setIsSelected(true);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  302 */       a_userProfile.setAssetBox(assetBox);
/*      */ 
/*  304 */       if (AssetBankSettings.ecommerce())
/*      */       {
/*  307 */         refreshAssetBoxTax(a_dbTransaction, a_userProfile);
/*      */       }
/*      */     }
/*  310 */     else if (lCurrentAssetBoxId > 0L)
/*      */     {
/*  312 */       AssetBox assetBox = loadAssetBoxFromDB(a_dbTransaction, a_userProfile, lCurrentAssetBoxId, a_bFailIfNotFound);
/*      */ 
/*  315 */       if (assetBox.getAssets().size() == 0)
/*      */       {
/*  317 */         throw new NoPermissionException("Non logged in users do not have the permission to see any assets in AssetBox id=" + assetBox.getId());
/*      */       }
/*      */ 
/*  320 */       a_userProfile.setAssetBox(assetBox);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean getUserHasAssetBox(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/*  341 */     String ksMethodName = "getUserHasAssetBox";
/*  342 */     Connection con = null;
/*  343 */     boolean bUserHasAssetBox = false;
/*      */     try
/*      */     {
/*  347 */       con = a_dbTransaction.getConnection();
/*  348 */       String sSql = "SELECT 1 FROM AssetBox WHERE UserId=?";
/*  349 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  350 */       psql.setLong(1, a_lUserId);
/*      */ 
/*  352 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  354 */       while (rs.next())
/*      */       {
/*  356 */         bUserHasAssetBox = true;
/*      */       }
/*      */ 
/*  359 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  363 */       this.m_logger.error("AssetBoxManager.getUserHasAssetBox : SQL Exception : " + e);
/*  364 */       throw new Bn2Exception("AssetBoxManager.getUserHasAssetBox : SQL Exception : " + e, e);
/*      */     }
/*      */ 
/*  367 */     return bUserHasAssetBox;
/*      */   }
/*      */ 
/*      */   public long createAssetBox(DBTransaction a_dbTransaction, long a_lUserId, String a_sName)
/*      */     throws Bn2Exception
/*      */   {
/*  386 */     Connection con = null;
/*  387 */     DBTransaction transaction = a_dbTransaction;
/*  388 */     String sSQL = null;
/*  389 */     PreparedStatement psql = null;
/*  390 */     long lNewId = 0L;
/*      */ 
/*  392 */     if (transaction == null)
/*      */     {
/*  394 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  399 */       con = transaction.getConnection();
/*      */ 
/*  402 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/*  404 */       sSQL = "SELECT MAX(SequenceNumber) AS seq FROM AssetBox WHERE UserId=?";
/*  405 */       psql = con.prepareStatement(sSQL);
/*  406 */       psql.setLong(1, a_lUserId);
/*      */ 
/*  408 */       ResultSet rs = psql.executeQuery();
/*  409 */       int iSeq = 0;
/*      */ 
/*  411 */       if (rs.next()) {
/*  412 */         iSeq = rs.getInt("seq");
/*      */       }
/*      */ 
/*  415 */       sSQL = "INSERT INTO AssetBox (";
/*      */ 
/*  417 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  419 */         lNewId = sqlGenerator.getUniqueId(con, "AssetBoxSequence");
/*  420 */         sSQL = sSQL + "Id, ";
/*      */       }
/*      */ 
/*  423 */       sSQL = sSQL + "UserId, Name, SequenceNumber) VALUES (";
/*      */ 
/*  425 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  427 */         sSQL = sSQL + "?, ";
/*      */       }
/*      */ 
/*  430 */       sSQL = sSQL + "?,?,?)";
/*  431 */       psql.close();
/*      */ 
/*  434 */       psql = con.prepareStatement(sSQL);
/*  435 */       int iCol = 1;
/*      */ 
/*  437 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  439 */         psql.setLong(iCol++, lNewId);
/*      */       }
/*      */ 
/*  442 */       psql.setLong(iCol++, a_lUserId);
/*  443 */       psql.setString(iCol++, a_sName);
/*  444 */       psql.setInt(iCol++, iSeq + 1);
/*      */ 
/*  446 */       psql.executeUpdate();
/*  447 */       psql.close();
/*      */ 
/*  449 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  451 */         lNewId = sqlGenerator.getUniqueId(con, "AssetBox");
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  456 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  460 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  464 */           this.m_logger.error("SQL Exception whilst trying to roll back transaction : " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/*  468 */       this.m_logger.error("SQL Exception whilst creating an asset box in database : " + e);
/*  469 */       throw new Bn2Exception("SQL Exception whilst creating an asset box in the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  474 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  478 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  482 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  487 */     return lNewId;
/*      */   }
/*      */ 
/*      */   public void deleteAssetBox(DBTransaction a_dbTransaction, long a_lAssetBoxId)
/*      */     throws Bn2Exception
/*      */   {
/*  501 */     String ksMethodName = "deleteAssetBox";
/*  502 */     Connection con = null;
/*  503 */     String sSql = null;
/*  504 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  508 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  510 */       sSql = "DELETE FROM AssetBoxAsset WHERE AssetBoxId=?";
/*  511 */       psql = con.prepareStatement(sSql);
/*  512 */       psql.setLong(1, a_lAssetBoxId);
/*  513 */       psql.executeUpdate();
/*      */ 
/*  515 */       sSql = "DELETE FROM AssetBoxPriceBand WHERE AssetBoxId=?";
/*  516 */       psql = con.prepareStatement(sSql);
/*  517 */       psql.setLong(1, a_lAssetBoxId);
/*  518 */       psql.executeUpdate();
/*      */ 
/*  520 */       sSql = "DELETE FROM AssetBoxShare WHERE AssetBoxId=?";
/*  521 */       psql = con.prepareStatement(sSql);
/*  522 */       psql.setLong(1, a_lAssetBoxId);
/*  523 */       psql.executeUpdate();
/*      */ 
/*  525 */       sSql = "DELETE FROM AssetBox WHERE Id=?";
/*  526 */       psql = con.prepareStatement(sSql);
/*  527 */       psql.setLong(1, a_lAssetBoxId);
/*  528 */       psql.executeUpdate();
/*      */ 
/*  530 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  534 */       this.m_logger.error("AssetBoxManager.deleteAssetBox : SQL Exception : " + e);
/*  535 */       throw new Bn2Exception("AssetBoxManager.deleteAssetBox : SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void renameAssetBox(DBTransaction a_dbTransaction, long a_lAssetBoxId, long a_lUserId, String a_sName)
/*      */     throws Bn2Exception
/*      */   {
/*  551 */     String ksMethodName = "renameAssetBox";
/*  552 */     Connection con = null;
/*      */     try
/*      */     {
/*  556 */       con = a_dbTransaction.getConnection();
/*  557 */       String sSql = "UPDATE AssetBox SET Name=? WHERE Id=? AND UserId=?";
/*  558 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  559 */       psql.setString(1, a_sName);
/*  560 */       psql.setLong(2, a_lAssetBoxId);
/*  561 */       psql.setLong(3, a_lUserId);
/*  562 */       psql.executeUpdate();
/*  563 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  567 */       this.m_logger.error("AssetBoxManager.renameAssetBox : SQL Exception : " + e);
/*  568 */       throw new Bn2Exception("AssetBoxManager.renameAssetBox : SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void renameSharedAssetBox(DBTransaction a_dbTransaction, long a_lAssetBoxId, long a_lUserId, String a_sName)
/*      */     throws Bn2Exception, AssetBoxNotFoundException
/*      */   {
/*  584 */     String ksMethodName = "renameSharedAssetBox";
/*  585 */     Connection con = null;
/*      */ 
/*  587 */     verifyAssetBox(a_dbTransaction, a_lAssetBoxId, a_lUserId);
/*      */     try
/*      */     {
/*  591 */       con = a_dbTransaction.getConnection();
/*  592 */       String sSql = "UPDATE AssetBoxShare SET Alias=? WHERE AssetBoxId=? AND UserId=?";
/*  593 */       PreparedStatement psql = con.prepareStatement(sSql);
/*  594 */       psql.setString(1, a_sName);
/*  595 */       psql.setLong(2, a_lAssetBoxId);
/*  596 */       psql.setLong(3, a_lUserId);
/*  597 */       psql.executeUpdate();
/*  598 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  602 */       this.m_logger.error("AssetBoxManager.renameSharedAssetBox : SQL Exception : " + e);
/*  603 */       throw new Bn2Exception("AssetBoxManager.renameSharedAssetBox : SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isAssetBoxNameUnique(String name, ABUserProfile userProfile)
/*      */   {
/*  617 */     List assetBoxes = userProfile.getAssetBoxes();
/*  618 */     Iterator itAssetBoxes = assetBoxes.iterator();
/*  619 */     while (itAssetBoxes.hasNext()) {
/*  620 */       AssetBoxSummary summary = (AssetBoxSummary)itAssetBoxes.next();
/*      */ 
/*  624 */       if ((((summary instanceof SharedAssetBox)) && (name.equalsIgnoreCase(((SharedAssetBox)summary).getAlias()))) || ((!(summary instanceof SharedAssetBox)) && (summary.getName().equalsIgnoreCase(name))))
/*      */       {
/*  626 */         return false;
/*      */       }
/*      */     }
/*  629 */     return true;
/*      */   }
/*      */ 
/*      */   public void reorderAssetBoxes(DBTransaction a_dbTransaction, List a_assetBoxSummaries, long a_lUserId)
/*      */     throws Bn2Exception, StaleDataException
/*      */   {
/*  645 */     String ksMethodName = "reorderAssetBoxex";
/*  646 */     Connection con = null;
/*      */ 
/*  649 */     List boxes = loadAssetBoxSummariesFromDB(a_dbTransaction, a_lUserId);
/*  650 */     if ((boxes.size() != a_assetBoxSummaries.size()) || (!a_assetBoxSummaries.containsAll(boxes)))
/*      */     {
/*  652 */       throw new StaleDataException("AssetBoxManager.reorderAssetBoxes() : Input parameter a_assetBoxSummaries is out of date.");
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  657 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  659 */       String sSql = null;
/*  660 */       PreparedStatement psql = null;
/*  661 */       Iterator itAsstBoxes = a_assetBoxSummaries.iterator();
/*  662 */       int iSeqNo = 1;
/*  663 */       while (itAsstBoxes.hasNext())
/*      */       {
/*  665 */         AssetBoxSummary assetBox = (AssetBoxSummary)itAsstBoxes.next();
/*      */ 
/*  667 */         if (assetBox.isShared())
/*      */         {
/*  669 */           sSql = "UPDATE AssetBoxShare SET SequenceNumber=? WHERE AssetBoxId=? AND UserId=?";
/*  670 */           psql = con.prepareStatement(sSql);
/*  671 */           psql.setInt(1, iSeqNo++);
/*  672 */           psql.setLong(2, assetBox.getId());
/*  673 */           psql.setLong(3, a_lUserId);
/*      */         }
/*      */         else
/*      */         {
/*  677 */           sSql = "UPDATE AssetBox SET SequenceNumber=? WHERE Id=?";
/*  678 */           psql = con.prepareStatement(sSql);
/*  679 */           psql.setInt(1, iSeqNo++);
/*  680 */           psql.setLong(2, assetBox.getId());
/*      */         }
/*      */ 
/*  683 */         psql.executeUpdate();
/*  684 */         psql.close();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  689 */       this.m_logger.error("AssetBoxManager.reorderAssetBoxex : SQL Exception : " + e);
/*  690 */       throw new Bn2Exception("AssetBoxManager.reorderAssetBoxex : SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeSharedAssetBox(DBTransaction a_dbTransaction, long a_lUserId, long a_lAssetBoxId)
/*      */     throws Bn2Exception
/*      */   {
/*  706 */     String ksMethodName = "removeSharedAssetBox";
/*  707 */     Connection con = null;
/*  708 */     String sSql = null;
/*  709 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  713 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  715 */       sSql = "DELETE FROM AssetBoxShare WHERE AssetBoxId=? AND UserId=?";
/*  716 */       psql = con.prepareStatement(sSql);
/*  717 */       psql.setLong(1, a_lAssetBoxId);
/*  718 */       psql.setLong(2, a_lUserId);
/*  719 */       psql.executeUpdate();
/*      */ 
/*  721 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  725 */       this.m_logger.error("AssetBoxManager.removeSharedAssetBox : SQL Exception : " + e);
/*  726 */       throw new Bn2Exception("AssetBoxManager.removeSharedAssetBox : SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public List getSharedAssetBoxUsers(DBTransaction a_dbTransaction, long a_lAssetBoxId, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/*  741 */     String ksMethodName = "getSharedAssetBoxUsers";
/*  742 */     Connection con = null;
/*  743 */     String sSql = null;
/*  744 */     PreparedStatement psql = null;
/*  745 */     ResultSet rs = null;
/*  746 */     List users = new ArrayList();
/*      */     try
/*      */     {
/*  750 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  752 */       sSql = "SELECT abs.UserId userId, abs.CanEdit canEdit FROM AssetBoxShare abs, AssetBox ab WHERE abs.AssetBoxId=? AND abs.AssetBoxId=ab.Id AND ab.UserId=?";
/*  753 */       psql = con.prepareStatement(sSql);
/*  754 */       psql.setLong(1, a_lAssetBoxId);
/*  755 */       psql.setLong(2, a_lUserId);
/*  756 */       rs = psql.executeQuery();
/*      */ 
/*  758 */       while (rs.next())
/*      */       {
/*  760 */         AssetBoxShare share = new AssetBoxShare();
/*  761 */         share.setHasEditPermission(rs.getBoolean("canEdit"));
/*  762 */         ABUser user = (ABUser)this.m_userManager.getUser(a_dbTransaction, rs.getLong("userId"));
/*  763 */         share.setUser(user);
/*  764 */         users.add(share);
/*      */       }
/*      */ 
/*  767 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  771 */       this.m_logger.error("AssetBoxManager.getSharedAssetBoxUsers : SQL Exception : " + e);
/*  772 */       throw new Bn2Exception("AssetBoxManager.getSharedAssetBoxUsers : SQL Exception : " + e, e);
/*      */     }
/*      */ 
/*  775 */     return users;
/*      */   }
/*      */ 
/*      */   public void changeAssetBoxSharePermission(DBTransaction a_dbTransaction, long a_lAssetBoxId, long a_lUserId, boolean a_bCanEdit)
/*      */     throws Bn2Exception
/*      */   {
/*  788 */     String ksMethodName = "changeAssetBoxSharePermission";
/*  789 */     Connection con = null;
/*  790 */     String sSql = null;
/*  791 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  795 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  797 */       sSql = "UPDATE AssetBoxShare SET CanEdit=? WHERE AssetboxId=? AND UserId=?";
/*  798 */       psql = con.prepareStatement(sSql);
/*  799 */       psql.setBoolean(1, a_bCanEdit);
/*  800 */       psql.setLong(2, a_lAssetBoxId);
/*  801 */       psql.setLong(3, a_lUserId);
/*  802 */       psql.executeUpdate();
/*      */ 
/*  804 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  808 */       this.m_logger.error("AssetBoxManager.changeAssetBoxSharePermission : SQL Exception : " + e);
/*  809 */       throw new Bn2Exception("AssetBoxManager.changeAssetBoxSharePermission : SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeAssetBoxShare(DBTransaction a_dbTransaction, long a_lAssetBoxId, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/*  823 */     String ksMethodName = "removeAssetBoxShare";
/*  824 */     Connection con = null;
/*  825 */     String sSql = null;
/*  826 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  830 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  832 */       sSql = "DELETE FROM AssetBoxShare WHERE AssetboxId=? AND UserId=?";
/*  833 */       psql = con.prepareStatement(sSql);
/*  834 */       psql.setLong(1, a_lAssetBoxId);
/*  835 */       psql.setLong(2, a_lUserId);
/*  836 */       psql.executeUpdate();
/*      */ 
/*  838 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  842 */       this.m_logger.error("AssetBoxManager.removeAssetBoxShare : SQL Exception : " + e);
/*  843 */       throw new Bn2Exception("AssetBoxManager.removeAssetBoxShare : SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeAllAssetBoxShares(DBTransaction a_dbTransaction, long a_lAssetBoxId)
/*      */     throws Bn2Exception
/*      */   {
/*  857 */     String ksMethodName = "removeAssetBoxShare";
/*  858 */     Connection con = null;
/*  859 */     String sSql = null;
/*  860 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  864 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  866 */       sSql = "DELETE FROM AssetBoxShare WHERE AssetboxId=?";
/*  867 */       psql = con.prepareStatement(sSql);
/*  868 */       psql.setLong(1, a_lAssetBoxId);
/*  869 */       psql.executeUpdate();
/*  870 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  874 */       this.m_logger.error("AssetBoxManager.removeAssetBoxShare : SQL Exception : " + e);
/*  875 */       throw new Bn2Exception("AssetBoxManager.removeAssetBoxShare : SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addAssetBoxShares(DBTransaction a_dbTransaction, long a_lAssetBoxId, long[] a_alUserIds, boolean a_bCanEdit, boolean a_bSendEmailNotification, String a_sSharedAssetBoxUrl, String a_sEmailTemplateId, EmailContent a_emailContent, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/*  904 */     String ksMethodName = "addAssetBoxShares";
/*  905 */     Connection con = null;
/*  906 */     String sSql = null;
/*  907 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  911 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  913 */       for (int i = 0; i < a_alUserIds.length; i++)
/*      */       {
/*  915 */         sSql = "SELECT CanEdit FROM AssetBoxShare WHERE AssetBoxId=? AND UserId=?";
/*  916 */         psql = con.prepareStatement(sSql);
/*  917 */         psql.setLong(1, a_lAssetBoxId);
/*  918 */         psql.setLong(2, a_alUserIds[i]);
/*  919 */         ResultSet rs = psql.executeQuery();
/*      */ 
/*  921 */         if (rs.next())
/*      */         {
/*  924 */           if ((rs.getBoolean("CanEdit")) || (!a_bCanEdit))
/*      */             continue;
/*  926 */           sSql = "UPDATE AssetBoxShare SET CanEdit=? WHERE AssetBoxId=? AND UserId=?";
/*  927 */           psql = con.prepareStatement(sSql);
/*  928 */           psql.setBoolean(1, a_bCanEdit);
/*  929 */           psql.setLong(2, a_lAssetBoxId);
/*  930 */           psql.setLong(3, a_alUserIds[i]);
/*  931 */           psql.executeUpdate();
/*      */         }
/*      */         else
/*      */         {
/*  936 */           sSql = "INSERT INTO AssetBoxShare (AssetBoxId,UserId,CanEdit,SequenceNumber) VALUES (?,?,?,?)";
/*  937 */           psql = con.prepareStatement(sSql);
/*  938 */           psql.setLong(1, a_lAssetBoxId);
/*  939 */           psql.setLong(2, a_alUserIds[i]);
/*  940 */           psql.setBoolean(3, a_bCanEdit);
/*  941 */           psql.setInt(4, 999);
/*  942 */           psql.executeUpdate();
/*      */ 
/*  944 */           ABUser user = (ABUser)this.m_userManager.getUser(a_dbTransaction, a_alUserIds[i]);
/*      */ 
/*  946 */           if ((!a_bSendEmailNotification) || (user == null))
/*      */             continue;
/*  948 */           Map params = buildAddUserEmailParams(a_dbTransaction, a_sSharedAssetBoxUrl, a_sEmailTemplateId, a_emailContent, user.getUsername(), user.getFullName(), user.getEmailAddress(), user.getLanguage());
/*      */ 
/*  958 */           long lTemplateTypeId = StringUtils.isEmpty(a_sEmailTemplateId) ? 1L : 2L;
/*      */           try
/*      */           {
/*  962 */             this.m_emailManager.sendTemplatedEmail(params, lTemplateTypeId, user.getLanguage());
/*      */           }
/*      */           catch (Bn2Exception e)
/*      */           {
/*  966 */             this.m_logger.debug("AssetBoxManager.addAssetBoxShares() : Sending email to sharing user failied : " + e.getLocalizedMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  971 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  975 */       this.m_logger.error("AssetBoxManager.addAssetBoxShares : SQL Exception : " + e);
/*  976 */       throw new Bn2Exception("AssetBoxManager.addAssetBoxShares : SQL Exception : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private Map<String, String> buildAddUserEmailParams(DBTransaction a_dbTransaction, String a_sSharedAssetBoxUrl, String a_sEmailTemplateId, EmailContent a_emailContent, String a_sUsername, String a_sFullName, String a_sEmailAddress, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/* 1000 */     ListItem assetBoxName = this.m_listManager.getListItem(a_dbTransaction, "a-lightbox", a_language);
/*      */ 
/* 1003 */     String sSubject = a_emailContent.getSubjectForLanguage(a_language);
/* 1004 */     String sMessage = a_emailContent.getBodyForLanguage(a_language);
/*      */ 
/* 1007 */     String sTemplate = a_sEmailTemplateId;
/* 1008 */     if (StringUtils.isEmpty(sTemplate))
/*      */     {
/* 1010 */       sTemplate = "shared_assetbox";
/*      */     }
/*      */ 
/* 1013 */     Map params = new HashMap();
/* 1014 */     params.put("template", sTemplate);
/* 1015 */     params.put("recipients", a_sEmailAddress);
/* 1016 */     params.put("greetname", a_sFullName);
/* 1017 */     params.put("username", a_sUsername);
/* 1018 */     params.put("lightboxname", assetBoxName.getBody());
/* 1019 */     params.put("url", a_sSharedAssetBoxUrl);
/* 1020 */     params.put("message", StringUtils.isNotEmpty(sMessage) ? "<br>" + StringUtil.formatNewlineForHTML(sMessage) + "<br>" : "");
/*      */ 
/* 1022 */     if (StringUtils.isNotEmpty(sSubject))
/*      */     {
/* 1024 */       params.put("replacement_subject", sSubject);
/*      */     }
/* 1026 */     return params;
/*      */   }
/*      */ 
/*      */   public HtmlEmail getShareAssetboxAddUserEmail(String a_sSharedAssetBoxUrl, String a_sEmailTemplateId, long a_lTemplateTypeId, EmailContent a_emailContent, String a_sUsername, String a_sFullName, String a_sEmailAddress, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/* 1052 */     Map params = buildAddUserEmailParams(null, a_sSharedAssetBoxUrl, a_sEmailTemplateId, a_emailContent, a_sUsername, a_sFullName, a_sEmailAddress, a_language);
/*      */     try
/*      */     {
/* 1056 */       return this.m_emailManager.buildHtmlEmail(params, null, a_lTemplateTypeId, a_language);
/*      */     }
/*      */     catch (MessagingException e)
/*      */     {
/* 1060 */       this.m_logger.debug("AssetBoxManager.getShareAssetboxAddUserEmail() : Couldn't construct email message : " + e.getLocalizedMessage());
/* 1061 */     throw new Bn2Exception("AssetBoxManager.getShareAssetboxAddUserEmail() : Couldn't construct email message", e);
            }
/*      */   }
/*      */ 
/*      */   public void addAsset(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, long a_lAssetId)
/*      */     throws Bn2Exception, AssetBoxNotFoundException
/*      */   {
/* 1089 */     AssetInList ail = new AssetInList(a_userProfile.getCurrentLanguage());
/*      */ 
/* 1092 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, a_lAssetId, null, false, true);
/*      */ 
/* 1095 */     ail.setAsset(asset);
/* 1096 */     ail.setIsDownloadable(this.m_assetManager.userCanDownloadAsset(a_userProfile, asset));
/* 1097 */     ail.setIsUpdatable(this.m_assetManager.userCanUpdateAsset(a_userProfile, asset));
/* 1098 */     ail.setIsDownloadableWithApproval(this.m_assetManager.userCanDownloadWithApprovalAsset(a_userProfile, asset));
/* 1099 */     ail.setUserCanDownloadOriginal(this.m_assetManager.userCanDownloadOriginal(a_userProfile, asset));
/* 1100 */     ail.setUserCanDownloadAdvanced(this.m_assetManager.userCanDownloadAdvanced(a_userProfile, asset));
/* 1101 */     ail.setRequiresHighResApproval(this.m_assetManager.userMustRequestApprovalForHighRes(a_userProfile, asset));
/*      */ 
/* 1103 */     long lUserId = 0L;
/*      */ 
/* 1106 */     if (a_userProfile.getIsLoggedIn())
/*      */     {
/* 1108 */       lUserId = a_userProfile.getUser().getId();
/* 1109 */       AssetApproval approval = this.m_assetApprovalManager.getAssetApproval(a_dbTransaction, a_lAssetId, lUserId);
/*      */ 
/* 1111 */       ail.setUserNotes(approval.getUserNotes());
/* 1112 */       ail.setAdminNotes(approval.getAdminNotes());
/* 1113 */       ail.setUsageTypeId(approval.getUsageTypeId());
/* 1114 */       ail.setApprovalStatus(approval.getApprovalStatus());
/*      */     }
/*      */ 
/* 1117 */     Date dtTimeAdded = new Date();
/* 1118 */     ail.setTimeAddedToAssetBox(dtTimeAdded);
/*      */ 
/* 1121 */     AssetBox assetBox = a_userProfile.getAssetBox();
/*      */ 
/* 1123 */     if (assetBox.getId() > 0L)
/*      */     {
/* 1126 */       addAssetToDB(a_dbTransaction, assetBox.getId(), a_lAssetId, lUserId, dtTimeAdded);
/*      */     }
/*      */ 
/* 1130 */     assetBox.addAsset(ail);
/*      */   }
/*      */ 
/*      */   public void removeAsset(DBTransaction a_dbTransaction, AssetBox a_assetBox, long a_lAssetId, long a_lUserId)
/*      */     throws Bn2Exception, AssetBoxNotFoundException
/*      */   {
/* 1154 */     a_assetBox.removeAsset(a_lAssetId);
/*      */ 
/* 1156 */     if (a_assetBox.getId() > 0L)
/*      */     {
/* 1159 */       removeAssetFromDB(a_dbTransaction, a_assetBox.getId(), a_lAssetId, a_lUserId);
/*      */     }
/*      */ 
/* 1163 */     if ((a_assetBox instanceof AssetBasket))
/*      */     {
/* 1165 */       removePricesForAsset(a_dbTransaction, (AssetBasket)a_assetBox, a_lAssetId);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeAllAssets(DBTransaction a_dbTransaction, AssetBox a_assetBox, long a_lUserId)
/*      */     throws Bn2Exception, AssetBoxNotFoundException
/*      */   {
/* 1187 */     a_assetBox.removeAllAssets();
/*      */ 
/* 1189 */     if (a_assetBox.getId() > 0L)
/*      */     {
/* 1192 */       removeAllAssetsFromDB(a_dbTransaction, a_assetBox.getId(), a_lUserId);
/*      */     }
/*      */ 
/* 1195 */     if ((a_assetBox instanceof AssetBasket))
/*      */     {
/* 1198 */       removeAllAssetPrices(a_dbTransaction, (AssetBasket)a_assetBox);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void refreshAssetBoxTax(DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/* 1214 */     ABUser user = (ABUser)a_userProfile.getUser();
/*      */ 
/* 1216 */     refreshAssetBoxTaxForUser(a_dbTransaction, a_userProfile, user);
/*      */   }
/*      */ 
/*      */   public void refreshAssetBoxTaxForUser(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, ABUser a_user)
/*      */     throws Bn2Exception
/*      */   {
/* 1232 */     long lCountryId = 0L;
/* 1233 */     if (a_user != null)
/*      */     {
/* 1235 */       if ((a_user.getHomeAddress() != null) && (a_user.getHomeAddress().getCountry() != null))
/*      */       {
/* 1237 */         lCountryId = a_user.getHomeAddress().getCountry().getId();
/*      */       }
/*      */     }
/*      */ 
/* 1241 */     if (lCountryId > 0L)
/*      */     {
/* 1243 */       TaxValue tv = this.m_taxManager.getTaxValueForCountry(a_dbTransaction, lCountryId, 1L);
/* 1244 */       if (tv != null)
/*      */       {

/* 1246 */         tv.getTaxRegion().setLanguage(a_userProfile.getCurrentLanguage());
/* 1247 */         tv.getTaxType().setLanguage(a_userProfile.getCurrentLanguage());
/* 1248 */         a_userProfile.getBasket().setTaxValue(tv);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1253 */     if (a_user != null)
/*      */     {
/* 1255 */       a_userProfile.getBasket().setTaxNumber(a_user.getTaxNumber());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void refreshAssetBoxShippingRegion(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, Address a_address)
/*      */     throws Bn2Exception
/*      */   {
/* 1277 */     long lCountryId = 0L;
/* 1278 */     if (a_address != null)
/*      */     {
/* 1280 */       if (a_address.getCountry() != null)
/*      */       {
/* 1282 */         lCountryId = a_address.getCountry().getId();
/*      */       }
/*      */     }
/*      */ 
/* 1286 */     StringDataBean shippingRegion = this.m_taxManager.getTaxRegionForCountry(a_dbTransaction, lCountryId);
/* 1287 */     a_userProfile.getBasket().setShippingRegion(shippingRegion);
/*      */   }
/*      */ 
/*      */   public void setPricesForAsset(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, long a_lAssetId, Vector a_vecAssetPrices)
/*      */     throws Bn2Exception
/*      */   {
/* 1315 */     Iterator it = a_vecAssetPrices.iterator();
/* 1316 */     while (it.hasNext())
/*      */     {
/* 1318 */       AssetPrice price = (AssetPrice)it.next();
/*      */ 
/* 1320 */       long lPriceBandId = price.getPriceBand().getId();
/* 1321 */       PriceBand pb = this.m_pbManager.getPriceBand(a_dbTransaction, lPriceBandId);
/* 1322 */       price.setPriceBand(pb);
/*      */     }
/*      */ 
/* 1326 */     AssetBasket assetBasket = a_userProfile.getBasket();
/* 1327 */     assetBasket.setPricesForAsset(a_lAssetId, a_vecAssetPrices);
/*      */ 
/* 1329 */     if (assetBasket.getId() > 0L)
/*      */     {
/* 1332 */       addAssetPricesToDB(a_dbTransaction, assetBasket.getId(), a_lAssetId, a_vecAssetPrices);
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1338 */       addAsset(a_dbTransaction, a_userProfile, a_lAssetId);
/*      */     }
/*      */     catch (AssetBoxNotFoundException e)
/*      */     {
/* 1342 */       throw new Bn2Exception("AssetBoxManager.setPricesForAsset() : Asset box not found - this should not happen - shared asset boxes should not be enabled", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void removePricesForAsset(DBTransaction a_dbTransaction, AssetBasket a_assetBasket, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1364 */     a_assetBasket.removeAssetPrices(a_lAssetId);
/*      */ 
/* 1366 */     if (a_assetBasket.getId() > 0L)
/*      */     {
/* 1369 */       removeAssetPricesFromDB(a_dbTransaction, a_assetBasket.getId(), a_lAssetId);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void removeAllAssetPrices(DBTransaction a_dbTransaction, AssetBasket a_assetBasket)
/*      */     throws Bn2Exception
/*      */   {
/* 1392 */     a_assetBasket.removeAllAssetPrices();
/*      */ 
/* 1394 */     if (a_assetBasket.getId() > 0L)
/*      */     {
/* 1397 */       removeAllAssetPricesFromDB(a_dbTransaction, a_assetBasket.getId());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void synchroniseAssetBox(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, long a_lUserId, long a_lAssetBoxId)
/*      */     throws Bn2Exception
/*      */   {
/* 1420 */     AssetBox assetBox = a_userProfile.getAssetBox();
/* 1421 */     AssetBox persistedAssetBox = null;
/*      */     try
/*      */     {
/*      */       //long lAssetBoxId;
/*      */       long lAssetBoxId;
/* 1431 */       if (a_lAssetBoxId < 1L)
/*      */       {
/* 1433 */         persistedAssetBox = loadAssetBoxFromDB(a_dbTransaction, a_userProfile, false);
/* 1434 */         lAssetBoxId = persistedAssetBox.getId();
/*      */       }
/*      */       else
/*      */       {
/* 1439 */         lAssetBoxId = a_lAssetBoxId;
/* 1440 */         persistedAssetBox = new AssetBox();
/*      */       }
/*      */ 
/* 1445 */       Iterator it = assetBox.getAssets().iterator();
/*      */ 
/* 1448 */       HashMap hmAssetPrices = null;
/* 1449 */       if (AssetBankSettings.getUsePriceBands())
/*      */       {
/* 1451 */         hmAssetPrices = ((AssetBasket)assetBox).getAssetPrices();
/*      */       }
/*      */ 
/* 1454 */       while (it.hasNext())
/*      */       {
/* 1456 */         AssetInList ail = (AssetInList)it.next();
/* 1457 */         long lAssetId = ail.getAsset().getId();
/*      */ 
/* 1460 */         if (!persistedAssetBox.containsAsset(lAssetId))
/*      */         {
/* 1462 */           addAssetToDB(a_dbTransaction, lAssetBoxId, ail.getAsset().getId(), a_lUserId, new Date());
/*      */ 
/* 1465 */           if (AssetBankSettings.getUsePriceBands())
/*      */           {
/* 1468 */             Long olAssetId = new Long(lAssetId);
/*      */ 
/* 1471 */             Vector vecAssetPrices = (Vector)hmAssetPrices.get(olAssetId);
/* 1472 */             if (vecAssetPrices != null)
/*      */             {
/* 1474 */               addAssetPricesToDB(a_dbTransaction, lAssetBoxId, lAssetId, vecAssetPrices);
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1482 */       refreshAssetBoxesInProfile(a_dbTransaction, a_userProfile);
/*      */     }
/*      */     catch (AssetBoxNotFoundException e)
/*      */     {
/* 1486 */       throw new Bn2Exception("AssetBoxManager.synchroniseAssetBox() : Cannot synchronise - asset box not found!", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeAssetFromDBForUser(DBTransaction a_dbTransaction, long a_UserId, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1509 */     long lAssetBoxId = getFirstAssetBoxIdForUser(a_dbTransaction, a_UserId);
/*      */     try
/*      */     {
/* 1513 */       removeAssetFromDB(a_dbTransaction, lAssetBoxId, a_lAssetId, a_UserId);
/*      */     }
/*      */     catch (AssetBoxNotFoundException e)
/*      */     {
/* 1517 */       throw new Bn2Exception("AssetBoxManager.removeAssetFromDBForUser() : Couldn't get default asset box id from the database", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addAssetToDBForUser(DBTransaction a_dbTransaction, long a_lUserId, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1540 */     long lAssetBoxId = getFirstAssetBoxIdForUser(a_dbTransaction, a_lUserId);
/*      */     try
/*      */     {
/* 1544 */       addAssetToDB(a_dbTransaction, lAssetBoxId, a_lAssetId, a_lUserId, new Date());
/*      */     }
/*      */     catch (AssetBoxNotFoundException e)
/*      */     {
/* 1548 */       throw new Bn2Exception("AssetBoxManager.addAssetToDBForUser() : Could not retrieve the user's default asset box from the database", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeAssetPricesFromDBForUser(DBTransaction a_dbTransaction, long a_UserId, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1569 */     long lAssetBoxId = getFirstAssetBoxIdForUser(a_dbTransaction, a_UserId);
/*      */ 
/* 1571 */     removeAssetPricesFromDB(a_dbTransaction, lAssetBoxId, a_lAssetId);
/*      */   }
/*      */ 
/*      */   private long getFirstAssetBoxIdForUser(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1594 */     String ksMethodName = "getAssetBoxIdForUser";
/* 1595 */     Connection con = null;
/* 1596 */     String sSQL = null;
/* 1597 */     PreparedStatement psql = null;
/* 1598 */     ResultSet rs = null;
/*      */ 
/* 1600 */     long lAssetBoxId = 0L;
/*      */     try
/*      */     {
/* 1603 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1605 */       sSQL = "SELECT Id FROM AssetBox WHERE UserId=? ORDER BY SequenceNumber";
/*      */ 
/* 1608 */       psql = con.prepareStatement(sSQL);
/* 1609 */       psql.setLong(1, a_lUserId);
/* 1610 */       rs = psql.executeQuery();
/*      */ 
/* 1612 */       if (rs.next())
/*      */       {
/* 1614 */         lAssetBoxId = rs.getLong("Id");
/*      */       }
/* 1616 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1621 */       throw new Bn2Exception("AssetBoxManager.getAssetBoxIdForUser: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1624 */     return lAssetBoxId;
/*      */   }
/*      */ 
/*      */   private AssetBox loadAssetBoxFromDB(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, boolean a_bFailIfNotFound)
/*      */     throws Bn2Exception, AssetBoxNotFoundException
/*      */   {
/* 1643 */     return loadAssetBoxFromDB(a_dbTransaction, a_userProfile, 0L, a_bFailIfNotFound);
/*      */   }
/*      */ 
/*      */   private AssetBox loadAssetBoxFromDB(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, long a_lAssetBoxId, boolean a_bFailIfNotFound)
/*      */     throws Bn2Exception, AssetBoxNotFoundException
/*      */   {
/* 1680 */     AssetBox assetBox = null;
/* 1681 */     Connection con = null;
/* 1682 */     ResultSet rs = null;
/* 1683 */     DBTransaction transaction = a_dbTransaction;
/* 1684 */     String sSQL = null;
/* 1685 */     PreparedStatement psql = null;
/* 1686 */     String sCurrentAssetBoxName = null;
/* 1687 */     int iNumShares = 0;
/* 1688 */     long lUserId = a_userProfile.getUser() != null ? a_userProfile.getUser().getId() : 0L;
/* 1689 */     boolean bEditable = lUserId > 0L;
/*      */ 
/* 1691 */     if (a_bFailIfNotFound)
/*      */     {
/* 1693 */       verifyAssetBox(a_dbTransaction, a_lAssetBoxId, lUserId);
/*      */     }
/*      */ 
/* 1697 */     long lCurrentAssetBoxId = a_userProfile.getAssetBox() != null ? a_userProfile.getAssetBox().getId() : a_lAssetBoxId > 0L ? a_lAssetBoxId : 0L;
/*      */ 
/* 1699 */     if (transaction == null)
/*      */     {
/* 1701 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1706 */       con = transaction.getConnection();
/*      */ 
/* 1708 */       if (AssetBankSettings.isMultipleLightboxesEnabled())
/*      */       {
/* 1710 */         Iterator itAssetBoxes = a_userProfile.getAssetBoxes().iterator();
/* 1711 */         while (itAssetBoxes.hasNext())
/*      */         {
/* 1713 */           AssetBoxSummary summary = (AssetBoxSummary)itAssetBoxes.next();
/*      */ 
/* 1716 */           if (lCurrentAssetBoxId == 0L)
/*      */           {
/* 1718 */             lCurrentAssetBoxId = summary.getId();
/*      */           }
/*      */ 
/* 1721 */           if (lCurrentAssetBoxId == summary.getId())
/*      */           {
/* 1723 */             sCurrentAssetBoxName = summary.getName();
/* 1724 */             bEditable = summary.isEditable();
/* 1725 */             iNumShares = summary.getNumShares();
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1731 */       if (lCurrentAssetBoxId == 0L)
/*      */       {
/* 1733 */         lCurrentAssetBoxId = getFirstAssetBoxIdForUser(a_dbTransaction, lUserId);
/*      */       }
/*      */ 
/* 1736 */       sSQL = "SELECT ab.Id abId, ab.UserId userId, ab.Name abName, ass.Id assId, ass.AssetTypeId assAssetTypeId, aa.AdminNotes, aa.UserNotes, appstatus.Id appstatusId, appstatus.Name appstatusName, aba.AssetBoxId abaAssetBoxId, aba.AddedByUserId addedByUserId, aba.SequenceNumber sequenceNumber, aba.TimeAdded FROM AssetBox ab LEFT JOIN AssetBoxAsset aba ON aba.AssetboxId = ab.Id LEFT JOIN Asset ass ON aba.AssetId = ass.Id LEFT JOIN AssetApproval aa ON (aa.AssetId = ass.Id AND aa.UserId = ?)LEFT JOIN ApprovalStatus appstatus ON appstatus.Id = aa.ApprovalStatusId WHERE ab.Id = ? ORDER BY aba.SequenceNumber, aba.TimeAdded ";
/*      */ 
/* 1759 */       psql = con.prepareStatement(sSQL);
/* 1760 */       psql.setLong(1, lUserId);
/* 1761 */       psql.setLong(2, lCurrentAssetBoxId);
/*      */ 
/* 1763 */       rs = psql.executeQuery();
/*      */ 
/* 1765 */       while (rs.next())
/*      */       {
/* 1768 */         if ((rs.getLong("abaAssetBoxId") > 0L) && (rs.getLong("SequenceNumber") <= 0L))
/*      */         {
/* 1771 */           reorderAssets(transaction);
/*      */         }
/*      */ 
/* 1774 */         if (assetBox == null)
/*      */         {
/* 1776 */           if (AssetBankSettings.ecommerce())
/*      */           {
/* 1778 */             assetBox = new AssetBasket();
/*      */           }
/*      */           else
/*      */           {
/* 1782 */             assetBox = new AssetBox();
/*      */           }
/* 1784 */           assetBox.setId(rs.getLong("abId"));
/*      */ 
/* 1787 */           if (AssetBankSettings.isMultipleLightboxesEnabled())
/*      */           {
/* 1789 */             assetBox.setName(sCurrentAssetBoxName);
/*      */           }
/*      */           else
/*      */           {
/* 1793 */             assetBox.setName(rs.getString("abName"));
/*      */           }
/*      */ 
/* 1796 */           assetBox.setEditable(bEditable);
/* 1797 */           assetBox.setNumShares(iNumShares);
/* 1798 */           assetBox.setShared(rs.getLong("userId") != lUserId);
/*      */         }
/*      */ 
/* 1801 */         long lAssetId = rs.getLong("assId");
/*      */ 
/* 1803 */         if (lAssetId > 0L)
/*      */         {
/* 1805 */           AssetInList ail = new AssetInList(a_userProfile.getCurrentLanguage());
/*      */ 
/* 1808 */           Asset asset = this.m_assetManager.getAsset(transaction, rs.getLong("assId"), null, false, a_userProfile.getCurrentLanguage(), true);
/*      */ 
/* 1810 */           if (this.m_assetManager.userCanViewAsset(a_userProfile, asset))
/*      */           {
/* 1812 */             ail.setAsset(asset);
/* 1813 */             ail.setAddedToAssetBoxByUserId(rs.getLong("addedByUserId"));
/* 1814 */             ail.setSequenceNumber(rs.getLong("sequenceNumber"));
/*      */ 
/* 1817 */             long lApprovalStatusId = rs.getLong("appstatusId");
/* 1818 */             if (lApprovalStatusId > 0L)
/*      */             {
/* 1820 */               ail.getApprovalStatus().setId(rs.getLong("appstatusId"));
/* 1821 */               ail.getApprovalStatus().setName(rs.getString("appstatusName"));
/* 1822 */               ail.setAdminNotes(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "AdminNotes"));
/* 1823 */               ail.setUserNotes(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "UserNotes"));
/*      */             }
/*      */ 
/* 1829 */             ail.setIsDownloadable(this.m_assetManager.userCanDownloadAsset(a_userProfile, asset));
/* 1830 */             ail.setIsDownloadableWithApproval(this.m_assetManager.userCanDownloadWithApprovalAsset(a_userProfile, asset));
/* 1831 */             ail.setUserCanDownloadOriginal(this.m_assetManager.userCanDownloadOriginal(a_userProfile, asset));
/* 1832 */             ail.setUserCanDownloadAdvanced(this.m_assetManager.userCanDownloadAdvanced(a_userProfile, asset));
/* 1833 */             ail.setRequiresHighResApproval(this.m_assetManager.userMustRequestApprovalForHighRes(a_userProfile, asset));
/* 1834 */             ail.setTimeAddedToAssetBox(rs.getDate("TimeAdded"));
/*      */ 
/* 1836 */             assetBox.addAsset(ail);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 1841 */       psql.close();
/*      */ 
/* 1843 */       if (assetBox != null)
/*      */       {
/* 1845 */         if ((AssetBankSettings.ecommerce()) && (AssetBankSettings.getUsePriceBands()))
/*      */         {
/* 1847 */           loadAssetBoxPriceBandsFromDB(a_dbTransaction, (AssetBasket)assetBox, a_userProfile.getCurrentLanguage());
/*      */         }
/*      */       }
/* 1850 */       else if (a_bFailIfNotFound)
/*      */       {
/* 1853 */         throw new AssetBoxNotFoundException("AssetBoxManager.loadAssetBoxFromDB() : Requested AssetBox not found: id=" + lCurrentAssetBoxId);
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1858 */       this.m_logger.error("SQL Exception whilst getting an asset box from the database : " + e);
/* 1859 */       throw new Bn2Exception("SQL Exception whilst getting an asset box from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1864 */       if (a_dbTransaction == null)
/*      */       {
/*      */         try
/*      */         {
/* 1868 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1872 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1877 */     return assetBox;
/*      */   }
/*      */ 
/*      */   private List loadAssetBoxSummariesFromDB(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1891 */     Connection con = null;
/* 1892 */     ResultSet rs = null;
/* 1893 */     DBTransaction transaction = a_dbTransaction;
/* 1894 */     String sSQL = null;
/* 1895 */     PreparedStatement psql = null;
/* 1896 */     List assetBoxes = new ArrayList();
/*      */ 
/* 1898 */     if (transaction == null)
/*      */     {
/* 1900 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
                AssetBoxSummary summary;
/*      */ 
/*      */     try
/*      */     {
/* 1905 */       con = transaction.getConnection();
/*      */ 
/* 1907 */       if (AssetBankSettings.isMultipleLightboxesEnabled())
/*      */       {
/* 1910 */         sSQL = "SELECT * FROM (SELECT ab.Id Id, ab.Name Name,NULL Alias,? UserId, NULL Username,NULL EmailAddress,'1' Editable, ab.SequenceNumber Seq, COUNT(shares.AssetBoxId) NumShares, COUNT(aba.AssetId) NumAssets FROM AssetBox ab LEFT JOIN AssetBoxShare shares ON shares.AssetBoxId=ab.Id LEFT JOIN AssetBoxAsset aba ON aba.AssetBoxId=ab.Id WHERE ab.UserId=? GROUP BY ab.Id, ab.Name, ab.SequenceNumber  UNION SELECT sharedbox.Id Id, sharedbox.Name Name, assetboxshare.Alias Alias, sharedboxowner.Id UserId, sharedboxowner.Username Username, sharedboxowner.EmailAddress EmailAddress, assetboxshare.CanEdit Editable, assetboxshare.SequenceNumber Seq, 0 NumShares, abcount.NumAssets FROM AssetBoxShare assetboxshare, AssetBox sharedbox, AssetBankUser sharedboxowner, (SELECT AssetBoxId, COUNT(AssetId) AS NumAssets FROM AssetBoxAsset GROUP BY AssetBoxId) abcount WHERE assetboxshare.UserId=? AND assetboxshare.AssetBoxId=sharedbox.Id AND sharedboxowner.Id=sharedbox.UserId AND sharedbox.Id = abcount.AssetBoxId ) query1 ORDER BY Seq";
/*      */ 
/* 1945 */         psql = con.prepareStatement(sSQL);
/*      */ 
/* 1947 */         psql.setLong(1, a_lUserId);
/* 1948 */         psql.setLong(2, a_lUserId);
/* 1949 */         psql.setLong(3, a_lUserId);
/* 1950 */         rs = psql.executeQuery();
/*      */ 
/* 1952 */         while (rs.next())
/*      */         {
/* 1954 */           summary = null;
/*      */ 
/* 1956 */           if (rs.getLong("UserId") != a_lUserId)
/*      */           {
/* 1959 */             if (!AssetBankSettings.isSharedLightboxesEnabled())
/*      */             {
/*      */               continue;
/*      */             }
/*      */ 
/* 1964 */             SharedAssetBox sharedBox = new SharedAssetBox();
/* 1965 */             sharedBox.setAlias(rs.getString("Alias"));
/* 1966 */             sharedBox.setOwningUsername(rs.getString("Username"));
/* 1967 */             sharedBox.setOwningEmailAddress(rs.getString("EmailAddress"));
/* 1968 */             sharedBox.setOwningUserId(rs.getLong("UserId"));
/* 1969 */             summary = sharedBox;
/*      */           }
/*      */           else
/*      */           {
/* 1973 */             summary = new AssetBoxSummary();
/*      */           }
/*      */ 
/* 1976 */           summary.setId(rs.getLong("Id"));
/* 1977 */           summary.setName(rs.getString("Name"));
/* 1978 */           summary.setEditable(rs.getBoolean("Editable"));
/* 1979 */           summary.setNumShares(rs.getInt("NumShares"));
/* 1980 */           summary.setAssetBoxSize(rs.getLong("NumAssets"));
/* 1981 */           assetBoxes.add(summary);
/*      */         }
/*      */       }
/*      */ 
/* 1985 */       //summary =  assetBoxes;
                  return assetBoxes;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       //AssetBoxSummary summary;
/* 1989 */       this.m_logger.error("SQL Exception whilst getting asset box summaries from the database : " + e);
/* 1990 */       throw new Bn2Exception("SQL Exception whilst getting asset box summaries from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1995 */       if (a_dbTransaction == null)
/*      */       {
/*      */         try
/*      */         {
/* 1999 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 2003 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void loadAssetBoxPriceBandsFromDB(DBTransaction a_dbTransaction, AssetBasket a_assetBasket, Language a_language)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 2024 */     Connection con = null;
/* 2025 */     ResultSet rs = null;
/* 2026 */     String sSQL = null;
/* 2027 */     PreparedStatement psql = null;
/*      */ 
/* 2029 */     con = a_dbTransaction.getConnection();
/*      */ 
/* 2031 */     sSQL = "SELECT abpb.Quantity, abpb.AssetId, abpb.PriceBandId FROM AssetBoxPriceBand abpb WHERE abpb.AssetBoxId = ? ORDER BY AssetId ";
/*      */ 
/* 2038 */     psql = con.prepareStatement(sSQL);
/* 2039 */     psql.setLong(1, a_assetBasket.getId());
/* 2040 */     rs = psql.executeQuery();
/*      */ 
/* 2042 */     long lLastAssetId = 0L;
/* 2043 */     HashMap hmAssetPrices = new HashMap();
/*      */ 
/* 2045 */     while (rs.next())
/*      */     {
/* 2048 */       PriceBand pb = this.m_pbManager.getPriceBand(a_dbTransaction, rs.getLong("PriceBandId"));
/*      */ 
/* 2051 */       pb.setLanguage(a_language);
/*      */ 
/* 2053 */       long lAssetId = rs.getLong("AssetId");
/* 2054 */       Long olAssetId = new Long(lAssetId);
/*      */ 
/* 2057 */       AssetPrice price = new AssetPrice();
/* 2058 */       price.setAssetId(lAssetId);
/* 2059 */       price.setPriceBand(pb);
/* 2060 */       price.setQuantity(rs.getInt("Quantity"));
/*      */ 
/* 2062 */       if (lAssetId != lLastAssetId)
/*      */       {
/* 2065 */         Vector vec = new Vector();
/* 2066 */         hmAssetPrices.put(olAssetId, vec);
/*      */ 
/* 2068 */         lLastAssetId = lAssetId;
/*      */       }
/*      */ 
/* 2072 */       Vector vecPrices = (Vector)hmAssetPrices.get(olAssetId);
/* 2073 */       vecPrices.add(price);
/*      */     }
/*      */ 
/* 2076 */     psql.close();
/*      */ 
/* 2078 */     a_assetBasket.setAssetPrices(hmAssetPrices);
/*      */   }
/*      */ 
/*      */   private void addAssetToDB(DBTransaction a_dbTransaction, long a_lAssetBoxId, long a_lAssetId, long a_lUserId, Date a_dtTimeAdded)
/*      */     throws Bn2Exception, AssetBoxNotFoundException
/*      */   {
/* 2104 */     String ksMethodName = "addAssetToDB";
/* 2105 */     Connection con = null;
/* 2106 */     String sSQL = null;
/* 2107 */     PreparedStatement psql = null;
/*      */ 
/* 2109 */     verifyAssetBox(a_dbTransaction, a_lAssetBoxId, a_lUserId);
/*      */     try
/*      */     {
/* 2113 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2117 */       sSQL = "SELECT MAX(SequenceNumber) AS MaxSequenceNumber FROM AssetBoxAsset WHERE AssetBoxId=? AND AssetId<>?";
/*      */ 
/* 2121 */       psql = con.prepareStatement(sSQL);
/* 2122 */       psql.setLong(1, a_lAssetBoxId);
/* 2123 */       psql.setLong(2, a_lAssetId);
/*      */ 
/* 2126 */       ResultSet rs = psql.executeQuery();
/*      */       long lNewSequenceNumber;
/*      */       //long lNewSequenceNumber;
/* 2127 */       if (rs.next())
/*      */       {
/* 2129 */         lNewSequenceNumber = rs.getLong("MaxSequenceNumber") + 1L;
/*      */       }
/*      */       else
/*      */       {
/* 2134 */         lNewSequenceNumber = 1L;
/*      */       }
/* 2136 */       psql.close();
/*      */ 
/* 2139 */       if (!SQLGenerator.getInstance().getSupportsOnDuplicateKeyUpdate())
/*      */       {
/* 2142 */         sSQL = "DELETE FROM AssetBoxAsset WHERE AssetBoxId=? AND AssetId=?";
/* 2143 */         psql = con.prepareStatement(sSQL);
/* 2144 */         psql.setLong(1, a_lAssetBoxId);
/* 2145 */         psql.setLong(2, a_lAssetId);
/* 2146 */         psql.executeUpdate();
/* 2147 */         psql.close();
/*      */ 
/* 2149 */         sSQL = "INSERT INTO AssetBoxAsset (AssetBoxId,AssetId,TimeAdded,AddedByUserId,SequenceNumber) VALUES (?,?,?,?,?)";
/*      */       }
/*      */       else
/*      */       {
/* 2153 */         sSQL = "INSERT INTO AssetBoxAsset (AssetBoxId,AssetId,TimeAdded,AddedByUserId,SequenceNumber) VALUES (?,?,?,?,?)ON DUPLICATE KEY UPDATE TimeAdded = VALUES(TimeAdded), AddedByUserId = VALUES(AddedByUserId), SequenceNumber = VALUES(SequenceNumber)";
/*      */       }
/*      */ 
/* 2158 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 2160 */       psql.setLong(1, a_lAssetBoxId);
/* 2161 */       psql.setLong(2, a_lAssetId);
/* 2162 */       psql.setTimestamp(3, new Timestamp(a_dtTimeAdded.getTime()));
/* 2163 */       psql.setLong(4, a_lUserId);
/* 2164 */       psql.setLong(5, lNewSequenceNumber);
/*      */ 
/* 2166 */       psql.executeUpdate();
/* 2167 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2172 */       throw new Bn2Exception("AssetBoxManager.addAssetToDB: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void verifyAssetBox(DBTransaction a_dbTransaction, long a_lAssetBoxId, long a_lUserId)
/*      */     throws Bn2Exception, AssetBoxNotFoundException
/*      */   {
/*      */     try
/*      */     {
/* 2189 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/* 2193 */       if (a_lUserId <= 0L)
/*      */       {
/* 2195 */         String sSQL = "SELECT Id FROM AssetBox WHERE Id=?";
/* 2196 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 2197 */         psql.setLong(1, a_lAssetBoxId);
/* 2198 */         ResultSet rs = psql.executeQuery();
/*      */ 
/* 2200 */         if (!rs.next())
/*      */         {
/* 2202 */           throw new AssetBoxNotFoundException("AssetBoxManager.addAssetToDB() : No asset box found with id=" + a_lAssetBoxId);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 2207 */         String sSQL = "SELECT COUNT(ab.Id) assetBoxCount FROM AssetBox ab LEFT JOIN AssetBoxShare abs ON ab.Id=abs.AssetBoxId WHERE ab.Id=? AND (ab.UserId=? OR abs.UserId=?)";
/* 2208 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 2209 */         psql.setLong(1, a_lAssetBoxId);
/* 2210 */         psql.setLong(2, a_lUserId);
/* 2211 */         psql.setLong(3, a_lUserId);
/* 2212 */         ResultSet rs = psql.executeQuery();
/*      */ 
/* 2214 */         if ((!rs.next()) || (rs.getInt("assetBoxCount") <= 0))
/*      */         {
/* 2216 */           throw new AssetBoxNotFoundException("AssetBoxManager.addAssetToDB() : User id=" + a_lUserId + " cannot access assetbox id=" + a_lAssetBoxId);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2222 */       throw new Bn2Exception("AssetBoxManager.addAssetToDB() : SQLException occurred: ", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void removeAssetFromDB(DBTransaction a_dbTransaction, long a_lAssetBoxId, long a_lAssetId, long a_lUserId)
/*      */     throws Bn2Exception, AssetBoxNotFoundException
/*      */   {
/* 2241 */     String ksMethodName = "removeAssetFromDB";
/* 2242 */     Connection con = null;
/* 2243 */     String sSQL = null;
/* 2244 */     PreparedStatement psql = null;
/*      */ 
/* 2247 */     verifyAssetBox(a_dbTransaction, a_lAssetBoxId, a_lUserId);
/*      */     try
/*      */     {
/* 2251 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2253 */       sSQL = "DELETE FROM AssetBoxAsset WHERE AssetBoxId=? AND AssetId=?";
/*      */ 
/* 2256 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 2258 */       psql.setLong(1, a_lAssetBoxId);
/* 2259 */       psql.setLong(2, a_lAssetId);
/*      */ 
/* 2261 */       psql.executeUpdate();
/* 2262 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2267 */       throw new Bn2Exception("AssetBoxManager.removeAssetFromDB: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void removeAllAssetsFromDB(DBTransaction a_dbTransaction, long a_lAssetBoxId, long a_lUserId)
/*      */     throws Bn2Exception, AssetBoxNotFoundException
/*      */   {
/* 2287 */     String ksMethodName = "removeAllAssetsFromDB";
/* 2288 */     Connection con = null;
/* 2289 */     String sSQL = null;
/* 2290 */     PreparedStatement psql = null;
/*      */ 
/* 2293 */     verifyAssetBox(a_dbTransaction, a_lAssetBoxId, a_lUserId);
/*      */     try
/*      */     {
/* 2297 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2299 */       sSQL = "DELETE FROM AssetBoxAsset WHERE AssetBoxId=?";
/*      */ 
/* 2302 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 2304 */       psql.setLong(1, a_lAssetBoxId);
/*      */ 
/* 2306 */       psql.executeUpdate();
/* 2307 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2312 */       throw new Bn2Exception("AssetBoxManager.removeAllAssetsFromDB: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addAssetPricesToDB(DBTransaction a_dbTransaction, long a_lAssetBoxId, long a_lAssetId, Vector a_vecAssetPrices)
/*      */     throws Bn2Exception
/*      */   {
/* 2329 */     String ksMethodName = "addAssetPricesToDB";
/* 2330 */     Connection con = null;
/* 2331 */     String sSQL = null;
/* 2332 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/* 2336 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2339 */       sSQL = "DELETE FROM AssetBoxPriceBand WHERE AssetBoxId=? AND AssetId=? ";
/* 2340 */       psql = con.prepareStatement(sSQL);
/* 2341 */       psql.setLong(1, a_lAssetBoxId);
/* 2342 */       psql.setLong(2, a_lAssetId);
/* 2343 */       psql.executeUpdate();
/* 2344 */       psql.close();
/*      */ 
/* 2346 */       sSQL = "INSERT INTO AssetBoxPriceBand (AssetBoxId, AssetId, PriceBandId, Quantity) VALUES (?,?,?,?)";
/*      */ 
/* 2348 */       Iterator it = a_vecAssetPrices.iterator();
/* 2349 */       while (it.hasNext())
/*      */       {
/* 2351 */         AssetPrice price = (AssetPrice)it.next();
/*      */ 
/* 2353 */         psql = con.prepareStatement(sSQL);
/* 2354 */         psql.setLong(1, a_lAssetBoxId);
/* 2355 */         psql.setLong(2, a_lAssetId);
/* 2356 */         psql.setLong(3, price.getPriceBand().getId());
/* 2357 */         psql.setLong(4, price.getQuantity());
/* 2358 */         psql.executeUpdate();
/* 2359 */         psql.close();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2364 */       throw new Bn2Exception("AssetBoxManager.addAssetPricesToDB: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void removeAssetPricesFromDB(DBTransaction a_dbTransaction, long a_lAssetBoxId, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 2385 */     String ksMethodName = "removeAssetPriceFromDB";
/* 2386 */     Connection con = null;
/* 2387 */     String sSQL = null;
/* 2388 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/* 2392 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2394 */       sSQL = "DELETE FROM AssetBoxPriceBand WHERE AssetBoxId=? AND AssetId=?";
/*      */ 
/* 2396 */       psql = con.prepareStatement(sSQL);
/* 2397 */       psql.setLong(1, a_lAssetBoxId);
/* 2398 */       psql.setLong(2, a_lAssetId);
/*      */ 
/* 2400 */       psql.executeUpdate();
/* 2401 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2406 */       throw new Bn2Exception("AssetBoxManager.removeAssetPriceFromDB: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void removeAllAssetPricesFromDB(DBTransaction a_dbTransaction, long a_lAssetBoxId)
/*      */     throws Bn2Exception
/*      */   {
/* 2425 */     String ksMethodName = "removeAllAssetPricesFromDB";
/* 2426 */     Connection con = null;
/* 2427 */     String sSQL = null;
/* 2428 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/* 2432 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2434 */       sSQL = "DELETE FROM AssetBoxPriceBand WHERE AssetBoxId=?";
/*      */ 
/* 2436 */       psql = con.prepareStatement(sSQL);
/* 2437 */       psql.setLong(1, a_lAssetBoxId);
/*      */ 
/* 2439 */       psql.executeUpdate();
/* 2440 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2445 */       throw new Bn2Exception("AssetBoxManager.removeAllAssetPricesFromDB: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateAssetSequenceNumbers(DBTransaction a_dbTransaction, long a_lAssetBoxId, Collection<AssetInList> a_assets)
/*      */     throws Bn2Exception
/*      */   {
/* 2456 */     Connection con = null;
/* 2457 */     PreparedStatement psql = null;
/* 2458 */     String sSQL = null;
/*      */     try
/*      */     {
/* 2462 */       con = a_dbTransaction.getConnection();
/* 2463 */       sSQL = "UPDATE AssetBoxAsset SET SequenceNumber=? WHERE AssetBoxId=? AND AssetId=?";
/* 2464 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 2466 */       for (AssetInList asset : a_assets)
/*      */       {
/* 2468 */         psql.setLong(1, asset.getSequenceNumber());
/* 2469 */         psql.setLong(2, a_lAssetBoxId);
/* 2470 */         psql.setLong(3, asset.getId());
/* 2471 */         psql.executeUpdate();
/*      */       }
/* 2473 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2477 */       throw new Bn2Exception("SQL Exception whilst reordering lightbox: ", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void moveAssetInAssetBox(DBTransaction a_dbTransaction, long a_lAssetId, long a_lAssetBoxId, boolean a_bUp, long a_lAssetIdInfrontOf)
/*      */     throws Bn2Exception
/*      */   {
/* 2496 */     if (a_lAssetIdInfrontOf > 0L)
/*      */     {
/* 2498 */       Connection con = null;
/* 2499 */       PreparedStatement psql1 = null;
/* 2500 */       String sSQL = null;
/* 2501 */       ResultSet rs = null;
/*      */       try
/*      */       {
/* 2505 */         con = a_dbTransaction.getConnection();
/*      */ 
/* 2512 */         sSQL = "SELECT SequenceNumber FROM AssetBoxAsset WHERE AssetBoxId=? AND AssetId=?";
/* 2513 */         psql1 = con.prepareStatement(sSQL);
/* 2514 */         psql1.setLong(1, a_lAssetBoxId);
/* 2515 */         psql1.setLong(2, a_lAssetIdInfrontOf);
/* 2516 */         rs = psql1.executeQuery();
/*      */ 
/* 2518 */         if (rs.next())
/*      */         {
/* 2520 */           int iAssetInFrontOfSequenceNumberIncrement = rs.getInt("SequenceNumber");
/* 2521 */           int iAssetInFrontOfSequenceNumber = rs.getInt("SequenceNumber");
/*      */ 
/* 2525 */           sSQL = "SELECT AssetId, SequenceNumber FROM AssetBoxAsset WHERE SequenceNumber>=? ORDER BY SequenceNumber ASC";
/*      */ 
/* 2531 */           PreparedStatement psql = con.prepareStatement(sSQL);
/* 2532 */           psql.setInt(1, iAssetInFrontOfSequenceNumberIncrement);
/* 2533 */           rs = psql.executeQuery();
/*      */ 
/* 2536 */           while (rs.next())
/*      */           {
/* 2538 */             sSQL = "UPDATE AssetBoxAsset SET SequenceNumber=? WHERE AssetBoxId=? AND AssetId=?";
/* 2539 */             PreparedStatement psql3 = con.prepareStatement(sSQL);
/* 2540 */             iAssetInFrontOfSequenceNumberIncrement++; psql3.setInt(1, iAssetInFrontOfSequenceNumberIncrement);
/* 2541 */             psql3.setLong(2, a_lAssetBoxId);
/* 2542 */             psql3.setLong(3, rs.getLong("AssetId"));
/* 2543 */             psql3.executeUpdate();
/* 2544 */             psql3.close();
/*      */           }
/*      */ 
/* 2549 */           sSQL = "UPDATE AssetBoxAsset SET SequenceNumber=? WHERE AssetBoxId=? AND AssetId=?";
/* 2550 */           PreparedStatement psql3 = con.prepareStatement(sSQL);
/* 2551 */           psql3.setInt(1, iAssetInFrontOfSequenceNumber);
/* 2552 */           psql3.setLong(2, a_lAssetBoxId);
/* 2553 */           psql3.setLong(3, a_lAssetId);
/* 2554 */           psql3.executeUpdate();
/* 2555 */           psql3.close();
/*      */ 
/* 2558 */           psql.close();
/*      */         }
/*      */ 
/* 2561 */         psql1.close();
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 2566 */         throw new Bn2Exception("SQL Exception whilst moving an asset in lightbox: ", e);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 2574 */       Connection con = null;
/* 2575 */       PreparedStatement psql1 = null;
/* 2576 */       String sSQL = null;
/* 2577 */       ResultSet rs = null;
/*      */       try
/*      */       {
/* 2581 */         con = a_dbTransaction.getConnection();
/*      */ 
/* 2586 */         sSQL = "SELECT SequenceNumber FROM AssetBoxAsset WHERE AssetBoxId=? AND AssetId=?";
/* 2587 */         psql1 = con.prepareStatement(sSQL);
/* 2588 */         psql1.setLong(1, a_lAssetBoxId);
/* 2589 */         psql1.setLong(2, a_lAssetId);
/* 2590 */         rs = psql1.executeQuery();
/*      */ 
/* 2592 */         if (rs.next())
/*      */         {
/* 2594 */           int iOldSequenceNumber = rs.getInt("SequenceNumber");
/*      */ 
/* 2598 */           sSQL = "SELECT AssetBoxId, AssetId, SequenceNumber FROM AssetBoxAsset WHERE 1=1";
/*      */ 
/* 2602 */           if (!a_bUp)
/*      */           {
/* 2604 */             sSQL = sSQL + " AND SequenceNumber>? ORDER BY SequenceNumber ASC";
/*      */           }
/*      */           else
/*      */           {
/* 2609 */             sSQL = sSQL + " AND SequenceNumber<? ORDER BY SequenceNumber DESC";
/*      */           }
/*      */ 
/* 2613 */           PreparedStatement psql = con.prepareStatement(sSQL);
/* 2614 */           psql.setInt(1, iOldSequenceNumber);
/* 2615 */           rs = psql.executeQuery();
/*      */ 
/* 2618 */           if (rs.next())
/*      */           {
/* 2620 */             sSQL = "UPDATE AssetBoxAsset SET SequenceNumber=? WHERE AssetBoxId=? AND AssetId=?";
/* 2621 */             PreparedStatement psql3 = con.prepareStatement(sSQL);
/* 2622 */             psql3.setInt(1, iOldSequenceNumber);
/* 2623 */             psql3.setLong(2, rs.getLong("AssetBoxId"));
/* 2624 */             psql3.setLong(3, rs.getLong("AssetId"));
/* 2625 */             psql3.executeUpdate();
/* 2626 */             psql3.close();
/*      */ 
/* 2628 */             sSQL = "UPDATE AssetBoxAsset SET SequenceNumber=? WHERE AssetBoxId=? AND AssetId=?";
/* 2629 */             psql3 = con.prepareStatement(sSQL);
/* 2630 */             psql3.setInt(1, rs.getInt("SequenceNumber"));
/* 2631 */             psql3.setLong(2, a_lAssetBoxId);
/* 2632 */             psql3.setLong(3, a_lAssetId);
/* 2633 */             psql3.executeUpdate();
/* 2634 */             psql3.close();
/*      */           }
/*      */ 
/* 2637 */           psql.close();
/*      */         }
/*      */ 
/* 2640 */         psql1.close();
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 2645 */         throw new Bn2Exception("SQL Exception whilst moving an asset in lightbox: " + e, e);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void reorderAssets(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 2666 */     Connection con = null;
/* 2667 */     PreparedStatement psql = null;
/* 2668 */     PreparedStatement psql2 = null;
/* 2669 */     String sSQL = null;
/* 2670 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2674 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2676 */       sSQL = "SELECT AssetBoxId, AssetId FROM AssetBoxAsset ORDER BY " + SQLGenerator.getInstance().getOrderByForLargeTextField("TimeAdded", 100) + " ASC";
/*      */ 
/* 2680 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 2682 */       rs = psql.executeQuery();
/*      */ 
/* 2684 */       int iSequenceNumber = 1;
/*      */ 
/* 2686 */       while (rs.next())
/*      */       {
/* 2688 */         sSQL = "UPDATE AssetBoxAsset SET SequenceNumber=? WHERE AssetBoxId=? AND AssetId=?";
/* 2689 */         psql2 = con.prepareStatement(sSQL);
/* 2690 */         psql2.setLong(1, iSequenceNumber++);
/* 2691 */         psql2.setLong(2, rs.getLong("AssetBoxId"));
/* 2692 */         psql2.setLong(3, rs.getLong("AssetId"));
/* 2693 */         psql2.executeUpdate();
/* 2694 */         psql2.close();
/*      */       }
/* 2696 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2701 */       this.m_logger.error("SQL Exception whilst adding sequence numbers to assets in asset box : " + e);
/* 2702 */       throw new Bn2Exception("SQL Exception whilst adding sequence numbers to assets in asset box : " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public long copyAssetBox(DBTransaction a_dbTransaction, long a_lIdToCopyFrom, long a_lUserId, String a_sNewAssetBoxName)
/*      */     throws Bn2Exception
/*      */   {
/* 2717 */     long lNewId = createAssetBox(a_dbTransaction, a_lUserId, a_sNewAssetBoxName);
/*      */ 
/* 2719 */     Connection con = null;
/* 2720 */     PreparedStatement psql = null;
/* 2721 */     String sSQL = null;
/*      */     try
/*      */     {
/* 2725 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 2727 */       sSQL = "INSERT INTO AssetBoxAsset (AssetBoxId, AssetId, AddedByUserId, TimeAdded, SequenceNumber) SELECT ?, AssetId, AddedByUserId, TimeAdded, SequenceNumber FROM AssetBoxAsset WHERE AssetBoxId=?";
/*      */ 
/* 2730 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 2732 */       psql.setLong(1, lNewId);
/* 2733 */       psql.setLong(2, a_lIdToCopyFrom);
/*      */ 
/* 2735 */       psql.executeUpdate();
/* 2736 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2741 */       this.m_logger.error("SQL Exception whilst copying assetbox : " + e);
/* 2742 */       throw new Bn2Exception("SQL Exception whilst copying assetbox : " + e, e);
/*      */     }
/*      */ 
/* 2746 */     return lNewId;
/*      */   }
/*      */ 
/*      */   public DBTransactionManager getTransactionManager()
/*      */   {
/* 2754 */     return this.m_transactionManager;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*      */   {
/* 2760 */     this.m_transactionManager = a_sTransactionManager;
/*      */   }
/*      */ 
/*      */   public FileStoreManager getFileStoreManager()
/*      */   {
/* 2766 */     return this.m_fileStoreManager;
/*      */   }
/*      */ 
/*      */   public void setFileStoreManager(FileStoreManager a_sFileStoreManager)
/*      */   {
/* 2772 */     this.m_fileStoreManager = a_sFileStoreManager;
/*      */   }
/*      */ 
/*      */   public IAssetManager getAssetManager()
/*      */   {
/* 2778 */     return this.m_assetManager;
/*      */   }
/*      */ 
/*      */   public void setAssetManager(IAssetManager a_sAssetManager)
/*      */   {
/* 2784 */     this.m_assetManager = a_sAssetManager;
/*      */   }
/*      */ 
/*      */   public void setAssetApprovalManager(AssetApprovalManager a_assetApprovalManager)
/*      */   {
/* 2789 */     this.m_assetApprovalManager = a_assetApprovalManager;
/*      */   }
/*      */ 
/*      */   public void setTaxManager(TaxManager a_taxManager)
/*      */   {
/* 2794 */     this.m_taxManager = a_taxManager;
/*      */   }
/*      */ 
/*      */   public void setUserManager(ABUserManager manager)
/*      */   {
/* 2799 */     this.m_userManager = manager;
/*      */   }
/*      */ 
/*      */   public void setEmailManager(EmailManager emailManager)
/*      */   {
/* 2804 */     this.m_emailManager = emailManager;
/*      */   }
/*      */ 
/*      */   public void setListManager(ListManager listManager)
/*      */   {
/* 2809 */     this.m_listManager = listManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.service.AssetBoxManager
 * JD-Core Version:    0.6.0
 */