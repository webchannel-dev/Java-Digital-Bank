/*     */ package com.bright.framework.database.bean;
/*     */ 
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.NClob;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLClientInfoException;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.sql.SQLXML;
/*     */ import java.sql.Savepoint;
/*     */ import java.sql.Statement;
/*     */ import java.sql.Struct;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class AdaptedConnection
/*     */   implements Connection
/*     */ {
/*  45 */   private Connection m_connection = null;
/*  46 */   private boolean m_bUseNationalCharacterTypes = false;
/*     */ 
/*     */   public AdaptedConnection(Connection a_connection)
/*     */   {
/*  50 */     this.m_connection = a_connection;
/*     */   }
/*     */ 
/*     */   public AdaptedConnection(Connection a_connection, boolean a_bUseNationalCharacterTypes)
/*     */   {
/*  55 */     this.m_connection = a_connection;
/*  56 */     this.m_bUseNationalCharacterTypes = a_bUseNationalCharacterTypes;
/*     */   }
/*     */ 
/*     */   public Connection getNativeConnection()
/*     */   {
/*  61 */     return this.m_connection;
/*     */   }
/*     */ 
/*     */   public void clearWarnings() throws SQLException
/*     */   {
/*  66 */     this.m_connection.clearWarnings();
/*     */   }
/*     */ 
/*     */   public void close() throws SQLException
/*     */   {
/*  71 */     this.m_connection.close();
/*     */   }
/*     */ 
/*     */   public void commit() throws SQLException
/*     */   {
/*  76 */     this.m_connection.commit();
/*     */   }
/*     */ 
/*     */   public Statement createStatement() throws SQLException
/*     */   {
/*  81 */     return this.m_connection.createStatement();
/*     */   }
/*     */ 
/*     */   public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
/*     */   {
/*  86 */     return this.m_connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
/*     */   }
/*     */ 
/*     */   public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException
/*     */   {
/*  91 */     return this.m_connection.createStatement(resultSetType, resultSetConcurrency);
/*     */   }
/*     */ 
/*     */   public boolean getAutoCommit() throws SQLException
/*     */   {
/*  96 */     return this.m_connection.getAutoCommit();
/*     */   }
/*     */ 
/*     */   public String getCatalog() throws SQLException
/*     */   {
/* 101 */     return this.m_connection.getCatalog();
/*     */   }
/*     */ 
/*     */   public int getHoldability() throws SQLException
/*     */   {
/* 106 */     return this.m_connection.getHoldability();
/*     */   }
/*     */ 
/*     */   public DatabaseMetaData getMetaData() throws SQLException
/*     */   {
/* 111 */     return this.m_connection.getMetaData();
/*     */   }
/*     */ 
/*     */   public int getTransactionIsolation() throws SQLException
/*     */   {
/* 116 */     return this.m_connection.getTransactionIsolation();
/*     */   }
/*     */ 
/*     */   public Map getTypeMap() throws SQLException
/*     */   {
/* 121 */     return this.m_connection.getTypeMap();
/*     */   }
/*     */ 
/*     */   public SQLWarning getWarnings() throws SQLException
/*     */   {
/* 126 */     return this.m_connection.getWarnings();
/*     */   }
/*     */ 
/*     */   public boolean isClosed() throws SQLException
/*     */   {
/* 131 */     return this.m_connection.isClosed();
/*     */   }
/*     */ 
/*     */   public boolean isReadOnly() throws SQLException
/*     */   {
/* 136 */     return this.m_connection.isReadOnly();
/*     */   }
/*     */ 
/*     */   public String nativeSQL(String sql) throws SQLException
/*     */   {
/* 141 */     return this.m_connection.nativeSQL(sql);
/*     */   }
/*     */ 
/*     */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
/*     */   {
/* 146 */     return this.m_connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
/*     */   }
/*     */ 
/*     */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException
/*     */   {
/* 151 */     return this.m_connection.prepareCall(sql, resultSetType, resultSetConcurrency);
/*     */   }
/*     */ 
/*     */   public CallableStatement prepareCall(String sql) throws SQLException
/*     */   {
/* 156 */     return this.m_connection.prepareCall(sql);
/*     */   }
/*     */ 
/*     */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
/*     */   {
/* 161 */     return new AdaptedStatement(this.m_connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability), this.m_bUseNationalCharacterTypes);
/*     */   }
/*     */ 
/*     */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException
/*     */   {
/* 166 */     return new AdaptedStatement(this.m_connection.prepareStatement(sql, resultSetType, resultSetConcurrency), this.m_bUseNationalCharacterTypes);
/*     */   }
/*     */ 
/*     */   public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException
/*     */   {
/* 171 */     return new AdaptedStatement(this.m_connection.prepareStatement(sql, autoGeneratedKeys), this.m_bUseNationalCharacterTypes);
/*     */   }
/*     */ 
/*     */   public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException
/*     */   {
/* 176 */     return new AdaptedStatement(this.m_connection.prepareStatement(sql, columnIndexes), this.m_bUseNationalCharacterTypes);
/*     */   }
/*     */ 
/*     */   public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException
/*     */   {
/* 181 */     return new AdaptedStatement(this.m_connection.prepareStatement(sql, columnNames), this.m_bUseNationalCharacterTypes);
/*     */   }
/*     */ 
/*     */   public PreparedStatement prepareStatement(String sql) throws SQLException
/*     */   {
/* 186 */     return new AdaptedStatement(this.m_connection.prepareStatement(sql), this.m_bUseNationalCharacterTypes);
/*     */   }
/*     */ 
/*     */   public void releaseSavepoint(Savepoint savepoint) throws SQLException
/*     */   {
/* 191 */     this.m_connection.releaseSavepoint(savepoint);
/*     */   }
/*     */ 
/*     */   public void rollback() throws SQLException
/*     */   {
/* 196 */     this.m_connection.rollback();
/*     */   }
/*     */ 
/*     */   public void rollback(Savepoint savepoint) throws SQLException
/*     */   {
/* 201 */     this.m_connection.rollback(savepoint);
/*     */   }
/*     */ 
/*     */   public void setAutoCommit(boolean autoCommit) throws SQLException
/*     */   {
/* 206 */     this.m_connection.setAutoCommit(autoCommit);
/*     */   }
/*     */ 
/*     */   public void setCatalog(String catalog) throws SQLException
/*     */   {
/* 211 */     this.m_connection.setCatalog(catalog);
/*     */   }
/*     */ 
/*     */   public void setHoldability(int holdability) throws SQLException
/*     */   {
/* 216 */     this.m_connection.setHoldability(holdability);
/*     */   }
/*     */ 
/*     */   public void setReadOnly(boolean readOnly) throws SQLException
/*     */   {
/* 221 */     this.m_connection.setReadOnly(readOnly);
/*     */   }
/*     */ 
/*     */   public Savepoint setSavepoint() throws SQLException
/*     */   {
/* 226 */     return this.m_connection.setSavepoint();
/*     */   }
/*     */ 
/*     */   public Savepoint setSavepoint(String name) throws SQLException
/*     */   {
/* 231 */     return this.m_connection.setSavepoint(name);
/*     */   }
/*     */ 
/*     */   public void setTransactionIsolation(int level) throws SQLException
/*     */   {
/* 236 */     this.m_connection.setTransactionIsolation(level);
/*     */   }
/*     */ 
/*     */   public void setTypeMap(Map arg0) throws SQLException
/*     */   {
/* 241 */     this.m_connection.setTypeMap(arg0);
/*     */   }
/*     */ 
/*     */   public Array createArrayOf(String typeName, Object[] elements)
/*     */     throws SQLException
/*     */   {
/* 247 */     return null;
/*     */   }
/*     */ 
/*     */   public Blob createBlob() throws SQLException
/*     */   {
/* 252 */     return null;
/*     */   }
/*     */ 
/*     */   public Clob createClob() throws SQLException
/*     */   {
/* 257 */     return null;
/*     */   }
/*     */ 
/*     */   public NClob createNClob() throws SQLException
/*     */   {
/* 262 */     return null;
/*     */   }
/*     */ 
/*     */   public SQLXML createSQLXML() throws SQLException
/*     */   {
/* 267 */     return null;
/*     */   }
/*     */ 
/*     */   public Struct createStruct(String typeName, Object[] attributes)
/*     */     throws SQLException
/*     */   {
/* 273 */     return null;
/*     */   }
/*     */ 
/*     */   public Properties getClientInfo() throws SQLException
/*     */   {
/* 278 */     return null;
/*     */   }
/*     */ 
/*     */   public String getClientInfo(String name) throws SQLException
/*     */   {
/* 283 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isValid(int timeout) throws SQLException
/*     */   {
/* 288 */     return false;
/*     */   }
/*     */ 
/*     */   public void setClientInfo(Properties properties)
/*     */     throws SQLClientInfoException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setClientInfo(String name, String value)
/*     */     throws SQLClientInfoException
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean isWrapperFor(Class arg0)
/*     */     throws SQLException
/*     */   {
/* 305 */     return false;
/*     */   }
/*     */ 
/*     */   public Object unwrap(Class arg0) throws SQLException
/*     */   {
/* 310 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.bean.AdaptedConnection
 * JD-Core Version:    0.6.0
 */