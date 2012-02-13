/*     */ package com.bright.framework.database.sql;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.database.bean.ForeignKey;
/*     */ import com.bright.framework.io.InputStreamConsumer;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Properties;
/*     */ import java.util.Random;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public abstract class BaseApplicationSql
/*     */   implements ApplicationSql
/*     */ {
/*  56 */   protected Log m_logger = GlobalApplication.getInstance().getLogger();
/*     */ 
/*  58 */   private static final Random c_random = new Random();
/*     */ 
/*     */   public String getLowerCaseFunction(String a_sIdentifier)
/*     */   {
/*  74 */     return "LOWER(" + a_sIdentifier + ")";
/*     */   }
/*     */ 
/*     */   public String getUpperCaseFunction(String a_sIdentifier)
/*     */   {
/*  86 */     return "UPPER(" + a_sIdentifier + ")";
/*     */   }
/*     */ 
/*     */   public String getTrimFunction(String a_sIdentifier)
/*     */   {
/* 103 */     return "TRIM(" + a_sIdentifier + ")";
/*     */   }
/*     */ 
/*     */   public String getNullCheckStatement(String a_sIdentifier)
/*     */   {
/* 120 */     return a_sIdentifier + " IS NULL";
/*     */   }
/*     */ 
/*     */   public String getFieldNotEmptyStatement(String a_sIdentifier)
/*     */   {
/* 137 */     return a_sIdentifier + " IS NOT NULL AND " + a_sIdentifier + "<>''";
/*     */   }
/*     */ 
/*     */   public String getLengthFunction(String a_sIdentifier)
/*     */   {
/* 154 */     return "LENGTH(" + a_sIdentifier + ")";
/*     */   }
/*     */ 
/*     */   public String getRandomOrderByStatement()
/*     */   {
/* 170 */     return "RAND()";
/*     */   }
/*     */ 
/*     */   public boolean getSupportsOnDuplicateKeyUpdate()
/*     */   {
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean usesAutoincrementFields()
/*     */   {
/* 192 */     return true;
/*     */   }
/*     */ 
/*     */   public String getStringFromLargeTextField(ResultSet a_rs, String a_sColumnName)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 203 */     return a_rs.getString(a_sColumnName);
/*     */   }
/*     */ 
/*     */   public String getLengthUpperBoundFunctionForLargeTextField(String a_sIdentifier)
/*     */   {
/* 208 */     return getLengthFunction(a_sIdentifier);
/*     */   }
/*     */ 
/*     */   public String getInitialEmptyBlobValue()
/*     */   {
/* 213 */     return "NULL";
/*     */   }
/*     */ 
/*     */   public void readBlob(Connection a_con, String a_sSql, Object[] a_args, int[] a_argTypes, String a_sBlobColumnName, InputStreamConsumer a_consumer)
/*     */     throws SQLException, IOException
/*     */   {
/* 235 */     InputStream is = null;
/*     */     try
/*     */     {
/* 239 */       PreparedStatement psql = a_con.prepareStatement(a_sSql);
/* 240 */       if (a_args != null)
/*     */       {
/* 242 */         for (int i = 0; i < a_args.length; i++)
/*     */         {
/* 244 */           psql.setObject(i + 1, a_args[i], a_argTypes[i]);
/*     */         }
/*     */       }
/*     */ 
/* 248 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 250 */       if (rs.next())
/*     */       {
/* 252 */         is = rs.getBinaryStream(a_sBlobColumnName);
/* 253 */         if (is != null)
/*     */         {
/* 255 */           is = new BufferedInputStream(is);
/* 256 */           a_consumer.consume(is);
/*     */         }
/*     */       }
/* 259 */       psql.close();
/*     */     }
/*     */     finally
/*     */     {
/* 263 */       IOUtils.closeQuietly(is);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setBlobData(PreparedStatement a_psql, InputStream a_data, String a_sTableName, String a_sBlobColumnName, String a_sRowConditionColumnName, Object a_rowConditionId, int a_iParamNo, long a_lFileLen)
/*     */     throws IOException, SQLException
/*     */   {
/* 280 */     a_psql.setBinaryStream(a_iParamNo, a_data, (int)a_lFileLen);
/*     */   }
/*     */ 
/*     */   public String getAsSelectForUpdate(String a_sSelectStatement)
/*     */   {
/* 290 */     return a_sSelectStatement + " FOR UPDATE";
/*     */   }
/*     */ 
/*     */   public boolean getHasErrorsWithLongTextComparisons()
/*     */   {
/* 295 */     return false;
/*     */   }
/*     */ 
/*     */   public String getCharInValuesClause(String a_sIdentifier, String a_sValues)
/*     */   {
/* 309 */     String sClause = a_sIdentifier + " IN (" + addQuotes(a_sValues) + ")";
/* 310 */     return sClause;
/*     */   }
/*     */ 
/*     */   protected String addQuotes(String a_sValues)
/*     */   {
/* 320 */     String sRet = "";
/*     */ 
/* 322 */     if (a_sValues != null)
/*     */     {
/* 325 */       String sTemp = a_sValues.replace(" ", "");
/*     */ 
/* 328 */       String[] asTerms = sTemp.split(",");
/*     */ 
/* 331 */       if (asTerms != null)
/*     */       {
/* 333 */         for (int i = 0; i < asTerms.length; i++)
/*     */         {
/* 335 */           if (i > 0)
/*     */           {
/* 337 */             sRet = sRet + ",";
/*     */           }
/*     */ 
/* 340 */           sRet = sRet + "'" + asTerms[i] + "'";
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 345 */     return sRet;
/*     */   }
/*     */ 
/*     */   public void setupConnectionProperties(Properties a_properties)
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getDefaultSchemaName(Connection a_con)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/*     */     long lRand;
/* 365 */     synchronized (c_random)
/*     */     {
/* 367 */       lRand = c_random.nextLong();
/*     */     }
/*     */ 
/* 373 */     String sTableName = "TEST" + Long.toHexString(lRand).toUpperCase();
/*     */ 
/* 375 */     String sSQL = "CREATE TABLE " + sTableName + " (ID INTEGER)";
/*     */ 
/* 377 */     PreparedStatement psql = a_con.prepareStatement(sSQL);
/* 378 */     psql.executeUpdate();
/* 379 */     psql.close();
/*     */ 
/* 381 */     String sSchema = null;
/* 382 */     boolean bFoundSchema = false;
/*     */     try
/*     */     {
/* 385 */       DatabaseMetaData metaData = a_con.getMetaData();
/* 386 */       String sCatalog = a_con.getCatalog();
/* 387 */       ResultSet rs = metaData.getTables(sCatalog, null, sTableName, null);
/* 388 */       if (rs.next())
/*     */       {
/* 390 */         sSchema = rs.getString("TABLE_SCHEM");
/* 391 */         bFoundSchema = true;
/*     */       }
/*     */ 
/* 395 */       if (!bFoundSchema)
/*     */       {
/* 397 */         throw new Bn2Exception("Couldn't determine schema name");
/*     */       }
            /*     */
            /* 400 */
            String str1 = sSchema;
            /*     */
     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/*     */        // String str1;
/* 406 */         sSQL = "DROP TABLE " + sTableName;
/* 407 */         psql = a_con.prepareStatement(sSQL);
/* 408 */         psql.executeUpdate();
/* 409 */         psql.close();
/*     */       }
/*     */       catch (SQLException e)
/*     */       {
/* 413 */         this.m_logger.error("Couldn't drop dummy test table " + sTableName, e);
/*     */       }
                return sSchema;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getCatalog(Connection a_con)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 428 */     return a_con.getCatalog();
/*     */   }
/*     */ 
/*     */   public String canonicaliseTableName(String a_sTableName)
/*     */     throws SQLException
/*     */   {
/* 439 */     return a_sTableName;
/*     */   }
/*     */ 
/*     */   public void dropAllImportedForeignKeys(Connection a_con, String a_sCatalog, String a_sSchemaName, String a_sTableName) throws SQLException
/*     */   {
/* 444 */     DatabaseMetaData metaData = a_con.getMetaData();
/* 445 */     ResultSet rs = metaData.getImportedKeys(a_sCatalog, a_sSchemaName, canonicaliseTableName(a_sTableName));
/* 446 */     Collection<ForeignKey> fks = buildForeignKeysFromResultSet(a_sCatalog, a_sSchemaName, rs);
/* 447 */     rs.close();
/*     */ 
/* 449 */     for (ForeignKey fk : fks)
/*     */     {
/* 451 */       dropForeignKey(a_con, fk);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dropAllExportedForeignKeys(Connection a_con, String a_sCatalog, String a_sSchemaName, String a_sTableName) throws SQLException
/*     */   {
/* 457 */     DatabaseMetaData metaData = a_con.getMetaData();
/* 458 */     ResultSet rs = metaData.getExportedKeys(a_sCatalog, a_sSchemaName, canonicaliseTableName(a_sTableName));
/* 459 */     Collection<ForeignKey> fks = buildForeignKeysFromResultSet(a_sCatalog, a_sSchemaName, rs);
/* 460 */     rs.close();
/*     */ 
/* 462 */     for (ForeignKey fk : fks)
/*     */     {
/* 464 */       dropForeignKey(a_con, fk);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected static Collection<ForeignKey> buildForeignKeysFromResultSet(String a_sCatalog, String a_sSchemaName, ResultSet a_rs)
/*     */     throws SQLException
/*     */   {
/* 471 */     Collection fks = new ArrayList();
/* 472 */     while (a_rs.next())
/*     */     {
/* 474 */       ForeignKey fk = new ForeignKey(a_sCatalog, a_sSchemaName, a_rs.getString("FK_NAME"), a_rs.getString("FKTABLE_NAME"), a_rs.getString("FKCOLUMN_NAME"), a_rs.getString("PKTABLE_NAME"), a_rs.getString("PKCOLUMN_NAME"));
/*     */ 
/* 481 */       fks.add(fk);
/*     */     }
/* 483 */     return fks;
/*     */   }
/*     */ 
/*     */   public void dropAllForeignKeys(Connection a_con, String a_sCatalog, String a_sSchemaName, String a_sTableName)
/*     */     throws SQLException
/*     */   {
/* 491 */     dropAllImportedForeignKeys(a_con, a_sCatalog, a_sSchemaName, a_sTableName);
/* 492 */     dropAllExportedForeignKeys(a_con, a_sCatalog, a_sSchemaName, a_sTableName);
/*     */   }
/*     */ 
/*     */   public void dropForeignKey(Connection a_con, ForeignKey a_fk) throws SQLException
/*     */   {
/* 497 */     String sSQL = getDropForeignKeySQL(a_fk);
/* 498 */     this.m_logger.info("Dropping foreign key using SQL: " + sSQL);
/* 499 */     PreparedStatement psql = a_con.prepareStatement(sSQL);
/* 500 */     psql.executeUpdate();
/* 501 */     psql.close();
/*     */   }
/*     */ 
/*     */   protected String getDropForeignKeySQL(ForeignKey a_fk)
/*     */   {
/* 506 */     return "ALTER TABLE " + a_fk.getFKTableName() + " DROP CONSTRAINT " + a_fk.getFKConstraintName();
/*     */   }
/*     */ 
/*     */   public void renameTable(Connection a_con, String a_sOldTableName, String a_sNewTableName)
/*     */     throws SQLException
/*     */   {
/* 514 */     String sSQL = getRenameTableSQL(a_sOldTableName, a_sNewTableName);
/* 515 */     this.m_logger.info("Renaming table using SQL: " + sSQL);
/* 516 */     PreparedStatement psql = a_con.prepareStatement(sSQL);
/* 517 */     psql.executeUpdate();
/* 518 */     psql.close();
/*     */   }
/*     */ 
/*     */   protected String getRenameTableSQL(String a_sOldTableName, String a_sNewTableName)
/*     */   {
/* 523 */     return "ALTER TABLE " + a_sOldTableName + " RENAME TO " + a_sNewTableName;
/*     */   }
/*     */ 
/*     */   public String getIdInStatement(Vector<Long> a_vecIds, String a_sFieldName)
/*     */   {
/* 532 */     String sInStatement = "";
/* 533 */     if ((a_vecIds != null) && (a_vecIds.size() > 0))
/*     */     {
/* 535 */       sInStatement = sInStatement + a_sFieldName + " IN (";
/* 536 */       for (Long longId : a_vecIds)
/*     */       {
/* 538 */         sInStatement = sInStatement + String.valueOf(longId) + ",";
/*     */       }
/*     */ 
/* 542 */       sInStatement = sInStatement.substring(0, sInStatement.length() - 1);
/* 543 */       sInStatement = sInStatement + ") ";
/*     */     }
/* 545 */     return sInStatement;
/*     */   }
/*     */ 
/*     */   public boolean isRowSizeTooLarge(SQLException e)
/*     */   {
/* 550 */     return false;
/*     */   }
/*     */ 
/*     */   public String getDateOnlyFunction(String a_sDateTimeFieldName)
/*     */   {
/* 561 */     return "DATE(" + a_sDateTimeFieldName + ")";
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.sql.BaseApplicationSql
 * JD-Core Version:    0.6.0
 */