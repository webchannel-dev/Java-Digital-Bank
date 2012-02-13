/*      */ package com.bright.assetbank.subscription.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.ecommerce.bean.Purchase;
/*      */ import com.bright.assetbank.ecommerce.service.PaymentCallbackProcessor;
/*      */ import com.bright.assetbank.subscription.bean.Subscription;
/*      */ import com.bright.assetbank.subscription.bean.SubscriptionModel;
/*      */ import com.bright.assetbank.subscription.bean.SubscriptionModel.Translation;
/*      */ import com.bright.assetbank.subscription.bean.SubscriptionModelUpgrade;
/*      */ import com.bright.assetbank.subscription.bean.SubscriptionPurchase;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.assetbank.user.service.ABUserManager;
/*      */ import com.bright.framework.common.bean.BrightDate;
/*      */ import com.bright.framework.common.bean.BrightMoney;
/*      */ import com.bright.framework.common.bean.BrightNaturalNumber;
/*      */ import com.bright.framework.common.bean.StringDataBean;
/*      */ import com.bright.framework.common.service.ScheduleManager;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.mail.service.EmailManager;
/*      */ import com.bright.framework.user.constant.UserSettings;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.TimerTask;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class SubscriptionManager extends Bn2Manager
/*      */   implements PaymentCallbackProcessor
/*      */ {
/*      */   private static final String c_ksClassName = "SubscriptionManager";
/*   70 */   private EmailManager m_emailManager = null;
/*      */ 
/*   76 */   private ABUserManager m_userManager = null;
/*      */ 
/*   82 */   private ScheduleManager m_scheduleManager = null;
/*      */ 
/*   87 */   private DBTransactionManager m_transactionManager = null;
/*      */ 
/*      */   public void setEmailManager(EmailManager a_emailManager)
/*      */   {
/*   73 */     this.m_emailManager = a_emailManager;
/*      */   }
/*      */ 
/*      */   public void setUserManager(ABUserManager a_userManager)
/*      */   {
/*   79 */     this.m_userManager = a_userManager;
/*      */   }
/*      */ 
/*      */   public void setScheduleManager(ScheduleManager a_sScheduleManager)
/*      */   {
/*   84 */     this.m_scheduleManager = a_sScheduleManager;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*      */   {
/*   89 */     this.m_transactionManager = a_sTransactionManager;
/*      */   }
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*  104 */     super.startup();
/*      */ 
/*  107 */     TimerTask task = new TimerTask()
/*      */     {
/*      */       public void run()
/*      */       {
/*      */         try
/*      */         {
/*  113 */           SubscriptionManager.this.runSubscriptionActivationRoutine();
/*      */         }
/*      */         catch (Bn2Exception bn2e)
/*      */         {
/*  117 */           SubscriptionManager.this.m_logger.error("SubscriptionManager: Exception in runSubscriptionActivationRoutine : " + bn2e);
/*      */         }
/*      */       }
/*      */     };
/*  123 */     int iHourOfDay = AssetBankSettings.getRunSubscriptionActivationHourOfDay();
/*  124 */     if ((iHourOfDay >= 0) && (AssetBankSettings.getSubscription()))
/*      */     {
/*  126 */       this.m_scheduleManager.scheduleDailyTask(task, iHourOfDay, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getSubscriptionModels(DBTransaction a_dbTransaction, boolean a_bActiveOnly)
/*      */     throws Bn2Exception
/*      */   {
/*  142 */     String ksMethodName = "getSubscriptionModels";
/*  143 */     Connection con = null;
/*  144 */     String sSQL = null;
/*  145 */     PreparedStatement psql = null;
/*  146 */     Vector vec = new Vector();
/*      */     try
/*      */     {
/*  150 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  152 */       sSQL = "SELECT sm.Id smId, sm.Description, sm.Price, sm.NoOfDownloads, sm.Duration, sm.Inactive, tsm.Description, l.Id lId, l.Code lCode, l.Name lName  FROM SubscriptionModel sm LEFT JOIN TranslatedSubscriptionModel tsm ON tsm.SubscriptionModelId = sm.Id LEFT JOIN Language l ON l.Id = tsm.LanguageId ";
/*      */ 
/*  159 */       if (a_bActiveOnly)
/*      */       {
/*  161 */         sSQL = sSQL + "WHERE sm.Inactive = 0 ";
/*      */       }
/*      */ 
/*  164 */       psql = con.prepareStatement(sSQL);
/*  165 */       ResultSet rs = psql.executeQuery();
/*  166 */       SubscriptionModel model = null;
/*  167 */       long lLastSeenId = 0L;
/*      */ 
/*  169 */       while (rs.next())
/*      */       {
/*  171 */         if (rs.getLong("smId") != lLastSeenId)
/*      */         {
/*  173 */           model = buildSubscriptionModel(rs);
/*      */ 
/*  177 */           if (modelHasActiveSubscriptions(a_dbTransaction, model.getId()))
/*      */           {
/*  179 */             model.setHasActiveSubscriptions(true);
/*      */           }
/*      */ 
/*  182 */           vec.add(model);
/*      */ 
/*  184 */           lLastSeenId = model.getId();
/*      */         }
/*      */ 
/*  187 */         buildTranslation(rs, model);
/*      */       }
/*      */ 
/*  190 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  195 */       throw new Bn2Exception("SubscriptionManager.getSubscriptionModels: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  198 */     return vec;
/*      */   }
/*      */ 
/*      */   public SubscriptionModel getSubscriptionModel(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  213 */     String ksMethodName = "getSubscriptionModel";
/*  214 */     Connection con = null;
/*  215 */     String sSQL = null;
/*  216 */     PreparedStatement psql = null;
/*  217 */     SubscriptionModel model = null;
/*      */     try
/*      */     {
/*  221 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  223 */       sSQL = "SELECT sm.Id smId, sm.Description, sm.Price, sm.NoOfDownloads, sm.Duration, sm.Inactive, tsm.Description, l.Id lId, l.Code lCode, l.Name lName FROM SubscriptionModel sm LEFT JOIN TranslatedSubscriptionModel tsm ON tsm.SubscriptionModelId = sm.Id LEFT JOIN Language l ON l.Id = tsm.LanguageId WHERE sm.Id = ? ";
/*      */ 
/*  231 */       psql = con.prepareStatement(sSQL);
/*  232 */       psql.setLong(1, a_lId);
/*  233 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  235 */       while (rs.next())
/*      */       {
/*  237 */         if (model == null)
/*      */         {
/*  239 */           model = buildSubscriptionModel(rs);
/*      */         }
/*      */ 
/*  242 */         buildTranslation(rs, model);
/*      */       }
/*  244 */       psql.close();
/*      */ 
/*  247 */       if (modelHasActiveSubscriptions(a_dbTransaction, model.getId()))
/*      */       {
/*  249 */         model.setHasActiveSubscriptions(true);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  255 */       throw new Bn2Exception("SubscriptionManager.getSubscriptionModel: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  259 */     Vector groups = getGroupsForSubscriptionModel(a_dbTransaction, a_lId);
/*  260 */     model.setGroups(groups);
/*      */ 
/*  263 */     Vector upgrades = getUpgradesForSubscriptionModel(a_dbTransaction, a_lId);
/*  264 */     model.setUpgrades(upgrades);
/*      */ 
/*  266 */     return model;
/*      */   }
/*      */ 
/*      */   private void buildTranslation(ResultSet a_rs, SubscriptionModel a_model) throws SQLException, Bn2Exception
/*      */   {
/*  271 */     if (a_rs.getLong("lId") > 0L)
/*      */     {
/*      */       //SubscriptionModel tmp18_17 = a_model; tmp18_17.getClass(); SubscriptionModel.Translation translation = new SubscriptionModel.Translation(tmp18_17, new Language(a_rs.getLong("lId"), a_rs.getString("lName"), a_rs.getString("lCode")));
/*  274 */       SubscriptionModel.Translation translation = a_model.new Translation(new Language(a_rs.getLong("lId"), a_rs.getString("lName"), a_rs.getString("lCode")));
                translation.setDescription(SQLGenerator.getInstance().getStringFromLargeTextField(a_rs, "tsm.Description"));
/*  275 */       a_model.getTranslations().add(translation);
/*      */     }
/*      */   }
/*      */ 
/*      */   private SubscriptionModel buildSubscriptionModel(ResultSet a_rs)
/*      */     throws SQLException
/*      */   {
/*  293 */     SubscriptionModel model = new SubscriptionModel();
/*  294 */     model.setId(a_rs.getLong("smId"));
/*  295 */     model.setDescription(a_rs.getString("sm.Description"));
/*  296 */     model.setInactive(a_rs.getBoolean("sm.Inactive"));
/*  297 */     model.getDuration().setNumber(a_rs.getInt("sm.Duration"));
/*  298 */     model.getNoOfDownloads().setNumber(a_rs.getInt("sm.NoOfDownloads"));
/*  299 */     BrightMoney money = new BrightMoney(a_rs.getLong("sm.Price"));
/*  300 */     model.setPrice(money);
/*      */ 
/*  302 */     return model;
/*      */   }
/*      */ 
/*      */   public void saveSubscriptionModel(DBTransaction a_dbTransaction, SubscriptionModel a_model)
/*      */     throws Bn2Exception
/*      */   {
/*  317 */     long lId = a_model.getId();
/*  318 */     if (lId > 0L)
/*      */     {
/*  320 */       updateSubscriptionModel(a_dbTransaction, a_model);
/*      */     }
/*      */     else
/*      */     {
/*  324 */       newSubscriptionModel(a_dbTransaction, a_model);
/*      */     }
/*      */ 
/*  328 */     setGroupsForSubscriptionModel(a_dbTransaction, a_model.getGroups(), a_model.getId());
/*      */ 
/*  331 */     setUpgradesForSubscriptionModel(a_dbTransaction, a_model.getUpgrades(), a_model.getId());
/*      */   }
/*      */ 
/*      */   private void newSubscriptionModel(DBTransaction a_dbTransaction, SubscriptionModel a_model)
/*      */     throws Bn2Exception
/*      */   {
/*  345 */     String ksMethodName = "newSubscriptionModel";
/*  346 */     Connection con = null;
/*  347 */     String sSQL = null;
/*  348 */     PreparedStatement psql = null;
/*  349 */     long lNewId = 0L;
/*      */     try
/*      */     {
/*  353 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  355 */       sSQL = "INSERT INTO SubscriptionModel (";
/*      */ 
/*  357 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*  358 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  360 */         lNewId = sqlGenerator.getUniqueId(con, "SubscriptionModelSequence");
/*  361 */         sSQL = sSQL + "Id, ";
/*      */       }
/*  363 */       sSQL = sSQL + "Description, Price, NoOfDownloads, Duration, Inactive) VALUES (";
/*      */ 
/*  365 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  367 */         sSQL = sSQL + "?, ";
/*      */       }
/*  369 */       sSQL = sSQL + "?,?,?,?,?)";
/*      */ 
/*  371 */       psql = con.prepareStatement(sSQL);
/*  372 */       int iCol = 1;
/*  373 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  375 */         psql.setLong(iCol++, lNewId);
/*      */       }
/*  377 */       psql.setString(iCol++, a_model.getDescription());
/*  378 */       psql.setLong(iCol++, a_model.getPrice().getAmount());
/*  379 */       psql.setLong(iCol++, a_model.getNoOfDownloads().getNumber());
/*  380 */       psql.setLong(iCol++, a_model.getDuration().getNumber());
/*  381 */       psql.setBoolean(iCol++, a_model.getInactive());
/*  382 */       psql.executeUpdate();
/*  383 */       psql.close();
/*      */ 
/*  385 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  387 */         lNewId = sqlGenerator.getUniqueId(con, "SubscriptionModel");
/*      */       }
/*  389 */       a_model.setId(lNewId);
/*      */ 
/*  391 */       insertTranslations(a_model, con);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  396 */       throw new Bn2Exception("SubscriptionManager.newSubscriptionModel: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void insertTranslations(SubscriptionModel a_model, Connection a_con)
/*      */     throws SQLException
/*      */   {
/*  404 */     Iterator itTranslations = a_model.getTranslations().iterator();
/*  405 */     while (itTranslations.hasNext())
/*      */     {
/*  407 */       SubscriptionModel.Translation translation = (SubscriptionModel.Translation)itTranslations.next();
/*      */ 
/*  409 */       if (translation.getLanguage().getId() > 0L)
/*      */       {
/*  411 */         String sSQL = "INSERT INTO TranslatedSubscriptionModel (SubscriptionModelId,LanguageId,Description) VALUES (?,?,?)";
/*  412 */         PreparedStatement psql = a_con.prepareStatement(sSQL);
/*  413 */         psql.setLong(1, a_model.getId());
/*  414 */         psql.setLong(2, translation.getLanguage().getId());
/*  415 */         psql.setString(3, translation.getDescription());
/*  416 */         psql.executeUpdate();
/*  417 */         psql.close();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateSubscriptionModel(DBTransaction a_dbTransaction, SubscriptionModel a_model)
/*      */     throws Bn2Exception
/*      */   {
/*  438 */     String ksMethodName = "updateSubscriptionModel";
/*  439 */     Connection con = null;
/*  440 */     String sSQL = null;
/*  441 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  445 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  447 */       sSQL = "UPDATE SubscriptionModel SET Description=?, Price=?, NoOfDownloads=?, Duration=?, Inactive=? WHERE Id=?";
/*      */ 
/*  450 */       psql = con.prepareStatement(sSQL);
/*  451 */       int iCol = 1;
/*  452 */       psql.setString(iCol++, a_model.getDescription());
/*  453 */       psql.setLong(iCol++, a_model.getPrice().getAmount());
/*  454 */       psql.setLong(iCol++, a_model.getNoOfDownloads().getNumber());
/*  455 */       psql.setLong(iCol++, a_model.getDuration().getNumber());
/*  456 */       psql.setBoolean(iCol++, a_model.getInactive());
/*  457 */       psql.setLong(iCol++, a_model.getId());
/*  458 */       psql.executeUpdate();
/*  459 */       psql.close();
/*      */ 
/*  461 */       sSQL = "DELETE FROM TranslatedSubscriptionModel WHERE SubscriptionModelId=?";
/*  462 */       psql = con.prepareStatement(sSQL);
/*  463 */       psql.setLong(1, a_model.getId());
/*  464 */       psql.executeUpdate();
/*  465 */       psql.close();
/*      */ 
/*  467 */       insertTranslations(a_model, con);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  472 */       throw new Bn2Exception("SubscriptionManager.updateSubscriptionModel: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void activateSubscriptionModel(DBTransaction a_dbTransaction, long a_lId, boolean a_bActive)
/*      */     throws Bn2Exception
/*      */   {
/*  489 */     String ksMethodName = "activateSubscriptionModel";
/*  490 */     Connection con = null;
/*  491 */     String sSQL = null;
/*  492 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  496 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  498 */       sSQL = "UPDATE SubscriptionModel SET Inactive=? WHERE Id = ? ";
/*      */ 
/*  500 */       psql = con.prepareStatement(sSQL);
/*  501 */       psql.setBoolean(1, !a_bActive);
/*  502 */       psql.setLong(2, a_lId);
/*  503 */       psql.executeUpdate();
/*  504 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  509 */       throw new Bn2Exception("SubscriptionManager.activateSubscriptionModel: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteSubscriptionModel(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  525 */     String ksMethodName = "deleteSubscriptionModel";
/*  526 */     Connection con = null;
/*  527 */     String sSQL = null;
/*  528 */     PreparedStatement psql = null;
/*      */ 
/*  531 */     deleteGroupsForSubscriptionModel(a_dbTransaction, a_lId);
/*      */ 
/*  534 */     deleteUpgradesForSubscriptionModel(a_dbTransaction, a_lId);
/*      */     try
/*      */     {
/*  538 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  541 */       sSQL = "DELETE FROM Subscription WHERE SubscriptionModelId = ? ";
/*  542 */       psql = con.prepareStatement(sSQL);
/*  543 */       psql.setLong(1, a_lId);
/*  544 */       psql.executeUpdate();
/*  545 */       psql.close();
/*      */ 
/*  548 */       sSQL = "DELETE FROM TranslatedSubscriptionModel WHERE SubscriptionModelId = ? ";
/*  549 */       psql = con.prepareStatement(sSQL);
/*  550 */       psql.setLong(1, a_lId);
/*  551 */       psql.executeUpdate();
/*  552 */       psql.close();
/*      */ 
/*  556 */       sSQL = "DELETE FROM SubscriptionModelUpgrade WHERE ToId = ? ";
/*  557 */       psql = con.prepareStatement(sSQL);
/*  558 */       psql.setLong(1, a_lId);
/*  559 */       psql.executeUpdate();
/*  560 */       psql.close();
/*      */ 
/*  563 */       sSQL = "DELETE FROM SubscriptionModel WHERE Id = ? ";
/*  564 */       psql = con.prepareStatement(sSQL);
/*  565 */       psql.setLong(1, a_lId);
/*  566 */       psql.executeUpdate();
/*  567 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  572 */       throw new Bn2Exception("SubscriptionManager.deleteSubscriptionModel: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private Vector getGroupsForSubscriptionModel(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  591 */     String ksMethodName = "getGroupsForSubscriptionModel";
/*  592 */     Connection con = null;
/*  593 */     String sSQL = null;
/*  594 */     PreparedStatement psql = null;
/*  595 */     Vector vec = new Vector();
/*      */     try
/*      */     {
/*  599 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  601 */       sSQL = "SELECT g.Id, g.Name FROM UserGroup g INNER JOIN GroupInSubscriptionModel gism ON gism.UserGroupId = g.Id WHERE gism.SubscriptionModelId = ?";
/*      */ 
/*  606 */       psql = con.prepareStatement(sSQL);
/*  607 */       psql.setLong(1, a_lId);
/*  608 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  610 */       while (rs.next())
/*      */       {
/*  612 */         Long olGroupId = new Long(rs.getLong("Id"));
/*  613 */         vec.add(olGroupId);
/*      */       }
/*      */ 
/*  616 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  621 */       throw new Bn2Exception("SubscriptionManager.getGroupsForSubscriptionModel: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  624 */     return vec;
/*      */   }
/*      */ 
/*      */   private void setGroupsForSubscriptionModel(DBTransaction a_dbTransaction, Vector a_vecGroupIds, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  639 */     String ksMethodName = "setGroupsForSubscriptionModel";
/*  640 */     Connection con = null;
/*  641 */     String sSql = null;
/*  642 */     PreparedStatement psql = null;
/*      */ 
/*  644 */     deleteGroupsForSubscriptionModel(a_dbTransaction, a_lId);
/*      */     try
/*      */     {
/*  648 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  651 */       sSql = "INSERT INTO GroupInSubscriptionModel (SubscriptionModelId, UserGroupId) VALUES (?,?)";
/*  652 */       psql = con.prepareStatement(sSql);
/*      */ 
/*  654 */       for (int i = 0; i < a_vecGroupIds.size(); i++)
/*      */       {
/*  656 */         psql.setLong(1, a_lId);
/*  657 */         psql.setLong(2, ((Long)a_vecGroupIds.elementAt(i)).longValue());
/*  658 */         psql.executeUpdate();
/*      */       }
/*      */ 
/*  661 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  666 */       throw new Bn2Exception("SubscriptionManager.setGroupsForSubscriptionModel: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void deleteGroupsForSubscriptionModel(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  681 */     String ksMethodName = "deleteGroupsForSubscriptionModel";
/*  682 */     Connection con = null;
/*  683 */     String sSql = null;
/*  684 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  688 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  691 */       sSql = "DELETE FROM GroupInSubscriptionModel WHERE SubscriptionModelId=?";
/*  692 */       psql = con.prepareStatement(sSql);
/*  693 */       psql.setLong(1, a_lId);
/*  694 */       psql.executeUpdate();
/*  695 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  700 */       throw new Bn2Exception("SubscriptionManager.deleteGroupsForSubscriptionModel: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean modelHasActiveSubscriptions(DBTransaction a_dbTransaction, long a_lModelId)
/*      */     throws Bn2Exception
/*      */   {
/*  717 */     String ksMethodName = "modelHasActiveSubscriptions";
/*  718 */     Connection con = null;
/*  719 */     String sSQL = null;
/*  720 */     PreparedStatement psql = null;
/*  721 */     boolean bHasActiveSubscriptions = false;
/*      */     try
/*      */     {
/*  725 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  727 */       sSQL = "SELECT * FROM Subscription sub INNER JOIN SubscriptionModel sm ON sub.SubscriptionModelId = sm.Id WHERE sm.Id=? AND (sub.IsActive=1 OR sub.StartDate >= ?) ";
/*      */ 
/*  733 */       psql = con.prepareStatement(sSQL);
/*  734 */       psql.setLong(1, a_lModelId);
/*      */ 
/*  736 */       BrightDate today = BrightDate.today();
/*  737 */       DBUtil.setFieldDateOrNull(psql, 2, today.getDate());
/*  738 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  740 */       while (rs.next())
/*      */       {
/*  742 */         bHasActiveSubscriptions = true;
/*      */       }
/*      */ 
/*  745 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  750 */       throw new Bn2Exception("SubscriptionManager.modelHasActiveSubscriptions: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  753 */     return bHasActiveSubscriptions;
/*      */   }
/*      */ 
/*      */   private Vector getUpgradesForSubscriptionModel(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  771 */     String ksMethodName = "getUpgradesForSubscriptionModel";
/*  772 */     Connection con = null;
/*  773 */     String sSQL = null;
/*  774 */     PreparedStatement psql = null;
/*  775 */     Vector vec = new Vector();
/*      */     try
/*      */     {
/*  779 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  781 */       sSQL = "SELECT smu.ToId, smu.Price FROM SubscriptionModelUpgrade smu WHERE smu.FromId = ?";
/*      */ 
/*  785 */       psql = con.prepareStatement(sSQL);
/*  786 */       psql.setLong(1, a_lId);
/*  787 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  789 */       while (rs.next())
/*      */       {
/*  791 */         SubscriptionModelUpgrade upgrade = new SubscriptionModelUpgrade();
/*  792 */         upgrade.setFromModelId(a_lId);
/*  793 */         upgrade.setToModelId(rs.getLong("ToId"));
/*  794 */         upgrade.getPrice().setAmount(rs.getLong("Price"));
/*  795 */         vec.add(upgrade);
/*      */       }
/*      */ 
/*  798 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  803 */       throw new Bn2Exception("SubscriptionManager.getUpgradesForSubscriptionModel: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/*  806 */     return vec;
/*      */   }
/*      */ 
/*      */   private void setUpgradesForSubscriptionModel(DBTransaction a_dbTransaction, Vector a_vec, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  821 */     String ksMethodName = "setUpgradesForSubscriptionModel";
/*  822 */     Connection con = null;
/*  823 */     String sSql = null;
/*  824 */     PreparedStatement psql = null;
/*      */ 
/*  826 */     deleteUpgradesForSubscriptionModel(a_dbTransaction, a_lId);
/*      */     try
/*      */     {
/*  830 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  833 */       sSql = "INSERT INTO SubscriptionModelUpgrade (FromId, ToId, Price) VALUES (?,?,?)";
/*  834 */       psql = con.prepareStatement(sSql);
/*      */ 
/*  836 */       for (int i = 0; i < a_vec.size(); i++)
/*      */       {
/*  838 */         SubscriptionModelUpgrade upgrade = (SubscriptionModelUpgrade)a_vec.elementAt(i);
/*  839 */         psql.setLong(1, a_lId);
/*  840 */         psql.setLong(2, upgrade.getToModelId());
/*  841 */         psql.setLong(3, upgrade.getPrice().getAmount());
/*  842 */         psql.executeUpdate();
/*      */       }
/*      */ 
/*  845 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  850 */       throw new Bn2Exception("SubscriptionManager.setUpgradesForSubscriptionModel: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void deleteUpgradesForSubscriptionModel(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  865 */     String ksMethodName = "deleteUpgradesForSubscriptionModel";
/*  866 */     Connection con = null;
/*  867 */     String sSql = null;
/*  868 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  872 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  875 */       sSql = "DELETE FROM SubscriptionModelUpgrade WHERE FromId=?";
/*  876 */       psql = con.prepareStatement(sSql);
/*  877 */       psql.setLong(1, a_lId);
/*  878 */       psql.executeUpdate();
/*  879 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  884 */       throw new Bn2Exception("SubscriptionManager.deleteUpgradesForSubscriptionModel: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getAllSubscriptions(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  902 */     Vector vec = getSubscriptions(a_dbTransaction, 0L, false);
/*  903 */     return vec;
/*      */   }
/*      */ 
/*      */   public Vector getSubscriptions(DBTransaction a_dbTransaction, long a_lUserId, boolean a_bValidOnly)
/*      */     throws Bn2Exception
/*      */   {
/*  918 */     String ksMethodName = "getSubscriptions";
/*  919 */     Connection con = null;
/*  920 */     String sSQL = null;
/*  921 */     PreparedStatement psql = null;
/*  922 */     Vector vec = new Vector();
/*      */     try
/*      */     {
/*  926 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  928 */       sSQL = "SELECT sm.Id smId, sm.Description, sm.Price, sm.NoOfDownloads, sm.Duration, sm.Inactive, sub.Id subId, sub.StartDate, sub.IsActive subIsActive, sub.UserId, sub.PricePaid, tsm.Description, l.Id lId, l.Code lCode, l.Name lName FROM Subscription sub INNER JOIN SubscriptionModel sm ON sub.SubscriptionModelId = sm.Id LEFT JOIN TranslatedSubscriptionModel tsm ON tsm.SubscriptionModelId = sm.Id LEFT JOIN Language l ON l.Id = tsm.LanguageId WHERE 1=1 ";
/*      */ 
/*  951 */       if (a_lUserId > 0L)
/*      */       {
/*  953 */         sSQL = sSQL + "AND sub.UserId = ? ";
/*      */       }
/*      */ 
/*  956 */       if (a_bValidOnly)
/*      */       {
/*  958 */         sSQL = sSQL + "AND sub.IsActive = 1 ";
/*      */       }
/*      */ 
/*  961 */       sSQL = sSQL + "ORDER BY sub.StartDate DESC, sm.Description ";
/*      */ 
/*  963 */       psql = con.prepareStatement(sSQL);
/*      */ 
/*  965 */       if (a_lUserId > 0L)
/*      */       {
/*  967 */         psql.setLong(1, a_lUserId);
/*      */       }
/*      */ 
/*  970 */       ResultSet rs = psql.executeQuery();
/*  971 */       long lLastSeenId = 0L;
/*  972 */       Subscription sub = null;
/*      */ 
/*  974 */       while (rs.next())
/*      */       {
/*  976 */         if (rs.getLong("subId") != lLastSeenId)
/*      */         {
/*  978 */           SubscriptionModel model = buildSubscriptionModel(rs);
/*      */ 
/*  980 */           sub = new Subscription();
/*  981 */           sub.setId(rs.getLong("subId"));
/*  982 */           sub.getStartDate().setDate(rs.getDate("sub.StartDate"));
/*  983 */           sub.setActive(rs.getBoolean("subIsActive"));
/*  984 */           sub.getRefUser().setId(rs.getLong("sub.UserId"));
/*  985 */           sub.getPricePaid().setAmount(rs.getLong("sub.PricePaid"));
/*  986 */           sub.setModel(model);
/*      */ 
/*  988 */           vec.add(sub);
/*      */ 
/*  990 */           lLastSeenId = sub.getId();
/*      */         }
/*      */ 
/*  993 */         buildTranslation(rs, sub.getModel());
/*      */       }
/*      */ 
/*  996 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1001 */       throw new Bn2Exception("SubscriptionManager.getSubscriptions: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1004 */     return vec;
/*      */   }
/*      */ 
/*      */   public BrightMoney getBestPriceOfModelForUser(DBTransaction a_dbTransaction, long a_lModelId, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1026 */     String ksMethodName = "getBestPriceOfModelForUser";
/* 1027 */     Connection con = null;
/* 1028 */     String sSQL = null;
/* 1029 */     PreparedStatement psql = null;
/* 1030 */     BrightMoney bestPrice = new BrightMoney();
/*      */     try
/*      */     {
/* 1034 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1036 */       sSQL = "SELECT MIN(smu.Price) BestPrice FROM Subscription sub INNER JOIN SubscriptionModel sm ON sub.SubscriptionModelId = sm.Id INNER JOIN SubscriptionModelUpgrade smu ON smu.FromId = sm.Id WHERE  sub.UserId = ? AND sub.IsActive = 1 AND sm.Inactive = 0 AND smu.ToId = ? ";
/*      */ 
/* 1046 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 1048 */       psql.setLong(1, a_lUserId);
/* 1049 */       psql.setLong(2, a_lModelId);
/*      */ 
/* 1051 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1053 */       if (rs.next())
/*      */       {
/* 1055 */         long lPrice = rs.getLong("BestPrice");
/* 1056 */         bestPrice.setAmount(lPrice);
/*      */       }
/*      */ 
/* 1059 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1064 */       throw new Bn2Exception("SubscriptionManager.getBestPriceOfModelForUser: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1067 */     return bestPrice;
/*      */   }
/*      */ 
/*      */   public Subscription getSubscription(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 1083 */     String ksMethodName = "getSubscription";
/* 1084 */     Connection con = null;
/* 1085 */     String sSQL = null;
/* 1086 */     PreparedStatement psql = null;
/* 1087 */     Subscription sub = new Subscription();
/* 1088 */     long lModelId = 0L;
/*      */     try
/*      */     {
/* 1092 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1094 */       sSQL = "SELECT * FROM Subscription WHERE Id = ? ";
/*      */ 
/* 1097 */       psql = con.prepareStatement(sSQL);
/* 1098 */       psql.setLong(1, a_lId);
/* 1099 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1101 */       while (rs.next())
/*      */       {
/* 1103 */         sub.setId(a_lId);
/* 1104 */         sub.setActive(rs.getBoolean("IsActive"));
/* 1105 */         sub.getStartDate().setDate(rs.getDate("StartDate"));
/* 1106 */         sub.getRefUser().setId(rs.getLong("UserId"));
/* 1107 */         sub.getPricePaid().setAmount(rs.getLong("PricePaid"));
/* 1108 */         lModelId = rs.getLong("SubscriptionModelId");
/*      */       }
/* 1110 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1116 */       throw new Bn2Exception("SubscriptionManager.getSubscription: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1120 */     if (lModelId > 0L)
/*      */     {
/* 1122 */       sub.setModel(getSubscriptionModel(a_dbTransaction, lModelId));
/*      */     }
/*      */ 
/* 1125 */     return sub;
/*      */   }
/*      */ 
/*      */   public void saveSubscription(DBTransaction a_dbTransaction, Subscription a_sub)
/*      */     throws Bn2Exception
/*      */   {
/* 1146 */     newSubscription(a_dbTransaction, a_sub);
/*      */ 
/* 1149 */     activateSubscription(a_dbTransaction, a_sub);
/*      */   }
/*      */ 
/*      */   private void newSubscription(DBTransaction a_dbTransaction, Subscription a_sub)
/*      */     throws Bn2Exception
/*      */   {
/* 1164 */     String ksMethodName = "newSubscription";
/* 1165 */     Connection con = null;
/* 1166 */     String sSQL = null;
/* 1167 */     PreparedStatement psql = null;
/* 1168 */     long lNewId = 0L;
/*      */     try
/*      */     {
/* 1172 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1174 */       sSQL = "INSERT INTO Subscription (";
/*      */ 
/* 1176 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 1177 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1179 */         lNewId = sqlGenerator.getUniqueId(con, "SubscriptionSequence");
/* 1180 */         sSQL = sSQL + "Id, ";
/*      */       }
/* 1182 */       sSQL = sSQL + "StartDate, SubscriptionModelId, UserId, PricePaid, IsActive) VALUES (";
/*      */ 
/* 1184 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1186 */         sSQL = sSQL + "?, ";
/*      */       }
/* 1188 */       sSQL = sSQL + "?,?,?,?,?)";
/*      */ 
/* 1190 */       psql = con.prepareStatement(sSQL);
/* 1191 */       int iCol = 1;
/* 1192 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1194 */         psql.setLong(iCol++, lNewId);
/*      */       }
/* 1196 */       DBUtil.setFieldDateOrNull(psql, iCol++, a_sub.getStartDate().getDate());
/* 1197 */       psql.setLong(iCol++, a_sub.getModel().getId());
/* 1198 */       psql.setLong(iCol++, a_sub.getRefUser().getId());
/* 1199 */       psql.setLong(iCol++, a_sub.getPricePaid().getAmount());
/* 1200 */       psql.setBoolean(iCol++, false);
/* 1201 */       psql.executeUpdate();
/* 1202 */       psql.close();
/*      */ 
/* 1204 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1206 */         lNewId = sqlGenerator.getUniqueId(con, "Subscription");
/*      */       }
/* 1208 */       a_sub.setId(lNewId);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1213 */       throw new Bn2Exception("SubscriptionManager.newSubscription: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void activateSubscription(DBTransaction a_dbTransaction, Subscription a_sub)
/*      */     throws Bn2Exception
/*      */   {
/* 1229 */     long lSubscriptionId = a_sub.getId();
/* 1230 */     if ((a_sub.getStarted()) && (!a_sub.getExpired()))
/*      */     {
/* 1235 */       long lUserId = a_sub.getRefUser().getId();
/* 1236 */       long lModelId = a_sub.getModel().getId();
/* 1237 */       Vector vecModelGroups = getGroupsForSubscriptionModel(a_dbTransaction, lModelId);
/* 1238 */       this.m_userManager.addGroupsToUser(a_dbTransaction, lUserId, vecModelGroups);
/*      */ 
/* 1241 */       markSubscriptionActive(a_dbTransaction, lSubscriptionId, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void deactivateSubscription(DBTransaction a_dbTransaction, Subscription a_sub)
/*      */     throws Bn2Exception
/*      */   {
/* 1259 */     long lSubscriptionId = a_sub.getId();
/* 1260 */     long lUserId = a_sub.getRefUser().getId();
/* 1261 */     long lModelId = a_sub.getModel().getId();
/* 1262 */     Vector vecModelGroups = getGroupsForSubscriptionModel(a_dbTransaction, lModelId);
/* 1263 */     this.m_userManager.removeGroupsFromUser(a_dbTransaction, lUserId, vecModelGroups);
/*      */ 
/* 1266 */     markSubscriptionActive(a_dbTransaction, lSubscriptionId, false);
/*      */   }
/*      */ 
/*      */   private void markSubscriptionActive(DBTransaction a_dbTransaction, long a_lId, boolean a_bActive)
/*      */     throws Bn2Exception
/*      */   {
/* 1282 */     String ksMethodName = "markSubscriptionActive";
/* 1283 */     Connection con = null;
/* 1284 */     String sSQL = null;
/* 1285 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/* 1289 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1291 */       sSQL = "UPDATE Subscription SET IsActive=? WHERE Id=? ";
/*      */ 
/* 1293 */       psql = con.prepareStatement(sSQL);
/* 1294 */       psql.setBoolean(1, a_bActive);
/* 1295 */       psql.setLong(2, a_lId);
/* 1296 */       psql.executeUpdate();
/* 1297 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1302 */       throw new Bn2Exception("SubscriptionManager.markSubscriptionActive: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public long getTotalDownloadsTodayForUser(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1319 */     long lTotalDownloads = 0L;
/*      */ 
/* 1321 */     Vector vecSubscriptions = getSubscriptions(a_dbTransaction, a_lUserId, true);
/*      */ 
/* 1323 */     Iterator itSubscriptions = vecSubscriptions.iterator();
/* 1324 */     while (itSubscriptions.hasNext())
/*      */     {
/* 1326 */       Subscription sub = (Subscription)itSubscriptions.next();
/*      */ 
/* 1328 */       lTotalDownloads += sub.getModel().getNoOfDownloads().getNumber();
/*      */     }
/*      */ 
/* 1331 */     return lTotalDownloads;
/*      */   }
/*      */ 
/*      */   public boolean getUserHasValidSubscription(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1347 */     Vector vecSubscriptions = getSubscriptions(a_dbTransaction, a_lUserId, true);
/*      */ 
/* 1349 */     long lNumValidSubscriptions = vecSubscriptions.size();
/* 1350 */     boolean bHasValidSubscription = lNumValidSubscriptions > 0L;
/* 1351 */     return bHasValidSubscription;
/*      */   }
/*      */ 
/*      */   public long getRemainingDownloadsTodayForUser(DBTransaction a_dbTransaction, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1366 */     long lTotalDownloads = getTotalDownloadsTodayForUser(a_dbTransaction, a_lUserId);
/*      */ 
/* 1368 */     BrightDate today = BrightDate.today();
/* 1369 */     BrightDate tomorrow = BrightDate.tomorrow();
/* 1370 */     long lUsedDownloads = getUserDownloadsInRange(a_dbTransaction, today.getDate(), tomorrow.getDate(), a_lUserId);
/*      */ 
/* 1375 */     long lRemaining = lTotalDownloads - lUsedDownloads;
/* 1376 */     if (lRemaining < 0L)
/*      */     {
/* 1378 */       lRemaining = 0L;
/*      */     }
/*      */ 
/* 1381 */     return lRemaining;
/*      */   }
/*      */ 
/*      */   public void registerPaymentSuccess(DBTransaction a_dbTransaction, Purchase a_purchase, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/* 1403 */     String ksMethodName = "registerPaymentSuccess";
/*      */ 
/* 1406 */     SubscriptionPurchase purchase = (SubscriptionPurchase)a_purchase;
/*      */ 
/* 1409 */     if (purchase.getHasRegistered())
/*      */     {
/* 1411 */       createNewAccount(a_dbTransaction, purchase, a_language);
/*      */     }
/*      */ 
/* 1415 */     saveSubscription(a_dbTransaction, purchase.getSubscription());
/*      */ 
/* 1418 */     HashMap params = new HashMap();
/* 1419 */     params.put("email", purchase.getEmailAddress());
/* 1420 */     params.put("username", purchase.getLoginUser());
/* 1421 */     params.put("amount", purchase.getChargedAmount().getDisplayAmountWithCodePrefix());
/* 1422 */     params.put("description", purchase.getDescription());
/*      */ 
/* 1424 */     if (purchase.getHasRegistered())
/*      */     {
/* 1426 */       params.put("password", purchase.getLoginPassword());
/* 1427 */       params.put("template", "subscription_and_registration_confirmation");
/*      */     }
/*      */     else
/*      */     {
/* 1432 */       if (!UserSettings.getEncryptPasswords())
/*      */       {
/* 1434 */         params.put("password", purchase.getLoginPassword());
/*      */       }
/* 1436 */       params.put("template", "subscription_confirmation");
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1441 */       this.m_emailManager.sendTemplatedEmail(params, a_language);
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 1445 */       this.m_logger.debug("SubscriptionManager:registerPaymentSuccess: The confirmation was not successfully sent to the user: " + purchase.getEmailAddress() + ": " + e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   private ABUser createNewAccount(DBTransaction a_dbTransaction, SubscriptionPurchase a_purchase, Language a_language)
/*      */     throws Bn2Exception
/*      */   {
/* 1468 */     ABUser user = a_purchase.getRegisterUser();
/* 1469 */     user.setLanguage(a_language);
/* 1470 */     this.m_userManager.saveUser(a_dbTransaction, user);
/*      */ 
/* 1473 */     a_purchase.setUserId(user.getId());
/* 1474 */     a_purchase.getSubscription().getRefUser().setId(user.getId());
/*      */ 
/* 1476 */     return user;
/*      */   }
/*      */ 
/*      */   public void runSubscriptionActivationRoutine()
/*      */     throws Bn2Exception
/*      */   {
/* 1493 */     String ksMethodName = "runSubscriptionActivationRoutine";
/* 1494 */     this.m_logger.debug("SubscriptionManager.runSubscriptionActivationRoutine: Running");
/* 1495 */     DBTransaction transaction = null;
/*      */ 
/* 1497 */     HashMap hmDaysBefore = getDaysBeforeExpirySendAlerts();
/*      */     try
/*      */     {
/* 1501 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/* 1504 */       Vector vecSubscriptions = getAllSubscriptions(transaction);
/*      */ 
/* 1506 */       Iterator itSubscriptions = vecSubscriptions.iterator();
/* 1507 */       while (itSubscriptions.hasNext())
/*      */       {
/* 1509 */         Subscription sub = (Subscription)itSubscriptions.next();
/*      */ 
/* 1512 */         if ((!sub.getActive()) && (sub.getStarted()) && (!sub.getExpired()))
/*      */         {
/* 1514 */           activateSubscription(transaction, sub);
/*      */         }
/*      */ 
/* 1518 */         if ((sub.getActive()) && (sub.getExpired()))
/*      */         {
/* 1520 */           deactivateSubscription(transaction, sub);
/*      */         }
/*      */ 
/* 1524 */         if (sub.getActive())
/*      */         {
/* 1526 */           Long olDaysLeft = new Long(sub.getDaysLeft());
/*      */ 
/* 1528 */           if (hmDaysBefore.containsKey(olDaysLeft))
/*      */           {
/* 1530 */             sendExpiryAlert(transaction, sub);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */       try
/*      */       {
/* 1540 */         this.m_logger.debug("SubscriptionManager.runSubscriptionActivationRoutine: About to rollback");
/* 1541 */         transaction.rollback();
/*      */       }
/*      */       catch (SQLException rbe)
/*      */       {
/*      */       }
/*      */ 
/* 1547 */       throw new Bn2Exception("SubscriptionManager.runSubscriptionActivationRoutine: SQL Exception whilst deleting expired approvals: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1552 */       if (transaction != null)
/*      */       {
/*      */         try
/*      */         {
/* 1556 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1561 */           this.m_logger.debug("SubscriptionManager.runSubscriptionActivationRoutine: Exception while attempting to commit transaction.");
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void sendExpiryAlert(DBTransaction a_dbTransaction, Subscription a_sub)
/*      */     throws Bn2Exception
/*      */   {
/* 1577 */     String ksMethodName = "sendExpiryAlert";
/* 1578 */     ABUser user = (ABUser)this.m_userManager.getUser(a_dbTransaction, a_sub.getRefUser().getId());
/*      */ 
/* 1581 */     HashMap hmParams = new HashMap();
/* 1582 */     String sName = user.getFullName();
/* 1583 */     if (!StringUtil.stringIsPopulated(sName))
/*      */     {
/* 1585 */       sName = user.getUsername();
/*      */     }
/*      */ 
/* 1588 */     hmParams.put("template", "subscription_expiry_alert");
/* 1589 */     hmParams.put("email", user.getEmailAddress());
/* 1590 */     hmParams.put("name", sName);
/* 1591 */     hmParams.put("username", user.getUsername());
/* 1592 */     if (!UserSettings.getEncryptPasswords())
/*      */     {
/* 1594 */       hmParams.put("password", user.getPassword());
/*      */     }
/* 1596 */     hmParams.put("days_left", new Long(a_sub.getDaysLeft()).toString());
/* 1597 */     hmParams.put("description", a_sub.getModel().getDescription());
/*      */     try
/*      */     {
/* 1601 */       this.m_emailManager.sendTemplatedEmail(hmParams, user.getLanguage());
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 1605 */       this.m_logger.debug("SubscriptionManager:sendExpiryAlert: The subscription expiry alert was not successfully sent to the user: " + user.getEmailAddress() + ": " + e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   private HashMap getDaysBeforeExpirySendAlerts()
/*      */   {
/* 1624 */     String sList = AssetBankSettings.getDaysBeforeExpirySendAlerts();
/*      */ 
/* 1626 */     long[] alDays = null;
/*      */     try
/*      */     {
/* 1630 */       alDays = StringUtil.getIdsArray(sList, ",");
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/* 1634 */       alDays = new long[0];
/*      */     }
/*      */ 
/* 1637 */     HashMap hmDays = new HashMap();
/* 1638 */     for (int i = 0; i < alDays.length; i++)
/*      */     {
/* 1640 */       Long ol = new Long(alDays[i]);
/* 1641 */       hmDays.put(ol, ol);
/*      */     }
/*      */ 
/* 1644 */     return hmDays;
/*      */   }
/*      */ 
/*      */   public long getUserDownloadsInRange(DBTransaction a_dbTransaction, Date a_dtStartDate, Date a_dtEndDate, long a_lUserId)
/*      */     throws Bn2Exception
/*      */   {
/* 1668 */     Connection con = null;
/* 1669 */     PreparedStatement psql = null;
/* 1670 */     String sSQL = null;
/* 1671 */     ResultSet rs = null;
/* 1672 */     long lNumDownloads = 0L;
/*      */     try
/*      */     {
/* 1676 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1678 */       sSQL = "SELECT COUNT(DISTINCT au.AssetId) countAss FROM AssetUse au INNER JOIN AssetBankUser u ON u.Id = au.UserId WHERE (au.TimeOfDownload >= ? AND au.TimeOfDownload <= ?) AND au.UserId = ? AND NOT EXISTS (SELECT 1 FROM AssetApproval aa \tWHERE aa.AssetId=au.AssetId \tAND aa.UserId=? \tAND ApprovalStatusId=?)";
/*      */ 
/* 1692 */       psql = con.prepareStatement(sSQL);
/* 1693 */       DBUtil.setFieldTimestampOrNull(psql, 1, a_dtStartDate);
/* 1694 */       DBUtil.setFieldTimestampOrNull(psql, 2, a_dtEndDate);
/* 1695 */       psql.setLong(3, a_lUserId);
/* 1696 */       psql.setLong(4, a_lUserId);
/* 1697 */       psql.setLong(5, 2L);
/* 1698 */       rs = psql.executeQuery();
/*      */ 
/* 1700 */       while (rs.next())
/*      */       {
/* 1702 */         lNumDownloads = rs.getInt("countAss");
/*      */       }
/*      */ 
/* 1705 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1710 */       this.m_logger.error("SQL Exception whilst getting asset view report : " + e);
/* 1711 */       throw new Bn2Exception("SQL Exception whilst getting asset view report : " + e, e);
/*      */     }
/* 1713 */     return lNumDownloads;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.subscription.service.SubscriptionManager
 * JD-Core Version:    0.6.0
 */