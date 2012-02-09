package com.bright.framework.database.sql;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.framework.database.bean.ForeignKey;
import com.bright.framework.database.exception.SQLStatementException;
import com.bright.framework.io.InputStreamConsumer;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

public abstract interface ApplicationSql
{
  public abstract boolean usesAutoincrementFields();

  public abstract long getUniqueId(Connection paramConnection, String paramString)
    throws SQLException;

  public abstract String getNullCheckStatement(String paramString);

  public abstract String getFieldNotEmptyStatement(String paramString);

  public abstract String getRandomOrderByStatement();

  public abstract String getLowerCaseFunction(String paramString);

  public abstract String getUpperCaseFunction(String paramString);

  public abstract String getTrimFunction(String paramString);

  public abstract String getLengthFunction(String paramString);

  public abstract boolean getSupportsOnDuplicateKeyUpdate();

  public abstract void prepareForIdentityInsert(Connection paramConnection, String paramString)
    throws SQLException;

  public abstract void postIdentityInsert(Connection paramConnection, String paramString1, String paramString2)
    throws SQLException;

  public abstract String setRowLimit(String paramString, int paramInt);

  public abstract String getStringFromLargeTextField(ResultSet paramResultSet, String paramString)
    throws SQLException, Bn2Exception;

  public abstract String getOrderByForLargeTextField(String paramString, int paramInt);

  public abstract String getLengthUpperBoundFunctionForLargeTextField(String paramString);

  public abstract String getInitialEmptyBlobValue();

  public abstract void readBlob(Connection paramConnection, String paramString1, Object[] paramArrayOfObject, int[] paramArrayOfInt, String paramString2, InputStreamConsumer paramInputStreamConsumer)
    throws SQLException, IOException;

  public abstract void setBlobData(PreparedStatement paramPreparedStatement, InputStream paramInputStream, String paramString1, String paramString2, String paramString3, Object paramObject, int paramInt, long paramLong)
    throws IOException, SQLException;

  public abstract String getAsSelectForUpdate(String paramString);

  public abstract String getNumberFieldOverThreshold(String paramString, int paramInt);

  public abstract boolean getHasErrorsWithLongTextComparisons();

  public abstract String getCharInValuesClause(String paramString1, String paramString2);

  public abstract void setupConnectionProperties(Properties paramProperties);

  public abstract void testAlterSchema(Connection paramConnection)
    throws SQLStatementException;

  public abstract String getDefaultSchemaName(Connection paramConnection)
    throws SQLException, Bn2Exception;

  public abstract String getCatalog(Connection paramConnection)
    throws SQLException, Bn2Exception;

  public abstract String canonicaliseTableName(String paramString)
    throws SQLException;

  public abstract void dropAllImportedForeignKeys(Connection paramConnection, String paramString1, String paramString2, String paramString3)
    throws SQLException;

  public abstract void dropAllExportedForeignKeys(Connection paramConnection, String paramString1, String paramString2, String paramString3)
    throws SQLException;

  public abstract void dropAllForeignKeys(Connection paramConnection, String paramString1, String paramString2, String paramString3)
    throws SQLException;

  public abstract void dropForeignKey(Connection paramConnection, ForeignKey paramForeignKey)
    throws SQLException;

  public abstract void renameTable(Connection paramConnection, String paramString1, String paramString2)
    throws SQLException;

  public abstract String getIdInStatement(Vector<Long> paramVector, String paramString);

  public abstract boolean isRowSizeTooLarge(SQLException paramSQLException);

  public abstract String getBlobSizeStatement(String paramString);

  public abstract String getDateOnlyFunction(String paramString);
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.sql.ApplicationSql
 * JD-Core Version:    0.6.0
 */