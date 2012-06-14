/*     */ package com.bright.assetbank.plugin.sampleplugin.abplugin;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.plugin.sampleplugin.bean.AssetEntityDescription;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.exception.SQLStatementException;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ public class AssetEntityDescriptionService
/*     */ {
/*     */   public AssetEntityDescription get(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/*  37 */     String sSQL = null;
/*     */     try
/*     */     {
/*  41 */       Connection con = a_transaction.getConnection();
/*     */ 
/*  43 */       sSQL = "SELECT Description FROM AssetEntityDescription WHERE AssetEntityId=?";
/*     */ 
/*  47 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  48 */       psql.setLong(1, a_lId);
/*     */ 
/*  50 */       ResultSet rs = psql.executeQuery();
/*     */       AssetEntityDescription result;
/*  53 */       if (rs.next())
/*     */       {
/*  55 */         result = new AssetEntityDescription();
/*  56 */         result.setDescription(rs.getString("Description"));
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
/*     */   public void add(DBTransaction a_transaction, long a_lId, AssetEntityDescription a_extensionData)
/*     */     throws Bn2Exception
/*     */   {
/*  78 */     String sSQL = null;
/*     */     try
/*     */     {
/*  82 */       Connection con = a_transaction.getConnection();
/*     */ 
/*  84 */       sSQL = "INSERT INTO AssetEntityDescription (AssetEntityId, Description) VALUES (?, ?)";
/*     */ 
/*  88 */       PreparedStatement psql = con.prepareStatement(sSQL);
/*  89 */       psql.setLong(1, a_lId);
/*  90 */       psql.setString(2, a_extensionData.getDescription());
/*     */ 
/*  92 */       psql.executeUpdate();
/*  93 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*  97 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addOrUpdate(DBTransaction a_transaction, long a_lId, AssetEntityDescription a_extensionData)
/*     */     throws Bn2Exception
/*     */   {
/* 110 */     String sSQL = null;
/*     */     try
/*     */     {
/* 114 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 117 */       if (!SQLGenerator.getInstance().getSupportsOnDuplicateKeyUpdate())
/*     */       {
/* 119 */         sSQL = "DELETE FROM AssetEntityDescription WHERE AssetEntityId = ?";
/*     */ 
/* 121 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 122 */         psql.setLong(1, a_lId);
/*     */ 
/* 124 */         psql.executeUpdate();
/* 125 */         psql.close();
/*     */ 
/* 127 */         sSQL = "INSERT INTO AssetEntityDescription (AssetEntityId, Description) VALUES (?, ?)";
/*     */       }
/*     */       else
/*     */       {
/* 133 */         sSQL = "INSERT INTO AssetEntityDescription (AssetEntityId, Description) VALUES (?, ?) ON DUPLICATE KEY UPDATE Description = VALUES(Description)";
/*     */       }
/*     */ 
/* 138 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 139 */       psql.setLong(1, a_lId);
/* 140 */       psql.setString(2, a_extensionData.getDescription());
/*     */ 
/* 142 */       psql.executeUpdate();
/* 143 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 147 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void update(DBTransaction a_transaction, long a_lId, AssetEntityDescription a_extensionData)
/*     */     throws Bn2Exception
/*     */   {
/* 155 */     String sSQL = null;
/*     */     try
/*     */     {
/* 159 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 161 */       sSQL = "UPDATE AssetEntityDescription SET Description = ? WHERE AssetEntityId = ?";
/*     */ 
/* 165 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 166 */       psql.setString(1, a_extensionData.getDescription());
/* 167 */       psql.setLong(2, a_lId);
/*     */ 
/* 169 */       psql.executeUpdate();
/* 170 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 174 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void delete(DBTransaction a_transaction, long a_lId) throws Bn2Exception
/*     */   {
/* 180 */     String sSQL = null;
/*     */     try
/*     */     {
/* 184 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 186 */       sSQL = "DELETE FROM AssetEntityDescription WHERE AssetEntityId = ?";
/*     */ 
/* 188 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 189 */       psql.setLong(1, a_lId);
/*     */ 
/* 191 */       psql.executeUpdate();
/* 192 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 197 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.sampleplugin.abplugin.AssetEntityDescriptionService
 * JD-Core Version:    0.6.0
 */