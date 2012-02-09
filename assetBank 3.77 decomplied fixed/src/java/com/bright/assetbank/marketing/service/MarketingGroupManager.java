/*     */ package com.bright.assetbank.marketing.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.database.AssetBankSql;
/*     */ import com.bright.assetbank.marketing.bean.MarketingGroup;
/*     */ import com.bright.assetbank.marketing.bean.MarketingGroup.Translation;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class MarketingGroupManager extends Bn2Manager
/*     */ {
/*     */   private static final String c_ksClassName = "MarketingGroupManager";
/*     */ 
/*     */   public void addMarketingGroup(DBTransaction a_transaction, MarketingGroup a_group)
/*     */     throws Bn2Exception
/*     */   {
/*  49 */     String ksMethodName = "addMarketingGroup";
/*  50 */     Connection con = null;
/*     */     try
/*     */     {
/*  54 */       con = a_transaction.getConnection();
/*     */ 
/*  57 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*  58 */       long lNewId = 0L;
/*     */ 
/*  60 */       String sSql = "INSERT INTO MarketingGroup (";
/*     */ 
/*  62 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/*  64 */         lNewId = sqlGenerator.getUniqueId(con, "MarketingGroupSequence");
/*  65 */         sSql = sSql + "Id,";
/*     */       }
/*     */ 
/*  68 */       sSql = sSql + "Name,Description,Purpose,IsHiddenInDefaultLanguage) VALUES (?,?,?,?";
/*     */ 
/*  70 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/*  72 */         sSql = sSql + ",?";
/*     */       }
/*     */ 
/*  75 */       sSql = sSql + ")";
/*     */ 
/*  77 */       int iCol = 1;
/*  78 */       PreparedStatement psql = con.prepareStatement(sSql);
/*     */ 
/*  80 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/*  82 */         psql.setLong(iCol++, lNewId);
/*     */       }
/*     */ 
/*  85 */       psql.setString(iCol++, a_group.getName());
/*  86 */       psql.setString(iCol++, a_group.getDescription());
/*  87 */       psql.setString(iCol++, a_group.getPurpose());
/*  88 */       psql.setBoolean(iCol++, a_group.isHiddenInDefaultLanguage());
/*     */ 
/*  90 */       psql.executeUpdate();
/*     */ 
/*  93 */       if (sqlGenerator.usesAutoincrementFields())
/*     */       {
/*  95 */         lNewId = sqlGenerator.getUniqueId(con, "MarketingGroup");
/*     */       }
/*     */ 
/*  98 */       psql.close();
/*     */ 
/* 100 */       a_group.setId(lNewId);
/*     */ 
/* 103 */       Iterator itTranslations = a_group.getTranslations().iterator();
/* 104 */       while (itTranslations.hasNext())
/*     */       {
/* 106 */         MarketingGroup.Translation translation = (MarketingGroup.Translation)itTranslations.next();
/* 107 */         if (translation.getLanguage().getId() > 0L)
/*     */         {
/* 109 */           saveMarketingGroupTranslation(a_transaction, translation);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 115 */       this.m_logger.error("MarketingGroupManager.addMarketingGroup : SQL Exception : " + e);
/* 116 */       throw new Bn2Exception("MarketingGroupManager.addMarketingGroup : SQL Exception : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void saveMarketingGroupTranslation(DBTransaction a_dbTransaction, MarketingGroup.Translation a_translation)
/*     */     throws Bn2Exception
/*     */   {
/* 128 */     String ksMethodName = "saveMarketingGroupTranslation";
/*     */ 
/* 130 */     Connection con = null;
/* 131 */     PreparedStatement psql = null;
/* 132 */     String sSQL = null;
/*     */     try
/*     */     {
/* 136 */       con = a_dbTransaction.getConnection();
/* 137 */       int iCol = 1;
/*     */ 
/* 139 */       sSQL = "DELETE FROM TranslatedMarketingGroup WHERE MarketingGroupId=? AND LanguageId=?";
/* 140 */       psql = con.prepareStatement(sSQL);
/* 141 */       psql.setLong(iCol++, a_translation.getMarketingGroupId());
/* 142 */       psql.setLong(iCol++, a_translation.getLanguage().getId());
/* 143 */       psql.executeUpdate();
/* 144 */       psql.close();
/*     */ 
/* 146 */       iCol = 1;
/* 147 */       sSQL = "INSERT INTO TranslatedMarketingGroup (Name,Description,MarketingGroupId,LanguageId) VALUES (?,?,?,?)";
/* 148 */       psql = con.prepareStatement(sSQL);
/* 149 */       psql.setString(iCol++, a_translation.getName());
/* 150 */       psql.setString(iCol++, a_translation.getDescription());
/* 151 */       psql.setLong(iCol++, a_translation.getMarketingGroupId());
/* 152 */       psql.setLong(iCol++, a_translation.getLanguage().getId());
/* 153 */       psql.executeUpdate();
/* 154 */       psql.close();
/*     */     }
/*     */     catch (SQLException sqe)
/*     */     {
/* 158 */       this.m_logger.error("MarketingGroupManager.saveMarketingGroupTranslation - " + sqe);
/* 159 */       throw new Bn2Exception("MarketingGroupManager.saveMarketingGroupTranslation", sqe);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void saveMarketingGroup(DBTransaction a_transaction, MarketingGroup a_group)
/*     */     throws Bn2Exception
/*     */   {
/* 171 */     String ksMethodName = "saveMarketingGroup";
/* 172 */     Connection con = null;
/*     */     try
/*     */     {
/* 176 */       con = a_transaction.getConnection();
/*     */ 
/* 178 */       String sSql = "UPDATE MarketingGroup SET Name=?, Description=?, Purpose=?, IsHiddenInDefaultLanguage=? WHERE Id=?";
/*     */ 
/* 180 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 181 */       psql.setString(1, a_group.getName());
/* 182 */       psql.setString(2, a_group.getDescription());
/* 183 */       psql.setString(3, a_group.getPurpose());
/* 184 */       psql.setBoolean(4, a_group.isHiddenInDefaultLanguage());
/* 185 */       psql.setLong(5, a_group.getId());
/*     */ 
/* 187 */       psql.executeUpdate();
/*     */ 
/* 190 */       Iterator itTranslations = a_group.getTranslations().iterator();
/* 191 */       while (itTranslations.hasNext())
/*     */       {
/* 193 */         MarketingGroup.Translation translation = (MarketingGroup.Translation)itTranslations.next();
/* 194 */         if (translation.getLanguage().getId() > 0L)
/*     */         {
/* 196 */           saveMarketingGroupTranslation(a_transaction, translation);
/*     */         }
/*     */       }
/*     */ 
/* 200 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 204 */       this.m_logger.error("MarketingGroupManager.saveMarketingGroup : SQL Exception : " + e);
/* 205 */       throw new Bn2Exception("MarketingGroupManager.saveMarketingGroup : SQL Exception : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteMarketingGroup(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 217 */     String ksMethodName = "deleteMarketingGroup";
/* 218 */     Connection con = null;
/* 219 */     String sSql = null;
/* 220 */     PreparedStatement psql = null;
/*     */     try
/*     */     {
/* 223 */       con = a_transaction.getConnection();
/*     */ 
/* 225 */       sSql = "DELETE FROM UserInMarketingGroup WHERE MarketingGroupId=?";
/* 226 */       psql = con.prepareStatement(sSql);
/* 227 */       psql.setLong(1, a_lId);
/* 228 */       psql.executeUpdate();
/*     */ 
/* 230 */       sSql = "DELETE FROM TranslatedMarketingGroup WHERE MarketingGroupId=?";
/* 231 */       psql = con.prepareStatement(sSql);
/* 232 */       psql.setLong(1, a_lId);
/* 233 */       psql.executeUpdate();
/*     */ 
/* 235 */       sSql = "DELETE FROM MarketingGroup WHERE Id=?";
/* 236 */       psql = con.prepareStatement(sSql);
/* 237 */       psql.setLong(1, a_lId);
/* 238 */       psql.executeUpdate();
/*     */ 
/* 240 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 244 */       this.m_logger.error("MarketingGroupManager.deleteMarketingGroup : SQL Exception : " + e);
/* 245 */       throw new Bn2Exception("MarketingGroupManager.deleteMarketingGroup : SQL Exception : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public MarketingGroup getMarketingGroup(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 258 */     String ksMethodName = "getMarketingGroup";
/* 259 */     Connection con = null;
/* 260 */     String sSql = null;
/* 261 */     PreparedStatement psql = null;
/* 262 */     MarketingGroup group = null;
/*     */     try
/*     */     {
/* 266 */       con = a_transaction.getConnection();
/*     */ 
/* 268 */       sSql = "SELECT mg.Id mgId, mg.Name mgName, mg.Description mgDescription, mg.Purpose mgPurpose, mg.IsHiddenInDefaultLanguage mgHiddenInDefaultLanguage, tmg.Name tmgName, tmg.Description tmgDescription, l.Id lId, l.Name lName, l.Code lCode, COUNT(uimg.UserID) numUsers FROM MarketingGroup mg LEFT JOIN UserInMarketingGroup uimg ON mg.Id = uimg.MarketingGroupId LEFT JOIN TranslatedMarketingGroup tmg ON tmg.MarketingGroupId = mg.Id LEFT JOIN Language l ON l.Id = tmg.LanguageId WHERE mg.Id=? GROUP BY mg.Id, mg.Name, mg.Description, mg.Purpose, mg.IsHiddenInDefaultLanguage, tmg.Name, tmg.Description, l.Id, l.Code, l.Name ";
/*     */ 
/* 288 */       psql = con.prepareStatement(sSql);
/* 289 */       psql.setLong(1, a_lId);
/* 290 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 292 */       while (rs.next())
/*     */       {
/* 294 */         if (group == null)
/*     */         {
/* 296 */           group = new MarketingGroup();
/* 297 */           group.setId(a_lId);
/* 298 */           group.setName(rs.getString("mgName"));
/* 299 */           group.setDescription(rs.getString("mgDescription"));
/* 300 */           group.setPurpose(rs.getString("mgPurpose"));
/* 301 */           group.setHiddenInDefaultLanguage(rs.getBoolean("mgHiddenInDefaultLanguage"));
/* 302 */           group.setNumUsers(rs.getInt("numUsers"));
/*     */         }
/*     */ 
/* 305 */         addMarketingGroupTranslation(group, rs);
/*     */       }
/*     */ 
/* 308 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 312 */       this.m_logger.error("MarketingGroupManager.getMarketingGroup : SQL Exception : " + e);
/* 313 */       throw new Bn2Exception("MarketingGroupManager.getMarketingGroup : SQL Exception : " + e, e);
/*     */     }
/* 315 */     return group;
/*     */   }
/*     */ 
/*     */   private void addMarketingGroupTranslation(MarketingGroup a_group, ResultSet a_rs) throws SQLException
/*     */   {
/* 320 */     if (a_rs.getLong("lId") > 0L)
/*     */     {
/*     */       //MarketingGroup tmp18_17 = a_group; tmp18_17.getClass(); MarketingGroup.Translation translation = new MarketingGroup.Translation(tmp18_17, new Language(a_rs.getLong("lId"), a_rs.getString("lName"), a_rs.getString("lCode")));
/* 323 */       MarketingGroup.Translation translation = a_group.new Translation(new Language(a_rs.getLong("lId"), a_rs.getString("lName"), a_rs.getString("lCode")));
                translation.setName(a_rs.getString("tmgName"));
/* 324 */       translation.setDescription(a_rs.getString("tmgDescription"));
/* 325 */       a_group.getTranslations().add(translation);
/*     */     }
/*     */   }
/*     */ 
/*     */   public List getMarketingGroups(DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/* 337 */     return getMarketingGroups(a_transaction, null);
/*     */   }
/*     */ 
/*     */   public List getMarketingGroups(DBTransaction a_transaction, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 349 */     List groups = null;
/* 350 */     String ksMethodName = "getMarketingGroups";
/* 351 */     Connection con = null;
/* 352 */     String sSql = null;
/* 353 */     PreparedStatement psql = null;
/*     */     try
/*     */     {
/* 357 */       con = a_transaction.getConnection();
/*     */ 
/* 359 */       sSql = "SELECT mg.Id mgId, mg.Name mgName, mg.Description mgDescription, mg.Purpose mgPurpose, mg.IsHiddenInDefaultLanguage mgHiddenInDefaultLanguage, tmg.Name tmgName, tmg.Description tmgDescription, l.Id lId, l.Name lName, l.Code lCode, COUNT(uimg.MarketingGroupId) numUsers FROM MarketingGroup mg LEFT JOIN UserInMarketingGroup uimg ON mg.Id = uimg.MarketingGroupId LEFT JOIN TranslatedMarketingGroup tmg ON tmg.MarketingGroupId = mg.Id LEFT JOIN Language l ON l.Id = tmg.LanguageId ";
/*     */ 
/* 377 */       if (a_language != null)
/*     */       {
/* 379 */         if (a_language.equals(LanguageConstants.k_defaultLanguage))
/*     */         {
/* 381 */           sSql = sSql + "WHERE mg.IsHiddenInDefaultLanguage=? ";
/*     */         }
/*     */         else
/*     */         {
/* 385 */           sSql = sSql + "WHERE l.Id=" + a_language.getId() + " AND " + SQLGenerator.getInstance().getLengthFunction("tmg.Name") + " > 0 ";
/*     */         }
/*     */       }
/*     */ 
/* 389 */       sSql = sSql + "GROUP BY mg.Id, mg.Name, mg.Description, mg.Purpose, mg.IsHiddenInDefaultLanguage, tmg.Name, tmg.Description, l.Id, l.Code, l.Name ";
/*     */ 
/* 391 */       psql = con.prepareStatement(sSql);
/*     */ 
/* 393 */       if (LanguageConstants.k_defaultLanguage.equals(a_language))
/*     */       {
/* 395 */         psql.setBoolean(1, false);
/*     */       }
/*     */ 
/* 398 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 400 */       groups = new ArrayList();
/* 401 */       long lLastGroupId = 0L;
/* 402 */       MarketingGroup group = null;
/*     */ 
/* 404 */       while (rs.next())
/*     */       {
/* 406 */         if (lLastGroupId != rs.getLong("mgId"))
/*     */         {
/* 408 */           group = new MarketingGroup();
/* 409 */           group.setId(rs.getLong("mgId"));
/* 410 */           group.setName(rs.getString("mgName"));
/* 411 */           group.setDescription(rs.getString("mgDescription"));
/* 412 */           group.setPurpose(rs.getString("mgPurpose"));
/* 413 */           group.setHiddenInDefaultLanguage(rs.getBoolean("mgHiddenInDefaultLanguage"));
/* 414 */           group.setNumUsers(rs.getInt("numUsers"));
/* 415 */           groups.add(group);
/*     */ 
/* 417 */           lLastGroupId = group.getId();
/*     */         }
/*     */ 
/* 420 */         addMarketingGroupTranslation(group, rs);
/*     */       }
/*     */ 
/* 423 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 427 */       this.m_logger.error("MarketingGroupManager.getMarketingGroups : SQL Exception : " + e);
/* 428 */       throw new Bn2Exception("MarketingGroupManager.getMarketingGroups : SQL Exception : " + e, e);
/*     */     }
/* 430 */     return groups;
/*     */   }
/*     */ 
/*     */   public List getMarketingGroupIdsForUser(DBTransaction a_transaction, long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/* 441 */     List groupIds = null;
/* 442 */     String ksMethodName = "getMarketingGroupIdsForUser";
/* 443 */     Connection con = null;
/* 444 */     String sSql = null;
/* 445 */     PreparedStatement psql = null;
/*     */     try
/*     */     {
/* 449 */       con = a_transaction.getConnection();
/*     */ 
/* 451 */       sSql = "SELECT MarketingGroupId FROM UserInMarketingGroup WHERE UserId=?";
/*     */ 
/* 453 */       psql = con.prepareStatement(sSql);
/* 454 */       psql.setLong(1, a_lUserId);
/* 455 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 457 */       groupIds = new ArrayList();
/*     */ 
/* 459 */       while (rs.next())
/*     */       {
/* 461 */         groupIds.add(String.valueOf(rs.getLong("MarketingGroupId")));
/*     */       }
/*     */ 
/* 464 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 468 */       this.m_logger.error("MarketingGroupManager.getMarketingGroupIdsForUser : SQL Exception : " + e);
/* 469 */       throw new Bn2Exception("MarketingGroupManager.getMarketingGroupIdsForUser : SQL Exception : " + e, e);
/*     */     }
/* 471 */     return groupIds;
/*     */   }
/*     */ 
/*     */   public void setMarketingGroupIdsForUser(DBTransaction a_transaction, long a_lUserId, List a_MarketingGroupIds)
/*     */     throws Bn2Exception
/*     */   {
/* 483 */     String ksMethodName = "setMarketingGroupIdsForUser";
/* 484 */     Connection con = null;
/* 485 */     String sSql = null;
/* 486 */     PreparedStatement psql = null;
/*     */     try
/*     */     {
/* 490 */       con = a_transaction.getConnection();
/*     */ 
/* 492 */       sSql = "DELETE FROM UserInMarketingGroup WHERE UserId=?";
/*     */ 
/* 494 */       psql = con.prepareStatement(sSql);
/* 495 */       psql.setLong(1, a_lUserId);
/* 496 */       psql.executeUpdate();
/*     */ 
/* 498 */       Iterator itGroups = a_MarketingGroupIds.iterator();
/*     */ 
/* 500 */       while (itGroups.hasNext())
/*     */       {
/* 502 */         sSql = "INSERT INTO UserInMarketingGroup (UserId,MarketingGroupId) VALUES (?,?)";
/*     */ 
/* 504 */         psql = con.prepareStatement(sSql);
/* 505 */         psql.setLong(1, a_lUserId);
/* 506 */         psql.setLong(2, Long.valueOf((String)itGroups.next()).longValue());
/* 507 */         psql.executeUpdate();
/*     */       }
/*     */ 
/* 510 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 514 */       this.m_logger.error("MarketingGroupManager.setMarketingGroupIdsForUser : SQL Exception : " + e);
/* 515 */       throw new Bn2Exception("MarketingGroupManager.setMarketingGroupIdsForUser : SQL Exception : " + e, e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.service.MarketingGroupManager
 * JD-Core Version:    0.6.0
 */