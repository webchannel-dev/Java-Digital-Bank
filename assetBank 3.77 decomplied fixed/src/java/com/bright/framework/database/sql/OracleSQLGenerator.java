/*     */ package com.bright.framework.database.sql;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.exception.SQLStatementException;
/*     */ import com.bright.framework.database.util.DBUtil;
/*     */ import com.bright.framework.io.InputStreamConsumer;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import oracle.jdbc.OracleResultSet;
/*     */ import oracle.sql.BLOB;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ public class OracleSQLGenerator extends BaseApplicationSql
/*     */   implements ApplicationSql
/*     */ {
/*     */   private static final int k_iBufSize = 200;
/*     */   private static final int k_iMaxTextSize = 4000;
/*     */ 
/*     */   public boolean usesAutoincrementFields()
/*     */   {
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */   public long getUniqueId(Connection a_cCon, String a_sIdentifier)
/*     */     throws SQLException
/*     */   {
/* 106 */     long lId = -1L;
/*     */ 
/* 109 */     String sSql = "SELECT " + a_sIdentifier + ".nextval AS NewId FROM dual";
/* 110 */     PreparedStatement psql = a_cCon.prepareStatement(sSql);
/* 111 */     ResultSet rs = psql.executeQuery();
/*     */ 
/* 113 */     if (rs.next())
/*     */     {
/* 115 */       lId = rs.getLong("NewId");
/*     */     }
/*     */ 
/* 118 */     psql.close();
/*     */ 
/* 120 */     return lId;
/*     */   }
/*     */ 
/*     */   public String getRandomOrderByStatement()
/*     */   {
/* 137 */     return "DBMS_RANDOM.RANDOM";
/*     */   }
/*     */ 
/*     */   public String getStringFromLargeTextField(ResultSet a_rs, String a_sColumnName)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 149 */     String sValue = null;
/* 150 */     Reader reader = a_rs.getCharacterStream(a_sColumnName);
/*     */     try
/*     */     {
/* 154 */       if (reader != null)
/*     */       {
/* 156 */         StringBuffer output = new StringBuffer();
/* 157 */         char[] buff = new char['È'];
/*     */ 
/* 159 */         int total = 0;
/*     */         int count;
/* 161 */         while (((count = reader.read(buff, 0, 200)) != -1) && (total < 4000))
/*     */         {
/* 163 */           output.append(buff, 0, count);
/* 164 */           total += count;
/*     */         }
/* 166 */         sValue = output.toString();
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 171 */       throw new Bn2Exception("IOException in OracleSQLGenerator.getStringFromLargeTextField() for column: " + a_sColumnName, e);
/*     */     }
/*     */     finally
/*     */     {
/* 175 */       IOUtils.closeQuietly(reader);
/*     */     }
/*     */ 
/* 178 */     return sValue;
/*     */   }
/*     */ 
/*     */   public String getInitialEmptyBlobValue()
/*     */   {
/* 183 */     return "empty_blob()";
/*     */   }
/*     */ 
/*     */   public void readBlob(Connection a_con, String a_sSql, Object[] a_args, int[] a_argTypes, String a_sBlobColumnName, InputStreamConsumer a_consumer)
/*     */     throws SQLException, IOException
/*     */   {
/* 208 */     a_con = DBUtil.unwrapConnection(a_con);
/*     */ 
/* 210 */     InputStream is = null;
/*     */     try
/*     */     {
/* 214 */       PreparedStatement psql = a_con.prepareStatement(a_sSql);
/* 215 */       if (a_args != null)
/*     */       {
/* 217 */         for (int i = 0; i < a_args.length; i++)
/*     */         {
/* 219 */           psql.setObject(i + 1, a_args[i], a_argTypes[i]);
/*     */         }
/*     */       }
/*     */ 
/* 223 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 225 */       if (rs.next())
/*     */       {
/* 227 */         BLOB blob = ((OracleResultSet)rs).getBLOB(a_sBlobColumnName);
/* 228 */         if (blob != null)
/*     */         {
/* 230 */           is = blob.getBinaryStream();
/* 231 */           if (is != null)
/*     */           {
/* 233 */             is = new BufferedInputStream(is);
/* 234 */             a_consumer.consume(is);
/*     */           }
/*     */         }
/*     */       }
/* 238 */       psql.close();
/*     */     }
/*     */     finally
/*     */     {
/* 242 */       IOUtils.closeQuietly(is);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setBlobData(PreparedStatement a_psql, InputStream a_data, String a_sTableName, String a_sBlobColumnName, String a_sRowConditionColumnName, Object a_rowConditionId, int a_iParamNo, long a_lFileLen)
/*     */     throws IOException, SQLException
/*     */   {
/* 259 */     Connection con = a_psql.getConnection();
/* 260 */     con = DBUtil.unwrapConnection(con);
/*     */ 
/* 262 */     BLOB blob = null;
/* 263 */     String sSql = "SELECT " + a_sBlobColumnName + " FROM " + a_sTableName + " WHERE " + a_sRowConditionColumnName + "=? FOR UPDATE";
/*     */ 
/* 265 */     PreparedStatement selectForUpdateStatement = con.prepareStatement(sSql);
/*     */     try
/*     */     {
/* 269 */       if ((a_rowConditionId instanceof String))
/*     */       {
/* 271 */         selectForUpdateStatement.setString(1, (String)a_rowConditionId);
/*     */       }
/* 273 */       else if ((a_rowConditionId instanceof Long))
/*     */       {
/* 275 */         selectForUpdateStatement.setLong(1, ((Long)a_rowConditionId).longValue());
/*     */       }
/*     */       else
/*     */       {
/* 279 */         throw new IllegalArgumentException("Parameter a_rowConditionId (Object) can only be either String or Long");
/*     */       }
/*     */ 
/* 282 */       ResultSet rs = selectForUpdateStatement.executeQuery();
/* 283 */       if (rs.next())
/*     */       {
/* 285 */         blob = ((OracleResultSet)rs).getBLOB(a_sBlobColumnName);
/*     */       }
/*     */       else
/*     */       {
/* 289 */         throw new IllegalArgumentException("Cannot find a row using " + a_sTableName + "." + a_sRowConditionColumnName + " = " + a_rowConditionId);
/*     */       }
/*     */ 
/* 292 */       blob.open(12);
/* 293 */       OutputStream os = blob.getBinaryOutputStream();
/*     */ 
/* 295 */       byte[] buffer = new byte[blob.getBufferSize()];
/* 296 */       int count = 0;
/* 297 */       for (int n = 0; -1 != (n = a_data.read(buffer)); )
/*     */       {
/* 299 */         os.write(buffer, 0, n);
/* 300 */         count += n;
/*     */       }
/*     */ 
/* 303 */       os.flush();
/* 304 */       os.close();
/* 305 */       blob.close();
/*     */ 
/* 307 */       a_psql.setBlob(a_iParamNo, blob);
/*     */     }
/*     */     finally
/*     */     {
/* 311 */       if (selectForUpdateStatement != null)
/*     */       {
/* 313 */         selectForUpdateStatement.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String setRowLimit(String a_sSelectStatement, int a_iLimit)
/*     */   {
/* 324 */     return "SELECT * FROM (" + a_sSelectStatement + ") WHERE ROWNUM <= " + a_iLimit;
/*     */   }
/*     */ 
/*     */   public String getOrderByForLargeTextField(String a_sIdentifier, int a_iCharsRequired)
/*     */   {
/* 332 */     return " CAST(" + a_sIdentifier + " AS " + (FrameworkSettings.getDatabaseUsesNationalCharacterTypes() ? "N" : "") + "Varchar2(" + a_iCharsRequired + ")) ";
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
/* 346 */     PreparedStatement psql = a_con.prepareStatement("SELECT MAX(Id) maxId FROM " + a_sTable);
/* 347 */     ResultSet rs = psql.executeQuery();
/* 348 */     rs.next();
/* 349 */     long lMaxId = rs.getLong("maxId");
/* 350 */     psql.close();
/*     */ 
/* 352 */     psql = a_con.prepareStatement("SELECT " + a_sSeq + ".NEXTVAL AS tempId FROM dual");
/* 353 */     rs = psql.executeQuery();
/* 354 */     rs.next();
/* 355 */     long lTempId = rs.getLong("tempId");
/* 356 */     psql.close();
/*     */ 
/* 358 */     long lDiff = lMaxId - lTempId;
/*     */ 
/* 360 */     if (lDiff > 0L)
/*     */     {
/* 362 */       psql = a_con.prepareStatement("ALTER SEQUENCE " + a_sSeq + " INCREMENT BY " + lDiff);
/* 363 */       psql.executeUpdate();
/* 364 */       psql.close();
/*     */ 
/* 366 */       psql = a_con.prepareStatement("SELECT " + a_sSeq + ".NEXTVAL AS tempId FROM dual");
/* 367 */       psql.executeUpdate();
/* 368 */       psql.close();
/*     */ 
/* 370 */       psql = a_con.prepareStatement("ALTER SEQUENCE " + a_sSeq + " INCREMENT BY 1");
/* 371 */       psql.executeUpdate();
/* 372 */       psql.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getNumberFieldOverThreshold(String a_sField, int a_iThreshold)
/*     */   {
/* 389 */     return "DECODE(greatest(" + a_sField + ", " + a_iThreshold + "), " + a_iThreshold + ", -1, " + a_sField + ") " + a_sField;
/*     */   }
/*     */ 
/*     */   public String getToNumberFunction(String a_sIdentifier)
/*     */   {
/* 394 */     String sExpression = "TO_NUMBER(" + a_sIdentifier + ")";
/* 395 */     return sExpression;
/*     */   }
/*     */ 
/*     */   public String getCharInValuesClause(String a_sIdentifier, String a_sValues)
/*     */   {
/* 410 */     String sClause = "TO_CHAR(" + a_sIdentifier + ")" + " IN (" + addQuotes(a_sValues) + ")";
/* 411 */     return sClause;
/*     */   }
/*     */ 
/*     */   public String getFieldNotEmptyStatement(String a_sIdentifier)
/*     */   {
/* 430 */     return a_sIdentifier + " IS NOT NULL";
/*     */   }
/*     */ 
/*     */   public void setupConnectionProperties(Properties a_properties)
/*     */   {
/* 436 */     if (FrameworkSettings.getDatabaseUsesNationalCharacterTypes())
/*     */     {
/* 443 */       a_properties.setProperty("defaultNChar", "true");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void testAlterSchema(Connection a_con)
/*     */     throws SQLStatementException
/*     */   {
/* 456 */     String sSql = null;
              PreparedStatement psql;
/*     */     try
/*     */     {
/*     */       try
/*     */       {
/* 465 */         sSql = "DROP TABLE TempAlterTest";
/*     */ 
/* 467 */         psql = a_con.prepareStatement(sSql);
/* 468 */         psql.execute();
/* 469 */         psql.close();
/*     */       }
/*     */       catch (SQLException e)
/*     */       {
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 479 */         sSql = "DROP SEQUENCE TempAlterTestSequence";
/*     */ 
/* 481 */         psql = a_con.prepareStatement(sSql);
/* 482 */         psql.execute();
/* 483 */         psql.close();
/*     */       }
/*     */       catch (SQLException e)
/*     */       {
/*     */       }
/*     */ 
/* 490 */       sSql = "CREATE SEQUENCE TempAlterTestSequence START WITH 1000 INCREMENT BY 1 NOCACHE NOCYCLE";
/*     */ 
/* 492 */       psql = a_con.prepareStatement(sSql);
/* 493 */       psql.execute();
/* 494 */       psql.close();
/*     */ 
/* 496 */       sSql = "CREATE TABLE TempAlterTest (Name Varchar2(200) NOT NULL , PRIMARY KEY (Name) )";
/*     */ 
/* 502 */       psql = a_con.prepareStatement(sSql);
/* 503 */       psql.execute();
/* 504 */       psql.close();
/*     */ 
/* 506 */       sSql = "ALTER TABLE TempAlterTest ADD TestAlterCol Varchar2(200)";
/*     */ 
/* 508 */       psql = a_con.prepareStatement(sSql);
/* 509 */       psql.execute();
/* 510 */       psql.close();
/*     */ 
/* 512 */       sSql = "ALTER TABLE TempAlterTest DROP COLUMN TestAlterCol";
/*     */ 
/* 514 */       psql = a_con.prepareStatement(sSql);
/* 515 */       psql.execute();
/* 516 */       psql.close();
/*     */ 
/* 518 */       sSql = "DROP TABLE TempAlterTest";
/*     */ 
/* 520 */       psql = a_con.prepareStatement(sSql);
/* 521 */       psql.execute();
/* 522 */       psql.close();
/*     */ 
/* 524 */       sSql = "DROP SEQUENCE TempAlterTestSequence";
/*     */ 
/* 526 */       psql = a_con.prepareStatement(sSql);
/* 527 */       psql.execute();
/* 528 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 532 */       throw new SQLStatementException(sSql, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String canonicaliseTableName(String a_sTableName)
/*     */     throws SQLException
/*     */   {
/* 545 */     return a_sTableName.toUpperCase();
/*     */   }
/*     */ 
/*     */   public String getIdInStatement(Vector<Long> a_vecIds, String a_sFieldName)
/*     */   {
/* 556 */     int iCounter = 0;
/*     */ 
/* 558 */     Vector<String> vecIdBatches = new Vector();
/* 559 */     Vector vecIdBatch = new Vector();
/*     */ 
/* 561 */     for (Long longId : a_vecIds)
/*     */     {
/* 563 */       vecIdBatch.add(longId);
/* 564 */       iCounter++;
/*     */ 
/* 566 */       if (iCounter % 1000 == 0)
/*     */       {
/* 568 */         vecIdBatches.add(StringUtil.convertNumbersToString(vecIdBatch, ","));
/* 569 */         vecIdBatch = new Vector();
/*     */       }
/*     */     }
/* 572 */     if (vecIdBatch.size() > 0)
/*     */     {
/* 574 */       vecIdBatches.add(StringUtil.convertNumbersToString(vecIdBatch, ","));
/*     */     }
/*     */ 
/* 577 */     String sInStatement = "";
/* 578 */     sInStatement = sInStatement + a_sFieldName + " IN (" + (String)vecIdBatches.get(0) + ") ";
/* 579 */     vecIdBatches.remove(0);
/*     */ 
/* 581 */     for (String sBatch : vecIdBatches)
/*     */     {
/* 583 */       sInStatement = sInStatement + " OR " + a_sFieldName + " IN (" + sBatch + ") ";
/*     */     }
/*     */ 
/* 586 */     if (StringUtil.stringIsPopulated(sInStatement))
/*     */     {
/* 588 */       sInStatement = "(" + sInStatement + ") ";
/*     */     }
/* 590 */     return sInStatement;
/*     */   }
/*     */ 
/*     */   public String getBlobSizeStatement(String a_sFieldName)
/*     */   {
/* 595 */     return "dbms_lob.getlength(" + a_sFieldName + ")";
/*     */   }
/*     */ 
/*     */   public String getDateOnlyFunction(String a_sDateTimeFieldName)
/*     */   {
/* 606 */     return "TRUNC(" + a_sDateTimeFieldName + ")";
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.sql.OracleSQLGenerator
 * JD-Core Version:    0.6.0
 */