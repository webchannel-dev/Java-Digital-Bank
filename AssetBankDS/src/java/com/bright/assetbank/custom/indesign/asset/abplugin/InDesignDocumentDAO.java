/*     */ package com.bright.assetbank.custom.indesign.asset.abplugin;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.custom.indesign.asset.bean.InDesignDocument;
/*     */ import com.bright.assetbank.database.AssetBankSql;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.exception.SQLStatementException;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class InDesignDocumentDAO
/*     */ {
/*     */   public InDesignDocument get(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/*  37 */     String sSQL = null;
/*     */     try
/*     */     {
/*  41 */       Connection con = a_transaction.getConnection();
/*     */ 
/*  43 */       sSQL = "SELECT idd.Id AS iddId, idd.InDesignPDFQualityId, idd.FileLocation, idd.OriginalFilename, idd.FileSizeInBytes, idd.NumSymbols FROM InDesignDocument idd WHERE idd.Id=?";
/*     */ 
/*  48 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  49 */       psql.setLong(1, a_lId);
/*     */ 
/*  51 */       ResultSet rs = psql.executeQuery();
/*     */       InDesignDocument result;
/*     */       //InDesignDocument result;
/*  54 */       if (rs.next())
/*     */       {
/*  56 */         result = InDesignDocumentDBUtil.createInDesignDocumentFromRS(rs);
/*     */       }
/*     */       else
/*     */       {
/*  60 */         result = null;
/*     */       }
/*     */ 
/*  63 */       psql.close();
/*     */ 
/*  65 */       return result;
/*     */     }
/*     */     catch (SQLException e) {
/*     */     
/*  69 */     throw new SQLStatementException(sSQL, e);}
/*     */   }
/*     */ 
/*     */   public void add(DBTransaction a_transaction, InDesignDocument a_indd)
/*     */     throws Bn2Exception
/*     */   {
/*  80 */     StringBuilder sbSQL = new StringBuilder();
/*     */     try
/*     */     {
/*  84 */       Connection con = a_transaction.getConnection();
/*  85 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*     */ 
/*  87 */       long newId = 0L;
/*  88 */       sbSQL.append("INSERT INTO InDesignDocument (");
/*  89 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/*  91 */         newId = sqlGenerator.getUniqueId(con, "InDesignDocumentSequence");
/*  92 */         sbSQL.append("Id, ");
/*     */       }
/*  94 */       sbSQL.append("InDesignPDFQualityId, FileLocation, OriginalFilename, FileSizeInBytes, NumSymbols");
/*  95 */       sbSQL.append(") ");
/*     */ 
/*  97 */       sbSQL.append("VALUES (");
/*  98 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 100 */         sbSQL.append("?, ");
/*     */       }
/* 102 */       sbSQL.append("?, ?, ?, ?, ?");
/* 103 */       sbSQL.append(")");
/*     */ 
/* 105 */       PreparedStatement psql = con.prepareStatement(sbSQL.toString());
/* 106 */       int param = 1;
/* 107 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 109 */         psql.setLong(param++, newId);
/*     */       }
/*     */ 
/* 112 */       param = InDesignDocumentDBUtil.prepareInDesignDocumentNonPK(psql, param, a_indd);
/*     */ 
/* 114 */       psql.executeUpdate();
/* 115 */       psql.close();
/* 116 */       if (sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 118 */         newId = sqlGenerator.getUniqueId(con, "InDesignDocumentSequence");
/*     */       }
/*     */ 
/* 121 */       a_indd.setId(newId);
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 125 */       throw new SQLStatementException(sbSQL.toString(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void update(DBTransaction a_transaction, InDesignDocument a_indd)
/*     */     throws Bn2Exception
/*     */   {
/* 133 */     String sSQL = "UPDATE InDesignDocument SET InDesignPDFQualityId = ?, FileLocation = ?, OriginalFilename = ?, FileSizeInBytes = ?, NumSymbols = ? WHERE Id = ?";
/*     */     try
/*     */     {
/* 142 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 145 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 146 */       int param = 1;
/*     */ 
/* 148 */       param = InDesignDocumentDBUtil.prepareInDesignDocumentNonPK(psql, param, a_indd);
/*     */ 
/* 150 */       psql.setLong(param++, a_indd.getId());
/*     */ 
/* 152 */       psql.executeUpdate();
/* 153 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 157 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 214 */     String sSQL = null;
/*     */     try
/*     */     {
/* 218 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 220 */       sSQL = "DELETE FROM InDesignDocument WHERE Id = ?";
/*     */ 
/* 222 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 223 */       psql.setLong(1, a_lId);
/*     */ 
/* 225 */       psql.executeUpdate();
/* 226 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 231 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.asset.abplugin.InDesignDocumentDAO
 * JD-Core Version:    0.6.0
 */