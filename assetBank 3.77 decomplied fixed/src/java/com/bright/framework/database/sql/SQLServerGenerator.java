/*     */ package com.bright.framework.database.sql;
/*     */ 
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.exception.SQLStatementException;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class SQLServerGenerator extends BaseApplicationSql
/*     */   implements ApplicationSql
/*     */ {
/*     */   public long getUniqueId(Connection a_cCon, String a_sIdentifier)
/*     */     throws SQLException
/*     */   {
/*  68 */     long lId = -1L;
/*     */ 
/*  71 */     String sSql = "SELECT @@IDENTITY AS NewId";
/*  72 */     PreparedStatement psql = a_cCon.prepareStatement(sSql);
/*  73 */     ResultSet rs = psql.executeQuery();
/*     */ 
/*  75 */     if (rs.next())
/*     */     {
/*  77 */       lId = rs.getLong("NewId");
/*     */     }
/*     */ 
/*  80 */     psql.close();
/*     */ 
/*  82 */     return lId;
/*     */   }
/*     */ 
/*     */   public String getTrimFunction(String a_sIdentifier)
/*     */   {
/*  99 */     return "LTRIM(RTRIM(" + a_sIdentifier + "))";
/*     */   }
/*     */ 
/*     */   public String getLengthFunction(String a_sIdentifier)
/*     */   {
/* 116 */     return "LEN(" + a_sIdentifier + ")";
/*     */   }
/*     */ 
/*     */   public String getFieldNotEmptyStatement(String a_sIdentifier)
/*     */   {
/* 136 */     return a_sIdentifier + " IS NOT NULL AND SUBSTRING(" + a_sIdentifier + ",1,1)<>''";
/*     */   }
/*     */ 
/*     */   public String setRowLimit(String a_sSelectStatement, int a_iLimit)
/*     */   {
/* 144 */     return a_sSelectStatement.replaceFirst("SELECT ", "SELECT TOP " + a_iLimit + " ");
/*     */   }
/*     */ 
/*     */   public String getOrderByForLargeTextField(String a_sIdentifier, int a_iCharsRequired)
/*     */   {
/* 152 */     return " CAST(" + a_sIdentifier + " AS " + (FrameworkSettings.getDatabaseUsesNationalCharacterTypes() ? "N" : "") + "Varchar(" + a_iCharsRequired + ")) ";
/*     */   }
/*     */ 
/*     */   public String getLengthUpperBoundFunctionForLargeTextField(String a_sIdentifier)
/*     */   {
/* 158 */     return "DATALENGTH(" + a_sIdentifier + ")";
/*     */   }
/*     */ 
/*     */   public String getAsSelectForUpdate(String a_sSelectStatement)
/*     */   {
/* 174 */     return a_sSelectStatement;
/*     */   }
/*     */ 
/*     */   public void prepareForIdentityInsert(Connection a_con, String a_sTable)
/*     */     throws SQLException
/*     */   {
/* 183 */     Statement stmt = a_con.createStatement();
/* 184 */     stmt.execute("SET IDENTITY_INSERT " + a_sTable + " ON");
/* 185 */     stmt.close();
/*     */   }
/*     */ 
/*     */   public void postIdentityInsert(Connection a_con, String a_sTable, String a_sSeq)
/*     */     throws SQLException
/*     */   {
/* 193 */     Statement stmt = a_con.createStatement();
/* 194 */     stmt.execute("SET IDENTITY_INSERT " + a_sTable + " OFF");
/* 195 */     stmt.close();
/*     */   }
/*     */ 
/*     */   public String getNumberFieldOverThreshold(String a_sField, int a_iThreshold)
/*     */   {
/* 210 */     String sSQL = "CASE WHEN " + a_sField + " <= " + a_iThreshold + " THEN -1 " + "WHEN " + a_sField + " > " + a_iThreshold + " THEN " + a_sField + " " + "END AS " + a_sField;
/*     */ 
/* 214 */     return sSQL;
/*     */   }
/*     */ 
/*     */   public boolean getHasErrorsWithLongTextComparisons() {
/* 218 */     return true;
/*     */   }
/*     */ 
/*     */   public void testAlterSchema(Connection a_con)
/*     */     throws SQLStatementException
/*     */   {
/* 230 */     String sSql = null;
/*     */     try
/*     */     {
/* 236 */       sSql = "If OBJECT_ID('TempAlterTest') IS NOT NULL DROP TABLE TempAlterTest";
/*     */ 
/* 238 */       PreparedStatement psql = a_con.prepareStatement(sSql);
/* 239 */       psql.execute();
/* 240 */       psql.close();
/*     */ 
/* 242 */       sSql = "CREATE TABLE [TempAlterTest] ([TestCreateCol] Varchar(200) NOT NULL, Primary Key (TestCreateCol) )";
/*     */ 
/* 247 */       psql = a_con.prepareStatement(sSql);
/* 248 */       psql.execute();
/* 249 */       psql.close();
/*     */ 
/* 251 */       sSql = "ALTER TABLE TempAlterTest ADD TestAlterCol Varchar(200)";
/*     */ 
/* 253 */       psql = a_con.prepareStatement(sSql);
/* 254 */       psql.execute();
/* 255 */       psql.close();
/*     */ 
/* 257 */       sSql = "ALTER TABLE TempAlterTest DROP COLUMN TestAlterCol";
/*     */ 
/* 259 */       psql = a_con.prepareStatement(sSql);
/* 260 */       psql.execute();
/* 261 */       psql.close();
/*     */ 
/* 263 */       sSql = "DROP TABLE TempAlterTest";
/*     */ 
/* 265 */       psql = a_con.prepareStatement(sSql);
/* 266 */       psql.execute();
/* 267 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 271 */       throw new SQLStatementException(sSql, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void renameTable(Connection a_con, String a_sOldTableName, String a_sNewTableName)
/*     */     throws SQLException
/*     */   {
/* 281 */     this.m_logger.info("Renaming table " + a_sOldTableName + " to " + a_sNewTableName);
/* 282 */     CallableStatement cstmt = a_con.prepareCall("{ call sp_RENAME (?,?) }");
/* 283 */     cstmt.setString(1, a_sOldTableName);
/* 284 */     cstmt.setString(2, a_sNewTableName);
/* 285 */     cstmt.executeUpdate();
/* 286 */     cstmt.close();
/*     */   }
/*     */ 
/*     */   public String getBlobSizeStatement(String a_sFieldName)
/*     */   {
/* 291 */     return "DATALENGTH(" + a_sFieldName + ")";
/*     */   }
/*     */ 
/*     */   public String getDateOnlyFunction(String a_sDateTimeFieldName)
/*     */   {
/* 302 */     return "CONVERT(DATETIME, FLOOR(CONVERT(FLOAT," + a_sDateTimeFieldName + ")))";
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.sql.SQLServerGenerator
 * JD-Core Version:    0.6.0
 */