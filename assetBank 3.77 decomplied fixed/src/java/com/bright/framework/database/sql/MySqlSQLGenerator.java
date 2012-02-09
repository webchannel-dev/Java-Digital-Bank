/*     */ package com.bright.framework.database.sql;
/*     */ 
/*     */ import com.bright.framework.database.bean.ForeignKey;
/*     */ import com.bright.framework.database.exception.SQLStatementException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public class MySqlSQLGenerator extends BaseApplicationSql
/*     */   implements ApplicationSql
/*     */ {
/*     */   public long getUniqueId(Connection a_cCon, String a_sIdentifier)
/*     */     throws SQLException
/*     */   {
/*  67 */     long lId = -1L;
/*     */ 
/*  70 */     String sSql = "SELECT LAST_INSERT_ID() AS NewId ";
/*  71 */     PreparedStatement psql = a_cCon.prepareStatement(sSql);
/*  72 */     ResultSet rs = psql.executeQuery();
/*     */ 
/*  74 */     if (rs.next())
/*     */     {
/*  76 */       lId = rs.getLong("NewId");
/*     */     }
/*     */ 
/*  79 */     psql.close();
/*     */ 
/*  81 */     return lId;
/*     */   }
/*     */ 
/*     */   public String getNullCheckStatement(String a_sIdentifier)
/*     */   {
/*  98 */     return "ISNULL(" + a_sIdentifier + ")";
/*     */   }
/*     */ 
/*     */   public String getLowerCaseFunction(String a_sIdentifier)
/*     */   {
/* 115 */     return "LCASE(" + a_sIdentifier + ")";
/*     */   }
/*     */ 
/*     */   public String getUpperCaseFunction(String a_sIdentifier)
/*     */   {
/* 128 */     return "UCASE(" + a_sIdentifier + ")";
/*     */   }
/*     */ 
/*     */   public boolean getSupportsOnDuplicateKeyUpdate()
/*     */   {
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */   public String setRowLimit(String a_sSelectStatement, int a_iLimit)
/*     */   {
/* 142 */     return a_sSelectStatement = a_sSelectStatement + " LIMIT " + a_iLimit;
/*     */   }
/*     */ 
/*     */   public String getOrderByForLargeTextField(String a_sIdentifier, int a_iCharsRequired)
/*     */   {
/* 150 */     return " " + a_sIdentifier + " ";
/*     */   }
/*     */ 
/*     */   public void prepareForIdentityInsert(Connection a_con, String a_sTable)
/*     */     throws SQLException
/*     */   {
/*     */   }
/*     */ 
/*     */   public synchronized void postIdentityInsert(Connection a_con, String a_sTable, String a_sSeq)
/*     */     throws SQLException
/*     */   {
/* 163 */     PreparedStatement psql = a_con.prepareStatement("SELECT MAX(Id)+1 newId FROM " + a_sTable);
/* 164 */     ResultSet rs = psql.executeQuery();
/* 165 */     rs.next();
/* 166 */     long lNewId = rs.getLong("newId");
/* 167 */     psql.close();
/*     */ 
/* 169 */     String sSQL = "ALTER TABLE " + a_sTable + " AUTO_INCREMENT = " + lNewId;
/* 170 */     psql = a_con.prepareStatement(sSQL);
/* 171 */     psql.executeUpdate();
/* 172 */     psql.close();
/*     */   }
/*     */ 
/*     */   public String getNumberFieldOverThreshold(String a_sField, int a_iThreshold)
/*     */   {
/* 188 */     return "IF(" + a_sField + ">" + a_iThreshold + ", " + a_sField + ", -1) " + a_sField;
/*     */   }
/*     */ 
/*     */   public void testAlterSchema(Connection con)
/*     */     throws SQLStatementException
/*     */   {
/* 200 */     String sSql = null;
/*     */     try
/*     */     {
/* 206 */       sSql = "DROP TABLE IF EXISTS TempAlterTest";
/*     */ 
/* 208 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 209 */       psql.execute();
/* 210 */       psql.close();
/*     */ 
/* 212 */       sSql = "CREATE TABLE TempAlterTest ( TestCreateCol Varchar(200) NOT NULL, Primary Key (TestCreateCol)) ENGINE = InnoDB ROW_FORMAT = Default";
/*     */ 
/* 220 */       psql = con.prepareStatement(sSql);
/* 221 */       psql.execute();
/* 222 */       psql.close();
/*     */ 
/* 224 */       sSql = "ALTER TABLE TempAlterTest ADD COLUMN TestAlterCol Varchar(200)";
/*     */ 
/* 226 */       psql = con.prepareStatement(sSql);
/* 227 */       psql.execute();
/* 228 */       psql.close();
/*     */ 
/* 230 */       sSql = "ALTER TABLE TempAlterTest DROP COLUMN TestAlterCol";
/*     */ 
/* 232 */       psql = con.prepareStatement(sSql);
/* 233 */       psql.execute();
/* 234 */       psql.close();
/*     */ 
/* 236 */       sSql = "DROP TABLE TempAlterTest";
/*     */ 
/* 238 */       psql = con.prepareStatement(sSql);
/* 239 */       psql.execute();
/* 240 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 244 */       throw new SQLStatementException(sSql, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected String getDropForeignKeySQL(ForeignKey a_fk)
/*     */   {
/* 251 */     return "ALTER TABLE " + a_fk.getFKTableName() + " DROP FOREIGN KEY " + a_fk.getFKConstraintName();
/*     */   }
/*     */ 
/*     */   public boolean isRowSizeTooLarge(SQLException e)
/*     */   {
/* 257 */     return e.getErrorCode() == 1118;
/*     */   }
/*     */ 
/*     */   public String getBlobSizeStatement(String a_sFieldName)
/*     */   {
/* 262 */     return "LENGTH(" + a_sFieldName + ")";
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.sql.MySqlSQLGenerator
 * JD-Core Version:    0.6.0
 */