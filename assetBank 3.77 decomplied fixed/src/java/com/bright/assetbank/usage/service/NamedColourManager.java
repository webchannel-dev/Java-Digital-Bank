/*     */ package com.bright.assetbank.usage.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.exception.CannotDeleteReferredToRowException;
/*     */ import com.bright.assetbank.database.AssetBankSql;
/*     */ import com.bright.assetbank.usage.bean.NamedColour;
/*     */ import com.bright.assetbank.usage.util.NamedColourDBUtil;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.exception.SQLStatementException;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class NamedColourManager extends Bn2Manager
/*     */ {
/*     */   private DBTransactionManager m_transactionManager;
/*     */ 
/*     */   public List<NamedColour> getColours(DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  55 */     String sSQL = "SELECT nc.id AS ncId, nc.name AS ncName, nc.red, nc.green, nc.blue FROM NamedColour nc ORDER BY nc.name";
/*     */ 
/*  60 */     DBTransaction transaction = a_transaction;
/*     */ 
/*  62 */     if (transaction == null)
/*     */     {
/*  64 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  69 */       Connection con = transaction.getConnection();
/*  70 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  71 */       ResultSet rs = psql.executeQuery();
/*     */ 
/*  73 */       List<NamedColour> colours = new ArrayList();
/*  74 */       while (rs.next())
/*     */       {
/*  76 */         colours.add(NamedColourDBUtil.createNamedColourFromRS(rs));
/*     */       }
/*     */ 
/*  79 */       psql.close();
/*     */       return colours;
/*  81 */       //localList1 = colours;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*     */       List localList1;
/*  85 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */     finally
/*     */     {
/*  89 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/*  93 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/*  97 */           this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public NamedColour getColourById(DBTransaction a_transaction, long a_lNamedColourId)
/*     */     throws Bn2Exception
/*     */   {
/* 108 */     String sSQL = "SELECT nc.id AS ncId, nc.name AS ncName, nc.red, nc.green, nc.blue FROM NamedColour nc WHERE nc.Id=?";
/*     */ 
/* 113 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 115 */     if (transaction == null)
/*     */     {
/* 117 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 122 */       Connection con = transaction.getConnection();
/* 123 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 124 */       psql.setLong(1, a_lNamedColourId);
/*     */ 
/* 126 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 128 */       NamedColour colour = null;
/* 129 */       if (rs.next())
/*     */       {
/* 131 */         colour = NamedColourDBUtil.createNamedColourFromRS(rs);
/*     */       }
/*     */ 
/* 134 */       psql.close();
/*     */       return colour;
/* 136 */       //localNamedColour1 = colour;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*     */       NamedColour localNamedColour1;
/* 140 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */     finally
/*     */     {
/* 144 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 148 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 152 */           this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addColour(DBTransaction a_transaction, NamedColour a_colour)
/*     */     throws Bn2Exception
/*     */   {
/* 164 */     StringBuilder sbSQL = new StringBuilder();
/*     */ 
/* 166 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 168 */     if (transaction == null)
/*     */     {
/* 170 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 175 */       Connection con = transaction.getConnection();
/* 176 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*     */ 
/* 178 */       long lNewId = 0L;
/* 179 */       sbSQL.append("INSERT INTO NamedColour (");
/* 180 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 182 */         lNewId = sqlGenerator.getUniqueId(con, "NamedColourSequence");
/* 183 */         sbSQL.append("Id, ");
/*     */       }
/* 185 */       sbSQL.append("Name, Red, Green, Blue) ");
/*     */ 
/* 187 */       sbSQL.append("VALUES (");
/* 188 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 190 */         sbSQL.append("?, ");
/*     */       }
/* 192 */       sbSQL.append("?, ?, ?, ?)");
/*     */ 
/* 194 */       PreparedStatement psql = con.prepareStatement(sbSQL.toString());
/* 195 */       int iParam = 1;
/* 196 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 198 */         psql.setLong(iParam++, lNewId);
/*     */       }
/*     */ 
/* 201 */       iParam = NamedColourDBUtil.prepareNamedColourStatement(a_colour, psql, iParam);
/*     */ 
/* 203 */       psql.executeUpdate();
/* 204 */       psql.close();
/* 205 */       if (sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 207 */         lNewId = sqlGenerator.getUniqueId(con, "NamedColourSequence");
/*     */       }
/*     */ 
/* 210 */       a_colour.setId(lNewId);
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 214 */       throw new SQLStatementException(sbSQL.toString(), e);
/*     */     }
/*     */     finally
/*     */     {
/* 218 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 222 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 226 */           this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateColour(DBTransaction a_transaction, NamedColour a_colour)
/*     */     throws Bn2Exception
/*     */   {
/* 235 */     String sSQL = null;
/*     */ 
/* 237 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 239 */     if (transaction == null)
/*     */     {
/* 241 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 246 */       Connection con = transaction.getConnection();
/*     */ 
/* 248 */       sSQL = "UPDATE NamedColour SET Name=?,Red=?,Green=?,Blue=? WHERE Id=?";
/*     */ 
/* 255 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 256 */       int iParam = 1;
/* 257 */       iParam = NamedColourDBUtil.prepareNamedColourStatement(a_colour, psql, iParam);
/*     */ 
/* 259 */       psql.setLong(iParam++, a_colour.getId());
/*     */ 
/* 261 */       psql.executeUpdate();
/* 262 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 266 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */     finally
/*     */     {
/* 270 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 274 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 278 */           this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteColour(DBTransaction a_transaction, long a_lNamedColourId)
/*     */     throws Bn2Exception
/*     */   {
/* 289 */     DBTransaction transaction = a_transaction;
/* 290 */     if (transaction == null)
/*     */     {
/* 292 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 297 */       if (colourIsReferredTo(a_transaction, a_lNamedColourId))
/*     */       {
/* 299 */         throw new CannotDeleteReferredToRowException("NamedColour", a_lNamedColourId);
/*     */       }
/*     */ 
/* 302 */       deleteColourFromDB(a_transaction, a_lNamedColourId);
/*     */     }
/*     */     finally
/*     */     {
/* 306 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 310 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 314 */           this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean colourIsReferredTo(DBTransaction a_transaction, long a_lNamedColourId) throws Bn2Exception
/*     */   {
/* 325 */     ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/*     */ 
/* 327 */     String sSQL = sqlGenerator.getAsSelectForUpdate("SELECT 1 FROM UsageTypeFormat WHERE PresetMaskColourId=?");
/*     */     boolean isReferredTo;
/*     */     try {
/* 332 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 334 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 335 */       psql.setLong(1, a_lNamedColourId);
/*     */ 
/* 337 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 340 */       isReferredTo = rs.next();
/*     */ 
/* 342 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 346 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */ 
/* 349 */     return isReferredTo;
/*     */   }
/*     */ 
/*     */   private void deleteColourFromDB(DBTransaction a_transaction, long a_lNamedColourId)
/*     */     throws Bn2Exception
/*     */   {
/* 355 */     String sSQL = "DELETE FROM NamedColour WHERE Id=?";
/*     */ 
/* 357 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 359 */     if (transaction == null)
/*     */     {
/* 361 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 366 */       Connection con = transaction.getConnection();
/*     */ 
/* 368 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 369 */       psql.setLong(1, a_lNamedColourId);
/*     */ 
/* 371 */       psql.executeUpdate();
/* 372 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 376 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */     finally
/*     */     {
/* 380 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 384 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 388 */           this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public DBTransactionManager getTransactionManager()
/*     */   {
/* 399 */     return this.m_transactionManager;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/* 404 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.service.NamedColourManager
 * JD-Core Version:    0.6.0
 */