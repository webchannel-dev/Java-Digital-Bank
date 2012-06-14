/*     */ package com.bright.assetbank.commercialoption.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.commercialoption.bean.CommercialOption;
/*     */ import com.bright.assetbank.commercialoption.bean.CommercialOption.Translation;
/*     */ import com.bright.assetbank.database.AssetBankSql;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class CommercialOptionManager extends Bn2Manager
/*     */ {
/*     */   private static final String c_ksClassName = "CommercialOptionManager";
/*     */ 
/*     */   public Vector getCommercialOptionList(DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  61 */     String ksMethodName = "getCommercialOptionList";
/*  62 */     Connection con = null;
/*  63 */     String sSQL = null;
/*  64 */     PreparedStatement psql = null;
/*  65 */     Vector vecCommOpts = new Vector();
/*     */     try
/*     */     {
/*  69 */       con = a_dbTransaction.getConnection();
/*     */ 
/*  71 */       sSQL = "SELECT co.Id, co.Name, co.Description, co.Price, co.Terms, co.IsDisabled, tco.Name, tco.Description, tco.Terms, l.Id lId, l.Code lCode, l.Name lName FROM CommercialOption co LEFT JOIN TranslatedCommercialOption tco ON tco.CommercialOptionId = co.Id LEFT JOIN Language l ON l.Id = tco.LanguageId WHERE co.IsDisabled='0' ORDER BY co.Name ";
/*     */ 
/*  80 */       psql = con.prepareStatement(sSQL);
/*  81 */       ResultSet rs = psql.executeQuery();
/*  82 */       long lLastSeenId = 0L;
/*  83 */       CommercialOption commOpt = null;
/*     */ 
/*  85 */       while (rs.next())
/*     */       {
/*  87 */         if (lLastSeenId != rs.getLong("co.Id"))
/*     */         {
/*  89 */           commOpt = new CommercialOption();
/*     */ 
/*  91 */           commOpt.setId(rs.getLong("co.Id"));
/*  92 */           commOpt.setName(rs.getString("co.Name"));
/*  93 */           commOpt.setDescription(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "co.Description"));
/*  94 */           commOpt.getPrice().setAmount(rs.getLong("co.Price"));
/*  95 */           commOpt.setTerms(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "co.Terms"));
/*  96 */           commOpt.setDisabled(rs.getBoolean("co.IsDisabled"));
/*     */ 
/*  98 */           vecCommOpts.add(commOpt);
/*     */ 
/* 100 */           lLastSeenId = commOpt.getId();
/*     */         }
/*     */ 
/* 104 */         buildTranslation(rs, commOpt);
/*     */       }
/* 106 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 112 */       throw new Bn2Exception("CommercialOptionManager.getCommercialOptionList: Exception occurred: " + e, e);
/*     */     }
/*     */ 
/* 115 */     return vecCommOpts;
/*     */   }
/*     */ 
/*     */   private void buildTranslation(ResultSet a_rs, CommercialOption a_commOpt)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 121 */     if (a_rs.getLong("lId") > 0L)
/*     */     {
/*     */       //CommercialOption tmp18_17 = a_commOpt; tmp18_17.getClass(); CommercialOption.Translation translation = new CommercialOption.Translation(tmp18_17, new Language(a_rs.getLong("lId"), a_rs.getString("lName"), a_rs.getString("lCode")));
                //CommercialOption tmp18_17 = a_commOpt; 
               // tmp18_17.getClass(); 
                CommercialOption.Translation translation = a_commOpt.new Translation(new Language(a_rs.getLong("lId"), a_rs.getString("lName"), a_rs.getString("lCode")));
/* 124 */       translation.setDescription(SQLGenerator.getInstance().getStringFromLargeTextField(a_rs, "tco.Description"));
/* 125 */       translation.setName(a_rs.getString("tco.Name"));
/* 126 */       translation.setTerms(SQLGenerator.getInstance().getStringFromLargeTextField(a_rs, "tco.Terms"));
/* 127 */       a_commOpt.getTranslations().add(translation);
/*     */     }
/*     */   }
/*     */ 
/*     */   public CommercialOption getCommercialOption(DBTransaction a_dbTransaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 150 */     String ksMethodName = "getCommercialOption";
/* 151 */     Connection con = null;
/* 152 */     String sSQL = null;
/* 153 */     PreparedStatement psql = null;
/* 154 */     CommercialOption commOpt = null;
/*     */     try
/*     */     {
/* 158 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 160 */       sSQL = "SELECT co.Id, co.Name, co.Description, co.Price, co.Terms, co.IsDisabled, tco.Name, tco.Description, tco.Terms, l.Id lId, l.Code lCode, l.Name lName FROM CommercialOption co LEFT JOIN TranslatedCommercialOption tco ON tco.CommercialOptionId = co.Id LEFT JOIN Language l ON l.Id = tco.LanguageId WHERE co.Id = ? AND co.IsDisabled='0' ";
/*     */ 
/* 167 */       psql = con.prepareStatement(sSQL);
/* 168 */       psql.setLong(1, a_lId);
/* 169 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 171 */       while (rs.next())
/*     */       {
/* 173 */         if (commOpt == null)
/*     */         {
/* 175 */           commOpt = new CommercialOption();
/*     */ 
/* 177 */           commOpt.setId(rs.getLong("co.Id"));
/* 178 */           commOpt.setName(rs.getString("co.Name"));
/* 179 */           commOpt.setDescription(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "co.Description"));
/* 180 */           commOpt.getPrice().setAmount(rs.getLong("co.Price"));
/* 181 */           commOpt.setTerms(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "co.Terms"));
/* 182 */           commOpt.setDisabled(rs.getBoolean("co.IsDisabled"));
/*     */         }
/*     */ 
/* 186 */         buildTranslation(rs, commOpt);
/*     */       }
/*     */ 
/* 189 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 195 */       throw new Bn2Exception("CommercialOptionManager.getCommercialOption: Exception occurred: " + e, e);
/*     */     }
/*     */ 
/* 198 */     return commOpt;
/*     */   }
/*     */ 
/*     */   public void saveCommercialOption(DBTransaction a_dbTransaction, CommercialOption a_commOpt)
/*     */     throws Bn2Exception
/*     */   {
/* 218 */     long lId = a_commOpt.getId();
/*     */ 
/* 220 */     if (lId > 0L)
/*     */     {
/* 222 */       updateCommercialOption(a_dbTransaction, a_commOpt);
/*     */     }
/*     */     else
/*     */     {
/* 226 */       newCommercialOption(a_dbTransaction, a_commOpt);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void newCommercialOption(DBTransaction a_dbTransaction, CommercialOption a_commOpt)
/*     */     throws Bn2Exception
/*     */   {
/* 246 */     String ksMethodName = "newCommercialOption";
/* 247 */     Connection con = null;
/* 248 */     String sSQL = null;
/* 249 */     PreparedStatement psql = null;
/* 250 */     long lNewId = 0L;
/*     */     try
/*     */     {
/* 254 */       con = a_dbTransaction.getConnection();
/* 255 */       sSQL = "INSERT INTO CommercialOption (";
/*     */ 
/* 257 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/* 258 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 260 */         lNewId = sqlGenerator.getUniqueId(con, "CommercialOptionSequence");
/* 261 */         sSQL = sSQL + "Id, ";
/*     */       }
/* 263 */       sSQL = sSQL + "IsDisabled, Name, Description, Price, Terms) VALUES ('0', ";
/*     */ 
/* 265 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 267 */         sSQL = sSQL + "?, ";
/*     */       }
/* 269 */       sSQL = sSQL + "?,?,?,?)";
/*     */ 
/* 271 */       psql = con.prepareStatement(sSQL);
/* 272 */       int iCol = 1;
/* 273 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 275 */         psql.setLong(iCol++, lNewId);
/*     */       }
/*     */ 
/* 278 */       psql.setString(iCol++, a_commOpt.getName());
/* 279 */       psql.setString(iCol++, a_commOpt.getDescription());
/* 280 */       psql.setLong(iCol++, a_commOpt.getPrice().getAmount());
/* 281 */       psql.setString(iCol++, a_commOpt.getTerms());
/*     */ 
/* 283 */       psql.executeUpdate();
/* 284 */       psql.close();
/*     */ 
/* 286 */       if (sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 288 */         lNewId = sqlGenerator.getUniqueId(con, "CommercialOption");
/*     */       }
/* 290 */       a_commOpt.setId(lNewId);
/*     */ 
/* 292 */       insertTranslations(a_commOpt, con);
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 297 */       throw new Bn2Exception("CommercialOptionManager.newCommercialOption: Exception occurred: " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void insertTranslations(CommercialOption a_commOpt, Connection a_con)
/*     */     throws SQLException
/*     */   {
/* 306 */     Iterator itTranslations = a_commOpt.getTranslations().iterator();
/* 307 */     while (itTranslations.hasNext())
/*     */     {
/* 309 */       CommercialOption.Translation translation = (CommercialOption.Translation)itTranslations.next();
/*     */ 
/* 311 */       if (translation.getLanguage().getId() > 0L)
/*     */       {
/* 313 */         String sSQL = "INSERT INTO TranslatedCommercialOption (CommercialOptionId,LanguageId,Name,Description,Terms) VALUES (?,?,?,?,?)";
/* 314 */         PreparedStatement psql = a_con.prepareStatement(sSQL);
/* 315 */         psql.setLong(1, a_commOpt.getId());
/* 316 */         psql.setLong(2, translation.getLanguage().getId());
/* 317 */         psql.setString(3, translation.getName());
/* 318 */         psql.setString(4, translation.getDescription());
/* 319 */         psql.setString(5, translation.getTerms());
/* 320 */         psql.executeUpdate();
/* 321 */         psql.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateCommercialOption(DBTransaction a_dbTransaction, CommercialOption a_commOpt)
/*     */     throws Bn2Exception
/*     */   {
/* 342 */     String ksMethodName = "updateCommercialOption";
/* 343 */     Connection con = null;
/* 344 */     String sSQL = null;
/* 345 */     PreparedStatement psql = null;
/*     */     try
/*     */     {
/* 349 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 351 */       sSQL = "UPDATE CommercialOption SET Name = ?, Description = ?, Price = ?, Terms = ? WHERE Id = ? ";
/*     */ 
/* 355 */       psql = con.prepareStatement(sSQL);
/* 356 */       int iCol = 1;
/*     */ 
/* 358 */       psql.setString(iCol++, a_commOpt.getName());
/* 359 */       psql.setString(iCol++, a_commOpt.getDescription());
/* 360 */       psql.setLong(iCol++, a_commOpt.getPrice().getAmount());
/* 361 */       psql.setString(iCol++, a_commOpt.getTerms());
/*     */ 
/* 363 */       psql.setLong(iCol++, a_commOpt.getId());
/*     */ 
/* 365 */       psql.executeUpdate();
/* 366 */       psql.close();
/*     */ 
/* 368 */       sSQL = "DELETE FROM TranslatedCommercialOption WHERE CommercialOptionId=?";
/* 369 */       psql = con.prepareStatement(sSQL);
/* 370 */       psql.setLong(1, a_commOpt.getId());
/* 371 */       psql.executeUpdate();
/* 372 */       psql.close();
/*     */ 
/* 374 */       insertTranslations(a_commOpt, con);
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 379 */       throw new Bn2Exception("CommercialOptionManager.updateCommercialOption: Exception occurred: " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteCommercialOption(DBTransaction a_dbTransaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 399 */     String ksMethodName = "deleteCommercialOption";
/* 400 */     Connection con = null;
/* 401 */     PreparedStatement psql = null;
/*     */     try
/*     */     {
/* 405 */       con = a_dbTransaction.getConnection();
/*     */ 
/* 407 */       String sSQL = "SELECT * FROM CommercialOptionPurchase WHERE CommercialOptionId=?";
/* 408 */       psql = con.prepareStatement(sSQL);
/* 409 */       psql.setLong(1, a_lId);
/*     */ 
/* 411 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 413 */       if (rs.next())
/*     */       {
/* 415 */         sSQL = "UPDATE CommercialOption SET IsDisabled = 1 WHERE Id = ? ";
/*     */       }
/*     */       else
/*     */       {
/* 421 */         sSQL = "DELETE FROM TranslatedCommercialOption WHERE CommercialOptionId=?";
/* 422 */         psql = con.prepareStatement(sSQL);
/* 423 */         psql.setLong(1, a_lId);
/* 424 */         psql.executeUpdate();
/* 425 */         psql.close();
/*     */ 
/* 427 */         sSQL = "DELETE FROM CommercialOption WHERE Id=?";
/*     */       }
/*     */ 
/* 430 */       psql = con.prepareStatement(sSQL);
/* 431 */       psql.setLong(1, a_lId);
/* 432 */       psql.executeUpdate();
/* 433 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 439 */       throw new Bn2Exception("CommercialOptionManager.deleteCommercialOption: Exception occurred: " + e, e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.commercialoption.service.CommercialOptionManager
 * JD-Core Version:    0.6.0
 */