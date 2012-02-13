/*      */ package com.bright.assetbank.attribute.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.assetbank.actiononasset.action.ActionOnAsset;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.service.IAssetManager;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.attribute.bean.ChangeAttributeValueRule;
/*      */ import com.bright.assetbank.attribute.bean.SendEmailRule;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.usage.bean.AssetUseLogEntry;
/*      */ import com.bright.assetbank.usage.service.UsageManager;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.framework.common.bean.BrightDate;
/*      */ import com.bright.framework.common.bean.BrightDateTime;
/*      */ import com.bright.framework.common.bean.StringDataBean;
/*      */ import com.bright.framework.common.service.RefDataManager;
/*      */ import com.bright.framework.common.service.ScheduleManager;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.mail.service.EmailManager;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import java.util.TimerTask;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class AttributeDateRuleManager extends Bn2Manager
/*      */ {
/*      */   private static final String c_ksClassName = "AttributeDateRuleManager";
/*      */   private TimerTask m_changeAttributeValueRulesTask;
/*      */   private DBTransactionManager m_transactionManager;
/*      */   private AttributeManager m_attributeManager;
/*      */   private EmailManager m_emailManager;
/*      */   private UsageManager m_usageManager;
/*      */   private IAssetManager m_assetManager;
/*      */   private ScheduleManager m_scheduleManager;
/*      */   private RefDataManager m_refDataManager;
/*      */   private TimerTask m_sendEmailRulesTask;
/*      */   private Object m_oProcessingLock;
/*      */ 
/*      */   public AttributeDateRuleManager()
/*      */   {
/*   67 */     this.m_changeAttributeValueRulesTask = null;
/*      */ 
/*   69 */     this.m_transactionManager = null;
/*   70 */     this.m_attributeManager = null;
/*   71 */     this.m_emailManager = null;
/*   72 */     this.m_usageManager = null;
/*   73 */     this.m_assetManager = null;
/*   74 */     this.m_scheduleManager = null;
/*   75 */     this.m_refDataManager = null;
/*      */ 
/*   78 */     this.m_sendEmailRulesTask = null;
/*   79 */     this.m_oProcessingLock = new Object();
/*      */   }
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*   99 */     super.startup();
/*  100 */     initialScheduleRules();
/*      */   }
/*      */ 
/*      */   public Vector getChangeAttributeValueRuleList(DBTransaction a_dbTransaction, long a_lAttributeId)
/*      */     throws Bn2Exception
/*      */   {
/*  114 */     String ksMethodName = "getChangeAttributeValueRuleList";
/*  115 */     Vector vecRuleList = new Vector();
/*  116 */     Connection con = null;
/*  117 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  121 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  123 */       String sSQL = "SELECT attrrule.Id ruleId, attrrule.RuleName ruleName, attrrule.Enabled, attrrule.ActionOnASsetId FROM ChangeAttributeValueDateRule attrrule WHERE attrrule.AttributeId=? ";
/*      */ 
/*  133 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  134 */       psql.setLong(1, a_lAttributeId);
/*  135 */       rs = psql.executeQuery();
/*      */ 
/*  137 */       while (rs.next())
/*      */       {
/*  139 */         ChangeAttributeValueRule rule = new ChangeAttributeValueRule();
/*      */ 
/*  141 */         rule.setId(rs.getLong("ruleId"));
/*  142 */         rule.setName(rs.getString("ruleName"));
/*  143 */         rule.getAttributeRef().setId(a_lAttributeId);
/*  144 */         rule.setEnabled(rs.getBoolean("Enabled"));
/*  145 */         rule.setActionOnAssetId(rs.getLong("ActionOnAssetId"));
/*      */ 
/*  147 */         vecRuleList.add(rule);
/*      */       }
/*      */ 
/*  150 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  154 */       throw new Bn2Exception("AttributeDateRuleManager.getChangeAttributeValueRuleListSQL Exception whilst getting rules from the database : ", e);
/*      */     }
/*      */ 
/*  157 */     return vecRuleList;
/*      */   }
/*      */ 
/*      */   public ChangeAttributeValueRule getChangeAttributeValueRule(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  171 */     String ksMethodName = "getChangeAttributeValueRule";
/*  172 */     ChangeAttributeValueRule rule = null;
/*  173 */     Connection con = null;
/*  174 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  178 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  180 */       String sSQL = "SELECT parentattr.Id parentattrId, parentattr.Label parentattrLabel, childattr.Id childattrId, childattr.Label childattrLabel, attrrule.RuleName ruleName, attrrule.NewAttributeValue, attrrule.Enabled, attrrule.ActionOnAssetId, aoa.Id aoaId, aoa.Name aoaName, aoa.ActionClass aoaActionClass FROM Attribute parentattr, Attribute childattr, ChangeAttributeValueDateRule attrrule LEFT JOIN ActionOnAsset aoa ON aoa.Id=attrrule.ActionOnAssetId WHERE attrrule.AttributeId=parentattr.Id AND attrrule.ChangeAttributeId=childattr.Id AND attrrule.Id=? ";
/*      */ 
/*  203 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  204 */       psql.setLong(1, a_lId);
/*      */ 
/*  206 */       rs = psql.executeQuery();
/*      */ 
/*  208 */       if (rs.next())
/*      */       {
/*  210 */         rule = new ChangeAttributeValueRule();
/*      */ 
/*  212 */         rule.setId(a_lId);
/*  213 */         rule.setName(rs.getString("ruleName"));
/*  214 */         rule.getAttributeRef().setId(rs.getLong("parentattrId"));
/*  215 */         rule.getAttributeRef().setName(rs.getString("parentattrLabel"));
/*  216 */         rule.getAttributeToChange().setId(rs.getLong("childattrId"));
/*  217 */         rule.getAttributeToChange().setName(rs.getString("childattrLabel"));
/*  218 */         rule.setNewValue(rs.getString("NewAttributeValue"));
/*  219 */         rule.setEnabled(rs.getBoolean("Enabled"));
/*      */ 
/*  221 */         if (rs.getLong("ActionOnAssetId") > 0L)
/*      */         {
/*  223 */           ActionOnAsset action = ActionOnAsset.createAction(rs.getString("aoaActionClass"));
/*  224 */           action.setId(rs.getLong("aoaId"));
/*  225 */           action.setName(rs.getString("aoaName"));
/*  226 */           rule.setAction(action);
/*  227 */           rule.setActionOnAssetId(rs.getLong("aoaId"));
/*      */         }
/*      */       }
/*      */ 
/*  231 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  235 */       throw new Bn2Exception("AttributeDateRuleManager.getChangeAttributeValueRuleSQL Exception whilst getting rule from the database : " + e, e);
/*      */     }
/*      */ 
/*  238 */     return rule;
/*      */   }
/*      */ 
/*      */   public void addChangeAttributeValueRule(DBTransaction a_dbTransaction, ChangeAttributeValueRule a_rule)
/*      */     throws Bn2Exception
/*      */   {
/*  251 */     String ksMethodName = "addChangeAttributeValueRule";
/*  252 */     Connection con = null;
/*  253 */     String sSQL = null;
/*  254 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  258 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  261 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*  262 */       long lNewId = 0L;
/*      */ 
/*  265 */       sSQL = "INSERT INTO ChangeAttributeValueDateRule (";
/*      */ 
/*  267 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  269 */         lNewId = sqlGenerator.getUniqueId(con, "ChangeAttributeRuleSeq");
/*  270 */         sSQL = sSQL + "Id, ";
/*      */       }
/*  272 */       sSQL = sSQL + "AttributeId, RuleName, ChangeAttributeId, NewAttributeValue, Enabled, ActionOnAssetId) VALUES (";
/*      */ 
/*  274 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  276 */         sSQL = sSQL + "?,";
/*      */       }
/*      */ 
/*  279 */       sSQL = sSQL + "?,?,?,?,?,?)";
/*      */ 
/*  282 */       psql = con.prepareStatement(sSQL);
/*  283 */       int iCol = 1;
/*  284 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  286 */         psql.setLong(iCol++, lNewId);
/*      */       }
/*  288 */       psql.setLong(iCol++, a_rule.getAttributeRef().getId());
/*  289 */       psql.setString(iCol++, a_rule.getName());
/*  290 */       psql.setLong(iCol++, a_rule.getAttributeToChange().getId());
/*  291 */       psql.setString(iCol++, a_rule.getNewValue());
/*  292 */       psql.setBoolean(iCol++, a_rule.getEnabled());
/*  293 */       DBUtil.setFieldIdOrNull(psql, iCol++, a_rule.getActionOnAssetId());
/*  294 */       psql.executeUpdate();
/*  295 */       psql.close();
/*      */ 
/*  297 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  299 */         lNewId = sqlGenerator.getUniqueId(con, "ChangeAttributeValueDateRule");
/*      */       }
/*  301 */       a_rule.setId(lNewId);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  307 */       throw new Bn2Exception("AttributeDateRuleManager.addChangeAttributeValueRule: Exception occurred: ", e);
/*      */     }
/*      */ 
/*  311 */     BrightDateTime dtNow = BrightDateTime.now();
/*  312 */     scheduleNextChangeAttributeValueRulesTime(a_dbTransaction, dtNow);
/*      */   }
/*      */ 
/*      */   public void updateChangeAttributeValueRule(DBTransaction a_dbTransaction, ChangeAttributeValueRule a_rule)
/*      */     throws Bn2Exception
/*      */   {
/*  325 */     String ksMethodName = "updateChangeAttributeValueRule";
/*  326 */     Connection con = null;
/*  327 */     String sSQL = null;
/*  328 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  332 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  334 */       sSQL = "UPDATE ChangeAttributeValueDateRule SET AttributeId=?, RuleName=?, ChangeAttributeId=?, NewAttributeValue=?, Enabled=?, ActionOnAssetId=? WHERE Id=? ";
/*      */ 
/*  338 */       psql = con.prepareStatement(sSQL);
/*  339 */       int iCol = 1;
/*  340 */       psql.setLong(iCol++, a_rule.getAttributeRef().getId());
/*  341 */       psql.setString(iCol++, a_rule.getName());
/*  342 */       psql.setLong(iCol++, a_rule.getAttributeToChange().getId());
/*  343 */       psql.setString(iCol++, a_rule.getNewValue());
/*  344 */       psql.setBoolean(iCol++, a_rule.getEnabled());
/*  345 */       DBUtil.setFieldIdOrNull(psql, iCol++, a_rule.getActionOnAssetId());
/*  346 */       psql.setLong(iCol++, a_rule.getId());
/*  347 */       psql.executeUpdate();
/*  348 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  353 */       throw new Bn2Exception("AttributeDateRuleManager.updateChangeAttributeValueRule: Exception occurred: ", e);
/*      */     }
/*      */ 
/*  356 */     BrightDateTime dtNow = BrightDateTime.now();
/*  357 */     scheduleNextChangeAttributeValueRulesTime(a_dbTransaction, dtNow);
/*      */   }
/*      */ 
/*      */   public void deleteChangeAttributeValueRule(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  365 */     String ksMethodName = "deleteChangeAttributeValueRule";
/*  366 */     Connection con = null;
/*  367 */     String sSQL = null;
/*  368 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/*  372 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  374 */       sSQL = "DELETE FROM ChangeAttributeValueDateRule WHERE Id=?";
/*  375 */       psql = con.prepareStatement(sSQL);
/*  376 */       psql.setLong(1, a_lId);
/*  377 */       psql.executeUpdate();
/*  378 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  383 */       throw new Bn2Exception("AttributeDateRuleManager.deleteChangeAttributeValueRule: Exception occurred: ", e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void processChangeAttributeValueRules()
/*      */   {
/*  390 */     processChangeAttributeValueRules(false);
/*      */   }
/*      */ 
/*      */   public void processChangeAttributeValueRules(boolean force)
/*      */   {
/*  407 */     String ksMethodName = "processChangeAttributeValueRules";
/*  408 */     DBTransaction dbTransaction = null;
/*  409 */     Connection con = null;
/*  410 */     String sSQL = null;
/*      */ 
/*  413 */     String sMessage = "AttributeDateRuleManager: Processing change attribute value attribute rules.";
/*  414 */     this.m_logger.info(sMessage);
/*      */ 
/*  416 */     BrightDateTime dtThisRunTime = null;
/*      */     try
/*      */     {
/*  421 */       BrightDateTime dtLastRun = getLastRunTime("lastRunCAVRules");
/*      */ 
/*  424 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/*  427 */       dtThisRunTime = setLastRunTime("lastRunCAVRules", dbTransaction);
/*      */ 
/*  429 */       con = dbTransaction.getConnection();
/*      */ 
/*  432 */       sSQL = "SELECT Id FROM ChangeAttributeValueDateRule WHERE Enabled=1 ";
/*      */ 
/*  436 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  437 */       ResultSet rs = psql.executeQuery();
/*      */ 
/*  439 */       while (rs.next())
/*      */       {
/*  441 */         long lRuleId = rs.getLong("Id");
/*      */ 
/*  444 */         List<Long> assets = new ArrayList();
/*      */ 
/*  447 */         DateValueColumns columns = getDateValueColumns(con, "ChangeAttributeValueDateRule", lRuleId);
/*      */         BrightDate dtTriggerDate;
/*  451 */         if ((force) || (dtLastRun.getDate().getTime() < BrightDate.today().getDate().getTime()))
/*      */         {
/*  454 */           dtTriggerDate = BrightDate.today();
/*      */ 
/*  456 */           for (String col : columns.m_sDateColumns)
/*      */           {
/*  458 */             sSQL = "SELECT ass.Id FROM Asset ass INNER JOIN AssetAttributeValues aav ON aav.AssetId = ass.Id WHERE aav." + col + " = ? ";
/*      */ 
/*  464 */             PreparedStatement psql2 = con.prepareStatement(sSQL);
/*  465 */             DBUtil.setFieldDateOrNull(psql2, 1, dtTriggerDate.getDate());
/*  466 */             ResultSet rs2 = psql2.executeQuery();
/*      */ 
/*  468 */             while (rs2.next())
/*      */             {
/*  470 */               assets.add(Long.valueOf(rs2.getLong("Id")));
/*      */             }
/*      */ 
/*  473 */             psql2.close();
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  479 */         BrightDateTime dtTriggerDateUpper = dtThisRunTime;
/*  480 */         BrightDateTime dtTriggerDateLower = dtLastRun;
/*      */ 
/*  482 */         for (String col : columns.m_sDatetimeColumns)
/*      */         {
/*  484 */           sSQL = "SELECT ass.Id FROM Asset ass INNER JOIN AssetAttributeValues aav ON aav.AssetId = ass.Id WHERE (aav." + col + " <= ? AND aav." + col + " > ?) ";
/*      */ 
/*  490 */           PreparedStatement psql2 = con.prepareStatement(sSQL);
/*  491 */           DBUtil.setFieldTimestampOrNull(psql2, 1, dtTriggerDateUpper.getDate());
/*  492 */           DBUtil.setFieldTimestampOrNull(psql2, 2, dtTriggerDateLower.getDate());
/*  493 */           ResultSet rs2 = psql2.executeQuery();
/*      */ 
/*  495 */           while (rs2.next())
/*      */           {
/*  497 */             assets.add(Long.valueOf(rs2.getLong("Id")));
/*      */           }
/*  499 */           psql2.close();
/*      */         }
/*      */ 
/*  503 */         if (assets.size() > 0)
/*      */         {
/*  506 */           ChangeAttributeValueRule rule = getChangeAttributeValueRule(dbTransaction, lRuleId);
/*      */ 
/*  509 */           sMessage = "AttributeDateRuleManager.processChangeAttributeValueRules: Rule=" + rule.getName() + ", Num assets=" + assets.size();
/*  510 */           this.m_logger.info(sMessage);
/*      */ 
/*  513 */           long lAttributeToChangeId = rule.getAttributeToChange().getId();
/*  514 */           Attribute attrToChange = this.m_attributeManager.getAttribute(dbTransaction, lAttributeToChangeId);
/*  515 */           String sNewValue = rule.getNewValue();
/*      */ 
/*  518 */           long lAttributeListValueId = 0L;
/*  519 */           if ((attrToChange.getIsDropdownList()) || (attrToChange.getIsCheckList()))
/*      */           {
/*  522 */             lAttributeListValueId = getIdForListAttributeValue(dbTransaction, lAttributeToChangeId, sNewValue);
/*      */           }
/*      */ 
/*  526 */           for (Long lAssetId : assets)
/*      */           {
/*  529 */             Asset asset = this.m_assetManager.getAsset(dbTransaction, lAssetId.longValue(), null, false, false);
/*      */ 
/*  531 */             Vector vecAttributes = asset.getAttributeValues();
/*  532 */             Iterator itAttributes = vecAttributes.iterator();
/*  533 */             while (itAttributes.hasNext())
/*      */             {
/*  535 */               AttributeValue av = (AttributeValue)itAttributes.next();
/*      */ 
/*  537 */               if (av.getAttribute().getId() == rule.getAttributeToChange().getId())
/*      */               {
/*  540 */                 if (lAttributeListValueId > 0L)
/*      */                 {
/*  542 */                   av.setId(lAttributeListValueId); break;
/*      */                 }
/*      */ 
/*  546 */                 av.setValue(sNewValue);
/*      */ 
/*  548 */                 break;
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*  553 */             if (rule.getAction() != null)
/*      */             {
/*  555 */               rule.getAction().performOnAssetBeforeSave(dbTransaction, asset);
/*      */             }
/*      */ 
/*  559 */             this.m_assetManager.saveAsset(dbTransaction, asset, null, 1L, null, null, false, 0);
/*      */ 
/*  562 */             if (rule.getAction() != null)
/*      */             {
/*  564 */               rule.getAction().performOnAssetAfterSave(dbTransaction, lAssetId.longValue(), 1L);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  570 */           this.m_assetManager.clearAssetCaches();
/*      */         }
/*      */       }
/*      */ 
/*  574 */       psql.close();
/*      */ 
/*  577 */       dbTransaction.commit();
/*  578 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*  579 */       scheduleNextChangeAttributeValueRulesTime(dbTransaction, dtThisRunTime);
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/*  583 */       this.m_logger.error("AttributeDateRuleManager.processChangeAttributeValueRules: SQL Exception: " + sqle, sqle);
/*      */       try { dbTransaction.rollback(); } catch (Exception ee) {
/*  585 */       }dbTransaction = null;
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/*  589 */       this.m_logger.error("AttributeDateRuleManager.processChangeAttributeValueRules: Exception: " + e, e);
/*      */       try { dbTransaction.rollback(); } catch (Exception ee) {
/*  591 */       }dbTransaction = null;
/*      */     }
/*      */     finally
/*      */     {
/*  596 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  600 */           dbTransaction.commit();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  604 */           this.m_logger.error("AttributeDateRuleManager.processChangeAttributeValueRules: Exception whilst trying to close connection: " + e.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private DateValueColumns getDateValueColumns(Connection a_con, String a_sTableName, long a_lRuleId)
/*      */     throws SQLException
/*      */   {
/*  614 */     DateValueColumns columns = new DateValueColumns();
/*      */ 
/*  616 */     List sDateColumns = new ArrayList();
/*  617 */     List sDatetimeColumns = new ArrayList();
/*      */ 
/*  620 */     String sSQL = "SELECT a.AttributeTypeId, a.ValueColumnName FROM " + a_sTableName + " t " + "INNER JOIN Attribute a ON a.Id = t.AttributeId " + "WHERE 1=1 ";
/*      */ 
/*  625 */     if (a_lRuleId > 0L)
/*      */     {
/*  627 */       sSQL = sSQL + "AND t.Id = ?";
/*      */     }
/*      */     else
/*      */     {
/*  631 */       sSQL = sSQL + "AND t.Enabled = ?";
/*      */     }
/*      */ 
/*  634 */     PreparedStatement psql2 = a_con.prepareStatement(sSQL);
/*  635 */     int iCol = 1;
/*      */ 
/*  637 */     if (a_lRuleId > 0L)
/*      */     {
/*  639 */       psql2.setLong(iCol++, a_lRuleId);
/*      */     }
/*      */     else
/*      */     {
/*  643 */       psql2.setBoolean(iCol++, true);
/*      */     }
/*      */ 
/*  646 */     ResultSet rs2 = psql2.executeQuery();
/*      */ 
/*  648 */     while (rs2.next())
/*      */     {
/*  651 */       if (rs2.getString("ValueColumnName") == null)
/*      */         continue;
/*  653 */       if (rs2.getLong("AttributeTypeId") == 3L)
/*      */       {
/*  655 */         sDateColumns.add(rs2.getString("ValueColumnName")); continue;
/*      */       }
/*  657 */       if (rs2.getLong("AttributeTypeId") != 8L)
/*      */         continue;
/*  659 */       sDatetimeColumns.add(rs2.getString("ValueColumnName"));
/*      */     }
/*      */ 
/*  664 */     psql2.close();
/*      */ 
/*  666 */     //DateValueColumns.access$002(columns, sDateColumns);
/*  667 */     //DateValueColumns.access$102(columns, sDatetimeColumns);
/*      */ 
/*  669 */     return columns;
/*      */   }
/*      */ 
/*      */   public void scheduleNextChangeAttributeValueRulesTime(DBTransaction a_dbTransaction, BrightDateTime a_dtAfterThisTime)
/*      */     throws Bn2Exception
/*      */   {
/*  683 */     String ksMethodName = "scheduleNextChangeAttributeValueRulesTime";
/*  684 */     Connection con = null;
/*  685 */     String sSQL = null;
/*      */ 
/*  689 */     BrightDateTime dtNext = BrightDate.tomorrow().getBrightDateTime();
                BrightDateTime dtTriggerDate;
/*      */ 
/*  691 */     if (a_dtAfterThisTime != null)
/*      */     {
/*      */       try
/*      */       {
/*  697 */         con = a_dbTransaction.getConnection();
/*      */ 
/*  700 */         dtTriggerDate = a_dtAfterThisTime;
/*      */ 
/*  702 */         DateValueColumns columns = getDateValueColumns(con, "ChangeAttributeValueDateRule", 0L);
/*      */ 
/*  704 */         for (String col : columns.m_sDatetimeColumns)
/*      */         {
/*  706 */           sSQL = "SELECT MIN(" + col + ") NextRun " + "FROM AssetAttributeValues aav " + "WHERE aav." + col + " > ?";
/*      */ 
/*  710 */           PreparedStatement psql = con.prepareStatement(sSQL);
/*  711 */           DBUtil.setFieldTimestampOrNull(psql, 1, dtTriggerDate.getDate());
/*  712 */           ResultSet rs = psql.executeQuery();
/*      */ 
/*  714 */           if (rs.next())
/*      */           {
/*  716 */             Date dt = rs.getTimestamp("NextRun");
/*      */ 
/*  718 */             if (dt != null)
/*      */             {
/*  721 */               if (dt.getTime() < dtNext.getDate().getTime())
/*      */               {
/*  723 */                 dtNext.setDate(dt);
/*      */               }
/*      */             }
/*      */           }
/*  727 */           psql.close();
/*      */         }
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*      */         
/*  732 */         this.m_logger.error("AttributeDateRuleManager.scheduleNextChangeAttributeValueRulesTime: SQL Exception: " + sqle);
/*  733 */         throw new Bn2Exception("AttributeDateRuleManager.scheduleNextChangeAttributeValueRulesTime: SQL Exception: " + sqle, sqle);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  741 */       synchronized (this.m_oProcessingLock)
/*      */       {
/*  744 */         TimerTask task = new TimerTask()
/*      */         {
/*      */           public void run()
/*      */           {
/*  748 */             AttributeDateRuleManager.this.processChangeAttributeValueRules();
/*      */           }
/*      */         };
/*  753 */         this.m_logger.info("Scheduling change attribute value rules for " + dtNext.getDisplayDateTime());
/*      */ 
/*  755 */         this.m_scheduleManager.schedule(task, dtNext.getDate(), false);
/*      */ 
/*  758 */         if (this.m_changeAttributeValueRulesTask != null)
/*      */         {
/*  760 */           this.m_changeAttributeValueRulesTask.cancel();
/*      */         }
/*      */ 
/*  764 */         this.m_changeAttributeValueRulesTask = task;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (IllegalStateException e)
/*      */     {
/*  770 */       this.m_logger.info("Caught IllegalStateException when setting new Timer in scheduleNextChangeAttributeValueRulesTime: " + e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   private long getIdForListAttributeValue(DBTransaction a_dbTransaction, long a_lAttributeId, String a_sValue)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/*  783 */     Connection con = null;
/*  784 */     String sSQL = null;
/*  785 */     PreparedStatement psql = null;
/*  786 */     con = a_dbTransaction.getConnection();
/*      */ 
/*  788 */     sSQL = "SELECT Id FROM ListAttributeValue WHERE AttributeId=? AND Value LIKE ?";
/*  789 */     psql = con.prepareStatement(sSQL);
/*  790 */     psql.setLong(1, a_lAttributeId);
/*  791 */     psql.setString(2, a_sValue);
/*  792 */     ResultSet rs = psql.executeQuery();
/*      */ 
/*  794 */     long lId = 0L;
/*      */ 
/*  796 */     if (rs.next())
/*      */     {
/*  798 */       lId = rs.getLong("Id");
/*      */     }
/*      */ 
/*  801 */     psql.close();
/*      */ 
/*  803 */     return lId;
/*      */   }
/*      */ 
/*      */   public void initialScheduleRules()
/*      */   {
/*  813 */     String ksMethodName = "initialScheduleRules";
/*  814 */     DBTransaction dbTransaction = null;
/*      */     try
/*      */     {
/*  819 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/*  821 */       BrightDateTime dtNow = BrightDateTime.now();
/*  822 */       scheduleNextChangeAttributeValueRulesTime(dbTransaction, dtNow);
/*  823 */       scheduleNextSendEmailRulesTime(dbTransaction, dtNow);
/*      */     }
/*      */     catch (Bn2Exception e)
/*      */     {
/*  827 */       this.m_logger.error("AttributeDateRuleManager.initialScheduleRules: Exception: " + e);
/*      */     }
/*      */     finally
/*      */     {
/*  832 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/*  836 */           dbTransaction.commit();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  840 */           this.m_logger.error("AttributeDateRuleManager.initialScheduleRules: Exception whilst trying to close connection: " + e.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void processSendEmailRules()
/*      */   {
/*  848 */     processSendEmailRules(false);
/*      */   }
/*      */ 
/*      */   public void processSendEmailRules(boolean force)
/*      */   {
/*  865 */     String ksMethodName = "processSendEmailRules";
/*  866 */     DBTransaction dbTransaction = null;
/*  867 */     Connection con = null;
/*  868 */     String sSQL = null;
/*      */ 
/*  871 */     this.m_logger.debug("AttributeDateRuleManager.processSendEmailRules: Processing send email attribute rules");
/*  872 */     String sMessage = "AttributeDateRuleManager: Processing send email attribute rules.";
/*  873 */     this.m_logger.info(sMessage);
/*      */ 
/*  875 */     BrightDateTime dtThisRunTime = null;
/*      */     try
/*      */     {
/*  880 */       BrightDateTime dtLastRun = getLastRunTime("lastRunSERules");
/*  881 */       this.m_logger.debug("AttributeDateRuleManager.processSendEmailRules: Last run time: " + dtLastRun.getDisplayDate());
/*      */ 
/*  885 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */ 
/*  888 */       dtThisRunTime = setLastRunTime("lastRunSERules", dbTransaction);
/*      */ 
/*  890 */       con = dbTransaction.getConnection();
/*      */ 
/*  893 */       sSQL = "SELECT Id, DaysBefore FROM SendEmailDateRule WHERE Enabled=1 ";
/*      */ 
/*  895 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  896 */       ResultSet rs = psql.executeQuery();
/*      */       SendEmailRule rule;
/*      */       Map hmParams;
/*  898 */       while (rs.next())
/*      */       {
/*  900 */         long lRuleId = rs.getLong("Id");
/*  901 */         int iDaysBefore = rs.getInt("DaysBefore");
/*  902 */         this.m_logger.debug("AttributeDateRuleManager.processSendEmailRules: Found rule id: " + lRuleId + " : Days Before: " + iDaysBefore);
/*      */ 
/*  904 */         DateValueColumns columns = getDateValueColumns(con, "SendEmailDateRule", lRuleId);
/*  905 */         this.m_logger.debug("Columns: " + columns.m_sDateColumns.toString());
/*      */ 
/*  908 */         Set<Long> assetIds = new HashSet();
/*      */ 
/*  914 */         if ((force) || (dtLastRun.getDate().getTime() < BrightDate.today().getDate().getTime()))
/*      */         {
/*  916 */           for (String col : columns.m_sDateColumns)
/*      */           {
/*  919 */             BrightDate dtTriggerDate = BrightDate.todayPlusOffsetDays(iDaysBefore);
/*  920 */             this.m_logger.debug("AttributeDateRuleManager.processSendEmailRules: Trigger date: " + dtTriggerDate.toString());
/*      */ 
/*  922 */             sSQL = "SELECT ass.Id FROM AssetAttributeValues aav INNER JOIN Asset ass ON aav.AssetId = ass.Id WHERE aav." + col + " = ? ";
/*      */ 
/*  927 */             PreparedStatement psql2 = con.prepareStatement(sSQL);
/*      */ 
/*  931 */             DBUtil.setFieldDateOrNull(psql2, 1, dtTriggerDate.getDate());
/*  932 */             ResultSet rs2 = psql2.executeQuery();
/*      */ 
/*  934 */             while (rs2.next())
/*      */             {
/*  936 */               long lAssetId = rs2.getLong("Id");
/*  937 */               this.m_logger.debug("AttributeDateRuleManager.processSendEmailRules: Found asset matching rule with id: " + lAssetId);
/*  938 */               assetIds.add(Long.valueOf(lAssetId));
/*      */             }
/*  940 */             psql2.close();
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  946 */         BrightDateTime dtTriggerDateUpper = BrightDateTime.addOffsetDays(dtThisRunTime, iDaysBefore);
/*  947 */         BrightDateTime dtTriggerDateLower = BrightDateTime.addOffsetDays(dtLastRun, iDaysBefore);
/*      */ 
/*  949 */         for (String col : columns.m_sDatetimeColumns)
/*      */         {
/*  951 */           sSQL = "SELECT ass.Id FROM AssetAttributeValues aav INNER JOIN Asset ass ON aav.AssetId = ass.Id WHERE aav." + col + " <= ? AND aav." + col + " > ?";
/*      */ 
/*  956 */           PreparedStatement psql2 = con.prepareStatement(sSQL);
/*  957 */           DBUtil.setFieldTimestampOrNull(psql2, 1, dtTriggerDateUpper.getDate());
/*  958 */           DBUtil.setFieldTimestampOrNull(psql2, 2, dtTriggerDateLower.getDate());
/*  959 */           ResultSet rs2 = psql2.executeQuery();
/*      */ 
/*  961 */           while (rs2.next())
/*      */           {
/*  963 */             assetIds.add(Long.valueOf(rs2.getLong("Id")));
/*      */           }
/*  965 */           psql2.close();
/*      */         }
/*      */ 
/*  970 */         if (assetIds.size() > 0)
/*      */         {
/*  974 */           rule = getSendEmailRule(dbTransaction, lRuleId);
/*      */ 
/*  977 */           List vecGroups = rule.getGroups();
/*      */ 
/*  980 */           sMessage = "AttributeDateRuleManager.processSendEmailRules: Rule=" + rule.getName() + ", Num assets=" + assetIds.size() + ", Recipients=";
/*      */ 
/*  982 */           this.m_logger.info(sMessage);
/*      */ 
/*  985 */           String sAssetsDescription = createPrintableAssetList(assetIds);
/*      */ 
/*  988 */           hmParams = new HashMap();
/*  989 */           hmParams.put("template", "attribute_rule_email");
/*  990 */           hmParams.put("asset_list", sAssetsDescription);
/*  991 */           hmParams.put("rule_message", rule.getMessage());
/*  992 */           hmParams.put("rule_attribute", rule.getAttributeRef().getName());
/*  993 */           hmParams.put("rule_name", rule.getName());
/*      */           try
/*      */           {
/*  998 */             this.m_emailManager.sendEmailsToAdminAndGroups(hmParams, vecGroups, null, false);
/*      */           }
/*      */           catch (Bn2Exception be)
/*      */           {
/*      */           }
/*      */ 
/* 1007 */           if (rule.getEmailUsersWhoDownloadedAsset())
/*      */           {
/* 1009 */             Map assetUseLogEntriesMap = new HashMap();
/* 1010 */             for (Long assetId : assetIds) {
/* 1011 */               assetUseLogEntriesMap.put(assetId, this.m_usageManager.getAssetUsage(dbTransaction, assetId.longValue()));
/*      */             }
/*      */ /* 1015 */     
                        Map mapUserIdsToAssetDescription = new HashMap();
/*      */              Long assetId;
                        Set assetuselong = assetUseLogEntriesMap.entrySet();
/* 1018 */             for (Iterator itr = assetuselong.iterator(); itr.hasNext();){
                        Map.Entry entry =(Map.Entry) itr.next(); 
/* 1019 */               assetId = (Long)entry.getKey();
/* 1020 */               List<AssetUseLogEntry> assetUseLogEntries = (List)entry.getValue();
/*      */             /* 1023 */     
                          for (AssetUseLogEntry assetUseLogEntry : assetUseLogEntries) {
/* 1024 */                 ABUser user = assetUseLogEntry.getUser();
/*      */ /* 1026 */      if (user == null)
/*      */                 {
/*      */                   continue;
/*      */                 }
/*      */ 
/* 1031 */                 if (!mapUserIdsToAssetDescription.containsKey(user))
/*      */                 {
/* 1033 */                   mapUserIdsToAssetDescription.put(user, createPrintableAssetUse(assetId, assetUseLogEntry));
/*      */                 }
/*      */                 else {
/* 1036 */                   String current = (String)mapUserIdsToAssetDescription.get(user);
/* 1037 */                   mapUserIdsToAssetDescription.put(user, current + createPrintableAssetUse(assetId, assetUseLogEntry));
/*      */                 }
/*      */               }
/*      */             }
/*      */             Set uidasset = mapUserIdsToAssetDescription.entrySet();
                        
/* 1043 */             for (Iterator it =uidasset.iterator(); it.hasNext();) {
                         Map.Entry entry =(Map.Entry) it.next();
/* 1044 */               hmParams = new HashMap();
/* 1045 */               hmParams.put("template", "attribute_rule_email");
/* 1046 */               hmParams.put("rule_message", rule.getMessage());
/* 1047 */               hmParams.put("rule_attribute", rule.getAttributeRef().getName());
/* 1048 */               hmParams.put("rule_name", rule.getName());
/* 1049 */               hmParams.put("asset_list", entry.getValue());
/* 1050 */               hmParams.put("recipients", ((ABUser)entry.getKey()).getEmailAddress());
/*      */               try
/*      */               {
/* 1053 */                 this.m_emailManager.sendTemplatedEmail(hmParams, null, false, LanguageConstants.k_defaultLanguage);
/*      */               }
/*      */               catch (Bn2Exception be)
/*      */               {
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1064 */       psql.close();
/*      */ 
/* 1067 */       dbTransaction.commit();
/* 1068 */       dbTransaction = this.m_transactionManager.getNewTransaction();
/* 1069 */       scheduleNextSendEmailRulesTime(dbTransaction, dtThisRunTime);
/*      */ 
/* 1072 */       this.m_emailManager.processQueueAsynchronously();
/*      */     }
/*      */     catch (SQLException sqle)
/*      */     {
/* 1076 */       this.m_logger.error("AttributeDateRuleManager.processSendEmailRules: SQL Exception: " + sqle);
/*      */       try
/*      */       {
/* 1079 */         dbTransaction.rollback();
/*      */       }
/*      */       catch (Exception ee) {
/*      */       }
/* 1083 */       dbTransaction = null;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1087 */       this.m_logger.error("AttributeDateRuleManager.processSendEmailRules: Bn2Exception: " + e);
/*      */       try
/*      */       {
/* 1090 */         dbTransaction.rollback();
/*      */       }
/*      */       catch (Exception ee) {
/*      */       }
/* 1094 */       dbTransaction = null;
/*      */     }
/*      */     finally
/*      */     {
/* 1099 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/* 1103 */           dbTransaction.commit();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1107 */           this.m_logger.error("AttributeDateRuleManager.processSendEmailRules: Exception whilst trying to close connection: " + e.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void scheduleNextSendEmailRulesTime(DBTransaction a_dbTransaction, BrightDateTime a_dtAfterThisTime)
/*      */     throws Bn2Exception
/*      */   {
/* 1126 */     String ksMethodName = "scheduleNextSendEmailRulesTime";
/* 1127 */     Connection con = null;
/* 1128 */     String sSQL = null;
/*      */ 
/* 1132 */     BrightDateTime dtNext = BrightDate.tomorrow().getBrightDateTime();
/*      */ 
/* 1134 */     if (a_dtAfterThisTime != null)
/*      */     {
/*      */       try
/*      */       {
/* 1139 */         con = a_dbTransaction.getConnection();
/*      */ 
/* 1142 */         sSQL = "SELECT Id, DaysBefore FROM SendEmailDateRule WHERE Enabled=1 ";
/*      */ 
/* 1146 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 1147 */         ResultSet rs = psql.executeQuery();
/*      */         int iDaysBefore;
/*      */         BrightDateTime dtTriggerDate;
/* 1149 */         while (rs.next())
/*      */         {
/* 1151 */           long lRuleId = rs.getLong("Id");
/* 1152 */           iDaysBefore = rs.getInt("DaysBefore");
/*      */ 
/* 1155 */           dtTriggerDate = BrightDateTime.addOffsetDays(a_dtAfterThisTime, iDaysBefore);
/*      */ 
/* 1157 */           DateValueColumns columns = getDateValueColumns(con, "SendEmailDateRule", lRuleId);
/*      */ 
/* 1159 */           for (String col : columns.m_sDatetimeColumns)
/*      */           {
/* 1161 */             sSQL = "SELECT MIN(" + col + ") NextRun " + "FROM AssetAttributeValues aav " + "WHERE aav." + col + " > ?";
/*      */ 
/* 1165 */             PreparedStatement psql2 = con.prepareStatement(sSQL);
/* 1166 */             DBUtil.setFieldTimestampOrNull(psql2, 1, dtTriggerDate.getDate());
/* 1167 */             ResultSet rs2 = psql2.executeQuery();
/*      */ 
/* 1169 */             if (rs2.next())
/*      */             {
/* 1171 */               Date dt = rs2.getTimestamp("NextRun");
/*      */ 
/* 1173 */               if (dt != null)
/*      */               {
/* 1176 */                 BrightDateTime dtToRun = BrightDateTime.addOffsetDays(new BrightDateTime(dt), 0 - iDaysBefore);
/*      */ 
/* 1180 */                 if (dtToRun.getDate().getTime() < dtNext.getDate().getTime())
/*      */                 {
/* 1182 */                   dtNext = dtToRun;
/*      */                 }
/*      */               }
/*      */             }
/* 1186 */             psql2.close();
/*      */           }
/*      */         }
/* 1189 */         psql.close();
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/* 1194 */         this.m_logger.error("AttributeDateRuleManager.scheduleNextSendEmailRulesTime: SQL Exception: " + sqle);
/* 1195 */         throw new Bn2Exception("AttributeDateRuleManager.scheduleNextSendEmailRulesTime: SQL Exception: " + sqle, sqle);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1208 */       synchronized (this.m_oProcessingLock)
/*      */       {
/* 1211 */         TimerTask task = new TimerTask()
/*      */         {
/*      */           public void run() {
/* 1214 */             AttributeDateRuleManager.this.processSendEmailRules();
/*      */           }
/*      */         };
/* 1219 */         this.m_logger.info("Scheduling send mail rules for " + dtNext.getDisplayDateTime());
/*      */ 
/* 1221 */         this.m_scheduleManager.schedule(task, dtNext.getDate(), false);
/*      */ 
/* 1224 */         if (this.m_sendEmailRulesTask != null)
/*      */         {
/* 1226 */           this.m_sendEmailRulesTask.cancel();
/*      */         }
/*      */ 
/* 1230 */         this.m_sendEmailRulesTask = task;
/*      */       }
/*      */     }
/*      */     catch (IllegalStateException e)
/*      */     {
/* 1235 */       this.m_logger.info("Caught IllegalStateException when setting new Timer in scheduleNextSendEmailRulesTime: " + e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addSendEmailRule(DBTransaction a_dbTransaction, SendEmailRule a_rule)
/*      */     throws Bn2Exception
/*      */   {
/* 1250 */     String ksMethodName = "addSendEmailRule";
/* 1251 */     Connection con = null;
/* 1252 */     String sSQL = null;
/* 1253 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/* 1257 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1260 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 1261 */       long lNewId = 0L;
/*      */ 
/* 1264 */       sSQL = "INSERT INTO SendEmailDateRule (";
/*      */ 
/* 1266 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1268 */         lNewId = sqlGenerator.getUniqueId(con, "SendEmailDateRuleSequence");
/* 1269 */         sSQL = sSQL + "Id, ";
/*      */       }
/* 1271 */       sSQL = sSQL + "AttributeId, RuleName, DaysBefore, EmailText, Enabled, EmailAssetDownloaders) VALUES (";
/*      */ 
/* 1273 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1275 */         sSQL = sSQL + "?,";
/*      */       }
/*      */ 
/* 1278 */       sSQL = sSQL + "?,?,?,?,?,?)";
/*      */ 
/* 1280 */       psql = con.prepareStatement(sSQL);
/* 1281 */       int iCol = 1;
/* 1282 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1284 */         psql.setLong(iCol++, lNewId);
/*      */       }
/* 1286 */       psql.setLong(iCol++, a_rule.getAttributeRef().getId());
/* 1287 */       psql.setString(iCol++, a_rule.getName());
/* 1288 */       psql.setLong(iCol++, a_rule.getDaysBefore());
/* 1289 */       psql.setString(iCol++, a_rule.getMessage());
/* 1290 */       psql.setBoolean(iCol++, a_rule.getEnabled());
/* 1291 */       psql.setBoolean(iCol++, a_rule.getEmailUsersWhoDownloadedAsset());
/* 1292 */       psql.executeUpdate();
/* 1293 */       psql.close();
/*      */ 
/* 1295 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1297 */         lNewId = sqlGenerator.getUniqueId(con, "SendEmailDateRule");
/*      */       }
/* 1299 */       a_rule.setId(lNewId);
/*      */ 
/* 1302 */       setGroupsForEmailRule(a_dbTransaction, a_rule);
/*      */ 
/* 1305 */       BrightDateTime dtNow = BrightDateTime.now();
/* 1306 */       scheduleNextSendEmailRulesTime(a_dbTransaction, dtNow);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1311 */       throw new Bn2Exception("AttributeDateRuleManager.addSendEmailRule: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateSendEmailRule(DBTransaction a_dbTransaction, SendEmailRule a_rule)
/*      */     throws Bn2Exception
/*      */   {
/* 1326 */     String ksMethodName = "updateSendEmailRule";
/* 1327 */     Connection con = null;
/* 1328 */     String sSQL = null;
/* 1329 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/* 1333 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1335 */       sSQL = "UPDATE SendEmailDateRule SET AttributeId=?, RuleName=?, DaysBefore=?, EmailText=?, Enabled=?, EmailAssetDownloaders=? WHERE Id=? ";
/*      */ 
/* 1339 */       psql = con.prepareStatement(sSQL);
/* 1340 */       int iCol = 1;
/* 1341 */       psql.setLong(iCol++, a_rule.getAttributeRef().getId());
/* 1342 */       psql.setString(iCol++, a_rule.getName());
/* 1343 */       psql.setLong(iCol++, a_rule.getDaysBefore());
/* 1344 */       psql.setString(iCol++, a_rule.getMessage());
/* 1345 */       psql.setBoolean(iCol++, a_rule.getEnabled());
/* 1346 */       psql.setBoolean(iCol++, a_rule.getEmailUsersWhoDownloadedAsset());
/* 1347 */       psql.setLong(iCol++, a_rule.getId());
/* 1348 */       psql.executeUpdate();
/* 1349 */       psql.close();
/*      */ 
/* 1352 */       setGroupsForEmailRule(a_dbTransaction, a_rule);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1357 */       throw new Bn2Exception("AttributeDateRuleManager.updateSendEmailRule: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1360 */     BrightDateTime dtNow = BrightDateTime.now();
/* 1361 */     scheduleNextSendEmailRulesTime(a_dbTransaction, dtNow);
/*      */   }
/*      */ 
/*      */   public void deleteSendEmailRule(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 1368 */     String ksMethodName = "deleteSendEmailRule";
/* 1369 */     Connection con = null;
/* 1370 */     String sSQL = null;
/* 1371 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/* 1375 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1378 */       sSQL = "DELETE FROM GroupEmailRule WHERE RuleId=?";
/* 1379 */       psql = con.prepareStatement(sSQL);
/* 1380 */       psql.setLong(1, a_lId);
/* 1381 */       psql.executeUpdate();
/* 1382 */       psql.close();
/*      */ 
/* 1385 */       sSQL = "DELETE FROM SendEmailDateRule WHERE Id=?";
/* 1386 */       psql = con.prepareStatement(sSQL);
/* 1387 */       psql.setLong(1, a_lId);
/* 1388 */       psql.executeUpdate();
/* 1389 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1395 */       throw new Bn2Exception("AttributeDateRuleManager.deleteSendEmailRule: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setGroupsForEmailRule(DBTransaction a_dbTransaction, SendEmailRule a_rule)
/*      */     throws SQLException, Bn2Exception
/*      */   {
/* 1415 */     Connection con = null;
/* 1416 */     String sSQL = null;
/* 1417 */     PreparedStatement psql = null;
/* 1418 */     con = a_dbTransaction.getConnection();
/*      */ 
/* 1421 */     sSQL = "DELETE FROM GroupEmailRule WHERE RuleId = ?";
/* 1422 */     psql = con.prepareStatement(sSQL);
/* 1423 */     psql.setLong(1, a_rule.getId());
/* 1424 */     psql.executeUpdate();
/* 1425 */     psql.close();
/*      */ 
/* 1427 */     sSQL = "INSERT INTO GroupEmailRule (RuleId, UserGroupId) VALUES (?, ?)";
/*      */ 
/* 1429 */     psql = con.prepareStatement(sSQL);
/*      */ 
/* 1431 */     Iterator it = a_rule.getGroups().iterator();
/* 1432 */     while (it.hasNext())
/*      */     {
/* 1434 */       StringDataBean group = (StringDataBean)it.next();
/* 1435 */       psql = con.prepareStatement(sSQL);
/* 1436 */       psql.setLong(1, a_rule.getId());
/* 1437 */       psql.setLong(2, group.getId());
/* 1438 */       psql.executeUpdate();
/* 1439 */       psql.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   public List<SendEmailRule> getSendEmailRuleList(DBTransaction a_dbTransaction, long a_lAttributeId)
/*      */     throws Bn2Exception
/*      */   {
/* 1454 */     String ksMethodName = "getSendEmailRuleList";
/* 1455 */     Vector vecRuleList = new Vector();
/* 1456 */     Connection con = null;
/* 1457 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1461 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1463 */       String sSQL = "SELECT attrrule.Id ruleId, attrrule.RuleName ruleName, attrrule.Enabled, attrrule.EmailAssetDownloaders FROM SendEmailDateRule attrrule WHERE attrrule.AttributeId=? ";
/*      */ 
/* 1473 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1474 */       psql.setLong(1, a_lAttributeId);
/* 1475 */       rs = psql.executeQuery();
/*      */ 
/* 1477 */       while (rs.next())
/*      */       {
/* 1479 */         SendEmailRule rule = new SendEmailRule();
/*      */ 
/* 1481 */         rule.setId(rs.getLong("ruleId"));
/* 1482 */         rule.setName(rs.getString("ruleName"));
/* 1483 */         rule.getAttributeRef().setId(a_lAttributeId);
/* 1484 */         rule.setEnabled(rs.getBoolean("Enabled"));
/* 1485 */         rule.setEmailUsersWhoDownloadedAsset(rs.getBoolean("EmailAssetDownloaders"));
/*      */ 
/* 1487 */         vecRuleList.add(rule);
/*      */       }
/*      */ 
/* 1490 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1494 */       throw new Bn2Exception("AttributeDateRuleManager.getSendEmailRuleListSQL Exception whilst getting rules from the database : " + e, e);
/*      */     }
/*      */ 
/* 1497 */     return vecRuleList;
/*      */   }
/*      */ 
/*      */   public SendEmailRule getSendEmailRule(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 1512 */     String ksMethodName = "getSendEmailRule";
/* 1513 */     SendEmailRule rule = null;
/* 1514 */     Connection con = null;
/* 1515 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1519 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1521 */       String sSQL = "SELECT parentattr.Id parentattrId, parentattr.Label parentattrLabel, attrrule.RuleName ruleName, attrrule.DaysBefore, attrrule.EmailText, attrrule.Enabled, attrrule.EmailAssetDownloaders FROM Attribute parentattr, SendEmailDateRule attrrule WHERE attrrule.AttributeId=parentattr.Id AND attrrule.Id=? ";
/*      */ 
/* 1537 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1538 */       psql.setLong(1, a_lId);
/* 1539 */       rs = psql.executeQuery();
/*      */ 
/* 1541 */       if (rs.next())
/*      */       {
/* 1543 */         rule = new SendEmailRule();
/*      */ 
/* 1545 */         rule.setId(a_lId);
/* 1546 */         rule.setName(rs.getString("ruleName"));
/* 1547 */         rule.getAttributeRef().setId(rs.getLong("parentattrId"));
/* 1548 */         rule.getAttributeRef().setName(rs.getString("parentattrLabel"));
/* 1549 */         rule.setMessage(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "EmailText"));
/* 1550 */         rule.setDaysBefore(rs.getLong("DaysBefore"));
/* 1551 */         rule.setEnabled(rs.getBoolean("Enabled"));
/* 1552 */         rule.setEmailUsersWhoDownloadedAsset(rs.getBoolean("EmailAssetDownloaders"));
/*      */ 
/* 1554 */         rs.close();
/* 1555 */         psql.close();
/*      */ 
/* 1558 */         List vecGroups = new Vector();
/* 1559 */         sSQL = "SELECT ug.Id groupId, ug.Name groupName FROM GroupEmailRule ugrule, UserGroup ug WHERE ug.Id = ugrule.UserGroupId AND ugrule.RuleId = ?";
/*      */ 
/* 1563 */         psql = con.prepareStatement(sSQL);
/* 1564 */         psql.setLong(1, a_lId);
/* 1565 */         rs = psql.executeQuery();
/*      */ 
/* 1567 */         while (rs.next())
/*      */         {
/* 1569 */           StringDataBean group = new StringDataBean(rs.getLong("groupId"), rs.getString("groupName"));
/* 1570 */           vecGroups.add(group);
/*      */         }
/* 1572 */         rule.setGroups(vecGroups);
/*      */       }
/*      */ 
/* 1575 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1579 */       throw new Bn2Exception("AttributeDateRuleManager.getSendEmailRuleSQL Exception whilst getting rule from the database : " + e, e);
/*      */     }
/*      */ 
/* 1582 */     return rule;
/*      */   }
/*      */ 
/*      */   private String createPrintableAssetList(Set<Long> a_beans)
/*      */   {
/* 1593 */     String sIds = "";
/*      */ 
/* 1595 */     for (Long id : a_beans)
/*      */     {
/* 1597 */       sIds = sIds + id + " \n";
/*      */     }
/*      */ 
/* 1600 */     return sIds;
/*      */   }
/*      */ 
/*      */   private String createPrintableAssetUse(Long assetId, AssetUseLogEntry a_entry)
/*      */   {
/* 1610 */     String ret = "Asset: " + assetId;
/* 1611 */     ret = ret + ", Downloaded: " + a_entry.getDate().toString();
/* 1612 */     ret = ret + ", Usage: " + a_entry.getDescription();
/*      */ 
/* 1614 */     if ((a_entry.getSecondaryUsageTypes() != null) && (a_entry.getSecondaryUsageTypes().size() > 0)) {
/* 1615 */       ret = ret + " (";
/* 1616 */       boolean firstInList = true;
/* 1617 */       for (String secondaryUsage : a_entry.getSecondaryUsageTypes()) {
/* 1618 */         if (firstInList)
/* 1619 */           firstInList = false;
/*      */         else {
/* 1621 */           ret = ret + ", ";
/*      */         }
/* 1623 */         ret = ret + secondaryUsage;
/*      */       }
/* 1625 */       ret = ret + ")";
/*      */     }
/*      */ 
/* 1628 */     ret = ret + "\n";
/* 1629 */     return ret;
/*      */   }
/*      */ 
/*      */   private BrightDateTime getLastRunTime(String a_sSettingName)
/*      */     throws Bn2Exception
/*      */   {
/* 1642 */     BrightDateTime dtLastRun = BrightDate.yesterday().getBrightDateTime();
/*      */ 
/* 1645 */     String sDateLastRun = this.m_refDataManager.getSystemSetting(a_sSettingName);
/* 1646 */     if (StringUtil.stringIsPopulated(sDateLastRun))
/*      */     {
/* 1648 */       BrightDateTime date = new BrightDateTime();
/* 1649 */       date.setFormTimeFormat("HH:mm:ss");
/*      */ 
/* 1651 */       if ((date.setFormDateTime(sDateLastRun)) && (date.processFormData()))
/*      */       {
/* 1653 */         dtLastRun = date;
/*      */       }
/*      */     }
/*      */ 
/* 1657 */     return dtLastRun;
/*      */   }
/*      */ 
/*      */   private BrightDateTime setLastRunTime(String a_sSettingName, DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 1671 */     BrightDateTime dtThisRunTime = BrightDateTime.now();
/* 1672 */     dtThisRunTime.setDisplayDateTimeFormat("dd/MM/yyyy HH:mm:ss");
/* 1673 */     String sDateNow = dtThisRunTime.getDisplayDateTime();
/* 1674 */     this.m_refDataManager.updateSystemSetting(a_dbTransaction, a_sSettingName, sDateNow);
/*      */ 
/* 1676 */     return dtThisRunTime;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_manager)
/*      */   {
/* 1681 */     this.m_transactionManager = a_manager;
/*      */   }
/*      */ 
/*      */   public void setAttributeManager(AttributeManager manager)
/*      */   {
/* 1686 */     this.m_attributeManager = manager;
/*      */   }
/*      */ 
/*      */   public void setEmailManager(EmailManager a_sEmailManager)
/*      */   {
/* 1691 */     this.m_emailManager = a_sEmailManager;
/*      */   }
/*      */ 
/*      */   public void setScheduleManager(ScheduleManager a_sScheduleManager)
/*      */   {
/* 1696 */     this.m_scheduleManager = a_sScheduleManager;
/*      */   }
/*      */ 
/*      */   public void setRefDataManager(RefDataManager a_manager)
/*      */   {
/* 1701 */     this.m_refDataManager = a_manager;
/*      */   }
/*      */ 
/*      */   public void setAssetManager(IAssetManager a_sAssetManager)
/*      */   {
/* 1706 */     this.m_assetManager = a_sAssetManager;
/*      */   }
/*      */ 
/*      */   public void setUsageManager(UsageManager a_usageManager)
/*      */   {
/* 1711 */     this.m_usageManager = a_usageManager;
/*      */   }
/*      */ 
/*      */   private class DateValueColumns
/*      */   {
/*   87 */     private List<String> m_sDateColumns = new ArrayList();
/*   88 */     private List<String> m_sDatetimeColumns = new ArrayList();
/*      */ 
/*      */     private DateValueColumns()
/*      */     {
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.service.AttributeDateRuleManager
 * JD-Core Version:    0.6.0
 */